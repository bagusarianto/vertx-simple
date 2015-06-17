(function () {
    angular.module("simple-example", ["knalli.angular-vertxbus"])
        .controller("MainController", ["$scope", "vertxEventBusService", function ($scope, vertxEventBusService) {
            vertxEventBusService.registerHandler("news.feed", function (a) {
                $scope.message = a;
            });
        }]);
    angular.bootstrap(document, ["simple-example"]);
}());