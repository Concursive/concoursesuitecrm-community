<%-- 
  - Copyright(c) 2004 Concursive Corporation (http://www.concursive.com/) All
  - rights reserved. This material cannot be distributed without written
  - permission from Concursive Corporation. Permission to use, copy, and modify
  - this material for internal use is hereby granted, provided that the above
  - copyright notice and this permission notice appear in all copies. CONCURSIVE
  - CORPORATION MAKES NO REPRESENTATIONS AND EXTENDS NO WARRANTIES, EXPRESS OR
  - IMPLIED, WITH RESPECT TO THE SOFTWARE, INCLUDING, BUT NOT LIMITED TO, THE
  - IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR ANY PARTICULAR
  - PURPOSE, AND THE WARRANTY AGAINST INFRINGEMENT OF PATENTS OR OTHER
  - INTELLECTUAL PROPERTY RIGHTS. THE SOFTWARE IS PROVIDED "AS IS", AND IN NO
  - EVENT SHALL CONCURSIVE CORPORATION OR ANY OF ITS AFFILIATES BE LIABLE FOR
  - ANY DAMAGES, INCLUDING ANY LOST PROFITS OR OTHER INCIDENTAL OR CONSEQUENTIAL
  - DAMAGES RELATING TO THE SOFTWARE.
  - 
  - Version: $Id$
  - Description:
  --%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,org.aspcfs.modules.contacts.base.*, org.aspcfs.utils.web.* " %>
<jsp:useBean id="ContactDetails" class="org.aspcfs.modules.contacts.base.Contact" scope="request"/>
<jsp:useBean id="ContactPhoneTypeList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="ContactEmailTypeList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="ContactAddressTypeList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="ContactInstantMessageAddressTypeList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="ContactInstantMessageAddressServiceList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="ContactTextMessageAddressTypeList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="StateSelect" class="org.aspcfs.utils.web.StateSelect" scope="request"/>
<jsp:useBean id="CountrySelect" class="org.aspcfs.utils.web.CountrySelect" scope="request"/>
<jsp:useBean id="applicationPrefs" class="org.aspcfs.controller.ApplicationPrefs" scope="application"/>
<jsp:useBean id="systemStatus" class="org.aspcfs.controller.SystemStatus" scope="request"/>
<%@ include file="../initPage.jsp" %>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/checkEmail.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/checkPhone.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/spanDisplay.js"></script>
<html>
<head>
  <title><dhv:label name="campaign.thankYouForVisitingAddressUpdatePage">Thank you for visiting our address update page</dhv:label></title>
<script language="JavaScript">
  function doCheck(form) {
    if (form.dosubmit.value == "false") {
      return true;
    } else {
      return(checkForm(form));
    }
  }
  function checkForm(form) {
    formTest = true;
    message = "";
<%
    for (int i=1; i<=(ContactDetails.getPhoneNumberList().size()); i++) {
%>
		<dhv:evaluate if="<%=(i>1)%>">else </dhv:evaluate>if (!checkPhone(form.phone<%=i%>number.value)) {
			message += label("check.phone", "- At least one entered phone number is invalid.  Make sure there are no invalid characters and that you have entered the area code\r\n");
			formTest = false;
		}
<%
    }
    
    for (int i=1; i<=(ContactDetails.getEmailAddressList().size()); i++) {
%>
  <dhv:evaluate if="<%=(i>1)%>">else </dhv:evaluate>if (!checkEmail(form.email<%=i%>address.value)) {
      message += label("check.email", "- At least one entered email address is invalid.  Make sure there are no invalid characters\r\n");
      formTest = false;
    }
<%
    }

    if(ContactDetails.getOrgId() == -1){
%>

    if(document.addContact.contactcategory[0] && document.addContact.contactcategory[1].checked && document.addContact.orgId.value == '-1') {
       message += label("sure.select.account", "- Make sure you select an account.\r\n");
			 formTest = false;
    }
<%
  }
%>
  
    if (formTest == false) {
      alert(label("check.form", "Form could not be saved, please check the following:\r\n\r\n") + message);
      return false;
    } else {
      var test = document.addContact.selectedList;
      if (test != null) {
        return selectAllOptions(document.addContact.selectedList);
      }
    }
  }

  function update(countryObj, stateObj, selectedValue) {
    var country = document.forms['addContact'].elements[countryObj].value;
    var url = "ExternalContacts.do?command=States&country="+country+"&obj="+stateObj+"&selected="+selectedValue+"&form=addContact&stateObj=address"+stateObj+"state";
    window.frames['server_commands'].location.href=url;
  }

  function continueUpdateState(stateObj, showText) {
    if(showText == 'true'){
      hideSpan('state1' + stateObj);
      showSpan('state2' + stateObj);
    } else {
      hideSpan('state2' + stateObj);
      showSpan('state1' + stateObj);
    }
  }
</script>
</head>
<body>
<center>
<form name="addContact" action="ProcessAddressSurvey.do?command=Update&auto-populate=true" onSubmit="return doCheck(this);window.close()" method="post">
<dhv:include name="contact.secretword">
<table class="note" cellspacing="0">
  <tr>
    <th><img src="images/icons/stock_about-16.gif" border="0" align="absmiddle"/></th>
    <td><dhv:label name="contacts.secretWord.text">Please specify a secret word that will be displayed on all messages to you.</dhv:label></td>
  </tr>
</table>
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="2">
	    <strong><dhv:label name="contacts.add.secretWord">Secret Word</dhv:label></strong>
	  </th>
  </tr>
  <tr class="containerBody">
    <td class="formLabel">
      <dhv:label name="contacts.add.secretWord">Secret Word</dhv:label>
    </td>
    <td>
      <input type="text" size="40" name="secretWord" maxlength="255" value="<%= toHtmlValue(ContactDetails.getSecretWord()) %>">
    </td>
  </tr>
</table>
&nbsp;<br />
</dhv:include>
<table class="note" cellspacing="0">
<tr>
  <th>
    <img src="images/icons/stock_form-open-in-design-mode-16.gif" border="0" align="absmiddle" />
  </th>
  <td>
    <dhv:label name="accounts.accounts_add.addressRequestMessage">Address Update Form. You have recieved this request to update your contact information from Concourse Suite Community Edition.</dhv:label>
  </td>
</tr>
</table>
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="2">
	    <strong><dhv:label name="accounts.accounts_add.name">Name</dhv:label></strong>
	  </th>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="accounts.accounts_add.FirstName">First Name</dhv:label>
    </td>
    <td>
      <%= toHtmlValue(ContactDetails.getNameFirst()) %>&nbsp;
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
    <dhv:label name="accounts.accounts_add.MiddleName">Middle Name</dhv:label>
    </td>
    <td>
      <%= toHtmlValue(ContactDetails.getNameMiddle()) %>&nbsp;
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="accounts.accounts_add.LastName">Last Name</dhv:label>
    </td>
    <td>
      <%= toHtmlValue(ContactDetails.getNameLast()) %>&nbsp;
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="accounts.accounts_contacts_detailsimport.Company">Company</dhv:label>
    </td>
    <td>
      <%= toHtmlValue(ContactDetails.getCompany()) %>&nbsp;
    </td>
  </tr>
  <tr class="containerBody">
      <td nowrap class="formLabel">
        <dhv:label name="accounts.accounts_add.additionalNames">Additional Names</dhv:label>
      </td>
      <td>
        <input type="text" size="35" name="additionalNames" value="<%= toHtmlValue(ContactDetails.getAdditionalNames()) %>">
      </td>
    </tr>
    <tr class="containerBody">
      <td nowrap class="formLabel">
        <dhv:label name="accounts.accounts_add.nickname">Nickname</dhv:label>
      </td>
      <td>
        <input type="text" size="35" name="nickname" value="<%= toHtmlValue(ContactDetails.getNickname()) %>">
      </td>
    </tr>
    <tr class="containerBody">
      <td nowrap class="formLabel">
        <dhv:label name="accounts.accounts_contacts_add.Title">Title</dhv:label>
      </td>
      <td>
        <input type="text" size="35" name="title" value="<%= toHtmlValue(ContactDetails.getTitle()) %>">
      </td>
    </tr>
    <tr class="containerBody">
      <td nowrap class="formLabel">
        <dhv:label name="accounts.accounts_contacts_add.Role">Role</dhv:label>
      </td>
      <td>
        <input type="text" size="35" name="role" value="<%= toHtmlValue(ContactDetails.getRole()) %>">
      </td>
    </tr>
</table>
&nbsp;<br>
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="2">
	    <strong><dhv:label name="accounts.accounts_add.EmailAddresses">Email Addresses</dhv:label></strong>
	  </th>
  </tr>
<%
  int ecount = 0;
  int etotal = 3;
  Iterator enumber = ContactDetails.getEmailAddressList().iterator();
  while (enumber.hasNext()) {
    ++ecount;
    ContactEmailAddress thisEmailAddress = (ContactEmailAddress)enumber.next();
%>  
  <tr class="containerBody">
    <td class="formLabel">
      <dhv:label name="accounts.accounts_add.Email">Email</dhv:label> <%= ecount %>
    </td>
    <td>
      <%= ContactEmailTypeList.getHtmlSelect("email"+ecount+"type", thisEmailAddress.getType()) %>
      <input type="text" size="40" name="email<%= ecount %>address" maxlength="255" value="<%= toHtmlValue(thisEmailAddress.getEmail()) %>">
      <input type="radio" name="primaryEmail" value="<%= ecount %>" <%= (thisEmailAddress.getPrimaryEmail()) ? " checked" : "" %>>Primary
      <dhv:evaluate if="<%= thisEmailAddress.getId() > 0 %>">
        <input type="hidden" name="email<%= ecount %>id" value="<%= thisEmailAddress.getId() %>">
        <input type="checkbox" name="email<%= ecount %>delete" value="on"><dhv:label name="accounts.accounts_modify.MarkToRemove">mark to remove</dhv:label>
      </dhv:evaluate>
    </td>
  </tr>
<%    
  }
  while (ecount < etotal) {
    ++ecount;
%>
  <tr class="containerBody">
    <td class="formLabel">
      <dhv:label name="accounts.accounts_add.Email">Email</dhv:label> <%= ecount %>
    </td>
    <td>
      <%= ContactEmailTypeList.getHtmlSelect("email" + ecount + "type", ((ContactDetails.getEmailAddressTypeId(1)==-1)?1:ContactDetails.getEmailAddressTypeId(1))) %>
      <input type="text" size="40" name="email<%=ecount%>address" maxlength="255">
      <input type="radio" name="primaryEmail" value="<%= ecount %>">Primary
    </td>
  </tr>
<%
  }
%>
</table>
&nbsp;<br />
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="2">
	    <strong><dhv:label name="accounts.accounts_add.InstantMessageAddresses">Instant Message Addresses</dhv:label></strong>
	  </th>
  </tr>
<%
  int imcount = 0;
  int imtotal = 3;
  Iterator imnumber = ContactDetails.getInstantMessageAddressList().iterator();
  while (imnumber.hasNext()) {
    ++imcount;
    ContactInstantMessageAddress thisInstantMessageAddress = (ContactInstantMessageAddress)imnumber.next();
%>  
  <tr class="containerBody">
    <td class="formLabel" nowrap>
      <dhv:label name="accounts.accounts_add.InstantMessageAddress">IM Address</dhv:label> <%= imcount %>
    </td>
    <td nowrap>
      <%= ContactInstantMessageAddressTypeList.getHtmlSelect("instantmessage"+imcount+"type", thisInstantMessageAddress.getAddressIMType()) %>
      <%= ContactInstantMessageAddressServiceList.getHtmlSelect("instantmessage"+imcount+"service", thisInstantMessageAddress.getAddressIMService()) %>
      <input type="text" size="40" name="instantmessage<%= imcount %>address" maxlength="255" value="<%= toHtmlValue(thisInstantMessageAddress.getAddressIM()) %>">
      <input type="radio" name="primaryIM" value="<%= imcount %>" <%= (thisInstantMessageAddress.getPrimaryIM()) ? " checked" : "" %>><dhv:label name="contact.primary">Primary</dhv:label>
      <dhv:evaluate if="<%= thisInstantMessageAddress.getId() > 0 %>">
        <input type="hidden" name="instantmessage<%= imcount %>id" value="<%= thisInstantMessageAddress.getId() %>"><br />
        <input type="checkbox" name="instantmessage<%= imcount %>delete" value="on"><dhv:label name="accounts.accounts_modify.MarkToRemove">mark to remove</dhv:label>
      </dhv:evaluate>
    </td>
  </tr>
<%    
  }
  while (imcount < imtotal) {
    ++imcount;
%>
  <tr class="containerBody">
    <td class="formLabel" nowrap>
      <dhv:label name="accounts.accounts_add.InstantMessageAddress">IM Address</dhv:label> <%= imcount %>
    </td>
    <td>
      <%= ContactInstantMessageAddressTypeList.getHtmlSelect("instantmessage" + imcount + "type", ((ContactDetails.getInstantMessageAddressTypeId(1)==-1)?1:ContactDetails.getInstantMessageAddressTypeId(1))) %>
      <%= ContactInstantMessageAddressServiceList.getHtmlSelect("instantmessage" + imcount + "service", ((ContactDetails.getInstantMessageAddressServiceId(1)==-1)?1:ContactDetails.getInstantMessageAddressServiceId(1))) %>
      <input type="text" size="40" name="instantmessage<%=imcount%>address" maxlength="255">
      <input type="radio" name="primaryIM" value="<%= imcount %>"><dhv:label name="contact.primary">Primary</dhv:label>
    </td>
  </tr>
<%}%>
<%if (ContactDetails.getInstantMessageAddressList().size() >= 3) {
    ++imcount;
%>
  <tr class="containerBody">
    <td class="formLabel" nowrap>
      <dhv:label name="accounts.accounts_add.InstantMessageAddress">IM Address</dhv:label> <%= imcount %>
    </td>
    <td>
      <%= ContactInstantMessageAddressTypeList.getHtmlSelect("instantmessage" + imcount + "type", ((ContactDetails.getInstantMessageAddressTypeId(1)==-1)?1:ContactDetails.getInstantMessageAddressTypeId(1))) %>
      <%= ContactInstantMessageAddressServiceList.getHtmlSelect("instantmessage" + imcount + "service", ((ContactDetails.getInstantMessageAddressServiceId(1)==-1)?1:ContactDetails.getInstantMessageAddressServiceId(1))) %>
      <input type="text" size="40" name="instantmessage<%=imcount%>address" maxlength="255">
      <input type="radio" name="primaryIM" value="<%= imcount %>"><dhv:label name="contact.primary">Primary</dhv:label>
    </td>
  </tr>
<%}%>
</table>
&nbsp;<br />
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="2">
	    <strong><dhv:label name="accounts.accounts_add.TextMessageAddresses">Text Message Addresses</dhv:label></strong>
	  </th>
  </tr>
<%
  int tcount = 0;
  int ttotal = 3;
  Iterator tnumber = ContactDetails.getTextMessageAddressList().iterator();
  while (tnumber.hasNext()) {
    ++tcount;
    ContactTextMessageAddress thisTextMessageAddress = (ContactTextMessageAddress)tnumber.next();
%>  
  <tr class="containerBody">
    <td class="formLabel">
      <dhv:label name="accounts.accounts_add.TextMessageAddress">Text Message Address</dhv:label> <%= tcount %>
    </td>
    <td>
      <%= ContactTextMessageAddressTypeList.getHtmlSelect("textmessage"+tcount+"type", thisTextMessageAddress.getType()) %>
      <input type="text" size="40" name="textmessage<%= tcount %>address" maxlength="255" value="<%= toHtmlValue(thisTextMessageAddress.getTextMessageAddress()) %>">
      <input type="radio" name="primaryTextMessageAddress" value="<%= tcount %>" <%= (thisTextMessageAddress.getPrimaryTextMessageAddress()) ? " checked" : "" %>>Primary
      <dhv:evaluate if="<%= thisTextMessageAddress.getId() > 0 %>">
        <input type="hidden" name="textmessage<%= tcount %>id" value="<%= thisTextMessageAddress.getId() %>">
        <input type="checkbox" name="textmessage<%= tcount %>delete" value="on"><dhv:label name="accounts.accounts_modify.MarkToRemove">mark to remove</dhv:label>
      </dhv:evaluate>
    </td>
  </tr>
<%    
  }
  while (tcount < ttotal) {
    ++tcount;
%>
  <tr class="containerBody">
    <td class="formLabel">
      <dhv:label name="accounts.accounts_add.TextMessageAddress">Text Message Address</dhv:label> <%= tcount %>
    </td>
    <td>
      <%= ContactTextMessageAddressTypeList.getHtmlSelect("textmessage" + tcount + "type", ((ContactDetails.getTextMessageAddressTypeId(1)==-1)?1:ContactDetails.getTextMessageAddressTypeId(1))) %>
      <input type="text" size="40" name="textmessage<%=tcount%>address" maxlength="255">
      <input type="radio" name="primaryTextMessageAddress" value="<%= tcount %>">Primary
    </td>
  </tr>
<%
  }
%>
</table>
<div align="center" style="padding:3px;"><dhv:label name="contact.internationalNumbers.note">Note: All international phone numbers must be preceded by a "+" symbol.</dhv:label></div>
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="2">
	    <strong><dhv:label name="accounts.accounts_add.PhoneNumbers">Phone Numbers</dhv:label></strong>
	  </th>
  </tr>
<%  
  int icount = 0;
  int itotal = 3;
  Iterator inumber = ContactDetails.getPhoneNumberList().iterator();
  while (inumber.hasNext()) {
    ++icount;
    ContactPhoneNumber thisPhoneNumber = (ContactPhoneNumber)inumber.next();
%>    
  <tr class="containerBody">
    <td class="formLabel">
      <%= icount %>
    </td>
    <td>
      <%= ContactPhoneTypeList.getHtmlSelect("phone" + icount + "type", thisPhoneNumber.getType()) %>
      <input type="text" size="20" name="phone<%= icount %>number" value="<%= toHtmlValue(thisPhoneNumber.getNumber()) %>">&nbsp;<dhv:label name="accounts.accounts_add.ext">ext.</dhv:label>
      <input type="text" size="5" name="phone<%= icount %>ext" maxlength="10" value="<%= toHtmlValue(thisPhoneNumber.getExtension()) %>">
      <input type="radio" name="primaryNumber" value="<%= icount %>" <%= (thisPhoneNumber.getPrimaryNumber()) ? " checked" : "" %>>Primary
      <dhv:evaluate if="<%= thisPhoneNumber.getId() > 0 %>">
        <input type="hidden" name="phone<%= icount %>id" value="<%= thisPhoneNumber.getId() %>">
        <input type="checkbox" name="phone<%= icount %>delete" value="on"><dhv:label name="accounts.accounts_modify.MarkToRemove">mark to remove</dhv:label>
      </dhv:evaluate>
      
    </td>
  </tr>
<%
  }
  while (icount < itotal) {
    ++icount;
%>
  <tr class="containerBody">
    <td class="formLabel">
       <%= icount %>
    </td>
    <td>
      <%= ContactPhoneTypeList.getHtmlSelect("phone"+icount+"type", "") %>
      <input type="text" size="20" name="phone<%=icount%>number">&nbsp;<dhv:label name="accounts.accounts_add.ext">ext.</dhv:label>
      <input type="text" size="5" name="phone<%=icount%>ext" maxlength="10">
      <input type="radio" name="primaryNumber" value="<%= icount %>">Primary
    </td>
  </tr>
<%}%>  
</table>
&nbsp;<br>  
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="2">
      <strong><dhv:label name="accounts.accounts_add.Addresses">Addresses</dhv:label></strong>
    </th>
  </tr>
<%  
  int acount = 0;
  int atotal = 2;
  Iterator anumber = ContactDetails.getAddressList().iterator();
  while (anumber.hasNext()) {
    ++acount;
    ContactAddress thisAddress = (ContactAddress)anumber.next();
%>    
  <tr class="containerBody">
    <input type="hidden" name="address<%= acount %>id" value="<%= thisAddress.getId() %>">
    <td class="formLabel">
      <dhv:label name="accounts.accounts_add.Type">Type</dhv:label>
    </td>
    <td>
      <%= ContactAddressTypeList.getHtmlSelect("address" + acount + "type", thisAddress.getType()) %>
      <input type="radio" name="primaryAddress" value="<%=acount%>" <%= thisAddress.getPrimaryAddress() ? " checked" : ""%>>Primary
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="accounts.accounts_add.AddressLine1">Address Line 1</dhv:label>
    </td>
    <td>
      <input type="text" size="40" name="address<%= acount %>line1" maxlength="80" value="<%= toHtmlValue(thisAddress.getStreetAddressLine1()) %>">
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="accounts.accounts_add.AddressLine2">Address Line 2</dhv:label>
    </td>
    <td>
      <input type="text" size="40" name="address<%= acount %>line2" maxlength="80" value="<%= toHtmlValue(thisAddress.getStreetAddressLine2()) %>">
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="accounts.accounts_add.AddressLine3">Address Line 3</dhv:label>
    </td>
    <td>
      <input type="text" size="40" name="address<%= acount %>line3" maxlength="80" value="<%= toHtmlValue(thisAddress.getStreetAddressLine3()) %>">
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="accounts.accounts_add.City">City</dhv:label>
    </td>
    <td>
      <input type="text" size="28" name="address<%= acount %>city" maxlength="80" value="<%= toHtmlValue(thisAddress.getCity()) %>">
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="accounts.accounts_add.StateProvince">State/Province</dhv:label>
    </td>
    <td>
      <span name="state1<%= acount %>" ID="state1<%= acount %>" style="<%= StateSelect.hasCountry(thisAddress.getCountry())? "" : " display:none" %>">
        <%= StateSelect.getHtmlSelect("address" + acount + "state", thisAddress.getCountry(), thisAddress.getState()) %>
      </span>
      <%-- If selected country is not US/Canada use textfield --%>
      <span name="state2<%= acount %>" ID="state2<%= acount %>" style="<%= !StateSelect.hasCountry(thisAddress.getCountry()) ? "" : " display:none" %>">
        <input type="text" size="25" name="<%= "address" + acount + "otherState" %>"  value="<%= toHtmlValue(thisAddress.getState()) %>">
      </span>
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="accounts.accounts_add.ZipPostalCode">Zip/Postal Code</dhv:label>
    </td>
    <td>
      <input type="text" size="10" name="address<%= acount %>zip" maxlength="12" value="<%= toHtmlValue(thisAddress.getZip()) %>">
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="accounts.accounts_add.Country">Country</dhv:label>
    </td>
    <td>
      <% CountrySelect.setJsEvent("onChange=\"javascript:update('address" + acount + "country', '" + acount + "','"+thisAddress.getState()+"');\"");%>
      <%= CountrySelect.getHtml("address" + acount + "country", thisAddress.getCountry()) %>
      <% CountrySelect = new CountrySelect(systemStatus); %>
    </td>
  </tr>
  <tr class="containerBody">
    <td colspan="2">
      &nbsp;
    </td>
  </tr>
<%
  }
  while (acount < atotal) {
    ++acount;
%>
  
  <tr class="containerBody">
    <td class="formLabel">
      <dhv:label name="accounts.accounts_add.Type">Type</dhv:label>
    </td>
    <td>
      <%= ContactAddressTypeList.getHtmlSelect("address" + acount + "type", "") %>
      <input type="radio" name="primaryAddress" value="<%=acount%>">Primary
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="accounts.accounts_add.AddressLine1">Address Line 1</dhv:label>
    </td>
    <td>
      <input type="text" size="40" name="address<%= acount %>line1" maxlength="80">
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="accounts.accounts_add.AddressLine2">Address Line 2</dhv:label>
    </td>
    <td>
      <input type="text" size="40" name="address<%= acount %>line2" maxlength="80">
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="accounts.accounts_add.AddressLine3">Address Line 3</dhv:label>
    </td>
    <td>
      <input type="text" size="40" name="address<%= acount %>line3" maxlength="80">
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="accounts.accounts_add.City">City</dhv:label>
    </td>
    <td>
      <input type="text" size="28" name="address<%= acount %>city" maxlength="80">
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="accounts.accounts_add.StateProvince">State/Province</dhv:label>
    </td>
    <td>
      <span name="state1<%= acount %>" ID="state1<%= acount %>">
        <%= StateSelect.getHtmlSelect("address" + acount + "state", applicationPrefs.get("SYSTEM.COUNTRY"),"-1") %>
      </span>
      <%-- If selected country is not US/Canada use textfield --%>
      <span name="state2<%= acount %>" ID="state2<%= acount %>" style="display:none">
        <input type="text" size="25" name="<%= "address" + acount + "otherState" %>">
      </span>
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="accounts.accounts_add.ZipPostalCode">Zip/Postal Code</dhv:label>
    </td>
    <td>
      <input type="text" size="10" name="address<%= acount %>zip" maxlength="12">
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="accounts.accounts_add.Country">Country</dhv:label>
    </td>
    <td>
      <% CountrySelect.setJsEvent("onChange=\"javascript:update('address" + acount + "country', '" + acount + "','');\"");%>
      <%= CountrySelect.getHtml("address" + acount + "country",applicationPrefs.get("SYSTEM.COUNTRY")) %>
      <% CountrySelect = new CountrySelect(systemStatus); %>
    </td>
  </tr>
  <dhv:evaluate if="<%= acount != atotal %>">
  <tr class="containerBody">
    <td colspan="2">
      &nbsp;
    </td>
  </tr>
  </dhv:evaluate>
<%}%>  
</table>
<tr>
<td>
<input type="submit" value="<dhv:label name="global.button.update">Update</dhv:label>" /> 
<input type="button" value="<dhv:label name="button.cancel">Cancel</dhv:label>" onClick="window.opener=self;window.close();" />
<input type="hidden" name="dosubmit" value="true" />
<input type="hidden" name="enteredBy" value="<%=ContactDetails.getEnteredBy()%>" />
<input type="hidden" name="modifiedBy" value="<%=ContactDetails.getModifiedBy()%>" />
<input type="hidden" name="id" value="<%=request.getAttribute("id")%>" />
<input type="hidden" name="nameFirst" value="<%= toHtmlValue(ContactDetails.getNameFirst()) %>">
<input type="hidden" name="nameMiddle" value="<%= toHtmlValue(ContactDetails.getNameMiddle()) %>">
<input type="hidden" name="nameLast" value="<%= toHtmlValue(ContactDetails.getNameLast()) %>">
<input type="hidden" name="notes" value="<%= toString(ContactDetails.getNotes()) %>" />
</td>
</tr>
&nbsp;<br>
<iframe src="empty.html" name="server_commands" id="server_commands" style="visibility:hidden" height="0"></iframe>
</form>
</center>
 </body>
</html>
