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
<jsp:useBean id="ProductCategory" class="org.aspcfs.modules.products.base.ProductCategory" scope="request"/>
<jsp:useBean id="ProductCatalog" class="org.aspcfs.modules.products.base.ProductCatalog" scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<%@ include file="../initPage.jsp" %>
<form name="addCatalog" action="ProductCategoryProducts.do?command=Update&auto-populate=true" onSubmit="return doCheck(this);" method="post">
<%-- Trails --%>
<table class="trails" cellspacing="0">
	<tr>
		<td>
			<a href="Admin.do"><dhv:label name="trails.admin">Admin</dhv:label></a> >
			<a href="Admin.do?command=Config"><dhv:label name="trails.configureModules">Configure Modules</dhv:label></a> >
			<a href="Admin.do?command=ConfigDetails&moduleId=<%= PermissionCategory.getId() %>"><%= toHtml(PermissionCategory.getCategory()) %></a> >
			<a href="ProductCatalogEditor.do?command=Options&moduleId=<%= PermissionCategory.getId() %>"><dhv:label name="product.editor">Editor</dhv:label></a> >
			<a href="ProductCategories.do?command=SearchForm&moduleId=<%= PermissionCategory.getId() %>"><dhv:label name="product.searchCategories">Search Categories</dhv:label></a> >
      <a href="ProductCategories.do?command=Search&moduleId=<%= PermissionCategory.getId() %>"><dhv:label name="accounts.SearchResults">Search Results</dhv:label></a> >
			<a href="ProductCategories.do?command=Details&moduleId=<%= PermissionCategory.getId() %>&categoryId=<%= ProductCategory.getId() %>"><dhv:label name="product.categoryDetails">Category Details</dhv:label></a> >
      <a href="ProductCategoryProducts.do?command=List&moduleId=<%= PermissionCategory.getId() %>&categoryId=<%= ProductCategory.getId() %>"><dhv:label name="product.products">Products</dhv:label></a> >
			<a href="ProductCategoryProducts.do?command=Details&moduleId=<%= PermissionCategory.getId() %>&categoryId=<%= ProductCategory.getId() %>&catalogId=<%= ProductCatalog.getId() %>"><dhv:label name="product.productDetails">Product Details</dhv:label></a> >
			<dhv:label name="product.modifyProduct">Modify Product</dhv:label>
		</td>
	</tr>
</table>
<%-- End Trails --%>
<%@ include file="product_category_header_include.jsp" %>
<% String param1 = "categoryId=" + ProductCategory.getId(); %>
<% String param2 = "moduleId=" + PermissionCategory.getId(); %>
<dhv:container name="productcategories" selected="products" object="ProductCategory" param="<%= param1 + "|" + param2 %>">
			<strong><%= toHtml(ProductCatalog.getName()) %></strong><br><br>
			<input type="hidden" name="moduleId" value="<%= toHtmlValue(PermissionCategory.getId()) %>"/>
			<input type="hidden" name="categoryId" value="<%= ProductCategory.getId() %>"/>
			<input type="hidden" name="catalogId" value="<%= ProductCatalog.getId() %>"/>
			<input type="hidden" name="id" value="<%= ProductCatalog.getId() %>"/>
			<input type="submit" value="<dhv:label name="button.update">Update</dhv:label>" name="Save" onClick="this.form.dosubmit.value='true';">
			<input type="submit" value="<dhv:label name="button.cancel">Cancel</dhv:label>" onClick="javascript:this.form.action='ProductCategoryProducts.do?command=Details&moduleId=<%= PermissionCategory.getId() %>&categoryId=<%= ProductCategory.getId() %>&catalogId=<%= ProductCatalog.getId() %>';this.form.dosubmit.value='false';">
			<br>
			<%= showError(request, "actionError") %><iframe src="empty.html" name="server_commands" id="server_commands" style="visibility:hidden" height="0"></iframe>
			<%@ include file="product_category_product_include.jsp" %>
			<input type="submit" value="<dhv:label name="button.update">Update</dhv:label>" name="Save" onClick="this.form.dosubmit.value='true';">
			<input type="submit" value="<dhv:label name="button.cancel">Cancel</dhv:label>" onClick="javascript:this.form.action='ProductCategoryProducts.do?command=Details&moduleId=<%= PermissionCategory.getId() %>&categoryId=<%= ProductCategory.getId() %>&catalogId=<%= ProductCatalog.getId() %>';this.form.dosubmit.value='false';">
			<input type="hidden" name="dosubmit" value="true" />
</dhv:container>
</form>