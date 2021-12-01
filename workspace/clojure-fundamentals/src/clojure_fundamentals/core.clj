(ns clojure-fundamentals.core)


      ; 1 => Right Click 

      ; 2 => Select Start or Connect to Clojure REPL

      ; 3 => Choose  : Start Your Project with REPL and connect (a.k.a Jackin)

      ; 4 =>Choose :   Leiningen

      ; Finally you should see a repl on right side


"Hello Clojure"

(+ 2 3)


(defn sum-of-numbers-till-10[] 
  
  (loop [i  1 sum 0]
   (if (<= i 10)
     (recur (inc i) (+ i sum))
     sum))
  )



(sum-of-numbers-till-10)






(defn print-4-welcome-with-indexes []

  (loop [index 1]
    (when (<= index 4)

      (println "Welcome " index)
      (recur (inc index)))))

(print-4-welcome-with-indexes)




;Map
{}

{:name "Peter Parker" :alias "Spider Man" :age 25}

(hash-map :name "Peter Parker" :alias "Spider Man" :age 25 )


(def spider-man {:name "Peter Parker" :alias "Spider Man" :age 25} )

;Retrieving from Map
(get spider-man :name)

(spider-man :name)

;Only Keywords has the cpability of retrieving by placing on front
(:name spider-man)


(conj spider-man {:latest-movie "No Way Home"})

(print spider-man)


(def spider-man-with-latest-movie  (conj spider-man {:latest-movie "No Way Home"}))

(println spider-man-with-latest-movie)

(keys spider-man)

(vals spider-man)

;remove a key
(dissoc spider-man :age)


(count spider-man)


(def dr-strange {"name" "Dr Stepehn Strange" "age" 45})


(get dr-strange "name")
(dr-strange "name")

(def months {1 "January" 2 "february"}  )

(months 1)
(months 13)

(months 13 "Invalid Month")


;Convert between keyword and string
(keyword "name")
(name :name)


(def spider-map {:name "Peter "})
(+ 2 3)
(peek ["peter parker" " mj" "Stephen"])




