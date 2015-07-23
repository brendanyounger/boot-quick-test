(set-env!
  :source-paths #{"src"}
  :test-paths #{"test"}
  :dependencies
  '[[adzerk/bootlaces "0.1.11" :scope "test"]
    [ns-tracker "0.3.0"]
    [org.clojure/tools.namespace "0.2.11"]])

(require '[adzerk.bootlaces :refer :all])
(require '[brendanyounger.boot-quick-test :refer :all])

(def +version+ "0.1.0")
(bootlaces! +version+)

(task-options!
  pom
  { :project     'brendanyounger/boot-quick-test
    :version     +version+
    :description "Boot task to quickly run clojure.test tests."
    :url         "https://github.com/brendanyounger/boot-quick-test"
    :scm         {:url "https://github.com/brendanyounger/boot-quick-test"}
    :license     {"EPL" "http://www.eclipse.org/legal/epl-v10.html"}})
