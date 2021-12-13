(ns hello-web-compojure-sweet.core
(:require

  [muuntaja.core :as m]
  [muuntaja.middleware :as middleware]
  [ring.middleware.defaults :refer [wrap-defaults site-defaults api-defaults]]
  [ring.util.http-response :as http-response]
  )

)



(defn insert-data [request]

      (println request)
      (println (keys request) )
      {:status  200
       :body    {
                 :result "Succesfully Inserted"
                 :request (get request :body-params)
                 }
       }

      )
(defn update-data [request]

      (println request)
      (println (keys request) )
      {:status  200
       :body    {
                 :result (str "Succesfully Updated" (get-in request [:path-params :id ]))
                 :request (get request :body-params)
                 }
       }

      )
(defn update-data [request]

      (println request)
      (println (keys request) )
      {:status  200
       :body    {
                 :result (str "Succesfully Updated" (get-in request [:path-params :id ]))
                 :request (get request :body-params)
                 }
       }

      )


(defn delete-data [request]

      (println request)
      (println (keys request) )
      {:status  200
       :body    {
                 :result (str "Succesfully Deleted" (get-in request [:path-params :id ]))

                 }
       }
      )

(defn insert-data-string [request]

      (println request)
      {:status  200

       :body    (str "Succesfully Inserted"  )
       }

      )

;Coercion is a process of transforming parameters (and responses) from one format into another. Reitit separates routing and coercion into two separate steps.
;
;By default, all wildcard and catch-all parameters are parsed into strings:


; The First parameter should be  a vector of all routes
; The Second parameter should be the coercisions




(def app
  (middleware/wrap-format
    (ring/ring-handler
      (ring/router
        [

         ["/hello" {:get  (fn [obj]
                              (println obj)
                              { :status 200
                               :headers {}
                               :body "Hello Clojure newes"}
                              )
                    }]


         ;["/hello" {:get  (constantly
         ;                   { :status 200
         ;                    :headers {}
         ;                    :body "Hello Clojure newes"}
         ;                   )
         ;           }]





         ["/hello/:name" {:get
                          (fn [request]
                              (println request)
                              {:status 200
                               :headers {}
                               :body  (str "Hello Clojure " (get-in request [:path-params :name]))
                               }
                              )

                          }]

         ["/insertdata" {:post
                         (fn [request]
                             (insert-data request)
                             )

                         }]

         ["/updatedata/:id" {:put
                             (fn [request]
                                 (update-data request)
                                 )

                             }]

         ["/deletedata/:id" {:delete
                             (fn [request]
                                 (delete-data request)
                                 )

                             }]


         ]




        ))))



(comment






  (app {:request-method :get
        :uri "/hello/mrx"
        })



  (app {:request-method :get
        :uri "/hello"
        })


  (app {:request-method :post
        :uri "/insertdata"
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