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
      (.then #(.json %))))

;;; Example code.
(-> (.resolve js/Promise (fetch-row-page-async (get-page-number)))
    (.then println))
