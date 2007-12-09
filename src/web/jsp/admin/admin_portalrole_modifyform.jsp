<%-- 
  - Copyright(c) 2004 Concursive Corporation (http://www.concursive.com/) All
  - rights reserved. This material cannot be distributed without written
  - permission from Concursive Corporation. Permission to use, copy, and modify
  - this material for internal use is hereby granted, provided that the above
  - copyright notice and this permission notice appear in all copies. CONCURSIVE
  - CORPORATION MAKES NO REPRESENTATIONS AND EXTENDS NO WARRANTIES, EXPRESS OR
  - IMPLIED, WITH RESPECT TO THE SOFTWARE, INCLUDING, BUT NOT LIMITED TO, THE
  - IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR ANY PARTICULAR
  - PURPOSE, AND THE WARRANTY AGAINST INFRINGEMENT OF PATENTS OR OTHER
  - INTELLECTUAL PROPERTY RIGHTS. THE SOFTWARE IS PROVIDED "AS IS", AND IN NO
  - EVENT SHALL CONCURSIVE CORPORATION OR ANY OF ITS AFFILIATES BE LIABLE FOR
  - ANY DAMAGES, INCLUDING ANY LOST PROFITS OR OTHER INCIDENTAL OR CONSEQUENTIAL
  - DAMAGES RELATING TO THE SOFTWARE.
  - 
  - Version: $Id: admin_roledetails.jsp 11310 2005-04-13 20:05:00 +0000 (Wed, 13 Apr 2005) mrajkowski $
  - Description: 
  --%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="org.aspcfs.modules.admin.base.User,java.util.*,org.aspcfs.modules.admin.base.Permission" %>
<%@ include file="../initPage.jsp" %>
<jsp:useBean id="role" class="org.aspcfs.modules.admin.base.Role" scope="request"/>
<jsp:useBean id="permissionList" class="org.aspcfs.modules.admin.base.PermissionList" scope="request"/>
<form name="inputForm" action='PortalRoleEditor.do?command=Update&auto-populate=true' method='post'>
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="Admin.do"><dhv:label name="trails.admin">Admin</dhv:label></a> >
<a href="PortalRoleEditor.do"><dhv:label name="admin.viewPortalRoles">View  Portal Roles</dhv:label></a> >
<dhv:label name="admin.updatePortalRole">Update Portal Role</dhv:label>
</td>
</tr>
</table>
<%-- End Trails --%>
<script language="JavaScript">
	function setField(formField,thisValue,thisForm) {
		var frm = document.forms[thisForm];
		var len = document.forms[thisForm].elements.length;
		var i=0;
		for( i=0 ; i<len ; i++) {
			if (frm.elements[i].name.indexOf(formField)!=-1) {
				if(thisValue){
					frm.elements[i].value = "1";
				}	else {
					frm.elements[i].value = "0";
				}
			}
		}
	}
</script>
<input type="hidden" name="id" value="<%= role.getId() %>">
<input type="hidden" name="modified" value="<%= role.getModified() %>">
<dhv:permission name="admin-roles-edit">
  <input type="submit" value="<dhv:label name="global.button.update">Update</dhv:label>" name="Save">
  <input type="submit" value="<dhv:label name="global.button.cancel">Cancel</dhv:label>" onClick="javascript:this.form.action='PortalRoleEditor.do?command=List'">
</dhv:permission>
<br>
<dhv:formMessage />
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="2">
	    <strong><dhv:label name="admin.updatePortalRole">Update Portal Role</dhv:label></strong>
	  </th>
  </tr>
  <tr>
    <td class="formLabel"><dhv:label name="admin.portalRoleName">Portal Role Name</dhv:label></td>
    <td><input type="text" name="role" maxlength="80" size="30" value="<%= toHtmlValue(role.getRole()) %>"><font color="red">*</font> <%= showAttribute(request, "roleError") %></td>
  </tr>
  <tr>
    <td class="formLabel"><dhv:label name="accounts.accountasset_include.Description">Description</dhv:label></td>
    <td nowrap><input type="text" name="description" maxlength="255" size="60" value="<%= toHtmlValue(role.getDescription()) %>"><font color="red">*</font> <%= showAttribute(request, "descriptionError") %></td>
  </tr>
  <tr>
    <td class="formLabel">
      <dhv:label name="accounts.accounts_contacts_portal_include.Enabled">Enabled</dhv:label>
    </td>
    <td>
    <input type="checkbox" name="chk1" value="on" onclick="javascript:setField('enabled', document.inputForm.chk1.checked, 'inputForm');" <%= role.getEnabled() ? "checked":""%>>
    <input type="hidden" name="enabled" value="<%= role.getEnabled() ? "1":"0"%>"></input>
   </td>
  </tr>
</table>
&nbsp;
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="pagedList">
  <tr>
    <th colspan="5">
	    <strong><dhv:label name="documents.permissions.long_html">Permissions</dhv:label></strong>
	  </th>
  </tr>
<%
  Iterator i = permissionList.iterator();
  int idCount = 0;
  while (i.hasNext()) {
    ++idCount;
    Permission thisPermission = (Permission)i.next();
    if (permissionList.isNewCategory(thisPermission.getCategoryName())) {
%>
  <tr class="row1">
    <td>
      <%= toHtml(thisPermission.getCategoryName()) %>
    </td>
    <td width="40" align="center"><dhv:label name="admin.accessView" param="break=<br />">Access/<br />View</dhv:label></td>
    <td width="40" align="center"><dhv:label name="button.add">Add</dhv:label></td>
    <td width="40" align="center"><dhv:label name="button.edit">Edit</dhv:label></td>
    <td width="40" align="center"><dhv:label name="button.delete">Delete</dhv:label></td>
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
        <input type="checkbox" value="ON" name="permission<%= idCount %>view" <%= (role.getPermissionList().hasPermission(thisPermission.getName(), "view"))?"checked":"" %>>
<%
      } else {
%>
        --
<%
      }
%>
    </td>
    <td align="center">
<%
      if (thisPermission.getAdd()) {
%>
        <input type="checkbox" value="ON" name="permission<%= idCount %>add" <%= (role.getPermissionList().hasPermission(thisPermission.getName(), "add"))?"checked":"" %>>
<%
      } else {
%>
        --
<%
      }
%>
    </td>
    <td align="center">
<%
      if (thisPermission.getEdit()) {
%>
        <input type="checkbox" value="ON" name="permission<%= idCount %>edit" <%= (role.getPermissionList().hasPermission(thisPermission.getName(), "edit"))?"checked":"" %>>
<%
      } else {
%>
        --
<%
      }
%>
    </td>
    <td align="center">
<%
      if (thisPermission.getDelete()) {
%>
        <input type="checkbox" value="ON" name="permission<%= idCount %>delete" <%= (role.getPermissionList().hasPermission(thisPermission.getName(), "delete"))?"checked":"" %>>
<%
      } else {
%>
        --
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
  <br />
  <input type="submit" value="<dhv:label name="global.button.update">Update</dhv:label>" name="Save">
  <input type="submit" value="<dhv:label name="global.button.cancel">Cancel</dhv:label>" onClick="javascript:this.form.action='PortalRoleEditor.do?command=List'">
</dhv:permission>
</form>
