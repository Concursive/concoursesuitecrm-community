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
      <strong>Previous Activity Details</strong>  
    </th>
  </tr>
  <tr class="containerBody">
    <td class="formLabel" nowrap>
      Date
    </td>
    <td>
      <zeroio:tz timestamp="<%= PreviousCallDetails.getEntered() %>" timeZone="<%= User.getTimeZone() %>" showTimeZone="true" />
    </td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel" nowrap>
      Type
    </td>
    <td>
      <%= toHtml(PreviousCallDetails.getCallType()) %>
    </td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel" nowrap>
      Subject
    </td>
    <td>
      <%= toHtml(PreviousCallDetails.getSubject()) %>
    </td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel" valign="top">
      Notes
    </td>
    <td>
      <%= toHtml(StringUtils.trimToSize(PreviousCallDetails.getNotes(), 40)) %>
    </td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel" nowrap>
      Result
    </td>
    <td>
      <%= callResultList.getLookupList(-1).getSelectedValue(PreviousCallDetails.getResultId()) %>
    </td>
  </tr>
</table><br>

<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="2">
      <strong>Pending Activity Reminder</strong>  
    </th>
  </tr>
  <tr class="containerBody">
    <td class="formLabel" nowrap>
      Type
    </td>
    <td>
      <%= toHtml(PreviousCallDetails.getAlertCallType()) %>
    </td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel" nowrap>
      Subject
    </td>
    <td>
      <%= toHtml(PreviousCallDetails.getAlertText()) %>
    </td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel" valign="top">
      Notes
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
          Complete Activity
      <% }else if(PreviousCallDetails.getId() > 0 && "cancel".equals(request.getParameter("action"))){ %>
          Cancel Activity
      <% }else{ %>
          <%= CallDetails.getId() > 0 ? "Modify  Activity" : "Add an Activity" %>
      <% } %>
      </strong>
    </th>
  </tr>
  <tr class="containerBody">
    <td class="formLabel">
      Type
    </td>
    <td>
      <%= CallTypeList.getHtmlSelect("callTypeId", (PreviousCallDetails.getId() > 0 ? -1 : CallDetails.getCallTypeId())) %><font color="red">*</font><%= showAttribute(request, "typeError") %>
      <dhv:include name="call-type" none="true">
      Length:
      <input type="text" size="5" name="length" value="<%= (PreviousCallDetails.getId() > 0 ? "" : toHtmlValue(CallDetails.getLengthString())) %>"> minutes  <%= showAttribute(request, "lengthError") %>
      </dhv:include>
    </td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel">
      Subject
    </td>
    <td>
      <input type="text" size="50" maxlength="255" name="subject" value="<%= (PreviousCallDetails.getId() > 0 ? "" : toHtmlValue(CallDetails.getSubject())) %>"><font color="red">*</font><%= showAttribute(request, "subjectError") %>
    </td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel" valign="top">
      Notes
    </td>
    <td>
      <TEXTAREA NAME="notes" ROWS="3" COLS="50"><%= (PreviousCallDetails.getId() > 0 ? "" :toString(CallDetails.getNotes())) %></TEXTAREA>
    </td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel">
      Result
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
        HtmlSelect resultSelect = thisLookupList.getHtmlSelectObj(PreviousCallDetails.getId() > 0 ? -1 : CallDetails.getResultId());
        resultSelect.addAttribute("onChange", "javascript:makeSuggestion();");
        resultSelect.addAttribute("id", "resultId");
      %>
      <%= resultSelect.getHtml("resultId") %><font color="red">*</font><%= showAttribute(request, "resultError") %>
      <input type="checkbox" name="hasFollowup" onClick="toggleSpan(this, 'nextActionSpan')" <%= CallDetails.getHasFollowup() ? " checked" : "" %>/>
      Schedule follow-up activity?
      <iframe src="empty.html" name="server_commands" id="server_commands" style="visibility:hidden" height="0"></iframe>
      <% }else{ %>
        <%= thisLookupList.getHtmlSelect("resultId", CallDetails.getResultId()) %>
      <% } %>
    </td>

  </tr>
</table>

