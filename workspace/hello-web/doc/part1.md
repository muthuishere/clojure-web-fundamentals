### Setting Up of Hello Web
#### Start With project.clj

###### Add dependencies

Ring is an Http Server built on Clojure

[ring/ring-defaults "0.3.2"]

Compojure is a routing library for Clojure
[compojure "1.6.1"]


###### Add Plugins
:plugins [[lein-ring "0.12.5"]]


### Update in Main Namespace core.clj

define the routes

```Clojure
(defroutes app-routes
(GET "/hello" [] "Hello Clojure ")
)
```

Create a variable called app and set invoke with 

```clojure


;wrap-defaults is a middleware that wraps the default middleware with routes and basic server side parameters
;api-defaults is a middleware that sets the default site parameters


(def app
(wrap-defaults app-routes api-defaults))
```

Import Library

```clojure

  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [ring.middleware.defaults :refer [wrap-defaults api-defaults]])

```



#### Update project.clj
###### Add Server Handler
:ring {:handler #YOUR_NAMESPACE#/#APP_VARIABLE#}

In Our case it might me
```clojure
:ring {:handler movie-booking-app.core/app}
```


### Run Application

```clojure


lein ring server-headless

```

    Open the browser http://localhost:3000/  You should see Hello Movie Booking

Also the log might show the  following

```text
WARN:oejs.HttpChannel:qtp2039613101-18: /favicon.ico
java.lang.NullPointerException: Response map is nil

```

The Browser always send a request for the favicon.ico file.

to show an icon on tab

Two ways to stop issue

RUn it from PostMan
(or)

Now create a route for not found

```clojure
(route/not-found "Not Found")

```


Run it from PostMan, you should not see the favicon.ico error


**How to access parameters**

create a new route 

```clojure
 (GET "/hello/:name" [name] (str "Hello " name) )


;http://localhost:3000/hello/mrx  =>  Hello mrx


; You can also send response directly like
{:status  200
 :body     (str "Hello with status " name)
 }

;http://localhost:3000/hello/mrx  =>  Hello with status  mrx
```


*How to make POST request *


Create a route
```clojure

;see no brackets , just plain request as second parameter
 (POST "/insertdata" request

             (insert-data request)


             )


;What is the request is all about
(defn insert-data [request]

  (println request)
  {:status  200
   :body    "Received"
   }

  )

```

Create a Postman request
```clojure

;{
;  "name": "POST",
;  "url": "http://localhost:3000/insertdata",
;  "data": {

;"name":"Peter Parker"

;  }
;}
;POST with postman
;http://localhost:3000/insertdata

;The log might show
;{:ssl-client-cert nil, :protocol HTTP/1.1,
; :remote-addr 0:0:0:0:0:0:0:1, :params {},
; :route-params {}, 
; :headers {host localhost:3000, 
; user-agent PostmanRuntime/7.28.4,
; content-type application/json,
; cookie
; ring-session=1a4e1f9e-5c8d-402d-a98b-8f2bea976db9, 
; content-length 31, connection keep-alive,
; accept */*, accept-encoding gzip, deflate, br,
; postman-token b4ecb109-434f-4169-a802-f6e331c9cac8,
; cache-control no-cache}, :server-port 3000, 
; :content-length 31, :form-params {},
; :compojure/route [:post /insertdata],
; :query-params {},
; :content-type application/json,
; :character-encoding UTF-8, 
; :uri /insertdata, :server-name localhost,
; :query-string nil, 
; :body #object[org.eclipse.jetty.server.HttpInputOverHTTP 0x336cf4c4 HttpInputOverHTTP@336cf4c4], :scheme :http, :request-method :post}


;=============

;

```
The body is something object, we need that binary object to be converted into map , so use library

Add the below as dependency
[ring/ring-json "0.4.0"]

which will help us to handle json

Now update the routes to handle json

```clojure

;import
[ring.middleware.json :as middleware]



(def app  (->
               app-routes
               (middleware/wrap-json-body {:keywords? true})
               (middleware/wrap-json-response)
               (wrap-defaults api-defaults)
               )
  )


```

Restart the application and now fire the same request

```clojure


;{:ssl-client-cert nil, :protocol HTTP/1.1, :remote-addr 0:0:0:0:0:0:0:1, :params {}, :route-params {}, :headers {host localhost:3000, user-agent PostmanRuntime/7.28.4, content-type application/json, cookie ring-session=1a4e1f9e-5c8d-402d-a98b-8f2bea976db9, content-length 31, connection keep-alive, accept */*, accept-encoding gzip, deflate, br, postman-token 6a144d71-88da-4cb1-864b-6d4ff8f21c38, cache-control no-cache}, :server-port 3000, :content-length 31, :form-params {}, :compojure/route [:post /insertdata], :query-params {}, :content-type application/json, :character-encoding UTF-8, :uri /insertdata, :server-name localhost, :query-string nil,
; :body {:name Peter Parker}, :scheme :http, :request-method :post}


;Now the body is a map 
;To Send the response as json

;' Change insert-data to
;
 {:status  200
:body    {
          :result (str "Succesfully Inserted" ((request :body) :name) )
          }
}

```

    Maps in Clojure are similar to JSON objects. create an endpoint to retreive movies

    ```clojure


    ```

