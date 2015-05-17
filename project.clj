(defproject kipsufi "0.1.0-SNAPSHOT"
  :description "darth.kipsu.fi website"
  :url "http://darth.kipsu.fi"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [org.clojure/java.jdbc "0.3.6"]
                 [org.clojure/clojurescript "0.0-3211"]
                 [im.chit/purnam "0.5.2"]
                 [im.chit/gyr "0.3.1"] 
                 [java-jdbc/dsl "0.1.2"]
                 [postgresql/postgresql "8.4-702.jdbc4"]
                 [ring/ring-jetty-adapter "1.2.1"]
                 [ring/ring-json "0.3.1"]
                 [org.clojure/data.json "0.2.5"]
                 [compojure "1.3.0"]
                 [hiccup "1.0.5"]]
  :plugins [[lein-ring "0.9.3"]
            [lein-cljsbuild "1.0.6"]]
  :cljsbuild {
    :builds [{
      :source-paths ["src-cljs"]
      :compiler {
        :output-to "resources/public/js/script.js"
        :optimizations :whitespace
        :pretty-print true}}]}
  :ring {:handler kipsufi.web/app})
