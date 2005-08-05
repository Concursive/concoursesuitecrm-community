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
<%@ page import="java.util.*,java.text.DateFormat,org.aspcfs.modules.contacts.base.*,org.aspcfs.modules.pipeline.base.OpportunityComponent,com.zeroio.iteam.base.*" %>
<jsp:useBean id="ContactDetails" class="org.aspcfs.modules.contacts.base.Contact" scope="request"/>
<jsp:useBean id="OpportunityHeader" class="org.aspcfs.modules.pipeline.base.OpportunityHeader" scope="request"/>
<jsp:useBean id="ComponentList" class="org.aspcfs.modules.pipeline.base.OpportunityComponentList" scope="request"/>
<jsp:useBean id="ComponentListInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<jsp:useBean id="OrgDetails" class="org.aspcfs.modules.accounts.base.Organization" scope="request"/>
<jsp:useBean id="AccountContactComponentListInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<jsp:useBean id="applicationPrefs" class="org.aspcfs.controller.ApplicationPrefs" scope="application"/>
<%@ include file="../initPage.jsp" %>
<%-- Initialize the drop-down menus --%>
<%@ include file="../initPopupMenu.jsp" %>
<%@ include file="accounts_contacts_opps_details_menu.jsp" %>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/spanDisplay.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popURL.js"></SCRIPT>
<script language="JavaScript" type="text/javascript">
  <%-- Preload image rollovers for drop-down menu --%>
  loadImages('select');

  function reopenOpportunity(id) {
    if (id == '<%= OpportunityHeader.getId() %>') {
      scrollReload('AccountContactsOpps.do?command=ViewOpps&contactId=<%= ContactDetails.getId() %><%= isPopup(request)?"&popup=true":"" %>');
      return id;
    } else {
      return '<%= OpportunityHeader.getId() %>';
    }
  }
</script>
<dhv:evaluate if="<%= !isPopup(request) %>">
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="Accounts.do"><dhv:label name="accounts.accounts">Accounts</dhv:label></a> > 
<% if (request.getParameter("return") == null) { %>
<a href="Accounts.do?command=Search"><dhv:label name="accounts.SearchResults">Search Results</dhv:label></a> >
<%} else if (request.getParameter("return").equals("dashboard")) {%>
<a href="Accounts.do?command=Dashboard"><dhv:label name="communications.campaign.Dashboard">Dashboard</dhv:label></a> >
<%}%>
<a href="Accounts.do?command=Details&orgId=<%=OrgDetails.getOrgId()%>"><dhv:label name="accounts.details">Account Details</dhv:label></a> >
<a href="Contacts.do?command=View&orgId=<%=OrgDetails.getOrgId()%>"><dhv:label name="accounts.Contacts">Contacts</dhv:label></a> >
<a href="Contacts.do?command=Details&id=<%=ContactDetails.getId()%>&orgId=<%=OrgDetails.getOrgId()%>"><dhv:label name="accounts.accounts_contacts_add.ContactDetails">Contact Details</dhv:label></a> >
<a href="AccountContactsOpps.do?command=ViewOpps&contactId=<%= ContactDetails.getId() %>"><dhv:label name="accounts.accounts_contacts_oppcomponent_add.Opportunities">Opportunities</dhv:label></a> >
<dhv:label name="accounts.accounts_contacts_oppcomponent_add.OpportunityDetails">Opportunity Details</dhv:label>
</td>
</tr>
</table>
<%-- End Trails --%>
</dhv:evaluate>
<dhv:container name="accounts" selected="contacts" object="OrgDetails" param="<%= "orgId=" + OrgDetails.getOrgId() %>">
  <dhv:container name="accountscontacts" selected="opportunities" object="ContactDetails" param="<%= "id=" + ContactDetails.getId() %>">
    <img src="images/icons/stock_form-currency-field-16.gif" border="0" align="absmiddle">
    <strong><%= toHtml(OpportunityHeader.getDescription()) %></strong>
    <% FileItem thisFile = new FileItem(); %>
    <dhv:evaluate if="<%= OpportunityHeader.hasFiles() %>">
      <%= thisFile.getImageTag("-23") %>
    </dhv:evaluate>
    <br />
    <dhv:evaluate if="<%= !OpportunityHeader.isTrashed() %>">
      <dhv:evaluate if="<%= ContactDetails.getEnabled() && !ContactDetails.isTrashed() %>">
        <dhv:permission name="accounts-accounts-contacts-opportunities-add">
          <br />
          <a href="AccountContactsOppComponents.do?command=Prepare&headerId=<%= OpportunityHeader.getId() %>&contactId=<%= OpportunityHeader.getContactLink() %><%= addLinkParams(request, "popup|popupType|actionId") %>"><dhv:label name="accounts.accounts_contacts_opps_details.AddAComponent">Add a Component</dhv:label></a><br>
        </dhv:permission>
      </dhv:evaluate>
    </dhv:evaluate>
    <%= addHiddenParams(request, "popup|popupType|actionId") %>
    <input type="hidden" name="actionSource" value="AccountContactsOppComponents">
    <dhv:pagedListStatus title="<%= showError(request, "actionError") %>" object="AccountContactComponentListInfo"/>
    <table cellpadding="4" cellspacing="0" border="0" width="100%" class="pagedList">
      <tr>
        <th nowrap>
          &nbsp;
        </th>
        <th nowrap>
          <strong><a href="AccountContactsOpps.do?command=DetailsOpp&headerId=<%= OpportunityHeader.getId() %>&contactId=<%= ContactDetails.getId() %>&column=oc.description<%= addLinkParams(request, "popup|popupType|actionId") %>"><dhv:label name="accounts.accounts_contacts_opps_details.Component">Component</dhv:label></a></strong>
          <%= AccountContactComponentListInfo.getSortIcon("oc.description") %>
        </th>
        <th nowrap>
          <strong><a href="AccountContactsOpps.do?command=DetailsOpp&headerId=<%= OpportunityHeader.getId() %>&contactId=<%= ContactDetails.getId() %>&column=oc.closed<%= addLinkParams(request, "popup|popupType|actionId") %>"><dhv:label name="accounts.accountasset_include.Status">Status</dhv:label></a></strong>
          <%= AccountContactComponentListInfo.getSortIcon("oc.closed") %>
        </th>
        <th nowrap>
          <strong><a href="AccountContactsOpps.do?command=DetailsOpp&headerId=<%= OpportunityHeader.getId() %>&contactId=<%= ContactDetails.getId() %>&column=oc.guessvalue<%= addLinkParams(request, "popup|popupType|actionId") %>"><dhv:label name="accounts.accounts_contacts_opps_details.GuessAmount">Guess Amount</dhv:label></a></strong>
          <%= AccountContactComponentListInfo.getSortIcon("oc.guessvalue") %>
        </th>
        <th nowrap>
          <strong><a href="AccountContactsOpps.do?command=DetailsOpp&headerId=<%= OpportunityHeader.getId() %>&contactId=<%= ContactDetails.getId() %>&column=oc.closedate<%= addLinkParams(request, "popup|popupType|actionId") %>"><dhv:label name="accounts.accounts_contacts_opps_details.CloseDate">Close Date</dhv:label></a></strong>
          <%= AccountContactComponentListInfo.getSortIcon("oc.closedate") %>
        </th>
        <th nowrap>
          <strong><a href="AccountContactsOpps.do?command=DetailsOpp&headerId=<%= OpportunityHeader.getId() %>&contactId=<%= ContactDetails.getId() %>&column=stagename<%= addLinkParams(request, "popup|popupType|actionId") %>"><dhv:label name="accounts.accounts_contacts_oppcomponent_details.CurrentStage">Current Stage</dhv:label></a></strong>
          <%= AccountContactComponentListInfo.getSortIcon("stagename") %>
        </th>
      </tr>
    <%
      Iterator j = ComponentList.iterator();
      if ( j.hasNext() ) {
        int rowid = 0;
        int i =0;
          while (j.hasNext()) {
            i++;
            rowid = (rowid != 1?1:2);
            OpportunityComponent oppComponent = (OpportunityComponent)j.next();
    %>
      <tr class="containerBody">
        <td width="8" valign="top" nowrap class="row<%= rowid %>">
          <%-- Use the unique id for opening the menu, and toggling the graphics --%>
          <%-- To display the menu, pass the actionId, accountId and the contactId--%>
          <dhv:evaluate if="<%= ContactDetails.getEnabled() && !ContactDetails.isTrashed() && !OpportunityHeader.isTrashed() %>">
            <a href="javascript:displayMenu('select<%= i %>','menuOpp','<%= OpportunityHeader.getId() %>', '<%= oppComponent.getId() %>', '<%= OpportunityHeader.getContactLink() %>','<%= OpportunityHeader.isTrashed()%>');" onMouseOver="over(0, <%= i %>)" onmouseout="out(0, <%= i %>); hideMenu('menuOpp');"><img src="images/select.gif" name="select<%= i %>" id="select<%= i %>" align="absmiddle" border="0"></a>
          </dhv:evaluate>
          <dhv:evaluate if="<%= !ContactDetails.getEnabled() || ContactDetails.isTrashed() || OpportunityHeader.isTrashed() %>">&nbsp;</dhv:evaluate>
        </td>
        <td width="100%" valign="top" class="row<%= rowid %>">
          <a href="AccountContactsOppComponents.do?command=DetailsComponent&contactId=<%= ContactDetails.getId() %>&id=<%= oppComponent.getId() %><%= addLinkParams(request, "popup|popupType|actionId") %>">
          <%= toHtml(oppComponent.getDescription()) %></a>
        </td>
        <td valign="top" align="center" nowrap class="row<%= rowid %>">
          <% if(oppComponent.getClosed() != null) {%>
           <font color="red"><dhv:label name="project.closed.lowercase">closed</dhv:label></font>
          <%} else {%>
           <font color="green"><dhv:label name="project.open.lowercase">open</dhv:label></font>
          <%}%>
        </td>
        <td valign="top" align="right" nowrap class="row<%= rowid %>">
          <zeroio:currency value="<%= oppComponent.getGuess() %>" code="<%= applicationPrefs.get("SYSTEM.CURRENCY") %>" locale="<%= User.getLocale() %>" default="&nbsp;"/>
        </td>
        <td valign="top" align="center" nowrap class="row<%= rowid %>">
          <zeroio:tz timestamp="<%= oppComponent.getCloseDate() %>" dateOnly="true" timeZone="<%= oppComponent.getCloseDateTimeZone() %>" showTimeZone="true" default="&nbsp;"/>
          <% if(!User.getTimeZone().equals(oppComponent.getCloseDateTimeZone())){%>
          <br />
          <zeroio:tz timestamp="<%= oppComponent.getCloseDate() %>" timeZone="<%= User.getTimeZone() %>" showTimeZone="true" default="&nbsp;"/>
          <% } %>
        </td>
        <td valign="top" align="center" nowrap class="row<%= rowid %>">
          <%= toHtml(oppComponent.getStageName()) %>
        </td>
      </tr>
    <%}%>
    <%} else {%>
      <tr class="containerBody">
        <td colspan="6">
          <dhv:label name="accounts.accounts_contacts_opps_details.NoOpportunityComponentsFound">No opportunity components found.</dhv:label>
        </td>
      </tr>
    <%}%>
    </table>
    <br />
    <dhv:pagedListControl object="AccountContactComponentListInfo"/>
    <br />
    <dhv:evaluate if="<%= ContactDetails.getEnabled() && !ContactDetails.isTrashed() && !OpportunityHeader.isTrashed() %>">
      <dhv:permission name="accounts-accounts-contacts-opportunities-edit">
        <input type="button" value="<dhv:label name="global.button.RenameOpportunity">Rename Opportunity</dhv:label>" onClick="javascript:window.location.href='AccountContactsOpps.do?command=ModifyOpp&headerId=<%= OpportunityHeader.getId() %>&contactId=<%= OpportunityHeader.getContactLink() %><%= addLinkParams(request, "popup|popupType|actionId") %>';">
      </dhv:permission>
      <dhv:permission name="accounts-accounts-contacts-opportunities-delete">
        <input type="button" value="<dhv:label name="global.button.DeleteOpportunity">Delete Opportunity</dhv:label>" onClick="javascript:popURLReturn('AccountContactsOpps.do?command=ConfirmDelete&contactId=<%= ContactDetails.getId() %>&headerId=<%= OpportunityHeader.getId() %>&popup=true<%= addLinkParams(request, "popupType|actionId") %>','AccountContactsOpps.do?command=ViewOpps&contactId=<%= ContactDetails.getId() %>', 'Delete_opp','320','200','yes','no')">
      </dhv:permission>
    </dhv:evaluate>
  </dhv:container>
</dhv:container>