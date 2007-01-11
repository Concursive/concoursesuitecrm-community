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
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page pageEncoding="UTF-8" %>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page  import="java.util.*,org.aspcfs.modules.base.*,org.aspcfs.controller.*" %>
<jsp:useBean id="ModuleBean" class="org.aspcfs.modules.beans.ModuleBean" scope="request"/>
<%
  response.setHeader("Pragma", "no-cache"); // HTTP 1.0
  response.setHeader("Cache-Control", "no-cache"); // HTTP 1.1
  response.setHeader("Expires", "-1");
%>
<!-- (C) 2000-2006 Dark Horse Ventures, LLC -->
<html>
<head>
<title><dhv:label name="templates.CentricCRM">Centric CRM</dhv:label><%= ((!ModuleBean.hasName())?"":": " + ModuleBean.getName()) %></title>
<jsp:include page="cssInclude.jsp" flush="true"/>
</head>
<body bgcolor="#EEEEEE" LEFTMARGIN="0" MARGINWIDTH="0" TOPMARGIN="0" MARGINHEIGHT="0">
<center>
<table width="780" border="0" cellspacing="0" cellpadding="0" style="border-right: 1px solid #898989; background-color: #FFFFFF; border-left: 1px solid #898989;">
  <%-- logo --%>
  <tr> 
    <td width="100%">
      <table border="0" cellpadding="0" cellspacing="0" width="100%" style="padding-left: 10px; padding-right: 10px">
        <tr>
          <td><img src="images/centric/logo-centric.gif" width="295" height="66" alt="" border="0" /></td>
        </tr>
      </table>
    </td>
  </tr>
  <%-- line --%>
  <tr>
    <td width="100%">
      <table width="100%" border="0" cellspacing="0" cellpadding="0" bgcolor="#989898">
        <tr>
          <td width="100%">&nbsp;</td>
        </tr>
      </table>
    </td>
  </tr>
  <%-- body --%>
  <tr>
    <td width="100%" style="padding: 6px 8px 10px 10px;">
      <% String includeModule = (String) request.getAttribute("IncludeModule"); %>
      <jsp:include page="<%= includeModule %>" flush="true"/>
    </td>
  </tr>
  <%-- bottom --%>
  <tr>
    <td width="100%">
      <table width="100%" border="0" cellspacing="0" cellpadding="0" bgcolor="#989898">
        <tr>
          <td width="100%">&nbsp;</td>
        </tr>
      </table>
      <br />
      <br />
      <table width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td align="center">&#169; Copyright 2000-2007 Centric CRM &#149; <dhv:label name="global.label.allRightsReserved">All rights reserved</dhv:label> &#149; <dhv:label name="templates.variousTrademarksHeldByTheirRespectiveOwners">Various trademarks held by their respective owners.</dhv:label></td>
        </tr>
      </table>
      <br />
      <table width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td align="center"><a href="http://www.centriccrm.com" target="_blank"><img src="images/centric/Power3sm.gif" border="0" alt="Power of Centric CRM" /></a><br /><br /><br /><br />
          <br /><br /><br /><br /></td>
        </tr>
      </table>
    </td>
  </tr>
</table>
</center>
</body>
</html>
