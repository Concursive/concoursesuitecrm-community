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
<dhv:evaluate exp="<%= !isPopup(request) %>">
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="ExternalContacts.do">Contacts</a> > 
<a href="ExternalContacts.do?command=SearchContacts">Search Results</a> >
<a href="ExternalContacts.do?command=ContactDetails&id=<%= ContactDetails.getId() %>">Contact Details</a> >
<a href="ExternalContactsCalls.do?command=View&contactId=<%=ContactDetails.getId() %>">Activities</a> >
<dhv:evaluate if="<%= !"list".equals(request.getParameter("return")) %>">
  <a href="ExternalContactsCalls.do?command=View&contactId=<%= ContactDetails.getId() %>&id=<%= request.getParameter("id") %>">Activity Details</a> >
</dhv:evaluate>
Forward Activity
</td>
</tr>
</table>
</dhv:evaluate>
<%-- End Trails --%>
<%@ include file="contact_details_header_include.jsp" %>
<% String param1 = "id=" + ContactDetails.getId(); 
   String param2 = addLinkParams(request, "popup|popupType|actionId"); %>
<dhv:container name="contacts" selected="calls" param="<%= param1 %>" appendToUrl="<%= param2 %>" style="tabs"/>
<table cellpadding="4" cellspacing="0" border="0" width="100%">
  <tr>
    <td class="containerBack">
      <form name="newMessageForm" action="ExternalContactsCallsForward.do?command=SendCall&contactId=<%= request.getParameter("contactId") %>&id=<%= request.getParameter("id") %>" method="post" onSubmit="return sendMessage();">
      <input type="submit" value="Send">
      <% if("list".equals(request.getParameter("return"))){ %>
        <input type="button" value="Cancel" onClick="javascript:window.location.href='ExternalContactsCalls.do?command=View&contactId=<%= request.getParameter("contactId") %><%= (request.getParameter("popup") != null?"&popup=true":"") %>'">
      <% }else{ %>
        <input type="button" value="Cancel" onClick="javascript:window.location.href='ExternalContactsCalls.do?command=Details&id=<%= request.getParameter("id") %>&contactId=<%= request.getParameter("contactId") %><%= (request.getParameter("popup") != null?"&popup=true":"") %>'">
      <% } %>
      <br><br>
      <%@ include file="../newmessage.jsp" %>
      <br>
      <input type="submit" value="Send">
      <% if("list".equals(request.getParameter("return"))){ %>
        <input type="button" value="Cancel" onClick="javascript:window.location.href='ExternalContactsCalls.do?command=View&contactId=<%= request.getParameter("contactId") %><%= (request.getParameter("popup") != null?"&popup=true":"") %>'">
      <% }else{ %>
        <input type="button" value="Cancel" onClick="javascript:window.location.href='ExternalContactsCalls.do?command=Details&id=<%= request.getParameter("id") %>&contactId=<%= request.getParameter("contactId") %><%= (request.getParameter("popup") != null?"&popup=true":"") %>'">
      <% } %>
      </form>
    </td>
  </tr>
</table>
