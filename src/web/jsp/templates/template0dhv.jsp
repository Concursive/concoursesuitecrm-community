<%-- 
  - Copyright(c) 2004 Dark Horse Ventures LLC (http://www.centriccrm.com/) All
  - rights reserved. This material cannot be distributed without written
  - permission from Dark Horse Ventures LLC. Permission to use, copy, and modify
  - this material for internal use is hereby granted, provided that the above
  - copyright notice and this permission notice appear in all copies. DARK HORSE
  - VENTURES LLC MAKES NO REPRESENTATIONS AND EXTENDS NO WARRANTIES, EXPRESS OR
  - IMPLIED, WITH RESPECT TO THE SOFTWARE, INCLUDING, BUT NOT LIMITED TO, THE
  - IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR ANY PARTICULAR
  - PURPOSE, AND THE WARRANTY AGAINST INFRINGEMENT OF PATENTS OR OTHER
  - INTELLECTUAL PROPERTY RIGHTS. THE SOFTWARE IS PROVIDED "AS IS", AND IN NO
  - EVENT SHALL DARK HORSE VENTURES LLC OR ANY OF ITS AFFILIATES BE LIABLE FOR
  - ANY DAMAGES, INCLUDING ANY LOST PROFITS OR OTHER INCIDENTAL OR CONSEQUENTIAL
  - DAMAGES RELATING TO THE SOFTWARE.
  - 
  - Version: $Id$
  - Description: 
  --%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page  import="java.util.*,org.aspcfs.modules.base.*,org.aspcfs.controller.*" %>
<jsp:useBean id="ModuleBean" class="org.aspcfs.modules.beans.ModuleBean" scope="request"/>
<%
  response.setHeader("Pragma", "no-cache"); // HTTP 1.0
  response.setHeader("Cache-Control", "no-cache"); // HTTP 1.1
%>
<!-- (C) 2001-2004 Dark Horse Ventures -->
<html>
<head>
<title>Centric CRM<%= ((!ModuleBean.hasName())?"":": " + ModuleBean.getName()) %></title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<jsp:include page="cssInclude.jsp" flush="true"/>
</head>
<body bgcolor="#FFFFFF" LEFTMARGIN="0" MARGINWIDTH="0" TOPMARGIN="0" MARGINHEIGHT="0">
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td width="100%" nowrap>
      <table border="0" cellpadding="0" cellspacing="0" width="100%">
        <tr>
          <td><img src="images/dhv1.gif" width="339" height="65"></td>
          <td width="100%"><img src="images/spaceb1.gif" width="100%" height="65"></td>
        </tr>
        <tr>
          <td><img src="images/dhv2.gif" width="339" height="30"></td>
          <td width="100%"><img src="images/spaceb2.gif" width="100%" height="30"></td>
        </tr>
        <tr>
          <td><img src="images/dhv3.gif" width="339" height="11"></td>
          <td width="100%"><img src="images/spaceg1.gif" width="100%" height="11"></td>
        </tr>
      </table>
    </td>
  </tr>
  <tr>
    <td>
      <table width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td width="100%">
            <table width="100%" border="0" cellpadding="8" cellspacing="0">
              <tr>
                <dhv:evaluate if="<%= "Upgrade".equals(ModuleBean.getCurrentAction()) %>">
                  <td width="150" valign="top" nowrap style="border-right: #333333 solid 1px;">
                    <jsp:include page="../setup/configure_global_upgrade_items.jsp" flush="true"/>
                  </td>
                </dhv:evaluate>
                <dhv:evaluate if="<%= !"Upgrade".equals(ModuleBean.getCurrentAction()) %>">
                  <td width="150" valign="top" nowrap style="border-right: #333333 solid 1px;">
                    <jsp:include page="../setup/configure_global_items.jsp" flush="true"/>
                  </td>
                </dhv:evaluate>
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
    <td valign="bottom" width="100%">
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
