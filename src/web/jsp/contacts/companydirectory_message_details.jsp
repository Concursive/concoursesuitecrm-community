<%-- 
  - Copyright Notice: (C) 2000-2004 Dark Horse Ventures, All Rights Reserved.
  - License: This source code cannot be modified, distributed or used without
  -          written permission from Dark Horse Ventures. This notice must
  -          remain in place.
  - Version: $Id$
  - Description: 
  --%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,org.aspcfs.modules.contacts.base.*,org.aspcfs.modules.communications.base.Campaign" %>
<jsp:useBean id="Campaign" class="org.aspcfs.modules.communications.base.Campaign" scope="request"/>
<jsp:useBean id="ContactDetails" class="org.aspcfs.modules.contacts.base.Contact" scope="request"/>
<%@ include file="../initPage.jsp" %>
<dhv:evaluate exp="<%= !isPopup(request) %>">
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="ExternalContacts.do">Contacts</a> > 
<a href="ExternalContacts.do?command=SearchContacts">Search Results</a> >
<a href="ExternalContacts.do?command=ContactDetails&id=<%=ContactDetails.getId()%>">Contact Details</a> >
<a href="ExternalContacts.do?command=ViewMessages&contactId=<%= ContactDetails.getId() %>">Messages</a> >
Message Details
</td>
</tr>
</table>
<%-- End Trails --%>
</dhv:evaluate>
<%@ include file="contact_details_header_include.jsp" %>
<% String param1 = "id=" + ContactDetails.getId(); 
    String param2 = addLinkParams(request, "popup|popupType|actionId"); %>
<dhv:container name="contacts" selected="messages" param="<%= param1 %>" appendToUrl="<%= param2 %>" style="tabs"/>
<table cellpadding="4" cellspacing="0" border="0" width="100%">
  <tr>
    <td class="containerBack">
      <br>
      <table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
        <tr>
          <th colspan="2">
            <strong>Selected message</strong>
          </th>
        </tr>
        <tr class="containerBody">
          <td class="formLabel">
            Campaign
          </td>
          <td>
            <%=toHtml(Campaign.getName())%>
          </td>
        </tr>
        <tr class="containerBody">
          <td class="formLabel">
            Reply To
          </td>
          <td>
            <%=toHtml(Campaign.getReplyTo())%>
          </td>
        </tr>
        <tr class="containerBody">
          <td class="formLabel">
            Message Name
          </td>
          <td>
            <%= toHtml(Campaign.getMessageName() != null && !"".equals(Campaign.getMessageName()) ? Campaign.getMessageName() : "\"No name available\"") %>
          </td>
        </tr>
        <tr class="containerBody">
          <td class="formLabel">
            Message Subject
          </td>
          <td>
            <%= toHtml(Campaign.getSubject()) %>
          </td>
        </tr>
        <tr class="containerBody">
          <td class="formLabel" valign="top">
            Message Text
          </td>
          <td>
            <%= (Campaign.getMessage()) %>&nbsp; 
          </td>
        </tr>
      </table>
      <br>
    </td>
  </tr>
</table>
