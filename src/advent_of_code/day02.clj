(ns advent-of-code.day02
  (:require [advent-of-code.core :as core]
            [clojure.string :as str]))

(defn parse-input
  "Parse the input - a single line of comma-separated ranges"
  [input]
  (str/split (str/trim input) #","))

(defn parse-range
  "Parse a range string like '5-10' into a vector [5 6 7 8 9 10]"
  [range-str]
  (let [[start end] (map #(Long/parseLong %) (str/split range-str #"-"))]
    (vec (range start (inc end)))))

(defn repeated-n-times?
  "Check if a number is made of a pattern repeated exactly n times."
  [number n]
  (let [s (str number)
        len (count s)]
    (and (zero? (mod len n))
         (let [part-len (/ len n)
               part (subs s 0 part-len)]
           (every? #(= part (subs s % (+ % part-len)))
                   (range part-len len part-len))))))

(defn invalid-id?
  "Check if a number is invalid. With n, checks exactly n repeats.
   Without n, checks for any repeat >= 2."
  ([number]
   (let [s (str number)
         len (count s)]
     (some #(repeated-n-times? number %) (range 2 (inc len)))))
  ([number n]
   (repeated-n-times? number n)))

(defn part1
  "Solve Part 1"
  [id-ranges]
  (transduce (comp (mapcat parse-range)
                   (filter #(invalid-id? % 2)))
             + id-ranges))

(defn part2
  "Solve Part 2"
  [id-ranges]
  (transduce (comp (mapcat parse-range)
                   (filter invalid-id?))
             + id-ranges))

(defn solve
  "Run Day 2 with benchmarking"
  []
  (let [input (core/read-input 2 "input")
        id-ranges (parse-input input)]
    (println "Day 2:")
    (core/run-parts part1 part2 id-ranges :runs 10000)))

(defn -main
  "Entry point for Day 2"
  [& args]
  (solve))