(ns hello-web-httpkit.core(:require
                    [compojure.core :refer :all]
                    ;[ring.adapter.jetty :refer :all]
                            [compojure.route :as route]
                            [ring.middleware.json :as middleware]
                            [ring.middleware.defaults :refer [wrap-defaults site-defaults api-defaults]]
                           
                           )
  (:use [org.httpkit.server :only [run-server]])

  )




(defn insert-data [request]

  (println request)
  {:status  200
   :body    {
             :result (str "Succesfully Inserted" ((request :body) :name) )
             }
   }

  )

;(defn check-ip-handler [request]
;  (ring.util.response/content-type
;    (ring.util.response/response (:remote-addr request))
;    "text/plain"))

(defroutes app-routes
           (GET "/hello" [] "Hello Clojure fff ")
           ;(GET "/hello/:name" [name] (str "Hello " name) )
           (GET "/hello/:name" [name]
                {:status  200
                 :body     (str "Hello with status " name)
                 }

                )
           (POST "/insertdata" request

                 (insert-data request)


                 )

           (route/not-found "Not Found")
           )


;wrap-defaults is a middleware that wraps the default middleware with routes and basic server side parameters
;site-defaults is a middleware that sets the default site parameters
;api-defaults
(def oldapp
  (wrap-defaults app-routes api-defaults))

;3.4. Middleware
;Middleware is a name that's common in some languages but less so in the Java world. Conceptually they are similar to Servlet Filters and Spring Interceptors.
;
;In Ring, middleware refers to simple functions that wrap the main handler and adjusts some aspects of it in some way. This could mean mutating the incoming request before it's processed, mutating the outgoing response after it's generated or potentially doing nothing more than logging how long it took to process.
;
;In general, middleware functions take a first parameter of the handler to wrap and returns a new handler function with the new functionality.
;
;The middleware can use as many other parameters as needed. For example, we could use the following to set the Content-Type header on every response from the wrapped handler:


(def app  (->
            app-routes
            (middleware/wrap-json-body {:keywords? true})
            (middleware/wrap-json-response)
            (wrap-defaults api-defaults)
            )
  )

;
;(defn -mainold
;  [& args]
;  (run-jetty app {:port 3000})
;
;  )


(defn -main
  [& args]
  (run-server app {:port 3000})

  )

;Use
;lein ring server-headless  to reload and run the server