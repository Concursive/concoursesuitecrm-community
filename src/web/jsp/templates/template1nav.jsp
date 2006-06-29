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
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ page import="org.aspcfs.controller.SubmenuItem,java.text.DateFormat,java.util.Iterator" %>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<jsp:useBean id="ModuleBean" class="org.aspcfs.modules.beans.ModuleBean" scope="request"/>
<jsp:useBean id="GlobalItems" class="java.lang.String" scope="request"/>
<jsp:useBean id="globalItemsPaneState" class="java.lang.String" scope="session"/>
<%
  response.setHeader("Pragma", "no-cache"); // HTTP 1.0
  response.setHeader("Cache-Control", "no-cache"); // HTTP 1.1
  response.setHeader("Expires", "-1");
  boolean globalItemsPaneHidden = "HIDE".equals(globalItemsPaneState);
%>
<html>
<head>
<%@ include file="../initPage.jsp" %>
<META HTTP-EQUIV="refresh" content="<%= User.getSystemStatus(getServletConfig()).getSessionTimeout() + 60 %>;URL=<%= request.getScheme() %>://<%= getServerUrl(request) %>/MyCFS.do?command=Home">
<title><dhv:label name="templates.CentricCRM">Centric CRM</dhv:label><%= ((!ModuleBean.hasName())?"":": " + ModuleBean.getName()) %></title>
  <jsp:include page="cssInclude.jsp" flush="true"/>
</head>
<script language="JavaScript" type="text/javascript" src="javascript/popURL.js"></script>
<script language="JavaScript" type="text/javascript" src="javascript/quickAction.js"></script>
<script language="JavaScript" type="text/javascript" src="javascript/spanDisplay.js"></script>
<script language="JavaScript" type="text/javascript" src="javascript/globalItemsPane.js"></script>
<body leftmargin="0" rightmargin="0" margin="0" marginwidth="0" topmargin="0" marginheight="0">
<div id="header">
<table border="0" width="100%" cellpadding="2" cellspacing="0">
  <tr>
    <td valign="top">
      <table border="0" cellpadding="0" cellspacing="2">
        <tr>
          <td valign="top">
            <dhv:label name="logo"><img src="images/centriccrm.gif" align="absMiddle" height="30" width="135" border="0" /></dhv:label>
          </td>
        </tr>
      </table>
    </td>
    <th align="right" valign="top" nowrap>
      <img src="images/icons/stock_print-16.gif" border="0" align="absmiddle" height="16" width="16"/>
      <a href="javascript:window.print()" class="s"><dhv:label name="global.button.Print">Print</dhv:label></a>
      |
      <dhv:permission name="help-view">
      <img src="images/icons/stock_help-16.gif" border="0" align="absmiddle" height="16" width="16" />
      <a href="javascript:popURL('Help.do?module=<%= request.getAttribute("moduleAction") %><%= request.getAttribute("moduleCommand") != null ? "&section=" + (String) request.getAttribute("moduleCommand") : ""%><%= request.getAttribute("moduleSection") != null ? "&sub=" + (String) request.getAttribute("moduleSection") : "" %>&popup=true','CRM_Help','790','500','yes','yes');" class="s" onMouseOver="window.status='Pop-up Help';return true;" onMouseOut="window.status='';return true;"><dhv:label name="global.button.Help">Help</dhv:label></a>
      |</dhv:permission>
      <%--
      <dhv:permission name="help-view">
      <img src="images/icons/stock_help-16.gif" border="0" align="absmiddle" height="16" width="16" />
      <a href="javascript:popURL('tutorials/index.html','CRM_Tutorials','1024','700','yes','yes');" class="s" onMouseOver="window.status='Tutorials';return true;" onMouseOut="window.status='';return true;">Tutorials</a>
      |</dhv:permission>
      --%>
      <dhv:permission name="qa-view">
      <img src="images/icons/stock_glue-16.gif" border="0" align="absmiddle" height="16" width="16" />
      <dhv:label name="admin.qa.colon">QA:</dhv:label>
      <a href="javascript:popURL('QA.do?module=<%= request.getAttribute("moduleAction") %><%= request.getAttribute("moduleCommand") != null ? "&section=" + (String) request.getAttribute("moduleCommand") : ""%><%= request.getAttribute("moduleSection") != null ? "&sub=" + (String) request.getAttribute("moduleSection") : "" %>&popup=true','CRM_QA','450','550','yes','yes');" class="s" onMouseOver="window.status='Pop-up QA';return true;" onMouseOut="window.status='';return true;"><dhv:label name="admin.page">Page</dhv:label></a>
      <a href="javascript:popURL('Help.do?command=ViewModuleDescription&module=<%=request.getAttribute("moduleAction")%>&popup=true','CRM_Help','790','500','yes','yes');" class="s" onMouseOver="window.status='Pop-up Help';return true;" onMouseOut="window.status='';return true;"><dhv:label name="qa.module">Module</dhv:label></a>
      |</dhv:permission>
      <dhv:permission name="admin-view">
      <img src="images/icons/stock_form-properties-16.gif" border="0" align="absmiddle" height="16" width="16" />
      <a href="Admin.do"><dhv:label name="trails.admin">Admin</dhv:label></a>
      |</dhv:permission>
      <img src="images/icons/stock_exit-16.gif" border="0" align="absmiddle" height="16" width="16"/>
      <a href="Login.do?command=Logout" class="s"><dhv:label name="global.button.Logout">Logout</dhv:label></a>
      <dhv:evaluate if="<%= !User.getUserRecord().getContact().getNameFirstLast().equals("") %>">
        <br />
        <% if(User.getActualUserId() != User.getUserId()) {%>
          <dhv:label name="admin.userAliasedTo" param="<%= "contactName="+toHtml(User.getUserRecord().getContact().getNameFirstLast()) %>">User Aliased To <span class="highlight"><%= toHtml(User.getUserRecord().getContact().getNameFirstLast()) %></span> /</dhv:label>
        <%} else {%>
          <dhv:label name="admin.user.colon" param="<%= "contactName="+toHtml(User.getUserRecord().getContact().getNameFirstLast()) %>">User: <span class="highlight"><%= toHtml(User.getUserRecord().getContact().getNameFirstLast()) %></span> /</dhv:label>
        <%}%>
      </dhv:evaluate>
      <b class="highlight"><%= User.getRole() %></b>
      <dhv:evaluate if="<%= User.getUserRecord().getManagerUser() != null && User.getUserRecord().getManagerUser().getContact() != null %>">
        <br /><dhv:label name="admin.manager.colon" param="<%= "managerName="+toHtml(User.getUserRecord().getManagerUser().getContact().getNameFull()) %>">Manager: <span class="highlight"><%= toHtml(User.getUserRecord().getManagerUser().getContact().getNameFull()) %></span></dhv:label>
      </dhv:evaluate>
      <dhv:evaluate if="<%= System.getProperty("DEBUG") != null && "2".equals(System.getProperty("DEBUG")) && request.getAttribute("debug.action.time") != null %>">
        <br />
        Users logged in: <span class="highlight"><%= User.getSystemStatus(getServletConfig()).getTracker().getUserCount() %></span>
        Website visitors: <span class="highlight"><%= User.getSystemStatus(getServletConfig()).getTracker().getGuestCount() %></span>
        <dhv:label name="admin.actionTook.colon" param="<%= "time=" + request.getAttribute("debug.action.time") %>">Action took:</dhv:label>
      </dhv:evaluate>
    </th>
  </tr>
</table>
</div>
<!-- Main Menu -->
<div id="topmenutabs">
<table border="0" width="100%" cellspacing="0" cellpadding="0">
  <tr>
    <td><img border="0" src="images/blank.gif" /></td>
    <%= request.getAttribute("MainMenuTops") %>
    <td style="width:100%; background: #FFF;"><img border="0" src="images/blank.gif" /></td>
  </tr>
  <tr>
    <td class="menuBackground">&nbsp;</td>
    <%= request.getAttribute("MainMenu") %>
    <td width="100%" class="menuBackground"><img border="0" src="images/blank.gif" /></td>
  </tr>
</table>
</div>
<!-- Sub Menu -->
<div id="header">
<table border="0" width="100%" cellspacing="0" class="submenu">
  <tr>
    <td width="100%">
      <table border="0" cellspacing="0" class="submenuItem">
        <tr>
          <td width="5"><font size="1">&nbsp;</font></td>
<%
    Iterator i = ModuleBean.getMenuItems().iterator();
    while (i.hasNext()) {
    	SubmenuItem thisItem = (SubmenuItem)i.next();
    	if ("".equals(thisItem.getPermission()) || User.getSystemStatus(getServletConfig()).hasPermission(User.getUserId(), thisItem.getPermission())) {
%>
          <td ><%= (thisItem.getAlternateHtml(User.getSystemStatus(getServletConfig()))) %></td>
          <td width="10">&nbsp;</td>
<%
      }
    }
%>
          <td><font size="1">&nbsp;</font></td>
        </tr>
      </table>
    </td>
  </tr>
</table>
</div>
<table border="0" width="100%" cellpadding="0" cellspacing="0" class="layoutPane">
  <tr>
    <td valign="top" width="100%" class="contentPane">
      <!-- The module goes here -->
      <% String includeModule = (String) request.getAttribute("IncludeModule"); %>
      <jsp:include page="<%= includeModule %>" flush="true"/>
      <!-- End module -->
    </td>
    <!-- Global Items -->
    <dhv:evaluate if="<%= GlobalItems.length() > 0 %>">
    <td width="4" class="navDiv" id="rightnavcol" height="100%">
      <span id="globalItemsHide" name="globalItemsHide"<dhv:evaluate if="<%= globalItemsPaneHidden %>"> style="display:none"</dhv:evaluate>><a href="javascript:resizeGlobalItemsPane('hide')"><img src="images/layout/div-hide.gif" alt="Hide" width="6" border="0" /></a></span>
      <span id="globalItemsShow" name="globalItemsShow"<dhv:evaluate if="<%= !globalItemsPaneHidden %>"> style="display:none"</dhv:evaluate>><a href="javascript:resizeGlobalItemsPane('show')"><img src="images/layout/div-show.gif" alt="Show" width="6" border="0" /></a></span>
    </td>
    <td width="150" valign="top" id="rightcol" class="globalItemsPane" <dhv:evaluate if="<%= globalItemsPaneHidden %>"> style="display:none"</dhv:evaluate>>
      <%= GlobalItems %>
    </td>
    </dhv:evaluate>
    <!-- End Global Items -->
  </tr>
</table>
<div id="footer">
<br />
<center>
<zeroio:tz timestamp="<%= new java.util.Date() %>" timeFormat="<%= DateFormat.LONG %>" timeZone="<%= User.getTimeZone()%>" /><br />
&#169; Copyright 2000-2006 Dark Horse Ventures, LLC &#149; <dhv:label name="global.label.allRightsReserved">All rights reserved.</dhv:label><br />
</div>
<%-- Allow pages have to have a scrollTo... must be at end of html --%>
<script language="JavaScript" type="text/javascript" src="javascript/scrollReload.js"></script>
<dhv:evaluate if="<%= request.getParameter("scrollTop") != null %>">
<script language="JavaScript" type="text/javascript">
    if (window.scrollTo) window.scrollTo(<%= request.getParameter("scrollLeft") %>, <%= request.getParameter("scrollTop") %>);
</script>
</dhv:evaluate>
<iframe src="empty.html" name="template_commands" id="template_commands" style="visibility:hidden" height="0"></iframe>
</body>
</html>
