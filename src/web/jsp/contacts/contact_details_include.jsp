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
<%@ page import="java.util.*,java.text.DateFormat,org.aspcfs.modules.contacts.base.*, org.aspcfs.modules.base.Constants" %>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/confirmDelete.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/scrollReload.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popURL.js"></script>
<script type="text/javascript">
<% String param2 = addLinkParams(request, "popup|popupType|actionId");%>
function reopen() {
  scrollReload('ExternalContacts.do?command=ContactDetails&id=<%= ContactDetails.getId() %><%= param2 %>');
}
function reopenOnDelete() {
  try {
    if ('<%= isPopup(request) %>' != 'true') {
      scrollReload('ExternalContacts.do?command=SearchContacts');
    } else {
      var contactId = -1;
      try {
        contactId = opener.reopenContact('<%= ContactDetails.getId() %>');
      } catch (oException) {
      }
      if (contactId != '<%= ContactDetails.getId() %>') {
        opener.reopen();
      }
    }
  } catch (oException) {
  }
  if ('<%= isPopup(request) %>' == 'true') {
    window.close();
  }
}

function reopenContact(id) {
  if (id == '<%= ContactDetails.getId() %>') {
    scrollReload('ExternalContacts.do?command=SearchContacts');
    return id;
  } else {
    return '<%= ContactDetails.getId() %>';
  }
}
</script>
<dhv:container name="contacts" selected="details" object="ContactDetails" param="<%= "id=" + ContactDetails.getId() %>" appendToUrl="<%= param2 %>" hideContainer="<%= !ContactDetails.getEnabled() || ContactDetails.isTrashed() %>">
  <dhv:evaluate if="<%= ContactDetails.getEnabled()  && !ContactDetails.isTrashed() %>">
    <dhv:sharing primaryBean="ContactDetails" action="edit" all="true"><input type="button" name="cmd" value="<dhv:label name="global.button.modify">Modify</dhv:label>"	onClick="document.details.command.value='Modify';document.details.submit()"></dhv:sharing>
    <dhv:evaluate if="<%= !isPopup(request) %>">
      <dhv:permission name="contacts-external_contacts-add"><input type="button" value="<dhv:label name="global.button.Clone">Clone</dhv:label>" onClick="javascript:this.form.action='ExternalContacts.do?command=Clone&id=<%= ContactDetails.getId() %>';submit();"></dhv:permission>
    </dhv:evaluate>
    <dhv:sharing primaryBean="ContactDetails" action="delete" all="true"><input type="button" name="cmd" value="<dhv:label name="button.delete">Delete</dhv:label>" onClick="javascript:popURLReturn('ExternalContacts.do?command=ConfirmDelete&id=<%= ContactDetails.getId() %>&popup=true<%= isPopup(request) ? "&sourcePopup=true":"" %>','ExternalContacts.do?command=SearchContacts', 'Delete_contact','320','200','yes','no');"></dhv:sharing>
    <dhv:evaluate if="<%= ContactDetails.getOrgId() > 0 %>">
      <dhv:permission name="contacts-external_contacts-edit,contacts-external_contacts-delete"><input type="button" value="<dhv:label name="global.button.move">Move</dhv:label>" onClick="javascript:popURLReturn('ExternalContacts.do?command=MoveToAccount&orgId=<%=ContactDetails.getOrgId()%>&id=<%=ContactDetails.getId()%>&popup=true','ExternalContacts.do?command=View', 'Move_contact','400','320','yes','yes');"/></dhv:permission>
    </dhv:evaluate>
    <dhv:permission name="contacts-external_contacts-view"><input type="button" value="<dhv:label name="button.downloadVcard">Download VCard</dhv:label>" onClick="javascript:window.location.href='ExternalContacts.do?command=DownloadVCard&id=<%= ContactDetails.getId() %>'"/></dhv:permission>
    <dhv:permission name="contacts-external_contacts-view,contacts-external_contacts-add,contacts-external_contacts-edit,contacts-external_contacts-delete"><br>&nbsp;</dhv:permission>
  </dhv:evaluate>
<%--  <dhv:evaluate if="<%= ContactDetails.isTrashed() %>">
      <dhv:permission name="contacts-external_contacts-delete">
        <input type="button" value="<dhv:label name="button.restore">Restore</dhv:label>" onClick="javascript:this.form.action='ExternalContacts.do?command=Restore&id=<%= ContactDetails.getId() %>';submit();"><br>&nbsp;
      </dhv:permission>
  </dhv:evaluate> --%>
  <table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="2">
      <strong><dhv:label name="contacts.details">Details</dhv:label></strong>
    </th>
  </tr>
  <dhv:evaluate if="<%= hasText(ContactDetails.getAdditionalNames()) %>">
  <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="accounts.accounts_add.additionalNames">Additional Names</dhv:label>
    </td>
    <td>
      <%= toHtml(ContactDetails.getAdditionalNames()) %>
    </td>
  </tr>
  </dhv:evaluate>
  <dhv:evaluate if="<%= hasText(ContactDetails.getNickname()) %>">
  <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="accounts.accounts_add.nickname">Nickname</dhv:label>
    </td>
    <td>
      <%= toHtml(ContactDetails.getNickname()) %>
    </td>
  </tr>
  </dhv:evaluate>
  <dhv:evaluate if="<%= ContactDetails.getBirthDate() != null %>">
  <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="accounts.accounts_add.dateOfBirth">Date of Birth</dhv:label>
    </td>
    <td>
      <zeroio:tz timestamp="<%= ContactDetails.getBirthDate() %>" dateOnly="true"/>
    </td>
  </tr>
  </dhv:evaluate>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="accounts.accounts_contacts_add.Title">Title</dhv:label>
    </td>
    <td>
      <%= toHtml(ContactDetails.getTitle()) %>
    </td>
  </tr>
  <dhv:evaluate if="<%= hasText(ContactDetails.getRole()) %>">
  <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="accounts.accounts_add.role">Role</dhv:label>
    </td>
    <td>
      <%= toHtml(ContactDetails.getRole()) %>
    </td>
  </tr>
  </dhv:evaluate>
  </table>
  &nbsp;<br />
  <table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
    <tr>
      <th colspan="2">
        <strong><dhv:label name="accounts.accounts_add.EmailAddresses">Email Addresses</dhv:label></strong>
      </th>
    </tr>
  <%
    Iterator iemail = ContactDetails.getEmailAddressList().iterator();
    if (iemail.hasNext()) {
      while (iemail.hasNext()) {
        ContactEmailAddress thisEmailAddress = (ContactEmailAddress)iemail.next();
  %>
      <tr class="containerBody">
        <td class="formLabel" nowrap>
          <%= toHtml(thisEmailAddress.getTypeName()) %>
        </td>
        <td>
          <a href="mailto:<%= toHtml(thisEmailAddress.getEmail()) %>"><%= toHtml(thisEmailAddress.getEmail()) %></a>&nbsp;
          <dhv:evaluate if="<%=thisEmailAddress.getPrimaryEmail()%>">
            <dhv:label name="account.primary.brackets">(Primary)</dhv:label>
          </dhv:evaluate>
        </td>
      </tr>
  <%
      }
    } else {
  %>
      <tr class="containerBody">
        <td>
          <font color="#9E9E9E"><dhv:label name="contacts.NoEmailAdresses">No email addresses entered.</dhv:label></font>
        </td>
      </tr>
  <%}%>
  </table>
  &nbsp;
  
  <table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
    <tr>
      <th colspan="2">
        <strong><dhv:label name="accounts.accounts_add.InstantMessageAddresses">Instant Message Addresses</dhv:label></strong>
      </th>
    </tr>
  <%
    Iterator imAddress = ContactDetails.getInstantMessageAddressList().iterator();
    if (imAddress.hasNext()) {
      while (imAddress.hasNext()) {
        ContactInstantMessageAddress thisInstantMessageAddress = (ContactInstantMessageAddress)imAddress.next();
  %>
    <tr class="containerBody">
      <td class="formLabel">
        <%= toHtml(thisInstantMessageAddress.getAddressIMTypeName()) %>
      </td>
      <td>
        <dhv:evaluate if="<%= hasText(thisInstantMessageAddress.getAddressIM()) %>">
          <%= toHtml(thisInstantMessageAddress.getAddressIM()) %>
           (<%= toHtml(thisInstantMessageAddress.getAddressIMServiceName()) %>)
          <dhv:evaluate if="<%=thisInstantMessageAddress.getPrimaryIM()%>">
            <dhv:label name="account.primary.brackets">(Primary)</dhv:label>
          </dhv:evaluate>
        </dhv:evaluate>
        &nbsp;
      </td>
    </tr>
  <%
      }
    } else {
  %>
    <tr class="containerBody">
      <td>
        <font color="#9E9E9E"><dhv:label name="contacts.NoInstantMessageAdresses">No instant message addresses entered.</dhv:label></font>
      </td>
    </tr>
  <%}%>
  </table>
  &nbsp;
  <table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
    <tr>
      <th colspan="2">
        <strong><dhv:label name="accounts.accounts_add.TextMessageAddresses">Text Message Addresses</dhv:label></strong>
      </th>
    </tr>
  <%
    Iterator itmAddress = ContactDetails.getTextMessageAddressList().iterator();
    if (itmAddress.hasNext()) {
      while (itmAddress.hasNext()) {
        ContactTextMessageAddress thisTextMessageAddress = (ContactTextMessageAddress)itmAddress.next();
  %>
    <tr class="containerBody">
      <td class="formLabel">
        <%= toHtml(thisTextMessageAddress.getTypeName()) %>
      </td>
      <td>
        <dhv:evaluate if="<%= hasText(thisTextMessageAddress.getTextMessageAddress()) %>">
        <a href="mailto:<%= toHtml(thisTextMessageAddress.getTextMessageAddress()) %>"><%= toHtml(thisTextMessageAddress.getTextMessageAddress()) %></a>&nbsp;
          <dhv:evaluate if="<%=thisTextMessageAddress.getPrimaryTextMessageAddress()%>">
            <dhv:label name="account.primary.brackets">(Primary)</dhv:label>
          </dhv:evaluate>
        </dhv:evaluate>
        &nbsp;
      </td>
    </tr>
  <%
      }
    } else {
  %>
    <tr class="containerBody">
      <td>
        <font color="#9E9E9E"><dhv:label name="contacts.NoTextMessageAdresses">No text message addresses entered.</dhv:label></font>
      </td>
    </tr>
  <%}%>
  </table>
  &nbsp;
  <table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
    <tr>
      <th colspan="2">
        <strong><dhv:label name="accounts.accounts_add.PhoneNumbers">Phone Numbers</dhv:label></strong>
      </th>
    </tr>
  <%
    Iterator inumber = ContactDetails.getPhoneNumberList().iterator();
    if (inumber.hasNext()) {
      while (inumber.hasNext()) {
        ContactPhoneNumber thisPhoneNumber = (ContactPhoneNumber)inumber.next();
  %>
      <tr class="containerBody">
        <td class="formLabel" nowrap>
          <%= toHtml(thisPhoneNumber.getTypeName()) %>
        </td>
        <td>
          <%= toHtml(thisPhoneNumber.getPhoneNumber()) %>&nbsp;
          <dhv:evaluate if="<%=thisPhoneNumber.getPrimaryNumber()%>">
            <dhv:label name="account.primary.brackets">(Primary)</dhv:label>
          </dhv:evaluate>
        </td>
      </tr>
  <%
      }
    } else {
  %>
    <tr class="containerBody">
      <td>
        <font color="#9E9E9E"><dhv:label name="contacts.NoPhoneNumbers">No phone numbers entered.</dhv:label></font>
      </td>
    </tr>
  <%}%>
  </table>
  &nbsp;
  <table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
    <tr>
      <th colspan="2">
        <strong><dhv:label name="accounts.accounts_add.Addresses">Addresses</dhv:label></strong>
      </th>
    </tr>
  <%
    Iterator iaddress = ContactDetails.getAddressList().iterator();
    if (iaddress.hasNext()) {
      while (iaddress.hasNext()) {
        ContactAddress thisAddress = (ContactAddress)iaddress.next();
  %>
      <tr class="containerBody">
        <td class="formLabel" valign="top" nowrap>
          <%= toHtml(thisAddress.getTypeName()) %>
        </td>
        <td>
          <%= toHtml(thisAddress.toString()) %>&nbsp;
          <dhv:evaluate if="<%=thisAddress.getPrimaryAddress()%>">
            <dhv:label name="account.primary.brackets">(Primary)</dhv:label>
          </dhv:evaluate>
        </td>
      </tr>
  <%
      }
    } else {
  %>
    <tr class="containerBody">
      <td>
        <font color="#9E9E9E"><dhv:label name="contacts.NoAddresses">No addresses entered.</dhv:label></font>
      </td>
    </tr>
  <%}%>
  </table>
  &nbsp;
  <table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
    <tr>
      <th colspan="2">
        <strong><dhv:label name="accounts.accounts_add.AdditionalDetails">Additional Details</dhv:label></strong>
      </th>
    </tr>
    <tr class="containerBody">
      <td class="formLabel" nowrap><dhv:label name="accounts.accounts_add.Notes">Notes</dhv:label></td>
      <td><%= toHtml(ContactDetails.getNotes()) %></td>
    </tr>
  </table>
  &nbsp;
  <table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
    <tr>
      <th colspan="2">
        <strong><dhv:label name="accounts.accounts_contacts_calls_details.RecordInformation">Record Information</dhv:label></strong>
      </th>
    </tr>
    <tr class="containerBody">
      <td class="formLabel">
        <dhv:label name="accounts.accounts_contacts_detailsimport.Owner">Owner</dhv:label>
      </td>
      <td>
        <dhv:username id="<%= ContactDetails.getOwner() %>"/>
        <dhv:evaluate if="<%=!(ContactDetails.getHasEnabledOwnerAccount())%>"><font color="red"><dhv:label name="accounts.accounts_importcontact_details.NoLongerAccess">(No longer has access)</dhv:label></font></dhv:evaluate>
      </td>
    </tr>
    <tr class="containerBody">
      <td class="formLabel" nowrap>
        <dhv:label name="accounts.accounts_calls_list.Entered">Entered</dhv:label>
      </td>
      <td>
        <dhv:username id="<%= ContactDetails.getEnteredBy() %>"/>
        <zeroio:tz timestamp="<%= ContactDetails.getEntered()  %>" timeZone="<%= User.getTimeZone() %>" showTimeZone="true" />
      </td>
    </tr>
    <tr class="containerBody">
      <td class="formLabel" nowrap>
        <dhv:label name="accounts.accounts_contacts_calls_details.Modified">Modified</dhv:label>
      </td>
      <td>
        <dhv:username id="<%= ContactDetails.getModifiedBy() %>"/>
        <zeroio:tz timestamp="<%= ContactDetails.getModified()  %>" timeZone="<%= User.getTimeZone() %>" showTimeZone="true" />
      </td>
    </tr>
  </table>
  <dhv:evaluate if="<%= ContactDetails.getEnabled() && !ContactDetails.isTrashed() %>">
    <dhv:permission name="contacts-external_contacts-delete,contacts-external_contacts-edit"><br></dhv:permission>
    <dhv:sharing primaryBean="ContactDetails" action="edit" all="true"><input type="button" name="cmd" value="<dhv:label name="global.button.modify">Modify</dhv:label>"	onClick="document.details.command.value='Modify';document.details.submit()"></dhv:sharing>
    <dhv:evaluate if="<%= !isPopup(request) %>">
      <dhv:permission name="contacts-external_contacts-add"><input type="button" value="<dhv:label name="global.button.Clone">Clone</dhv:label>" onClick="javascript:this.form.action='ExternalContacts.do?command=Clone&id=<%= ContactDetails.getId() %>';submit();"></dhv:permission>
    </dhv:evaluate>
    <dhv:sharing primaryBean="ContactDetails" action="delete" all="true"><input type="button" name="cmd" value="<dhv:label name="button.delete">Delete</dhv:label>" onClick="javascript:popURLReturn('ExternalContacts.do?command=ConfirmDelete&id=<%= ContactDetails.getId() %>&popup=true<%= isPopup(request) ? "&sourcePopup=true":"" %>','ExternalContacts.do?command=SearchContacts', 'Delete_contact','320','200','yes','no');"></dhv:sharing>
    <dhv:evaluate if="<%= ContactDetails.getOrgId() > 0 %>">
      <dhv:permission name="contacts-external_contacts-edit,contacts-external_contacts-delete"><input type="button" value="<dhv:label name="global.button.move">Move</dhv:label>" onClick="javascript:popURLReturn('ExternalContacts.do?command=MoveToAccount&orgId=<%=ContactDetails.getOrgId()%>&id=<%=ContactDetails.getId()%>&popup=true','ExternalContacts.do?command=View', 'Move_contact','400','320','yes','yes');"/></dhv:permission>
    </dhv:evaluate>
    <dhv:permission name="contacts-external_contacts-view"><input type="button" value="<dhv:label name="button.downloadVcard">Download VCard</dhv:label>" onClick="javascript:window.location.href='ExternalContacts.do?command=DownloadVCard&id=<%= ContactDetails.getId() %>'"/></dhv:permission>
  </dhv:evaluate>
<%--  <dhv:evaluate if="<%= ContactDetails.isTrashed() %>">
      <br><dhv:permission name="contacts-external_contacts-delete">
        <input type="button" value="<dhv:label name="button.restore">Restore</dhv:label>" onClick="javascript:this.form.action='ExternalContacts.do?command=Restore&id=<%= ContactDetails.getId() %>';submit();">
      </dhv:permission>
  </dhv:evaluate> --%>
</dhv:container>
<%= addHiddenParams(request, "popup|popupType|actionId") %>
