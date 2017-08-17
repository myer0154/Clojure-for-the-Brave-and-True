(ns fwpd.core)

(def filename "suspects.csv")

(def vamp-keys [:name :glitter-index])

(defn str->int
  [str]
  (Integer. str))

(def conversion {:name identity
                  :glitter-index str->int})

(defn convert
  [vamp-key value]
  ((get conversion vamp-key) value))

(defn parse
  "converts a CSV into rows of columns"
  [string]
  (map #(clojure.string/split % #",")
       (clojure.string/split string #"\r\n")))

(defn mapify
  "Returns a seq of maps like {:name \"Edward Cullen\" :glitter-index 10}"
  [rows]
  (map (fn [unmapped-row]
         (reduce (fn [row-map [vamp-key value]]
                   (assoc row-map vamp-key (convert vamp-key value)))
                 {}
                 (map vector vamp-keys unmapped-row)))
       rows))

(defn glitter-filter
  [minimum-glitter records]
  (filter #(>= (:glitter-index %) minimum-glitter) records))

(defn glitter-names
  "Produces a list of names that match the minimum glitter value"
  [minimum-glitter records]
  (map :name (glitter-filter minimum-glitter records)))

(defn append
  "Appends a new suspect into the list"
  [suspect records]
  (conj records suspect))

;(defn validate
;  "Checks that the specified keys are present in the record"
;  [required-keys record]
;
;  )