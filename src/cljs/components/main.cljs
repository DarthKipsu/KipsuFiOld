(ns cljs.components.main
  (:require [cljs.reagent_react_router.core :refer [RouteHandler]])
  (:use [cljs.components.list :only [list-items load-items]]))

(defn main-page [route]
  (load-items "api/recent")
  [:div.col-md-12
   [:p.intro "Hi, I'm Verna Koskinen. In December 2013 I started my journey as a padawan programmer. I'm constantly thriving to be better as a programmer as well as a person. I want to create well crafted, clean and functional code — that way the application will have beauty from the inside out."]
   [:p "On this site I've collected articles I've written of subjects I've found most interesting throughout my studies or which I've felt I need to study more by writing about them, and also photos I've taken from my travels."]
   [:h3 "Recent articles"]
   [list-items]
   [RouteHandler route]])
