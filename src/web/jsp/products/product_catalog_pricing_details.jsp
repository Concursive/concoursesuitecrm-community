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
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<jsp:useBean id="applicationPrefs" class="org.aspcfs.controller.ApplicationPrefs" scope="application"/>
<%@ include file="../initPage.jsp" %>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/confirmDelete.js"></script>
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
      <a href="ProductCatalogPricings.do?command=PricingList&productId=<%= productCatalog.getId() %>&moduleId=<%= permissionCategory.getId() %>&categoryId=<%= productCategory.getId() %>"><dhv:label name="product.price">Price</dhv:label></a> >
      <dhv:label name="product.priceDetails">Price Details</dhv:label>
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
			<strong><dhv:label name="product.priceNumber.symbol">Price #</dhv:label><%= Pricing.getId() %></strong><br>
      <dhv:permission name="admin-sysconfig-products-edit">
        &nbsp;<br />
        <input type="button" value="<dhv:label name="button.modify">Modify</dhv:label>" onClick="javascript:window.location.href='ProductCatalogPricings.do?command=ModifyPricing&pricingId=<%= Pricing.getId() %>&categoryId=<%= productCategory.getId() %>&productId=<%= productCatalog.getId() %>&moduleId=<%= permissionCategory.getId() %>'"/>
        <% if (Pricing.getEnabled()) { %>
          <input type="button" value="<dhv:label name="button.disable">Disable</dhv:label>" onClick="javascript:confirmDelete('ProductCatalogPricings.do?command=DisablePricing&productId=<%= productCatalog.getId() %>&pricingId=<%= Pricing.getId() %>&categoryId=<%= productCategory.getId() %>&moduleId=<%= permissionCategory.getId() %>&return=details');"/>
        <% } else { %>
          <input type="button" value="<dhv:label name="button.enable">Enable</dhv:label>" onClick="javascript:confirmDelete('ProductCatalogPricings.do?command=EnablePricing&productId=<%= productCatalog.getId() %>&pricingId=<%= Pricing.getId() %>&categoryId=<%= productCategory.getId() %>&moduleId=<%= permissionCategory.getId() %>&return=details');"/>
        <% } %>
        <br />
      </dhv:permission>
      <dhv:formMessage/>
      <table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
				<tr>
					<th colspan="2">
						<strong><dhv:label name="accounts.details.long_html">Details</dhv:label></strong>
					</th>
				</tr>
      <tr class="containerBody">
        <td class="formLabel">
          <dhv:label name="product.msrp">MSRP</dhv:label>
        </td>
        <td>
          <zeroio:currency value="<%= Pricing.getMsrpAmount() %>" code="<%= applicationPrefs.get("SYSTEM.CURRENCY") %>" locale="<%= User.getLocale() %>" default="&nbsp;" truncate="false"/>
        </td>
      </tr>
      <tr class="containerBody">
        <td class="formLabel">
          <dhv:label name="quotes.Price">Price</dhv:label>
        </td>
        <td>
          <zeroio:currency value="<%= Pricing.getPriceAmount() %>" code="<%= applicationPrefs.get("SYSTEM.CURRENCY") %>" locale="<%= User.getLocale() %>" default="&nbsp;" truncate="false"/>
        </td>
      </tr>
      <tr class="containerBody">
        <td class="formLabel">
          <dhv:label name="product.cost">Cost</dhv:label>
        </td>
        <td>
          <zeroio:currency value="<%= Pricing.getCostAmount() %>" code="<%= applicationPrefs.get("SYSTEM.CURRENCY") %>" locale="<%= User.getLocale() %>" default="&nbsp;" truncate="false"/>
        </td>
      </tr>
      <tr class="containerBody">
        <td class="formLabel" nowrap>
          <dhv:label name="product.recurringAmount">Recurring Amount</dhv:label>
        </td>
        <td>
          <zeroio:currency value="<%= Pricing.getRecurringAmount() %>" code="<%= applicationPrefs.get("SYSTEM.CURRENCY") %>" locale="<%= User.getLocale() %>" default="&nbsp;" truncate="false"/>
        </td>
      </tr>
      <tr class="containerBody">
        <td class="formLabel">
          <dhv:label name="product.recurringType">Recurring Type</dhv:label>
        </td>
        <td>
          <%= toHtml(Pricing.getRecurringTypeName()) %>
        </td>
      </tr>
      <dhv:evaluate if="<%= hasText(Pricing.getTaxName()) %>">
				<tr class="containerBody">
					<td class="formLabel">
						<dhv:label name="product.taxType">Tax Type</dhv:label>
					</td>
					<td>
						<%= toHtml(Pricing.getTaxName()) %>
					</td>
				</tr>
      </dhv:evaluate>
      <dhv:evaluate if="<%= Pricing.getStartDate() != null %>">
				<tr class="containerBody">
					<td nowrap class="formLabel">
						<dhv:label name="documents.details.startDate">Start Date</dhv:label>
					</td>
					<td>
						<zeroio:tz timestamp="<%= Pricing.getStartDate() %>" />
					</td>
				</tr>
      </dhv:evaluate>
      <dhv:evaluate if="<%= Pricing.getExpirationDate() != null %>">
				<tr class="containerBody">
					<td nowrap class="formLabel">
						<dhv:label name="accounts.accountasset_include.ExpirationDate">Expiration Date</dhv:label>
					</td>
					<td>
						<zeroio:tz timestamp="<%= Pricing.getExpirationDate() %>" />
					</td>
				</tr>
      </dhv:evaluate>
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
						<dhv:username id="<%= Pricing.getEnteredBy() %>" />
						<zeroio:tz timestamp="<%= Pricing.getEntered() %>" />
					</td>
				</tr>
				<tr class="containerBody">
					<td nowrap class="formLabel">
						<dhv:label name="accounts.accounts_contacts_calls_details.Modified">Modified</dhv:label>
					</td>
					<td>
						<dhv:username id="<%= Pricing.getModifiedBy() %>" />
						<zeroio:tz timestamp="<%= Pricing.getModified() %>" />
					</td>
				</tr>
			</table>
			&nbsp;<br />
			<input type="hidden" name="catalogId" value="<%= productCatalog.getId() %>" />
			<input type="hidden" name="moduleId" value="<%= permissionCategory.getId() %>" />
			<dhv:permission name="admin-sysconfig-products-edit">
      <input type="button" value="<dhv:label name="button.modify">Modify</dhv:label>" onClick="javascript:window.location.href='ProductCatalogPricings.do?command=ModifyPricing&pricingId=<%= Pricing.getId() %>&categoryId=<%= productCategory.getId() %>&productId=<%= productCatalog.getId() %>&moduleId=<%= permissionCategory.getId() %>'"/>
      <% if (Pricing.getEnabled()) { %>
        <input type="button" value="<dhv:label name="button.disable">Disable</dhv:label>" onClick="javascript:confirmDelete('ProductCatalogPricings.do?command=DisablePricing&productId=<%= productCatalog.getId() %>&pricingId=<%= Pricing.getId() %>&categoryId=<%= productCategory.getId() %>&moduleId=<%= permissionCategory.getId() %>&return=details');"/>
      <% } else { %>
        <input type="button" value="<dhv:label name="button.enable">Enable</dhv:label>" onClick="javascript:confirmDelete('ProductCatalogPricings.do?command=EnablePricing&productId=<%= productCatalog.getId() %>&pricingId=<%= Pricing.getId() %>&categoryId=<%= productCategory.getId() %>&moduleId=<%= permissionCategory.getId() %>&return=details');"/>
      <% } %>
      </dhv:permission>
			<input type="hidden" name="dosubmit" value="true"/>
		</td>
	</tr>
</table>
</dhv:container>
