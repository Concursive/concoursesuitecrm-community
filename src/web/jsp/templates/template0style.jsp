<%@ page  import="java.util.*,org.aspcfs.modules.base.*,org.aspcfs.controller.*" %>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<jsp:useBean id="ModuleBean" class="org.aspcfs.modules.beans.ModuleBean" scope="request"/>
<%
  response.setHeader("Pragma", "no-cache"); // HTTP 1.0
  response.setHeader("Cache-Control", "no-cache"); // HTTP 1.1
%>
<html>

<head>
<title>CFS<%= ((!ModuleBean.hasName())?"":": " + ModuleBean.getName()) %></title>
<link rel="stylesheet" href="css/template0<%= User.getBrowserIdAndOS() %>.css" type="text/css">
<link rel="stylesheet" href="css/template0.css" type="text/css">
</head>

<body leftmargin=0 rightmargin=0 margin=0 marginwidth=0 topmargin=0 marginheight=0>
<table border="0" width="100%">
  <tr>
    <td valign="top">
<% String includeModule = (String) request.getAttribute("IncludeModule"); %>          
<jsp:include page="<%= includeModule %>" flush="true"/>
    </td>
  </tr>
</table>
<!-- (C) 2001-2002 Dark Horse Ventures -->
</body>

</html>

