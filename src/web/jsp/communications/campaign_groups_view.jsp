<%@ taglib uri="WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,com.darkhorseventures.cfsbase.*" %>
<jsp:useBean id="CampaignGroupListInfo" class="com.darkhorseventures.webutils.PagedListInfo" scope="session"/>
<jsp:useBean id="sclList" class="com.darkhorseventures.cfsbase.SearchCriteriaListList" scope="request"/>
<%@ include file="initPage.jsp" %>
<script language="JavaScript" TYPE="text/javascript" SRC="/javascript/confirmDelete.js"></script>
<form name="listView" method="post" action="/CampaignManagerGroup.do?command=View">
<dhv:permission name="campaign-campaigns-groups-add"><a href="/CampaignManagerGroup.do?command=Add">Add a Contact Group</a></dhv:permission>
<dhv:permission name="campaign-campaigns-messages-add" none="true"><br></dhv:permission>
<center><%= CampaignGroupListInfo.getAlphabeticalPageLinks() %></center>
<table width="100%" border="0">
  <tr>
    <td align="left">
      <select size="1" name="listView" onChange="javascript:document.forms[0].submit();">
        <option <%= CampaignGroupListInfo.getOptionValue("my") %>>My Groups</option>
        <option <%= CampaignGroupListInfo.getOptionValue("all") %>>All Groups</option>
      </select>
      <%= showAttribute(request, "actionError") %>
    </td>
  </tr>
</table>
<table cellpadding="4" cellspacing="0" border="1" width="100%" class="pagedlist" bordercolorlight="#000000" bordercolor="#FFFFFF">
	<tr class="title">
	<dhv:permission name="campaign-campaigns-groups-edit,campaign-campaigns-groups-delete">
    <td width=8 valign=center align=left>
      <strong>Action</strong>
    </td>
    	</dhv:permission>
    <td valign=center align=left width="100%">
      <a href="/CampaignManagerGroup.do?command=View&column=name"><strong>Group Name</strong></a>
      <%= CampaignGroupListInfo.getSortIcon("name") %>
    </td>
    <td valign=center align=left nowrap>
      <a href="/CampaignManagerGroup.do?command=View&column=enteredby"><strong>Entered By</strong></a>
      <%= CampaignGroupListInfo.getSortIcon("enteredby") %>
    </td>
    <td valign=center align=left nowrap>
      <a href="/CampaignManagerGroup.do?command=View&column=modified"><strong>Last Modified</strong></a>
      <%= CampaignGroupListInfo.getSortIcon("modified") %>
    </td>
	<%
	Iterator j = sclList.iterator();
	
	if ( j.hasNext() ) {
		int rowid = 0;
    while (j.hasNext()) {
    
      if (rowid != 1) {
        rowid = 1;
      } else {
        rowid = 2;
      }
	
		SearchCriteriaList thisList = (SearchCriteriaList)j.next();
	%>      
	<tr class="containerBody">
	<dhv:permission name="campaign-campaigns-groups-edit,campaign-campaigns-groups-delete">
    <td width=8 valign=center nowrap class="row<%= rowid %>">
      <dhv:permission name="campaign-campaigns-groups-edit"><a href="/CampaignManagerGroup.do?command=Modify&id=<%= thisList.getId() %>">Edit</a></dhv:permission><dhv:permission name="campaign-campaigns-groups-edit,campaign-campaigns-groups-delete" all="true">|</dhv:permission><dhv:permission name="campaign-campaigns-groups-delete"><a href="javascript:confirmDelete('/CampaignManagerGroup.do?command=Delete&id=<%=thisList.getId()%>');">Del</a></dhv:permission>
    </td>
    	</dhv:permission>
    <td valign=center nowrap class="row<%= rowid %>">
      <a href="/CampaignManagerGroup.do?command=Preview&id=<%= thisList.getId() %>"><%= toHtml(thisList.getGroupName()) %></a>
    </td>
    <td valign="center" align="left" class="row<%= rowid %>" nowrap>
      <dhv:username id="<%= thisList.getEnteredBy() %>" />
    </td>
    <td valign=center nowrap class="row<%= rowid %>">
      <%= thisList.getModifiedDateTimeString() %>
    </td>
  </tr>
	<%}%>
<%} else {%>
	<tr class="containerBody">
    <td colspan=4 valign=center>
      No groups found.
    </td>
  </tr>
<%}%>
</table>
<br>
[<%= CampaignGroupListInfo.getPreviousPageLink("<font class='underline'>Previous</font>", "Previous") %> <%= CampaignGroupListInfo.getNextPageLink("<font class='underline'>Next</font>", "Next") %>] <%= CampaignGroupListInfo.getNumericalPageLinks() %>
</form>
