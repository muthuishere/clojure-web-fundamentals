(ns hello-web-4.core (:require

                       [muuntaja.middleware :as middleware]
                       [org.httpkit.server :refer [run-server]]
                       [reitit.ring :as ring]
                       [hello-web-4.movies :refer :all]

                       ))

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


(defn -main
  [& args]

  (run-server app {:port         3000
                   :event-logger (fn [& args]
                                   (println args)
                                   )
                   }

              )

  )