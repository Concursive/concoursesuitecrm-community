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
<jsp:useBean id="PermissionCategory" class="org.aspcfs.modules.admin.base.PermissionCategory" scope="request"/>
<%@ include file="../initPage.jsp" %>
<%-- Trails --%>
<table class="trails" cellspacing="0">
	<tr>
		<td>
			<a href="ProductCatalogEditor.do?command=List"><dhv:label name="product.products">Products</dhv:label></a> >
			<dhv:label name="product.editor">Editor</dhv:label>
		</td>
	</tr>
</table>
<%-- End Trails --%>
<dhv:label name="product.productEditorTitle">Review and manage the product catalog with the following options:</dhv:label><br />
<br />
<table cellpadding="4" cellspacing="0" width="100%" class="details">
  <tr>
    <th>
      <strong><dhv:label name="product.productsServices">Product and Services</dhv:label></strong>
    </th>
  </tr>
  <tr class="containerBody">
    <td>
      <li><a href="ProductCatalogs.do?command=SearchForm&moduleId=<%= PermissionCategory.getId() %>"><dhv:label name="product.reviewProducts">Review products and services in the catalog.</dhv:label></a></li>
      <li><a href="ProductCatalogs.do?command=Add&moduleId=<%= PermissionCategory.getId() %>"><dhv:label name="product.addProductService">Add a product or service to the catalog.</dhv:label></a></li>
    </td>
  </tr>
</table>
<br />
<table cellpadding="4" cellspacing="0" width="100%" class="details">
  <tr>
    <th>
      <strong><dhv:label name="product.productCategories">Product Categories</dhv:label></strong>
    </th>
  </tr>
  <tr class="containerBody">
    <td>
      <li><a href="ProductCategories.do?command=SearchForm&moduleId=<%= PermissionCategory.getId() %>"><dhv:label name="product.reviewProductCategories">Review product catalog categories.</dhv:label></a></li>
      <li><a href="ProductCategories.do?command=Add&moduleId=<%= PermissionCategory.getId() %>"><dhv:label name="product.addCategory">Add a category in the product catalog.</dhv:label></a></li>
    </td>
  </tr>
</table>
<%--
&nbsp;<br>
<table cellpadding="4" cellspacing="0" width="100%" class="details">
  <tr>
    <th>
      <strong><a href="ProductOptions.do?command=SearchForm&moduleId=<%= PermissionCategory.getId() %>">Product Options</a></strong>
    </th>
  </tr>
  <tr class="containerBody">
    <td>
      Create or edit existing product options.<br>
      &nbsp;<br>
      The Product Option configurator allows you to create new product options. 
			&nbsp;<br>
      Choose <a href="ProductOptions.do?command=Add&moduleId=<%= PermissionCategory.getId() %>">Add Product Option</a> to create a new product option.
    </td>
  </tr>
</table>
--%>
