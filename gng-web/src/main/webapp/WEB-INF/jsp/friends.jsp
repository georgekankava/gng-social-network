<%--
  Created by IntelliJ IDEA.
  User: georgekankava
  Date: 8/5/16
  Time: 10:44 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Friends</title>
    <link href="resources/css/bootstrap.min.css" rel="stylesheet" media="screen">
</head>
<body>
    <div class="container">
            <div class="row" style="padding: 10px">
            <c:forEach var="friend" items="${friendsList}">
                <div class="row">
                    <div class="col-md-6">
                        <img src="images?id=${friend.id}&profileImage=true" width="140" class="img-circle" />
                    </div>
                    <div class="col-md-6">
                        <a href="#"><c:out value="${friend.fullname}" /></a>
                    </div>
                </div>
            </c:forEach>2
    </div>
    <script type="text/javascript" src="resources/scripts/jquery-3.1.0.min.js"></script>
    <script type="text/javascript" src="resources/scripts/bootstrap-3.3.7.min.js"></script>
</body>
</html>
