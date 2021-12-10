(ns moviebookingappv5datomicgraphql.env
  (:require
    [selmer.parser :as parser]
    [clojure.tools.logging :as log]
    [moviebookingappv5datomicgraphql.dev-middleware :refer [wrap-dev]]))

(def defaults
  {:init
   (fn []
     (parser/cache-off!)
     (log/info "\n-=[moviebookingappv5datomicgraphql started successfully using the development profile]=-"))
   :stop
   (fn []
     (log/info "\n-=[moviebookingappv5datomicgraphql has shut down successfully]=-"))
   :middleware wrap-dev})
