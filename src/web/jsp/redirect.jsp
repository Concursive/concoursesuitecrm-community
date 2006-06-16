<%@ include file="initPage.jsp" %>
<html>
<head>
  <title>Centric CRM</title>
  <meta http-equiv="refresh" content="1;URL=<%= request.getScheme() %>://<%= getServerUrl(request) + "/" + request.getParameter("redirectTo") %>">
</head>
</html>