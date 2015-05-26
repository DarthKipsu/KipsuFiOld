(ns cljs.components.main
  (:require [cljs.reagent_react_router.core :refer [RouteHandler]]))

(defn main-page [route]
  [:div "Hello world!"
   [RouteHandler route]])
