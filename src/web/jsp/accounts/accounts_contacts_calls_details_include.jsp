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
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="2">
      <strong><dhv:label name="accounts.accounts_contacts_calls_add.ActivityDetails">Activity Details</dhv:label></strong>  
    </th>
  </tr>
  <tr class="containerBody">
    <td class="formLabel" nowrap>
      <dhv:label name="accounts.accounts_add.Type">Type</dhv:label>
    </td>
    <td>
      <%= toHtml(CallDetails.getCallType()) %>
      <dhv:evaluate if="<%= CallDetails.hasLength() %>">,
        <dhv:label name="accounts.accounts_contacts_calls_details_include.Length">Length:</dhv:label>
        <%= toHtml(CallDetails.getLengthString()) %>
        <%= ReminderTypeList.getSelectedValue(CallDetails.getCallLengthDuration()) %>
      </dhv:evaluate>
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel" valign="top">
      <dhv:label name="accounts.accounts_contacts_calls_details_include.StartDate">Start Date</dhv:label>
    </td>
    <td>
      <zeroio:tz timestamp="<%= CallDetails.getCallStartDate() %>" default="&nbsp;" timeZone="<%= CallDetails.getCallStartDateTimeZone() %>" showTimeZone="true" default="&nbsp;" />
      <% if(!User.getTimeZone().equals(CallDetails.getCallStartDateTimeZone())){%>
      <br>
      <zeroio:tz timestamp="<%= CallDetails.getCallStartDate() %>" default="&nbsp;" timeZone="<%= User.getTimeZone() %>" showTimeZone="true" default="&nbsp;" />
      <% } %>
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel" valign="top">
      <dhv:label name="accounts.accounts_contacts_calls_details_include.EndDate">End Date</dhv:label>
    </td>
    <td>
      <zeroio:tz timestamp="<%= CallDetails.getCallEndDate() %>" default="&nbsp;" timeZone="<%= CallDetails.getCallEndDateTimeZone() %>" showTimeZone="true" default="&nbsp;" />
      <% if(!User.getTimeZone().equals(CallDetails.getCallEndDateTimeZone())){%>
      <br>
      <zeroio:tz timestamp="<%= CallDetails.getCallEndDate() %>" default="&nbsp;" timeZone="<%= User.getTimeZone() %>" showTimeZone="true" default="&nbsp;" />
      <% } %>
    </td>
  </tr>

  <tr class="containerBody">
    <td class="formLabel" nowrap>
      <dhv:label name="accounts.accountasset_include.Contact">Contact</dhv:label>
    </td>
    <td>
      <dhv:evaluate if="<%= CallDetails.getContactName() != null && !"".equals(CallDetails.getContactName()) %>">
        <%= toHtml(CallDetails.getContactName()) %>
      </dhv:evaluate>
    </td>
  </tr>

  <tr class="containerBody">
    <td class="formLabel" nowrap>
    <dhv:label name="accounts.accounts_contacts_calls_details_include.Subject">Subject</dhv:label>
    </td>
    <td>
      <%= toHtml(CallDetails.getSubject()) %>
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel" valign="top">
      <dhv:label name="accounts.accountasset_include.Notes">Notes</dhv:label>
    </td>
    <td>
      <%= toHtml(CallDetails.getNotes()) %>
    </td>
  </tr>
	<tr class="containerBody">
    <td nowrap class="formLabel" valign="top">
      <dhv:label name="accounts.accounts_contacts_calls_details_include.Location">Location</dhv:label>
    </td>
    <td>
      <% if (CallDetails.getCallLocation() != null) {%>
        <%= toHtml(CallDetails.getCallLocation()) %>
      <% } %>
    </td>
  </tr>

  <tr class="containerBody">
    <td nowrap class="formLabel" valign="top">
      <dhv:label name="accounts.accountasset_include.Participants">Participants</dhv:label>
    </td>
    <td>
      <%= toHtmlValue(CallDetails.getParticipants().getValuesAsString()) %>&nbsp;
    </td>
  </tr>  
  <tr class="containerBody">
    <td class="formLabel" nowrap>
     <dhv:label  name="accounts.accountasset_include.EmailParticipants"> Email Participants</dhv:label> 	
    </td>
    <td>
      <%= CallDetails.getEmailParticipants() %>
    </td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel" nowrap>
      <dhv:label name="accounts.accounts_calls_list.Result">Result</dhv:label>
    </td>
    <td>
      <%= toHtml(CallResult.getDescription()) %>
    </td>
  </tr>
</table>
