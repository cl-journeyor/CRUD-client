(def headers (js-obj "Content-Type" "application/json"))

(defn get-elem
  [id]
  (.getElementById js/document id))

(defn new-elem
  ([name]
   (.createElement js/document name))
  ([name txt]
   (let [elem (new-elem name)]
     (set! (.-textContent elem) txt)
     elem)))

(defn new-hidden
  [name value]
  (let [hidden (new-elem "input")]
    (set! (.-type hidden) "hidden")
    (set! (.-name hidden) name)
    (set! (.-value hidden) value)
    hidden))
