<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ page import="java.util.*,java.text.DateFormat,org.aspcfs.modules.communications.base.*" %>
<jsp:useBean id="campList" class="org.aspcfs.modules.communications.base.CampaignList" scope="request"/>
<jsp:useBean id="CampaignDashboardListInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<%@ include file="../initPage.jsp" %>
<%@ include file="../initPopupMenu.jsp" %>
<%@ include file="campaign_dashboard_menu.jsp" %>
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
<a href="CampaignManager.do">Communications</a> >
Dashboard
</td>
</tr>
</table>
<%-- End Trails --%>
<center><%= CampaignDashboardListInfo.getAlphabeticalPageLinks() %></center>
<table width="100%" border="0">
  <tr>
    <form name="listView" method="post" action="CampaignManager.do?command=Dashboard">
    <td align="left">
      <select size="1" name="listView" onChange="javascript:document.forms[0].submit();">
        <option <%= CampaignDashboardListInfo.getOptionValue("my") %>>My Running Campaigns</option>
        <option <%= CampaignDashboardListInfo.getOptionValue("all") %>>All Running Campaigns</option>
        <option <%= CampaignDashboardListInfo.getOptionValue("instant") %>>All Instant Campaigns</option>
      </select>
    </td>
    <td>
      <dhv:pagedListStatus title="<%= showError(request, "actionError") %>" object="CampaignDashboardListInfo"/>
    </td>
    </form>
  </tr>
</table>
<table cellpadding="4" cellspacing="0" width="100%" class="pagedList">
	<tr>
    <th width="8" align="left" nowrap>
      <strong>Action</strong>
    </th>
    <th align="left" nowrap>
      <a href="CampaignManager.do?command=Dashboard&column=c.name"><strong>Name</strong></a>
      <%= CampaignDashboardListInfo.getSortIcon("c.name") %>
    </th>
    <th align="left" nowrap>
      <a href="CampaignManager.do?command=Dashboard&column=active_date"><strong>Start Date</strong></a>
      <%= CampaignDashboardListInfo.getSortIcon("active_date") %>
    </th>
    <th align="left" nowrap>
      <strong># Recipients</strong>
    </th>
    <th align="left" nowrap>
      <a href="CampaignManager.do?command=Dashboard&column=status"><strong>Status</strong></a>
      <%= CampaignDashboardListInfo.getSortIcon("status") %>
    </th>
    <th align="left" nowrap>
      <a href="CampaignManager.do?command=Dashboard&column=active"><strong>Active?</strong></a>
      <%= CampaignDashboardListInfo.getSortIcon("active") %>
    </th>
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
    <td width="8" valign="center" align="center" nowrap class="row<%= rowid %>">
      <%  int cancelPermission = 0;
          int downloadAvailable = 0;
          if(!campaign.hasRun()){
            cancelPermission = 1;
          }
          if(campaign.hasFiles()){
            downloadAvailable  = 1;
          }
      %>
      <%-- Use the unique id for opening the menu, and toggling the graphics --%>
      <a href="javascript:displayMenu('select<%= count %>','menuCampaign', '<%= campaign.getId() %>', '<%= cancelPermission %>', '<%= downloadAvailable %>');" onMouseOver="over(0, <%= count %>)" onmouseout="out(0, <%= count %>); hideMenu('menuCampaign');"><img src="images/select.gif" name="select<%= count %>" id="select<%= count %>" align="absmiddle" border="0"></a>
    </td>
    <td valign="center" width="100%" class="row<%= rowid %>">
      <a href="CampaignManager.do?command=Details&id=<%=campaign.getId()%>&reset=true"><%=toHtml(campaign.getName())%></a>
      <%= ("true".equals(request.getParameter("notify")) && ("" + campaign.getId()).equals(request.getParameter("id"))?" <font color=\"red\">(Added)</font>":"") %>
    </td>
    <td valign="center" align="center" nowrap class="row<%= rowid %>">
      <zeroio:tz timestamp="<%=  campaign.getActiveDate() %>" dateOnly="true" default="&nbsp;"/>
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

