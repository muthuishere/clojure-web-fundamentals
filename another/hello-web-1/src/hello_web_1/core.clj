(ns hello-web-1.core
  (:require
    [ring.middleware.defaults :refer [wrap-defaults  api-defaults]]
    [compojure.core :refer :all]
    [ring.middleware.params :refer [wrap-params]]
    [ring.middleware.json :as middleware]
    [ring.middleware.keyword-params :refer [wrap-keyword-params]]
    [compojure.route :as route]
    [ring.adapter.jetty :refer :all]
    )
  )

;Part 1 Insert Data
;
;(defn insert-data [request]
;(println request)
;  "post data"
;  )

;Part 4 CHange reponse from name
;(defn insert-data [request]
;(println request)
;  (str "Halo " (get-in request [:body :name]))
;
;
;  )

;Part 5 return response as json
(defn insert-data [request]
(println request)

  {:status  200
   :body    {:result (str "Halo " (get-in request [:body :name]))}
   }




  )

(defroutes app-routes


           (GET "/hi" request (fn [request]
                                   (println request)
                                   "hi"
                                   ) )

           (GET "/hello" [] "Hello Clojure Web jkj ")
           (GET "/hello/:name" [name] (str "Hello Clojure " name)
             )
           (POST "/insertdata" request (insert-data request))
           (route/not-found "Not Found")
           )


;(macroexpand '(->
;               app-routes
;               (middleware/wrap-json-body {:keywords? true})
;               (middleware/wrap-json-response)
;               (wrap-defaults api-defaults)
;               ))


;Part 2
; include  [ring.middleware.json :as middleware]
(def app




  ;
  ; (wrap-defaults app-routes api-defaults)

  ;part 3
  ;(wrap-defaults (middleware/wrap-json-body app-routes {:keywords? true}) api-defaults)


  ;part 6
  ;(wrap-defaults (middleware/wrap-json-response (middleware/wrap-json-body app-routes {:keywords? true})) api-defaults)

  ;part 7
  (->   app-routes
        (middleware/wrap-json-body {:keywords? true})
        (middleware/wrap-json-response)
        (wrap-defaults api-defaults)
        )
  )

;part 3
;  [ring.adapter.jetty :refer :all]
(defn -main
  [& args]
  (run-jetty app {:port 3000})

  )


