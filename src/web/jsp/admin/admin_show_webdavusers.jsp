<%-- 
  - Copyright(c) 2006 Dark Horse Ventures LLC (http://www.centriccrm.com/) All
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
  - Version: $Id: 
  - Description:
  --%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ page
    import="org.aspcfs.modules.admin.base.User,org.aspcfs.modules.contacts.base.Contact,java.util.Iterator" %>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean"
             scope="session"/>
<jsp:useBean id="userList" class="org.aspcfs.modules.admin.base.UserList"
             scope="request"/>
<jsp:useBean id="roleList" class="org.aspcfs.modules.admin.base.RoleList"
             scope="request"/>
<jsp:useBean id="webdavUserListInfo" class="org.aspcfs.utils.web.PagedListInfo"
             scope="session"/>
<jsp:useBean id="selectedUsers" class="java.util.ArrayList" scope="request"/>
<%@ include file="../initPage.jsp" %>
<script language="Javascript">
  function submitForm(form, access) {
    form.action = 'AdminWebdavManager.do?command=SetWebdavAccess&access=' + access;
    form.submit();
  }
</script>
<%
  Iterator p = selectedUsers.iterator();
  String previousSelection = "";
  while (p.hasNext()) {
    String id = (String) p.next();
    if (!"".equals(previousSelection)) {
      previousSelection = previousSelection + "|";
    }
    previousSelection = previousSelection + id;
  }
%>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript"
        SRC="javascript/popAccounts.js"></script>
<%-- Trails --%>
<table class="trails" cellspacing="0">
  <tr>
    <td>
      <a href="Admin.do"><dhv:label name="trails.admin">Admin</dhv:label></a> >
      <a href="AdminConfig.do?command=ListGlobalParams"><dhv:label
          name="admin.configureSystem">Configure System</dhv:label></a> >
      <dhv:label name="admin.config.webdavManager">Webdav User
        Manager</dhv:label>
    </td>
  </tr>
</table>
<%-- End Trails --%>
<form name="httpWebdavForm" method="post"
      action="AdminWebdavManager.do?command=ShowUsers">
<table width="100%" border="0">
  <tr>
    <td align="left">
      <strong><dhv:label name="accounts.accounts_add.Role">
        Role</dhv:label></strong>
      : <%= roleList.getHtmlSelect("roleId", userList.getRoleId()) %>
    </td>
    <td>
      <dhv:pagedListStatus title="<%= showError(request, "actionError") %>"
                           object="webdavUserListInfo"/>
    </td>
  </tr>
</table>
<table cellpadding="4" cellspacing="0" border="0" width="100%"
       class="pagedList">
  <tr>
    <th align="center">
      &nbsp;
    </th>
    <th nowrap>
      <b><a href="AdminWebdavManager.do?command=ShowUsers&column=c.namelast">
        <dhv:label name="contacts.name">Name</dhv:label></a></b>
      <%= webdavUserListInfo.getSortIcon("c.namelast") %>
    </th>
    <th nowrap>
      <b><a href="AdminWebdavManager.do?command=ShowUsers&column=a.username">
        <dhv:label name="accounts.Username">Username</dhv:label></a></b>
      <%= webdavUserListInfo.getSortIcon("a.username") %>
    </th>
    <th nowrap>
      <b><a href="AdminWebdavManager.do?command=ShowUsers&column=r.role">
        <dhv:label name="project.role">Role</dhv:label></a></b>
      <%= webdavUserListInfo.getSortIcon("r.role") %>
    </th>
    <th nowrap>
      <b><dhv:label name="admin.reportsTo">Reports To</dhv:label></b>
    </th>
    <th nowrap>
      <b><dhv:label name="admin.user.site">Site</dhv:label></b>
    </th>
    <th nowrap>
      <b><dhv:label name="admin.config.webdavAccess">Webdav
        Access</dhv:label></b>
    </th>
  </tr>
  <%
    Iterator i = userList.iterator();
    if (i.hasNext()) {
      int rowid = 0;
      int count = 0;
      while (i.hasNext()) {
        count++;
        rowid = (rowid != 1 ? 1 : 2);
        User thisUser = (User) i.next();
        Contact thisContact = (Contact) thisUser.getContact();
  %>
  <tr class="row<%= rowid %>" width="8">
    <td valign="center" align="center" nowrap>
      <input type="checkbox" name="user<%= count %>"
             value="<%= thisUser.getId() %>" <%= (selectedUsers.indexOf(String.valueOf(thisUser.getId())) != -1 ? " checked" : "") %>
             onClick="highlight(this,'<%= User.getBrowserId() %>');">
      <input type="hidden" name="hiddenUserId<%= count %>"
             value="<%= thisUser.getId() %>">
      <input type="hidden" name="hiddenRoleId<%= count %>"
             value="<%= thisUser.getRoleId() %>">
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
        <dhv:evaluate if="<%=!(thisUser.getManagerUserEnabled())%>"><font
            color="red">*</font></dhv:evaluate>
      </dhv:evaluate>
    </td>
    <td nowrap>
      <%= toHtml(thisUser.getSiteIdName()) %>
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
    <td class="containerBody" valign="center" colspan="6">
      <dhv:label name="admin.noUsersFound">No users found.</dhv:label>
    </td>
  </tr>
  <%
    }
  %>
</table>
<br/>
<input type="button"
       value="<dhv:label name="button.enable.webdavaccess">Enable Webdav Access</dhv:label>"
       onClick="javascript:submitForm(this.form, true);">
<input type="button"
       value="<dhv:label name="button.disable.webdavaccess">Disable Webdav Access</dhv:label>"
       onClick="javascript:submitForm(this.form, false);">
<input type="hidden" name="previousSelection" value="<%= previousSelection %>"/>
<input type="hidden" name="rowcount" value="0">
<a href="javascript:SetChecked(1,'user','httpUserForm','<%= User.getBrowserId() %>');">
  <dhv:label name="quotes.checkAll">Check All</dhv:label></a>
<a href="javascript:SetChecked(0,'user','httpUserForm','<%= User.getBrowserId() %>');">
  <dhv:label name="quotes.clearAll">Clear All</dhv:label></a>
</form>
