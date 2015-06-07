(ns cljs.components.algo
  (:require [cljs.reagent_react_router.core :refer [RouteHandler]])
  (:use [cljs.api_connector :only [list-algorithms]]))

(defn algo-page [route]
  [:div.content
   [:div.img-bg [:img {:src "images/faces_big/algorithms.png" :class "face-big"}]]
   [:h1.title "Algorithms"]
   [:div.print
    [:div.col-md-12
     [:p.intro "I got interested in algorithms during my first months at the University of Helsinki, during the fabulous algorithms and datastructures course I had scheduled for my first semester. An algorithm is a method of representing instructions for operating a set of precise steps to solve a problem. They are most important in computer science, mathematics and everyday life, needed for data processing and calculations."]
     [list-algorithms]]
    [:div.col-md-12.space]
    [RouteHandler route]]])
