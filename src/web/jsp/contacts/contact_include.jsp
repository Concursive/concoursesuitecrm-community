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
<%-- reusable contact form --%>
<jsp:useBean id="ContactPhoneTypeList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="ContactEmailTypeList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="ContactAddressTypeList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="ContactTextMessageAddressTypeList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="ContactInstantMessageAddressTypeList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="ContactInstantMessageAddressServiceList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="StateSelect" class="org.aspcfs.utils.web.StateSelect" scope="request"/>
<jsp:useBean id="CountrySelect" class="org.aspcfs.utils.web.CountrySelect" scope="request"/>
<jsp:useBean id="applicationPrefs" class="org.aspcfs.controller.ApplicationPrefs" scope="application"/>
<jsp:useBean id="systemStatus" class="org.aspcfs.controller.SystemStatus" scope="request"/>
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
      <input type="radio" name="primaryNumber" value="<%= icount %>" <%= (thisPhoneNumber.getPrimaryNumber()) ? " checked" : "" %>><dhv:label name="contact.primary">Primary</dhv:label>
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
      <input type="radio" name="primaryNumber" value="<%= icount %>"><dhv:label name="contact.primary">Primary</dhv:label>
    </td>
  </tr>
<%}%>
<%if (ContactDetails.getPhoneNumberList().size() >= 3) {
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
      <input type="radio" name="primaryNumber" value="<%= icount %>"><dhv:label name="contact.primary">Primary</dhv:label>
    </td>
  </tr>
<%}%>
</table>
&nbsp;<br />
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
      <input type="radio" name="primaryEmail" value="<%= ecount %>" <%= (thisEmailAddress.getPrimaryEmail()) ? " checked" : "" %>><dhv:label name="contact.primary">Primary</dhv:label>
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
      <input type="radio" name="primaryEmail" value="<%= ecount %>"><dhv:label name="contact.primary">Primary</dhv:label>
    </td>
  </tr>
<%
  }
%>
<%if (ContactDetails.getEmailAddressList().size() >= 3) {
    ++ecount;
%>
  <tr class="containerBody">
    <td class="formLabel">
      <dhv:label name="accounts.accounts_add.Email">Email</dhv:label> <%= ecount %>
    </td>
    <td>
      <%= ContactEmailTypeList.getHtmlSelect("email" + ecount + "type", ((ContactDetails.getEmailAddressTypeId(1)==-1)?1:ContactDetails.getEmailAddressTypeId(1))) %>
      <input type="text" size="40" name="email<%=ecount%>address" maxlength="255">
      <input type="radio" name="primaryEmail" value="<%= ecount %>"><dhv:label name="contact.primary">Primary</dhv:label>
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
      <input type="radio" name="primaryTextMessageAddress" value="<%= tcount %>" <%= (thisTextMessageAddress.getPrimaryTextMessageAddress()) ? " checked" : "" %>><dhv:label name="contact.primary">Primary</dhv:label>
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
      <input type="radio" name="primaryTextMessageAddress" value="<%= tcount %>"><dhv:label name="contact.primary">Primary</dhv:label>
    </td>
  </tr>
<%}%>
<%if (ContactDetails.getTextMessageAddressList().size() >= 3) {
    ++tcount;
%>
  <tr class="containerBody">
    <td class="formLabel">
      <dhv:label name="accounts.accounts_add.TextMessageAddress">Text Message Address</dhv:label> <%= tcount %>
    </td>
    <td>
      <%= ContactTextMessageAddressTypeList.getHtmlSelect("textmessage" + tcount + "type", ((ContactDetails.getTextMessageAddressTypeId(1)==-1)?1:ContactDetails.getTextMessageAddressTypeId(1))) %>
      <input type="text" size="40" name="textmessage<%=tcount%>address" maxlength="255">
      <input type="radio" name="primaryTextMessageAddress" value="<%= tcount %>"><dhv:label name="contact.primary">Primary</dhv:label>
    </td>
  </tr>
<%}%>
</table>
&nbsp;<br />
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
      <input type="radio" name="primaryAddress" value="<%=acount%>" <%= thisAddress.getPrimaryAddress() ? " checked" : ""%>><dhv:label name="contact.primary">Primary</dhv:label>
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
      <span name="state1<%= acount %>" ID="state1<%= acount %>" style="<%= ("UNITED STATES".equals(thisAddress.getCountry()) || "CANADA".equals(thisAddress.getCountry()))? "" : " display:none" %>">
        <%= StateSelect.getHtml("address" + acount + "state", thisAddress.getState()) %>
      </span>
      <%-- If selected country is not US/Canada use textfield --%>
      <span name="state2<%= acount %>" ID="state2<%= acount %>" style="<%= (!"UNITED STATES".equals(thisAddress.getCountry()) && !"CANADA".equals(thisAddress.getCountry())) ? "" : " display:none" %>">
        <input type="text" size="25" name="<%= "address" + acount + "otherState" %>"  value="<%= toHtmlValue(thisAddress.getState()) %>">
      </span>
      <% StateSelect = new StateSelect(systemStatus); %>
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
      <% CountrySelect.setJsEvent("onChange=\"javascript:update('address" + acount + "country', '" + acount + "');\""); %>
      <%= CountrySelect.getHtml("address" + acount + "country", thisAddress.getCountry()) %>
      <script type="text/javascript">
        update('address<%= acount %>country','<%= acount %>');
      </script>
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
      <input type="radio" name="primaryAddress" value="<%=acount%>"><dhv:label name="contact.primary">Primary</dhv:label>
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
        <%= StateSelect.getHtml("address" + acount + "state") %>
      </span>
      <%-- If selected country is not US/Canada use textfield --%>
      <span name="state2<%= acount %>" ID="state2<%= acount %>" style="display:none">
        <input type="text" size="25" name="<%= "address" + acount + "otherState" %>">
      </span>
      <% StateSelect = new StateSelect(systemStatus); %>
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
      <% CountrySelect.setJsEvent("onChange=\"javascript:update('address" + acount + "country', '" + acount + "');\""); %>
      <%= CountrySelect.getHtml("address" + acount + "country",applicationPrefs.get("SYSTEM.COUNTRY")) %>
      <script type="text/javascript">
        update('address<%= acount %>country','<%= acount %>');
      </script>
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
<%}
  if (ContactDetails.getAddressList().size() >= 2) {
    ++acount;
%>
  <tr class="containerBody">
    <td class="formLabel">
      <dhv:label name="accounts.accounts_add.Type">Type</dhv:label>
    </td>
    <td>
      <%= ContactAddressTypeList.getHtmlSelect("address" + acount + "type", "") %>
      <input type="radio" name="primaryAddress" value="<%=acount%>"><dhv:label name="contact.primary">Primary</dhv:label>
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
        <%= StateSelect.getHtml("address" + acount + "state") %>
      </span>
      <%-- If selected country is not US/Canada use textfield --%>
      <span name="state2<%= acount %>" ID="state2<%= acount %>" style="display:none">
        <input type="text" size="25" name="<%= "address" + acount + "otherState" %>">
      </span>
      <% StateSelect = new StateSelect(systemStatus); %>
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
      <% CountrySelect.setJsEvent("onChange=\"javascript:update('address" + acount + "country', '" + acount + "');\""); %>
      <%= CountrySelect.getHtml("address" + acount + "country",applicationPrefs.get("SYSTEM.COUNTRY")) %>
      <script type="text/javascript">
        update('address<%= acount %>country','<%= acount %>');
      </script>
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
&nbsp;<br>
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="2">
      <strong><dhv:label name="accounts.accounts_add.AdditionalDetails">Additional Details</dhv:label></strong>
    </th>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="accounts.accountasset_include.Notes">Notes</dhv:label>
    </td>
    <td>
      <TEXTAREA NAME="notes" ROWS="3" COLS="50"><%= toString(ContactDetails.getNotes()) %></TEXTAREA>
    </td>
  </tr>
</table>
<input type="hidden" name="saveAndClone" value="false"/>
<input type="hidden" name="saveAndNew" value="false"/>
<input type="hidden" name="enabled" value="<%=ContactDetails.getEnabled()%>"/>
<%= addHiddenParams(request, "popup|popupType|actionId") %>


