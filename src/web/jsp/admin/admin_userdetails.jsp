<%@ taglib uri="WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="com.darkhorseventures.cfsbase.User" %>
<%@ include file="initPage.jsp" %>
<script language="JavaScript" TYPE="text/javascript" SRC="/javascript/confirmDelete.js"></script>
<jsp:useBean id="UserRecord" class="com.darkhorseventures.cfsbase.User" scope="request"/>
<form name="details" action="/Users.do?auto-populate=true" method="post">
<a href="Admin.do">Setup</a> >
<a href="Users.do?command=ListUsers">View Users</a> >
User Details<br>
<hr color="#BFBFBB" noshade>
<input type=hidden name="id" value="<%= UserRecord.getId() %>">
<dhv:permission name="admin-users-edit">
  <input type="button" name="action" value="Modify"	onClick="document.details.command.value='ModifyUser';document.details.submit()">
</dhv:permission>
<dhv:permission name="admin-users-delete">  
  <input type="button" name="action" value="Delete" onClick="document.details.command.value='DeleteUser';confirmSubmit(document.details);">
</dhv:permission>
<dhv:permission name="admin-users-edit,admin-users-delete">
  <br>
  &nbsp;
</dhv:permission>
<table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr bgcolor="#DEE0FA">
    <td colspan=2 valign=center align=left>
	    <strong>User: <%= toHtml(UserRecord.getContact().getNameFull()) %></strong>
	  </td>
  </tr>
  <tr>
    <td width="150">Username</td>
    <td><%= toHtml(UserRecord.getUsername()) %></td>
  </tr>
  <tr>
    <td width="150">User Group (Role)</td>
    <td><%= toHtml(UserRecord.getRole()) %></td>
  </tr>
  <tr>
    <td width="150">Reports To</td>
    <td><%= toHtml(UserRecord.getManager()) %></td>
  </tr>
  <tr>
    <td width="150">Aliased To</td>
    <td><%= toHtml(UserRecord.getAliasName()) %></td>
  </tr>
  <tr>
    <td width="150">Last Login</td>
    <td><%= (UserRecord.getLastLoginString()) %></td>
  </tr>
    <tr>
    <td width="150">Account Expires On</td>
    <td><%= toHtml(UserRecord.getExpiresString()) %></td>
  </tr>
</table>
<br>
<input type=hidden name="command" value="">
<dhv:permission name="admin-users-edit">
  <input type="button" name="action" value="Modify"	onClick="document.details.command.value='ModifyUser';document.details.submit()">
</dhv:permission>
<dhv:permission name="admin-users-delete">  
  <input type="button" name="action" value="Delete" onClick="document.details.command.value='DeleteUser';confirmSubmit(document.details)">
</dhv:permission>
