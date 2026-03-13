;;;; UTILITIES

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

;;;; ----------

;;;; DOMAIN CODE

(defn get-page-number
  []
  (let [unsafe-page-number (-> js/location
                               .-search
                               js/URLSearchParams.
                               (.get "page")
                               js/parseInt)]
    (if (js/isNaN unsafe-page-number)
      1
      unsafe-page-number)))

(defn render-employees!
  [emps]
  (letfn [(new-prop-cell
            [name value]
            (doto (new-elem "div" value)
              (.appendChild (new-hidden name value))))]
    (doseq [emp emps]
      (let [delete-btn (let [btn (new-elem "button" "Delete")]
                         (set! (.-type btn) "button")
                         (.addEventListener
                          btn
                          "click"
                          #(println (.-id emp)))
                         btn)
            cells [(new-prop-cell "id" (.-id emp))
                   (new-prop-cell "name" (.-name emp))
                   (new-prop-cell "role" (.-role emp))
                   (new-prop-cell "salary" (.-salary emp))
                   (new-prop-cell "added-date" (.-added_date emp))
                   (doto (new-elem "div")
                     (.appendChild (new-elem "button" "Update")))
                   (doto (new-elem "div")
                     (.appendChild delete-btn))]
            row (let [form (new-elem "form")]
                  (set! (.-className form) "employee-row")
                  (set! (.-action form) "employee-form")
                  form)]
        (doseq [cell cells]
          (.appendChild row cell))
        (.appendChild (get-elem "body") row)))))

(defn render-error!
  [msg]
  (.appendChild (get-elem "body") (new-elem "h1" msg)))

(defn load-app!
  []
  (-> (js/fetch (str "http://localhost:5070/?page=" (get-page-number)))
      (.then #(.json %))
      (.then #(if (instance? js/Object %)
                (render-employees! (.-employees %))
                (render-error! %)))
      (.catch render-error!)))

(load-app!)
