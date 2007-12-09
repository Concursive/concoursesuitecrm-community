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
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ page import="java.text.DateFormat,java.util.*,org.aspcfs.modules.admin.base.*,org.aspcfs.modules.contacts.base.*"%>
<jsp:useBean id="userGroup" class="org.aspcfs.modules.admin.base.UserGroup" scope="request"/>
<jsp:useBean id="SiteIdList" class="org.aspcfs.utils.web.LookupList" scope="request"/> 
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<jsp:useBean id="applicationPrefs" class="org.aspcfs.controller.ApplicationPrefs" scope="application"/>
<%@ include file="../initPage.jsp" %>
<script type="text/javascript">
  function reopen() {
    window.location.href='UserGroups.do?command=Details&groupId=<%= userGroup.getId() %>';
  }
</script>
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
  <a href="Admin.do"><dhv:label name="trails.admin">Admin</dhv:label></a> >
  <a href="UserGroups.do?command=List"><dhv:label name="usergroups.manageGroups">Manage Groups</dhv:label></a> >
  <dhv:label name="campaign.groupDetails">Group Details</dhv:label>
</td>
</tr>
</table>
<%-- End Trails --%>
<dhv:evaluate if="<%= userGroup.getPermission(User.getUserRecord().getSiteId()) > PermissionCategory.VIEW %>">
<input type="button" value="<dhv:label name="button.modify">Modify</dhv:label>" onClick="javascript:window.location.href='UserGroups.do?command=Modify&groupId=<%= userGroup.getId() %>';"/> 
<input type="button" value="<dhv:label name="button.delete">Delete</dhv:label>" onClick="javascript:popURLReturn('UserGroups.do?command=ConfirmDelete&groupId=<%= userGroup.getId() %>&popup=true','UserGroups.do?command=List', 'Delete_group','320','200','yes','no');" />
<br /><br />
</dhv:evaluate>
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="2">
      <strong><dhv:label name="campaign.groupDetails">Group Details</dhv:label></strong>
    </th>
  </tr>
	<tr>
    <td class="formLabel">
      <dhv:label name="admin.groupName">Group Name</dhv:label>
    </td>
    <td>
      <%= toHtml(userGroup.getName()) %>
    </td>
  </tr>
	<tr>
    <td class="formLabel">
      <dhv:label name="accounts.accountasset_include.Description">Description</dhv:label>
    </td>
    <td>
      <%= toHtml(userGroup.getDescription()) %>
    </td>
  </tr>
	<tr>
    <td class="formLabel">
      <dhv:label name="product.enabled">Enabled</dhv:label>
    </td>
    <td><%= userGroup.getEnabled() %></td>
  </tr>
	<tr>
    <td class="formLabel">
      <dhv:label name="accounts.site">Site</dhv:label>
    </td>
    <td>
      <%= toHtml(SiteIdList.getSelectedValue(User.getUserRecord().getSiteId() > -1?User.getUserRecord().getSiteId():userGroup.getSiteId())) %>
    </td>
  </tr>
</table>
<%if (userGroup.getGroupUsers() != null) {%>
<br />
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="4">
      <strong><dhv:label name="usergroups.groupUserList">Group User List</dhv:label></strong>
    </th>
  </tr>
  <tr>
    <th nowrap>
      <strong><dhv:label name="contacts.name">Name</dhv:label></strong>
    </th>
    <th nowrap>
      <strong><dhv:label name="accounts.Username">Username</dhv:label></strong>
    </th>
    <th nowrap>
      <strong><dhv:label name="project.role">Role</dhv:label></strong>
    </th>
    <th nowrap>
      <strong><dhv:label name="admin.reportsTo">Reports To</dhv:label></strong>
    </th>
  </tr>
<%
  Iterator i = userGroup.getGroupUsers().iterator();
  if (i.hasNext()) {
    int rowid = 0;
    int count = 0;
    while (i.hasNext()) {
      count++;
      rowid = (rowid != 1?1:2);
      User thisUser = (User) i.next();
      Contact thisContact = (Contact) thisUser.getContact();
%>
      <tr class="row<%= rowid %>" width="8">
        <td width="60%">
          <dhv:evaluate if="<%= userGroup.getPermission(User.getUserRecord().getSiteId()) > PermissionCategory.VIEW  && (User.getUserRecord().getSiteId() == -1 || thisUser.getSiteId() != -1) %>">
            <a href="Users.do?command=UserDetails&id=<%= thisUser.getId() %>"><%= toHtml(thisContact.getNameLastFirst()) %><dhv:evaluate if="<%=!(thisUser.getEnabled())%>"><font color="red">*</font></dhv:evaluate></a>
          </dhv:evaluate><dhv:evaluate if="<%= userGroup.getPermission(User.getUserRecord().getSiteId()) == PermissionCategory.VIEW|| (User.getUserRecord().getSiteId() != -1 && thisUser.getSiteId() == -1) %>">
            <%= toHtml(thisContact.getNameLastFirst()) %><dhv:evaluate if="<%=!(thisUser.getEnabled())%>"><font color="red">*</font></dhv:evaluate>
          </dhv:evaluate>
        </td>
        <dhv:evaluate if="<%= userGroup.getPermission(User.getUserRecord().getSiteId()) > PermissionCategory.VIEW && (User.getUserRecord().getSiteId() == -1 || thisUser.getSiteId() != -1) %>">
        <td width="20%" nowrap>
          <%= toHtml(thisUser.getUsername()) %>
        </td>
        <td nowrap>
          <%= toHtml(thisUser.getRole()) %>
        </td>
        <td nowrap>
          <dhv:username id="<%= thisUser.getManagerId() %>"/>
          <dhv:evaluate if="<%=!(thisUser.getManagerUserEnabled())%>"><font color="red">*</font></dhv:evaluate>
        </td>
        </dhv:evaluate><dhv:evaluate if="<%= userGroup.getPermission(User.getUserRecord().getSiteId()) == PermissionCategory.VIEW || (User.getUserRecord().getSiteId() != -1 && thisUser.getSiteId() == -1) %>">
          <td colspan="3"><dhv:label name="adminUserGroups.permissionDeniedForUserDetails.text">Permission denied to view User details</dhv:label></td>
        </dhv:evaluate>
      </tr>
<%  }
  } else {
%>
<tr>
    <td class="containerBody" valign="center" colspan="4">
      <dhv:label name="admin.noUsersFound">No users found.</dhv:label>
    </td>
  </tr>
<%} }%>
</table>
<br />
<dhv:evaluate if="<%= userGroup.getPermission(User.getUserRecord().getSiteId()) > PermissionCategory.VIEW %>">
<input type="button" value="<dhv:label name="button.modify">Modify</dhv:label>" onClick="javascript:window.location.href='UserGroups.do?command=Modify&groupId=<%= userGroup.getId() %>';"/> 
<input type="button" value="<dhv:label name="button.delete">Delete</dhv:label>" onClick="javascript:popURLReturn('UserGroups.do?command=ConfirmDelete&groupId=<%= userGroup.getId() %>&popup=true','UserGroups.do?command=List', 'Delete_group','320','200','yes','no');" />
</dhv:evaluate>
