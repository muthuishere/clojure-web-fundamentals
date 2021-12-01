(ns real-clojure.dates)





(defn number-to-three-character-month [number]


  (get  {1 "Jan" 2 "Feb" 3 "Mar" 4 "Apr" 5 "May" 6 "Jun" 7 "Jul" 8 "Aug" 9 "Sep" 10 "Oct" 11 "Nov" 12 "Dec"} number)
  
  )


;(number-to-three-character-month 2)


(defn add [ a b]

  (+ a b)
  
  )


(comment 


  
;(add 2 3)

(drop 200 (range 300))


(drop 1 (take 2 [34 21 75]))


(drop 5 (take 10 (range)))

  (defn is-less-than-20? [n]
    (< n 20))
  
  
  ; List<Long> items =[]
  ;for(Long n :numbers){
  ;  if(n < 20){
  ;  break
  ;else
  ;  items.add(n)                                                 ;
  ; }
(take-while is-less-than-20? [34 56 7 8 12])

(take-while is-less-than-20? [7 8 12 34 56])


(take-while is-less-than-20? [7 8 34 12 56])


(take-while (fn [n]
              (< n 20)) [7 8 34 12 56])

(take-while #(< % 20) [7 8 34 12 56])



; Home Assignment
;In Array
["Spider Man" "Iron Man" "Captain America"]

  ; drop-while , if the string contains Man
;  Result("Captain America")



)