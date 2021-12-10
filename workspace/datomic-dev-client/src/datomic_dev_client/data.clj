(ns datomic-dev-client.data)

(def movie-schema

  [

   {
    :db/ident :movie/plot
    :db/valueType :db.type/string
    :db/cardinality :db.cardinality/one
    :db.install/_attribute :db.part/db
    }

   {
    :db/ident :movie/director
    :db/valueType :db.type/string
    :db/cardinality :db.cardinality/one
    :db.install/_attribute :db.part/db
    }

   {
    :db/ident :movie/genres
    :db/valueType :db.type/string
    :db/cardinality :db.cardinality/many
    :db.install/_attribute :db.part/db
    }

   {
    :db/ident :movie/title
    :db/valueType :db.type/string
    :db/cardinality :db.cardinality/one
    :db.install/_attribute :db.part/db
    }

   {
    :db/ident :movie/year
    :db/valueType :db.type/long
    :db/cardinality :db.cardinality/one
    :db.install/_attribute :db.part/db
    }

   {
    :db/ident :movie/actors
    :db/valueType :db.type/string
    :db/cardinality :db.cardinality/many
    :db.install/_attribute :db.part/db
    }
   {
    :db/ident :movie/id
    :db/unique :db.unique/identity
    :db/valueType :db.type/long
    :db/cardinality :db.cardinality/one
    :db.install/_attribute :db.part/db
    }
   {
    :db/ident :movie/runtime
    :db/valueType :db.type/long
    :db/cardinality :db.cardinality/one
    :db.install/_attribute :db.part/db
    }
   {
    :db/ident :movie/posterUrl
    :db/valueType :db.type/string
    :db/cardinality :db.cardinality/one
    :db.install/_attribute :db.part/db
    }


   ]
  )



(def default-movie-data
  [
   {:movie/plot "A couple of recently deceased ghosts contract the services of a \"bio-exorcist\" in order to remove the obnoxious new owners of their house."
    :movie/director "Tim Burton"
    :movie/genres ["Fantasy","Comedy"]
    :movie/title "Beetlejuice"
    :movie/year 1988
    :movie/actors ["Alec Baldwin", "Geena Davis","Annie McEnroe", "Maurice Page"]
    :movie/id 1
    :movie/runtime 92
    :movie/posterUrl "https://images-na.ssl-images-amazon.com/images/M/MV5BMTUwODE3MDE0MV5BMl5BanBnXkFtZTgwNTk1MjI4MzE@._V1_SX300.jpg"
    }
   {:movie/plot "The Cotton Club was a famous night club in Harlem. The story follows the people that visited the club, those that ran it, and is peppered with the Jazz music that made it so famous."
    :movie/director "Francis Ford Coppola"
    :movie/genres ["Crime","Drama" "Music"]
    :movie/title "The Cotton Club"
    :movie/year 1984
    :movie/actors ["Richard Gere", "Gregory Hines", "Diane Lane", "Lonette McKee"]
    :movie/id 2
    :movie/runtime 127
    :movie/posterUrl "https://images-na.ssl-images-amazon.com/images/M/MV5BMTU5ODAyNzA4OV5BMl5BanBnXkFtZTcwNzYwNTIzNA@@._V1_SX300.jpg"
    }

   {:movie/plot "Two imprisoned men bond over a number of years, finding solace and eventual redemption through acts of common decency."
    :movie/director "Frank Darabont"
    :movie/genres ["Crime","Drama"]
    :movie/title "The Shawshank Redemption"
    :movie/year 1994
    :movie/actors ["Tim Robbins","Morgan Freeman", "Bob Gunton", "William Sadler"]
    :movie/id 3
    :movie/runtime 142
    :movie/posterUrl "https://images-na.ssl-images-amazon.com/images/M/MV5BODU4MjU4NjIwNl5BMl5BanBnXkFtZTgwMDU2MjEyMDE@._V1_SX300.jpg"
    }


   {:movie/plot "An American reporter goes to the Australian outback to meet an eccentric crocodile poacher and invites him to New York City."
    :movie/director "Peter Faiman"
    :movie/genres ["Adventure","Comedy"]
    :movie/title "Crocodile Dundee"
    :movie/year 1986
    :movie/actors ["Paul Hogan","Linda Kozlowski", "John Meillon", "David Gulpilil"]
    :movie/id 4
    :movie/runtime 97
    :movie/posterUrl "https://images-na.ssl-images-amazon.com/images/M/MV5BMTg0MTU1MTg4NF5BMl5BanBnXkFtZTgwMDgzNzYxMTE@._V1_SX300.jpg"
    }

   ] )