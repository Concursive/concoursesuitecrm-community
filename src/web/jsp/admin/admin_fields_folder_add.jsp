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
<%@ page import="java.util.*,java.text.*,org.aspcfs.modules.admin.base.*" %>
<jsp:useBean id="Category" class="org.aspcfs.modules.base.CustomFieldCategory" scope="request"/>
<jsp:useBean id="ModId" class="java.lang.String" scope="request"/>
<jsp:useBean id="ConstantId" class="java.lang.String" scope="request"/>
<jsp:useBean id="PermissionCategory" class="org.aspcfs.modules.admin.base.PermissionCategory" scope="request"/>
<%@ include file="../initPage.jsp" %>
<body onLoad="document.details.name.focus();">
<form name="details" action="AdminFieldsFolder.do?command=InsertFolder&modId=<%= ModId %>&auto-populate=true" method="post">
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="Admin.do"><dhv:label name="trails.admin">Admin</dhv:label></a> >
<a href="Admin.do?command=Config"><dhv:label name="trails.configureModules">Configure Modules</dhv:label></a> >
<a href="Admin.do?command=ConfigDetails&moduleId=<%= ModId %>"><%=PermissionCategory.getCategory()%></a> >
<a href="AdminFieldsFolder.do?command=ListFolders&modId=<%= ModId %>"><dhv:label name="admin.customFolders">Custom Folders</dhv:label></a> >
<dhv:label name="documents.documents.newFolder">New Folder</dhv:label>
</td>
</tr>
</table>
<%-- End Trails --%>
<dhv:formMessage showSpace="false" />
<strong><dhv:label name="admin.module.colon">Module:</dhv:label></strong> <%= PermissionCategory.getCategory() %><br />
<br />
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="2">
      <strong><dhv:label name="admin.addFolder">Add a Folder</dhv:label></strong>
    </th>
  </tr>
  <tr class="containerBody">
    <td class="formLabel">
      <dhv:label name="admin.folderName">Folder Name</dhv:label>
    </td>
    <td>
      <input type="text" name="name" maxlength="200" value="<%= toHtmlValue(Category.getName()) %>"><font color="red">*</font>
      <%= showAttribute(request, "nameError") %>
    </td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel">
      <dhv:label name="admin.records">Records</dhv:label>
    </td>
    <td>
      <input type="checkbox" value="ON" name="allowMultipleRecords" <%= Category.getAllowMultipleRecords()?"checked":"" %>><dhv:label name="accounts.accounts_fields_list.FolderHaveMultipleRecords">folder can have multiple records</dhv:label>
    </td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel">
      <dhv:label name="admin.readOnly">Read Only</dhv:label>
    </td>
    <td>
      <input type="checkbox" value="ON" name="readOnly" <%= Category.getReadOnly()?"checked":"" %>><dhv:label name="admin.folderReadOnlyAllUsers.text">folder is read only by all users</dhv:label>
    </td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel">
      <dhv:label name="product.enabled">Enabled</dhv:label>
    </td>
    <td>
      <input type="checkbox" value="ON" name="enabled" <%= Category.getEnabled()?"checked":"" %>><dhv:label name="admin.folderVisibleUsers.text">folder is visible by users</dhv:label>
    </td>
  </tr>
</table>
&nbsp;<br>
<input type="hidden" name="moduleId" value="<%= ConstantId %>">
<input type="submit" value="<dhv:label name="global.button.save">Save</dhv:label>">
<input type="submit" value="<dhv:label name="global.button.cancel">Cancel</dhv:label>" onClick="javascript:this.form.action='AdminFieldsFolder.do?command=ListFolders&modId=<%= ModId %>'">
</form>
</body>
