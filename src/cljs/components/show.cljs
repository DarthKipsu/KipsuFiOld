(ns cljs.components.show
  (:require [reagent.core :as reagent]))

(extend-type js/NodeList
    ISeqable
    (-seq [array] (array-seq array 0)))

(extend-type js/HTMLCollection
    ISeqable
    (-seq [array] (array-seq array 0)))

(defn is-algo-or-datas? [category]
  (or (= category :algorithm) (= category :datastructure)))

(defn set-html-from-db [html]
  [:div.col-md-12 {:dangerouslySetInnerHTML {:__html html}}])

(defn list-all [title items]
  [:div.col-md-4
   [:p [:strong title]]
   (map-indexed
     (fn [index item] [:span {:key index} (if (pos? index) ", ") item])
     items)])

(defn show-algorithm-datastructure [item]
  [:div
   [:div.img-bg [:img {:src (str "images/faces_big/" (:name item) ".png") :class "face-big"}]]
   [:h1 (:name item)]
   [:div.print
    [set-html-from-db (:description item)]
    [:div.col-md-12
     (if (:datastructures item)
       [list-all "Datastructures:" (:datastructures item)])
     [list-all "Advantages:" (:advantages item)]
     [list-all "Disadvantages:" (:disadvantages item)]]
    [:div.col-md-12.space]
    [set-html-from-db (:content item)]]
    [:div.col-md-12.space]])

(defn show-project [item]
  [:div
   [:div.img-bg [:img {:src (str "images/faces_big/" (:name item) ".png") :class "face-big"}]]
   [:h1 (:name item)]
   [:div.print
    [:div.intro
     [set-html-from-db (:description item)]]
    [:div.col-md-12
     [list-all "Languages: " (:languages item)]
     [:div.col-md-4 [:p [:strong "Link:"]]
      [:p [:a {:href (:link_url item)} (:link_name item)]]]
     [:div.col-md-4 [:p [:strong "Launched:"]]
      [:p (:launched item)]]]
    [:div.col-md-12
     [set-html-from-db (:content item)]
     [:div.col-md-12 [:h3 "Comments:"]]
     [set-html-from-db (:comments item)]
     [:div.col-md-12 [:h3 "What I could have done better:"]]
     [set-html-from-db (:better_done item)]
     [:div.col-md-12 [:h3 "Lesson learned:"]]
     [set-html-from-db (:lesson item)]]]
    [:div.col-md-12.space]])

(defn show-article [category item]
  (cond
    (not (:name item)) [:div]
    (is-algo-or-datas? category) [show-algorithm-datastructure item]
    (= category :project) [show-project item]))
