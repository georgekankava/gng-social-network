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
</head>
<body>
    <c:forEach var="friend" items="${friendsList}">
        <c:out value="${friend.fullname}"/><p>
    </c:forEach>
</body>
</html>
