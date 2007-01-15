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
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="stageProgress">
  <tr>
    <td>
      <table border="0" cellpadding="0" cellspacing="0" width="100%">
        <tr>
          <%
            Iterator phaseHeaderItr = actionPlanWork.getPhaseWorkList().iterator();
            while (phaseHeaderItr.hasNext()) {
              ActionPhaseWork phaseItem = (ActionPhaseWork) phaseHeaderItr.next();
              if (phaseItem.allStepsComplete()) {
          %>
              <td nowrap class="stageComplete"><%= toHtml(phaseItem.getPhaseName()) %></td>
          <%
              } else if (phaseItem.getActionPhaseId() == actionPlanWork.getCurrentPhaseId()) {
          %>
              <td nowrap class="stageCurrent"><%= toHtml(phaseItem.getPhaseName()) %></td>
              <td nowrap class="stageCurrentArrow">&nbsp;&nbsp;</td>
          <%
              } else {
          %>
              <td nowrap class="stageIncomplete"><%= toHtml(phaseItem.getPhaseName()) %></td>
          <%
              }
            }
          %>
          <td nowrap class="stageActions" align="right" width="100%">
            &nbsp;
            <dhv:evaluate if="<%= User.getUserRecord().getId() == actionPlanWork.getManagerId() %>">
              <dhv:label name="actionPlan.actions.colon">Actions:</dhv:label>
              <a href="javascript:displayMenu('select1','menuDisplay');"
              onMouseOver="over(0, 1)" onmouseout="out(0, 1); hideMenu('menuDisplay');"><img src="images/select.gif" name="select1" id="select1" align="absmiddle" border="0"></a>
            </dhv:evaluate>
            &nbsp;
          </td>
        </tr>
      </table>
    </td>
  </tr>
</table>
<br />
<table width="100%" border="0" cellpadding="0" cellspacing="4">
  <tr>
    <td valign="top" width="40%">
      <table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
        <tr class="containerBody">
          <td nowrap class="formLabel">
            <dhv:label name="accounts.accounts_contacts_calls_list.AssignedTo">Assigned To</dhv:label>:
          </td>
          <td nowrap>
            <dhv:username id="<%= actionPlanWork.getAssignedTo() %>"/>
          </td>
        </tr>
        <tr class="containerBody">
          <td nowrap class="formLabel">
            <dhv:label name="actionPlan.planManager">Plan Manager</dhv:label>:
          </td>
          <td>
            <dhv:username id="<%= actionPlanWork.getManagerId() %>"/>
          </td>
        </tr>
        <dhv:evaluate if="<%= hasText(actionPlanWork.getLinkItemName()) %>">
        <tr class="containerBody">
           <td nowrap class="formLabel">
              <dhv:label name="actionPlan.prospectName">Prospect Name</dhv:label>
           </td>  
           <td>    
                <%= toHtml(actionPlanWork.getLinkItemName()) %>
             
           </td>
        </tr>
        </dhv:evaluate>
        <dhv:evaluate if="<%= actionPlanWork.getContact() != null %>">
          <dhv:evaluate if="<%= hasText(actionPlanWork.getContact().getAccountNumber()) %>">
            <tr class="containerBody">
              <td nowrap class="formLabel">
                <dhv:label name="actionPlan.prospect">Prospect</dhv:label> #:
              </td>
              <td>
                <%= toHtml(actionPlanWork.getContact().getAccountNumber()) %>
              </td>
            </tr>
          </dhv:evaluate>
          <dhv:evaluate if="<%= actionPlanWork.getContact().getPrimaryAddress() != null %>">
            <tr class="containerBody">
              <td nowrap class="formLabel">
                <dhv:label name="accounts.accounts_add.City">City</dhv:label>, <dhv:label name="accounts.accounts_add.Zip">Zip</dhv:label>:
              </td>
              <td>
                <%= toHtml(actionPlanWork.getContact().getPrimaryAddress().getCity()) %>
                <%= toHtml(actionPlanWork.getContact().getPrimaryAddress().getZip()) %>
              </td>
            </tr>
          </dhv:evaluate>
          <dhv:evaluate if="<%= actionPlanWork.getContact().getPotential() > 0 %>">
            <tr class="containerBody">
              <td nowrap class="formLabel">
                <dhv:label name="actionPlan.totalWkPotential">Total Wk. Potential</dhv:label>:
              </td>
              <td>
                <zeroio:currency value="<%= actionPlanWork.getContact().getPotential() %>" code='<%= applicationPrefs.get("SYSTEM.CURRENCY") %>' locale="<%= User.getLocale() %>" default="&nbsp;"/>
              </td>
            </tr>
          </dhv:evaluate>
        </dhv:evaluate>
        <dhv:evaluate if="<%= actionPlanWork.getOrganization() != null %>">
          <dhv:evaluate if="<%= hasText(actionPlanWork.getOrganization().getAccountNumber()) %>">
            <tr class="containerBody">
              <td nowrap class="formLabel">
                <dhv:label name="actionPlan.prospect">Prospect</dhv:label> #:
              </td>
              <td>
                <%= toHtml(actionPlanWork.getOrganization().getAccountNumber()) %>
              </td>
            </tr>
          </dhv:evaluate>
          <dhv:evaluate if="<%= actionPlanWork.getOrganization().getPrimaryAddress() != null %>">
            <tr class="containerBody">
              <td nowrap class="formLabel">
                <dhv:label name="accounts.accounts_add.City">City</dhv:label>, <dhv:label name="accounts.accounts_add.Zip">Zip</dhv:label>:
              </td>
              <td>
                <%= toHtml(actionPlanWork.getOrganization().getPrimaryAddress().getCity()) %>
                <%= toHtml(actionPlanWork.getOrganization().getPrimaryAddress().getZip()) %>
              </td>
            </tr>
          </dhv:evaluate>
          <dhv:evaluate if="<%= actionPlanWork.getOrganization().getPotential() > 0 %>">
            <tr class="containerBody">
              <td nowrap class="formLabel">
                <dhv:label name="actionPlan.totalWkPotential">Total Wk. Potential</dhv:label>:
              </td>
              <td>
                <zeroio:currency value="<%= actionPlanWork.getOrganization().getPotential() %>" code='<%= applicationPrefs.get("SYSTEM.CURRENCY") %>' locale="<%= User.getLocale() %>" default="&nbsp;"/>
              </td>
            </tr>
          </dhv:evaluate>
        </dhv:evaluate>
        <tr class="containerBody">
          <td nowrap class="formLabel">
            <dhv:label name="actionPlan.activationDate">Activation Date</dhv:label>:
          </td>
          <td>
            <zeroio:tz timestamp="<%= actionPlanWork.getEntered() %>" timeZone="<%= User.getUserRecord().getTimeZone() %>" dateOnly="true"/>
          </td>
        </tr>
        <tr class="containerBody">
          <td nowrap class="formLabel">
            <dhv:label name="actionPlan.totalDaysActive">Total # Days Active</dhv:label>:
          </td>
          <td>
            <%= actionPlanWork.getDaysActive() %>
          </td>
        </tr>
      </table>
    </td>
    <%--
    <td nowrap align="right" valign="top">
      <a href="javascript:reviewNotes();"><img src="images/icons/stock_new_bullet-16.gif" border="0" align="absmiddle" height="16" width="16"/></a>&nbsp;<a href="javascript:reviewNotes();"><dhv:label name="actionPlan.reviewPlanNotes">Review Plan Notes</dhv:label></a>
    </td>
    --%>
    <dhv:evaluate if="<%= globalActionPlanWork.getPhaseWorkList().size() > 0 %>">
    <td width="60%" nowrap align="right" valign="top">
      <table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
        <%
          Iterator globalPhases = globalActionPlanWork.getPhaseWorkList().iterator();
          while (globalPhases.hasNext()) {
            ActionPhaseWork globalPhase = (ActionPhaseWork) globalPhases.next();
            Iterator globalSteps = globalPhase.getItemWorkList().iterator();
            while (globalSteps.hasNext()) {
              ActionItemWork thisItemWork = (ActionItemWork) globalSteps.next();
        %>
        <tr class="containerBody">
          <%-- Global Step --%>
          <td nowrap valign="top" class="row1" style="border-right:0px">
            <dhv:evaluate if="<%= thisItemWork.userHasPermission(User.getUserRecord().getId(), request) %>">
              <%-- The user logged-in is the steps owner --%>
              <a class="rollover" href="javascript:updateGlobalStatus('<%= !thisItemWork.isComplete() ? thisItemWork.getActionRequired() : false %>','<%= actionPlanWork.getId() %>', '<%= thisItemWork.getActionId() %>', '<%= thisItemWork.getId() %>');"><%= thisItemWork.getStatusGraphicTag(systemStatus) %></a>
              <a class="rollover" href="javascript:updateGlobalStatus('<%= !thisItemWork.isComplete() ? thisItemWork.getActionRequired() : false %>','<%= actionPlanWork.getId() %>', '<%= thisItemWork.getActionId() %>', '<%= thisItemWork.getId() %>');"><%= toHtml(thisItemWork.getStepDescription()) %></a>
            </dhv:evaluate>
            <dhv:evaluate if="<%= ! thisItemWork.userHasPermission(User.getUserRecord().getId(), request) %>">
              <%-- The user logged-in is NOT the steps owner --%>
              <%= thisItemWork.getStatusGraphicTag(systemStatus) %>
              <%= toHtml(thisItemWork.getStepDescription()) %>
            </dhv:evaluate>
          </td>
          <%-- Attachments --%>
          <td valign="top" align="center" class="row1" nowrap>
            <%@ include file="action_plan_work_details_attachments_include.jsp" %>
          </td>
        </tr>
        <%
           }
         }
        %>
      </table>
    </td>
    </dhv:evaluate>
  </tr>
</table>
<br />

