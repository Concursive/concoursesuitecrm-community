<%--
 Copyright 2000-2004 Matt Rajkowski
 matt.rajkowski@teamelements.com
 http://www.teamelements.com
 This source code cannot be modified, distributed or used without
 permission from Matt Rajkowski
--%>
<jsp:useBean id="projectView" class="java.lang.String" scope="session"/>
<html>
<head>
</head>
<body onload='page_init();'>
<script language='Javascript'>
function page_init() {
<% if ("MAXIMIZED".equals(projectView)) { %>
  parent.doMax();
<% } else { %>
  parent.doMin();
<% } %>
}
</script>
</body>
</html>

