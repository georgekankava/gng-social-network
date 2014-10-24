$(document).ready(function() {
	
});

jQuery.fn.reverse = [].reverse;

$('#search-friends-input').keyup(function(e) {
	var searchString = $('#search-friends-input').val();
	if(e.keyCode == 27) {
		hideSearchBox();
		return;
	}
	if(searchString.length >= 2) {
		searchPeople(searchString);
	}
	if(searchString.length == 0) {
		$('#search-list-box').remove();
	}
});

$('.user-post-input').on("paste", function(event) {
	var element = this;
	setTimeout(function () {
		var postData = $(element).val();
		var re = new RegExp("^(https://|https://www.|http://|http://www.|www.|//www.)(\\w*)\\.(.*)");
		if(re.test(postData)) {
			readPostURLContent(postData);
		}
	}, 100);
	
});


function hideSearchBox() {
	$('#search-list-box').remove();
}

$(window).resize(function() {
	var activeWindowCount = 1;
	setSearchListPosition();
	$.each($('#chat').children(), function(index, value) {
		var width = $(window).width() - 230 * activeWindowCount;
		var height = $(window).height() - 315;
		$(value).addClass('chat-window').attr('style', 'left:' + width + 'px;top:' + height + 'px' );
		activeWindowCount++;
	});
});

function setSearchListPosition() {
	$('#search-list').attr('style', 'position:absolute;left:' + $('#search-friends-input').position().left + 'px;top:' + '51' + 'px;z-index:1');
}

function showSearchList(users) {
	if($('#search-list').children().length === 0) {
		$('#search-list').append($('<div id="search-list-box">').addClass('show-search-list'));
		setSearchListPosition();
	}
	$('#search-list-box').children().remove();
	var searchListBoxLength = 0;
	$.each(users, function(index, user) {
		var element1 = $('<div>').addClass('row').attr('style', 'height:62px;').append($('<div>').addClass('col-md-4').attr('id','search-result-img-' + user.userId).append($('<img>').addClass('img-thumbnail').attr('style', 'width:60px;height:60px;').attr('src','images?id=' + user.userId + '&profileImage=true')));
		$('#search-list-box').append(element1);
		var nameElement = $('<div>').addClass('col-md-8').attr('style', 'margin-left:0px;margin-top:5px;').append('<a href="profile?userId=' + user.userId+ '">' + user.fullname + '</a>');
		var userData = $('<div style="margin-left:5px;">').append(nameElement);
		if(user.city != null && user.country != null) {
			var locationElement = $('<div>').addClass('col-md-8').attr('style', 'margin-left:0px;').append(user.city + ", " + user.country);
			userData.append(locationElement);
		}
		element1.append(userData);
		searchListBoxLength += 60;
	});
	$('#search-list-box').attr('height', searchListBoxLength + 'px');
	
}

function openChatWindow(friendId, friendFullname) {
	var width = $(window).width() - 250 * activeChatWindowCount;
	var height = $(window).height() - 315;
	$('#chat').append(
		$('<div id="chat-window-box-' + friendId + '">').addClass('chat-window-box').attr('style', 'left:' + width + 'px;top:' + height + 'px;' ).append(
			$('<div id="chat-window-' + friendId + '">').addClass('chat-window').attr('style', 'overflow-y: scroll' )
		).append(
			$('<div class="chat-window-input">').append(
				$('<input>').attr('id', 'chat-input-' + friendId).attr('type', 'text')
			)
		)
	);
	$('#chat-input-' + friendId).keydown(function(e) {  if (e.keyCode === 13) {

        var msg = $(this).val();
        var userId = $('#userId').html();

        subSocket.push(jQuery.stringifyJSON({ author: userId, receiver: friendId , message: msg }));
        $(this).val('');
        var fullname =  $('#fullname').html();
        addMessage(fullname, friendId, msg, 'blue', new Date());
        scrollChatWindowBottom(friendId);
	}});
	getMessages($('#userId').html(),friendId,lastMessageMillies,true);
	$('#chat-window-' + friendId).scroll(function() {
		if($('#chat-window-' + friendId).scrollTop() === 0) {
			getMessages($('#userId').html(),friendId,lastMessageMillies,false);
		}
	});
	scrollChatWindowBottom(friendId);
	$('#chat-input-' + friendId).focus();
	activeChatWindowCount++;
	
}

function addMessage(fullname, friendId, message, color, datetime) {
    $('#chat-window-' + friendId).append('<div><span style="color:' + color + '">' + fullname + '</span> @ ' +
        + (datetime.getHours() < 10 ? '0' + datetime.getHours() : datetime.getHours()) + ':'
        + (datetime.getMinutes() < 10 ? '0' + datetime.getMinutes() : datetime.getMinutes())
        + ' ' + message + '</p>');
}

function prependMessage(fullname, friendId, message, color, datetime) {
    $('#chat-window-' + friendId).prepend('<div><span style="color:' + color + '">' + fullname + '</span> @ ' +
        + (datetime.getHours() < 10 ? '0' + datetime.getHours() : datetime.getHours()) + ':'
        + (datetime.getMinutes() < 10 ? '0' + datetime.getMinutes() : datetime.getMinutes())
        + ' ' + message + '</p>');
}

function scrollChatWindowBottom(windowId) {
	$('#chat-window-' + windowId).scrollTop('1400');
}
var months = ['January', 'February', 'March', 'April', 'May', 'June', 'July', 'August', 'September', 'October', 'November',	'December'];
var activeChatWindowCount = 1;
var lastMessageMillies = 0;