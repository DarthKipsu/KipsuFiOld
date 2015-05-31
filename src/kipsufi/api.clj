(ns kipsufi.api
    (:require [kipsufi.database :as db]
              [clj-time.format :as f]
              [clj-time.coerce :as c]))

; ---- PRIVATE ---- ;

(defn ^:private filter-features
  "Return features that match the given selector."
  [obj selector]
  (map (fn [x] (:f1 x)) (selector (fn [x] (:f2 x)) (:features obj))))

(defn ^:private features->advantage-groups
  "Divide :features into :advantages and :disadvantages."
  [obj]
  (dissoc (assoc obj
                 :advantages (filter-features obj filter)
                 :disadvantages (filter-features obj remove))
          :features))

(defn ^:private format-time
  "Format sgl timestamp into better display format for web."
  [obj]
  (assoc obj :created (f/unparse (f/formatter "d MMMM yyyy") (c/from-sql-time (:created obj)))
             :edited (f/unparse (f/formatter "d MMMM yyyy") (c/from-sql-time (:edited obj)))
             :launched (f/unparse (f/formatter "d MMMM yyyy") (c/from-sql-time (:launched obj)))))

(defn ^:private with-group
  "Add :group to map."
  [group obj]
  (assoc obj :group group))

; ---- PUBLIC ---- ;

(defn index
  "Returns a list of API subdirectories."
  []
  {"algorithms list" "/algorithms"
   "datastructures list" "/datastructures"
   "recent items" "/recent"})

(defn algorithms
  "Returns a formatted list of algorithms."
  []
  (->> (db/list-algorithms)
    (map format-time)
    (map features->advantage-groups)
    (map (partial with-group "algorithms"))))

(defn datastructures
  "Returns a formatted list of datastructures."
  []
  (->> (db/list-datastructures)
    (map format-time)
    (map features->advantage-groups)
    (map (partial with-group "datastructures"))))

(defn projects
  "Returns a formatted list of projects."
  []
  (->> (db/list-projects)
    (map format-time)
    (map (partial with-group "projects"))))

(defn all
  "Returns a formatted list of both algorithms and datastructures."
  []
  (concat (algorithms) (datastructures)))

(defn algorithm
  "Returns the formatted entry of a single algorithm."
  [algorithm]
  (format-time
    (features->advantage-groups
      (first (db/read-algorithm algorithm)))))

(defn datastructure
  "Returns the formatted entry of a single datastructure."
  [datastructure]
  (format-time
    (features->advantage-groups
      (first (db/read-datastructure datastructure)))))

(defn project
  "Returns the formatted entry of a single project."
  [project]
  (format-time
      (first (db/read-project project))))
