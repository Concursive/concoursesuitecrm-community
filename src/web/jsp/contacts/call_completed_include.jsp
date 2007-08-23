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
<%@ page import="org.aspcfs.utils.web.HtmlSelect,org.aspcfs.utils.StringUtils,java.util.Iterator,org.aspcfs.utils.web.LookupList,org.aspcfs.modules.contacts.base.Contact" %>
<jsp:directive.page import="org.aspcfs.modules.contacts.base.CallParticipant"/>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popContacts.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popURL.js"></SCRIPT>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/submit.js"></script>

<dhv:evaluate if="<%= PreviousCallDetails.getId() > 0 %>">
<dhv:evaluate if="<%= PreviousCallDetails.getSubject()!=null && !"".equals(PreviousCallDetails.getSubject())%>">
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="2">
      <strong><dhv:label name="contact.call.previousActivityDetails">Previous Activity Details</dhv:label></strong>
    </th>
  </tr>
  <tr class="containerBody">
    <td class="formLabel" nowrap>
      <dhv:label name="accounts.accounts_contacts_calls_details_include.StartDate">Start Date</dhv:label>
    </td>
    <td>
      <zeroio:tz timestamp="<%= PreviousCallDetails.getCallStartDate() %>" timeZone="<%= User.getTimeZone() %>" showTimeZone="true" />
    </td>
  </tr>
    <tr class="containerBody">
    <td class="formLabel" nowrap>
      <dhv:label name="accounts.accounts_contacts_calls_details_include.End Date">End Date</dhv:label>
    </td>
    <td>
      <zeroio:tz timestamp="<%= PreviousCallDetails.getCallEndDate() %>" timeZone="<%= User.getTimeZone() %>" showTimeZone="true" />
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
</dhv:evaluate>
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
       <%= toHtmlValue(PreviousCallDetails.getAlertCallType()) %>
      <dhv:evaluate if="<%= PreviousCallDetails.hasFollowupLength() %>">,
        <dhv:label name="accounts.accounts_contacts_calls_details_include.Length">Length:</dhv:label>
        <%= toHtml(PreviousCallDetails.getFollowupLengthString()) %>
        <%= ReminderTypeList.getSelectedValue(PreviousCallDetails.getFollowupLengthDuration()) %>
     	</dhv:evaluate>
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
    <td nowrap class="formLabel" valign="top">
      <dhv:label name="accounts.accounts_contacts_calls_details_include.StartDate">Start Date</dhv:label>
    </td>
    <td>
      <zeroio:tz timestamp="<%= PreviousCallDetails.getAlertDate() %>" default="&nbsp;" timeZone="<%= PreviousCallDetails.getAlertDateTimeZone() %>" showTimeZone="true" default="&nbsp;" />
      <% if(!User.getTimeZone().equals(PreviousCallDetails.getAlertDateTimeZone())){%>
      <br>
      <zeroio:tz timestamp="<%= PreviousCallDetails.getAlertDate() %>" default="&nbsp;" timeZone="<%= User.getTimeZone() %>" showTimeZone="true" default="&nbsp;" />
      <% } %>
    </td>
  </tr><tr class="containerBody">
    <td nowrap class="formLabel" valign="top">
      <dhv:label name="accounts.accounts_contacts_calls_details_include.EndDate">End Date</dhv:label>
    </td>
    <td>
      <zeroio:tz timestamp="<%= PreviousCallDetails.getFollowupEndDate() %>" default="&nbsp;" timeZone="<%= PreviousCallDetails.getFollowupEndDateTimeZone() %>" showTimeZone="true" default="&nbsp;" />
      <% if(!User.getTimeZone().equals(PreviousCallDetails.getFollowupEndDateTimeZone())){%>
      <br>
      <zeroio:tz timestamp="<%= PreviousCallDetails.getFollowupEndDate() %>" default="&nbsp;" timeZone="<%= User.getTimeZone() %>" showTimeZone="true" default="&nbsp;" />
      <% } %>
    </td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel" nowrap>
     <dhv:label name="accounts.accounts_contacts_calls_details_include.Location">Location</dhv:label>
    </td>
    <td>
      <%= toHtml(PreviousCallDetails.getFollowupLocation()) %>
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel" valign="top">
      <dhv:label name="accounts.accountasset_include.Participants">Participants</dhv:label>
    </td>
    <td>
      <%= toHtmlValue(PreviousCallDetails.getFollowupParticipants().getValuesAsString()) %>&nbsp;
    </td>
  </tr>  
  <tr class="containerBody">
    <td class="formLabel" nowrap>
     <dhv:label  name="accounts.accountasset_include.EmailParticipants"> Email Participants</dhv:label> 
    </td>
    <td>
      <%= PreviousCallDetails.getEmailFollowupParticipantsString() %>
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
          <dhv:label name="contact.call.modifyActivity">Modify  Activity</dhv:label>
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
      <input type="text" size="5" name="callLength" value="<%= toHtmlValue(CallDetails.getLengthString()) %>">
      <%= ReminderTypeList.getHtmlSelect("callLengthDuration", CallDetails.getCallLengthDuration()) %>
      <%= showAttribute(request, "lengthError") %>
      </dhv:include>
    </td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel">
      <dhv:label name="accounts.accounts_contacts_calls_details_include.StartDate">Start Date</dhv:label>
    </td>
    <td>
      <zeroio:dateSelect form="addCall" field="callStartDate" timestamp="<%= CallDetails.getCallStartDate() %>" timeZone="<%= CallDetails.getCallStartDateTimeZone() %>"  />
      <dhv:label name="project.at">at</dhv:label>
      <zeroio:timeSelect baseName="callStartDate" value="<%= CallDetails.getCallStartDate() %>" timeZone="<%= CallDetails.getCallStartDateTimeZone() %>" showTimeZone="true" />
      <font color="red">*</font>
    </td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel">
      <dhv:label name="accounts.accounts_contacts_calls_details_include.EndDate">End Date</dhv:label>
    </td>
    <td>
      <zeroio:dateSelect form="addCall" field="callEndDate" timestamp="<%= CallDetails.getCallEndDate() %>" timeZone="<%= CallDetails.getCallEndDateTimeZone() %>"/>
      <dhv:label name="project.at">at</dhv:label>
      <zeroio:timeSelect baseName="callEndDate" value="<%= CallDetails.getCallEndDate() %>" timeZone="<%= CallDetails.getCallEndDateTimeZone() %>" showTimeZone="true" />
      <font color="red">*</font><%= showAttribute(request, "callEndDateError") %>
    </td>
  </tr>
<% if(contactList!=null && !contactList.isEmpty()){%>    
  <tr class="containerBody">
  <td class="formLabel">
      <dhv:label name="accounts.accounts_add.Contact">Contact</dhv:label>
   </td>
   <td>
   
   <select name="contactId">
     <option value="-1"><dhv:label name="calendar.none.4dashes">-- None --</dhv:label></option>
     <% Iterator j = contactList.iterator();
      if ( j.hasNext() ) {
          while (j.hasNext()) {
             Contact thisContact = (Contact) j.next();%>
         <%if (CallDetails!=null && thisContact.getId()==ContactDetails.getId()){%>
         <option value="<%= thisContact.getId()%>" selected="selected"><%= thisContact.getValidName()%></option>
         <%}else{%>
         <option value="<%= thisContact.getId()%>"><%= thisContact.getValidName()%></option>
         <%}%>
       <%}
      }
      %>       
   </select>
   </td>
  </tr>
 <%} else {  %>
   <tr class="containerBody">
    <td class="formLabel">
      <dhv:label name="accounts.accountasset_include.Contact">Contact</dhv:label>
    </td>
    <td>
      <table border="0" cellspacing="0" cellpadding="4" class="empty">
        <tr>
          <td valign="top" nowrap>
            <div id="changecontact">
              <% if(ContactDetails.getId() != -1) {%>
                <%= toHtml(ContactDetails.getValidName()) %>
              <%} else {%>
                <dhv:label name="accounts.accounts_add.NoneSelected">None Selected</dhv:label>
              <%}%>
            </div>
          </td>
          <td valign="top" width="100%" nowrap>
            <%= showAttribute(request, "contactIdError") %>
            [<a href="javascript:popContactsListSingle('contactLink','changecontact','listView=mycontacts<%= User.getUserRecord().getSiteId() == -1?"&includeAllSites=true&siteId=-1":"&mySiteOnly=true&siteId="+User.getUserRecord().getSiteId() %><%=(OrgDetails != null && OrgDetails.getId() != -1)?"&orgId=" + OrgDetails.getId(): "" %>&nonUsersOnly=true&reset=true&filters=all|accountcontacts|mycontacts');"><dhv:label name="admin.selectContact">Select Contact</dhv:label></a>]
            <input type="hidden" name="contactId" id="contactLink" value="<%= ContactDetails.getId() %>">
            [<a href="javascript:popURL('ExternalContacts.do?command=Prepare&source=addactivity&actionSource=GlobalItem&displayType=name&popup=true', 'New_Contact','600','550','yes','yes');"><dhv:label name="admin.createContact">Create new contact</dhv:label></a>]
            [<a href="javascript:document.addCall.contactId.value='-1';javascript:changeDivContent('changecontact',label('label.none','None'));"><dhv:label name="admin.clearContact">Clear Contact</dhv:label></a>]
          </td>
        </tr>
      </table>
    </td>
  </tr>
     <!--  <input type="hidden" name="contactId" id="contactLink" value="<%= ContactDetails.getId() %>"> -->
  <%} %>
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
      <dhv:label name="accounts.accounts_contacts_calls_details_include.Location">Location</dhv:label>
    </td>
    <td>
      <input type="text" size="50" maxlength="255" name="callLocation" value="<%= toHtmlValue(CallDetails.getCallLocation()) %>">
    </td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel" valign="top">
      <dhv:label name="accounts.accountasset_include.Participants">Participants</dhv:label>
    </td>
    <td>
      <table border="0" cellpadding="0" cellspacing="0" class="empty">
        <tr>
          <td>
           <%= CallDetails.getParticipants().getHtmlSelectMultiple("participant", 5) %>

          </td>
          <td valign="top">
            <table>
              <tr>
                <td valign="top">
                    [<a href="javascript:popContactsListMultiple('participantId','1', '&displayType=name&reset=true<%= User.getUserRecord().getSiteId() == -1?"&includeAllSites=true&siteId=-1":"&mySiteOnly=true&siteId="+ User.getUserRecord().getSiteId() %>','participantIdValues');"><dhv:label name="accounts.accounts_add.select">Select</dhv:label></a>]
                    <%= showAttribute(request, "contactsError") %>
                </td>
               </tr>
               <tr>
                <td valign="bottom">
                  <input type="checkbox" name="emailParticipants" <%=CallDetails.getEmailParticipants() ? "checked":""%>>
                  <dhv:label  name="accounts.accountasset_include.EmailParticipants"> Email Participants</dhv:label> 
                  <%= showAttribute(request, "participantsError") %>
                </td>
              </tr>
            </table>
          </td>
        </tr>
      </table>
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
      <input type="checkbox" name="hasFollowup" id="hasFollowup" value="on" onClick="toggleSpan(this, 'nextActionSpan')" <%= CallDetails.getHasFollowup() ? " checked" : "" %> />
      <dhv:label name="contacts.scheduleFollowUpActivity">Schedule follow-up activity?</dhv:label>
      <iframe src="empty.html" name="server_commands" id="server_commands" style="visibility:hidden" height="0"></iframe>
      <% }else{ %>
        <%= thisLookupList.getHtmlSelect("resultId", CallDetails.getResultId()) %>
      <% } %>
    </td>

  </tr>
</table>