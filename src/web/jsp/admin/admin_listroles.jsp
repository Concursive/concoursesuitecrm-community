<%@ page import="java.util.*,com.darkhorseventures.cfsbase.*" %>
<jsp:useBean id="RoleList" class="com.darkhorseventures.cfsbase.RoleList" scope="request"/>
<jsp:useBean id="RoleListInfo" class="com.darkhorseventures.webutils.PagedListInfo" scope="session"/>
<%@ include file="initPage.jsp" %>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="/javascript/confirmDelete.js"></SCRIPT>
<a href="/Roles.do?command=InsertRoleForm">Add New Role</a>
<center><%= RoleListInfo.getAlphabeticalPageLinks() %></center>
<%= showAttribute(request, "actionError") %>
<%
  Iterator i = RoleList.iterator();
  if (i.hasNext()) {
    int rowid = 0;
%>
<table cellpadding="4" cellspacing="0" border="1" width="100%" class="pagedlist" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr>
    <td valign=center align=left bgcolor="#DEE0FA">
      <strong>Action</strong>
    </td>
    <td bgcolor="#DEE0FA" width="150">
      <b><font class="column">
      <a href="/Roles.do?command=ListRoles&column=role">
      Role</a></font></b>
    </td>
    <td bgcolor="#DEE0FA">
      <b><font class="column">
      <a href="/Roles.do?command=ListRoles&column=description">
      Description</a></font></b>
    </td>
    <td bgcolor="#DEE0FA">
      <b><font class="column">
      # of users
      </font></b>
  </tr>
<%    
    while (i.hasNext()) {
      if (rowid != 1) {
        rowid = 1;
      } else {
        rowid = 2;
      }
      Role thisRole = (Role)i.next();
%>      
      <tr>
        <td width=8 valign=center nowrap class="row<%= rowid %>">
          <a href="/Roles.do?command=RoleDetails&id=<%= thisRole.getId() %>">Edit</a>|<a href="javascript:confirmDelete('/Roles.do?command=DeleteRole&id=<%= thisRole.getId() %>');">Del</a>
        </td>
        <td class="row<%= rowid %>"  width="150"><font class="columntext1">
          <a href="/Roles.do?command=RoleDetails&id=<%= thisRole.getId() %>"><%= toHtml(thisRole.getRole()) %></a></font>
        </td>
        <td class="row<%= rowid %>">
          <%= toHtml(thisRole.getDescription()) %>
        </td>
        <td class="row<%= rowid %>">
          <%= thisRole.getUserList().size() %>
        </td>
      </tr>
<%      
    }
%>
</table>
&nbsp;<br>
[<%= RoleListInfo.getPreviousPageLink("<font class='underline'>Previous</font>", "Previous") %> <%= RoleListInfo.getNextPageLink("<font class='underline'>Next</font>", "Next") %>] <%= RoleListInfo.getNumericalPageLinks() %>
<%
  } else {
%>  
No roles found.
<%  
  }
%>
