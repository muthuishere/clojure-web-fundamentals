(ns hello-web-reitit.core
(:require [reitit.core :as r]
  [reitit.ring :as ring]
  [reitit.ring.coercion :as rrc]
  [reitit.coercion.spec :as rcs]
  [muuntaja.core :as m]
  [muuntaja.middleware :as munt-middleware]
  [reitit.ring.middleware.muuntaja :as muuntaja]
  [ring.middleware.defaults :refer [wrap-defaults site-defaults api-defaults]]
  [ring.util.http-response :as http-response]
  )

)
; I - 5 Hours
;REad All
;REad By ID
;Insert New
;Update By ID
;Delete By ID
;Swagger
;Upload File

;  II -  5 Hours
; Now Use Luminus to Create an Application
;DataBase Movies
;Read All
;Read By ID
;Insert New
;Update By ID
;Delete By ID

;========== ClojureScript ==========
;HTML
; Shadow CLjs Reagent
; What is React
; Sample React Application

;What is COmponent
;hiccup language
; How to nest Components
;What is routing
;How to add links
;what is state
;How to Connect Rest API
;How to Show Loading Bar
;Routes with components
;List Movies Components
;Movie Detail Component
;Add Movie Component
;Update Movie Component
;Delete Movie Component
;===================



;III -  5 Hours
;Exercise
;showtimes
; list/add/update/delete showtimes

;Clojure Script
;Select Movie
;List Date and Show Times
; Select Showtime
;Click Book , Booked Succesfully


;IV 5 Hours
;Exercise
;register
;------
;JSON Token Generation & Validation
;login
;JSON Token Validation
;Authentication & Authorization
;  Add Movie , Update Movie , Delete Movie
;  Add Showtime , Update Showtime , Delete Showtime

;Update Swagger with Authenticating Routes
;Test it with Postman

; Clojure Script
;Forms
;Register Component
;Login Component
;Saving User Configuration in global state
; Authenticating ROutes
;Change menus to invisible , if the user logged in
;     is admin for add/edit/delete showtime
;     is admin for add/edit/delete movie

;Make the Book Button Visible Only if user is logged in


;V 5 hours



;== Clojure
;Ticket in Datomic
;Ticket in Datomic
; GraphQL Book Ticket
; Mutation
; List Tickets Query
; Mutation Book Ticket
; Query List Tickets

; Clojure Script
; On click Book Button
; Call Book Tickets Mutation
; On Tickets Menu => List down all the booked tickets

;Datomic
;What is Datomic
;Install Datomic
;Create a Schema (Actor and Movies,  Bookings)

;GraphQL
;Queries
;Mutation


;Graphql Client


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
  (munt-middleware/wrap-format
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