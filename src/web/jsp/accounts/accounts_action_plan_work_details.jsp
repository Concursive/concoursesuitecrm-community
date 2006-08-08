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
  - Version: $Id: accounts_action_plan_work_details.jsp
  - Description: 
  --%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ page import="java.util.*,org.aspcfs.modules.actionplans.base.*, org.aspcfs.modules.base.Constants"%>
<%@ page import="org.aspcfs.utils.CurrencyFormat,org.aspcfs.modules.troubletickets.base.Ticket" %>
<jsp:useBean id="orgDetails" class="org.aspcfs.modules.accounts.base.Organization" scope="request"/>
<jsp:useBean id="ticket" class="org.aspcfs.modules.troubletickets.base.Ticket" scope="request"/>
<jsp:useBean id="actionPlanWork" class="org.aspcfs.modules.actionplans.base.ActionPlanWork" scope="request"/>
<jsp:useBean id="globalActionPlanWork" class="org.aspcfs.modules.actionplans.base.ActionPlanWork" scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<jsp:useBean id="applicationPrefs" class="org.aspcfs.controller.ApplicationPrefs" scope="application"/>
<script language="JavaScript" TYPE="text/javascript">
  var stepsWithAttachments = new Array();
  <%
    if (ticket == null) {
      ticket = new Ticket();
    }
    Iterator steps = actionPlanWork.getSteps().iterator();
    int count = -1;
    while (steps.hasNext()) {
      count++;
      ActionItemWork thisItem = (ActionItemWork) steps.next();
      if (thisItem.hasAttachment()|| (thisItem.getActionId() == ActionStep.UPDATE_RATING && actionPlanWork.getOrganization().getRating() != -1)) {
  %>
        stepsWithAttachments[<%= count %>] = '<%= thisItem.getId() %>';
    <%}
    }
    steps = globalActionPlanWork.getSteps().iterator();
    while (steps.hasNext()) {
      count++;
      ActionItemWork thisItem = (ActionItemWork) steps.next();
      if (thisItem.hasAttachment() || (thisItem.getActionId() == ActionStep.UPDATE_RATING &&
                                                      actionPlanWork.getOrganization().getRating() != -1)) {
  %>
        stepsWithAttachments[<%= count %>] = '<%= thisItem.getId() %>';
  <%
      }
    }
  %>
  
  function attachContact(itemId) {
    var contactId = document.getElementById('contactid').value;
    stepsWithAttachments[stepsWithAttachments.length + 1] = itemId;
    var url = "AccountActionPlans.do?command=Attach&item=contact&itemId=" + itemId + "&contactId=" + contactId;
    window.frames['server_commands'].location.href = url;
  }

  function attachOpportunity(itemId) {
    var oppId = document.actionPlan.opportunity.value;
    stepsWithAttachments[stepsWithAttachments.length + 1] = itemId;
    window.frames['server_commands'].location.href = "AccountActionPlans.do?command=Attach&item=opportunity&itemId=" + itemId + "&oppId=" + oppId;
  }
  
  function attachFolder(itemId) {
    var recordId = document.getElementById('recordid'+itemId).value;
    stepsWithAttachments[stepsWithAttachments.length + 1] = itemId;
    window.frames['server_commands'].location.href = "AccountActionPlans.do?command=Attach&item=folder&itemId=" + itemId + "&recordId=" + recordId;
  }
  
  function deleteFolder(itemId, categoryId) {
    resetAttachment(itemId);
    window.frames['server_commands'].location.href = "AccountActionPlans.do?command=ResetFolderAttachment&item=folder&itemId=" + itemId + "&categoryId=" + categoryId;
  }
  
  function attachFileItem(itemId) {
    var fileId = document.actionPlan.fileitem.value;
    stepsWithAttachments[stepsWithAttachments.length + 1] = itemId;
    window.frames['server_commands'].location.href = "AccountActionPlans.do?command=Attach&item=fileitem&itemId=" + itemId + "&fileId=" + fileId;
  }
  
  function attachNote(itemId) {
    var noteId = document.actionPlan.note.value;
    stepsWithAttachments[stepsWithAttachments.length + 1] = itemId;
    window.frames['server_commands'].location.href = "AccountActionPlans.do?command=Attach&item=note&itemId=" + itemId + "&noteId=" + noteId;
  }
  
  function attachSelection(itemId) {
    var selectionId = document.actionPlan.selection.value;
    stepsWithAttachments[stepsWithAttachments.length + 1] = itemId;
    window.frames['server_commands'].location.href = "AccountActionPlans.do?command=Attach&item=selection&itemId=" + itemId + "&selectionId=" + selectionId;
  }
  
  function attachRelation(itemId) {
    var relationId = document.actionPlan.relation.value;
    stepsWithAttachments[stepsWithAttachments.length + 1] = itemId;
    window.frames['server_commands'].location.href = "AccountActionPlans.do?command=Attach&item=relation&itemId=" + itemId + "&relationId=" + relationId;
  }
  
  function updateRating(select) {
    var rating = select.options[select.selectedIndex].value;
    var planId = '<%= actionPlanWork.getId() %>';
    if (rating != -1) {
      stepsWithAttachments[stepsWithAttachments.length + 1] = document.actionPlan.ratingItemId.value;
    }
    window.frames['server_commands'].location.href = "AccountActionPlans.do?command=UpdateRating&planId=" + planId + "&rating=" + rating;
  }
  
  function continueAddRecipient(contactId, allowDuplicates, campaignId, itemId) {
    var url = "CampaignManager.do?command=AddRecipient&planWorkId=<%= actionPlanWork.getId() %>&id="+ campaignId +"&contactId="+contactId+'&allowDuplicates='+allowDuplicates+'&actionItemId='+itemId+'&actionSource=ActionPlans';
    window.frames['server_commands'].location.href=url;
  }

  function attachRecipient(itemId) {
    stepsWithAttachments[stepsWithAttachments.length + 1] = itemId;
  }

  function hasAttachment(stepId) {
    for (var i=0; i < stepsWithAttachments.length; ++i) {
      if (stepsWithAttachments[i] == stepId) {
        return true;
      }
    }
    return false;
  }

  function resetAttachment(stepId) {
    for (var i=0; i < stepsWithAttachments.length; ++i) {
      if (stepsWithAttachments[i] == stepId) {
        stepsWithAttachments[i] = '';
        break;
      }
    }
  }
  
  function markComplete(required, actionId, actionPlanId, itemId, nextStepId) {
    if (required == 'true') {
      if (!checkAttachment(actionId, itemId)) {
        return;
      }
    }
    var statusId = '<%= ActionPlanWork.COMPLETED %>';
    window.location.href = "AccountActionPlans.do?command=UpdateStatus&orgId=<%= orgDetails.getOrgId() %>&actionPlanId=" + actionPlanId + "&stepId=" + itemId + "&nextStepId=" + nextStepId + "&statusId=" + statusId+'<%= addLinkParams(request, "popup|actionId") %><%= isPopup(request)?"&popupType=inline":"" %>';
  }
  
  function revertStatus(actionPlanId, itemId, nextStepId) {
    window.location.href = "AccountActionPlans.do?command=RevertStatus&orgId=<%= orgDetails.getOrgId() %>&actionPlanId=" + actionPlanId + "&stepId=" + itemId + "&nextStepId=" + nextStepId + "&statusId=-1<%= addLinkParams(request, "popup|popupType|actionId") %>";
  }
  
  function continueReassignPlan(userId, actionPlanWork) {
    window.location.href = "AccountActionPlans.do?command=Reassign&orgId=<%= orgDetails.getOrgId() %>&actionPlanId=" + actionPlanWork + "&userId=" + userId + "&return=details<%= addLinkParams(request, "popup|popupType|actionId") %>";
  }
  
  function reopen(attachment) {
    window.location.href='AccountActionPlans.do?command=Details&actionPlanId=<%= actionPlanWork.getId() %>&orgId=<%= orgDetails.getOrgId() %>'+attachment+'<%= addLinkParams(request, "popup|popupType|actionId") %>';
  }

  function updateGlobalStatus(required, actionPlanId, actionId, itemId) {
    if (required == 'true') {
      if (!checkAttachment(actionId, itemId)) {
        return;
      }
    }
    window.location.href = "AccountActionPlans.do?command=UpdateGlobalStatus&orgId=<%= orgDetails.getOrgId() %>&actionPlanId=" + actionPlanId + "&stepId=" + itemId + "&statusId=-1<%= addLinkParams(request, "popup|popupType|actionId") %>";
  }
</script>
<%@ include file="../initPage.jsp" %>
<dhv:evaluate if="<%= !isPopup(request) %>">
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="Accounts.do"><dhv:label name="accounts.accounts">Accounts</dhv:label></a> > 
<a href="Accounts.do?command=Search"><dhv:label name="accounts.SearchResults">Search Results</dhv:label></a> >
<a href="Accounts.do?command=Details&orgId=<%= orgDetails.getOrgId() %>"><dhv:label name="accounts.details">Account Details</dhv:label></a> >
<a href="AccountActionPlans.do?command=View&orgId=<%= orgDetails.getOrgId() %>"><dhv:label name="sales.actionPlans">Action Plans</dhv:label></a> >
<dhv:label name="actionPlan.actionPlanDetails">Action Plan Details</dhv:label>
</td>
</tr>
</table>
<%-- End Trails --%>
</dhv:evaluate>
<dhv:formMessage />
<dhv:container name="accounts" selected="actionplans" object="orgDetails" param="<%= "orgId=" + orgDetails.getOrgId() %>" appendToUrl="<%= addLinkParams(request, "popup|popupType|actionId") %>">
  <%@ include file="../troubletickets/troubletickets_actionplan_work_details_include.jsp" %>
<%--  <%@ include file="../actionplans/action_plan_work_details_include.jsp" %> --%>
</dhv:container>
<iframe src="empty.html" name="server_commands" id="server_commands" style="visibility:hidden" height="0"></iframe>
