(ns cljs.api_connector
  (:require [reagent.core :as reagent :refer [atom]]
            [ajax.core :refer [GET POST]]
            [cljs.reagent_react_router.core :refer [RouteHandler]])
  (:use [cljs.components.list :only [single-item]]
        [cljs.components.show :only [show-article]]))

; ---- PRIVATE ---- ;

(def ^:private items (atom []))

(defn ^:private load-items
  "Load item(s) from given url and reset atom to contain the item(s)."
  [url]
  (GET url {:response-format :json
            :keywords? true
            :handler (fn [data] (reset! items data))
            :error-handler (fn [e] (js/console.log "error"))}))

(defn ^:private display-list
  "Display a list of articles."
  []
  [:div (map-indexed single-item @items)])

(defn ^:private display-item
  "Display a single article."
  []
  [show-article @items])

; ---- PUBLIC ---- ;

(defn list-recent
  "Fetch and display most recent articles from the api."
  []
  (load-items "api/recent")
  [display-list])

(defn list-algorithms
  "Fetch and display a list of algorithms from the api."
  []
  (load-items "api/algorithms")
  [display-list])

(defn list-datastructures
  "Fetch and display a list of datastructures from the api."
  []
  (load-items "api/datastructures")
  [display-list])

(defn list-projects
  "Fetch and display a list of projects from the api."
  []
  (load-items "api/projects")
  (js/console.log "projektit")
  (js/console.log items)
  [display-list])

(defn show-algorithm
  "Fetch and display a single algorithm from the api by name in route params."
  [route]
  (load-items (str "api/algorithms/" (-> route :params (.-name))))
  [:div.col-md-12 [display-item] [RouteHandler route]])

(defn show-datastructure
  "Fetch and display a single datastructure from api by name in route params."
  [route]
  (load-items (str "api/datastructures/" (-> route :params (.-name))))
  [:div.col-md-12 [display-item] [RouteHandler route]])
