(ns kipsufi.views.photo.categories)

; PRIVATE

(defn single-item [n gallery]
  [:div.gallery-selector
   [:a {:href (str "/photography/" (:group gallery) "/" (:name gallery))}
   [:header [:div (:name gallery)] [:div (:date gallery)]]
   [:img {:src (str "/images/galleries/" (:name gallery) ".png")
          :data-order (inc n)}]]])

(defn single-thumb [n gallery]
  [:img.thumb {:src (str "/images/galleries/" (:name gallery) "-small.png")
               :data-order (inc n)}])

(defn as-list [content]
  (map-indexed single-item content))

(defn thumbnails [content]
  (map-indexed single-thumb content))

; PUBLIC

(def title "Photography")

(defn index []
  [:section.photography
   [:a {:href "/photography/camping"}
    [:img {:src "/images/photography/camping.png"}]] " "
   [:a {:href "/photography/travel"}
    [:img {:src "/images/photography/travel.png"}]] " "
   [:a {:href "/photography/moments"}
    [:img {:src "/images/photography/moments.png"}]]])

(defn wrapper [content]
  [:section.photography
   [:div.faces (as-list content)]
   [:div.thumbnails (thumbnails content)]])
