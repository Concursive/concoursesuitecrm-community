<%-- 
  - Copyright Notice: (C) 2000-2004 Dark Horse Ventures, All Rights Reserved.
  - License: This source code cannot be modified, distributed or used without
  -          written permission from Dark Horse Ventures. This notice must
  -          remain in place.
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
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="Leads.do">Pipeline</a> >
<% if ("dashboard".equals(request.getParameter("viewSource"))){ %>
	<a href="Leads.do?command=Dashboard">Dashboard</a> >
<% }else{ %>
	<a href="Leads.do?command=Search">Search Results</a> >
<% } %>
Opportunity Details
</td>
</tr>
</table>
<%-- End Trails --%>
<% if (request.getAttribute("actionError") != null) { %>
<%= showError(request, "actionError") %>
<%}%>
<dhv:evaluate exp="<%= PipelineViewpointInfo.isVpSelected(User.getUserId()) %>">
  <b>Viewpoint: </b><b class="highlight"><%= PipelineViewpointInfo.getVpUserName() %></b><br>
  &nbsp;<br>
</dhv:evaluate>
<%-- Begin container --%>
<%@ include file="leads_details_header_include.jsp" %>
<% String param1 = "id=" + opportunityHeader.getId(); 
   String param2 = addLinkParams(request, "viewSource");
%>
<dhv:container name="opportunities" selected="details" param="<%= param1 %>" appendToUrl="<%= param2 %>" style="tabs"/>
<table cellpadding="4" cellspacing="0" border="0" width="100%">
  <tr>
    <td class="containerBack">
      <%-- Begin container content --%>
      <dhv:permission name="pipeline-opportunities-add">
        <a href="LeadsComponents.do?command=Prepare&headerId=<%= opportunityHeader.getId() %><%= addLinkParams(request, "viewSource") %>">Add a Component</a><br>
      </dhv:permission>
<dhv:pagedListStatus title="<%= showError(request, "actionError") %>" object="LeadsComponentListInfo"/>
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="pagedList">
  <tr>
    <th align="center" nowrap>
      <strong>Action</strong>
    </th>
    <th nowrap>
      <strong><a href="Leads.do?command=DetailsOpp&headerId=<%= opportunityHeader.getId() %>&column=oc.description<%= addLinkParams(request, "viewSource") %>">Component</a></strong>
      <%= LeadsComponentListInfo.getSortIcon("oc.description") %>
    </th>
    <th nowrap>
      <strong><a href="Leads.do?command=DetailsOpp&headerId=<%= opportunityHeader.getId() %>&column=oc.closed<%= addLinkParams(request, "viewSource") %>">Status</a></strong>
      <%= LeadsComponentListInfo.getSortIcon("oc.closed") %>
    </th>
    <th align="center" nowrap>
      <strong><a href="Leads.do?command=DetailsOpp&headerId=<%= opportunityHeader.getId() %>&column=oc.guessvalue<%= addLinkParams(request, "viewSource") %>">Guess<br>Amount</a></strong>
      <%= LeadsComponentListInfo.getSortIcon("oc.guessvalue") %>
    </th>
    <th nowrap>
      <strong><a href="Leads.do?command=DetailsOpp&headerId=<%= opportunityHeader.getId() %>&column=oc.closedate<%= addLinkParams(request, "viewSource") %>">Close Date</a></strong>
      <%= LeadsComponentListInfo.getSortIcon("oc.closedate") %>
    </th>
    <th nowrap>
      <strong><a href="Leads.do?command=DetailsOpp&headerId=<%= opportunityHeader.getId() %>&column=stage<%= addLinkParams(request, "viewSource") %>">Current Stage</a></strong>
      <%= LeadsComponentListInfo.getSortIcon("stage") %>
    </th>
    <th>
      <strong>Owner</strong>
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
      <%= thisComponent.getClosed() != null ? "<font color=\"red\">closed</font>" : "<font color=\"green\">open</font>" %>
    </td>
    <td valign="top" align="right" nowrap>
      <zeroio:currency value="<%= thisComponent.getGuess() %>" code="<%= applicationPrefs.get("SYSTEM.CURRENCY") %>" locale="<%= User.getLocale() %>" default="&nbsp;"/>
    </td>
    <td valign="top" align="center" nowrap>
      <zeroio:tz timestamp="<%= thisComponent.getCloseDate() %>" dateOnly="true" default="&nbsp;"/>
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
      No opportunity components found.
    </td>
  </tr>
<%}%>
</table>
<br>
<dhv:pagedListControl object="LeadsComponentListInfo"/>
&nbsp;<br>
<dhv:permission name="pipeline-opportunities-edit"><input type="button" value="Rename Opportunity" onClick="javascript:window.location.href='Leads.do?command=ModifyOpp&headerId=<%= opportunityHeader.getId() %><%= addLinkParams(request, "viewSource") %>';"></dhv:permission>
<dhv:permission name="pipeline-opportunities-delete"><input type="button" value="Delete Opportunity" onClick="javascript:popURLReturn('Leads.do?command=ConfirmDelete&id=<%= opportunityHeader.getId() %>&popup=true<%= addLinkParams(request, "viewSource") %>','Leads.do?command=Search', 'Delete_opp','320','200','yes','no')"></dhv:permission>
</td>
</tr>
</table>
