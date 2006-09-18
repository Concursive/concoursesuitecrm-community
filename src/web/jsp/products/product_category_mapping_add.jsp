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
<jsp:useBean id="MappingList" class="org.aspcfs.modules.products.base.ProductCategoryList" scope="request"/>
<%@ include file="../initPage.jsp" %>
<form name="mappingForm" action="ProductCategories.do?command=InsertMapping" method="post">
<%-- Trails --%>
<table class="trails" cellspacing="0">
	<tr>
		<td>
			<a href="ProductCatalogEditor.do?command=List"><dhv:label name="product.products">Products</dhv:label></a> >
			<a href="ProductCatalogEditor.do?command=Options&moduleId=<%= PermissionCategory.getId() %>"><dhv:label name="product.editor">Editor</dhv:label></a> >
			<a href="ProductCategories.do?command=SearchForm&moduleId=<%= PermissionCategory.getId() %>"><dhv:label name="product.searchCategories">Search Categories</dhv:label></a> >
      <a href="ProductCategories.do?command=Search&moduleId=<%= PermissionCategory.getId() %>"><dhv:label name="accounts.SearchResults">Search Results</dhv:label></a> >
			<a href="ProductCategories.do?command=Details&moduleId=<%= PermissionCategory.getId() %>&categoryId=<%= ProductCategory.getId() %>"><dhv:label name="product.categoryDetails">Category Details</dhv:label></a> >
      <a href="ProductCategories.do?command=MappingList&moduleId=<%= PermissionCategory.getId() %>&categoryId=<%= ProductCategory.getId() %>"><dhv:label name="product.mappings">Mappings</dhv:label></a> >
			<dhv:label name="product.addCategoryLink">Add Category Link</dhv:label>
		</td>
	</tr>
</table>
<%-- End Trails --%>
<%@ include file="product_category_header_include.jsp" %>
<% String param1 = "categoryId=" + ProductCategory.getId(); %>
<% String param2 = "moduleId=" + PermissionCategory.getId(); %>
<dhv:container name="productcategories" selected="categorylinks" object="ProductCategory" param="<%= param1 + "|" + param2 %>">
  <table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
    <tr>
      <th colspan="2">
        <strong><dhv:label name="product.addCategoryLink">Add Category Link</dhv:label></strong>
      </th>
    </tr>
    <tr class="containerBody">
      <td class="formLabel">
        <dhv:label name="accounts.accountasset_include.Category">Category</dhv:label>
      </td>
      <td>
        <%= MappingList.getHtmlSelect("mapcategory", -1) %>
      </td>
    </tr>
  </table>
  <br />
  <input type="hidden" name="categoryId" value="<%= ProductCategory.getId() %>" />
  <input type="hidden" name="moduleId" value="<%= PermissionCategory.getId() %>" />
  <input type="submit" value="<dhv:label name="button.createMapping">Create Mapping</dhv:label>"/>
  <input type="submit" value="<dhv:label name="button.cancel">Cancel</dhv:label>" onClick="javascript:this.form.action='ProductCategories.do?command=MappingList&categoryId=<%= ProductCategory.getId() %>&moduleId=<%= PermissionCategory.getId() %>'"/>
</dhv:container>
</form>
