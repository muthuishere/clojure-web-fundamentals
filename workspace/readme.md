Teach them
 Dynamic Variables
Agents
shutdown-agents


 Dynamic Variables

Clojure provides support for declaring dynamic variables that can have their
value changed within a particular scope. Let’s look at how this works.
```(declare ^:dynamic *currentdb*)
(println *currentdb*)```
; =>#<Unbound Unbound: #'bar/*currentdb*>
Here we declared *foo* as a dynamic Var and didn’t provide any value for it.
When we try to print *foo* we get an error indicating that this Var has not
been bound to any value. Let’s look at how we can assign a value to *foo* using
a binding.
```(binding [*currentdb* "jdbc://xx"]
(println *currentdb*))```
=>"I exist!"




Create a project 

lein new luminus moviebookingappv1 +service +swagger


Explain About start stop with mount
Swagger
about Logging
Dev Profiles

;Update movies
;Update Schema swagger
;show swagger

nrepl - use (count @movies)


; Just copy movies handler alone , no api


;Part 2
lein new luminus moviebookingapp +service +swagger +h2

;explain how database is started

; Add plugin
  [migratus-lein "0.7.3"]

;lein migratus create add-movies-table
;lein migratus create insert-movies

; lein run migrate