(ns cljs.js)

;(extend-type js/HTMLCollection
;  ISeqable
;  (-seq [array] (array-seq array 0)))
;
;(defn init []
;  (js/console.log "loaded")
;  (let [imgHeight (.. (.getElementById js/document "jolla") -offsetHeight)
;        nav (first (.getElementsByTagName js/document "nav"))]
;    (.setAttribute nav "data-offset-top" imgHeight)
;    (.setAttribute nav "data-spy" "affix")))
;
;(set! (.-onload js/window) init)
