(ns hello-web.core(:require [compojure.core :refer :all]
                            [compojure.route :as route]
                            [ring.middleware.json :as middleware]
                            [ring.middleware.defaults :refer [wrap-defaults site-defaults api-defaults]])

  )




(defn insert-data [request]

  (println request)
  {:status  200
   :body    {
             :result (str "Succesfully Inserted" ((request :body) :name) )
             }
   }

  )

(defroutes app-routes
           (GET "/hello" [] "Hello Clojure ")
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


(def app  (->
            app-routes
            (middleware/wrap-json-body {:keywords? true})
            (middleware/wrap-json-response)
            (wrap-defaults api-defaults)
            )
  )

;Use
;lein ring server-headless  to reload and run the server