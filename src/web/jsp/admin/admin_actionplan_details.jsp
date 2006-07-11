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
<jsp:useBean id="actionPlan" class="org.aspcfs.modules.actionplans.base.ActionPlan" scope="request"/>
<jsp:useBean id="durationType" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="departmentType" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="roles" class="org.aspcfs.modules.admin.base.RoleList" scope="request"/>
<jsp:useBean id="permissionCategory" class="org.aspcfs.modules.admin.base.PermissionCategory" scope="request"/>
<jsp:useBean id="constantId" class="java.lang.String" scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<jsp:useBean id="applicationPrefs" class="org.aspcfs.controller.ApplicationPrefs" scope="application"/>
<%@ include file="../initPage.jsp" %>
<%@ include file="../initPopupMenu.jsp" %>
<%@ include file="admin_actionphase_menu.jsp" %>
<%@ include file="admin_actionstep_menu.jsp" %>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/confirmDelete.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popURL.js"></script>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/spanDisplay.js"></SCRIPT>
<script type="text/javascript">
  <%-- Preload image rollovers for drop-down menu --%>
  loadImages('select');
  
  function reopen() {
    scrollReload('ActionPlanEditor.do?command=PlanDetails&planId=<%= actionPlan.getId() %>&moduleId=<%= permissionCategory.getId() %>&constantId=<%= constantId %>&return=details');
  }
</script>
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
  <a href="Admin.do"><dhv:label name="trails.admin">Admin</dhv:label></a> >
  <a href="Admin.do?command=Config"><dhv:label name="trails.configureModules">Configure Modules</dhv:label></a> >
  <a href="Admin.do?command=ConfigDetails&moduleId=<%= permissionCategory.getId() %>"><%= toHtml(permissionCategory.getCategory()) %></a> >
  <a href="ActionPlanEditor.do?command=ListEditors&moduleId=<%= permissionCategory.getId() %>"><dhv:label name="actionPlan.actionPlanEditors">Action Plan Editors</dhv:label></a> >
  <a href="ActionPlanEditor.do?command=ListPlans&moduleId=<%= permissionCategory.getId() %>&constantId=<%= constantId %>"><dhv:label name="sales.actionPlans">Action Plans</dhv:label></a> >
<dhv:label name="actionPlan.actionPlanDetails">Action Plan Details</dhv:label>
</td>
</tr>
</table>
<%-- End Trails --%>
<%-- <dhv:permission value="admin-actionplan-add"> --%> 
<img src="images/icons/stock_bcard-16.gif" border="0" align="absmiddle">
<strong><dhv:label name="actionPlan.actionPlan.colon">Action Plan:</dhv:label> &nbsp;<%= toHtml(actionPlan.getName()) %></strong>
<dhv:evaluate if="<%= actionPlan.getPhases() == null || actionPlan.getPhases().size() == 0 %>">
<br /><br /><a href="javascript:popURL('ActionPhaseEditor.do?command=AddPhase&planId=<%= actionPlan.getId() %>&popup=true&moduleId=<%= permissionCategory.getId() %>&constantId=<%= constantId %>','PhaseAdd',600,300,'yes','yes');"><dhv:label name="actionPlan.addAPhase">Add a Phase</dhv:label></a>
</dhv:evaluate>
<%-- </dhv:permission> --%>
&nbsp;<br /><br />
<table cellpadding="4" cellspacing="0" width="100%" class="pagedList">
<%if (actionPlan.getPhases() != null && actionPlan.getPhases().size() > 0) {
    int rowid = 0;
    int count = 0;
    ActionPhaseList phases = actionPlan.getPhases();
    Iterator iterator = (Iterator) phases.iterator();
    while (iterator.hasNext()) {
      ActionPhase phase = (ActionPhase) iterator.next();
      if (phase.isGlobal()) {
        count++;
        rowid = (rowid != 1?1:2);
%>
        <%-- include global phase details --%>
        <%@ include file="admin_actionplan_phase_details_include.jsp" %>
<%
      }
    }
  } 
%>
</table>
&nbsp;<br/>
<table cellpadding="4" cellspacing="0" width="100%" class="pagedList">
<%if (actionPlan.getPhases() != null && actionPlan.getPhases().size() > 0) {
    int rowid = 0;
    int count = 0;
    ActionPhaseList phases = actionPlan.getPhases();
    Iterator iterator = (Iterator) phases.iterator();
    while (iterator.hasNext()) {
      ActionPhase phase = (ActionPhase) iterator.next();
      if (!phase.isGlobal()) {
        count++;
        rowid = (rowid != 1?1:2);
%>
        <%-- include regular phase details --%>
        <%@ include file="admin_actionplan_phase_details_include.jsp" %>
<%
      }
    }
  } else {%>
  <tr>
    <th valign="center" colspan="6">
      <dhv:label name="actionPlan.noPhasesFound.text">No Phases found.</dhv:label>
    </th>
  </tr>
<%}%>
</table>
<br />
<dhv:evaluate if="<%= actionPlan.getPhases() != null && actionPlan.getPhases().size() != 0 %>">
<a href="javascript:popURL('ActionPhaseEditor.do?command=AddPhase&planId=<%= actionPlan.getId() %><%= (actionPlan.getPhases() == null || actionPlan.getPhases().size() == 0) ?"":"&previousPhaseId="+actionPlan.getPhases().getLastPhaseId() %>&popup=true&moduleId=<%= permissionCategory.getId() %>&constantId=<%= constantId %>','PhaseAdd',600,300,'yes','yes');"><dhv:label name="actionPlan.addAPhase">Add a Phase</dhv:label></a>
</dhv:evaluate>
<iframe src="../empty.html" name="server_commands" id="server_commands" style="visibility:hidden" height="0"></iframe>

