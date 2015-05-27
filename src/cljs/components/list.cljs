(ns cljs.components.list
  (:require [reagent.core :as reagent :refer [atom]]
            [cljs.reagent_react_router.core :refer []]
            [ajax.core :refer [GET POST]]))

(def items (atom []))

(defn load-items [url]
  (GET url {:response-format :json
            :keywords? true
            :handler (fn [data] (reset! items (map :name data)))
            :error-handler (fn [e] (js/console.log "error"))}))

(defn indexed-list [& children]
  [:div
   (map-indexed #(with-meta %2 {:key %1}) children)])

(defn list-from [url]
  (load-items url)
  [indexed-list
   (for [item @items] [:p item])])
