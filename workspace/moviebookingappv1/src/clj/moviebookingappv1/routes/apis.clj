(ns moviebookingappv1.routes.apis
  (:require
    [reitit.swagger :as swagger]
    [clojure.java.io :as io]
    [reitit.swagger-ui :as swagger-ui]
    [reitit.ring.coercion :as coercion]
    [reitit.coercion.spec :as spec-coercion]
    [reitit.ring.middleware.muuntaja :as muuntaja]
    [reitit.ring.middleware.multipart :as multipart]
    [reitit.ring.middleware.parameters :as parameters]
    [moviebookingappv1.middleware.formats :as formats]
    [ring.util.http-response :refer :all]
    [moviebookingappv1.services.movies :refer :all]
    [clojure.java.io :as io]
    [schema.core :as s]
    [clojure.spec.alpha :as spec]
    [spec-tools.core :as spec-core]
    ))





;
;(clojure.spec.alpha/def ::results
;  (spec-tools.core/spec
;    {:spec (clojure.spec.alpha/and int? #(< 0 % 100))
;     :description "between 1-100"
;     :swagger/default 10
;     :reason "invalid number"}))
;  [clojure.spec.alpha :as spec]
;    [spec-tools.core :as spec-core]

(defn create-with-default [default]
  (spec-core/spec
    {:spec                string?
     :swagger/default     default
     }
    )
  )

(def movie-data {

                 :plot
                 (spec-core/spec
                   {:spec                string?
                    :swagger/default     "Four denizens in the world of high-finance predict the credit and housing bubble collapse of the mid-2000s, and decide to take on the big banks for their greed and lack of foresight."
                    }
                   )
                 :director (spec-core/spec
                             {:spec                string?
                               :swagger/default     "Martin scorcose"
                              }
                             ),
                 :genres   (spec-core/spec
                             {:spec               (spec/coll-of string?)
                              :swagger/default     ["Biography" "Comedy" "Drama"]
                              }
                             )

                 :title    (create-with-default "The Big Short") ,
                 :year    (create-with-default "2015") ,
                 :runtime    (create-with-default "130") ,
                 :posterUrl  (create-with-default  "https://images-na.ssl-images-amazon.com/images/M/MV5BNDc4MThhN2EtZjMzNC00ZDJmLThiZTgtNThlY2UxZWMzNjdkXkEyXkFqcGdeQXVyNDk3NzU2MTQ@._V1_SX300.jpg") ,
                 :actors    (create-with-default "Ryan Gosling, Rudy Eisenzopf, Casey Groves, Charlie Talbert")


                 })


(s/defschema UpdateMovieRequest  movie-data)



(s/defschema Movie  (merge movie-data {:id pos-int?})
                   )

(s/defschema OldMovie {

                     :plot
                     (spec-core/spec
                       {:spec                string?
                        :swagger/default     "Four denizens in the world of high-finance predict the credit and housing bubble collapse of the mid-2000s, and decide to take on the big banks for their greed and lack of foresight."
                        }
                       )
                     :director (spec-core/spec
                                 {:spec                string?
                                  :swagger/default     "Four denizens in the world of high-finance predict the credit and housing bubble collapse of the mid-2000s, and decide to take on the big banks for their greed and lack of foresight."
                                  }
                                 ),
                     :genres   (spec-core/spec
                                 {:spec               (spec/coll-of string?)
                                  :swagger/default     ["Biography" "Comedy" "Drama"]
                                  }
                                 )

                     :title    (create-with-default "The Big Short") ,
                    :year    (create-with-default "2015") ,
                    :runtime    (create-with-default "130") ,
                    :posterUrl  (create-with-default  "https://images-na.ssl-images-amazon.com/images/M/MV5BNDc4MThhN2EtZjMzNC00ZDJmLThiZTgtNThlY2UxZWMzNjdkXkEyXkFqcGdeQXVyNDk3NzU2MTQ@._V1_SX300.jpg") ,
                    :actors    (create-with-default "Ryan Gosling, Rudy Eisenzopf, Casey Groves, Charlie Talbert") ,
                    :id int?

                    }
                   )

(defn copy-file [source-path dest-path]
  (io/copy (io/file source-path) (io/file dest-path)))

(defn movie-routes []

  [""  {:swagger {:tags ["movies"]}}

   ["/movies" {

               :get   {

                       :handler all-movies

                       :summary    "List All Movies"

                       :responses  {200 {:body
                                         [Movie]
                                         }}

                       }
               :post    {
                         :handler insert-movie
                         :parameters {
                                      :body Movie
                                      }
                         }

               }]

   ["/movies/:id" {

                   :get {
                         :handler movie-by-id
                         :parameters {
                                      :path {:id int?}

                                      }
                         }




                   :put   {
                           :handler update-movie-by-id
                           :parameters {
                                        :path {:id int?}
                                        :body UpdateMovieRequest
                                        }
                           }
                   :delete  {
                             :handler delete-movie-by-id
                             :parameters {
                                          :path {:id int?}

                                          }
                             }


                   }]

   ]
  )

(defn service-routes []
  ["/api"
   {:coercion   spec-coercion/coercion
    :muuntaja   formats/instance
    :swagger    {:id ::api}
    :middleware [;; query-params & form-params
                 parameters/parameters-middleware
                 ;; content-negotiation
                 muuntaja/format-negotiate-middleware
                 ;; encoding response body
                 muuntaja/format-response-middleware
                 ;; exception handling
                 coercion/coerce-exceptions-middleware
                 ;; decoding request body
                 muuntaja/format-request-middleware
                 ;; coercing response bodys
                 coercion/coerce-response-middleware
                 ;; coercing request parameters
                 coercion/coerce-request-middleware
                 ;; multipart
                 multipart/multipart-middleware]}

   ;; swagger documentation
   ["" {:no-doc  true
        :swagger {:info {:title       "Movies API"
                         :description "https://deemwar.com"}}}

    ["/swagger.json"
     {:get (swagger/create-swagger-handler)}]

    ["/api-docs/*"
     {:get (swagger-ui/create-swagger-ui-handler
             {:url    "/api/swagger.json"
              :config {:validator-url nil}})}]]

    (movie-routes)
   ["/ping"
    {:get (constantly (ok {:message "pong"}))}]


   ["/math"
    {:swagger {:tags ["math"]}

     }

    ["/plus"
     {:get  {:summary    "plus with spec query parameters"
             :parameters {:query {:x int?, :y int?}}
             :responses  {200 {:body {:total pos-int?}}}
             :handler    (fn [{{{:keys [x y]} :query} :parameters}]
                           {:status 200
                            :body   {:total (+ x y)}})}
      :post {


             :summary    "plus with spec body parameters new"
             :parameters {
                          :body {:x  (spec-core/spec
                                           {:spec                int?
                                            :swagger/default     125
                                            }
                                           )
                                 :y int?
                                 }


                         }
             :responses  {200 {:body {:total pos-int?}}}
             :handler    (fn [{{{:keys [x y]} :body} :parameters}]
                           {:status 200
                            :body   {:total (+ x y)}})}}]]

   ["/files"
    {:swagger {:tags ["files"]}}

    ["/upload"
     {:post {:summary    "upload a file"
             :parameters {:multipart {:file multipart/temp-file-part}}
             :responses  {200 {:body {:name string?, :size int?}}}
             :handler    (fn [{{{:keys [file]} :multipart} :parameters}]

                           (println (type file))
                           (println  file)
                           (io/copy (file :tempfile) (io/file "ar.pdf"))
                           {:status 200
                            :body   {
                                     :result "Saved Sucessfully"
                                     :name (:filename file)
                                     :size (:size file)}}

                           )}}]

    ["/download"
     {:get {:summary "downloads a file"
            :swagger {:produces ["image/png"]}
            :handler (fn [_]
                       {:status  200
                        :headers {"Content-Type" "image/png"}
                        :body    (-> "public/img/warning_clojure.png"
                                     (io/resource)
                                     (io/input-stream))

                        })}}]]])
