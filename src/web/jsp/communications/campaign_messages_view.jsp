<%-- 
  - Copyright Notice: (C) 2000-2004 Dark Horse Ventures, All Rights Reserved.
  - License: This source code cannot be modified, distributed or used without
  -          written permission from Dark Horse Ventures. This notice must
  -          remain in place.
  - Version: $Id$
  - Description: 
  --%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ page import="java.util.*,java.text.DateFormat,org.aspcfs.modules.communications.base.*" %>
<jsp:useBean id="MessageList" class="org.aspcfs.modules.communications.base.MessageList" scope="request"/>
<jsp:useBean id="CampaignMessageListInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<%@ include file="../initPage.jsp" %>
<%-- Initialize the drop-down menus --%>
<%@ include file="../initPopupMenu.jsp" %>
<%@ include file="campaign_messages_view_menu.jsp" %>
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
<a href="CampaignManager.do">Communications</a> >
Message List
</td>
</tr>
</table>
<%-- End Trails --%>
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
    <th>
      <strong>Action</strong>
    </th>
    <th width="40%" nowrap>
      <a href="CampaignManagerMessage.do?command=View&column=name"><strong>Name</strong></a>
      <%= CampaignMessageListInfo.getSortIcon("name") %>
    </th>
    <th><strong>Subject</strong></th>
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
    int count  =0;
    while (j.hasNext()) {
      count++;
		  rowid = (rowid != 1?1:2);
      Message thisMessage = (Message)j.next();
%>      
  <tr class="containerBody">
    <td width="8" valign="center" nowrap align="center" class="row<%= rowid %>">
      <%-- Use the unique id for opening the menu, and toggling the graphics --%>
      <a href="javascript:displayMenu('select<%= count %>','menuMsg', '<%= thisMessage.getId() %>');"
      onMouseOver="over(0, <%= count %>)" onmouseout="out(0, <%= count %>); hideMenu('menuMsg');"><img src="images/select.gif" name="select<%= count %>" id="select<%= count %>" align="absmiddle" border="0"></a>
    </td>
		<td width="20%" valign="center" class="row<%= rowid %>">
      <a href="CampaignManagerMessage.do?command=Details&id=<%=thisMessage.getId()%>">
      <%= toHtml(thisMessage.getName() != null && !"".equals(thisMessage.getName()) ? thisMessage.getName() : "\"No name available\"") %></a>
		</td>
    <td width="30%" valign="center" class="row<%= rowid %>"><%= toHtml( thisMessage.getMessageSubject() ) %></td>
		<td width="50%" valign="center" class="row<%= rowid %>">
      <%= toHtml(thisMessage.getDescription()) %>
    </td>
    <td valign="center" class="row<%= rowid %>" nowrap>
      <dhv:username id="<%= thisMessage.getEnteredBy() %>" lastFirst="true" />
    </td>
    <td valign="center" class="row<%= rowid %>" nowrap>
      <zeroio:tz timestamp="<%= thisMessage.getModified() %>" />
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
