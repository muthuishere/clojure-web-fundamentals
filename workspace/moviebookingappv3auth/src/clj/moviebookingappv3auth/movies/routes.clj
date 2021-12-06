(ns moviebookingappv3auth.movies.routes
  (:require     [schema.core :as s]
                [clojure.spec.alpha :as spec]
                [spec-tools.core :as spec-core]
                [moviebookingappv3auth.movies.handler :refer :all]

                )
  )


(defn create-with-default [default]
  (spec-core/spec
    {:spec                string?
     :swagger/default     default
     }
    )
  )

(def movie-data {

                 :plot
                 (spec-core/spec
                   {:spec                string?
                    :swagger/default     "Four denizens in the world of high-finance predict the credit and housing bubble collapse of the mid-2000s, and decide to take on the big banks for their greed and lack of foresight."
                    }
                   )
                 :director (spec-core/spec
                             {:spec                string?
                              :swagger/default     "Martin scorcose"
                              }
                             ),
                 :genres   (spec-core/spec
                             {:spec               (spec/coll-of string?)
                              :swagger/default     ["Biography" "Comedy" "Drama"]
                              }
                             )

                 :title    (create-with-default "The Big Short") ,
                 :year    (create-with-default "2015") ,
                 :runtime    (create-with-default "130") ,
                 :posterUrl  (create-with-default  "https://images-na.ssl-images-amazon.com/images/M/MV5BNDc4MThhN2EtZjMzNC00ZDJmLThiZTgtNThlY2UxZWMzNjdkXkEyXkFqcGdeQXVyNDk3NzU2MTQ@._V1_SX300.jpg") ,
                 :actors    (create-with-default "Ryan Gosling, Rudy Eisenzopf, Casey Groves, Charlie Talbert")


                 })


(s/defschema UpdateMovieRequest  movie-data)



(s/defschema Movie  (merge movie-data {:id pos-int?})
             )
(defn routes []

  [""  {:swagger {:tags ["movies"]}}

   ["/movies" {

               :get   {

                       :handler all-movies

                       :summary    "List All Movies"



                       }
               :post    {
                         :handler insert-movie
                         :parameters {
                                      :body Movie
                                      }
                         }

               }]

   ["/movies/:id" {

                   :get {
                         :handler movie-by-id
                         :parameters {
                                      :path {:id int?}

                                      }
                         }




                   :put   {
                           :handler update-movie-by-id
                           :parameters {
                                        :path {:id int?}
                                        :body UpdateMovieRequest
                                        }
                           }
                   :delete  {
                             :handler delete-movie-by-id
                             :parameters {
                                          :path {:id int?}

                                          }
                             }


                   }]

   ]
  )