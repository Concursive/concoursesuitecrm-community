<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,org.aspcfs.modules.admin.base.*" %>
<jsp:useBean id="RoleList" class="org.aspcfs.modules.admin.base.RoleList" scope="request"/>
<jsp:useBean id="RoleListInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<%@ include file="../initPage.jsp" %>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popURL.js"></SCRIPT>
<a href="Admin.do">Setup</a> >
View Roles<br>
<hr color="#BFBFBB" noshade>
<dhv:permission name="admin-roles-add"><a href="Roles.do?command=InsertRoleForm">Add New Role</a></dhv:permission>
<center><%= RoleListInfo.getAlphabeticalPageLinks() %></center>
<dhv:pagedListStatus title="<%= showError(request, "actionError") %>" object="RoleListInfo"/>
<table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr class="title">
    <dhv:permission name="admin-roles-edit,admin-roles-delete">
    <td>
      <strong>Action</strong>
    </td>
    </dhv:permission>
    <td width="35%" nowrap>
      <b><a href="Roles.do?command=ListRoles&column=role">Role</a></b>
      <%= RoleListInfo.getSortIcon("role") %>
    </td>
    <td width="65%" nowrap>
      <b><a href="Roles.do?command=ListRoles&column=description">Description</a></b>
      <%= RoleListInfo.getSortIcon("description") %>
    </td>
    <td nowrap>
      <b># of users</b>
    </td>
  </tr>
<%
  Iterator i = RoleList.iterator();
  if (i.hasNext()) {
    int rowid = 0;
    while (i.hasNext()) {
      rowid = (rowid != 1?1:2);
      Role thisRole = (Role)i.next();
%>      
      <tr>
        <dhv:permission name="admin-roles-edit,admin-roles-delete">
        <td nowrap class="row<%= rowid %>">
          <dhv:permission name="admin-roles-edit"><a href="Roles.do?command=RoleDetails&id=<%= thisRole.getId() %>">Edit</a></dhv:permission><dhv:permission name="admin-roles-edit,admin-roles-delete" all="true">|</dhv:permission><dhv:permission name="admin-roles-delete"><a href="javascript:popURLReturn('Roles.do?command=ConfirmDelete&id=<%=thisRole.getId()%>&popup=true','Roles.do?command=ListRoles', 'Delete_role','320','200','yes','no');">Del</a></dhv:permission>
        </td>
        </dhv:permission>
        <td class="row<%= rowid %>">
          <a href="Roles.do?command=RoleDetails&id=<%= thisRole.getId() %>"><%= toHtml(thisRole.getRole()) %></a>
        </td>
        <td class="row<%= rowid %>">
          <%= toHtml(thisRole.getDescription()) %>
        </td>
        <td align="center" class="row<%= rowid %>">
          <%= thisRole.getUserCount() %>
        </td>
      </tr>
<%      
    }
  } else {
%>  
  <tr>
    <td class="containerBody" colspan="5">
      No roles found.
    </td>
  </tr>
<%  
  }
%>
</table>
<br>
<dhv:pagedListControl object="RoleListInfo" tdClass="row1"/>

