(ns kipsufi.api
    (:require [clj-time.format :as f]
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

(defn edited>DateTime
  "Return edited date in DateTime format"
  [x] (f/parse (f/formatter "d MMMM yyyy") (:edited x)))

(defn with-group
  "Add :group to map."
  [group obj]
  (assoc obj :group group))

(defn list-from-db
  "GEt a list of items from db with groups and formatted time"
  [db-query group]
  (->> db-query
    (map format-time)
    (map (partial with-group group))))

(defn single-item-from-db
  "Get a single item from db with formatted time"
  [db-query]
  (format-time (first db-query)))

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
  ([db] (map features->advantage-groups
           (list-from-db ((ns-resolve db 'list-algorithms)) "algorithms")))
  ([db n] (map features->advantage-groups
            (list-from-db ((ns-resolve db 'list-algorithms) n) "algorithms"))))

(defn datastructures
  "Returns a formatted list of datastructures."
  ([db] (map features->advantage-groups
           (list-from-db ((ns-resolve db 'list-datastructures)) "datastructures")))
  ([db n] (map features->advantage-groups
           (list-from-db ((ns-resolve db 'list-datastructures) n) "datastructures"))))

(defn projects
  "Returns a formatted list of projects."
  ([db] (list-from-db ((ns-resolve db 'list-projects)) "work"))
  ([db n] (list-from-db ((ns-resolve db 'list-projects) n) "work")))

(defn articles
  "Returns a formatted list of articles."
  ([db] (list-from-db ((ns-resolve db 'list-articles)) "articles"))
  ([db n] (list-from-db ((ns-resolve db 'list-articles) n) "articles")))

(defn all
  "Returns a formatted list of all item types."
  [db n]
  (concat (algorithms db n) (datastructures db n) (articles db n) (projects db n)))

(defn recent
  "Returns a formatted list of all item types."
  [db]
  (take 10 (sort-by edited>DateTime #(compare %2 %1) (all db 10))))

(defn algorithm
  "Returns the formatted entry of a single algorithm."
  [db algorithm]
  (features->advantage-groups
    (single-item-from-db ((ns-resolve db 'read-algorithm) algorithm))))

(defn datastructure
  "Returns the formatted entry of a single datastructure."
  [db datastructure]
  (features->advantage-groups
    (single-item-from-db ((ns-resolve db 'read-datastructure) datastructure))))

(defn project
  "Returns the formatted entry of a single project."
  [db project]
  (single-item-from-db ((ns-resolve db 'read-project) project)))

(defn article
  "Returns the formatted entry of a single project."
  [db article]
  (single-item-from-db ((ns-resolve db 'read-article) article)))
