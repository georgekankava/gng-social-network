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
                chatWindowMessages = $scope.messages.messages;
                $('#messageInput').show();
                $('#messageUserToId').html(userToId);
            });
        }
        $scope.sendMessage = function ($event, usereFromId) {
            if ($event.keyCode === 13) {
                var messageToUserId = $('#messageUserToId').html();
                var message = $('#messageInput').val();
                subSocket.push(jQuery.stringifyJSON({ author: usereFromId, receiver: messageToUserId , message: message }));
                $('#messageInput').val('');
                $scope.messages.messages = $scope.messages.messages.concat(
                    {message : message}
                );
            }
        }
    });