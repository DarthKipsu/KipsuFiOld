(ns kipsufi.features.about
  (:use [kerodon.core]
        [kerodon.test]
        [clojure.test])
  (:require [kipsufi.web :refer [app db api-routes]]))

(println "Running 5 feature assertions for about page")

(deftest display-about-page
  (-> (session app)
    (visit "/about")
    (has (status? 200)
         "about page is found")
    (within [:h1] (has (some-text? "About Me")
         "about page has about me title"))
    (within [:p] (has (some-text? "Verna Koskinen")
         "about page displays my name"))
    (within [:p] (has (some-text? "darth.kipsu@gmail.com")
         "about page displays my email address"))
    (has (link? :href "//github.com/DarthKipsu/KipsuFi")
         "about page displays link to the site github repository")))
