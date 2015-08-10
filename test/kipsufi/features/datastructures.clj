(ns kipsufi.features.datastructures
  (:use [kerodon.core]
        [kerodon.test]
        [clojure.test])
  (:require [kipsufi.web :refer [app db api-routes]]))

(println "Running 8 feature assertions for datastructures.")

(deftest display-algorithm-list
  (-> (session app)
    (visit "/datastructures")
    (has (status? 200)
         "datastructure list page is found")
    (within [:h1] (has (text? "Datastructures")
         "datastructure list page title is Datastructures"))
    (within [:h3] (has (some-text? "Datastructure 1")
         "datastructure list page displays the first datastructure"))
    (within [:h3] (has (some-text? "Datastructure 8")
         "datastructure list page displays the last datastructure"))))

(deftest display-single-algorithm-page
  (-> (session app)
    (visit "/datastructures/Datastructure%201")
    (has (status? 200)
         "single datastructure page is found")
    (has (missing? [:h1])
         "single datastructure page title should not be displayed")
    (within [:div.intro] (has (text? "Datastructure called Datastructure 1")
         "single datastructure page displays description of the datastructure"))
    (within [:div.col-md-12] (has (some-text? "Some super cool datastructure")
         "single datastructure page displays the content of the datastructure"))))
