(ns kipsufi.views.datastructures)

(def title "Datastructures")

(defn wrapper [articles]
  [:div.content
   [:div.img-bg [:img {:src "images/faces_big/datastructures.png" :class "face-big"}]]
   [:h1.title title]
   [:div.print
    [:div:col-md-12
     [:p.intro "Datastructures are as they are called, structures for holding and maintaining data. They are extremely important in writing efficient programs. Knowledge of datastructures is also most important if one wants to learn to use different algorithms efficiently."]
     articles]
    [:div.col-md-12.space]]])
