(ns moviebookingappv3auth.handler
  (:require
    [moviebookingappv3auth.middleware :as middleware]
    [moviebookingappv3auth.routes :refer [service-routes]]
    [reitit.swagger-ui :as swagger-ui]
    [reitit.ring :as ring]
    [ring.middleware.content-type :refer [wrap-content-type]]
    [ring.middleware.webjars :refer [wrap-webjars]]
    [moviebookingappv3auth.env :refer [defaults]]
    [mount.core :as mount]))

(mount/defstate init-app
  :start ((or (:init defaults) (fn [])))
  :stop  ((or (:stop defaults) (fn []))))

(mount/defstate app-routes
  :start
  (ring/ring-handler
    (ring/router
      [["/" {:get
             {:handler (constantly {:status 301 :headers {"Location" "/api/api-docs/index.html"}}) }}]
       (service-routes)])
    (ring/routes
      (ring/create-resource-handler
        {:path "/"})
      (wrap-content-type (wrap-webjars (constantly nil)))
      (ring/create-default-handler))))

(defn app []
  (middleware/wrap-base #'app-routes))



(comment

  ;(def all-routes ())
   ((app)
              {:request-method :get
               :uri            "/api/movies"
               }

               )


  ((app)
   {:request-method :post
    :uri            "/api/login"
    :body-params
                  {
                   :email "user@gmail.com"
                   :pass "password"

                   }

    }

   )

  (def auth-token "Bearer eyJhbGciOiJIUzI1NiJ9.eyJpZCI6IjEiLCJmaXJzdF9uYW1lIjoidXNlciIsImxhc3RfbmFtZSI6Imxhc3RuYW1lIiwiZW1haWwiOiJ1c2VyQGdtYWlsLmNvbSIsImFkbWluIjpmYWxzZSwiZXhwIjoiMTYzODc3NTAyMSJ9.FmBU3xTUQYOg6XaAu9D_pzxVuXTqh4bh74p5zXqvBzc")



  ((app)
   {:request-method :get
    :uri            "/api/auth-ping"
    ;:headers [
    ;          :Authorization auth-token
    ;          ]

    }

   )


  )