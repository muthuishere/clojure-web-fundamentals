(defproject hello-web-1 "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[org.clojure/clojure "1.10.1"]
                 [ring/ring-core "1.7.1"]
                 [ring/ring-jetty-adapter "1.7.1"]
                 [ring/ring-defaults "0.3.2"]
                 [compojure "1.6.1"]
                 [ring/ring-json "0.4.0"]

                 ]
  :repl-options {:init-ns hello-web-1.core}
  ;part 6 for live reload
  ;use now lein ring server-headless
  :ring {:handler hello-web-1.core/app}
  :plugins [[lein-ring "0.12.5"]]
  ; end of part 6

  ;Part 5 for lein run
  ;Main Namespace after first thing done
  :main hello-web-1.core
  )
