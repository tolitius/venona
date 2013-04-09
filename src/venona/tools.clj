(ns venona.tools
  (use [venona.freqs]))

(defn parse-hex-str [hexs]
  "create a number from a hex string"
  (BigInteger. hexs 16))

(defn byte-count [hexs]
  "given a hex string returns its byte-count"
  (count (partition 2 hexs))) ;; this one reads better :)
  ;; (/ (count (:target ctexts)) 2))

(defn spaces [n]
  "creates a number that reflects a string of 'n' ASCII spaces"
  (BigInteger. 
    (reduce str (repeat n "20")) 16))

(defn hexify [n]
  "creates a hex string from a number"
  (format "%x" n))

(defn unhexify [hex] 
  "given an hex string e.g. '3f561ba9adb..' 
   coverts it an UTF-8 ASCII (readable) string"
  (reduce (fn [s [x y]] 
            (str s (char (Integer/parseInt (str x y) 16)))) "" 
          (partition 2 hex)))

(defn numerify [s]
  "given a string creates a number that reprents it's ASCII byte sequence"
  (BigInteger. (.getBytes s)))

(defn asciify [n]
  "returs a string which is an ASCII representation of 'number's bytes"
  (unhexify (hexify n)))

(defn alpha-numerify [n]
  "returs a string which is an ASCII representation of 'number's bytes
   all non alpha numeric characters are replaced with spaces"
  (let [text (asciify n)]
    (clojure.string/replace text #"[^a-zA-Z0-9\s]" " ")))

(defn fmap [f m]
  "map 'f' over all the keys of 'm'"
  (into {} (for [[k v] m] [k (f v)])))

(defn xor [x y]
  ".xor to IFun to use it as partial when needed"
  (.xor x y))

(defn hexify-all [ctnums]
  "convert all the cypher texts number values to hexidecimal string represntations"
  (fmap hexify ctnums))

(defn asciify-all [hexs]
  (fmap asciify hexs))

(defn alpha-numerify-all [hexs]
  (fmap alpha-numerify hexs))

(defn xor-with-trigrams [hexs]
  "given a hex string xors it with most frequent English trigrams"
  (map #(->> (numerify %)
             (xor (parse-hex-str hexs))
             (asciify)
             (hash-map (keyword %))) trigrams))

(defn hex-str-xor [s1 s2]
  (let [n1 (parse-hex-str s1)
        n2 (parse-hex-str s2)]
    (xor n1 n2)))

(defn trigram-at [index ctmap]
  (let [cts (-> (hexify-all ctmap)
                (dissoc :target))]
    (fmap #(clojure.string/join 
             (take 6 (drop index %))) ;; 6 bytes is an "ASCII 8 bit" triagram
          cts)))

