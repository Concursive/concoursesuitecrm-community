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
      <strong>Follow-up Activity Reminder</strong>
    </th>
  </tr>
  <tr class="containerBody">
    <td class="formLabel" nowrap>
      Description
    </td>
    <td>
      <%= toHtml(CallDetails.getAlertText()) %>
    </td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel" valign="top">
      Notes
    </td>
    <td>
      <%= toHtml(CallDetails.getFollowupNotes()) %>
    </td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel" nowrap>
      Type
    </td>
    <td>
      <%= toHtmlValue(CallDetails.getAlertCallType()) %>&nbsp;
    </td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel" nowrap>
      Priority
    </td>
    <td>
      <%= toHtml(CallDetails.getPriorityString()) %>
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel" valign="top">
      Due Date
    </td>
    <td>
      <zeroio:tz timestamp="<%= CallDetails.getAlertDate() %>" default="&nbsp;" timeZone="<%= CallDetails.getAlertDateTimeZone() %>" showTimeZone="yes" default="&nbsp;" />
      <% if(!User.getTimeZone().equals(CallDetails.getAlertDateTimeZone())){%>
      <br>
      <zeroio:tz timestamp="<%= CallDetails.getAlertDate() %>" default="&nbsp;" timeZone="<%= User.getTimeZone() %>" showTimeZone="yes"  default="&nbsp;" />
      <% } %>
    </td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel" nowrap>
      Reminder
    </td>
    <td>
      <% if(CallDetails.getReminderId() > -1){ %>
        <%= CallDetails.getReminderId() %><%= ReminderTypeList.getSelectedValue(CallDetails.getReminderTypeId()) %>&nbsp; before due date  
      <% }else{ %>
        None
      <% } %>
    </td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel" nowrap>
      Assigned to...
    </td>
    <td>
      <dhv:username id="<%= CallDetails.getOwner() %>"/>
    </td>
  </tr>
</table>
