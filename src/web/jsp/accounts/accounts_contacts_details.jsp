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
<%@ page import="java.util.*,java.text.DateFormat,org.aspcfs.modules.accounts.base.*,org.aspcfs.modules.contacts.base.*" %>
<jsp:useBean id="applicationPrefs" class="org.aspcfs.controller.ApplicationPrefs" scope="application"/>
<jsp:useBean id="OrgDetails" class="org.aspcfs.modules.accounts.base.Organization" scope="request"/>
<jsp:useBean id="ContactDetails" class="org.aspcfs.modules.contacts.base.Contact" scope="request"/>
<jsp:useBean id="ContactAddressList" class="org.aspcfs.modules.contacts.base.ContactAddressList" scope="request"/>
<jsp:useBean id="SiteIdList" class="org.aspcfs.utils.web.LookupList" scope="request"/> 
<jsp:useBean id="ContactSourceList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="ContactTypeList2" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="SalutationList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="Address" class="org.aspcfs.modules.base.Address" scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<%@ include file="../initPage.jsp" %>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popURL.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popAccounts.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/executeFunction.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/moveContact.js"></script>
<script language="JavaScript" TYPE="text/javascript">
function reopen() {
  window.location.href='Contacts.do?command=Details&id=<%= ContactDetails.getId() %>';
}
</script>
<form name="modContact" action="Contacts.do?command=Modify&id=<%=ContactDetails.getId()%>&orgId=<%=ContactDetails.getOrgId()%>" method="post">
<dhv:evaluate if="<%= !isPopup(request) %>">
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="Accounts.do"><dhv:label name="accounts.accounts">Accounts</dhv:label></a> > 
<a href="Accounts.do?command=Search"><dhv:label name="accounts.SearchResults">Search Results</dhv:label></a> >
<a href="Accounts.do?command=Details&orgId=<%=OrgDetails.getOrgId()%>"><dhv:label name="accounts.details">Account Details</dhv:label></a> >
<a href="Contacts.do?command=View&orgId=<%=OrgDetails.getOrgId()%>"><dhv:label name="accounts.Contacts">Contacts</dhv:label></a> >
<dhv:label name="accounts.accounts_contacts_add.ContactDetails">Contact Details</dhv:label>
</td>
</tr>
</table>
<%-- End Trails --%>
</dhv:evaluate>
<dhv:container name="accounts" selected="contacts" object="OrgDetails" param='<%= "orgId=" + OrgDetails.getOrgId() %>' hideContainer="<%= !OrgDetails.getEnabled() || OrgDetails.isTrashed() %>" appendToUrl='<%= addLinkParams(request, "popup|popupType|actionId") %>'>
  <dhv:container name="accountscontacts" selected="details" object="ContactDetails" param='<%= "id=" + ContactDetails.getId() %>' hideContainer="<%= !OrgDetails.getEnabled() || OrgDetails.isTrashed() || !ContactDetails.getEnabled() || ContactDetails.isTrashed() %>" appendToUrl='<%= addLinkParams(request, "popup|popupType|actionId") %>'>
    <dhv:evaluate if="<%= (OrgDetails.getEnabled() && !OrgDetails.isTrashed()) && (ContactDetails.getEnabled() && !ContactDetails.isTrashed()) %>">
      <input type="hidden" name="id" value="<%=ContactDetails.getId()%>">
      <input type="hidden" name="orgId" value="<%=ContactDetails.getOrgId()%>">
      <dhv:permission name="accounts-accounts-contacts-edit"><input type='button' value="<dhv:label name="global.button.modify">Modify</dhv:label>" onClick="javascript:this.form.action='Contacts.do?command=Modify<%= addLinkParams(request, "popup|popupType|actionId") %>';submit();"></dhv:permission>
      <dhv:permission name="accounts-accounts-contacts-add"><input type='button' value="<dhv:label name="global.button.Clone">Clone</dhv:label>" onClick="javascript:this.form.action='Contacts.do?command=Clone<%= addLinkParams(request, "popup|popupType|actionId") %>';submit();"></dhv:permission>
      <%--<dhv:permission name="accounts-accounts-contacts-edit"><input type='button' value="<dhv:label name="global.button.move">Move</dhv:label>" onClick="javascript:check('moveContact','<%= OrgDetails.getId() %>','<%= ContactDetails.getId() %>','&filters=all|my|disabled','<%= ContactDetails.getPrimaryContact() %>')"></dhv:permission>--%>
      <dhv:permission name="accounts-accounts-contacts-delete"><input type='button' value="<dhv:label name="global.button.delete">Delete</dhv:label>" onClick="javascript:popURLReturn('Contacts.do?command=ConfirmDelete&orgId=<%=OrgDetails.getId()%>&id=<%=ContactDetails.getId()%>&popup=true','Contacts.do?command=View', 'Delete_contact','320','200','yes','no');"></dhv:permission>
      <dhv:permission name="accounts-accounts-contacts-move-view"><input type="button" value="<dhv:label name="glpbal.button.move">Move</dhv:label>" onClick="javascript:popURLReturn('Contacts.do?command=MoveToAccount&orgId=<%=OrgDetails.getId()%>&id=<%=ContactDetails.getId()%>&popup=true','Contacts.do?command=View', 'Move_contact','400','320','yes','yes');"/></dhv:permission>
      <dhv:permission name="accounts-accounts-contacts-view"><input type="button" value="<dhv:label name="button.downloadVcard">Download VCard</dhv:label>" onClick="javascript:window.location.href='ExternalContacts.do?command=DownloadVCard&id=<%= ContactDetails.getId() %>'"/></dhv:permission>
    </dhv:evaluate>
    <dhv:permission name="accounts-accounts-contacts-view,accounts-accounts-contacts-add,accounts-accounts-contacts-edit,accounts-accounts-contacts-delete"><br>&nbsp;</dhv:permission>
<%-- TODO: Currently this block appears and hides depending on the content,
     this will need to be changed --%>
<dhv:formMessage />
<dhv:evaluate if='<%= hasText(ContactDetails, "title, typesNameString, additionalNames, nickname, birthDate, title, role") %>'>
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="2">
      <strong><dhv:label name="contacts.details">Details</dhv:label></strong>
    </th>
  </tr>
  <dhv:evaluate if="<%= hasText(ContactDetails.getTypesNameString()) %>">
  <tr class="containerBody">
    <td class="formLabel">
      <dhv:label name="accounts.accounts_contacts_add.ContactType">Contact Type</dhv:label>
    </td>
    <td>
      <%= toHtml(ContactDetails.getTypesNameString()) %>
    </td>
  </tr>
  </dhv:evaluate>
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
      <dhv:label name="accounts.accounts_add.dateOfBirth">Birthday</dhv:label>
    </td>
    <td>
      <zeroio:tz timestamp="<%= ContactDetails.getBirthDate() %>" dateOnly="true"/>
    </td>
  </tr>
  </dhv:evaluate>
  <dhv:evaluate if="<%= (ContactDetails.getSource() > 0) %>">
    <tr class="containerBody">
      <td nowrap class="formLabel">
        <dhv:label name="contact.source">Source</dhv:label>
      </td>
      <td>
        <%= ContactSourceList.getSelectedValue(ContactDetails.getSource()) %>  
      </td>
    </tr>
  </dhv:evaluate>
  <dhv:evaluate if="<%= hasText(ContactDetails.getTitle()) %>">
    <tr class="containerBody">
      <td class="formLabel">
        <dhv:label name="accounts.accounts_contacts_add.Title">Title</dhv:label>
      </td>
      <td>
        <%= toHtml(ContactDetails.getTitle()) %>
      </td>
    </tr>
  </dhv:evaluate>
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
</dhv:evaluate>
<dhv:include name="contact.emailAddresses" none="true">
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="2">
	    <strong><dhv:label name="accounts.accounts_add.EmailAddresses">Email Addresses</dhv:label></strong>
	  </th>
  </tr>
    <dhv:evaluate if="<%= ContactDetails.getNoEmail() %>">
      <tr class="containerBody">
        <td class="formLabel" nowrap>
          <dhv:label name="accounts.accounts_contacts.emailPreference">Email Preference</dhv:label>
        </td>
        <td>
           <dhv:label name="accounts.accounts_contacts.noEmail">Do Not Email</dhv:label>
        </td>
      </tr>
    </dhv:evaluate>
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
      <a href="mailto:<%= toHtml(thisEmailAddress.getEmail()) %>"><%= toHtml(thisEmailAddress.getEmail()) %></a>
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
    <td colspan="2">
      <font color="#9E9E9E"><dhv:label name="contacts.NoEmailAdresses">No email addresses entered.</dhv:label></font>
    </td>
  </tr>
<%}%>
</table>
<br />
</dhv:include>
<dhv:include name="contact.instantMessageAddresses" none="true">
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="2">
      <strong><dhv:label name="accounts.accounts_add.InstantMessageAddresses">Instant Message Addresses</dhv:label></strong>
    </th>
  </tr>
    <dhv:evaluate if="<%= ContactDetails.getNoInstantMessage() %>">
      <tr class="containerBody">
        <td class="formLabel" nowrap>
          <dhv:label name="accounts.accounts_contacts.IMPreference">Instant Messages Preference</dhv:label>
        </td>
        <td>
           <dhv:label name="accounts.accounts_contacts.noIM">Do Not Instant Message</dhv:label>
        </td>
      </tr>
    </dhv:evaluate>
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
    <td colspan="2">
      <font color="#9E9E9E"><dhv:label name="contacts.NoInstantMessageAdresses">No instant message addresses entered.</dhv:label></font>
    </td>
  </tr>
<%}%>
</table>
<br />
</dhv:include>
<dhv:include name="contact.textMessageAddresses" none="true">
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="2">
	    <strong><dhv:label name="accounts.accounts_add.TextMessageAddresses">Text Message Addresses</dhv:label></strong>
	  </th>
  </tr>
  <dhv:evaluate if="<%= ContactDetails.getNoTextMessage() %>">
    <tr class="containerBody">
      <td class="formLabel" nowrap>
        <dhv:label name="accounts.accounts_contacts.textMessagePreference">Text Messages Preference</dhv:label>
      </td>
      <td>
         <dhv:label name="accounts.accounts_contacts.noTextMessage">Do Not Text Message</dhv:label>
      </td>
    </tr>
  </dhv:evaluate>
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
        <a href="mailto:<%= toHtml(thisTextMessageAddress.getTextMessageAddress()) %>"><%= toHtml(thisTextMessageAddress.getTextMessageAddress()) %></a>
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
    <td colspan="2">
      <font color="#9E9E9E"><dhv:label name="contacts.NoTextMessageAdresses">No text message addresses entered.</dhv:label></font>
    </td>
  </tr>
<%}%>
</table>
&nbsp;
</dhv:include>
<dhv:include name="contact.phoneNumbers" none="true">
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="2">
	    <strong><dhv:label name="accounts.accounts_add.PhoneNumbers">Phone Numbers</dhv:label></strong>
	  </th>
  </tr>
  <dhv:evaluate if="<%= ContactDetails.getNoPhone() %>">
    <tr class="containerBody">
      <td class="formLabel" nowrap>
        <dhv:label name="accounts.accounts_contacts.phonePreference">Phone Calls Preference</dhv:label>
      </td>
      <td>
         <dhv:label name="accounts.accounts_contacts.noPhone">Do Not Phone Call</dhv:label>
      </td>
    </tr>
  </dhv:evaluate>
  <dhv:evaluate if="<%= ContactDetails.getNoFax() %>">
    <tr class="containerBody">
      <td class="formLabel" nowrap>
        <dhv:label name="accounts.accounts_contacts.faxPreference">Fax Preference</dhv:label>
      </td>
      <td>
         <dhv:label name="accounts.accounts_contacts.noFax">Do Not Fax</dhv:label>
      </td>
    </tr>
  </dhv:evaluate>
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
      <%= toHtml(thisPhoneNumber.getPhoneNumber()) %>
      <dhv:evaluate if='<%= "true".equals(applicationPrefs.get("ASTERISK.OUTBOUND.ENABLED")) %>'>
        <dhv:evaluate if="<%= hasText(thisPhoneNumber.getPhoneNumber()) %>">
          <a href="javascript:popURL('OutboundDialer.do?command=Call&auto-populate=true&number=<%= StringUtils.jsStringEscape(thisPhoneNumber.getPhoneNumber()) %>','OUTBOUND_CALL','400','200','yes','yes');"><img src="images/icons/stock_call-16.gif" align="absMiddle" title="Call number" border="0"/></a>
        </dhv:evaluate>
      </dhv:evaluate>
      <dhv:evaluate if="<%= thisPhoneNumber.getPrimaryNumber() %>">
        <dhv:label name="account.primary.brackets">(Primary)</dhv:label>
      </dhv:evaluate>
    </td>
  </tr>
<%    
    }
  } else {
%>
  <tr class="containerBody">
    <td colspan="2">
      <font color="#9E9E9E"><dhv:label name="contacts.NoPhoneNumbers">No phone numbers entered.</dhv:label></font>
    </td>
  </tr>
<%}%>
</table>
&nbsp;
</dhv:include>
<dhv:include name="contact.addresses" none="true">
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="2">
	    <strong><dhv:label name="accounts.accounts_add.Addresses">Addresses</dhv:label></strong>
	  </th>
  </tr>
  <dhv:evaluate if="<%= ContactDetails.getNoMail() %>">
    <tr class="containerBody">
      <td class="formLabel" nowrap>
        <dhv:label name="accounts.accounts_contacts.mailPreference">Mail Preference</dhv:label>
      </td>
      <td>
         <dhv:label name="accounts.accounts_contacts.noMail">Do Not Mail</dhv:label>
      </td>
    </tr>
  </dhv:evaluate>
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
    <td colspan="2">
      <font color="#9E9E9E"><dhv:label name="contacts.NoAddresses">No addresses entered.</dhv:label></font>
    </td>
  </tr>
<%}%>
</table>
&nbsp;
</dhv:include>
  <dhv:evaluate if="<%= hasText(ContactDetails.getNotes()) %>">
<dhv:include name="contact.additionalDetails" none="true">
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
</dhv:include>
  </dhv:evaluate>
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="2">
      <strong><dhv:label name="accounts.accounts_contacts_calls_details.RecordInformation">Record Information</dhv:label></strong>
    </th>
  </tr>
  <dhv:evaluate if="<%= ContactDetails.getConversionDate() != null %>">
    <tr class="containerBody">
      <td class="formLabel">
        <dhv:label name="leads.conversionDate">Lead Conversion Date</dhv:label>
      </td>
      <td>
        <zeroio:tz timestamp="<%= ContactDetails.getConversionDate() %>" dateOnly="true" timeZone="<%= User.getTimeZone() %>" showTimeZone="false" default="&nbsp;"/>
      </td>
    </tr>
  </dhv:evaluate>
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
  <dhv:evaluate if="<%= hasText(ContactDetails.getImportName()) %>">
    <tr class="containerBody">
      <td class="formLabel">
        <dhv:label name="contact.importName">Import Name</dhv:label>
     </td>
     <td>
       <%= toHtml(ContactDetails.getImportName()) %>
     </td>
    </tr>
  </dhv:evaluate>      
</table>
  <dhv:evaluate if="<%= (OrgDetails.getEnabled() && !OrgDetails.isTrashed()) && (ContactDetails.getEnabled() && !ContactDetails.isTrashed()) %>">
    <dhv:permission name="accounts-accounts-contacts-view,accounts-accounts-contacts-add,accounts-accounts-contacts-edit,accounts-accounts-contacts-delete"><br></dhv:permission>
    <dhv:permission name="accounts-accounts-contacts-edit"><input type='button' value="<dhv:label name="global.button.modify">Modify</dhv:label>" onClick="javascript:this.form.action='Contacts.do?command=Modify<%= addLinkParams(request, "popup|popupType|actionId") %>';submit();"></dhv:permission>
    <dhv:permission name="accounts-accounts-contacts-add"><input type='button' value="<dhv:label name="global.button.Clone">Clone</dhv:label>" onClick="javascript:this.form.action='Contacts.do?command=Clone<%= addLinkParams(request, "popup|popupType|actionId") %>';submit();"></dhv:permission>
    <%--<dhv:permission name="accounts-accounts-contacts-edit"><input type='button' value="<dhv:label name="global.button.move">Move</dhv:label>" onClick="javascript:check('moveContact','<%=OrgDetails.getId()%>','<%=ContactDetails.getId()%>','&filters=all|my|disabled','<%=ContactDetails.getPrimaryContact()%>')"></dhv:permission>--%>
    <dhv:permission name="accounts-accounts-contacts-delete"><input type='button' value="<dhv:label name="global.button.delete">Delete</dhv:label>" onClick="javascript:popURLReturn('Contacts.do?command=ConfirmDelete&orgId=<%=OrgDetails.getId()%>&id=<%=ContactDetails.getId()%>&popup=true','Contacts.do?command=View', 'Delete_contact','320','200','yes','no');"></dhv:permission>
    <dhv:permission name="accounts-accounts-contacts-move-view"><input type="button" value="<dhv:label name="glpbal.button.move">Move</dhv:label>" onClick="javascript:popURLReturn('Contacts.do?command=MoveToAccount&orgId=<%=OrgDetails.getId()%>&id=<%=ContactDetails.getId()%>&popup=true','Contacts.do?command=View', 'Move_contact','400','320','yes','yes');"/></dhv:permission>
    <dhv:permission name="accounts-accounts-contacts-view"><input type="button" value="<dhv:label name="button.downloadVcard">Download VCard</dhv:label>" onClick="javascript:window.location.href='ExternalContacts.do?command=DownloadVCard&id=<%= ContactDetails.getId() %>'"/></dhv:permission>
  </dhv:evaluate>
</dhv:container>
</dhv:container>
</form>
