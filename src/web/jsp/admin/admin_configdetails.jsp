<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="org.aspcfs.modules.admin.base.PermissionCategory, java.util.*" %>
<%@ include file="../initPage.jsp" %>
<jsp:useBean id="PermissionCategory" class="org.aspcfs.modules.admin.base.PermissionCategory" scope="request"/>
<a href="Admin.do">Setup</a> > 
<a href="Admin.do?command=Config">Configure Modules</a> >
<%= PermissionCategory.getCategory() %><br>
<hr color="#BFBFBB" noshade>
<table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr class="title">
    <td>
      <strong>Configuration Options</strong>
    </td>
  </tr>
<% if (PermissionCategory.getLookups()) { %>
  <dhv:permission name="admin-sysconfig-lists-view">
  <tr>
    <td><a href="Admin.do?command=EditLists&moduleId=<%= PermissionCategory.getId() %>">Lookup Lists</a></td>
  </tr>
  </dhv:permission>
<%}%>
<% if (PermissionCategory.getFolders()) { %>
  <dhv:permission name="admin-sysconfig-folders-view">
  <tr>
    <td><a href="AdminFieldsFolder.do?modId=<%= PermissionCategory.getId() %>">Custom Folders &amp; Fields</a></td>
  </tr>
  </dhv:permission>
<%}%>
<% if (PermissionCategory.getCategories()) { %>
  <dhv:permission name="admin-sysconfig-folders-view">
  <tr>
    <td><a href="AdminCategories.do?command=ViewActive&moduleId=<%= PermissionCategory.getId() %>">Categories</a></td>
  </tr>
  </dhv:permission>
<%}%>
<% if (!PermissionCategory.getLookups() && !PermissionCategory.getFolders() && !PermissionCategory.getCategories()) { %>
  <tr>
    <td>Nothing to configure in this CFS module.</td>
  </tr>
<%}%>
</table>

