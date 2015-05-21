(ns kipsufi.script.angular
  (:require [goog.object :as o])
  (:use-macros [purnam.core :only [obj arr ! def.n]]
               [gyr.core :only [def.module def.config def.factory
                                def.controller def.service]]))

(def.module kipsufi ["ngRoute"])

(def.config kipsufi [$routeProvider]
  (-> $routeProvider
    (.when "/"
            (obj :controller "MainController"
                :templateUrl "views/list.html"))
    (.when "/algorithms"
            (obj :controller "AlgorithmsController"
                :templateUrl "views/list.html"))
    (.when "/datastructures"
            (obj :controller "DatastructuresController"
                :templateUrl "views/list.html"))
    (.when "/algorithms/:name"
            (obj :controller "AlgorithmController"
                :templateUrl "views/show.html"))
    (.when "/datastructures/:name"
            (obj :controller "DatastructureController"
                :templateUrl "views/show.html"))
    (.otherwise (obj :redirectTo "/"))))

(def.controller kipsufi.MainController [$scope ApiService]
  (ApiService.list "/api/recent" $scope))

(def.controller kipsufi.AlgorithmsController [$scope ApiService]
  (ApiService.list "/api/algorithms" $scope))

(def.controller kipsufi.DatastructuresController [$scope ApiService]
  (ApiService.list "/api/datastructures" $scope))

(def.controller kipsufi.AlgorithmController [$scope $routeParams ApiService]
  (ApiService.data-for (str "/api/algorithms/" $routeParams.name) $scope))

(def.controller kipsufi.DatastructureController [$scope $routeParams ApiService]
  (ApiService.data-for (str "/api/datastructures/" $routeParams.name) $scope))

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
