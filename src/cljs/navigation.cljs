(ns cljs.navigation
  (:require [domina :as dom]
            [domina.css :as css]
            [domina.events :as events]))

(def gallery-count (count (dom/by-class "gallery-selector")))
(def displaying (atom 1))

(defn reset-display-photo [id]
  (dom/set-style! (css/sel (str ".gallery-selector:nth-child(" @displaying ")"))
                  :display "none")
  (.log js/console @displaying)
  (reset! displaying id)
  (.log js/console @displaying)
  (dom/set-style! (css/sel (str ".gallery-selector:nth-child(" id ")"))
                  :display "table"))

(defn switch-photo!
  "Hides all other photos / galeries and displays the one with the given id."
  [id]
  (cond
    (> id gallery-count) (switch-photo! 1)
    (< id 1) (switch-photo! gallery-count)
    :else (reset-display-photo id)))

(defn add-listener!
  "adds the given function to click event in the given selector"
  [div fun event]
  (events/listen! (css/sel div) event fun))

(dom/set-style! (css/sel ".gallery-selector:not(:nth-child(1))")
                :display "none")

(add-listener! ".next" (fn [evt] (switch-photo! (inc @displaying))) :click)
(add-listener! ".previous" (fn [evt] (switch-photo! (dec @displaying))) :click)
(add-listener! ".thumb"
  (fn [evt] (switch-photo! (js/parseInt (.getAttribute (:target evt) "data-order")))) :click)

(.log js/console gallery-count)
