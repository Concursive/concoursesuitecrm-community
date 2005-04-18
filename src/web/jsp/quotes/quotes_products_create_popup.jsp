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
<%@ page import="java.util.*, org.aspcfs.modules.quotes.base.*" %>
<jsp:useBean id="PermissionCategory" class="org.aspcfs.modules.admin.base.PermissionCategory" scope="request"/>
<jsp:useBean id="quoteProductBean" class="org.aspcfs.modules.quotes.base.QuoteProductBean" scope="request"/>
<jsp:useBean id="quote" class="org.aspcfs.modules.quotes.base.Quote" scope="request"/>
<jsp:useBean id="TaxSelect" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="CurrencySelect" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="RecurringTypeSelect" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<jsp:useBean id="applicationPrefs" class="org.aspcfs.controller.ApplicationPrefs" scope="application"/>
<%@ include file="../initPage.jsp" %>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/confirmDelete.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/checkNumber.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/checkDate.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/checkCurrency.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popCalendar.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/submit.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popProductCatalogs.js"></script> 
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popLookupSelect.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popURL.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/checkString.js"></script>
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
		if (checkNullString(form.name.value)) {
			message += label("check.quote.product.name","- Please specify the product name\r\n");
			formTest = false;
		}
    if (checkNullString(form.priceAmount.value)) {
      message += label("check.priceamount.blank","- The price can not be blank\r\n");
      flag = false;
    } else {
      if (!checkRealNumber(form.priceAmount.value)) {
        message += label("check.number.invalid","- Please enter a valid Number\r\n");
        flag = false;
      }
    }
    if (checkNullString(form.quantity.value)) {
      message += label("check.quote.quantity.blank","The quantity can not be blank.\r\n");
      flag = false;
    } else {
      if (!checkNumber(form.quantity.value)) {
        message += label("check.number.invalid","- Please enter a valid Number\r\n");
        flag = false;
      }
    }
		if (flag == false) {
        alert(label("check.form","Form could not be saved, please check the following:\r\n\r\n") + message);
	      return false;
		} else {
			return true;
		}
	}
</script>
<body onLoad="javascript:document.createCatalog.name.focus();">
<form name="createCatalog" action="QuotesProducts.do?command=Create&auto-populate=true" onSubmit="return doCheck(this);" method="post">
<input type="hidden" name="quoteId" value="<%= quote.getId() %>"/>
<input type="submit" value="<dhv:label name="button.createItem">Create Item</dhv:label>" name="Save" onClick="this.form.dosubmit.value='true';">
<input type="button" value="<dhv:label name="button.cancel">Cancel</dhv:label>" onClick="javascript:window.close();">
<br />
<%= showError(request, "actionError") %><iframe src="empty.html" name="server_commands" id="server_commands" style="visibility:hidden" height="0"></iframe>
<br />
<%-- Insert the quote product form here --%>
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="2">
      <strong><dhv:label name="quotes.quoteItemDetails">Quote Item Details</dhv:label></strong>
    </th>
  </tr>
	<tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="quotes.itemName">Item Name</dhv:label>
    </td>
		<td>
      <table border="0" cellpadding="0" cellspacing="0" class="empty"><tr><td valign="top">
        <input type="text" size="35" maxlength="255" name="name" value="<%= toHtmlValue(quoteProductBean.getName()) %>"><font color="red">*</font>
      </td><td valign="top" nowrap>&nbsp;
        <%= showAttribute(request, "nameError") %>
      </td></tr></table>
		</td>
	</tr>
	<tr class="containerBody">
		<td nowrap class="formLabel">
			<dhv:label name="literal.abbreviation.name">Abbreviation</dhv:label>
		</td>
		<td>
			<input type="text" size="10" maxlength="30" name="abbreviation" value="<%= toHtmlValue(quoteProductBean.getAbbreviation()) %>">
		</td>
	</tr>
	<tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="quotes.itemDescription">Item Description</dhv:label>
    </td>
		<td>
      <input type="text" size="35" maxlength="300" name="comment" value="<%= toHtmlValue(quoteProductBean.getComment()) %>">
		</td>
	</tr>
	<tr class="containerBody">
		<td nowrap class="formLabel">
      <dhv:label name="quotes.sku">SKU</dhv:label>
		</td>
		<td>
			<input type="text" size="15" maxlength="40" name="sku" value="<%= toHtmlValue(quoteProductBean.getSku()) %>">
		</td>
	</tr>
	<tr class="containerBody">
		<td nowrap class="formLabel">
      <dhv:label name="quotes.estimatedDelivery" param="break=&nbsp;">Estimated Delivery</dhv:label>			
		</td>
		<td>
			<input type="text" size="10" name="estimatedDelivery" id="estimatedDelivery" value="<%= toHtmlValue(quoteProductBean.getEstimatedDelivery()) %>">
      [<a href="javascript:popLookupSelectSingle('estimatedDelivery','<%= PermissionCategory.PERMISSION_CAT_PRODUCT_CATALOG %>','<%= PermissionCategory.LOOKUP_PRODUCT_SHIP_TIME %>');"><dhv:label name="accounts.accounts_add.select">Select</dhv:label></a>]
		</td>
	</tr>
</table>
<br />
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
	<tr>
    <th colspan="2">
	    <strong>
      <dhv:label name="quotes.priceDetails">Price Details</dhv:label>
      </strong>
	  </th>
  </tr>
	<tr class="containerBody">
  	<td nowrap class="formLabel">
      <dhv:label name="quotes.unitPrice">Unit Price</dhv:label>
    </td>
		<td>
      <%= applicationPrefs.get("SYSTEM.CURRENCY") %>
      <input type="text" name="priceAmount" size="15" value="<zeroio:number value="<%= quoteProductBean.getPriceAmount() %>" locale="<%= User.getLocale() %>" />">
      <%= showAttribute(request, "priceAmountError") %>
		</td>
	</tr>
	<tr class="containerBody">
  	<td nowrap class="formLabel">
      <dhv:label name="quotes.quantity">Quantity</dhv:label>
    </td>
		<td>
			<input type="text" size="10" maxlength="80" name="quantity" value="<%= quoteProductBean.getQuantity() %>">
      <%= showAttribute(request, "quantityError") %>
		</td>
	</tr>
</table>
<br />
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
	<tr>
    <th colspan="2">
	    <strong>
      <dhv:label name="accounts.accounts_add.AdditionalDetails">Additional Details</dhv:label></strong>
	  </th>
  </tr>
	<tr class="containerBody">
  	<td nowrap class="formLabel">
      <dhv:label name="documents.details.shortDescription">Short Description</dhv:label>
    </td>
		<td>
			<input type="text" size="35" maxlength="80" name="shortDescription" value="<%= toHtmlValue(quoteProductBean.getShortDescription()) %>">
		</td>
	</tr>
	<tr class="containerBody">
    <td valign="top" nowrap class="formLabel">
      <dhv:label name="documents.details.longDescription">Long Description</dhv:label></td>
    <td>
			<TEXTAREA NAME="longDescription" ROWS="3" COLS="50"><%= toString(quoteProductBean.getLongDescription()) %></TEXTAREA>
		</td>
  </tr>
	<tr class="containerBody">
    <td valign="top" nowrap class="formLabel">
      <dhv:label name="documents.details.specialNotes">Special Notes</dhv:label></td>
    <td>
			<TEXTAREA NAME="specialNotes" ROWS="3" COLS="50"><%= toString(quoteProductBean.getSpecialNotes()) %></TEXTAREA>
		</td>
  </tr>
</table>
<input type="hidden" name="onlyWarnings" value="on"/>
<%-- End of the quote product include--%>
<br />
<input type="submit" value="<dhv:label name="button.createItem">Create Item</dhv:label>" name="Save" onClick="this.form.dosubmit.value='true';">
<input type="button" value="<dhv:label name="button.cancel">Cancel</dhv:label>" onClick="javascript:window.close();">
<input type="hidden" name="dosubmit" value="true" />
</form>

