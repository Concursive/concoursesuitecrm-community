<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="org.aspcfs.modules.admin.base.User,java.util.*,org.aspcfs.modules.admin.base.Permission" %>
<%@ include file="../initPage.jsp" %>
<jsp:useBean id="Viewpoint" class="org.aspcfs.modules.admin.base.Viewpoint" scope="request"/>
<jsp:useBean id="PermissionList" class="org.aspcfs.modules.admin.base.PermissionList" scope="request"/>
<jsp:useBean id="UserRecord" class="org.aspcfs.modules.admin.base.User" scope="request"/>
<form action="Viewpoints.do?command=UpdateViewpoint&auto-populate=true" method="post">
<a href="Admin.do">Setup</a> >
<a href="Users.do?command=ListUsers">View Users</a> >
<a href="Users.do?command=UserDetails&id=<%= request.getParameter("userId") %>">User Details</a> >
<a href="Viewpoints.do?command=ListViewpoints&userId=<%= request.getParameter("userId") %>">Viewpoints</a> >
Update Viewpoint <br>
<hr color="#BFBFBB" noshade>
<table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr class="containerHeader">
    <td>
      <strong><%= toHtml(UserRecord.getUsername()) %> (<%= toHtml(UserRecord.getContact().getNameLastFirst()) %>)</strong>
    </td>
  </tr>
  <tr class="containerMenu">
    <td>
      <% String param1 = "id=" + UserRecord.getId(); %>      
      <dhv:container name="users" selected="viewpoints" param="<%= param1 %>" />
    </td>
  </tr>
  <tr>
    <td class="containerBack">
<dhv:permission name="admin-roles-edit">
  <input type="submit" value="Update" name="Save">
  <input type="submit" value="Cancel" onClick="javascript:this.form.action='Viewpoints.do?command=ListViewpoints'">
  <input type="reset" value="Reset">
  <br>
</dhv:permission>
<%= showError(request, "actionError") %>
<input type="hidden" name="userId" value="<%= UserRecord.getId() %>">
<input type="hidden" name="vpUserId" value="<%= Viewpoint.getVpUserId() %>">
<input type="hidden" name="id" value="<%= Viewpoint.getId() %>">
<input type="hidden" name="modified" value="<%= Viewpoint.getModified() %>">
<table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr class="title">
    <td colspan="2">
	    <strong>Update Viewpoint</strong>
	  </td>
  </tr>
  <tr class="containerBody">
    <td width="150" align="right" class="formLabel">Viewpoint</td>
    <td align="left"> <%= Viewpoint.getVpUser().getContact().getNameLastFirst() %> <%= showAttribute(request, "ViewpointError") %></td>
  </tr>
  <tr class="containerBody">
    <td width="150" align="right" class="formLabel">Entered</td>
    <td align="left"><%= toDateTimeString(Viewpoint.getEntered()) %></td>
  </tr>
  <tr class="containerBody">
    <td width="150" align="right" class="formLabel">Enabled</td>
    <td align="left"><input type="checkbox" name="enabled" <%= Viewpoint.getEnabled() ? " checked" : ""%>></td>
  </tr>
</table>
&nbsp;
<table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr class="title">
    <td colspan="5">
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
    <td width="40" align="center">Access</td>
  </tr>
<%
   }    
%>    
  <tr class="containerBody">
    <td>
      <input type="hidden" name="permission<%= idCount %>id" value="<%= thisPermission.getId() %>">
      &nbsp; &nbsp;<%= toHtml(thisPermission.getDescription()) %>
    </td>
    <td align="center">
        <input type="checkbox" value="ON" name="permission<%= idCount %>view" <%= (Viewpoint.getPermissionList().hasPermission(thisPermission.getName(), "view"))?"checked":"" %>>
    </td>
  </tr>
<%
  }
%>
</table>
<dhv:permission name="admin-roles-edit">
  <br>
  <input type="submit" value="Update" name="Save">
  <input type="submit" value="Cancel" onClick="javascript:this.form.action='Viewpoints.do?command=ListViewpoints'">
  <input type="reset" value="Reset">
</dhv:permission>
</td></tr>
</table>
</form>
