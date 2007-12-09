<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%
  response.setHeader("Pragma", "no-cache"); // HTTP 1.0
  response.setHeader("Cache-Control", "no-cache"); // HTTP 1.1
  response.setHeader("Expires", "-1");
%>
<html>
<head>
<%@ include file="initPage.jsp" %>
<META HTTP-EQUIV="refresh" content="2;URL=https://<%= getServerUrl(request) %>">
<title><dhv:label name="templates.CentricCRM">Concourse Suite Community Edition</dhv:label></title>
</head>
<body>
<center><dhv:label name="calendar.redirectingToSecureLogin.text">Redirecting to secure login...</dhv:label></center>
</body>
</html>