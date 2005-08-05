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
<jsp:useBean id="CatalogTypeList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="CatalogFormatList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="CatalogShippingList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="CatalogShipTimeList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/checkDate.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/submit.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popProductCatalogs.js"></script> 
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popCalendar.js"></script>
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
		if (formTest == false) {
			alert(label("check.form","Form could not be saved, please check the following:\r\n\r\n") + message);
			return false;
		} else {
			return true;
		}
	}
  
  function clearParentProduct() {
    document.addCatalog.parentId.value = "-1";
    changeDivContent('changeparent', label("none.selected","None Selected"));
  }
  
  function setField(formField,thisValue,thisForm) {
    var frm = document.forms[thisForm];
    var len = document.forms[thisForm].elements.length;
    var i=0;
    for( i=0 ; i<len ; i++) {
      if (frm.elements[i].name == formField) {
        if(thisValue){
          frm.elements[i].value = "true";
        } else {
          frm.elements[i].value = "false";
        }
        break;
      }
    }
 }
</script>
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="2">
      <strong><dhv:label name="product.productDetails">Product Details</dhv:label></strong>
    </th>
  </tr>
  <%--
  <tr class="containerBody">
    <td nowrap class="formLabel" valign="top">
      <dhv:label name="product.parentProduct">Parent Product</dhv:label>
    </td>
    <td>
      <table border="0" cellspacing="0" cellpadding="0" class="empty">
        <tr>
          <td valign="top" nowrap>
            <div id="changeparent">
              <% if( ProductCatalog.getParentId() != -1) {%>
                <%= toHtml(ProductCatalog.getParentName()) %>
              <%} else {%>
                <dhv:label name="accounts.accounts_add.NoneSelected">None Selected</dhv:label>
              <%}%>
            </div>
          </td>
          <td valign="top" width="100%" nowrap>
            <input type="hidden" name="parentId" id="parentLink" value="<%= toHtmlValue(ProductCatalog.getParentId()) %>">
            &nbsp;[<a href="javascript:popProductCatalogsListSingle('parentLink', 'changeparent', 'filters=top&setParentList=true');"><dhv:label name="accounts.accounts_add.select">Select</dhv:label></a>]
            &nbsp;[<a href="javascript:clearParentProduct();"><dhv:label name="accounts.accountasset_include.clear">Clear</dhv:label></a>]
          </td>
        </tr>
      </table>
    </td>
  </tr>
  --%>
	<tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="products.productName">Product Name</dhv:label>
    </td>
		<td>
			<input type="text" size="35" maxlength="80" name="name" value="<%= toHtmlValue(productCatalog.getName()) %>"><font color="red">*</font> 
      <%= showAttribute(request, "nameError") %>
		</td>
	</tr>
  <%-- 
	<tr class="containerBody">
    <td nowrap class="formLabel" valign="top">
      Product Category
    </td>
    <td>
      <table border="0" cellspacing="0" cellpadding="0" class="empty">
        <tr>
          <td valign="top" nowrap>
            <div id="changecategory"><%= ProductCatalog.getCategoryId() == -1 ? "None Selected" : ProductCatalog.getCategoryName() %></div>
          </td>
          <td valign="top" width="100%" nowrap>
            &nbsp;[<a href="javascript:popProductCategoriesListSingle('categoryLink', 'changecategory', 'filters=top');">Select</a>]
						<input type="hidden" name="categoryId" id="categoryLink" value="<%= toHtmlValue(ProductCatalog.getCategoryId()) %>"
          </td>
        </tr>
      </table>
    </td>
  </tr>
  --%>
	<tr class="containerBody">
		<td nowrap class="formLabel">
			<dhv:label name="literal.abbreviation.name">Abbreviation</dhv:label>
		</td>
		<td>
			<input type="text" size="10" maxlength="10" name="abbreviation" value="<%= toHtmlValue(productCatalog.getAbbreviation()) %>">
		</td>
	</tr>
	<tr class="containerBody">
		<td nowrap class="formLabel">
			<dhv:label name="products.SKU">SKU</dhv:label>
		</td>
		<td>
			<input type="text" size="15" maxlength="40" name="sku" value="<%= toHtmlValue(productCatalog.getSku()) %>">
		</td>
	</tr>
	<tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="product.productType">Product Type</dhv:label>
    </td>
    <td>
      <%= CatalogTypeList.getHtmlSelect("typeId", productCatalog.getTypeId()) %>
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="product.productShipTime">Product Ship Time</dhv:label>
    </td>
    <td>
      <%= CatalogShipTimeList.getHtmlSelect("estimatedShipTime", productCatalog.getEstimatedShipTime()) %>
    </td>
  </tr>
	<tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="product.productShipping">Product Shipping</dhv:label>
    </td>
    <td>
      <%= CatalogShippingList.getHtmlSelect("shippingId", productCatalog.getShippingId()) %>
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="product.productFormat">Product Format</dhv:label>
    </td>
    <td>
      <%= CatalogFormatList.getHtmlSelect("formatId", productCatalog.getFormatId()) %>
    </td>
  </tr>
</table>
<br>
<table cellpadding="4" cellspacing="0" width="100%" class="details">
  <tr>
    <th colspan="2">
      <strong><dhv:label name="product.option.availability">Availability</dhv:label></strong>
    </th>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="product.enabled">Enabled</dhv:label>
    </td>
    <td>
      <input type="checkbox" name="chk1" onclick="javascript:setField('active', document.addCatalog.chk1.checked, 'addCatalog');" <%= (productCatalog.getActive() ? "checked" : "")%>>
      <input type="hidden" name="active" value="<%= productCatalog.getActive() %>">
      <input type="hidden" name="enabled" value="true">
      <%= showWarningAttribute(request, "activeWarning") %>
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="product.startDate">Start Date</dhv:label>
    </td>
    <td>
      <zeroio:dateSelect form="addCatalog" field="startDate" timestamp="<%= productCatalog.getStartDate() %>"  timeZone="<%= User.getTimeZone() %>" showTimeZone="false" />
      <%= showAttribute(request, "startDateError") %>
    </td>
  </tr>
	<tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="product.expirationDate">Expiration Date</dhv:label>
    </td>
    <td>
      <zeroio:dateSelect form="addCatalog" field="expirationDate" timestamp="<%= productCatalog.getExpirationDate() %>"  timeZone="<%= User.getTimeZone() %>" showTimeZone="false" />
      <%= showAttribute(request, "expirationDateError") %>
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
			<input type="text" size="35" maxlength="80" name="shortDescription" value="<%= toHtmlValue(productCatalog.getShortDescription()) %>">
		</td>
	</tr>
	<tr class="containerBody">
    <td valign="top" nowrap class="formLabel"><dhv:label name="documents.details.longDescription">Long Description</dhv:label></td>
    <td>
			<TEXTAREA NAME="longDescription" ROWS="3" COLS="50"><%= toString(productCatalog.getLongDescription()) %></TEXTAREA>
		</td>
  </tr>
	<tr class="containerBody">
    <td valign="top" nowrap class="formLabel"><dhv:label name="documents.details.specialNotes">Special Notes</dhv:label></td>
    <td>
			<TEXTAREA NAME="specialNotes" ROWS="3" COLS="50"><%= toString(productCatalog.getSpecialNotes()) %></TEXTAREA>
		</td>
  </tr>
</table>
<input type="hidden" name="onlyWarnings" value="<%= (productCatalog.getOnlyWarnings()? "on" : "off") %>">
