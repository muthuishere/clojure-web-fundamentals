(ns moviebookingappv2h2.env
  (:require
    [selmer.parser :as parser]
    [clojure.tools.logging :as log]
    [moviebookingappv2h2.dev-middleware :refer [wrap-dev]]))

(def defaults
  {:init
   (fn []
     (parser/cache-off!)
     (log/info "\n-=[moviebookingappv2h2 started successfully using the development profile]=-"))
   :stop
   (fn []
     (log/info "\n-=[moviebookingappv2h2 has shut down successfully]=-"))
   :middleware wrap-dev})
