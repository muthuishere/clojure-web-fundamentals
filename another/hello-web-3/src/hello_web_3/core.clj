(ns hello-web-3.core
  (:require
                      [ring.middleware.defaults :refer [wrap-defaults  api-defaults]]
                       [ring.middleware.json :as middleware]
                      [org.httpkit.server :refer [run-server]]
                       [compojure.api.sweet :as sw]
                      [hello-web-3.movies :refer :all]

                      ))


(def app
  (sw/api

    (sw/context "/api" []

                (sw/GET "/movies" request (all-movies request) )


                (sw/POST "/movies" request (insert-movie request) )

                (sw/GET "/movies/:id" request (movie-by-id request) )

                (sw/PUT "/movies/:id" request (update-movie-by-id request) )

                (sw/DELETE "/movies/:id" request (delete-movie-by-id request) )


                )



    ))

(comment


  (app {:request-method :get
        :uri "/api/movies"
        })

  )

(defn -main
  [& args]
  (run-server app {:port 3000})

  )
