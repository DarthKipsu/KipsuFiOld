(ns kipsufi.web
  (:require [clojure.data.json :as json]
            [compojure.core :refer :all]
            [compojure.route :as route]
            [kipsufi.api :as api]
            [kipsufi.api_converter :as api->hiccup]
            [kipsufi.database :as database]
            [kipsufi.db-mock :as db_mock]
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
  (:use [clojure.java.io :only [resource]]
        [ring.middleware.json :refer :all]
        [ring.middleware.keyword-params :only [wrap-keyword-params]]
        [ring.middleware.params :only [wrap-params]])
  (:refer-clojure :exclude [update]) 
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

(defn config [] (try (load-file (.getFile (resource "config.clj")))
                  (catch Exception e {:database "prod"})))

(def mem-config (memoize config))

(defn db []
  (println "Using database " (:database (mem-config)))
  (if (= "dev" (:database (mem-config)))
    'kipsufi.db-mock
    'kipsufi.database))

(defroutes www-routes
    (GET "/" []
         (page/common (main/wrapper (api->hiccup/as-list (api/recent (db))))
                      main/title))
    (GET "/articles" []
         (page/common (articles/wrapper (api->hiccup/as-list (api/articles (db))))
                      (str main/title " - " articles/title)))
    (GET "/articles/:item" [item]
         (page/common (show/article (api/article (db) item))
                      (str main/title " - " item)))
    (GET "/algorithms" []
         (page/common (algorithms/wrapper (api->hiccup/as-list
                                            (api/algorithms (db))))
                      (str main/title " - " algorithms/title)))
    (GET "/algorithms/:item" [item]
         (page/common (show/algorithm-datastructure (api/algorithm (db) item))
                      (str main/title " - " item)))
    (GET "/datastructures" []
         (page/common (datastructures/wrapper (api->hiccup/as-list
                                                (api/datastructures (db))))
                      (str main/title " - " datastructures/title)))
    (GET "/datastructures/:item" [item]
         (page/common (show/algorithm-datastructure (api/datastructure (db) item))
                      (str main/title " - " item)))
    (GET "/work" []
         (page/common (projects/wrapper (api->hiccup/as-list (api/projects (db))))
                      (str main/title " - " projects/title)))
    (GET "/work/:item" [item]
         (page/common (show/project (api/project (db) item))
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
         (json-response (api/recent (db))))
    (GET "/api/algorithms" []
         (json-response (api/algorithms (db))))
    (GET "/api/datastructures" []
         (json-response (api/datastructures (db))))
    (GET "/api/projects" []
         (json-response (api/projects (db))))
    (GET "/api/articles" []
         (json-response (api/articles (db))))
    (GET "/api/algorithms/:algorithm-name" [algorithm-name]
         (json-response (api/algorithm (db) algorithm-name)))
    (GET "/api/datastructures/:datastructure-name" [datastructure-name]
         (json-response (api/datastructure (db) datastructure-name)))
    (GET "/api/projects/:project-name" [project-name]
         (json-response (api/project (db) project-name)))
    (GET "/api/articles/:article-name" [article-name]
         (json-response (api/article (db) article-name)))

    (GET "/api/photography" []
         (json-response (api/photography)))
    (GET "/api/photography/:category" [category]
         (json-response (api/photography category)))

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
    (jetty/run-jetty app {:port  port
                          :join? false})))
