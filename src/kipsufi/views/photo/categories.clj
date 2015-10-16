(ns kipsufi.views.photo.categories)

; PRIVATE

(defn single-item [gallery]
  [:div.gallery-selector
   [:a {:href (str "/photography/" (:group gallery) "/" (:name gallery))}
   [:header [:div (:name gallery)] [:div (:date gallery)]]
   [:img {:src (str "/images/galleries/" (:name gallery) ".png")}]]])

(defn single-thumb [n gallery]
  [:a {:href (str "/photography/" (:group gallery) "/" (:name gallery))}
   [:img.thumb {:src (str "/images/galleries/" (:name gallery) "-small.png")
                :data-order (inc n)}]])

(defn as-list [content]
  (map single-item content))

(defn thumbnails [content]
  (map-indexed single-thumb content))

; PUBLIC

(def title "Photography")

(defn index []
  [:section.photography
   [:a {:href "/photography/camping"} "Camping"] " "
   [:a {:href "/photography/travel"} "Travel"] " "
   [:a {:href "/photography/moments"} "Moments"]])

(defn wrapper [content]
  [:section.photography
   [:div.faces (as-list content)]
   [:div.thumbnails (thumbnails content)]])