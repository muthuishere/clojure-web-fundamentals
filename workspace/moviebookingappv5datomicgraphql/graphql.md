
### Graphql
; Create a template from luminus
; with service swagger graphql

; and copy and paste the data here


; lein 
inproject.clj

```clojure

;Graphql
[com.walmartlabs/lacinia "0.32.0"]



```

;Insert layout for custom rendering
src/clj/moviebookingappv5datomicgraphql/layout.clj

```clojure


;in handler.clj

;import

[moviebookingappv5datomicgraphql.layout :as layout]


;under app-routes add

["/graphiql" {:get (fn [request]
                     (layout/render request "graphiql.html"))}]


; add
;resources/graphql/schema.edn
;update schema for movies crud
```

Add a template for graphql.core

```clojure

(:require
  [com.walmartlabs.lacinia.util :refer [attach-resolvers]]
  [com.walmartlabs.lacinia.schema :as schema]
  [com.walmartlabs.lacinia :as lacinia]
  [clojure.data.json :as json]
  [clojure.edn :as edn]
  [clojure.java.io :as io]
  [ring.util.http-response :refer :all]
  [mount.core :refer [defstate]])


(defstate compiled-schema
          :start
          (-> "graphql/schema.edn"
              io/resource
              slurp
              edn/read-string
              (attach-resolvers {:get-hero get-hero
                                 :get-droid (constantly {})})
              schema/compile)
          )


(defn execute-request [query]
  (let [{:keys [query variables operationName]} (json/read-str query :key-fn keyword)]
    (-> (lacinia/execute compiled-schema query variables nil)
        (json/write-str))))



```

Update file m.routes

```clojure

   ["/graphql" {:no-doc true
                :post (fn [req]
                        (ok (graphql/execute-request (-> req :body slurp))))}
    ]

```

