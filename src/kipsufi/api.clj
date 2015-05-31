(ns kipsufi.api
    (:require [kipsufi.database :as db]
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
  "Format sgl timestamp into 12 digit int display format."
  [obj]
  (assoc obj :created (c/to-long (c/from-sql-time (:created obj)))
             :edited (c/to-long (c/from-sql-time (:edited obj)))))

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
