<%-- TODO: Show 'nothing to configure' if none available AND no permissions --%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="org.aspcfs.modules.admin.base.PermissionCategory, java.util.*" %>
<%@ include file="../initPage.jsp" %>
<jsp:useBean id="PermissionCategory" class="org.aspcfs.modules.admin.base.PermissionCategory" scope="request"/>
<%-- Trails --%>
<table class="trails">
<tr>
<td>
<a href="Admin.do">Admin</a> > 
<a href="Admin.do?command=Config">Configure Modules</a> >
<%= PermissionCategory.getCategory() %>
</td>
</tr>
</table>
<%-- End Trails --%>
<table cellpadding="4" cellspacing="0" width="100%" class="pagedList">
  <tr>
    <th>
      <strong>Configuration Options</strong>
    </th>
  </tr>
<% 
  int count = 0;
  int rowid = 0;
%>
<%-- Categories --%>
<% 
  if (PermissionCategory.getCategories()) { 
%>
  <dhv:permission name="admin-sysconfig-categories-view">
  <% ++count; rowid = (rowid != 1 ? 1 : 2); %>
  <tr class="row<%= rowid %>">
    <td><a href="AdminCategories.do?command=Show&moduleId=<%= PermissionCategory.getId() %>">Categories</a></td>
  </tr>
  </dhv:permission>
<%}%>
<%-- Folders --%>
<% 
  if (PermissionCategory.getFolders()) {
%>
  <dhv:permission name="admin-sysconfig-folders-view">
  <% ++count; rowid = (rowid != 1 ? 1 : 2); %>
  <tr class="row<%= rowid %>">
    <td><a href="AdminFieldsFolder.do?modId=<%= PermissionCategory.getId() %>">Custom Folders &amp; Fields</a></td>
  </tr>
  </dhv:permission>
<%}%>
<%-- Lookups --%>
<%
  if (PermissionCategory.getLookups()) {
%>
  <dhv:permission name="admin-sysconfig-lists-view">
  <% ++count; rowid = (rowid != 1 ? 1 : 2); %>
  <tr class="row<%= rowid %>">
    <td><a href="Admin.do?command=EditLists&moduleId=<%= PermissionCategory.getId() %>">Lookup Lists</a></td>
  </tr>
  </dhv:permission>
<%}%>
<%-- Object Events --%>
<% 
  if (PermissionCategory.getObjectEvents()) { 
%>
  <dhv:permission name="admin-object-workflow-view">
  <% ++count; rowid = (rowid != 1 ? 1 : 2); %>
  <tr class="row<%= rowid %>">
    <td>
      <a href="AdminObjectEvents.do?moduleId=<%= PermissionCategory.getId() %>">Object Events</a>
      <%--<i>Object events are background processes that are triggered by an action</i>--%>
    </td>
  </tr>
  </dhv:permission>
<%}%>
<%-- Scheduled Events --%>
<% 
  if (PermissionCategory.getScheduledEvents()) { 
%>
  <dhv:permission name="admin-object-workflow-view">
  <% ++count; rowid = (rowid != 1 ? 1 : 2); %>
  <tr class="row<%= rowid %>">
    <td>
      <a href="AdminScheduledEvents.do?moduleId=<%= PermissionCategory.getId() %>">Scheduled Events</a>
      <%--<i>Scheduled events are background processes that can run continously or at a specific date and time</i>--%>
    </td>
  </tr>
  </dhv:permission>
<%}%>
<%-- Nothing to configure --%>
<dhv:evaluate if="<%= count == 0 %>">
  <tr>
    <td>Nothing to configure in this Dark Horse CRM module.</td>
  </tr>
</dhv:evaluate>
</table>

