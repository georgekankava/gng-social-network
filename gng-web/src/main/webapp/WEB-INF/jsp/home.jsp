<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<meta name="description" content="">
	<meta name="author" content="">

	<!-- Le styles -->
	<link href="resources/css/all.css" rel="stylesheet" media="screen">
	
	<!-- Latest compiled and minified CSS -->
	<link rel="stylesheet" href="//netdna.bootstrapcdn.com/bootstrap/3.0.0/css/bootstrap.min.css">

	<!-- Optional theme -->
	<link rel="stylesheet" href="//netdna.bootstrapcdn.com/bootstrap/3.0.0/css/bootstrap-theme.min.css">
<title>Home</title>
</head>
<body>
	<sec:authentication property="principal.fullname" var="fullname"/>
	<sec:authentication property="principal.username" var="username"/>
	<sec:authentication property="principal.userId" var="userId"/>
	<jsp:include page="inc/header.jsp"><jsp:param name="fullname" value="${fullname}"></jsp:param></jsp:include>
	<span id="ctx" style="display: none;">${pageContext.request.contextPath}</span>
	<span id="username" style="display: none;">${username}</span>
	<span id="userId" style="display: none;">${userId}</span>
	<span id="fullname" style="display: none;">${fullname}</span>
	<div class="container">
		<div class="row">
			<div class="col-md-2">
				<div><img alt="Profile Picture" class="img-thumbnail profile-image" width="85" src="images?id=${userId}&profileImage=true" /></div>
				<div class="vertical-menu-header">
					Navigation Menu
				</div>
				<div class="vertical-menu">
					<div class="vertical-menu-inner">
						<div><a href="user-friends?userId=${userId}">Friends</a></div>
						<div><a href="#">Messages</a></div>
						<div><a href="#">Friend requests</a></div>
						<div><a href="photos.html?userId=${userId}">Photos</a></div>
						<div><a href="#">Events</a></div>
					</div>	
				 </div>
				 <div>
					<div id="friends-content" class="friends-box">
					<div class="vertical-menu-header" style="margin-top:0px;">
						Friends
					</div>
					<div class="friends-list vertical-menu">
						<div class="vertical-menu-inner friends-vertical-menu" style="height:120px;overflow:auto;">
							<c:forEach var="friend" items="${friends}">
							<c:choose>
								<c:when test="${friend.online eq true}">
									<div>
										<a onclick="openChatWindow('${friend.id}', '${friend.fullname}');" style="cursor:pointer"> ${friend.fullname} </a>
										<img alt="write message" width="25px" src="resources/images/write_message.jpg" />
									</div>
								</c:when>
								<c:otherwise>
									<div><a onclick="openChatWindow('${friend.id}', '${friend.fullname}');" style="cursor:pointer"> ${friend.fullname} </a></div>
								</c:otherwise>
							</c:choose>
							</c:forEach>
						</div>
					</div>
					</div>
				 </div>
				   <sec:authentication property="principal.friendRequests" var="friendRequests"/>
				<div>
					<div class="vertical-menu-header" style="margin-top:20px;">
						Friend Requests
					</div>
					<div class="friends-request-list vertical-menu">
						<div class="vertical-menu-inner" style="height:120px;overflow:auto;">
							<c:forEach var="friendRequest" items="${friendRequests}">
								<div>
									<span id="add-friend-link-${friendRequest.userFrom.id}">
										<a href="profile?userId=${friendRequest.userFrom.id }">${friendRequest.userFrom.fullname}</a>
										  <span id="add-friend-yes-link-${friendRequest.userFrom.id}">
											<a style="margin-left:3px;" href="#">yes</a>
										  </span>
										<span id="add-friend-no-link-${friendRequest.userFrom.id}">
										  <a href="" style="margin-left:3px;">no</a>
										  </span>
									  </span>
								</div>	
							</c:forEach>
						</div>
					</div>
				</div>
			</div>
			<div class="col-md-6 col-md-offset-1">
				<!-- Add Post Input -->
				<form class="form-inline" role="form" action="add-post.ajax" method="get">
					<div class="row">
						<div class="col-lg-offset-1 col-lg-8">
							<input type="text" name="post-content" class="form-control user-post-input" placeholder="What is on your mind?">
						</div>
						<div class="col-lg-2">
							<button type="button" id="post-button" class="btn btn-default">Post</button>
						</div>
					</div>
				</form>
				<div id="postedUrlFrame" style="display:none;width: 400px; margin: 15px 0px 0px 47px;">
					<div id="postedUrlImagesFrame">
						<img id="postedUrlImages" src="" width="400" class="img-thumbnail">
					</div>
					<div id="postedUrlDescription" style="margin: 10px 0px 10px 0px;"></div>
					<div id="postUrlButtons">
						<button id="previousPostUrlImageButton" type="button" class="btn btn-success">previous</button>
						<button id="nextPostUrlImageButton" type="button" class="btn btn-success">next</button>
					</div>
				</div>
				<c:forEach var="post" items="${posts}" varStatus="postCounter">
					<blockquote id="post-content-${post.id }">
						<c:if test="${post.user.id eq userId}">
							<button title="Remove Post" id="post-remove-button-${post.id  }" type="button" class="close">&times;</button>
						</c:if>
						<c:choose>
							<c:when test="${not empty post.url and fn:contains(post.url, 'youtube.com')}">
								<iframe width="420" height="315" src="${post.url }" frameborder="0" allowfullscreen></iframe>
							</c:when>
							<c:when test="${not empty post.url}">
								<a target="_blank" href="${post.url}">${post.url}</a>
							</c:when>
							<c:otherwise>
								<p>${post.text}</p>
							</c:otherwise>
						</c:choose>
						<c:choose>
							<c:when test="${not empty postLikes[post.id]}">
							<c:set var="numOfLikesExceptLoggedUser" value="${fn:length(post.likes) - 1 }" />
							<c:choose>
								<c:when test="${numOfLikesExceptLoggedUser eq '0'}">
									<h5><span style="cursor:pointer;margin-top:5px" class="label label-warning" id="post-like-link-${post.id }">Unlike</span><span style="margin-left:2px;" class="badge badge-success post-liked-badge-${post.id}">You like this</span></h5>
								</c:when>
								<c:otherwise>
									<h5><span style="cursor:pointer;margin-top:5px" class="label label-warning" id="post-like-link-${post.id }">Unlike</span><span style="margin-left:2px;cursor:pointer" class="badge badge-success post-liked-badge-${post.id}">You and other ${numOfLikesExceptLoggedUser} like this</span></h5>
								</c:otherwise>
							</c:choose>
							</c:when>
							<c:otherwise>
								<h5><span style="cursor:pointer;margin-top:0px" class="label label-info" id="post-like-link-${post.id }">Like</span></h5>
							</c:otherwise>
						</c:choose>
						<fmt:formatDate value="${post.postDate }" type="date" var="postDate"/>
						<small class="small-name"><cite title="${post.user.fullname}">   ${post.user.fullname}</cite> <cite title="${postDate }"><fmt:formatDate value="${post.postDate }" type="both" timeStyle="short"/></cite></small>
						<c:set var="postCommentCounter" value="0"></c:set>
						<c:forEach var="comment" items="${post.comments }" varStatus="commentCounter">
							<c:set var="userfullname" value="${comment.user.fullname }"></c:set> 
							<blockquote id="comment-content-${post.id  }-${comment.id }">
								<button id="comment-remove-button-${post.id  }-${comment.id }" type="button" class="close">&times;</button>
								<input type="hidden" id="comment-id-${post.id  }-${comment.id }" value="${comment.id }">
								<p>${comment.commentContent }</p>
								<c:set var="commentDate"><fmt:formatDate value="${comment.date }" type="date"/></c:set>
								<small><cite title="${userfullname }">${userfullname }</cite> <cite title="${commentDate }"><fmt:formatDate value="${comment.date }" pattern="hh:mm" /></cite></small>
							</blockquote>
							<c:set var="postCommentCounter" value="${comment.id  }"></c:set>
						</c:forEach>
						<div class="row comment-div-${post.id }" id="comment-input-${post.id }">
							<div class="col-md-5">
								<input type="text" class="form-control input-sm" id="comment-${post.id }" placeholder="Type something">
								<input type="hidden" id="post-id-${post.id }" value="${post.id }">
							</div>
						</div>
					</blockquote>
				</c:forEach>
			</div>
			<div class="col-md-3"></div>
		</div>
		<div id="chat" />
	</div>
	<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
    <script src="//code.jquery.com/jquery.js"></script>
	<script type="text/javascript" src="resources/scripts/jquery.atmosphere.js"></script>
	<script type="text/javascript" src="resources/scripts/application.js"></script>
	<!-- Latest compiled and minified JavaScript -->
	<script type="text/javascript" src="resources/scripts/ajax.js"></script>
	<script type="text/javascript" src="resources/scripts/all.js"></script>
	<!-- Latest compiled and minified JavaScript -->
	<script src="//netdna.bootstrapcdn.com/bootstrap/3.0.0/js/bootstrap.min.js"></script>
	<script type="text/javascript">
			<c:forEach var="post" items="${posts}" varStatus="postCounter">
				$('#comment-${post.id}').keydown(function(e) {  if (e.keyCode === 13) { addComment('${post.id}', '${fullname}');}});
				$('.post-liked-badge-${post.id}').bind('click', function() {showPostLikeFriendsList('${post.id}');});
				<c:if test="${post.user.id eq userId}">
					$('#post-remove-button-${post.id}').bind('click', function() {removePost('${post.id}');});
				</c:if>
				$('#post-like-link-${post.id }').bind('click', function() {likeOrUnlikePost('${userId}', '${post.id}');});
				<c:forEach var="comment" items="${post.comments }" varStatus="commentCounter">
					$('#comment-remove-button-${post.id}-${comment.id}').bind('click', function() {removeComment('${post.id}', '${comment.id}');});
				</c:forEach>
			</c:forEach>
			 $('#post-button').bind('click', function() {addPost();});
			<c:forEach var="friendRequest" items="${friendRequests}">
			  $('#add-friend-yes-link-${friendRequest.userFrom.id}').bind('click', function() {acceptFriendRequest('${friendRequest.id}');});
			  $('#add-friend-no-link-${friendRequest.userFrom.id}').bind('click', function() {denyFriendRequest('${friendRequest.id}');});
			</c:forEach>
			$('#previousPostUrlImageButton').bind('click', function() {showPreviousPostUrlImage();});
			$('#nextPostUrlImageButton').bind('click', function() {showNextPostUrlImage();});
	</script>
</body>
</html>
