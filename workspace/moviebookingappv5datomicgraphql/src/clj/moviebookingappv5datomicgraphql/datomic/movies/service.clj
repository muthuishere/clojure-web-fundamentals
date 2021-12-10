(ns moviebookingappv5datomicgraphql.datomic.movies.service
  (:require

    [datomic.client.api :as d]
    [moviebookingappv5datomicgraphql.datomic.movies.db :refer [datomic-connection]]


    )
  )

(defn query-engine []

  (d/db datomic-connection)
  )
(comment

  (all-movies-array )
  )
(defn all-movies-array []

  ;Part 1
  (d/q '[:find ?movie ?title
         :where
         [?movie :movie/title ?title]
         ]

       (query-engine))

  ;part 2
  ;
  ;(d/q '[:find ?title ?plot ?director (distinct ?actors)  (distinct ?genres)  ?year ?id ?runtime ?posterUrl
  ;       :where
  ;       [?movie :movie/title ?title]
  ;       [?movie :movie/plot ?plot]
  ;       [?movie :movie/director ?director]
  ;       [?movie :movie/genres ?genres]
  ;       [?movie :movie/year ?year]
  ;       [?movie :movie/id ?id]
  ;       [?movie :movie/actors ?actors]
  ;
  ;       [?movie :movie/runtime ?runtime]
  ;       [?movie :movie/posterUrl ?posterUrl]
  ;
  ;       ]
  ;
  ;     (query-engine))

  )


(defn all-movies-keys-verbose[]

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

       (query-engine))

  )


(comment
  (all-movies )



 (get-all-entity-ids)


  )

(defn get-all-entity-ids []

 (flatten  ( d/q '[:find ?entityid
          :where
          [?entityid :movie/id]
          ]
        (query-engine)
        ))
  )

(defn get-keyword-from [key]

  (if (= "db" (namespace key) )
    (keyword (str (namespace key) (name key)))
    (keyword (name key))

    )
  )
(defn movie-by-entity-id [id]
  (reduce-kv #(assoc %1 (get-keyword-from  %2) %3 ) {} (d/pull (query-engine) '[*] id))


  )
(defn all-movies []

  (map movie-by-entity-id   (get-all-entity-ids))

  )

(comment
  (movie-by-id 147)
  (all-movies)
  )

(defn get-entity-id-by-movie-id [movie-id]


  (first  (flatten ( d/q '[:find ?entityid
                           :in $ ?id
                           :where
                           [?entityid :movie/id ?id]
                           ]
                         (query-engine)
                         movie-id
                         )))

  )

(defn movie-by-id [id]
  (let [res (get-entity-id-by-movie-id id)]
    (if res
      (movie-by-entity-id res)
      nil
      )
    )
  )

(comment
  (insert-movie { :plot
                 "Four denizens in the world of high-finance predict the credit and housing bubble collapse of the mid-2000s, and decide to take on the big banks for their greed and lack of foresight.",
                 :director "Adam McKay",
                 :genres   ["Biography" "Comedy" "Drama"],
                 :title    "The Big Short",
                 :year     2015,
                 :actors   "Ryan Gosling, Rudy Eisenzopf, Casey Groves, Charlie Talbert",
                 :id       147,
                 :runtime  130,
                 :posterUrl
                 "https://images-na.ssl-images-amazon.com/images/M/MV5BNDc4MThhN2EtZjMzNC00ZDJmLThiZTgtNThlY2UxZWMzNjdkXkEyXkFqcGdeQXVyNDk3NzU2MTQ@._V1_SX300.jpg"
                 })

  (movie-by-id 147)
  (all-movies)
  )



(defn insert-movie [{:keys [ plot director genres title year actors id runtime posterUrl]}]

(println "year" year)

  (let [
        data-to-be-inserted [ {:movie/plot plot
                             :movie/director director
                             :movie/genres genres
                             :movie/title title
                             :movie/year year
                             :movie/actors actors
                             :movie/id id
                             :movie/runtime runtime
                             :movie/posterUrl posterUrl
                             }]

        ]

    (d/transact datomic-connection  {:tx-data data-to-be-inserted })

    )


  )

(comment

  (delete-movie-by-id 147)

  (movie-by-id 147)
  )

(defn delete-movie-by-id [id]

  (let [
        entity-id (get-entity-id-by-movie-id id)
        ]



    (d/transact
      datomic-connection
      {:tx-data
       [
        [:db/retractEntity entity-id]
        ]
       })


    )

  )



(comment
  (update-movie 147 { :plot
                 "Four denizens in the world of high-finance predict the credit and housing bubble collapse of the mid-2000s, and decide to take on the big banks for their greed and lack of foresight.",
                 :director "Adam McKay",
                 :genres   ["Biographynew" "Comedy" "Drama"],
                 :title    "The Big Short",
                 :year     2015,
                 :actors   "Ryan Gosling, Rudy Eisenzopf, Casey Groves, Charlie Talbert",
                 :runtime  130,
                 :posterUrl
                 "https://images-na.ssl-images-amazon.com/images/M/MV5BNDc4MThhN2EtZjMzNC00ZDJmLThiZTgtNThlY2UxZWMzNjdkXkEyXkFqcGdeQXVyNDk3NzU2MTQ@._V1_SX300.jpg"
                 })

  (movie-by-id 147)
  (all-movies)
  )


;to parse
; use (keyword (name :movie/year))
(defn update-movie [id {:keys [ plot director genres title year actors runtime posterUrl]}]


  (let [
        data-to-be-updated   [ {:movie/plot plot
                               :movie/director director
                               :movie/genres genres
                               :movie/title title
                               :movie/year year
                               :movie/actors actors
                               :movie/id id
                               :movie/runtime runtime
                               :movie/posterUrl posterUrl
                               }]



        ]

    (delete-movie-by-id id)
    (d/transact datomic-connection
                {:tx-data data-to-be-updated

                  })

    )


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
