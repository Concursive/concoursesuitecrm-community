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
<%@ page import="org.aspcfs.utils.web.HtmlSelect,org.aspcfs.utils.StringUtils,org.aspcfs.utils.web.LookupList" %>
<dhv:evaluate if="<%= PreviousCallDetails.getId() > 0 %>">
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="2">
      <strong><dhv:label name="contact.call.previousActivityDetails">Previous Activity Details</dhv:label></strong>  
    </th>
  </tr>
  <tr class="containerBody">
    <td class="formLabel" nowrap>
      <dhv:label name="quotes.date">Date</dhv:label>
    </td>
    <td>
      <zeroio:tz timestamp="<%= PreviousCallDetails.getEntered() %>"/>
    </td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel" nowrap>
      <dhv:label name="accounts.accounts_add.Type">Type</dhv:label>
    </td>
    <td>
      <%= toHtml(PreviousCallDetails.getCallType()) %>
    </td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel" nowrap>
      <dhv:label name="accounts.accounts_contacts_calls_details_include.Subject">Subject</dhv:label>
    </td>
    <td>
      <%= toHtml(PreviousCallDetails.getSubject()) %>
    </td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel" valign="top">
      <dhv:label name="accounts.accountasset_include.Notes">Notes</dhv:label>
    </td>
    <td>
      <%= toHtml(StringUtils.trimToSize(PreviousCallDetails.getNotes(), 40)) %>
    </td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel" nowrap>
      <dhv:label name="accounts.accounts_calls_list.Result">Result</dhv:label>
    </td>
    <td>
      <%= callResultList.getLookupList(-1).getSelectedValue(PreviousCallDetails.getResultId()) %>
    </td>
  </tr>
</table><br>

<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="2">
      <strong><dhv:label name="contact.pendingActivityReminder">Pending Activity Reminder</dhv:label></strong>
    </th>
  </tr>
  <tr class="containerBody">
    <td class="formLabel" nowrap>
      <dhv:label name="accounts.accounts_add.Type">Type</dhv:label>
    </td>
    <td>
      <%= toHtml(PreviousCallDetails.getAlertCallType()) %>
    </td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel" nowrap>
      <dhv:label name="accounts.accounts_contacts_calls_details_include.Subject">Subject</dhv:label>
    </td>
    <td>
      <%= toHtml(PreviousCallDetails.getAlertText()) %>
    </td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel" valign="top">
      <dhv:label name="accounts.accountasset_include.Notes">Notes</dhv:label>
    </td>
    <td>
      <%= toHtml(StringUtils.trimToSize(PreviousCallDetails.getFollowupNotes(), 40)) %>
    </td>
  </tr>
</table><br>
</dhv:evaluate>
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="2">
      <strong>
      <% if(PreviousCallDetails.getId() > 0 && !"cancel".equals(request.getParameter("action"))){ %>
          <dhv:label name="accounts.accounts_calls_list_menu.CompleteActivity">Complete Activity</dhv:label>
      <% }else if(PreviousCallDetails.getId() > 0 && "cancel".equals(request.getParameter("action"))){ %>
          <dhv:label name="accounts.accounts_calls_list_menu.CancelActivity">Cancel Activity</dhv:label>
      <% }else{ %>
        <% if(CallDetails.getId() > 0) {%>
          <dhv:label name="contact.call.modifyActivity">Modify Activity</dhv:label>
        <%} else {%>
          <dhv:label name="accounts.accounts_contacts_calls_list.LogAnActivity">Log an Activity</dhv:label>
        <%}%>
      <% } %>
      </strong>
    </th>
  </tr>
  <tr class="containerBody">
    <td class="formLabel">
      <dhv:label name="accounts.accounts_add.Type">Type</dhv:label>
    </td>
    <td>
      <%= CallTypeList.getHtmlSelect("callTypeId",CallDetails.getCallTypeId()) %><font color="red">*</font><%= showAttribute(request, "typeError") %>
      <dhv:include name="call-type" none="true">
      <dhv:label name="contact.length.colon">Length:</dhv:label>
      <input type="text" size="5" name="length" value="<%= toHtmlValue(CallDetails.getLengthString()) %>"> <dhv:label name="admin.minutes">minutes</dhv:label>  <%= showAttribute(request, "lengthError") %>
      </dhv:include>
    </td>
  </tr>
  <% if (opportunityHeader.getContactLink() == -1) { %>
        <tr class="containerBody">
          <td nowrap class="formLabel">
            <dhv:label name="accounts.accountasset_include.Contact">Contact</dhv:label>
          </td>
          <td valign="center">
	<% if (opportunityHeader.getAccountLink() == -1 || ContactList.size() == 0) {
  %>
            <%= ContactList.getEmptyHtmlSelect(systemStatus, "contactId") %>
	<%} else {%>
            <%= ContactList.getHtmlSelect("contactId", CallDetails.getContactId() ) %>
	<%}%>
            <font color="red">*</font>
          </td>
        </tr>
  <%} else {%>
          <input type="hidden" name="contactId" value="<%= opportunityHeader.getContactLink() %>">
  <%}%>
  <tr class="containerBody">
    <td class="formLabel">
      <dhv:label name="accounts.accounts_contacts_calls_details_include.Subject">Subject</dhv:label>
    </td>
    <td>
      <input type="text" size="50" maxlength="255" name="subject" value="<%= toHtmlValue(CallDetails.getSubject()) %>"><font color="red">*</font><%= showAttribute(request, "subjectError") %>
    </td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel" valign="top">
      <dhv:label name="accounts.accountasset_include.Notes">Notes</dhv:label>
    </td>
    <td>
      <TEXTAREA NAME="notes" ROWS="3" COLS="50"><%= toString(CallDetails.getNotes()) %></TEXTAREA>
    </td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel">
      <dhv:label name="accounts.accounts_calls_list.Result">Result</dhv:label>
    </td>
    <td>
      <%
        LookupList thisLookupList = null;
        if(CallDetails.getStatusId() == Call.CANCELED || "cancel".equals(request.getParameter("action"))){
          thisLookupList = callResultList.getCanceledLookupList(CallDetails.getResultId());
        }else{
          thisLookupList = callResultList.getCompletedLookupList(CallDetails.getResultId());
        }
       if((CallDetails.getStatusId() != Call.CANCELED && !"cancel".equals(request.getParameter("action"))) && (CallDetails.getId() == -1 || (CallDetails.getId() > 0 && ((CallDetails.getAlertDate() == null) || (request.getAttribute("alertDateWarning") != null))  ))){ 
        HtmlSelect resultSelect = thisLookupList.getHtmlSelectObj(CallDetails.getResultId());
        resultSelect.addAttribute("onChange", "javascript:makeSuggestion();");
        resultSelect.addAttribute("id", "resultId");
      %>
      <%= resultSelect.getHtml("resultId") %><font color="red">*</font><%= showAttribute(request, "resultError") %>
      <input type="checkbox" name="hasFollowup" value="on" onClick="toggleSpan(this, 'nextActionSpan')" <%= CallDetails.getHasFollowup() ? " checked" : "" %> />
      <dhv:label name="contacts.scheduleFollowUpActivity">Schedule follow-up activity?</dhv:label>
      <iframe src="empty.html" name="server_commands" id="server_commands" style="visibility:hidden" height="0"></iframe>
      <% }else{ %>
        <%= thisLookupList.getHtmlSelect("resultId", CallDetails.getResultId()) %>
      <% } %>
    </td>

  </tr>
</table>

