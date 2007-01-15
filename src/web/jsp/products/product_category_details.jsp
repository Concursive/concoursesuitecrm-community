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
<jsp:useBean id="CategoryTypeList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="ProductCategory" class="org.aspcfs.modules.products.base.ProductCategory" scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<%@ include file="../initPage.jsp" %>
<%-- Trails --%>
<table class="trails" cellspacing="0">
	<tr>
		<td>
			<a href="ProductCatalogEditor.do?command=List"><dhv:label name="product.products">Products</dhv:label></a> >
			<a href="ProductCatalogEditor.do?command=Options&moduleId=<%= PermissionCategory.getId() %>"><dhv:label name="product.editor">Editor</dhv:label></a> >
			<a href="ProductCategories.do?command=SearchForm&moduleId=<%= PermissionCategory.getId() %>"><dhv:label name="product.searchCategories">Search Categories</dhv:label></a> >
      <a href="ProductCategories.do?command=Search&moduleId=<%= PermissionCategory.getId() %>"><dhv:label name="accounts.SearchResults">Search Results</dhv:label></a> >
			<dhv:label name="product.categoryDetails">Category Details</dhv:label>
		</td>
	</tr>
</table>
<%-- End Trails --%>
<%@ include file="product_category_header_include.jsp" %>
<% String param1 = "categoryId=" + ProductCategory.getId(); %>
<% String param2 = "moduleId=" + PermissionCategory.getId(); %>
<dhv:container name="productcategories" selected="details" object="ProductCategory" param='<%= param1 + "|" + param2 %>'>
      <input type="hidden" name="categoryId" value="<%= ProductCategory.getId() %>">
      <dhv:permission name="product-catalog-product-edit">
        <input type="button" value="<dhv:label name="global.button.modify">Modify</dhv:label>" onClick="javascript:window.location.href='ProductCategories.do?command=Modify&categoryId=<%= ProductCategory.getId() %>&moduleId=<%= PermissionCategory.getId() %>'">
      </dhv:permission>
			<%-- <input type="button" value="<dhv:label name="button.delete">Delete</dhv:label>" onClick="javascript:popURLReturn('ProductCategories.do?command=ConfirmDelete&categoryId=<%=ProductCategory.getId()%>&popup=true','ProductCategories.do?command=List&moduleId=<%=PermissionCategory.getId()%>', 'Delete_category','320','200','yes','no');"> --%>
			<input type="submit" value="<dhv:label name="button.cancel">Cancel</dhv:label>" onClick="javascript:window.location.href='ProductCategories.do?command=Search&moduleId=<%= PermissionCategory.getId() %>'"/>
			<br>&nbsp;
			<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
				<tr>
					<th colspan="2">
						<strong><dhv:label name="accounts.details.long_html">Details</dhv:label></strong>
					</th>
				</tr>
        <tr class="containerBody">
					<td class="formLabel">
						<dhv:label name="product.parentCategory">Parent Category</dhv:label>
					</td>
					<td>
            <dhv:evaluate if="<%= ProductCategory.getParentId() != -1 %>">
              <a href="ProductCategories.do?command=Details&moduleId=<%= PermissionCategory.getId() %>&categoryId=<%= ProductCategory.getParentId() %>"><%= toHtml(ProductCategory.getParentName()) %></a>
            </dhv:evaluate>
            <dhv:evaluate if="<%= ProductCategory.getParentId() == -1 %>">
              <%= toHtml(ProductCategory.getParentName()) %> &nbsp;
            </dhv:evaluate>
          </td>
				</tr>
      <dhv:evaluate if="<%= hasText(ProductCategory.getTypeName()) %>">	
        <tr class="containerBody">
					<td class="formLabel">
						<dhv:label name="product.catalogType">Catalog Type</dhv:label>
					</td>
					<td><%= toHtml(ProductCategory.getTypeName()) %></td>
				</tr>
      </dhv:evaluate>
      <dhv:evaluate if="<%= hasText(ProductCategory.getAbbreviation()) %>">
				<tr class="containerBody">
					<td class="formLabel">
						<dhv:label name="literal.abbreviation.name">Abbreviation</dhv:label>
					</td>
					<td><%= toHtml(ProductCategory.getAbbreviation()) %></td>
				</tr>
      </dhv:evaluate>
      <dhv:evaluate if="<%= ProductCategory.getStartDate() != null %>">
				<tr class="containerBody">
					<td class="formLabel">
						<dhv:label name="documents.details.startDate">Start Date</dhv:label>
					</td>
					<td><zeroio:tz timestamp="<%= ProductCategory.getStartDate() %>" /></td>
				</tr>
      </dhv:evaluate>
      <dhv:evaluate if="<%= ProductCategory.getExpirationDate() != null %>">
				<tr class="containerBody">
					<td class="formLabel">
						<dhv:label name="accounts.accountasset_include.ExpirationDate">Expiration Date</dhv:label>
					</td>
					<td><zeroio:tz timestamp="<%= ProductCategory.getExpirationDate() %>" /></td>
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
					<td><%= toHtml(ProductCategory.getShortDescription()) %></td>
				</tr>
				<tr class="containerBody">
					<td class="formLabel">
						<dhv:label name="documents.details.longDescription">Long Description</dhv:label>
					</td>
					<td><%= toHtml(ProductCategory.getLongDescription()) %></td>
				</tr>
			</table>
			&nbsp;<br />
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
						<dhv:username id="<%= ProductCategory.getEnteredBy() %>" />
						<zeroio:tz timestamp="<%= ProductCategory.getEntered() %>" />
					</td>
				</tr>
				<tr class="containerBody">
					<td nowrap class="formLabel">
						<dhv:label name="accounts.accounts_contacts_calls_details.Modified">Modified</dhv:label>
					</td>
					<td>
						<dhv:username id="<%= ProductCategory.getModifiedBy() %>" />
						<zeroio:tz timestamp="<%= ProductCategory.getModified() %>" />
					</td>
				</tr>
			</table>
			&nbsp;<br />
			<dhv:permission name="product-catalog-edit">
        <input type="button" value="<dhv:label name="global.button.modify">Modify</dhv:label>" onClick="javascript:window.location.href='ProductCategories.do?command=Modify&categoryId=<%= ProductCategory.getId() %>&moduleId=<%= PermissionCategory.getId() %>'">
      </dhv:permission>
			<%-- <input type="button" value="<dhv:label name="button.delete">Delete</dhv:label>" onClick="javascript:popURLReturn('ProductCategories.do?command=ConfirmDelete&categoryId=<%=ProductCategory.getId()%>&popup=true','ProductCategories.do?command=List&moduleId=<%=PermissionCategory.getId()%>', 'Delete_category','320','200','yes','no');"> --%>
			<input type="submit" value="<dhv:label name="button.cancel">Cancel</dhv:label>" onClick="javascript:window.location.href='ProductCategories.do?command=Search&moduleId=<%= PermissionCategory.getId() %>'"/>
</dhv:container>