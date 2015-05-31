(ns cljs.components.show
  (:require [reagent.core :as reagent :refer [atom]]
            [cljs.reagent_react_router.core :refer [RouteHandler]]
            [ajax.core :refer [GET POST]]))

(def item (atom {}))

(defn load-item [url]
  (js/console.log "url:")
  (js/console.log url)
  (GET url {:response-format :json
            :keywords? true
            :handler (fn [data] (reset! item data))
            :error-handler (fn [e] (js/console.log "error"))}))

(defn show-algorithm [route]
  (load-item (str "api/algorithms/" (-> route :params (.-name))))
  (show-article route))

(defn show-datastructure [route]
  (load-item (str "api/datastructures/" (-> route :params (.-name))))
  (show-article route))

(defn show-article [route]
  [:div.col-md-12
   [article]
   [RouteHandler route]])

(defn list-all [title items]
  [:div.col-md-4
   [:p [:strong title]]
   (map-indexed (fn [index item] [:p {:key index} item]) items)])

(defn article []
  [:section
   [:h1 (:name @item)]
   [:div.col-md-12.intro (:description @item)]
   [:div.col-md-12.highlight
    (if (:datastructures @item)
      [list-all "Datastructures:" (:datastructures @item)])
    [list-all "Advantages:" (:advantages @item)]
    [list-all "Disadvantages:" (:disadvantages @item)]]
   [:div.col-md-12 (:content @item)]])
