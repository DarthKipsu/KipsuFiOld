(ns kipsufi.features.list-recent
  (:use [kerodon.core]
        [kerodon.test]
        [kipsufi.db-mock]
        [clojure.test])
  (:require [kipsufi.web :refer [app db api-routes]]))

(defn path-to-articles [state#]
  (:content
    (nth (:content (first
           (:content (second
             (:content (second
               (:content (second
                 (:enlive state#)))))))))
         3)))

(defn articles [side state#]
  (if (= side :left)
    (:content (first (path-to-articles state#)))
    (:content (second (path-to-articles state#)))))

(defmacro content-count-eql? [n]
  `(validate = (fn [state#] (+ (count (articles :left state#))
                               (count (articles :right state#))))
                ~n
                (~'content-count-eql? ~n)))

(println "Running 18 feature assertions for main page and layout.")

(deftest display-main-page-content
  (-> (session app)
    (visit "/")
    (has (status? 200)
         "page is found")
    (within [:h1] (has (text? "darth.kipsu.fi")
         "page title is darth.kipsu.fi"))
    (within [:span.item-type] (has (some-text? "algorithms")
         "has articles with type algorithms"))
    (within [:span.item-type] (has (some-text? "datastructures")
         "has articles with type datastructures"))
    (has (content-count-eql? 10)
         "page has exactly 10 most recent articles")))

(deftest display-layout-contents
  (-> (session app)
    (visit "/")
    (within [:footer] (has (text? "Verna Koskinen - darth.kipsu@gmail.com")
         "footer text is displayed"))
    (within [:nav] (has (link? "Home")
         "navigation displays a link to Home"))
    (within [:nav] (has (link? :href "/")
         "navigation link to Home takes to correct url"))
    (within [:nav] (has (link? "Articles")
         "navigation displays a link to Articles"))
    (within [:nav] (has (link? :href "/articles")
         "navigation link to Articles takes to correct url"))
    (within [:nav] (has (link? "Algorithms")
         "navigation displays a link to Algorithms"))
    (within [:nav] (has (link? :href "/algorithms")
         "navigation link to Algorithms takes to correct url"))
    (within [:nav] (has (link? "Datastructures")
         "navigation displays a link to Datastructures"))
    (within [:nav] (has (link? :href "/datastructures")
         "navigation link to Datastructures takes to correct url"))
    (within [:nav] (has (link? "Projects")
         "navigation displays a link to Projects"))
    (within [:nav] (has (link? :href "/work")
         "navigation link to Projects takes to correct url"))
    (within [:nav] (has (link? "About")
         "navigation displays a link to About"))
    (within [:nav] (has (link? :href "/about")
         "navigation link to About takes to correct url"))))
