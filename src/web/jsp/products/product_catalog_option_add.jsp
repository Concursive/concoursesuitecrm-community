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
<jsp:useBean id="permissionCategory" class="org.aspcfs.modules.admin.base.PermissionCategory" scope="request"/>
<jsp:useBean id="productCatalog" class="org.aspcfs.modules.products.base.ProductCatalog" scope="request"/>
<jsp:useBean id="productCategory" class="org.aspcfs.modules.products.base.ProductCategory" scope="request"/>
<jsp:useBean id="configuratorList" class="org.aspcfs.modules.products.base.ProductOptionConfiguratorList" scope="request"/>
<%@ include file="../initPage.jsp" %>
<script language="javascript">
  function checkForm(form) {
    //alert(form.choice.selected);
    return true;
  }
</script>
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
      <a href="ProductCatalogOptions.do?command=List&productId=<%= productCatalog.getId() %>&moduleId=<%= permissionCategory.getId() %>&categoryId=<%= productCategory.getId() %>"><dhv:label name="product.options">Options</dhv:label></a> >
      <dhv:label name="product.addOption">Add Option</dhv:label>
    </td>
  </tr>
</table>
<br />
<dhv:container name="productcatalogs" selected="options" object="productCatalog" param='<%= param1 + "|" + param2 + "|" + param3 %>'>
<table cellpadding="4" cellspacing="0" border="0" width="100%">
  <tr>
    <td>
			<table class="note" cellspacing="0">
				<tr class="containerBody">
					<th><img src="images/icons/stock_about-16.gif" border="0" align="absmiddle"/></th>
					<td><dhv:label name="product.optionProceedHint">Select a type of option from the list to proceed</dhv:label><br /></td>
				</tr>
			</table>
			<table cellpadding="4" cellspacing="0" border="0" width="100%" class="pagedList">
				<tr>
					<th nowrap>
					  <strong><dhv:label name="product.configurator.type">Type</dhv:label></strong>
          </th>
          <th nowrap>
					  <strong><dhv:label name="product.configurator.description">Description</dhv:label></strong>
          </th>
				</tr>
        <% 
          Iterator j = configuratorList.iterator();
          if (j.hasNext()) {
            int rowid = 0;
            int i = 0;
            while (j.hasNext()) {
              i++;
              rowid = (rowid != 1 ? 1 : 2);
              ProductOptionConfigurator configurator = (ProductOptionConfigurator) j.next();
        %>
				<tr class="containerBody">
          <td class="row<%= rowid %>">
							<a href="ProductCatalogOptions.do?command=LoadConfigurator&productId=<%= productCatalog.getId() %>&categoryId=<%= productCategory.getId() %>&moduleId=<%= permissionCategory.getId() %>&configId=<%= configurator.getId() %>&return=list">
                <%= toHtml(configurator.getConfiguratorName()) %>
              </a>
					</td>
					<td class="row<%= rowid %>">
							<%= toHtml(configurator.getShortDescription()) %>
					</td>
        </tr>
        <%
					}
				} else {
        %>
				<tr class="containerBody">
					<td colspan="2">
						<dhv:label name="product.option.configuratorsNotFound">Option configurators not found</dhv:label>
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
