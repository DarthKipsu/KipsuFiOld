(ns kipsufi.views.photo.galleries)

; PRIVATE

(defn single-item [image]
  [:div.gallery-selector
   [:img {:src (:url image)}]
   [:p (:description image)]
   [:div.next ">"]
   [:div.previous "<"]])

(defn single-thumb [n image]
  [:img.thumb {:src (:thumb image) :data-order (inc n)}])

(defn as-list [content]
  (map single-item content))

(defn thumbnails [content]
  (map-indexed single-thumb content))

; PUBLIC

(defn wrapper [content]
  [:section.photography
   [:div (:gallery (first content))]
   [:div.thumbnails (thumbnails content)]
   [:div.faces (as-list content)]])
