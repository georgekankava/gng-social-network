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

    <link rel="stylesheet" href="resources/css/all.css?starttime=${startDate.time}" type="text/css" />
    <!-- Latest compiled and minified CSS -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">

    <!-- Optional theme -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css" integrity="sha384-rHyoN1iRsVXV4nD0JutlnGaslCJuC7uwjduW9SVrLvRYooPp2bWYgmgJQIXwl/Sp" crossorigin="anonymous">
</head>
<body>
    <jsp:include page="inc/header.jsp"><jsp:param name="fullname" value="${fullname}"></jsp:param></jsp:include>
    <div class="container">
            <div class="dropdown">
                <ul class="dropdown-menu" aria-labelledby="dLabel">
                    ...
                </ul>
            </div>
            <div class="row" style="padding: 10px">
                <h3>Your Friends</h3>
                <c:forEach var="friend" items="${friendsList}">
                    <div class="row">
                        <div class="col-md-6">
                            <img src="images?id=${friend.id}&profileImage=true" width="140" class="img-circle" />
                        </div>
                        <div class="col-md-6">
                            <a href="/profile?userId=${friend.id}"><c:out value="${friend.fullname}" /></a>
                        </div>
                    </div>
                </c:forEach>
            </div>
    </div>
    <script type="text/javascript" src="resources/scripts/jquery-3.1.0.min.js"></script>
    <script type="text/javascript" src="resources/scripts/bootstrap-3.3.7.min.js"></script>
</body>
</html>
