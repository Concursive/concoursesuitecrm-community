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
  - Version: $Id: homepage_send_message_form.jsp 18876 2007-07-19 03:19:30 +0530 (Turs, 19 Jul 2007) manojk $
  - Description:
  --%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ page import="java.util.*"%>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<jsp:useBean id="ContactDetails" class="org.aspcfs.modules.contacts.base.Contact" scope="request"/>
<jsp:useBean id="bcc" class="java.lang.String" scope="request"/>
<jsp:useBean id="cc" class="java.lang.String" scope="request"/>
<jsp:useBean id="Message" class="org.aspcfs.modules.communications.base.Message" scope="request"/>
<jsp:useBean id="messageList" class="org.aspcfs.modules.communications.base.MessageList" scope="request"/>
<jsp:useBean id="clientType" class="org.aspcfs.utils.web.ClientType" scope="session"/>
<jsp:useBean id="applicationPrefs" class="org.aspcfs.controller.ApplicationPrefs" scope="application"/>
<%@ include file="../initPage.jsp" %>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/checkString.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/checkEmail.js"></script>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/spanDisplay.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popURL.js"></SCRIPT>
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
    if (document.getElementById("contactLink").value == "-1") {
      alert(label("check.ticket.contact.entered", "- Check that a Contact is selected\r\n"));
      return false;
    }

    document.forms['sendMessage'].elements['messageId'].selectedIndex = 0;
    messageType = "<%=request.getAttribute("messageType")%>";
    if (messageType == "addressRequest"){
      document.forms['sendMessage'].action = 'ExternalContactsMessages.do?command=PrepareQuickMessage&actionSource=ExternalContactsMessages&messageType=addressRequest&orgId=<%=request.getAttribute("orgId")%><%= addLinkParams(request, "popup|popupType|actionId|actionListId")%>';
    }else{
      document.forms['sendMessage'].action = 'ExternalContactsMessages.do?command=PrepareQuickMessage&actionSource=ExternalContactsMessages<%= addLinkParams(request, "popup|popupType|actionId|actionListId")%>';
    }
    document.forms['sendMessage'].submit();
  }
  function updateMessage() {
    if (document.getElementById("contactLink").value == "-1") {
      alert(label("check.ticket.contact.entered", "- Check that a Contact is selected\r\n"));
      return false;
    }

    messageType = "<%=request.getAttribute("messageType")%>";
    if (messageType == "addressRequest"){
      document.forms['sendMessage'].action = 'ExternalContactsMessages.do?command=PrepareQuickMessage&actionSource=ExternalContactsMessages&messageType=addressRequest&orgId=<%=request.getAttribute("orgId")%><%= addLinkParams(request, "popup|popupType|actionId|actionListId") %>';
    }else{
      document.forms['sendMessage'].action = 'ExternalContactsMessages.do?command=PrepareQuickMessage&actionSource=ExternalContactsMessages<%= addLinkParams(request, "popup|popupType|actionId|actionListId") %>';
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

    if ((form.contactId.value == "-1") || form.contactId.value == "") {
      messageText += label("check.ticket.contact.entered", "- Check that a Contact is selected\r\n");
      formTest = false;
    }

  <dhv:include name="contact.textMessageAddresses" none="true">
      if (!checkEmail(form.email1address.value)) {
        messageText += label("check.email", "- At least one entered email address is invalid.  Make sure there are no invalid characters\r\n");
        formTest = false;
      }

    if (document.getElementById("emptyEmail").value == "true" && form.email1address.value == "") {
        messageText += label("check.email", "- At least one entered email address is invalid.  Make sure there are no invalid characters\r\n");
        formTest = false;
      }
    </dhv:include>
    if (formTest == false) {
      alert(label("check.send.email", "The message could not be sent, please check the following:\r\n\r\n") + messageText);
      return false;
    }
    hideSpan('send');
    showSpan('sendingEmail');
    return selectAllOptions(form.selectedList);
  }

  function popContactsListSingle(hiddenFieldId, displayFieldId, params) {
  title  = 'Contacts';
  width  =  '700';
  height =  '425';
  resize =  'yes';
  bars   =  'yes';
  var posx = (screen.width - width)/2;
  var posy = (screen.height - height)/2;
  var windowParams = 'WIDTH=' + width + ',HEIGHT=' + height + ',RESIZABLE=' + resize + ',SCROLLBARS=' + bars + ',STATUS=0,LEFT=' + posx + ',TOP=' + posy + 'screenX=' + posx + ',screenY=' + posy;
    if (params != null && params != "") {
      params = '&' + params;
    }
  var newwin=window.open('ContactsList.do?command=ContactList&listType=single&flushtemplist=true&commandName=executeCommandPrepareQuickMessage&selectedIds='+document.getElementById(hiddenFieldId).value+'&displayFieldId='+displayFieldId+'&hiddenFieldId='+hiddenFieldId + params, title, windowParams);
  newwin.focus();
    if (newwin != null) {
      if (newwin.opener == null)
        newwin.opener = self;
    }
  }

  function showWireFrame() {
    <% if( ContactDetails.getId()!= -1 && "".equals(ContactDetails.getPrimaryEmailAddress()) ) { %>
      changeDivDisplayStyle("contactemail","");
   <% } else { %>
      changeDivDisplayStyle("contactemail","none");
  <%  } %>
  }

</script>
<body onLoad="javascript:showWireFrame();">
<form name="sendMessage" action="ExternalContactsMessages.do?command=SendMessage&auto-populate=true" method="post" onSubmit="return checkForm(this);">
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
<tr>
  <td class="formLabel">
    <dhv:label name="accounts.accountasset_include.Contact">Contact</dhv:label>
  </td>
  <td>
    <table border="0" cellspacing="0" cellpadding="4" class="empty">
      <tr>
        <td valign="top" nowrap>
          <div id="changecontact">
            <% if (ContactDetails.getId() != -1) { %>
            <%= toHtml(ContactDetails.getNameLastFirst()) %>
            <% } else { %>
            <dhv:label name="accounts.accounts_add.NoneSelected">None
              Selected</dhv:label>
            <% } %>
          </div>
        </td>
        <td valign="top" width="100%" nowrap>
          <font color="red">
            *</font><%= showAttribute(request, "contactIdError") %>
          [<a
            href="javascript:popContactsListSingle('contactLink','changecontact','reset=true<%= User.getUserRecord().getSiteId() == -1?"&includeAllSites=true&siteId=-1":"&mySiteOnly=true&siteId="+ User.getUserRecord().getSiteId() %>&filters=mycontacts|accountcontacts');"><dhv:label name="admin.selectContact">Select Contact</dhv:label></a>]
           <input type="hidden" name="contactId" id="contactLink"
                 value="<%= ContactDetails.getId() %>">
          <input type="hidden" name="emptyEmail" id="emptyEmail">
          [<a
            href="javascript:popURL('ExternalContacts.do?command=Prepare&popup=true&source=sendMailHomePage', 'New_Contact','600','550','yes','yes');"><dhv:label name="admin.createContact">Create new contact</dhv:label></a>]
        </td>
      </tr>
    </table>
  </td>
</tr>
</table>

  <div id="contactemail" style="display:none;">
      <table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
      <tr>
        <td class="formLabel">&nbsp;</td>
        <td>
            <font color="red"><dhv:label name="object.validation.actionError.contactNoEmail">This contact does not have a valid email address associated</dhv:label></font>
            <table cellpadding="4" cellspacing="0" border="0" width="100%" class="empty">
              <tr>
                <td>
                  <select size='1' name='email1type' ><option selected value='1' >Business</option><option value='2' >Personal</option><option value='3' >Other</option></select>
                  <input type="text" size="40" name="email1address" maxlength="255">
                  <input type="radio" name="primaryEmail" value="1">Primary
                </td>
              </tr>
            </table>
        </td>
      </tr>
    </table>
  </div>

<br>
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
<input type="button" value="<dhv:label name="global.button.cancel">Cancel</dhv:label>" onClick="javascript:window.close();"> 
<br/>
</td>
</tr>
<tr id="sendingEmail"><td><dhv:label name="quotes.sendingEmail.label">Sending the email...</dhv:label></td></tr>
</table>
  <input type="hidden" name="commandName" value="executeCommandPrepareQuickMessage" />  
</form>
</body>
<iframe src="empty.html" name="server_commands" id="server_commands" style="visibility:hidden" height="0"></iframe>
<script language="JavaScript" TYPE="text/javascript">
  showSpan('send');
  hideSpan('sendingEmail');
  document.sendMessage.replyTo.focus();
</script>

