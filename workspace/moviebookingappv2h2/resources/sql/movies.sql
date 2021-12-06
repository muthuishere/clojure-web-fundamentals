-- :name get-all-movies :? :*
-- :doc retrieves a list of movies
SELECT * FROM movies


-- :name insert-movie! :! :n
-- :doc creates a new user record
    INSERT INTO movies
(plot,director,genres,title,year,actors,id,runtime,posterUrl)
VALUES (:plot,:director,:genres,:title,:year,:actors,:id,:runtime,:posterUrl)



-- :name update-movie! :! :n
-- :doc updates an existing user record
UPDATE movies
SET plot =:plot ,director =:director,genres =:genres,title =:title ,year =:year,actors = :actors,runtime = :runtime,posterUrl =:posterUrl
WHERE id = :id


-- :name get-movie-by-id :? :1
-- :doc retrieves a movie record given the id
SELECT * FROM movies
WHERE id = :id


-- :name delete-movie-by-id! :! :n
-- :doc deletes a movies record given the id
DELETE FROM movies
WHERE id = :id
