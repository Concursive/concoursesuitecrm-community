<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,org.aspcfs.modules.communications.base.*" %>
<jsp:useBean id="campList" class="org.aspcfs.modules.communications.base.CampaignList" scope="request"/>
<jsp:useBean id="CampaignListInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<%@ include file="../initPage.jsp" %>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/confirmDelete.js"></script>
<a href="CampaignManager.do">Communications Manager</a> >
Campaign List
<hr color="#BFBFBB" noshade>
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
<table cellpadding="4" cellspacing="0" border="1" width="100%" class="pagedlist" bordercolorlight="#000000" bordercolor="#FFFFFF">
	<tr class="title">
	<dhv:permission name="campaign-campaigns-edit,campaign-campaigns-delete">
    <td width="8" valign="center" align="left">
      <strong>Action</strong>
    </td>
    	</dhv:permission>
    <td valign="center" width="100%" align="left">
      <a href="CampaignManager.do?command=View&column=c.name"><strong>Name</strong></a>
      <%= CampaignListInfo.getSortIcon("c.name") %>
    </td>  
    <td valign="center" align="left">
      <strong>Groups?</strong>
    </td> 
    <td valign="center" align="left">
      <strong>Message?</strong>
    </td>
    <td valign="center" align="left">
      <strong>Delivery?</strong>
    </td>
    <td valign="center" align="left" nowrap>
      <a href="CampaignManager.do?command=View&column=active_date"><strong>Start Date</strong></a>
      <%= CampaignListInfo.getSortIcon("active_date") %>
    </td>
    <dhv:permission name="campaign-campaigns-edit">
    <td valign="center" align="left">
      <strong>Activate?</strong>
    </td>
    </dhv:permission>
<%
	Iterator j = campList.iterator();
	if ( j.hasNext() ) {
		int rowid = 0;
    while (j.hasNext()) {
      rowid = (rowid != 1?1:2);
      Campaign campaign = (Campaign)j.next();
%>
	<tr class="containerBody">
	<dhv:permission name="campaign-campaigns-edit,campaign-campaigns-delete">
    <td width="8" valign="center" nowrap class="row<%= rowid %>">
      <dhv:permission name="campaign-campaigns-edit"><a href="CampaignManager.do?command=ViewDetails&id=<%= campaign.getId() %>&reset=true">Edit</a></dhv:permission><dhv:permission name="campaign-campaigns-edit,campaign-campaigns-delete" all="true">|</dhv:permission><dhv:permission name="campaign-campaigns-delete"><a href="javascript:confirmDelete('CampaignManager.do?command=Delete&id=<%= campaign.getId() %>');">Del</a></dhv:permission>
      </td>
    	</dhv:permission>
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
      <%=toHtml(campaign.getActiveDateString())%>
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
