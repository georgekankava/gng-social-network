<?xml version="1.0" encoding="ISO-8859-1" ?>
<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page"
	xmlns:sec="http://www.springframework.org/security/tags"
	xmlns:c="http://java.sun.com/jsp/jstl/core" version="2.0">
	<jsp:directive.page contentType="text/html; charset=ISO-8859-1"
		pageEncoding="ISO-8859-1" session="false" />
	<jsp:output doctype-root-element="html"
		doctype-public="-//W3C//DTD XHTML 1.0 Transitional//EN"
		doctype-system="http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"
		omit-xml-declaration="true" />
	<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<!-- Atmosphere -->
<script type="text/javascript" src="resources/scripts/jquery-1.9.0.js"></script>
<!-- Application -->
<script type="text/javascript" src="resources/scripts/application.js"></script>
<script type="text/javascript" src="resources/scripts/jquery.atmosphere.js"></script>
<title>Insert title here</title>

    <style>
        * {
            font-family: tahoma;
            font-size: 12px;
            padding: 0px;
            margin: 0px;
        }

        p {
            line-height: 18px;
        }

        div {
            width: 500px;
            margin-left: auto;
            margin-right: auto;
        }

        #content {
            padding: 5px;
            background: #ddd;
            border-radius: 5px;
            border: 1px solid #CCC;
            margin-top: 10px;
        }

        #header {
            padding: 5px;
            background: #f5deb3;
            border-radius: 5px;
            border: 1px solid #CCC;
            margin-top: 10px;
        }

        #input {
            border-radius: 2px;
            border: 1px solid #ccc;
            margin-top: 10px;
            padding: 5px;
            width: 400px;
        }

        #status {
            width: 88px;
            display: block;
            float: left;
            margin-top: 15px;
        }
    </style>
</head>
<body>
<div id="header"><h3>Atmosphere Chat. Default transport is WebSocket, fallback is long-polling</h3></div>
<div id="content"></div>
<div>
    <span id="status">Connecting...</span>
    <input type="text" id="input" disabled="disabled"/>
</div>

</body>
</html>
</jsp:root>