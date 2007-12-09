<%-- 
  - Copyright(c) 2004 Concursive Corporation (http://www.concursive.com/) All
  - rights reserved. This material cannot be distributed without written
  - permission from Concursive Corporation. Permission to use, copy, and modify
  - this material for internal use is hereby granted, provided that the above
  - copyright notice and this permission notice appear in all copies. CONCURSIVE
  - CORPORATION MAKES NO REPRESENTATIONS AND EXTENDS NO WARRANTIES, EXPRESS OR
  - IMPLIED, WITH RESPECT TO THE SOFTWARE, INCLUDING, BUT NOT LIMITED TO, THE
  - IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR ANY PARTICULAR
  - PURPOSE, AND THE WARRANTY AGAINST INFRINGEMENT OF PATENTS OR OTHER
  - INTELLECTUAL PROPERTY RIGHTS. THE SOFTWARE IS PROVIDED "AS IS", AND IN NO
  - EVENT SHALL CONCURSIVE CORPORATION OR ANY OF ITS AFFILIATES BE LIABLE FOR
  - ANY DAMAGES, INCLUDING ANY LOST PROFITS OR OTHER INCIDENTAL OR CONSEQUENTIAL
  - DAMAGES RELATING TO THE SOFTWARE.
  - 
  - Version: $Id$
  - Description: 
  --%>
<%-- Trails --%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="MyCFS.do?command=Home"><dhv:label name="My Home Page" mainMenuItem="true">My Home Page</dhv:label></a> >
<a href="MyCFSInbox.do?command=Inbox&return=1"><dhv:label name="Mailbox">Mailbox</dhv:label></a> >
<% if("-1".equals(request.getParameter("noteId"))){ %>
<a href="javascript:window.location.href='MyCFSInbox.do?command=NewMessage&sendUrl='+escape('MyCFSInbox.do?command=SendMessage')+'&return='+escape('MyCFSInbox.do?command=Inbox');"><dhv:label name="actionList.newMessage">New Message</dhv:label></a><br>
<% } else { %>
<a href="MyCFSInbox.do?command=CFSNoteDetails&id=<%= request.getParameter("noteId") %>"><dhv:label name="accounts.MessageDetails">Message Details</dhv:label></a> >
<dhv:label name="calendar.forwardMessage">Forward Message</dhv:label>
<%}%>
</td>
</tr>
</table>
<%-- End Trails --%>
<%@ include file="../confirmsend.jsp" %>
