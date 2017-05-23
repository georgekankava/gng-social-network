angular.module('settingsApp', ['ngRoute'])

    .controller('ChangePasswordController', function($scope, $http) {
        $scope.submit = function () {
            $http({
                url: "/process-change-password.ajax",
                method: "POST",
                params: {
                    "userId" : $('#userId').html(),
                    "currentPassword" : $("#currentPassword").val(),
                    "newPassword" : $("#newPassword").val(),
                    "confirmNewPassword" : $("#confirmNewPassword").val()
                }
            }).then(function(response) {
                $('#messageLabel').removeClass('alert alert-success');
                $('#messageLabel').removeClass('alert alert-danger');
                if(!response.data.errorMessage) {
                    $('#messageLabel').addClass('alert alert-success');
                } else {
                    $('#messageLabel').addClass('alert alert-danger');
                }
                $('#messageLabel').text(response.data.message);
            });
        }
    })
    .controller('PrivacyController', function ($scope, $http) {
        $scope.participateYes = function() {
            $http({
                url: "/participate-in-search.ajax",
                method: "POST",
                params: {
                    "userId" : $('#userId').html(),
                    "currentPassword" : $("#currentPassword").val(),
                    "newPassword" : $("#newPassword").val(),
                    "confirmNewPassword" : $("#confirmNewPassword").val()
                }
            }).then(function(response) {

            });
        }
        $scope.participateNo = function() {
            $http({
                url: "/participate-in-search.ajax",
                method: "POST",
                params: {
                    "userId" : $('#userId').html(),
                    "currentPassword" : $("#currentPassword").val(),
                    "newPassword" : $("#newPassword").val(),
                    "confirmNewPassword" : $("#confirmNewPassword").val()
                }
            }).then(function(response) {

            });
        }
    })

    .config(function($routeProvider, $locationProvider) {
        $routeProvider
            .when('/ChangePassword', {
                templateUrl: 'change-password.ajax',
                controller: 'ChangePasswordController',

            })
            .when('/Privacy', {
                templateUrl: 'privacy.ajax',
                controller: 'PrivacyController'
            });

        // configure html5 to get links working on jsfiddle
        $locationProvider.html5Mode(true);
    });