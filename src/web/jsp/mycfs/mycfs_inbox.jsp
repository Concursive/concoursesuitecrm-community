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
<a href="MyCFS.do?command=Home">My Home Page</a> >
Mailbox
</td>
</tr>
</table>
<%-- End Trails --%>
<a href="javascript:window.location.href='MyCFSInbox.do?command=NewMessage';">New Message</a><br />
<br />
<table width="100%" border="0">
  <tr>
    <form name="listView" method="post" action="MyCFSInbox.do?command=Inbox">
    <td align="left">
      <select size="1" name="listView" onChange="javascript:document.forms[0].submit();">
        <option <%= InboxInfo.getOptionValue("new") %>>Messages (Inbox)</option>
        <option <%= InboxInfo.getOptionValue("old") %>>Archive</option>
        <option <%= InboxInfo.getOptionValue("sent") %>>Sent (Outbox)</option>
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
      <strong>Action</strong>
    </th>
    <dhv:evaluate if="<%= InboxInfo.getListView().equalsIgnoreCase("new") %>">
      <th>
        <strong>Status</strong>
      </th>
    </dhv:evaluate>
    <th width="40%" nowrap>
      <strong><a href="MyCFSInbox.do?command=Inbox&column=m.subject">Subject</a></strong>
      <%= InboxInfo.getSortIcon("m.subject") %>
    </th>
  <% if(!InboxInfo.getListView().equalsIgnoreCase("sent")){%>
    <th width="30%" nowrap>
      <strong><a href="MyCFSInbox.do?command=Inbox&column=sent_namelast">From</a></strong>
      <%= InboxInfo.getSortIcon("sent_namelast") %>
    </th>
    <th width="30%" nowrap>
      <strong>Received</strong>
  <%} else {%>
    <th width="30%" nowrap>
      <strong>To</strong>
      <%= InboxInfo.getSortIcon("sent_namelast") %>
    </th>
    <th width="30%"  nowrap>
      <strong><a href="MyCFSInbox.do?command=Inbox&column=m.sent">Sent</a></strong>
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
  <tr>
    <td valign="center" nowrap class="row<%= rowid %>">
        <%-- Use the unique id for opening the menu, and toggling the graphics --%>
        <a href="javascript:displayMenu('select<%= count %>','menuNote', '<%= Constants.CFSNOTE %>', '<%=  thisNote.getId() %>');"
         onMouseOver="over(0, <%= count %>)" onmouseout="out(0, <%= count %>); hideMenu('menuNote');"><img src="images/select.gif" name="select<%= count %>" id="select<%= count %>" align="absmiddle" border="0"></a>
    </td>
  <% if (InboxInfo.getListView().equalsIgnoreCase("new")){ %>
		<td valign="center" nowrap class="row<%= rowid %>">
      <%= toHtml(thisNote.getStatusText()) %>
    </td>
  <%}%>
		<td class="row<%= rowid %>">
      <a href="MyCFSInbox.do?command=CFSNoteDetails&id=<%= thisNote.getId() %>"><%= toHtml(thisNote.getSubject()) %></a>
		</td>
  <% if (!InboxInfo.getListView().equalsIgnoreCase("sent")){ %>
		<td valign="center" class="row<%= rowid %>" nowrap><%= toHtml(thisNote.getSentName()) %></td>
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
		<td valign="center" class="row<%= rowid %>"><%= recipientList.toString() %></td>
                <%
		}%>
		<td valign="center" class="row<%= rowid %>" nowrap>
      <zeroio:tz timestamp="<%= thisNote.getEntered() %>" timeZone="<%= User.getTimeZone() %>" showTimeZone="yes"/>
    </td>
  </tr>
<%}%>
</table>
<%} else {%>
  <tr class="containerBody"><td colspan="5" valign=center>No messages found.</td></tr>
</table>
<%}%>
<br>
<dhv:pagedListControl object="InboxInfo" tdClass="row1"/>

