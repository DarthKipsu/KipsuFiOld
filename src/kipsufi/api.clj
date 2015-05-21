(ns kipsufi.api
    (:require [kipsufi.database :as db]))

; ---- PRIVATE ---- ;

(defn ^:private filter-features [obj selector]
  (map (fn [x] (:f1 x)) (selector (fn [x] (:f2 x)) (:features obj))))

(defn ^:private features->advantage-groups [obj]
  (dissoc (assoc obj
                 :advantages (filter-features obj filter)
                 :disadvantages (filter-features obj remove))
          :features))

(defn ^:private format-time [obj]
  (assoc obj :created (str (:created obj)) :edited (str (:edited obj))))

(defn ^:private with-group [group obj]
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
