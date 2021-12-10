(ns datomic-dev-client.core
  (:require

    [datomic.client.api :as d]
    [datomic-dev-client.data :refer [movie-schema default-movie-data]]

    )
  )
(def cfg {:server-type :dev-local
          :system      "dev"
          }
          
          )
(def movie-db-name "movies")
(def client (d/client cfg))


(defn create-database [db-name]
  (d/create-database client {:db-name db-name})
  )



(defn delete-database [db-name]
  (d/delete-database client {:db-name db-name})
  )


(defn connect-db [db-name]

  (d/connect client {:db-name db-name})

  )

(defn database-exists? [db-name]
  (try
    (connect-db db-name)
    true
    (catch Exception e
      false
      )
    )
  )

(defn database-does-not-exists? [db-name]
  (not (database-exists? db-name))
  )

(defn query-engine [db-name]
  (d/db (connect-db db-name))
  )


(defn connect-movie-db []
  (connect-db "movies")
  )




(defn query-engine-for-movie []
  (query-engine movie-db-name)
  )



(defn create-movie-schema []
  (d/transact (connect-movie-db) {:tx-data movie-schema
                                  })
  )

(defn insert-default-movies []


  (d/transact (connect-movie-db) {:tx-data default-movie-data

                                  })

  )

;insert data

;update data
  ;delete & update


;graphql
;queries
; all
; by id
; by genre
;mutation
;insert
;delete
(defn setup-database []

  ; (println "does not exists" (database-does-not-exists? movie-db-name))
  (when (database-does-not-exists? movie-db-name)
    (println "Creating Database")
    (create-database movie-db-name)

    (println "Creating Schema")
    (create-movie-schema)
    (println "inserting movies")
    (println (insert-default-movies))
    )

  )

(defn get-all-movies-array []
  ; Find All


  (d/q '[:find ?title ?plot ?director (distinct ?actors)  (distinct ?genres)  ?year ?id ?runtime ?posterUrl
         :where
         [?movie :movie/title ?title]
         [?movie :movie/plot ?plot]
         [?movie :movie/director ?director]
         [?movie :movie/genres ?genres]
         [?movie :movie/year ?year]
         [?movie :movie/id ?id]
         [?movie :movie/actors ?actors]

         [?movie :movie/runtime ?runtime]
         [?movie :movie/posterUrl ?posterUrl]

         ]

       (query-engine-for-movie))

  )


(defn find-actors-by-id [movie-id]

  (d/q '[:find ?actors
         :in $ ?id
         :where
         [?movie :movie/actors ?actors]
         [?movie :movie/id ?id]

         ]
       (query-engine-for-movie)
       movie-id
       )
  )

(defn find-genres-by-id [movie-id]

  (d/q '[:find ?genres
         :in $ ?id
         :where
         [?movie :movie/genres ?genres]
         [?movie :movie/id ?id]

         ]
       (query-engine-for-movie)
       movie-id
       )
  )


(defn get-all-movies []
  ; Find All




  (d/q '[:find ?title ?plot ?director (distinct ?actors)  (distinct ?genres)  ?year ?id ?runtime ?posterUrl
         :keys title plot director actors genres year id runtime posterUrl
         :where
         [?movie :movie/title ?title]
         [?movie :movie/plot ?plot]
         [?movie :movie/director ?director]
         [?movie :movie/genres ?genres]
         [?movie :movie/year ?year]
         [?movie :movie/id ?id]
         [?movie :movie/actors ?actors]

         [?movie :movie/runtime ?runtime]
         [?movie :movie/posterUrl ?posterUrl]

         ]

       (query-engine-for-movie))

  )


(defn find-movie-by-id [movie-id]

  (d/q '[:find ?title ?plot ?director (distinct ?actors)  (distinct ?genres)  ?year ?id ?runtime ?posterUrl
         :in $ ?id
         :keys title plot director actors genres year id runtime posterUrl
         :where
         [?movie :movie/title ?title]
         [?movie :movie/plot ?plot]
         [?movie :movie/director ?director]
         [?movie :movie/genres ?genres]
         [?movie :movie/year ?year]
         [?movie :movie/id ?id]
         [?movie :movie/actors ?actors]

         [?movie :movie/runtime ?runtime]
         [?movie :movie/posterUrl ?posterUrl]

         ]

       (query-engine-for-movie) movie-id)
  )



(defn parse-entity-id [[[entity-id]]]
  entity-id
  )
(defn find-entity-id-by-movie-id [movie-id]


  (parse-entity-id ( d/q '[:find ?entityid
                           :in $ ?id
                           :where
                           [?entityid :movie/id ?id]
                           ]
                         (query-engine-for-movie)
                         movie-id
                         ))

  )




(defn delete-by-id [ connection entity-id]

  (println "deleting " entity-id)
  (d/transact
    connection
    {:tx-data
     [[:db/retractEntity entity-id]]

     })
  )



(defn delete-by-movie-id [movie-id]

  (delete-by-id (connect-movie-db)  (find-entity-id-by-movie-id movie-id ))


  )




(comment

  (delete-by-movie-id 2)


;(find-all-movie-ids)

(find-entity-id-by-movie-id 2)
  (get-all-movies)
  (find-movie-by-id 1)
  (find-genres-by-id 2)

  (delete-database movie-db-name)
  ;(database-exists? movie-db-name)
  ;(database-does-not-exists? movie-db-name)
  ;(create-database movie-db-name)

  (setup-database)




  (get-all-movies)

  (count (get-all-movies))



  )




;Download cognitect tools and run install
;https://cognitect.com/dev-tools


; Update your leiningen project.clj

;Add dependency
; [com.datomic/dev-local "1.0.238"]


; Add repository

;:repositories [
;                 ["cognitect-dev-tools" {:url      "https://dev-tools.cognitect.com/maven/releases/"
;                                         :username "muthuishere@gmail.com"
;                                         :password "CB5BB29D51A198961E1A1C68509D410458A29E10"}]]
;



;Datomic is ready for development stage


;For prod

;(require '[datomic.dev-local :as dl])
;(dl/divert-system {:system "production"})
;
;;; existing requests for Cloud system will be served locally!
;(def client (d/client {:system "production"
;                       :server-type :ion
;                       :region "us-east-1"
;                       :endpoint "https://ljfrt3pr18.execute-api.us-east-1.amazonaws.com"}))



; This is db client

; Create a Database

;(d/delete-database client {:db-name "movies"})



(comment
  ;   plot      VARCHAR(781) NOT NULL
  ;    ,director  VARCHAR(120) NOT NULL
  ;    ,genres    VARCHAR(117) NOT NULL
  ;    ,title     VARCHAR(124) NOT NULL
  ;    ,year      INTEGER  NOT NULL
  ;    ,actors    VARCHAR(157) NOT NULL
  ;    ,id        INTEGER  NOT NULL PRIMARY KEY
  ;    ,runtime   INTEGER  NOT NULL
  ;    ,posterUrl VARCHAR(781) NOT NULL

  )

(comment

  ;Queries should be exceuted against database
  (def db (d/db (connect-movie-db)))

  ; Find All


  (d/q '[:find ?title ?plot ?director ?genres ?year ?id ?runtime ?posterUrl
         :where

         [?movie :movie/title ?title]
         [?movie :movie/plot ?plot]
         [?movie :movie/director ?director]
         [?movie :movie/plot ?genres]
         [?movie :movie/plot ?year]
         [?movie :movie/plot ?id]
         [?movie :movie/plot ?runtime]
         [?movie :movie/plot ?posterUrl]

         ]

       db)


  ;FInd Determine which data to return , you will be using only dump variable there

  ;Where you will do the mapping against the variavle
  ;Query Data
  (def all-titles-q '[:find ?title ?plot
                      :where

                      [?movie :movie/title ?title]
                      [?movie :movie/plot ?plot]

                      ])


  (d/q all-titles-q db)

  (d/pull db '[*])

  )

(defn foo
  "I don't do a whole lot."
  [x]
  (println x "Hello, World!"))
