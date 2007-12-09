<%-- 
  - Copyright(c) 2004 Concursive Corporation (http://www.concursive.com/) All
  - rights reserved. This material cannot be distributed without written
  - permission from Concursive Corporation. Permission to use, copy, and modify
  - this material for internal use is hereby granted, provided that the above
  - copyright notice and this permission notice appear in all copies. CONCURSIVE
  - CORPORATION MAKES NO REPRESENTATIONS AND EXTENDS NO WARRANTIES, EXPRESS OR
  - IMPLIED, WITH RESPECT TO THE SOFTWARE, INCLUDING, BUT NOT LIMITED TO, THE
  - IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR ANY PARTICULAR
  - PURPOSE, AND THE WARRANTY AGAINST INFRINGEMENT OF PATENTS OR OTHER
  - INTELLECTUAL PROPERTY RIGHTS. THE SOFTWARE IS PROVIDED "AS IS", AND IN NO
  - EVENT SHALL CONCURSIVE CORPORATION OR ANY OF ITS AFFILIATES BE LIABLE FOR
  - ANY DAMAGES, INCLUDING ANY LOST PROFITS OR OTHER INCIDENTAL OR CONSEQUENTIAL
  - DAMAGES RELATING TO THE SOFTWARE.
  - 
  - Version: $Id$
  - Description: 
  --%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ page import="java.util.*,java.text.DateFormat,org.aspcfs.modules.communications.base.*, org.aspcfs.modules.base.Notification " %>
<jsp:useBean id="campList" class="org.aspcfs.modules.communications.base.CampaignList" scope="request"/>
<jsp:useBean id="CampaignListInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<%@ include file="../initPage.jsp" %>
<%-- Initialize the drop-down menus --%>
<%@ include file="../initPopupMenu.jsp" %>
<%@ include file="campaign_view_menu.jsp" %>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/confirmDelete.js"></script>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/spanDisplay.js"></SCRIPT>
<script language="JavaScript" type="text/javascript">
  <%-- Preload image rollovers for drop-down menu --%>
  loadImages('select');
</script>
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="CampaignManager.do"><dhv:label name="communications.campaign.Communications">Communications</dhv:label></a> >
<dhv:label name="campaign.campaignList">Campaign List</dhv:label>
</td>
</tr>
</table>
<%-- End Trails --%>
<dhv:permission name="campaign-campaigns-add"><a href="CampaignManager.do?command=Add&source=list"><dhv:label name="campaign.addCampaign">Add a Campaign</dhv:label></a></dhv:permission>
<dhv:include name="pagedListInfo.alphabeticalLinks" none="true">
<center><dhv:pagedListAlphabeticalLinks object="CampaignListInfo"/></center></dhv:include>
<table width="100%" border="0">
  <tr>
    <form name="listView" method="post" action="CampaignManager.do?command=View">
    <td align="left">
      <select size="1" name="listView" onChange="javascript:document.listView.submit();">
        <option <%= CampaignListInfo.getOptionValue("my") %>><dhv:label name="campaign.myIncompleteCampaigns">My Incomplete Campaigns</dhv:label></option>
        <option <%= CampaignListInfo.getOptionValue("all") %>><dhv:label name="campaign.allIncompleteCampaigns">All Incomplete Campaigns</dhv:label></option>
      </select>
    </td>
    <td>
      <dhv:pagedListStatus title='<%= showError(request, "actionError") %>' object="CampaignListInfo"/>
    </td>
    </form>
  </tr>
</table>
<table cellpadding="4" cellspacing="0" width="100%" class="pagedList">
	<tr>
    <th width="8">&nbsp;</th>
    <th valign="center" width="100%" align="left" nowrap>
      <a href="CampaignManager.do?command=View&column=c.name"><strong><dhv:label name="contacts.name">Name</dhv:label></strong></a>
      <%= CampaignListInfo.getSortIcon("c.name") %>
    </th>
    <th valign="center" align="left">
      <strong><dhv:label name="campaign.groups.question">Groups?</dhv:label></strong>
    </th>
    <th valign="center" align="left">
      <strong><dhv:label name="campaign.message.question">Message?</dhv:label></strong>
    </th>
    <th valign="center" align="left">
      <strong><dhv:label name="campaign.delivery.question">Delivery?</dhv:label></strong>
    </th>
    <th valign="center" align="left" nowrap>
      <a href="CampaignManager.do?command=View&column=active_date"><strong><dhv:label name="documents.details.startDate">Start Date</dhv:label></strong></a>
      <%= CampaignListInfo.getSortIcon("active_date") %>
    </th>
    <dhv:permission name="campaign-campaigns-edit">
    <th valign="center" align="left">
      <strong><dhv:label name="campaign.activate.question">Activate?</dhv:label></strong>
    </th>
    </dhv:permission>
<%
	Iterator j = campList.iterator();
	if ( j.hasNext() ) {
		int rowid = 0;
    int count = 0;
    while (j.hasNext()) {
      count++;
      rowid = (rowid != 1?1:2);
      Campaign campaign = (Campaign)j.next();
%>
	<tr class="containerBody">
    <td width="8" valign="center" nowrap class="row<%= rowid %>">
        <%-- Use the unique id for opening the menu, and toggling the graphics --%>
        <a href="javascript:displayMenu('select<%= count %>','menuCampaign', '<%= campaign.getId() %>');"
        onMouseOver="over(0, <%= count %>)" onmouseout="out(0, <%= count %>); hideMenu('menuCampaign');"><img src="images/select.gif" name="select<%= count %>" id="select<%= count %>" align="absmiddle" border="0"></a>
      </td>
    <td valign="center" width="100%" class="row<%= rowid %>">
      <a href="CampaignManager.do?command=ViewDetails&id=<%= campaign.getId() %>&reset=true"><%= toHtml(campaign.getName()) %></a>
<font color="red"><% if(("true".equals(request.getParameter("notify")) && (String.valueOf(campaign.getId())).equals(request.getParameter("id")))) {%>
  <dhv:label name="account.canceled.brackets">(Canceled)</dhv:label>
<%} else {%>
<%}%></font>
    </td>
    <td valign="center" align="center" nowrap class="row<%= rowid %>">
      <dhv:permission name="campaign-campaigns-groups-edit"><a href="CampaignManager.do?command=AddGroups&id=<%= campaign.getId() %>"></dhv:permission>
        <% if(campaign.hasGroups()) {%>
         <font color='green'><dhv:label name="global.button.complete">Complete</dhv:label></font>
        <%} else {%>
         <font color='red'><dhv:label name="quotes.incomplete">Incomplete</dhv:label></font>
        <%}%>
      <dhv:permission name="campaign-campaigns-groups-view"></a></dhv:permission>
    </td>
    <td valign="center" align="center" nowrap class="row<%= rowid %>">
      <dhv:permission name="campaign-campaigns-messages-view"><a href="CampaignManager.do?command=ViewMessage&id=<%= campaign.getId() %>"></dhv:permission>
        <% if(campaign.hasMessage()) {%>
         <font color='green'><dhv:label name="global.button.complete">Complete</dhv:label></font>
        <%} else {%>
         <font color='red'><dhv:label name="quotes.incomplete">Incomplete</dhv:label></font>
        <%}%>
      <dhv:permission name="campaign-campaigns-messages-view"></a></dhv:permission>
    </td>
    <td valign="center" align="center" nowrap class="row<%= rowid %>">
      <dhv:permission name="campaign-campaigns-view"><a href="CampaignManager.do?command=ViewSchedule&id=<%= campaign.getId() %>"></dhv:permission>
        <% if(campaign.hasDetails()) {%>
         <font color='green'><dhv:label name="global.button.complete">Complete</dhv:label></font>
        <%} else {%>
         <font color='red'><dhv:label name="quotes.incomplete">Incomplete</dhv:label></font>
        <%}%>
      <dhv:permission name="campaign-campaigns-view"></a></dhv:permission>
    </td>
    <td valign="center" align="center" nowrap class="row<%= rowid %>">
      <zeroio:tz timestamp="<%= campaign.getActiveDate() %>" dateOnly="true" default="&nbsp;"/>
    </td>
    <dhv:permission name="campaign-campaigns-edit">
    <td valign="center" align="center" nowrap class="row<%= rowid %>">
      <% if(campaign.isReadyToActivate()) {%>
        <a href="javascript:confirmForward('CampaignManager.do?command=Activate&id=<%= campaign.getId() %>&notify=true&modified=<%= campaign.getModified() %>');"><font color="red"><dhv:label name="campaign.activate">Activate</dhv:label></font></a>
      <%} else {%>
        &nbsp;
      <%}%>
    </td>
    </dhv:permission>
	</tr>
	<%}%>
<%} else {%>
  <tr class="containerBody">
    <td colspan="7">
      <dhv:label name="campaign.noIncompleteCampaignsFound">No incomplete campaigns found.</dhv:label>
    </td>
  </tr>
<%}%>
</table>
<br>
<dhv:pagedListControl object="CampaignListInfo" tdClass="row1"/>
