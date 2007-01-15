<jsp:useBean id="constants" class="java.util.HashMap" scope="request"/>
<jsp:useBean id="objectName" class="java.lang.String" scope="request"/>
<jsp:useBean id="ratingLookup" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<jsp:useBean id="systemStatus" class="org.aspcfs.controller.SystemStatus" scope="request"/>
<jsp:useBean id="applicationPrefs" class="org.aspcfs.controller.ApplicationPrefs" scope="application"/>
<%-- Initialize the drop-down menus --%>
<%@ include file="../initPopupMenu.jsp" %>
<%@ include file="action_plan_work_details_menu.jsp" %>
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
      if ((actionId == '<%= ActionStep.UPDATE_RATING %>') &&
          (document.actionPlan.rating.options[document.actionPlan.rating.selectedIndex].value == -1)) {
        alert(label("actionstep.rating.alert", "This step requires a valid rating selection"));
      } else if (actionId == '<%= ActionStep.ATTACH_RELATIONSHIP %>' && !hasAttachment(itemId)) {
        alert(label("", "This step requires you to add a relationship"));
      } else if ((actionId != '<%= ActionStep.ATTACH_NOTHING %>') && (actionId != '<%= ActionStep.UPDATE_RATING %>') 
                          && (actionId != '<%= ActionStep.VIEW_ACCOUNT %>') && !hasAttachment(itemId)) {
        alert(label("actionstep.attachment.alert", "This step requires an attachment before it can be completed"));
      } else {
        <dhv:evaluate if="<%= orgDetails.getOrgId() == -1 %>">
          popURLReturn('MyActionPlans.do?command=ModifyStatus&planId=' + planId + '&itemId=' + itemId,'MyActionPlans.do?command=Details&actionPlanId=' + planId,'Action_Plan',550,200);
        </dhv:evaluate>
        <dhv:evaluate if="<%= orgDetails.getOrgId() != -1 %>">
          popURLReturn('AccountActionPlans.do?command=ModifyStatus&orgId=<%= orgDetails.getOrgId() %>&planId=' + planId + '&itemId=' + itemId,'AccountActionPlans.do?command=Details&orgId=<%= orgDetails.getOrgId() %>&actionPlanId=' + planId,'Action_Plan',550,200);
        </dhv:evaluate>
      }
    } else {
      <dhv:evaluate if="<%= orgDetails.getOrgId() == -1 %>">
        popURLReturn('MyActionPlans.do?command=ModifyStatus&planId=' + planId + '&itemId=' + itemId,'MyActionPlans.do?command=Details&actionPlanId=' + planId,'Action_Plan',550,200);
      </dhv:evaluate>
      <dhv:evaluate if="<%= orgDetails.getOrgId() != -1 %>">
        popURLReturn('AccountActionPlans.do?command=ModifyStatus&orgId=<%= orgDetails.getOrgId() %>&planId=' + planId + '&itemId=' + itemId,'AccountActionPlans.do?command=Details&orgId=<%= orgDetails.getOrgId() %>&actionPlanId=' + planId,'Action_Plan',550,200);
      </dhv:evaluate>
    }
  }
  
  function reviewNotes() {
    <dhv:evaluate if="<%= orgDetails.getOrgId() == -1 %>">
      popURL('MyActionPlans.do?command=ViewNotes&orgId=<%= actionPlanWork.getOrganization() != null ? actionPlanWork.getOrganization().getOrgId() : -1 %>&planWorkId=<%= actionPlanWork.getId() %>','Action_Plan',700,425,'yes','yes');
    </dhv:evaluate>
    <dhv:evaluate if="<%= orgDetails.getOrgId() != -1 %>">
      popURL('AccountActionPlans.do?command=ViewNotes&orgId=<%= orgDetails.getOrgId() %>&planWorkId=<%= actionPlanWork.getId() %>','Action_Plan',700,425,'yes','yes');
    </dhv:evaluate>
  }
  
  function reassignPlan() {
    popURL('ContactsList.do?command=ContactList&listView=employees&hierarchy=<%= User.getUserId() %>&searchcodePermission=sales-leads-edit,myhomepage-action-plans-view&listType=single&flushtemplist=true&usersOnly=true&source=reassignplan&actionplan=true&actionPlanWork=<%= actionPlanWork.getId() %>','Action_Plan','700','425','yes','yes');
  }
  
  function restartPlan() {
    <dhv:evaluate if="<%= orgDetails.getOrgId() == -1 %>">
      confirmForward('MyActionPlans.do?command=Restart&orgId=<%= actionPlanWork.getOrganization() != null ? actionPlanWork.getOrganization().getOrgId() : -1 %>&actionPlanId=<%= actionPlanWork.getId() %>');
    </dhv:evaluate>
    <dhv:evaluate if="<%= orgDetails.getOrgId() != -1 %>">
      confirmForward('AccountActionPlans.do?command=Restart&orgId=<%= orgDetails.getOrgId() %>&actionPlanId=<%= actionPlanWork.getId() %>');
    </dhv:evaluate>
  }

  function deletePlan() {
    <dhv:evaluate if="<%= orgDetails.getOrgId() == -1 %>">
      confirmForward('MyActionPlans.do?command=Delete&orgId=<%= actionPlanWork.getOrganization() != null ? actionPlanWork.getOrganization().getOrgId() : -1 %>&actionPlanId=<%= actionPlanWork.getId() %>');
    </dhv:evaluate>
    <dhv:evaluate if="<%= orgDetails.getOrgId() != -1 %>">
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
</script>
<script language="JavaScript" type="text/javascript">
  <%-- Preload image rollovers for drop-down menu --%>
  loadImages('select');
</script>
<form name="actionPlan">
<%-- Plan Header --%>
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
<%@ include file="action_plan_work_header_include.jsp" %>
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
                <%@ include file="action_plan_work_details_attachments_include.jsp" %>
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
                <dhv:evaluate if='<%= thisItemWork.getUserGroupName() != null && !"".equals(thisItemWork.getUserGroupName()) %>'>
                  <%= toHtml(thisItemWork.getUserGroupName()) %>
                </dhv:evaluate><dhv:evaluate if='<%= thisItemWork.getUserGroupName() == null || "".equals(thisItemWork.getUserGroupName()) %>'>
                  <dhv:label name="usergroups.notSet">Not set</dhv:label>
                </dhv:evaluate>
              </dhv:evaluate><dhv:evaluate if="<%= thisItemWork.getStep().getPermissionType() == ActionStep.SPECIFIC_USER_GROUP %>">
                <dhv:label name="usergroups.userGroup">User Group</dhv:label>: 
                <dhv:evaluate if='<%= thisItemWork.getUserGroupName() != null && !"".equals(thisItemWork.getUserGroupName()) %>'>
                  <%= toHtml(thisItemWork.getUserGroupName()) %>
                </dhv:evaluate><dhv:evaluate if='<%= thisItemWork.getUserGroupName() == null || "".equals(thisItemWork.getUserGroupName()) %>'>
                  <dhv:label name="usergroups.notSet">Not set</dhv:label>
                </dhv:evaluate>
              </dhv:evaluate><dhv:evaluate if="<%= thisItemWork.getStep().getPermissionType() == ActionStep.DEPARTMENT %>">
                <dhv:label name="project.department">Department</dhv:label>: <%= toHtml(thisItemWork.getDepartmentName()) %>
              </dhv:evaluate><dhv:evaluate if="<%= thisItemWork.getStep().getPermissionType() == ActionStep.ROLE %>">
                <dhv:label name="accounts.accounts_add.Role">Role</dhv:label>: <%= toHtml(thisItemWork.getRoleName()) %>
              </dhv:evaluate><dhv:evaluate if="<%= thisItemWork.getStep().getPermissionType() != ActionStep.DEPARTMENT && 
                thisItemWork.getStep().getPermissionType() != ActionStep.ROLE && 
                thisItemWork.getStep().getPermissionType() != ActionStep.SPECIFIC_USER_GROUP && 
                  thisItemWork.getStep().getPermissionType() != ActionStep.USER_GROUP %>">
                <dhv:username id="<%= thisItemWork.getOwnerId() %>"/>
              </dhv:evaluate>
            </td>
          </tr>
  <%
        }
        if (n == 0) {
          //no steps found in this phase. just output the phase
  %>
          <tr class="row<%= rowid %>">
            <%-- Action Plan Stage --%>
            <td valign="top" colspan="5">
              <%= toHtml(thisPhaseWork.getPhaseName()) %>
            </td>
          </tr>
  <%
        }
      }
    }
  %>
</table>
<input type="hidden" name="contact" id="contactid" value=""/>
<input type="hidden" name="opportunity" id="opportunityid" value="-1"/>
<input type="hidden" name="fileitem" id="fileitemid" value=""/>
<input type="hidden" name="note" id="noteid" value=""/>
<input type="hidden" name="selection" id="selectionid" value=""/>
<input type="hidden" name="relation" id="relationid" value=""/>
</form>
