(ns kipsufi.api
    (:require [kipsufi.database :as db]
              [clj-time.format :as f]
              [clj-time.coerce :as c]))

; ---- PRIVATE ---- ;

(defn filter-features
  "Return features that match the given selector."
  [obj selector]
  (map (fn [x] (:f1 x)) (selector (fn [x] (:f2 x)) (:features obj))))

(defn features->advantage-groups
  "Divide :features into :advantages and :disadvantages."
  [obj]
  (dissoc (assoc obj
                 :advantages (filter-features obj filter)
                 :disadvantages (filter-features obj remove))
          :features))

(defn format-time
  "Format sgl timestamp into better display format for web."
  [obj]
  (assoc obj :created (f/unparse (f/formatter "d MMMM yyyy") (c/from-sql-time (:created obj)))
             :edited (f/unparse (f/formatter "d MMMM yyyy") (c/from-sql-time (:edited obj)))
             :launched (if (:launched obj) (f/unparse (f/formatter "d MMMM yyyy") (c/from-sql-time (:launched obj))) nil)))

(defn with-group
  "Add :group to map."
  [group obj]
  (assoc obj :group group))

(defn list-from-db
  "GEt a list of items from db with groups and formatted time"
  [db-query group]
  (->> (db-query)
    (map format-time)
    (map (partial with-group group))))

(defn single-item-from-db
  "Get a single item from db with formatted time"
  [db-query item]
  (format-time (first (db-query item))))

; ---- PUBLIC ---- ;

(defn index
  "Returns a list of API subdirectories."
  []
  {"algorithms list" "/algorithms"
   "datastructures list" "/datastructures"
   "projects list" "/projectsrojects"
   "recent items" "/recent"})

(defn algorithms
  "Returns a formatted list of algorithms."
  []
  (map features->advantage-groups
       (list-from-db db/list-algorithms "algorithms")))

(defn datastructures
  "Returns a formatted list of datastructures."
  []
  (map features->advantage-groups
       (list-from-db db/list-datastructures "datastructures")))

(defn projects
  "Returns a formatted list of projects."
  []
  (list-from-db db/list-projects "projects"))

(defn articles
  "Returns a formatted list of articles."
  []
  (list-from-db db/list-articles "articles"))

(defn all
  "Returns a formatted list of all item types."
  []
  (concat (algorithms) (datastructures) (articles) (projects)))

(defn algorithm
  "Returns the formatted entry of a single algorithm."
  [algorithm]
  (features->advantage-groups
    (single-item-from-db db/read-algorithm algorithm)))

(defn datastructure
  "Returns the formatted entry of a single datastructure."
  [datastructure]
  (features->advantage-groups
    (single-item-from-db db/read-datastructure datastructure)))

(defn project
  "Returns the formatted entry of a single project."
  [project]
  (single-item-from-db db/read-project project))

(defn article
  "Returns the formatted entry of a single project."
  [article]
  (single-item-from-db db/read-article article))
