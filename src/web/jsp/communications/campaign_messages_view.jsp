<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,org.aspcfs.modules.communications.base.*" %>
<jsp:useBean id="MessageList" class="org.aspcfs.modules.communications.base.MessageList" scope="request"/>
<jsp:useBean id="CampaignMessageListInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<%@ include file="../initPage.jsp" %>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popURL.js"></script>
<a href="CampaignManager.do">Communications Manager</a> >
Message List
<hr color="#BFBFBB" noshade>
<dhv:permission name="campaign-campaigns-messages-add"><a href="CampaignManagerMessage.do?command=Add">Add a Message</a></dhv:permission>
<center><%= CampaignMessageListInfo.getAlphabeticalPageLinks() %></center>
<table width="100%" border="0">
  <tr>
    <form name="listView" method="post" action="CampaignManagerMessage.do?command=View">
    <td>
      <select size="1" name="listView" onChange="javascript:document.forms[0].submit();">
        <option <%= CampaignMessageListInfo.getOptionValue("my") %>>My Messages</option>
        <option <%= CampaignMessageListInfo.getOptionValue("all") %>>All Messages</option>
        <option <%= CampaignMessageListInfo.getOptionValue("hierarchy") %>>Controlled Hierarchy Messages</option>
        <option <%= CampaignMessageListInfo.getOptionValue("personal") %>>Personal Messages</option>
      </select>
    </td>
    <td>
      <dhv:pagedListStatus title="<%= showError(request, "actionError") %>" object="CampaignMessageListInfo"/>
    </td>
    </form>
  </tr>
</table>
<table cellpadding="4" cellspacing="0" width="100%" class="pagedList">
  <tr>
  <dhv:permission name="campaign-campaigns-messages-edit,campaign-campaigns-messages-delete">
    <th>
      <strong>Action</strong>
    </th>
    </dhv:permission>
    <th width="40%" nowrap>
      <a href="CampaignManagerMessage.do?command=View&column=name"><strong>Name</strong></a>
      <%= CampaignMessageListInfo.getSortIcon("name") %>
    </th>
    <th width="60%" nowrap>
      <a href="CampaignManagerMessage.do?command=View&column=description"><strong>Description</strong></a>
      <%= CampaignMessageListInfo.getSortIcon("description") %>
    </th>
    <th style="text-align: center;" nowrap>
      <a href="CampaignManagerMessage.do?command=View&column=ct_eb.namelast,ct_eb.namefirst"><strong>Entered By</strong></a>
      <%= CampaignMessageListInfo.getSortIcon("ct_eb.namelast,ct_eb.namefirst") %>
    </th>
    <th style="text-align: center;" nowrap>
      <a href="CampaignManagerMessage.do?command=View&column=m.modified"><strong>Last Modified</strong></a>
      <%= CampaignMessageListInfo.getSortIcon("m.modified") %>
    </th>
  </tr>
<%
	Iterator j = MessageList.iterator();
	if ( j.hasNext() ) {
		int rowid = 0;
    while (j.hasNext()) {
		  rowid = (rowid != 1?1:2);
      Message thisMessage = (Message)j.next();
%>      
  <tr class="containerBody">
  <dhv:permission name="campaign-campaigns-messages-edit,campaign-campaigns-messages-delete">
    <td width="8" valign="center" nowrap align="center" class="row<%= rowid %>">
      <dhv:permission name="campaign-campaigns-messages-edit"><a href="CampaignManagerMessage.do?command=Modify&id=<%=thisMessage.getId()%>&return=list">Edit</a></dhv:permission><dhv:permission name="campaign-campaigns-messages-edit,campaign-campaigns-messages-delete" all="true">|</dhv:permission><dhv:permission name="campaign-campaigns-messages-delete"><a href="javascript:popURLReturn('CampaignManagerMessage.do?command=ConfirmDelete&id=<%=thisMessage.getId()%>&popup=true','CampaignManagerMessage.do?command=View', 'Delete_message','330','200','yes','no');">Del</a></dhv:permission>
    </td>
    </dhv:permission>
		<td width="40%" valign="center" class="row<%= rowid %>">
      <a href="CampaignManagerMessage.do?command=Details&id=<%=thisMessage.getId()%>"><%= toHtml(thisMessage.getName()) %></a>
		</td>
		<td width="60%" valign="center" class="row<%= rowid %>">
      <%= toHtml(thisMessage.getDescription()) %>
    </td>
    <td valign="center" class="row<%= rowid %>" nowrap>
      <dhv:username id="<%= thisMessage.getEnteredBy() %>" lastFirst="true" />
    </td>
    <td valign="center" class="row<%= rowid %>" nowrap>
      <%= toHtml(thisMessage.getModifiedDateTimeString()) %>
    </td>
  </tr>
<%
    }
  } else {%>
  <tr class="containerBody">
    <td colspan="5">
      No messages found.
    </td>
  </tr>
<%}%>
</table>
<br>
<dhv:pagedListControl object="CampaignMessageListInfo" tdClass="row1"/>
