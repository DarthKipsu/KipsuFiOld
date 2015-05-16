(ns kipsufi.web
    (:require [compojure.core :refer :all]
              [compojure.route :as route]
              [ring.adapter.jetty :as ring]
              [clojure.data.json :as json]
              [kipsufi.views.index :as index]
              [kipsufi.views.list :as listView]
              [kipsufi.database :as db]
              [kipsufi.api :as api])
  (:use [ring.middleware.params :only [wrap-params]]
        [ring.middleware.json :refer :all]
        [ring.middleware.keyword-params :only [wrap-keyword-params]]))

(defn json-response [content]
  (println content)
  {:status 200
   :headers {"Content-Type" "application/json"}
   :body (json/write-str content)})

(defroutes www-routes
    (GET "/" []
         (index/show))
    (GET "/algorithms" []
         (listView/show "Algorithms" ""))
    (GET "/datastructures" []
         (listView/show "Datastructures" "")))

(defroutes api-routes
    (GET "/api" []
         (json-response (db/list-algorithms)))
    (route/resources "/")
    (route/not-found "Not Found"))

(def app
  (routes www-routes 
          (-> api-routes
             wrap-json-response 
             wrap-json-params 
             wrap-json-body
             wrap-keyword-params
             wrap-params)))
