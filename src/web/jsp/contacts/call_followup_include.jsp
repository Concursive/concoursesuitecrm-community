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
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ page import="java.text.DateFormat,org.aspcfs.utils.DatabaseUtils,java.util.Iterator" %>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popContacts.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/submit.js"></script>
<br>
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="2">
      <strong>
        <% if((CallDetails.getAlertDate() == null) || (request.getAttribute("alertDateWarning") != null)) {%>
          <dhv:label name="accounts.accounts_contacts_calls_details_followup_include.ScheduleAnActivity">Schedule an Activity</dhv:label>
        <%} else {%>
          <dhv:label name="contact.call.modifyActivity">Modify Activity</dhv:label>
        <%}%>
      </strong>
    </th>
  </tr>
  <tr class="containerBody">
    <td class="formLabel">
      <dhv:label name="accounts.accounts_add.Type">Type</dhv:label>
    </td>
    <td>
      <%= CallTypeList.getHtmlSelect("alertCallTypeId", CallDetails.getAlertCallTypeId()) %><font color="red">*</font><%= showAttribute(request, "followupTypeError") %>
    </td>
  </tr>
  <dhv:evaluate if="<%= "GlobalItem".equals(request.getParameter("actionSource")) %>">  
  <tr>
    <td class="formLabel">
      <dhv:label name="accounts.accountasset_include.Contact">Contact</dhv:label>
    </td>
    <td>
      <table border="0" cellspacing="0" cellpadding="4" class="empty">
        <tr>
          <td valign="top" nowrap>
            <div id="changefollowupcontact">
              <% if(ContactDetails.getId() != -1) {%>
                <%= toHtml(ContactDetails.getValidName()) %>
              <%} else {%>
                <dhv:label name="accounts.accounts_add.NoneSelected">None Selected</dhv:label>
              <%}%>
            </div>
          </td>
          <td valign="top" width="100%" nowrap>
            <font color="red">*</font><%= showAttribute(request, "contactIdError") %>
            [<a href="javascript:popContactsListSingle('followupContactLink','changefollowupcontact','listView=mycontacts<%= User.getUserRecord().getSiteId() == -1?"&includeAllSites=true&siteId=-1":"&mySiteOnly=true&siteId="+User.getUserRecord().getSiteId() %>&nonUsersOnly=true&reset=true&filters=all|accountcontacts|mycontacts');"><dhv:label name="admin.selectContact">Select Contact</dhv:label></a>]
            <input type="hidden" name="followupContactId" id="followupContactLink" value="<%= ContactDetails.getId() %>">
            [<a href="javascript:popURL('ExternalContacts.do?command=Prepare&source=addactivity&actionSource=GlobalItem&popup=true', 'New_Contact','600','550','yes','yes');"><dhv:label name="admin.createContact">Create new contact</dhv:label></a>]
          </td>
        </tr>
      </table>
    </td>
  </tr>
</dhv:evaluate>
<% if(contactList!=null && !contactList.isEmpty() && "accountItem".equals(actionSource)){%>    
  <tr class="containerBody">
  <td class="formLabel">
      <dhv:label name="accounts.accounts_add.Contact">Contact</dhv:label>
   </td>
   <td>
   <select name="followupContactId">
   <option value="-1"><dhv:label name="calendar.none.4dashes">-- None --</dhv:label></option>
   <% Iterator j = contactList.iterator();
      if ( j.hasNext() ) {
          while (j.hasNext()) {
             Contact thisContact = (Contact) j.next();%>
         <%if (CallDetails!=null && thisContact.getId()==CallDetails.getFollowupContactId()){%>
         <option value="<%= thisContact.getId()%>" selected="selected"><%= thisContact.getNameLast()%></option>
         <%}else{%>
         <option value="<%= thisContact.getId()%>"><%= thisContact.getNameLast()%></option>
         <%}%>
     <%}
     }
     %>       
   </select>
   </td>
  </tr>
  <%}%>
    <tr class="containerBody">
    <td class="formLabel">
      <dhv:label name="quotes.date">Date</dhv:label>
    </td>
    <td>
      <%-- TODO: If no time set, default to 8:30 AM, or user's daily start time --%>
      <zeroio:dateSelect form="addCall" field="alertDate" timestamp="<%= CallDetails.getAlertDate() %>" timeZone="<%= CallDetails.getAlertDateTimeZone() %>"/>
     <dhv:label name="project.at">at</dhv:label>
      <zeroio:timeSelect baseName="alertDate" value="<%= CallDetails.getAlertDate() %>" timeZone="<%= CallDetails.getAlertDateTimeZone() %>" showTimeZone="true" />
      <font color="red">*</font><%= showAttribute(request, "alertDateError") %><%= showWarningAttribute(request, "alertDateWarning") %>
    </td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel">
      <dhv:label name="accounts.accountasset_include.Description">Description</dhv:label>
    </td>
    <td>
      <input type="text" size="50" maxlength="255" name="alertText" value="<%= toHtmlValue(CallDetails.getAlertText()) %>"><font color="red">*</font><%= showAttribute(request, "descriptionError") %>
    </td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel" valign="top">
      <dhv:label name="accounts.accountasset_include.Notes">Notes</dhv:label>
    </td>
    <td>
      <TEXTAREA NAME="followupNotes" ROWS="3" COLS="50"><%= toString(CallDetails.getFollowupNotes()) %></TEXTAREA>
    </td>
  </tr>
  
  <tr class="containerBody">
    <td class="formLabel" valign="top">
      <dhv:label name="accounts.accounts_contacts_calls_details_followup_include.Priority">Priority</dhv:label>
    </td>
    <td>
       <%= PriorityList.getHtmlSelect("priorityId", CallDetails.getPriorityId()) %><font color="red">*</font><%= showAttribute(request, "priorityError") %>
    </td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel" valign="top">
      <dhv:label name="accounts.accounts_contacts_calls_details_followup_include.Reminder">Reminder</dhv:label>
    </td>
    <td>
      <input type="radio" name="reminder" value="0" <%= CallDetails.getReminderId() > 0 ? "" : " checked"%>><dhv:label name="contact.doNotSendReminder">Do not send a reminder</dhv:label><br>
      <input type="radio" name="reminder" value="1" <%= CallDetails.getReminderId() > 0 ? " checked" : ""%>><dhv:label name="contact.sendReminder">Send a reminder</dhv:label> <input type="text" size="2" name="reminderId" value="<%= CallDetails.getReminderId() > -1 ? CallDetails.getReminderId() : 5 %>">
      <%= ReminderTypeList.getHtmlSelect("reminderTypeId", CallDetails.getReminderTypeId()) %> <dhv:label name="contact.beforeDueDate">before the due date</dhv:label>
    </td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel">
      <dhv:label name="accounts.accounts_contacts_calls_details_followup_include.AssignedTo">Assigned to...</dhv:label>
    </td>
    <td>
      <table class="empty">
        <tr>
          <td>
            <div id="changeowner">
            <%if(CallDetails.getOwner() > 0){ %>
              <dhv:username id="<%= CallDetails.getOwner() %>"/>
            <% }else{ %>
               <dhv:username id="<%= User.getUserId() %>"/>
            <%}%>
            </div>
          </td>
          <td>
            <input type="hidden" name="owner" id="ownerid" value="<%= CallDetails.getOwner() == -1 ? User.getUserRecord().getId() : CallDetails.getOwner() %>">
            <%if (ContactDetails.getId() != -1) { %>
              &nbsp;[<a href="javascript:popContactsListSingle('ownerid','changeowner', 'usersOnly=true&siteIdContact=<%=ContactDetails.getId()%>&reset=true&filters=employees|accountcontacts|mycontacts|myprojects|all');"><dhv:label name="accounts.accounts_contacts_validateimport.ChangeOwner">Change Owner</dhv:label></a>]
            <%}else if (OrgDetails.getId() != -1){%>
              &nbsp;[<a href="javascript:popContactsListSingle('ownerid','changeowner', 'usersOnly=true&siteIdOrg=<%=OrgDetails.getId()%>&reset=true&filters=employees|accountcontacts|mycontacts|myprojects|all');"><dhv:label name="accounts.accounts_contacts_validateimport.ChangeOwner">Change Owner</dhv:label></a>]
            <%}else{%>
              &nbsp;[<a href="javascript:popContactsListSingle('ownerid','changeowner', 'usersOnly=true&reset=true&filters=employees|accountcontacts|mycontacts|myprojects|all');"><dhv:label name="accounts.accounts_contacts_validateimport.ChangeOwner">Change Owner</dhv:label></a>]
            <%}%>
          </td>
        </tr>
      </table>
    </td>
  </tr>
</table>
<input type="hidden" name="onlyWarnings" value="<%=(CallDetails.getOnlyWarnings()?"on":"off")%>" />
