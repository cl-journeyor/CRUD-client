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

(defn fetch-row-page-async
  [page]
  (-> (js/fetch (str "http://localhost:5070/?page=" page))
      (.then #(.text %))))

(defn new-elem
  ([name]
   (.createElement js/document name))
  ([name txt]
   (let [elem (new-elem name)]
     (set! (.-textContent elem) txt)
     elem)))

(defn get-elem
  [id]
  (.getElementById js/document id))

(defn render-rows!
  [rows]
  (doseq [row rows]
    (let [idTd (new-elem "td" (.-id row))
          nameTd (new-elem "td" (.-name row))
          ])))



;;; Example code.
(-> (.resolve js/Promise (fetch-row-page-async (get-page-number)))
    (.then println))
