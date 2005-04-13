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
<%@ page import="java.util.*,java.text.DateFormat,java.text.*,org.aspcfs.modules.admin.base.*,org.aspcfs.modules.base.*" %>
<jsp:useBean id="CategoryList" class="org.aspcfs.modules.base.CustomFieldCategoryList" scope="request"/>
<jsp:useBean id="Category" class="org.aspcfs.modules.base.CustomFieldCategory" scope="request"/>
<jsp:useBean id="PermissionCategory" class="org.aspcfs.modules.admin.base.PermissionCategory" scope="request"/>
<%@ include file="../initPage.jsp" %>
<form name="details" action="AdminFieldsFolder.do?command=ListFolders" method="post">
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="Admin.do"><dhv:label name="trails.admin">Admin</dhv:label></a> >
<a href="Admin.do?command=Config"><dhv:label name="trails.configureModules">Configure Modules</dhv:label></a> >
<a href="Admin.do?command=ConfigDetails&moduleId=<%=PermissionCategory.getId()%>"><%= PermissionCategory.getCategory() %></a> >
<dhv:label name="admin.customFolders">Custom Folders</dhv:label>
</td>
</tr>
</table>
<%-- End Trails --%>
  <dhv:permission name="admin-sysconfig-folders-add">
    <a href="AdminFieldsFolder.do?command=AddFolder&modId=<%= PermissionCategory.getId() %>">Add a Folder to this Module</a><br>
    <% if (request.getAttribute("actionError") == null) { %>
    <br />
    <%}%>
  </dhv:permission>
<dhv:formMessage showSpace="false" />
  <table cellpadding="4" cellspacing="0" width="100%" class="pagedList">
    <tr>
      <dhv:permission name="admin-sysconfig-folders-edit">
      <th align="center">
        &nbsp;
      </th>
      </dhv:permission>
      <th width="100%">
        <strong><dhv:label name="admin.customFolders">Custom Folders</dhv:label></strong>
      </th>
      <th align="center">
        <strong><dhv:label name="product.enabled">Enabled</dhv:label></strong>
      </th>
      <th align="center" nowrap>
        <strong><dhv:label name="admin.activeDate">Active Date</dhv:label></strong>
      </th>
    </tr>
<%
  if (CategoryList.size() > 0) {
    int rowId = 0;
    Iterator records = CategoryList.iterator();
    while (records.hasNext()) {
      rowId = (rowId == 1 ? 2 : 1);
      CustomFieldCategory thisCategory = (CustomFieldCategory) records.next();
%>    
    <tr class="row<%= rowId %>">
      <dhv:permission name="admin-sysconfig-folders-edit">
      <td align="center">
        <a href="AdminFieldsFolder.do?command=ModifyFolder&modId=<%= PermissionCategory.getId() %>&catId=<%= thisCategory.getId() %>"><dhv:label name="accounts.accounts_contacts_oppcomponent_list.Edit">Edit</dhv:label></a>
      </td>
      </dhv:permission>
      <td width="100%">
        <dhv:permission name="admin-sysconfig-folders-view"><a href="AdminFieldsGroup.do?command=ListGroups&modId=<%= PermissionCategory.getId() %>&catId=<%= thisCategory.getId() %>"><%= toHtml(thisCategory.getName()) %></a><%= (thisCategory.getReadOnly()?"&nbsp;<img border='0' valign='absBottom' src='images/lock.gif'>":"") %></dhv:permission>
      </td>
      <td align="center" nowrap>
        <dhv:permission name="admin-sysconfig-folders-edit">
          <a href="AdminFieldsFolder.do?command=ToggleFolder&modId=<%= PermissionCategory.getId() %>&catId=<%= thisCategory.getId() %>"></dhv:permission>
<% if(thisCategory.getEnabled()) {%>
  <dhv:label name="account.yes">Yes</dhv:label>
<%} else {%>
  <dhv:label name="account.no">No</dhv:label>
<%}%>
          <dhv:permission name="admin-sysconfig-folders-edit"></a></dhv:permission>
      </td>
      <td align="center" nowrap>
        <zeroio:tz timestamp="<%= thisCategory.getStartDate() %>" dateOnly="true" default="&nbsp;"/>
      </td>
    </tr>
<%    
    }
  } else {
%>
    <tr class="containerBody">
      <td colspan="4">
        <font color="#9E9E9E"><dhv:label name="admin.noFoldersHaveBeenEntered.text">No folders have been entered.</dhv:label></font>
      </td>
    </tr>
<%}%>
  </table>
</form>
