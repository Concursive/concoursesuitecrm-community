<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page  import="java.util.*,org.aspcfs.modules.base.*,org.aspcfs.controller.*" %>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<jsp:useBean id="ModuleBean" class="org.aspcfs.modules.beans.ModuleBean" scope="request"/>
<jsp:useBean id="GlobalItems" class="java.lang.String" scope="request"/>
<%
  response.setHeader("Pragma", "no-cache"); // HTTP 1.0
  response.setHeader("Cache-Control", "no-cache"); // HTTP 1.1
%>
<html>
<head>
<%@ include file="../initPage.jsp" %>
<META HTTP-EQUIV="refresh" content="<%= User.getSystemStatus(getServletConfig()).getSessionTimeout() + 60 %>;URL=<%= request.getScheme() %>://<%= getServerUrl(request) %>/MyCFS.do?command=Home">
<title>Dark Horse CRM<%= ((!ModuleBean.hasName())?"":": " + ModuleBean.getName()) %></title>
<jsp:include page="cssInclude.jsp" flush="true"/>
</head>
<script language="JavaScript" type="text/javascript" src="javascript/popURL.js"></script>
<script language="JavaScript" type="text/javascript" src="javascript/trackMouse.js"></script>
<body leftmargin="0" rightmargin="0" margin="0" marginwidth="0" topmargin="0" marginheight="0">
<div id="header">
<table border="0" width="100%" cellpadding="2" cellspacing="0">
  <tr>
    <td valign="top">
      <table border="0" cellpadding="0" cellspacing="2">
        <tr>
          <td valign="top">
            <dhv:label name="logo"/>
          </td>
          <td valign="top" nowrap>
            <dhv:evaluate if="<%= !User.getUserRecord().getContact().getNameFirstLast().equals("") %>">
              <%= User.getActualUserId() != User.getUserId() ? "User Aliased To:":"User:" %> <b class="highlight"><%= User.getUserRecord().getContact().getNameFirstLast() %></b> /
            </dhv:evaluate>
              <b class="highlight"><%= User.getRole() %></b>
            <dhv:evaluate if="<%= User.getUserRecord().getManagerUser() != null && User.getUserRecord().getManagerUser().getContact() != null %>">
              <br>Manager: <b class="highlight"><%= User.getUserRecord().getManagerUser().getContact().getNameFull() %></b>
            </dhv:evaluate>
            <dhv:evaluate if="<%= System.getProperty("DEBUG") != null && "2".equals(System.getProperty("DEBUG")) && request.getAttribute("debug.action.time") != null %>">
              <br>Action took: <b class="highlight"><%= request.getAttribute("debug.action.time") %> ms</b>
            </dhv:evaluate>
          </td>
        </tr>
      </table>
    </td>
    <th align="right" valign="top" nowrap>
      <img src="images/icons/stock_print-16.gif" border="0" align="absmiddle" height="16" width="16"/>
      <a href="javascript:window.print()" class="s">Print</a>
      |
      <dhv:permission name="help-view">
      <img src="images/icons/stock_help-16.gif" border="0" align="absmiddle" height="16" width="16"/>
      <a href="javascript:popURL('Help.do?module=<%= request.getAttribute("moduleAction") %><%= request.getAttribute("moduleCommand") != null ? "&section=" + (String) request.getAttribute("moduleCommand") : ""%><%= request.getAttribute("moduleSection") != null ? "&sub=" + (String) request.getAttribute("moduleSection") : "" %>&popup=true','CRM_Help','790','500','yes','yes');" class="s" onMouseOver="window.status='Pop-up Help';return true;" onMouseOut="window.status='';return true;">Help</a>
      |</dhv:permission><dhv:permission name="qa-view">
      <img src="images/icons/stock_glue-16.gif" border="0" align="absmiddle" height="16" width="16"/>
      <a href="javascript:popURL('QA.do?module=<%= request.getAttribute("moduleAction") %><%= request.getAttribute("moduleCommand") != null ? "&section=" + (String) request.getAttribute("moduleCommand") : ""%><%= request.getAttribute("moduleSection") != null ? "&sub=" + (String) request.getAttribute("moduleSection") : "" %>&popup=true','CRM_QA','450','550','yes','yes');" class="s" onMouseOver="window.status='Pop-up QA';return true;" onMouseOut="window.status='';return true;">QA</a>
      |</dhv:permission>
      <img src="images/icons/stock_exit-16.gif" border="0" align="absmiddle" height="16" width="16"/>
      <a href="Login.do?command=Logout" class="s">Logout</a>
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
          <td height="15" valign="middle" width="5"><font size="1">&nbsp;</font></td>
<%
    Iterator i = ModuleBean.getMenuItems().iterator();
    while (i.hasNext()) {
    	SubmenuItem thisItem = (SubmenuItem)i.next();
    	if ("".equals(thisItem.getPermission()) || User.getSystemStatus(getServletConfig()).hasPermission(User.getUserId(), thisItem.getPermission())) {
%>
          <td height="15" align="center" valign="middle" width="0"><b><font color="#FFFFFF" size="1"><%= (thisItem.getAlternateHtml()) %></font></b></td>
          <td width="10">&nbsp;</td>
<%
      }
    }
%>
          <td height="15" valign="middle"><font size="1">&nbsp;</font></td>
        </tr>
      </table>
    </td>
  </tr>
</table>
</div>
<table border="0" width="100%">
  <tr>
    <td valign="top" width="100%">
<!-- The module goes here -->
<% String includeModule = (String) request.getAttribute("IncludeModule"); %>          
<jsp:include page="<%= includeModule %>" flush="true"/>
<!-- End module -->
    </td>
    <!-- Global Items -->
    <dhv:evaluate if="<%= GlobalItems.length() > 0 %>">
    <td width="150" valign="top" id="rightcol">
      <%= GlobalItems %>
    </td>
    </dhv:evaluate>
    <!-- End Global Items -->
  </tr>
</table>
<div id="footer">
<br>
<center><%= request.getAttribute("MainMenuSmall") %></center>
<br>
<center>Copyright (c) 2000-2003 Dark Horse Ventures.  All rights reserved.</center>
<center><dhv:tz timestamp="<%= new java.util.Date() %>"/></center>
</div>
</body>
</html>
