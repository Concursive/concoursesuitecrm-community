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
<%-- TODO: Show 'nothing to configure' if none available AND no permissions --%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="org.aspcfs.modules.admin.base.PermissionCategory, java.util.*" %>
<%@ include file="../initPage.jsp" %>
<jsp:useBean id="PermissionCategory" class="org.aspcfs.modules.admin.base.PermissionCategory" scope="request"/>
<%-- Trails --%>
<table class="trails" cellspacing="0">
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
<%-- Product Catalog Editor --%>
<% 
  if (PermissionCategory.getProducts()) { 
%>
  <dhv:permission name="admin-sysconfig-products-view">
  <% ++count; rowid = (rowid != 1 ? 1 : 2); %>
  <tr class="row<%= rowid %>">
    <td>
      <a href="ProductsCatalog.do?command=ListAllProducts&moduleId=<%= PermissionCategory.getId() %>">Labor Category Editor</a>
    </td>
  </tr>
  </dhv:permission>
<%}%>
<%-- Nothing to configure --%>
<dhv:evaluate if="<%= count == 0 %>">
  <tr>
    <td>Nothing to configure in this module.</td>
  </tr>
</dhv:evaluate>
</table>

