<%@ include file="initPage.jsp" %>
<html>
<head>
  <title>Concourse Suite Community Edition</title>
  <meta http-equiv="refresh" content="1;URL=<%= request.getScheme() %>://<%= getServerUrl(request) + "/" + request.getParameter("redirectTo") %>">
</head>
</html>