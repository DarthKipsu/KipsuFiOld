(ns cljs.components.algo
  (:require [cljs.reagent_react_router.core :refer [RouteHandler]])
  (:use [cljs.components.list :only [list-items load-items]]))

(defn algo-page [route]
  (load-items "api/algorithms")
  [:div.col-md-12
   [:h1 "Algorithms"]
   [:p.intro "I got interested in algorithms during my first months at the University of Helsinki, during the fabulous algorithms and datastructures course I had scheduled for my first semester. An algorithm is a method of representing instructions for operating a set of precise steps to solve a problem. They are most important in computer science, mathematics and everyday life, needed for data processing and calculations."]
   [list-items]
   [RouteHandler route]])
