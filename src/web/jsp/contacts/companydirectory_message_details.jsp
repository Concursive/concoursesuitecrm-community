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
<%@ page import="java.util.*,org.aspcfs.modules.communications.base.*,com.zeroio.iteam.base.*,org.aspcfs.modules.base.Constants,org.aspcfs.modules.documents.base.*,org.aspcfs.modules.contacts.base.*" %>
<jsp:useBean id="Campaign" class="org.aspcfs.modules.communications.base.Campaign" scope="request"/>
<jsp:useBean id="ContactDetails" class="org.aspcfs.modules.contacts.base.Contact" scope="request"/>
<jsp:useBean id="FileItem" class="com.zeroio.iteam.base.FileItem" scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<%@ include file="../initPage.jsp" %>
<dhv:evaluate if="<%= !isPopup(request) %>">
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="ExternalContacts.do"><dhv:label name="Contacts" mainMenuItem="true">Contacts</dhv:label></a> >
<a href="ExternalContacts.do?command=SearchContacts"><dhv:label name="accounts.SearchResults">Search Results</dhv:label></a> >
<a href="ExternalContacts.do?command=ContactDetails&id=<%=ContactDetails.getId()%>"><dhv:label name="accounts.accounts_contacts_add.ContactDetails">Contact Details</dhv:label></a> >
<a href="ExternalContacts.do?command=ViewMessages&contactId=<%= ContactDetails.getId() %>"><dhv:label name="accounts.Messages">Messages</dhv:label></a> >
<dhv:label name="accounts.MessageDetails">Message Details</dhv:label>
</td>
</tr>
</table>
<%-- End Trails --%>
</dhv:evaluate>
<dhv:container name="contacts" selected="messages" object="ContactDetails" param='<%= "id=" + ContactDetails.getId() %>' appendToUrl='<%= addLinkParams(request, "popup|popupType|actionId") %>'>
  <table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
    <tr>
      <th colspan="2">
        <strong><dhv:label name="accounts.accounts_contacts_messages_details.SelectedMessage">Selected message</dhv:label></strong>
      </th>
    </tr>
    <tr class="containerBody">
      <td class="formLabel">
        <dhv:label name="accounts.accounts_contacts_messages_details.Campaign">Campaign</dhv:label>
      </td>
      <td>
        <%=toHtml(Campaign.getName())%>
      </td>
    </tr>
    <tr class="containerBody">
      <td class="formLabel">
        <dhv:label name="accounts.accounts_contacts_messages_details.ReplyTo">Reply To</dhv:label>
      </td>
      <td>
        <%=toHtml(Campaign.getReplyTo())%>
      </td>
    </tr>
    <dhv:evaluate if='<%= Campaign.getBcc() != null && !"".equals(Campaign.getCc()) %>'>
      <tr class="containerBody">
        <td class="formLabel">
          <dhv:label name="quotes.cc">CC</dhv:label>
        </td>
        <td>
          <%=toHtml(Campaign.getCc())%>
        </td>
      </tr>
    </dhv:evaluate>
    <dhv:evaluate if='<%= Campaign.getBcc() != null && !"".equals(Campaign.getBcc()) %>'>
      <tr class="containerBody">
        <td class="formLabel">
          <dhv:label name="quotes.bcc">BCC</dhv:label>
        </td>
        <td>
          <%=toHtml(Campaign.getBcc())%>
        </td>
      </tr>
    </dhv:evaluate>
    <dhv:evaluate if ="<%= hasText(Campaign.getMessageName()) %>">
    <tr class="containerBody">
      <td class="formLabel">
        <dhv:label name="accounts.accounts_contacts_messages_details.MessageName">Message Name</dhv:label>
      </td>
      <td>
          <%= toHtml(Campaign.getMessageName()) %>
      </td>
    </tr>
    </dhv:evaluate>
    <tr class="containerBody">
      <td class="formLabel">
        <dhv:label name="accounts.accounts_contacts_messages_details.MessageSubject">Message Subject</dhv:label>
      </td>
      <td>
        <%= toHtml(Campaign.getSubject()) %>
      </td>
    </tr>
    <tr class="containerBody">
      <td class="formLabel" valign="top">
        <dhv:label name="accounts.accounts_contacts_messages_details.MessageText">Message Text</dhv:label>
      </td>
      <td>
        <%= (Campaign.getMessage()) %>&nbsp;
      </td>
    </tr>
    <tr class="containerBody">
      <td class="formLabel" valign="top">
        <dhv:label name="accounts.accounts_contacts_messages_details.Attachments">Attachments</dhv:label>
      </td>
        <td>
          <%
          if (Campaign.getMessageAttachments().size() > 0) { 
          boolean hasPermissionDownload = false; 
          Iterator it = Campaign.getMessageAttachments().iterator();
          while (it.hasNext()) {
            MessageAttachment thisAttachment = (MessageAttachment)it.next();
            FileItem thisFile= thisAttachment.getFileItem();%>
            <dhv:evaluate if="<%= thisFile!=null %>">
            <%hasPermissionDownload = false;%>
              <%@ include file="../communications/message_attachment_download_permissions_include.jsp"%>  
            </dhv:evaluate>       	
            <dhv:evaluate if="<%= thisAttachment.getFileExists() && hasPermissionDownload %>">
              <a href="DocumentSelector.do?command=Download&moduleId=<%=thisFile.getLinkModuleId() %>&linkItemId=<%= thisFile.getLinkItemId() %>&fid=<%= thisAttachment.getFileItemId() %>&ver=<%= thisAttachment.getVersion() %><%= addLinkParams(request, "popup|popupType|actionId|actionplan") %>">
              <%= toHtml(thisAttachment.getFileName()+" ("+thisAttachment.getRelativeSize()+User.getSystemStatus(getServletConfig()).getLabel("admin.oneThousand.abbreviation", "k")+")") %> 
              </a>  
            </dhv:evaluate>  
            <dhv:evaluate if="<%= !thisAttachment.getFileExists() || !hasPermissionDownload %>">
              <%= toHtml(thisAttachment.getFileName()+" ("+thisAttachment.getRelativeSize()+User.getSystemStatus(getServletConfig()).getLabel("admin.oneThousand.abbreviation", "k")+")") %> 
            </dhv:evaluate>
          <% if (it.hasNext()) { %>
          <br>
          <%}}} else { %>
          <dhv:label name="communications.messageAttachments.none">None</dhv:label>
        <% } %>
        </td>
    </tr>
  </table>
</dhv:container>
