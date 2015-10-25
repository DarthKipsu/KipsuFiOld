(ns cljs.navigation
  (:require [domina :as dom]
            [domina.css :as css]
            [domina.events :as events]))

(def gallery-count (count (dom/by-class "gallery-selector")))
(def displaying (atom 1))

(defn reset-display-photo [id]
  (dom/set-style! (css/sel (str ".gallery-selector:nth-child(" @displaying ")"))
                  :display "none")
  ;(.log js/console @displaying)
  (reset! displaying id)
  ;(.log js/console @displaying)
  (dom/set-style! (css/sel (str ".gallery-selector:nth-child(" id ")"))
                  :display "table"))

(defn data-order
  "Get event data-order attribute value"
  [event]
  (js/parseInt (.getAttribute (:target event) "data-order")))

(defn remove-class!
  [c selector]
  (dom/remove-class! (css/sel selector) c))

(defn add-class!
  [c selector]
  (dom/add-class! (css/sel selector) c))

(defn mouseenter-effects!
  [evt]
  (remove-class! "displaying" ".thumb")
  (add-class! "displaying" (str ".thumb:nth-child(" (data-order evt) ")")))

(defn mouseleave-effects!
  [evt]
  (remove-class! "displaying" ".thumb")
  (add-class! "displaying" (str ".thumb:nth-child(" @displaying ")")))

(defn switch-photo!
  "Hides all other photos / galeries and displays the one with the given id."
  [id]
  (cond
    (> id gallery-count) (switch-photo! 1)
    (< id 1) (switch-photo! gallery-count)
    :else (reset-display-photo id)))

(defn move-one-photo!
  "Switch displaying a photo before or after current photo depending on given
  inc or dec function"
  [fun]
  (fn [evt] (switch-photo! (fun @displaying))
            (mouseleave-effects! evt)))

(defn move-attr-photo!
  "Switch displaying a photo by given data-order value"
  [evt]
  (switch-photo! (data-order evt)))

(defn add-listener!
  "adds the given function to click event in the given selector"
  [div fun event]
  (events/listen! (css/sel div) event fun))

(dom/set-style! (css/sel ".gallery-selector:not(:nth-child(1))")
                :display "none")

(add-listener! ".next" (move-one-photo! inc) :click)
(add-listener! ".previous" (move-one-photo! dec) :click)
(add-listener! ".thumb" move-attr-photo! :click)
(add-listener! ".thumb" mouseenter-effects! :mouseenter)
(add-listener! ".thumb" mouseleave-effects! :mouseleave)
