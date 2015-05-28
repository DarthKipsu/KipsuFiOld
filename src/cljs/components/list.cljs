(ns cljs.components.list
  (:require [reagent.core :as reagent :refer [atom]]
            [cljs.reagent_react_router.core :refer []]
            [ajax.core :refer [GET POST]]))

(def items (atom []))

(defn load-items [url]
  (GET url {:response-format :json
            :keywords? true
            :handler (fn [data] (reset! items data))
            :error-handler (fn [e] (js/console.log "error"))}))

(defn display-in-p [item]
  [:p item])

(defn single-item [index item]
  ^{:key index} [:div.col-md-6
                 [:div.item-selector
                  [:div.list-item.head
                   [:p.item-date (:edited item)]
                   [:h3 (:name item)]]
                  [:div.list-item.content
                   (if (:datastructures item)
                     [:div.col-md-12
                      [:p [:strong "Datastructures: "]
                       [:span (map (fn [item] item) (:datastructures item))]]])
                   [:div.col-md-12.highlight
                    [:div.col-md-6
                     [:p [:strong "Advantages:"]]
                     (map display-in-p (:advantages item))]
                    [:div.col-md-6
                     [:p [:strong "Disadvantages:"]]
                     (map display-in-p (:advantages item))]]
                   [:div.col-md-12 (:description item)]]]])

(defn list-items []
  [:div (map-indexed single-item @items)])
