<%@ taglib uri="WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,com.darkhorseventures.cfsbase.*" %>
<jsp:useBean id="campList" class="com.darkhorseventures.cfsbase.CampaignList" scope="request"/>
<jsp:useBean id="CampaignDashboardListInfo" class="com.darkhorseventures.webutils.PagedListInfo" scope="session"/>
<%@ include file="initPage.jsp" %>
<script language="JavaScript" TYPE="text/javascript" SRC="/javascript/confirmDelete.js"></script>
<form name="listView" method="post" action="/CampaignManager.do?command=Dashboard">
&nbsp;
<center><%= CampaignDashboardListInfo.getAlphabeticalPageLinks() %></center>
<table width="100%" border="0">
  <tr>
    <td align="left">
      <select size="1" name="listView" onChange="javascript:document.forms[0].submit();">
        <option <%= CampaignDashboardListInfo.getOptionValue("my") %>>My Running Campaigns</option>
        <option <%= CampaignDashboardListInfo.getOptionValue("all") %>>All Running Campaigns</option>
      </select>
      <%= showAttribute(request, "actionError") %>
    </td>
  </tr>
</table>
<table cellpadding="4" cellspacing="0" border="1" width="100%" class="pagedlist" bordercolorlight="#000000" bordercolor="#FFFFFF">
	<tr class="title">
    <td width=8 valign=center align=left>
      <strong>Action</strong>
    </td>
    <td valign=center align=left>
      <a href="/CampaignManager.do?command=Dashboard&column=c.name"><strong>Name</strong></a>
      <%= CampaignDashboardListInfo.getSortIcon("c.name") %>
    </td>  
    <td valign=center align=left>
      <a href="/CampaignManager.do?command=Dashboard&column=active_date"><strong>Start Date</strong></a>
      <%= CampaignDashboardListInfo.getSortIcon("active_date") %>
    </td> 
    <td valign=center align=left>
      <strong># Recipients</strong>
    </td> 
    <td valign=center align=left>
      <a href="/CampaignManager.do?command=Dashboard&column=status"><strong>Status</strong></a>
      <%= CampaignDashboardListInfo.getSortIcon("status") %>
    </td>
    <td width=10 valign=center align=left nowrap>
      <a href="/CampaignManager.do?command=Dashboard&column=active"><strong>Active?</strong></a>
      <%= CampaignDashboardListInfo.getSortIcon("active") %>
    </td> 
	<%
	Iterator j = campList.iterator();
	
	if ( j.hasNext() ) {
		int rowid = 0;
	while (j.hasNext()) {
	
		if (rowid != 1) {
			rowid = 1;
		} else {
			rowid = 2;
		}
	
		Campaign campaign = (Campaign)j.next();
	%>      
	<tr class="containerBody">
	
    <td width="8" valign="center" align="center" nowrap class="row<%= rowid %>">
      <%= (campaign.hasRun() && !campaign.hasFiles()?"&nbsp":"") %>
      <dhv:permission name="campaign-campaigns-edit"><%= (campaign.hasRun()?"":"<a href=\"javascript:confirmForward('/CampaignManager.do?command=Cancel&id=" + campaign.getId() +"&notify=true')\">Cancel</a>") %></dhv:permission>
      <dhv:permission name="campaign-campaigns-edit" none="true">&nbsp;</dhv:permission>
      <%= (campaign.hasFiles()?"<a href=\"/CampaignManager.do?command=PrepareDownload&id=" + campaign.getId() + "\">Download<br>Available</a>":"") %>
    </td>

    
    <td valign="center" width="40%" nowrap class="row<%= rowid %>">
      <a href="CampaignManager.do?command=Details&id=<%=campaign.getId()%>&reset=true"><%=toHtml(campaign.getName())%></a>
      <%= ("true".equals(request.getParameter("notify")) && ("" + campaign.getId()).equals(request.getParameter("id"))?" <font color=\"red\">(Added)</font>":"") %>
    </td>
    <td valign="center" width="20%" nowrap class="row<%= rowid %>">
      <%=toHtml(campaign.getActiveDateString())%>
    </td>
    <td valign="center" width="25%" nowrap class="row<%= rowid %>">
      <%=campaign.getRecipientCount()%>
    </td>
    <td valign="center" width="25%" nowrap class="row<%= rowid %>">
      <%=toHtml(campaign.getStatus())%>
    </td>
    <td valign="center" width="10" nowrap class="row<%= rowid %>">
      <%=toHtml(campaign.getActiveYesNo())%>
    </td>
	</tr>
	<%}%>
<%} else {%>
  <tr class="containerBody">
    <td colspan="6" valign="center">
      No running campaigns found.
    </td>
  </tr>
<%}%>
</table>
<br>
[<%= CampaignDashboardListInfo.getPreviousPageLink("<font class='underline'>Previous</font>", "Previous") %> <%= CampaignDashboardListInfo.getNextPageLink("<font class='underline'>Next</font>", "Next") %>]  <%= CampaignDashboardListInfo.getNumericalPageLinks() %>
</form>

