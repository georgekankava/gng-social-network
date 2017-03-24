var pathname = window.location.pathname;
var baseUrl = 'http://' + window.location.host + pathname.substring(pathname.indexOf('/', 0), pathname.indexOf('/', 1));

var subSocket = null;

$(function () {
    "use strict";

    var content = $('#content');
    var input = $('#input');
    var status = $('#status');
    var author = $('#userId').html();
    var socket = $.atmosphere;
    var request = { url: baseUrl + '/meteor?userId=' + author,
                    contentType : "application/json",
                    logLevel : 'debug',
                    transport : 'websocket',
                    fallbackTransport: 'long-polling'};


    request.onOpen = function(response) {
        content.html($('<p>', { text: 'Chat connected'}));
        status.text('Choose user to Chat:');
    };

    request.onMessage = function (response) {
        var message = response.responseBody;
        try {
            var json = jQuery.parseJSON(message);
        } catch (e) {
            console.log('This doesn\'t look like a valid JSON: ', message);
            return;
        }
        if(json.postId != null && json.commentId != null) {
        	$('.comment-div-' + json.postId).before(
        			$('<blockquote>').attr('id', 'post-content' + json.postId + '-' + json.commentId).append(
        			$('<button>&times;</button>').attr('id', 'comment-remove-button-' + json.postId + '-' + json.commentId).attr('type', 'button').addClass('close')).append(
        			$('<input>').attr('type', 'hidden').attr('id', 'comment-id-' + json.postId + '-' + json.commentId).attr('value', json.commentId)).append(
        			$('<p>').text(json.comment)).append(
        			$('<small>').append($('<cite>').attr('title', json.fullname).text(json.fullname + ' ').append($('<cite>').attr('title', json.commentDate).text(json.commentDate))))
        	);
        }
        if($.isArray(json)) {
        	$.each(json, function(index, user) {
        		$('#friends-content').append(
        			$('<div>').append(
        				$('<a>').attr('href', 'contact/profile?userId=' + user.userId).html(user.firstName + ' ' + user.lastName),
        				$('<a>').attr('onclick', 'openChatWindow(' + user.userId + ', \'' + user.firstName + ' ' + user.lastName + '\');').append(
        					$('<img>').attr('alt', 'write message').attr('width', '25px').attr('src', 'resources/images/write_message.jpg')
        				)
        			)
        		);
        	});
        }
        if(json.message != null) {
        	if($('#chat-window-' + json.author).html() != undefined) {
        		addMessage(json.authorFullname, json.author, json.message, 'black', new Date(json.time));
        		$('#chat-window-' + json.author).topScroll('400');
        	} else {
        		openChatWindow(json.author, json.authorFullname);
        		addMessage(json.authorFullname, json.author, json.message, 'black', new Date(json.time));
        	}
        	if($('#messagesTable') != undefined) {
                var $scope = angular.element('div[ng-controller="MessagesController"]').scope();
                $scope.messages.messages = $scope.messages.messages.concat([
                    {message : json.message}
                ]);
                $scope.$apply();
            }
        	
        }
    };

    request.onError = function(response) {
        content.html($('<p>', { text: 'Sorry, but there\'s some problem with your '
            + 'socket or the server is down' }));
    };

    request.onClose = function(response) {
    };

    subSocket = socket.subscribe(request);

    input.keydown(function(e) {
        if (e.keyCode === 13) {
            var msg = $(this).val();

            var receiver = $('#receiver').html();
            if(receiver == "") {
            	alert('please select receiver');
            	return;
            }
            subSocket.push(jQuery.stringifyJSON({ author: author, receiver: receiver , message: msg }));
            $(this).val('');
            
            var me = true;
            addMessage(author, msg, me ? 'blue' : 'black', new Date());
            
        }
    });

});

function onUserClick(receiverId, receiverName){
	$('#chat').show();
	$('#receiver').html(receiverId);
	$('#status').text(receiverName + ': ');
	$('input').focus();
}
