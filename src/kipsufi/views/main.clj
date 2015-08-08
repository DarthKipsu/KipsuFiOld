(ns kipsufi.views.main)

(def title "darth.kipsu.fi")

(defn content [articles]
  [:div
   [:section.img-bg
    [:img.face-big {:src "images/faces_big/main.png"}]]
   [:h1.title title]
   [:div.print
    [:h3 "Recent articles"]]
   articles
   [:div.col-md-12.space]])
