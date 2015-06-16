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

(defn list-algorithms []
  (lazy-seq (map (fn [x] (first (read-algorithm (str "Algorithm " x))))
                 (range 1 9))))

(defn list-datastructures []
  (lazy-seq (map (fn [x] (first (read-datastructure (str "Datastructure " x))))
                       (range 1 9))))

(defn list-projects []
  (lazy-seq (map (fn [x] (first (read-project (str "Project " x))))
                       (range 1 9))))

(defn list-articles []
  (lazy-seq (map (fn [x] (first (read-article (str "Article " x))))
                       (range 1 9))))
