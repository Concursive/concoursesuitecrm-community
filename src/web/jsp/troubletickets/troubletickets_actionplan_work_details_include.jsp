<jsp:useBean id="constants" class="java.util.HashMap" scope="request"/>
<jsp:useBean id="objectName" class="java.lang.String" scope="request"/>
<jsp:useBean id="moduleName" class="java.lang.String" scope="request"/>
<jsp:useBean id="ratingLookup" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="systemStatus" class="org.aspcfs.controller.SystemStatus" scope="request"/>
<%-- Initialize the drop-down menus --%>
<%@ include file="../initPopupMenu.jsp" %>
<%@ include file="troubletickets_actionplan_work_details_menu.jsp" %>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popURL.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popContacts.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popOpportunities.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popDocuments.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popActionPlans.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popCustomFieldCategory.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/submit.js"></script>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/confirmDelete.js"></SCRIPT>
<script language="JavaScript" TYPE="text/javascript">
  function displayMsg() {
    alert(label("actionstep.update.alert", "You cannot update the status of this step at this time"));
  }
  
  function checkPopURLReturn(required, actionId, planId, itemId) {
    if (required == 'true') {
      if ((actionId == '<%= ActionStep.UPDATE_RATING %>') && (document.actionPlan.rating.options[document.actionPlan.rating.selectedIndex].value == -1)) {
        alert(label("actionstep.rating.alert", "This step requires a valid rating selection"));
      } else if (actionId == '<%= ActionStep.ATTACH_RELATIONSHIP %>' && !hasAttachment(itemId)) {
        alert(label("", "This step requires you to add a relationship"));
      } else if ((actionId != '<%= ActionStep.ATTACH_NOTHING %>') && (actionId != '<%= ActionStep.UPDATE_RATING %>') && (actionId != '<%= ActionStep.VIEW_ACCOUNT %>') && !hasAttachment(itemId)) {
        alert(label("actionstep.attachment.alert", "This step requires an attachment before it can be completed"));
      } else { 
        <dhv:evaluate if="<%= objectName != null && objectName.equals((String)constants.get(new Integer(ActionPlan.TICKETS))) %>">
          <dhv:evaluate if="<%= moduleName != null && moduleName.equals("accountticket") %>">
            popURLReturn('AccountTicketActionPlans.do?command=ModifyStatus&ticketId=<%= ticket.getId() %>&planId=' + planId + '&itemId=' + itemId,'TroubleTicketActionPlans.do?command=Details&ticketId=<%= ticket.getId() %>&actionPlanId=' + planId,'Action_Plan',550,200);
          </dhv:evaluate>
          <dhv:evaluate if="<%= moduleName == null || "".equals(moduleName) %>">
            popURLReturn('TroubleTicketActionPlans.do?command=ModifyStatus&ticketId=<%= ticket.getId() %>&planId=' + planId + '&itemId=' + itemId,'TroubleTicketActionPlans.do?command=Details&ticketId=<%= ticket.getId() %>&actionPlanId=' + planId,'Action_Plan',550,200);
          </dhv:evaluate>
        </dhv:evaluate>
        <dhv:evaluate if="<%= (objectName != null && objectName.equals((String)constants.get(new Integer(ActionPlan.MYHOMEPAGE)))) %>">
          popURLReturn('MyActionPlans.do?command=ModifyStatus&planId=' + planId + '&itemId=' + itemId,'MyActionPlans.do?command=Details&actionPlanId=' + planId,'Action_Plan',550,200);
        </dhv:evaluate>
        <dhv:evaluate if="<%= (objectName != null && objectName.equals((String)constants.get(new Integer(ActionPlan.ACCOUNTS)))) %>">
          popURLReturn('AccountActionPlans.do?command=ModifyStatus&orgId=<%= orgDetails.getOrgId() %>&planId=' + planId + '&itemId=' + itemId,'AccountActionPlans.do?command=Details&orgId=<%= orgDetails.getOrgId() %>&actionPlanId=' + planId,'Action_Plan',550,200);
        </dhv:evaluate>
      }
    } else { 
      <dhv:evaluate if="<%= objectName != null && objectName.equals((String)constants.get(new Integer(ActionPlan.TICKETS))) %>">
        <dhv:evaluate if="<%= moduleName != null && moduleName.equals("accountticket") %>">
          popURLReturn('AccountTicketActionPlans.do?command=ModifyStatus&ticketId=<%= ticket.getId() %>&planId=' + planId + '&itemId=' + itemId,'TroubleTicketActionPlans.do?command=Details&ticketId=<%= ticket.getId() %>&actionPlanId=' + planId,'Action_Plan',550,200);
        </dhv:evaluate>
        <dhv:evaluate if="<%= moduleName == null || "".equals(moduleName) %>">
          popURLReturn('TroubleTicketActionPlans.do?command=ModifyStatus&ticketId=<%= ticket.getId() %>&planId=' + planId + '&itemId=' + itemId,'TroubleTicketActionPlans.do?command=Details&ticketId=<%= ticket.getId() %>&actionPlanId=' + planId,'Action_Plan',550,200);
        </dhv:evaluate>
      </dhv:evaluate>
      <dhv:evaluate if="<%= (objectName != null && objectName.equals((String)constants.get(new Integer(ActionPlan.MYHOMEPAGE)))) %>">
        popURLReturn('MyActionPlans.do?command=ModifyStatus&planId=' + planId + '&itemId=' + itemId,'MyActionPlans.do?command=Details&actionPlanId=' + planId,'Action_Plan',550,200);
      </dhv:evaluate>
      <dhv:evaluate if="<%= (objectName != null && objectName.equals((String)constants.get(new Integer(ActionPlan.ACCOUNTS)))) %>">
        popURLReturn('AccountActionPlans.do?command=ModifyStatus&orgId=<%= orgDetails.getOrgId() %>&planId=' + planId + '&itemId=' + itemId,'AccountActionPlans.do?command=Details&orgId=<%= orgDetails.getOrgId() %>&actionPlanId=' + planId,'Action_Plan',550,200);
      </dhv:evaluate>
    }
  }
  
  function reviewNotes() {
    <dhv:evaluate if="<%= objectName != null && objectName.equals((String)constants.get(new Integer(ActionPlan.TICKETS))) %>">
      <dhv:evaluate if="<%= moduleName != null && moduleName.equals("accountticket") %>">
        popURL('AccountTicketActionPlans.do?command=ViewNotes&ticketId=<%= ticket.getId() %>&planWorkId=<%= actionPlanWork.getId() %>','Action_Plan',700,425,'yes','yes');
      </dhv:evaluate>
      <dhv:evaluate if="<%= moduleName == null || "".equals(moduleName) %>">
        popURL('TroubleTicketActionPlans.do?command=ViewNotes&ticketId=<%= ticket.getId() %>&planWorkId=<%= actionPlanWork.getId() %>','Action_Plan',700,425,'yes','yes');
      </dhv:evaluate>
    </dhv:evaluate>
    <dhv:evaluate if="<%= (objectName != null && objectName.equals((String)constants.get(new Integer(ActionPlan.MYHOMEPAGE)))) %>">
      popURL('MyActionPlans.do?command=ViewNotes&orgId=<%= actionPlanWork.getOrganization() != null ? actionPlanWork.getOrganization().getOrgId() : -1 %>&planWorkId=<%= actionPlanWork.getId() %>','Action_Plan',700,425,'yes','yes');
    </dhv:evaluate>
    <dhv:evaluate if="<%= (objectName != null && objectName.equals((String)constants.get(new Integer(ActionPlan.ACCOUNTS)))) %>">
      popURL('AccountActionPlans.do?command=ViewNotes&orgId=<%= orgDetails.getOrgId() %>&planWorkId=<%= actionPlanWork.getId() %>','Action_Plan',700,425,'yes','yes');
    </dhv:evaluate>
  }
  
  function reassignPlan() {
    popURL('ContactsList.do?command=ContactList&listView=employees&searchcodePermission=sales-leads-edit,tickets-action-plans-view,accounts-action-plans-view,myhomepage-action-plans-view&listType=single&flushtemplist=true&usersOnly=true&siteId=<%= actionPlanWork.getPlanSiteId() %>&hiddensource=reassignplan&actionplan=true&actionPlanWork=<%= actionPlanWork.getId() %>','Action_Plan','700','425','yes','yes');
  }

  function restartPlan() {
    <dhv:evaluate if="<%= objectName != null && objectName.equals((String)constants.get(new Integer(ActionPlan.TICKETS))) %>">
      <dhv:evaluate if="<%= moduleName != null && moduleName.equals("accountticket") %>">
        confirmForward('AccountTicketActionPlans.do?command=Restart&ticketId=<%= ticket.getId() %>&actionPlanId=<%= actionPlanWork.getId() %>');
      </dhv:evaluate>
      <dhv:evaluate if="<%= moduleName == null || "".equals(moduleName) %>">
        confirmForward('TroubleTicketActionPlans.do?command=Restart&ticketId=<%= ticket.getId() %>&actionPlanId=<%= actionPlanWork.getId() %>');
      </dhv:evaluate>
    </dhv:evaluate>
    <dhv:evaluate if="<%= (objectName != null && objectName.equals((String)constants.get(new Integer(ActionPlan.MYHOMEPAGE)))) %>">
      confirmForward('MyActionPlans.do?command=Restart&orgId=<%= actionPlanWork.getOrganization() != null ? actionPlanWork.getOrganization().getOrgId() : -1 %>&actionPlanId=<%= actionPlanWork.getId() %>');
    </dhv:evaluate>
    <dhv:evaluate if="<%= (objectName != null && objectName.equals((String)constants.get(new Integer(ActionPlan.ACCOUNTS)))) %>">
      confirmForward('AccountActionPlans.do?command=Restart&orgId=<%= orgDetails.getOrgId() %>&actionPlanId=<%= actionPlanWork.getId() %>');
    </dhv:evaluate>
  }
  
  function deletePlan() {
    <dhv:evaluate if="<%= objectName != null && objectName.equals((String)constants.get(new Integer(ActionPlan.TICKETS))) %>">
      <dhv:evaluate if="<%= moduleName != null && moduleName.equals("accountticket") %>">
        confirmDelete('AccountTicketActionPlans.do?command=Delete&ticketId=<%= ticket.getId() %>&planId=<%= actionPlanWork.getId() %>');
      </dhv:evaluate>
      <dhv:evaluate if="<%= moduleName == null || "".equals(moduleName) %>">
        confirmDelete('TroubleTicketActionPlans.do?command=Delete&ticketId=<%= ticket.getId() %>&planId=<%= actionPlanWork.getId() %>');
      </dhv:evaluate>
    </dhv:evaluate>
    <dhv:evaluate if="<%= (objectName != null && objectName.equals((String)constants.get(new Integer(ActionPlan.MYHOMEPAGE)))) %>">
      confirmForward('MyActionPlans.do?command=Delete&orgId=<%= actionPlanWork.getOrganization() != null ? actionPlanWork.getOrganization().getOrgId() : -1 %>&actionPlanId=<%= actionPlanWork.getId() %>');
    </dhv:evaluate>
    <dhv:evaluate if="<%= (objectName != null && objectName.equals((String)constants.get(new Integer(ActionPlan.ACCOUNTS)))) %>">
      confirmDelete('AccountActionPlans.do?command=Delete&orgId=<%= orgDetails.getOrgId() %>&actionPlanId=<%= actionPlanWork.getId() %>');
    </dhv:evaluate>
  }
  
  function checkAttachment(actionId, itemId) {
    if ((actionId == '<%= ActionStep.UPDATE_RATING %>') &&
          (document.actionPlan.rating.options[document.actionPlan.rating.selectedIndex].value == -1)) {
      alert(label("actionstep.rating.alert", "This step requires a valid rating selection"));
      return false;
    } else if (actionId == '<%= ActionStep.ATTACH_RELATIONSHIP %>' && !hasAttachment(itemId)) {
      alert(label("actionstep.relationship.alert", "This step requires you to add a relationship"));
      return false;
    } else if ((actionId != '<%= ActionStep.ATTACH_NOTHING %>') && (actionId != '<%= ActionStep.UPDATE_RATING %>') 
                        && (actionId != '<%= ActionStep.VIEW_ACCOUNT %>') && !hasAttachment(itemId)) {
      alert(label("actionstep.attachment.alert", "This step requires an attachment before it can be completed"));
      return false;
    }
    return true;
  }
  <%-- Preload image rollovers for drop-down menu --%>
  loadImages('select');
</script>
<form name="actionPlan">
<%-- Plan Header --%>
<dhv:evaluate if="<%= (objectName != null && objectName.equals((String)constants.get(new Integer(ActionPlan.ACCOUNTS)))) %>">
<table width="100%" border="0">
  <%-- Contact --%>
  <dhv:evaluate if="<%= actionPlanWork.getContact() != null %>">
    <dhv:evaluate if="<%= hasText(actionPlanWork.getContact().getCompany()) %>">
      <tr>
        <td><%= toHtml(actionPlanWork.getContact().getCompany()) %></td>
      </tr>
    </dhv:evaluate>
    <dhv:evaluate if="<%= hasText(actionPlanWork.getContact().getNameFirstLast()) %>">
      <tr>
        <td><%= toHtml(actionPlanWork.getContact().getNameFirstLast()) %></td>
      </tr>
    </dhv:evaluate>
  </dhv:evaluate>
  <%-- Account --%>
  <dhv:evaluate if="<%= actionPlanWork.getOrganization() != null %>">
    <dhv:evaluate if="<%= hasText(actionPlanWork.getOrganization().getName()) %>">
      <tr>
        <td><%= toHtml(actionPlanWork.getOrganization().getName()) %></td>
      </tr>
    </dhv:evaluate>
  </dhv:evaluate>
</table>
</dhv:evaluate>
<%@ include file="../actionplans/action_plan_work_header_include.jsp" %>
<%--
<dhv:evaluate if="<%= User.getUserRecord().getId() == actionPlanWork.getManagerId() %>">
  <input type="button" value="<dhv:label name="actionPlan.reassign">Reassign</dhv:label>" onClick="javascript:reassignPlan();"/>
  <input type="button" value="<dhv:label name="actionPlan.restart">Restart</dhv:label>" onClick="javascript:restartPlan();"/>
  <input type="button" value="<dhv:label name="button.delete">Delete</dhv:label>" onClick="javascript:deletePlan();"/><br /><br />
</dhv:evaluate>
--%>
<table cellpadding="0" cellspacing="0" width="100%" border="0" class="progressTable">
  <tr>
    <th>&nbsp;</th>
    <th>&nbsp;</th>
    <th style="border-bottom: 1px solid #9C9A9C;" width="40%" nowrap><dhv:label name="admin.step">Step</dhv:label></th>
    <th style="border-bottom: 1px solid #9C9A9C;" width="25%" nowrap><strong>Action</strong></th>
    <th style="border-bottom: 1px solid #9C9A9C;" nowrap><strong><dhv:label name="reports.owner">Owner</dhv:label></strong></th>
  </tr>
  <%
    Iterator j = actionPlanWork.getPhaseWorkList().iterator();
    Iterator nextPhaseIter = actionPlanWork.getPhaseWorkList().iterator();
    if (nextPhaseIter.hasNext()) {
      ActionPhaseWork temp = (ActionPhaseWork) nextPhaseIter.next();
    }
    if ( j.hasNext() ) {
      int rowid = 0;
      int i =0;
      while (j.hasNext()) {
        i++;
        rowid = (rowid != 1 ? 1 : 2);
        ActionPhaseWork thisPhaseWork = (ActionPhaseWork) j.next();
        ActionPhaseWork nextPhaseWork = null;
        if (nextPhaseIter.hasNext()) {
          nextPhaseWork = (ActionPhaseWork) nextPhaseIter.next();
        }
        Iterator m = thisPhaseWork.getItemWorkList().iterator();
        int n = 0;
        while (m.hasNext()) {
          n++;
          ActionItemWork thisItemWork = (ActionItemWork) m.next();
  %>
          <tr>
            <% if (n == 1) { %>
            <%-- Action Plan Stage --%>
            <td rowspan="<%= thisPhaseWork.getItemWorkList().size() %>" nowrap
            <%
              if (thisPhaseWork.allStepsComplete()) {
            %>
                class="phaseComplete"
            <%
                } else if (thisPhaseWork.getActionPhaseId() == actionPlanWork.getCurrentPhaseId()) {
            %>
                class="phaseCurrent"
            <%
                } else {
            %>
                class="phaseIncomplete"
            <%
                }
            %>
            <dhv:evaluate if="<%= !j.hasNext() %>">
              style="border-bottom: 1px solid #000000;"
            </dhv:evaluate>
            >
              <strong><%= toHtml(thisPhaseWork.getPhaseName()) %></strong>
              <dhv:evaluate if="<%= hasText(thisPhaseWork.getPhaseDescription()) %>">
                <br />
                <%= toHtml(thisPhaseWork.getPhaseDescription()) %>
              </dhv:evaluate>
              <dhv:evaluate if="<%= thisPhaseWork.getStartDate() != null %>">
                <br />
                (<%= thisPhaseWork.getDaysInPhase() %>
                <dhv:evaluate if="<%= thisPhaseWork.getDaysInPhase() == 1 %>"><dhv:label name="actionPlan.day">day</dhv:label>)</dhv:evaluate>
                <dhv:evaluate if="<%= (thisPhaseWork.getDaysInPhase() == 0) || (thisPhaseWork.getDaysInPhase() > 1) %>"><dhv:label name="actionPlan.days">days</dhv:label>)</dhv:evaluate>
              </dhv:evaluate>
            </td>
            <% } %>
            <%-- Action Plan Step --%>
            <td nowrap valign="top"
                    <dhv:evaluate if="<%= thisPhaseWork.getPhase().getRandom() && thisPhaseWork.getPhase().getId() == actionPlanWork.getCurrentPhaseId() %>">
                      class="phaseStepRandomImage"
                    </dhv:evaluate>
                    <dhv:evaluate if="<%= thisPhaseWork.getPhase().getRandom() && thisPhaseWork.getPhase().getId() != actionPlanWork.getCurrentPhaseId() %>">
                      class="phaseStepNoneImage"
                    </dhv:evaluate>
                    <dhv:evaluate if="<%= !thisPhaseWork.getPhase().getRandom() && !thisItemWork.isCurrent() %>">
                      class="phaseStepNoneImage"
                    </dhv:evaluate>
                    <dhv:evaluate if="<%= !thisPhaseWork.getPhase().getRandom() && thisItemWork.isCurrent() %>">
                      class="phaseStepCurrentImage"
                    </dhv:evaluate>
                  >&nbsp;&nbsp;
            </td>
            <td valign="top"
                    <dhv:evaluate if="<%= thisPhaseWork.getPhase().getRandom() && thisPhaseWork.getPhase().getId() == actionPlanWork.getCurrentPhaseId() %>">
                      class="phaseStepRandom"
                    </dhv:evaluate>
                    <dhv:evaluate if="<%= thisPhaseWork.getPhase().getRandom() && thisPhaseWork.getPhase().getId() != actionPlanWork.getCurrentPhaseId() %>">
                      class="phaseStepNone"
                    </dhv:evaluate>
                    <dhv:evaluate if="<%= !thisPhaseWork.getPhase().getRandom() && !thisItemWork.isCurrent() %>">
                      class="phaseStepNone"
                    </dhv:evaluate>
                    <dhv:evaluate if="<%= !thisPhaseWork.getPhase().getRandom() && thisItemWork.isCurrent() %>">
                      class="phaseStepCurrent"
                    </dhv:evaluate>
                  >&nbsp;&nbsp;
                  <dhv:evaluate if="<%= thisItemWork.getAllowUpdate() %>">
                    <%--This step allows user to update --%>
                    <dhv:evaluate if="<%= thisPhaseWork.getPhase().getRandom() %>">
                      <%-- Random Steps --%>
                      <dhv:evaluate if="<%= thisItemWork.userHasPermission(User.getUserRecord().getId(), request) %>">
                        <%-- User has permission to complete this step --%>
                        <dhv:evaluate if="<%= thisPhaseWork.isCurrent() %>">
                          <dhv:evaluate if="<%= !thisItemWork.hasStatus() %>">
                            <a class="rollover" href="javascript:markComplete('<%= thisItemWork.getActionRequired() %>', '<%= thisItemWork.getActionId() %>','<%= actionPlanWork.getId() %>', '<%= thisItemWork.getId() %>', '-1');"><%= thisItemWork.getStatusGraphicTag(systemStatus) %></a>
                            <a class="phaseCurrentLink" href="javascript:markComplete('<%= thisItemWork.getActionRequired() %>', '<%= thisItemWork.getActionId() %>','<%= actionPlanWork.getId() %>', '<%= thisItemWork.getId() %>', '-1');"><%= toHtml(thisItemWork.getStepDescription()) %></a>
                          </dhv:evaluate>
                          <dhv:evaluate if="<%= thisItemWork.hasStatus() %>">
                            <a class="rollover" href="javascript:revertStatus('<%= actionPlanWork.getId() %>', '<%= thisItemWork.getId() %>', '-1');"><%= thisItemWork.getStatusGraphicTag(systemStatus) %></a>
                            <a class="phaseCurrentLink" href="javascript:revertStatus('<%= actionPlanWork.getId() %>', '<%= thisItemWork.getId() %>', '-1');"><%= toHtml(thisItemWork.getStepDescription()) %></a>
                          </dhv:evaluate>
                        </dhv:evaluate>
                        <dhv:evaluate if="<%= !thisPhaseWork.isCurrent() %>">
                            <%-- This should read.. thisPhaseWork.getNextPhase().isCurrent() && thisPhaseWork.getNextPhase().noStepsComplete() --%>
                          <dhv:evaluate if="<%= (nextPhaseWork != null && nextPhaseWork.isCurrent() && nextPhaseWork.noStepComplete() && thisItemWork.hasStatus()) || (nextPhaseWork == null && thisItemWork.hasStatus()) %>">
                            <a class="rollover" href="javascript:revertStatus('<%= actionPlanWork.getId() %>', '<%= thisItemWork.getId() %>', '-1');"><%= thisItemWork.getStatusGraphicTag(systemStatus) %></a>
                            <a class="rollover" href="javascript:revertStatus('<%= actionPlanWork.getId() %>', '<%= thisItemWork.getId() %>', '-1');"><%= toHtml(thisItemWork.getStepDescription()) %></a>
                          </dhv:evaluate>
                            <%-- The else should execute displayMsg() --%>
                          <dhv:evaluate if="<%= nextPhaseWork != null && (!nextPhaseWork.isCurrent() || !nextPhaseWork.noStepComplete() || !thisItemWork.hasStatus()) || (nextPhaseWork == null && !thisItemWork.hasStatus()) %>">
                            <a class="rollover" href="javascript:displayMsg();"><%= thisItemWork.getStatusGraphicTag(systemStatus) %></a>
                            <a class="rollover" href="javascript:displayMsg();"><%= toHtml(thisItemWork.getStepDescription()) %></a>
                          </dhv:evaluate>
                        </dhv:evaluate>
                      </dhv:evaluate>
                      <dhv:evaluate if="<%= !thisItemWork.userHasPermission(User.getUserRecord().getId(), request) %>">
                        <%-- User does not have permission to compete this step --%>
                        <dhv:evaluate if="<%= thisItemWork.hasStatus() %>">
                          <%-- The step has a valid status --%>
                          <%= thisItemWork.getStatusGraphicTag(systemStatus) %>
                          <%= toHtml(thisItemWork.getStepDescription()) %>
                        </dhv:evaluate>
                        <dhv:evaluate if="<%= !thisItemWork.hasStatus() %>">
                          <%-- The step DOES NOT have a valid status --%>
                          <%= thisItemWork.getStatusGraphicTag(systemStatus) %>
                          <%= toHtml(thisItemWork.getStepDescription()) %>
                        </dhv:evaluate>
                      </dhv:evaluate>
                    </dhv:evaluate>
                    <dhv:evaluate if="<%= !thisPhaseWork.getPhase().getRandom() %>">
                      <%-- Sequential Steps --%>
                      <dhv:evaluate if="<%= thisItemWork.userHasPermission(User.getUserRecord().getId(), request) %>">
                        <%-- The user logged-in is the steps owner --%>
                        <dhv:evaluate if="<%= thisItemWork.isCurrent() %>">
                          <%-- This is the current step in the Action Plan --%>
                          <dhv:evaluate if="<%= thisItemWork.getHasNext() %>">
                            <%-- There exists a next step --%>
                            <dhv:evaluate if="<%= thisItemWork.getNextStep().userHasPermission(User.getUserRecord().getId(), request) %>">
                              <%-- The Next step's owner is the current step's owner  --%>
                              <a class="rollover" href="javascript:markComplete('<%= thisItemWork.getActionRequired() %>', '<%= thisItemWork.getActionId() %>','<%= actionPlanWork.getId() %>', '<%= thisItemWork.getId() %>', '<%= thisItemWork.getNextStep().getId() %>');"><%= thisItemWork.getStatusGraphicTag(systemStatus) %></a>
                              <a class="phaseCurrentLink" href="javascript:markComplete('<%= thisItemWork.getActionRequired() %>', '<%= thisItemWork.getActionId() %>','<%= actionPlanWork.getId() %>', '<%= thisItemWork.getId() %>', '<%= thisItemWork.getNextStep().getId() %>');"><%= toHtml(thisItemWork.getStepDescription()) %></a>
                            </dhv:evaluate>
                            <dhv:evaluate if="<%= !thisItemWork.getNextStep().userHasPermission(User.getUserRecord().getId(), request) %>">
                              <%-- The Next step's owner is NOT the current step's owner  --%>
                              <a class="rollover" href="javascript:checkPopURLReturn('<%= thisItemWork.getActionRequired() %>', '<%= thisItemWork.getActionId() %>','<%= actionPlanWork.getId() %>','<%= thisItemWork.getId() %>');"><%= thisItemWork.getStatusGraphicTag(systemStatus) %></a>
                              <a class="phaseCurrentLink" href="javascript:checkPopURLReturn('<%= thisItemWork.getActionRequired() %>', '<%= thisItemWork.getActionId() %>','<%= actionPlanWork.getId() %>','<%= thisItemWork.getId() %>');"><%= toHtml(thisItemWork.getStepDescription()) %></a>
                            </dhv:evaluate>
                          </dhv:evaluate>
                          <dhv:evaluate if="<%= !thisItemWork.getHasNext() && !thisItemWork.hasStatus() %>">
                            <%-- This is the last step in the plan and has no status yet --%>
                            <a class="rollover" href="javascript:markComplete('<%= thisItemWork.getActionRequired() %>', '<%= thisItemWork.getActionId() %>','<%= actionPlanWork.getId() %>', '<%= thisItemWork.getId() %>', '-1');"><%= thisItemWork.getStatusGraphicTag(systemStatus) %></a>
                            <a class="phaseCurrentLink" href="javascript:markComplete('<%= thisItemWork.getActionRequired() %>', '<%= thisItemWork.getActionId() %>','<%= actionPlanWork.getId() %>', '<%= thisItemWork.getId() %>', '-1');"><%= toHtml(thisItemWork.getStepDescription()) %></a>
                          </dhv:evaluate>
                          <dhv:evaluate if="<%= !thisItemWork.getHasNext() && thisItemWork.hasStatus() %>">
                            <%-- This is the last step in the plan and has a status --%>
                            <a class="rollover" href="javascript:displayMsg();"><%= thisItemWork.getStatusGraphicTag(systemStatus) %></a>
                            <a class="phaseCurrentLink" href="javascript:displayMsg();"><%= toHtml(thisItemWork.getStepDescription()) %></a>
                          </dhv:evaluate>
                        </dhv:evaluate>
                        <dhv:evaluate if="<%= !thisItemWork.isCurrent() %>">
                          <%-- This is NOT the current step in the Action Plan --%>
                          <dhv:evaluate if="<%= !thisItemWork.isPrevious() %>">
                            <%-- This is NOT the previous step in the Action Plan --%>
                            <dhv:evaluate if="<%= thisItemWork.getAllowSkipToHere() && !thisItemWork.hasStatus() %>">
                              <%-- This Step allows skipping here from the current step --%>
                              <a class="rollover" href="javascript:checkPopURLReturn('<%= thisItemWork.getActionRequired() %>', '<%= thisItemWork.getActionId() %>','<%= actionPlanWork.getId() %>','<%= thisItemWork.getId() %>');"><%= thisItemWork.getStatusGraphicTag(systemStatus) %></a>
                              <a class="rollover" href="javascript:checkPopURLReturn('<%= thisItemWork.getActionRequired() %>', '<%= thisItemWork.getActionId() %>','<%= actionPlanWork.getId() %>','<%= thisItemWork.getId() %>');"><%= toHtml(thisItemWork.getStepDescription()) %></a>
                            </dhv:evaluate>
                            <dhv:evaluate if="<%= thisItemWork.getAllowSkipToHere() && thisItemWork.hasStatus() %>">
                              <%-- This Step allows skipping but the status has already been updated --%>
                              <a class="rollover" href="javascript:displayMsg();"><%= thisItemWork.getStatusGraphicTag(systemStatus) %></a>
                              <a class="rollover" href="javascript:displayMsg();"><%= toHtml(thisItemWork.getStepDescription()) %></a>
                            </dhv:evaluate>
                            <dhv:evaluate if="<%= !thisItemWork.getAllowSkipToHere() %>">
                              <%-- This Step DOES NOT allow skipping here from the current step --%>
                              <a class="rollover" href="javascript:displayMsg();"><%= thisItemWork.getStatusGraphicTag(systemStatus) %></a>
                              <a class="rollover" href="javascript:displayMsg();"><%= toHtml(thisItemWork.getStepDescription()) %></a>
                            </dhv:evaluate>
                          </dhv:evaluate>
                          <dhv:evaluate if="<%= thisItemWork.isPrevious() %>">
                            <%-- This is the previous step in the Action Plan (could be the last step too!) --%>
                              <dhv:evaluate if="<%= thisItemWork.userHasPermission(User.getUserRecord().getId(), request) %>">
                                <%-- The Previous step's owner is the current step's owner  --%>
                                <a class="rollover" href="javascript:revertStatus('<%= actionPlanWork.getId() %>', '<%= thisItemWork.getId() %>', '<%= thisItemWork.getHasNext() ? thisItemWork.getNextStep().getId() : -1 %>');"><%= thisItemWork.getStatusGraphicTag(systemStatus) %></a>
                                <a class="rollover" href="javascript:revertStatus('<%= actionPlanWork.getId() %>', '<%= thisItemWork.getId() %>', '<%= thisItemWork.getHasNext() ? thisItemWork.getNextStep().getId() : -1 %>');"><%= toHtml(thisItemWork.getStepDescription()) %></a>
                              </dhv:evaluate>
                              <dhv:evaluate if="<%= ! thisItemWork.userHasPermission(User.getUserRecord().getId(), request) %>">
                                <%-- The Previous step's owner is NOT the current step's owner  --%>
                                <a class="rollover" href="javascript:displayMsg();"><%= thisItemWork.getStatusGraphicTag(systemStatus) %></a>
                                <a class="rollover" href="javascript:displayMsg();"><%= toHtml(thisItemWork.getStepDescription()) %></a>
                              </dhv:evaluate>
                          </dhv:evaluate>
                        </dhv:evaluate>
                      </dhv:evaluate>
                      <%-- The user logged-in is NOT the steps owner --%>
                      <dhv:evaluate if="<%= ! thisItemWork.userHasPermission(User.getUserRecord().getId(), request) %>">
                        <dhv:evaluate if="<%= thisItemWork.hasStatus() %>">
                          <%-- The step has a valid status --%>
                          <%= thisItemWork.getStatusGraphicTag(systemStatus) %>
                          <%= toHtml(thisItemWork.getStepDescription()) %>
                        </dhv:evaluate>
                        <dhv:evaluate if="<%= !thisItemWork.hasStatus() %>">
                          <%-- The step DOES NOT have a valid status --%>
                          <%= thisItemWork.getStatusGraphicTag(systemStatus) %>
                          <%= toHtml(thisItemWork.getStepDescription()) %>
                        </dhv:evaluate>
                      </dhv:evaluate>
                    </dhv:evaluate>
                  </dhv:evaluate>
                  <dhv:evaluate if="<%= !thisItemWork.getAllowUpdate() %>">
                    <%-- This step does not allow user to update --%>
                    <%= toHtml(thisItemWork.getStepDescription()) %>
                  </dhv:evaluate>
            </td>
            <%-- Attachments --%>
            <td valign="top" align="center" nowrap
                <dhv:evaluate if="<%= !thisPhaseWork.getPhase().getRandom() && thisItemWork.isCurrent() %>">
                class="phaseStepHighlight"
                </dhv:evaluate>
                <dhv:evaluate if="<%= thisPhaseWork.getPhase().getRandom() && thisPhaseWork.getPhase().getId() == actionPlanWork.getCurrentPhaseId() %>">
                class="phaseStepHighlight"
                </dhv:evaluate>
                width="30%">
                <%@ include file="../actionplans/action_plan_work_details_attachments_include.jsp" %>
<%--

              <%-- No Attachment --% >
              <dhv:evaluate if="<%= (thisItemWork.getActionId() == ActionStep.ATTACH_NOTHING) %>">
                &nbsp;
              </dhv:evaluate>
              <%-- Account Contacts --% >
              <dhv:evaluate if="<%= (thisItemWork.getActionId() == ActionStep.ATTACH_ACCOUNT_CONTACT) %>">
                <table class="empty" border="0">
                  <tr>
                    <td><img border="0" src="images/icons/stock_bcard-16.gif" align="absmiddle" /></td>
                    <dhv:evaluate if="<%= thisItemWork.getContact() != null %>">
                      <td valign="middle"><a href="javascript:popContactsListSingle('contactid','changecontact<%= thisItemWork.getId() %>','orgId=<%= actionPlanWork.getOrganization().getOrgId() %>&siteId=<%= actionPlanWork.getOrganization().getSiteId() %>&listView=accountcontacts&reset=true&addNewContact=true&hiddensource=attachplan&actionplan=true&actionStepWork=<%= thisItemWork.getId() %>');"><div id="changecontact<%= thisItemWork.getId() %>"><%= toHtml(thisItemWork.getContact().getNameLastFirst()) %></div></a></td>
                    </dhv:evaluate>
                    <dhv:evaluate if="<%= thisItemWork.getContact() == null %>">
                      <td valign="middle">
                        <a href="javascript:popContactsListSingle('contactid','changecontact<%= thisItemWork.getId() %>','orgId=<%= actionPlanWork.getOrganization().getOrgId() %>&siteId=<%= actionPlanWork.getOrganization().getSiteId() %>&listView=accountcontacts&reset=true&addNewContact=true&hiddensource=attachplan&actionplan=true&actionStepWork=<%= thisItemWork.getId() %>');">
                          <div id="changecontact<%= thisItemWork.getId() %>">
                            <%= thisItemWork.getLabel() != null ? thisItemWork.getLabel() : "<dhv:label name=\"actionPlan.attachContact\">Attach Contact</dhv:label>" %>
                          </div>
                        </a>
                      </td>
                    </dhv:evaluate>
                  </tr>
                </table>
              </dhv:evaluate>

              <%-- Opportunity --% >
              <dhv:evaluate if="<%= (thisItemWork.getActionId() == ActionStep.ATTACH_OPPORTUNITY) %>">
                <table class="empty" border="0">
                  <tr>
                    <td><img border="0" src="images/icons/stock_form-currency-field-16.gif" align="absmiddle" /></td>
                    <dhv:evaluate if="<%= thisItemWork.getComponent() != null %>">
                      <td valign="middle"><a href="javascript:popOppForm('opportunityid','changeopportunity<%= thisItemWork.getId() %>','<%= thisItemWork.getComponent().getHeaderId() %>','<%= thisItemWork.getComponent().getId() %>','orgId=<%= actionPlanWork.getOrganization().getOrgId() %>&actionplan=true&actionStepWork=<%= thisItemWork.getId() %>');"><div id="changeopportunity<%= thisItemWork.getId() %>"><%= toHtml(CurrencyFormat.getCurrencyString(thisItemWork.getComponent().getGuess(), User.getLocale(), applicationPrefs.get("SYSTEM.CURRENCY")) + " " + NumberFormat.getPercentInstance().format(thisItemWork.getComponent().getCloseProb())) %></div></a></td>
                    </dhv:evaluate>
                    <dhv:evaluate if="<%= thisItemWork.getComponent() == null %>">
                      <td valign="middle"><a href="javascript:popOppForm('opportunityid','changeopportunity<%= thisItemWork.getId() %>','-1','-1','orgId=<%= actionPlanWork.getOrganization().getOrgId() %>&actionplan=true&source=attachplan&actionStepWork=<%= thisItemWork.getId() %>');"><div id="changeopportunity<%= thisItemWork.getId() %>"><%= thisItemWork.getLabel() != null ? thisItemWork.getLabel() : "<dhv:label name=\"actionPlans.attachOpportunity.text\">Attach Opportunity</dhv:label>" %></div></a></td>
                    </dhv:evaluate>
                  </tr>
                </table>
              </dhv:evaluate>

              <%-- Document -- %>
              <dhv:evaluate if="<%= (thisItemWork.getActionId() == ActionStep.ATTACH_DOCUMENT) %>">
                <table class="empty" border="0">
                  <tr>
                    <td><img border="0" src="images/icons/stock_new_bullet-16.gif" align="absmiddle" /></td>
                    <dhv:evaluate if="<%= thisItemWork.getFileItem() != null %>">
                      <td valign="middle">
                        <a href="javascript:popAccountFileItemList('fileitemid','changefileitem<%= thisItemWork.getId() %>','orgId=<%= actionPlanWork.getOrganization().getOrgId() %>&addNewFile=true&source=attachplan&actionplan=true&actionStepWork=<%= thisItemWork.getId() %>');">
                          <div id="changefileitem<%= thisItemWork.getId() %>">
                            <%= toHtml(thisItemWork.getFileItem().getSubject()) %>
                          </div>
                        </a>
                      </td>
                    </dhv:evaluate>
                    <dhv:evaluate if="<%= thisItemWork.getFileItem() == null %>">
                      <td valign="middle">
                        <a href="javascript:popAccountFileItemList('fileitemid','changefileitem<%= thisItemWork.getId() %>','orgId=<%= actionPlanWork.getOrganization().getOrgId() %>&addNewFile=true&source=attachplan&actionplan=true&actionStepWork=<%= thisItemWork.getId() %>');">
                          <div id="changefileitem<%= thisItemWork.getId() %>">
                            <%= thisItemWork.getLabel() != null ? thisItemWork.getLabel() : "<dhv:label name=\"actionPlans.attachDocument.text\">Attach Document</dhv:label>" %>
                          </div>
                        </a>
                      </td>
                    </dhv:evaluate>
                  </tr>
                </table>
              </dhv:evaluate>
              
              <%-- Update Rating -- %>
              <dhv:evaluate if="<%= (thisItemWork.getActionId() == ActionStep.UPDATE_RATING) %>">
                <dhv:evaluate if="<%= thisItemWork.userHasPermission(User.getUserRecord().getId()) %>">
                  <%-- The user logged-in is the steps owner -- %>
                  <%= ratingLookup.getHtmlSelect("rating", actionPlanWork.getOrganization().getRating()) %>
                  <input type="hidden" name="ratingItemId" value=<%= thisItemWork.getId() %>/>
                </dhv:evaluate>
                <dhv:evaluate if="<%= !thisItemWork.userHasPermission(User.getUserRecord().getId()) %>">
                  <%-- The user logged-in is NOT the steps owner, so disable the lookup -- %>
                  <%= ratingLookup.getHtmlSelect("rating", actionPlanWork.getOrganization().getRating(), true) %>
                </dhv:evaluate>
             </dhv:evaluate>
             
             <%-- Single Note (Date oriented) - -%>
             <dhv:evaluate if="<%= thisItemWork.getActionId() == ActionStep.ATTACH_NOTE_SINGLE %>">
                <table class="empty" border="0">
                  <tr>
                    <td><img border="0" src="images/icons/stock_form-date-field-16.gif" align="absmiddle" /></td>
                    <dhv:evaluate if="<%= thisItemWork.getNote() != null %>">
                      <td valign="middle"><a href="javascript:popActionPlanAttachment('note', 'noteid','changenote<%= thisItemWork.getId() %>','orgId=<%= actionPlanWork.getOrganization().getOrgId() %>&source=attachnote&actionStepWork=<%= thisItemWork.getId() %>');"><div id="changenote<%= thisItemWork.getId() %>"><zeroio:tz timestamp="<%=  thisItemWork.getNote().getSubmitted() %>" timeZone="<%= User.getUserRecord().getTimeZone() %>" dateOnly="true"/></div></a></td>
                    </dhv:evaluate>
                    <dhv:evaluate if="<%= thisItemWork.getNote() == null %>">
                      <td valign="middle"><a href="javascript:popActionPlanAttachment('note', 'noteid','changenote<%= thisItemWork.getId() %>','orgId=<%= actionPlanWork.getOrganization().getOrgId() %>&source=attachnote&actionStepWork=<%= thisItemWork.getId() %>');"><div id="changenote<%= thisItemWork.getId() %>"><%= thisItemWork.getLabel() != null ? thisItemWork.getLabel() : "<dhv:label name=\"actionPlans.attachDate.text\">Attach Date</dhv:label>" %></div></a></td>
                    </dhv:evaluate>
                  </tr>
                </table>
             </dhv:evaluate>
             
             <%-- Multiple Notes --% >
             <dhv:evaluate if="<%= thisItemWork.getActionId() == ActionStep.ATTACH_NOTE_MULTIPLE %>">
                <table class="empty" border="0">
                  <tr>
                    <td><img border="0" src="images/icons/stock_insert-note-16.gif" align="absmiddle" /></td>
                    <dhv:evaluate if="<%= thisItemWork.getNote() != null %>">
                      <td valign="middle"><a href="javascript:popActionPlanAttachment('note', 'noteid','changenote<%= thisItemWork.getId() %>','orgId=<%= actionPlanWork.getOrganization().getOrgId() %>&source=attachnote&actionStepWork=<%= thisItemWork.getId() %>');"><div id="changenote<%= thisItemWork.getId() %>"><zeroio:tz timestamp="<%=  thisItemWork.getNote().getSubmitted() %>" timeZone="<%= User.getUserRecord().getTimeZone() %>" dateOnly="true"/></div></a></td>
                    </dhv:evaluate>
                    <dhv:evaluate if="<%= thisItemWork.getNote() == null %>">
                      <td valign="middle"><a href="javascript:popActionPlanAttachment('note', 'noteid','changenote<%= thisItemWork.getId() %>','orgId=<%= actionPlanWork.getOrganization().getOrgId() %>&source=attachnote&actionStepWork=<%= thisItemWork.getId() %>');"><div id="changenote<%= thisItemWork.getId() %>"><%= thisItemWork.getLabel() != null ? thisItemWork.getLabel() : "<dhv:label name=\"actionPlans.attachNote.text\">Attach Note</dhv:label>" %></div></a></td>
                    </dhv:evaluate>
                  </tr>
                </table>
             </dhv:evaluate>

             <%-- Lookup List Multiple Selection - -%>
             <dhv:evaluate if="<%= (thisItemWork.getActionId() == ActionStep.ATTACH_LOOKUPLIST_MULTIPLE) %>">
               <table class="empty" border="0">
                  <tr>
                    <td><img border="0" src="images/icons/stock_list_bullet-16.gif" align="absmiddle" /></td>
                    <dhv:evaluate if="<%= thisItemWork.getSelectionList() != null %>">
                      <td valign="middle"><a href="javascript:popActionPlanAttachment('selection', 'selectionid','changeselection<%= thisItemWork.getId() %>','orgId=<%= actionPlanWork.getOrganization().getOrgId() %>&source=attachselection&actionStepWork=<%= thisItemWork.getId() %>');"><div id="changeselection<%= thisItemWork.getId() %>"><%= toHtml(thisItemWork.getSelectionList().getDisplayHtml()) %></div></a></td>
                    </dhv:evaluate>
                    <dhv:evaluate if="<%= thisItemWork.getSelectionList() == null %>">
                      <td valign="middle"><a href="javascript:popActionPlanAttachment('selection' ,'selectionid','changeselection<%= thisItemWork.getId() %>','orgId=<%= actionPlanWork.getOrganization().getOrgId() %>&source=attachselection&actionStepWork=<%= thisItemWork.getId() %>');"><div id="changeselection<%= thisItemWork.getId() %>"><%= thisItemWork.getLabel() != null ? thisItemWork.getLabel() : "<dhv:label name=\"actionPlans.attachItems.text\">Attach Items</dhv:label>" %></div></a></td>
                    </dhv:evaluate>
                  </tr>
                </table>
             </dhv:evaluate>
             
             <%-- Add Relationships --  %>
             <dhv:evaluate if="<%= (thisItemWork.getActionId() == ActionStep.ATTACH_RELATIONSHIP) %>">
               <table class="empty" border="0">
                  <tr>
                    <td><img border="0" src="images/icons/stock_list_bullet-16.gif" align="absmiddle" /></td>
                    <dhv:evaluate if="<%= thisItemWork.getRelationshipList() != null %>">
                      <td valign="middle"><a href="javascript:popActionPlanAttachment('relation', 'relationid','changerelation<%= thisItemWork.getId() %>','orgId=<%= actionPlanWork.getOrganization().getOrgId() %>&init=true&source=attachrelation&planWorkId=<%= actionPlanWork.getId() %>&actionStepWork=<%= thisItemWork.getId() %>');"><div id="changerelation<%= thisItemWork.getId() %>"><%= toHtml(thisItemWork.getRelationshipList().getDisplayHtml()) %></div></a></td>
                    </dhv:evaluate>
                    <dhv:evaluate if="<%= thisItemWork.getRelationshipList() == null %>">
                      <td valign="middle"><a href="javascript:popActionPlanAttachment('relation' ,'relationid','changerelation<%= thisItemWork.getId() %>','orgId=<%= actionPlanWork.getOrganization().getOrgId() %>&init=true&source=attachrelation&planWorkId=<%= actionPlanWork.getId() %>&actionStepWork=<%= thisItemWork.getId() %>');"><div id="changerelation<%= thisItemWork.getId() %>"><%= thisItemWork.getLabel() != null ? thisItemWork.getLabel() : "<dhv:label name=\"actionPlans.addRelationships.text\">Add Relationship</dhv:label>" %></div></a>
                    </dhv:evaluate>
                  </tr>
                </table>
             </dhv:evaluate>
             
             <%-- View Account -- %>
              <dhv:evaluate if="<%= (thisItemWork.getActionId() == ActionStep.VIEW_ACCOUNT) %>">
                <table class="empty" border="0">
                  <tr>
                    <td><img border="0" src="images/icons/stock_account-16.gif" align="absmiddle" /></td>
                    <td valign="middle"><a href="javascript:popURL('Accounts.do?command=Details&orgId=<%= actionPlanWork.getOrganization().getOrgId() %>&actionplan=true&popup=true','Action_Plan','700','425','yes','yes');"><%= thisItemWork.getLabel() != null ? thisItemWork.getLabel() : "<dhv:label name=\"actionPlans.reviewAccount.text\">Review Account</dhv:label>" %></a></td>
                  </tr>
                </table>
              </dhv:evaluate>
             
             <%-- Activity -- %>
              <dhv:evaluate if="<%= (thisItemWork.getActionId() == ActionStep.ATTACH_ACTIVITY) %>">
              Attach Activity
              <%--
                  <dhv:evaluate if="<%= thisItemWork.getActivity() != null %>">
                    <table class="empty" border="0"><tr><td><img border="0" src="images/icons/stock_bcard-16.gif" align="absmiddle" /></td><td valign="middle"><a href="javascript:popContactsListSingle('contactid','changecontact','listView=accountcontacts&actionplan=true&itemId=<%= thisItemWork.getId() %>');"><div id="changecontact"><%= toHtml(thisItemWork.getContact().getNameFirstLast()) %></div></a></td></tr></table>
                  </dhv:evaluate>
                  <dhv:evaluate if="<%= thisItemWork.getActivity() == null %>">
                    <table class="empty" border="0"><tr><td><img border="0" src="images/icons/stock_bcard-16.gif" align="absmiddle" /></td><td valign="middle"><a href="javascript:popContactsListSingle('contactid','changecontact','listView=accountcontacts&actionplan=true&itemId=<%= thisItemWork.getId() %>');"><div id="changecontact"><%= thisItemWork.getLabel() != null ? thisItemWork.getLabel() : "<dhv:label name=\"actionPlan.attachContact\">Attach Contact</dhv:label>" %></div></a></td></tr></table>
                  </dhv:evaluate>
                  <input type="hidden" name="contact" id="contactid" value=""/>
                --% >
              </dhv:evaluate>

              <%-- Custom Folder -- %>
              <dhv:evaluate if="<%= (thisItemWork.getActionId() == ActionStep.ATTACH_FOLDER) %>">
                <dhv:evaluate if="<%= thisItemWork.getCustomFieldCategory() != null %>">
                  <table class="empty" border="0"><tr><td>&nbsp;</td>
                    <td valign="middle"><a href="javascript:popFolderForm('recordid<%= thisItemWork.getId() %>','changefolder<%= thisItemWork.getId() %>','<%= thisItemWork.getStep().getCustomFieldCategoryId() %>','<%= thisItemWork.getCustomFieldCategory().getRecordId() %>','<%= thisItemWork.getPlanWork().getOrganization().getOrgId() %>','actionplan=true&source=attachplan&actionStepId=<%= thisItemWork.getId() %>');">
                      <div id="changefolder<%= thisItemWork.getId() %>"><%= toHtml(thisItemWork.getCustomFieldCategory().getFirstFieldValue()) %></div></a></td></tr></table>
                </dhv:evaluate>
                <dhv:evaluate if="<%= thisItemWork.getCustomFieldCategory() == null %>">
                  <table class="empty" border="0"><tr><td>&nbsp;</td>
                    <td valign="middle"><a href="javascript:popFolderForm('recordid<%= thisItemWork.getId() %>','changefolder<%= thisItemWork.getId() %>','<%= thisItemWork.getStep().getCustomFieldCategoryId() %>','-1','<%= thisItemWork.getPlanWork().getOrganization().getOrgId() %>','actionplan=true&source=attachplan&actionStepId=<%= thisItemWork.getId() %>');">
                      <div id="changefolder<%= thisItemWork.getId() %>"><%= thisItemWork.getLabel() != null && !"".equals(thisItemWork.getLabel()) ? thisItemWork.getLabel() : "<dhv:label name=\"actionPlan.attachFolder\">Attach Folder</dhv:label>" %></div></a></td></tr></table>
                </dhv:evaluate>
                <input type="hidden" name="recordid<%= thisItemWork.getId() %>" id="recordid<%= thisItemWork.getId() %>" value=""/>
              </dhv:evaluate>

              < %-- Add Recipient --% >
              <dhv:evaluate if="<%= (thisItemWork.getActionId() == ActionStep.ADD_RECIPIENT) %>">
                <table class="empty" border="0">
                  <tr>
                    <dhv:evaluate if="<%= thisItemWork.getContact() != null %>">
                      <td align="middle" valign="top">
                        <a href="javascript:popContactsListSingle('recipientId','<%= thisItemWork.getStep().getCampaignId() %>','<%= User.getUserRecord().getSiteId() == -1?"includeAllSites=true&siteId=-1":"mySiteOnly=true&siteId="+User.getUserRecord().getSiteId() %>&recipient=true&orgId=<%= (actionPlanWork.getOrganization() != null ? actionPlanWork.getOrganization().getOrgId() : -1) %>&hiddensource=actionplanrecipients&actionItemId=<%= thisItemWork.getId() %>&allowDuplicateRecipient=<%= thisItemWork.getStep().getAllowDuplicateRecipient() %>&listView=accountcontacts&filters=accountcontacts');">
                          <div id="changerecipient<%= thisItemWork.getId() %>">
                            <%= toHtml(thisItemWork.getContact().getNameFull()) %>
                          </div>
                        </a> (<a href="CampaignManager.do?command=PreviewRecipients&id=<%= thisItemWork.getStep().getCampaignId() %>"><dhv:label name="accounts.accounts_contacts_messages_details.Campaign">Campaign</dhv:label></a>)</td>
                    </dhv:evaluate>
                    <dhv:evaluate if="<%= thisItemWork.getContact() == null %>">
                      <td align="middle" valign="top">
                        <a href="javascript:popContactsListSingle('recipientId','<%= thisItemWork.getStep().getCampaignId() %>','<%= User.getUserRecord().getSiteId() == -1?"includeAllSites=true&siteId=-1":"mySiteOnly=true&siteId="+User.getUserRecord().getSiteId() %>&recipient=true&orgId=<%= (actionPlanWork.getOrganization() != null ? actionPlanWork.getOrganization().getOrgId() : -1) %>&hiddensource=actionplanrecipients&actionItemId=<%= thisItemWork.getId() %>&allowDuplicateRecipient=<%= thisItemWork.getStep().getAllowDuplicateRecipient() %>&listView=accountcontacts&filters=accountcontacts');">
                          <div id="changerecipient<%= thisItemWork.getId() %>">
                            <%= thisItemWork.getLabel() != null ? thisItemWork.getLabel() : "<dhv:label name=\"\">Add Recipient</dhv:label>" %>
                          </div>
                        </a> (<a href="CampaignManager.do?command=PreviewRecipients&id=<%= thisItemWork.getStep().getCampaignId() %>"><dhv:label name="accounts.accounts_contacts_messages_details.Campaign">Campaign</dhv:label></a>)</td>
                    </dhv:evaluate>
                  </tr>
                </table>
              </dhv:evaluate>
--%>
            </td>
            <%-- Owner/Responsible This can also be the group user or role or department user responsible --%>
            <td valign="top" nowrap
                <dhv:evaluate if="<%= !thisPhaseWork.getPhase().getRandom() && thisItemWork.isCurrent() %>">
                class="phaseStepHighlight"
                </dhv:evaluate>
                <dhv:evaluate if="<%= thisPhaseWork.getPhase().getRandom() && thisPhaseWork.getPhase().getId() == actionPlanWork.getCurrentPhaseId() %>">
                class="phaseStepHighlight"
                </dhv:evaluate>
              >
              <dhv:evaluate if="<%= thisItemWork.getStep().getPermissionType() == ActionStep.USER_GROUP %>">
                <dhv:label name="usergroups.userGroup">User Group</dhv:label>: 
                <dhv:evaluate if="<%= thisItemWork.getUserGroupName() != null && !"".equals(thisItemWork.getUserGroupName()) %>">
                  <%= toHtml(thisItemWork.getUserGroupName()) %>
                </dhv:evaluate><dhv:evaluate if="<%= thisItemWork.getUserGroupName() == null || "".equals(thisItemWork.getUserGroupName()) %>">
                  <dhv:label name="usergroups.notSet">Not set</dhv:label>
                </dhv:evaluate>
              </dhv:evaluate><dhv:evaluate if="<%= thisItemWork.getStep().getPermissionType() == ActionStep.SPECIFIC_USER_GROUP %>">
                <dhv:label name="usergroups.userGroup">User Group</dhv:label>: 
                <dhv:evaluate if="<%= thisItemWork.getUserGroupName() != null && !"".equals(thisItemWork.getUserGroupName()) %>">
                  <%= toHtml(thisItemWork.getUserGroupName()) %>
                </dhv:evaluate><dhv:evaluate if="<%= thisItemWork.getUserGroupName() == null || "".equals(thisItemWork.getUserGroupName()) %>">
                  <dhv:label name="usergroups.notSet">Not set</dhv:label>
                </dhv:evaluate>
              </dhv:evaluate><dhv:evaluate if="<%= thisItemWork.getStep().getPermissionType() == ActionStep.DEPARTMENT %>">
                <dhv:label name="project.department">Department</dhv:label>: <%= toHtml(thisItemWork.getDepartmentName()) %>
              </dhv:evaluate><dhv:evaluate if="<%= thisItemWork.getStep().getPermissionType() == ActionStep.ROLE %>">
                <dhv:label name="accounts.accounts_add.Role">Role</dhv:label>: <%= toHtml(thisItemWork.getRoleName()) %>
              </dhv:evaluate><dhv:evaluate if="<%= thisItemWork.getStep().getPermissionType() == ActionStep.WITHIN_USER_HIERARCHY %>">
                <dhv:label name="actionPlan.assignedUserHierarchy.withName" param="<%= "username="+ getUsername(pageContext, thisItemWork.getOwnerId(), false,true,"&nbsp;") %>">Within the hierarchy of <%= getUsername(pageContext, thisItemWork.getOwnerId(),false,true,"&nbsp;") %></dhv:label>
              </dhv:evaluate><dhv:evaluate if="<%= thisItemWork.getStep().getPermissionType() == ActionStep.UP_USER_HIERARCHY %>">
                <dhv:label name="actionPlan.upTheUserHierarchy.withName" param="<%= "username="+ getUsername(pageContext, thisItemWork.getOwnerId(), false,true,"&nbsp;") %>">Up the hierarchy of <%= getUsername(pageContext, thisItemWork.getOwnerId(), false,true,"&nbsp;") %></dhv:label>
              </dhv:evaluate><dhv:evaluate if="<%= thisItemWork.getStep().getPermissionType() == ActionStep.MANAGER %>">
                <dhv:username id="<%= actionPlanWork.getManagerId() %>"/>
              </dhv:evaluate><dhv:evaluate if="<%= thisItemWork.getStep().getPermissionType() == ActionStep.ASSIGNED_USER_AND_MANAGER %>">
                <dhv:username id="<%= actionPlanWork.getManagerId() %>"/> <dhv:label name="">and</dhv:label> <dhv:username id="<%= thisItemWork.getOwnerId() %>"/>
              </dhv:evaluate><dhv:evaluate if="<%= thisItemWork.getStep().getPermissionType() != ActionStep.DEPARTMENT && 
                thisItemWork.getStep().getPermissionType() != ActionStep.ROLE && 
                thisItemWork.getStep().getPermissionType() != ActionStep.SPECIFIC_USER_GROUP && 
                  thisItemWork.getStep().getPermissionType() != ActionStep.USER_GROUP &&
                  thisItemWork.getStep().getPermissionType() != ActionStep.UP_USER_HIERARCHY &&
                  thisItemWork.getStep().getPermissionType() != ActionStep.WITHIN_USER_HIERARCHY &&
                  thisItemWork.getStep().getPermissionType() != ActionStep.MANAGER &&
                  thisItemWork.getStep().getPermissionType() != ActionStep.ASSIGNED_USER_AND_MANAGER
                %>">
                <dhv:username id="<%= thisItemWork.getOwnerId() %>"/>
              </dhv:evaluate>
            </td>
          </tr>
  <%}
    if (n == 0) {%>
      <%-- no steps found in this phase. just output the phase --%>
          <tr class="row<%= rowid %>">
            <%-- Action Plan Stage --%>
            <td valign="top" colspan="5">
              <%= toHtml(thisPhaseWork.getPhaseName()) %>
            </td>
          </tr>
  <%} } }%>
</table>
<input type="hidden" name="recipientId" id="recipientId" value="-1"/>
<input type="hidden" name="contact" id="contactid" value="-1"/>
<input type="hidden" name="opportunity" id="opportunityid" value="-1"/>
<input type="hidden" name="fileitem" id="fileitemid" value=""/>
<input type="hidden" name="note" id="noteid" value=""/>
<input type="hidden" name="selection" id="selectionid" value=""/>
<input type="hidden" name="relation" id="relationid" value=""/>
</form>
