var pathname = window.location.pathname;
var baseUrl = 'http://' + window.location.host + pathname.substring(pathname.indexOf('/', 0), pathname.indexOf('/', 1));

function addPost() {
	var url = baseUrl + '/add-post.ajax';
	var username = $('#username').html();
	var postContent = $('input[name="post-content"]').val();
	$.ajax({
		  url: url,
		  data: { 
			  username: username,
			  postContent: postContent
		  },
		  success: function(post) {
			  var content = post.postContent;
			  var re = new RegExp("^(https://|https://www.|http://|http://www.|www.|//www.)(\\w*)\\.(.*)");
			  var datetime = new Date(post.date);
			  var fullname = $('#fullname').html();
			  var date = months[datetime.getMonth()] + " " + datetime.getDate() + ', ' + datetime.getFullYear() + ' ' +  
			  		(datetime.getHours() < 10 ? '0' + datetime.getHours() : datetime.getHours())  + ':' +  
			  		(datetime.getMinutes() < 10 ? '0' + datetime.getMinutes() : datetime.getMinutes());
			  var formSearch = $('.form-inline').after(
				      $('<blockquote>').attr('id', 'post-content-' + post.postId).append(
				    		  $('<button>&times;</button>').addClass('close').attr('title', 'Remove Post').attr('id', 'post-remove-button-' + post.postId).attr('type', 'button')
				      ));
			  if(re.test(content)) {
				  if(content.search('youtube.com') != -1) {
					  formSearch.append(
					          $('<iframe>').attr('width', 420).attr('height', 315).attr('src', content).attr('allowfullscreen', '').attr('frameborder', '0')
					  );
				  } else {
					 formSearch.append($('<a>').attr('href', content).html(content));
				  }
			  } else {
				  formSearch.append(
					$('<p>').html(content)
				  );  
			  }
			  formSearch.append(
			    	  $('<small>').append($('<cite>').attr('title', fullname).text(fullname + ' ')).append($('<cite>').attr('title', date).text(date))	  
		      ).append(
		    	  $('<fieldset>').addClass('comment-div-' + post.postId).attr('id', 'comment-input-' + post.postId).append(
		    			  $('<input>').attr('type', 'text').attr('id', 'comment-' + post.postId).attr('placeholder', 'Type something')
		    	  ).append(
		    			  $('<input>').attr('type', 'hidden').attr('id', 'post-id-' + post.postId).attr('value', post.postId)
		    	  )
		      );
			  $('#comment-' + post.postId).keydown(function(e) {  if (e.keyCode === 13) { addComment(post.postId, fullname);}});
			  $('#post-remove-button-' + post.postId).bind('click', function() {removePost(post.postId);});
		  },
		  error: function(data) {
			  $('body').append('<div class="label label-danger">error on the server</div>');
		  }
		});
}

function removePost(postId) {
	var url = baseUrl + '/remove-post.ajax';
	var post = $('#post-content-' + postId);
	if(post == null || postId == null) return;
	$.ajax({
		  url: url,
		  data: { postId: postId},
		  success: function(data) {
			  post.remove();
		  }
		});
	return false;
}

function likeOrUnlikePost(userId, postId) {
	var url = baseUrl + '/like-unlike-post.ajax';
	var likeAction = $('#post-like-link-' + postId).html();
	$.ajax({
		url: url,
		data: { userId: userId, postId: postId, likeAction: likeAction},
		beforeSend: function(jqXHR, settings) {
			// empty
		}
	}).done(function(data) {
		if(data.postLikeText == 'Liked') {
			$('#post-like-link-' + data.postId).removeClass('label-info').addClass('label-warning').html('Unlike');
			if((data.numberOfLikes - 1) == 0 ) {
				$('#post-like-link-' + data.postId).after($('<span>').attr('style', "margin-left:2px;cursor:pointer")
					.addClass("badge badge-success post-liked-badge-" + data.postId).text('You like this'));
			} else {
				$('#post-like-link-' + data.postId).after($('<span>').attr('style', "margin-left:2px;cursor:pointer")
						.addClass("badge badge-success post-liked-badge-" + data.postId).text('You and other ' + (data.numberOfLikes - 1) + ' like this'));
			}
		} else if(data.postLikeText == 'Unliked') {
			$('#post-like-link-' + data.postId).removeClass('label-warning').addClass("label-info").html('Like');
			$('.post-liked-badge-' + data.postId).remove();
		}
	});
}

function showPostLikeFriendsList(postId) {
	var url = baseUrl + '/show-post-like-friends-list.ajax';
	var userId = $('#userId').text();
	$.ajax({
		url: url,
		data: {userId: userId, postId: postId}
	}).done(function(response) {
		var width = $(window).width();
		var height = $(window).height() / 4;
		$('.container').after(response);
		$('.show-post-like-friends-like').attr('style', 'position:fixed;top:' + height + 'px;left:' + width / 3 + 'px;width:' + width / 4 + 'px;');
	});
}


function addComment(postId, fullname) {
	var url = baseUrl + '/add-comment.ajax';
	var comment = $('#comment-' + postId).val();
	var username = $('#username').html();
	$.ajax({
		  url: url,
		  data: { username: username, 
			  	  postId: postId, 
			  	  commentContent: comment},
		  success: function(data) {
			  var datetime = new Date();
			  var commentId = data;
			  $('#comment-id-' + postId + '-' + commentId).val(commentId);
			  var date = months[datetime.getMonth()] + " " + datetime.getDate() + ', ' + datetime.getFullYear() + ' ' + 
			  				    (datetime.getHours() < 10 ? '0' + datetime.getHours() : datetime.getHours())  + ':' + 
			  					(datetime.getMinutes() < 10 ? '0' + datetime.getMinutes() : datetime.getMinutes());
			  $('.comment-div-' + postId).before(
					  $('<blockquote>').attr('id', 'comment-content-' + postId + '-' + commentId).append(
					  $('<button>&times;</button>').attr('id', 'comment-remove-button-' + postId + '-' + commentId).attr('type', 'button').addClass('close')).append(
				      $('<input>').attr('id', 'comment-id-' + postId +'-' + commentId).attr('type', 'hidden').attr('value', commentId)).append(
				      $('<p>').text(comment)).append(
				      $('<small>').append($('<cite>').attr('title', fullname).text(fullname + ' ')).append($('<cite>').attr('title', date).text(date)))
			  );
			  $('#comment-' + postId).val('');
			  $('#comment-remove-button-' + postId + '-' + commentId).bind('click', function() {removeComment(postId, commentId);});
			  subSocket.push(jQuery.stringifyJSON({ commentId: commentId }));
		  }
	});
}

function getContent() {
	var url = baseUrl + '/find-friends.ajax';
	var username = $('input[name="username"]').val();
	$.ajax({
		url: url,
		data: { username: username},
	}).done(function(data) {
		$('.result').html(data);
	}); 
}

function searchPeople(searchString) {
	var url = baseUrl + '/search-people.ajax';
	$.ajax({
		  url: url,
		  data: { searchString: searchString},
		  success: function(data) {
			  var peopleJson = jQuery.parseJSON(data);
			  if(peopleJson.errorMessage != null) {
				  
			  }
			  showSearchList(peopleJson);
		  }
		});
	
}

function getOnlineUsers(userId) {
	subSocket.push(jQuery.stringifyJSON({ userId: userId, getOnlineUsers: true }));
}

function sendFriendRequest(fromId, toId) {
	var url = baseUrl + '/send-friend-request.ajax';
	$.ajax({
		  url: url,
		  data: { fromId: fromId, toId: toId},
		  success: function(data) {
			  alert("Request sent");
		  }
		});
	return false;
}

function acceptFriendRequest(friendRequestId) {
	var url = baseUrl + '/accept-friend-request.ajax';
	$.ajax({
		  url: url,
		  data: { friendRequestId: friendRequestId},
		  success: function(data) {
			var user = jQuery.parseJSON(data);
			$('.friends-vertical-menu').append('<div><a href="profile?userId=${' + user.id + '}">' + user.fullname + '</a></div>');
			$('#add-friend-link-' + user.id).fadeOut('slow');
			$('#new-friend-' + user.id ).fadeIn('slow');
		  }
		});
	return false;
}

function denyFriendRequest(friendRequestId) {
	var url = baseUrl + '/deny-friend-request.ajax';
	$.ajax({
		  url: url,
		  data: { friendRequestId: friendRequestId},
		  success: function(data) {
			  var user = jQuery.parseJSON(data);
			  $('#friends-content').append('<div id="new-friend-' + user.id + '" style="display:none">' +
				'<a href="contact/profile.html?userId=' + user.id + '">' + user.fullname + '</a>' +
				'<a onclick="onUserClick(\'' + user.username + '\', \'' + user.fullname + '\');">' +
				'<img alt="write message" width="30px" src="resources/images/write_message.jpg" /></a>' +
			'</div>');
			$('#add-friend-link-' + user.id).fadeOut('slow');
			$('#new-friend-' + user.id ).fadeIn('slow');
		  }
		});
	return false;
}

function removeComment(postId, commentId) {
	var url = baseUrl + '/remove-comment.ajax';
	var comment = $('#comment-content-' + postId + '-' + commentId);
	if(comment == null || commentId == null) return;
	$.ajax({
		  url: url,
		  data: { commentId: commentId},
		  success: function(data) {
			  comment.remove();
		  }
		});
	return false;

}

function getMessages(userId,friendUserId,millies,initial) {
	var url = baseUrl + '/get-messages.ajax';
	$.ajax({
		  url: url,
		  data: { userFromId: userId, userToId: friendUserId, fromMillies: millies},
		  success: function(data) {
			  var json = jQuery.parseJSON(data);
			  if(initial) {
				  $.each(json.messages, function(index, message) {
				  if(message.author == $('#userId').html()) {
					  addMessage(message.authorFullname, message.receiver, message.message, 'blue', new Date(message.time));
				  } else {
					  addMessage(message.authorFullname, message.author, message.message, '#000', new Date(message.time));
				  }
				  
				  });
			  } else {
				  $(json.messages).reverse().each(function(index, message) {
					  if(message.author == $('#userId').html()) {
						  prependMessage(message.authorFullname, message.receiver, message.message, 'blue', new Date(message.time));
					  } else {
						  prependMessage(message.authorFullname, message.author, message.message, '#000', new Date(message.time));
					  }
					  
		          });
			  }
			  lastMessageMillies = json.messageMillies;
			  // if initial scroll to bottom
			  if(initial) {
			    scrollChatWindowBottom(friendUserId);
			  }
		  }
	});
}


function readPostURLContent(postUrl) {
	var url = baseUrl + '/read-post-url.ajax';
	$.ajax({
		url: url,
		data: {postUrl: postUrl},
	}).done(function(data) {
		
		$( data ).each(function() {
			if(this.tagName === 'meta') {
				$('#postedUrlDescription').html(this.attributes.content);
			} else if(this.tagName === 'img') {
				postUrlImages.push(this.attributes.src);
			}
		});
	});
	
}