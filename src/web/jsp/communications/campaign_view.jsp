<%@ page import="java.util.*,com.darkhorseventures.cfsbase.*" %>
<jsp:useBean id="campList" class="com.darkhorseventures.cfsbase.CampaignList" scope="request"/>
<jsp:useBean id="CampaignListInfo" class="com.darkhorseventures.webutils.PagedListInfo" scope="session"/>
<%@ include file="initPage.jsp" %>
<script language="JavaScript" TYPE="text/javascript" SRC="/javascript/confirmDelete.js"></script>
<form name="listView" method="post" action="/CampaignManager.do?command=View">
<a href="/CampaignManager.do?command=Add">Create a Campaign</a>
<center><%= CampaignListInfo.getAlphabeticalPageLinks() %></center>
<table width="100%" border="0">
  <tr>
    <td align="left">
      <select size="1" name="listView" onChange="javascript:document.forms[0].submit();">
        <option <%= CampaignListInfo.getOptionValue("my") %>>My Incomplete Campaigns</option>
        <option <%= CampaignListInfo.getOptionValue("all") %>>All Incomplete Campaigns</option>
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
    <td valign=center width="70%" align=left>
      <a href="/CampaignManager.do?command=View&column=c.name"><strong>Name</strong></a>
      <%= CampaignListInfo.getSortIcon("c.name") %>
    </td>  
    <td valign=center width="30%" align=left>
      <a href="/CampaignManager.do?command=View&column=active_date"><strong>Start Date</strong></a>
      <%= CampaignListInfo.getSortIcon("active_date") %>
    </td> 
    <td valign=center align=left>
      <strong>Groups?</strong>
    </td> 
    <td valign=center align=left>
      <strong>Message?</strong>
    </td>
    <td valign=center align=left>
      <strong>Details?</strong>
    </td>
    <td valign=center align=left>
      <strong>Activate?</strong>
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
    <td width=8 valign=center nowrap class="row<%= rowid %>">
      <a href="/CampaignManager.do?command=ViewDetails&id=<%= campaign.getId() %>&reset=true">Edit</a>|<a href="javascript:confirmDelete('/CampaignManager.do?command=Delete&id=<%= campaign.getId() %>');">Del</a>
    </td>
    <td valign=center nowrap class="row<%= rowid %>">
      <a href="CampaignManager.do?command=ViewDetails&id=<%= campaign.getId() %>&reset=true"><%= toHtml(campaign.getName()) %></a>
      <%= (("true".equals(request.getParameter("notify")) && ("" + campaign.getId()).equals(request.getParameter("id")))?" <font color=\"red\">(Cancelled)</font>":"") %>
    </td>
    <td valign=center align="center" nowrap class="row<%= rowid %>">
      <%=toHtml(campaign.getActiveDateString())%>
    </td>
    <td valign=center align="center" nowrap class="row<%= rowid %>">
      <a href="/CampaignManager.do?command=ViewGroups&id=<%= campaign.getId() %>"><%= (campaign.hasGroups()?"<font color='green'>Complete</font>":"<font color='red'>Incomplete</font>") %></a>
    </td>
    <td valign=center align="center" nowrap class="row<%= rowid %>">
      <a href="/CampaignManager.do?command=ViewMessage&id=<%= campaign.getId() %>"><%= (campaign.hasMessage()?"<font color='green'>Complete</font>":"<font color='red'>Incomplete</font>") %></a>
    </td>
    <td valign=center align="center" nowrap class="row<%= rowid %>">
      <a href="/CampaignManager.do?command=ViewSchedule&id=<%= campaign.getId() %>"><%= (campaign.hasDetails()?"<font color='green'>Complete</font>":"<font color='red'>Incomplete</font>") %></a>
    </td>
    <td valign=center align="center" nowrap class="row<%= rowid %>">
      <%= (campaign.isReadyToActivate()?"<a href=\"javascript:confirmForward('/CampaignManager.do?command=Activate&id=" + campaign.getId() + "&notify=true&modified=" + campaign.getModified() + "');\"><font color=\"red\">Activate</font></a>":"&nbsp;") %>
    </td>
	</tr>
	<%}%>
<%} else {%>
  <tr class="containerBody">
    <td colspan=7 valign=center>
      No incomplete campaigns found.
    </td>
  </tr>
<%}%>
</table>
<br>
[<%= CampaignListInfo.getPreviousPageLink("<font class='underline'>Previous</font>", "Previous") %> <%= CampaignListInfo.getNextPageLink("<font class='underline'>Next</font>", "Next") %>]  <%= CampaignListInfo.getNumericalPageLinks() %>
</form>
