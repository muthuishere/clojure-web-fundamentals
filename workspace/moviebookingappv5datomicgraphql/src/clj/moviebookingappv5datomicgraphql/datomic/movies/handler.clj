(ns moviebookingappv5datomicgraphql.datomic.movies.handler
  (:require [moviebookingappv5datomicgraphql.datomic.movies.service :as movie-service])
  )


(defn get-all-movies [context args value]
  (movie-service/all-movies)
  )


