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
<%@ page import="java.util.*,java.text.DateFormat,org.aspcfs.modules.contacts.base.*,org.aspcfs.modules.communications.base.Campaign" %>
<jsp:useBean id="campList" class="org.aspcfs.modules.communications.base.CampaignList" scope="request"/>
<jsp:useBean id="ContactDetails" class="org.aspcfs.modules.contacts.base.Contact" scope="request"/>
<jsp:useBean id="ContactMessageListInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
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
<a href="ExternalContacts.do"><dhv:label name="Contacts" mainMenuItem="true">Contacts</dhv:label></a> >
<a href="ExternalContacts.do?command=SearchContacts"><dhv:label name="accounts.SearchResults">Search Results</dhv:label></a> >
<a href="ExternalContacts.do?command=ContactDetails&id=<%=ContactDetails.getId()%>"><dhv:label name="accounts.accounts_contacts_add.ContactDetails">Contact Details</dhv:label></a> >
<dhv:label name="Messages">Messages</dhv:label>
</td>
</tr>
</table>
<%-- End Trails --%>
</dhv:evaluate>
<dhv:container name="contacts" selected="messages" object="ContactDetails" param="<%= "id=" + ContactDetails.getId() %>" appendToUrl="<%= addLinkParams(request, "popup|popupType|actionId") %>">
<dhv:evaluate if="<%= ContactDetails.getEnabled() && !ContactDetails.isTrashed() %>">
  <dhv:permission name="contacts-external_contacts-messages-view">
    <a href="ExternalContactsMessages.do?command=PrepareMessage&contactId=<%= ContactDetails.getId() %><%= isPopup(request)?"&popup=true":"" %>"><dhv:label name="actionList.newMessage">New Message</dhv:label></a>
  </dhv:permission>
</dhv:evaluate>
<br /><br />
  <table width="100%" border="0">
    <tr>
      <form name="listView" method="post" action="ExternalContacts.do?command=ViewMessages&contactId=<%=ContactDetails.getId()%>">
      <td align="left">
        <select size="1" name="listView" onChange="javascript:document.listView.submit();">
          <option <%= ContactMessageListInfo.getOptionValue("my") %>><dhv:label name="accounts.accounts_contacts_messages_view.MyMessages">My Messages</dhv:label></option>
          <option <%= ContactMessageListInfo.getOptionValue("all") %>><dhv:label name="accounts.accounts_contacts_messages_view.AllMessages">All Messages</dhv:label></option>
        </select>
      </td>
      <td>
        <dhv:pagedListStatus title="<%= showError(request, "actionError") %>" object="ContactMessageListInfo"/>
      </td>
      <%= addHiddenParams(request, "popup|popupType|actionId") %>
      </form>
    </tr>
  </table>
  <table cellpadding="4" cellspacing="0" border="0" width="100%" class="pagedList">
    <tr>
      <th width="45%" ><strong><dhv:label name="accounts.accounts_calls_list.Subject">Subject</dhv:label></strong></th>
      <th width="20%" nowrap>
        <a href="ExternalContacts.do?command=ViewMessages&column=msg.name&contactId=<%= ContactDetails.getId() %><%= addLinkParams(request, "popup|popupType|actionId") %>"><strong><dhv:label name="contacts.name">Name</dhv:label></strong></a>
        <%= ContactMessageListInfo.getSortIcon("msg.name") %>
      </th>
      <th width="20%" nowrap>
        <a href="ExternalContacts.do?command=ViewMessages&column=active_date&contactId=<%= ContactDetails.getId() %><%= addLinkParams(request, "popup|popupType|actionId") %>"><strong><dhv:label name="accounts.accounts_contacts_messages_view.RunDate">Run Date</dhv:label></strong></a>
        <%= ContactMessageListInfo.getSortIcon("active_date") %>
      </th>
      <th width="15%">
        <strong><dhv:label name="accounts.accountasset_include.Status">Status</dhv:label></strong>
      </th>
    </tr>
  <%
      Iterator j = campList.iterator();
      if ( j.hasNext() ) {
        int rowid = 0;
        while (j.hasNext()) {
          rowid = (rowid != 1?1:2);
          Campaign campaign = (Campaign)j.next();
  %>
    <tr class="row<%= rowid %>">
      <td><a href="ExternalContacts.do?command=MessageDetails&id=<%= campaign.getId() %>&contactId=<%=ContactDetails.getId()%><%= addLinkParams(request, "popup|popupType|actionId") %>"><%= toHtml(campaign.getSubject()) %></a></td>
      <td>
          <% if(campaign.getMessageName() != null && !"".equals(campaign.getMessageName())) {%>
            <%= toHtml(campaign.getMessageName()) %>
          <%} else {%>
            <dhv:label name="account.noNameAvailable.quotes">"No name available"</dhv:label>
          <%}%>
  <font color="red"><% if(("true".equals(request.getParameter("notify")) && ("" + campaign.getId()).equals(request.getParameter("id")))) {%>
    <dhv:label name="account.canceled.brackets">(Canceled)</dhv:label>
  <%} else {%>
  <%}%></font>
      </td>
      <td valign="top" align="left" nowrap>
        <zeroio:tz timestamp="<%= campaign.getActiveDate() %>" dateOnly="true" timeZone="<%= campaign.getActiveDateTimeZone() %>" showTimeZone="true" default="&nbsp;"/>
        <% if (campaign.getActiveDateTimeZone() != null && !User.getTimeZone().equals(campaign.getActiveDateTimeZone())) {%>
          <br /><zeroio:tz timestamp="<%= campaign.getActiveDate() %>" timeZone="<%= User.getTimeZone() %>" showTimeZone="true" default="&nbsp;"/>
        <%}%>
      </td>
      <td valign="top" nowrap>
        <%= toHtml(campaign.getStatus()) %>
      </td>
    </tr>
    <%}%>
  <%} else {%>
    <tr class="containerBody">
      <td colspan="4">
        <dhv:label name="accounts.accounts_contacts_messages_view.NoMessagesFound">No messages found.</dhv:label>
      </td>
    </tr>
  <%}%>
  </table>
  <br>
  <dhv:pagedListControl object="ContactMessageListInfo"/>
</dhv:container>
