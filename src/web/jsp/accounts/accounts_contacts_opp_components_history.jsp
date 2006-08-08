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
  - Version: $Id: 
  - Description:
  --%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ page import="java.util.*,java.text.DateFormat,org.aspcfs.modules.pipeline.base.*,com.zeroio.iteam.base.*, org.aspcfs.modules.base.Constants" %>
<jsp:useBean id="orgDetails" class="org.aspcfs.modules.accounts.base.Organization" scope="request"/>
<jsp:useBean id="contactDetails" class="org.aspcfs.modules.contacts.base.Contact" scope="request"/>
<jsp:useBean id="opportunityHeader" class="org.aspcfs.modules.pipeline.base.OpportunityHeader" scope="request"/>
<jsp:useBean id="opportunityComponent" class="org.aspcfs.modules.pipeline.base.OpportunityComponent" scope="request"/>
<jsp:useBean id="componentHistoryList" class="org.aspcfs.modules.pipeline.base.OpportunityComponentLogList" scope="request"/>
<jsp:useBean id="accountContactsComponentHistoryListInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<jsp:useBean id="applicationPrefs" class="org.aspcfs.controller.ApplicationPrefs" scope="application"/>
<%@ include file="../initPage.jsp" %>
<%-- Initialize the drop-down menus --%>
<%@ include file="../initPopupMenu.jsp" %>
<%@ include file="accounts_contacts_opps_components_history_menu.jsp" %>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/spanDisplay.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popURL.js"></SCRIPT>
<script language="JavaScript" type="text/javascript">
  <%-- Preload image rollovers for drop-down menu --%>
  loadImages('select');

  function reopenOpportunity(id) {
    if (id == '<%= opportunityHeader.getId() %>') {
      scrollReload('AccountContactsOppComponents.do?command=ViewOpps&contactId=<%= contactDetails.getId() %><%= isPopup(request)?"&popup=true":"" %>');
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
<a href="Accounts.do"><dhv:label name="accounts.accounts">Accounts</dhv:label></a> > 
<% if (request.getParameter("return") == null) { %>
<a href="Accounts.do?command=Search"><dhv:label name="accounts.SearchResults">Search Results</dhv:label></a> >
<%} else if (request.getParameter("return").equals("dashboard")) {%>
<a href="Accounts.do?command=Dashboard"><dhv:label name="communications.campaign.Dashboard">Dashboard</dhv:label></a> >
<%}%>
<a href="Accounts.do?command=Details&orgId=<%=orgDetails.getOrgId()%>"><dhv:label name="accounts.details">Account Details</dhv:label></a> >
<a href="Contacts.do?command=View&orgId=<%=orgDetails.getOrgId()%>"><dhv:label name="accounts.Contacts">Contacts</dhv:label></a> >
<a href="Contacts.do?command=Details&id=<%=contactDetails.getId()%>&orgId=<%=orgDetails.getOrgId()%>"><dhv:label name="accounts.accounts_contacts_add.contactDetails">Contact Details</dhv:label></a> >
<a href="AccountContactsOppComponents.do?command=ViewOpps&contactId=<%= contactDetails.getId() %>"><dhv:label name="accounts.accounts_contacts_oppcomponent_add.Opportunities">Opportunities</dhv:label></a> >
<a href="AccountContactsOpps.do?command=DetailsOpp&headerId=<%= opportunityHeader.getId() %>&orgId=<%=orgDetails.getOrgId()%>&contactId=<%=contactDetails.getId()%>"><dhv:label name="accounts.accounts_contacts_oppcomponent_add.OpportunityDetails">Opportunity Details</dhv:label></a> >
<dhv:label name="accounts.accounts_contacts_oppcomponent.componentLog">Component Log</dhv:label>
</td>
</tr>
</table>
<%-- End Trails --%>
</dhv:evaluate>
<dhv:container name="accounts" selected="contacts" object="orgDetails" param="<%= "orgId=" + orgDetails.getOrgId() %>" appendToUrl="<%= addLinkParams(request, "popup|popupType|actionId") %>">
  <dhv:container name="accountscontacts" selected="opportunities" object="contactDetails" param="<%= "id=" + contactDetails.getId() %>" appendToUrl="<%= addLinkParams(request, "popup|popupType|actionId") %>">
    <dhv:container name="accountcontactopportunities" selected="details" object="opportunityHeader" param="<%= "headerId=" + opportunityHeader.getId() + "|" + "orgId=" + orgDetails.getOrgId() +"|" + "contactId=" + contactDetails.getId() %>" appendToUrl="<%= addLinkParams(request, "popup|popupType|actionId") %>">
    <% FileItem thisFile = new FileItem(); %>
    <dhv:evaluate if="<%= opportunityHeader.hasFiles() %>">
      <%= thisFile.getImageTag("-23") %>
    </dhv:evaluate>
    <br />
    <dhv:pagedListStatus title="<%= showError(request, "actionError") %>" object="accountContactsComponentHistoryListInfo"/>
  <table cellpadding="4" cellspacing="0" border="0" width="100%" class="pagedList">
    <tr>
      <th align="center" nowrap>
        &nbsp;
      </th>
      <dhv:include name="opportunity.openOrClosed" none="true">
        <th nowrap>
          <strong><a href="AccountContactsOppComponents.do?command=ComponentHistory&contactId=<%= contactDetails.getId() %>&headerId=<%= opportunityHeader.getId() %>&id=<%= opportunityComponent.getId() %>&column=ocl.description<%= addLinkParams(request, "viewSource") %>"><dhv:label name="accounts.accounts_contacts_opps_details.Component">Component</dhv:label></a></strong>
          <%= accountContactsComponentHistoryListInfo.getSortIcon("ocl.description") %>
        </th>
      </dhv:include>
      <dhv:include name="opportunity.singleComponent" none="true">
        <th align="center" nowrap>
          <strong><a href="AccountContactsOppComponents.do?command=ComponentHistory&contactId=<%= contactDetails.getId() %>&headerId=<%= opportunityHeader.getId() %>&id=<%= opportunityComponent.getId() %>&column=ocl.owner<%= addLinkParams(request, "viewSource") %>"><dhv:label name="accounts.accounts_contacts_detailsimport.Owner">Owner</dhv:label></strong>
          <%= accountContactsComponentHistoryListInfo.getSortIcon("ocl.owner") %>
        </th>
      </dhv:include>
      <th align="center" nowrap>
        <strong><a href="AccountContactsOppComponents.do?command=ComponentHistory&contactId=<%= contactDetails.getId() %>&headerId=<%= opportunityHeader.getId() %>&id=<%= opportunityComponent.getId() %>&column=ocl.closeprob<%= addLinkParams(request, "viewSource") %>"><dhv:label name="reports.pipeline.probability">Probability</dhv:label></a></strong>
        <%= accountContactsComponentHistoryListInfo.getSortIcon("ocl.closeprob") %>
      </th>
      <th nowrap>
        <strong><a href="AccountContactsOppComponents.do?command=ComponentHistory&contactId=<%= contactDetails.getId() %>&headerId=<%= opportunityHeader.getId() %>&id=<%= opportunityComponent.getId() %>&column=ocl.closedate<%= addLinkParams(request, "viewSource") %>"><dhv:label name="accounts.accounts_contacts_opps_details.CloseDate">Close Date</dhv:label></a></strong>
        <%= accountContactsComponentHistoryListInfo.getSortIcon("ocl.closedate") %>
      </th>
      <dhv:include name="opportunity.currentStage" none="true">
        <th nowrap>
          <strong><a href="AccountContactsOppComponents.do?command=ComponentHistory&contactId=<%= contactDetails.getId() %>&headerId=<%= opportunityHeader.getId() %>&id=<%= opportunityComponent.getId() %>&column=enteredby<%= addLinkParams(request, "viewSource") %>"><dhv:label name="accounts.accounts_fields_list.ModifiedBy">Modified By</dhv:label></a></strong>
          <%= accountContactsComponentHistoryListInfo.getSortIcon("enteredby") %>
        </th>
      </dhv:include>
      <th nowrap>
        <strong><a href="AccountContactsOppComponents.do?command=ComponentHistory&contactId=<%= contactDetails.getId() %>&headerId=<%= opportunityHeader.getId() %>&id=<%= opportunityComponent.getId() %>&column=entered<%= addLinkParams(request, "viewSource") %>"><dhv:label name="accounts.accounts_contacts_oppcomponent_list.LastModified">Last Modified</dhv:label></a></strong>
        <%= accountContactsComponentHistoryListInfo.getSortIcon("entered") %>
      </th>
    </tr>
<%
	Iterator j = componentHistoryList.iterator();
	if ( j.hasNext() ) {
		int rowid = 0;
    int i =0;
	    while (j.hasNext()) {
        i++;
        rowid = (rowid != 1?1:2);
        OpportunityComponentLog thisComponentLog = (OpportunityComponentLog)j.next();
        boolean hasPermission = false;
%>
  <dhv:hasAuthority owner="<%= String.valueOf(opportunityHeader.getManager()+(opportunityHeader.getManager() == thisComponentLog.getOwner()?"":","+thisComponentLog.getOwner())) %>">
    <% hasPermission = true;%>
  </dhv:hasAuthority>
    <tr class="row<%= rowid %>">
      <td width="8" valign="top" align="center" nowrap>
        <%-- Use the unique id for opening the menu, and toggling the graphics --%>
         <a href="javascript:displayMenu('select<%= i %>','menuOpp',<%= contactDetails.getId() %>,'<%= thisComponentLog.getId() %>','<%= hasPermission %>');"
         onMouseOver="over(0, <%= i %>)" onmouseout="out(0, <%= i %>); hideMenu('menuOpp');"><img src="images/select.gif" name="select<%= i %>" id="select<%= i %>" align="absmiddle" border="0"></a>
      </td>
      <td width="100%" valign="top">
        <a href="AccountContactsOppComponents.do?command=ComponentHistoryDetails&headerId=<%= opportunityHeader.getId() %>&contactId=<%= contactDetails.getId() %>&id=<%= thisComponentLog.getId() %>&return=details<%= addLinkParams(request, "viewSource") %>">
          <%= toHtml(thisComponentLog.getDescription()) %></a>
      </td>
      <td valign="top" align="center" nowrap>
        <dhv:username id="<%= thisComponentLog.getOwner() %>"/>
      </td>
      <td valign="top" align="center" nowrap>
        <%= thisComponentLog.getCloseProbValue() %>%
      </td>
      <td valign="top" align="center" nowrap>
        <% if(!User.getTimeZone().equals(thisComponentLog.getCloseDateTimeZone())){%>
        <zeroio:tz timestamp="<%= thisComponentLog.getCloseDate() %>" timeZone="<%= User.getTimeZone() %>" showTimeZone="true" default="&nbsp;"/>
        <% } else { %>
        <zeroio:tz timestamp="<%= thisComponentLog.getCloseDate() %>" dateOnly="true" timeZone="<%= thisComponentLog.getCloseDateTimeZone() %>" showTimeZone="true" default="&nbsp;"/>
        <% } %>
      </td>
      <td valign="top" align="center" nowrap>
        <dhv:username id="<%= thisComponentLog.getEnteredBy() %>"/>
      </td>
      <td valign="top" align="center" nowrap>
        <zeroio:tz timestamp="<%= thisComponentLog.getEntered() %>" timeZone="<%= User.getTimeZone() %>" showTimeZone="true" default="&nbsp;"/>
      </td>
    </tr>
<%}%>
<%} else {%>
    <tr class="containerBody">
      <td colspan="9">
        <dhv:label name="accounts.accounts_contacts_opps_details.NoOpportunityComponentLogFound">No opportunity component history found.</dhv:label>
      </td>
    </tr>
<%}%>
  </table>
  <br>
  <dhv:pagedListControl object="accountContactsComponentHistoryListInfo"/>
  &nbsp;<br>
    <%= addHiddenParams(request, "popup|popupType|actionId") %>
    <input type="hidden" name="actionSource" value="AccountContactsOppComponents">
    </dhv:container>
  </dhv:container>
</dhv:container>
