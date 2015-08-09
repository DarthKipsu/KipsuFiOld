(defproject kipsufi "0.1.0-SNAPSHOT"
  :description "darth.kipsu.fi website"
  :url "http://darth.kipsu.fi"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[clj-time "0.10.0"]
                 [cljs-ajax "0.3.14"]
                 [com.jquery/jquery "1.9.1"]
                 [compojure "1.4.0"]
                 [hiccup "1.0.5"]
                 [hickory "0.5.4"]
                 [java-jdbc/dsl "0.1.3"]
                 [kerodon "0.6.1"]
                 [org.clojure/clojure "1.7.0"]
                 [org.clojure/data.json "0.2.6"]
                 [org.clojure/java.jdbc "0.4.1"]
                 [postgresql/postgresql "9.3-1102.jdbc41"]
                 [ring/ring-jetty-adapter "1.3.2"]
                 [ring/ring-json "0.4.0"]]
  :main kipsufi.web
  :plugins [[com.cemerick/clojurescript.test "0.3.3"]
            [lein-lesscss "1.2"]
            [lein-npm "0.6.1"]
            [lein-ring "0.9.6"]]
  :profiles {:dev {:dependencies [[midje "1.7.0"]]
                   :plugins [[lein-midje "3.1.3"]]
                   :resource-paths ["resource-dev"]}
             :uberjar {:aot :all}
             :prod {:resource-paths ["resource-prod"]}}
  :lesscss-paths ["src/less"]
  :cljsbuild {:builds []
              :test-commands nil}
  :lesscss-output-path "resources/public/css"
  :ring {:handler kipsufi.web/app})
