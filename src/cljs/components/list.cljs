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

(defn display-in-p [index item]
  [:p {:key index} item])

(defn single-item [index item]
  ^{:key index}
  [:div.col-md-6
   [:div.item-selector
    {:on-click #(js/console.log (str (:group item) "/" (:name item)))}
    [:div.list-item.head
     [:p.item-date (:edited item)]
     [:h3 (:name item)]]
    [:div.list-item.content
     (if (:datastructures item)
       [:div.col-md-12
        [:p [:strong "Datastructures: "]
        (map-indexed (fn [i item] [:span {:key i} item]) (:datastructures item))]])
     [:div.col-md-12.highlight
      [:div.col-md-6
       [:p [:strong "Advantages:"]]
       (map-indexed display-in-p (:advantages item))]
      [:div.col-md-6
       [:p [:strong "Disadvantages:"]]
       (map-indexed display-in-p (:advantages item))]]
     [:div.col-md-12 (:description item)]]]])

(defn list-items []
  [:div (map-indexed single-item @items)])
