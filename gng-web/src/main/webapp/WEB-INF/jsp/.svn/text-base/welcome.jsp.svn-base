<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Welcome</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="">
	<link rel="stylesheet" href="//netdna.bootstrapcdn.com/bootstrap/3.0.0/css/bootstrap.min.css">
	<link rel="stylesheet" href="//netdna.bootstrapcdn.com/bootstrap/3.0.0/css/bootstrap-theme.min.css">
    <link rel="stylesheet" href="resources/css/login.css?starttime=${startDate.time}" type="text/css" >
    <script type="text/javascript" src="resources/scripts/jquery-1.9.0.js"></script>
	<script src="//netdna.bootstrapcdn.com/bootstrap/3.0.0/js/bootstrap.min.js"></script>
</head>
<body>
<div class="container">
    <div style="margin-top:20px;"></div>
    <div class="page-header">
    <h1>Welcome!</h1>
        <p>Please sign in or sign up if you don't have an existing account.</p>
    </div>
    <form:form modelAttribute="userSignupData" action="register-user.html" enctype="multipart/form-data" method="post">
        <div class="row"> 
            <div class="span1"><form:label path="username">Email:</form:label></div>
            <div class="span1"><form:input path="username" type="text"/></div>
            <div><form:errors path="username" cssClass="error"/></div>
        </div>
        <div class="row"> 
            <div class="span1"><form:label path="firstname">Firstname:</form:label></div>
            <div class="span1"><form:input path="firstname"/></div>
            <form:errors path="firstname" cssClass="error"/>
        </div>
        <div class="row">
            <div class="span1"><form:label path="lastname">Lastname:</form:label></div>
            <div class="span1"><form:input path="lastname"/></div>
            <form:errors path="lastname" cssClass="error"/>                
        </div>
        <div class="row">
            <div class="span1"><form:label path="password">Password:</form:label></div>
            <div class="span1"><form:password  path="password" /></div>
            <form:errors path="password" cssClass="error"/>
        </div>
        <div class="row">
            <div class="span1"><form:label path="confirmpassword">Confirm Password:</form:label></div>
            <div class="span1"><form:password path="confirmpassword"/></div>
            <form:errors path="confirmpassword" cssClass="error"/>
        </div>
        <div>
            <label for="image">Profile image:</label>
            <input name="image" type="file" />
        </div>
         <div class="login-inputs"><input type="submit" value="Sign Up"/></div>
    </form:form>
    <form action="j_spring_security_check" method="post">
        <c:if test="${!empty errorMessage }">
            <spring:message var="errorMessage" code="error.message.user.or.password.not.found"/>
            <div class="error-message">${errorMessage}</div>
        </c:if>
        <div class="row">
            <div class="span1"><label for="username">Username:</label></div>
            <div class="span1"><input type="text" name="j_username" id="username"/></div>
        </div>
        <div class="row">
            <div class="span1"><label for="password">Password:</label></div>
            <div class="span1"><input type="password" name="j_password" id="password"/></div>
        </div>
        <div class="row">
            <div class="span1"><input type="submit" value="Login"/></div>
        </div>
    </form>
</div>
</body>
</html>
