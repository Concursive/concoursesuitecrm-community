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
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<%@ include file="../initPage.jsp" %>
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
      <dhv:label name="product.productDetails">Product Details</dhv:label>
    </td>
  </tr>
</table>
<br />
  <dhv:container name="productcatalogs" selected="details" object="productCatalog" param="<%= "productId=" + productCatalog.getId() + "|" + "moduleId=" + permissionCategory.getId() + "|" + "categoryId=" + productCategory.getId() %>">
  <table cellpadding="4" cellspacing="0" border="0" width="100%">
    <tr>
      <td>
        <input type="hidden" name="catalogId" value="<%= productCatalog.getId() %>">
        <dhv:evaluate if="<%= !productCatalog.isTrashed() %>">
          <dhv:permission name="admin-sysconfig-products-edit">
            <input type="button" value="<dhv:label name="button.modify">Modify</dhv:label>" onClick="javascript:window.location.href='ProductCatalogs.do?command=Modify&productId=<%= productCatalog.getId() %>&categoryId=<%= productCategory.getId() %>&moduleId=<%= permissionCategory.getId() %>'">
          </dhv:permission>
          <dhv:permission name="admin-sysconfig-products-add">
            <input type="button" value="<dhv:label name="button.clone">Clone</dhv:label>" onClick="javascript:window.location.href='ProductCatalogs.do?command=Clone&categoryId=<%= productCategory.getId() %>&moduleId=<%= permissionCategory.getId() %>&productId=<%= productCatalog.getId() %>&actionReq=clone'"/>
          </dhv:permission>
          <dhv:permission name="admin-sysconfig-products-delete">
            <input type="button" value="<dhv:label name="button.delete">Delete</dhv:label>" onClick="javascript:popURLReturn('ProductCatalogs.do?command=ConfirmDelete&productId=<%=productCatalog.getId()%>&popup=true','ProductCatalogEditor.do?command=List&moduleId=<%=permissionCategory.getId()%>&categoryId=<%= productCategory.getId() %>', 'Delete_category','320','200','yes','no');">
          </dhv:permission>
        </dhv:evaluate>
        <dhv:evaluate if="<%= productCatalog.isTrashed() %>">
          <dhv:permission name="admin-sysconfig-products-delete">
            <input type="button" value="<dhv:label name="button.restore">Restore</dhv:label>" onClick="javascript:window.location.href='ProductCatalogs.do?command=Restore&productId=<%=productCatalog.getId()%>&categoryId=<%= productCategory.getId() %>&moduleId=<%= permissionCategory.getId() %>'">
          </dhv:permission>
        </dhv:evaluate>
        <dhv:permission name="admin-sysconfig-products-edit,admin-sysconfig-products-add,admin-sysconfig-products-delete">
        &nbsp;<br /><br />
        </dhv:permission>
        <table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
          <tr>
            <th colspan="2">
              <strong><dhv:label name="accounts.accounts_details.PrimaryInformation">Primary Information</dhv:label></strong>
            </th>
          </tr>
        <dhv:evaluate if="<%= hasText(productCatalog.getParentName()) %>">
          <tr class="containerBody">
            <td class="formLabel">
              <dhv:label name="product.parentCatalog">Parent Catalog</dhv:label>
            </td>
            <td><%= toHtml(productCatalog.getParentName()) %></td>
          </tr>
        </dhv:evaluate>
        <%--
        <dhv:evaluate if="<%= hasText(productCatalog.getCategoryName()) %>">
          <tr class="containerBody">
            <td class="formLabel" nowrap>
              Product Category
            </td>
            <td><%= toHtml(productCatalog.getCategoryName()) %></td>
          </tr>
        </dhv:evaluate>
        --%>
        <dhv:evaluate if="<%= hasText(productCatalog.getAbbreviation()) %>">
          <tr class="containerBody">
            <td class="formLabel">
              <dhv:label name="literal.abbreviation.name">Abbreviation</dhv:label>
            </td>
            <td><%= toHtml(productCatalog.getAbbreviation()) %></td>
          </tr>
        </dhv:evaluate>
          <tr class="containerBody">
            <td class="formLabel">
              <dhv:label name="quotes.sku">SKU</dhv:label>
            </td>
            <td><%= toHtml(productCatalog.getSku()) %></td>
          </tr>
        <dhv:evaluate if="<%= hasText(productCatalog.getTypeName()) %>">
          <tr class="containerBody">
            <td class="formLabel">
              <dhv:label name="product.productType">Product Type</dhv:label>
            </td>
            <td><%= toHtml(productCatalog.getTypeName()) %></td>
          </tr>
        </dhv:evaluate>
        <dhv:evaluate if="<%= hasText(productCatalog.getShippingTimeName()) %>">
          <tr class="containerBody">
            <td nowrap class="formLabel">
              <dhv:label name="product.productShipTime">Product Ship Time</dhv:label>
            </td>
            <td><%= toHtml(productCatalog.getShippingTimeName()) %></td>
          </tr>
        </dhv:evaluate>
        <dhv:evaluate if="<%= hasText(productCatalog.getShippingName()) %>">
          <tr class="containerBody">
            <td class="formLabel">
              <dhv:label name="product.productShipping">Product Shipping</dhv:label>
            </td>
            <td><%= toHtml(productCatalog.getShippingName()) %></td>
          </tr>
        </dhv:evaluate>
        <dhv:evaluate if="<%= hasText(productCatalog.getFormatName()) %>">
          <tr class="containerBody">
            <td class="formLabel">
              <dhv:label name="product.productFormat">Product Format</dhv:label>
            </td>
            <td><%= toHtml(productCatalog.getFormatName()) %></td>
          </tr>
        </dhv:evaluate>
        </table>
        &nbsp;<br />
        <table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
          <tr>
            <th colspan="2">
              <strong><dhv:label name="product.option.availability">Availability</dhv:label></strong>
            </th>
          </tr>
          <tr class="containerBody">
          <td nowrap class="formLabel">
            <dhv:label name="product.enabled">Enabled</dhv:label>
          </td>
          <td>
            <% if(productCatalog.getActive()) {%>
              <dhv:label name="account.yes">Yes</dhv:label>
            <%} else {%>
              <dhv:label name="account.no">No</dhv:label>
            <%}%>
          </td>
        </tr>
          <tr class="containerBody">
            <td class="formLabel">
              <dhv:label name="documents.details.startDate">Start Date</dhv:label>
            </td>
            <td><zeroio:tz timestamp="<%= productCatalog.getStartDate() %>" />&nbsp;</td>
          </tr>
          <tr class="containerBody">
            <td class="formLabel">
              <dhv:label name="accounts.accountasset_include.ExpirationDate">Expiration Date</dhv:label>
            </td>
            <td><zeroio:tz timestamp="<%= productCatalog.getExpirationDate() %>" />&nbsp;</td>
          </tr>
        </table>
        <br />
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
            <td><%= toHtml(productCatalog.getShortDescription()) %></td>
          </tr>
          <tr class="containerBody">
            <td class="formLabel">
              <dhv:label name="documents.details.longDescription">Long Description</dhv:label>
            </td>
            <td><%= toHtml(productCatalog.getLongDescription()) %></td>
          </tr>
          <tr class="containerBody">
            <td class="formLabel">
              <dhv:label name="documents.details.specialNotes">Special Notes</dhv:label>
            </td>
            <td><%= toHtml(productCatalog.getSpecialNotes()) %></td>
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
              <dhv:username id="<%= productCatalog.getEnteredBy() %>" />
              <zeroio:tz timestamp="<%= productCatalog.getEntered() %>" />
            </td>
          </tr>
          <tr class="containerBody">
            <td nowrap class="formLabel">
              <dhv:label name="accounts.accounts_contacts_calls_details.Modified">Modified</dhv:label>
            </td>
            <td>
              <dhv:username id="<%= productCatalog.getModifiedBy() %>" />
              <zeroio:tz timestamp="<%= productCatalog.getModified() %>" />
            </td>
          </tr>
        </table>
        <br />
        <dhv:evaluate if="<%= !productCatalog.isTrashed() %>">
          <dhv:permission name="admin-sysconfig-products-edit">
            <input type="button" value="<dhv:label name="button.modify">Modify</dhv:label>" onClick="javascript:window.location.href='ProductCatalogs.do?command=Modify&productId=<%= productCatalog.getId() %>&categoryId=<%= productCategory.getId() %>&moduleId=<%= permissionCategory.getId() %>'">
          </dhv:permission>
          <dhv:permission name="admin-sysconfig-products-add">
            <input type="button" value="<dhv:label name="button.clone">Clone</dhv:label>" onClick="javascript:window.location.href='ProductCatalogs.do?command=Clone&categoryId=<%= productCategory.getId() %>&moduleId=<%= permissionCategory.getId() %>&productId=<%= productCatalog.getId() %>&actionReq=clone'"/>
          </dhv:permission>
          <dhv:permission name="admin-sysconfig-products-delete">
            <input type="button" value="<dhv:label name="button.delete">Delete</dhv:label>" onClick="javascript:popURLReturn('ProductCatalogs.do?command=ConfirmDelete&productId=<%=productCatalog.getId()%>&popup=true','ProductCatalogEditor.do?command=List&moduleId=<%=permissionCategory.getId()%>&categoryId=<%= productCategory.getId() %>', 'Delete_category','320','200','yes','no');">
          </dhv:permission>
        </dhv:evaluate>
        <dhv:evaluate if="<%=productCatalog.getTrashedDate() != null%>">
          <dhv:permission name="admin-sysconfig-products-delete">
            <input type="button" value="<dhv:label name="button.restore">Restore</dhv:label>" onClick="javascript:window.location.href='ProductCatalogs.do?command=Restore&productId=<%=productCatalog.getId()%>&categoryId=<%= productCategory.getId() %>&moduleId=<%= permissionCategory.getId() %>'">
          </dhv:permission>
        </dhv:evaluate>
      </td>
      </tr>
    </table>
</dhv:container>
