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
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<jsp:useBean id="ticket" class="org.aspcfs.modules.troubletickets.base.Ticket"
             scope="request"/>
<jsp:useBean id="tickets" class="org.aspcfs.modules.troubletickets.base.Ticket"
             scope="request"/>
<jsp:useBean id="actionPlanWork"
             class="org.aspcfs.modules.actionplans.base.ActionPlanWork"
             scope="request"/>
<jsp:useBean id="globalActionPlanWork"
             class="org.aspcfs.modules.actionplans.base.ActionPlanWork"
             scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean"
             scope="session"/>
<jsp:useBean id="applicationPrefs"
             class="org.aspcfs.controller.ApplicationPrefs"
             scope="application"/>
<script language="JavaScript" TYPE="text/javascript">
var stepsWithAttachments = new Array();
<%
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
  var contactId = document.actionPlan.contact.value;
  stepsWithAttachments[stepsWithAttachments.length + 1] = itemId;
  window.frames['server_commands'].location.href = "TroubleTicketActionPlans.do?command=Attach&item=contact&itemId=" + itemId + "&contactId=" + contactId;
}

function attachOpportunity(itemId) {
  var oppId = document.actionPlan.opportunity.value;
  stepsWithAttachments[stepsWithAttachments.length + 1] = itemId;
  window.frames['server_commands'].location.href = "TroubleTicketActionPlans.do?command=Attach&item=opportunity&itemId=" + itemId + "&oppId=" + oppId;
}

function attachFileItem(itemId) {
  var fileId = document.actionPlan.fileitem.value;
  stepsWithAttachments[stepsWithAttachments.length + 1] = itemId;
  window.frames['server_commands'].location.href = "TroubleTicketActionPlans.do?command=Attach&item=fileitem&itemId=" + itemId + "&fileId=" + fileId;
}

function attachNote(itemId) {
  var noteId = document.actionPlan.note.value;
  stepsWithAttachments[stepsWithAttachments.length + 1] = itemId;
  window.frames['server_commands'].location.href = "TroubleTicketActionPlans.do?command=Attach&item=note&itemId=" + itemId + "&noteId=" + noteId;
}

function attachSelection(itemId) {
  var selectionId = document.actionPlan.selection.value;
  stepsWithAttachments[stepsWithAttachments.length + 1] = itemId;
  window.frames['server_commands'].location.href = "TroubleTicketActionPlans.do?command=Attach&item=selection&itemId=" + itemId + "&selectionId=" + selectionId;
}

function updateRating(select) {
  var rating = select.options[select.selectedIndex].value;
  var planId = '<%= actionPlanWork.getId() %>';
  if (rating != -1) {
    stepsWithAttachments[stepsWithAttachments.length + 1] = document.actionPlan.ratingItemId.value;
  }
  window.frames['server_commands'].location.href = "TroubleTicketActionPlans.do?command=UpdateRating&planId=" + planId + "&rating=" + rating;
}

function continueAddRecipient(contactId, allowDuplicates, campaignId, itemId) {
  var url = "CampaignManager.do?command=AddRecipient&planWorkId=<%= actionPlanWork.getId() %>&id=" + campaignId + "&contactId=" + contactId + '&allowDuplicates=' + allowDuplicates + '&actionItemId=' + itemId + '&actionSource=ActionPlans';
  window.frames['server_commands'].location.href = url;
}

function attachRecipient(itemId) {
  stepsWithAttachments[stepsWithAttachments.length + 1] = itemId;
}

function hasAttachment(stepId) {
  for (var i = 0; i < stepsWithAttachments.length; ++i) {
    if (stepsWithAttachments[i] == stepId) {
      return true;
    }
  }
  return false;
}

function resetAttachment(stepId) {
  for (var i = 0; i < stepsWithAttachments.length; ++i) {
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
  window.location.href = "TroubleTicketActionPlans.do?command=UpdateStatus&ticketId=<%= ticket.getId() %>&actionPlanId=" + actionPlanId + "&stepId=" + itemId + "&nextStepId=" + nextStepId + "&statusId=" + statusId;
}

function revertStatus(actionPlanId, itemId, nextStepId) {
  window.location.href = "TroubleTicketActionPlans.do?command=RevertStatus&ticketId=<%= ticket.getId() %>&actionPlanId=" + actionPlanId + "&stepId=" + itemId + "&nextStepId=" + nextStepId + "&statusId=-1";
}

function continueReassignPlan(userId, actionPlanWork) {
  window.location.href = "TroubleTicketActionPlans.do?command=Reassign&ticketId=<%= ticket.getId() %>&actionPlanId=" + actionPlanWork + "&userId=" + userId + "&return=details";
}

function reopen(addendum) {
  window.location.href = 'TroubleTicketActionPlans.do?command=Details&actionPlanId=<%= actionPlanWork.getId() %>&ticketId=<%= ticket.getId() %>' + addendum;
}

function updateGlobalStatus(required, actionPlanId, actionId, itemId) {
  if (required == 'true') {
    if (!checkAttachment(actionId, itemId)) {
      return;
    }
  }
  window.location.href = "TroubleTicketActionPlans.do?command=UpdateGlobalStatus&ticketId=<%= ticket.getId() %>&actionPlanId=" + actionPlanId + "&stepId=" + itemId + "&statusId=-1";
}
</script>
<%@ include file="../initPage.jsp" %>
<%-- Trails --%>
<table class="trails" cellspacing="0">
  <tr>
    <td>
      <a href="TroubleTickets.do"><dhv:label name="tickets.helpdesk">Help
        Desk</dhv:label></a> >
      <% if (("list".equals((String) request.getParameter("return"))) ||
          ("searchResults".equals((String) request.getParameter("return")))) {%>
      <% if ("yes".equals((String) session.getAttribute("searchTickets"))) {%>
      <a href="TroubleTickets.do?command=SearchTicketsForm"><dhv:label
          name="tickets.searchForm">Search Form</dhv:label></a> >
      <a href="TroubleTickets.do?command=SearchTickets"><dhv:label
          name="accounts.SearchResults">Search Results</dhv:label></a> >
      <%} else {%>
      <a href="TroubleTickets.do?command=Home"><dhv:label name="tickets.view">View
        Tickets</dhv:label></a> >
      <%}%>
      <%} else {%>
      <% if ("yes".equals((String) session.getAttribute("searchTickets"))) {%>
      <a href="TroubleTickets.do?command=SearchTickets"><dhv:label
          name="tickets.search">Search Tickets</dhv:label></a> >
      <%} else {%>
      <a href="TroubleTickets.do?command=Home"><dhv:label name="tickets.view">View
        Tickets</dhv:label></a> >
      <%}%>
      <a href="TroubleTickets.do?command=Details&id=<%= ticket.getId() %>"><dhv:label
          name="tickets.details">Ticket Details</dhv:label></a> >
      <%}%>
      <a href="TroubleTicketActionPlans.do?command=List&ticketId=<%= ticket.getId() %>"><dhv:label
          name="sales.actionPlans">Action Plans</dhv:label></a> >
      <dhv:label name="actionPlan.actionPlanDetails">Action Plan
        Details</dhv:label>
    </td>
  </tr>
</table>
<%-- End Trails --%>
<dhv:formMessage/>
<% String param1 = "id=" + ticket.getId(); %>
<dhv:container name="tickets" selected="actionplans" object="ticket"
               param="<%= param1 %>" hideContainer="<%= isPopup(request) %>">
  <%@ include file="ticket_header_include.jsp" %>
  <dhv:evaluate if="<%= ticket.getClosed() != null %>">
    <font color="red"><dhv:label name="tickets.alert.closed">This ticket has
      been closed:</dhv:label>
      <zeroio:tz timestamp="<%= ticket.getClosed() %>"
                 timeZone="<%= User.getTimeZone() %>" showTimeZone="true"/>
    </font><br/>
  </dhv:evaluate>
  <% Organization orgDetails = new Organization();
    orgDetails.setOrgId(-2);%>
  <%@ include file="troubletickets_actionplan_work_details_include.jsp" %>
</dhv:container>
<iframe src="empty.html" name="server_commands" id="server_commands"
        style="visibility:hidden" height="0"></iframe>
