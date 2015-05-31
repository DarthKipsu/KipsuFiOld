(ns cljs.components.articles
  (:require [cljs.reagent_react_router.core :refer [RouteHandler]])
  (:use [cljs.api_connector :only [list-algorithms]]))

(defn article-page [route]
  [:div.col-md-12
   [:h1 "Articles"]
   [:p.intro "Here's a list of articles I've written about programming or other interesting topics."]
   ;[list-algorithms]
   [RouteHandler route]])
