(ns moviebookingappv3auth.env
  (:require
    [selmer.parser :as parser]
    [clojure.tools.logging :as log]
    [moviebookingappv3auth.dev-middleware :refer [wrap-dev]]))

(def defaults
  {:init
   (fn []
     (parser/cache-off!)
     (log/info "\n-=[moviebookingappv3auth started successfully using the development profile]=-"))
   :stop
   (fn []
     (log/info "\n-=[moviebookingappv3auth has shut down successfully]=-"))
   :middleware wrap-dev})
