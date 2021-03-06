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
  - Version: $Id$
  - Description: 
  --%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.Iterator,org.aspcfs.modules.admin.base.PermissionCategory, org.aspcfs.modules.actionplans.base.*" %>
<jsp:useBean id="planEditors" class="org.aspcfs.modules.actionplans.base.PlanEditorList" scope="request"/>
<jsp:useBean id="permissionCategory" class="org.aspcfs.modules.admin.base.PermissionCategory" scope="request"/>
<%@ include file="../initPage.jsp" %>
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="Admin.do"><dhv:label name="trails.admin">Admin</dhv:label></a> >
<a href="Admin.do?command=Config"><dhv:label name="trails.configureModules">Configure Modules</dhv:label></a> >
<a href="Admin.do?command=ConfigDetails&moduleId=<%= permissionCategory.getId() %>"><%= toHtml(permissionCategory.getCategory()) %></a> >
<dhv:label name="actionPlan.actionPlanEditors">Action Plan Editors</dhv:label>
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
  Iterator i = planEditors.iterator();
  while (i.hasNext()) {
    rowid = (rowid != 1 ? 1 : 2);
    PlanEditor thisEditor = (PlanEditor) i.next();
%>
    <tr class="row<%= rowid %>">
      <dhv:permission name="admin-sysconfig-categories-edit"><td align="center"><a href="ActionPlanEditor.do?command=ListPlans&moduleId=<%= permissionCategory.getId() %>&constantId=<%= thisEditor.getConstantId() %>"><dhv:label name="accounts.accounts_contacts_oppcomponent_list.Edit">Edit</dhv:label></a></td></dhv:permission>
      <td valign="center" width="100%"><%= toHtml(thisEditor.getDescription()) %></td>
    </tr>
<% } %>
<dhv:evaluate if="<%= planEditors.size() == 0 %>">
  <tr>
    <td valign="center" colspan="4"><dhv:label name="admin.noPlanEditorsToConfigure">No action plan editors to configure.</dhv:label></td>
  </tr>
</dhv:evaluate>
</table>
