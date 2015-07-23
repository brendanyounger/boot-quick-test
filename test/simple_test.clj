(ns simple-test
  (:require
    [clojure.test :refer :all]))

(deftest basic-logic
  (is (= 2 (+ 1 1))))
