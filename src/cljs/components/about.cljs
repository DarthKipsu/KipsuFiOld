(ns cljs.components.about
  (:require [cljs.reagent_react_router.core :refer [RouteHandler]]))

(defn about-page [route]
  [:div.content
   [:div.img-bg [:img {:src "images/faces_big/about.png" :class "face-big"}]]
   [:h1.title "About Me"]
   [:div.print
    [:div.col-md-12
     [:p.intro "I'm a first year computer sciences student at the Univeristy of Helsinki. I'm especially interested in finding efficient and elegant solutions to problems."]]
    [:div.col-md-6.small-font
     [:p]
     [:p [:strong "Name: "] "Verna Koskinen"]
     [:p [:strong "Email: "] "dath.kipsu@gmail.com"]
     [:p [:strong "Phone: "] "+358451277921"]
     [:p [:strong "Address: "] "Kurkimäentie 19 D 36, 00940 Helsinki, Finland"]]
    [:div.col-md-6.small-font
     [:p [:strong "Website: "] [:a {:href "//darth.kipsu.fi"} "darth.kipsu.fi"]]
     [:p [:strong "GitHub: "] [:a {:href "//github.com/DarthKipsu"} "Darth Kipsu"]]
     [:p [:strong "LinkedIn: "] [:a {:href "//www.linkedin.com/pub/verna-koskinen/85/339/a4a"} "Verna Koskinen"]]
     [:p [:strong "Facebook: "] [:a {:href "//www.facebook.com/verna.koskinen"} "Verna Koskinen"]]]
    [:div.col-md-12.space]
    [:div.col-md-12
    [:p "I have a previous degree in environmental sciences, but begun studying programming just before Christmas in 2013 and completely fell for it! I'm still exploring the field and having fun learning new things. Seems like in programming, learning new can never stop - how fun is that! I like problem solving and math, so this is definitely \"my thing\"."]
    [:p "In my free time, when not learning about programming, I like to get outside with my dog and spouse and go walking in the forest, hiking or cycling. Nature is really important to me and I think caring for the environment and spending time in the nature is one of the best ways to keep ones mind at ease."]]
    [:div.col-md-12.space]
   [RouteHandler route]]])
