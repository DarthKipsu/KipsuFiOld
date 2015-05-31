(ns cljs.components.show)

(defn list-all [title items]
  [:div.col-md-4
   [:p [:strong title]]
   (map-indexed (fn [index item] [:p {:key index} item]) items)])

(defn show-article [item]
  [:section
   [:h1 (:name item)]
   [:div.col-md-12.intro (:description item)]
   [:div.col-md-12.highlight
    (if (:datastructures item)
      [list-all "Datastructures:" (:datastructures item)])
    [list-all "Advantages:" (:advantages item)]
    [list-all "Disadvantages:" (:disadvantages item)]]
   [:div.col-md-12 (:content item)]])
