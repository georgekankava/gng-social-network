
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<%--
  Created by IntelliJ IDEA.
  User: georgekankava
  Date: 04.01.17
  Time: 8:04 PM
  To change this template use File | Settings | File Templates.
--%>
<html ng-app="messages">
<head>
    <script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.6.1/angular.min.js"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.6.1/angular-resource.min.js">
    </script>
    <script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.6.1/angular-route.min.js"></script>
    <script src="../../resources/scripts/ng-controllers/messages.js" ></script>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
    <!-- Latest compiled and minified CSS -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
    <!-- Optional theme -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css" integrity="sha384-rHyoN1iRsVXV4nD0JutlnGaslCJuC7uwjduW9SVrLvRYooPp2bWYgmgJQIXwl/Sp" crossorigin="anonymous">
    <!-- Latest compiled and minified JavaScript -->
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>
    <title>Messages</title>
</head>
<body>
    <jsp:include page="inc/header.jsp"><jsp:param name="fullname" value="${fullname}"></jsp:param></jsp:include>
    <sec:authentication property="principal.userId" var="userId"/>
    <div class="container" ng-controller="MessagesController">
        <div class="col-md-3">
            <label>Users:</label>
            <table class="table table-striped">
                <c:forEach items="${users}" var="user" varStatus="loop">
                    <tr>
                        <td>
                            <div style="padding: 0px;">
                                <button ng-click="retriveUserMessages(${userId}, ${user.id}, 0)" id="message-button-${user.id}" class="btn btn-default" style="width: 250px;text-align: left;"><c:out value="${user.fullname}" /></button>
                            </div>
                        </td>
                    </tr>
                </c:forEach>
            </table>
        </div>
        <div class="col-md-9">
            <label>Messages:</label>
            <div>
                <table class="table table-hover" style="margin-bottom: 10px">
                    <tr ng-repeat="message in messages.messages">
                        <td>{{message.message}}</td>
                    </tr>
                </table>
            </div>
        </div>
    </div>
</body>
</html>
