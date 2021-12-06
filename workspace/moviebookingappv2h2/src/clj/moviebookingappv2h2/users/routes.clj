(ns moviebookingappv2h2.users.routes (:require     [schema.core :as s]
                                                   [clojure.spec.alpha :as spec]
                                                   [spec-tools.core :as spec-core]
                                                   [moviebookingappv2h2.users.handler :refer :all]
                                                   [moviebookingappv2h2.users.jwt-util :refer :all]
                                                   [ring.util.http-response :refer :all]
                                                   )
  )


(defn create-with-default [default]
  (spec-core/spec
    {:spec                string?
     :swagger/default     default
     }
    )
  )

;(id VARCHAR(20) PRIMARY KEY,
; first_name VARCHAR(30),
; last_name VARCHAR(30),
; email VARCHAR(30),
; admin BOOLEAN,
; last_login TIMESTAMP,
; is_active BOOLEAN,
; pass VARCHAR(300));

;
;(def user-data {
;                 :id    (create-with-default "peterparker") ,
;                 :first_name    (create-with-default "Peter") ,
;                 :last_name    (create-with-default "Parker") ,
;                 :email    (create-with-default "peterparker@gmail.com") ,
;                 :pass  (create-with-default  "password")
;
;
;
;                 })




(s/defschema User  {
                       :id    (create-with-default "peterparker") ,
                       :first_name    (create-with-default "Peter") ,
                       :last_name    (create-with-default "Parker") ,
                       :email    (create-with-default "peterparker@gmail.com") ,
                       :pass  (create-with-default  "password")



                       })

(s/defschema LoginRequest  {
                       :email    (create-with-default "user@gmail.com") ,
                       :pass  (create-with-default  "password")



                       })

(defn routes []

  [""  {:swagger {:tags ["users"]}}

   ["/register" {


               :post    {
                         :handler register
                         :parameters {
                                      :body User
                                      }
                         }

               }]
   ["/login" {


               :post    {
                         :handler login
                         :parameters {
                                      :body LoginRequest
                                      }
                         }

               }]
   ["/register-admin" {


               :post    {
                         :middleware [wrap-jwt-authentication restrict-authenticated]
                         :handler register-admin
                         :parameters {
                                      :body User
                                      }
                         }

               }]


   ["/auth-ping" {


                       :get    {
                                :middleware [wrap-jwt-authentication restrict-authenticated]
                                 :handler (constantly (ok {:message "auth pong"}))

                                 }

                       }]
   ["/auth-admin-ping" {


                       :get    {
                                :middleware [wrap-jwt-authentication restrict-admin]
                                 :handler (constantly (ok {:message "auth admin pong"}))

                                 }

                       }]

   ]
  )


