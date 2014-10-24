<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<div class="jumbotron">
	<div class="show-post-like-friends-like container">
		<button type="button" class="close margin-right-5px" data-dismiss="alert" aria-hidden="true">&times;</button>
		<h4 class=""><spring:message code="post.like.header.title.message" text="Message file not found"></spring:message></h4>
		<div class="show-post-like-friends-like-header"></div>
		<div class="show-post-like-friends-like-body container">
			<c:forEach items="${postLikeFriendList }" var="postLikeFriend">
				<div class="row">
					<div class="col-md-4"><div><img alt="Profile Picture" class="img-thumbnail" width="65" src="images?id=${postLikeFriend.id}&profileImage=true" /></div></div>
					</a><div class="col-md-8"><span><a href="profile?userId=${postLikeFriend.id }" >${postLikeFriend.fullname }</a></span></div>
				</div>
			</c:forEach>
		</div>
	</div>
</div>