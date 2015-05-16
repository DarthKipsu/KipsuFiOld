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

(defn algorithms []
  (->> (db/list-algorithms)
    (map format-time)
    (map features->advantage-groups)
    (map (partial with-group "algorithms"))))

(defn datastructures []
  (->> (db/list-datastructures)
    (map format-time)
    (map features->advantage-groups)
    (map (partial with-group "datastructures"))))

(defn all []
  (concat (algorithms) (datastructures)))

(defn algorithm [algorithm]
  (format-time
    (features->advantage-groups
      (first (db/read-algorithm algorithm)))))

(defn datastructure [datastructure]
  (format-time
    (features->advantage-groups
      (first (db/read-datastructure datastructure)))))
