(ns moviebookingappv5datomicgraphql.graphql.core (:require
             [com.walmartlabs.lacinia.util :refer [attach-resolvers]]
             [com.walmartlabs.lacinia.schema :as schema]
             [com.walmartlabs.lacinia :as lacinia]
             [clojure.data.json :as json]
             [moviebookingappv5datomicgraphql.datomic.movies.handler :refer [get-all-movies]]

             [clojure.edn :as edn]
             [clojure.java.io :as io]
             [ring.util.http-response :refer :all]
             [mount.core :refer [defstate]]))

(defn get-hero [context args value]

  (println context)
  (println args)
  (println value)
  (let [data  [{:id 1000
                :name "Luke"
                :home_planet "Tatooine"
                :appears_in ["NEWHOPE" "EMPIRE" "JEDI"]}
               {:id 2000
                :name "Lando Calrissian"
                :home_planet "Socorro"
                :appears_in ["EMPIRE" "JEDI"]}]]
    (first data)))

(defstate compiled-schema
          :start
          (-> "graphql/schema.edn"
              io/resource
              slurp
              edn/read-string
              (attach-resolvers {
                                 :get-all-movies get-all-movies
                                 :get-hero get-hero
                                 :get-droid (constantly {})})
              schema/compile)
          )

(defn format-params [query]
  (let [parsed (json/read-str query)] ;;-> placeholder - need to ensure query meets graphql syntax
    (str "query { hero(id: \"1000\") { name appears_in }}")))

(defn execute-request [query]
  (let [{:keys [query variables operationName]} (json/read-str query :key-fn keyword)]
    (-> (lacinia/execute compiled-schema query variables nil)
        (json/write-str))))

