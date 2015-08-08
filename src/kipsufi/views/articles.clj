(ns kipsufi.views.articles)

(def title "Articles")

(defn wrapper [articles]
  [:div.content
   [:div.img-bg [:img.face-big {:src "images/faces_big/articles.png"}]]
   [:h1.title.black title]
   [:div.print
    [:div.col-md-12
     [:p.intro "Here's a list of articles I've written about programming or other interesting topics."]]
    articles
    [:div.col-md-12.space]]])
