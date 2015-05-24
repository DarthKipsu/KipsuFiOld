(ns kipsufi.views.layout
  (:require [hiccup.page :as h]))

(defn common []
  (h/html5
    [:head
     [:meta {:charset "utf-8"}]
     [:title "darth.kipsu.fi"]
     (h/include-css "//maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap.min.css")
     (h/include-css "css/style.css")]
    [:body {:ng-app "kipsufi"}
     [:nav {:class "navbar navbar-default"}
      [:div {:class "container-fluid"}
       [:ul {:class "nav navbar-nav"}
        [:li [:a {:ng-href "#/"} "Home"]]
        [:li [:a {:ng-href "#/algorithms"} "Algorithms"]]
        [:li [:a {:ng-href "#/datastructures"} "Datastructures"]]]]]
     [:section
      [:div {:class "col-md-10 col-md-offset-1" :ng-view ""}]]
     (h/include-js "//ajax.googleapis.com/ajax/libs/angularjs/1.3.15/angular.min.js")
     (h/include-js "//ajax.googleapis.com/ajax/libs/angularjs/1.3.15/angular-route.js")
     (h/include-js "js/script.js")]))
