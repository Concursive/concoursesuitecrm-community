<%@ page  import="java.util.*,org.aspcfs.modules.base.*,org.aspcfs.controller.*" %>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<jsp:useBean id="ModuleBean" class="org.aspcfs.modules.beans.ModuleBean" scope="request"/>
<%
  response.setHeader("Pragma", "no-cache"); // HTTP 1.0
  response.setHeader("Cache-Control", "no-cache"); // HTTP 1.1
%>
<!-- (C) 2001-2004 Dark Horse Ventures -->
<html>
<head>
<title>Dark Horse CRM<%= ((!ModuleBean.hasName())?"":": " + ModuleBean.getName()) %></title>
<jsp:include page="cssInclude.jsp" flush="true"/>
</head>
<script language="JavaScript" type="text/javascript" src="javascript/trackMouse.js"></script>
<body leftmargin="0" rightmargin="0" margin="0" marginwidth="0" topmargin="0" marginheight="0">
<table border="0" width="100%">
  <tr>
    <td valign="top">
<% String includeModule = (String) request.getAttribute("IncludeModule"); %>
<jsp:include page="<%= includeModule %>" flush="true"/>
    </td>
  </tr>
</table>
</body>
</html>

