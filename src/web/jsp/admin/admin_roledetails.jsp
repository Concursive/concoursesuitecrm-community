<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="org.aspcfs.modules.admin.base.User,java.util.*,org.aspcfs.modules.Permission" %>
<%@ include file="../initPage.jsp" %>
<jsp:useBean id="Role" class="org.aspcfs.modules.Role" scope="request"/>
<jsp:useBean id="PermissionList" class="org.aspcfs.modules.PermissionList" scope="request"/>
<a href="Admin.do">Setup</a> >
<a href="Roles.do">View Roles</a> >
Update Role
<hr color="#BFBFBB" noshade>
<form action='Roles.do?command=UpdateRole&auto-populate=true' method='post'>
<input type="hidden" name="id" value="<%= Role.getId() %>">
<input type="hidden" name="modified" value="<%= Role.getModified() %>">
<dhv:permission name="admin-roles-edit">
  <input type="submit" value="Update" name="Save">
  <input type="submit" value="Cancel" onClick="javascript:this.form.action='Roles.do?command=ListRoles'">
  <input type="reset" value="Reset">
</dhv:permission>
<br>
<%= showError(request, "actionError") %>
<table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr bgcolor="#DEE0FA">
    <td colspan=2 valign=center align=left>
	    <strong>Update Role</strong>
	  </td>
  </tr>
  <tr>
    <td width="150">Role Name</td>
    <td><input type="text" name="role" maxlength="80" size="30" value="<%= toHtmlValue(Role.getRole()) %>"><font color="red">*</font> <%= showAttribute(request, "roleError") %></td>
  </tr>
  <tr>
    <td width="150">Description</td>
    <td><input type="text" name="description" maxlength="255" size="60" value="<%= toHtmlValue(Role.getDescription()) %>"><font color="red">*</font> <%= showAttribute(request, "descriptionError") %></td>
  </tr>
</table>
&nbsp;
<table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr bgcolor="#DEE0FA">
    <td valign=center align=left colspan=5>
	    <strong>Permissions</strong>
	  </td>
  </tr>
<%
  Iterator i = PermissionList.iterator();
  int idCount = 0;
  while (i.hasNext()) {
    ++idCount;
    Permission thisPermission = (Permission)i.next();
    if (PermissionList.isNewCategory(thisPermission.getCategoryName())) {
%>
  <tr class="row1">
    <td>
      <%= toHtml(thisPermission.getCategoryName()) %>
    </td>
    <td width=40 align="center">Access/<br>View</td>
    <td width=40 align="center">Add</td>
    <td width=40 align="center">Edit</td>
    <td width=40 align="center">Delete</td>
  </tr>
<%
   }    
%>    
  <tr>
    <td>
      <input type="hidden" name="permission<%= idCount %>id" value="<%= thisPermission.getId() %>">
      &nbsp; &nbsp;<%= toHtml(thisPermission.getDescription()) %>
    </td>
    <td align="center">
<%
      if (thisPermission.getView()) {
%>
        <input type="checkbox" value="ON" name="permission<%= idCount %>view" <%= (Role.getPermissionList().hasPermission(thisPermission.getName(), "view"))?"checked":"" %>>
<%
      } else {
%>
        <input type="checkbox" disabled>
<%
      }
%>
    </td>
    <td align="center">
<%
      if (thisPermission.getAdd()) {
%>
        <input type="checkbox" value="ON" name="permission<%= idCount %>add" <%= (Role.getPermissionList().hasPermission(thisPermission.getName(), "add"))?"checked":"" %>>
<%
      } else {
%>
        <input type="checkbox" disabled>
<%
      }
%>
    </td>
    <td align="center">
<%
      if (thisPermission.getEdit()) {
%>
        <input type="checkbox" value="ON" name="permission<%= idCount %>edit" <%= (Role.getPermissionList().hasPermission(thisPermission.getName(), "edit"))?"checked":"" %>>
<%
      } else {
%>
        <input type="checkbox" disabled>
<%
      }
%>
    </td>
    <td align="center">
<%
      if (thisPermission.getDelete()) {
%>
        <input type="checkbox" value="ON" name="permission<%= idCount %>delete" <%= (Role.getPermissionList().hasPermission(thisPermission.getName(), "delete"))?"checked":"" %>>
<%
      } else {
%>
        <input type="checkbox" disabled>
<%
      }
%>
    </td>
  </tr>
<%
  }
%>
</table>
<dhv:permission name="admin-roles-edit">
  <br>
  <input type="submit" value="Update" name="Save">
  <input type="submit" value="Cancel" onClick="javascript:this.form.action='Roles.do?command=ListRoles'">
  <input type="reset" value="Reset">
</dhv:permission>
</form>
