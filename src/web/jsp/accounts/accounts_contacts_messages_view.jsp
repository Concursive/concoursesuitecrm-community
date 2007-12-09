<%-- 
  - Copyright(c) 2004 Concursive Corporation (http://www.concursive.com/) All
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
  - Version: $Id$
  - Description:
  --%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ page import="java.util.*,java.text.DateFormat,org.aspcfs.modules.accounts.base.*,org.aspcfs.modules.contacts.base.*, org.aspcfs.modules.communications.base.Campaign" %>
<jsp:useBean id="OrgDetails" class="org.aspcfs.modules.accounts.base.Organization" scope="request"/>
<jsp:useBean id="CampaignList" class="org.aspcfs.modules.communications.base.CampaignList" scope="request"/>
<jsp:useBean id="ContactDetails" class="org.aspcfs.modules.contacts.base.Contact" scope="request"/>
<jsp:useBean id="AccountContactMessageListInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<%@ include file="../initPage.jsp" %>
<dhv:evaluate if="<%= !isPopup(request) %>">
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="Accounts.do"><dhv:label name="accounts.accounts">Accounts</dhv:label></a> > 
<% if (request.getParameter("return") == null) { %>
<a href="Accounts.do?command=Search"><dhv:label name="accounts.SearchResults">Search Results</dhv:label></a> >
<%} else if (request.getParameter("return").equals("dashboard")) {%>
<a href="Accounts.do?command=Dashboard"><dhv:label name="communications.campaign.Dashboard">Dashboard</dhv:label></a> >
<%}%>
<a href="Accounts.do?command=Details&orgId=<%=OrgDetails.getOrgId()%>"><dhv:label name="accounts.details">Account Details</dhv:label></a> >
<a href="Contacts.do?command=View&orgId=<%=OrgDetails.getOrgId()%>"><dhv:label name="accounts.Contacts">Contacts</dhv:label></a> >
<a href="Contacts.do?command=Details&id=<%=ContactDetails.getId()%>"><dhv:label name="accounts.accounts_contacts_add.ContactDetails">Contact Details</dhv:label></a> >
<dhv:label name="accounts.Messages">Messages</dhv:label>
</td>
</tr>
</table>
<%-- End Trails --%>
</dhv:evaluate>
<dhv:container name="accounts" selected="contacts" object="OrgDetails" param='<%= "orgId=" + OrgDetails.getOrgId() %>' appendToUrl='<%= addLinkParams(request, "popup|popupType|actionId") %>'>
<dhv:container name="accountscontacts" selected="messages" object="ContactDetails" param='<%= "id=" + ContactDetails.getId() %>' appendToUrl='<%= addLinkParams(request, "popup|popupType|actionId") %>'>
  <dhv:evaluate if="<%= ContactDetails.getEnabled() && !ContactDetails.isTrashed() %>">
    <dhv:permission name="accounts-accounts-contacts-messages-view">
      <a href="AccountContactsMessages.do?command=PrepareMessage&contactId=<%= ContactDetails.getId() %><%= addLinkParams(request, "popup|popupType") %>"><dhv:label name="actionList.newMessage">New Message</dhv:label></a>
    </dhv:permission>
  </dhv:evaluate>
<br /><br />
    <table width="100%" border="0">
      <tr>
        <td align="left">
        <form name="listView" method="post" action="Contacts.do?command=ViewMessages&contactId=<%=ContactDetails.getId()%><%= addLinkParams(request, "popup|popupType") %>">
          <select size="1" name="listView" onChange="javascript:document.listView.submit();">
            <option <%= AccountContactMessageListInfo.getOptionValue("my") %>><dhv:label name="accounts.accounts_contacts_messages_view.MyMessages">My Messages</dhv:label></option>
            <option <%= AccountContactMessageListInfo.getOptionValue("all") %>><dhv:label name="accounts.accounts_contacts_messages_view.AllMessages">All Messages</dhv:label></option>
          </select>
          </form>
        </td>
        <td>
          <dhv:pagedListStatus title='<%= showError(request, "actionError") %>' object="AccountContactMessageListInfo"/>
        </td>
      </tr>
    </table>
    <table cellpadding="4" cellspacing="0" border="0" width="100%" class="pagedList">
      <tr>
        <th width="2%"><img src="images\icons\stock_insert_bookmark-16.gif"></th>
        <th width="43%" ><strong><dhv:label name="accounts.accounts_calls_list.Subject">Subject</dhv:label></strong></th>
        <th width="20%" nowrap>
          <a href="Contacts.do?command=ViewMessages&column=msg.name&contactId=<%= ContactDetails.getId() %><%= addLinkParams(request, "popup|popupType") %>"><strong><dhv:label name="contacts.name">Name</dhv:label></strong></a>
          <%= AccountContactMessageListInfo.getSortIcon("msg.name") %>
        </th>
        <th width="20%" nowrap>
          <a href="Contacts.do?command=ViewMessages&column=active_date&contactId=<%= ContactDetails.getId() %><%= addLinkParams(request, "popup|popupType") %>"><strong><dhv:label name="accounts.accounts_contacts_messages_view.RunDate">Run Date</dhv:label></strong></a>
          <%= AccountContactMessageListInfo.getSortIcon("active_date") %>
        </th>
        <th width="15%">
          <strong><dhv:label name="accounts.accountasset_include.Status">Status</dhv:label></strong>
        </th>
      </tr>
  <%
    Iterator j = CampaignList.iterator();
    if ( j.hasNext() ) {
      int rowid = 0;
      while (j.hasNext()) {
        rowid = (rowid != 1?1:2);
        Campaign campaign = (Campaign)j.next();
  %>
      <tr class="row<%= rowid %>">
        <td> 
          <% if (campaign.getMessageAttachments().size() > 0) {%>
            <img src="images\icons\stock_insert_bookmark-16.gif">
          <% } else { %>
            &nbsp;
          <% } %>
        </td>
        <td>
          <a href="Contacts.do?command=MessageDetails&id=<%= campaign.getId() %>&contactId=<%=ContactDetails.getId()%><%= addLinkParams(request, "popup|popupType") %>"><%= toHtml(campaign.getSubject()) %></a>
        </td>
        <td>
          <% if(campaign.getMessageName() != null && !"".equals(campaign.getMessageName())) {%>
            <%= toHtml(campaign.getMessageName()) %>
          <%} else {%>
            <dhv:label name="account.noNameAvailable.quotes">"No name available"</dhv:label>
          <%}%>
          <font color="red">
<% if(("true".equals(request.getParameter("notify")) && ("" + campaign.getId()).equals(request.getParameter("id")))) {%>
  <dhv:label name="account.canceled.brackets">(Canceled)</dhv:label>
<%} else {%>
<%}%></font>
        </td>
        <td valign="top" align="left" nowrap>
          <zeroio:tz timestamp="<%= campaign.getActiveDate() %>" timeZone="<%= campaign.getActiveDateTimeZone() %>" dateOnly="true" showTimeZone="true" default="&nbsp;"/>
          <% if(!User.getTimeZone().equals(campaign.getActiveDateTimeZone())){%>
            <br /><zeroio:tz timestamp="<%= campaign.getActiveDate() %>" timeZone="<%= User.getTimeZone() %>" showTimeZone="true" default="&nbsp;"/>
          <%}%>
        </td>
        <td valign="top" nowrap>
          <%=toHtml(campaign.getStatus())%>
        </td>
      </tr>
	<%}%>
 <%} else {%>
      <tr class="containerBody">
        <td colspan="5">
          <dhv:label name="accounts.accounts_contacts_messages_view.NoMessagesFound">No messages found.</dhv:label>
        </td>
      </tr>
<%}%>
    </table>
    <br>
    <dhv:pagedListControl object="AccountContactMessageListInfo"/>
    <input type="hidden" name="id" value="<%=ContactDetails.getId()%>">
  </dhv:container>
</dhv:container>