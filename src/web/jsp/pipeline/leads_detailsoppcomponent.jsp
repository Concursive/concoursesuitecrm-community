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
<%@ page import="java.util.*,java.text.DateFormat,org.aspcfs.modules.pipeline.base.*,com.zeroio.iteam.base.*" %>
<jsp:useBean id="opportunityHeader" class="org.aspcfs.modules.pipeline.base.OpportunityHeader" scope="request"/>
<jsp:useBean id="LeadsComponentDetails" class="org.aspcfs.modules.pipeline.base.OpportunityComponent" scope="request"/>
<jsp:useBean id="PipelineViewpointInfo" class="org.aspcfs.utils.web.ViewpointInfo" scope="session"/>
<jsp:useBean id="environmentSelect" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="competitorsSelect" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="compellingEventSelect" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="budgetSelect" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<jsp:useBean id="applicationPrefs" class="org.aspcfs.controller.ApplicationPrefs" scope="application"/>
<%@ include file="../initPage.jsp" %>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popURL.js"></script>
<script type="text/javascript">
function reopenOpportunity(id) {
  if (id == '<%= opportunityHeader.getId() %>') {
    if ('<%= "dashboard".equals(request.getParameter("viewSource")) %>' == 'true') {
      scrollReload('Leads.do?command=Dashboard');
    } else {
      scrollReload('Leads.do?command=Search');
    }
    return id;
  } else {
    return '<%= opportunityHeader.getId() %>';
  }
}
</script>
<form name="componentDetails" action="Leads.do?command=ModifyComponent&id=<%= LeadsComponentDetails.getId() %>&return=details" method="post">
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="Leads.do"><dhv:label name="pipeline.pipeline">Pipeline</dhv:label></a> >
<% if ("dashboard".equals(request.getParameter("viewSource"))){ %>
	<a href="Leads.do?command=Dashboard"><dhv:label name="communications.campaign.Dashboard">Dashboard</dhv:label></a> >
<% }else{ %>
	<a href="Leads.do?command=Search"><dhv:label name="accounts.SearchResults">Search Results</dhv:label></a> >
<% } %>
<a href="Leads.do?command=DetailsOpp&headerId=<%= LeadsComponentDetails.getHeaderId() %>&reset=true<%= addLinkParams(request, "viewSource") %>"><dhv:label name="accounts.accounts_contacts_oppcomponent_add.OpportunityDetails">Opportunity Details</dhv:label></a> > 
<dhv:label name="accounts.accounts_contacts_oppcomponent_add.ComponentDetails">Component Details</dhv:label>
</td>
</tr>
</table>
<%-- End Trails --%>
<dhv:evaluate if="<%= PipelineViewpointInfo.isVpSelected(User.getUserId()) %>">
  <dhv:label name="pipeline.viewpoint.colon" param="<%= "username="+PipelineViewpointInfo.getVpUserName() %>"><b>Viewpoint: </b><b class="highlight"><%= PipelineViewpointInfo.getVpUserName() %></b></dhv:label><br />
  &nbsp;<br>
</dhv:evaluate>
<%-- Begin container --%>
<% String param1 = "id=" + opportunityHeader.getId(); 
   String param2 = addLinkParams(request, "viewSource");
%>      
<dhv:container name="opportunities" selected="details" object="opportunityHeader" param="<%= param1 %>" appendToUrl="<%= param2 %>">
  <dhv:permission name="pipeline-opportunities-edit"><input type="button" value="<dhv:label name="global.button.modify">Modify</dhv:label>" onClick="javascript:this.form.action='LeadsComponents.do?command=ModifyComponent&id=<%= LeadsComponentDetails.getId() %>&return=details';submit();"></dhv:permission>
  <dhv:permission name="pipeline-opportunities-delete"><input type="button" value="<dhv:label name="global.button.delete">Delete</dhv:label>" onClick="javascript:popURLReturn('LeadsComponents.do?command=ConfirmComponentDelete&id=<%= LeadsComponentDetails.getId() %>&popup=true<%= addLinkParams(request, "viewSource") %>','Leads.do?command=DetailsOpp&headerId=<%= LeadsComponentDetails.getHeaderId() %>', 'Delete_opp','320','200','yes','no')"></dhv:permission>
  <br>&nbsp;
  <table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
    <tr>
      <th colspan="2">
        <strong><%= toHtml(LeadsComponentDetails.getDescription()) %></strong>
      </th>
    </tr>
    <tr class="containerBody">
      <td class="formLabel">
        <dhv:label name="accounts.accounts_contacts_detailsimport.Owner">Owner</dhv:label>
      </td>
      <td>
        <dhv:username id="<%= LeadsComponentDetails.getOwner() %>"/>
        <dhv:evaluate if="<%= !(LeadsComponentDetails.getHasEnabledOwnerAccount()) %>"><font color="red">*</font></dhv:evaluate>
      </td>
    </tr>
  <dhv:evaluate if="<%= hasText(LeadsComponentDetails.getTypes().valuesAsString()) %>">
    <tr class="containerBody">
      <td nowrap class="formLabel">
        <dhv:label name="accounts.accounts_contacts_oppcomponent_add.OpportunityTypes">Opportunity Type(s)</dhv:label>
      </td>
      <td>
        <%= toHtml(LeadsComponentDetails.getTypes().valuesAsString()) %>
       </td>
    </tr>
  </dhv:evaluate>
  <dhv:evaluate if="<%= hasText(LeadsComponentDetails.getNotes()) %>">
    <tr class="containerBody">
      <td valign="top" nowrap class="formLabel"><dhv:label name="accounts.accounts_contacts_oppcomponent_add.AdditionalNotes">Additional Notes</dhv:label></td>
      <td><%= toHtml(LeadsComponentDetails.getNotes()) %></td>
    </tr>
  </dhv:evaluate>
    <tr class="containerBody">
      <td nowrap class="formLabel">
        <dhv:label name="accounts.accounts_contacts_oppcomponent_add.ProbOfClose">Prob. of Close</dhv:label>
      </td>
      <td>
        <%= LeadsComponentDetails.getCloseProbValue() %>%
      </td>
    </tr>
    <tr class="containerBody">
      <td nowrap class="formLabel">
        <dhv:label name="accounts.accounts_contacts_oppcomponent_add.EstCloseDate">Est. Close Date</dhv:label>
      </td>
      <td>
        <zeroio:tz timestamp="<%= LeadsComponentDetails.getCloseDate() %>" dateOnly="true" timeZone="<%= LeadsComponentDetails.getCloseDateTimeZone() %>" showTimeZone="true" default="&nbsp;"/>
        <% if(!User.getTimeZone().equals(LeadsComponentDetails.getCloseDateTimeZone())){%>
        <br />
        <zeroio:tz timestamp="<%= LeadsComponentDetails.getCloseDate() %>" timeZone="<%= User.getTimeZone() %>" showTimeZone="true" default="&nbsp;"/>
        <% } %>
      </td>
    </tr>
    <tr class="containerBody">
      <td nowrap class="formLabel">
        <dhv:label name="accounts.accounts_contacts_oppcomponent_add.LowEstimate">Low Estimate</dhv:label>
      </td>
      <td>
        <zeroio:currency value="<%= LeadsComponentDetails.getLow() %>" code="<%= applicationPrefs.get("SYSTEM.CURRENCY") %>" locale="<%= User.getLocale() %>" default="&nbsp;"/>
      </td>
    </tr>
    <tr class="containerBody">
      <td nowrap class="formLabel">
        <dhv:label name="accounts.accounts_contacts_oppcomponent_add.BestGuess">Best Guess</dhv:label>
      </td>
      <td>
        <zeroio:currency value="<%= LeadsComponentDetails.getGuess() %>" code="<%= applicationPrefs.get("SYSTEM.CURRENCY") %>" locale="<%= User.getLocale() %>" default="&nbsp;"/>
      </td>
    </tr>
    <tr class="containerBody">
      <td nowrap class="formLabel">
        <dhv:label name="accounts.accounts_contacts_oppcomponent_add.HighEstimate">High Estimate</dhv:label>
      </td>
      <td>
        <zeroio:currency value="<%= LeadsComponentDetails.getHigh() %>" code="<%= applicationPrefs.get("SYSTEM.CURRENCY") %>" locale="<%= User.getLocale() %>" default="&nbsp;"/>
      </td>
    </tr>
    <tr class="containerBody">
      <td nowrap class="formLabel">
        <dhv:label name="accounts.accounts_contacts_oppcomponent_add.EstTerm">Est. Term</dhv:label>
      </td>
      <td>
        <%= LeadsComponentDetails.getTerms() %> <dhv:label name="accounts.accounts_contacts_oppcomponent_details.months">months</dhv:label>
      </td>
    </tr>
    <tr class="containerBody">
      <td nowrap class="formLabel">
        <dhv:label name="accounts.accounts_contacts_oppcomponent_details.CurrentStage">Current Stage</dhv:label>
      </td>
      <td>
        <%= toHtml(LeadsComponentDetails.getStageName()) %>&nbsp;
      </td>
    </tr>
    <tr class="containerBody">
      <td nowrap class="formLabel">
        <dhv:label name="accounts.accounts_contacts_oppcomponent_details.CurrentStageDate">Current Stage Date</dhv:label>
      </td>
      <td>
        <zeroio:tz timestamp="<%= LeadsComponentDetails.getStageDate() %>" dateOnly="true" default="&nbsp;"/>
      </td>
    </tr>
    <dhv:evaluate if="<%= environmentSelect.getEnabledElementCount() > 0 %>">
    <tr class="containerBody">
      <td nowrap class="formLabel">
        <dhv:label name="pipeline.environment">Environment</dhv:label>
      </td>
      <td>
      <zeroio:debug value="<%= "JSP:: the environment is "+ LeadsComponentDetails.getEnvironment() %>"/>
      <%= toHtml(environmentSelect.getSelectedValue(LeadsComponentDetails.getEnvironment())) %>
      </td>
    </tr>
    </dhv:evaluate>
    <dhv:evaluate if="<%= competitorsSelect.getEnabledElementCount() > 0 %>">
    <tr class="containerBody">
      <td nowrap class="formLabel">
        <dhv:label name="pipeline.competitors">Competitors</dhv:label>
      </td>
      <td>
      <%= toHtml(competitorsSelect.getSelectedValue(LeadsComponentDetails.getCompetitors())) %>
      </td>
    </tr>
    </dhv:evaluate>
    <dhv:evaluate if="<%= compellingEventSelect.getEnabledElementCount() > 0 %>">
    <tr class="containerBody">
      <td nowrap class="formLabel">
        <dhv:label name="pipeline.compellingEvent">Compelling Event</dhv:label>
      </td>
      <td>
      <%= toHtml(compellingEventSelect.getSelectedValue(LeadsComponentDetails.getCompellingEvent())) %>
      </td>
    </tr>
    </dhv:evaluate>
    <dhv:evaluate if="<%= budgetSelect.getEnabledElementCount() > 0 %>">
    <tr class="containerBody">
      <td nowrap class="formLabel">
        <dhv:label name="project.budget">Budget</dhv:label>
      </td>
      <td>
      <%= toHtml(budgetSelect.getSelectedValue(LeadsComponentDetails.getBudget())) %>
      </td>
    </tr>
    </dhv:evaluate>
    <tr class="containerBody">
      <td nowrap class="formLabel">
        <dhv:label name="accounts.accounts_contacts_oppcomponent_details.EstCommission">Est. Commission</dhv:label>
      </td>
      <td>
        <%= LeadsComponentDetails.getCommissionValue() %>%
      </td>
    </tr>
  <dhv:evaluate if="<%= hasText(LeadsComponentDetails.getAlertText()) %>">
     <tr class="containerBody">
      <td nowrap class="formLabel">
        <dhv:label name="accounts.accounts_add.AlertDescription">Alert Description</dhv:label>
      </td>
      <td>
         <%= toHtml(LeadsComponentDetails.getAlertText()) %>
      </td>
    </tr>
  </dhv:evaluate>
  <dhv:evaluate if="<%= (LeadsComponentDetails.getAlertDate() != null) %>">
     <tr class="containerBody">
      <td nowrap class="formLabel">
        <dhv:label name="accounts.accounts_add.AlertDate">Alert Date</dhv:label>
      </td>
      <td>
        <zeroio:tz timestamp="<%= LeadsComponentDetails.getAlertDate() %>" dateOnly="true" timeZone="<%= LeadsComponentDetails.getAlertDateTimeZone() %>" showTimeZone="true"  default="&nbsp;"/>
        <% if(!User.getTimeZone().equals(LeadsComponentDetails.getAlertDateTimeZone())){%>
        <br>
        <zeroio:tz timestamp="<%= LeadsComponentDetails.getAlertDate() %>" timeZone="<%= User.getTimeZone() %>" showTimeZone="true"  default="&nbsp;" />
        <% } %>
      </td>
    </tr>
  </dhv:evaluate>
    <tr class="containerBody">
      <td nowrap class="formLabel">
        <dhv:label name="accounts.accounts_calls_list.Entered">Entered</dhv:label>
      </td>
      <td>
        <dhv:username id="<%= LeadsComponentDetails.getEnteredBy() %>"/>
        <zeroio:tz timestamp="<%= LeadsComponentDetails.getEntered() %>" timeZone="<%= User.getTimeZone() %>" showTimeZone="true" />
      </td>
    </tr>
    <tr class="containerBody">
      <td nowrap class="formLabel">
        <dhv:label name="accounts.accounts_contacts_calls_details.Modified">Modified</dhv:label>
      </td>
      <td>
        <dhv:username id="<%= LeadsComponentDetails.getModifiedBy() %>"/>
        <zeroio:tz timestamp="<%= LeadsComponentDetails.getModified() %>" timeZone="<%= User.getTimeZone() %>" showTimeZone="true" />
      </td>
    </tr>
  </table>
  <dhv:permission name="pipeline-opportunities-edit,pipeline-opportunities-delete"><br></dhv:permission>
  <dhv:permission name="pipeline-opportunities-edit"><input type="button" value="<dhv:label name="global.button.modify">Modify</dhv:label>" onClick="javascript:this.form.action='LeadsComponents.do?command=ModifyComponent&id=<%= LeadsComponentDetails.getId() %>&return=details';submit();"></dhv:permission>
  <dhv:permission name="pipeline-opportunities-delete"><input type="button" value="<dhv:label name="global.button.delete">Delete</dhv:label>" onClick="javascript:popURLReturn('LeadsComponents.do?command=ConfirmComponentDelete&id=<%= LeadsComponentDetails.getId() %>&popup=true<%= addLinkParams(request, "viewSource") %>','Leads.do?command=DetailsOpp&headerId=<%= LeadsComponentDetails.getHeaderId() %>', 'Delete_opp','320','200','yes','no')"></dhv:permission>
  <%= addHiddenParams(request, "viewSource") %>
</dhv:container>
</form>
