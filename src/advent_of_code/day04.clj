(ns advent-of-code.day04
  (:require [advent-of-code.core :as core]))

(defn parse-input
  "Parse input into a 2D grid (vector of vectors)"
  [input]
  (mapv vec (core/parse-lines input)))

(def directions
  "All 8 adjacent directions: [row-delta col-delta]"
  [[-1 -1] [-1 0] [-1 1]
   [0 -1]         [0 1]
   [1 -1]  [1 0]  [1 1]])

(defn adjacent-positions
  "Get all valid adjacent positions for a given [row col] in the grid"
  [grid row col]
  (let [rows (count grid)
        cols (count (first grid))]
    (->> directions
         (map (fn [[dr dc]] [(+ row dr) (+ col dc)]))
         (filter (fn [[r c]] (and (>= r 0) (< r rows)
                                  (>= c 0) (< c cols)))))))

(defn adjacent-values
  "Get all values at adjacent positions for a given [row col] in the grid"
  [grid row col]
  (map (fn [[r c]] (get-in grid [r c]))
       (adjacent-positions grid row col)))

(defn count-adjacent-at
  "Count how many adjacent cells have the given value"
  [grid row col value]
  (count (filter #(= % value) (adjacent-values grid row col))))

(defn part1
  "Solve Part 1 - count @ positions with less than 4 adjacent @"
  [input]
  (let [rows (count input)
        cols (count (first input))]
    (->> (for [r (range rows)
               c (range cols)
               :when (= (get-in input [r c]) \@)
               :when (< (count-adjacent-at input r c \@) 4)]
           [r c])
         count)))

(defn find-removable
  "Find all @ positions with less than 4 adjacent @"
  [grid]
  (let [rows (count grid)
        cols (count (first grid))]
    (for [r (range rows)
          c (range cols)
          :when (= (get-in grid [r c]) \@)
          :when (< (count-adjacent-at grid r c \@) 4)]
      [r c])))

(defn remove-positions
  "Remove positions by replacing them with ."
  [grid positions]
  (reduce (fn [g [r c]] (assoc-in g [r c] \.)) grid positions))

(defn removal-step
  "Perform one removal step, returns [new-grid removed-count]"
  [grid]
  (let [removable (find-removable grid)]
    [(remove-positions grid removable) (count removable)]))

(defn part2
  "Solve Part 2 - keep removing accessible @ until none left"
  [input]
  (->> (iterate (fn [[grid _]] (removal-step grid)) [input 0])
       (drop 1)
       (take-while (fn [[_ removed]] (pos? removed)))
       (map second)
       (reduce +)))

(defn solve
  "Run Day 4 with benchmarking"
  []
  (let [input (parse-input (core/read-input 4 "input"))]
    (println "Day 4:")
    (core/run-parts part1 part2 input :runs 100)))

(defn -main
  "Entry point for Day 4"
  [& args]
  (solve))
