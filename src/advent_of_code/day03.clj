(ns advent-of-code.day03
  (:require [advent-of-code.core :as core]))

(defn parse-banks
  "Parse input into lines"
  [input]
  (core/parse-lines input))

(defn unique
  "Get unique elements from a collection"
  [coll]
  (distinct coll))

(defn max-digit-idx
  "Find the index of the maximum digit in a collection, searching up to end-idx (exclusive)"
  [digits start-idx end-idx]
  (->> (range start-idx end-idx)
       (reduce (fn [max-idx idx]
                 (if (> (Character/digit (nth digits idx) 10)
                        (Character/digit (nth digits max-idx) 10))
                   idx
                   max-idx))
               start-idx)))

(defn max-joltage
  "Get the largest n-digit number from digits while preserving order"
  [input n]
  (let [digits (vec (seq input))
        len (count digits)]
    (->> (range n)
         (reduce (fn [[result start-idx] i]
                   (let [end-idx (- (inc len) (- n i))
                         best-idx (max-digit-idx digits start-idx end-idx)]
                     [(conj result (nth digits best-idx)) (inc best-idx)]))
                 [[] 0])
         first
         (apply str)
         Long/parseLong)))

(defn part1
  "Solve Part 1"
  [input]
  (reduce + (map #(max-joltage % 2) input)))

(defn part2
  "Solve Part 2"
  [input]
  (reduce + (map #(max-joltage % 12) input)))

(defn solve
  "Run Day 3 with benchmarking"
  []
  (let [input (parse-banks (core/read-input 3 "input"))]
    (println "Day 3:")
    (core/run-parts part1 part2 input :runs 100)))

(defn -main
  "Entry point for Day 3"
  [& args]
  (solve))
