(ns kipsufi.views.photo.galleries)

; PRIVATE

(defn single-item [n image]
  [:div.gallery-selector
   [:img {:src (:url image) :data-order (inc n)}]
   [:p (:description image)]
   [:div.next ">"]
   [:div.previous "<"]])

(defn single-thumb [n image]
  [:img.thumb {:src (:thumb image) :data-order (inc n)}])

(defn as-list [content]
  (map-indexed single-item content))

(defn thumbnails [content]
  (map-indexed single-thumb content))

; PUBLIC

(defn wrapper [content]
  [:section.photography
   [:div (:gallery (first content))]
   [:div.faces (as-list content)]
   [:div.thumbnails (thumbnails content)]])
