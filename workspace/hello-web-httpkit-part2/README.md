Add Dependencies

     [ring/ring-core "1.7.1"]                 
                 [http-kit "2.3.0"]
                 [ring/ring-defaults "0.3.2"]
                 [compojure "1.6.1"]
                 [ring/ring-json "0.4.0"]


in main method

  (run-server app {:port 3000})

  