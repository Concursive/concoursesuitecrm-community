<%@ page import="java.util.*,com.darkhorseventures.cfsbase.*" %>
<jsp:useBean id="NoteDetails" class="com.darkhorseventures.cfsbase.CFSNote" scope="request"/>
<%@ include file="initPage.jsp" %>
<form name="details" action="/MyCFSInbox.do" method="post">
<a href="/MyCFSInbox.do?command=Inbox">Back to Inbox</a>
<p>
<input type=button name="action" value="Forward" onClick="document.details.command.value='ForwardForm';document.details.submit()">
<input type=button name="action" value="Delete" onClick="document.details.command.value='CFSNoteDelete';document.details.submit()">
<input type=hidden name="id" value="<%= NoteDetails.getId() %>">
<input type=hidden name="type" value="<%= NoteDetails.getType() %>">
<br>&nbsp;
<table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr class="title">
    <td colspan=2 valign=center align=left>
      <strong><%=toHtml(NoteDetails.getSubject())%></strong>
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
</table>
<br>
<table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
<tr>
<td class="containerBack">

<%= NoteDetails.getBody() %>

</td>
</tr>
</table>

<br>
<input type=hidden name="command" value="">
<input type=button name="action" value="Forward" onClick="document.details.command.value='ForwardForm';document.details.submit()">
<input type=button name="action" value="Delete" onClick="document.details.command.value='CFSNoteDelete';document.details.submit()">

</form>
