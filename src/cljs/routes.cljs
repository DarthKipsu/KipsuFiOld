(ns cljs.routes
  (:require [cljs.reagent_react_router.core :refer [RouteHandler NotFound run-router defroutes]])
  (:use [cljs.components.main :only [main-page]]
        [cljs.components.algo :only [algo-page]]
        [cljs.components.datas :only [datas-page]]
        [cljs.components.articles :only [article-page]]
        [cljs.components.projects :only [project-page]]
        [cljs.api_connector :only [show-algorithm show-datastructure show-project]]
        [cljs.components.about :only [about-page]]))

(defn container [route]
  [:div.content
   [RouteHandler route]])

(def routes
  (defroutes [:route "" container
              [:route "/" main-page]
              [:route "/algorithms" algo-page]
              [:route "/algorithms/:name" show-algorithm]
              [:route "/datastructures" datas-page]
              [:route "/datastructures/:name" show-datastructure]
              [:route "/articles" article-page]
              [:route "/projects" project-page]
              [:route "/projects/:name" show-project]
              [:route "about" about-page]
              [:not-found main-page]]))

(run-router (js/document.getElementById "react-content") routes)
