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
<%@ page import="java.util.*, org.aspcfs.modules.products.base.*" %>
<jsp:useBean id="PermissionCategory" class="org.aspcfs.modules.admin.base.PermissionCategory" scope="request"/>
<jsp:useBean id="ProductCategory" class="org.aspcfs.modules.products.base.ProductCategory" scope="request"/>
<jsp:useBean id="ProductCatalog" class="org.aspcfs.modules.products.base.ProductCatalog" scope="request"/>
<%@ include file="../initPage.jsp" %>
<%-- Trails --%>
<table class="trails" cellspacing="0">
	<tr>
		<td>
			<a href="ProductCatalogEditor.do?command=List"><dhv:label name="product.products">Products</dhv:label></a> >
			<a href="ProductCatalogEditor.do?command=Options&moduleId=<%= PermissionCategory.getId() %>"><dhv:label name="product.editor">Editor</dhv:label></a> >
			<a href="ProductCategories.do?command=SearchForm&moduleId=<%= PermissionCategory.getId() %>"><dhv:label name="product.searchCategories">Search Categories</dhv:label></a> >
      <a href="ProductCategories.do?command=Search&moduleId=<%= PermissionCategory.getId() %>"><dhv:label name="accounts.SearchResults">Search Results</dhv:label></a> >
			<a href="ProductCategories.do?command=Details&moduleId=<%= PermissionCategory.getId() %>&categoryId=<%= ProductCategory.getId() %>"><dhv:label name="product.categoryDetails">Category Details</dhv:label></a> >
      <a href="ProductCategoryProducts.do?command=List&moduleId=<%= PermissionCategory.getId() %>&categoryId=<%= ProductCategory.getId() %>"><dhv:label name="product.products">Products</dhv:label></a> >
			<dhv:label name="product.productDetails">Product Details</dhv:label>
		</td>
	</tr>
</table>
<%-- End Trails --%>
<%@ include file="product_category_header_include.jsp" %>
<% String param1 = "categoryId=" + ProductCategory.getId(); %>
<% String param2 = "moduleId=" + PermissionCategory.getId(); %>
<dhv:container name="productcategories" selected="products" object="ProductCategory" param='<%= param1 + "|" + param2 %>'>
  <input type="button" value="<dhv:label name="button.cancel">Cancel</dhv:label>" onClick="javascript:window.location.href='ProductCategoryProducts.do?command=List&moduleId=<%= PermissionCategory.getId() %>&categoryId=<%= ProductCategory.getId() %>'"/>
  <br><br>
  <% boolean primaryInfoExists = false;%>
  <table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
    <tr>
      <th colspan="2">
        <strong><dhv:label name="accounts.accounts_details.PrimaryInformation">Primary Information</dhv:label></strong>
      </th>
    </tr>
  <dhv:evaluate if="<%= hasText(ProductCatalog.getParentName()) %>">
    <tr class="containerBody"><% primaryInfoExists = true; %>
      <td class="formLabel">
        <dhv:label name="product.parentCatalog">Parent Catalog</dhv:label>
      </td>
      <td><%= toHtml(ProductCatalog.getParentName()) %></td>
    </tr>
  </dhv:evaluate>
  <dhv:evaluate if="<%= hasText(ProductCatalog.getAbbreviation()) %>">
    <tr class="containerBody"><% primaryInfoExists = true; %>
      <td class="formLabel">
        <dhv:label name="literal.abbreviation.name">Abbreviation</dhv:label>
      </td>
      <td><%= toHtml(ProductCatalog.getAbbreviation()) %></td>
    </tr>
  </dhv:evaluate>
  <dhv:evaluate if="<%= hasText(ProductCatalog.getSku()) %>">
    <tr class="containerBody"><% primaryInfoExists = true; %>
      <td class="formLabel">
        <dhv:label name="quotes.sku">SKU</dhv:label>
      </td>
      <td><%= toHtml(ProductCatalog.getSku()) %></td>
    </tr>
  </dhv:evaluate>
  <dhv:evaluate if="<%= ProductCatalog.getStartDate() != null %>">
    <tr class="containerBody"><% primaryInfoExists = true; %>
      <td class="formLabel">
        <dhv:label name="documents.details.startDate">Start Date</dhv:label>
      </td>
      <td><zeroio:tz timestamp="<%= ProductCatalog.getStartDate() %>" /></td>
    </tr>
  </dhv:evaluate>
  <dhv:evaluate if="<%= ProductCatalog.getExpirationDate() != null %>">
    <tr class="containerBody"><% primaryInfoExists = true; %>
      <td class="formLabel">
        <dhv:label name="accounts.accountasset_include.ExpirationDate">Expiration Date</dhv:label>
      </td>
      <td><zeroio:tz timestamp="<%= ProductCatalog.getExpirationDate() %>" /></td>
    </tr>
  </dhv:evaluate>
  <dhv:evaluate if="<%= hasText(ProductCatalog.getTypeName()) %>">
    <tr class="containerBody"><% primaryInfoExists = true; %>
      <td class="formLabel">
        <dhv:label name="product.productType">Product Type</dhv:label>
      </td>
      <td><%= toHtml(ProductCatalog.getTypeName()) %></td>
    </tr>
  </dhv:evaluate>
  <dhv:evaluate if="<%= hasText(ProductCatalog.getShippingTimeName()) %>">
    <tr class="containerBody"><% primaryInfoExists = true; %>
      <td nowrap class="formLabel">
        <dhv:label name="product.productShipTime">Product Ship Time</dhv:label>
      </td>
      <td><%= toHtml(ProductCatalog.getShippingTimeName()) %></td>
    </tr>
  </dhv:evaluate>
  <dhv:evaluate if="<%= hasText(ProductCatalog.getShippingName()) %>">
    <tr class="containerBody"><% primaryInfoExists = true; %>
      <td class="formLabel">
        <dhv:label name="product.productShipping">Product Shipping</dhv:label>
      </td>
      <td><%= toHtml(ProductCatalog.getShippingName()) %></td>
    </tr>
  </dhv:evaluate>
  <dhv:evaluate if="<%= hasText(ProductCatalog.getFormatName()) %>">
    <tr class="containerBody"><% primaryInfoExists = true; %>
      <td class="formLabel">
        <dhv:label name="product.productFormat">Product Format</dhv:label>
      </td>
      <td><%= toHtml(ProductCatalog.getFormatName()) %></td>
    </tr>
  </dhv:evaluate>
  <dhv:evaluate if="<%= !primaryInfoExists %>">
    <tr class="containerBody">
      <td colspan="2">
        <font color="#9E9E9E"><dhv:label name="product.primaryInfoDoesNotExist">Primary Information does not exist</dhv:label></font>
      </td>
    </tr>
  </dhv:evaluate>
  </table>
  &nbsp;<br />
  <table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
    <tr>
      <th colspan="2">
        <strong><dhv:label name="accounts.accounts_add.AdditionalDetails">Additional Details</dhv:label></strong>
      </th>
    </tr>
    <tr class="containerBody">
      <td class="formLabel">
        <dhv:label name="documents.details.shortDescription">Short Description</dhv:label>
      </td>
      <td><%= toHtml(ProductCatalog.getShortDescription()) %></td>
    </tr>
    <tr class="containerBody">
      <td class="formLabel">
        <dhv:label name="documents.details.longDescription">Long Description</dhv:label>
      </td>
      <td><%= toHtml(ProductCatalog.getLongDescription()) %></td>
    </tr>
    <tr class="containerBody">
      <td class="formLabel">
        <dhv:label name="documents.details.specialNotes">Special Notes</dhv:label>
      </td>
      <td><%= toHtml(ProductCatalog.getSpecialNotes()) %></td>
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
      <td nowrap class="formLabel">
        <dhv:label name="accounts.accounts_calls_list.Entered">Entered</dhv:label>
      </td>
      <td>
        <dhv:username id="<%= ProductCatalog.getEnteredBy() %>" />
        <zeroio:tz timestamp="<%= ProductCatalog.getEntered() %>" />
      </td>
    </tr>
    <tr class="containerBody">
      <td nowrap class="formLabel">
        <dhv:label name="accounts.accounts_contacts_calls_details.Modified">Modified</dhv:label>
      </td>
      <td>
        <dhv:username id="<%= ProductCatalog.getModifiedBy() %>" />
        <zeroio:tz timestamp="<%= ProductCatalog.getModified() %>" />
      </td>
    </tr>
  </table>
  &nbsp;<br />
  <%--
  <input type="button" value="<dhv:label name="global.button.modify">Modify</dhv:label>" onClick="javascript:window.location.href='ProductCategoryProducts.do?command=Modify&catalogId=<%= ProductCatalog.getId() %>&moduleId=<%= PermissionCategory.getId() %>&categoryId=<%= ProductCategory.getId() %>'">
  <input type="button" value="<dhv:label name="button.delete">Delete</dhv:label>" onClick="javascript:popURLReturn('ProductCategoryProducts.do?command=ConfirmDelete&catalogId=<%=ProductCatalog.getId()%>&popup=true','ProductCategoryProducts.do?command=List&moduleId=<%=PermissionCategory.getId()%>&categoryId=<%= ProductCategory.getId() %>', 'Delete_category','320','200','yes','no');">
  --%>
  <input type="button" value="<dhv:label name="button.cancel">Cancel</dhv:label>" onClick="javascript:window.location.href='ProductCategoryProducts.do?command=List&moduleId=<%= PermissionCategory.getId() %>&categoryId=<%= ProductCategory.getId() %>'"/>
</dhv:container>