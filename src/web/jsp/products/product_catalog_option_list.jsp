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
<jsp:useBean id="permissionCategory" class="org.aspcfs.modules.admin.base.PermissionCategory" scope="request"/>
<jsp:useBean id="productCatalog" class="org.aspcfs.modules.products.base.ProductCatalog" scope="request"/>
<jsp:useBean id="OptionList" class="org.aspcfs.modules.products.base.ProductOptionList" scope="request" />
<jsp:useBean id="CompleteList" class="org.aspcfs.modules.products.base.ProductOptionList" scope="request" />
<jsp:useBean id="ProductCatalogOptionListInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<jsp:useBean id="productCategory" class="org.aspcfs.modules.products.base.ProductCategory" scope="request"/>
<%@ include file="../initPage.jsp" %>
<%-- Initialize the drop-down menus --%>
<%@ include file="../initPopupMenu.jsp" %>
<%@ include file="product_catalog_option_list_menu.jsp" %> 
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/spanDisplay.js"></SCRIPT>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/confirmDelete.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popProductOptions.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popURL.js"></script>
<script language="JavaScript" type="text/javascript">
  <%-- Preload image rollovers for drop-down menu --%>
  loadImages('select');
</script>
<script>existingIds = new Array();</script>
<%
  Iterator k = CompleteList.iterator();
  int count = -1;
  while (k.hasNext()) {
    count++;
    ProductOption thisOption = (ProductOption) k.next();
%>
   <script>
    existingIds[<%= count %>] = "<%= thisOption.getId() %>";
   </script>
<%
  }
%>
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
<% String param1 = "productId=" + productCatalog.getId(); %>
<% String param2 = "moduleId=" + permissionCategory.getId(); %>
<% String param3 = "categoryId=" + productCategory.getId(); %>
<table border="0" cellpadding="1" cellspacing="0" width="100%">
  <tr class="abovetab">
    <td>
      <% String link = "ProductCatalogEditor.do?command=List&moduleId=" + permissionCategory.getId(); %>
      <dhv:productCategoryHierarchy link="<%= link %>" showLastLink="true"/> > 
      <a href="ProductCatalogs.do?command=Details&productId=<%= productCatalog.getId() %>&moduleId=<%= permissionCategory.getId() %>&categoryId=<%= productCategory.getId() %>"><dhv:label name="product.productDetails">Product Details</dhv:label></a> >
      <dhv:label name="product.options">Options</dhv:label>
    </td>
  </tr>
</table>
<br />
<dhv:container name="productcatalogs" selected="options" object="productCatalog" param='<%= param1 + "|" + param2 + "|" + param3 %>'>
<table cellpadding="4" cellspacing="0" border="0" width="100%">
  <tr>
    <td>
      <dhv:evaluate if="<%= !productCatalog.isTrashed() %>">
        <dhv:permission name="product-catalog-product-add">
          <a href="ProductCatalogOptions.do?command=Add&productId=<%= productCatalog.getId() %>&categoryId=<%= productCategory.getId() %>&moduleId=<%= permissionCategory.getId() %>"><dhv:label name="product.addProductOption">Add Product Option</dhv:label></a>
        </dhv:permission>
      </dhv:evaluate>
    <dhv:pagedListStatus title='<%= showError(request, "actionError") %>' object="ProductCatalogOptionListInfo"/>
		<% int columnCount = 0; %>
		<table cellpadding="4" cellspacing="0" border="0" width="100%" class="pagedList">
			<tr>
				<th width="8" <% ++columnCount; %>>
					&nbsp;
				</th>
				<th nowrap width="100%" <% ++columnCount; %>>
					<strong><dhv:label name="contacts.name">Name</dhv:label></strong>
				</th>
				<th nowrap <% ++columnCount; %>>
					<strong><dhv:label name="product.configurator.type">Type</dhv:label></strong>
				</th>
				<th nowrap <% ++columnCount; %>>
					<strong><dhv:label name="documents.details.startDate">Start Date</dhv:label></strong>
				</th>
				<th nowrap <% ++columnCount; %>>
					<strong><dhv:label name="accounts.accountasset_include.ExpirationDate">Expiration Date</dhv:label></strong>
				</th>
				<th nowrap <% ++columnCount; %>>
					<strong><dhv:label name="product.enabled">Enabled</dhv:label></strong>
				</th>
			</tr>
			<% 
				Iterator j = OptionList.iterator();
				if (j.hasNext()) {
					int rowid = 0;
					int i = 0;
					while (j.hasNext()) {
						i++;
						rowid = (rowid != 1 ? 1 : 2);
						ProductOption thisOption = (ProductOption) j.next();
			%>
					<tr class="containerBody">
						<td width="8" valign="center" nowrap class="row<%= rowid %>">
							<% int status = -1; %>
							<% status = thisOption.getEnabled() ? 1 : 0; %>
							<a href="javascript:displayMenu('select<%= i %>', 'menuOption', '<%= thisOption.getId() %>', '<%= thisOption.getConfiguratorId() %>', '<%= status %>','<%= productCatalog.isTrashed() %>');"
							onMouseOver="over(0, <%= i %>)" onMouseOut="out(0, <%= i %>); hideMenu('menuOption');"><img src="images/select.gif" name="select<%= i %>" id="select<%= i %>" align="absmiddle" border="0"></a>
						</td>
						<td class="row<%= rowid %>">
							<a href="ProductCatalogOptions.do?command=Details&productId=<%= productCatalog.getId() %>&optionId=<%=thisOption.getId()%>&moduleId=<%= permissionCategory.getId() %>&categoryId=<%= productCategory.getId() %>"><%= toHtml(thisOption.getLabel()) %></a>
						</td>
						<td class="row<%= rowid %>" nowrap>
							<%= toHtml(thisOption.getConfiguratorName()) %>
						</td>
						<td class="row<%= rowid %>" align="center">
							<zeroio:tz timestamp="<%= thisOption.getStartDate() %>" dateOnly="true" default="&nbsp;"/>
						</td>
						<td class="row<%= rowid %>" align="center">
							<zeroio:tz timestamp="<%= thisOption.getEndDate() %>" dateOnly="true" default="&nbsp;"/>
						</td>
						<td class="row<%= rowid %>" align="center">
              <% if(thisOption.getEnabled()) {%>
                <dhv:label name="account.yes">Yes</dhv:label>
              <%} else {%>
                <dhv:label name="account.no">No</dhv:label>
              <%}%>
						</td>
					</tr>
			<%
					}
				} else {
			%>
				<tr class="containerBody">
					<td colspan="<%= columnCount %>">
						<dhv:label name="product.noOptionsAssociatedWithProduct">No Options have been associated with this Product</dhv:label><br />
					</td>
				</tr>
			<%
			}
			%>
			</table>
		</td>
	</tr>
</table>
</dhv:container>
