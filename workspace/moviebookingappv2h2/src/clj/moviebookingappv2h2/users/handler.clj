(ns moviebookingappv2h2.users.handler
  (:require
    [moviebookingappv2h2.db.core :as db]
    [moviebookingappv2h2.users.user-service :as user-service]
    [moviebookingappv2h2.users.jwt-util :as jwt]
    [ring.util.response :refer :all]
    )
  )

(comment

  (db/get-user-by-email-and-pass {
                                  :email "user@gmail.com"
                                  :pass "password"

                                  })

  (db/get-user-by-email-and-pass {
                                  :email "usedsdsr@gmail.com"
                                  :pass "password"

                                  })
  (login  {

           :body-params {
                         :email "user@gmail.com"
                         :pass "password"

                         }
           } )

  (login  {

           :body-params {
                         :email "userdsdsx@gmail.com"
                         :pass "password"

                         }
           } )
  )
(defn login [request]

  ;get-user-by-email-and-pass
  ;email = :email and pass =:pass

  (let [

        data (get request :body-params)
        result (db/get-user-by-email-and-pass data
                                        )

        ]




    (if (nil? result)
      {:status 401
       :body   "Invalid User Credentials"
       }
      (response  {
               :result    "Successful"
               :token
                           (jwt/create-token!  result)

               }  )

      )



    )

  )

(comment

  ;:id, :first_name, :last_name, :email

  (db/create-user! {
                    :id "userx"
                    :first_name "user"
                    :last_name "x"
                    :email "userx@gmail.com"
                    :pass "password"
                    })

  (register {

             :body-params  {
                            :id "userx"
                            :first_name "user"
                            :last_name "x"
                            :email "userx@gmail.com"
                            :pass "password"
                            }
             })
  (register {

             :body-params  {
                            :id "userx1"
                            :first_name "user1"
                            :last_name "x1"
                            :email "userx1@gmail.com"
                            :pass "password"
                            }
             })

  )



(defn register [{:keys [body-params]}]

  (let [

        {:keys [result error]} (user-service/create-user! body-params )

        ]




    (if (nil? result)
      {:status 503
       :body   error
       }
      (response  {
                  :result   (str "Succesfully created user")

                  }
                 )

      )

    )
  )

(comment



  (register-admin {

             :body-params  {
                            :id "usery"
                            :first_name "user"
                            :last_name "x"
                            :email "usery@gmail.com"
                            :pass "password"
                            }
             })


  (register-admin {

             :body-params  {
                            :id "usery1"
                            :first_name "user1"
                            :last_name "x1"
                            :email "usery1@gmail.com"
                            :pass "password"
                            }
             })

  )
(defn register-admin [{:keys [body-params]}]
  (let [

        {:keys [result error]} (user-service/create-admin-user! body-params )

        ]




    (if (nil? result)
      {:status 503
       :body   error
       }
      (response  {
                  :result   (str "Succesfully created admin user")

                  }
                 )

      )

    )
  )
