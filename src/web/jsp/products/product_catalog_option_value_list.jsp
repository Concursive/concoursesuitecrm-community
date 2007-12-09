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
<%@ page import="java.util.*, org.aspcfs.modules.products.base.*, org.aspcfs.utils.web.*" %>
<jsp:useBean id="PermissionCategory" class="org.aspcfs.modules.admin.base.PermissionCategory" scope="request"/>
<jsp:useBean id="ProductCatalog" class="org.aspcfs.modules.products.base.ProductCatalog" scope="request"/>
<jsp:useBean id="ProductOption" class="org.aspcfs.modules.products.base.ProductOption" scope="request"/>
<jsp:useBean id="PricingList" class="org.aspcfs.modules.products.base.ProductOptionValuesList" scope="request"/>
<jsp:useBean id="DisplayText" class="java.lang.String" scope="request"/>
<%@ include file="../initPage.jsp" %>
<script language="javascript">
  function addValue() {
    document.optionValue.action = 'ProductCatalogOptionPricings.do?command=AddValue';
    document.optionValue.submit();
  }
</script>
<form name="optionValue" method="post" action="ProductCatalogOptionPricings.do?command=AddMapping">
<strong><dhv:label name="product.result.colon">Result:</dhv:label> <%= toHtml(DisplayText) %></strong><br /><br />
<dhv:evaluate if="<%= PricingList.size() > 0 %>">
<table class="note" cellspacing="0">
  <tr>
    <th><img src="images/icons/stock_about-16.gif" border="0" align="absmiddle"/></th>
    <% String temp_displayText = "displayText=" + DisplayText; %>
    <td><dhv:label name="product.valueExistsFor.Text" param="<%= temp_displayText %>">The following values exist for "<%= toHtml(DisplayText) %>". Select an existing value or add a new value</dhv:label></td>
  </tr>
</table>
</dhv:evaluate>
<table cellspacing="0" cellpadding="4">
  <tr>
    <td><a href="javascript:addValue();"><dhv:label name="product.addNewValue">Add New Value</dhv:label></a></td>
  </tr>
</table>
&nbsp;<br>
<table cellpadding="4" cellspacing="0" width="100%" class="pagedList">
  <dhv:evaluate if="<%= ProductOption.getHasMultiplier() %>">
    <tr>
      <th width="8" style="text-align: center;"><dhv:label name="accounts.accounts_add.select">Select</dhv:label></th>
      <th><dhv:label name="product.value">Value</dhv:label></th>
      <th><dhv:label name="product.multiplier">Multiplier</dhv:label></th>
      <th><dhv:label name="accounts.accountasset_include.Description">Description</dhv:label></th>
    </tr>
  </dhv:evaluate>
  <dhv:evaluate if="<%= !ProductOption.getHasMultiplier() %>">
  <tr>
    <th width="8" style="text-align: center;"><dhv:label name="accounts.accounts_add.select">Select</dhv:label></th>
    <th><dhv:label name="product.msrpAmount">MSRP Amount</dhv:label></th>
    <th><dhv:label name="product.priceAmount">Price Amount</dhv:label></th>
    <th><dhv:label name="accounts.accountasset_include.Description">Description</dhv:label></th>
  </tr>
  </dhv:evaluate>
  <%
    Iterator j = PricingList.iterator();
    int rowid = 0;
    int i = 0;
    boolean exists = false;
    if (j.hasNext()) {
      while (j.hasNext()) {
        i++;
        rowid = (rowid != 1 ? 1 : 2);
        ProductOptionValues thisPricing = (ProductOptionValues) j.next();
    %>
    <dhv:evaluate if="<%= ProductOption.getHasMultiplier() %>">
    <tr class="containerBody">
      <td width="8" valign="center" nowrap class="row<%= rowid %>">
       <input type="radio" name="valueId" value="<%= thisPricing.getId() %>">
      </td>
      <td class="row<%= rowid %>"><%= thisPricing.getValue() %></td>
      <td class="row<%= rowid %>"><%= thisPricing.getMultiplier() %></td>
      <td class="row<%= rowid %>"><%= toHtml(thisPricing.getDescription()) %></td>
    </tr>
    </dhv:evaluate>
    <dhv:evaluate if="<%= !ProductOption.getHasMultiplier() %>">
    <tr class="containerBody">
      <td width="8" valign="center" nowrap class="row<%= rowid %>">
       <input type="radio" name="valueId" value="<%= thisPricing.getId() %>">
      </td>
      <td class="row<%= rowid %>"><%= thisPricing.getId() %></td>
      <%-- <td class="row<%= rowid %>"><%= toHtml(thisPricing.getMsrpCurrencyName()) %></td> --%>
      <td class="row<%= rowid %>"><%= thisPricing.getMsrpAmount() %></td>
      <%-- <td class="row<%= rowid %>"><%= toHtml(thisPricing.getPriceCurrencyName()) %></td> --%>
      <td class="row<%= rowid %>"><%= thisPricing.getPriceAmount() %></td>
      <td class="row<%= rowid %>"><%= toHtml(thisPricing.getDescription()) %></td>
    </tr>
    </dhv:evaluate>
<%
      }
    } else {
%>
    <tr class="containerBody">
      <td colspan="5"><dhv:label name="product.noValueExists">No value exists</dhv:label></td>
    </tr>
<%  }  %>
</table>
&nbsp;<br>
<input type="hidden" name="moduleId" value="<%= PermissionCategory.getId() %>"/>
<input type="hidden" name="catalogId" value="<%= ProductCatalog.getId() %>"/>
<input type="hidden" name="optionId" value="<%= ProductOption.getId() %>"/>
<input type="hidden" name="resultId" value="<%= (String) request.getAttribute("resultId") %>"/>
<input type="hidden" name="displayValue" value="<%= DisplayText %>"/>
<input type="submit" value="<dhv:label name="button.ok">OK</dhv:label>" />
<input type="button" value="<dhv:label name="button.cancel">Cancel</dhv:label>" onClick="javascript:self.close()"/>
