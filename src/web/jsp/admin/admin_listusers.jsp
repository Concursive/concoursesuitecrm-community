<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,org.aspcfs.modules.admin.base.*,org.aspcfs.modules.contacts.base.Contact" %>
<jsp:useBean id="UserList" class="org.aspcfs.modules.admin.base.UserList" scope="request"/>
<jsp:useBean id="UserListInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<%@ include file="../initPage.jsp" %>
<a href="Admin.do">Setup</a> >
View Users<br>
<hr color="#BFBFBB" noshade>
<dhv:permission name="admin-users-add"><a href="Users.do?command=InsertUserForm">Add New User</a></dhv:permission>
<center><%= UserListInfo.getAlphabeticalPageLinks() %></center>
<table width="100%" border="0">
  <tr>
    <form name="userForm" method="post" action="Users.do?command=ListUsers">
    <td align="left">
      <select name="listView" onChange="javascript:document.forms[0].submit();">
        <option <%= UserListInfo.getOptionValue("enabled") %>>Active Users</option>
        <option <%= UserListInfo.getOptionValue("disabled") %>>Inactive Users</option>
        <dhv:permission name="demo-view">
        <option <%= UserListInfo.getOptionValue("aliases") %>>Aliased Users</option>
        </dhv:permission>
      </select>
    </td>
    <td>
      <dhv:pagedListStatus title="<%= showError(request, "actionError") %>" object="UserListInfo"/>
    </td>
    </form>
  </tr>
</table>
<table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr class="title">
    <dhv:permission name="admin-users-edit,admin-users-delete">
    <td>
      <strong>Action</strong>
    </td>
    </dhv:permission>
    <td nowrap>
      <b><a href="Users.do?command=ListUsers&column=c.namelast">Name</a></b>
      <%= UserListInfo.getSortIcon("c.namelast") %>
    </td>
    <td nowrap>
      <b><a href="Users.do?command=ListUsers&column=username">Username</a></b>
      <%= UserListInfo.getSortIcon("username") %>
    </td>
    <td nowrap>
      <b><a href="Users.do?command=ListUsers&column=role">Role</a></b>
      <%= UserListInfo.getSortIcon("role") %>
    </td>
    <td nowrap>
      <b><a href="Users.do?command=ListUsers&column=mgr_namelast">Reports To</a></b>
      <%= UserListInfo.getSortIcon("mgr_namelast") %>
    </td>
  </tr>
<%
  Iterator i = UserList.iterator();
  if (i.hasNext()) {
    int rowid = 0;
    while (i.hasNext()) {
      rowid = (rowid != 1?1:2);
      User thisUser = (User) i.next();
      Contact thisContact = (Contact) thisUser.getContact();
%>      
      <tr class="row<%= rowid %>" width="8">
        <dhv:permission name="admin-users-edit,admin-users-delete">
        <td valign="center" align="center" nowrap>
          <dhv:evaluate exp="<%=(thisUser.getEnabled())%>">
          <dhv:permission name="admin-users-edit"><a href="Users.do?command=ModifyUser&id=<%= thisUser.getId() %>&return=list">Edit</a></dhv:permission><dhv:permission name="admin-users-edit,admin-users-delete" all="true">|</dhv:permission><dhv:permission name="admin-users-delete"><a href="Users.do?command=DisableUserConfirm&id=<%= thisUser.getId() %>&return=list">Disable</a></dhv:permission>
          </dhv:evaluate>
          <dhv:evaluate exp="<%=!(thisUser.getEnabled())%>">
          <dhv:permission name="admin-users-edit"><a href="Users.do?command=EnableUser&id=<%= thisUser.getId() %>&return=list">Enable</a></dhv:permission>
          </dhv:evaluate>
        </td>
        </dhv:permission>
        <td>
          <a href="Users.do?command=UserDetails&id=<%= thisUser.getId() %>"><%= toHtml(thisContact.getNameLastFirst()) %></a>
        </td>
        <td>
          <%= toHtml(thisUser.getUsername()) %>
        </td>
        <td>
          <%= toHtml(thisUser.getRole()) %>
        </td>
        <td>
          <dhv:username id="<%= thisUser.getManagerId() %>"/>
          <dhv:evaluate exp="<%=!(thisUser.getManagerUserEnabled())%>"><font color="red">*</font></dhv:evaluate>
        </td>
      </tr>
<%
    }
  } else {
%>  
<tr>
    <td class="containerBody" valign="center" colspan="5">
      No users found.
    </td>
  </tr>
<%  
  }
%>
</table>
<br>
<dhv:pagedListControl object="UserListInfo" tdClass="row1"/>

