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
<%@ page import="org.aspcfs.utils.StringUtils" %>
<%@ page import="java.util.*"%>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<jsp:useBean id="Message" class="org.aspcfs.modules.communications.base.Message" scope="request"/>
<jsp:useBean id="messageList" class="org.aspcfs.modules.communications.base.MessageList" scope="request"/>
<jsp:useBean id="clientType" class="org.aspcfs.utils.web.ClientType" scope="session"/>
<jsp:useBean id="applicationPrefs" class="org.aspcfs.controller.ApplicationPrefs" scope="application"/>
<%@ include file="../initPage.jsp" %>
<%-- Editor must go here, before the body onload --%>
<dhv:evaluate if="<%= !clientType.showApplet() %>">
<jsp:include page="../htmlarea_include.jsp" flush="true"/>
<body onload="initEditor('messageText');document.sendMessage.replyTo.focus();">
</dhv:evaluate>
<%-- Use applet instead --%>
<dhv:evaluate if="<%= clientType.showApplet() %>">
<body onload="document.sendMessage.replyTo.focus();">
</dhv:evaluate>
<script language="JavaScript">
  function updateMessageList() {
    document.forms['sendMessage'].elements['messageId'].selectedIndex = 0;
    document.forms['sendMessage'].action = 'CampaignManager.do?command=MessageJSList&actionSource=MyActionContacts<%= addLinkParams(request, "popup|popupType|actionId")%>';
    document.forms['sendMessage'].submit();
  }
  function updateMessage(){
    document.forms['sendMessage'].action = 'MyActionContacts.do?command=PrepareMessage<%= addLinkParams(request, "popup|popupType|actionId") %>';
    document.forms['sendMessage'].submit();
  }
  function checkForm(form) {
    var formTest = true;
    var messageText = "";
<dhv:evaluate if="<%= clientType.showApplet() %>">
    document.sendMessage.messageText.value = document.Kafenio.getDocumentBody();
</dhv:evaluate>
    return true;
  }

</script>
<form name="sendMessage" action="MyActionContacts.do?command=SendMessage&auto-populate=true&actionSource=MyActionContacts" method="post" onSubmit="return checkForm(this);">
<dhv:formMessage showSpace="false" />
<iframe src="empty.html" name="server_commands" id="server_commands" style="visibility:hidden" height="0"></iframe>
Start by choosing an existing message or create a new one:<br>
<SELECT SIZE="1" name="listView" onChange="javascript:updateMessageList();">
  <OPTION VALUE="my"<dhv:evaluate if="<%= "my".equals((String) request.getParameter("listView")) %>"> selected</dhv:evaluate>>My Messages</OPTION>
  <OPTION VALUE="all"<dhv:evaluate if="<%= "all".equals((String) request.getParameter("listView")) %>"> selected</dhv:evaluate>>All Messages</OPTION>
  <OPTION VALUE="hierarchy"<dhv:evaluate if="<%= "hierarchy".equals((String) request.getParameter("listView")) %>"> selected</dhv:evaluate>>Controlled Hierarchy Messages</OPTION>
  <OPTION VALUE="personal"<dhv:evaluate if="<%= "personal".equals((String) request.getParameter("listView")) %>"> selected</dhv:evaluate>>Personal Messages</OPTION>
  <OPTION VALUE="new"<dhv:evaluate if="<%= "new".equals((String) request.getParameter("listView")) %>"> selected</dhv:evaluate>>New Message</OPTION>
</SELECT>
<%if(!"new".equals(request.getParameter("listView"))){ %>
<% 
   messageList.setJsEvent("onChange=\"javascript:updateMessage();\""); 
   messageList.addItem(0, "--None--");
%>
<%= messageList.getHtmlSelect("messageId", (request.getParameter("messageId") != null ? Integer.parseInt(request.getParameter("messageId")) : -1)) %>
<% }else{ %>
  <select size="1" name="messageId">
    <option value="0">--None--</option>
  </select>  
<% } %>
<%-- include the message form from create messages --%>
<%@ include file="../communications/message_include.jsp" %>
<br>
<input type="submit" value="Send Message">
<input type="button" value="Cancel" onClick="javascript:window.close();">
<br>
<input type="hidden" name="contactId" value="<%= request.getParameter("contactId") %>">
</form>
</body>

