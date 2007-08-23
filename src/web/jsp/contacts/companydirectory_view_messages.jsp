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
<%@ page
    import="org.aspcfs.modules.communications.base.Campaign,org.aspcfs.modules.communications.base.ContactMessage,java.util.Iterator" %>
<jsp:useBean id="campList"
             class="org.aspcfs.modules.communications.base.CampaignList"
             scope="request"/>
<jsp:useBean id="receivedList"
             class="org.aspcfs.modules.communications.base.ContactMessageList"
             scope="request"/>
<jsp:useBean id="ContactDetails"
             class="org.aspcfs.modules.contacts.base.Contact" scope="request"/>
<jsp:useBean id="contactSentMessageListInfo"
             class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<jsp:useBean id="contactReceivedMessageListInfo"
             class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean"
             scope="session"/>
<jsp:useBean id="selected" class="java.lang.String" scope="request"/>
<%@ include file="../initPage.jsp" %>
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
<dhv:evaluate if="<%= !isPopup(request) %>">
  <%-- Trails --%>
  <table class="trails" cellspacing="0">
    <tr>
      <td>
        <a href="ExternalContacts.do"><dhv:label name="Contacts"
                                                 mainMenuItem="true">
          Contacts</dhv:label></a> >
        <a href="ExternalContacts.do?command=SearchContacts"><dhv:label
            name="accounts.SearchResults">Search Results</dhv:label></a> >
        <a href="ExternalContacts.do?command=ContactDetails&id=<%=ContactDetails.getId()%>">
          <dhv:label name="accounts.accounts_contacts_add.ContactDetails">
            Contact Details</dhv:label></a> >
        <dhv:label name="Messages">Messages</dhv:label>
      </td>
    </tr>
  </table>
  <%-- End Trails --%>
</dhv:evaluate>
<dhv:container name="contacts" selected="messages" object="ContactDetails"
               param='<%= "id=" + ContactDetails.getId() %>'
               appendToUrl='<%= addLinkParams(request, "popup|popupType|actionId") %>'>
<dhv:evaluate
    if="<%= ContactDetails.getEnabled() && !ContactDetails.isTrashed() %>">
  <dhv:permission name="contacts-external_contacts-messages-view">
    <a href="ExternalContactsMessages.do?command=PrepareMessage&contactId=<%= ContactDetails.getId() %><%= isPopup(request)?"&popup=true":"" %>">
      <dhv:label name="actionList.newMessage">New Message</dhv:label></a>
  </dhv:permission>
</dhv:evaluate>
<br/><br/>

<form name="listView" method="post"
      action="ExternalContacts.do?command=ViewMessages&contactId=<%=ContactDetails.getId()%>">
  <select size="1" name="listView"
          onChange="javascript:document.listView.submit();">
    <option value="my" <%= "my".equals(selected) ? "selected" : "" %>><dhv:label
        name="accounts.accounts_contacts_messages_view.MyMessages">My
      Messages</dhv:label></option>
    <option value="all" <%= "all".equals(selected) ? "selected" : "" %>>
      <dhv:label name="accounts.accounts_contacts_messages_view.AllMessages">All
        Messages</dhv:label></option>
  </select>
  <%= addHiddenParams(request, "popup|popupType|actionId") %>
</form>
<% if ((request.getParameter("pagedListSectionId") == null && !contactReceivedMessageListInfo.getExpandedSelection()) || contactSentMessageListInfo.getExpandedSelection()) { %>
<%-- Sent Messages --%>
<dhv:pagedListStatus showExpandLink="true"
                     title='<%= User.getSystemStatus(getServletConfig()).getLabel("", "Sent Messages") %>'
                     object="contactSentMessageListInfo"/>
<table cellpadding="4" cellspacing="0" border="0" width="100%"
       class="pagedList">
  <tr>
    <th width="2%">
      <img src="images\icons\stock_insert_bookmark-16.gif">
    </th>
    <th width="43%"><strong><dhv:label
        name="accounts.accounts_calls_list.Subject">Subject</dhv:label></strong>
    </th>
    <th width="20%" nowrap>
      <a href="ExternalContacts.do?command=ViewMessages&pagedListSectionId=contactSentMessageListInfo&column=msg.name&contactId=<%= ContactDetails.getId() %><%= addLinkParams(request, "popup|popupType|actionId") %>">
        <strong><dhv:label name="contacts.name">Name</dhv:label></strong></a>
      <%= contactSentMessageListInfo.getSortIcon("msg.name") %>
    </th>
    <th width="20%" nowrap>
      <a href="ExternalContacts.do?command=ViewMessages&pagedListSectionId=contactSentMessageListInfo&column=active_date&contactId=<%= ContactDetails.getId() %><%= addLinkParams(request, "popup|popupType|actionId") %>">
        <strong><dhv:label
            name="accounts.accounts_contacts_messages_view.RunDate">Run
          Date</dhv:label></strong></a>
      <%= contactSentMessageListInfo.getSortIcon("active_date") %>
    </th>
    <th width="15%">
      <strong><dhv:label name="accounts.accountasset_include.Status">
        Status</dhv:label></strong>
    </th>
  </tr>
  <%
    Iterator j = campList.iterator();
    if (j.hasNext()) {
      int rowid = 0;
      while (j.hasNext()) {
        rowid = (rowid != 1 ? 1 : 2);
        Campaign campaign = (Campaign) j.next();
  %>
  <tr class="row<%= rowid %>">
    <td> 
      <% if (campaign.getMessageAttachments().size() > 0) {%>
        <img src="images\icons\stock_insert_bookmark-16.gif">
      <% } else { %>
        &nbsp;
      <% } %>
    </td>
    <td><a
        href="ExternalContacts.do?command=MessageDetails&id=<%= campaign.getId() %>&contactId=<%=ContactDetails.getId()%><%= addLinkParams(request, "popup|popupType|actionId") %>"><%= toHtml(campaign.getSubject()) %></a>
    </td>
    <td>
      <% if (campaign.getMessageName() != null && !"".equals(campaign.getMessageName())) {%>
      <%= toHtml(campaign.getMessageName()) %>
      <%} else {%>
      <dhv:label name="account.noNameAvailable.quotes">"No name
        available"</dhv:label>
      <%}%>
      <font
          color="red"><% if (("true".equals(request.getParameter("notify")) && ("" + campaign.getId()).equals(request.getParameter("id")))) {%>
        <dhv:label name="account.canceled.brackets">(Canceled)</dhv:label>
        <%} else {%>
        <%}%></font>
    </td>
    <td valign="top" align="left" nowrap>
      <zeroio:tz timestamp="<%= campaign.getActiveDate() %>" dateOnly="true"
                 timeZone="<%= campaign.getActiveDateTimeZone() %>"
                 showTimeZone="true" default="&nbsp;"/>
      <% if (campaign.getActiveDateTimeZone() != null && !User.getTimeZone().equals(campaign.getActiveDateTimeZone())) {%>
      <br/><zeroio:tz timestamp="<%= campaign.getActiveDate() %>"
                      timeZone="<%= User.getTimeZone() %>" showTimeZone="true"
                      default="&nbsp;"/>
      <%}%>
    </td>
    <td valign="top" nowrap>
      <%= toHtml(campaign.getStatus()) %>
    </td>
  </tr>
  <dhv:evaluate
      if='<%= contactSentMessageListInfo.getExpandedSelection() && !"".equals(toString(campaign.getMessage())) %>'>
    <tr class="row<%= rowid %>">
      <td colspan="5" valign="top">
        <%= (campaign.getMessage()) %>
      </td>
    </tr>
  </dhv:evaluate>
  <%}%>
  <%} else {%>
  <tr class="containerBody">
    <td colspan="5">
      <dhv:label
          name="accounts.accounts_contacts_messages_view.NoMessagesFound">No
        messages found.</dhv:label>
    </td>
  </tr>
  <%}%>
</table>
&nbsp;<br/>
<%}%>
<% if ((request.getParameter("pagedListSectionId") == null && !contactSentMessageListInfo.getExpandedSelection()) || contactReceivedMessageListInfo.getExpandedSelection()) { %>
<%-- Received Messages--%>
<dhv:pagedListStatus showExpandLink="true"
                     title='<%= User.getSystemStatus(getServletConfig()).getLabel("", "Received Messages") %>'
                     object="contactReceivedMessageListInfo"/>
<table cellpadding="4" cellspacing="0" border="0" width="100%"
       class="pagedList">
  <tr>
    <th width="2%">
      <img src="images\icons\stock_insert_bookmark-16.gif">
    </th>
    <th width="43%"><strong><dhv:label
        name="accounts.accounts_calls_list.Subject">Subject</dhv:label></strong>
    </th>
    <th width="20%" nowrap>
      <a href="ExternalContacts.do?command=ViewMessages&pagedListSectionId=contactReceivedMessageListInfo&column=name&contactId=<%= ContactDetails.getId() %><%= addLinkParams(request, "popup|popupType|actionId") %>">
        <strong><dhv:label name="contacts.name">Name</dhv:label></strong></a>
      <%= contactReceivedMessageListInfo.getSortIcon("name") %>
    </th>
    <th width="20%" nowrap>
      <a href="ExternalContacts.do?command=ViewMessages&pagedListSectionId=contactReceivedMessageListInfo&column=received_date&contactId=<%= ContactDetails.getId() %><%= addLinkParams(request, "popup|popupType|actionId") %>">
        <strong><dhv:label name="campaigns.receivedOn">Received On</dhv:label></strong></a>
      <%= contactReceivedMessageListInfo.getSortIcon("received_date") %>
    </th>
    <th width="15%"><strong><dhv:label name="campaigns.receivedBy">Received By</dhv:label></strong>
    </th>
  </tr>
  <%
    Iterator k = receivedList.iterator();
    if (k.hasNext()) {
      int rowid = 0;
      while (k.hasNext()) {
        rowid = (rowid != 1 ? 1 : 2);
        ContactMessage contactMessage = (ContactMessage) k.next();
  %>
  <tr class="row<%= rowid %>">
    <td>
      <%-- TODO: Import messages from Outlook with attachments and provide support to display such attachments --%>
      &nbsp;
    </td>
    <td>
      <a href="ExternalContacts.do?command=ContactMessageDetails&id=<%= contactMessage.getId() %>&contactId=<%=ContactDetails.getId()%><%= addLinkParams(request, "popup|popupType|actionId") %>"><%= toHtml(contactMessage.getContactMessage().getMessageSubject()) %></a>
    </td>
    <td>
      <% if (contactMessage.getContactMessage().getName() != null && !"".equals(contactMessage.getContactMessage().getName())) {%>
      <%= toHtml(contactMessage.getContactMessage().getName()) %>
      <% } else { %>
      <dhv:label name="account.noNameAvailable.quotes">"No name
        available"</dhv:label>
      <% } %>
    </td>
    <td valign="top" align="left" nowrap>
      <zeroio:tz timestamp="<%= contactMessage.getReceivedDate() %>"
                 timeZone="<%= User.getTimeZone() %>" showTimeZone="true"
                 dateOnly="true" default="&nbsp;"/>
    </td>
    <td valign="top" align="center" nowrap>
      <dhv:username id="<%= contactMessage.getReceivedBy() %>"/>
    </td>
  </tr>
  <dhv:evaluate
      if='<%= contactReceivedMessageListInfo.getExpandedSelection()  && !"".equals(toString(contactMessage.getContactMessage().getMessageText()))%>'>
    <tr class="row<%= rowid %>">
      <td colspan="7" valign="top">
        <%= (contactMessage.getContactMessage().getMessageText()) %>
      </td>
    </tr>
  </dhv:evaluate>
  <%}%>
  <%} else {%>
  <tr class="containerBody">
    <td colspan="5">
      <dhv:label
          name="accounts.accounts_contacts_messages_view.NoMessagesFound">No
        messages found.</dhv:label>
    </td>
  </tr>
  <%}%>
</table>
<%}%>
</dhv:container>
