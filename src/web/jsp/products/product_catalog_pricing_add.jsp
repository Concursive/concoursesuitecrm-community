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
<jsp:useBean id="permissionCategory" class="org.aspcfs.modules.admin.base.PermissionCategory" scope="request"/>
<jsp:useBean id="productCatalog" class="org.aspcfs.modules.products.base.ProductCatalog" scope="request"/>
<jsp:useBean id="productCategory" class="org.aspcfs.modules.products.base.ProductCategory" scope="request"/>
<jsp:useBean id="Pricing" class="org.aspcfs.modules.products.base.ProductCatalogPricing" scope="request"/>
<%@ include file="../initPage.jsp" %>
<body onLoad="javascript:document.pricingForm.msrpAmount.focus();">
<form name="pricingForm" action="ProductCatalogPricings.do?command=InsertPricing&auto-populate=true" onSubmit="return doCheck(this);" method="post">
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
<table border="0" cellpadding="1" cellspacing="0" width="100%">
  <tr class="abovetab">
    <td>
      <% String link = "ProductCatalogEditor.do?command=List&moduleId=" + permissionCategory.getId(); %>
      <dhv:productCategoryHierarchy link="<%= link %>" showLastLink="true"/> > 
      <a href="ProductCatalogs.do?command=Details&productId=<%= productCatalog.getId() %>&moduleId=<%= permissionCategory.getId() %>&categoryId=<%= productCategory.getId() %>"><dhv:label name="product.productDetails">Product Details</dhv:label></a> >
      <a href="ProductCatalogPricings.do?command=PricingList&productId=<%= productCatalog.getId() %>&moduleId=<%= permissionCategory.getId() %>&categoryId=<%= productCategory.getId() %>"><dhv:label name="product.price">Price</dhv:label></a> >
      <dhv:label name="product.addPrice">Add Price</dhv:label>
    </td>
  </tr>
</table>
<br />
<% String param1 = "productId=" + productCatalog.getId(); %>
<% String param2 = "moduleId=" + permissionCategory.getId(); %>
<% String param3 = "categoryId=" + productCategory.getId(); %>
<dhv:container name="productcatalogs" selected="price" object="productCatalog" param="<%= param1 + "|" + param2 + "|" + param3 %>">
<table cellpadding="4" cellspacing="0" border="0" width="100%">
  <tr>
    <td>
		 	<input type="submit" value="<dhv:label name="button.insert">Insert</dhv:label>" name="Save" onClick="this.form.dosubmit.value='true'"/>
			<input type="submit" value="<dhv:label name="button.cancel">Cancel</dhv:label>" onClick="javascript:this.form.action='ProductCatalogPricings.do?command=PricingList&productId=<%= productCatalog.getId() %>&moduleId=<%= permissionCategory.getId() %>'"/><br>
			<%= showError(request, "actionError") %>
		  <%@ include file="product_catalog_pricing_include.jsp" %>
			<br />
			<input type="hidden" name="productId" value="<%= productCatalog.getId() %>" />
			<input type="hidden" name="moduleId" value="<%= permissionCategory.getId() %>" />
      <input type="hidden" name="categoryId" value="<%= productCategory.getId() %>" />
			<input type="submit" value="<dhv:label name="button.insert">Insert</dhv:label>" name="Save" onClick="this.form.dosubmit.value='true'"/>
			<input type="submit" value="<dhv:label name="button.cancel">Cancel</dhv:label>" onClick="javascript:this.form.action='ProductCatalogPricings.do?command=PricingList&productId=<%= productCatalog.getId() %>&moduleId=<%= permissionCategory.getId() %>'"/>
			<input type="hidden" name="dosubmit" value="true"/>
		</td>
	</tr>
</table>
</dhv:container>
</form>
</body>