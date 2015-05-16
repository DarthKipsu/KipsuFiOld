(ns kipsufi.views.list
  (:require [kipsufi.views.layout :as layout]
            [hiccup.core :refer [h]]))

(defn- algorithm [obj]
  (let [aName (:algorithm_name obj)]
    [:p [:a {:href (str "algorithms/" aName)} aName]]
    [:p (str (:json_aqq obj))]
    [:p (str (:json_agg_2 obj))]))


(defn show [title content]
  (layout/common title
                 [:col {:class "col-md-12"} (str content)]
                 [:col {:class "col-md-12"} (algorithm (first content))]))
