<%@ page import="java.util.*,com.darkhorseventures.cfsbase.*" %>
<jsp:useBean id="NoteDetails" class="com.darkhorseventures.cfsbase.CFSNote" scope="request"/>
<%@ include file="initPage.jsp" %>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="/javascript/confirmDelete.js"></SCRIPT>
<script language="JavaScript" type="text/javascript" src="/javascript/popURL.js"></script>
<form name="details" action="/MyCFSInbox.do" method="post">
<a href="/MyCFSInbox.do?command=Inbox">Back to Inbox</a>
<p>
<input type=button name="action" value="Delete Message" onClick="document.details.command.value='CFSNoteDelete';document.details.submit()">
<input type=button name="action" value="<%= (NoteDetails.getStatus() != 2?"Archive Message":"Send to Inbox") %>" onClick="document.details.command.value='CFSNoteTrash';document.details.submit()">
<input type=hidden name="id" value="<%= NoteDetails.getId() %>">
<input type=hidden name="type" value="<%= NoteDetails.getType() %>">
<br>&nbsp;
<table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr class="title">
    <td colspan=2 valign=center align=left>
      <strong><%=toHtml(NoteDetails.getSubject())%></strong> [ <a href="javascript:popURLReturn('/ForwardNote.do?command=ShowForm&linkRecordId=<%=NoteDetails.getId()%>&linkModuleId=9&popup=true', 'MyCFSInbox.do?command=CFSNoteDetails&id=<%=NoteDetails.getId()%>','Forward_Note','600','450','yes','no');">Forward</a> ]
    </td>     
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      From
    </td>
    <td>
      <%= toHtml(NoteDetails.getSentName()) %>
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      Received
    </td>
    <td width=100%>
       <%= NoteDetails.getEnteredDateTimeString() %>&nbsp;
    </td>
  </tr>
<tr class="containerBody">
<td nowrap valign="top" class="formLabel">
      Text
    </td>
    <td width=100%>
<%= toHtml(NoteDetails.getBody()) %>
</td>
</tr>
</table>

<br>
<input type=hidden name="command" value="">
<input type=button name="action" value="Delete Message" onClick="document.details.command.value='CFSNoteDelete';document.details.submit()">
<input type=button name="action" value="<%= (NoteDetails.getStatus() != 2?"Archive Message":"Send to Inbox") %>" onClick="document.details.command.value='CFSNoteTrash';document.details.submit()">
</form>
