<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/security/tags"
	prefix="sec"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="">
<meta name="author" content="">

<!-- Le styles -->
<link href="resources/css/bootstrap.css" rel="stylesheet" media="screen">
<link href="resources/css/bootstrap-responsive.css" rel="stylesheet"
	media="screen">
<link href="resources/css/all.css" rel="stylesheet" media="screen">
<title>Home</title>
</head>
<body>
	<sec:authentication property="principal.fullname" var="fullname"/>
	<jsp:include page="inc/header.jsp"><jsp:param name="fullname" value="${fullname}"></jsp:param></jsp:include>
	<div class="container">
		<div class="row" style="margin-top: 60px">
			<div class="span3">
				<form action="add-photo.html" enctype="multipart/form-data" method="post">
					<div>
						<label for="image">Add Photo</label> <input type="hidden" name="userId" value="${userId }"> <input name="image" type="file" />
						<div><input type="submit" value="upload"/></div>
					</div>
				</form>
			</div>
			<div class="span7">
				<c:forEach items="${images }" var="image"  varStatus="imageCount">
					<span>
						<img alt="${image.name }" class="img-polaroid" style="height: 250px;width: 180px;" src="images?imageId=${image.id}&profileImage=false" />
					</span>
				</c:forEach>
			</div>
		</div>
		<div class="row">
			
		</div>
	</div>
	<script type="text/javascript" src="resources/scripts/jquery-1.9.0.js"></script>
	<script type="text/javascript" src="resources/scripts/bootstrap.js"></script>
	<script type="text/javascript" src="resources/scripts/ajax.js"></script>
	<script type="text/javascript" src="resources/scripts/all.js"></script>
</body>
</html>
