<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,org.aspcfs.modules.mycfs.base.*, org.aspcfs.modules.base.Constants" %>
<jsp:useBean id="NoteDetails" class="org.aspcfs.modules.mycfs.base.CFSNote" scope="request"/>
<jsp:useBean id="InboxInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<%@ include file="../initPage.jsp" %>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/confirmDelete.js"></SCRIPT>
<form name="details" action="MyCFSInbox.do" method="post">
<a href="MyCFS.do?command=Home">My Home Page</a> >
<a href="MyCFSInbox.do?command=Inbox">My Mailbox</a> >
Message Details<br>
<hr color="#BFBFBB" noshade>
<input type="button" name="btn" value="Delete" onClick="document.details.command.value='CFSNoteDelete';document.details.submit()">
<input type="button" name="btn" value="Forward" onClick="javascript:window.location.href='MyCFSInbox.do?command=ForwardMessage&forwardType=<%= Constants.CFSNOTE %>&id=<%=NoteDetails.getId()%>&return=MyCFSInbox.do?command=Inbox&sendUrl=MyCFSInbox.do?command=SendMessage'">
<dhv:evaluate if="<%= !InboxInfo.getListView().equalsIgnoreCase("sent") %>">
  <input type="button" name="btn" value="<%= (NoteDetails.getStatus() != 2?"Archive":"Send to Inbox") %>" onClick="document.details.command.value='CFSNoteTrash';document.details.submit()">
</dhv:evaluate>
<input type="hidden" name="id" value="<%= NoteDetails.getId() %>">
<input type="hidden" name="type" value="<%= NoteDetails.getType() %>">
<br>&nbsp;
<table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr class="title">
    <td colspan="2">
      <strong><%= toHtml(NoteDetails.getSubject()) %></strong> 
    </td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel">
      From
    </td>
    <td>
      <%= toHtml(NoteDetails.getSentName()) %>
    </td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel">
      Received
    </td>
    <td>
       <%= NoteDetails.getEnteredDateTimeString() %>&nbsp;
    </td>
  </tr>
  <tr class="containerBody">
    <td valign="top" class="formLabel">
      Text
    </td>
    <td>
      <%= toHtml(NoteDetails.getBody()) %>
    </td>
  </tr>
</table>
<br>
<input type="hidden" name="command" value="">
<input type="button" name="btn" value="Delete" onClick="document.details.command.value='CFSNoteDelete';document.details.submit()">
<input type="button" name="btn" value="Forward" onClick="javascript:window.location.href='MyCFSInbox.do?command=ForwardMessage&popup=true&forwardType=<%= Constants.CFSNOTE %>&id=<%=NoteDetails.getId()%>&return=/MyCFSInbox.do?command=Inbox'">
<dhv:evaluate if="<%= !InboxInfo.getListView().equalsIgnoreCase("sent") %>">
  <input type="button" name="btn" value="<%= (NoteDetails.getStatus() != 2?"Archive":"Send to Inbox") %>" onClick="document.details.command.value='CFSNoteTrash';document.details.submit()">
</dhv:evaluate>
</form>
