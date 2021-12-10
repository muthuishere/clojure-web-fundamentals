(ns moviebookingappv5datomicgraphql.users.user-service
  (:require [moviebookingappv5datomicgraphql.db.core :as db]
            [clojure.tools.logging :as log])
  )

(defn create-user! [data]

  (try


    {
     :result (db/create-user! data )
     :error nil
     }

    (catch Exception e

      (log/info e)

      {
       :error (.toString e)
       }
      )
    )
  )

(defn create-admin-user! [data]

  (try


    {
     :result (db/create-admin! data )
     :error nil
     :stacktrace nil
     }

    (catch Exception e

      ;(log/info e)

      {
       :result nil
       :error (.getMessage e)
       ; :stacktrace (.toString e)
       }
      )
    )
  )