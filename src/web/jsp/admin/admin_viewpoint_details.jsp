<%-- 
  - Copyright(c) 2004 Dark Horse Ventures LLC (http://www.centriccrm.com/) All
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
  - Version: $Id$
  - Description: 
  --%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ page import="java.text.DateFormat,org.aspcfs.modules.admin.base.User,java.util.*,org.aspcfs.modules.admin.base.Permission" %>
<%@ include file="../initPage.jsp" %>
<jsp:useBean id="Viewpoint" class="org.aspcfs.modules.admin.base.Viewpoint" scope="request"/>
<jsp:useBean id="PermissionList" class="org.aspcfs.modules.admin.base.PermissionList" scope="request"/>
<jsp:useBean id="UserRecord" class="org.aspcfs.modules.admin.base.User" scope="request"/>
<form action="Viewpoints.do?command=UpdateViewpoint&auto-populate=true" method="post">
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="Admin.do"><dhv:label name="trails.admin">Admin</dhv:label></a> >
<a href="Users.do?command=ListUsers"><dhv:label name="admin.viewUsers">View Users</dhv:label></a> >
<a href="Users.do?command=UserDetails&id=<%= request.getParameter("userId") %>"><dhv:label name="admin.userDetails">User Details</dhv:label></a> >
<a href="Viewpoints.do?command=ListViewpoints&userId=<%= request.getParameter("userId") %>"><dhv:label name="users.viewpoints.long_html">Viewpoints</dhv:label></a> >
<dhv:label name="admin.updateViewpoint">Update Viewpoint</dhv:label>
</td>
</tr>
</table>
<%-- End Trails --%>
<dhv:container name="users" selected="viewpoints" object="UserRecord" param='<%= "id=" + UserRecord.getId() %>'>
  <dhv:permission name="admin-roles-edit">
    <input type="submit" value="<dhv:label name="global.button.update">Update</dhv:label>" name="Save">
    <input type="submit" value="<dhv:label name="global.button.cancel">Cancel</dhv:label>" onClick="javascript:this.form.action='Viewpoints.do?command=ListViewpoints'">
    <br />
  </dhv:permission>
  <dhv:formMessage />
  <input type="hidden" name="userId" value="<%= UserRecord.getId() %>">
  <input type="hidden" name="vpUserId" value="<%= Viewpoint.getVpUserId() %>">
  <input type="hidden" name="id" value="<%= Viewpoint.getId() %>">
  <input type="hidden" name="modified" value="<%= Viewpoint.getModified() %>">
  <table cellpadding="4" cellspacing="0" border="0" width="100%" class="pagedList">
    <tr>
      <th colspan="2">
        <strong><dhv:label name="admin.updateViewpoint">Update Viewpoint</dhv:label></strong>
      </th>
    </tr>
    <tr class="containerBody">
      <td class="formLabel"><dhv:label name="admin.viewpoint">Viewpoint</dhv:label></td>
      <td>
        <%= toHtml(Viewpoint.getVpUser().getContact().getNameLastFirst()) %>
        <%= showAttribute(request, "ViewpointError") %>
      </td>
    </tr>
    <tr class="containerBody">
      <td class="formLabel"><dhv:label name="accounts.accounts_calls_list.Entered">Entered</dhv:label></td>
      <td><zeroio:tz timestamp="<%= Viewpoint.getEntered() %>" /></td>
    </tr>
    <tr class="containerBody">
      <td class="formLabel"><dhv:label name="product.enabled">Enabled</dhv:label></td>
      <td><input type="checkbox" name="enabled" value="on" <%= Viewpoint.getEnabled() ? " checked" : ""%>></td>
    </tr>
  </table>
  <br />
  <table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
    <tr>
      <th colspan="5">
        <strong><dhv:label name="documents.permissions.long_html">Permissions</dhv:label></strong>
      </th>
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
      <td width="40" align="center"><dhv:label name="admin.access">Access</dhv:label></td>
    </tr>
  <%
     }
  %>
    <tr class="containerBody">
      <td align="center">
        <input type="checkbox" value="ON" name="permission<%= idCount %>view" <%= (Viewpoint.getPermissionList().hasPermission(thisPermission.getName(), "view"))?"checked":"" %>>
      </td>
      <td width="100%">
        <input type="hidden" name="permission<%= idCount %>id" value="<%= thisPermission.getId() %>">
        <%= toHtml(thisPermission.getDescription()) %>
      </td>
    </tr>
  <%
    }
  %>
  </table>
  <dhv:permission name="admin-roles-edit">
    <br />
    <input type="submit" value="<dhv:label name="global.button.update">Update</dhv:label>" name="Save">
    <input type="submit" value="<dhv:label name="global.button.cancel">Cancel</dhv:label>" onClick="javascript:this.form.action='Viewpoints.do?command=ListViewpoints'">
  </dhv:permission>
</dhv:container>
</form>
