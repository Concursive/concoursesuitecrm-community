<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,org.aspcfs.modules.mycfs.base.*, org.aspcfs.modules.base.Constants" %>
<jsp:useBean id="NoteDetails" class="org.aspcfs.modules.mycfs.base.CFSNote" scope="request"/>
<jsp:useBean id="InboxInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<%@ include file="../initPage.jsp" %>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/confirmDelete.js"></SCRIPT>
<a href="MyCFS.do?command=Home">My Home Page</a> >
<a href="MyCFSInbox.do?command=Inbox">My Mailbox</a> >
Message Details<br>
<hr color="#BFBFBB" noshade>
<input type="button" name="btn" value="Delete" onClick="javascript:window.location.href='MyCFSInbox.do?command=CFSNoteDelete&id=<%= NoteDetails.getId() %>&type=<%= NoteDetails.getType() %>';">
<input type="button" name="btn" value="Forward" onClick="javascript:window.location.href='MyCFSInbox.do?command=ForwardMessage&forwardType=<%= Constants.CFSNOTE %>&id=<%=NoteDetails.getId()%>'">
<dhv:evaluate if="<%= !InboxInfo.getListView().equalsIgnoreCase("sent") %>">
  <input type="button" value="<%= (NoteDetails.getStatus() != 2?"Archive":"Send to Inbox") %>" onClick="javascript:window.location.href='MyCFSInbox.do?command=CFSNoteTrash&id=<%= NoteDetails.getId() %>&type=<%= NoteDetails.getType() %>';">
</dhv:evaluate>
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
<input type="button" name="btn" value="Delete" onClick="javascript:window.location.href='MyCFSInbox.do?command=CFSNoteDelete&id=<%= NoteDetails.getId() %>&type=<%= NoteDetails.getType() %>';">
<input type="button" name="btn" value="Forward" onClick="javascript:window.location.href='MyCFSInbox.do?command=ForwardMessage&forwardType=<%= Constants.CFSNOTE %>&id=<%=NoteDetails.getId()%>'">
<dhv:evaluate if="<%= !InboxInfo.getListView().equalsIgnoreCase("sent") %>">
  <input type="button" value="<%= (NoteDetails.getStatus() != 2?"Archive":"Send to Inbox") %>" onClick="javascript:window.location.href='MyCFSInbox.do?command=CFSNoteTrash&id=<%= NoteDetails.getId() %>&type=<%= NoteDetails.getType() %>';">
</dhv:evaluate>

