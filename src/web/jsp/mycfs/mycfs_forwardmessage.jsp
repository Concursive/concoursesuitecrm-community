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

