<%--
  Created by IntelliJ IDEA.
  User: georgekankava
  Date: 24.03.17
  Time: 9:56 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <base href="/">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
        <script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.6.4/angular.min.js"></script>
        <script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.6.4/angular-route.min.js"></script>
        <script src="resources/scripts/ng-controllers/settings.js" ></script>
        <script src="resources/scripts/ajax.js"></script>
        <script src="resources/scripts/all.js"></script>
        <!-- Latest compiled and minified CSS -->
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
        <!-- Optional theme -->
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css" integrity="sha384-rHyoN1iRsVXV4nD0JutlnGaslCJuC7uwjduW9SVrLvRYooPp2bWYgmgJQIXwl/Sp" crossorigin="anonymous">
        <!-- Latest compiled and minified JavaScript -->
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>
        <title>Settings</title>
    </head>
    <body ng-app="settingsApp">
        <jsp:include page="inc/header.jsp"><jsp:param name="fullname" value="${fullname}"></jsp:param></jsp:include>
        <sec:authentication property="principal.userId" var="userId"/>
        <span id="userId" style="display: none;">${userId}</span>
        <div class="container" ng-controller="ChangePasswordController">
            <div class="row">
                <div class="col-md-3">
                    <div class="btn-group-vertical" role="group" aria-label="">
                        <a class="btn btn-default" href="ChangePassword" role="button">Change Password</a>
                        <a class="btn btn-default" href="Privacy" role="button">Privacy</a>
                    </div>
                </div>
                <div class="col-md-9">
                    <div ng-view></div>
                </div>
            </div>
        </div>
    </body>
</html>
