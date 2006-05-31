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
      count++;
      rowid = (rowid != 1?1:2);
%>
  <tr>
    <th valign="center" align="center" nowrap>
      <a href="javascript:displayMenuPhase('select<%= count %>','menuPhase', '<%= permissionCategory.getId() %>','<%= constantId %>','<%= phase.getId() %>','<%= actionPlan.getId() %>', '<%= phase.getLastStep() %>');" 
        onMouseOver="over(0, <%= count %>)" onmouseout="out(0, <%= count %>); hideMenu('menuPhase');">
        <img src="images/select.gif" name="select<%= count %>" id="select<%= count %>" align="absmiddle" border="0">
      </a>
    </th>
    <th colspan="5" valign="top" width="100%">
      <%= toHtml(phase.getName()) %>
      <dhv:evaluate if="<%= phase.isGlobal() %>">
        (<dhv:label name="accounts.actionplans.globalphase">Global Phase</dhv:label>)
      </dhv:evaluate>
    </th>
  </tr>
      <%if (phase.getSteps() != null && phase.getSteps().size() > 0) {
        ActionStepList steps = phase.getSteps();
        Iterator iter = (Iterator) steps.iterator();
        while (iter.hasNext()) {
          ActionStep step = (ActionStep) iter.next();
          count++;
          rowid = (rowid != 1?1:2);
      %>
      <tr class="row<%= rowid %>">
        <td valign="center" align="center" nowrap>
          <a href="javascript:displayMenuStep('select<%= count %>','menuStep', '<%= permissionCategory.getId() %>','<%= constantId %>','<%= step.getId() %>', '<%= phase.getId() %>', '<%= actionPlan.getId() %>');" 
            onMouseOver="over(0, <%= count %>)" onmouseout="out(0, <%= count %>); hideMenu('menuStep');">
            <img src="images/select.gif" name="select<%= count %>" id="select<%= count %>" align="absmiddle" border="0">
          </a>
        </td>
        <td width="80%" valign="top">
          <%= toHtml(step.getDescription()) %> <dhv:evaluate if="<%= step.getActionRequired() %>"><font color="red">*</font></dhv:evaluate>
        </td>
       <td valign="top" nowrap>
          <dhv:label name="<%= ActionStep.getActionString(step.getActionId()) %>"><%= toHtml(ActionStep.getActionString(step.getActionId())) %></dhv:label>
        </td>
        <td width="20%" valign="top">
          <dhv:evaluate if="<%= step.getPermissionType() == ActionStep.ASSIGNED_USER %>">
            <dhv:label name="actionPlan.userAssignedToThePlan.text">User assigned to the plan</dhv:label>
          </dhv:evaluate>
          <dhv:evaluate if="<%= step.getPermissionType() == ActionStep.UP_USER_HIERARCHY %>">
            <dhv:label name="actionPlan.upUserHierarchy.text">User assigned and the user's managers</dhv:label>
          </dhv:evaluate>
          <dhv:evaluate if="<%= step.getPermissionType() == ActionStep.WITHIN_USER_HIERARCHY %>">
            <dhv:label name="actionPlan.assignedUserHierarchy.text">Within the hierarchy of the assigned user</dhv:label>
          </dhv:evaluate>
          <dhv:evaluate if="<%= step.getPermissionType() == ActionStep.MANAGER %>">
            <dhv:label name="actionPlan.planManager.text">Manager of the plan</dhv:label>
          </dhv:evaluate>
          <dhv:evaluate if="<%= step.getPermissionType() == ActionStep.ASSIGNED_USER_AND_MANAGER %>">
            <dhv:label name="actionPlan.assignedUserAndPlanManager.text">Assigned User and Manager of the plan</dhv:label>
          </dhv:evaluate>
          <dhv:evaluate if="<%= step.getPermissionType() == ActionStep.ROLE %>">
            <dhv:label name="actionPlan.memberOfTheRole.colon">Member of the role:</dhv:label>&nbsp;<%= toHtml(roles.getRoleNameFromId(step.getRoleId())) %>
          </dhv:evaluate>
          <dhv:evaluate if="<%= step.getPermissionType() == ActionStep.DEPARTMENT %>">
            <dhv:label name="actionPlan.memberOfTheDepartment.colon">Member of the department:</dhv:label>&nbsp;<%= toHtml(departmentType.getSelectedValue(step.getDepartmentId())) %>
          </dhv:evaluate>
          <dhv:evaluate if="<%= step.getPermissionType() == ActionStep.USER_DELEGATED %>">
            <dhv:label name="actionPlan.userDelegated">User delegated</dhv:label>
          </dhv:evaluate>
          <dhv:evaluate if="<%= step.getPermissionType() == ActionStep.USER_GROUP %>">
            <dhv:label name="usergroups.userGroup">User Group</dhv:label>
          </dhv:evaluate>
          <dhv:evaluate if="<%= step.getPermissionType() == ActionStep.SPECIFIC_USER_GROUP %>">
            <dhv:label name="usergroups.specificUserGroup.colon">Member of a specific group:</dhv:label>&nbsp;<%= toHtml(step.getUserGroupName()) %>
          </dhv:evaluate>
        </td>
        <td valign="top" nowrap>
          <dhv:evaluate if="<%= step.getActionRequired() %>">
            <dhv:label name="actionPlan.attachmentMandatory">Attachment Mandatory</dhv:label>
          </dhv:evaluate><dhv:evaluate if="<%= !step.getActionRequired() %>">
            <dhv:label name="actionPlan.attachmentOptional">Attachment Optional</dhv:label>
          </dhv:evaluate>
        </td>
        <td valign="top" nowrap>
          <dhv:evaluate if="<%= step.getEstimatedDuration() != -1 %>">
            <%= step.getEstimatedDuration() %>&nbsp;<%= durationType.getSelectedValue(step.getDurationTypeId()) %>
          </dhv:evaluate><dhv:evaluate if="<%= step.getEstimatedDuration() == -1 %>">&nbsp;</dhv:evaluate>
        </td>
      </tr>
    <%  }
        rowid = (rowid != 1?1:2);
    %>
      <tr>
        <td colspan="7">&nbsp;</td>
      </tr>
    <%} else {%>
      <tr>
        <td class="containerBody" valign="center" colspan="6">
          <dhv:label name="actionPlan.noStepsForPhase.text">No Steps found for this Phase.</dhv:label>
        </td>
      </tr>
    <%}%>
<%  }
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

