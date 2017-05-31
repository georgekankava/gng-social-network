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
        $http.get('/user-search-participation').then(function (response) {
            if (response.data.participatesInSearch) {
                $('#includeInSearchNo').removeClass('active');
                $('#includeInSearchYes').addClass('active');
            } else {
                $('#includeInSearchYes').removeClass('active');
                $('#includeInSearchNo').addClass('active');
            }
        });
        $http.get('/user-add-as-friend-strategy').then(function (response) {
            if (response.data === 'FIENDS_OF_FRIENDS') {
                $('#publicLookupStrategy').removeClass('active');
                $('#friendsOfFriendsLookupStrategy').addClass('active');
            } else {
                $('#friendsOfFriendsLookupStrategy').removeClass('active');
                $('#publicLookupStrategy').addClass('active');
            }
        });
        $scope.participateYes = function() {
            $http({
                url: "/participate-in-search.ajax",
                method: "POST",
                params: {
                    "participateInSearch" : true
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
        $scope.participateNo = function() {
            $http({
                url: "/participate-in-search.ajax",
                method: "POST",
                params: {
                    "participateInSearch" : false
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
        $scope.publicLookupStrategy = function() {
            $http({
                url: "/user-lookup-strategy",
                method: "POST",
                params: {
                    "userPrivacyEnum" : "PUBLIC"
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
        $scope.friendsOfFriendsLookupStrategy = function() {
            $http({
                url: "/user-lookup-strategy",
                method: "POST",
                params: {
                    "userPrivacyEnum" : "FIENDS_OF_FRIENDS"
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