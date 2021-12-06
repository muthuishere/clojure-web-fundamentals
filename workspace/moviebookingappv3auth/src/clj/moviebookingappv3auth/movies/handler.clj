(ns moviebookingappv3auth.movies.handler
  (:require
    [moviebookingappv3auth.db.core :as db]
    [ring.util.response :refer :all]
    )
  )


(def movies (atom []))

(comment


  (clojure.string/split   ({:genres "an,b"} :genres) #",")
  (parse-movie {:genres "an,b"})
  )
(defn parse-movie [input]

  (let
    [
     genres-as-string (input :genres)
     genres (clojure.string/split  genres-as-string #",")
     ]
    (assoc input :genres genres)

    )


  )

(comment
  (all-movies nil)
  )
(defn all-movies [request]

  (let [movies (db/get-all-movies) ]
  (println "Total Movies" movies)
  {:status 200
   :body   (map parse-movie movies)

   }
  )
  )

(comment
  ;To test in repl
  (db/get-movie-by-id {:id 1})
  (db/get-movie-by-id {:id 147})


  )
(defn movie-by-id [request]


  (let [
        id (Integer/parseInt (get-in request [:path-params :id]))
        result (db/get-movie-by-id {:id id})
        ]

    (print result)
    (if (nil? result)
      {:status 404
       :body   "Movie not found"
       }
      {:status 200
       :body   (parse-movie result)
       }

      )

    )

  )





;Find a remote REPL for Clojure , And show all the values

(comment
  ;To test in repl
  (db/insert-movie! { :plot
                     "Four denizens in the world of high-finance predict the credit and housing bubble collapse of the mid-2000s, and decide to take on the big banks for their greed and lack of foresight.",
                     :director "Adam McKay",
                     :genres   (clojure.string/join "," ["Biography" "Comedy" "Drama"]),
                     :title    "The Big Short",
                     :year     "2015",
                     :actors   "Ryan Gosling, Rudy Eisenzopf, Casey Groves, Charlie Talbert",
                     :id       146,
                     :runtime  "130",
                     :posterUrl
                     "https://images-na.ssl-images-amazon.com/images/M/MV5BNDc4MThhN2EtZjMzNC00ZDJmLThiZTgtNThlY2UxZWMzNjdkXkEyXkFqcGdeQXVyNDk3NzU2MTQ@._V1_SX300.jpg"
                     }
              )

  ;Updated movie with insert movie

  (insert-movie {
                 :body-params { :plot
                               "Four denizens in the world of high-finance predict the credit and housing bubble collapse of the mid-2000s, and decide to take on the big banks for their greed and lack of foresight.",
                               :director "Adam McKay",
                               :genres   ["Biography" "Comedy" "Drama"],
                               :title    "The Big Short",
                               :year     "2015",
                               :actors   "Ryan Gosling, Rudy Eisenzopf, Casey Groves, Charlie Talbert",
                               :id       147,
                               :runtime  "130",
                               :posterUrl
                               "https://images-na.ssl-images-amazon.com/images/M/MV5BNDc4MThhN2EtZjMzNC00ZDJmLThiZTgtNThlY2UxZWMzNjdkXkEyXkFqcGdeQXVyNDk3NzU2MTQ@._V1_SX300.jpg"
                               }

                 })

  )
(defn insert-movie [request]

  (println (get request :body-params))
  ;(swap! movies conj (get request :body-params))

  (let [
        movie (get request :body-params)
        concatenated-genres (clojure.string/join "," (get movie :genres))
        updated-movie (assoc movie :genres concatenated-genres)
        affected-rows (db/insert-movie! updated-movie
                                        )]

    {:status 200
     :body   {
              :result     (str "Succesfully updated " affected-rows  " row"  )
              :request    updated-movie
              }
     }

    )


  )

(comment


  ;To test in repl
  (db/update-movie! { :plot
                     "runtime 530 Four denizens in the world of high-finance predict the credit and housing bubble collapse of the mid-2000s, and decide to take on the big banks for their greed and lack of foresight.",
                     :director "Adam McKay",
                     :genres   (clojure.string/join "," ["Biography" "Comedy" "Drama"]),
                     :title    "The Big Short",
                     :year     "2015",
                     :actors   "Ryan Gosling, Rudy Eisenzopf, Casey Groves, Charlie Talbert",
                     :id       147,
                     :runtime  "530",
                     :posterUrl
                     "https://images-na.ssl-images-amazon.com/images/M/MV5BNDc4MThhN2EtZjMzNC00ZDJmLThiZTgtNThlY2UxZWMzNjdkXkEyXkFqcGdeQXVyNDk3NzU2MTQ@._V1_SX300.jpg"
                     }
                    )

  (update-movie-by-id {
                       :path-params {
                                     :id "147"
                                     }

                       :body-params { :plot
                                     "runtime 690 Four denizens in the world of high-finance predict the credit and housing bubble collapse of the mid-2000s, and decide to take on the big banks for their greed and lack of foresight.",
                                     :director "Adam McKay",
                                     :genres    ["anotBiography" "Comedy" "Drama"],
                                     :title    "The Big Short",
                                     :year     "2015",
                                     :actors   "Ryan Gosling, Rudy Eisenzopf, Casey Groves, Charlie Talbert",

                                     :runtime  "690",
                                     :posterUrl
                                     "https://images-na.ssl-images-amazon.com/images/M/MV5BNDc4MThhN2EtZjMzNC00ZDJmLThiZTgtNThlY2UxZWMzNjdkXkEyXkFqcGdeQXVyNDk3NzU2MTQ@._V1_SX300.jpg"
                                     }

                       })

  )
(defn update-movie-by-id [request]


  (let [
        id (Integer/parseInt (get-in request [:path-params :id]))
        movie (get request :body-params)
        concatenated-genres (clojure.string/join "," (get movie :genres))
        updated-movie (assoc movie :genres concatenated-genres :id id)
        affected-rows (db/update-movie! updated-movie
                                        )]

    {:status 200
     :body   {
              :result     (str "Succesfully updated " affected-rows  " row"  )
              :request    updated-movie
              }
     }

    )

  )


(defn delete-movie-by-id [request]

  (let [
        id (Integer/parseInt (get-in request [:path-params :id]))
        affected-rows (db/delete-movie-by-id! {:id id})
        ]


    (response {
               :result    (str "Succesfully deleted " affected-rows  " row"  )


               } )

    )

  )

(comment

  (delete-movie-by-id {
                       :path-params {
                                     :id "147"
                                     }}
                      )
  )

