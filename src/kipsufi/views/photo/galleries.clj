(ns kipsufi.views.photo.galleries)

; PRIVATE

(defn single-item [image]
  [:div
   [:img {:src (:url image)}]
   [:p (:description image)]])

(defn as-list [content]
  (map single-item content))

; PUBLIC

(defn wrapper [content]
  [:div
   [:p (:gallery (first content))]
   (as-list content)])
