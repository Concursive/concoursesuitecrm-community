<%@ page import="java.util.*" %>
<%@ page import="org.aspcfs.modules.base.*" %>
<%@ page import="org.aspcfs.controller.*" %>
<%@ page import="org.aspcfs.utils.web.*" %>
<jsp:useBean id="ModuleBean" class="org.aspcfs.modules.beans.ModuleBean" scope="request"/>
<%
  response.setHeader("Pragma", "no-cache"); // HTTP 1.0
  response.setHeader("Cache-Control", "no-cache"); // HTTP 1.1
%>
<html>
<head>
<title>Dark Horse CRM<%= ((!ModuleBean.hasName())?"":": " + ModuleBean.getName()) %></title>
<jsp:include page="cssInclude.jsp" flush="true"/>
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
<!-- (C) 2001-2004 Dark Horse Ventures -->
</body>

</html>

