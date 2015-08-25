(ns kipsufi.features.algorithms
  (:use [kerodon.core]
        [kerodon.test]
        [clojure.test])
  (:require [kipsufi.web :refer [app db api-routes]]))

(println "Running 12 feature assertions for algorithms.")

(deftest display-algorithm-list
  (-> (session app)
    (visit "/algorithms")
    (has (status? 200)
         "algorithm list page is found")
    (within [:h1] (has (text? "Algorithms")
         "algorithm list page title is Algorithms"))
    (within [:h3] (has (some-text? "Algorithm 1")
         "algorithm list page displays the first algorithm"))
    (within [:h3] (has (some-text? "Algorithm 8")
         "algorithm list page displays the last algorithm"))))

(deftest display-single-algorithm-page
  (-> (session app)
    (visit "/algorithms/Algorithm%201")
    (has (status? 200)
         "single algorithm page is found")
    (has (missing? [:h1])
         "single algorithm page title should not be displayed")
    (has (link? "Datastructure 1")
         "single algorithm page has a link to related datastructure")
    (has (link? :href "/datastructures/Datastructure 1")
         "single algorithm page link to related datastructure has correct url")
    (within [:span] (has (some-text? "pro")
         "single algorithm page displays algorithm pros"))
    (within [:span] (has (some-text? "con")
         "single algorithm page displays algorithm cons"))
    (within [:div.intro] (has (text? "Algo called Algorithm 1")
         "single algorithm page displays description of the algorithm"))
    (within [:div.col-md-12] (has (some-text? "Some super cool algorithm")
         "single algorithm page displays the contents of the algorithm"))))
