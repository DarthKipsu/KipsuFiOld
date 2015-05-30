(ns cljs.components.datas
  (:require [cljs.reagent_react_router.core :refer [RouteHandler]])
  (:use [cljs.components.list :only [list-items load-items]]))

(defn datas-page [route]
  (load-items "api/datastructures")
  [:div.col-md-12
   [:h1 "Datastructures"]
   [:p.intro "Datastructures are as they are called, structures for holding and maintaining data. They are extremely important in writing efficient programs. Knowledge of datastructures is also most important if one wants to learn to use different algorithms efficiently."]
   [list-items]
   [RouteHandler route]])
