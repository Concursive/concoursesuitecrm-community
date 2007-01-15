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
  - Version: $Id: LeadsComponentHistory.jsp 13379 2005-11-22 21:58:54Z kbhoopal $
  - Description:
  --%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ page import="java.util.*,java.text.DateFormat,org.aspcfs.modules.pipeline.base.*,com.zeroio.iteam.base.*, org.aspcfs.modules.base.Constants" %>
<jsp:useBean id="opportunityHeader" class="org.aspcfs.modules.pipeline.base.OpportunityHeader" scope="request"/>
<jsp:useBean id="opportunityComponent" class="org.aspcfs.modules.pipeline.base.OpportunityComponent" scope="request"/>
<jsp:useBean id="componentHistoryList" class="org.aspcfs.modules.pipeline.base.OpportunityComponentLogList" scope="request"/>
<jsp:useBean id="componentHistoryListInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<jsp:useBean id="PipelineViewpointInfo" class="org.aspcfs.utils.web.ViewpointInfo" scope="session"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<jsp:useBean id="applicationPrefs" class="org.aspcfs.controller.ApplicationPrefs" scope="application"/>
<%@ include file="../initPage.jsp" %>
<%-- Initialize the drop-down menus --%>
<%@ include file="../initPopupMenu.jsp" %>
<%@ include file="leads_component_history_menu.jsp" %>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/spanDisplay.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popURL.js"></SCRIPT>
<script language="JavaScript" type="text/javascript">
  <%-- Preload image rollovers for drop-down menu --%>
  loadImages('select');
</script>
<script type="text/javascript">
function reopenOpportunity(id) {
  if (id == '<%= opportunityHeader.getId() %>') {
    if ('<%= opportunityHeader.getComponentCount() %>' == '1' || '<%= opportunityHeader.getComponentCount() %>' == '0' || '<%= opportunityHeader.getComponentCount() %>' == '-1') {
      if ('<%= "dashboard".equals(request.getParameter("viewSource")) %>' == 'true') {
        scrollReload('Leads.do?command=Dashboard');
      } else {
        scrollReload('Leads.do?command=Search');
      }
    } else {
      scrollReload('Leads.do?command=DetailsOpp&headerId=<%= opportunityHeader.getId() %>&reset=true<%= "dashboard".equals(request.getParameter("viewSource")) ? "viewSource=dashboard":"" %>');
    }
    return id;
  } else {
    return '<%= opportunityHeader.getId() %>';
  }
}
</script>
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
<a href="Leads.do?command=DetailsOpp&headerId=<%= opportunityHeader.getId() %>"><dhv:label name="accounts.accounts_contacts_oppcomponent_add.OpportunityDetails">Opportunity Details</dhv:label></a> >
<dhv:evaluate if="<%= opportunityComponent.getId() == -1 %>">
  <dhv:label name="accounts.accounts_contacts_oppcomponent_add.ComponentHistory">History</dhv:label>
</dhv:evaluate>
<dhv:evaluate if="<%= opportunityComponent.getId() != -1 %>">
  <dhv:label name="accounts.accounts_contacts_oppcomponent.componentLog">Componet Log</dhv:label>
</dhv:evaluate>
</td>
</tr>
</table>
<%-- End Trails --%>
<dhv:formMessage showSpace="false" />
<dhv:evaluate if="<%= PipelineViewpointInfo.isVpSelected(User.getUserId()) %>">
  <dhv:label name="pipeline.viewpoint.colon" param='<%= "username="+PipelineViewpointInfo.getVpUserName() %>'><b>Viewpoint: </b><b class="highlight"><%= PipelineViewpointInfo.getVpUserName() %></b></dhv:label><br />
  &nbsp;<br>
</dhv:evaluate>
<dhv:container name="opportunities" selected='<%= (opportunityComponent.getId() != -1 ? "details" : "history") %>' object="opportunityHeader" param='<%= "id=" + opportunityHeader.getId() %>' appendToUrl='<%= addLinkParams(request, "viewSource") %>'>
  <dhv:pagedListStatus title='<%= showError(request, "actionError") %>' object="componentHistoryListInfo"/>
  <table cellpadding="4" cellspacing="0" border="0" width="100%" class="pagedList">
    <tr>
      <th align="center" nowrap>
        &nbsp;
      </th>
      <dhv:include name="opportunity.openOrClosed" none="true">
        <th nowrap>
          <strong><a href="LeadsComponents.do?command=ComponentHistory&headerId=<%= opportunityHeader.getId() %>&id=<%= opportunityComponent.getId() %>&column=ocl.description<%= addLinkParams(request, "viewSource") %>"><dhv:label name="accounts.accounts_contacts_opps_details.Component">Component</dhv:label></a></strong>
          <%= componentHistoryListInfo.getSortIcon("ocl.description") %>
        </th>
      </dhv:include>
      <dhv:include name="opportunity.singleComponent" none="true">
        <th align="center" nowrap>
          <strong><a href="LeadsComponents.do?command=ComponentHistory&headerId=<%= opportunityHeader.getId() %>&id=<%= opportunityComponent.getId() %>&column=ocl.owner<%= addLinkParams(request, "viewSource") %>"><dhv:label name="accounts.accounts_contacts_detailsimport.Owner">Owner</dhv:label></strong>
          <%= componentHistoryListInfo.getSortIcon("ocl.owner") %>
        </th>
      </dhv:include>
      <th align="center" nowrap>
        <strong><a href="LeadsComponents.do?command=ComponentHistory&headerId=<%= opportunityHeader.getId() %>&id=<%= opportunityComponent.getId() %>&column=ocl.closeprob<%= addLinkParams(request, "viewSource") %>"><dhv:label name="reports.pipeline.probability">Probability</dhv:label></a></strong>
        <%= componentHistoryListInfo.getSortIcon("ocl.closeprob") %>
      </th>
      <th nowrap>
        <strong><a href="LeadsComponents.do?command=ComponentHistory&headerId=<%= opportunityHeader.getId() %>&id=<%= opportunityComponent.getId() %>&column=ocl.closedate<%= addLinkParams(request, "viewSource") %>"><dhv:label name="accounts.accounts_contacts_opps_details.CloseDate">Close Date</dhv:label></a></strong>
        <%= componentHistoryListInfo.getSortIcon("ocl.closedate") %>
      </th>
      <dhv:include name="opportunity.currentStage" none="true">
        <th nowrap>
          <strong><a href="LeadsComponents.do?command=ComponentHistory&headerId=<%= opportunityHeader.getId() %>&id=<%= opportunityComponent.getId() %>&column=enteredby<%= addLinkParams(request, "viewSource") %>"><dhv:label name="accounts.accounts_fields_list.ModifiedBy">Modified By</dhv:label></a></strong>
          <%= componentHistoryListInfo.getSortIcon("enteredby") %>
        </th>
      </dhv:include>
      <th nowrap>
        <strong><a href="LeadsComponents.do?command=ComponentHistory&headerId=<%= opportunityHeader.getId() %>&id=<%= opportunityComponent.getId() %>&column=entered<%= addLinkParams(request, "viewSource") %>"><dhv:label name="accounts.accounts_contacts_oppcomponent_list.LastModified">Last Modified</dhv:label></a></strong>
        <%= componentHistoryListInfo.getSortIcon("entered") %>
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
  <dhv:hasAuthority owner='<%= String.valueOf(opportunityHeader.getManager()+(opportunityHeader.getManager() == thisComponentLog.getOwner()?"":","+thisComponentLog.getOwner())) %>'>
    <% hasPermission = true;%>
  </dhv:hasAuthority>
    <tr class="row<%= rowid %>">
      <td width="8" valign="top" align="center" nowrap>
        <%-- Use the unique id for opening the menu, and toggling the graphics --%>
         <a href="javascript:displayMenu('select<%= i %>','menuOpp',<%= opportunityHeader.getId() %>,'<%= thisComponentLog.getId() %>','<%= hasPermission %>');"
         onMouseOver="over(0, <%= i %>)" onmouseout="out(0, <%= i %>); hideMenu('menuOpp');"><img src="images/select.gif" name="select<%= i %>" id="select<%= i %>" align="absmiddle" border="0"></a>
      </td>
      <td width="100%" valign="top">
        <a href="LeadsComponents.do?command=ComponentHistoryDetails&headerId=<%= opportunityHeader.getId() %>&componentId=<%= opportunityComponent.getId() %>&id=<%= thisComponentLog.getId() %><%= addLinkParams(request, "viewSource") %>">
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
  <dhv:pagedListControl object="componentHistoryListInfo"/>
  &nbsp;<br>
</dhv:container>
