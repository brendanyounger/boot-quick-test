(ns brendanyounger.boot-quick-test
  {:boot/export-tasks true}
  (:require
    [boot.core :as boot]
    [clojure.test :as test]
    [clojure.java.io :as io]
    [ns-tracker.core :refer [ns-tracker]]
    [clojure.tools.namespace.find :refer [find-namespaces-in-dir]]))

(defn- test-ns* [pred ns]
  (binding [test/*report-counters* (ref test/*initial-report-counters*)]
    (let [ns-obj (the-ns ns)]
      (test/do-report {:type :begin-test-ns :ns ns-obj})
      (test/test-vars (filter pred (vals (ns-publics ns))))
      (test/do-report {:type :end-test-ns :ns ns-obj}))
    @test/*report-counters*))

(boot/deftask quick-test
  "Quickly run tests without a pod. This is mostly useful when doing something like: `boot watch speak quick-test` to keep an eye on tests in the background.  It does not play well with being run from the repl or anywhere else that loads the `boot.user` namespace."
  []
  (boot/merge-env! :source-paths (boot/get-env :test-paths))
  (let [modified-namespaces (ns-tracker (seq (boot/get-env :directories)))]
    (boot/with-pre-wrap fileset
      (let [namespaces (mapcat #(find-namespaces-in-dir (io/file %)) (boot/get-env :test-paths))
            modified (modified-namespaces)]
        (doseq [ns (concat modified (remove (set modified) namespaces))] (require ns :reload))
        (let [ns-results (mapv (partial test-ns* identity) namespaces)
              summary
              (->
                (reduce (partial merge-with +) ns-results)
                (assoc :type :summary)
                (doto test/do-report))
              errors (apply + (map summary [:fail :error]))]
          (when (> errors 0)
            (throw (ex-info "Some tests failed or errored" summary)))))
      fileset)))
