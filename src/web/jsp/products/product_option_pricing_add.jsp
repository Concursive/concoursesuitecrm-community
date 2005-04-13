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
<%@ page import="java.util.*, org.aspcfs.modules.products.base.*" %>
<jsp:useBean id="PermissionCategory" class="org.aspcfs.modules.admin.base.PermissionCategory" scope="request"/>
<jsp:useBean id="ProductOption" class="org.aspcfs.modules.products.base.ProductOption" scope="request"/>
<jsp:useBean id="Pricing" class="org.aspcfs.modules.products.base.ProductOptionValues" scope="request"/>
<jsp:useBean id="Result" class="org.aspcfs.utils.web.HtmlSelect" scope="request"/>
<jsp:useBean id="nextRangeMin" class="java.lang.String" scope="request"/>
<%@ include file="../initPage.jsp" %>
<form name="addPricing" action="ProductOptionPricings.do?command=Insert&auto-populate=true" method="post">
<%-- Trails --%>
<table class="trails" cellspacing="0">
	<tr>
		<td>
			<a href="Admin.do"><dhv:label name="trails.admin">Admin</dhv:label></a> >
			<a href="Admin.do?command=Config"><dhv:label name="trails.configureModules">Configure Modules</dhv:label></a> >
			<a href="Admin.do?command=ConfigDetails&moduleId=<%= PermissionCategory.getId() %>"><%= toHtml(PermissionCategory.getCategory()) %></a> >
			<a href="ProductCatalogEditor.do?command=Options&moduleId=<%= PermissionCategory.getId() %>"><dhv:label name="product.editor">Editor</dhv:label></a> >
			<a href="ProductOptions.do?command=SearchForm&moduleId=<%= PermissionCategory.getId() %>"><dhv:label name="product.searchOptions">Search Options</dhv:label></a> >
      <a href="ProductOptions.do?command=Search&moduleId=<%= PermissionCategory.getId() %>"><dhv:label name="accounts.SearchResults">Search Results</dhv:label></a> >
      <a href="ProductOptions.do?command=Details&moduleId=<%= PermissionCategory.getId() %>&optionId=<%= ProductOption.getId() %>"><dhv:label name="product.optionDetails">Option Details</dhv:label></a> >
      <a href="ProductOptionPricings.do?command=List&moduleId=<%= PermissionCategory.getId() %>&optionId=<%= ProductOption.getId() %>"><dhv:label name="product.pricings">Pricings</dhv:label></a> >
      <dhv:label name="product.addPrice">Add Price</dhv:label>
  	</td>
	</tr>
</table>
<%-- End Trails --%>
<%@ include file="product_option_header_include.jsp" %>
<% String param1 = "optionId=" + ProductOption.getId(); %>
<% String param2 = "moduleId=" + PermissionCategory.getId(); %>
<dhv:container name="productoptions" selected="values" object="ProductOption" param="<%= param1 + "|" + param2 %>">
  <input type="hidden" name="moduleId" value="<%= toHtmlValue(PermissionCategory.getId()) %>"/>
  <input type="hidden" name="optionId" value="<%= ProductOption.getId() %>"/>
  <input type="submit" value="<dhv:label name="button.insert">Insert</dhv:label>" name="Save" onClick="this.form.dosubmit.value='true';">
  <input type="submit" value="<dhv:label name="button.cancel">Cancel</dhv:label>" onClick="javascript:this.form.action='ProductOptionPricings.do?command=List&moduleId=<%= PermissionCategory.getId() %>&optionId=<%= ProductOption.getId() %>';this.form.dosubmit.value='false';">
  <br><br>
  <table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
    <tr>
      <th colspan="2">
        <dhv:evaluate if="<%= ProductOption.getHasMultiplier() %>">
          <strong><dhv:label name="product.optionMultiplier">Product Option Multiplier</dhv:label></strong>
        </dhv:evaluate>
        <dhv:evaluate if="<%= !ProductOption.getHasMultiplier() %>">
          <strong><dhv:label name="product.productOptionPricings">Product Option Pricings</dhv:label></strong>
        </dhv:evaluate>
      </th>
    </tr>
    <tr class="containerBody">
      <td nowrap class="formLabel" valign="top">
        <dhv:label name="accounts.accounts_calls_list.Result">Result</dhv:label>
      </td>
      <td><%= Result.getHtml("resultId", Pricing.getResultId()) %></td>
    </tr>
    <tr class="containerBody">
      <td class="formLabel" valign="top">
        <dhv:label name="accounts.accountasset_include.Description">Description</dhv:label>
      </td>
      <td>
        <textarea rows="2" cols="25" name="description"><%= toHtml(Pricing.getDescription()).trim() %></textarea>
      </td>
    </tr>
    <dhv:evaluate if="<%= ProductOption.getHasRange() %>">
      <tr class="containerBody">
        <td class="formLabel">
          <dhv:label name="product.rangeMin">Range Min</dhv:label>
        </td>
        <td>
          <input type="text" size="10" maxlength="10" value="<%= nextRangeMin %>" disabled>
          <input type="hidden" name="rangeMin" value="<%= nextRangeMin %>"/>
        </td>
      </tr>
      <tr class="containerBody">
        <td class="formLabel">
          <dhv:label name="product.rangeMax">Range Max</dhv:label>
        </td>
        <td>
          <input type="text" size="10" maxlength="10" name="rangeMax" value="">
        </td>
      </tr>
    </dhv:evaluate>
    <dhv:evaluate if="<%= !ProductOption.getHasMultiplier() %>">
      <tr class="containerBody">
        <td class="formLabel">
          <dhv:label name="product.msrpAmount">MSRP Amount</dhv:label>
        </td>
        <td>
          <input type="text" size="10" maxlength="10" name="msrpAmount" value="<%= Pricing.getMsrpAmount() %>">
        </td>
      </tr>
      <tr class="containerBody">
        <td class="formLabel">
          <dhv:label name="product.priceAmount">Price Amount</dhv:label>
        </td>
        <td>
          <input type="text" size="10" maxlength="10" name="priceAmount" value="<%= Pricing.getPriceAmount() %>">
        </td>
      </tr>
    </dhv:evaluate>
    <dhv:evaluate if="<%= ProductOption.getHasMultiplier() %>">
      <tr class="containerBody">
        <td class="formLabel">
          <dhv:label name="product.value">Value</dhv:label>
        </td>
        <td>
          <input type="text" size="10" maxlength="10" name="value" value="<%= Pricing.getValue() %>">
        </td>
      </tr>
      <tr class="containerBody">
        <td class="formLabel">
          <dhv:label name="product.multiplier">Multiplier</dhv:label>
        </td>
        <td>
          <input type="text" size="10" maxlength="10" name="multiplier" value="<%= Pricing.getMultiplier() %>">
        </td>
      </tr>
    </dhv:evaluate>
  </table>
  &nbsp;<br>
  <input type="submit" value="<dhv:label name="button.insert">Insert</dhv:label>" name="Save" onClick="this.form.dosubmit.value='true';">
  <input type="submit" value="<dhv:label name="button.cancel">Cancel</dhv:label>" onClick="javascript:this.form.action='ProductOptionPricings.do?command=List&moduleId=<%= PermissionCategory.getId() %>&optionId=<%= ProductOption.getId() %>';this.form.dosubmit.value='false';">
</dhv:container>
</form>


