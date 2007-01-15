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
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<jsp:useBean id="applicationPrefs" class="org.aspcfs.controller.ApplicationPrefs" scope="application"/>
<%@ page import="java.util.*,java.text.DateFormat,org.aspcfs.modules.contacts.base.*" %>
<%@ include file="../initPage.jsp" %>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/confirmDelete.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popURL.js"></script>
<script language="JavaScript">
  function confirmDeleteContact() {
    url = 'SalesImports.do?command=DeleteContact&contactId=<%= ContactDetails.getId() %>&importId=<%= ImportDetails.getId() %>';
    confirmDelete(url);
  }
</script>
<table class="trails" cellspacing="0">
<tr>
  <td>
    <a href="Sales.do"><dhv:label name="Leads" mainMenuItem="true">Leads</dhv:label></a> >
    <a href="SalesImports.do?command=View"><dhv:label name="accounts.ViewImports">View Imports</dhv:label></a> >
    <a href="SalesImports.do?command=Details&importId=<%= ImportDetails.getId() %>"><dhv:label name="accounts.ImportDetails">Import Details</dhv:label></a> >
    <a href="SalesImports.do?command=ViewResults&importId=<%= ImportDetails.getId() %>"><dhv:label name="global.button.ViewResults">View Results</dhv:label></a> >
    <dhv:label name="sales.leadDetails">Lead Details</dhv:label>
  </td>
</tr>
</table>
<%-- End Trails --%>
<dhv:container name="contacts" selected="details" object="ContactDetails" hideContainer="true">
<dhv:formMessage showSpace="false" />
<dhv:permission name="leads-import-view">
  <dhv:evaluate if="<%= !ContactDetails.isApproved()  %>">
    <input type="button" value="<dhv:label name="global.button.delete">Delete</dhv:label>" onClick="javascript:confirmDeleteContact();"><br /><br />
  </dhv:evaluate>
</dhv:permission>
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="2">
      <dhv:label name="sales.leadDetails">Lead Details</dhv:label>
    </th>
  </tr>
  <tr class="containerBody">
    <td class="formLabel" nowrap>
      <dhv:label name="quotes.productName">Name</dhv:label>
    </td>
    <td>
      <%= toHtml(ContactDetails.getNameFull()) %>
    </td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel" nowrap>
      <dhv:label name="accounts.accounts_contacts_detailsimport.Company">Company</dhv:label>
    </td>
    <td>
      <%= toHtml(ContactDetails.getCompany()) %>
    </td>
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
      <dhv:label name="accounts.accounts_add.dateOfBirth">Birthday</dhv:label>
    </td>
    <td>
      <zeroio:tz timestamp="<%= ContactDetails.getBirthDate() %>" dateOnly="true"/>
    </td>
  </tr>
  </dhv:evaluate>
  <dhv:evaluate if="<%= hasText(ContactDetails.getTitle()) %>">
  <tr class="containerBody">
    <td nowrap class="formLabel">
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
<dhv:include name="organization.industry" none="true">
  <dhv:evaluate if="<%= hasText(ContactDetails.getIndustryName()) %>">
    <tr class="containerBody">
      <td nowrap class="formLabel">
        <dhv:label name="accounts.accounts_add.Industry">Industry</dhv:label>
      </td>
      <td>
         <%= toHtml(ContactDetails.getIndustryName()) %>&nbsp;
      </td>
    </tr>
  </dhv:evaluate>
</dhv:include>
<dhv:include name="organization.dunsType" none="true">
  <dhv:evaluate if="<%= hasText(ContactDetails.getDunsType()) %>">
    <tr class="containerBody"><td nowrap class="formLabel">
      <dhv:label name="accounts.accounts_add.duns_type">DUNS Type</dhv:label>
    </td><td>
       <%= toHtml(ContactDetails.getDunsType()) %>&nbsp;
    </td></tr>
  </dhv:evaluate>
</dhv:include>
<dhv:include name="organization.yearStarted" none="true">
  <dhv:evaluate if="<%= (ContactDetails.getYearStarted() > -1) %>">
    <tr class="containerBody"><td nowrap class="formLabel">
      <dhv:label name="accounts.accounts_add.year_started">Year Started</dhv:label>
    </td><td>
       <%= ContactDetails.getYearStarted() %>&nbsp;
    </td></tr>
  </dhv:evaluate>
</dhv:include>
<dhv:include name="organization.employees" none="true">
  <dhv:evaluate if="<%= (ContactDetails.getEmployees() > 0) %>">
    <tr class="containerBody">
      <td nowrap class="formLabel">
        <dhv:label name="organization.employees">No. of Employees</dhv:label>
      </td>
      <td>
         <%= ContactDetails.getEmployees() %>
      </td>
    </tr>
  </dhv:evaluate>
</dhv:include>
<dhv:include name="organization.potential" none="true">
  <dhv:evaluate if="<%= (ContactDetails.getPotential() > 0) %>">
  <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="accounts.accounts_add.potential">Potential</dhv:label>
    </td>
    <td>
       <zeroio:currency value="<%= ContactDetails.getPotential() %>" code='<%= applicationPrefs.get("SYSTEM.CURRENCY") %>' locale="<%= User.getLocale() %>" default="&nbsp;"/>
    </td>
  </tr>
  </dhv:evaluate>
</dhv:include>
<dhv:include name="organization.revenue" none="true">
  <dhv:evaluate if="<%= (ContactDetails.getRevenue() > 0) %>">
    <tr class="containerBody">
      <td nowrap class="formLabel">
        <dhv:label name="accounts.accounts_add.Revenue">Revenue</dhv:label>
      </td>
      <td>
         <zeroio:currency value="<%= ContactDetails.getRevenue() %>" code='<%= applicationPrefs.get("SYSTEM.CURRENCY") %>' locale="<%= User.getLocale() %>" default="&nbsp;"/>
      </td>
    </tr>
  </dhv:evaluate>
  </dhv:include>
<dhv:include name="organization.dunsNumber" none="true">
  <dhv:evaluate if="<%= hasText(ContactDetails.getDunsNumber()) %>">
    <tr class="containerBody"><td nowrap class="formLabel">
      <dhv:label name="accounts.accounts_add.duns_number">DUNS Number</dhv:label>
    </td><td>
       <%= toHtml(ContactDetails.getDunsNumber()) %>&nbsp;
    </td></tr>
  </dhv:evaluate>
</dhv:include>
<dhv:include name="organization.businessNameTwo" none="true">
  <dhv:evaluate if="<%= hasText(ContactDetails.getBusinessNameTwo()) %>">
    <tr class="containerBody"><td nowrap class="formLabel">
      <dhv:label name="accounts.accounts_add.business_name_two">Business Name 2</dhv:label>
    </td><td>
       <%= toHtml(ContactDetails.getBusinessNameTwo()) %>&nbsp;
    </td></tr>
  </dhv:evaluate>
</dhv:include>
<%--
<dhv:include name="organization.sicCode" none="true">
  <dhv:evaluate if="<%= (ContactDetails.getSicCode() > -1) %>">
    <tr class="containerBody"><td nowrap class="formLabel">
      <dhv:label name="accounts.accounts_add.sic_code">SIC</dhv:label>
    </td><td>
       <%= SICCodeList.getDescriptionByCode(ContactDetails.getSicCode()) %>&nbsp;
    </td></tr>
  </dhv:evaluate>
</dhv:include>
--%>
<dhv:include name="organization.sicDescription" none="true">
  <dhv:evaluate if="<%= hasText(ContactDetails.getSicDescription()) %>">
    <tr class="containerBody">
			<td nowrap class="formLabel">
      <dhv:label name="accounts.accounts_add.sicDescription">SIC Description</dhv:label>
			</td>
			<td>
         <%= toHtml(ContactDetails.getSicDescription()) %>&nbsp;
			</td>
		</tr>
  </dhv:evaluate>
</dhv:include>
</table>
<br />
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
        <%= toHtml(thisPhoneNumber.getPhoneNumber()) %>
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
<br />
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
        <a href="mailto:<%= toHtml(thisEmailAddress.getEmail()) %>"><%= toHtml(thisEmailAddress.getEmail()) %></a>
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
<br />
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
<br />
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
      <zeroio:tz timestamp="<%= ContactDetails.getEntered()  %>" />
    </td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel" nowrap>
      <dhv:label name="accounts.accounts_contacts_calls_details.Modified">Modified</dhv:label>
    </td>
    <td>
      <dhv:username id="<%= ContactDetails.getModifiedBy() %>"/>
      <zeroio:tz timestamp="<%= ContactDetails.getModified()  %>" />
    </td>
  </tr>
</table>
<br>
<dhv:permission name="sales-import-view">
  <dhv:evaluate if="<%= !ContactDetails.isApproved()  %>">
    <input type="button" value="<dhv:label name="global.button.delete">Delete</dhv:label>" onClick="javascript:confirmDeleteContact();">
  </dhv:evaluate>
</dhv:permission>
</dhv:container>
