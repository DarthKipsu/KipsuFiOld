(ns kipsufi.views.layout
  (:require [hiccup.page :as h]))

(defn common [title]
  (h/html5
    [:head
     [:meta {:charset "utf-8"}]
     [:title title]
     (h/include-css "https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap.min.css")]
    [:body {:ng-app "kipsufi"}
     [:div {:class "row"}
      [:div {:class "col-md-8 col-md-offset-2"}
       [:h1 title]]
      [:div {:class "col-md-10 col-md-offset-1" :ng-view ""}]]
     (h/include-js "//ajax.googleapis.com/ajax/libs/angularjs/1.3.15/angular.min.js")
     (h/include-js "//ajax.googleapis.com/ajax/libs/angularjs/1.3.15/angular-route.js")
     (h/include-js "js/script.js")]))
