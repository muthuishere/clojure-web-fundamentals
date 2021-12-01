(ns real-clojure.dates-test
  (:require [real-clojure.dates :as dates]
            [clojure.test :refer :all])

  ;(:use [real-clojure.dates])
  ;(:use [clojure.test])
  )


(deftest test-number-to-three-character-month
  (is  (= "Jan" (dates/number-to-three-character-month 1)))
  (is  (= "Feb" (dates/number-to-three-character-month 2)))
  
  )
