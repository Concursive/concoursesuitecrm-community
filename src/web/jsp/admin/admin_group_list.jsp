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
<jsp:useBean id="userGroupListInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<jsp:useBean id="SiteIdList" class="org.aspcfs.utils.web.LookupList" scope="request"/> 
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<%@ include file="../initPage.jsp" %>
<%-- Initialize the drop-down menus --%>
<%@ include file="../initPopupMenu.jsp" %>
<%@ include file="admin_group_list_menu.jsp" %>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/spanDisplay.js"></SCRIPT>
<script language="JavaScript" type="text/javascript">
  <%-- Preload image rollovers for drop-down menu --%>
  loadImages('select');
</script>
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="Admin.do"><dhv:label name="trails.admin">Admin</dhv:label></a> >
<dhv:label name="usergroups.manageGroups">Manage Groups</dhv:label>
</td>
</tr>
</table>
<%-- End Trails --%>
<dhv:permission name="admin-users-add"><a href="UserGroups.do?command=Add"><dhv:label name="actionPlan.addNewGroup">Add New Group</dhv:label></a></dhv:permission>
<dhv:include name="pagedListInfo.alphabeticalLinks" none="true">
<center><dhv:pagedListAlphabeticalLinks object="userGroupListInfo"/></center></dhv:include>
<dhv:pagedListStatus title='<%= showError(request, "actionError") %>' object="userGroupListInfo"/>
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="pagedList">
  <tr>
    <th align="center">
      &nbsp;
    </th>
    <th nowrap>
      <strong><a href="UserGroups.do?command=List&column=ug.group_name"><dhv:label name="quotes.productName">Name</dhv:label></a></strong>
      <%= userGroupListInfo.getSortIcon("ug.group_name") %>
    </th>
    <th align="center" nowrap>
      <strong><dhv:label name="admin.user.site">Site</dhv:label></strong>
    </th>
    <th nowrap>
      <strong><a href="UserGroups.do?command=List&column=ug.entered"><dhv:label name="accounts.accounts_calls_list.Entered">Entered</dhv:label></a></strong>
      <%= userGroupListInfo.getSortIcon("ug.entered") %>
    </th>
    <th nowrap>
      <strong><dhv:label name="dependency.usersingroup">Users</dhv:label></strong>
    </th>
    <th nowrap>
      <strong><a href="UserGroups.do?command=List&column=ug.enabled"><dhv:label name="reports.admin.users.enabled">Enabled</dhv:label></a></strong>
      <%= userGroupListInfo.getSortIcon("ug.enabled") %>
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
        <td valign="center" align="center" nowrap>
          <dhv:evaluate if="<%= thisGroup.getPermission(User.getUserRecord().getSiteId()) > PermissionCategory.NONE %>">
            <a href="javascript:displayMenu('select<%= count %>','menuGroup', '<%= thisGroup.getId() %>', '<%= thisGroup.getPermission(User.getUserRecord().getSiteId()) %>');" 
              onMouseOver="over(0, <%= count %>)" onmouseout="out(0, <%= count %>); hideMenu('menuGroup');"><img src="images/select.gif" name="select<%= count %>" id="select<%= count %>" align="absmiddle" border="0"></a>
          </dhv:evaluate>
          <dhv:evaluate if="<%= thisGroup.getPermission(User.getUserRecord().getSiteId()) == PermissionCategory.NONE %>">&nbsp;</dhv:evaluate>
        </td>
        <td width="90%">
          <%-- <a href="UserGroups.do?command=Details&groupId=<%= thisGroup.getId() %>"><%= toHtml(thisGroup.getName()) %></a> --%>
          <%= toHtml(thisGroup.getName()) %>
        </td>
        <td width="10%" valign="top" align="center">
          <%= SiteIdList.getSelectedValue(thisGroup.getSiteId()) %>
        </td>
        <td nowrap>
          <zeroio:tz timestamp="<%= thisGroup.getEntered() %>" dateOnly="true" timeZone="<%= User.getTimeZone() %>" showTimeZone="false" default="&nbsp;"/>
        </td>
        <td nowrap>
          <%= toHtml(thisGroup.getGroupUsers() != null?String.valueOf(thisGroup.getGroupUsers().size()):"") %>
        </td>
        <td nowrap>
          <%= thisGroup.getEnabled() %>
        </td>
      </tr>
<%
    }
  } else {
%>
<tr>
    <td class="containerBody" valign="center" colspan="6">
      <dhv:label name="usergroups.noUserGroupsFound.txt">No user groups found.</dhv:label>
    </td>
  </tr>
<%  
  }
%>
</table>
<br>
<dhv:pagedListControl object="userGroupListInfo" tdClass="row1"/>

