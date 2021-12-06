(ns moviebookingappv3auth.env
  (:require [clojure.tools.logging :as log]))

(def defaults
  {:init
   (fn []
     (log/info "\n-=[moviebookingappv3auth started successfully]=-"))
   :stop
   (fn []
     (log/info "\n-=[moviebookingappv3auth has shut down successfully]=-"))
   :middleware identity})
