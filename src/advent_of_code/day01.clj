(ns advent-of-code.day01
  (:require [advent-of-code.core :as core]
            [clojure.string :as str]))

(def starting-position 50)

(defn parse-input
  "Parse the input for Day 1"
  [input]
  (core/parse-lines input))

(defn parse-step
  [step]
  (let [direction (first step)
        amount (Integer/parseInt (subs step 1))]
    [direction amount]))

(defn rotate
  "Rotates the dial and returns the new position"
  [position [direction amount]]
  (let [delta (if (= direction \L) (- amount) amount)]
    (mod (+ position delta) 100)))

(defn positions
  "Returns all positions from start through each step"
  [start-pos steps]
  (reductions rotate start-pos steps))

(defn count-position
  "Count how many times a position is visited"
  [positions target]
  (reduce (fn [cnt pos]
            (if (= pos target) (inc cnt) cnt))
          0
          positions))

(defn count-passes
  "Count how many times we pass through or land on 0 when moving."
  [pos dir distance]
  (if (zero? distance)
    0
    (let [first-hit (if (= dir \L)
                      (if (zero? pos) 100 pos)
                      (if (zero? pos) 100 (- 100 pos)))]
      (if (< distance first-hit)
        0
        (inc (quot (- distance first-hit) 100))))))

(defn count-all-passes
  "Count total times passing through or landing on 0"
  [start-pos steps]
  (first
    (reduce (fn [[cnt pos] [dir dist]]
              [(+ cnt (count-passes pos dir dist))
               (rotate pos [dir dist])])
            [0 start-pos]
            steps)))

(defn part1
  "Solve Part 1 - count times landing on 0"
  [steps start-pos]
  (count-position (positions start-pos steps) 0))

(defn part2
  "Solve Part 2 - count times passing through 0"
  [steps start-pos]
  (count-all-passes start-pos steps))

(defn solve
  "Run Day 1 with benchmarking"
  []
  (let [input (core/read-input 1 "input")
        steps (mapv parse-step (parse-input input))]
    (println "Day 1:")
    (core/run-parts #(part1 % starting-position) #(part2 % starting-position) steps :runs 10000)))

(defn -main
  "Entry point for Day 1"
  [& args]
  (solve))
