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
<script language="JavaScript" type="text/javascript">
  <%-- Preload image rollovers for drop-down menu --%>
  loadImages('select');
</script>
<dhv:evaluate exp="<%= !isPopup(request) %>">
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="ExternalContacts.do">Contacts</a> > 
<a href="ExternalContacts.do?command=SearchContacts">Search Results</a> >
<a href="ExternalContacts.do?command=ContactDetails&id=<%= ContactDetails.getId() %>">Contact Details</a> >
<a href="ExternalContactsOpps.do?command=ViewOpps&contactId=<%= ContactDetails.getId() %>">Opportunities</a> >
Opportunity Details
</td>
</tr>
</table>
<%-- End Trails --%>
</dhv:evaluate>
<%-- Begin container --%>
<%@ include file="contact_details_header_include.jsp" %>
<% String param1 = "id=" + ContactDetails.getId(); 
   String param2 = addLinkParams(request, "popup|popupType|actionId"); %>
<dhv:container name="contacts" selected="opportunities" param="<%= param1 %>" appendToUrl="<%= param2 %>" style="tabs"/>
<table cellpadding="4" cellspacing="0" border="0" width="100%">
  <tr>
    <td class="containerBack">
      <%-- Begin container content --%>
      <img src="images/icons/stock_form-currency-field-16.gif" border="0" align="absmiddle">
      <strong><%= toHtml(opportunityHeader.getDescription()) %></strong>
      <% FileItem thisFile = new FileItem(); %>
      <dhv:evaluate if="<%= opportunityHeader.hasFiles() %>">
        <%= thisFile.getImageTag() %>
      </dhv:evaluate>
      <br>
    <% if(ContactDetails.getOrgId() > 0){ %>
      <dhv:permission name="contacts-external_contacts-opportunities-add,accounts-accounts-contacts-opportunities-add"  all="true">
      <br>
      <a href="ExternalContactsOppComponents.do?command=Prepare&headerId=<%= opportunityHeader.getId() %>&contactId=<%= opportunityHeader.getContactLink() %><%= addLinkParams(request, "popup|popupType|actionId") %>">Add a Component</a><br>
    </dhv:permission>
    <% }else{ %>
      <dhv:permission name="contacts-external_contacts-opportunities-add">
      <br>
      <a href="ExternalContactsOppComponents.do?command=Prepare&headerId=<%= opportunityHeader.getId() %>&contactId=<%= opportunityHeader.getContactLink() %><%= addLinkParams(request, "popup|popupType|actionId") %>">Add a Component</a><br>
    </dhv:permission>
    <%}%>
    
<%= addHiddenParams(request, "popup|popupType|actionId") %>
<input type="hidden" name="actionSource" value="ExternalContactsOppComponents">
<dhv:pagedListStatus title="<%= showError(request, "actionError") %>" object="ComponentListInfo"/>
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th nowrap>
      <strong>Action</strong>
    </th>
    <th nowrap>
      <strong><a href="ExternalContactsOpps.do?command=DetailsOpp&headerId=<%= opportunityHeader.getId() %>&contactId=<%= ContactDetails.getId() %>&column=oc.description<%= addLinkParams(request, "popup|popupType|actionId") %>">Component</a></strong>
      <%= ComponentListInfo.getSortIcon("oc.description") %>
    </th>
    <th nowrap>
      <strong><a href="ExternalContactsOpps.do?command=DetailsOpp&headerId=<%= opportunityHeader.getId() %>&contactId=<%= ContactDetails.getId() %>&column=oc.closed<%= addLinkParams(request, "popup|popupType|actionId") %>">Status</a></strong>
      <%= ComponentListInfo.getSortIcon("oc.closed") %>
    </th>
    <th nowrap>
      <strong><a href="ExternalContactsOpps.do?command=DetailsOpp&headerId=<%= opportunityHeader.getId() %>&contactId=<%= ContactDetails.getId() %>&column=oc.guessvalue<%= addLinkParams(request, "popup|popupType|actionId") %>">Guess Amount</a></strong>
      <%= ComponentListInfo.getSortIcon("oc.guessvalue") %>
    </th>
    <th nowrap>
      <strong><a href="ExternalContactsOpps.do?command=DetailsOpp&headerId=<%= opportunityHeader.getId() %>&contactId=<%= ContactDetails.getId() %>&column=oc.closedate<%= addLinkParams(request, "popup|popupType|actionId") %>">Close Date</a></strong>
      <%= ComponentListInfo.getSortIcon("oc.closedate") %>
    </th>
    <th nowrap>
      <strong><a href="ExternalContactsOpps.do?command=DetailsOpp&headerId=<%= opportunityHeader.getId() %>&contactId=<%= ContactDetails.getId() %>&column=stagename<%= addLinkParams(request, "popup|popupType|actionId") %>">Current Stage</a></strong>
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
%>      
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
        <dhv:permission name="contacts-external_contacts-opportunities-delete,,accounts-accounts-contacts-opportunities-delete" all="true">
         <% hasDeletePermission = 1; %>
        </dhv:permission>
      <% } %>
      <%-- Use the unique id for opening the menu, and toggling the graphics --%>
       <a href="javascript:displayMenu('select<%= count %>','menuOpp','<%= ContactDetails.getId() %>', '<%= opportunityHeader.getId() %>','<%= oppComponent.getId() %>','<%= hasEditPermission %>', '<%= hasDeletePermission %>');" onMouseOver="over(0, <%= count %>)" onmouseout="out(0, <%= count %>); hideMenu('menuOpp');"><img src="images/select.gif" name="select<%= count %>" id="select<%= count %>" align="absmiddle" border="0"></a>
    </td>
    <td width="100%" valign="top" class="row<%= rowid %>">
      <a href="ExternalContactsOppComponents.do?command=DetailsComponent&contactId=<%= ContactDetails.getId() %>&id=<%= oppComponent.getId() %><%= addLinkParams(request, "popup|popupType|actionId") %>">
      <%= toHtml(oppComponent.getDescription()) %></a>
    </td>
    <td valign="top" align="center" nowrap class="row<%= rowid %>">
      <%= oppComponent.getClosed() != null ? "<font color=\"red\">closed</font>" : "<font color=\"green\">open</font>" %>
    </td>
    <td valign="top" align="right" nowrap class="row<%= rowid %>">
      <zeroio:currency value="<%= oppComponent.getGuess() %>" code="<%= applicationPrefs.get("SYSTEM.CURRENCY") %>" locale="<%= User.getLocale() %>" default="&nbsp;"/>
    </td>
    <td valign="top" align="center" nowrap class="row<%= rowid %>">
      <% if(!User.getTimeZone().equals(oppComponent.getCloseDateTimeZone())){%>
      <zeroio:tz timestamp="<%= oppComponent.getCloseDate() %>" timeZone="<%= User.getTimeZone() %>" showTimeZone="yes" default="&nbsp;"/>
      <% } else { %>
      <zeroio:tz timestamp="<%= oppComponent.getCloseDate() %>" dateOnly="true" timeZone="<%= oppComponent.getCloseDateTimeZone() %>" showTimeZone="yes" default="&nbsp;"/>
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
      No opportunity components found.
    </td>
  </tr>
<%}%>
</table>
<br>
<dhv:pagedListControl object="ComponentListInfo"/>
&nbsp;<br>
<dhv:sharing primaryBean="opportunityHeader" secondaryBeans="ContactDetails" action="edit"><input type="button" value="Rename Opportunity" onClick="javascript:window.location.href='ExternalContactsOpps.do?command=ModifyOpp&headerId=<%= opportunityHeader.getId() %>&contactId=<%= opportunityHeader.getContactLink() %><%= addLinkParams(request, "popup|popupType|actionId") %>';"></dhv:sharing>
<dhv:sharing primaryBean="opportunityHeader" secondaryBeans="ContactDetails" action="delete"><input type="button" value="Delete Opportunity" onClick="javascript:popURLReturn('ExternalContactsOpps.do?command=ConfirmDelete&contactId=<%= ContactDetails.getId() %>&headerId=<%= opportunityHeader.getId() %>&popup=true<%= addLinkParams(request, "popupType|actionId") %>','ExternalContactsOpps.do?command=ViewOpps&contactId=<%= ContactDetails.getId() %>', 'Delete_opp','320','200','yes','no')"></dhv:sharing>
</td>
</tr>
</table>
