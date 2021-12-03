(defproject hello-web-httpkit "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [
                 [org.clojure/clojure "1.10.1"]

                 [ring/ring-core "1.7.1"]
                 ;  [ring/ring-jetty-adapter "1.7.1"]
                 [http-kit "2.3.0"]
                 [ring/ring-defaults "0.3.2"]
                 [compojure "1.6.1"]
                 [ring/ring-json "0.4.0"]
                 ]
  :ring {:handler hello-web-httpkit.core/app}
  :main hello-web-httpkit.core
  :plugins [[lein-ring "0.12.5"]]
  :repl-options {:init-ns hello-web-httpkit.core})