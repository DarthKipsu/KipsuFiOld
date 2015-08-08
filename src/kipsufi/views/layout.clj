(ns kipsufi.views.layout
  (:require [hiccup.page :as h]))

(defn common [content title]
  (h/html5
    [:head
     [:meta {:charset "utf-8"}]
     [:title title]
     (h/include-css "//maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap.min.css")
     (h/include-css "//fonts.googleapis.com/css?family=Lobster")
     (h/include-css "//fonts.googleapis.com/css?family=Droid+Sans")
     (h/include-css "css/style.css")]
    [:body
     [:div.nav-container
      [:nav.navbar.navbar-default.affix-top {:data-spy "affix"
                                             :data-offset-top "50"}
       [:div.container-fluid
        [:div.navbar-header
         [:button.navbar-toggle.collapsed {:type "button"
                                           :data-toggle "collapse"
                                           :data-target "#main-navbar"}
          [:span.sr-only "Toggle navigation"]
          [:span.icon-bar]
          [:span.icon-bar]
          [:span.icon-bar]]]
        [:div#main-navbar.collapse.navbar-collapse
        [:ul.nav.navbar-nav
         [:li [:a {:href "/"} "Home"]]
         [:li.divider.hidden-xs]
         [:li [:a {:href "/articles"} "Articles"]]
         [:li [:a {:href "/algorithms"} "Algorithms"]]
         [:li [:a {:href "/datastructures"} "Datastructures"]]]
        [:ul.nav.navbar-nav.navbar-right
         [:li [:a {:href "/projects"} "Projects"]]
         [:li [:a {:href "/about"} "About"]]
         [:li.space]]]]]]
     [:section#content content]
     [:footer
      [:div.col-md-12 "Verna Koskinen - darth.kipsu@gmail.com"]]
     (h/include-js "//ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js")
     (h/include-js "//maxcdn.bootstrapcdn.com/bootstrap/3.3.4/js/bootstrap.min.js")
     ]));(h/include-js "js/script.js")]))
