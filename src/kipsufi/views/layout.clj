(ns kipsufi.views.layout
  (:require [hiccup.page :as h]))

(defn common []
  (h/html5
    [:head
     [:meta {:charset "utf-8"}]
     [:title "darth.kipsu.fi"]
     (h/include-css "//maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap.min.css")
     (h/include-css "//fonts.googleapis.com/css?family=Lobster")
     (h/include-css "//fonts.googleapis.com/css?family=Droid+Sans")
     (h/include-css "css/style.css")]
    [:body
     [:div {:class "layout-pic"}
      [:img {:src "images/jolla.jpg" :id "jolla"}]]
     [:div {:style "height: 70px;"}
      [:nav {:class "navbar navbar-default affix-top" :data-spy "affix" :data-offset-top "250"}
       [:div {:class "container-fluid"}
        [:div {:class "navbar-header"}
         [:button {:type "button" :class "navbar-toggle collapsed" :data-toggle "collapse" :data-target "#main-navbar"}
          [:span {:class "sr-only"}"Toggle navigation"]
          [:span {:class "icon-bar"}]
          [:span {:class "icon-bar"}]
          [:span {:class "icon-bar"}]]]
        [:div {:class "collapse navbar-collapse" :id "main-navbar"}
        [:ul {:class "nav navbar-nav"}
         [:li [:a {:href "#/"} "Home"]]
         [:li {:class "divider hidden-xs"}]
         [:li [:a {:href "#/articles"} "Articles"]]
         [:li [:a {:href "#/algorithms"} "Algorithms"]]
         [:li [:a {:href "#/datastructures"} "Datastructures"]]]
        [:ul {:class "nav navbar-nav navbar-right"}
         [:li [:a {:href "#/projects"} "Projects"]]
         [:li [:a {:href "#/about"} "About"]]
         [:li {:class "space"}]]]]]]
     [:section {:id "react-content"}]
     [:footer
      [:div {:class "col-md-12"} "Verna Koskinen - darth.kipsu@gmail.com"]]
     (h/include-js "//ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js")
     (h/include-js "//maxcdn.bootstrapcdn.com/bootstrap/3.3.4/js/bootstrap.min.js")
     (h/include-js "js/script.js")]))
