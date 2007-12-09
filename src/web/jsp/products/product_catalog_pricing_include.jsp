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
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<jsp:useBean id="TaxSelect" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="CurrencySelect" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="RecurringTypeSelect" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<jsp:useBean id="applicationPrefs" class="org.aspcfs.controller.ApplicationPrefs" scope="application"/>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/checkDate.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/submit.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popCalendar.js"></script>
<script language="JavaScript">
	function doCheck(form) {
		if (form.dosubmit.value == "false") {
			return true;
		} else {
			return (checkForm(form));
		}
	}
	
	function checkForm(form) {
		formTest = true;
		message = "";
		if (form.name.value == "") {
			message += label("check.name","- Please specify the  name\r\n");
			formTest = false;
		}
		
		if (formTest == false) {
			alert(label("check.form","Form could not be saved, please check the following:\r\n\r\n") + message);
			return false;
		} else {
			return true;
		}
	}
</script>
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="2">
      <dhv:evaluate if="<%= Pricing.getId() != -1 %>"><strong><dhv:label name="product.modifyPrice">Modify Price</dhv:label></strong></dhv:evaluate>
      <dhv:evaluate if="<%= Pricing.getId() == -1 %>"><strong><dhv:label name="product.addPrice">Add Price</dhv:label></strong></dhv:evaluate>
    </th>
  </tr>
  <tr class="containerBody">
    <td class="formLabel">
      <dhv:label name="product.msrp">MSRP</dhv:label>
    </td>
    <td>
      <%= applicationPrefs.get("SYSTEM.CURRENCY") %>
      <input type="text" name="msrpAmount" size="15" value="<zeroio:number value="<%= Pricing.getMsrpAmount() %>" locale="<%= User.getLocale() %>" />">
      <%= showAttribute(request, "msrpAmountError") %>
    </td>
  </tr>
  </tr>
  <tr class="containerBody">
    <td class="formLabel">
      <dhv:label name="quotes.Price">Price</dhv:label>
    </td>
    <td>
      <%= applicationPrefs.get("SYSTEM.CURRENCY") %>
      <input type="text" name="priceAmount" size="15" value="<zeroio:number value="<%= Pricing.getPriceAmount() %>" locale="<%= User.getLocale() %>" />">
      <%= showAttribute(request, "priceAmountError") %>
    </td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel">
      <dhv:label name="product.cost">Cost</dhv:label>
    </td>
    <td>
      <%= applicationPrefs.get("SYSTEM.CURRENCY") %>
      <input type="text" name="costAmount" size="15" value="<zeroio:number value="<%= Pricing.getCostAmount() %>" locale="<%= User.getLocale() %>" />">
      <%= showAttribute(request, "costAmountError") %>
    </td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel">
      <dhv:label name="product.recurringAmount">Recurring Amount</dhv:label>
    </td>
    <td>
      <%= applicationPrefs.get("SYSTEM.CURRENCY") %>
      <input type="text" name="recurringAmount" size="15" value="<zeroio:number value="<%= Pricing.getRecurringAmount() %>" locale="<%= User.getLocale() %>" />">
      <%= showAttribute(request, "recurringAmountError") %>
    </td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel" nowrap>
      <dhv:label name="product.recurringType">Recurring Type</dhv:label>
    </td>
    <td>
      <%= RecurringTypeSelect.getHtmlSelect("recurringType", Pricing.getRecurringType()) %>
    </td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel">
      <dhv:label name="product.taxType">Tax Type</dhv:label>
    </td>
    <td>
      <%= TaxSelect.getHtmlSelect("taxId", Pricing.getTaxId()) %>
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="documents.details.startDate">Start Date</dhv:label>
    </td>
    <td>
      <zeroio:dateSelect form="pricingForm" field="startDate" timestamp="<%= Pricing.getStartDate() %>"  timeZone="<%= User.getTimeZone() %>" showTimeZone="false" />
      <%= showAttribute(request, "startDateError") %>
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="accounts.accountasset_include.ExpirationDate">Expiration Date</dhv:label>
    </td>
    <td>
      <zeroio:dateSelect form="pricingForm" field="expirationDate" timestamp="<%= Pricing.getExpirationDate() %>"  timeZone="<%= User.getTimeZone() %>" showTimeZone="false" />
      <%= showAttribute(request, "expirationDateError") %>
    </td>
  </tr>
</table>
