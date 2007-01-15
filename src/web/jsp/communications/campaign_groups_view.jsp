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
<%@ page import="java.util.*,java.text.DateFormat,org.aspcfs.modules.communications.base.*" %>
<jsp:useBean id="CampaignGroupListInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<jsp:useBean id="sclList" class="org.aspcfs.modules.communications.base.SearchCriteriaListList" scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<%@ include file="../initPage.jsp" %>
<%-- Initialize the drop-down menus --%>
<%@ include file="../initPopupMenu.jsp" %>
<%@ include file="campaign_groups_view_menu.jsp" %>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/spanDisplay.js"></SCRIPT>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popURL.js"></script>
<script language="JavaScript" type="text/javascript">
  <%-- Preload image rollovers for drop-down menu --%>
  loadImages('select');
</script>
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="CampaignManager.do"><dhv:label name="communications.campaign.Communications">Communications</dhv:label></a> >
<dhv:label name="campaign.viewGroups">View Groups</dhv:label>
</td>
</tr>
</table>
<%-- End Trails --%>
<dhv:permission name="campaign-campaigns-groups-add"><a href="CampaignManagerGroup.do?command=Add"><dhv:label name="communications.campaign.addContactGroup">Add a Contact Group</dhv:label></a></dhv:permission>
<dhv:permission name="campaign-campaigns-messages-add" none="true"><br></dhv:permission>
<dhv:include name="pagedListInfo.alphabeticalLinks" none="true">
<center><dhv:pagedListAlphabeticalLinks object="CampaignGroupListInfo"/></center></dhv:include>
<table width="100%" border="0">
  <tr>
    <form name="listView" method="post" action="CampaignManagerGroup.do?command=View">
    <td align="left">
      <select size="1" name="listView" onChange="javascript:document.listView.submit();">
        <option <%= CampaignGroupListInfo.getOptionValue("my") %>><dhv:label name="campaign.myGroups">My Groups</dhv:label></option>
        <option <%= CampaignGroupListInfo.getOptionValue("all") %>><dhv:label name="campaign.allGroups">All Groups</dhv:label></option>
      </select>
    </td>
    <td>
      <dhv:pagedListStatus title='<%= showError(request, "actionError") %>' object="CampaignGroupListInfo"/>
    </td>
    </form>
  </tr>
</table>
<table cellpadding="4" cellspacing="0" width="100%" class="pagedList">
	<tr>
    <th width="8">
      &nbsp;
    </th>
    <th width="100%" nowrap>
      <a href="CampaignManagerGroup.do?command=View&column=name"><strong><dhv:label name="admin.groupName">Group Name</dhv:label></strong></a>
      <%= CampaignGroupListInfo.getSortIcon("name") %>
    </th>
    <th nowrap>
      <strong><dhv:label name="accounts.accounts_calls_list.EnteredBy">Entered By</dhv:label></strong>
    </th>
    <th nowrap>
      <a href="CampaignManagerGroup.do?command=View&column=modified"><strong><dhv:label name="accounts.accounts_contacts_oppcomponent_list.LastModified">Last Modified</dhv:label></strong></a>
      <%= CampaignGroupListInfo.getSortIcon("modified") %>
    </th>
<%
	Iterator j = sclList.iterator();
	if ( j.hasNext() ) {
		int rowid = 0;
    int count  = 0;
    while (j.hasNext()) {
      count++;
      rowid = (rowid != 1?1:2);
      SearchCriteriaList thisList = (SearchCriteriaList)j.next();
%>
	<tr class="containerBody">
    <td width="8" valign="center" nowrap class="row<%= rowid %>">
      <%-- Use the unique id for opening the menu, and toggling the graphics --%>
      <a href="javascript:displayMenu('select<%= count %>','menuGroup', '<%= thisList.getId() %>');"
      onMouseOver="over(0, <%= count %>)" onmouseout="out(0, <%= count %>); hideMenu('menuGroup');"><img src="images/select.gif" name="select<%= count %>" id="select<%= count %>" align="absmiddle" border="0"></a>
    </td>
    <td valign="center" class="row<%= rowid %>">
      <a href="CampaignManagerGroup.do?command=Details&id=<%= thisList.getId() %>"><%= toHtml(thisList.getGroupName()) %></a>
    </td>
    <td valign="center" align="left" class="row<%= rowid %>" nowrap>
      <dhv:username id="<%= thisList.getEnteredBy() %>" lastFirst="true"/>
    </td>
    <td valign="center" nowrap class="row<%= rowid %>">
      <zeroio:tz timestamp="<%= thisList.getModified() %>" timeZone="<%= User.getTimeZone() %>" showTimeZone="true"/>
    </td>
  </tr>
	<%}%>
<%} else {%>
	<tr class="containerBody">
    <td colspan="4">
      <dhv:label name="campaign.noGroupsFound">No groups found.</dhv:label>
    </td>
  </tr>
<%}%>
</table>
<br>
<dhv:pagedListControl object="CampaignGroupListInfo" tdClass="row1"/>
