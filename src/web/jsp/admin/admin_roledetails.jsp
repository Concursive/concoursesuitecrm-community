<%@ page import="com.darkhorseventures.cfsbase.User,java.util.*,com.darkhorseventures.cfsbase.Permission" %>
<%@ include file="initPage.jsp" %>
<jsp:useBean id="Role" class="com.darkhorseventures.cfsbase.Role" scope="request"/>
<jsp:useBean id="PermissionList" class="com.darkhorseventures.cfsbase.PermissionList" scope="request"/>
<a href="/Roles.do?command=ListRoles">Back to list</a><p>
<form action='Roles.do?command=UpdateRole&auto-populate=true' method='post'>
<input type="hidden" name="id" value="<%= Role.getId() %>">
<input type="hidden" name="modified" value="<%= Role.getModifiedString() %>">
<input type="submit" value="Update" name="Save">
<input type="submit" value="Cancel" onClick="javascript:this.form.action='/Roles.do?command=ListRoles'">
<input type="reset" value="Reset">
<br>
&nbsp;
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
<br>
<input type="submit" value="Update" name="Save">
<input type="submit" value="Cancel" onClick="javascript:this.form.action='/Roles.do?command=ListRoles'">
<input type="reset" value="Reset">
</form>
