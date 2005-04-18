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
<jsp:useBean id="ContactDetails" class="org.aspcfs.modules.contacts.base.Contact" scope="request"/>
<script type="text/javascript">
function reopenContact(id) {
  if (id == '<%= ContactDetails.getId() %>') {
    scrollReload('ExternalContacts.do?command=SearchContacts');
    return -1;
  } else {
    return '<%= ContactDetails.getId() %>';
  }
}
</script>
<form name="newMessageForm" action="ExternalContactsCallsForward.do?command=SendCall&contactId=<%= request.getParameter("contactId") %>&id=<%= request.getParameter("id") %>" method="post" onSubmit="return sendMessage();">
<dhv:evaluate if="<%= !isPopup(request) %>">
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="ExternalContacts.do"><dhv:label name="module-general-contacts">General Contacts</dhv:label></a> > 
<a href="ExternalContacts.do?command=SearchContacts"><dhv:label name="accounts.SearchResults">Search Results</dhv:label></a> >
<a href="ExternalContacts.do?command=ContactDetails&id=<%= ContactDetails.getId() %>"><dhv:label name="accounts.accounts_contacts_add.ContactDetails">Contact Details</dhv:label></a> >
<a href="ExternalContactsCalls.do?command=View&contactId=<%=ContactDetails.getId() %>"><dhv:label name="accounts.accounts_calls_list.Activities">Activities</dhv:label></a> >
<dhv:evaluate if="<%= !"list".equals(request.getParameter("return")) %>">
  <a href="ExternalContactsCalls.do?command=View&contactId=<%= ContactDetails.getId() %>&id=<%= request.getParameter("id") %>"><dhv:label name="accounts.accounts_contacts_calls_add.ActivityDetails">Activity Details</dhv:label></a> >
</dhv:evaluate>
<dhv:label name="accounts.accounts_contacts_calls_forward.ForwardActivity">Forward Activity</dhv:label>
</td>
</tr>
</table>
</dhv:evaluate>
<%-- End Trails --%>
<dhv:container name="contacts" selected="calls" object="ContactDetails" param="<%= "id=" + ContactDetails.getId() %>" appendToUrl="<%= addLinkParams(request, "popup|popupType|actionId") %>">
  <input type="submit" value="<dhv:label name="global.button.send">Send</dhv:label>">
  <% if("list".equals(request.getParameter("return"))){ %>
    <input type="button" value="<dhv:label name="global.button.cancel">Cancel</dhv:label>" onClick="javascript:window.location.href='ExternalContactsCalls.do?command=View&contactId=<%= request.getParameter("contactId") %><%= addLinkParams(request, "popup|view|popupType") %>'">
  <% }else{ %>
    <input type="button" value="<dhv:label name="global.button.cancel">Cancel</dhv:label>" onClick="javascript:window.location.href='ExternalContactsCalls.do?command=Details&id=<%= request.getParameter("id") %>&contactId=<%= request.getParameter("contactId") %><%= addLinkParams(request, "popup|view|popupType") %>'">
  <% } %>
  <br><br>
  <%@ include file="../newmessage.jsp" %>
  <br>
  <input type="submit" value="<dhv:label name="global.button.send">Send</dhv:label>">
  <% if("list".equals(request.getParameter("return"))){ %>
    <input type="button" value="<dhv:label name="global.button.cancel">Cancel</dhv:label>" onClick="javascript:window.location.href='ExternalContactsCalls.do?command=View&contactId=<%= request.getParameter("contactId") %>'">
  <% }else{ %>
    <input type="button" value="<dhv:label name="global.button.cancel">Cancel</dhv:label>" onClick="javascript:window.location.href='ExternalContactsCalls.do?command=Details&id=<%= request.getParameter("id") %>&contactId=<%= request.getParameter("contactId") %>'">
  <% } %>
</dhv:container>
</form>