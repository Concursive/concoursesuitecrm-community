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
<%@ page import="java.util.*,org.aspcfs.modules.admin.base.*,org.aspcfs.modules.contacts.base.Contact" %>
<jsp:useBean id="UserList" class="org.aspcfs.modules.admin.base.UserList" scope="request"/>
<jsp:useBean id="UserListInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<jsp:useBean id="userListBatchInfo" class="org.aspcfs.utils.web.BatchInfo" scope="request"/>
<jsp:useBean id="roleList" class="org.aspcfs.modules.admin.base.RoleList" scope="request"/>
<jsp:useBean id="APP_SIZE" class="java.lang.String" scope="application"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<jsp:useBean id="systemStatus" class="org.aspcfs.controller.SystemStatus" scope="request"/>
<%@ include file="../initPage.jsp" %>
<%-- Initialize the drop-down menus --%>
<%@ include file="../initPopupMenu.jsp" %>
<%@ include file="admin_listusers_menu.jsp" %>
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
<dhv:label name="admin.viewUsers">View Users</dhv:label>
</td>
</tr>
</table>
<%-- End Trails --%>
<dhv:evaluate if="<%= hasText(APP_SIZE) %>">
  <table class="note" cellspacing="0">
    <tr>
      <th><img src="images/icons/stock_about-16.gif" border="0" align="absmiddle"/></th>
      <td><dhv:label name="admin.installedLicenseLimits.text" param='<%= "appsize="+APP_SIZE %>'>The installed license limits this system to <%= APP_SIZE %> active users.</dhv:label></td></tr>
  </table>
</dhv:evaluate>
<dhv:permission name="admin-users-add"><a href="Users.do?command=InsertUserForm"><dhv:label name="admin.addNewUser">Add New User</dhv:label></a></dhv:permission>
<dhv:include name="pagedListInfo.alphabeticalLinks" none="true">
<center><dhv:pagedListAlphabeticalLinks object="UserListInfo"/></center></dhv:include>
<table width="100%" border="0">
  <tr>
    <form name="userForm" method="post" action="Users.do?command=ListUsers">
    <td align="left">
      <select name="listView" onChange="javascript:document.userForm.submit();">
        <option <%= UserListInfo.getOptionValue("enabled") %>><dhv:label name="dependency.activeUsers">Active Users</dhv:label></option>
        <option <%= UserListInfo.getOptionValue("disabled") %>><dhv:label name="admin.inactiveUsers">Inactive Users</dhv:label></option>
        <dhv:permission name="demo-view">
        <option <%= UserListInfo.getOptionValue("aliases") %>><dhv:label name="admin.aliasedUsers">Aliased Users</dhv:label></option>
        </dhv:permission>
      </select>
      <%= roleList.getHtmlSelect("listFilter1", UserList.getRoleId()) %>
    </td>
    <td>
      <dhv:pagedListStatus title='<%= showError(request, "actionError") %>' object="UserListInfo"/>
    </td>
    </form>
  </tr>
</table>
<dhv:batch object="userListBatchInfo">
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="pagedList">
  <tr>
    <th align="center">
      &nbsp;
    </th>
    <th nowrap>
      <b><a href="Users.do?command=ListUsers&column=c.namelast"><dhv:label name="contacts.name">Name</dhv:label></a></b>
      <%= UserListInfo.getSortIcon("c.namelast") %>
    </th>
    <th nowrap>
      <b><a href="Users.do?command=ListUsers&column=a.username"><dhv:label name="accounts.Username">Username</dhv:label></a></b>
      <%= UserListInfo.getSortIcon("a.username") %>
    </th>
    <th nowrap>
      <b><a href="Users.do?command=ListUsers&column=r.role"><dhv:label name="project.role">Role</dhv:label></a></b>
      <%= UserListInfo.getSortIcon("r.role") %>
    </th>
    <th nowrap>
      <b><dhv:label name="admin.reportsTo">Reports To</dhv:label></b>
    </th>
    <th nowrap>
      <b><dhv:label name="admin.user.site">Site</dhv:label></b>
    </th>
    <th nowrap>
      <b><dhv:label name="admin.config.httpApiAccess">HTTP-API Access</dhv:label></b>
    </th>
    <th nowrap>
      <b><dhv:label name="admin.config.webdavAccess">Webdav Access</dhv:label></b>
    </th>
  </tr>
<%
  Iterator i = UserList.iterator();
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
        <td valign="center" align="center" nowrap>
          <dhv:batchInput object="userListBatchInfo" value="<%= thisUser.getId() %>" hiddenParams="userId|roleId" 
                    hiddenValues='<%= thisUser.getId() + "|" + thisUser.getRoleId() %>'/>
          <% int status = thisUser.getEnabled() ? 1 : 0; %>
          <dhv:permission name="admin-users-edit" none="true"><% status = -1; %></dhv:permission>
          <%-- Use the unique id for opening the menu, and toggling the graphics --%>
          <a href="javascript:displayMenu('select<%= count %>','menuUser', '<%= thisUser.getId() %>','<%= status %>','<%= !thisContact.getEnabled() || thisContact.isTrashed() %>');" onMouseOver="over(0, <%= count %>)" onmouseout="out(0, <%= count %>); hideMenu('menuUser');"><img src="images/select.gif" name="select<%= count %>" id="select<%= count %>" align="absmiddle" border="0"></a>
        </td>
        <td width="60%">
          <a href="Users.do?command=UserDetails&id=<%= thisUser.getId() %>"><%= toHtml(thisContact.getNameLastFirst()) %></a>
        </td>
        <td width="20%" nowrap>
          <%= toHtml(thisUser.getUsername()) %>
        </td>
        <td nowrap>
          <%= toHtml(thisUser.getRole()) %>
        </td>
        <td nowrap>
          <dhv:username id="<%= thisUser.getManagerId() %>"/>
          <dhv:evaluate if="<%=thisUser.getManagerId() != -1 %>">
            <dhv:evaluate if="<%=!(thisUser.getManagerUserEnabled())%>"><font color="red">*</font></dhv:evaluate>
          </dhv:evaluate>
        </td>
        <td nowrap>
          <%= toHtml(thisUser.getSiteIdName()) %>
        </td>
        <td nowrap align="center">
          <dhv:evaluate if="<%= thisUser.getHasHttpApiAccess() %>">
            <dhv:label name="account.yes">Yes</dhv:label>
          </dhv:evaluate>
          <dhv:evaluate if="<%= !thisUser.getHasHttpApiAccess() %>">
            <dhv:label name="account.no">No</dhv:label>
          </dhv:evaluate>
        </td>
        <td nowrap align="center">
          <dhv:evaluate if="<%= thisUser.getHasWebdavAccess() %>">
            <dhv:label name="account.yes">Yes</dhv:label>
          </dhv:evaluate>
          <dhv:evaluate if="<%= !thisUser.getHasWebdavAccess() %>">
            <dhv:label name="account.no">No</dhv:label>
          </dhv:evaluate>
        </td>
      </tr>
<%
    }
  } else {
%>  
<tr>
    <td class="containerBody" valign="center" colspan="8">
      <dhv:label name="admin.noUsersFound">No users found.</dhv:label>
    </td>
  </tr>
<%  
  }
%>
</table>
</dhv:batch>
<br>
<dhv:pagedListControl object="UserListInfo" tdClass="row1">
  <dhv:batchList object="userListBatchInfo" returnURL="Users.do?command=ListUsers">
    <dhv:batchItem display='<%= systemStatus.getLabel("batch.enable.webdavaccess", "Enable Webdav Access") %>' 
            link="Users.do?command=ProcessBatch&action=Webdav&status=Enable" />
    <dhv:batchItem display='<%= systemStatus.getLabel("batch.enable.httpapiaccess", "Enable HTTP-API Access") %>' 
            link="Users.do?command=ProcessBatch&action=HttpAPI&status=Enable" />
    <dhv:batchItem display='<%= systemStatus.getLabel("batch.disable.webdavaccess", "Disable Webdav Access") %>' 
            link="Users.do?command=ProcessBatch&action=Webdav&status=Disable" />
    <dhv:batchItem display='<%= systemStatus.getLabel("batch.disable.httpapiaccess", "Disable HTTP-API Access") %>' 
            link="Users.do?command=ProcessBatch&action=HttpAPI&status=Disable" />
  </dhv:batchList>
</dhv:pagedListControl>
