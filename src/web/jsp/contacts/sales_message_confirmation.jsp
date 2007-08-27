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
  - Version: $Id: companydirectory_message_confirmation.jsp 18488 2007-01-15 20:12:32Z matt $
  - Description: 
  --%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<jsp:useBean id="ContactDetails" class="org.aspcfs.modules.contacts.base.Contact" scope="request"/>
<jsp:useBean id="Message" class="org.aspcfs.modules.communications.base.Message" scope="request"/>
<jsp:useBean id="bcc" class="java.lang.String" scope="request"/>
<jsp:useBean id="cc" class="java.lang.String" scope="request"/>
<jsp:useBean id="from" class="java.lang.String" scope="request" />
<jsp:useBean id="listForm" class="java.lang.String" scope="request" />
<%@ include file="../initPage.jsp" %>
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
<a href="SalesMessages.do?command=ViewMessages&contactId=<%= ContactDetails.getId() %><%= addLinkParams(request, "popup|popupType|actionId|from|listForm") %>"><dhv:label name="accounts.Messages">Messages</dhv:label></a> >
<dhv:label name="contacts.messageConfirmation">Message Confirmation</dhv:label>
</td>
</tr>
</table>
<%-- End Trails --%>
</dhv:evaluate>
<dhv:container name="leads" selected="messages" object="ContactDetails"
               param='<%= "id=" + ContactDetails.getId() %>'
               appendToUrl='<%= addLinkParams(request, "popup|popupType|actionId") %>'>
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th>
      <dhv:label name="actionList.messageSentToContacts.text">Your message has been queued and will be sent to the following contacts:</dhv:label>
    </th>
  </tr>
  <tr class="row2">
    <td>
      <%= ContactDetails.getNameFull() %> (<%= ContactDetails.getPrimaryEmailAddress() %>)
    </td>
  </tr>
  <dhv:evaluate if='<%= cc != null && !"".equals(cc) %>'>
  <tr class="row2">
    <td>
      <dhv:label name="quotes.cc">CC</dhv:label>: <%= toHtml(cc) %>
    </td>
  </tr>
  </dhv:evaluate>
  <dhv:evaluate if='<%= bcc != null && !"".equals(bcc) %>'>
  <tr class="row2">
    <td>
      <dhv:label name="quotes.bcc">BCC</dhv:label>: <%= toHtml(bcc) %>
    </td>
  </tr>
  </dhv:evaluate>
</table>
</dhv:container>
