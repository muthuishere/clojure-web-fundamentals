(ns real-clojure.core

  (:require  [real-clojure.dates :as dates])
(:gen-class)
  )

(defn foo
  "I don't do a whole lot."
  [x]
  (println x "Hello, World!"))


(defn -main [month]
  
  ;Home Assignment
  ;COnvert String to Integer

  ; Accept a parameter called month and print its abbrevation

  ;lein run 2  => Feb
  ;lein run 4  => Apr

  (println "Month number to value " (dates/number-to-three-character-month month))

  
  )