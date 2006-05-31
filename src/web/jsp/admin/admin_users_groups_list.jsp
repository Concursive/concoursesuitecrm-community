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
<%@ page import="java.util.*,org.aspcfs.modules.admin.base.*,org.aspcfs.modules.contacts.base.Contact" %>
<jsp:useBean id="groupList" class="org.aspcfs.modules.admin.base.UserGroupList" scope="request"/>
<jsp:useBean id="completeGroupList" class="org.aspcfs.modules.admin.base.UserGroupList" scope="request"/>
<jsp:useBean id="groupListInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<jsp:useBean id="thisUser" class="org.aspcfs.modules.admin.base.User" scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<%@ include file="../initPage.jsp" %>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/spanDisplay.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popLookupSelect.js"></SCRIPT>
<script language="JavaScript" type="text/javascript">
function reopen() {
  window.location.href='UserGroups.do?command=GroupList&userId=<%= thisUser.getId() %>';
}
var currentUserGroupIDs = '';
var currentUserGroupNames = '';
</script>
<%
  if (completeGroupList.size() != 0) {
  int counter = 0;
  Iterator it = (Iterator) completeGroupList.iterator();
  while (it.hasNext()) {
    UserGroup group = (UserGroup) it.next();
    counter++;
%>
<script type="text/javascript">
  currentUserGroupIDs = currentUserGroupIDs+'|'+ '<%= group.getId() %>';
  currentUserGroupNames = currentUserGroupNames + '|'+ '<%= StringUtils.jsEscape(group.getName()) %>';
</script>
<%}}%>

<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
  <a href="Admin.do"><dhv:label name="trails.admin">Admin</dhv:label></a> >
  <a href="Users.do?command=ListUsers"><dhv:label name="admin.viewUsers">View Users</dhv:label></a> >
  <a href="Users.do?command=UserDetails&id=<%= thisUser.getId() %>"><dhv:label name="admin.userDetails">User Details</dhv:label></a> >
  <dhv:label name="usergroups.manageGroups">Manage Groups</dhv:label>
</td>
</tr>
</table>
<%-- End Trails --%>
<dhv:container name="users" selected="groups" object="thisUser" param="<%= "id=" + thisUser.getId() %>">
<dhv:permission name="admin-users-view"><a href="javascript:popUserGroupsSelectMultiple('groups','1','lookup_quote_remarks','<%= thisUser.getId() %>',currentUserGroupIDs, currentUserGroupNames,'UserGroups');"><dhv:label name="actionPlan.editGroups">Edit Groups</dhv:label></a></dhv:permission>
<dhv:include name="pagedListInfo.alphabeticalLinks" none="true">
<center><dhv:pagedListAlphabeticalLinks object="groupListInfo"/></center></dhv:include>
<dhv:pagedListStatus title="<%= showError(request, "actionError") %>" object="groupListInfo"/>
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="pagedList">
  <tr>
    <th nowrap>
      <strong><a href="UserGroups.do?command=GroupList&column=ug.group_name&userId=<%= thisUser.getId() %>"><dhv:label name="quotes.productName">Name</dhv:label></a></strong>
      <%= groupListInfo.getSortIcon("ug.group_name") %>
    </th>
    <th nowrap>
      <strong><a href="UserGroups.do?command=GroupList&column=ug.entered&userId=<%= thisUser.getId() %>"><dhv:label name="accounts.accounts_calls_list.Entered">Entered</dhv:label></a></strong>
      <%= groupListInfo.getSortIcon("ug.entered") %>
    </th>
    <th nowrap>
      <strong><dhv:label name="dependency.usersingroup">Users</dhv:label></strong>
    </th>
    <th nowrap>
      <strong><a href="UserGroups.do?command=GroupList&column=ug.enabled&userId=<%= thisUser.getId() %>"><dhv:label name="product.enabled">Enabled</dhv:label></a></strong>
      <%= groupListInfo.getSortIcon("ug.enabled") %>
    </th>
  </tr>
<%
  Iterator i = groupList.iterator();
  if (i.hasNext()) {
    int rowid = 0;
    int count = 0;
    while (i.hasNext()) {
      count++;
      rowid = (rowid != 1?1:2);
      UserGroup thisGroup = (UserGroup) i.next();
%>      
      <tr class="row<%= rowid %>" width="8">
        <td valign="top" width="100%">
          <%-- <a href="UserGroups.do?command=Details&groupId=<%= thisGroup.getId() %>"><%= toHtml(thisGroup.getName()) %></a> --%>
          <%= toHtml(thisGroup.getName()) %>
        </td>
        <td valign="top" nowrap>
          <zeroio:tz timestamp="<%= thisGroup.getEntered() %>" dateOnly="true" timeZone="<%= User.getTimeZone() %>" showTimeZone="false" default="&nbsp;"/>
        </td>
        <td valign="top" nowrap><%= thisGroup.getUserCount() %></td>
        <td valign="top" nowrap>
          <%= thisGroup.getEnabled() %>
        </td>
      </tr>
<%
    }
  } else {
%>
<tr>
    <td class="containerBody" valign="center" colspan="4">
      <dhv:label name="usergroups.noUserGroupsFound.txt">No user groups found.</dhv:label>
    </td>
  </tr>
<%} %>
</table>
<br>
<dhv:pagedListControl object="groupListInfo"/>
</dhv:container>

