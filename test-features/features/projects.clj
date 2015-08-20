(ns kipsufi.features.projects
  (:use [kerodon.core]
        [kerodon.test]
        [clojure.test])
  (:require [kipsufi.web :refer [app db api-routes]]))

(println "Running 12 feature assertions for projects.")

(deftest display-project-list
  (-> (session app)
    (visit "/work")
    (has (status? 200)
         "project list page is found")
    (within [:h1] (has (text? "Projects")
         "project list page title is Projects"))
    (within [:h3] (has (some-text? "Project 1")
         "project list page displays the first project"))
    (within [:h3] (has (some-text? "Project 8")
         "project list page displays the last project"))))

(deftest display-single-project-page
  (-> (session app)
    (visit "/work/Project%201")
    (has (status? 200)
         "single project page is found")
    (within [:h1] (has (text? "Project 1")
         "single project page title is displayed"))
    (within [:div.intro] (has (text? "Project called Project 1")
         "single project page displays description of the project"))
    (within [:strong] (has (some-text? "Languages:")
         "single project languages are displayed"))
    (within [:strong] (has (some-text? "Launched:")
         "single project launch date is displayed"))
    (within [:div.col-md-12] (has (some-text? "Some super cool project")
         "single project page displays the content of the project"))
    (within [:h3] (has (some-text? "Comments:")
         "single project page displays project comments"))
    (within [:h3] (has (some-text? "What I could have done better:")
         "single project page displays project improvements"))))
