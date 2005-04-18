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
<jsp:useBean id="OrgDetails" class="org.aspcfs.modules.accounts.base.Organization" scope="request"/>
<jsp:useBean id="ContactDetails" class="org.aspcfs.modules.contacts.base.Contact" scope="request"/>
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
<dhv:container name="accounts" selected="contacts" object="OrgDetails" param="<%= "orgId=" + OrgDetails.getOrgId() %>">
  <dhv:container name="accountscontacts" selected="details" object="ContactDetails" param="<%= "id=" + ContactDetails.getId() %>">
    <input type="hidden" name="id" value="<%=ContactDetails.getId()%>">
    <input type="hidden" name="orgId" value="<%=ContactDetails.getOrgId()%>">
    <dhv:permission name="accounts-accounts-contacts-edit"><input type='button' value="<dhv:label name="global.button.modify">Modify</dhv:label>" onClick="javascript:this.form.action='Contacts.do?command=Modify';submit();"></dhv:permission>
    <dhv:permission name="accounts-accounts-contacts-add"><input type='button' value="<dhv:label name="global.button.Clone">Clone</dhv:label>" onClick="javascript:this.form.action='Contacts.do?command=Clone';submit();"></dhv:permission>
    <%--<dhv:permission name="accounts-accounts-contacts-edit"><input type='button' value="<dhv:label name="global.button.move">Move</dhv:label>" onClick="javascript:check('moveContact','<%= OrgDetails.getId() %>','<%= ContactDetails.getId() %>','&filters=all|my|disabled','<%= ContactDetails.getPrimaryContact() %>')"></dhv:permission>--%>
    <dhv:permission name="accounts-accounts-contacts-delete"><input type='button' value="<dhv:label name="global.button.delete">Delete</dhv:label>" onClick="javascript:popURLReturn('Contacts.do?command=ConfirmDelete&orgId=<%=OrgDetails.getId()%>&id=<%=ContactDetails.getId()%>&popup=true','Contacts.do?command=View', 'Delete_contact','320','200','yes','no');"></dhv:permission>
    <dhv:permission name="accounts-accounts-contacts-move-view"><input type="button" value="<dhv:label name="glpbal.button.move">Move</dhv:label>" onClick="javascript:popURLReturn('Contacts.do?command=MoveToAccount&orgId=<%=OrgDetails.getId()%>&id=<%=ContactDetails.getId()%>&popup=true','Contacts.do?command=View', 'Move_contact','400','320','yes','yes');"/></dhv:permission>
    <dhv:permission name="accounts-accounts-contacts-edit,accounts-accounts-contacts-delete"><br>&nbsp;</dhv:permission>
<%-- TODO: Currently this block appears and hides depending on the content,
     this will need to be changed --%>
<dhv:evaluate if="<%= hasText(ContactDetails.getTitle()) || hasText(ContactDetails.getTypesNameString()) %>">
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
</table>
&nbsp;
</dhv:evaluate>
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
    <td class="formLabel">
      <%= toHtml(thisEmailAddress.getTypeName()) %>
    </td>
    <td>
      <dhv:evaluate if="<%= hasText(thisEmailAddress.getEmail()) %>">
      <a href="mailto:<%= toHtml(thisEmailAddress.getEmail()) %>"><%= toHtml(thisEmailAddress.getEmail()) %></a>&nbsp;
        <% if(!thisEmailAddress.getPrimaryEmail()) {%>
          &nbsp;
        <%} else {%>
          <dhv:label name="account.primary.brackets">(Primary)</dhv:label>
        <%}%>
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
      <font color="#9E9E9E"><dhv:label name="contacts.NoEmailAdresses">No email addresses entered.</dhv:label></font>
    </td>
  </tr>
<%}%>
</table>
<br />
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
      <a href="mailto:<%= toHtml(thisTextMessageAddress.getTextMessageAddress()) %>"><%= toHtml(thisTextMessageAddress.getTextMessageAddress()) %></a>&nbsp;<%= (thisTextMessageAddress.getPrimaryTextMessageAddress()) ? "(Primary)" : "" %>
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
    <td class="formLabel">
      <%= toHtml(thisPhoneNumber.getTypeName()) %>
    </td>
    <td>
      <%= toHtml(thisPhoneNumber.getPhoneNumber()) %>&nbsp;
<% if(!thisPhoneNumber.getPrimaryNumber()) {%>
  &nbsp;
<%} else {%>
  <dhv:label name="account.primary.brackets">(Primary)</dhv:label>
<%}%>
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
    <td class="formLabel" valign="top">
      <%= toHtml(thisAddress.getTypeName()) %>
    </td>
    <td>
      <%= toHtml(thisAddress.toString()) %>&nbsp;
<% if(!thisAddress.getPrimaryAddress()) {%>
  &nbsp;
<%} else {%>
  <dhv:label name="account.primary.brackets">(Primary)</dhv:label>
<%}%>
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
    <td class="formLabel"><dhv:label name="accounts.accounts_add.Notes">Notes</dhv:label></td>
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
<dhv:permission name="accounts-accounts-contacts-edit,accounts-accounts-contacts-delete"><br></dhv:permission>
<dhv:permission name="accounts-accounts-contacts-edit"><input type='button' value="<dhv:label name="global.button.modify">Modify</dhv:label>" onClick="javascript:this.form.action='Contacts.do?command=Modify';submit();"></dhv:permission>
<dhv:permission name="accounts-accounts-contacts-add"><input type='button' value="<dhv:label name="global.button.Clone">Clone</dhv:label>" onClick="javascript:this.form.action='Contacts.do?command=Clone';submit();"></dhv:permission>
<%--<dhv:permission name="accounts-accounts-contacts-edit"><input type='button' value="<dhv:label name="global.button.move">Move</dhv:label>" onClick="javascript:check('moveContact','<%=OrgDetails.getId()%>','<%=ContactDetails.getId()%>','&filters=all|my|disabled','<%=ContactDetails.getPrimaryContact()%>')"></dhv:permission>--%>
<dhv:permission name="accounts-accounts-contacts-delete"><input type='button' value="<dhv:label name="global.button.delete">Delete</dhv:label>" onClick="javascript:popURLReturn('Contacts.do?command=ConfirmDelete&orgId=<%=OrgDetails.getId()%>&id=<%=ContactDetails.getId()%>&popup=true','Contacts.do?command=View', 'Delete_contact','320','200','yes','no');"></dhv:permission>
<dhv:permission name="accounts-accounts-contacts-move-view"><input type="button" value="<dhv:label name="glpbal.button.move">Move</dhv:label>" onClick="javascript:popURLReturn('Contacts.do?command=MoveToAccount&orgId=<%=OrgDetails.getId()%>&id=<%=ContactDetails.getId()%>&popup=true','Contacts.do?command=View', 'Move_contact','400','320','yes','yes');"/></dhv:permission>
  </dhv:container>
</dhv:container>
</form>
