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
<%@ page import="java.util.*,org.aspcfs.modules.actionplans.base.*,org.aspcfs.modules.admin.base.*" %>
<jsp:useBean id="actionPlanList" class="org.aspcfs.modules.actionplans.base.ActionPlanList" scope="request"/>
<jsp:useBean id="planListInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<jsp:useBean id="permissionCategory" class="org.aspcfs.modules.admin.base.PermissionCategory" scope="request"/>
<jsp:useBean id="constantId" class="java.lang.String" scope="request"/>
<jsp:useBean id="SiteIdList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<jsp:useBean id="applicationPrefs" class="org.aspcfs.controller.ApplicationPrefs" scope="application"/>
<%@ include file="../initPage.jsp" %>
<%@ include file="../initPopupMenu.jsp" %>
<%@ include file="admin_actionplan_list_menu.jsp" %>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/confirmDelete.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popURL.js"></script>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/spanDisplay.js"></SCRIPT>
<script type="text/javascript">
  <%-- Preload image rollovers for drop-down menu --%>
  loadImages('select');
</script>
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
  <a href="Admin.do"><dhv:label name="trails.admin">Admin</dhv:label></a> >
  <a href="Admin.do?command=Config"><dhv:label name="trails.configureModules">Configure Modules</dhv:label></a> >
  <a href="Admin.do?command=ConfigDetails&moduleId=<%= permissionCategory.getId() %>"><%= toHtml(permissionCategory.getCategory()) %></a> >
  <a href="ActionPlanEditor.do?command=ListEditors&moduleId=<%= permissionCategory.getId() %>"><dhv:label name="actionPlan.actionPlanEditors">Action Plan Editors</dhv:label></a> >
  <dhv:label name="trails.admin.configureActionPlans">Action Plans</dhv:label>
</td>
</tr>
</table>
<%-- End Trails --%>
<table class="note" cellspacing="0">
  <tr>
    <th><img src="images/icons/stock_about-16.gif" border="0" align="absmiddle"/></th>
    <td><dhv:label name="actionPlan.actionPlanDetailsPage.text">Create, Modify or Delete Action Plans</dhv:label></td>
  </tr>
</table>
<dhv:permission name="admin-actionplans-add">
<a href="ActionPlanEditor.do?command=AddPlan&moduleId=<%= permissionCategory.getId() %>&constantId=<%= constantId %>&siteId=<%= User.getUserRecord().getSiteId() %>"><dhv:label name="actionPlan.addActionPlan">Add Action Plan</dhv:label></a>
</dhv:permission>
<dhv:permission name="admin-actionplans-add,admin-sysconfig-categories-edit" all="true">&nbsp;|&nbsp;</dhv:permission>
<dhv:permission name="admin-sysconfig-categories-edit"> 
<a href="javascript:popURL('AdminCategories.do?command=ViewActive&moduleId=<%= PermissionCategory.PERMISSION_CAT_ADMIN %>&constantId=<%= PermissionCategory.MULTIPLE_CATEGORY_ACTIONPLAN %>&popup=true','ActionPlanCategories',600,400,'yes','yes');"><dhv:label name="actionPlan.configureCategories">Configure Categories</dhv:label></a>
</dhv:permission>
<%-- <dhv:permission name="admin-actionplans-add,admin-sysconfig-categories-edit" all="true">&nbsp;|&nbsp;</dhv:permission>
<dhv:permission name="admin-actionplans-edit">
<a href="ActionPlanEditor.do?command=AddMappings&moduleId=<%= permissionCategory.getId() %>&constantId=<%= constantId %>"><dhv:label name="actionPlan.configureMappings">Configure Mappings</dhv:label></a>
</dhv:permission> --%>
<dhv:include name="pagedListInfo.alphabeticalLinks" none="true"><center><dhv:pagedListAlphabeticalLinks object="planListInfo"/></center></dhv:include>
<dhv:pagedListStatus title="<%= showError(request, "actionError") %>" object="planListInfo"/>
<table cellpadding="4" cellspacing="0" width="100%" class="pagedList">
  <tr>
    <th align="center">
      &nbsp;
    </th>
    <th nowrap>
      <strong><a href="ActionPlanEditor.do?command=ListPlans&column=ap.plan_name&moduleId=<%= permissionCategory.getId() %>&constantId=<%= constantId %>"><dhv:label name="quotes.productName">Name</dhv:label></a></strong>
      <%= planListInfo.getSortIcon("ap.plan_name") %>
    </th>
    <th nowrap>
      <strong><dhv:label name="actionPlan.phases">Phases</dhv:label></strong>
    </th>
    <th nowrap>
      <strong><dhv:label name="actionPlan.steps">Steps</dhv:label></strong>
    </th>
    <th nowrap>
      <strong><dhv:label name="documents.details.approved">Approved</dhv:label></strong>
    </th>
    <th nowrap>
      <strong><a href="ActionPlanEditor.do?command=ListPlans&column=ap.approved&moduleId=<%= permissionCategory.getId() %>&constantId=<%= constantId %>"><dhv:label name="admin.activeDate">Active Date</dhv:label></a></strong>
      <%= planListInfo.getSortIcon("ap.approved") %>
    </th>
    <th nowrap>
      <strong><a href="ActionPlanEditor.do?command=ListPlans&column=ap.enabled&moduleId=<%= permissionCategory.getId() %>&constantId=<%= constantId %>"><dhv:label name="documents.details.archived">Archived</dhv:label></a></strong>
      <%= planListInfo.getSortIcon("ap.enabled") %>
    </th>
    <th nowrap>
      <strong><a href="ActionPlanEditor.do?command=ListPlans&column=ap.archive_date&moduleId=<%= permissionCategory.getId() %>&constantId=<%= constantId %>"><dhv:label name="actionPlan.archivedDate">Archived Date</dhv:label></a></strong>
      <%= planListInfo.getSortIcon("ap.archive_date") %>
    </th>
    <dhv:evaluate if="<%= User.getUserRecord().getSiteId() == -1 %>">
    <th nowrap>
      <strong><dhv:label name="accounts.site">Site</dhv:label></strong>
    </th>
    </dhv:evaluate>
    <th nowrap>
      <strong><dhv:label name="admin.records">Records</dhv:label></strong>
    </th>
  </tr>
<%if (actionPlanList.size() > 0) {
    int rowid = 0;
    int count = 0;
    Iterator iterator = (Iterator) actionPlanList.iterator();
    while (iterator.hasNext()) {
      ActionPlan plan = (ActionPlan) iterator.next();
      count++;
      rowid = (rowid != 1?1:2);
%>
  <tr class="row<%= rowid %>">
    <td valign="center" align="center" nowrap>
      <a href="javascript:displayMenu('select<%= count %>','menuPlan', '<%= permissionCategory.getId() %>','<%= constantId %>', '<%= plan.getId() %>', <%= plan.getApproved() != null %>,<%= !plan.getEnabled() %>);" 
        onMouseOver="over(0, <%= count %>)" onmouseout="out(0, <%= count %>); hideMenu('menuPlan');">
        <img src="images/select.gif" name="select<%= count %>" id="select<%= count %>" align="absmiddle" border="0">
      </a>
    </td>
    <td valign="top" width="100%">
      <a href="ActionPlanEditor.do?command=PlanDetails&planId=<%= plan.getId() %>&moduleId=<%= permissionCategory.getId() %>&constantId=<%= constantId %>&return=list"><%= toHtml(plan.getName()) %></a>
    </td>
    <td valign="top" align="center" nowrap>
      <dhv:evaluate if="<%= plan.getPhases() != null %>">
        <%= plan.getPhases().size() %>
      </dhv:evaluate><dhv:evaluate if="<%= plan.getPhases() == null %>">0</dhv:evaluate>
    </td>
    <td valign="top" align="center" nowrap>
      <%= plan.getPhases().getStepsSize() %>
    </td>
    <td valign="top" align="center" nowrap>
      <%if(plan.getApproved() != null) { %>
        <dhv:label name="account.yes">Yes</dhv:label>
      <%} else {%>
        <dhv:label name="account.no">No</dhv:label>
      <%}%>
    </td>
    <td valign="top" align="center" nowrap>
      <zeroio:tz timestamp="<%= plan.getApproved() %>" dateOnly="true" timeZone="<%= User.getTimeZone() %>" showTimeZone="true" default="&nbsp;"/>
    </td>
    <td valign="top" align="center" nowrap>
      <%if(plan.getEnabled()) { %>
        <dhv:label name="account.no">No</dhv:label>
      <%} else {%>
        <dhv:label name="account.yes">Yes</dhv:label>
      <%}%>
    </td>
    <td valign="top" align="center" nowrap>
      <zeroio:tz timestamp="<%= plan.getArchiveDate() %>" dateOnly="true" timeZone="<%= User.getTimeZone() %>" showTimeZone="true" default="&nbsp;"/>
    </td>
    <dhv:evaluate if="<%= User.getUserRecord().getSiteId() == -1 %>">
      <td valign="top" nowrap><%= SiteIdList.getSelectedValue(plan.getSiteId()) %></td>
    </dhv:evaluate>
    <td valign="top" align="center" nowrap>
<%-- TODO --%>
      <%= plan.getPlanWorks() != null ? plan.getPlanWorks().size():0 %>
    </td>
  </tr>
<%  }
  } else {%>
<tr>
    <td class="containerBody" valign="center" colspan="<%= User.getUserRecord().getSiteId() == -1 ? "10":"9" %>">
      <dhv:label name="actionPlan.noActionPlansFound.text">No action plans found.</dhv:label>
    </td>
  </tr>
<%}%>
</table>
<br />
<dhv:pagedListControl object="planListInfo" tdClass="row1"/>
