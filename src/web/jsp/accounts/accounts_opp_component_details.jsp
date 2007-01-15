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
  - Version: $Id: accountsComponentDetails.jsp 13324 2005-11-15 19:07:41Z partha $
  - Description:
  --%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ page import="java.util.*,java.text.DateFormat,org.aspcfs.modules.pipeline.base.*,com.zeroio.iteam.base.*" %>
<jsp:useBean id="orgDetails" class="org.aspcfs.modules.accounts.base.Organization" scope="request"/>
<jsp:useBean id="opportunityHeader" class="org.aspcfs.modules.pipeline.base.OpportunityHeader" scope="request"/>
<jsp:useBean id="opportunityComponent" class="org.aspcfs.modules.pipeline.base.OpportunityComponent" scope="request"/>
<jsp:useBean id="accountsComponentDetails" class="org.aspcfs.modules.pipeline.base.OpportunityComponentLog" scope="request"/>
<jsp:useBean id="PipelineViewpointInfo" class="org.aspcfs.utils.web.ViewpointInfo" scope="session"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<jsp:useBean id="applicationPrefs" class="org.aspcfs.controller.ApplicationPrefs" scope="application"/>
<%@ include file="../initPage.jsp" %>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popURL.js"></script>
<script type="text/javascript">
function reopenOpportunity(id) {
  if (id == '<%= opportunityHeader.getId() %>') {
    scrollReload('Opportunities.do?command=View&orgId=<%= orgDetails.getOrgId() %><%= addLinkParams(request, "viewSource|popup|popupType") %>');
    return id;
  } else {
    return '<%= opportunityHeader.getId() %>';
  }
}
</script>
<dhv:evaluate if="<%= !isPopup(request) %>">
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="Accounts.do"><dhv:label name="accounts.accounts">Accounts</dhv:label></a> > 
<a href="Accounts.do?command=Search"><dhv:label name="accounts.SearchResults">Search Results</dhv:label></a> >
<a href="Accounts.do?command=Details&orgId=<%= orgDetails.getOrgId() %>"><dhv:label name="accounts.details">Account Details</dhv:label></a> >
<a href="Opportunities.do?command=View&orgId=<%= orgDetails.getOrgId() %>"><dhv:label name="accounts.accounts_contacts_oppcomponent_add.Opportunities">Opportunities</dhv:label></a> >
<a href="Opportunities.do?command=Details&headerId=<%= opportunityHeader.getId() %>&orgId=<%= orgDetails.getId() %>&reset=true"><dhv:label name="accounts.accounts_contacts_oppcomponent_add.OpportunityDetails">Opportunity Details</dhv:label></a> >
<a href="OpportunitiesComponents.do?command=ComponentHistory&orgId=<%= orgDetails.getOrgId() %>&headerId=<%= opportunityHeader.getId() %>&id=<%= opportunityComponent.getId() %>"><dhv:label name="accounts.accounts_contacts_oppcomponent.componentLog">Componet Log</dhv:label></a> > 
<dhv:label name="accounts.accounts_contacts_oppcomponent_add.ComponentLogDetails">Component Log Details</dhv:label>
</td>
</tr>
</table>
<%-- End Trails --%>
</dhv:evaluate>
<dhv:evaluate if="<%= PipelineViewpointInfo.isVpSelected(User.getUserId()) %>">
  <dhv:label name="pipeline.viewpoint.colon" param='<%= "username="+PipelineViewpointInfo.getVpUserName() %>'><b>Viewpoint: </b><b class="highlight"><%= PipelineViewpointInfo.getVpUserName() %></b></dhv:label><br />
  &nbsp;<br>
</dhv:evaluate>
<%-- Begin container --%>
<% String param1 = "id=" + opportunityHeader.getId(); 
   String param2 = addLinkParams(request, "viewSource|popup|popupType");
%>
<dhv:container name="accounts" selected="opportunities" hideContainer='<%="true".equals(request.getParameter("actionplan")) %>' object="orgDetails" param='<%= "orgId=" + orgDetails.getOrgId() %>' appendToUrl='<%= addLinkParams(request, "popup|popupType|actionId|actionplan") %>'>
  <img src="images/icons/stock_form-currency-field-16.gif" border="0" align="absmiddle">
  <strong><%= toHtml(opportunityHeader.getDescription()) %></strong>
  <% FileItem thisFile = new FileItem(); %>
  <dhv:evaluate if="<%= opportunityHeader.hasFiles() %>">
    <%= thisFile.getImageTag("-23") %>
  </dhv:evaluate>
  <br /><br />
  <table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
    <tr>
      <th colspan="2">
        <dhv:label name="accounts.accounts_contacts_oppcomponent_add.ComponentLogDetails">Component Log Details</dhv:label>
      </th>
    </tr>
    <tr class="containerBody">
      <td class="formLabel">
        <dhv:label name="accounts.accounts_contacts_opps_details.Component">Component</dhv:label>
      </td>
      <td>
        <%= toHtml(accountsComponentDetails.getDescription()) %>
      </td>
    </tr>
    <tr class="containerBody">
      <td nowrap class="formLabel">
        <dhv:label name="accounts.accounts_contacts_oppcomponent_add.ProbOfClose">Prob. of Close</dhv:label>
      </td>
      <td>
        <%= accountsComponentDetails.getCloseProbValue() %>%
      </td>
    </tr>
    <tr class="containerBody">
      <td nowrap class="formLabel">
        <dhv:label name="accounts.accounts_contacts_oppcomponent_add.EstCloseDate">Est. Close Date</dhv:label>
      </td>
      <td>
        <zeroio:tz timestamp="<%= accountsComponentDetails.getCloseDate() %>" dateOnly="true" timeZone="<%= accountsComponentDetails.getCloseDateTimeZone() %>" showTimeZone="true" default="&nbsp;"/>
        <% if(!User.getTimeZone().equals(accountsComponentDetails.getCloseDateTimeZone())){%>
        <br />
        <zeroio:tz timestamp="<%= accountsComponentDetails.getCloseDate() %>" timeZone="<%= User.getTimeZone() %>" showTimeZone="true" default="&nbsp;"/>
        <% } %>
      </td>
    </tr>
    <dhv:include name="opportunity.lowEstimate,pipeline-lowEstimate"  none="true">
    <tr class="containerBody">
      <td nowrap class="formLabel">
        <dhv:label name="accounts.accounts_contacts_oppcomponent_add.LowEstimate">Low Estimate</dhv:label>
      </td>
      <td>
        <zeroio:currency value="<%= accountsComponentDetails.getLow() %>" code='<%= applicationPrefs.get("SYSTEM.CURRENCY") %>' locale="<%= User.getLocale() %>" default="&nbsp;"/>
      </td>
    </tr>
    </dhv:include>
    <tr class="containerBody">
      <td nowrap class="formLabel">
        <dhv:label name="accounts.accounts_contacts_oppcomponent_add.BestGuess">Best Guess</dhv:label>
      </td>
      <td>
        <zeroio:currency value="<%= accountsComponentDetails.getGuess() %>" code='<%= applicationPrefs.get("SYSTEM.CURRENCY") %>' locale="<%= User.getLocale() %>" default="&nbsp;"/>
      </td>
    </tr>
    <tr class="containerBody">
      <td nowrap class="formLabel">
        <dhv:label name="accounts.accounts_contacts_oppcomponent_add.HighEstimate">High Estimate</dhv:label>
      </td>
      <td>
        <zeroio:currency value="<%= accountsComponentDetails.getHigh() %>" code='<%= applicationPrefs.get("SYSTEM.CURRENCY") %>' locale="<%= User.getLocale() %>" default="&nbsp;"/>
      </td>
    </tr>
    <dhv:include name="opportunity.termsAndUnits" none="true">
    <tr class="containerBody">
      <td nowrap class="formLabel">
        <dhv:label name="accounts.accounts_contacts_oppcomponent_add.EstTerm">Est. Term</dhv:label>
      </td>
      <td><%= accountsComponentDetails.getTerms() %>
        <dhv:evaluate if='<%= accountsComponentDetails.getUnits().equals("M") %>'>
           <dhv:label name="accounts.accounts_contacts_oppcomponent_details.months">months</dhv:label>
        </dhv:evaluate><dhv:evaluate if='<%= accountsComponentDetails.getUnits().equals("W") %>'>
          <dhv:label name="accounts.accounts_contacts_oppcomponent_details.weeks">weeks</dhv:label>
        </dhv:evaluate>
      </td>
    </tr>
    </dhv:include>
    <dhv:include name="opportunity.currentStage" none="true">
    <tr class="containerBody">
      <td nowrap class="formLabel">
        <dhv:label name="accounts.accounts_contacts_oppcomponent_details.CurrentStage">Current Stage</dhv:label>
      </td>
      <td>
        <%= toHtml(accountsComponentDetails.getStageName()) %>&nbsp;
      </td>
    </tr>
    </dhv:include>
    <dhv:include name="opportunity.details.modified" none="true">
    <tr class="containerBody">
      <td nowrap class="formLabel">
        <dhv:label name="accounts.accounts_contacts_calls_details.Modified">Modified</dhv:label>
      </td>
      <td>
        <zeroio:tz timestamp="<%= accountsComponentDetails.getEntered() %>" timeZone="<%= User.getTimeZone() %>" showTimeZone="true" />
      </td>
    </tr>
    </dhv:include>
    <dhv:include name="opportunity.details.modifiedby" none="true">
    <tr class="containerBody">
      <td nowrap class="formLabel">
        <dhv:label name="accounts.accounts_fields_list.ModifiedBy">Modified By</dhv:label>
      </td>
      <td>
        <dhv:username id="<%= accountsComponentDetails.getEnteredBy() %>"/>
      </td>
    </tr>
    </dhv:include>
    <tr class="containerBody">
      <td class="formLabel">
        <dhv:label name="accounts.accounts_contacts_detailsimport.Owner">Owner</dhv:label>
      </td>
      <td>
        <dhv:username id="<%= accountsComponentDetails.getOwner() %>"/>
      </td>
    </tr>
  </table>
  <%= addHiddenParams(request, "viewSource|popup|popupType") %>
</dhv:container>
