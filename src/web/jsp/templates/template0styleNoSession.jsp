<%@ page  import="java.util.*,com.darkhorseventures.cfsmodule.*,com.darkhorseventures.controller.*,com.darkhorseventures.webutils.*" %>
<jsp:useBean id="ModuleBean" class="com.darkhorseventures.cfsmodule.ModuleBean" scope="request"/>
<%
  ClientType clientType = new ClientType(request);
  response.setHeader("Pragma", "no-cache"); // HTTP 1.0
  response.setHeader("Cache-Control", "no-cache"); // HTTP 1.1
%>
<html>

<head>
<title>CFS<%= ((!ModuleBean.hasName())?"":": " + ModuleBean.getName()) %></title>
<link rel="stylesheet" href="css/template0<%= clientType.getBrowserIdAndOS() %>.css" type="text/css">
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

