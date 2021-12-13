(ns hello-web-1.oldcore
  (:require
    [ring.middleware.defaults :refer [wrap-defaults  api-defaults]]
    [compojure.core :refer :all]
    [compojure.route :as route]
    [ring.adapter.jetty :refer :all]
    )
  )

;Part 1
;[compojure.core :refer :all]
;(defroutes app-routes
;
;           (GET "/hello" [] "Hello Clojure Web")
;           )


;;Part 4
;;for not found error handler
;;   [compojure.route :as route]
;(defroutes app-routes
;
;           (GET "/hello" [] "Hello Clojure Web jkj ")
;           (route/not-found "Not Found")
;           )

(defn insert-data [request]
(println request)
  "post data"
  )
;Part 7
;For Getting Parameters from URL
(defroutes app-routes


           ;(GET "/hello" request (fn [request]
           ;                        "Hello Clojure Web"
           ;                        ) )

           (GET "/hello" [] "Hello Clojure Web jkj ")
           (GET "/hello/:name" [name] (str "Hello Clojure " name)
             )
           (POST "/insertdata" request (insert-data request))
           (route/not-found "Not Found")
           )

;Part 2
; [ring.middleware.defaults :refer [wrap-defaults  api-defaults]
(def app
  (wrap-defaults app-routes api-defaults))

;part 3
;  [ring.adapter.jetty :refer :all]
(defn -main
  [& args]
  (run-jetty app {:port 3000})

  )


