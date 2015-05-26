(ns cljs.components.about
  (:require [cljs.reagent_react_router.core :refer [RouteHandler]]))

(defn about-page [route]
  [:div "About world!"
   [RouteHandler route]])
