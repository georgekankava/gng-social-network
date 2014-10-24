<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div>
	<c:forEach items="${pageHtmlTags }" var="htmlTag">
		<c:choose>
			<c:when test="${htmlTag.tagName eq 'meta' }">
				<div>test${htmlTag.getAttribute('content')}</div>
			</c:when>
			<c:when test="${htmlTag.tagName eq 'img' }">
				<div><img src="${hostUrl}${htmlTag.getAttribute('src')}"/></div>
			</c:when>
		</c:choose>
		<div></div>
	</c:forEach>
</div>