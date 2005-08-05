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
<%@ page import="java.util.*,java.text.DateFormat,org.aspcfs.modules.mycfs.base.*, org.aspcfs.modules.base.Constants" %>
<jsp:useBean id="CFSNoteList" class="org.aspcfs.modules.mycfs.base.CFSNoteList" scope="request"/>
<jsp:useBean id="InboxInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<%@ include file="../initPage.jsp" %>
<%-- Initialize the drop-down menus --%>
<%@ include file="../initPopupMenu.jsp" %>
<%@ include file="mycfs_inbox_menu.jsp" %>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/spanDisplay.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/confirmDelete.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/submit.js"></SCRIPT>
<script language="JavaScript" type="text/javascript">
  <%-- Preload image rollovers for drop-down menu --%>
  loadImages('select');
</script>
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="MyCFS.do?command=Home"><dhv:label name="My Home Page" mainMenuItem="true">My Home Page</dhv:label></a> >
<dhv:label name="myitems.mailbox">Mailbox</dhv:label>
</td>
</tr>
</table>
<%-- End Trails --%>
<a href="javascript:window.location.href='MyCFSInbox.do?command=NewMessage';"><dhv:label name="actionList.newMessage">New Message</dhv:label></a><br />
<br />
<table width="100%" border="0">
  <tr>
    <form name="listView" method="post" action="MyCFSInbox.do?command=Inbox">
    <td align="left">
      <select size="1" name="listView" onChange="javascript:document.listView.submit();">
        <option <%= InboxInfo.getOptionValue("new") %>><dhv:label name="calendar.messages.inbox.brackets">Messages (Inbox)</dhv:label></option>
        <option <%= InboxInfo.getOptionValue("old") %>><dhv:label name="accounts.accounts_list_menu.Archive">Archive</dhv:label></option>
        <option <%= InboxInfo.getOptionValue("sent") %>><dhv:label name="calendar.sent.outbox.brackets">Sent (Outbox)</dhv:label></option>
      </select>
    </td>
    <td>
      <dhv:pagedListStatus title="<%= showError(request, "actionError") %>" object="InboxInfo"/>
    </td>
    </form>
  </tr>
</table>
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="pagedList">
  <tr>
    <th>
      &nbsp;
    </th>
    <dhv:evaluate if="<%= InboxInfo.getListView().equalsIgnoreCase("new") %>">
      <th>
        <strong><dhv:label name="accounts.accountasset_include.Status">Status</dhv:label></strong>
      </th>
    </dhv:evaluate>
    <th width="40%" nowrap>
      <strong><a href="MyCFSInbox.do?command=Inbox&column=m.subject"><dhv:label name="accounts.accounts_contacts_calls_details_include.Subject">Subject</dhv:label></a></strong>
      <%= InboxInfo.getSortIcon("m.subject") %>
    </th>
  <% if(!InboxInfo.getListView().equalsIgnoreCase("sent")){%>
    <th width="30%" nowrap>
      <strong><a href="MyCFSInbox.do?command=Inbox&column=sent_namelast"><dhv:label name="campaign.from">From</dhv:label></a></strong>
      <%= InboxInfo.getSortIcon("sent_namelast") %>
    </th>
    <th width="30%" nowrap>
      <strong><a href="MyCFSInbox.do?command=Inbox&column=m.entered"><dhv:label name="calendar.received">Received</dhv:label></a></strong>
      <%= InboxInfo.getSortIcon("m.entered") %>
  <%} else {%>
    <th width="30%" nowrap>
      <strong>To</strong>
      <%= InboxInfo.getSortIcon("sent_namelast") %>
    </th>
    <th width="30%"  nowrap>
      <strong><a href="MyCFSInbox.do?command=Inbox&column=m.sent"><dhv:label name="calendar.sent">Sent</dhv:label></a></strong>
      <%= InboxInfo.getSortIcon("m.sent") %>
  <%}%>
    </th>
  </tr>
<%
	Iterator j = CFSNoteList.iterator();
	if ( j.hasNext() ) {
    int rowid = 0;
    int count =0;
    while (j.hasNext()) {
      count++;
      rowid = (rowid != 1?1:2);
      CFSNote thisNote = (CFSNote) j.next();
%>      
  <tr class="row<%= rowid %>">
    <td valign="center" nowrap>
        <%-- Use the unique id for opening the menu, and toggling the graphics --%>
        <a href="javascript:displayMenu('select<%= count %>','menuNote', '<%= Constants.CFSNOTE %>', '<%=  thisNote.getId() %>');"
         onMouseOver="over(0, <%= count %>)" onmouseout="out(0, <%= count %>); hideMenu('menuNote');"><img src="images/select.gif" name="select<%= count %>" id="select<%= count %>" align="absmiddle" border="0"></a>
    </td>
  <% if (InboxInfo.getListView().equalsIgnoreCase("new")){ %>
		<td valign="center" nowrap>
      <%= toHtml(thisNote.getStatusText()) %>
    </td>
  <%}%>
		<td>
      <a href="MyCFSInbox.do?command=CFSNoteDetails&id=<%= thisNote.getId() %>"><%= toHtml(thisNote.getSubject()) %></a>
		</td>
  <% if (!InboxInfo.getListView().equalsIgnoreCase("sent")){ %>
		<td valign="center" nowrap><%= toHtml(thisNote.getSentName()) %></td>
  <%}else{
		HashMap recipients = thisNote.getRecipientList();
		Set s = recipients.keySet();
		Iterator i = s.iterator();
		StringBuffer recipientList = new StringBuffer();
		while (i.hasNext()) {
      Object st = i.next();
      Object o = recipients.get(st);
      Integer statusId = (Integer) o;
      if (statusId.intValue() == CFSNote.NEW) {
        recipientList.append(" <font color=" + thisNote.getStatusColor(statusId.intValue()) + ">" + st + "</font>");
      } else {
        recipientList.append(" <font color=\"\">" + st + "</font>");
      }
      if (i.hasNext()) {
        recipientList.append(", ");
      }
		}
		%>  
		<td valign="center"><%= recipientList.toString() %></td>
                <%
		}%>
		<td valign="center" nowrap>
      <zeroio:tz timestamp="<%= thisNote.getEntered() %>" timeZone="<%= User.getTimeZone() %>" showTimeZone="true"/>
    </td>
  </tr>
<%}%>
</table>
<%} else {%>
  <tr class="containerBody"><td colspan="5" valign=center><dhv:label name="accounts.accounts_contacts_messages_view.NoMessagesFound">No messages found.</dhv:label></td></tr>
</table>
<%}%>
<br>
<dhv:pagedListControl object="InboxInfo" tdClass="row1"/>

