(ns kipsufi.db-mock
  (:require [clj-time.coerce :as c]
            [clojure.string :as s]))

(def pro&con [{:f1 "pro" :f2 true} {:f1 "con" :f2 false}])

(defn date-from-index [object]
  (c/to-sql-time (str "2015-03-0" (second (s/split object #" ")))))

(defn read-algorithm [algorithm] [{:features pro&con
                                  :datastructures ["Datastructure 1"]
                                  :edited (date-from-index algorithm)
                                  :created (date-from-index algorithm)
                                  :content "Some super cool algorithm."
                                  :description (str "Algo called " algorithm)
                                  :name algorithm}])

(defn read-datastructure [ds] [{:features pro&con
                                :edited (date-from-index ds)
                                :created (date-from-index ds)
                                :content "Some super cool datastructure."
                                :description (str "Datastructure called " ds)
                                :name ds}])

(defn read-article [article] [{:edited (date-from-index article)
                               :created (date-from-index article)
                               :content "Some super cool article."
                               :description (str "Article called " article)
                               :name article}])

(defn read-project [project] [{:edited (date-from-index project)
                               :created (date-from-index project)
                               :content "Some super cool project."
                               :description (str "Project called " project)
                               :name project}])

;(defn read-datastructure [datastructure] {})

;(defn read-project [project] {})

;(defn read-article [article] {})

;(defn list-algorithms []
;  (apply lazy-seq (map (fn [x] (read-algorithm (str "Algorithm " x)))
;                       (range 10))))

;(defn list-datastructures []
;  (apply lazy-seq (map (fn [x] (read-datastructure (str "Algorithm " x)))
;                       (range 10))))

;(defn list-projects []
;  (apply lazy-seq (map (fn [x] (read-project (str "Algorithm " x)))
;                       (range 10))))

;(defn list-articles []
;  (apply lazy-seq (map (fn [x] (read-article (str "Algorithm " x)))
;                       (range 10))))
