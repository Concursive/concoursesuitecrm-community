<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,org.aspcfs.modules.admin.base.*,org.aspcfs.modules.contacts.base.Contact" %>
<jsp:useBean id="UserList" class="org.aspcfs.modules.admin.base.UserList" scope="request"/>
<jsp:useBean id="UserListInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<jsp:useBean id="APP_SIZE" class="java.lang.String" scope="application"/>
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
<table class="trails">
<tr>
<td>
<a href="Admin.do">Admin</a> >
View Users
</td>
</tr>
</table>
<%-- End Trails --%>
<%-- License info --%>
<dhv:evaluate if="<%= hasText(APP_SIZE) %>">
<table class="note">
  <tr>
    <th><img src="images/icons/stock_about-16.gif" border="0" align="absmiddle"/></th>
    <td>The installed Dark Horse CRM license limits this system to <%= APP_SIZE %> active users.</td></tr>
</table>
</dhv:evaluate>
<%-- End license info --%>
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
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="pagedList">
  <tr>
    <th align="center">
      <strong>Action</strong>
    </th>
    <th nowrap>
      <b><a href="Users.do?command=ListUsers&column=c.namelast">Name</a></b>
      <%= UserListInfo.getSortIcon("c.namelast") %>
    </th>
    <th nowrap>
      <b><a href="Users.do?command=ListUsers&column=a.username">Username</a></b>
      <%= UserListInfo.getSortIcon("a.username") %>
    </th>
    <th nowrap>
      <b><a href="Users.do?command=ListUsers&column=role">Role</a></b>
      <%= UserListInfo.getSortIcon("role") %>
    </th>
    <th nowrap>
      <b>Reports To</b>
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
          <% int status = thisUser.getEnabled() ? 1 : 0; %>
          <dhv:permission name="admin-users-edit" none="true"><% status = -1; %></dhv:permission>
          <%-- Use the unique id for opening the menu, and toggling the graphics --%>
          <a href="javascript:displayMenu('menuUser', '<%= thisUser.getId() %>','<%= status %>');" onMouseOver="over(0, <%= count %>)" onmouseout="out(0, <%= count %>)"><img src="images/select.gif" name="select<%= count %>" align="absmiddle" border="0"></a>
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

