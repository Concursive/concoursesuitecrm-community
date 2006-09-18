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
  - Version: $Id: product_importcatalog_details.jsp 12613 2006-05-26 Olga.Kaptyug@corratech.com $
  - Description: 
  --%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv"%>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio"%>
<jsp:useBean id="ProductDetails" class="org.aspcfs.modules.products.base.ProductCatalog" scope="request" />
<jsp:useBean id="ImportDetails" class="org.aspcfs.modules.base.Import" scope="request" />
<%@ page import="java.util.*,java.text.DateFormat,org.aspcfs.modules.products.base.*"%>
<jsp:useBean id="permissionCategory" class="org.aspcfs.modules.admin.base.PermissionCategory" scope="request" />
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session" />
<jsp:useBean id="applicationPrefs" class="org.aspcfs.controller.ApplicationPrefs" scope="application" />
<jsp:useBean id="ThumbnailImage" class="com.zeroio.iteam.base.FileItem" scope="request"/>
<jsp:useBean id="SmallImage" class="com.zeroio.iteam.base.FileItem" scope="request"/>
<jsp:useBean id="LargeImage" class="com.zeroio.iteam.base.FileItem" scope="request"/>
<%@ include file="../initPage.jsp"%>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/confirmDelete.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popURL.js"></script>
<script language="JavaScript">
  function confirmDeleteProduct() {
    url = 'ProductCatalogImports.do?command=DeleteProduct&productId=<%= ProductDetails.getId() %>&importId=<%= ImportDetails.getId() %>&moduleId=<%=permissionCategory.getId()%>';
    confirmDelete(url);
  }
</script>
<table class="trails" cellspacing="0">
  <tr>
    <td>
      <a href="ProductCatalogEditor.do?command=List"><dhv:label name="product.products">Products</dhv:label></a> > 
      <a href="ProductCatalogImports.do?command=View&moduleId=<%=permissionCategory.getId()%>"><dhv:label name="product.ViewImports">View Imports</dhv:label></a> > 
      <a href="ProductCatalogImports.do?command=Details&moduleId=<%=permissionCategory.getId()%>&importId=<%= ImportDetails.getId() %>"><dhv:label name="product.ImportDetails">Import Details</dhv:label></a> > 
      <a href="ProductCatalogImports.do?command=ViewResults&moduleId=<%=permissionCategory.getId()%>&importId=<%= ImportDetails.getId() %>"><dhv:label name="global.button.ViewResults">View Results</dhv:label></a> >
      <dhv:label name="product.product_catalogs_add.ProductDetails">Product Details</dhv:label>
    </td>
  </tr>
</table>
<%-- End Trails --%>
<dhv:container name="productcatalogimports" selected="details" object="ProductDetails" hideContainer="true" param="<%= "productId=" + ProductDetails.getId() + "|" + "moduleId=" + permissionCategory.getId()%>">
  <table cellpadding="4" cellspacing="0" border="0" width="100%">
    <tr>
      <td>
        <input type="hidden" name="productId" value="<%= ProductDetails.getId() %>">
        <%if (!ProductDetails.isApproved()) {

			%>
        <dhv:permission name="product-catalog-delete">
          <input type="button" value="<dhv:label name="button.delete">Delete</dhv:label>" onClick="javascript:confirmDeleteProduct();">
        </dhv:permission>
        <dhv:permission name="product-catalog-delete">
        &nbsp;<br />
          <br />
        </dhv:permission>
        <%}%>
        <table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
          <tr>
            <th colspan="2">
              <strong><dhv:label name="accounts.accounts_details.PrimaryInformation">Primary Information</dhv:label></strong>
            </th>
          </tr>
          <dhv:evaluate if="<%= hasText(ProductDetails.getParentName()) %>">
            <tr class="containerBody">
              <td class="formLabel">
                <dhv:label name="product.parentCatalog">Parent Catalog</dhv:label>
              </td>
              <td>
                <%=toHtml(ProductDetails.getParentName())%>
              </td>
            </tr>
          </dhv:evaluate>
          <%--
        <dhv:evaluate if="<%= hasText(ProductDetails.getCategoryName()) %>">
          <tr class="containerBody">
            <td class="formLabel" nowrap>
              Product Category
            </td>
            <td><%= toHtml(ProductDetails.getCategoryName()) %></td>
          </tr>
        </dhv:evaluate>
        --%>
          <dhv:evaluate if="<%= hasText(ProductDetails.getAbbreviation()) %>">
            <tr class="containerBody">
              <td class="formLabel">
                <dhv:label name="literal.abbreviation.name">Abbreviation</dhv:label>
              </td>
              <td>
                <%=toHtml(ProductDetails.getAbbreviation())%>
              </td>
            </tr>
          </dhv:evaluate>
          <tr class="containerBody">
            <td class="formLabel">
              <dhv:label name="quotes.sku">SKU</dhv:label>
            </td>
            <td>
              <%=toHtml(ProductDetails.getSku())%>
            </td>
          </tr>
          <dhv:evaluate if="<%= hasText(ProductDetails.getTypeName()) %>">
            <tr class="containerBody">
              <td class="formLabel">
                <dhv:label name="product.productType">Product Type</dhv:label>
              </td>
              <td>
                <%=toHtml(ProductDetails.getTypeName())%>
              </td>
            </tr>
          </dhv:evaluate>
          <dhv:evaluate if="<%= hasText(ProductDetails.getShippingTimeName()) %>">
            <tr class="containerBody">
              <td nowrap class="formLabel">
                <dhv:label name="product.productShipTime">Product Ship Time</dhv:label>
              </td>
              <td>
                <%=toHtml(ProductDetails.getShippingTimeName())%>
              </td>
            </tr>
          </dhv:evaluate>
          <dhv:evaluate if="<%= hasText(ProductDetails.getShippingName()) %>">
            <tr class="containerBody">
              <td class="formLabel">
                <dhv:label name="product.productShipping">Product Shipping</dhv:label>
              </td>
              <td>
                <%=toHtml(ProductDetails.getShippingName())%>
              </td>
            </tr>
          </dhv:evaluate>
          <dhv:evaluate if="<%= hasText(ProductDetails.getFormatName()) %>">
            <tr class="containerBody">
              <td class="formLabel">
                <dhv:label name="product.productFormat">Product Format</dhv:label>
              </td>
              <td>
                <%=toHtml(ProductDetails.getFormatName())%>
              </td>
            </tr>
          </dhv:evaluate>
        </table>
        &nbsp;
        <br />
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
              <%if (ProductDetails.getActive()) {%>
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
            <td>
              <zeroio:tz timestamp="<%= ProductDetails.getStartDate() %>" />
              &nbsp;
            </td>
          </tr>
          <tr class="containerBody">
            <td class="formLabel">
              <dhv:label name="accounts.accountasset_include.ExpirationDate">Expiration Date</dhv:label>
            </td>
            <td>
              <zeroio:tz timestamp="<%= ProductDetails.getExpirationDate() %>" />
              &nbsp;
            </td>
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
            <td>
              <%=toHtml(ProductDetails.getShortDescription())%>
            </td>
          </tr>
          <tr class="containerBody">
            <td class="formLabel">
              <dhv:label name="documents.details.longDescription">Long Description</dhv:label>
            </td>
            <td>
              <%=toHtml(ProductDetails.getLongDescription())%>
            </td>
          </tr>
          <tr class="containerBody">
            <td class="formLabel">
              <dhv:label name="documents.details.specialNotes">Special Notes</dhv:label>
            </td>
            <td>
              <%=toHtml(ProductDetails.getSpecialNotes())%>
            </td>
          </tr>
        </table>
        &nbsp;
        <br />
        <table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
          <tr>
            <th colspan="2">
              <strong><dhv:label name="products.product_catalog_details.RecordInformation">Record Information</dhv:label></strong>
            </th>
          </tr>
          <tr class="containerBody">
            <td nowrap class="formLabel">
              <dhv:label name="accounts.accounts_calls_list.Entered">Entered</dhv:label>
            </td>
            <td>
              <dhv:username id="<%= ProductDetails.getEnteredBy() %>" />
              <zeroio:tz timestamp="<%= ProductDetails.getEntered() %>" />
            </td>
          </tr>
          <tr class="containerBody">
            <td nowrap class="formLabel">
              <dhv:label name="products.product_catalog_details.Modified">Modified</dhv:label>
            </td>
            <td>
              <dhv:username id="<%= ProductDetails.getModifiedBy() %>" />
              <zeroio:tz timestamp="<%= ProductDetails.getModified() %>" />
            </td>
          </tr>
        </table>
        <br />
        <%if (ProductDetails.getPriceList() != null) {%>

        <strong><dhv:label name="products.Price">Price</dhv:label></strong>
        <table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
          <%Iterator i = ProductDetails.getPriceList().iterator();
				if (i.hasNext()) {
					int rowid = 0;
					int count = 0;
					while (i.hasNext()) {
						count++;
						rowid = (rowid != 1 ? 1 : 2);
						ProductCatalogPricing Pricing = (ProductCatalogPricing) i
								.next();

						%>
          <tr>
            <th colspan="1">
              <dhv:label name="product.msrp">MSRP</dhv:label>
            </th>
            <th colspan="1">
              <dhv:label name="quotes.Price">Price</dhv:label>
            </th>
            <th colspan="1">
              <dhv:label name="product.cost">Cost</dhv:label>
            </th>
            <th colspan="1">
              <dhv:label name="product.recurringAmount">Recurring Amount</dhv:label>
            </th>
            <th colspan="1">
              <dhv:label name="product.recurringType">Recurring Type</dhv:label>
            </th>
            <dhv:evaluate if="<%= hasText(Pricing.getTaxName()) %>">
              <th colspan="1">
                <dhv:label name="product.taxType">Tax Type</dhv:label>
              </th>
            </dhv:evaluate>
          </tr>
          <tr class="containerBody">
            <td>
              <zeroio:currency value="<%= Pricing.getMsrpAmount() %>" code="<%= applicationPrefs.get("SYSTEM.CURRENCY") %>" locale="<%= User.getLocale() %>" default="&nbsp;" truncate="false" />
            </td>
            <td>
              <zeroio:currency value="<%= Pricing.getPriceAmount() %>" code="<%= applicationPrefs.get("SYSTEM.CURRENCY") %>" locale="<%= User.getLocale() %>" default="&nbsp;" truncate="false" />
            </td>
            <td>
              <zeroio:currency value="<%= Pricing.getCostAmount() %>" code="<%= applicationPrefs.get("SYSTEM.CURRENCY") %>" locale="<%= User.getLocale() %>" default="&nbsp;" truncate="false" />
            </td>
            <td>
              <zeroio:currency value="<%= Pricing.getRecurringAmount() %>" code="<%= applicationPrefs.get("SYSTEM.CURRENCY") %>" locale="<%= User.getLocale() %>" default="&nbsp;" truncate="false" />
            </td>
            <td>
              <%=toHtml(Pricing.getRecurringTypeName())%>
            </td>
            <dhv:evaluate if="<%= hasText(Pricing.getTaxName()) %>">

              <td>
                <%=toHtml(Pricing.getTaxName())%>
              </td>
            </dhv:evaluate>
          </tr>
          <%}
				}
			}%>
         
        </table>
        <br/>
         <strong><dhv:label name="products.imageFiles">Image Files</dhv:label></strong>
        <table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
          <tr>
            <th>
              <dhv:label name="product.fileType">File Type</dhv:label>
            </th>
            <th>
              <dhv:label name="documents.documents.fileName">File Name</dhv:label>
            </th>
            <th style="text-align: center;">
              <dhv:label name="accounts.accounts_documents_details.Size">Size</dhv:label>
            </th>            <th style="text-align: center;">
              <dhv:label name="quotes.date">Date</dhv:label>
            </th>
          </tr>
          <tr class="containerBody">
            <td class="formLabel">
              <dhv:label name="product.thumbnailImage">Thumbnail Image</dhv:label>
            </td>
            <dhv:evaluate if="<%= ThumbnailImage.getId() != -1 %>">
              <td valign="middle" width="100%">
                <dhv:fileItemImage id="<%=  ThumbnailImage.getId() %>" path="products" thumbnail="true" name="<%= ThumbnailImage.getClientFilename() %>" />
              </td>
              <td style="text-align: center;" valign="middle" nowrap>
                <%=ThumbnailImage.getRelativeSize()%>
                <dhv:label name="admin.oneThousand.abbreviation">k</dhv:label>
                &nbsp;
              </td>
              <td nowrap valign="middle">
                <%=ThumbnailImage.getModifiedDateTimeString()%>
                <br>
                <dhv:username id="<%= ThumbnailImage.getEnteredBy() %>" />
              </td>
            </dhv:evaluate>
            <dhv:evaluate if="<%= ThumbnailImage.getId() == -1 %>">
              <td colspan="4">
                <font color="red"><dhv:label name="product.noImageAvailable">No Image Available.</dhv:label></font>
              </td>
            </dhv:evaluate>
          </tr>
          <br>
          <tr class="containerBody">
            <td class="formLabel" nowrap>
              <dhv:label name="product.smallImage">Small Image</dhv:label>
            </td>
            <dhv:evaluate if="<%= SmallImage.getId() != -1 %>">
              <td valign="middle" width="100%">
                <dhv:fileItemImage id="<%=  SmallImage.getId() %>" path="products" thumbnail="true" name="<%=  SmallImage.getClientFilename() %>" />
              </td>
              <td style="text-align: center;" valign="middle" nowrap>
                <%=SmallImage.getRelativeSize()%>
                <dhv:label name="admin.oneThousand.abbreviation">k</dhv:label>
                &nbsp;
              </td>
              <td nowrap valign="middle">
                <%=SmallImage.getModifiedDateTimeString()%>
                <br>
                <dhv:username id="<%= SmallImage.getEnteredBy() %>" />
              </td>
            </dhv:evaluate>
            <dhv:evaluate if="<%= SmallImage.getId() == -1 %>">
              <td colspan="4">
                <font color="red"><dhv:label name="product.noImageAvailable">No Image Available.</dhv:label></font> 
              </td>
            </dhv:evaluate>
          </tr>

          <tr class="containerBody">
            <td class="formLabel" nowrap>
              <dhv:label name="product.largeImage">Large Image</dhv:label>
            </td>
            <dhv:evaluate if="<%= LargeImage.getId() != -1 %>">
              <td valign="middle" width="100%">
                <dhv:fileItemImage id="<%=  LargeImage.getId() %>" path="products" thumbnail="true" name="<%=  LargeImage.getClientFilename() %>" />
              </td>
              <td style="text-align: center;" valign="middle" nowrap>
                <%=LargeImage.getRelativeSize()%>
                <dhv:label name="admin.oneThousand.abbreviation">k</dhv:label>
                &nbsp;
              </td>
              <td nowrap valign="middle">
                <%=LargeImage.getModifiedDateTimeString()%>
                <br>
                <dhv:username id="<%= LargeImage.getEnteredBy() %>" />
              </td>
            </dhv:evaluate>
            <dhv:evaluate if="<%= LargeImage.getId() == -1 %>">
              <td colspan="4">
                <font color="red"><dhv:label name="product.noImageAvailable">No Image Available.</dhv:label></font> 
              </td>
            </dhv:evaluate>
          </tr>
          <tr class="containerBody">
            <%=showError(request, "actionError")%>
          </tr>
        </table>
        <br/>
        <%if (!ProductDetails.isApproved()) {

			%>
        <dhv:permission name="product-catalog-delete">
          <input type="button" value="<dhv:label name="button.delete">Delete</dhv:label>" onClick="javascript:confirmDeleteProduct();">
        </dhv:permission>
        <%}%>

      </td>
    </tr>
  </table>
</dhv:container>
