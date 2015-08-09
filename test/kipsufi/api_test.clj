(ns kipsufi.api-test
  (:require [clojure.test :refer :all]
            [kipsufi.api :refer :all]
            [kipsufi.db-mock :as db]
            [clj-time.coerce :as c])
  (:use midje.sweet))

(facts "format helpers for API"
 (fact "filters algorithm and datastructure features based on given selector"
   (filter-features
     {:features [{:f2 true :f1 'take}
                  {:f2 false :f1 'skip}]} filter) => '(take)
   (filter-features
     {:features [{:f2 true :f1 'skip}
                  {:f2 false :f1 'take}
                  {:f2 false :f1 'these}]} remove) => '(take these))
 (fact "splits :features options to :advantages and :disadvantages"
   (features->advantage-groups
     {:features [{:f2 true :f1 'adv}
                  {:f2 false :f1 'dis}]}) => {:advantages '(adv)
                                               :disadvantages '(dis)})
 (fact "formats time to better readable dates"
   (format-time
     {:created (c/to-sql-time "2015-03-02")
       :edited (c/to-sql-time "2015-03-02")
       :launched (c/to-sql-time "2015-03-02")}) => {:created "2 March 2015"
                                :edited "2 March 2015"
                                :launched "2 March 2015"}
   (format-time
     {:created (c/to-sql-time "2015-03-02")
       :edited (c/to-sql-time "2015-03-02")}) => {:created "2 March 2015"
                              :edited "2 March 2015"
                              :launched nil})
 (fact "adds a :group identifier to an object"
   (with-group :algorithms {}) => {:group :algorithms}))

(facts "database loaders for single items"
  (fact "a single algorithm is read from the database"
    (:name (single-item-from-db
             (db/read-algorithm "Algorithm 1"))) => "Algorithm 1"
    (:name (single-item-from-db
             (db/read-algorithm "Algorithm 2"))) => "Algorithm 2")
  (fact "a single datastructure is read from the database"
    (:name (single-item-from-db
             (db/read-datastructure "Datastructure 1"))) => "Datastructure 1"
    (:name (single-item-from-db
             (db/read-datastructure "Datastructure 2"))) => "Datastructure 2")
  (fact "a single article is read from the database"
    (:name (single-item-from-db
             (db/read-article "Article 1"))) => "Article 1"
    (:name (single-item-from-db
             (db/read-article "Article 2"))) => "Article 2")
  (fact "a single project is read from the database"
    (:name (single-item-from-db
             (db/read-project "Project 1"))) => "Project 1"
    (:name (single-item-from-db
             (db/read-project "Project 2"))) => "Project 2"))

(facts "database loaders for listed items"
  (fact "a list of algorithms is read from the database"
    (count (list-from-db (db/list-algorithms) "algorithms")) => 8
    (:name (first (list-from-db (db/list-algorithms) "algorithms")))
        => "Algorithm 1"
    (:name (second (list-from-db (db/list-algorithms) "algorithms")))
        => "Algorithm 2")
  (fact "a list of algorithms with desired length is read from the database"
    (count (list-from-db (db/list-algorithms 5) "algorithms")) => 5)
  (fact "a list of datastructures is read from the database"
    (count (list-from-db (db/list-datastructures) "datastructures")) => 8
    (:name (first (list-from-db (db/list-datastructures) "datastructures")))
        => "Datastructure 1"
    (:name (second (list-from-db (db/list-datastructures) "datastructures")))
        => "Datastructure 2")
  (fact "a list of datastructures with desired length is read from the database"
    (count (list-from-db (db/list-datastructures 3) "datastructures")) => 3)
  (fact "a list of projects is read from the database"
    (count (list-from-db (db/list-projects) "projects")) => 8
    (:name (first (list-from-db (db/list-projects) "projects")))
        => "Project 1"
    (:name (second (list-from-db (db/list-projects) "projects")))
        => "Project 2")
  (fact "a list of projects with desired length is read from the database"
    (count (list-from-db (db/list-projects 2) "projects")) => 2)
  (fact "a list of articles is read from the database"
    (count (list-from-db (db/list-articles) "articles")) => 8
    (:name (first (list-from-db (db/list-articles) "articles")))
        => "Article 1"
    (:name (second (list-from-db (db/list-articles) "articles")))
        => "Article 2")
  (fact "a list of articles with desired length is read from the database"
    (count (list-from-db (db/list-articles 4) "articles")) => 4))

