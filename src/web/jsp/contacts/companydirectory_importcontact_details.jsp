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
<jsp:useBean id="ContactDetails" class="org.aspcfs.modules.contacts.base.Contact" scope="request"/>
<jsp:useBean id="ImportDetails" class="org.aspcfs.modules.base.Import" scope="request"/>
<%@ page import="java.util.*,java.text.DateFormat,org.aspcfs.modules.contacts.base.*" %>
<%@ include file="../initPage.jsp" %>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/confirmDelete.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popURL.js"></script>
<script language="JavaScript">
  function confirmDeleteContact() {
    url = 'ExternalContactsImports.do?command=DeleteContact&contactId=<%= ContactDetails.getId() %>&importId=<%= ImportDetails.getId() %>';
    confirmDelete(url);
  }
</script>
<table class="trails" cellspacing="0">
<tr>
  <td>
    <a href="ExternalContacts.do">Contacts</a> >
    <a href="ExternalContactsImports.do?command=View">View Imports</a> >
    <a href="ExternalContactsImports.do?command=Details&importId=<%= ImportDetails.getId() %>">Import Details</a> >
    <a href="ExternalContactsImports.do?command=ViewResults&importId=<%= ImportDetails.getId() %>">View Results</a> >
    Contact Details
  </td>
</tr>
</table>
<%-- End Trails --%>
<%@ include file="contact_details_header_include.jsp" %>
<dhv:formMessage showSpace="false" />
<dhv:permission name="contacts-external_contacts-imports-edit">
<dhv:evaluate if="<%= !ContactDetails.isApproved()  %>">
<br><input type="button" value="Delete" onClick="javascript:confirmDeleteContact();"><br><br>
</dhv:evaluate>
</dhv:permission>
<table cellpadding="4" cellspacing="0" border="1" width="100%">
  <tr>
    <td class="containerBack">
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="2">
	    <strong>Email Addresses</strong>
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
        <a href="mailto:<%= toHtml(thisEmailAddress.getEmail()) %>"><%= toHtml(thisEmailAddress.getEmail()) %></a>
      </td>
    </tr>
<%
    }
  } else {
%>
    <tr class="containerBody">
      <td>
        <font color="#9E9E9E">No email addresses entered.</font>
      </td>
    </tr>
<%}%>
</table>
&nbsp;
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="2">
	    <strong>Phone Numbers</strong>
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
        <%= toHtml(thisPhoneNumber.getPhoneNumber()) %>
      </td>
    </tr>
<%
    }
  } else {
%>
  <tr class="containerBody">
    <td>
      <font color="#9E9E9E">No phone numbers entered.</font>
    </td>
  </tr>
<%}%>
</table>
&nbsp;
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="2">
	    <strong>Addresses</strong>
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
      </td>
    </tr>
<%
    }
  } else {
%>
  <tr class="containerBody">
    <td>
      <font color="#9E9E9E">No addresses entered.</font>
    </td>
  </tr>
<%}%>
</table>
&nbsp;
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="2">
	    <strong>Additional Details</strong>
	  </th>
  </tr>
  <tr class="containerBody">
    <td class="formLabel" nowrap>Notes</td>
    <td><%= toHtml(ContactDetails.getNotes()) %></td>
  </tr>
</table>
&nbsp;
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="2">
      <strong>Record Information</strong>
    </th>
  </tr>
  <tr class="containerBody">
    <td class="formLabel">
      Owner
    </td>
    <td>
      <dhv:username id="<%= ContactDetails.getOwner() %>"/>
      <dhv:evaluate exp="<%=!(ContactDetails.getHasEnabledOwnerAccount())%>"><font color="red">(No longer has access)</font></dhv:evaluate>
    </td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel" nowrap>
      Entered
    </td>
    <td>
      <dhv:username id="<%= ContactDetails.getEnteredBy() %>"/>
      <zeroio:tz timestamp="<%= ContactDetails.getEntered()  %>" />
    </td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel" nowrap>
      Modified
    </td>
    <td>
      <dhv:username id="<%= ContactDetails.getModifiedBy() %>"/>
      <zeroio:tz timestamp="<%= ContactDetails.getModified()  %>" />
    </td>
   </tr>
  </table>
 </td>
</tr>
</table><br>
<dhv:permission name="contacts-external_contacts-imports-edit">
<dhv:evaluate if="<%= !ContactDetails.isApproved()  %>">
<input type="button" value="Delete" onClick="javascript:confirmDeleteContact();">
</dhv:evaluate>
</dhv:permission>

