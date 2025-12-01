(defproject advent-of-code-2025 "0.1.0-SNAPSHOT"
  :description "Advent of Code 2025 solutions in Clojure"
  :url "https://adventofcode.com/2025"
  :license {:name "MIT"
            :url "https://opensource.org/licenses/MIT"}
  :dependencies [[org.clojure/clojure "1.11.1"]]
  :main ^:skip-aot advent-of-code.core
  :target-path "target/%s"
  :source-paths ["src"]
  :resource-paths ["resources"]
  :profiles {:uberjar {:aot :all
                       :jvm-opts ["-Dclojure.compiler.direct-linking=true"]}
             :dev {:dependencies [[nrepl "1.0.0"]]}})
