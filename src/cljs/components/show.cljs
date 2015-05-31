(ns cljs.components.show)

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
  [:section
   [:h1 (:name item)]
   [:div.col-md-12.intro (:description item)]
   [:div.col-md-12.highlight
    (if (:datastructures item)
      [list-all "Datastructures:" (:datastructures item)])
    [list-all "Advantages:" (:advantages item)]
    [list-all "Disadvantages:" (:disadvantages item)]]
   [:div.col-md-12 (:content item)]])

(defn show-project [item]
  [:section
   [:h1 (:name item)]
   [:div.intro
    [set-html-from-db (:description item)]]
   [:div.col-md-12.highlight
    [:img.col-md-8 {:src (str "images/projects/" (:name item) ".png")}]
    [list-all "Languages: " (:languages item)]
    [:div.col-md-4 [:br] [:strong "Link:"]
     [:p [:a {:href (:link_url item)} (:link_name item)]]]]
   [set-html-from-db (:content item)]
   [:div.col-md-12 [:strong "Comments:"]]
   [set-html-from-db (:comments item)]
   [:div.col-md-12 [:strong "What I could have done better:"]]
   [set-html-from-db (:better_done item)]
   [:div.col-md-12 [:strong "Lesson learned:"]]
   [set-html-from-db (:lesson item)]])

(defn show-article [category item]
  (cond
    (not (:name item)) [:div]
    (is-algo-or-datas? category) [show-algorithm-datastructure item]
    (= category :project) [show-project item]))
