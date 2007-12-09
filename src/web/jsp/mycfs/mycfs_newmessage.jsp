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
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/spanDisplay.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/submit.js"></SCRIPT>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="MyCFS.do?command=Home"><dhv:label name="My Home Page" mainMenuItem="true">My Home Page</dhv:label></a> >
<a href="MyCFSInbox.do?command=Inbox&return=1"><dhv:label name="Mailbox">Mailbox</dhv:label></a> >
<dhv:label name="actionList.newMessage">New Message</dhv:label>
</td>
</tr>
</table>
<%-- End Trails --%>
<script type="text/javascript">
  function hideSendButton() {
    try {
      var send1 = document.getElementById('send1');
      send1.value = label('label.sending','Sending...');
      send1.disabled=true;
    } catch (oException) {}
    try {
      var send2 = document.getElementById('send2');
      send2.value = label('label.sending','Sending...');
      send2.disabled=true;
    } catch (oException) {}
  }
</script>
<form name="newMessageForm" action="MyCFSInbox.do?command=SendMessage" method="post" onSubmit="return sendMessage();">
<input type="submit" id="send1" value="<dhv:label name="global.button.send">Send</dhv:label>" />
<input type="button" id="cancel1" value="<dhv:label name="global.button.cancel">Cancel</dhv:label>" onClick="javascript:window.location.href='MyCFSInbox.do?command=Inbox'"><br><br>
<%@ include file="../newmessage_include.jsp" %>
<br>
<input type="submit" id="send2" value="<dhv:label name="global.button.send">Send</dhv:label>" />
<input type="button" id="cancel2" value="<dhv:label name="global.button.cancel">Cancel</dhv:label>" onClick="javascript:window.location.href='MyCFSInbox.do?command=Inbox'">
</form>

