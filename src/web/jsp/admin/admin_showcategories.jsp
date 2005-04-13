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
<%@ page import="java.util.Iterator,org.aspcfs.modules.admin.base.PermissionCategory, org.aspcfs.modules.admin.base.CategoryEditor" %>
<jsp:useBean id="editorList" class="org.aspcfs.modules.admin.base.CategoryEditorList" scope="request"/>
<jsp:useBean id="PermissionCategory" class="org.aspcfs.modules.admin.base.PermissionCategory" scope="request"/>
<%@ include file="../initPage.jsp" %>
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="Admin.do"><dhv:label name="trails.admin">Admin</dhv:label></a> >
<a href="Admin.do?command=Config"><dhv:label name="trails.configureModules">Configure Modules</dhv:label></a> >
<a href="Admin.do?command=ConfigDetails&moduleId=<%= PermissionCategory.getId() %>"><%= toHtml(PermissionCategory.getCategory()) %></a> >
<dhv:label name="product.Categories">Categories</dhv:label>
</td>
</tr>
</table>
<%-- End Trails --%>
<table cellpadding="4" cellspacing="0" width="100%" class="pagedList">
  <tr>
    <th width="35" align="center">
      &nbsp;
    </th>
    <th width="100%">
      <strong><dhv:label name="accounts.accountasset_include.Description">Description</dhv:label></strong>
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
      <dhv:permission name="admin-sysconfig-categories-edit"><td align="center"><a href="AdminCategories.do?command=ViewActive&moduleId=<%= thisEditor.getModuleId() %>&constantId=<%= thisEditor.getConstantId() %>"><dhv:label name="accounts.accounts_contacts_oppcomponent_list.Edit">Edit</dhv:label></a></td></dhv:permission>
      <td valign="center" width="100%"><%= toHtml(thisEditor.getDescription()) %></td>
    </tr>
<%
     }
%>
<dhv:evaluate if="<%= editorList.size() == 0 %>">
  <tr>
    <td valign="center" colspan="4"><dhv:label name="admin.noCategoryEditorsToConfigure">No category editors to configure.</dhv:label></td>
  </tr>
</dhv:evaluate>
</table>
