<%-- 
  - Copyright(c) 2006 Dark Horse Ventures LLC (http://www.centriccrm.com/) All
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
  - Version: $Id: template1style.jsp 14952 2006-05-09 15:45:09 -0400 (Tue, 09 May 2006) matt $
  - Description:
  --%>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page pageEncoding="UTF-8" %>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<jsp:useBean id="site" class="org.aspcfs.modules.website.base.Site" scope="request"/>
<jsp:useBean id="style" class="org.aspcfs.modules.website.base.Style" scope="request"/>
<%@ include file="../initPage.jsp" %>
<%
  response.setHeader("Pragma", "no-cache"); // HTTP 1.0
  response.setHeader("Cache-Control", "no-cache"); // HTTP 1.1
  response.setHeader("Expires", "-1");
%>
<!-- Powered By Centric CRM, Inc. -->
<html>
<head>
  <title><%= toHtml(site.getName()) %>: <%= toHtml(site.getTabToDisplay().getDisplayText()) %></title>
  <dhv:meta />
  <link rel="stylesheet" href="portal/styles/style_global.css" type="text/css">
  <link rel="stylesheet" href="portal/styles/<%= style.getCss() %>" type="text/css">
</head>
<body leftmargin="0" rightmargin="0" margin="0" marginwidth="0" topmargin="0" marginheight="0">
<table border="0" width="100%" cellpadding="0" cellspacing="0">
  <tr>
    <td valign="top">
<% String includeModule = (String) request.getAttribute("IncludeModule"); %>
<jsp:include page="<%= includeModule %>" flush="true"/>
    </td>
  </tr>
</table>
</body>
</html>

