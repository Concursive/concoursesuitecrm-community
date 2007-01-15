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
<jsp:useBean id="opportunityHeader" class="org.aspcfs.modules.pipeline.base.OpportunityHeader" scope="request"/>
<jsp:useBean id="componentList" class="org.aspcfs.modules.pipeline.base.OpportunityComponentList" scope="request"/>
<jsp:useBean id="ComponentListInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<jsp:useBean id="applicationPrefs" class="org.aspcfs.controller.ApplicationPrefs" scope="application"/>
<%@ include file="../initPage.jsp" %>
<%-- Initialize the drop-down menus --%>
<%@ include file="../initPopupMenu.jsp" %>
<%@ include file="companydirectory_detailsopp_menu.jsp" %>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/spanDisplay.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popURL.js"></SCRIPT>
<%if (isPopup(request)) {%>
<script language="JavaScript" type="text/javascript" src="javascript/scrollReload.js"></script>
<%}%>
<script language="JavaScript" type="text/javascript">
  <%-- Preload image rollovers for drop-down menu --%>
  loadImages('select');
  
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
<%
  boolean allowMultiple = allowMultipleComponents(pageContext, OpportunityComponent.MULTPLE_CONFIG_NAME, "multiple");
%>
<dhv:evaluate if="<%= !isPopup(request) %>">
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="ExternalContacts.do"><dhv:label name="Contacts" mainMenuItem="true">Contacts</dhv:label></a> >
<a href="ExternalContacts.do?command=SearchContacts"><dhv:label name="accounts.SearchResults">Search Results</dhv:label></a> >
<a href="ExternalContacts.do?command=ContactDetails&id=<%= ContactDetails.getId() %>"><dhv:label name="accounts.accounts_contacts_add.ContactDetails">Contact Details</dhv:label></a> >
<a href="ExternalContactsOpps.do?command=ViewOpps&contactId=<%= ContactDetails.getId() %>"><dhv:label name="accounts.accounts_contacts_oppcomponent_add.Opportunities">Opportunities</dhv:label></a> >
<dhv:label name="accounts.accounts_contacts_oppcomponent_add.OpportunityDetails">Opportunity Details</dhv:label>
</td>
</tr>
</table>
<%-- End Trails --%>
</dhv:evaluate>
<dhv:container name="contacts" selected="opportunities" object="ContactDetails" param='<%= "id=" + ContactDetails.getId() %>' appendToUrl='<%= addLinkParams(request, "popup|popupType|actionId") %>'>
  <img src="images/icons/stock_form-currency-field-16.gif" border="0" align="absmiddle">
  <strong><%= toHtml(opportunityHeader.getDescription()) %></strong>
  <% FileItem thisFile = new FileItem(); %>
  <dhv:evaluate if="<%= opportunityHeader.hasFiles() %>">
    <%= thisFile.getImageTag() %>
  </dhv:evaluate>
  <br>
  <dhv:hasAuthority owner="<%= opportunityHeader.getManager() %>">
  <dhv:evaluate if="<%= !opportunityHeader.getLock() %>">
    <dhv:evaluate if="<%= ContactDetails.getEnabled() && !ContactDetails.isTrashed() && !opportunityHeader.isTrashed() %>">
      <% if(ContactDetails.getOrgId() > 0){ %>
      <dhv:permission name="contacts-external_contacts-opportunities-add,accounts-accounts-contacts-opportunities-add"  all="true">
          <dhv:evaluate if="<%=allowMultiple || (!allowMultiple && (componentList.size() == 0))%>" >
            <br /><a href="ExternalContactsOppComponents.do?command=Prepare&headerId=<%= opportunityHeader.getId() %>&contactId=<%= opportunityHeader.getContactLink() %><%= addLinkParams(request, "popup|popupType|actionId") %>"><dhv:label name="accounts.accounts_contacts_opps_details.AddAComponent">Add a Component</dhv:label></a><br />
          </dhv:evaluate>
        </dhv:permission>
      <% }else{ %>
        <dhv:permission name="contacts-external_contacts-opportunities-add">
          <br />
          <a href="ExternalContactsOppComponents.do?command=Prepare&headerId=<%= opportunityHeader.getId() %>&contactId=<%= opportunityHeader.getContactLink() %><%= addLinkParams(request, "popup|popupType|actionId") %>"><dhv:label name="accounts.accounts_contacts_opps_details.AddAComponent">Add a Component</dhv:label></a><br />
        </dhv:permission>
      <%}%>
    </dhv:evaluate>
  </dhv:evaluate>
  </dhv:hasAuthority>
  <%= addHiddenParams(request, "popup|popupType|actionId") %>
  <input type="hidden" name="actionSource" value="ExternalContactsOppComponents">
  <dhv:pagedListStatus title='<%= showError(request, "actionError") %>' object="ComponentListInfo"/>
  <table cellpadding="4" cellspacing="0" border="0" width="100%" class="pagedList">
    <tr>
      <th width="8">&nbsp;</th>
      <th nowrap>
        <a href="ExternalContactsOpps.do?command=DetailsOpp&headerId=<%= opportunityHeader.getId() %>&contactId=<%= ContactDetails.getId() %>&column=oc.description<%= addLinkParams(request, "popup|popupType|actionId") %>"><dhv:label name="accounts.accounts_contacts_opps_details.Component">Component</dhv:label></a>
        <%= ComponentListInfo.getSortIcon("oc.description") %>
      </th>
      <th nowrap>
        <a href="ExternalContactsOpps.do?command=DetailsOpp&headerId=<%= opportunityHeader.getId() %>&contactId=<%= ContactDetails.getId() %>&column=oc.closed<%= addLinkParams(request, "popup|popupType|actionId") %>"><dhv:label name="accounts.accountasset_include.Status">Status</dhv:label></a>
        <%= ComponentListInfo.getSortIcon("oc.closed") %>
      </th>
      <th nowrap>
        <a href="ExternalContactsOpps.do?command=DetailsOpp&headerId=<%= opportunityHeader.getId() %>&contactId=<%= ContactDetails.getId() %>&column=oc.guessvalue<%= addLinkParams(request, "popup|popupType|actionId") %>"><dhv:label name="accounts.accounts_contacts_opps_details.GuessAmount">Guess Amount</dhv:label></a>
        <%= ComponentListInfo.getSortIcon("oc.guessvalue") %>
      </th>
      <th nowrap>
        <a href="ExternalContactsOpps.do?command=DetailsOpp&headerId=<%= opportunityHeader.getId() %>&contactId=<%= ContactDetails.getId() %>&column=oc.closedate<%= addLinkParams(request, "popup|popupType|actionId") %>"><dhv:label name="accounts.accounts_contacts_opps_details.CloseDate">Close Date</dhv:label></a>
        <%= ComponentListInfo.getSortIcon("oc.closedate") %>
      </th>
      <th nowrap>
        <a href="ExternalContactsOpps.do?command=DetailsOpp&headerId=<%= opportunityHeader.getId() %>&contactId=<%= ContactDetails.getId() %>&column=stagename<%= addLinkParams(request, "popup|popupType|actionId") %>"><dhv:label name="accounts.accounts_contacts_oppcomponent_details.CurrentStage">Current Stage</dhv:label></a>
        <%= ComponentListInfo.getSortIcon("stagename") %>
      </th>
    </tr>
  <%
    Iterator j = componentList.iterator();
    if ( j.hasNext() ) {
      int rowid = 0;
      int count = 0;
        while (j.hasNext()) {
          count++;
          rowid = (rowid != 1?1:2);
          OpportunityComponent oppComponent = (OpportunityComponent)j.next();
          boolean hasPermission = false;
  %>
    <dhv:hasAuthority owner='<%= String.valueOf(opportunityHeader.getManager()+(opportunityHeader.getManager() == oppComponent.getOwner()?"":","+oppComponent.getOwner())) %>'>
      <% hasPermission = true; %>
    </dhv:hasAuthority>
    <zeroio:debug value='<%= String.valueOf(opportunityHeader.getManager()+(opportunityHeader.getManager() == oppComponent.getOwner()?"":","+oppComponent.getOwner())) + " and the value of hasPermission is "+hasPermission %>'/>
    <tr class="containerBody">
      <td width="8" valign="top" nowrap class="row<%= rowid %>">
        <%-- check if user has edit or delete based on the type of contact --%>
        <%
          int hasEditPermission = 0;
          int hasDeletePermission = 0;
          if(ContactDetails.getOrgId() < 0){ %>
          <dhv:permission name="contacts-external_contacts-opportunities-edit">
            <% hasEditPermission = 1; %>
          </dhv:permission>
          <dhv:permission name="contacts-external_contacts-opportunities-delete">
           <%  hasDeletePermission = 1; %>
          </dhv:permission>
        <% }else{ %>
          <dhv:permission name="contacts-external_contacts-opportunities-edit,accounts-accounts-contacts-opportunities-edit"  all="true">
           <% hasEditPermission = 1; %>
          </dhv:permission>
          <dhv:permission name="contacts-external_contacts-opportunities-delete,accounts-accounts-contacts-opportunities-delete" all="true">
           <% hasDeletePermission = 1; %>
          </dhv:permission>
        <% } %>
        <%-- Use the unique id for opening the menu, and toggling the graphics --%>
        <dhv:evaluate if="<%= ContactDetails.getEnabled() && !ContactDetails.isTrashed() && !opportunityHeader.isTrashed() %>">
          <a href="javascript:displayMenu('select<%= count %>','menuOpp','<%= ContactDetails.getId() %>', '<%= opportunityHeader.getId() %>','<%= oppComponent.getId() %>','<%= hasEditPermission %>', '<%= hasDeletePermission %>','<%= oppComponent.isTrashed() || opportunityHeader.isTrashed() %>','<%= hasPermission %>');" onMouseOver="over(0, <%= count %>)" onmouseout="out(0, <%= count %>); hideMenu('menuOpp');"><img src="images/select.gif" name="select<%= count %>" id="select<%= count %>" align="absmiddle" border="0"></a>
        </dhv:evaluate>
      </td>
      <td width="100%" valign="top" class="row<%= rowid %>">
        <a href="ExternalContactsOppComponents.do?command=DetailsComponent&contactId=<%= ContactDetails.getId() %>&id=<%= oppComponent.getId() %><%= addLinkParams(request, "popup|popupType|actionId") %>">
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
        <zeroio:currency value="<%= oppComponent.getGuess() %>" code='<%= applicationPrefs.get("SYSTEM.CURRENCY") %>' locale="<%= User.getLocale() %>" default="&nbsp;"/>
      </td>
      <td valign="top" align="center" nowrap class="row<%= rowid %>">
        <% if(!User.getTimeZone().equals(oppComponent.getCloseDateTimeZone())){%>
        <zeroio:tz timestamp="<%= oppComponent.getCloseDate() %>" timeZone="<%= User.getTimeZone() %>" showTimeZone="true" default="&nbsp;"/>
        <% } else { %>
        <zeroio:tz timestamp="<%= oppComponent.getCloseDate() %>" dateOnly="true" timeZone="<%= oppComponent.getCloseDateTimeZone() %>" showTimeZone="true" default="&nbsp;"/>
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
  <br>
  <dhv:pagedListControl object="ComponentListInfo"/>
  <br />
  <dhv:hasAuthority owner="<%= opportunityHeader.getManager() %>">
    <dhv:evaluate if="<%= ContactDetails.getEnabled() && !ContactDetails.isTrashed() && !opportunityHeader.isTrashed() %>">
      <dhv:evaluate if="<%= ContactDetails.getEnabled() && !ContactDetails.isTrashed() && !opportunityHeader.isTrashed() %>">
        <dhv:sharing primaryBean="opportunityHeader" secondaryBeans="ContactDetails" action="edit"><input type="button" value="<dhv:label name="global.button.RenameOpportunity">Rename Opportunity</dhv:label>" onClick="javascript:window.location.href='ExternalContactsOpps.do?command=ModifyOpp&headerId=<%= opportunityHeader.getId() %>&contactId=<%= opportunityHeader.getContactLink() %><%= addLinkParams(request, "popup|popupType|actionId") %>';"></dhv:sharing>
        <dhv:sharing primaryBean="opportunityHeader" secondaryBeans="ContactDetails" action="delete"><input type="button" value="<dhv:label name="global.button.DeleteOpportunity">Delete Opportunity</dhv:label>" onClick="javascript:popURLReturn('ExternalContactsOpps.do?command=ConfirmDelete&contactId=<%= ContactDetails.getId() %>&headerId=<%= opportunityHeader.getId() %>&popup=true<%= isPopup(request)?"&sourcePopup=true":"" %><%= addLinkParams(request, "popupType|actionId") %>','ExternalContactsOpps.do?command=ViewOpps&contactId=<%= ContactDetails.getId() %>', 'Delete_opp','320','200','yes','no')"></dhv:sharing>
      </dhv:evaluate>
    </dhv:evaluate>
  </dhv:hasAuthority>
</dhv:container>
