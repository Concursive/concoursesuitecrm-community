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
  - Version: $Id: action_item_modify.jsp
  - Description: 
  --%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ page import="java.util.*,java.text.DateFormat,org.aspcfs.modules.actionplans.base.*, org.aspcfs.modules.base.Constants"%>
<%@ page import="org.aspcfs.modules.admin.base.*" %>
<%@ include file="../initPage.jsp" %>
<jsp:useBean id="actionItemWork" class="org.aspcfs.modules.actionplans.base.ActionItemWork" scope="request"/>
<jsp:useBean id="nextItemWork" class="org.aspcfs.modules.actionplans.base.ActionItemWork" scope="request"/>
<jsp:useBean id="orgDetails" class="org.aspcfs.modules.accounts.base.Organization" scope="request"/>
<dhv:evaluate if="<%= orgDetails.getOrgId() > -1 %>">
  <form name="actionStep" action="AccountActionPlans.do?command=UpdateStatus&popup=true" method="post">
</dhv:evaluate>
<dhv:evaluate if="<%= orgDetails.getOrgId() == -1 %>">
  <form name="actionStep" action="MyActionPlans.do?command=UpdateStatus&popup=true" method="post">
</dhv:evaluate>
<table cellpadding="4" cellspacing="4" width="100%" border="0">
  <tr><td><strong><dhv:label name="actionItem.updateStep.comment">Updating this step will</dhv:label>:</strong></td></tr>
  <tr>
    <td>
      <ul>
        <li><dhv:label name="actionItem.updateStep.changeStatus">Change the status to</dhv:label>: <%= ActionPlanWork.getStatusSelect("statusId", actionItemWork.getStatusId()) %></li>
        <dhv:evaluate if="<%= (nextItemWork.getId() != -1) %>">
          <li><dhv:label name="actionItem.updateStep.beginNext">Begin the next step</dhv:label>: <strong><%= toHtml(nextItemWork.getStepDescription()) %></strong></li>
          <li><dhv:label name="actionItem.updateStep.ownerNext">Owner of the next step</dhv:label>: 
            <strong>
              <dhv:evaluate if="<%= nextItemWork.getStep().getPermissionType() == ActionStep.USER_GROUP %>">
                <dhv:label name="usergroups.userGroup">User Group</dhv:label>: 
                <dhv:evaluate if='<%= nextItemWork.getUserGroupName() != null && !"".equals(nextItemWork.getUserGroupName()) %>'>
                  <%= toHtml(nextItemWork.getUserGroupName()) %>
                </dhv:evaluate><dhv:evaluate if='<%= nextItemWork.getUserGroupName() == null || "".equals(nextItemWork.getUserGroupName()) %>'>
                  <dhv:label name="usergroups.notSet">Not set</dhv:label>
                </dhv:evaluate>
              </dhv:evaluate>
              <dhv:evaluate if="<%= nextItemWork.getStep().getPermissionType() == ActionStep.SPECIFIC_USER_GROUP %>">
                <dhv:label name="usergroups.userGroup">User Group</dhv:label>: 
                <dhv:evaluate if='<%= nextItemWork.getUserGroupName() != null && !"".equals(nextItemWork.getUserGroupName()) %>'>
                  <%= toHtml(nextItemWork.getUserGroupName()) %>
                </dhv:evaluate><dhv:evaluate if='<%= nextItemWork.getUserGroupName() == null || "".equals(nextItemWork.getUserGroupName()) %>'>
                  <dhv:label name="usergroups.notSet">Not set</dhv:label>
                </dhv:evaluate>
              </dhv:evaluate><dhv:evaluate if="<%= nextItemWork.getStep().getPermissionType() == ActionStep.DEPARTMENT %>">
                <dhv:label name="project.department">Department</dhv:label>: <%= toHtml(nextItemWork.getDepartmentName()) %>
              </dhv:evaluate><dhv:evaluate if="<%= nextItemWork.getStep().getPermissionType() == ActionStep.ROLE %>">
                <dhv:label name="accounts.accounts_add.Role">Role</dhv:label>: <%= toHtml(nextItemWork.getRoleName()) %>
              </dhv:evaluate><dhv:evaluate if="<%= nextItemWork.getStep().getPermissionType() != ActionStep.DEPARTMENT && 
                nextItemWork.getStep().getPermissionType() != ActionStep.ROLE && 
                nextItemWork.getStep().getPermissionType() != ActionStep.SPECIFIC_USER_GROUP && 
                  nextItemWork.getStep().getPermissionType() != ActionStep.USER_GROUP %>">
                <dhv:username id="<%= nextItemWork.getOwnerId() %>"/>
              </dhv:evaluate>
            </strong>
          </li>
          <li><dhv:label name="actionItem.updateStep.alertNextOwner">Alert the next step's owner by email</dhv:label></li>
        </dhv:evaluate>
      </ul>
    </td>
  </tr>
</table>
<input type="hidden" name="returnUrl" value="<%= request.getParameter("return") %>"/>
<input type="hidden" name="stepId" value="<%= actionItemWork.getId() %>"/>
<input type="hidden" name="nextStepId" value="<%= nextItemWork.getId() %>"/>
<input type="submit" value="<dhv:label name="global.button.save">Save</dhv:label>" name="Save"/>
<input type="button" value="<dhv:label name="global.button.cancel">Cancel</dhv:label>" onClick="self.close();" />
</form>           

