<%@ page import="java.util.*,com.darkhorseventures.cfsbase.*" %>
<jsp:useBean id="CFSNoteList" class="com.darkhorseventures.cfsbase.CFSNoteList" scope="request"/>
<jsp:useBean id="InboxInfo" class="com.darkhorseventures.webutils.PagedListInfo" scope="session"/>
<%@ include file="initPage.jsp" %>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="/javascript/confirmDelete.js"></SCRIPT>
<script language="JavaScript" type="text/javascript" src="/javascript/popURL.js"></script>
<a href="javascript:popURLReturn('/ForwardNote.do?command=ShowForm&popup=true', 'MyCFSInbox.do?command=Inbox', 'Inbox_message','600','450','yes','no');">New Message</a>
<br>
<center><%= InboxInfo.getAlphabeticalPageLinks() %></center>

<table width="100%" border="0">
  <tr>
    <form name="listView" method="post" action="/MyCFSInbox.do?command=Inbox">
    <td align="left">
      <select size="1" name="listView" onChange="javascript:document.forms[0].submit();">
        <option <%= InboxInfo.getOptionValue("new") %>>Messages (Inbox)</option>
        <option <%= InboxInfo.getOptionValue("old") %>>Archive</option>
      </select>
      <%= showAttribute(request, "actionError") %>
    </td>
    </form>
  </tr>
</table>

<table cellpadding="4" cellspacing="0" border="1" width="100%" class="pagedlist" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr class="title">
    <td valign=center align=left class="title">
      <strong>Action</strong>
    </td>
    <td valign=center align=left class="title">
      <strong>Status</strong>
    </td>
    <td width=40% valign=center align=left>
      <strong><a href="/MyCFSInbox.do?command=Inbox&column=m.subject">Subject</a></strong>
      <%= InboxInfo.getSortIcon("m.subject") %>
    </td>
    <td width=30% valign=center align=left nowrap>
      <strong><a href="/MyCFSInbox.do?command=Inbox&column=sent_namelast">From</a></strong>
      <%= InboxInfo.getSortIcon("sent_namelast") %>
    </td>
    <td width=30% valign=center align=left nowrap>
      <strong><a href="/MyCFSInbox.do?command=Inbox&column=m.sent">Received</a></strong>
      <%= InboxInfo.getSortIcon("m.sent") %>
    </td>
  </tr>
<%
	Iterator j = CFSNoteList.iterator();
	
	if ( j.hasNext() ) {
    int rowid = 0;
    while (j.hasNext()) {
    if (rowid != 1) {
      rowid = 1;
    } else {
      rowid = 2;
    }
    CFSNote thisNote = (CFSNote)j.next();
%>      
  <tr>
          <td width=4 valign=center nowrap class="row<%= rowid %>">
          <a href="javascript:popURLReturn('/ForwardNote.do?command=ShowForm&popup=true', 'MyCFSInbox.do?command=Inbox','Forward_Note','600','450','yes','no');">Fwd</a>|<a href="javascript:confirmDelete('/MyCFSInbox.do?command=CFSNoteDelete&id=<%= thisNote.getId() %>');">Del</a>
        </td>
	        <td width=4 valign=center nowrap class="row<%= rowid %>">
		<%=toHtml(thisNote.getStatusText())%>
        	</td>
		<td width="40%" class="row<%= rowid %>">
      		<a href="/MyCFSInbox.do?command=CFSNoteDetails&id=<%=thisNote.getId()%>"><%= toHtml(thisNote.getSubject()) %></a>
		</td>
		<td width=30% valign=center class="row<%= rowid %>" nowrap><%= toHtml(thisNote.getSentName()) %></td>
		<td width=30% valign=center class="row<%= rowid %>" nowrap><%= toHtml(thisNote.getEnteredDateTimeString()) %></td>
  </tr>
<%}%>
	
</table>
<br>
<dhv:pagedListControl object="InboxInfo" tdClass="row1"/>
<%} else {%>
  <tr bgcolor="white"><td colspan=5 valign=center>No messages found.</td></tr>
</table>
<%}%>

