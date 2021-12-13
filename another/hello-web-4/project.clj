(defproject hello-web-4 "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[org.clojure/clojure "1.10.1"]

 [org.clojure/clojure "1.10.1"]
 [ring/ring-defaults "0.3.2"]
 [metosin/reitit "0.5.15"]
 [metosin/ring-http-response "0.9.3"]
 [ring/ring-json "0.4.0"]
 [http-kit "2.3.0"]
                 ]

  :ring {
         :handler hello-web-4.core/app
         }
  :plugins [[lein-ring "0.12.5"]]
  :repl-options {:init-ns hello-web-4.core})
