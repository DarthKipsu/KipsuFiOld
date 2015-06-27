(ns kipsufi.database
  (:use [java-jdbc.sql])
  (:require [kipsufi.jdbc.json]
            [clojure.java.jdbc :as sql]))

(def connection
  {:subprotocol "postgresql"
   :subname "//localhost:5432/kipsu"
   :user "kipsu"
   :password "kipsu"})

(def query (partial sql/query connection))


(defn list-algorithms 
  "Reads all algorithms from db and returns them as lazy seq."
  []
  (query (str "SELECT a.algorithm_name AS name,a.description,a.created,a.edited,"
              "json_agg(DISTINCT ad.datastructure_name) AS datastructures,"
              "json_agg((af.feature_name, af.advantage)) AS features "
              "FROM algorithms a "
              "NATURAL JOIN algorithms_datastructures ad "
              "NATURAL JOIN algorithms_features af "
              "GROUP BY a.algorithm_name, a.description "
              "ORDER BY a.edited DESC")))

(defn list-datastructures 
  "Reads all datastructures from db and returns them as lazy seq."
  []
  (query (str "SELECT d.datastructure_name AS name,d.description,d.created,d.edited,"
              "json_agg((df.feature_name, df.advantage)) AS features "
              "FROM datastructures d "
              "NATURAL JOIN datastructures_features df "
              "GROUP BY d.datastructure_name, d.description "
              "ORDER BY d.edited DESC")))

(defn list-projects 
  "Reads all projects from db and returns them as lazy seq."
  []
  (query (str "SELECT p.project_name AS name,p.description,p.launched,p.created,p.edited,"
              "json_agg(DISTINCT pl.language_name) AS languages "
              "FROM projects p "
              "NATURAL JOIN projects_languages pl "
              "GROUP BY p.project_name, p.description "
              "ORDER BY p.edited DESC")))

(defn list-articles
  "Reads all articles from db and returns them as lazy seq."
  []
  (query (str "SELECT article_name AS name,description,edited,created "
              "FROM articles "
              "ORDER BY edited DESC")))

(defn read-algorithm
  "Reads a single algorithm fron db and returns it with contents."
  [algorithm]
  (query [(str "SELECT a.algorithm_name AS name,a.description,a.content,a.created,a.edited,"
               "json_agg(DISTINCT ad.datastructure_name) AS datastructures,"
               "json_agg((af.feature_name, af.advantage)) AS features "
               "FROM algorithms a "
               "NATURAL JOIN algorithms_datastructures ad "
               "NATURAL JOIN algorithms_features af "
               "WHERE a.algorithm_name = ? "
               "GROUP BY a.algorithm_name, a.description")
          algorithm]))

(defn read-datastructure
  "Reads a single datastructure from db and returns it with contents."
  [datastructure]
  (query [(str "SELECT d.datastructure_name AS name,d.description,d.content,d.created,d.edited,"
               "json_agg((df.feature_name, df.advantage)) AS features "
               "FROM datastructures d "
               "NATURAL JOIN datastructures_features df "
               "WHERE d.datastructure_name = ? "
               "GROUP BY d.datastructure_name, d.description")
          datastructure]))

(defn read-project 
  "Reads a single project from db and returns it with contents and categories."
  [project]
  (query [(str "SELECT p.project_name AS name,p.description,p.content,p.comments,"
               "p.better_done,p.lesson,p.link_url,p.link_name,p.launched,p.created,p.edited,"
               "json_agg(DISTINCT pl.language_name) AS languages "
               "FROM projects p "
               "NATURAL JOIN projects_languages pl "
               "WHERE p.project_name = ? "
               "GROUP BY p.project_name, p.description")
          project]))

(defn read-article 
  "Reads a single article from db and returns it with contents and categories."
  [article]
  (query [(str "SELECT article_name AS name,description,content,created,edited "
               "FROM articles "
               "WHERE article_name = ?")
          article]))

