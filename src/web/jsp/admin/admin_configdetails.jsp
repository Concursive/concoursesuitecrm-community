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
<a href="Admin.do"><dhv:label name="trails.admin">Admin</dhv:label></a> >
<a href="Admin.do?command=Config"><dhv:label name="trails.configureModules">Configure Modules</dhv:label></a> >
<%= PermissionCategory.getCategory() %>
</td>
</tr>
</table>
<%-- End Trails --%>
<table cellpadding="4" cellspacing="0" width="100%" class="pagedList">
  <tr>
    <th>
      <strong><dhv:label name="admin.configureOptions">Configuration Options</dhv:label></strong>
    </th>
  </tr>
<%
  int count = 0;
  int rowid = 0;
%>
<%-- Action Plans --%>
<%
  if (PermissionCategory.getActionPlans()) {
%>
  <dhv:permission name="admin-actionplans-view">
  <% ++count; rowid = (rowid != 1 ? 1 : 2); %>
	<tr class="row<%= rowid %>">
		<td>
			<a href="ActionPlanEditor.do?command=ListEditors&moduleId=<%= PermissionCategory.getId() %>"><dhv:label name="admin.actionPlan.actionPlanEditor">Action Plan Editor</dhv:label></a>
		</td>
	</tr>
  </dhv:permission>
<%}%>
<%-- Categories --%>
<%
  if (PermissionCategory.getCategories()) {
%>
  <dhv:permission name="admin-sysconfig-categories-view">
  <% ++count; rowid = (rowid != 1 ? 1 : 2); %>
  <tr class="row<%= rowid %>">
    <td><a href="AdminCategories.do?command=Show&moduleId=<%= PermissionCategory.getId() %>"><dhv:label name="product.Categories">Categories</dhv:label></a></td>
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
    <td><a href="AdminFieldsFolder.do?modId=<%= PermissionCategory.getId() %>"><dhv:label name="admin.customFoldersAndFields" param="amp=&amp;">Custom Folders &amp; Fields</dhv:label></a></td>
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
    <td><a href="Admin.do?command=EditLists&moduleId=<%= PermissionCategory.getId() %>"><dhv:label name="admin.lookupLists">Lookup Lists</dhv:label></a></td>
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
      <a href="AdminObjectEvents.do?moduleId=<%= PermissionCategory.getId() %>"><dhv:label name="admin.objectEvents">Object Events</dhv:label></a>
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
      <a href="AdminScheduledEvents.do?moduleId=<%= PermissionCategory.getId() %>"><dhv:label name="admin.scheduledEvents">Scheduled Events</dhv:label></a>
      <%--<i>Scheduled events are background processes that can run continously or at a specific date and time</i>--%>
    </td>
  </tr>
  </dhv:permission>
<%}%>
<%-- Logos --%>
<%
  if (PermissionCategory.getLogos()) {
%>
  <dhv:permission name="admin-sysconfig-logos-view">
  <% ++count; rowid = (rowid != 1 ? 1 : 2); %>
  <tr class="row<%= rowid %>">
    <td>
      <a href="AdminLogos.do?command=View&moduleId=<%= PermissionCategory.getId() %>">Logos</a>
    </td>
  </tr>
  </dhv:permission>
<%}%>
<%-- Custom List View Editor --%>
<%
  if (PermissionCategory.getCustomListViews()) {
%>
  <dhv:permission name="admin-sysconfig-customlistviews-view">
  <% ++count; rowid = (rowid != 1 ? 1 : 2); %>
	<tr class="row<%= rowid %>">
		<td>
			<a href="AdminCustomListViews.do?command=List&moduleId=<%= PermissionCategory.getId() %>"><dhv:label name="admin.customListViewEditor">Custom List View Editor</dhv:label></a>
		</td>
	</tr>
  </dhv:permission>
<%}%>
<%-- Dashboards --%>
<%
  if (PermissionCategory.getDashboards()) {
%>
  <dhv:permission name="admin-sysconfig-dashboard-view">
  <% ++count; rowid = (rowid != 1 ? 1 : 2); %>
  <tr class="row<%= rowid %>">
    <td>
      <a href="AdminDashboards.do?command=List&moduleId=<%= PermissionCategory.getId() %>">Dashboards</a>
    </td>
  </tr>
  </dhv:permission>
<%}%>
<%-- Custom Tabs --%>
<%
  if (PermissionCategory.getCustomtabs()) {
%>
  <dhv:permission name="admin-sysconfig-customtab-view">
  <% ++count; rowid = (rowid != 1 ? 1 : 2); %>
  <tr class="row<%= rowid %>">
    <td>
      <a href="AdminCustomTabs.do?command=List&moduleId=<%= PermissionCategory.getId() %>">Custom Tabs</a>
    </td>
  </tr>
  </dhv:permission>
<%}%>

<%-- Nothing to configure --%>
<dhv:evaluate if="<%= count == 0 %>">
  <tr>
    <td><dhv:label name="admin.nothingToConfigure.text">Nothing to configure in this module.</dhv:label></td>
  </tr>
</dhv:evaluate>
</table>

