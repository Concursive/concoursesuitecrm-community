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
  - Version: $Id: admin_actionplan_phase_details_include.jsp
  - Description: 
  --%>
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
				<dhv:evaluate if="<%= step.getQuickComplete() %>"><img src="images/approved.gif"></dhv:evaluate>
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
