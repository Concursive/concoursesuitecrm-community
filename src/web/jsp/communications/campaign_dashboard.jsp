<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,org.aspcfs.modules.communications.base.*" %>
<jsp:useBean id="campList" class="org.aspcfs.modules.communications.base.CampaignList" scope="request"/>
<jsp:useBean id="CampaignDashboardListInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<%@ include file="../initPage.jsp" %>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/confirmDelete.js"></script>
<a href="CampaignManager.do">Communications Manager</a> >
Dashboard
<hr color="#BFBFBB" noshade>
<center><%= CampaignDashboardListInfo.getAlphabeticalPageLinks() %></center>
<table width="100%" border="0">
  <tr>
    <form name="listView" method="post" action="CampaignManager.do?command=Dashboard">
    <td align="left">
      <select size="1" name="listView" onChange="javascript:document.forms[0].submit();">
        <option <%= CampaignDashboardListInfo.getOptionValue("my") %>>My Running Campaigns</option>
        <option <%= CampaignDashboardListInfo.getOptionValue("all") %>>All Running Campaigns</option>
      </select>
    </td>
    <td>
      <dhv:pagedListStatus title="<%= showError(request, "actionError") %>" object="CampaignDashboardListInfo"/>
    </td>
    </form>
  </tr>
</table>
<table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
	<tr class="title">
    <td width="8" align="left" nowrap>
      <strong>Action</strong>
    </td>
    <td align="left" nowrap>
      <a href="CampaignManager.do?command=Dashboard&column=c.name"><strong>Name</strong></a>
      <%= CampaignDashboardListInfo.getSortIcon("c.name") %>
    </td>  
    <td align="left" nowrap>
      <a href="CampaignManager.do?command=Dashboard&column=active_date"><strong>Start Date</strong></a>
      <%= CampaignDashboardListInfo.getSortIcon("active_date") %>
    </td> 
    <td align="left" nowrap>
      <strong># Recipients</strong>
    </td> 
    <td align="left" nowrap>
      <a href="CampaignManager.do?command=Dashboard&column=status"><strong>Status</strong></a>
      <%= CampaignDashboardListInfo.getSortIcon("status") %>
    </td>
    <td width="10" align="left" nowrap>
      <a href="CampaignManager.do?command=Dashboard&column=active"><strong>Active?</strong></a>
      <%= CampaignDashboardListInfo.getSortIcon("active") %>
    </td> 
	<%
	Iterator j = campList.iterator();
	if ( j.hasNext() ) {
		int rowid = 0;
    while (j.hasNext()) {
      rowid = (rowid != 1?1:2);
      Campaign campaign = (Campaign)j.next();
	%>      
	<tr class="containerBody">
    <td width="8" valign="center" align="center" nowrap class="row<%= rowid %>">
      <%= (campaign.hasRun() && !campaign.hasFiles()?"&nbsp":"") %>
      <dhv:permission name="campaign-campaigns-edit"><%= (campaign.hasRun()?"":"<a href=\"javascript:confirmForward('/CampaignManager.do?command=Cancel&id=" + campaign.getId() +"&notify=true')\">Cancel</a>") %></dhv:permission>
      <dhv:permission name="campaign-campaigns-edit" none="true">&nbsp;</dhv:permission>
      <%= (campaign.hasFiles()?"<a href=\"CampaignManager.do?command=PrepareDownload&id=" + campaign.getId() + "\">Download<br>Available</a>":"") %>
    </td>
    <td valign="center" width="100%" class="row<%= rowid %>">
      <a href="CampaignManager.do?command=Details&id=<%=campaign.getId()%>&reset=true"><%=toHtml(campaign.getName())%></a>
      <%= ("true".equals(request.getParameter("notify")) && ("" + campaign.getId()).equals(request.getParameter("id"))?" <font color=\"red\">(Added)</font>":"") %>
    </td>
    <td valign="center" align="center" nowrap class="row<%= rowid %>">
      <%=toHtml(campaign.getActiveDateString())%>
    </td>
    <td valign="center" align="center" nowrap class="row<%= rowid %>">
      <%=campaign.getRecipientCount()%>
    </td>
    <td valign="center" nowrap class="row<%= rowid %>">
      <%=toHtml(campaign.getStatus())%>
    </td>
    <td valign="center" align="center" nowrap class="row<%= rowid %>">
      <%=toHtml(campaign.getActiveYesNo())%>
    </td>
	</tr>
	<%}%>
<%} else {%>
  <tr class="containerBody">
    <td colspan="6">
      No running campaigns found.
    </td>
  </tr>
<%}%>
</table>
<br>
<dhv:pagedListControl object="CampaignDashboardListInfo" tdClass="row1"/>

