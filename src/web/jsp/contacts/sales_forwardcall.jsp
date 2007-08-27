<%-- 
  - Copyright(c) 2007 Dark Horse Ventures LLC (http://www.centriccrm.com/) All
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
  - Version: $Id: companydirectory_forwardcall.jsp 18488 2007-01-15 20:12:32Z matt $
  - Description:
  --%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<jsp:useBean id="ContactDetails" class="org.aspcfs.modules.contacts.base.Contact" scope="request"/>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/spanDisplay.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/submit.js"></SCRIPT>
<script type="text/javascript">
function reopenContact(id) {
  if (id == '<%= ContactDetails.getId() %>') {
    scrollReload('ExternalContacts.do?command=SearchContacts');
    return -1;
  } else {
    return '<%= ContactDetails.getId() %>';
  }
}
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
<form name="newMessageForm" action="SalesCalls.do?command=SendCall&contactId=<%= request.getParameter("contactId") %>&id=<%= request.getParameter("id") %>" method="post" onSubmit="return sendMessage();">
<dhv:evaluate if="<%= !isPopup(request) %>">
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="Sales.do"><dhv:label name="accounts.Conta1cts">Leads</dhv:label></a> > 
<a href="Sales.do?command=ContactDetails&id=<%= ContactDetails.getId() %>"><dhv:label name="accounts.accounts_contacts_add.Contac1tDetails">Leads Details</dhv:label></a> >
<a href="SalesCalls.do?command=View&contactId=<%=ContactDetails.getId() %>"><dhv:label name="accounts.accounts_calls_list.Activities">Activities</dhv:label></a> >
<dhv:evaluate if='<%= !"list".equals(request.getParameter("return")) %>'>
  <a href="SalesCalls.do?command=View&contactId=<%= ContactDetails.getId() %>&id=<%= request.getParameter("id") %>"><dhv:label name="accounts.accounts_contacts_calls_add.ActivityDetails">Activity Details</dhv:label></a> >
</dhv:evaluate>
<dhv:label name="accounts.accounts_contacts_calls_forward.ForwardActivity">Forward Activity</dhv:label>
</td>
</tr>
</table>
</dhv:evaluate>
<%-- End Trails --%>
<dhv:container name="leads" selected="activities" object="ContactDetails" param='<%= "id=" + ContactDetails.getId() %>' appendToUrl='<%= addLinkParams(request, "popup|popupType|actionId") %>'>
  <input type="submit" id="send1" value="<dhv:label name="global.button.send">Send</dhv:label>" />
  <% if("list".equals(request.getParameter("return"))){ %>
    <input type="button" value="<dhv:label name="global.button.cancel">Cancel</dhv:label>" onClick="javascript:window.location.href='SalesCalls.do?command=View&contactId=<%= request.getParameter("contactId") %><%= addLinkParams(request, "popup|view|popupType") %>'">
  <% }else{ %>
    <input type="button" value="<dhv:label name="global.button.cancel">Cancel</dhv:label>" onClick="javascript:window.location.href='SalesCalls.do?command=Details&id=<%= request.getParameter("id") %>&contactId=<%= request.getParameter("contactId") %><%= addLinkParams(request, "popup|view|popupType") %>'">
  <% } %>
  <br><br>
  <%@ include file="../newmessage_include.jsp" %>
  <br>
  <input type="submit" id="send2" value="<dhv:label name="global.button.send">Send</dhv:label>" />
  <% if("list".equals(request.getParameter("return"))){ %>
    <input type="button" value="<dhv:label name="global.button.cancel">Cancel</dhv:label>" onClick="javascript:window.location.href='SalesCalls.do?command=View&contactId=<%= request.getParameter("contactId") %><%= addLinkParams(request, "popup|view|popupType") %>'">
  <% }else{ %>
    <input type="button" value="<dhv:label name="global.button.cancel">Cancel</dhv:label>" onClick="javascript:window.location.href='SalesCalls.do?command=Details&id=<%= request.getParameter("id") %>&contactId=<%= request.getParameter("contactId") %><%= addLinkParams(request, "popup|view|popupType") %>'">
  <% } %>
</dhv:container>
</form>
