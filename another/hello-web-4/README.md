### Dependencies
[org.clojure/clojure "1.10.1"]
[ring/ring-defaults "0.3.2"]
[metosin/reitit "0.5.15"]
[metosin/ring-http-response "0.9.3"]
[ring/ring-json "0.4.0"]
[http-kit "2.3.0"]



### Plugins

:ring {
:handler hello-web-3.core/app
}
:plugins [[lein-ring "0.12.5"]]


#### Include for core.clj
[muuntaja.middleware :as middleware]
[org.httpkit.server :refer [run-server]]
[reitit.ring :as ring]
[hello-web-3.movies :refer :all]


##### Include routes

```clojure


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

```


Update main
```clojure


(defn -main
[& args]

(run-server app {:port         3000
:event-logger (fn [& args]
(println args)
)
}

              )

)



```

lein ring server-headless


The Url parameters always be available on :path-params

