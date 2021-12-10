(defproject datomic-dev-client "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [
                 [org.clojure/clojure "1.10.1"]
                 [com.datomic/dev-local "1.0.238"]

                 ]
  :repositories [
                 ["cognitect-dev-tools" {:url      "https://dev-tools.cognitect.com/maven/releases/"
                                         :username "muthuishere@gmail.com"
                                         :password "CB5BB29D51A198961E1A1C68509D410458A29E10"}]]


  :repl-options {:init-ns datomic-dev-client.core})
