<%-- (C) 2001-2003 Dark Horse Ventures --%>
<%@ page  import="java.util.*,org.aspcfs.modules.base.*,org.aspcfs.controller.*" %>
<jsp:useBean id="ModuleBean" class="org.aspcfs.modules.beans.ModuleBean" scope="request"/>
<%
  response.setHeader("Pragma", "no-cache"); // HTTP 1.0
  response.setHeader("Cache-Control", "no-cache"); // HTTP 1.1
%>
<!-- (C) 2001-2003 Dark Horse Ventures -->
<html>
<head>
<title>Dark Horse CRM<%= ((!ModuleBean.hasName())?"":": " + ModuleBean.getName()) %></title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<jsp:include page="cssInclude.jsp" flush="true"/>
</head>
<body bgcolor="#FFFFFF" LEFTMARGIN="0" MARGINWIDTH="0" TOPMARGIN="0" MARGINHEIGHT="0">
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td><img src="images/dhv1.gif" width="339" height="65"></td>
    <td width="100%" align="right" valign="top" style="background-image: url(images/spaceb1.gif); background-repeat: repeat-x; background-position: top left;">
      &nbsp;
    </td>
  </tr>
  <tr>
    <td><img src="images/dhv2.gif" width="339" height="30"></td>
    <td width="100%"><img src="images/spaceb2.gif" width="100%" height="30"></td>
  </tr>
  <tr>
    <td><img src="images/dhv3.gif" width="339" height="11"></td>
    <td width="100%"><img src="images/spaceg1.gif" width="100%" height="11"></td>
  </tr>
  <tr>
    <td colspan="2">
      <table width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td width="100%">
            <table width="100%" border="0" cellpadding="8" cellspacing="0">
              <tr>
                <td width="150" valign="top" nowrap style="border-right: #333333 solid 1px;">
                  <jsp:include page="../setup/configure_global_items.jsp" flush="true"/>
                </td>
                <td width="100%" valign="top" style="padding-left: 10px">
                  <% String includeModule = (String) request.getAttribute("IncludeModule"); %>
                  <jsp:include page="<%= includeModule %>" flush="true"/>
                </td>
              </tr>
            </table>
          </td>
        </tr>
      </table>
    </td>
  </tr>
  <tr>
    <td valign="bottom" width="100%" colspan="2">
      <table width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td><img src="images/bottom.gif" width="120" height="35"></td>
          <td width="100%"><img src="images/bottomspace.gif" width="100%" height="35"></td>
          <td><img src="images/bottomcopyright.gif" width="339" height="35"></td>
        </tr>
      </table>
    </td>
  </tr>
</table>
</body>
</html>
