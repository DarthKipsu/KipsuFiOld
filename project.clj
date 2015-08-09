(defproject kipsufi "0.1.0-SNAPSHOT"
  :description "darth.kipsu.fi website"
  :url "http://darth.kipsu.fi"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[clj-time "0.9.0"]
                 [cljs-ajax "0.3.11"]
                 [com.jquery/jquery "1.9.1"]
                 [compojure "1.3.4"]
                 [hiccup "1.0.5"]
                 [hickory "0.5.4"]
                 [java-jdbc/dsl "0.1.2"]
                 [kerodon "0.6.1"]
                 [org.clojure/clojure "1.6.0"]
                 [org.clojure/data.json "0.2.6"]
                 [org.clojure/java.jdbc "0.3.7"]
                 [postgresql/postgresql "9.3-1102.jdbc41"]
                 [ring/ring-jetty-adapter "1.3.2"]
                 [ring/ring-json "0.3.1"]]
  :main kipsufi.web
  :plugins [[com.cemerick/clojurescript.test "0.3.3"]
            [lein-lesscss "1.2"]
            [lein-npm "0.5.0"]
            [lein-ring "0.9.4"]]
  :profiles {:dev {:dependencies [[midje "1.6.3"]]
                   :plugins [[lein-midje "3.1.3"]]}
             :uberjar {:aot :all}}
  :lesscss-paths ["src/less"]
  :cljsbuild {:builds []
              :test-commands nil}
  :lesscss-output-path "resources/public/css"
  :ring {:handler kipsufi.web/app})
