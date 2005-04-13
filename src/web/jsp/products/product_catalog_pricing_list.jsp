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
<jsp:useBean id="ActivePriceList" class="org.aspcfs.modules.products.base.ProductCatalogPricingList" scope="request"/>
<jsp:useBean id="InactivePriceList" class="org.aspcfs.modules.products.base.ProductCatalogPricingList" scope="request"/>
<jsp:useBean id="productCategory" class="org.aspcfs.modules.products.base.ProductCategory" scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<jsp:useBean id="applicationPrefs" class="org.aspcfs.controller.ApplicationPrefs" scope="application"/>
<%@ include file="../initPage.jsp" %>
<%-- Initialize the drop-down menus --%>
<%@ include file="../initPopupMenu.jsp" %>
<%@ include file="product_catalog_pricing_list_menu.jsp" %> 
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/spanDisplay.js"></SCRIPT>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/confirmDelete.js"></script>
<script language="JavaScript" type="text/javascript">
  <%-- Preload image rollovers for drop-down menu --%>
  loadImages('select');
</script>
<%-- Trails --%>
<table class="trails" cellspacing="0">
	<tr>
		<td>
			<a href="Admin.do"><dhv:label name="trails.admin">Admin</dhv:label></a> >
			<a href="Admin.do?command=Config"><dhv:label name="trails.configureModules">Configure Modules</dhv:label></a> >
			<a href="Admin.do?command=ConfigDetails&moduleId=<%= permissionCategory.getId() %>"><%= toHtml(permissionCategory.getCategory()) %></a> >
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
      <dhv:label name="product.price">Price</dhv:label>
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
      <dhv:permission name="admin-sysconfig-products-add">
        <a href="ProductCatalogPricings.do?command=AddPricing&moduleId=<%= permissionCategory.getId() %>&productId=<%= productCatalog.getId() %>&categoryId=<%= productCategory.getId() %>"><dhv:label name="product.addProductPrice">Add Product Price</dhv:label></a>
        &nbsp;<br />
      </dhv:permission>
      <dhv:formMessage />
			<table cellspacing="0" cellpadding="0" border="0">
				<tr>
					<td><strong><dhv:label name="product.activePrice">Active Price</dhv:label></strong></td>
				</tr>
			</table>
			<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
				<tr>
          <th width="8">&nbsp;</th>
          <th><dhv:label name="product.priceId">Price Id</dhv:label></th>
					<th><dhv:label name="product.msrp">MSRP</dhv:label></th>
					<th><dhv:label name="quotes.Price">Price</dhv:label></th>
          <th><dhv:label name="product.cost">Cost</dhv:label></th>
					<th><dhv:label name="documents.details.startDate">Start Date</dhv:label></th>
					<th><dhv:label name="accounts.accountasset_include.ExpirationDate">Expiration Date</dhv:label></th>
        </tr>
				<%
					Iterator j = ActivePriceList.iterator();
					int rowid = 0;
					int i = 0;
					while (j.hasNext()) {
						i++;
						rowid = (rowid != 1 ? 1 : 2);
						ProductCatalogPricing thisPricing = (ProductCatalogPricing) j.next();
				%>
				<tr class="row<%= rowid %>">
					<td width="8" valign="center" nowrap>
						<%-- Use the unique id for opening the menu, and toggling the graphics --%>
						<a href="javascript:displayMenu('select<%= i %>','menuPricing', '<%= thisPricing.getId() %>', 'active');"
						onMouseOver="over(0, <%= i %>)" onmouseout="out(0, <%= i %>); hideMenu('menuPricing');"><img src="images/select.gif" name="select<%= i %>" id="select<%= i %>" align="absmiddle" border="0"></a>
					</td>
					<td><%= thisPricing.getId() %></td>
					<td valign="top">
            <zeroio:currency value="<%= thisPricing.getMsrpAmount() %>" code="<%= applicationPrefs.get("SYSTEM.CURRENCY") %>" locale="<%= User.getLocale() %>" default="&nbsp;" truncate="false"/>
          </td>
					<td valign="top">
            <zeroio:currency value="<%= thisPricing.getPriceAmount() %>" code="<%= applicationPrefs.get("SYSTEM.CURRENCY") %>" locale="<%= User.getLocale() %>" default="&nbsp;" truncate="false"/>
          </td>
					<td valign="top">
            <zeroio:currency value="<%= thisPricing.getCostAmount() %>" code="<%= applicationPrefs.get("SYSTEM.CURRENCY") %>" locale="<%= User.getLocale() %>" default="&nbsp;" truncate="false"/>
          </td>
					<td valign="top">
            <zeroio:tz timestamp="<%= thisPricing.getStartDate() %>" />
            <dhv:evaluate if="<%= thisPricing.getStartDate() == null %>">
              &nbsp;
            </dhv:evaluate>
          </td>
					<td nowrap>
            <zeroio:tz timestamp="<%= thisPricing.getExpirationDate() %>" />
            <dhv:evaluate if="<%= thisPricing.getExpirationDate() == null %>">
              &nbsp;
            </dhv:evaluate>
          </td>
				</tr>
				<%
					}
				%>
				<dhv:evaluate if="<%= ActivePriceList.size() == 0 %>">
					<tr class="containerBody">
						<td colspan="10"><dhv:label name="product.noPricesFound">No prices found</dhv:label></td>
					</tr>
				</dhv:evaluate>
			</table>
			&nbsp;<br />
			<table cellspacing="0" cellpadding="0" border="0">
				<tr>
					<td><strong><dhv:label name="product.inactivePrices">Inactive Prices</dhv:label></strong></td>
				</tr>
			</table>
			<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
				<tr>
          <th width="8">&nbsp;</th>
          <th><dhv:label name="product.priceId">Price Id</dhv:label></th>
					<th align="right"><dhv:label name="product.msrp">MSRP</dhv:label></th>
					<th align="right"><dhv:label name="quotes.Price">Price</dhv:label></th>
          <th align="right"><dhv:label name="product.cost">Cost</dhv:label></th>
					<th><dhv:label name="documents.details.startDate">Start Date</dhv:label></th>
					<th><dhv:label name="accounts.accountasset_include.ExpirationDate">Expiration Date</dhv:label></th>
        </tr>
				<%
					j = InactivePriceList.iterator();
					rowid = 0;
					while (j.hasNext()) {
						i++;
						rowid = (rowid != 1 ? 1 : 2);
						ProductCatalogPricing thisPricing = (ProductCatalogPricing) j.next();
				%>
				<tr class="row<%= rowid %>">
					<td width="8" valign="center" nowrap>
						<%-- Use the unique id for opening the menu, and toggling the graphics --%>
						<a href="javascript:displayMenu('select<%= i %>','menuPricing', '<%= thisPricing.getId() %>', 'inactive');"
						onMouseOver="over(0, <%= i %>)" onmouseout="out(0, <%= i %>); hideMenu('menuPricing');"><img src="images/select.gif" name="select<%= i %>" id="select<%= i %>" align="absmiddle" border="0"></a>
					</td>
					<td><%= thisPricing.getId() %></td>
					<td valign="top">
            <zeroio:currency value="<%= thisPricing.getMsrpAmount() %>" code="<%= applicationPrefs.get("SYSTEM.CURRENCY") %>" locale="<%= User.getLocale() %>" default="&nbsp;" truncate="false"/>
          </td>
					<td valign="top">
            <zeroio:currency value="<%= thisPricing.getPriceAmount() %>" code="<%= applicationPrefs.get("SYSTEM.CURRENCY") %>" locale="<%= User.getLocale() %>" default="&nbsp;" truncate="false"/>
          </td>
					<td valign="top">
            <zeroio:currency value="<%= thisPricing.getCostAmount() %>" code="<%= applicationPrefs.get("SYSTEM.CURRENCY") %>" locale="<%= User.getLocale() %>" default="&nbsp;" truncate="false"/>
          </td>
					<td nowrap>
            <zeroio:tz timestamp="<%= thisPricing.getStartDate() %>" />
            <dhv:evaluate if="<%= thisPricing.getStartDate() == null %>">
              &nbsp;
            </dhv:evaluate>
          </td>
					<td nowrap>
            <zeroio:tz timestamp="<%= thisPricing.getExpirationDate() %>" />
            <dhv:evaluate if="<%= thisPricing.getExpirationDate() == null %>">
              &nbsp;
            </dhv:evaluate>
          </td>
				</tr>
				<%
					}
				%>
				<dhv:evaluate if="<%= InactivePriceList.size() == 0 %>">
					<tr class="containerBody">
						<td colspan="10"><dhv:label name="product.noPricesFound">No prices found</dhv:label></td>
					</tr>
				</dhv:evaluate>
			</table>
			&nbsp;<br />
		</td>
	</tr>
</table>
</dhv:container>