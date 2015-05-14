(ns kipsufi.web
    (:require [compojure.core :refer [defroutes GET]]
              [ring.adapter.jetty :as ring]
              [hiccup.page :as page]))

(defroutes routes
           (GET "/" [] (page/html5
                         [:head
                          [:title "Hello World"]]
                         [:body
                          [:div {:id "content"} "Hello World"]])))

(defn -main []
    (ring/run-jetty #'routes {:port 8080 :join? false}))
