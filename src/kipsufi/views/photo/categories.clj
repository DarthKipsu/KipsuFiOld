(ns kipsufi.views.photo.categories)

; PRIVATE

(defn single-item [gallery]
  [:div.item-selector
   [:a {:href (str "/photography/" (:group gallery) "/" (:name gallery))}
   [:p (:name gallery)]
   [:p (:date gallery)]
   [:p (:group gallery)]
   [:p (:description gallery)]]])

(defn as-list [content]
  (map single-item content))

; PUBLIC

(def title "Photography")

(defn index []
  [:p
   [:a {:href "/photography/camping"} "Camping"] " "
   [:a {:href "/photography/travel"} "Travel"] " "
   [:a {:href "/photography/moments"} "Moments"]])

(defn wrapper [content]
  [:div (as-list content)])
