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
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ page import="java.text.DateFormat"%>
<jsp:useBean id="UserList" class="org.aspcfs.modules.admin.base.UserList" scope="request"/>
<jsp:useBean id="RoleList" class="org.aspcfs.modules.admin.base.RoleList" scope="request"/>
<jsp:useBean id="UserRecord" class="org.aspcfs.modules.admin.base.User" scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<%@ include file="../initPage.jsp" %>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/checkDate.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popCalendar.js"></SCRIPT>
<body onLoad="javascript:document.forms[0].username.focus();">
<form name="details" action="Users.do?command=UpdateUser&auto-populate=true" method="post">
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="Admin.do">Admin</a> > 
<% if (request.getParameter("return") != null) {%>
	<% if (request.getParameter("return").equals("list")) {%>
	<a href="Users.do?command=ListUsers">View Users</a> >
  <%}%>
<%} else {%>
<a href="Users.do?command=ListUsers">View Users</a> >
<a href="Users.do?command=UserDetails&id=<%= UserRecord.getId() %>">User Details</a> >
<%}%>
Modify User
</td>
</tr>
</table>
<%-- End Trails --%>
<strong><%= toHtml(UserRecord.getUsername()) %> (<%= toHtml(UserRecord.getContact().getNameLastFirst()) %>)</strong>
<% String param1 = "id=" + UserRecord.getId(); %>      
<dhv:container name="users" selected="details" param="<%= param1 %>" style="tabs"/>
<table cellpadding="4" cellspacing="0" border="0" width="100%">
  <tr>
    <td class="containerBack">
<% if (request.getParameter("return") != null) {%>
<input type="hidden" name="return" value="<%=request.getParameter("return")%>">
<%}%>
<dhv:permission name="admin-users-edit">
<input type="submit" value="Update">
</dhv:permission>
<% if (request.getParameter("return") != null) {%>
	<% if (request.getParameter("return").equals("list")) {%>
	<input type="submit" value="Cancel" onClick="javascript:this.form.action='Users.do?command=ListUsers'">
	<%}%>
<%} else {%>
<input type="submit" value="Cancel" onClick="javascript:this.form.action='Users.do?command=UserDetails&id=<%=UserRecord.getId()%>'">
<%}%>
<dhv:permission name="admin-users-delete">
<input type="submit" value="Disable" onClick="javascript:this.form.action='Users.do?command=DisableUserConfirm&id=<%=UserRecord.getId()%>'">
</dhv:permission>
<br>
<input type="hidden" name="id" value="<%= UserRecord.getId() %>">
<input type="hidden" name="contactId" value="<%= UserRecord.getContactId() %>">
<input type="hidden" name="previousUsername" value="<%= ((UserRecord.getPreviousUsername() == null)?UserRecord.getUsername():UserRecord.getPreviousUsername()) %>">
<dhv:formMessage />
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="2">
	    <strong>Modify Primary Information</strong>
    </th>
  </tr>
  <tr class="containerBody">
    <td class="formLabel">Unique Username</td>
    <td>
      <input type="text" name="username" value="<%= toHtmlValue(UserRecord.getUsername()) %>">
      <font color=red>*</font> <%= showAttribute(request, "usernameError") %>
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">Role (User Group)</td>
    <td>
      <%= RoleList.getHtmlSelect("roleId", UserRecord.getRoleId()) %>
      <font color="red">*</font> <%= showAttribute(request, "roleError") %>
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">Reports To</td>
    <td>
      <%= UserList.getHtmlSelect("managerId", UserRecord.getManagerId()) %>
      <%= showAttribute(request, "managerIdError") %>
    </td>
  </tr>
<dhv:permission name="demo-view">
  <tr class="containerBody">
    <td nowrap class="formLabel">Alias User</td>
    <td>
      <%= UserList.getHtmlSelect("alias", UserRecord.getAlias()) %>
    </td>
  </tr>
</dhv:permission>  
  <tr class="containerBody">
    <td nowrap class="formLabel">Expire Date</td>
    <td>
      <zeroio:dateSelect form="details" field="expires" timestamp="<%= UserRecord.getExpires() %>" />
      <%= showAttribute(request, "expiresError") %>
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">Generate new password</td>
    <td>
      <table border="0" cellpadding="0" cellspacing="0" class="empty">
        <tr>
          <td valign="center">
            <input type="checkbox" name="generatePass" value="true">
          </td>
          <td width="8"></td>
          <td valign="center">
            Note: New password will be emailed to the following address:
            <%= UserRecord.getContact().getEmailAddress("Business") %>
          </td>
        </tr>
      </table>
    </td>
  </tr>
</table>
<br>
<dhv:permission name="admin-users-edit">
<input type="submit" value="Update">
</dhv:permission>
<% if (request.getParameter("return") != null) {%>
	<% if (request.getParameter("return").equals("list")) {%>
	<input type="submit" value="Cancel" onClick="javascript:this.form.action='Users.do?command=ListUsers'">
	<%}%>
<%} else {%>
<input type="submit" value="Cancel" onClick="javascript:this.form.action='Users.do?command=UserDetails&id=<%=UserRecord.getId()%>'">
<%}%>
<dhv:permission name="admin-users-delete">
<input type="submit" value="Disable" onClick="javascript:this.form.action='Users.do?command=DisableUserConfirm&id=<%=UserRecord.getId()%>'">
</dhv:permission>
  </td>
  </tr>
</table>
</form>
