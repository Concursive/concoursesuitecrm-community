<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,java.text.DateFormat,org.aspcfs.modules.communications.base.*" %>
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
<table class="trails">
<tr>
<td>
<a href="CampaignManager.do">Communications</a> >
Campaign List
</td>
</tr>
</table>
<%-- End Trails --%>
<dhv:permission name="campaign-campaigns-add"><a href="CampaignManager.do?command=Add&source=list">Add a Campaign</a></dhv:permission>
<center><%= CampaignListInfo.getAlphabeticalPageLinks() %></center>
<table width="100%" border="0">
  <tr>
    <form name="listView" method="post" action="CampaignManager.do?command=View">
    <td align="left">
      <select size="1" name="listView" onChange="javascript:document.forms[0].submit();">
        <option <%= CampaignListInfo.getOptionValue("my") %>>My Incomplete Campaigns</option>
        <option <%= CampaignListInfo.getOptionValue("all") %>>All Incomplete Campaigns</option>
      </select>
    </td>
    <td>
      <dhv:pagedListStatus title="<%= showError(request, "actionError") %>" object="CampaignListInfo"/>
    </td>
    </form>
  </tr>
</table>
<table cellpadding="4" cellspacing="0" width="100%" class="pagedList">
	<tr>
    <th width="8" valign="center" align="left">
      <strong>Action</strong>
    </th>
    <th valign="center" width="100%" align="left" nowrap>
      <a href="CampaignManager.do?command=View&column=c.name"><strong>Name</strong></a>
      <%= CampaignListInfo.getSortIcon("c.name") %>
    </th>
    <th valign="center" align="left">
      <strong>Groups?</strong>
    </th>
    <th valign="center" align="left">
      <strong>Message?</strong>
    </th>
    <th valign="center" align="left">
      <strong>Delivery?</strong>
    </th>
    <th valign="center" align="left" nowrap>
      <a href="CampaignManager.do?command=View&column=active_date"><strong>Start Date</strong></a>
      <%= CampaignListInfo.getSortIcon("active_date") %>
    </th>
    <dhv:permission name="campaign-campaigns-edit">
    <th valign="center" align="left">
      <strong>Activate?</strong>
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
        <a href="javascript:displayMenu('menuCampaign', '<%= campaign.getId() %>');"
        onMouseOver="over(0, <%= count %>)" onmouseout="out(0, <%= count %>)"><img src="images/select.gif" name="select<%= count %>" align="absmiddle" border="0"></a>
      </td>
    <td valign="center" width="100%" class="row<%= rowid %>">
      <a href="CampaignManager.do?command=ViewDetails&id=<%= campaign.getId() %>&reset=true"><%= toHtml(campaign.getName()) %></a>
      <%= (("true".equals(request.getParameter("notify")) && (String.valueOf(campaign.getId())).equals(request.getParameter("id")))?" <font color=\"red\">(Cancelled)</font>":"") %>
    </td>
    <td valign="center" align="center" nowrap class="row<%= rowid %>">
      <dhv:permission name="campaign-campaigns-groups-edit"><a href="CampaignManager.do?command=AddGroups&id=<%= campaign.getId() %>"></dhv:permission><%= (campaign.hasGroups()?"<font color='green'>Complete</font>":"<font color='red'>Incomplete</font>") %><dhv:permission name="campaign-campaigns-groups-view"></a></dhv:permission>
    </td>
    <td valign="center" align="center" nowrap class="row<%= rowid %>">
      <dhv:permission name="campaign-campaigns-messages-view"><a href="CampaignManager.do?command=ViewMessage&id=<%= campaign.getId() %>"></dhv:permission><%= (campaign.hasMessage()?"<font color='green'>Complete</font>":"<font color='red'>Incomplete</font>") %><dhv:permission name="campaign-campaigns-messages-view"></a></dhv:permission>
    </td>
    <td valign="center" align="center" nowrap class="row<%= rowid %>">
      <dhv:permission name="campaign-campaigns-view"><a href="CampaignManager.do?command=ViewSchedule&id=<%= campaign.getId() %>"></dhv:permission><%= (campaign.hasDetails()?"<font color='green'>Complete</font>":"<font color='red'>Incomplete</font>") %><dhv:permission name="campaign-campaigns-view"></a></dhv:permission>
    </td>
    <td valign="center" align="center" nowrap class="row<%= rowid %>">
      <dhv:tz timestamp="<%= campaign.getActiveDate() %>" dateOnly="true" dateFormat="<%= DateFormat.SHORT %>" default="&nbsp;"/>
    </td>
    <dhv:permission name="campaign-campaigns-edit">
    <td valign="center" align="center" nowrap class="row<%= rowid %>">
      <%= (campaign.isReadyToActivate()?"<a href=\"javascript:confirmForward('CampaignManager.do?command=Activate&id=" + campaign.getId() + "&notify=true&modified=" + campaign.getModified() + "');\"><font color=\"red\">Activate</font></a>":"&nbsp;") %>
    </td>
    </dhv:permission>
	</tr>
	<%}%>
<%} else {%>
  <tr class="containerBody">
    <td colspan="7">
      No incomplete campaigns found.
    </td>
  </tr>
<%}%>
</table>
<br>
<dhv:pagedListControl object="CampaignListInfo" tdClass="row1"/>
