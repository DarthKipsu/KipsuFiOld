(ns kipsufi.web
    (:require [compojure.core :refer [defroutes GET]]
              [ring.adapter.jetty :as ring]
              [kipsufi.views.index :as index]
              [kipsufi.views.list :as listCont]))

(defroutes routes
    (GET "/" []
         (index/show))
    (GET "/algorithms" []
         (listCont/show "Algorithms" ""))
    (GET "/datastructures" []
         (listCont/show "Datastructures" "")))

(defn -main []
    (ring/run-jetty #'routes {:port 8080 :join? false}))
