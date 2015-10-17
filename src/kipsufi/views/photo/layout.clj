(ns kipsufi.views.photo.layout
  (:require [hiccup.page :as h]))

(defn common [content title]
  (h/html5
    [:head
     [:meta {:charset "utf-8"}]
     [:title title]
     (h/include-css "//maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap.min.css")
     (h/include-css "//fonts.googleapis.com/css?family=Lobster")
     (h/include-css "//fonts.googleapis.com/css?family=Droid+Sans")
     (h/include-css "/css/style.css")]
    [:body {:style "background-color: white"}
     [:div#photo-container
      [:nav.photography
        [:div [:a {:href "//darth.kipsu.fi"} "darth.kipsu.fi"]]
        [:div#ctm
         [:a#c {:href "/photography/camping"} "C"] " "
         [:a#t {:href "/photography/travel"} "T"] " "
         [:a#m {:href "/photography/moments"} "M"]]]
      content
      [:footer.photography "Verna Koskinen - darth.kipsu@gmail.com"]]
     (h/include-js "//ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js")
     (h/include-js "//maxcdn.bootstrapcdn.com/bootstrap/3.3.4/js/bootstrap.min.js")
     (h/include-js "/js/script.js")]))
