<%@ page  import="java.util.*,com.darkhorseventures.cfsmodule.*,com.darkhorseventures.controller.*" %>
<jsp:useBean id="User" class="com.darkhorseventures.cfsbase.UserBean" scope="session"/>
<jsp:useBean id="ModuleBean" class="com.darkhorseventures.cfsmodule.ModuleBean" scope="request"/>
<%
  response.setHeader("Pragma", "no-cache"); // HTTP 1.0
  response.setHeader("Cache-Control", "no-cache"); // HTTP 1.1
%>
<html>

<head>
<title>CFS<%= ((!ModuleBean.hasName())?"":": " + ModuleBean.getName()) %></title>
<link rel="stylesheet" href="css/template0<%= User.getBrowserId() %>.css" type="text/css">
<link rel="stylesheet" href="css/template0.css" type="text/css">
</head>
<script language="JavaScript" type="text/javascript" src="/javascript/popLEFT.js"></script>
<body leftmargin=0 rightmargin=0 margin=0 marginwidth=0 topmargin=0 marginheight=0>
<table border="0" width="100%">
  <tr>
    <th align="left" class="highlight">
      <!--<img border="0" src="/images/virginian-pilot.gif" width="295" height="32" align="top">-->
      &nbsp;
    </th>
    <th align="right">
      <a href="/Login.do?command=Logout" class="s"> Logout</a> |
      <a href="javascript:popLEFT('/Help.do?module=<%= request.getAttribute("moduleAction") %>&section=<%= request.getParameter("command") %>&sub=<%= request.getParameter("section") %>','CFS_Help','375','450','yes','yes');" class="s" onMouseOver="window.status='Pop-up Help';return true;" onMouseOut="window.status='';return true;">Help</a><br>
<%
  if (!User.getUserRecord().getContact().getNameFull().equals("")) {
%>      
      User: <b class="highlight"><%= User.getNameFirst() %> <%= User.getNameLast() %></b> /
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

<!-- Main Menu -->
<table border="0" width="100%" cellspacing="0" cellpadding="0">
  <tr>
    <!--<td width=10><img border="0" src="/images/menu-edge.gif" width="10" height="36"></td>-->
    <%= request.getAttribute("MainMenu") %>
    <td><img border="0" src="/images/menu-edge.gif" width="100%" height="36"></td>
  </tr>
</table>
<!-- Sub Menu 1 -->
<table border="0" width="100%" cellspacing="0" bgcolor="#006699">
  <tr>
    <td>
      <table border="0" cellspacing="0" bgcolor="#006699">
        <tr>  
          <td height="25" valign="middle" width="5"><font size="1">&nbsp;</font></td>
<%    
    Vector menuItems = ModuleBean.getMenuItems();
    Iterator i = menuItems.iterator();
    while (i.hasNext()) {
%>
          <td height="25" align="center" valign="middle" width="0"><b><font color="#FFFFFF" size="1"><%= (((SubmenuItem)i.next()).getAlternateHtml()) %></font></b></td>
          <td width="20">&nbsp;</td>
<%
    }
%>  
          <td height="25" valign="middle"><font size="1">&nbsp;</font></td>
        </tr>
      </table>  
    </td>
  </tr>
</table>

<table border="0" width="100%">
  <tr>
    <td valign="top">
      <table border="0" width="100%">
        <!-- The module goes here -->
        <tr>
          <td>
<!--&nbsp;<br>-->
<% String includeModule = (String) request.getAttribute("IncludeModule"); %>          
<jsp:include page="<%= includeModule %>" flush="true"/>
          </td>
        </tr>
        <!-- End module -->
      </table>
    </td>
    
    <!-- Global Items -->
    <%= request.getAttribute("GlobalItems") %>
    <!-- End Global Items -->
    
  </tr>
</table>

<!--hr color="#BFBFBB" noshade-->
<br>
<center><%= request.getAttribute("MainMenuSmall") %></center>
<br>
<center>(C) 2001 Dark Horse Ventures</center>
</body>

</html>

