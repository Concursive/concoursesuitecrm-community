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
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="2">
      <strong><dhv:label name="accounts.accounts_contacts_calls_details_followup_include.FollowupActivityReminder">Follow-up Activity Reminder</dhv:label></strong>
    </th>
  </tr>
  <tr class="containerBody">
    <td class="formLabel" nowrap>
      <dhv:label name="accounts.accounts_add.Type">Type</dhv:label>
    </td>
    <td>
      <%= toHtmlValue(CallDetails.getAlertCallType()) %>
      <dhv:evaluate if="<%= CallDetails.hasFollowupLength() %>">,
        <dhv:label name="accounts.accounts_contacts_calls_details_include.Length">Length:</dhv:label>
        <%= toHtml(CallDetails.getFollowupLengthString()) %>
        <%= ReminderTypeList.getSelectedValue(CallDetails.getFollowupLengthDuration()) %>
      </dhv:evaluate>
    </td>
  </tr><tr class="containerBody">
    <td nowrap class="formLabel" valign="top">
      <dhv:label name="accounts.accounts_contacts_calls_details_include.StartDate">Start Date</dhv:label>
    </td>
    <td>
      <zeroio:tz timestamp="<%= CallDetails.getAlertDate() %>" default="&nbsp;" timeZone="<%= CallDetails.getAlertDateTimeZone() %>" showTimeZone="true" default="&nbsp;" />
      <% if(!User.getTimeZone().equals(CallDetails.getAlertDateTimeZone())){%>
      <br>
      <zeroio:tz timestamp="<%= CallDetails.getAlertDate() %>" default="&nbsp;" timeZone="<%= User.getTimeZone() %>" showTimeZone="true" default="&nbsp;" />
      <% } %>
    </td>
  </tr><tr class="containerBody">
    <td nowrap class="formLabel" valign="top">
      <dhv:label name="accounts.accounts_contacts_calls_details_include.EndDate">End Date</dhv:label>
    </td>
    <td>
      <zeroio:tz timestamp="<%= CallDetails.getFollowupEndDate() %>" default="&nbsp;" timeZone="<%= CallDetails.getFollowupEndDateTimeZone() %>" showTimeZone="true" default="&nbsp;" />
      <% if(!User.getTimeZone().equals(CallDetails.getFollowupEndDateTimeZone())){%>
      <br>
      <zeroio:tz timestamp="<%= CallDetails.getFollowupEndDate() %>" default="&nbsp;" timeZone="<%= User.getTimeZone() %>" showTimeZone="true" default="&nbsp;" />
      <% } %>
    </td>
  </tr><tr class="containerBody">
    <td class="formLabel" nowrap>
     <dhv:label name="accounts.accountasset_include.Contact">Contact</dhv:label>
    </td>
    <td>
      <dhv:evaluate if="<%= CallDetails.getFollowupContactName() != null && !"".equals(CallDetails.getFollowupContactName()) %>">
        <%= CallDetails.getFollowupContactName() %>
      </dhv:evaluate>
    </td>
  </tr><tr class="containerBody">
    <td class="formLabel" nowrap>
      <dhv:label name="accounts.accountasset_include.Description">Description</dhv:label>
    </td>
    <td>
      <%= toHtml(CallDetails.getAlertText()) %>
    </td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel" valign="top">
      <dhv:label name="accounts.accountasset_include.Notes">Notes</dhv:label>
    </td>
    <td>
      <%= toHtml(CallDetails.getFollowupNotes()) %>
    </td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel" nowrap>
     <dhv:label name="accounts.accounts_contacts_calls_details_followup_include.Priority">Priority</dhv:label>
    </td>
    <td>
      <%= toHtml(CallDetails.getPriorityString()) %>
    </td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel" nowrap>
     <dhv:label name="accounts.accounts_contacts_calls_details_include.Location">Location</dhv:label>
    </td>
    <td>
      <% if (CallDetails.getFollowupLocation() != null) {%>
        <%= toHtml(CallDetails.getFollowupLocation()) %>
      <% } %>
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel" valign="top">
      <dhv:label name="accounts.accountasset_include.Participants">Participants</dhv:label>
    </td>
    <td>
      <%= toHtmlValue(CallDetails.getFollowupParticipants().getValuesAsString()) %>&nbsp;
    </td>
  </tr>  
  <tr class="containerBody">
    <td class="formLabel" nowrap>
     <dhv:label  name="accounts.accountasset_include.EmailParticipants"> Email Participants</dhv:label> 
    </td>
    <td>
      <%= CallDetails.getEmailFollowupParticipants() %>
    </td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel" nowrap>
      <dhv:label name="accounts.accounts_contacts_calls_details_followup_include.Reminder">Reminder</dhv:label>
    </td>
    <td>
      <% if(CallDetails.getReminderId() > -1){ %>
        <%= CallDetails.getReminderId() %>
        <%= ReminderTypeList.getSelectedValue(CallDetails.getReminderTypeId()) %> 
        <dhv:label name="accounts.accounts_contacts_calls_details_followup_include.BeforeTheStartDate">before the start date</dhv:label>
      <% }else{ %>
        <dhv:label name="accounts.accounts_contacts_calls_details_followup_include.None">None</dhv:label>
      <% } %>
    </td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel" nowrap>
      <dhv:label name="accounts.accounts_contacts_calls_details_followup_include.AssignedTo">Assigned to...</dhv:label>
    </td>
    <td>
      <dhv:username id="<%= CallDetails.getOwner() %>"/>
    </td>
  </tr>
</table>
