(ns cljs.navigation
  (:require [domina :as dom]
            [domina.css :as css]
            [domina.events :as events]))

(def gallery-count (count (dom/by-class "gallery-selector")))
(def displaying (atom 1))

(defn thumb-nth-child
  [n]
  (css/sel (str "img.thumb:nth-child(" n ")")))

(defn calculated-compr-width
  [cont-width displ]
  (cond (or (= displ 1) (= displ gallery-count))
          (/ (- cont-width 210) (inc gallery-count))
        (or (= displ 2) (= displ (dec gallery-count)))
          (/ (- cont-width 310) gallery-count)
        (or (= displ 3) (= displ (- gallery-count 2)))
          (/ (- cont-width 310) (+ gallery-count 2))
        :else 
          (/ (- cont-width 310) (+ gallery-count 3))))

(defn remove-class!
  [c selector]
  (dom/remove-class! (css/sel selector) c))

(defn add-class!
  [c selector]
  (dom/add-class! (css/sel selector) c))

(defn adj-thumb-width!
  [displ]
  (remove-class! "displaying" ".thumb")
  (add-class! "displaying" (str ".thumb:nth-child(" displ ")"))
  (let [cont-width (.-offsetWidth (.querySelector js/document ".thumbnails"))]
    (if (> gallery-count (/ cont-width 100))
      (let [compr-width (calculated-compr-width cont-width displ)]
        (if (> compr-width 33.33)
          (do (dom/set-style! (css/sel "img.thumb")
                              :width (str (/ (- cont-width 110)
                                             (dec gallery-count)) "px"))
              (dom/set-style! (thumb-nth-child displ)
                              :width "100px"))
          (do (dom/set-style! (css/sel "img.thumb")
                              :width (str compr-width "px"))
              (dom/set-style! (thumb-nth-child displ)
                              :width "100px")
              (dom/set-style! (thumb-nth-child (inc displ))
                              :width "100px")
              (dom/set-style! (thumb-nth-child (dec displ))
                              :width "100px")
              (dom/set-style! (thumb-nth-child (+ displ 2))
                              :width (str (* 3 compr-width) "px"))
              (dom/set-style! (thumb-nth-child (- displ 2))
                              :width (str (* 3 compr-width) "px"))
              (dom/set-style! (thumb-nth-child (+ displ 3))
                              :width (str (* 2 compr-width) "px"))
              (dom/set-style! (thumb-nth-child (- displ 3))
                              :width (str (* 2 compr-width) "px"))))))))

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

(defn mouseenter-effects!
  [evt]
  (adj-thumb-width! (data-order evt)))

(defn mouseleave-effects!
  [evt]
  (adj-thumb-width! @displaying))

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

(defn keybindings!
  "Adds keybindings for left, right arrow keys and A, D to move between images"
  [evt]
  (let [key-code (.-keyCode (events/raw-event evt))]
  (cond (or (= key-code 37) (= key-code 65))
        ((move-one-photo! dec) evt)
        (or (= key-code 39) (= key-code 68))
        ((move-one-photo! inc) evt))))

(defn add-listener!
  "adds the given function to click event in the given selector"
  [div fun event]
  (events/listen! (css/sel div) event fun))

(adj-thumb-width! @displaying)
(dom/set-style! (css/sel ".gallery-selector:not(:nth-child(1))")
                :display "none")

(add-listener! ".next" (move-one-photo! inc) :click)
(add-listener! ".previous" (move-one-photo! dec) :click)
(add-listener! ".thumb" move-attr-photo! :click)
(add-listener! ".thumb" mouseenter-effects! :mouseenter)
(add-listener! ".thumb" mouseleave-effects! :mouseleave)
(add-listener! js/document keybindings! :keydown)
