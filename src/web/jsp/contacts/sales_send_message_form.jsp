<%-- 
  - Copyright(c) 2007 Concursive Corporation (http://www.concursive.com/) All
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
  - Version: $Id: companydirectory_send_message_form.jsp 18876 2007-01-29 21:49:30Z matt $
  - Description:
  --%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ page import="org.aspcfs.utils.StringUtils,
                 org.aspcfs.utils.web.ClientType" %>
<%@ page import="java.util.*"%>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<jsp:useBean id="ContactDetails" class="org.aspcfs.modules.contacts.base.Contact" scope="request"/>
<jsp:useBean id="bcc" class="java.lang.String" scope="request"/>
<jsp:useBean id="cc" class="java.lang.String" scope="request"/>
<jsp:useBean id="Message" class="org.aspcfs.modules.communications.base.Message" scope="request"/>
<jsp:useBean id="messageList" class="org.aspcfs.modules.communications.base.MessageList" scope="request"/>
<jsp:useBean id="clientType" class="org.aspcfs.utils.web.ClientType" scope="session"/>
<jsp:useBean id="applicationPrefs" class="org.aspcfs.controller.ApplicationPrefs" scope="application"/>
<jsp:useBean id="from" class="java.lang.String" scope="request" />
<jsp:useBean id="listForm" class="java.lang.String" scope="request" />
<%@ include file="../initPage.jsp" %>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/checkString.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/checkEmail.js"></script>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/spanDisplay.js"></SCRIPT>
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
<script language="JavaScript">
  function updateMessageList() {
    document.forms['sendMessage'].elements['messageId'].selectedIndex = 0;
    messageType = "<%=request.getAttribute("messageType")%>";
    if (messageType == "addressRequest"){
      document.forms['sendMessage'].action = 'SalesLeadsMessages.do?command=PrepareMessage&actionSource=SalesLeadsMessages&messageType=addressRequest&orgId=<%=request.getAttribute("orgId")%><%= addLinkParams(request, "popup|popupType|contactId|actionId|actionListId|from|listForm")%>';
    }else{
      document.forms['sendMessage'].action = 'SalesLeadsMessages.do?command=PrepareMessage&actionSource=SalesLeadsMessages<%= addLinkParams(request, "popup|popupType|contactId|actionId|actionListId|from|listForm")%>';
    }
    document.forms['sendMessage'].submit();
  }
  function updateMessage(){
    messageType = "<%=request.getAttribute("messageType")%>";
    if (messageType == "addressRequest"){
      document.forms['sendMessage'].action = 'SalesLeadsMessages.do?command=PrepareMessage&actionSource=SalesLeadsMessages&messageType=addressRequest&orgId=<%=request.getAttribute("orgId")%><%= addLinkParams(request, "popup|popupType|contactId|actionId|actionListId|from|listForm") %>';
    }else{
      document.forms['sendMessage'].action = 'SalesLeadsMessages.do?command=PrepareMessage&actionSource=SalesLeadsMessages<%= addLinkParams(request, "popup|popupType|contactId|actionId|actionListId|from|listForm") %>';
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
    hideSpan('send');
    showSpan('sendingEmail');
    return selectAllOptions(form.selectedList);
  }
</script>
<form name="sendMessage" action="SalesLeadsMessages.do?command=SendMessage&auto-populate=true<%= addLinkParams(request, "popup|popupType|actionId|from|listForm") %>" method="post" onSubmit="return checkForm(this);">
<dhv:evaluate if="<%= !isPopup(request) %>">
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="Sales.do"><dhv:label name="Cfgfontacts" mainMenuItem="true">Leads</dhv:label></a> >
<% if (from != null && "list".equals(from) && !"dashboard".equals(from)) { %>
  <a href="Sales.do?command=List&listForm=<%= (listForm != null? listForm:"") %>"><dhv:label name="accounts.SearchResults">Search Results</dhv:label></a> >
<%}%>
<a href="Sales.do?command=Details&contactId=<%= ContactDetails.getId() %><%= addLinkParams(request, "popup|popupType|actionId|from|listForm") %>"><dhv:label name="sales.leadDetails">Lead Details</dhv:label></a> >
<a href="SalesMessages.do?command=ViewMessages&contactId=<%= ContactDetails.getId() %>"><dhv:label name="accounts.Messages">Messages</dhv:label></a> >
<dhv:label name="actionList.newMessage">New Message</dhv:label>
</td>
</tr>
</table>
<%-- End Trails --%>
</dhv:evaluate>
<dhv:formMessage showSpace="false"/>
<dhv:container name="leads" selected="messages" object="ContactDetails"
               param='<%= "id=" + ContactDetails.getId() %>'
               appendToUrl='<%= addLinkParams(request, "popup|popupType|actionId|from|listForm") %>'>
<dhv:label name="actionList.chooseCreateMessage.text">Start by choosing an existing message or create a new one:</dhv:label><br />
<SELECT SIZE="1" name="listView" onChange="javascript:updateMessageList();">
  <OPTION VALUE="my"<dhv:evaluate if='<%= "my".equals((String) request.getParameter("listView")) %>'> selected</dhv:evaluate>><dhv:label name="accounts.accounts_contacts_messages_view.MyMessages">My Messages</dhv:label></OPTION>
  <OPTION VALUE="all"<dhv:evaluate if='<%= "all".equals((String) request.getParameter("listView")) %>'> selected</dhv:evaluate>><dhv:label name="accounts.accounts_contacts_messages_view.AllMessages">All Messages</dhv:label></OPTION>
  <OPTION VALUE="hierarchy"<dhv:evaluate if='<%= "hierarchy".equals((String) request.getParameter("listView")) %>'> selected</dhv:evaluate>><dhv:label name="actionList.controlledHierarchyMessages">Controlled Hierarchy Messages</dhv:label></OPTION>
  <OPTION VALUE="personal"<dhv:evaluate if='<%= "personal".equals((String) request.getParameter("listView")) %>'> selected</dhv:evaluate>><dhv:label name="actionList.personalMessages">Personal Messages</dhv:label></OPTION>
  <OPTION VALUE="new"<dhv:evaluate if='<%= "new".equals((String) request.getParameter("listView")) %>'> selected</dhv:evaluate>><dhv:label name="actionList.newMessage">New Message</dhv:label></OPTION>
</SELECT>
<%
boolean showBcc = true;
boolean showCc = true;
if(!"new".equals(request.getParameter("listView"))){ %>
<% 
   messageList.setJsEvent("onChange=\"javascript:updateMessage();\""); 
   messageList.addItem(0, "-- None --");
%>
<%= messageList.getHtmlSelect("messageId", (request.getParameter("messageId") != null ? Integer.parseInt(request.getParameter("messageId")) : -1)) %>
<% }else{ %>
  <select size="1" name="messageId">
    <option value="0"><dhv:label name="calendar.none.4dashes">--None--</dhv:label></option>
  </select>  
<% } %>
<%-- include the message form from create messages --%>
<%@ include file="../communications/message_include.jsp" %>
<br />
<% if ("addressRequest".equals(request.getAttribute("messageType"))){%>
  <dhv:label name="action.contacts.Note.text">Note: The recipient's contact information will be attached with the chosen message.</dhv:label>
<%}%>
<br />
<table border="0" class="empty">
<tr id="send"><td>
<input type="submit" id="submitButton" value="<dhv:label name="button.sendMessage">Send Message</dhv:label>" />
<dhv:evaluate if="<%= isPopup(request) %>">
</dhv:evaluate>
<input type="button" value="<dhv:label name="global.button.cancel">Cancel</dhv:label>" onClick="javascript:window.location.href='SalesMessages.do?command=ViewMessages&contactId=<%= ContactDetails.getId() %><%= addLinkParams(request, "popup|popupType|actionId|from|listForm") %><%= isPopup(request)?"&popup=true":"" %>';" />
<br />
</td>
</tr>
<tr id="sendingEmail"><td><dhv:label name="quotes.sendingEmail.label">Sending the email...</dhv:label></td></tr>
</table>
<input type="hidden" name="contactId" value="<%= request.getParameter("contactId") %>" />
</dhv:container>
</form>
<iframe src="empty.html" name="server_commands" id="server_commands" style="visibility:hidden" height="0"></iframe>
<script language="JavaScript" TYPE="text/javascript">
  showSpan('send');
  hideSpan('sendingEmail');
  document.sendMessage.replyTo.focus();
</script>

