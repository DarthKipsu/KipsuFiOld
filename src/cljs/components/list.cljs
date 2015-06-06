(ns cljs.components.list
  (:use [cljs.components.show :only [set-html-from-db]]))

(defn display-in-line [index item]
  [:span {:key index}
   (if (pos? index) ", ") item])

(defn list-all [title items line-break?]
  [:p [:strong title] (if line-break? [:br])
   (map-indexed display-in-line items)])

(defn item-contents [item]
  (let [datastructures (:datastructures item)
        advantages (:advantages item)
        disadvantages (:disadvantages item)
        launched (:launched item)
        languages (:languages item)]
    [:div.list-item.content
     (if datastructures
       [:div.col-md-12 [list-all "Datastructures: " datastructures false]])
     (if launched
       [:div.col-md-12 [:p [:strong "Launched: "] launched]])
     [:div.col-md-12.highlight
      (if (or advantages disadvantages)
        [:div 
         [:div.col-md-6 [list-all "Advantages: " advantages true]]
         [:div.col-md-6 [list-all "Disadvantages: " disadvantages true]]])
      (if languages
        [:div.col-md-12 [list-all "Languages: " languages true]])]
     [set-html-from-db (:description item)]]))

(defn single-item [index item]
  ^{:key index}
  [:div.col-md-6
   [:div.item-selector
    {:on-click #(set! js/window.location (str "#/" (:group item) "/" (:name item)))}
    [:div.list-item.head
     [:p.item-date (:edited item)]
     [:h3 (:name item)]]
    [item-contents item]]])
