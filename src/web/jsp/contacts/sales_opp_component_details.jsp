<%-- 
  - Copyright(c) 2007 Dark Horse Ventures LLC (http://www.centriccrm.com/) All
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
  - Version: $Id: 
  - Description:
  --%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ page import="java.util.*,java.text.DateFormat,org.aspcfs.modules.pipeline.base.*,com.zeroio.iteam.base.*, org.aspcfs.modules.base.Constants" %>
<jsp:useBean id="contactDetails" class="org.aspcfs.modules.contacts.base.Contact" scope="request"/>
<jsp:useBean id="opportunityHeader" class="org.aspcfs.modules.pipeline.base.OpportunityHeader" scope="request"/>
<jsp:useBean id="opportunityComponent" class="org.aspcfs.modules.pipeline.base.OpportunityComponent" scope="request"/>
<jsp:useBean id="componentLogDetails" class="org.aspcfs.modules.pipeline.base.OpportunityComponentLog" scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<jsp:useBean id="applicationPrefs" class="org.aspcfs.controller.ApplicationPrefs" scope="application"/>
<jsp:useBean id="from" class="java.lang.String" scope="request" />
<jsp:useBean id="listForm" class="java.lang.String" scope="request" />
<%@ include file="../initPage.jsp" %>
<dhv:evaluate if="<%= !isPopup(request) %>">
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="Sales.do"><dhv:label name="Leads" mainMenuItem="true">Leads</dhv:label></a> >
<% if (from != null && "list".equals(from) && !"dashboard".equals(from)) { %>
  <a href="Sales.do?command=List&listForm=<%= (listForm != null? listForm:"") %>"><dhv:label name="accounts.SearchResults">Search Results</dhv:label></a> >
<%}%>
<a href="Sales.do?command=Details&contactId=<%= contactDetails.getId() %><%= addLinkParams(request, "popup|popupType|actionId|from|listForm") %>"><dhv:label name="sales.leadDetails">Lead Details</dhv:label></a> >
<a href="SalesOpportunities.do?command=ViewOpps&contactId=<%= contactDetails.getId() %><%= addLinkParams(request, "popup|popupType|actionId|from|listForm") %>"><dhv:label name="accounts.accounts_contacts_oppcomponent_add.Opportunities">Opportunities</dhv:label></a> >
<a href="SalesOpportunities.do?command=DetailsOpp&headerId=<%= opportunityHeader.getId() %>&contactId=<%= contactDetails.getId() %><%= addLinkParams(request, "popup|popupType|actionId|from|listForm") %>"><dhv:label name="accounts.accounts_contacts_oppcomponent_add.OpportunityDetails">Opportunity Details</dhv:label></a> >
<a href="SalesOpportunitiesComponents.do?command=ComponentHistory&contactId=<%= contactDetails.getId() %>&headerId=<%= opportunityHeader.getId() %>&id=<%= opportunityComponent.getId() %><%= addLinkParams(request, "popup|popupType|actionId|from|listForm") %>"><dhv:label name="accounts.accounts_contacts_oppcomponent.componentLog">Componet Log</dhv:label></a> >
<dhv:label name="accounts.accounts_contacts_oppcomponent.componentLog">Componet Log</dhv:label>
</td>
</tr>
</table>
<%-- End Trails --%>
</dhv:evaluate>
<%-- Begin container --%>
<dhv:container name="leads" selected="opportunities" object="contactDetails" param='<%= "id=" + contactDetails.getId() %>' appendToUrl='<%= addLinkParams(request, "popup|popupType|actionId|from|listForm") %>'>
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
        <%= toHtml(componentLogDetails.getDescription()) %>
      </td>
    </tr>
    <tr class="containerBody">
      <td nowrap class="formLabel">
        <dhv:label name="accounts.accounts_contacts_oppcomponent_add.ProbOfClose">Prob. of Close</dhv:label>
      </td>
      <td>
        <%= componentLogDetails.getCloseProbValue() %>%
      </td>
    </tr>
    <tr class="containerBody">
      <td nowrap class="formLabel">
        <dhv:label name="accounts.accounts_contacts_oppcomponent_add.EstCloseDate">Est. Close Date</dhv:label>
      </td>
      <td>
        <zeroio:tz timestamp="<%= componentLogDetails.getCloseDate() %>" dateOnly="true" timeZone="<%= componentLogDetails.getCloseDateTimeZone() %>" showTimeZone="true" default="&nbsp;"/>
        <% if(!User.getTimeZone().equals(componentLogDetails.getCloseDateTimeZone())){%>
        <br />
        <zeroio:tz timestamp="<%= componentLogDetails.getCloseDate() %>" timeZone="<%= User.getTimeZone() %>" showTimeZone="true" default="&nbsp;"/>
        <% } %>
      </td>
    </tr>
    <dhv:include name="opportunity.lowEstimate,pipeline-lowEstimate"  none="true">
    <tr class="containerBody">
      <td nowrap class="formLabel">
        <dhv:label name="accounts.accounts_contacts_oppcomponent_add.LowEstimate">Low Estimate</dhv:label>
      </td>
      <td>
        <zeroio:currency value="<%= componentLogDetails.getLow() %>" code='<%= applicationPrefs.get("SYSTEM.CURRENCY") %>' locale="<%= User.getLocale() %>" default="&nbsp;"/>
      </td>
    </tr>
    </dhv:include>
    <tr class="containerBody">
      <td nowrap class="formLabel">
        <dhv:label name="accounts.accounts_contacts_oppcomponent_add.BestGuess">Best Guess</dhv:label>
      </td>
      <td>
        <zeroio:currency value="<%= componentLogDetails.getGuess() %>" code='<%= applicationPrefs.get("SYSTEM.CURRENCY") %>' locale="<%= User.getLocale() %>" default="&nbsp;"/>
      </td>
    </tr>
    <tr class="containerBody">
      <td nowrap class="formLabel">
        <dhv:label name="accounts.accounts_contacts_oppcomponent_add.HighEstimate">High Estimate</dhv:label>
      </td>
      <td>
        <zeroio:currency value="<%= componentLogDetails.getHigh() %>" code='<%= applicationPrefs.get("SYSTEM.CURRENCY") %>' locale="<%= User.getLocale() %>" default="&nbsp;"/>
      </td>
    </tr>
    <dhv:include name="opportunity.termsAndUnits" none="true">
    <tr class="containerBody">
      <td nowrap class="formLabel">
        <dhv:label name="accounts.accounts_contacts_oppcomponent_add.EstTerm">Est. Term</dhv:label>
      </td>
      <td><%= componentLogDetails.getTerms() %>
        <dhv:evaluate if='<%= componentLogDetails.getUnits().equals("M") %>'>
           <dhv:label name="accounts.accounts_contacts_oppcomponent_details.months">months</dhv:label>
        </dhv:evaluate><dhv:evaluate if='<%= componentLogDetails.getUnits().equals("W") %>'>
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
        <%= toHtml(componentLogDetails.getStageName()) %>&nbsp;
      </td>
    </tr>
    </dhv:include>
    <dhv:include name="opportunity.details.modified" none="true">
    <tr class="containerBody">
      <td nowrap class="formLabel">
        <dhv:label name="accounts.accounts_contacts_calls_details.Modified">Modified</dhv:label>
      </td>
      <td>
        <zeroio:tz timestamp="<%= componentLogDetails.getEntered() %>" timeZone="<%= User.getTimeZone() %>" showTimeZone="true" />
      </td>
    </tr>
    </dhv:include>
    <dhv:include name="opportunity.details.modifiedby" none="true">
    <tr class="containerBody">
      <td nowrap class="formLabel">
        <dhv:label name="accounts.accounts_fields_list.ModifiedBy">Modified By</dhv:label>
      </td>
      <td>
        <dhv:username id="<%= componentLogDetails.getEnteredBy() %>"/>
      </td>
    </tr>
    </dhv:include>
    <tr class="containerBody">
      <td class="formLabel">
        <dhv:label name="accounts.accounts_contacts_detailsimport.Owner">Owner</dhv:label>
      </td>
      <td>
        <dhv:username id="<%= componentLogDetails.getOwner() %>"/>
      </td>
    </tr>
  </table>
  <%= addHiddenParams(request, "viewSource|from|listForm") %>
</dhv:container>
