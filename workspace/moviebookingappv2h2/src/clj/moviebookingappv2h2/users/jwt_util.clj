(ns moviebookingappv2h2.users.jwt-util

  (:require
    [buddy.auth :refer [authenticated?]]
    [buddy.sign.jwt :as jwt]
    [clj-time.core :as time]
    [buddy.sign.util :as util]
    [buddy.auth.backends :as backends]
    [buddy.auth.middleware :refer [wrap-authentication]]
    )
  )


(def secret "mysecret")
;Authorization Header should
;(def backend (backends/jws {:secret secret}))

(def backend (backends/jws {:secret secret :token-name "Bearer"}))



(defn get-expiration []
  ;5 hours from now
  (time/plus (time/now) (time/hours 5))
  ;(time/plus (time/now) (time/seconds 2))

  )

(comment

 (create-token!  "x1" "user")

 (time/now)
 (time/plus (time/now) (time/seconds 5))

  )
(defn create-token!
  "Creates an auth token in the database for the given user and puts it in the database"
  [claims]

  (let [
        token (jwt/sign (merge claims {:exp (get-expiration) } )  secret )
        ]
(println token)
(println claims)
      token
    )
  )



(comment

  (jwt/sign {:h "hi"} secret )
  (jwt/unsign

    "eyJhbGciOiJIUzI1NiJ9.eyJpZCI6IjEiLCJmaXJzdF9uYW1lIjoidXNlciIsImxhc3RfbmFtZSI6Imxhc3RuYW1lIiwiZW1haWwiOiJ1c2VyQGdtYWlsLmNvbSIsImFkbWluIjpmYWxzZX0.VRfUKLtuYmIMZOYBXwhp9gg39xM5RviM48-r3UgvqxA"
    secret )
  (wrap-jwt-authentication (fn [request]
                           (println (request) )
                             )
                           )
  )

(comment



  )
(defn wrap-jwt-authentication
  [handler]
  (println "handler" handler)
  (wrap-authentication handler backend))

(comment

  (def token-auth "eyJhbGciOiJIUzI1NiJ9.eyJpZCI6IjEiLCJmaXJzdF9uYW1lIjoidXNlciIsImxhc3RfbmFtZSI6Imxhc3RuYW1lIiwiZW1haWwiOiJ1c2VyQGdtYWlsLmNvbSIsImFkbWluIjpmYWxzZSwiZXhwIjoiMTYzODc3ODg4NCJ9.hIkaNZ9ckZ3IMqjkBOEz7UtgCdRAoURgfojxkku7pxA")

  (def token (last  (clojure.string/split (str "Token " token-auth )  #" " )))

  (type token)
  (jwt/unsign token secret)

  )
(defn restrict-authenticated
  [handler]
  (fn [request]

    ;(println "restrict-authenticated handler" handler)
    ;(println request)
    ;(println "keys" (keys (request :headers)) )
    ;(println "Authorization" ((request :headers) "authorization") )

    (if (authenticated? request)
      (handler request)
      {:status 401 :body {:error "Unauthorized"}})))

(comment



  (let [{:keys [identity] }  {:identity {:id 1  }} ]

    (println  (get identity :id ))
    (println  (get identity :isd  false))
    )
  )

(defn is-admin [{:keys [identity] } ]
  (get identity :admin false)
  )
(defn restrict-admin
  [handler]
  (fn [request]

    ;(println "restrict-authenticated handler" handler)
    ;(println request)
    ;(println "keys" (keys (request :headers)) )
    ;(println "Authorization" ((request :headers) "authorization") )

    (if (and (authenticated? request) (is-admin request))
      (handler request)
      {:status 401 :body {:error "User is not admin"}})))

