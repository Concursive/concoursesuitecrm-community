<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,java.text.DateFormat,org.aspcfs.modules.mycfs.base.*, org.aspcfs.modules.base.Constants" %>
<jsp:useBean id="NoteDetails" class="org.aspcfs.modules.mycfs.base.CFSNote" scope="request"/>
<jsp:useBean id="InboxInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<%@ include file="../initPage.jsp" %>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/confirmDelete.js"></SCRIPT>
<%-- Trails --%>
<table class="trails">
<tr>
<td>
<a href="MyCFS.do?command=Home">My Home Page</a> >
<a href="MyCFSInbox.do?command=Inbox">My Mailbox</a> >
Message Details
</td>
</tr>
</table>
<%-- End Trails --%>
<dhv:evaluate if="<%= !InboxInfo.getListView().equalsIgnoreCase("sent") %>">
<input type="button" name="btn" value="Reply" onClick="javascript:window.location.href='MyCFSInbox.do?command=ReplyToMessage&id=<%=NoteDetails.getId()%>'">
<input type="button" value="<%= (NoteDetails.getStatus() != 2?"Archive":"Send to Inbox") %>" onClick="javascript:window.location.href='MyCFSInbox.do?command=CFSNoteTrash&id=<%= NoteDetails.getId() %>&type=<%= NoteDetails.getType() %>';">
</dhv:evaluate>
<input type="button" name="btn" value="Forward" onClick="javascript:window.location.href='MyCFSInbox.do?command=ForwardMessage&forwardType=<%= Constants.CFSNOTE %>&id=<%=NoteDetails.getId()%>'">
<input type="button" name="btn" value="Delete" onClick="javascript:confirmDelete('MyCFSInbox.do?command=CFSNoteDelete&id=<%= NoteDetails.getId() %>&type=<%= NoteDetails.getType() %>');">
<br>&nbsp;
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="2">
      <strong><%= toHtml(NoteDetails.getSubject()) %></strong> 
    </th>
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
    <dhv:tz timestamp="<%= NoteDetails.getEntered() %>"  dateFormat="<%= DateFormat.SHORT %>" timeFormat="<%= DateFormat.LONG %>" default="&nbsp;"/>
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
<dhv:evaluate if="<%= !InboxInfo.getListView().equalsIgnoreCase("sent") %>">
<input type="button" name="btn" value="Reply" onClick="javascript:window.location.href='MyCFSInbox.do?command=ReplyToMessage&id=<%=NoteDetails.getId()%>'">
<input type="button" value="<%= (NoteDetails.getStatus() != 2?"Archive":"Send to Inbox") %>" onClick="javascript:window.location.href='MyCFSInbox.do?command=CFSNoteTrash&id=<%= NoteDetails.getId() %>&type=<%= NoteDetails.getType() %>';">
</dhv:evaluate>
<input type="button" name="btn" value="Forward" onClick="javascript:window.location.href='MyCFSInbox.do?command=ForwardMessage&forwardType=<%= Constants.CFSNOTE %>&id=<%=NoteDetails.getId()%>'">
<input type="button" name="btn" value="Delete" onClick="javascript:confirmDelete('MyCFSInbox.do?command=CFSNoteDelete&id=<%= NoteDetails.getId() %>&type=<%= NoteDetails.getType() %>');">

