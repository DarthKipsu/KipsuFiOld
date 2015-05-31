(ns cljs.components.list)

(defn display-in-p [index item]
  [:p {:key index} item])

(defn single-item [index item]
  ^{:key index}
  [:div.col-md-6
   [:div.item-selector
    {:on-click #(set! js/window.location (str "#/" (:group item) "/" (:name item)))}
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
