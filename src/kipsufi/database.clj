(ns kipsufi.database
  (:use [java-jdbc.sql])
  (:require [kipsufi.jdbc.json]
            [clojure.java.jdbc :as sql]))

(def ^:private connection
  {:subprotocol "postgresql"
   :subname "//localhost:5432/codingproject"})

(def ^:private query (partial sql/query connection))

(defn ^:private filter-features [obj selector]
  (map (fn [x] (:f1 x)) (selector (fn [x] (:f2 x)) (:features obj))))

(defn ^:private features->advantage-groups [obj]
  (dissoc (assoc obj
                 :advantages (filter-features obj filter)
                 :disadvantages (filter-features obj remove))
          :features))

(defn ^:private with-group [group obj]
  (assoc obj :group group))

(defn list-algorithms []
  (let [raw (query (select [{:a.algorithm_name :name}
                            :a.description
                            {"json_agg(DISTINCT ad.datastructure_name)" :datastructures}
                            {"json_agg((af.feature_name, af.advantage))" :features}]
                           {:algorithms :a}
                           "NATURAL JOIN algorithms_datastructures ad, algorithms_features af"
                           "GROUP BY a.algorithm_name, a.description"
                           (order-by :1)))]
    (->> raw
      (map features->advantage-groups)
      (map (partial with-group "algorithms")))))
