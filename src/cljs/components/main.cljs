(ns cljs.components.main
  (:require [cljs.reagent_react_router.core :refer [RouteHandler]])
  (:use [cljs.api_connector :only [list-recent]]))

(defn main-page [route]
  [:div.content
   [:div.img-bg [:img {:src "images/faces_big/main.png" :class "face-big"}]]
   [:h1.title "darth.kipsu.fi"]
   [:div.print
    [:h3 "Recent articles"]
    [list-recent]]
   [:div.col-md-12.space]
   [RouteHandler route]])
