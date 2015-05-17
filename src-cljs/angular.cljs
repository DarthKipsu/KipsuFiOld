(ns kipsufi.script.angular
  (:require [goog.object :as o])
  (:use-macros [purnam.core :only [obj arr ! def.n]]
               [gyr.core :only [def.module def.config def.factory
                                def.controller def.service]]))

(def.module kipsufi [])

(def.config kipsufi [$routeProvider]
            (-> $routeProvider
              (.when "/" (obj :controller "MainController" :templateUrl "views/index.html"))
              (.otherwise (obj :redirectTo "/"))))

(enable-console-print!)

(println "Hello world!")
