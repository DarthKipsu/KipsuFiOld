(ns cljs.components.articles
  (:require [cljs.reagent_react_router.core :refer [RouteHandler]])
  (:use [cljs.api_connector :only [list-algorithms]]))

(defn article-page [route]
  [:div.content
   [:div.img-bg [:img {:src "images/faces_big/articles.png" :class "face-big"}]]
   [:h1.title.black "Articles"]
   [:div.print
    [:div.col-md-12
     [:p.intro "Here's a list of articles I've written about programming or other interesting topics."]]
    ;[list-algorithms]
    [:div.col-md-12.space]
    [RouteHandler route]]])
