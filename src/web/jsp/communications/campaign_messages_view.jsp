<%-- 
  - Copyright(c) 2004 Dark Horse Ventures LLC (http://www.centriccrm.com/) All
  - rights reserved. This material cannot be distributed without written
  - permission from Dark Horse Ventures LLC. Permission to use, copy, and modify
  - this material for internal use is hereby granted, provided that the above
  - copyright notice and this permission notice appear in all copies. DARK HORSE
  - VENTURES LLC MAKES NO REPRESENTATIONS AND EXTENDS NO WARRANTIES, EXPRESS OR
  - IMPLIED, WITH RESPECT TO THE SOFTWARE, INCLUDING, BUT NOT LIMITED TO, THE
  - IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR ANY PARTICULAR
  - PURPOSE, AND THE WARRANTY AGAINST INFRINGEMENT OF PATENTS OR OTHER
  - INTELLECTUAL PROPERTY RIGHTS. THE SOFTWARE IS PROVIDED "AS IS", AND IN NO
  - EVENT SHALL DARK HORSE VENTURES LLC OR ANY OF ITS AFFILIATES BE LIABLE FOR
  - ANY DAMAGES, INCLUDING ANY LOST PROFITS OR OTHER INCIDENTAL OR CONSEQUENTIAL
  - DAMAGES RELATING TO THE SOFTWARE.
  - 
  - Version: $Id$
  - Description:
  --%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ page import="java.util.*,java.text.DateFormat,org.aspcfs.modules.communications.base.*" %>
<jsp:useBean id="MessageList" class="org.aspcfs.modules.communications.base.MessageList" scope="request"/>
<jsp:useBean id="CampaignMessageListInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
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
<a href="CampaignManager.do"><dhv:label name="communications.campaign.Communications">Communications</dhv:label></a> >
<dhv:label name="campaign.messageList">Message List</dhv:label>
</td>
</tr>
</table>
<%-- End Trails --%>
<dhv:permission name="campaign-campaigns-messages-add"><a href="CampaignManagerMessage.do?command=Add"><dhv:label name="campaign.addMessage">Add Message</dhv:label></a></dhv:permission>
<dhv:include name="pagedListInfo.alphabeticalLinks" none="true">
<center><dhv:pagedListAlphabeticalLinks object="CampaignMessageListInfo"/></center></dhv:include>
<table width="100%" border="0">
  <tr>
    <form name="listView" method="post" action="CampaignManagerMessage.do?command=View">
    <td>
      <select size="1" name="listView" onChange="javascript:document.listView.submit();">
        <option <%= CampaignMessageListInfo.getOptionValue("my") %>><dhv:label name="accounts.accounts_contacts_messages_view.MyMessages">My Messages</dhv:label></option>
        <option <%= CampaignMessageListInfo.getOptionValue("all") %>><dhv:label name="accounts.accounts_contacts_messages_view.AllMessages">All Messages</dhv:label></option>
        <option <%= CampaignMessageListInfo.getOptionValue("hierarchy") %>><dhv:label name="actionList.controlledHierarchyMessages">Controlled Hierarchy Messages</dhv:label></option>
        <option <%= CampaignMessageListInfo.getOptionValue("personal") %>><dhv:label name="actionList.personalMessages">Personal Messages</dhv:label></option>
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
      &nbsp;
    </th>
    <th width="40%" nowrap>
      <a href="CampaignManagerMessage.do?command=View&column=name"><strong><dhv:label name="contacts.name">Name</dhv:label></strong></a>
      <%= CampaignMessageListInfo.getSortIcon("name") %>
    </th>
    <th><strong><dhv:label name="accounts.accounts_contacts_calls_details_include.Subject">Subject</dhv:label></strong></th>
    <th width="60%" nowrap>
      <a href="CampaignManagerMessage.do?command=View&column=description"><strong><dhv:label name="accounts.accountasset_include.Description">Description</dhv:label></strong></a>
      <%= CampaignMessageListInfo.getSortIcon("description") %>
    </th>
    <th style="text-align: center;" nowrap>
      <a href="CampaignManagerMessage.do?command=View&column=ct_eb.namelast,ct_eb.namefirst"><strong><dhv:label name="accounts.accounts_calls_list.EnteredBy">Entered By</dhv:label></strong></a>
      <%= CampaignMessageListInfo.getSortIcon("ct_eb.namelast,ct_eb.namefirst") %>
    </th>
    <th style="text-align: center;" nowrap>
      <a href="CampaignManagerMessage.do?command=View&column=m.modified"><strong><dhv:label name="accounts.accounts_contacts_oppcomponent_list.LastModified">Last Modified</dhv:label></strong></a>
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
        <% if(thisMessage.getName() != null && !"".equals(thisMessage.getName())) {%>
          <%= toHtml(thisMessage.getName()) %>
        <%} else {%>
          <dhv:label name="account.noNameAvailable.quotes">"No name available"</dhv:label>
        <%}%>
      </a>
		</td>
    <td width="30%" valign="center" class="row<%= rowid %>"><%= toHtml( thisMessage.getMessageSubject() ) %></td>
		<td width="50%" valign="center" class="row<%= rowid %>">
      <%= toHtml(thisMessage.getDescription()) %>
    </td>
    <td valign="center" class="row<%= rowid %>" nowrap>
      <dhv:username id="<%= thisMessage.getEnteredBy() %>" lastFirst="true" />
    </td>
    <td valign="center" class="row<%= rowid %>" nowrap>
      <zeroio:tz timestamp="<%= thisMessage.getModified() %>" timeZone="<%= User.getTimeZone() %>" showTimeZone="true"/>
    </td>
  </tr>
<%
    }
  } else {%>
  <tr class="containerBody">
    <td colspan="6">
      <dhv:label name="accounts.accounts_contacts_messages_view.NoMessagesFound">No messages found.</dhv:label>
    </td>
  </tr>
<%}%>
</table>
<br>
<dhv:pagedListControl object="CampaignMessageListInfo" tdClass="row1"/>
