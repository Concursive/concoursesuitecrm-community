<%-- 
  - Copyright Notice: (C) 2000-2004 Dark Horse Ventures, All Rights Reserved.
  - License: This source code cannot be modified, distributed or used without
  -          written permission from Dark Horse Ventures. This notice must
  -          remain in place.
  - Version: $Id$
  - Description: 
  --%>
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="MyCFS.do?command=Home">My Home Page</a> >
<a href="MyCFSInbox.do?command=Inbox&return=1">Mailbox</a> >
<a href="MyCFSInbox.do?command=CFSNoteDetails&id=<%= request.getParameter("id") %>">Message Details</a> >
Forward Message
</td>
</tr>
</table>
<%-- End Trails --%>
<form name="newMessageForm" action="MyCFSInbox.do?command=SendMessage" method="post" onSubmit="return sendMessage();">
<input type="submit" value="Send">
<% if("list".equals(request.getParameter("return"))){ %>
  <input type="button" value="Cancel" onClick="javascript:window.location.href='MyCFSInbox.do?command=Inbox'">
<% } else { %>
  <input type="button" value="Cancel"  onClick="javascript:window.location.href='MyCFSInbox.do?command=CFSNoteDetails&id=<%= request.getParameter("id") %>'">
<% } %><br><br>
<%@ include file="../newmessage.jsp" %>
<br>
<input type="submit" value="Send">
<% if("list".equals(request.getParameter("return"))){ %>
  <input type="button" value="Cancel" onClick="javascript:window.location.href='MyCFSInbox.do?command=Inbox'">
<% }else{ %>
  <input type="button" value="Cancel" onClick="javascript:window.location.href='MyCFSInbox.do?command=CFSNoteDetails&id=<%= request.getParameter("id") %>'">
<% } %>
</form>

