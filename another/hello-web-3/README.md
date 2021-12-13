### Dependencies

     [ring/ring-core "1.7.1"]
     [ring/ring-defaults "0.3.2"]
     [http-kit "2.3.0"]

     [ring/ring-json "0.4.0"]


### Plugins
```clojure

:ring {
:handler hello-web-3.core/app
}
:plugins [[lein-ring "0.12.5"]]

```

Same as old except     ;[compojure "1.6.1"]

replace with 

     [metosin/compojure-api "1.1.13"]
     [metosin/ring-http-response "0.9.3"]


# Update core.clj
Add the following require library

        [ring.middleware.defaults :refer [wrap-defaults  api-defaults]]
        [ring.middleware.json :as middleware]
        [org.httpkit.server :refer [run-server]]
        [compojure.api.sweet :as sw] ; instead of compojure refer all

For compojure api , there is no defroutes rather we will use app directly 

```clojure

(def app
    (sw/api

    (sw/context "/api" []

                (sw/GET "/movies" request (all-movies request) )

                (sw/POST "/movies" request (insert-movie request) )

                (sw/GET "/movies/:id" request (movie-by-id request) )

                (sw/PUT "/movies/:id" request (update-movie-by-id request) )

                (sw/DELETE "/movies/:id" request (delete-movie-by-id request) )


                )



    ))
```


Update Definition for movies

```clojure



```

http://localhost:3000/api/movies

response will be there

The Url parameters always be available on :params for compojure api


## for post

```json


 {
      "id": 145,
      "title": "A Separation",
      "year": "2011",
      "runtime": "123",
      "genres": [
        "Drama",
        "Mystery"
      ],
      "director": "Asghar Farhadi",
      "actors": "Peyman Moaadi, Leila Hatami, Sareh Bayat, Shahab Hosseini",
      "plot": "A married couple are faced with a difficult decision - to improve the life of their child by moving to another country or to stay in Iran and look after a deteriorating parent who has Alzheimer's disease.",
      "posterUrl": "http://ia.media-imdb.com/images/M/MV5BMTYzMzU4NDUwOF5BMl5BanBnXkFtZTcwMTM5MjA5Ng@@._V1_SX300.jpg"
    }
```


Also you can use
```clojure

(comment



  (update-movie-for @movies 1 {:title "Changed Name" :year "2000"})

  (def id 1)
  (first (filter #(= 104 (get % :id)) @movies))
  (first (filter #(= "1" (get % :id)) @movies))

  (app {:request-method :put
        :uri            "/api/movies/1"
        :body-params    {
                         :title "Changed Name newest new"
                         :year  "2000"
                         }}
       )

  (app {:request-method :get
        :uri            "/api/movies/1"
        })



  (app {:request-method :delete
        :uri            "/api/movies/1"
        }
       )

  (app {:request-method :get
        :uri            "/api/movies/1"
        })



  (app {:request-method :get
        :uri            "/api/movies/233"
        })

  (app {:request-method :get
        :uri            "/api/movies"
        })


  (app {:request-method :post
        :uri            "/api/movies"
        :body-params    {

                         :plot
                         "Four denizens in the world of high-finance predict the credit and housing bubble collapse of the mid-2000s, and decide to take on the big banks for their greed and lack of foresight.",
                         :director "Adam McKay",
                         :genres   ["Biography" "Comedy" "Drama"],
                         :title    "The Big Short",
                         :year     "2015",
                         :actors   "Ryan Gosling, Rudy Eisenzopf, Casey Groves, Charlie Talbert",
                         :id       146,
                         :runtime  "130",
                         :posterUrl
                         "https://images-na.ssl-images-amazon.com/images/M/MV5BNDc4MThhN2EtZjMzNC00ZDJmLThiZTgtNThlY2UxZWMzNjdkXkEyXkFqcGdeQXVyNDk3NzU2MTQ@._V1_SX300.jpg"}

        })



  (app {:request-method :get
        :uri            "/hello"
        })


  (app {:request-method :post
        :uri            "/insertdata"
        })

  )

```