(ns kipsufi.views.projects)

(def title "Projects")

(defn wrapper [articles]
  [:div.content
   [:div.img-bg [:img {:src "images/faces_big/projects.png" :class "face-big"}]]
   [:h1.title "Projects"]
   [:div.print
    [:div.col-md-12
     [:p.intro "Creating personal projects in new languages or consepts is really the best way to learn. I try work on project or two all the time, to keep enhancing my programming skills and better understand the consepts I'm studying."]
     [:h3 "Recent projects"]
     articles]
    [:div.col-md-12.space]]])
