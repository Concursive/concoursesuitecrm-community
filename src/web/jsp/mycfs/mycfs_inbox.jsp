<%@ taglib uri="WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,com.darkhorseventures.cfsbase.*" %>
<jsp:useBean id="CFSNoteList" class="com.darkhorseventures.cfsbase.CFSNoteList" scope="request"/>
<jsp:useBean id="InboxInfo" class="com.darkhorseventures.webutils.PagedListInfo" scope="session"/>
<%@ include file="initPage.jsp" %>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="/javascript/confirmDelete.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="/javascript/submit.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="/javascript/popURL.js"></SCRIPT>
<a href="MyCFS.do?command=Home">My Home Page</a> >
My Mailbox<br>
<hr color="#BFBFBB" noshade>
<a href='javascript:window.location.href="MyCFSInbox.do?command=NewMessage&sendUrl="+escape("/MyCFSInbox.do?command=SendMessage")+"&return="+escape("MyCFSInbox.do?command=Inbox");'>New Message</a>
<br>
<center><%= InboxInfo.getAlphabeticalPageLinks() %></center>
<table width="100%" border="0">
  <tr>
    <form name="listView" method="post" action="/MyCFSInbox.do?command=Inbox">
    <td align="left">
      <select size="1" name="listView" onChange="javascript:document.forms[0].submit();">
        <option <%= InboxInfo.getOptionValue("new") %>>Messages (Inbox)</option>
        <option <%= InboxInfo.getOptionValue("old") %>>Archive</option>
        <option <%= InboxInfo.getOptionValue("sent") %>>Sent(Outbox)</option>
      </select>
    </td>
    <td>
      <dhv:pagedListStatus title="<%= showError(request, "actionError") %>" object="InboxInfo"/>
    </td>
    </form>
  </tr>
  </table>


<table cellpadding="4" cellspacing="0" border="1" width="100%" class="pagedlist" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr class="title">
    <td valign=center align=left class="title">
      <strong>Action</strong>
    </td>
    <% if(InboxInfo.getListView().equalsIgnoreCase("new")){%>
    <td valign=center align=left class="title">
      <strong>Status</strong>
    </td>
    <%}%>
    <td width=40% valign=center align=left>
      <strong><a href="/MyCFSInbox.do?command=Inbox&column=m.subject">Subject</a></strong>
      <%= InboxInfo.getSortIcon("m.subject") %>
    </td>
   <% if(!InboxInfo.getListView().equalsIgnoreCase("sent")){%>
    <td width=30% valign=center align=left nowrap>
      <strong><a href="/MyCFSInbox.do?command=Inbox&column=sent_namelast">From</a></strong>
      <%= InboxInfo.getSortIcon("sent_namelast") %>
    </td>
    <td width=30% valign=center align=left nowrap>
      <strong><a href="/MyCFSInbox.do?command=Inbox&column=m.sent">Received</a></strong>
    <%}
     else{%>
      <td width=30% valign=center align=left nowrap>
      <strong>To</strong>
      <%= InboxInfo.getSortIcon("sent_namelast") %>
    </td>
    <td width=30% valign=center align=left nowrap>
      <strong><a href="/MyCFSInbox.do?command=Inbox&column=m.sent">Sent</a></strong>
    <%}%>
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
          <a href="/MyCFSInbox.do?command=ForwardMessage&popup=true&forwardType=9&id=<%=thisNote.getId()%>&return=/MyCFSInbox.do?command=Inbox">Fwd</a>|<a href="javascript:confirmDelete('/MyCFSInbox.do?command=CFSNoteDelete&id=<%= thisNote.getId() %>');">Del</a>
          </td>
	        <% if(InboxInfo.getListView().equalsIgnoreCase("new")){%>
		<td width=4 valign=center nowrap class="row<%= rowid %>">
		<%=toHtml(thisNote.getStatusText())%>
        	</td>     
		<%}%>
		<td width="40%" class="row<%= rowid %>">
      		<a href="/MyCFSInbox.do?command=CFSNoteDetails&id=<%=thisNote.getId()%>"><%= toHtml(thisNote.getSubject()) %></a>
		</td>
		<% if(!InboxInfo.getListView().equalsIgnoreCase("sent")){%>
		<td width=30% valign=center class="row<%= rowid %>" nowrap><%= toHtml(thisNote.getSentName()) %></td>
		<%}else{
		HashMap recipients = thisNote.getRecipientList();
		Set s = recipients.keySet();
		Iterator i = s.iterator();
		StringBuffer recipientList = new StringBuffer();
		while (i.hasNext()) {
		Object st = i.next();
		Object o = recipients.get(st);
		Integer statusId = (Integer)o;
		if(statusId.intValue()==CFSNote.NEW){
    recipientList.append(" <font color="+thisNote.getStatusColor(statusId.intValue())+">" +st+ "</font>");
    }
    else{
    recipientList.append(" <font color=\"\">" +st+ "</font>");
    }
		
		if(i.hasNext()){
			recipientList.append(", ");
		}
		}
		%>  
		<td width=30% valign=center class="row<%= rowid %>"><%=recipientList.toString()%></td>
                <%
		}%>
		<td width=30% valign=center class="row<%= rowid %>" nowrap><%= toHtml(thisNote.getEnteredDateTimeString()) %></td>
  </tr>
<%}%>
	
</table>
<%} else {%>
  <tr bgcolor="white"><td colspan=5 valign=center>No messages found.</td></tr>
</table>
<%}%>
<br>
<dhv:pagedListControl object="InboxInfo" tdClass="row1"/>

