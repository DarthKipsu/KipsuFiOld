(ns kipsufi.web
  (:require [clojure.data.json :as json]
            [compojure.core :refer :all]
            [compojure.route :as route]
            [kipsufi.api :as api]
            [kipsufi.api_converter :as api->hiccup]
            [kipsufi.database :as db]
            [kipsufi.views.about :as about]
            [kipsufi.views.algorithms :as algorithms]
            [kipsufi.views.articles :as articles]
            [kipsufi.views.datastructures :as datastructures]
            [kipsufi.views.layout :as page]
            [kipsufi.views.main :as main]
            [kipsufi.views.projects :as projects]
            [kipsufi.views.show :as show]
            [ring.adapter.jetty :as jetty]
            [ring.middleware.session :as session])
  (:use [ring.middleware.json :refer :all]
        [ring.middleware.keyword-params :only [wrap-keyword-params]]
        [ring.middleware.params :only [wrap-params]])
  (:gen-class))

(defn ^:private json-response [content]
  {:status 200
   :headers {"Content-Type" "application/json"}
   :body (json/write-str content)})

(defn ^:private redirect-response [url]
  {:status 302
   :headers {"Location" url}
   :body ""})

(defn ^:private check-session [session]
  (if (:user session)
    {:body (str "Session set for " (:user session))}
    {:body "Setting session"
     :session (assoc session :user "Verna")}))

(defroutes www-routes
           (GET "/" []
                (page/common (main/wrapper (api->hiccup/as-list (api/recent 'kipsufi.database)))
                             main/title))
           (GET "/articles" []
                (page/common (articles/wrapper (api->hiccup/as-list (api/articles 'kipsufi.database)))
                             (str main/title " - " articles/title)))
           (GET "/articles/:item" [item]
                (page/common (show/article (api/article 'kipsufi.database item))
                             (str main/title " - " item)))
           (GET "/algorithms" []
                (page/common (algorithms/wrapper (api->hiccup/as-list (api/algorithms 'kipsufi.database)))
                             (str main/title " - " algorithms/title)))
           (GET "/algorithms/:item" [item]
                (page/common (show/algorithm-datastructure (api/algorithm 'kipsufi.database item))
                             (str main/title " - " item)))
           (GET "/datastructures" []
                (page/common (datastructures/wrapper (api->hiccup/as-list (api/datastructures 'kipsufi.database)))
                             (str main/title " - " datastructures/title)))
           (GET "/datastructures/:item" [item]
                (page/common (show/algorithm-datastructure (api/datastructure 'kipsufi.database item))
                             (str main/title " - " item)))
           (GET "/work" []
                (page/common (projects/wrapper (api->hiccup/as-list (api/projects 'kipsufi.database)))
                             (str main/title " - " projects/title)))
           (GET "/work/:item" [item]
                (page/common (show/project (api/project 'kipsufi.database item))
                             (str main/title " - " item)))
           (GET "/about" []
                (page/common (about/wrapper)
                             (str main/title " - " about/title)))

           (GET "/loves-me-not/" []
                (redirect-response "/projects/loves-me-not/"))
           (GET "/GhostStory/" []
                (redirect-response "/projects/GhostStory/"))
           (GET "/Laivanupotus/" []
                (redirect-response "/projects/Laivanupotus/"))
           (GET "/EclipseCalculator/" []
                (redirect-response "/projects/EclipseCalculator/")))

(defroutes api-routes
           (GET "/admin" {session :session}
                (check-session session))
           (GET "/api" []
                (json-response (api/index)))
           (GET "/api/recent" []
                (json-response (api/recent 'kipsufi.database)))
           (GET "/api/algorithms" []
                (json-response (api/algorithms 'kipsufi.database)))
           (GET "/api/datastructures" []
                (json-response (api/datastructures 'kipsufi.database)))
           (GET "/api/projects" []
                (json-response (api/projects 'kipsufi.database)))
           (GET "/api/articles" []
                (json-response (api/articles 'kipsufi.database)))
           (GET "/api/algorithms/:algorithm-name" [algorithm-name]
                (json-response (api/algorithm 'kipsufi.database algorithm-name)))
           (GET "/api/datastructures/:datastructure-name" [datastructure-name]
                (json-response (api/datastructure 'kipsufi.database datastructure-name)))
           (GET "/api/projects/:project-name" [project-name]
                (json-response (api/project 'kipsufi.database project-name)))
           (GET "/api/articles/:article-name" [article-name]
                (json-response (api/article 'kipsufi.database article-name)))
           (route/resources "/")
           (route/not-found "Not Found"))

(def app
  (routes www-routes 
          (-> api-routes
            wrap-json-response 
            wrap-json-params 
            wrap-json-body
            wrap-keyword-params
            wrap-params
            session/wrap-session)))

(defn -main
  [& [port]]
  (let [port (Integer. (or port
                           (System/getenv "PORT")
                           51236))]
    (jetty/run-jetty #'app {:port  port
                            :join? false})))
