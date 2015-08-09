(ns kipsufi.features.list_recent
  (:use [kerodon.core]
        [kerodon.test]
        [clojure.test])
  (:require [kipsufi.web :refer [app]]))

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

(deftest display-content
  (-> (session app)
    (visit "/")
    (has (status? 200)
         "page is found")
    (within [:h1] (has (text? "darth.kipsu.fi")
         "page title is darth.kipsu.fi"))
    (has (content-count-eql? 10)
         "page has exactly 10 most recent articles")))
