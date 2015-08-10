(ns kipsufi.features.articles
  (:use [kerodon.core]
        [kerodon.test]
        [clojure.test])
  (:require [kipsufi.web :refer [app db api-routes]]))

(println "Running 7 feature assertions for articles.")

(deftest display-article-list
  (-> (session app)
    (visit "/articles")
    (has (status? 200)
         "article list page is found")
    (within [:h1] (has (text? "Articles")
         "article list page title is Articles"))
    (within [:h3] (has (some-text? "Article 1")
         "article list page displays the first article"))
    (within [:h3] (has (some-text? "Article 8")
         "article list page displays the last article"))))

(deftest display-single-article-page
  (-> (session app)
    (visit "/articles/Article%201")
    (has (status? 200)
         "single article page is found")
    (within [:div.intro] (has (text? "Article called Article 1")
         "single article page displays description of the article"))
    (within [:div.col-md-12] (has (some-text? "Some super cool article.")
         "single article page displays the contents of the article"))))
