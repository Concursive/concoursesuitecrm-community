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
<%@ page import="java.util.*,java.text.DateFormat,org.aspcfs.modules.accounts.base.*,com.zeroio.iteam.base.*" %>
<jsp:useBean id="OrgDetails" class="org.aspcfs.modules.accounts.base.Organization" scope="request"/>
<jsp:useBean id="OpportunityHeader" class="org.aspcfs.modules.pipeline.base.OpportunityHeader" scope="request"/>
<jsp:useBean id="OppComponentDetails" class="org.aspcfs.modules.pipeline.base.OpportunityComponent" scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<jsp:useBean id="applicationPrefs" class="org.aspcfs.controller.ApplicationPrefs" scope="application"/>
<%@ include file="../initPage.jsp" %>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popURL.js"></script>
<script type="text/javascript">
function reopenOpportunity(id) {
  if (id == '<%= OpportunityHeader.getId() %>') {
    scrollReload('Opportunities.do?command=View&orgId=<%= OrgDetails.getOrgId() %><%= isPopup(request)?"&popup=true":"" %>');
    return id;
  } else {
    return '<%= OpportunityHeader.getId() %>';
  }
}
</script>
<form name="componentDetails" action="Opportunities.do?command=ModifyComponent&id=<%= OppComponentDetails.getId() %>" method="post">
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="Accounts.do"><dhv:label name="accounts.accounts">Accounts</dhv:label></a> > 
<a href="Accounts.do?command=Search"><dhv:label name="accounts.SearchResults">Search Results</dhv:label></a> >
<a href="Accounts.do?command=Details&orgId=<%= OrgDetails.getOrgId() %>"><dhv:label name="accounts.details">Account Details</dhv:label></a> >
<a href="Opportunities.do?command=View&orgId=<%= OrgDetails.getOrgId() %>"><dhv:label name="accounts.accounts_contacts_oppcomponent_add.Opportunities">Opportunities</dhv:label></a> >
<a href="Opportunities.do?command=Details&headerId=<%= OppComponentDetails.getHeaderId() %>&orgId=<%= OrgDetails.getId() %>&reset=true"><dhv:label name="accounts.accounts_contacts_oppcomponent_add.OpportunityDetails">Opportunity Details</dhv:label></a> >
<dhv:label name="accounts.accounts_contacts_oppcomponent_add.ComponentDetails">Component Details</dhv:label>
</td>
</tr>
</table>
<%-- End Trails --%>
<dhv:container name="accounts" selected="opportunities" object="OrgDetails" param="<%= "orgId=" + OrgDetails.getOrgId() %>">
  <img src="images/icons/stock_form-currency-field-16.gif" border="0" align="absmiddle">
  <strong><%= toHtml(OpportunityHeader.getDescription()) %></strong>
  <% FileItem thisFile = new FileItem(); %>
  <dhv:evaluate if="<%= OpportunityHeader.hasFiles() %>">
    <%= thisFile.getImageTag("-23") %>
  </dhv:evaluate>
  <input type="hidden" name="headerId" value="<%= OppComponentDetails.getHeaderId() %>">
  <input type="hidden" name="id" value="<%= OppComponentDetails.getId() %>">
  <input type="hidden" name="orgId" value="<%= OrgDetails.getId() %>">
  <dhv:permission name="accounts-accounts-opportunities-edit">
    <br><br>
    <input type="button" value="<dhv:label name="global.button.modify">Modify</dhv:label>" onClick="javascript:this.form.action='OpportunitiesComponents.do?command=ModifyComponent&id=<%= OppComponentDetails.getId() %>&orgId=<%= OrgDetails.getId() %>';submit();">
  </dhv:permission>
  <dhv:permission name="accounts-accounts-opportunities-delete">
    <input type="button" value="<dhv:label name="global.button.delete">Delete</dhv:label>" onClick="javascript:popURLReturn('OpportunitiesComponents.do?command=ConfirmComponentDelete&orgId=<%= OrgDetails.getId() %>&id=<%= OppComponentDetails.getId() %>&popup=true','Opportunities.do?command=DetailsOpp&orgId=<%= OrgDetails.getId() %>', 'Delete_opp','320','200','yes','no')">
  </dhv:permission>
  <dhv:permission name="accounts-accounts-opportunities-edit,accounts-accounts-opportunities-delete"></dhv:permission>
  <br />
  <br />
  <table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
    <tr>
      <th colspan="2">
        <strong><%= toHtml(OppComponentDetails.getDescription()) %></strong>
      </th>
    </tr>
    <tr class="containerBody">
      <td class="formLabel">
        <dhv:label name="accounts.accounts_contacts_detailsimport.Owner">Owner</dhv:label>
      </td>
      <td>
        <dhv:username id="<%= OppComponentDetails.getOwner() %>"/>
        <dhv:evaluate if="<%= !(OppComponentDetails.getHasEnabledOwnerAccount()) %>"><font color="red">*</font></dhv:evaluate>
      </td>
    </tr>
    <dhv:evaluate if="<%= hasText(OppComponentDetails.getTypes().valuesAsString()) %>">
    <tr class="containerBody">
      <td nowrap class="formLabel">
        <dhv:label name="accounts.accounts_contacts_oppcomponent_add.OpportunityTypes">Opportunity Type(s)</dhv:label>
      </td>
      <td>
        <%= toHtml(OppComponentDetails.getTypes().valuesAsString()) %>
       </td>
    </tr>
    </dhv:evaluate>
    <dhv:evaluate if="<%= hasText(OppComponentDetails.getNotes()) %>">
    <tr class="containerBody">
      <td valign="top" nowrap class="formLabel">
        <dhv:label name="accounts.accounts_contacts_oppcomponent_add.AdditionalNotes">Additional Notes</dhv:label>
      </td>
      <td valign="top">
        <%= toHtml(OppComponentDetails.getNotes()) %>
      </td>
    </tr>
    </dhv:evaluate>
    <tr class="containerBody">
      <td nowrap class="formLabel">
        <dhv:label name="accounts.accounts_contacts_oppcomponent_add.ProbOfClose">Prob. of Close</dhv:label>
      </td>
      <td>
        <%= OppComponentDetails.getCloseProbValue() %>%
      </td>
    </tr>
    <tr class="containerBody">
      <td nowrap class="formLabel">
        <dhv:label name="accounts.accounts_contacts_oppcomponent_add.EstCloseDate">Est. Close Date</dhv:label>
      </td>
      <td>
        <zeroio:tz timestamp="<%= OppComponentDetails.getCloseDate() %>" dateOnly="true" timeZone="<%= OppComponentDetails.getCloseDateTimeZone() %>" showTimeZone="true" default="&nbsp;"/>
        <% if(!User.getTimeZone().equals(OppComponentDetails.getCloseDateTimeZone())){%>
        <br>
        <zeroio:tz timestamp="<%= OppComponentDetails.getCloseDate() %>" timeZone="<%= User.getTimeZone() %>" showTimeZone="true" default="&nbsp;"/>
        <% } %>
      </td>
    </tr>
    <tr class="containerBody">
      <td nowrap class="formLabel">
        <dhv:label name="accounts.accounts_contacts_oppcomponent_add.LowEstimate">Low Estimate</dhv:label>
      </td>
      <td>
        <zeroio:currency value="<%= OppComponentDetails.getLow() %>" code="<%= applicationPrefs.get("SYSTEM.CURRENCY") %>" locale="<%= User.getLocale() %>" default="&nbsp;"/>
      </td>
    </tr>
    <tr class="containerBody">
      <td nowrap class="formLabel">
        <dhv:label name="accounts.accounts_contacts_oppcomponent_add.BestGuess">Best Guess</dhv:label>
      </td>
      <td>
        <zeroio:currency value="<%= OppComponentDetails.getGuess() %>" code="<%= applicationPrefs.get("SYSTEM.CURRENCY") %>" locale="<%= User.getLocale() %>" default="&nbsp;"/>
      </td>
    </tr>
    <tr class="containerBody">
      <td nowrap class="formLabel">
        <dhv:label name="accounts.accounts_contacts_oppcomponent_add.HighEstimate">High Estimate</dhv:label>
      </td>
      <td>
        <zeroio:currency value="<%= OppComponentDetails.getHigh() %>" code="<%= applicationPrefs.get("SYSTEM.CURRENCY") %>" locale="<%= User.getLocale() %>" default="&nbsp;"/>
      </td>
    </tr>
    <tr class="containerBody">
      <td nowrap class="formLabel">
        <dhv:label name="accounts.accounts_contacts_oppcomponent_add.EstTerm">Est. Term</dhv:label>
      </td>
      <td>
        <%= OppComponentDetails.getTerms() %> <dhv:label name="accounts.accounts_contacts_oppcomponent_details.months">months</dhv:label>
      </td>
    </tr>
    <tr class="containerBody">
      <td nowrap class="formLabel">
        <dhv:label name="accounts.accounts_contacts_oppcomponent_details.CurrentStage">Current Stage</dhv:label>
      </td>
      <td>
        <%= toHtml(OppComponentDetails.getStageName()) %>&nbsp;
      </td>
    </tr>
    <tr class="containerBody">
      <td nowrap class="formLabel">
        <dhv:label name="accounts.accounts_contacts_oppcomponent_details.CurrentStageDate">Current Stage Date</dhv:label>
      </td>
      <td>
        <zeroio:tz timestamp="<%= OppComponentDetails.getStageDate() %>" dateOnly="true" default="&nbsp;" timeZone="<%= User.getTimeZone() %>" showTimeZone="true"/>
      </td>
    </tr>
    <tr class="containerBody">
      <td nowrap class="formLabel">
        <dhv:label name="accounts.accounts_contacts_oppcomponent_details.EstCommission">Est. Commission</dhv:label>
      </td>
      <td>
        <%= OppComponentDetails.getCommissionValue() %>%
      </td>
    </tr>
  <dhv:evaluate if="<%= hasText(OppComponentDetails.getAlertText()) %>">
    <tr class="containerBody">
      <td nowrap class="formLabel">
        <dhv:label name="accounts.accounts_add.AlertDescription">Alert Description</dhv:label>
      </td>
      <td>
        <%= toHtml(OppComponentDetails.getAlertText()) %>
      </td>
    </tr>
  </dhv:evaluate>

  <dhv:evaluate if="<%= (OppComponentDetails.getAlertDate() != null) %>">
    <tr class="containerBody">
      <td nowrap class="formLabel">
        <dhv:label name="accounts.accounts_add.AlertDate">Alert Date</dhv:label>
      </td>
      <td>
        <zeroio:tz timestamp="<%= OppComponentDetails.getAlertDate() %>" dateOnly="true" timeZone="<%= OppComponentDetails.getAlertDateTimeZone() %>" showTimeZone="true"  default="&nbsp;"/>
        <% if(!User.getTimeZone().equals(OppComponentDetails.getAlertDateTimeZone())){%>
        <br />
        <zeroio:tz timestamp="<%= OppComponentDetails.getAlertDate() %>" timeZone="<%= User.getTimeZone() %>" showTimeZone="true"  default="&nbsp;" />
        <% } %>
      </td>
    </tr>
  </dhv:evaluate>
    <tr class="containerBody">
      <td nowrap class="formLabel">
        <dhv:label name="accounts.accounts_calls_list.Entered">Entered</dhv:label>
      </td>
      <td>
        <dhv:username id="<%= OppComponentDetails.getEnteredBy() %>"/>
        <zeroio:tz timestamp="<%= OppComponentDetails.getEntered() %>" timeZone="<%= User.getTimeZone() %>" showTimeZone="true"/>
      </td>
    </tr>
    <tr class="containerBody">
      <td nowrap class="formLabel">
        <dhv:label name="accounts.accounts_contacts_calls_details.Modified">Modified</dhv:label>
      </td>
      <td>
        <dhv:username id="<%= OppComponentDetails.getModifiedBy() %>"/>
        <zeroio:tz timestamp="<%= OppComponentDetails.getModified() %>" timeZone="<%= User.getTimeZone() %>" showTimeZone="true"/>
      </td>
    </tr>
  </table>
  <dhv:permission name="accounts-accounts-opportunities-edit,accounts-accounts-opportunities-delete"><br></dhv:permission>
  <dhv:permission name="accounts-accounts-opportunities-edit"><input type="button" value="<dhv:label name="global.button.modify">Modify</dhv:label>" onClick="javascript:this.form.action='OpportunitiesComponents.do?command=ModifyComponent&id=<%= OppComponentDetails.getId() %>&orgId=<%= OrgDetails.getId() %>';submit();"></dhv:permission>
  <dhv:permission name="accounts-accounts-opportunities-delete"><input type="button" value="<dhv:label name="global.button.delete">Delete</dhv:label>" onClick="javascript:popURLReturn('OpportunitiesComponents.do?command=ConfirmComponentDelete&orgId=<%=OrgDetails.getId()%>&id=<%= OppComponentDetails.getId() %>&popup=true','Opportunities.do?command=DetailsOpp&orgId=<%= OrgDetails.getId() %>', 'Delete_opp','320','200','yes','no')"></dhv:permission>
  <dhv:permission name="accounts-accounts-opportunities-edit,accounts-accounts-opportunities-delete"></dhv:permission></td>
</dhv:container>
</form>
