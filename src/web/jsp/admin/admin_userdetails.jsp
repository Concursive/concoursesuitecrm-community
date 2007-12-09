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
<a href="Admin.do"><dhv:label name="trails.admin">Admin</dhv:label></a> >
<a href="Users.do?command=ListUsers"><dhv:label name="admin.viewUsers">View Users</dhv:label></a> >
<dhv:label name="admin.userDetails">User Details</dhv:label>
</td>
</tr>
</table>
<%-- End Trails --%>
<dhv:container name="users" selected="details" object="UserRecord" param='<%= "id=" + UserRecord.getId() %>'>
  <dhv:evaluate if="<%= UserRecord.getContact().getEnabled() && !UserRecord.getContact().isTrashed() %>">
    <dhv:evaluate if="<%=(UserRecord.getEnabled())%>">
    <dhv:permission name="admin-users-edit">
      <input type="button" name="action" value="<dhv:label name="global.button.modify">Modify</dhv:label>"	onClick="javascript:window.location.href='Users.do?command=ModifyUser&id=<%= UserRecord.getId() %>'">
    </dhv:permission>
    </dhv:evaluate>
    <dhv:evaluate if="<%= !(UserRecord.getEnabled()) %>">
    <dhv:permission name="admin-users-edit">
      <input type="button" name="action" value="<dhv:label name="global.button.Enable">Enable</dhv:label>"	onClick="javascript:window.location.href='Users.do?command=EnableUser&id=<%= UserRecord.getId() %>'">
    </dhv:permission>
    </dhv:evaluate>
    <dhv:evaluate if="<%=(UserRecord.getEnabled())%>">
    <dhv:permission name="admin-users-delete">
      <input type="button" name="action" value="<dhv:label name="global.button.Disable">Disable</dhv:label>" onClick="javascript:window.location.href='Users.do?command=DisableUserConfirm&id=<%= UserRecord.getId() %>'">
    </dhv:permission>
    </dhv:evaluate>
    <dhv:permission name="admin-users-edit,admin-users-delete">
      <br>
      &nbsp;
    </dhv:permission>
  </dhv:evaluate>
  <table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
    <tr>
      <td class="title" colspan="2">
        <strong><dhv:label name="accounts.accounts_details.PrimaryInformation">Primary Information</dhv:label></strong>&nbsp;
        <dhv:evaluate if="<%= UserRecord.getContact().getEnabled() && !UserRecord.getContact().isTrashed() %>">
          <% if(UserRecord.getContact().getEmployee()){ %>
            [ <a href="CompanyDirectory.do?command=EmployeeDetails&empid=<%= UserRecord.getContact().getId() %>"><dhv:label name="admin.employeeLink">Employee Link</dhv:label></a> ]
          <% }else if (UserRecord.getContact().getOrgId() != -1){ %>
            [ <a href="Contacts.do?command=Details&id=<%= UserRecord.getContact().getId() %>"><dhv:label name="admin.accountContactLink">Account Contact Link</dhv:label></a> ]
          <% } %>
        </dhv:evaluate>
      </td>
    </tr>
    <tr class="containerBody">
      <td class="formLabel"><dhv:label name="accounts.Username">Username</dhv:label></td>
      <td><%= toHtml(UserRecord.getUsername()) %></td>
    </tr>
    <tr class="containerBody">
      <td nowrap class="formLabel"><dhv:label name="admin.userGroupRole">User Group (Role)</dhv:label></td>
      <td><%= toHtml(UserRecord.getRole()) %></td>
    </tr>
    <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="admin.user.site">Site</dhv:label>
    </td>
    <td><%= toHtml(UserRecord.getSiteIdName()) %></td>
  </tr>
  <tr class="containerBody">
      <td nowrap class="formLabel"><dhv:label name="admin.reportsTo">Reports To</dhv:label></td>
      <td>
        <dhv:username id="<%= UserRecord.getManagerId() %>"/>
        <dhv:evaluate if="<%=UserRecord.getManagerId() != -1 %>">
          <dhv:evaluate if="<%= !(UserRecord.getManagerUserEnabled()) %>"><font color="red">*</font></dhv:evaluate>
        </dhv:evaluate>
      </td>
    </tr>
  <dhv:evaluate if="<%= UserRecord.getAlias() != -1 %>">
    <tr class="containerBody">
      <td nowrap class="formLabel"><dhv:label name="admin.aliasedTo">Aliased To</dhv:label></td>
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
  <dhv:evaluate if="<%= UserRecord.getContact().getEnabled()  && !UserRecord.getContact().isTrashed() %>">
    <dhv:evaluate if="<%=(UserRecord.getEnabled())%>">
    <dhv:permission name="admin-users-edit">
      <input type="button" name="action" value="<dhv:label name="global.button.modify">Modify</dhv:label>"	onClick="javascript:window.location.href='Users.do?command=ModifyUser&id=<%= UserRecord.getId() %>'">
    </dhv:permission>
    </dhv:evaluate>
    <dhv:evaluate if="<%=!(UserRecord.getEnabled())%>">
    <dhv:permission name="admin-users-edit">
      <input type="button" name="action" value="<dhv:label name="global.button.Enable">Enable</dhv:label>"	onClick="javascript:window.location.href='Users.do?command=EnableUser&id=<%= UserRecord.getId() %>'">
    </dhv:permission>
    </dhv:evaluate>
    <dhv:evaluate if="<%=(UserRecord.getEnabled())%>">
    <dhv:permission name="admin-users-delete">
      <input type="button" name="action" value="<dhv:label name="global.button.Disable">Disable</dhv:label>" onClick="javascript:window.location.href='Users.do?command=DisableUserConfirm&id=<%= UserRecord.getId() %>'">
    </dhv:permission>
    </dhv:evaluate>
  </dhv:evaluate>
</dhv:container>
