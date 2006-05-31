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
<%@ page import="org.aspcfs.modules.communications.base.*" %>
<jsp:useBean id="actionPlan" class="org.aspcfs.modules.actionplans.base.ActionPlan" scope="request"/>
<jsp:useBean id="actionPhase" class="org.aspcfs.modules.actionplans.base.ActionPhase" scope="request"/>
<jsp:useBean id="actionStep" class="org.aspcfs.modules.actionplans.base.ActionStep" scope="request"/>
<jsp:useBean id="roles" class="org.aspcfs.modules.admin.base.RoleList" scope="request"/>
<jsp:useBean id="userGroupList" class="org.aspcfs.utils.web.HtmlSelect" scope="request"/>
<jsp:useBean id="actionPhaseSelect" class="org.aspcfs.utils.web.HtmlSelect" scope="request"/>
<jsp:useBean id="actionIdSelect" class="org.aspcfs.utils.web.HtmlSelect" scope="request"/>
<jsp:useBean id="durationType" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="stepActionSelect" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="departmentType" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="relationshipTypes" class="org.aspcfs.modules.relationships.base.RelationshipTypeList" scope="request"/>
<jsp:useBean id="accountTypeList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="campaignList" class="org.aspcfs.modules.communications.base.CampaignList" scope="request"/>
<jsp:useBean id="categoryList" class="org.aspcfs.modules.base.CustomFieldCategoryList" scope="request"/>
<jsp:useBean id="nextStepId" class="java.lang.String" scope="request" />
<jsp:useBean id="previousStepId" class="java.lang.String" scope="request" />
<jsp:useBean id="permissionCategory" class="org.aspcfs.modules.admin.base.PermissionCategory" scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<jsp:useBean id="applicationPrefs" class="org.aspcfs.controller.ApplicationPrefs" scope="application"/>
<%@ include file="../initPage.jsp" %>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/confirmDelete.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popURL.js"></script>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/spanDisplay.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/checkString.js"></SCRIPT>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popLookupSelect.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popCampaigns.js"></script>
<script type="text/javascript">
  function checkForm(form) {
    formTest = true;
    message = "";
    
    if (document.addActionStep.actionId.options[document.addActionStep.actionId.selectedIndex].value == '<%= ActionStep.ATTACH_RELATIONSHIP %>') {
      //Attach Relationships
      var test = document.addActionStep.selectedList;
      if (test != null) {
        if (test.options.length == 1 && test.options[0].value == -1) {
          message += label("select.account.types", "- Please select one or more Account Types.\r\n");
          formTest = false;
        } 
      }
    }
    if (document.addActionStep.actionId.options[document.addActionStep.actionId.selectedIndex].value == '<%= ActionStep.ADD_RECIPIENT %>') {
      if (document.addActionStep.campaignId.value == '-1') {
        message += label("check.campaign.selection", "- Please select a valid active campaign.\r\n");
        formTest = false;
      }
    } else {
      document.addActionStep.campaignId.value = '-1';
    }
    if (formTest == false) {
      alert(label("check.form", "Form could not be saved, please check the following:\r\n\r\n") + message);
      return false;
    } else {
      var test = document.addActionStep.selectedList;
      if (test != null) {
        return (selectAllOptions(document.addActionStep.selectedList));
      }
    }
    return true;
  }
  
  function updatePermissionType() {
    var permissions = document.addActionStep.permissionType;
    if (permissions.options[permissions.selectedIndex].value == '<%= ActionStep.ROLE %>') {
      showSpan("roles");
      hideSpan("departments");
      hideSpan("specifigusergroup");
      document.forms['addActionStep'].departmentId.options.selectedIndex = 0;
      document.forms['addActionStep'].userGroupId.options.selectedIndex = 0;
    } else if (permissions.options[permissions.selectedIndex].value == '<%= ActionStep.DEPARTMENT %>') {
      hideSpan("specifigusergroup");
      hideSpan("roles");
      showSpan("departments");
      document.forms['addActionStep'].roleId.options.selectedIndex = 0;
      document.forms['addActionStep'].userGroupId.options.selectedIndex = 0;
    } else if (permissions.options[permissions.selectedIndex].value== '<%= ActionStep.SPECIFIC_USER_GROUP %>') {
      showSpan("specifigusergroup");
      hideSpan("roles");
      hideSpan("departments");
      document.forms['addActionStep'].departmentId.options.selectedIndex = 0;
      document.forms['addActionStep'].roleId.options.selectedIndex = 0;
    } else {
      hideSpan("specifigusergroup");
      hideSpan("roles");
      hideSpan("departments");
      document.forms['addActionStep'].roleId.options.selectedIndex = 0;
      document.forms['addActionStep'].departmentId.options.selectedIndex = 0;
      document.forms['addActionStep'].userGroupId.options.selectedIndex = 0;
    }
  }
  
  function updateActionId() {
    if (document.addActionStep.actionId.options[document.addActionStep.actionId.selectedIndex].value == '<%= ActionStep.ATTACH_NOTHING %>' || 
        document.addActionStep.actionId.options[document.addActionStep.actionId.selectedIndex].value == '<%= ActionStep.ADD_RECIPIENT %>') {
      hideSpan("mandatory");
    } else {
      showSpan("mandatory");
    }
    if (document.addActionStep.actionId.options[document.addActionStep.actionId.selectedIndex].value == '<%= ActionStep.ATTACH_FOLDER %>') {
      showSpan("categories");
//      showSpan("fields");
    } else {
      hideSpan("categories");
//      hideSpan("fields");
    }
    if (document.addActionStep.actionId.options[document.addActionStep.actionId.selectedIndex].value == '<%= ActionStep.ATTACH_RELATIONSHIP %>') {
      showSpan("relationships");
      showSpan("accounttypes");
    } else {
      hideSpan("relationships");
      hideSpan("accounttypes");
    }

    if (document.addActionStep.actionId.options[document.addActionStep.actionId.selectedIndex].value == '<%= ActionStep.ADD_RECIPIENT %>') {
      showSpan("campaigns");
      showSpan("duplicateRecipients"); 
    } else {
      hideSpan("campaigns");
      hideSpan("duplicateRecipients");
    }
    
    //alert('We need to list the required custom field categories');
  }
</script>
<form name="addActionStep" action="ActionStepEditor.do?command=SaveStep&planId=<%= actionPlan.getId() %>&auto-populate=true" method="post" onSubmit="return checkForm(this);">
<input type="hidden" name="phaseId" value="<%= actionPhase.getId() %>"/>
<input type="hidden" name="id" value="<%= actionStep.getId() %>"/>
<input type="hidden" name="parentId" value="<%= actionStep.getParentId() %>"/>
<input type="hidden" name="nextStepId" value="<%= nextStepId %>"/>
<input type="hidden" name="enabled" value="true"/>
<input type="hidden" name="previousStepId" value="<%= previousStepId %>"/>
<input type="hidden" name="moduleId" value="<%= request.getAttribute("moduleId") %>"/>
<input type="hidden" name="constantId" value="<%= request.getAttribute("constantId") %>"/>
<dhv:formMessage showSpace="false"/>
<table cellpadding="4" cellspacing="0" width="100%" class="details">
  <tr><th colspan="2"><strong>
  <dhv:evaluate if="<%= actionStep.getId() == -1 %>">
    <dhv:label name="actionPlan.addActionStep">Add Action Step</dhv:label>
  </dhv:evaluate><dhv:evaluate if="<%= actionStep.getId() != -1 %>">
    <dhv:label name="actionPlan.modifyActionStep">Modify Action Step</dhv:label>
  </dhv:evaluate>
  </strong></th></tr>
  <tr class="containerBody">
    <td class="formLabel" valign="top"><dhv:label name="product.description">Description</dhv:label></td>
    <td><input type="text" name="description" id="description" size="58" maxlength="255" value="<%= toHtmlValue(actionStep.getDescription()) %>"/><font color="red">*</font>&nbsp;<%= showAttribute(request, "descriptionError") %></td>
  </tr>
<%--    <% actionIdSelect.setJsEvent("onChange=\"updateActionId();\""); %> --%>
  <tr>
    <td class="formLabel" valign="top"><dhv:label name="actionPlan.requiredAction">Required Action</dhv:label></td>
    <td>
      <table class="empty"><tr>
        <td><%= stepActionSelect.getHtmlSelect("actionId", actionStep.getActionId()) %></td><%-- <%= actionIdSelect.getHtml("actionId", actionStep.getActionId()) %> --%>
        <td norwap><span id="mandatory" name="mandatory" style="display:none">
          <input type="checkbox" name="actionRequired" value="true" <%= actionStep.getActionRequired()?"checked":"" %> /> <dhv:label name="actionPlan.mandatory">Mandatory</dhv:label>
        </span>
       </td>
     </tr></table>
    </td>
  </tr>
  <tr id="categories" >
      <td class="formLabel" valign="top"><dhv:label name="actionPlan.folderToUpdate.text">* Folder to update</dhv:label></td>
      <td><dhv:evaluate if="<%= categoryList != null && categoryList.size() > 0 %>"><%= categoryList.getHtmlSelect("customFieldCategoryId", actionStep.getCustomFieldCategoryId()) %></dhv:evaluate>
      <dhv:evaluate if="<%= categoryList == null || categoryList.size() == 0 %>">&nbsp;</dhv:evaluate></td>
  </tr>
<%--  <tr >
      <td class="formLabel" valign="top"><dhv:label name="communications.campaign.ActiveCampaign">Active Campaign</dhv:label></td>
      <td><dhv:evaluate if="<%= campaignList != null && campaignList.size() > 0 %>"><%= campaignList.getHtmlSelect("campaignId", actionStep.getCampaignId()) %></dhv:evaluate>
  </tr> --%>
    <tr id="campaigns" class="containerBody">
      <td class="formLabel" valign="top">
        <dhv:label name="communications.campaign.ActiveCampaign">Active Campaign</dhv:label>
      </td>
      <td>
        <table cellspacing="0" cellpadding="0" border="0" class="empty">
          <tr>
            <td>
              <div id="changecampaign">
                <dhv:evaluate if="<%= actionStep.getCampaignId() != -1 %>">
                  <%= toHtml(campaignList.getNameById(actionStep.getCampaignId())) %>
                </dhv:evaluate>
                <dhv:evaluate if="<%= actionStep.getCampaignId() == -1 %>">
                  <dhv:label name="accounts.accounts_add.NoneSelected">None Selected</dhv:label>
                </dhv:evaluate>
              </div>
            </td>
            <td valign="top">
              <input type="hidden" name="campaignId" id="campaignId" value="<%= actionStep.getCampaignId() %>"/> &nbsp;
                [<a href="javascript:popCampaignListSingle('campaignId','changecampaign','siteId=<%= actionPlan.getSiteId() %>');"><dhv:label name="accounts.accounts_add.select">Select</dhv:label></a>] &nbsp;
                [<a href="javascript:document.forms['addActionStep'].campaignId.value='-1';javascript:changeDivContent('changecampaign', label('none.selected','None Selected'));"><dhv:label name="button.clear">Clear</dhv:label></a>]
                <%= showAttribute(request,"campaignIdError") %>
            </td>
          </tr>
        </table>
      </td>
    </tr>
    <tr id="duplicateRecipients" class="containerBody">
      <td class="formLabel" valign="top">
        <dhv:label name="actionPlan.actionStep.allowDuplicateRecipients">Allow duplicate recipients</dhv:label>
      </td>
      <td>
        <dhv:checkbox name="allowDuplicateRecipient" value="true" checked="<%= actionStep.getAllowDuplicateRecipient() %>"/>
      </td>
    </tr>
<%--
  <tr id="fields">
      <td class="formLabel" valign="top"><dhv:label name="actionPlan.folderField.text">* Folder Field</dhv:label></td>
      <td>&nbsp;</td>
  </tr>
--%>
  <tr id="relationships">
      <td class="formLabel" valign="top">
        <dhv:label name="actionPlan.targetRelationship">Target Relationship</dhv:label>
      </td>
      <td><%= relationshipTypes.getHtmlSelect().getHtml("targetRelationship", actionStep.getTargetRelationship()) %></td>
  </tr>
  <tr id="accounttypes">
      <td class="formLabel" valign="top">
        <dhv:label name="actionPlan.accountTypes">Account Types</dhv:label>
      </td>
      <td>
        <table border="0" cellspacing="0" cellpadding="0" class="empty">
          <tr>
            <td>
              <select multiple name="selectedList" id="selectedList" size="5">
                 <dhv:lookupHtml listName="typeList" lookupName="accountTypeList"/>
              </select>
            </td>
            <td valign="top">
              [<a href="javascript:popLookupSelectMultiple('selectedList', '', 'lookup_account_types');"><dhv:label name="accounts.accounts_add.select">Select</dhv:label></a>]
            </td>
          </tr>
        </table>
      </td>
  </tr>
  <tr>
    <td class="formLabel" valign="top"><dhv:label name="actionPlan.estimatedStepDuration">Estimated Step Duration</dhv:label></td>
    <td><input type="text" size="5" name="estimatedDuration" value="<%= actionStep.getEstimatedDuration() < 0 ?"":String.valueOf(actionStep.getEstimatedDuration()) %>"/> &nbsp;
    <%= durationType.getHtmlSelect("durationTypeId", actionStep.getDurationTypeId()) %></td>
  </tr>
    <tr>
      <td valign="top" class="formLabel"><dhv:label name="actionPlan.whoCanCompleteThisStep.question">Who can complete this step?</dhv:label></td>
      <td>
        <table class="empty"><tr><td>
          <select name="permissionType" id="permissionType" onChange="updatePermissionType();">
            <option value="<%= ActionStep.ASSIGNED_USER %>" <%= actionStep.getPermissionType() == ActionStep.ASSIGNED_USER ? "selected":""%>><dhv:label name="actionPlan.userAssignedToThePlan.text">User assigned to the plan</dhv:label></option>
            <option value="<%= ActionStep.UP_USER_HIERARCHY %>" <%= actionStep.getPermissionType() == ActionStep.UP_USER_HIERARCHY ? "selected":""%>><dhv:label name="actionPlan.upUserHierarchy.text">User assigned and the user's managers</dhv:label></option>
            <option value="<%= ActionStep.WITHIN_USER_HIERARCHY %>" <%= actionStep.getPermissionType() == ActionStep.WITHIN_USER_HIERARCHY ? "selected":""%>><dhv:label name="actionPlan.assignedUserHierarchy.text">Within the hierarchy of the assigned user</dhv:label></option>
            <option value="<%= ActionStep.MANAGER %>" <%= actionStep.getPermissionType() == ActionStep.MANAGER ? "selected":""%>><dhv:label name="actionPlan.planManager.text">Manager of the plan</dhv:label></option>
            <option value="<%= ActionStep.ASSIGNED_USER_AND_MANAGER %>" <%= actionStep.getPermissionType() == ActionStep.ASSIGNED_USER_AND_MANAGER ? "selected":""%>><dhv:label name="actionPlan.assignedUserAndPlanManager.text">Assigned User and Manager of the plan</dhv:label></option>
            <option value="<%= ActionStep.ROLE %>" <%= actionStep.getPermissionType() == ActionStep.ROLE ? "selected":""%>><dhv:label name="actionPlan.memberOfSpecificRole.text">Member of a specific role</dhv:label></option>
            <option value="<%= ActionStep.DEPARTMENT %>" <%= actionStep.getPermissionType() == ActionStep.DEPARTMENT ? "selected":""%>><dhv:label name="actionPlan.departmentMember.text">Member of a specific department</dhv:label></option>
            <option value="<%= ActionStep.SPECIFIC_USER_GROUP %>" <%= actionStep.getPermissionType() == ActionStep.SPECIFIC_USER_GROUP ? "selected":""%>><dhv:label name="usergroups.specificUserGroup">Member of a specific group</dhv:label></option>
          <dhv:evaluate if="<%= permissionCategory.getConstant() != PermissionCategory.PERMISSION_CAT_ACCOUNTS %>">
            <option value="<%= ActionStep.USER_GROUP %>" <%= actionStep.getPermissionType() == ActionStep.USER_GROUP ? "selected":""%>><dhv:label name="usergroups.userGroup">User Group</dhv:label></option>
          </dhv:evaluate>
            <option value="<%= ActionStep.USER_DELEGATED %>" <%= actionStep.getPermissionType() == ActionStep.USER_DELEGATED ? "selected":""%>><dhv:label name="actionPlan.userDelegated">User delegated</dhv:label></option>
          </select>
        </td><td>
          <table class="empty">
            <tr id="roles"><td><%= roles.getHtmlSelect("roleId", actionStep.getRoleId()) %></td><td><%= showAttribute(request, "roleIdError") %></td></tr>
            <tr id="departments"><td><%= departmentType.getHtmlSelect("departmentId", actionStep.getDepartmentId()) %></td><td><%= showAttribute(request, "departmentIdError") %></td></tr>
            <tr id="specifigusergroup"><td><%= userGroupList.getHtml("userGroupId", actionStep.getUserGroupId()) %></td><td><%= showAttribute(request, "userGroupIdError") %></td></tr>
          </table>
      </td></tr></table>
    </td>
  </tr>
<dhv:evaluate if="<%= !actionPhase.getRandom() && !actionPhase.isGlobal() %>">
  <tr>
    <td class="formLabel" valign="top"><dhv:label name="actionPlan.skipToThisStep">Skip to this step</dhv:label></td>
    <td>
      <input type="checkbox" name="allowSkipToHere" value="true" <%= actionStep.getAllowSkipToHere() ?"checked":"" %> /></td>
  </tr>
</dhv:evaluate>
<dhv:evaluate if="<%= actionPhase.getRandom() %>">
  <input type="hidden" name="allowSkipToHere" value="false"/>
</dhv:evaluate>
<dhv:evaluate if="<%= !actionPhase.isGlobal() %>">
  <%-- Dislay Checkmark net if it is a regular phase --%>
  <tr>
    <td class="formLabel" valign="top"><dhv:label name="actionPlan.allowStepUpdate">Allow user to update step</dhv:label></td>
    <td>
      <input type="checkbox" name="allowUpdate" value="true" <%= (actionStep.getAllowUpdate() ? "checked" : "") %> /></td>
  </tr>
</dhv:evaluate>
</table>
<br />
<input type="submit" value="<dhv:label name="button.save">Save</dhv:label>"/>
<input type="button" value="<dhv:label name="button.cancel">Cancel</dhv:label>" onClick="self.close();"/>
</form>
<br />
<%-- Set the default cursor location in the form --%>
<script type="text/javascript">
  document.addActionStep.description.focus();
  updateActionId();
  updatePermissionType();
</script>
