<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,org.aspcfs.modules.communications.base.*" %>
<jsp:useBean id="CampaignGroupListInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<jsp:useBean id="sclList" class="org.aspcfs.modules.communications.base.SearchCriteriaListList" scope="request"/>
<%@ include file="../initPage.jsp" %>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popURL.js"></script>
<a href="CampaignManager.do">Communications Manager</a> >
Group List
<hr color="#BFBFBB" noshade>
<dhv:permission name="campaign-campaigns-groups-add"><a href="CampaignManagerGroup.do?command=Add">Add a Contact Group</a></dhv:permission>
<dhv:permission name="campaign-campaigns-messages-add" none="true"><br></dhv:permission>
<center><%= CampaignGroupListInfo.getAlphabeticalPageLinks() %></center>
<table width="100%" border="0">
  <tr>
    <form name="listView" method="post" action="CampaignManagerGroup.do?command=View">
    <td align="left">
      <select size="1" name="listView" onChange="javascript:document.forms[0].submit();">
        <option <%= CampaignGroupListInfo.getOptionValue("my") %>>My Groups</option>
        <option <%= CampaignGroupListInfo.getOptionValue("all") %>>All Groups</option>
      </select>
    </td>
    <td>
      <dhv:pagedListStatus title="<%= showError(request, "actionError") %>" object="CampaignGroupListInfo"/>
    </td>
    </form>
  </tr>
</table>
<table cellpadding="4" cellspacing="0" border="1" width="100%" class="pagedlist" bordercolorlight="#000000" bordercolor="#FFFFFF">
	<tr class="title">
	<dhv:permission name="campaign-campaigns-groups-edit,campaign-campaigns-groups-delete">
    <td width="8">
      <strong>Action</strong>
    </td>
    	</dhv:permission>
    <td width="100%" nowrap>
      <a href="CampaignManagerGroup.do?command=View&column=name"><strong>Group Name</strong></a>
      <%= CampaignGroupListInfo.getSortIcon("name") %>
    </td>
    <td nowrap>
      <strong>Entered By</strong>
    </td>
    <td nowrap>
      <a href="CampaignManagerGroup.do?command=View&column=modified"><strong>Last Modified</strong></a>
      <%= CampaignGroupListInfo.getSortIcon("modified") %>
    </td>
	<%
	Iterator j = sclList.iterator();
	if ( j.hasNext() ) {
		int rowid = 0;
    while (j.hasNext()) {
      rowid = (rowid != 1?1:2);
      SearchCriteriaList thisList = (SearchCriteriaList)j.next();
	%>      
	<tr class="containerBody">
	<dhv:permission name="campaign-campaigns-groups-edit,campaign-campaigns-groups-delete">
    <td width="8" valign="center" nowrap class="row<%= rowid %>">
      <dhv:permission name="campaign-campaigns-groups-edit"><a href="/CampaignManagerGroup.do?command=Modify&id=<%= thisList.getId() %>">Edit</a></dhv:permission><dhv:permission name="campaign-campaigns-groups-edit,campaign-campaigns-groups-delete" all="true">|</dhv:permission><dhv:permission name="campaign-campaigns-groups-delete"><a href="javascript:popURLReturn('CampaignManagerGroup.do?command=ConfirmDelete&id=<%=thisList.getId()%>&popup=true','CampaignManagerGroup.do?command=View', 'Delete_group','330','200','yes','no');">Del</a></dhv:permission>
    </td>
    	</dhv:permission>
    <td valign="center" class="row<%= rowid %>">
      <a href="CampaignManagerGroup.do?command=Preview&id=<%= thisList.getId() %>"><%= toHtml(thisList.getGroupName()) %></a>
    </td>
    <td valign="center" align="left" class="row<%= rowid %>" nowrap>
      <dhv:username id="<%= thisList.getEnteredBy() %>" lastFirst="true"/>
    </td>
    <td valign="center" nowrap class="row<%= rowid %>">
      <%= thisList.getModifiedDateTimeString() %>
    </td>
  </tr>
	<%}%>
<%} else {%>
	<tr class="containerBody">
    <td colspan="4">
      No groups found.
    </td>
  </tr>
<%}%>
</table>
<br>
<dhv:pagedListControl object="CampaignGroupListInfo" tdClass="row1"/>
