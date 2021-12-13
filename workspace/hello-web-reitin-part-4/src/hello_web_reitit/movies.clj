(ns hello-web-reitit.movies
  (:require
    [reitit.ring :as ring]
    [muuntaja.middleware :as middleware]
    [ring.middleware.defaults :refer [wrap-defaults site-defaults api-defaults]]
    )
  (:use [org.httpkit.server :only [run-server]])

  )

(def movies (atom [{:plot
                    "A couple of recently deceased ghosts contract the services of a \"bio-exorcist\" in order to remove the obnoxious new owners of their house.",
                    :director "Tim Burton",
                    :genres   ["Comedy" "Fantasy"],
                    :title    "Beetlejuice",
                    :year     "1988",
                    :actors   "Alec Baldwin, Geena Davis, Annie McEnroe, Maurice Page",
                    :id       1,
                    :runtime  "92",
                    :posterUrl
                    "https://images-na.ssl-images-amazon.com/images/M/MV5BMTUwODE3MDE0MV5BMl5BanBnXkFtZTgwNTk1MjI4MzE@._V1_SX300.jpg"}
                   {:plot
                    "The Cotton Club was a famous night club in Harlem. The story follows the people that visited the club, those that ran it, and is peppered with the Jazz music that made it so famous.",
                    :director "Francis Ford Coppola",
                    :genres   ["Crime" "Drama" "Music"],
                    :title    "The Cotton Club",
                    :year     "1984",
                    :actors   "Richard Gere, Gregory Hines, Diane Lane, Lonette McKee",
                    :id       2,
                    :runtime  "127",
                    :posterUrl
                    "https://images-na.ssl-images-amazon.com/images/M/MV5BMTU5ODAyNzA4OV5BMl5BanBnXkFtZTcwNzYwNTIzNA@@._V1_SX300.jpg"}
                   {:plot
                    "Two imprisoned men bond over a number of years, finding solace and eventual redemption through acts of common decency.",
                    :director "Frank Darabont",
                    :genres   ["Crime" "Drama"],
                    :title    "The Shawshank Redemption",
                    :year     "1994",
                    :actors   "Tim Robbins, Morgan Freeman, Bob Gunton, William Sadler",
                    :id       3,
                    :runtime  "142",
                    :posterUrl
                    "https://images-na.ssl-images-amazon.com/images/M/MV5BODU4MjU4NjIwNl5BMl5BanBnXkFtZTgwMDU2MjEyMDE@._V1_SX300.jpg"}
                   {:plot
                    "An American reporter goes to the Australian outback to meet an eccentric crocodile poacher and invites him to New York City.",
                    :director "Peter Faiman",
                    :genres   ["Adventure" "Comedy"],
                    :title    "Crocodile Dundee",
                    :year     "1986",
                    :actors   "Paul Hogan, Linda Kozlowski, John Meillon, David Gulpilil",
                    :id       4,
                    :runtime  "97",
                    :posterUrl
                    "https://images-na.ssl-images-amazon.com/images/M/MV5BMTg0MTU1MTg4NF5BMl5BanBnXkFtZTgwMDgzNzYxMTE@._V1_SX300.jpg"}]
                  ))

(defn all-movies [request]

  (println "Total Movies" (count @movies))
  {:status 200
   :body   @movies

   }

  )
(defn movie-by-id [request]


  (let [
        id (Integer/parseInt (get-in request [:path-params :id]))
        result (first (filter #(= id (get % :id)) @movies))
        ]

    (print result)
    (if (nil? result)
      {:status 404
       :body   "Movie not found"
       }
      {:status 200
       :body   result
       }

      )

    )

  )





;Find a remote REPL for Clojure , And show all the values

(defn insert-movie [request]

  (println (get request :body-params))
  (swap! movies conj (get request :body-params))

  {:status 200
   :body   {
            :result     "Succesfully Inserted"
            :request    (get request :body-params)
            :all-movies @movies
            }
   }

  )

(defn update-movie-for [id updated-data existing-movies]
  (map #(if (= (get % :id) id)
          (merge % updated-data)
          %)
       existing-movies
       )

  )


(defn update-movie-by-id-old [request]


  (let [
        id (Integer/parseInt (get-in request [:path-params :id]))
        updated-data (get request :body-params)

        ]



    (swap! movies (fn [values]
                    (update-movie-for id updated-data values)
                    ))

    {:status 200
     :body   {
              :result     "Succesfully Updated Movie"
              :request    (get request :body-params)
              :all-movies @movies
              }
     }

    )

  )

(defn update-movie-by-id [request]

  (let [
        id (Integer/parseInt (get-in request [:path-params :id]))
        updated-data (get request :body-params)
        update-movie-handler (partial update-movie-for id updated-data)

        ]

    (println "updated data" updated-data)
    ;
    ;(swap! movies (fn [values]
    ;                (update-movie-handler values)
    ;                ))
    (swap! movies update-movie-handler)


    {:status 200
     :body   {
              :result     "Succesfully Updated Movie with partial"
              :request    (get request :body-params)
              :all-movies @movies
              }
     }

    )

  )
(defn delete-movie-by-id [request]

  (let [
        id (Integer/parseInt (get-in request [:path-params :id]))
        ]



    (swap! movies (fn [values]
                    (filter #(not= id (get % :id)) values)
                    ))



    {:status 200
     :body   {
              :result     "Succesfully Deleted Movie "
              :request    (get request :body-params)
              :all-movies @movies
              }
     }

    )

  )




(def app
  (middleware/wrap-format
    (ring/ring-handler
      (ring/router
        ["/api"

         ["/movies" {
                     :get {

                           :handler all-movies
                           }


                     :post insert-movie

                     }]

         ["/movies/:id" {
                         :get    movie-by-id
                         :put    update-movie-by-id
                         :delete delete-movie-by-id

                         }]]

        )

      )))
;
;(def app
;  (munt-middleware/wrap-format
;    (ring/ring-handler
;      (ring/router
;        ["/api" [
;
;                 ["/movies" {
;                             :get  all-movies
;                             :post insert-movie
;
;                             }]
;
;                 ["/movies/:id" {
;                                 :get movie-by-id
;                                 :put update-movie-by-id
;                                 :delete delete-movie-by-id
;
;                                 }]
;
;                 ]]
;
;        ))))



(comment



  (update-movie-for @movies 1 {:title "Changed Name" :year "2000"})

  (def id 1)
  (first (filter #(= 104 (get % :id)) @movies))
  (first (filter #(= "1" (get % :id)) @movies))

  (app {:request-method :put
        :uri            "/api/movies/1"
        :body-params    {
                         :title "Changed Name newest new"
                         :year  "2000"
                         }}
       )

  (app {:request-method :get
        :uri            "/api/movies/1"
        })



  (app {:request-method :delete
        :uri            "/api/movies/1"
        }
       )

  (app {:request-method :get
        :uri            "/api/movies/1"
        })



  (app {:request-method :get
        :uri            "/api/movies/233"
        })

  (app {:request-method :get
        :uri            "/api/movies"
        })


  (app {:request-method :post
        :uri            "/api/movies"
        :body-params    {

                         :plot
                         "Four denizens in the world of high-finance predict the credit and housing bubble collapse of the mid-2000s, and decide to take on the big banks for their greed and lack of foresight.",
                         :director "Adam McKay",
                         :genres   ["Biography" "Comedy" "Drama"],
                         :title    "The Big Short",
                         :year     "2015",
                         :actors   "Ryan Gosling, Rudy Eisenzopf, Casey Groves, Charlie Talbert",
                         :id       146,
                         :runtime  "130",
                         :posterUrl
                         "https://images-na.ssl-images-amazon.com/images/M/MV5BNDc4MThhN2EtZjMzNC00ZDJmLThiZTgtNThlY2UxZWMzNjdkXkEyXkFqcGdeQXVyNDk3NzU2MTQ@._V1_SX300.jpg"}

        })



  (app {:request-method :get
        :uri            "/hello"
        })


  (app {:request-method :post
        :uri            "/insertdata"
        })

  )


;
;(defroutes app-routes
;           (GET "/hello" [] "Hello Clojure ")
;           ;(GET "/hello/:name" [name] (str "Hello " name) )
;           (GET "/hello/:name" [name]
;             {:status  200
;              :body     (str "Hello with status " name)
;              }
;
;             )
;           (POST "/insertdata" request
;
;             (insert-data request)
;
;
;             )
;
;           (route/not-found "Not Found")
;           )


;wrap-defaults is a middleware that wraps the default middleware with routes and basic server side parameters
;site-defaults is a middleware that sets the default site parameters
;api-defaults
;(def oldapp
;  (wrap-defaults app-routes api-defaults))
;
;
;(def app  (->
;               app-routes
;               (middleware/wrap-json-body {:keywords? true})
;               (middleware/wrap-json-response)
;               (wrap-defaults api-defaults)
;               )
;  )

;Use
;lein ring server-headless  to reload and run the server


(defn -main
  [& args]
  ;(run-server app {:port 3000})
  (run-server app {:port         3000
                   :event-logger (fn [& args]
                                   (println args)
                                   )
                   }

              )
  ;Lein run will not show any log http-kit special
  )