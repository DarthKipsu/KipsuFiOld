(ns cljs.navigation
  (:require [clojure.string :as str]
            [domina :as dom]
            [domina.css :as css]
            [domina.events :as events]))

(def gallery-count (count (dom/by-class "gallery-selector")))

(defn displaying-id []
  (let [id (get (str/split js/window.location.pathname "/") 4)]
    (if id (let [id (js/parseInt id)]
             (if (or (> id gallery-count) (< id 1)) 1 id))
        1)))

(def displaying (atom (displaying-id)))

(defn thumb-nth-child
  "Returns css selector for img.thmub:nth-child for the given n"
  [n]
  (css/sel (str "img.thumb:nth-child(" n ")")))

(defn change-width!
  "Changes the width of img.thumb element or if given n, the nth child of the
  element, with the given width"
  ([width]
   (dom/set-style! (css/sel "img.thumb") :width (str width "px")))
  ([n width]
   (dom/set-style! (thumb-nth-child n) :width (str width "px"))))

(defn calculated-compr-width
  "Returns the width for the smallest compressed image for the thumbnail
  navigation bar based on the container width"
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
  "Removes the given class from the given element"
  [c selector]
  (dom/remove-class! (css/sel selector) c))

(defn add-class!
  "Adds the given class to the given element"
  [c selector]
  (dom/add-class! (css/sel selector) c))

(defn adj-thumb-width!
  "Changes the width of all thumbnails in the thumbnail navigation bar, so that
  the displayed thumbnail has maximum width and the thumbnails around it have
  correctly decreasing width to fit the bar in one line"
  [displ]
  (remove-class! "displaying" ".thumb")
  (add-class! "displaying" (str ".thumb:nth-child(" displ ")"))
  (let [cont-width (.-offsetWidth (.querySelector js/document ".thumbnails"))]
    (if (> gallery-count (/ cont-width 100))
      (let [compr-width (calculated-compr-width cont-width displ)]
        (if (> compr-width 33.33)
          (do (change-width! (str (/ (- cont-width 110) (dec gallery-count))))
              (change-width! displ 100))
          (do (change-width! compr-width)
              (change-width! displ 100)
              (change-width! (inc displ) 100)
              (change-width! (dec displ) 100)
              (change-width! (+ displ 2)  (* 3 compr-width))
              (change-width! (- displ 2) (* 3 compr-width))
              (change-width! (+ displ 3) (* 2 compr-width))
              (change-width! (- displ 3) (* 2 compr-width))))))))

(defn displ-photo!
  "Either display or hide a gallery-selector element with the given n"
  [n display]
  (dom/set-style! (css/sel (str ".gallery-selector:nth-child(" n ")"))
                  :display display))

(defn reset-display-photo
  "Hide the currently visible gallery-selector element and display the one with
  the given id"
  [id]
  (displ-photo! @displaying "none")
  (reset! displaying id)
  (if (not (.querySelector js/document ".date"))
    (.replaceState js/window.history {} "" id))
  (displ-photo! id "table"))

(defn data-order
  "Get event data-order attribute value from the domina event, as integer"
  [event]
  (js/parseInt (.getAttribute (:target event) "data-order")))

(defn mouseenter-effects!
  "Adjust width for thumbnail bar thumbnails so that the element where the mouse
  is moved is fully displayed"
  [evt]
  (adj-thumb-width! (data-order evt)))

(defn mouseleave-effects!
  "Restore full display of thumbnail navigation to the corresponding thumbnail
  of the currently displayed gallery-selector"
  [evt]
  (adj-thumb-width! @displaying))

(defn switch-photo!
  "Hides all other photos / galleries and displays the one with the given id."
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
  (let [key-code (.-keyCode (events/raw-event evt))
        a-or-left-arrow (or (= key-code 37) (= key-code 65))
        d-or-right-arrow (or (= key-code 39) (= key-code 68))]
    (cond a-or-left-arrow ((move-one-photo! dec) evt)
          d-or-right-arrow ((move-one-photo! inc) evt))))

(defn add-listener!
  "adds the given function to click event in the given selector"
  [div fun event]
  (events/listen! (css/sel div) event fun))

; INITIALIZE EVERYTHING ;

(if (.querySelector js/document ".thumbnails")
    (do (adj-thumb-width! @displaying)
        (dom/set-style!
          (css/sel (str ".gallery-selector:not(:nth-child(" @displaying "))"))
                   :display "none")

        (add-listener! ".next" (move-one-photo! inc) :click)
        (add-listener! ".previous" (move-one-photo! dec) :click)
        (add-listener! ".thumb" move-attr-photo! :click)
        (add-listener! ".thumb" mouseenter-effects! :mouseenter)
        (add-listener! ".thumb" mouseleave-effects! :mouseleave)
        (add-listener! js/document keybindings! :keydown)))
