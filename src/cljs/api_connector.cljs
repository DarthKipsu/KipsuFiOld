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
  [:div
   [:div.col-md-6 (map-indexed single-item (take-nth 2 @items))]
   [:div.col-md-6 (map-indexed single-item (take-nth 2 (rest @items)))]])

(defn ^:private display-item
  "Display a single article."
  [category]
  [show-article category @items])

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
  [display-list])

(defn list-articles
  "Fetch and display a list of articles from the api."
  []
  (load-items "api/articles")
  [display-list])

(defn show-algorithm
  "Fetch and display a single algorithm from the api by name in route params."
  [route]
  (load-items (str "api/algorithms/" (-> route :params (.-name))))
  [:div.content [display-item :algorithm] [RouteHandler route]])

(defn show-datastructure
  "Fetch and display a single datastructure from api by name in route params."
  [route]
  (load-items (str "api/datastructures/" (-> route :params (.-name))))
  [:div.content [display-item :datastructure] [RouteHandler route]])

(defn show-project
  "Fetch and display a single project from api by name in route params."
  [route]
  (load-items (str "api/projects/" (-> route :params (.-name))))
  [:div.content [display-item :project] [RouteHandler route]])

(defn show-art
  "Fetch and display a single article from api by name in route params."
  [route]
  (load-items (str "api/articles/" (-> route :params (.-name))))
  [:div.content [display-item :article] [RouteHandler route]])
