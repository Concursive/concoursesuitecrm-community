<%@ taglib uri="WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,com.darkhorseventures.cfsbase.*" %>
<jsp:useBean id="RoleList" class="com.darkhorseventures.cfsbase.RoleList" scope="request"/>
<jsp:useBean id="RoleListInfo" class="com.darkhorseventures.webutils.PagedListInfo" scope="session"/>
<%@ include file="initPage.jsp" %>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="/javascript/confirmDelete.js"></SCRIPT>
<dhv:permission name="admin-roles-add"><a href="/Roles.do?command=InsertRoleForm">Add New Role</a></dhv:permission>
<center><%= RoleListInfo.getAlphabeticalPageLinks() %></center>
<dhv:pagedListStatus title="<%= showError(request, "actionError") %>" object="RoleListInfo"/>
<table cellpadding="4" cellspacing="0" border="1" width="100%" class="pagedlist" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr>
    <dhv:permission name="admin-roles-edit,admin-roles-delete">
    <td valign=center align=left bgcolor="#DEE0FA">
      <strong>Action</strong>
    </td>
    </dhv:permission>
    <td bgcolor="#DEE0FA" width="150">
      <b><font class="column">
      <a href="/Roles.do?command=ListRoles&column=role">
      Role</a></font></b>
      <%= RoleListInfo.getSortIcon("role") %>
    </td>
    <td bgcolor="#DEE0FA">
      <b><font class="column">
      <a href="/Roles.do?command=ListRoles&column=description">
      Description</a></font></b>
      <%= RoleListInfo.getSortIcon("description") %>
    </td>
    <td bgcolor="#DEE0FA">
      <b><font class="column">
      # of users
      </font></b>
  </tr>
<%
  Iterator i = RoleList.iterator();
  if (i.hasNext()) {
    int rowid = 0;
    while (i.hasNext()) {
      if (rowid != 1) {
        rowid = 1;
      } else {
        rowid = 2;
      }
      Role thisRole = (Role)i.next();
%>      
      <tr>
        <dhv:permission name="admin-roles-edit,admin-roles-delete">
        <td width=8 valign=center nowrap class="row<%= rowid %>">
          <dhv:permission name="admin-roles-edit"><a href="/Roles.do?command=RoleDetails&id=<%= thisRole.getId() %>">Edit</a></dhv:permission><dhv:permission name="admin-roles-edit,admin-roles-delete" all="true">|</dhv:permission><dhv:permission name="admin-roles-delete"><a href="javascript:confirmDelete('/Roles.do?command=DeleteRole&id=<%= thisRole.getId() %>');">Del</a></dhv:permission>
        </td>
        </dhv:permission>
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
  } else {
%>  
  <tr>
    <td class="row2" valign="center" colspan="5">
      No roles found.
    </td>
  </tr>
<%  
  }
%>
</table>
<br>
<dhv:pagedListControl object="RoleListInfo" tdClass="row1"/>

