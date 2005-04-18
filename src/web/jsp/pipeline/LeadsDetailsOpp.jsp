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
<jsp:useBean id="ComponentList" class="org.aspcfs.modules.pipeline.base.OpportunityComponentList" scope="request"/>
<jsp:useBean id="LeadsComponentListInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<jsp:useBean id="PipelineViewpointInfo" class="org.aspcfs.utils.web.ViewpointInfo" scope="session"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<jsp:useBean id="applicationPrefs" class="org.aspcfs.controller.ApplicationPrefs" scope="application"/>
<%@ include file="../initPage.jsp" %>
<%-- Initialize the drop-down menus --%>
<%@ include file="../initPopupMenu.jsp" %>
<%@ include file="LeadsDetailsOpp_menu.jsp" %>
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
<dhv:label name="accounts.accounts_contacts_oppcomponent_add.OpportunityDetails">Opportunity Details</dhv:label>
</td>
</tr>
</table>
<%-- End Trails --%>
<dhv:formMessage showSpace="false" />
<dhv:evaluate if="<%= PipelineViewpointInfo.isVpSelected(User.getUserId()) %>">
  <dhv:label name="pipeline.viewpoint.colon" param="<%= "username="+PipelineViewpointInfo.getVpUserName() %>"><b>Viewpoint: </b><b class="highlight"><%= PipelineViewpointInfo.getVpUserName() %></b></dhv:label><br />
  &nbsp;<br>
</dhv:evaluate>
<dhv:container name="opportunities" selected="details" object="opportunityHeader" param="<%= "id=" + opportunityHeader.getId() %>" appendToUrl="<%= addLinkParams(request, "viewSource") %>">
  <dhv:permission name="pipeline-opportunities-add">
    <a href="LeadsComponents.do?command=Prepare&headerId=<%= opportunityHeader.getId() %><%= addLinkParams(request, "viewSource") %>"><dhv:label name="accounts.accounts_contacts_opps_details.AddAComponent">Add a Component</dhv:label></a><br>
  </dhv:permission>
  <dhv:pagedListStatus title="<%= showError(request, "actionError") %>" object="LeadsComponentListInfo"/>
  <table cellpadding="4" cellspacing="0" border="0" width="100%" class="pagedList">
    <tr>
      <th align="center" nowrap>
        &nbsp;
      </th>
      <th nowrap>
        <strong><a href="Leads.do?command=DetailsOpp&headerId=<%= opportunityHeader.getId() %>&column=oc.description<%= addLinkParams(request, "viewSource") %>"><dhv:label name="accounts.accounts_contacts_opps_details.Component">Component</dhv:label></a></strong>
        <%= LeadsComponentListInfo.getSortIcon("oc.description") %>
      </th>
      <th nowrap>
        <strong><a href="Leads.do?command=DetailsOpp&headerId=<%= opportunityHeader.getId() %>&column=oc.closed<%= addLinkParams(request, "viewSource") %>"><dhv:label name="accounts.accountasset_include.Status">Status</dhv:label></a></strong>
        <%= LeadsComponentListInfo.getSortIcon("oc.closed") %>
      </th>
      <th align="center" nowrap>
        <strong><a href="Leads.do?command=DetailsOpp&headerId=<%= opportunityHeader.getId() %>&column=oc.guessvalue<%= addLinkParams(request, "viewSource") %>">Guess<br><dhv:label name="accounts.accounts_revenue_add.Amount">Amount</dhv:label></a></strong>
        <%= LeadsComponentListInfo.getSortIcon("oc.guessvalue") %>
      </th>
      <th nowrap>
        <strong><a href="Leads.do?command=DetailsOpp&headerId=<%= opportunityHeader.getId() %>&column=oc.closedate<%= addLinkParams(request, "viewSource") %>"><dhv:label name="accounts.accounts_contacts_opps_details.CloseDate">Close Date</dhv:label></a></strong>
        <%= LeadsComponentListInfo.getSortIcon("oc.closedate") %>
      </th>
      <th nowrap>
        <strong><a href="Leads.do?command=DetailsOpp&headerId=<%= opportunityHeader.getId() %>&column=stagename<%= addLinkParams(request, "viewSource") %>"><dhv:label name="accounts.accounts_contacts_oppcomponent_details.CurrentStage">Current Stage</dhv:label></a></strong>
        <%= LeadsComponentListInfo.getSortIcon("stagename") %>
      </th>
      <th>
        <strong><dhv:label name="accounts.accounts_contacts_detailsimport.Owner">Owner</dhv:label></strong>
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
        OpportunityComponent thisComponent = (OpportunityComponent)j.next();
%>      
    <tr class="row<%= rowid %>">
      <td width="8" valign="top" align="center" nowrap>
        <%-- Use the unique id for opening the menu, and toggling the graphics --%>
         <a href="javascript:displayMenu('select<%= i %>','menuOpp', '<%= thisComponent.getId() %>');"
         onMouseOver="over(0, <%= i %>)" onmouseout="out(0, <%= i %>); hideMenu('menuOpp');"><img src="images/select.gif" name="select<%= i %>" id="select<%= i %>" align="absmiddle" border="0"></a>
      </td>
      <td width="100%" valign="top">
        <a href="LeadsComponents.do?command=DetailsComponent&id=<%= thisComponent.getId() %><%= addLinkParams(request, "viewSource") %>">
        <%= toHtml(thisComponent.getDescription()) %></a>
      </td>
      <td valign="top" align="center" nowrap>
        <% if(thisComponent.getClosed() != null) {%>
         <font color="red"><dhv:label name="project.closed.lowercase">closed</dhv:label></font>
        <%} else {%>
         <font color="green"><dhv:label name="project.open.lowercase">open</dhv:label></font>
        <%}%>
      </td>
      <td valign="top" align="right" nowrap>
        <zeroio:currency value="<%= thisComponent.getGuess() %>" code="<%= applicationPrefs.get("SYSTEM.CURRENCY") %>" locale="<%= User.getLocale() %>" default="&nbsp;"/>
      </td>
      <td valign="top" align="center" nowrap>
        <% if(!User.getTimeZone().equals(thisComponent.getCloseDateTimeZone())){%>
        <zeroio:tz timestamp="<%= thisComponent.getCloseDate() %>" timeZone="<%= User.getTimeZone() %>" showTimeZone="true" default="&nbsp;"/>
        <% } else { %>
        <zeroio:tz timestamp="<%= thisComponent.getCloseDate() %>" dateOnly="true" timeZone="<%= thisComponent.getCloseDateTimeZone() %>" showTimeZone="true" default="&nbsp;"/>
        <% } %>
      </td>
      <td valign="top" align="center" nowrap>
        <%= toHtml(thisComponent.getStageName()) %>
      </td>
      <td valign="top" align="center" nowrap>
        <dhv:username id="<%= thisComponent.getOwner() %>"/>
      </td>
    </tr>
<%}%>
<%} else {%>
    <tr class="containerBody">
      <td colspan="7">
        <dhv:label name="accounts.accounts_contacts_opps_details.NoOpportunityComponentsFound">No opportunity components found.</dhv:label>
      </td>
    </tr>
<%}%>
  </table>
  <br>
  <dhv:pagedListControl object="LeadsComponentListInfo"/>
  &nbsp;<br>
  <dhv:permission name="pipeline-opportunities-edit"><input type="button" value="<dhv:label name="global.button.RenameOpportunity">Rename Opportunity</dhv:label>" onClick="javascript:window.location.href='Leads.do?command=ModifyOpp&headerId=<%= opportunityHeader.getId() %><%= addLinkParams(request, "viewSource") %>';"></dhv:permission>
  <dhv:permission name="pipeline-opportunities-delete"><input type="button" value="<dhv:label name="global.button.DeleteOpportunity">Delete Opportunity</dhv:label>" onClick="javascript:popURLReturn('Leads.do?command=ConfirmDelete&id=<%= opportunityHeader.getId() %>&popup=true<%= addLinkParams(request, "viewSource") %>','Leads.do?command=Search', 'Delete_opp','320','200','yes','no')"></dhv:permission>
</dhv:container>
