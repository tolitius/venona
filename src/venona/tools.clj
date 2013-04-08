(ns venona.tools)

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

(defn asciify [n]
  "returs a string which is an ASCII representation of 'number's bytes"
  (unhexify (hexify n)))

(defn alpha-numerify [n]
  "returs a string which is an ASCII representation of 'number's bytes
   all non alpha numeric characters are replaced with spaces"
  (let [text (asciify n)]
    (clojure.string/replace text #"[^a-zA-Z0-9\s]" " ")))

(defn fmap [m f]
  "map 'f' over all the keys of 'm'"
  (into {} (for [[k v] m] [k (f v)])))

(defn xor [x y]
  ".xor to IFun to use it as partial when needed"
  (.xor x y))


