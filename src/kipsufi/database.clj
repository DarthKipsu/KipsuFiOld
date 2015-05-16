(ns kipsufi.database
  (:use [java-jdbc.sql])
  (:require [kipsufi.jdbc.json]
            [clojure.java.jdbc :as sql]))

(def ^:private connection
  {:subprotocol "postgresql"
   :subname "//localhost:5432/codingproject"})

(def ^:private query (partial sql/query connection))


(defn list-algorithms []
  (query (select [{:a.algorithm_name :name}
                  :a.description
                  :a.date
                  {"json_agg(DISTINCT ad.datastructure_name)" :datastructures}
                  {"json_agg((af.feature_name, af.advantage))" :features}]
                 {:algorithms :a}
                 "NATURAL JOIN algorithms_datastructures ad, algorithms_features af"
                 "GROUP BY a.algorithm_name, a.description"
                 (order-by :a.date))))

(defn list-datastructures []
  (query (select [{:d.datastructure_name :name}
                  :d.description
                  :d.date
                  {"json_agg((df.feature_name, df.advantage))" :features}]
                 {:datastructures :d}
                 "NATURAL JOIN algorithms_datastructures ad, datastructures_features df"
                 "GROUP BY d.datastructure_name, d.description"
                 (order-by :d.date))))
