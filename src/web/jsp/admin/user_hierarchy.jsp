<%-- 
  - Copyright(c) 2004 Concursive Corporation (http://www.concursive.com/) All
  - rights reserved. This material cannot be distributed without written
  - permission from Concursive Corporation. Permission to use, copy, and modify
  - this material for internal use is hereby granted, provided that the above
  - copyright notice and this permission notice appear in all copies. CONCURSIVE
  - CORPORATION MAKES NO REPRESENTATIONS AND EXTENDS NO WARRANTIES, EXPRESS OR
  - IMPLIED, WITH RESPECT TO THE SOFTWARE, INCLUDING, BUT NOT LIMITED TO, THE
  - IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR ANY PARTICULAR
  - PURPOSE, AND THE WARRANTY AGAINST INFRINGEMENT OF PATENTS OR OTHER
  - INTELLECTUAL PROPERTY RIGHTS. THE SOFTWARE IS PROVIDED "AS IS", AND IN NO
  - EVENT SHALL CONCURSIVE CORPORATION OR ANY OF ITS AFFILIATES BE LIABLE FOR
  - ANY DAMAGES, INCLUDING ANY LOST PROFITS OR OTHER INCIDENTAL OR CONSEQUENTIAL
  - DAMAGES RELATING TO THE SOFTWARE.
  - 
  - Version: $Id$
  - Description: 
  --%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*, java.sql.*,org.aspcfs.modules.admin.base.*" %>
<%@ page import="org.aspcfs.modules.base.*, org.aspcfs.utils.*" %>
<jsp:useBean id="user" class="org.aspcfs.modules.admin.base.User" scope="request"/>
<jsp:useBean id="completeList" class="org.aspcfs.modules.admin.base.UserList" scope="request"/>
<jsp:useBean id="images" class="java.util.ArrayList" scope="request"/>
<jsp:useBean id="parent" class="java.lang.String" scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<jsp:useBean id="openUsers" class="java.util.ArrayList" scope="request"/>
<%@ include file="../initPage.jsp" %>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/submit.js"></SCRIPT>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/spanDisplay.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popURL.js"></script>
<form name="userHierarchy" action="UsersList.do?command=ListUsers&parent=<%= parent!=null && !"".equals(parent) ? parent : ""+ user.getId() %>" method="post" onSubmit="return doCheck(this)">
<script type="text/javascript">
<% int counter = 0; %>
var openUsers = new Array();
var counter = 0;
function doCheck(form) {
  return true;
}
</script>
<%Iterator openUsersIterator = (Iterator) openUsers.iterator();
  while (openUsersIterator.hasNext()) {
    String openUserId = (String) openUsersIterator.next();
    counter++;
%>
<%--
<script type="text/javascript">
openUsers[counter] = '<%= openUserId %>';
counter = counter+1;
</script>
--%>
<input type="hidden" name="hidden_<%= counter %>" value="<%= openUserId %>"/> 
<script type="text/javascript">
function addUser(userId) {
  var url = document.userHierarchy.action;
  url = url + '&hidden_<%= counter + 1 %>='+userId;
  document.userHierarchy.action = url;
  document.userHierarchy.submit();
}
function removeUser(userId) {
  var url = document.forms['userHierarchy'].action;
  url = url + '&hideUser='+userId;
  document.forms['userHierarchy'].action = url;
  document.forms['userHierarchy'].submit();
}
</script>
<table cellpadding="0" cellspacing="0" width="100%" border="1" rules="cols">
  <tr class="section">
    <td valign="top" width="100%">
      <img alt="" src="images/tree0.gif" border="0" align="absmiddle" height="16" width="16"/>
      <%= user.getContact().getNameFull() %> (<%= user.getUsername() %>) &nbsp; &nbsp;
    </td>
  </tr>
<%
  int rowid = 0;
  Iterator i = completeList.iterator();
  Iterator imageIterator = images.iterator();
  while (i.hasNext()) {
    User child = (User) i.next();
    String image = (String) imageIterator.next();
%>
  <tr class="row<%= rowid %>">
    <td valign="top">
      <%= image %>
      <%= child.getContact().getNameFull() %>
      <dhv:permission name="admin-users-view,admin-roles-view">(<%= child.getUsername() %>)</dhv:permission><font color="red"><%= (!child.getEnabled() && child.getExpires() != null && child.getExpires().before(new Timestamp(Calendar.getInstance().getTimeInMillis())))?"(X)":"" %></font>
    </td>
  </tr>
  <%}%>
<%}%>
</table>
<br />
<input type="button" value="<dhv:label name="global.button.cancel">Cancel</dhv:label>" onClick="javascript:window.close()" />
</form>
<iframe src="empty.html" name="server_commands" id="server_commands" style="visibility:hidden" height="0"></iframe>

