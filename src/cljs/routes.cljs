(ns cljs.routes
  (:require [cljs.reagent_react_router.core :refer [RouteHandler NotFound run-router defroutes]])
  (:use [cljs.components.main :only [main-page]]
        [cljs.components.about :only [about-page]]))

(defn container [route]
  [:div.content
   [RouteHandler route]])

(def routes
  (defroutes [:route "" container
              [:route "/" main-page]
              [:route "about" about-page]
              [:not-found main-page]]))

(run-router (js/document.getElementById "react-content") routes)
