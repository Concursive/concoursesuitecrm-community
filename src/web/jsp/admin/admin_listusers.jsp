<%@ page import="java.util.*,com.darkhorseventures.cfsbase.*" %>
<jsp:useBean id="UserList" class="com.darkhorseventures.cfsbase.UserList" scope="request"/>
<jsp:useBean id="UserListInfo" class="com.darkhorseventures.webutils.PagedListInfo" scope="session"/>
<%@ include file="initPage.jsp" %>
<script language="JavaScript" TYPE="text/javascript" SRC="/javascript/confirmDelete.js"></script>
<form name="listView" method="post" action="/Users.do?command=ListUsers">
<a href="/Users.do?command=InsertUserForm">Add New User</a>
<center><%= UserListInfo.getAlphabeticalPageLinks() %></center>
<table width="100%" border="0">
  <tr>
    <td align="left">
      <select size="1" name="listView" onChange="javascript:document.forms[0].submit();">
        <option <%= UserListInfo.getOptionValue("enabled") %>>Enabled Users</option>
        <option <%= UserListInfo.getOptionValue("disabled") %>>Disabled Users</option>
      </select>
      <%= showAttribute(request, "actionError") %>
    </td>
  </tr>
</table>
<%
  Iterator i = UserList.iterator();
  
  if (i.hasNext()) {
    int rowid = 0;
%>
<table cellpadding="4" cellspacing="0" border="1" width="100%" class="pagedlist" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr>
    <td valign=center align=left bgcolor="#DEE0FA">
      <strong>Action</strong>
    </td>
    <td bgcolor="#DEE0FA"><b><font class="column">
      <a href="/Users.do?command=ListUsers&column=namelast">
      Name</a></font></b>
      <%= UserListInfo.getSortIcon("namelast") %>
    </td>
    <td bgcolor="#DEE0FA"><b><font class="column">
      <a href="/Users.do?command=ListUsers&column=username">
      Username</a></font></b>
      <%= UserListInfo.getSortIcon("username") %>
    </td>
    <td bgcolor="#DEE0FA"><b><font class="column">
      <a href="/Users.do?command=ListUsers&column=role">
      Role</a></font></b>
      <%= UserListInfo.getSortIcon("role") %>
    </td>
    <td bgcolor="#DEE0FA"><b><font class="column">
      <a href="/Users.do?command=ListUsers&column=mgr_namelast">
      Reports To</a></font></b>
      <%= UserListInfo.getSortIcon("mgr_namelast") %>
    </td>
  </tr>
<%    
    while (i.hasNext()) {
      if (rowid != 1) {
        rowid = 1;
      } else {
        rowid = 2;
      }
      User thisUser = (User)i.next();
      Contact thisContact = (Contact)thisUser.getContact();
%>      
      <tr>
        <td width=8 valign=center nowrap class="row<%= rowid %>">
          <a href="/Users.do?command=ModifyUser&id=<%= thisUser.getId() %>">Edit</a>|<a href="javascript:confirmDelete('/Users.do?command=DeleteUser&id=<%= thisUser.getId() %>');">Del</a>
        </td>
        <td class="row<%= rowid %>"><font class="columntext1">
          <a href="/Users.do?command=UserDetails&id=<%= thisUser.getId() %>"><%= toHtml(thisContact.getNameLast()) %>, <%= toHtml(thisContact.getNameFirst()) %></a></font>
        </td>
        <td class="row<%= rowid %>">
          <%= toHtml(thisUser.getUsername()) %>
        </td>
        <td class="row<%= rowid %>">
          <%= toHtml(thisUser.getRole()) %>
        </td>
        <td class="row<%= rowid %>">
          <%= toHtml(thisUser.getManager()) %>
        </td>
      </tr>
<%      
    }
%>
</table><br>
[<%= UserListInfo.getPreviousPageLink("<font class='underline'>Previous</font>", "Previous") %> <%= UserListInfo.getNextPageLink("<font class='underline'>Next</font>", "Next") %>] <%= UserListInfo.getNumericalPageLinks() %>
<%
  } else {
%>  
No users found.
<%  
  }
%>
</form>
