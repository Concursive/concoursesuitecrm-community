<%-- 
  - Copyright Notice: (C) 2000-2004 Dark Horse Ventures, All Rights Reserved.
  - License: This source code cannot be modified, distributed or used without
  -          written permission from Dark Horse Ventures. This notice must
  -          remain in place.
  - Version: $Id$
  - Description: 
  --%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.Iterator,org.aspcfs.modules.admin.base.PermissionCategory, org.aspcfs.modules.admin.base.CategoryEditor" %>
<jsp:useBean id="editorList" class="org.aspcfs.modules.admin.base.CategoryEditorList" scope="request"/>
<jsp:useBean id="PermissionCategory" class="org.aspcfs.modules.admin.base.PermissionCategory" scope="request"/>
<%@ include file="../initPage.jsp" %>
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="Admin.do">Admin</a> >
<a href="Admin.do?command=Config">Configure Modules</a> >
<a href="Admin.do?command=ConfigDetails&moduleId=<%= PermissionCategory.getId() %>"><%= toHtml(PermissionCategory.getCategory()) %></a> >
Categories
</td>
</tr>
</table>
<%-- End Trails --%>
<table cellpadding="4" cellspacing="0" width="100%" class="pagedList">
  <tr>
    <th width="35" align="center">
      <strong>Action</strong>
    </th>
    <th width="100%">
      <strong>Description</strong>
    </th>
  </tr>
<%
  int rowid = 0;
  Iterator i = editorList.iterator();
  while (i.hasNext()) {
    rowid = (rowid != 1 ? 1 : 2);
    CategoryEditor thisEditor = (CategoryEditor) i.next();
%>
    <tr class="row<%= rowid %>">
      <dhv:permission name="admin-sysconfig-categories-edit"><td align="center"><a href="AdminCategories.do?command=ViewActive&moduleId=<%= thisEditor.getModuleId() %>&constantId=<%= thisEditor.getConstantId() %>">Edit</a></td></dhv:permission>
      <td valign="center" width="100%"><%= toHtml(thisEditor.getDescription()) %></td>
    </tr>
<%
     }
%>
<dhv:evaluate if="<%= editorList.size() == 0 %>">
  <tr>
    <td valign="center" colspan="4">No category editors to configure.</td>
  </tr>
</dhv:evaluate>
</table>
