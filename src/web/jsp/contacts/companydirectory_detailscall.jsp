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
<%@ page import="java.util.*,java.text.DateFormat,org.aspcfs.modules.contacts.base.*, org.aspcfs.modules.base.Constants" %>
<jsp:useBean id="ContactDetails" class="org.aspcfs.modules.contacts.base.Contact" scope="request"/>
<jsp:useBean id="OrgDetails" class="org.aspcfs.modules.accounts.base.Organization" scope="request"/>
<jsp:useBean id="CallDetails" class="org.aspcfs.modules.contacts.base.Call" scope="request"/>
<jsp:useBean id="ReminderTypeList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="CallResult" class="org.aspcfs.modules.contacts.base.CallResult" scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<%@ include file="../initPage.jsp" %>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/confirmDelete.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popURL.js"></SCRIPT>
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
<form name="addCall" action="ExternalContactsCalls.do?command=Modify&id=<%= CallDetails.getId() %>&contactId=<%= ContactDetails.getId() %>" method="post">
<dhv:evaluate if="<%= !isPopup(request) %>">
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="ExternalContacts.do"><dhv:label name="accounts.Contacts">Contacts</dhv:label></a> > 
<a href="ExternalContacts.do?command=SearchContacts"><dhv:label name="accounts.SearchResults">Search Results</dhv:label></a> >
<a href="ExternalContacts.do?command=ContactDetails&id=<%=ContactDetails.getId()%>"><dhv:label name="accounts.accounts_contacts_add.ContactDetails">Contact Details</dhv:label></a> >
<a href="ExternalContactsCalls.do?command=View&contactId=<%=ContactDetails.getId()%>"><dhv:label name="accounts.accounts_calls_list.Activities">Activities</dhv:label></a> >
<dhv:label name="accounts.accounts_contacts_calls_add.ActivityDetails">Activity Details</dhv:label>
</td>
</tr>
</table>
<%-- End Trails --%>
</dhv:evaluate>
<dhv:container name="contacts" selected="calls" object="ContactDetails" param='<%= "id=" + ContactDetails.getId() %>' appendToUrl='<%= addLinkParams(request, "popup|popupType|actionId") %>'>
  <dhv:evaluate if="<%= ContactDetails.getEnabled() && !ContactDetails.isTrashed() && !CallDetails.isTrashed() %>">
    <dhv:evaluate if='<%= "pending".equals(request.getParameter("view")) %>'>
      <dhv:sharing primaryBean="CallDetails" secondaryBeans="ContactDetails" action="edit"><input type="button" value="<dhv:label name="global.button.complete">Complete</dhv:label>" onClick="javascript:window.location.href='ExternalContactsCalls.do?command=Complete&contactId=<%= ContactDetails.getId() %>&id=<%= CallDetails.getId() %><%= addLinkParams(request, "view|popup|popupType|actionId") %>'"></dhv:sharing>
    </dhv:evaluate>
    <% if("pending".equals(request.getParameter("view"))){ %>
    <dhv:sharing primaryBean="CallDetails" secondaryBeans="ContactDetails" action="edit"><input type="submit" value="<dhv:label name="global.button.modify">Modify</dhv:label>"></dhv:sharing>
    <%}else if(CallDetails.getStatusId() != Call.CANCELED){%>
     <dhv:sharing primaryBean="CallDetails" secondaryBeans="ContactDetails" action="edit"><input type="submit" value="<dhv:label name="global.button.modify">Modify</dhv:label>"></dhv:sharing>
    <%}%>
    <dhv:evaluate if='<%= "pending".equals(request.getParameter("view")) %>'>
      <dhv:sharing primaryBean="CallDetails" secondaryBeans="ContactDetails" action="delete"><input type="button" value="<dhv:label name="global.button.CancelPendingActivity">Cancel Pending Activity</dhv:label>" onClick="javascript:window.location.href='ExternalContactsCalls.do?command=Cancel&contactId=<%= CallDetails.getContactId() %>&id=<%= CallDetails.getId() %>&action=cancel<%= addLinkParams(request, "popup|popupType|actionId|view") %>';"></dhv:sharing>
    </dhv:evaluate>
    <dhv:evaluate if="<%= !isPopup(request) %>">
      <dhv:permission name="contacts-external_contacts-calls-view"><input type="button" name="action" value="<dhv:label name="accounts.accounts_calls_list_menu.Forward"></dhv:label>" onClick="javascript:window.location.href='ExternalContactsCallsForward.do?command=ForwardCall&contactId=<%= ContactDetails.getId() %>&id=<%= CallDetails.getId() %>&forwardType=<%= Constants.TASKS %><%= addLinkParams(request, "popup|popupType|actionId|view") %>'"></dhv:permission>
    </dhv:evaluate>
  </dhv:evaluate>
  <dhv:permission name="contacts-external_contacts-calls-edit,contacts-external_contacts-calls-delete,contacts-external_contacts-calls-view"><br>&nbsp;</dhv:permission>
  <% if("pending".equals(request.getParameter("view"))){ %>
    <dhv:evaluate if="<%= CallDetails.getAlertDate() != null %>">
    <%-- include follow up activity details --%>
    <%@ include file="../accounts/accounts_contacts_calls_details_followup_include.jsp" %>
    </dhv:evaluate>
    &nbsp;
    <%-- include completed activity details --%>
    <%@ include file="../accounts/accounts_contacts_calls_details_include.jsp" %>
  <% }else{ %>
    <%-- include completed activity details --%>
    <%@ include file="../accounts/accounts_contacts_calls_details_include.jsp" %>
    &nbsp;
    <dhv:evaluate if="<%= CallDetails.getAlertDate() != null %>">
    <%-- include follow up activity details --%>
    <%@ include file="../accounts/accounts_contacts_calls_details_followup_include.jsp" %>
    </dhv:evaluate>
  <% } %>
  &nbsp;
  <table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
    <tr>
      <th colspan="2">
        <strong><dhv:label name="accounts.accounts_contacts_calls_details.RecordInformation">Record Information</dhv:label></strong>
      </th>
    </tr>
    <tr class="containerBody">
      <td class="formLabel" nowrap>
        <dhv:label name="accounts.accounts_calls_list.Entered">Entered</dhv:label>
      </td>
      <td>
        <dhv:username id="<%= CallDetails.getEnteredBy() %>"/>
        <zeroio:tz timestamp="<%= CallDetails.getEntered()  %>" timeZone="<%= User.getTimeZone() %>" showTimeZone="true" />
      </td>
    </tr>
    <tr class="containerBody">
      <td class="formLabel" nowrap>
        <dhv:label name="accounts.accounts_contacts_calls_details.Modified">Modified</dhv:label>
      </td>
      <td>
        <dhv:username id="<%= CallDetails.getModifiedBy() %>"/>
        <zeroio:tz timestamp="<%= CallDetails.getModified()  %>" timeZone="<%= User.getTimeZone() %>" showTimeZone="true" />
      </td>
    </tr>
  </table>
  <br>
  <dhv:evaluate if="<%= ContactDetails.getEnabled() && !ContactDetails.isTrashed() && !CallDetails.isTrashed() %>">
    <dhv:evaluate if='<%= "pending".equals(request.getParameter("view")) %>'>
      <dhv:sharing primaryBean="CallDetails" secondaryBeans="ContactDetails" action="edit"><input type="button" value="<dhv:label name="global.button.complete">Complete</dhv:label>" onClick="javascript:window.location.href='ExternalContactsCalls.do?command=Complete&contactId=<%= ContactDetails.getId() %>&id=<%= CallDetails.getId() %><%= addLinkParams(request, "view|popup|popupType|actionId") %>'"></dhv:sharing>
    </dhv:evaluate>
   <% if("pending".equals(request.getParameter("view"))){ %>
    <dhv:sharing primaryBean="CallDetails" secondaryBeans="ContactDetails" action="edit"><input type="submit" value="<dhv:label name="global.button.modify">Modify</dhv:label>"></dhv:sharing>
    <%}else if(CallDetails.getStatusId() != Call.CANCELED){%>
     <dhv:sharing primaryBean="CallDetails" secondaryBeans="ContactDetails" action="edit"><input type="submit" value="<dhv:label name="global.button.modify">Modify</dhv:label>"></dhv:sharing>
    <%}%>
    <dhv:evaluate if='<%= "pending".equals(request.getParameter("view")) %>'>
      <dhv:sharing primaryBean="CallDetails" secondaryBeans="ContactDetails" action="delete"><input type="button" value="<dhv:label name="global.button.CancelPendingActivity">Cancel Pending Activity</dhv:label>" onClick="javascript:window.location.href='ExternalContactsCalls.do?command=Cancel&contactId=<%= CallDetails.getContactId() %>&id=<%= CallDetails.getId() %>&action=cancel<%= addLinkParams(request, "popup|popupType|actionId|view") %>';"></dhv:sharing></dhv:evaluate>
      <dhv:evaluate if="<%= !isPopup(request) %>">
      <dhv:permission name="contacts-external_contacts-calls-view"><input type="button" name="action" value="<dhv:label name="accounts.accounts_calls_list_menu.Forward">Forward</dhv:label>" onClick="javascript:window.location.href='ExternalContactsCallsForward.do?command=ForwardCall&contactId=<%= ContactDetails.getId() %>&forwardType=<%= Constants.TASKS %>&id=<%= CallDetails.getId() %><%= addLinkParams(request, "popup|popupType|actionId|view") %>'"></dhv:permission>
    </dhv:evaluate>
  </dhv:evaluate>
  <%= addHiddenParams(request, "popup|popupType|actionId|view") %>
</dhv:container>
</form>
