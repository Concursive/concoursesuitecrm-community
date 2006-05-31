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
<%@ page import="java.text.DateFormat" %>
<jsp:useBean id="PriorityList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="EstimatedLOETypeList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<jsp:useBean id="TimeZoneSelect" class="org.aspcfs.utils.web.HtmlSelectTimeZone" scope="request"/>
<jsp:useBean id="ticketTaskCategoryList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<script language="JavaScript" type="text/javascript" src="javascript/popContacts.js"></script>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/submit.js"></script>
<SCRIPT language="JavaScript" TYPE="text/javascript" SRC="javascript/checkString.js"></script>
<SCRIPT language="JavaScript" TYPE="text/javascript" SRC="javascript/popCalendar.js"></script>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/tasks.js"></script>
<script type="text/javascript">
function selectLinkContact() {
  var selectedAssignedTo = document.getElementById('ownerid').value;
  if (selectedAssignedTo != '-1' && !checkNullString(selectedAssignedTo)) {
    popContactsListSingle('contactid','changecontact','mySiteOnly=true&siteIdUser='+selectedAssignedTo+'&reset=true');
  } else {
    popContactsListSingle('contactid','changecontact','<%= User.getUserRecord().getSiteId() == -1?"includeAllSites=true&siteId=-1":"mySiteOnly=true&siteId="+User.getUserRecord().getSiteId() %>reset=true');
  }
}

function selectAssignedTo() {
  var selectedLinkContact = document.getElementById('contactid').value;
  if (selectedLinkContact != '-1' && !checkNullString(selectedLinkContact)) {
    popContactsListSingle('ownerid','changeowner', 'listView=employees&usersOnly=true&mySiteOnly=true&siteIdContact='+selectedLinkContact+'&reset=true');
  } else {
    popContactsListSingle('ownerid','changeowner', 'listView=employees&usersOnly=true<%= User.getUserRecord().getSiteId() == -1? "&includeAllSites=true&siteId=-1":"&mySiteOnly=true&siteId="+User.getUserRecord().getSiteId() %>&reset=true');
  }
}
</script>
<table cellpadding="4" cellspacing="0" width="100%" class="details">
  <tr>
    <th colspan="2">
      <strong><dhv:label name="ticket.task">Task</dhv:label></strong>
    </th>
  </tr>
  <tr class="containerBody">
    <td class="formLabel">
      <dhv:label name="accounts.accountasset_include.Description">Description</dhv:label>
    </td>
    <td>
      <input type="text" name="description" value="<%= toHtmlValue(Task.getDescription()) %>" size="50" maxlength="80">
      <font color="red">*</font> <%= showAttribute(request, "descriptionError") %>
    </td>
  </tr>
  <dhv:evaluate if="<%= ticketTaskCategoryList != null && ticketTaskCategoryList.size() > 0 %>">
  <tr class="containerBody">
    <td class="formLabel">
      <dhv:label name="reports.helpdesk.category">Category</dhv:label>
    </td>
    <td><%= ticketTaskCategoryList.getHtmlSelect("ticketTaskCategoryId", Task.getTicketTaskCategoryId()) %>&nbsp;</td>
  </tr>
  </dhv:evaluate>
  <dhv:evaluate if="<%= ticketTaskCategoryList == null || ticketTaskCategoryList.size() == 0 %>">
    <input type="hidden" name="ticketTaskCategoryId" value="<%= Task.getTicketTaskCategoryId() %>"/>
  </dhv:evaluate>
  <tr>
    <td nowrap class="formLabel">
      <dhv:label name="accounts.accounts_calls_list.DueDate">Due Date</dhv:label>
    </td>
    <td>
      <zeroio:dateSelect form="addTask" field="dueDate" timestamp="<%= Task.getDueDate() %>" timeZone="<%= Task.getDueDateTimeZone() %>" showTimeZone="true" />
      <%= showAttribute(request, "dueDateError") %><%= showWarningAttribute(request, "dueDateWarning") %>
    </td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel"><dhv:label name="accounts.accounts_contacts_calls_details_followup_include.Priority">Priority</dhv:label></td>
    <td>
      <%= PriorityList.getHtmlSelect("priority",Task.getPriority()) %>
    </td>
  </tr>
  <tr class="containerBody"> 
    <td class="formLabel"><dhv:label name="accounts.accountasset_include.Status">Status</dhv:label></td>
    <td>
      <table cellpadding="3" cellspacing="0" class="empty">
        <tr>
          <td>
            <input type="checkbox" name="chk1" value="true" onclick="javascript:setField('complete',document.addTask.chk1.checked,'addTask');" <%= Task.getComplete()?" checked":"" %>>
          </td>
          <input type="hidden" name="complete" value="<%= Task.getComplete()?"1":"0" %>">
          <input type="hidden" name="modified" value="<%= Task.getModified() %>">
          <td><dhv:label name="global.button.complete">Complete</dhv:label></td>
        </tr>
      </table>
    </td>
  </tr>
  <tr class="containerBody"> 
    <td class="formLabel"><dhv:label name="tasks.sharing">Sharing</dhv:label></td>
    <td>
      <table cellpadding="3" cellspacing="0" class="empty">
        <tr>
          <td>
            <input type="checkbox" name="chk2" value="true" onclick="javascript:setField('sharing',document.addTask.chk2.checked,'addTask');" <%= (Task.getSharing()==1)?" checked":"" %>>
            <input type="hidden" name="sharing" value="<%= Task.getSharing() %>">
          </td>
          <td>
            <dhv:label name="tasks.personal.lowercase">personal</dhv:label>
            <%= showAttribute(request, "sharingError") %>
          </td>
        </tr>
      </table>
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel" valign="top">
      <dhv:label name="actionList.assignTo">Assign To</dhv:label>
    </td>
    <td>
      <table class="empty">
        <tr>
          <td>
            <div id="changeowner">
            <%if(Task.getOwner() > 0){ %>
              <dhv:username id="<%= Task.getOwner() %>"/>
            <% }else{ %>
               <dhv:username id="<%= User.getUserId() %>"/>
            <%}%>
            </div>
          </td>
          <td>
            <input type="hidden" name="owner" id="ownerid" value="<%= Task.getOwner() == -1 ? User.getUserRecord().getId() : Task.getOwner() %>">
            <dhv:evaluate if="<%= Task.getTicketId() > -1 %>">
              &nbsp;[<a href="javascript:popContactsListSingle('ownerid','changeowner', 'listView=employees&usersOnly=true&ticketId=<%= Task.getTicketId() %>&reset=true');"><dhv:label name="accounts.accounts_contacts_validateimport.ChangeOwner">Change Owner</dhv:label></a>]
            </dhv:evaluate><dhv:evaluate if="<%= Task.getTicketId() == -1 %>">
              &nbsp;[<a href="javascript:selectAssignedTo();"><dhv:label name="accounts.accounts_contacts_validateimport.ChangeOwner">Change Owner</dhv:label></a>]
            </dhv:evaluate>
          </td>
        </tr>
      </table>
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel" valign="top">
      <dhv:label name="tasks.estimatedLOE">Estimated LOE</dhv:label>
    </td>
    <td>
      <input type="text" size="4" name="estimatedLOE" value="<%= Task.getEstimatedLOEValue() %>">
      &nbsp;<%= EstimatedLOETypeList.getHtmlSelect("estimatedLOEType",Task.getEstimatedLOEType()) %>
      &nbsp;<%= showAttribute(request, "estimatedLOEError") %>
    </td>
  </tr>
  <tr class="containerBody">
    <td valign="top" nowrap class="formLabel"><dhv:label name="accounts.accounts_add.Notes">Notes</dhv:label></td>
    <td>
      <TEXTAREA NAME="notes" ROWS="3" COLS="50"><%= toString(Task.getNotes()) %></TEXTAREA>
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel" valign="top">
      <dhv:label name="tasks.linkContact">Link Contact</dhv:label>
    </td>
    <td>
      <table class="empty">
        <tr>
          <td>
            <div id="changecontact">
              <% if(Task.getContactName()!=null) {%>
                <%= toHtml(Task.getContactName()) %>
                <input type="hidden" name="contactName" value="<%= toHtmlValue(Task.getContactName()) %>">
              <%} else {%>
                <dhv:label name="accounts.accounts_contacts_calls_details_followup_include.None">None</dhv:label>
              <%}%>
            </div>
          </td>
          <td>
            <input type="hidden" name="contact" id="contactid" value="<%=(Task.getContactId() == -1)?-1:Task.getContactId()%>">
            <dhv:evaluate if="<%= Task.getTicketId() > -1 %>">
              &nbsp;[<a href="javascript:popContactsListSingle('contactid','changecontact','mySiteOnly=true&ticketId=<%= Task.getTicketId() %>&reset=true');"><dhv:label name="admin.changeContact">Change Contact</dhv:label></a>]
            </dhv:evaluate><dhv:evaluate if="<%= Task.getTicketId() == -1 %>">
              &nbsp;[<a href="javascript:selectLinkContact();"><dhv:label name="admin.changeContact">Change Contact</dhv:label></a>]
            </dhv:evaluate>
          </td>
          <td>
            [<a href="javascript:document.addTask.contact.value='-1';javascript:changeDivContent('changecontact',label('label.none','None'));"><dhv:label name="admin.clearContact">Clear Contact</dhv:label></a>]
          </td>
        </tr>
      </table>
    </td>
  </tr>
</table>
<input type="hidden" name="ticketId" value="<%= Task.getTicketId() %>"/>
<%= addHiddenParams(request, "popup|popupType|actionId") %> 
<input type="hidden" name="onlyWarnings" value="<%=(Task.getOnlyWarnings()?"on":"off")%>" />
