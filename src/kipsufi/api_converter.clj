(ns kipsufi.api_converter)

(defn display-in-line [index item]
    [:span {:key index}
        (if (pos? index) ", ") item])

(defn list-all [title items line-break?]
    [:p [:strong title] (if line-break? [:br])
        (map-indexed display-in-line items)])

(defn item-contents [item]
  (let [advantages (:advantages item)
        disadvantages (:disadvantages item)
        languages (:languages item)]
    [:div.list-item.content
     [:div.col-md-12
      (if (or advantages disadvantages)
        [:div 
         [:div.col-md-6 (list-all "Advantages: " advantages true)]
         [:div.col-md-6 (list-all "Disadvantages: " disadvantages true)]])
      (if languages
        [:div.col-md-12 (list-all "Languages: " languages true)])]
     [:div.col-md-12 (:description item)]]))

(defn ^:private single-item [index item]
  [:div
   [:div.item-selector
    ;{:on-click #(set! js/window.location (str "#/" (:group item) "/" (:name item)))}
    [:div.list-item.head
     [:p {:class "info"}
      [:span.item-type (:group item)]
      [:span.item-date (:edited item)]]
     [:img {:src (str "images/faces_small/" (:name item) ".png") :class "face-small"}]
     [:h3 (:name item)]]
    (item-contents item)]])

(defn as-list [content]
  [:div
   [:div.col-md-6 (map-indexed single-item (take-nth 2 content))]
   [:div.col-md-6 (map-indexed single-item (take-nth 2 (rest content)))]])
