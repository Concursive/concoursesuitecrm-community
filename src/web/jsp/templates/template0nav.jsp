<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page  import="java.util.*,org.aspcfs.modules.base.*,org.aspcfs.controller.*" %>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<jsp:useBean id="ModuleBean" class="org.aspcfs.modules.beans.ModuleBean" scope="request"/>
<%
  response.setHeader("Pragma", "no-cache"); // HTTP 1.0
  response.setHeader("Cache-Control", "no-cache"); // HTTP 1.1
%>
<html>
<head>
<META HTTP-EQUIV="refresh" content="<%= User.getSystemStatus(getServletConfig()).getSessionTimeout() + 60 %>;URL=<%= request.getScheme() %>://<%= request.getServerName() %>/MyCFS.do?command=Home">
<title>CFS<%= ((!ModuleBean.hasName())?"":": " + ModuleBean.getName()) %></title>
<jsp:include page="cssInclude.jsp" flush="true"/>
</head>
<script language="JavaScript" type="text/javascript" src="javascript/popURL.js"></script>
<body leftmargin="0" rightmargin="0" margin="0" marginwidth="0" topmargin="0" marginheight="0">
<div id="header">
<table border="0" width="100%">
  <tr>
    <th align="left" valign="top">
      <dhv:label name="logo">&nbsp;</dhv:label>
      &nbsp;
      <dhv:evaluate if="<%= System.getProperty("DEBUG") != null && "2".equals(System.getProperty("DEBUG")) %>">
        Action took: <b class="highlight"><%= request.getAttribute("debug.action.time") %> ms</b>
      </dhv:evaluate>
    </th>
    <th align="right">
      <a href="javascript:window.print()" class="s">Print</a>
      |
      <a href="javascript:popURL('Help.do?module=<%= request.getAttribute("moduleAction") %>&section=<%= request.getParameter("command") %>&sub=<%= request.getParameter("section") %>','CFS_Help','790','500','yes','yes');" class="s" onMouseOver="window.status='Pop-up Help';return true;" onMouseOut="window.status='';return true;">Help</a>
      |
      <dhv:permission name="help-edit"><a href="javascript:popURL('QA.do?module=<%= request.getAttribute("moduleAction") %>&section=<%= request.getParameter("command") %><%= request.getParameter("section") != null ? "&sub=" + request.getParameter("section") : "" %>&popup=true','CFS_QA','790','500','yes','yes');" class="s" onMouseOver="window.status='Pop-up QA';return true;" onMouseOut="window.status='';return true;">QA</a>
      |</dhv:permission><a href="Login.do?command=Logout" class="s">Logout</a><br>
<%
  if (!User.getUserRecord().getContact().getNameFirstLast().equals("")) {
%>  
    <%= User.getActualUserId() != User.getUserId() ? "User Aliased To:":"User:" %> <b class="highlight"><%= User.getUserRecord().getContact().getNameFirstLast() %></b> /
<%}%>      
      <b class="highlight"><%= User.getRole() %></b>
<%
  if (User.getUserRecord().getManagerUser() != null && User.getUserRecord().getManagerUser().getContact() != null) {
%>      
      <br>
      Manager: <b class="highlight"><%= User.getUserRecord().getManagerUser().getContact().getNameFull() %></b>
<%}%>
    </th>
  </tr>
</table>
</div>
<!-- Main Menu -->
<div class="menutabs" id="topmenutabs">
<table border="0" width="100%" cellspacing="0" cellpadding="0">
  <tr>
    <%= request.getAttribute("MainMenu") %>
  </tr>
</table>
</div>
<!-- Sub Menu -->
<div id="header">
<table border="0" width="100%" cellspacing="0" bgcolor="#006699">
  <tr>
    <td>
      <table border="0" cellspacing="0" bgcolor="#006699">
        <tr>  
          <td height="25" valign="middle" width="5"><font size="1">&nbsp;</font></td>
<%
    Iterator i = ModuleBean.getMenuItems().iterator();
    while (i.hasNext()) {
    	SubmenuItem thisItem = (SubmenuItem)i.next();
    	if (User.getSystemStatus(getServletConfig()).hasPermission(User.getUserId(), thisItem.getPermission())) {
%>
          <td height="25" align="center" valign="middle" width="0"><b><font color="#FFFFFF" size="1"><%= (thisItem.getAlternateHtml()) %></font></b></td>
          <td width="20">&nbsp;</td>
<%
      }
    }
%>
          <td height="25" valign="middle"><font size="1">&nbsp;</font></td>
        </tr>
      </table>
    </td>
  </tr>
</table>
</div>
<table border="0" width="100%">
  <tr>
    <td valign="top">
      <table border="0" width="100%">
        <tr>
          <td>
<!-- The module goes here -->
<% String includeModule = (String) request.getAttribute("IncludeModule"); %>          
<jsp:include page="<%= includeModule %>" flush="true"/>
<!-- End module -->
          </td>
        </tr>
      </table>
    </td>
    <!-- Global Items -->
    <jsp:include page="../globalItems.jsp" flush="true"/>
    <!-- End Global Items -->
  </tr>
</table>
<div id="footer">
<br>
<center><%= request.getAttribute("MainMenuSmall") %></center>
<br>
<center>Copyright (c) 2000-2003 Dark Horse Ventures.  All rights reserved.</center>
</div>
</body>
</html>
