(ns kipsufi.views.show)

(defn ^:private list-all [title items link]
  [:div.col-md-4
   [:p [:strong title]]
   (map-indexed
     (fn [index item] [:span
                       (if (pos? index) ", ")
                       (if link [:a {:href (str link item)} item]
                         item)])
     items)])

(defn article [item]
  [:div
   [:div.img-bg [:img.face-big {:src (str "/images/faces_big/" (:name item) ".png")}]]
   [:div.print
    [:div.intro (:description item)]
    [:div.col-md-12 (:content item)]]
   [:div.col-md-12.space]])

(defn algorithm-datastructure [item]
  [:div
   [:div.img-bg [:img.face-big {:src (str "/images/faces_big/" (:name item) ".png")}]]
   [:div.print
    [:div.intro (:description item)]
    [:div.col-md-12
     (if (:datastructures item)
       (list-all "Datastructures:" (:datastructures item) "/datastructures/"))
     (list-all "Advantages:" (:advantages item) nil)
     (list-all "Disadvantages:" (:disadvantages item) nil)]
    [:div.col-md-12.space]
    [:div.col-md-12 (:content item)]]
   [:div.col-md-12.space]])

(defn project [item]
  [:div
   [:div.img-bg [:img.face-big {:src (str "/images/faces_big/" (:name item) ".png")}]]
   [:h1 (:name item)]
   [:div.print
    [:div.intro (:description item)]
    [:div.col-md-12
     (list-all "Languages: " (:languages item) nil)
     [:div.col-md-4 [:p [:strong "Link:"]]
      [:p [:a {:href (:link_url item)} (:link_name item)]]]
     [:div.col-md-4 [:p [:strong "Launched:"]]
      [:p (:launched item)]]]
    [:div.col-md-12
     (:content item)
     [:div.col-md-12 [:h3 "Comments:"]]
     (:comments item)
     [:div.col-md-12 [:h3 "What I could have done better:"]]
     (:better_done item)
     [:div.col-md-12 [:h3 "Lesson learned:"]]
     (:lesson item)]]
   [:div.col-md-12.space]])
