<%-- 
  - Copyright Notice: (C) 2000-2004 Dark Horse Ventures, All Rights Reserved.
  - License: This source code cannot be modified, distributed or used without
  -          written permission from Dark Horse Ventures. This notice must
  -          remain in place.
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
<body onLoad="document.forms[0].name.focus();">
<form name="details" action="AdminFieldsFolder.do?command=UpdateFolder&modId=<%= ModId %>&catId=<%= Category.getId() %>&auto-populate=true" method="post">
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="Admin.do">Admin</a> >
<a href="Admin.do?command=Config">Configure Modules</a> >
<a href="Admin.do?command=ConfigDetails&moduleId=<%=ModId%>"><%= PermissionCategory.getCategory() %></a> >
<a href="AdminFieldsFolder.do?command=ListFolders&modId=<%= ModId %>">Custom Folders</a> > 
Existing Folder
</td>
</tr>
</table>
<%-- End Trails --%>
<dhv:formMessage showSpace="false" />
<strong>Module:</strong> <%=PermissionCategory.getCategory()%><br />
<br />
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="2">
      <strong>Update an Existing Folder</strong>
    </th>
  </tr>
  <tr class="containerBody">
    <td class="formLabel">
      Folder Name
    </td>
    <td>
      <input type="text" name="name" maxlength="200" value="<%= toHtmlValue(Category.getName()) %>"><font color="red">*</font>
      <%= showAttribute(request, "nameError") %>
    </td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel">
      Records
    </td>
    <td>
      <input type="checkbox" value="ON" name="allowMultipleRecords" <%= Category.getAllowMultipleRecords()?"checked":"" %>>folder can have multiple records
    </td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel">
      Read Only
    </td>
    <td>
      <input type="checkbox" value="ON" name="readOnly" <%= Category.getReadOnly()?"checked":"" %>>folder is read only by all users
    </td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel">
      Enabled
    </td>
    <td>
      <input type="checkbox" value="ON" name="enabled" <%= Category.getEnabled()?"checked":"" %>>folder is visible by users
    </td>
  </tr>
</table>
&nbsp;<br>
<input type="hidden" name="moduleId" value="<%= ConstantId %>">
<input type="hidden" name="categoryId" value="<%= Category.getId() %>">
<input type="submit" value="Update">
<input type="submit" value="Cancel" onClick="javascript:this.form.action='AdminFieldsFolder.do?command=ListFolders&modId=<%= ModId %>'">
</form>
</body>
