(ns cljs.components.main
  (:require [cljs.reagent_react_router.core :refer [RouteHandler]])
  (:use [cljs.api_connector :only [list-recent]]))

(defn main-page [route]
  [:div.col-md-12
   [:div.img-bg [:img {:src "images/faces_big/main.png" :class "face-big"}]]
   [:h1.title "darth.kipsu.fi"]
   [:h3 "Recent articles"]
   [list-recent]
   [RouteHandler route]])
