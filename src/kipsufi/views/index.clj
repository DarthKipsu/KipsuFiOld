(ns kipsufi.views.index
  (:require [kipsufi.views.layout :as layout]
            [hiccup.core :refer [h]]))

(defn show []
  (layout/common "Darth.Kipsu.fi"
                 [:col {:class "col-md-12"}
                  [:p "Welcome!"]
                  [:p [:a {:href "algorithms"} "Algorithms"]]
                  [:p [:a {:href "datastructures"} "Datastructures"]]]))
