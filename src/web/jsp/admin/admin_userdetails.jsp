<%-- THIS FORM INCORRECTLY USES BUTTONS NAMED 'action' AND SHOULD BE UPDATED --%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ page import="java.util.*,java.text.DateFormat,org.aspcfs.modules.admin.base.*" %>
<%@ include file="../initPage.jsp" %>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/confirmDelete.js"></script>
<jsp:useBean id="UserRecord" class="org.aspcfs.modules.admin.base.User" scope="request"/>
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="Admin.do">Admin</a> >
<a href="Users.do?command=ListUsers">View Users</a> >
User Details
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
<dhv:permission name="admin-users-delete">
  <input type="button" name="action" value="Disable" onClick="javascript:window.location.href='Users.do?command=DisableUserConfirm&id=<%= UserRecord.getId() %>'">
</dhv:permission>
</dhv:evaluate>
<dhv:permission name="admin-users-edit,admin-users-delete">
  <br>
  &nbsp;
</dhv:permission>
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <td class="title" colspan="2">
      <strong>Primary Information</strong>&nbsp;
      <% if(UserRecord.getContact().getEmployee()){ %>
        [ <a href="CompanyDirectory.do?command=EmployeeDetails&empid=<%= UserRecord.getContact().getId() %>">Employee Link</a> ]
      <% }else{ %>
        [ <a href="Contacts.do?command=Details&id=<%= UserRecord.getContact().getId() %>">Account Contact Link</a> ]
      <% } %>
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
    <td><zeroio:tz timestamp="<%= UserRecord.getLastLogin() %>" default="&nbsp;"/></td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">Account Expires On</td>
    <td><zeroio:tz timestamp="<%= UserRecord.getExpires() %>" default="&nbsp;"/></td>
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
<dhv:permission name="admin-users-delete">
  <input type="button" name="action" value="Disable" onClick="javascript:window.location.href='Users.do?command=DisableUserConfirm&id=<%= UserRecord.getId() %>'">
</dhv:permission>
</dhv:evaluate>
</td></tr>
</table>
</form>
