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
<%@ page import="org.aspcfs.utils.StringUtils,
                 org.aspcfs.utils.web.ClientType" %>
<%@ page import="java.util.*"%>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<jsp:useBean id="Message" class="org.aspcfs.modules.communications.base.Message" scope="request"/>
<jsp:useBean id="messageList" class="org.aspcfs.modules.communications.base.MessageList" scope="request"/>
<jsp:useBean id="bcc" class="java.lang.String" scope="request"/>
<jsp:useBean id="cc" class="java.lang.String" scope="request"/>
<jsp:useBean id="clientType" class="org.aspcfs.utils.web.ClientType" scope="session"/>
<jsp:useBean id="applicationPrefs" class="org.aspcfs.controller.ApplicationPrefs" scope="application"/>
<jsp:useBean id="systemStatus" class="org.aspcfs.controller.SystemStatus" scope="request"/>
<%@ include file="../initPage.jsp" %>
<body onload="document.sendMessage.replyTo.focus();">
<%
  if (clientType.getType() == -1) {
    clientType.setParameters(request);
  }
%>
<%-- Editor must go here, before the body onload --%>
<dhv:evaluate if="<%= !clientType.showApplet() %>">
  <jsp:include page="../tinymce_include.jsp" flush="true"/>
  <script language="javascript" type="text/javascript">
    initEditor('messageText');
  </script>
</dhv:evaluate>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/checkString.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/checkEmail.js"></script>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/spanDisplay.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/submit.js"></SCRIPT>
<script language="JavaScript">
  function hideSendButton() {
    try {
      var send1 = document.getElementById('send1');
      send1.value = label('label.sending','Sending...');
      send1.disabled=true;
    } catch (oException) {}
  }

  function updateMessageList() {
    document.forms['sendMessage'].elements['messageId'].selectedIndex = 0;
    messageType = "<%=request.getAttribute("messageType")%>";
    if (messageType == "addressRequest"){
      document.forms['sendMessage'].action = 'MyActionContacts.do?command=PrepareMessage&actionSource=MyActionContacts&messageType=addressRequest&orgId=<%=request.getAttribute("orgId")%><%= addLinkParams(request, "popup|popupType|contactId|actionId|actionListId")%>';
    }else{
      document.forms['sendMessage'].action = 'MyActionContacts.do?command=PrepareMessage&actionSource=MyActionContacts<%= addLinkParams(request, "popup|popupType|contactId|actionId|actionListId")%>';
    }
    document.forms['sendMessage'].submit();
  }
  function updateMessage(){
    messageType = "<%=request.getAttribute("messageType")%>";
    if (messageType == "addressRequest"){
      document.forms['sendMessage'].action = 'MyActionContacts.do?command=PrepareMessage&actionSource=MyActionContacts&messageType=addressRequest&orgId=<%=request.getAttribute("orgId")%><%= addLinkParams(request, "popup|popupType|contactId|actionId|actionListId") %>';
    }else{
      document.forms['sendMessage'].action = 'MyActionContacts.do?command=PrepareMessage&actionSource=MyActionContacts<%= addLinkParams(request, "popup|popupType|contactId|actionId|actionListId") %>';
    }
    document.forms['sendMessage'].submit();
  }
  function checkForm(form) {
    try { tinyMCE.triggerSave(false); } catch(e) { }
    var formTest = true;
    var messageText = "";
<dhv:evaluate if="<%= clientType.showApplet() %>">
    document.sendMessage.messageText.value = document.Kafenio.getDocumentBody();
</dhv:evaluate>
    if (formTest == false) {
      alert(label("check.send.email", "The message could not be sent, please check the following:\r\n\r\n") + messageText);
      return false;
    }
    hideSendButton();
    return selectAllOptions(form.selectedList);
  }
</script>
<% if ("addressRequest".equals(request.getAttribute("messageType"))){%>
  <form name="sendMessage" action="MyActionContacts.do?command=SendAddressRequest&auto-populate=true&actionSource=MyActionContacts&orgId=<%=request.getAttribute("orgId")%>&messageType=<%= (String) request.getAttribute("messageType") %>" method="post" onSubmit="return checkForm(this);">
<%}else{%>
  <form name="sendMessage" action="MyActionContacts.do?command=SendMessage&auto-populate=true&actionSource=MyActionContacts" method="post" onSubmit="return checkForm(this);">
<%}%>
<dhv:formMessage showSpace="false"/>
<dhv:label name="actionList.chooseCreateMessage.text">Start by choosing an existing message or create a new one:</dhv:label><br />
<SELECT SIZE="1" name="listView" onChange="javascript:updateMessageList();">
  <OPTION VALUE="my"<dhv:evaluate if='<%= "my".equals((String) request.getParameter("listView")) %>'> selected</dhv:evaluate>><dhv:label name="accounts.accounts_contacts_messages_view.MyMessages">My Messages</dhv:label></OPTION>
  <OPTION VALUE="all"<dhv:evaluate if='<%= "all".equals((String) request.getParameter("listView")) %>'> selected</dhv:evaluate>><dhv:label name="accounts.accounts_contacts_messages_view.AllMessages">All Messages</dhv:label></OPTION>
  <OPTION VALUE="hierarchy"<dhv:evaluate if='<%= "hierarchy".equals((String) request.getParameter("listView")) %>'> selected</dhv:evaluate>><dhv:label name="actionList.controlledHierarchyMessages">Controlled Hierarchy Messages</dhv:label></OPTION>
  <OPTION VALUE="personal"<dhv:evaluate if='<%= "personal".equals((String) request.getParameter("listView")) %>'> selected</dhv:evaluate>><dhv:label name="actionList.personalMessages">Personal Messages</dhv:label></OPTION>
  <OPTION VALUE="new"<dhv:evaluate if='<%= "new".equals((String) request.getParameter("listView")) %>'> selected</dhv:evaluate>><dhv:label name="actionList.newMessage">New Message</dhv:label></OPTION>
</SELECT>
<%if(!"new".equals(request.getParameter("listView"))){ %>
<% 
   messageList.setJsEvent("onChange=\"javascript:updateMessage();\""); 
   messageList.addItem(0, systemStatus.getLabel("calendar.none.4dashes"));
%>
<%= messageList.getHtmlSelect("messageId", (request.getParameter("messageId") != null ? Integer.parseInt(request.getParameter("messageId")) : -1)) %>
<% }else{ %>
  <select size="1" name="messageId">
    <option value="0"><dhv:label name="calendar.none.4dashes">--None--</dhv:label></option>
  </select> 
<% }
boolean showBcc = true;
boolean showCc = true;
%>
<%-- include the message form from create messages --%>
<%@ include file="../communications/message_include.jsp" %>
<br />
<% if ("addressRequest".equals(request.getAttribute("messageType"))){%>
  <dhv:label name="action.contacts.Note.text">Note: The recipient's contact information will be attached with the chosen message.</dhv:label>
<%}%>
<br />
</span><input type="submit" id="send1" value="<dhv:label name="button.sendMessage">Send Message</dhv:label>" />
<input type="button" value="<dhv:label name="global.button.cancel">Cancel</dhv:label>" onClick="javascript:window.close();" />
<br />
<input type="hidden" name="contactId" value="<%= request.getParameter("contactId") %>" />
</form>
<iframe src="empty.html" name="server_commands" id="server_commands" style="visibility:hidden" height="0"></iframe>
</body>

