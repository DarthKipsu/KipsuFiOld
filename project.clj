(defproject kipsufi "0.1.0-SNAPSHOT"
  :description "darth.kipsu.fi website"
  :url "http://darth.kipsu.fi"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [org.clojure/java.jdbc "0.3.7"]
                 [org.clojure/clojurescript "0.0-3211"]
                 [reagent "0.5.0"]
                 [cljsjs/react "0.13.3-0"]
                 [cljsjs/react-router "0.13.2-0"]
                 [cljs-ajax "0.3.11"]
                 [java-jdbc/dsl "0.1.2"]
                 [postgresql/postgresql "9.3-1102.jdbc41"]
                 [ring/ring-jetty-adapter "1.3.2"]
                 [ring/ring-json "0.3.1"]
                 [org.clojure/data.json "0.2.6"]
                 [compojure "1.3.4"]
                 [hiccup "1.0.5"]
                 [clj-time "0.9.0"]]
  :main kipsufi.web
  :plugins [[lein-ring "0.9.4"]
            [lein-cljsbuild "1.0.6"]
            [lein-lesscss "1.2"]]
  :cljsbuild {
    :builds [{
      :source-paths ["src/cljs"]
      :compiler {
        :output-to "resources/public/js/script.js"
        :optimizations :whitespace
        :pretty-print true}}]}
  :lesscss-paths ["src/less"]
  :lesscss-output-path "resources/public/css"
  :ring {:handler kipsufi.web/app})
