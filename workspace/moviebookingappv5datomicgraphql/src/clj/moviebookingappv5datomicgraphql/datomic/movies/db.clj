(ns moviebookingappv5datomicgraphql.datomic.movies.db
  (:require
            [mount.core :refer [defstate]]
            [datomic.client.api :as d]
            [moviebookingappv5datomicgraphql.datomic.movies.data :refer [movie-schema default-movie-data]]
    ; [moviebookingappv5datomicgraphql.datomic.client-api :refer :all]

             )

  )


;Get from Configuration
(def cfg {:server-type :dev-local
          :system      "dev"})

;For Cloud
;(def cfg {:server-type :ion
;          :region "<your AWS Region>" ;; e.g. us-east-1
;          :system "<system name>"
;          :creds-profile "<your_aws_profile_if_not_using_the_default>"
;          :endpoint "<your endpoint>"})


(def client (d/client cfg))


(def movie-db-name "moviesnew")




(defn get-connection []
  (d/connect client {:db-name movie-db-name})
  )





;(defn get-query-db []
;  (d/db (get-connection))
;
;  )



(comment

  ;to delete db
  ;(d/delete-database client {:db-name movie-db-name})

  )
(defn create-database []

  (d/create-database client {:db-name movie-db-name})

  )

(defn create-movie-schema []
 (d/transact (get-connection) {:tx-data movie-schema })
  )

(defn insert-default-movies []
  (d/transact (get-connection) {:tx-data default-movie-data })

  )
(comment
  (println client)
  )
(defn should-initialize-database? []
  (try
    (d/connect client {:db-name movie-db-name})
    ;connection established , => Db Exists , No need to initialize database
    false
    (catch Exception e
      true
      )
    )
  )


(defn do-migrations []

   (println "should-initialize-database?" (should-initialize-database?))
  (when (should-initialize-database?)
    (println "Creating Database")


    (create-database)
    (println "Creating Schema")
    (create-movie-schema)
    (println "inserting movies")
    (println (insert-default-movies))
    )

  )


(defstate ^{:on-reload :noop} datomic-connection
                :start         (do
            (do-migrations)
            (get-connection)
            )

                :stop
                (do
                  ;(close-connection datomic-db)
                  (println "do nothing for now")
                  )
          )