(defn get-input
  [name]
  (.querySelector js/document (str "input[name='" name "']")))

(defn fill-form!
  [params]
  (let [names ["name" "role" "salary"]]
    (doseq [name names]
      (set! (.-value (get-input name)) (.get params name)))))

(defn handle-insert!
  []
  ())

(defn handle-update!
  []
  )

(defn load-app!
  []
  (let [params (-> js/location
                   .-search
                   js/URLSearchParams.)
        id (.get params "id")]
    (set!
     (.-textContent (get-elem "title"))
     (if id (str "Update employee " id) "Insert employee"))
    (.addEventListener
     (get-elem "form")
     "submit"
     (fn [e]
       (.preventDefault e)
       (if id (handle-update!) (handle-insert!))))
    (when id (fill-form! params))))

(load-app!)
