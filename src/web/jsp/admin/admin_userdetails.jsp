<%@ page import="com.darkhorseventures.cfsbase.User" %>
<%@ include file="initPage.jsp" %>
<jsp:useBean id="UserRecord" class="com.darkhorseventures.cfsbase.User" scope="request"/>
<a href="/Users.do?command=ListUsers">Back to List</a>
<p>
<form name="details" action="/Users.do?auto-populate=true" method="post">
<input type=hidden name="id" value="<%= UserRecord.getId() %>">
<input type=button name="action" value="Modify"	onClick="document.details.command.value='ModifyUser';document.details.submit()">
<input type=button name="action" value="Delete" onClick="document.details.command.value='DeleteUser';document.details.submit()">
<br>
&nbsp;
<table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr bgcolor="#DEE0FA">
    <td colspan=2 valign=center align=left>
	    <strong>User: <%= toHtml(UserRecord.getContact().getNameFirst()) %> <%= toHtml(UserRecord.getContact().getNameLast()) %></strong>
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
    <td width="150">Last Login</td>
    <td><%= (UserRecord.getLastLogin()) %></td>
  </tr>
</table>
<br>
<input type=hidden name="command" value="">
<input type=button name="action" value="Modify"	onClick="document.details.command.value='ModifyUser';document.details.submit()">
<input type=button name="action" value="Delete" onClick="document.details.command.value='DeleteUser';document.details.submit()">
