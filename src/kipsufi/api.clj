(ns kipsufi.api
    (:require [kipsufi.database :as db]))

(defn all []
  (let [algorithms (db/list-algorithms)]
    (map (fn [x] (assoc x :url (str "algorithms/" (:algorithm_name x)))) algorithms)))
