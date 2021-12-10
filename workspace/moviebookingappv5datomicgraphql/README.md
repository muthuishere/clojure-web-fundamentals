
### Datomic

inproject.clj

```clojure

;Datomic
[com.datomic/dev-local "1.0.238"]

;might not required now , if we
;if using datomic cloud
    [com.datomic/client-cloud "1.0.117"]

[com.datomic/datomic-pro
 "0.9.5966"
]

;you need to add repo for that as well


  :repositories [
                 ["cognitect-dev-tools" {:url      "https://dev-tools.cognitect.com/maven/releases/"
                                         :username "muthuishere@gmail.com"
                                         :password "CB5BB29D51A198961E1A1C68509D410458A29E10"}]

                 ]


```
explain entity attribute value active/or not

explain schema


create package datomic.movies
data.clj
    explain schema
    explain data

db.clj
    explain mount start
    create database exists, if not create database
    insert schema,data
    create handler
    get all movies
        as array
        as keys
        as entity id and pull
        
        parse movie
        
    getbyid
    insert
    deletedbyid
    update id data

