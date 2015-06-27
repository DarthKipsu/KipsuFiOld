(ns kipsufi.web
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [ring.adapter.jetty :as ring]
            [clojure.data.json :as json]
            [kipsufi.views.layout :as page]
            [kipsufi.api :as api]
            [ring.adapter.jetty :as jetty])
  (:use [ring.middleware.params :only [wrap-params]]
        [ring.middleware.json :refer :all]
        [ring.middleware.keyword-params :only [wrap-keyword-params]])
  (:gen-class))

(defn ^:private json-response [content]
  {:status 200
   :headers {"Content-Type" "application/json"}
   :body (json/write-str content)})

(defn ^:private redirect [url]
  {:status 302
   :headers {"Location" url}
   :body ""})

(defroutes www-routes
           (GET "/" []
                (page/common))
           (GET "/loves-me-not" []
                (redirect "/projects/loves-me-not"))
           (GET "/GhostStory" []
                (redirect "/projects/GhostStory"))
           (GET "/Laivanupotus" []
                (redirect "/projects/Laivanupotus"))
           (GET "/EclipseCalculator" []
                (redirect "/projects/EclipseCalculator")))

(defroutes api-routes
           (GET "/api" []
                (json-response (api/index)))
           (GET "/api/recent" []
                (json-response (api/recent)))
           (GET "/api/algorithms" []
                (json-response (api/algorithms)))
           (GET "/api/datastructures" []
                (json-response (api/datastructures)))
           (GET "/api/projects" []
                (json-response (api/projects)))
           (GET "/api/articles" []
                (json-response (api/articles)))
           (GET "/api/algorithms/:algorithm-name" [algorithm-name]
                (json-response (api/algorithm algorithm-name)))
           (GET "/api/datastructures/:datastructure-name" [datastructure-name]
                (json-response (api/datastructure datastructure-name)))
           (GET "/api/projects/:project-name" [project-name]
                (json-response (api/project project-name)))
           (GET "/api/articles/:article-name" [article-name]
                (json-response (api/article article-name)))
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

(defn -main
  [& [port]]
  (let [port (Integer. (or port
                           (System/getenv "PORT")
                           51236))]
    (jetty/run-jetty #'app {:port  port
                            :join? false})))
