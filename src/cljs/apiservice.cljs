(ns kipsufi.script.apiservice
  (:require [goog.object :as o])
  (:use-macros [purnam.core :only [obj arr ! def.n]]
               [gyr.core :only [def.module def.config def.factory
                                def.controller def.service]]))

(def.service kipsufi.ApiService [$http]
  (obj :list (fn [path $scope]
               (-> $http
                  (.get path)
                  (.success (fn [res] (! $scope.data res)))
                  (.error (fn [] (js/console.log "error")))))
        :data-for (fn [path $scope]
                    (-> $http
                      (.get path)
                      (.success (fn [res]
                                  (! $scope.name res.name)
                                  (! $scope.datastructures res.datastructures)
                                  (! $scope.advantages res.advantages)
                                  (! $scope.disadvantages res.disadvantages)
                                  (! $scope.content res.content)))
                      (.error (fn [] (js/console.log "error")))))))
