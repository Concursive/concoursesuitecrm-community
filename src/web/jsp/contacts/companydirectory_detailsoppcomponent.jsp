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
<%@ page import="java.util.*,java.text.DateFormat,org.aspcfs.modules.contacts.base.*,com.zeroio.iteam.base.*" %>
<jsp:useBean id="ContactDetails" class="org.aspcfs.modules.contacts.base.Contact" scope="request"/>
<jsp:useBean id="opportunityHeader" class="org.aspcfs.modules.pipeline.base.OpportunityHeader" scope="request"/>
<jsp:useBean id="oppComponentDetails" class="org.aspcfs.modules.pipeline.base.OpportunityComponent" scope="request"/>
<jsp:useBean id="environmentSelect" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="competitorsSelect" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="compellingEventSelect" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="budgetSelect" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<jsp:useBean id="applicationPrefs" class="org.aspcfs.controller.ApplicationPrefs" scope="application"/>
<%@ include file="../initPage.jsp" %>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popURL.js"></script>
<%if (isPopup(request)) {%>
<script language="JavaScript" type="text/javascript" src="javascript/scrollReload.js"></script>
<%}%>
<script type="text/javascript">
  function reopenOnDelete() {
    try {
      if ('<%= isPopup(request) %>' != 'true') {
        scrollReload('ExternalContactsOpps.do?command=ViewOpps&contactId=<%= ContactDetails.getId() %>');
      } else {
        scrollReload('ExternalContactsOpps.do?command=ViewOpps&contactId=<%= ContactDetails.getId() %>&popup=true');
        var oppId = -1;
        try {
          oppId = opener.reopenOpportunity('<%= opportunityHeader.getId() %>');
        } catch (oException) {
        }
        if (oppId != '<%= opportunityHeader.getId() %>') {
          opener.reopen();
        }
      }
    } catch (oException) {
    }
  }

  function reopenOpportunity(id) {
    if (id == '<%= opportunityHeader.getId() %>') {
      scrollReload('ExternalContactsOpps.do?command=ViewOpps&contactId=<%= ContactDetails.getId() %><%= isPopup(request)?"&popup=true":"" %>');
      return id;
    } else {
      return '<%= opportunityHeader.getId() %>';
    }
  }
</script>
<form name="componentDetails" action="ExternalContactsOpps.do?command=ModifyComponent&id=<%= oppComponentDetails.getId() %>" method="post">
<dhv:evaluate if="<%= !isPopup(request) %>">
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="ExternalContacts.do"><dhv:label name="Contacts" mainMenuItem="true">Contacts</dhv:label></a> >
<a href="ExternalContacts.do?command=SearchContacts"><dhv:label name="accounts.SearchResults">Search Results</dhv:label></a> >
<a href="ExternalContacts.do?command=ContactDetails&id=<%= ContactDetails.getId() %>"><dhv:label name="accounts.accounts_contacts_add.ContactDetails">Contact Details</dhv:label></a> >
<a href="ExternalContactsOpps.do?command=ViewOpps&contactId=<%= ContactDetails.getId() %>"><dhv:label name="accounts.accounts_contacts_oppcomponent_add.Opportunities">Opportunities</dhv:label></a> >
<a href="ExternalContactsOpps.do?command=DetailsOpp&headerId=<%= oppComponentDetails.getHeaderId() %>&contactId=<%= ContactDetails.getId() %>"><dhv:label name="accounts.accounts_contacts_oppcomponent_add.OpportunityDetails">Opportunity Details</dhv:label></a> >
<dhv:label name="accounts.accounts_contacts_oppcomponent_add.ComponentDetails">Component Details</dhv:label>
</td>
</tr>
</table>
<%-- End Trails --%>
</dhv:evaluate>
<dhv:container name="contacts" selected="opportunities" object="ContactDetails" param="<%= "id=" + ContactDetails.getId() %>" appendToUrl="<%= addLinkParams(request, "popup|popupType|actionId") %>">
  <img src="images/icons/stock_form-currency-field-16.gif" border="0" align="absmiddle">
  <strong><%= toHtml(opportunityHeader.getDescription()) %></strong>
  <% FileItem thisFile = new FileItem(); %>
  <dhv:evaluate if="<%= opportunityHeader.hasFiles() %>">
    <%= thisFile.getImageTag() %>
  </dhv:evaluate>
  <dhv:evaluate if="<%= ContactDetails.getEnabled() && !ContactDetails.isTrashed() && !opportunityHeader.isTrashed() %>">
    <dhv:permission name="contacts-external_contacts-opportunities-edit,contacts-external_contacts-opportunities-delete">
      <br>
      <br>
    </dhv:permission>
    <dhv:sharing primaryBean="opportunityHeader" secondaryBeans="ContactDetails" action="edit"><input type="button" value="<dhv:label name="global.button.modify">Modify</dhv:label>" onClick="javascript:this.form.action='ExternalContactsOppComponents.do?command=ModifyComponent&id=<%= oppComponentDetails.getId() %>&headerId=<%= oppComponentDetails.getHeaderId() %>&contactId=<%= ContactDetails.getId() %>';submit();"></dhv:sharing>
    <dhv:sharing primaryBean="opportunityHeader" secondaryBeans="ContactDetails" action="delete"><input type="button" value="<dhv:label name="global.button.delete">Delete</dhv:label>" onClick="javascript:popURLReturn('ExternalContactsOppComponents.do?command=ConfirmComponentDelete&contactId=<%= ContactDetails.getId() %>&id=<%= oppComponentDetails.getId() %>&popup=true<%= isPopup(request)?"&sourcePopup=true":"" %><%= addLinkParams(request, "popupType|actionId") %>','ExternalContactsOpps.do?command=DetailsOpp&contactId=<%= ContactDetails.getId() %>', 'Delete_opp','320','200','yes','no')"></dhv:sharing>
    <dhv:permission name="contacts-external_contacts-opportunities-edit,contacts-external_contacts-opportunities-delete">
      <br>&nbsp;
    </dhv:permission>
  </dhv:evaluate>
  <table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
    <tr>
      <th colspan="2">
        <strong><%= toHtml(oppComponentDetails.getDescription()) %></strong>
      </th>
    </tr>
    <tr class="containerBody">
      <td class="formLabel" nowrap>
        <dhv:label name="accounts.accounts_contacts_detailsimport.Owner">Owner</dhv:label>
      </td>
      <td valign="center">
        <dhv:username id="<%= oppComponentDetails.getOwner() %>"/>
        <dhv:evaluate if="<%= !(oppComponentDetails.getHasEnabledOwnerAccount()) %>"><font color="red">*</font></dhv:evaluate>
      </td>
    </tr>
    <dhv:evaluate if="<%= hasText(oppComponentDetails.getTypes().valuesAsString()) %>">
    <tr class="containerBody">
      <td class="formLabel" nowrap>
        <dhv:label name="accounts.accounts_contacts_oppcomponent_add.OpportunityTypes">Opportunity Type(s)</dhv:label>
      </td>
      <td>
        <%= toHtml(oppComponentDetails.getTypes().valuesAsString()) %>
       </td>
    </tr>
    </dhv:evaluate>
    <dhv:evaluate if="<%= hasText(oppComponentDetails.getNotes()) %>">
    <tr class="containerBody">
      <td valign="top" nowrap class="formLabel"><dhv:label name="accounts.accounts_contacts_oppcomponent_add.AdditionalNotes">Additional Notes</dhv:label></td>
      <td valign="top"><%= toHtml(oppComponentDetails.getNotes()) %></td>
    </tr>
    </dhv:evaluate>
    <tr class="containerBody">
      <td class="formLabel" nowrap>
        <dhv:label name="accounts.accounts_contacts_oppcomponent_add.ProbOfClose">Prob. of Close</dhv:label>
      </td>
      <td>
        <%= oppComponentDetails.getCloseProbValue() %>%
      </td>
    </tr>
    <tr class="containerBody">
      <td class="formLabel" nowrap>
        <dhv:label name="accounts.accounts_contacts_oppcomponent_add.EstCloseDate">Est. Close Date</dhv:label>
      </td>
      <td>
        <zeroio:tz timestamp="<%= oppComponentDetails.getCloseDate() %>" dateOnly="true" timeZone="<%= oppComponentDetails.getCloseDateTimeZone() %>" showTimeZone="true" default="&nbsp;"/>
        <% if(!User.getTimeZone().equals(oppComponentDetails.getCloseDateTimeZone())){%>
        <br />
        <zeroio:tz timestamp="<%= oppComponentDetails.getCloseDate() %>" timeZone="<%= User.getTimeZone() %>" showTimeZone="true" default="&nbsp;"/>
        <% } %>
      </td>
    </tr>
    <tr class="containerBody">
      <td class="formLabel" nowrap>
        <dhv:label name="accounts.accounts_contacts_oppcomponent_add.LowEstimate">Low Estimate</dhv:label>
      </td>
      <td>
        <zeroio:currency value="<%= oppComponentDetails.getLow() %>" code="<%= applicationPrefs.get("SYSTEM.CURRENCY") %>" locale="<%= User.getLocale() %>" default="&nbsp;"/>
      </td>
    </tr>
    <tr class="containerBody">
      <td class="formLabel" nowrap>
        <dhv:label name="accounts.accounts_contacts_oppcomponent_add.BestGuess">Best Guess</dhv:label>
      </td>
      <td>
        <zeroio:currency value="<%= oppComponentDetails.getGuess() %>" code="<%= applicationPrefs.get("SYSTEM.CURRENCY") %>" locale="<%= User.getLocale() %>" default="&nbsp;"/>
      </td>
    </tr>
    <tr class="containerBody">
      <td class="formLabel" nowrap>
        <dhv:label name="accounts.accounts_contacts_oppcomponent_add.HighEstimate">High Estimate</dhv:label>
      </td>
      <td>
        <zeroio:currency value="<%= oppComponentDetails.getHigh() %>" code="<%= applicationPrefs.get("SYSTEM.CURRENCY") %>" locale="<%= User.getLocale() %>" default="&nbsp;"/>
      </td>
    </tr>
    <tr class="containerBody">
      <td class="formLabel" nowrap>
        <dhv:label name="accounts.accounts_contacts_oppcomponent_add.EstTerm">Est. Term</dhv:label>
      </td>
      <td>
        <%= oppComponentDetails.getTerms() %> <dhv:label name="accounts.accounts_contacts_oppcomponent_details.months">months</dhv:label>
      </td>
    </tr>
    <tr class="containerBody">
      <td class="formLabel" nowrap>
        <dhv:label name="accounts.accounts_contacts_oppcomponent_details.CurrentStage">Current Stage</dhv:label>
      </td>
      <td>
        <%= toHtml(oppComponentDetails.getStageName()) %>&nbsp;
      </td>
    </tr>
    <tr class="containerBody">
      <td class="formLabel" nowrap>
        <dhv:label name="accounts.accounts_contacts_oppcomponent_details.CurrentStageDate">Current Stage Date</dhv:label>
      </td>
      <td>
        <zeroio:tz timestamp="<%= oppComponentDetails.getStageDate() %>" dateOnly="true" default="&nbsp;"/>
      </td>
    </tr>
    <dhv:evaluate if="<%= environmentSelect.getEnabledElementCount() > 0 %>">
    <tr class="containerBody">
      <td nowrap class="formLabel">
        <dhv:label name="pipeline.environment">Environment</dhv:label>
      </td>
      <td>
      <zeroio:debug value="<%= "JSP:: the environment is "+ oppComponentDetails.getEnvironment() %>"/>
      <%= toHtml(environmentSelect.getSelectedValue(oppComponentDetails.getEnvironment())) %>
      </td>
    </tr>
    </dhv:evaluate>
    <dhv:evaluate if="<%= competitorsSelect.getEnabledElementCount() > 0 %>">
    <tr class="containerBody">
      <td nowrap class="formLabel">
        <dhv:label name="pipeline.competitors">Competitors</dhv:label>
      </td>
      <td>
      <%= toHtml(competitorsSelect.getSelectedValue(oppComponentDetails.getCompetitors())) %>
      </td>
    </tr>
    </dhv:evaluate>
    <dhv:evaluate if="<%= compellingEventSelect.getEnabledElementCount() > 0 %>">
    <tr class="containerBody">
      <td nowrap class="formLabel">
        <dhv:label name="pipeline.compellingEvent">Compelling Event</dhv:label>
      </td>
      <td>
      <%= toHtml(compellingEventSelect.getSelectedValue(oppComponentDetails.getCompellingEvent())) %>
      </td>
    </tr>
    </dhv:evaluate>
    <dhv:evaluate if="<%= budgetSelect.getEnabledElementCount() > 0 %>">
    <tr class="containerBody">
      <td nowrap class="formLabel">
        <dhv:label name="project.budget">Budget</dhv:label>
      </td>
      <td>
      <%= toHtml(budgetSelect.getSelectedValue(oppComponentDetails.getBudget())) %>
      </td>
    </tr>
    </dhv:evaluate>
    <tr class="containerBody">
      <td class="formLabel" nowrap>
        <dhv:label name="accounts.accounts_contacts_oppcomponent_details.EstCommission">Est. Commission</dhv:label>
      </td>
      <td>
        <%= oppComponentDetails.getCommissionValue() %>%
      </td>
    </tr>
  <dhv:evaluate if="<%= hasText(oppComponentDetails.getAlertText()) %>">
     <tr class="containerBody">
      <td class="formLabel" nowrap>
        <dhv:label name="accounts.accounts_add.AlertDescription">Alert Description</dhv:label>
      </td>
      <td>
         <%= toHtml(oppComponentDetails.getAlertText()) %>
      </td>
    </tr>
  </dhv:evaluate>
  <dhv:evaluate if="<%= oppComponentDetails.getAlertDate() != null %>">
    <tr class="containerBody">
      <td class="formLabel" nowrap>
        <dhv:label name="accounts.accounts_add.AlertDate">Alert Date</dhv:label>
      </td>
      <td>
        <zeroio:tz timestamp="<%= oppComponentDetails.getAlertDate() %>" dateOnly="true" timeZone="<%= oppComponentDetails.getAlertDateTimeZone() %>" showTimeZone="true"  default="&nbsp;"/>
        <% if(!User.getTimeZone().equals(oppComponentDetails.getAlertDateTimeZone())){%>
        <br>
        <zeroio:tz timestamp="<%= oppComponentDetails.getAlertDate() %>" timeZone="<%= User.getTimeZone() %>" showTimeZone="true"  default="&nbsp;" />
        <% } %>
      </td>
    </tr>
  </dhv:evaluate>
    <tr class="containerBody">
      <td class="formLabel" nowrap>
        <dhv:label name="accounts.accounts_calls_list.Entered">Entered</dhv:label>
      </td>
      <td>
        <dhv:username id="<%= oppComponentDetails.getEnteredBy() %>"/>
        <zeroio:tz timestamp="<%= oppComponentDetails.getEntered() %>" timeZone="<%= User.getTimeZone() %>" showTimeZone="true" />
      </td>
    </tr>
    <tr class="containerBody">
      <td class="formLabel" nowrap>
        <dhv:label name="accounts.accounts_contacts_calls_details.Modified">Modified</dhv:label>
      </td>
      <td>
        <dhv:username id="<%= oppComponentDetails.getModifiedBy() %>"/>
        <zeroio:tz timestamp="<%= oppComponentDetails.getModified() %>" timeZone="<%= User.getTimeZone() %>" showTimeZone="true" />
      </td>
    </tr>
  </table>
  <dhv:evaluate if="<%= ContactDetails.getEnabled() && !ContactDetails.isTrashed() && !opportunityHeader.isTrashed() %>">
    <dhv:permission name="contacts-external_contacts-opportunities-edit,contacts-external_contacts-opportunities-delete"><br></dhv:permission>
    <dhv:sharing primaryBean="opportunityHeader" secondaryBeans="ContactDetails" action="edit"><input type="button" value="<dhv:label name="global.button.modify">Modify</dhv:label>" onClick="javascript:this.form.action='ExternalContactsOppComponents.do?command=ModifyComponent&id=<%= oppComponentDetails.getId() %>&headerId=<%= oppComponentDetails.getHeaderId() %>&contactId=<%= ContactDetails.getId() %>';submit();"></dhv:sharing>
    <dhv:sharing primaryBean="opportunityHeader" secondaryBeans="ContactDetails" action="delete"><input type="button" value="<dhv:label name="global.button.delete">Delete</dhv:label>" onClick="javascript:popURLReturn('ExternalContactsOppComponents.do?command=ConfirmComponentDelete&contactId=<%= ContactDetails.getId() %>&id=<%= oppComponentDetails.getId() %>&popup=true<%= isPopup(request)?"&sourcePopup=true":"" %><%= addLinkParams(request, "popupType|actionId") %>','ExternalContactsOpps.do?command=DetailsOpp&contactId=<%= ContactDetails.getId() %>', 'Delete_opp','320','200','yes','no')"></dhv:sharing>
    <dhv:permission name="contacts-external_contacts-opportunities-edit,contacts-external_contacts-opportunities-delete"></dhv:permission></td>
  </dhv:evaluate>
  <input type="hidden" name="headerId" value="<%= oppComponentDetails.getHeaderId() %>">
  <input type="hidden" name="id" value="<%= oppComponentDetails.getId() %>">
  <input type="hidden" name="contactId" value="<%= ContactDetails.getId() %>">
  <input type="hidden" name="actionSource" value="ExternalContactsOppComponents">
  <%= addHiddenParams(request, "popup|popupType|actionId") %>
</dhv:container>
</form>
