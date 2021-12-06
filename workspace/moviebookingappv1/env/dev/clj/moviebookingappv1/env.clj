(ns moviebookingappv1.env
  (:require
    [selmer.parser :as parser]
    [clojure.tools.logging :as log]
    [moviebookingappv1.dev-middleware :refer [wrap-dev]]))

(def defaults
  {:init
   (fn []
     (parser/cache-off!)
     (log/info "\n-=[moviebookingappv1 started successfully using the development profile]=-"))
   :stop
   (fn []
     (log/info "\n-=[moviebookingappv1 has shut down successfully]=-"))
   :middleware wrap-dev})
