### Install Postman


### Dependencies

[ring/ring-core "1.7.1"]
[ring/ring-jetty-adapter "1.7.1"]
[ring/ring-defaults "0.3.2"]
[compojure "1.6.1"]
[ring/ring-json "0.4.0"]


;Part 1
;include [compojure.core :refer :all]
;(defroutes app-routes
;

           (GET "/hello" request (fn [request]                                   
                                   "Hello Clojure"
                                   ))

; do it later or specify there
;           (GET "/hello" [] "Hello Clojure Web")
;           )


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


Open browser , favicon issue will be shown

http://localhost:3000/hello


now Add route not found

;Part 4
;for not found error handler
;   [compojure.route :as route]
(defroutes app-routes

           (GET "/hello" [] "Hello Clojure Web jkj ")
           (route/not-found "Not Found")
           )



To make it run with lein run

;Part 5 for lein run
;Main Namespace after first thing done
:main hello-web-1.core

For Live Reload

;part 6 for live reload
;use now lein ring server-headless
:ring {:handler hello-web-1.core/app}
:plugins [[lein-ring "0.12.5"]]




;Part 7
;For Getting Parameters from URL
Update App Routes
; (GET "/hello/:name" [name] (str "Hello Clojure " name)
;             )




;Part 8 Read Post Request
(POST "/insertdata" request (insert-data request))

(defn insert-data [request]
(println request)
"post data"
)

In post man http://localhost:3000/insertdata

{
"name":"Peter Parker"
}

; The request will not have body-params rather it will show binary data
        :body #object[org.eclipe.jetty.server.HttpInputOverHTTP 0x302bb843 HttpInputOverHTTP@302bb843[c=0,q=0,[0]=null,s=STREAM]]


To Read JSOn body data

add wrapper to app-routes

include  [ring.middleware.json :as middleware]


(wrap-defaults app-routes api-defaults)  to

                (wrap-defaults (middleware/wrap-json-body app-routes) api-defaults)

; now request will have body converted to data than stream, But keywords are not map like
                (wrap-defaults (middleware/wrap-json-body app-routes {:keywords? true}) api-defaults)


=============

Now change insert data

(defn insert-data [request]
(println request)
(str "Halo " (get-in request [:body :name]))
)

 Try passing JSON

;Part 5 return response as json by wrapping with status and body
(defn insert-data [request]
(println request)


{:status  200
:body    {:result (str "Halo " (get-in request [:body :name]))

}


}


)

; The postman will not display any data, now wrap response as well

Use middleware/wrap-json-response
(wrap-defaults (middleware/wrap-json-response (middleware/wrap-json-body app-routes {:keywords? true})) api-defaults)



; Now refactor items
 First parameter for wrap-defaults (middleware/wrap-json-response (middleware/wrap-json-body app-routes {:keywords? true}))
 first parameter middleware/wrap-json-response (middleware/wrap-json-body app-routes {:keywords? true})
first parameter middleware/wrap-json-body app-routes
;find innermost
approutes
(->   app-routes
        (middleware/wrap-json-body {:keywords? true})
        (middleware/wrap-json-response)
        (wrap-defaults api-defaults)
)











