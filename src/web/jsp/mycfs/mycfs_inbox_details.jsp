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
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ page import="java.util.*,java.text.DateFormat,org.aspcfs.modules.mycfs.base.*, org.aspcfs.modules.base.Constants" %>
<jsp:useBean id="NoteDetails" class="org.aspcfs.modules.mycfs.base.CFSNote" scope="request"/>
<jsp:useBean id="InboxInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<%@ include file="../initPage.jsp" %>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/confirmDelete.js"></SCRIPT>
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="MyCFS.do?command=Home">My Home Page</a> >
<a href="MyCFSInbox.do?command=Inbox">Mailbox</a> >
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
      <zeroio:tz timestamp="<%= NoteDetails.getEntered() %>" default="&nbsp;" timeZone="<%= User.getTimeZone() %>" showTimeZone="yes" />
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

