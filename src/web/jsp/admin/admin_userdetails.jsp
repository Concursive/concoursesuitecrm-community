<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,org.aspcfs.modules.admin.base.*" %>
<%@ include file="../initPage.jsp" %>
<script language="JavaScript" TYPE="text/javascript" SRC="/javascript/confirmDelete.js"></script>
<jsp:useBean id="UserRecord" class="org.aspcfs.modules.admin.base.User" scope="request"/>
<form name="details" action="/Users.do?auto-populate=true" method="post">
<a href="Admin.do">Setup</a> >
<a href="Users.do?command=ListUsers">View Users</a> >
User Details<br>
<hr color="#BFBFBB" noshade>
<input type=hidden name="id" value="<%= UserRecord.getId() %>">


<table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr class="containerHeader">
    <td>
      <strong><%= toHtml(UserRecord.getUsername()) %> (<%= toHtml(UserRecord.getContact().getNameLastFirst()) %>)</strong>
    </td>
  </tr>
  <tr class="containerMenu">
    <td>
      <% String param1 = "id=" + UserRecord.getId(); %>      
      <dhv:container name="users" selected="details" param="<%= param1 %>" />
    </td>
  </tr>
  <tr>
    <td class="containerBack">


<dhv:evaluate exp="<%=(UserRecord.getEnabled())%>">
<dhv:permission name="admin-users-edit">
  <input type="button" name="action" value="Modify"	onClick="document.details.command.value='ModifyUser';document.details.submit()">
</dhv:permission>
</dhv:evaluate>

<dhv:evaluate exp="<%=!(UserRecord.getEnabled())%>">
<dhv:permission name="admin-users-edit">
  <input type=button name="action" value="Enable"	onClick="document.details.command.value='EnableUser';document.details.submit()">
</dhv:permission>
</dhv:evaluate>

<dhv:evaluate exp="<%=(UserRecord.getEnabled())%>">
<dhv:permission name="admin-users-edit">
  <input type="button" name="action" value="Disable" onClick="document.details.command.value='DisableUserConfirm';document.details.submit()">
</dhv:permission>
</dhv:evaluate>

<dhv:permission name="admin-users-edit,admin-users-delete">
  <br>
  &nbsp;
</dhv:permission>

<table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr class="title">
    <td colspan=2 valign=center align=left>
      <strong>Primary Information</strong>
    </td>   

  <tr class="containerBody">
    <td nowrap class="formLabel">Username</td>
    <td><%= toHtml(UserRecord.getUsername()) %></td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">User Group (Role)</td>
    <td><%= toHtml(UserRecord.getRole()) %></td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">Reports To</td>
    <td><%= toHtml(UserRecord.getManager()) %>
    <dhv:evaluate exp="<%=!(UserRecord.getManagerUserEnabled())%>"><font color="red">*</font></dhv:evaluate>
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">Aliased To</td>
    <td><%= toHtml(UserRecord.getAliasName()) %></td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">Last Login</td>
    <td><%= (UserRecord.getLastLoginString()) %></td>
  </tr>
    <tr class="containerBody">
    <td nowrap class="formLabel">Account Expires On</td>
    <td><%= toHtml(UserRecord.getExpiresString()) %></td>
  </tr>
</table>
<br>
<input type=hidden name="command" value="">
<dhv:evaluate exp="<%=(UserRecord.getEnabled())%>">
<dhv:permission name="admin-users-edit">
  <input type="button" name="action" value="Modify"	onClick="document.details.command.value='ModifyUser';document.details.submit()">
</dhv:permission>
</dhv:evaluate>

<dhv:evaluate exp="<%=!(UserRecord.getEnabled())%>">
<dhv:permission name="admin-users-edit">
  <input type=button name="action" value="Enable"	onClick="document.details.command.value='EnableUser';document.details.submit()">
</dhv:permission>
</dhv:evaluate>

<dhv:evaluate exp="<%=(UserRecord.getEnabled())%>">
<dhv:permission name="admin-users-edit">
  <input type="button" name="action" value="Disable" onClick="document.details.command.value='DisableUserConfirm';document.details.submit()">
</dhv:permission>
</dhv:evaluate>
</td></tr>
</table>
</form>
