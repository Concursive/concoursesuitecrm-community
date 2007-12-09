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
<jsp:useBean id="CatalogTypeList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="CatalogFormatList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="CatalogShippingList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="CatalogShipTimeList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/checkDate.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/submit.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popProductCategories.js"></script> 
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
			message += label("check.quote.product.name","- Please specify the product name\r\n");
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
      <strong><dhv:label name="product.productDetails">Product Details</dhv:label></strong>
    </th>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel" valign="top">
      <dhv:label name="product.parentProduct">Parent Product</dhv:label>
    </td>
    <td>
      <table border="0" cellspacing="0" cellpadding="0" class="empty">
        <tr>
          <td valign="top" nowrap>
            <div id="changeparent">
              <% if(ProductCatalog.getParentId() != -1) {%>
                <%= toHtml(ProductCatalog.getParentName()) %>
              <%} else {%>
                <dhv:label name="accounts.accounts_add.NoneSelected">None Selected</dhv:label>
              <%}%>
            </div>
          </td>
          <td valign="top" width="100%" nowrap>
            &nbsp;[<a href="javascript:popProductCategoriesListSingle('parentLink', 'changeparent', 'filters=top');"><dhv:label name="accounts.accounts_add.select">Select</dhv:label></a>]
						<input type="hidden" name="parentId" id="parentLink" value="<%= toHtmlValue(ProductCatalog.getParentId()) %>"
          </td>
        </tr>
      </table>
    </td>
  </tr>
	<tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="products.productName">Product Name</dhv:label>
    </td>
		<td>
			<input type="text" size="35" maxlength="80" name="name" value="<%= toHtmlValue(ProductCatalog.getName()) %>"><font color="red">*</font> <%= showAttribute(request, "nameError") %>
		</td>
	</tr>
	<tr class="containerBody">
		<td nowrap class="formLabel">
			<dhv:label name="literal.abbreviation.name">Abbreviation</dhv:label>
		</td>
		<td>
			<input type="text" size="10" maxlength="10" name="abbreviation" value="<%= toHtmlValue(ProductCatalog.getAbbreviation()) %>">
		</td>
	</tr>
	<tr class="containerBody">
		<td nowrap class="formLabel">
			<dhv:label name="quotes.sku">SKU</dhv:label>
		</td>
		<td>
			<input type="text" size="15" maxlength="40" name="sku" value="<%= toHtmlValue(ProductCatalog.getSku()) %>">
		</td>
	</tr>
	<tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="documents.details.startDate">Start Date</dhv:label>
    </td>
    <td>
      <zeroio:dateSelect form="addCatalog" field="startDate" timestamp="<%= ProductCatalog.getStartDate() %>"  timeZone="<%= User.getTimeZone() %>" showTimeZone="false" />
    </td>
  </tr>
	<tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="accounts.accountasset_include.ExpirationDate">Expiration Date</dhv:label>
    </td>
    <td>
      <zeroio:dateSelect form="addCatalog" field="expirationDate" timestamp="<%= ProductCatalog.getExpirationDate() %>"  timeZone="<%= User.getTimeZone() %>" showTimeZone="false" />
    </td>
  </tr>
	<tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="product.productType">Product Type</dhv:label>
    </td>
    <td>
      <%= CatalogTypeList.getHtmlSelect("typeId", ProductCatalog.getTypeId()) %>
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="product.productShipTime">Product Ship Time</dhv:label>
    </td>
    <td>
      <%= CatalogShipTimeList.getHtmlSelect("estimatedShipTime", ProductCatalog.getEstimatedShipTime()) %>
    </td>
  </tr>
	<tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="product.productShipping">Product Shipping</dhv:label>
    </td>
    <td>
      <%= CatalogShippingList.getHtmlSelect("shippingId", ProductCatalog.getShippingId()) %>
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="product.productFormat">Product Format</dhv:label>
    </td>
    <td>
      <%= CatalogFormatList.getHtmlSelect("formatId", ProductCatalog.getFormatId()) %>
    </td>
  </tr>
</table>
<br>
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
	<tr>
    <th colspan="2">
	    <strong><dhv:label name="accounts.accounts_add.AdditionalDetails">Additional Details</dhv:label></strong>
	  </th>
  </tr>
	<tr class="containerBody">
  	<td nowrap class="formLabel">
      <dhv:label name="documents.details.shortDescription">Short Description</dhv:label>
    </td>
		<td>
			<input type="text" size="35" maxlength="80" name="shortDescription" value="<%= toHtmlValue(ProductCatalog.getShortDescription()) %>">
		</td>
	</tr>
	<tr class="containerBody">
    <td valign="top" nowrap class="formLabel"><dhv:label name="documents.details.longDescription">Long Description</dhv:label></td>
    <td>
			<TEXTAREA NAME="longDescription" ROWS="3" COLS="50"><%= toString(ProductCatalog.getLongDescription()) %></TEXTAREA>
		</td>
  </tr>
	<tr class="containerBody">
    <td valign="top" nowrap class="formLabel"><dhv:label name="documents.details.specialNotes">Special Notes</dhv:label></td>
    <td>
			<TEXTAREA NAME="specialNotes" ROWS="3" COLS="50"><%= toString(ProductCatalog.getSpecialNotes()) %></TEXTAREA>
		</td>
  </tr>
</table>
&nbsp;<br />
