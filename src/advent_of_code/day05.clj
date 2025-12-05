(ns advent-of-code.day05
  (:require [advent-of-code.core :as core]
            [clojure.set :as set]
            [clojure.string :as str]))

(defn parse-range
  "Parse a range string like '3-5' into [low high]"
  [s]
  (let [[low high] (str/split s #"-")]
    [(Long/parseLong low) (Long/parseLong high)]))

(defn parse-input
  "Parse input into fresh-ingredients-ranges and available-ingredients"
  [input]
  (let [[ranges-section ids-section] (str/split input #"\n\n")]
    {:fresh-ingredients-ranges (mapv parse-range (core/parse-lines ranges-section))
     :available-ingredients (mapv #(Long/parseLong %) (core/parse-lines ids-section))}))

(defn is-in-range?
  "Check if a number is within the given range"
  [num [low high]]
  (<= low num high))

(defn is-in-ranges?
  "Check if an ingredient ID is valid based on the fresh ingredients ranges"
  [ingredient-id fresh-ingredients-ranges]
  (some #(is-in-range? ingredient-id %) fresh-ingredients-ranges))

(defn part1
  "Solve Part 1 - count fresh ingredients that are in ranges"
  [{:keys [fresh-ingredients-ranges available-ingredients]}]
  (count (filter #(is-in-ranges? % fresh-ingredients-ranges) available-ingredients)))

(defn merge-overlapping-ranges
  "Merge overlapping ranges and return list of non-overlapping ranges"
  [ranges]
  (let [sorted (sort-by first ranges)]
    (reduce (fn [merged [low high]]
              (if (empty? merged)
                [[low high]]
                (let [[prev-low prev-high] (last merged)]
                  (if (<= low (inc prev-high))
                    (conj (vec (butlast merged)) [prev-low (max prev-high high)])
                    (conj merged [low high])))))
            []
            sorted)))

(defn count-range
  "Count the number of elements in a range [low high]"
  [[low high]]
  (inc (- high low)))

(defn part2
  "Solve Part 2 - count total fresh ingredients across all ranges"
  [{:keys [fresh-ingredients-ranges]}]
  (reduce + (map count-range (merge-overlapping-ranges fresh-ingredients-ranges))))

(defn solve
  "Run Day 5 with benchmarking"
  []
  (let [input (parse-input (core/read-input 5 "input"))]
    (println "Day 5:")
    (core/run-parts part1 part2 input :runs 100)))

(defn -main
  "Entry point for Day 5"
  [& args]
  (solve))
