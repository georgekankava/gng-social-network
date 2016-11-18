<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta charset="utf-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <meta name="description" content=""/>
    <meta name="author" content=""/>
<title>Profile</title>
	<link rel="stylesheet" href="resources/css/all.css?starttime=${startDate.time}" type="text/css" />
	<!-- Latest compiled and minified CSS -->
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">

	<!-- Optional theme -->
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css" integrity="sha384-rHyoN1iRsVXV4nD0JutlnGaslCJuC7uwjduW9SVrLvRYooPp2bWYgmgJQIXwl/Sp" crossorigin="anonymous">


</head>
<body>
    <sec:authentication property="principal.fullname" var="fullname"/>
    <jsp:include page="inc/header.jsp"><jsp:param name="fullname" value="${fullname}"></jsp:param></jsp:include>
    <div class="container" style="margin-top: 100px">
          <c:choose>
			  <c:when test="${isFriend eq false}">
				  <div class="row">
                      <div class="col-md-12">
					    <div class="profile-labels"><blockquote><p><a id="add-friend-link" href="#">Add Friend</a></p></blockquote></div>
                      </div>
				  </div>
			  </c:when>
		  </c:choose>
          <div class="row">
                <div class="col-md-6">
                    <div class="profile-labels"><blockquote><p>${user.fullname }</p></blockquote></div>
                    <div style="padding-left: 25px;"><img alt="Profile Picture" class="img-polaroid" src="images?id=${user.id}&profileImage=true" width="500" /></div>
                </div>
                <div class="col-md-3">
                      <div class="profile-labels"><blockquote><p>Friends</p></blockquote></div>
                          <table class="table table-hover">
                              <c:forEach var="friend" items="${friends}" varStatus="friendCounter">
                                  <tr>
                                      <td><img alt="Profile Picture" class="img-polaroid" width="20%" src="images?id=${friend.id}&profileImage=true" /></td>
                                      <td><a href="profile?userId=${friend.id }">${friend.fullname}</a></td>
                                  </tr>
                              </c:forEach>
                          </table>
                </div>
                <div class="col-md-3">
                    <div id='abc' class="profile-labels"><blockquote><p>Photos</p></blockquote></div>
                        <c:set var="counter" value="1"/>
                        <c:forEach var="image" items="${images}" varStatus="imageCounter">
                            <c:choose>
                                <c:when test="${counter eq 1}">
                                    <div style="display: block;margin: 10px 0px 0px 25px;">
                                    <span><img alt="Profile Picture" class="profile-images-size img-rounded" width="500" src="images?imageId=${image.id }&profileImage=false" /></span>
                                    <c:set var="counter" value="2"/>
                                </c:when>
                                <c:when test="${counter eq 3}">
                                    <span><img alt="Profile Picture" class="profile-images-size img-rounded" width="500" src="images?imageId=${image.id }&profileImage=false" /></span>
                                    </div>
                                    <c:set var="counter" value="1"/>
                                </c:when>
                                <c:otherwise>
                                    <span><img alt="Profile Picture" class="profile-images-size img-rounded" width="500" src="images?imageId=${image.id }&profileImage=false" /></span>
                                    <c:set var="counter" value="3"/>
                                </c:otherwise>
                            </c:choose>
                          </c:forEach>
                </div>
          </div>
    </div>
	<script type="text/javascript" src="resources/scripts/jquery-3.1.0.min.js"></script>
	<!-- Latest compiled and minified JavaScript -->
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>
    <script src="resources/scripts/all.js"></script>
    <script src="resources/scripts/ajax.js"></script>
    <script>
    	$('#add-friend-link').bind('click', function() {sendFriendRequest('${loggedUser.id}', '${user.id}');});
    </script>
</body>
</html>
