(ns cljs.components.projects
  (:require [cljs.reagent_react_router.core :refer [RouteHandler]])
  (:use [cljs.api_connector :only [list-algorithms]]))

(defn project-page [route]
  [:div.col-md-12
   [:h1 "Projects"]
   [:p.intro "Creating personal projects in new languages or consepts is really the best way to learn. I try work on project or two all the time, to keep enhancing my programming skills and better understand the consepts I'm studying."]
   ;[list-algorithms]
   [RouteHandler route]])
