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
<%@ page import="java.util.*, org.aspcfs.modules.products.base.*, com.zeroio.iteam.base.*" %>
<jsp:useBean id="permissionCategory" class="org.aspcfs.modules.admin.base.PermissionCategory" scope="request"/>
<jsp:useBean id="productCatalog" class="org.aspcfs.modules.products.base.ProductCatalog" scope="request"/>
<jsp:useBean id="productCategory" class="org.aspcfs.modules.products.base.ProductCategory" scope="request"/>
<jsp:useBean id="ThumbnailImage" class="com.zeroio.iteam.base.FileItem" scope="request"/>
<jsp:useBean id="SmallImage" class="com.zeroio.iteam.base.FileItem" scope="request"/>
<jsp:useBean id="LargeImage" class="com.zeroio.iteam.base.FileItem" scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/spanDisplay.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/confirmDelete.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/div.js"></script>
<script language="JavaScript">
	function checkForm(form) {
    if (form.id<%= productCatalog.getId() %>.value.length < 5) {
      alert(label("check.product.filename","Please specify the file to be uploaded"));
      return false;
    }
    return true;
  }
  
  function showUploadSpan(item) {
		showSpan('uploadSpan');
		var form = document.inputForm;
		form.imageType.value = item;
		if (item == 'thumbnail') {
			changeDivContent('displaySpan', label('image.thumbnail',': Thumbnail Image'));
		} else if (item == 'small') {
			changeDivContent('displaySpan', label('image.small',': Small Image'));
		} else if (item == 'large') {
			changeDivContent('displaySpan', label('image.large',': Large Image'));
		}
	}
</script>
<%@ include file="../initPage.jsp" %>
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
      <dhv:label name="">Images</dhv:label>
    </td>
  </tr>
</table>
<br />
<dhv:formMessage showSpace="false"/>
<% String param1 = "productId=" + productCatalog.getId(); %>
<% String param2 = "moduleId=" + permissionCategory.getId(); %>
<% String param3 = "categoryId=" + productCategory.getId(); %>
<dhv:container name="productcatalogs" selected="images" object="productCatalog" param="<%= param1 + "|" + param2 + "|" + param3 %>">
  <table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
    <tr>
      <th><dhv:label name="product.fileType">File Type</dhv:label></th>
      <th width="8">&nbsp;</th>
      <th><dhv:label name="documents.documents.fileName">File Name</dhv:label></th>
      <th style="text-align: center;"><dhv:label name="accounts.accounts_documents_details.Size">Size</dhv:label></th>
      <th style="text-align: center;"><dhv:label name="quotes.date">Date</dhv:label></th>
    </tr>
    <tr class="containerBody">
      <td class="formLabel">
        <dhv:label name="product.thumbnailImage">Thumbnail Image</dhv:label>
      </td>
      <dhv:evaluate if="<%= ThumbnailImage.getId() != -1 %>">
      <td width="10" valign="middle" style="text-align: center;" nowrap>
        <a href="ProductCatalogs.do?command=DownloadFile&productId=<%= productCatalog.getId() %>&categoryId=<%= productCategory.getId() %>&fid=<%= ThumbnailImage.getId() %>"><dhv:label name="button.download">Download</dhv:label></a><br />
        <a href="javascript:confirmDelete('ProductCatalogs.do?command=RemoveFile&fid=<%= ThumbnailImage.getId() %>&productId=<%= productCatalog.getId()%>&categoryId=<%= productCategory.getId() %>&moduleId=<%= permissionCategory.getId() %>&imageType=thumbnail');"><dhv:label name="button.remove">Remove</dhv:label></a>
      </td>
      <td valign="middle" width="100%">
        <dhv:fileItemImage id="<%=  ThumbnailImage.getId() %>" path="products" thumbnail="true" name="<%= ThumbnailImage.getClientFilename() %>" />
      </td>
      <td style="text-align: center;" valign="middle" nowrap>
        <%= ThumbnailImage.getRelativeSize() %> <dhv:label name="admin.oneThousand.abbreviation">k</dhv:label>&nbsp;
      </td>
      <td nowrap valign="middle">
        <%= ThumbnailImage.getModifiedDateTimeString() %><br>
        <dhv:username id="<%= ThumbnailImage.getEnteredBy() %>"/>
      </td>
      </dhv:evaluate>
      <dhv:evaluate if="<%= ThumbnailImage.getId() == -1 %>">
      <td colspan="4">
        <font color="red"><dhv:label name="product.noImageAvailable">No Image Available.</dhv:label></font> [ <a href="javascript:showUploadSpan('thumbnail');">Upload</a> ]
      </td>
      </dhv:evaluate>
    </tr>
    
    <tr class="containerBody">
      <td class="formLabel" nowrap>
        <dhv:label name="product.smallImage">Small Image</dhv:label>
      </td>
      <dhv:evaluate if="<%= SmallImage.getId() != -1 %>">
      <td width="10" valign="middle" style="text-align: center;" nowrap>
        <a href="ProductCatalogs.do?command=DownloadFile&productId=<%= productCatalog.getId() %>&categoryId=<%= productCategory.getId() %>&fid=<%= SmallImage.getId() %>"><dhv:label name="button.download">Download</dhv:label></a><br />
        <a href="javascript:confirmDelete('ProductCatalogs.do?command=RemoveFile&fid=<%= SmallImage.getId() %>&productId=<%= productCatalog.getId()%>&categoryId=<%= productCategory.getId() %>&moduleId=<%= permissionCategory.getId() %>&imageType=small');"><dhv:label name="button.remove">Remove</dhv:label></a>
      </td>
      <td valign="middle" width="100%">
        <dhv:fileItemImage id="<%=  SmallImage.getId() %>" path="products" thumbnail="true" name="<%=  SmallImage.getClientFilename() %>" />
      </td>
      <td style="text-align: center;" valign="middle" nowrap>
        <%= SmallImage.getRelativeSize() %> <dhv:label name="admin.oneThousand.abbreviation">k</dhv:label>&nbsp;
      </td>
      <td nowrap valign="middle">
        <%= SmallImage.getModifiedDateTimeString() %><br>
        <dhv:username id="<%= SmallImage.getEnteredBy() %>"/>
      </td>
      </dhv:evaluate>
      <dhv:evaluate if="<%= SmallImage.getId() == -1 %>">
      <td colspan="4">
        <font color="red"><dhv:label name="product.noImageAvailable">No Image Available.</dhv:label></font> [ <a href="javascript:showUploadSpan('small');"><dhv:label name="global.button.Upload">Upload</dhv:label></a> ]
      </td>
      </dhv:evaluate>
    </tr>
    
    <tr class="containerBody">
      <td class="formLabel" nowrap>
        <dhv:label name="product.largeImage">Large Image</dhv:label>
      </td>
      <dhv:evaluate if="<%= LargeImage.getId() != -1 %>">
      <td width="10" valign="middle" style="text-align: center;" nowrap>
        <a href="ProductCatalogs.do?command=DownloadFile&productId=<%= productCatalog.getId() %>&categoryId=<%= productCategory.getId() %>&fid=<%= LargeImage.getId() %>"><dhv:label name="button.download">Download</dhv:label></a><br />
        <a href="javascript:confirmDelete('ProductCatalogs.do?command=RemoveFile&fid=<%= LargeImage.getId() %>&productId=<%= productCatalog.getId()%>&categoryId=<%= productCategory.getId() %>&moduleId=<%= permissionCategory.getId() %>&imageType=large');"><dhv:label name="button.remove">Remove</dhv:label></a>
      </td>
      <td valign="middle" width="100%">
        <dhv:fileItemImage id="<%=  LargeImage.getId() %>" path="products" thumbnail="true" name="<%=  LargeImage.getClientFilename() %>" />
      </td>
      <td style="text-align: center;" valign="middle" nowrap>
        <%= LargeImage.getRelativeSize() %> <dhv:label name="admin.oneThousand.abbreviation">k</dhv:label>&nbsp;
      </td>
      <td nowrap valign="middle">
        <%= LargeImage.getModifiedDateTimeString() %><br>
        <dhv:username id="<%= LargeImage.getEnteredBy() %>"/>
      </td>
      </dhv:evaluate>
      <dhv:evaluate if="<%= LargeImage.getId() == -1 %>">
      <td colspan="4">
        <font color="red"><dhv:label name="product.noImageAvailable">No Image Available.</dhv:label></font> [ <a href="javascript:showUploadSpan('large');"><dhv:label name="global.button.Upload">Upload</dhv:label></a> ]
      </td>
      </dhv:evaluate>
    </tr>
    <tr class="containerBody">
    <%= showError(request, "actionError") %>
    </tr>
  </table>
  &nbsp;<br />
  <span name="uploadSpan" id="uploadSpan" style="display:none">
    <table cellpadding="4" cellspacing="0" width="100%" class="details">
      <tr>
        <th>
          <strong><dhv:label name="product.attachFile">Attach a file</dhv:label><span name="displaySpan" id="displaySpan"></span></strong>
        </th>
      </tr>
      <tr class="containerBody">
        <form method="post" name="inputForm" action="ProductCatalogs.do?command=UploadFile&id=<%= productCatalog.getId() %>&productId=<%= productCatalog.getId() %>&categoryId=<%= productCategory.getId() %>&moduleId=<%= permissionCategory.getId() %>" onSubmit="return checkForm(this);" enctype="multipart/form-data">
          <td width="100%">
            <center>
              <input type="hidden" value="" name="imageType" id="imageType"/>
              <input type="file" name="id<%= productCatalog.getId() %>" size="30"><br>
              <dhv:label name="product.largeFileUploadStatement" param="break=<br />">* Large files may take a while to upload.<br />Wait for file completion message when upload is complete.</dhv:label><br />
              <input type="submit" value="<dhv:label name="documents.documents.uploadFile">Upload File</dhv:label>" name="upload">
            </center>
          </td>
        </form>
      </tr>
    </table>
  </span>
</dhv:container>
