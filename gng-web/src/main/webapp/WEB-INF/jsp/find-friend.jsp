<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>Find Friends</title>
<script src="../resources/scripts/jquery-1.9.0.js"><jsp:text></jsp:text></script>
<script src="../resources/scripts/ajax.js"><jsp:text></jsp:text></script>
<link rel="stylesheet" href="../resources/css/all.css?starttime=${startDate.time}" type="text/css" ></link>
</head>
<body>
	<jsp:include page="inc/header.jsp" />
	<div style="margin-top:15px;">
		<form action="find-friends.ajax" onsubmit="return getContent()" method="post">
			<label for="username">Find Friends:</label>
			<input type="text" name="username" style="margin-left: 83px;" ></input> 
			<input type="submit" value="find friend" style="margin-left: 3px;"/></input>
		</form>
	</div>
	<div class="result"></div>
</body>
</html>
