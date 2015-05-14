(ns kipsufi.views.list
  (:require [kipsufi.views.layout :as layout]
            [hiccup.core :refer [h]]))

(defn show [title content]
  (layout/common title
                 [:col {:class "col-md-12"}]))
