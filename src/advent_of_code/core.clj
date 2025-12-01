(ns advent-of-code.core
  (:require [clojure.string :as str])
  (:gen-class))

(defn read-input
  "Read the input file for a given day.
   Usage: (read-input 1) or (read-input 1 \"sample\")"
  ([day]
   (read-input day "input"))
  ([day filename]
   (slurp (str "resources/day" (format "%02d" day) "/" filename ".txt"))))

(defn parse-lines
  "Parse input into lines"
  [input]
  (str/split-lines input))

(defn parse-numbers
  "Parse input into numbers (one per line)"
  [input]
  (map #(Long/parseLong %) (parse-lines input)))

(defn benchmark
  "Run a function in batches, return [result best-avg-time-us].
   Runs 'batches' batches of 'per-batch' iterations each,
   and returns the best (minimum) average time."
  [f & {:keys [batches per-batch] :or {batches 5 per-batch 10000}}]
  (let [times (for [_ (range batches)]
                (let [start (System/nanoTime)
                      _ (dotimes [_ per-batch] (f))
                      end (System/nanoTime)]
                  (/ (/ (- end start) 1000.0) per-batch)))
        best-time (apply min times)]
    [(f) best-time]))

(defn run-parts
  "Run part1 and part2, show results, then benchmark."
  [part1-fn part2-fn input & {:keys [runs] :or {runs 10000}}]
  ;; Solve first
  (let [result1 (part1-fn input)
        result2 (part2-fn input)]
    (println (format "  Part 1: %s" result1))
    (println (format "  Part 2: %s" result2))
    ;; Then benchmark
    (let [[_ time1] (benchmark #(part1-fn input) :per-batch runs)
          [_ time2] (benchmark #(part2-fn input) :per-batch runs)]
      (println (format "  Part 1 time: %.3f us (best avg of %d runs)" time1 runs))
      (println (format "  Part 2 time: %.3f us (best avg of %d runs)" time2 runs)))))

(defn -main
  "Run solutions for Advent of Code 2025"
  [& args]
  (println "Advent of Code 2025")
  (println "==================")
  (println "Use the REPL to run individual day solutions"))
