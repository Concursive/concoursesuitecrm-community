<%-- THIS FORM INCORRECTLY USES BUTTONS NAMED 'action' AND SHOULD BE UPDATED --%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,org.aspcfs.modules.admin.base.*" %>
<%@ include file="../initPage.jsp" %>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/confirmDelete.js"></script>
<jsp:useBean id="UserRecord" class="org.aspcfs.modules.admin.base.User" scope="request"/>
<a href="Admin.do">Setup</a> >
<a href="Users.do?command=ListUsers">View Users</a> >
User Details<br>
<hr color="#BFBFBB" noshade>
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
  <input type="button" name="action" value="Modify"	onClick="javascript:window.location.href='Users.do?command=ModifyUser&id=<%= UserRecord.getId() %>'">
</dhv:permission>
</dhv:evaluate>
<dhv:evaluate exp="<%= !(UserRecord.getEnabled()) %>">
<dhv:permission name="admin-users-edit">
  <input type="button" name="action" value="Enable"	onClick="javascript:window.location.href='Users.do?command=EnableUser&id=<%= UserRecord.getId() %>'">
</dhv:permission>
</dhv:evaluate>
<dhv:evaluate exp="<%=(UserRecord.getEnabled())%>">
<dhv:permission name="admin-users-edit">
  <input type="button" name="action" value="Disable" onClick="javascript:window.location.href='Users.do?command=DisableUserConfirm&id=<%= UserRecord.getId() %>'">
</dhv:permission>
</dhv:evaluate>
<dhv:permission name="admin-users-edit,admin-users-delete">
  <br>
  &nbsp;
</dhv:permission>
<table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr class="title">
    <td colspan="2">
      <strong>Primary Information</strong>
    </td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel">Username</td>
    <td><%= toHtml(UserRecord.getUsername()) %></td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">User Group (Role)</td>
    <td><%= toHtml(UserRecord.getRole()) %></td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">Reports To</td>
    <td>
      <dhv:username id="<%= UserRecord.getManagerId() %>"/>
      <dhv:evaluate exp="<%= !(UserRecord.getManagerUserEnabled()) %>"><font color="red">*</font></dhv:evaluate>
    </td>
  </tr>
<dhv:evaluate if="<%= UserRecord.getAlias() != -1 %>">
  <tr class="containerBody">
    <td nowrap class="formLabel">Aliased To</td>
    <td><dhv:username id="<%= UserRecord.getAlias() %>"/></td>
  </tr>
</dhv:evaluate>
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
<dhv:evaluate exp="<%=(UserRecord.getEnabled())%>">
<dhv:permission name="admin-users-edit">
  <input type="button" name="action" value="Modify"	onClick="javascript:window.location.href='Users.do?command=ModifyUser&id=<%= UserRecord.getId() %>'">
</dhv:permission>
</dhv:evaluate>
<dhv:evaluate exp="<%=!(UserRecord.getEnabled())%>">
<dhv:permission name="admin-users-edit">
  <input type="button" name="action" value="Enable"	onClick="javascript:window.location.href='Users.do?command=EnableUser&id=<%= UserRecord.getId() %>'">
</dhv:permission>
</dhv:evaluate>
<dhv:evaluate exp="<%=(UserRecord.getEnabled())%>">
<dhv:permission name="admin-users-edit">
  <input type="button" name="action" value="Disable" onClick="javascript:window.location.href='Users.do?command=DisableUserConfirm&id=<%= UserRecord.getId() %>'">
</dhv:permission>
</dhv:evaluate>
</td></tr>
</table>
</form>
