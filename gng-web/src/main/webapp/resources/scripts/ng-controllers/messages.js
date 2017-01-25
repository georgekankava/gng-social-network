angular.module('messages', [])
    .controller('MessagesController', function($scope, $http) {
        $scope.retriveUserMessages = function (userFromId, userToId, fromMillies) {
            $http({
                url: "/get-messages.ajax",
                method: "GET",
                params: {
                    "userFromId" : userFromId, "userToId" : userToId, "fromMillies" : fromMillies
                }
            }).then(function(response) {
                $('.btn').removeClass('btn-info');
                $('.btn').addClass('btn-default');
                $('#message-button-' + userToId).removeClass('btn-default');
                $('#message-button-' + userToId).addClass('btn btn-info');
                $scope.messages = response.data;
                $('#messageInput').show();
                $('#messageUserToId').html(userToId);
            });
        }
        $scope.sendMessage = function ($event, usereFromId) {
            if ($event.keyCode === 13) {
                alert("Enter");
            }
        }
    });