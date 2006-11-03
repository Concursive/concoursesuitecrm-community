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
<%@ page import="java.util.*,org.aspcfs.modules.products.base.*,com.zeroio.iteam.base.*" %>
<jsp:useBean id="productDetails" class="org.aspcfs.modules.products.base.ProductCatalog" scope="request"/>
<jsp:useBean id="categoryList" class="org.aspcfs.modules.products.base.ProductCategoryList" scope="request"/>
<jsp:useBean id="PermissionCategory" class="org.aspcfs.modules.admin.base.PermissionCategory" scope="request"/>
<%@ include file="../initPage.jsp" %>
<form name="viewProduct" action="ProductsCatalog.do?command=ModifyProduct&productId=<%=productDetails.getId()%>&moduleId=<%= PermissionCategory.getId() %>&return=view" method="post">
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="ProductCatalogEditor.do?command=List"><dhv:label name="product.products">Products</dhv:label></a> >
<a href="ProductsCatalog.do?command=ListAllProducts&moduleId=<%= PermissionCategory.getId() %>"><dhv:label name="product.laborCategoryEditor">Labor Category Editor</dhv:label></a> >
<dhv:label name="product.itemDetails">Item Details</dhv:label>
</td>
</tr>
</table>
<%-- End Trails --%>
<table cellpadding="4" cellspacing="0" border="0" width="100%">
  <tr>
    <td>
      <dhv:permission name="product-catalog-product-edit"><input	type="submit" value="<dhv:label name="global.button.modify">Modify</dhv:label>" /></dhv:permission>
      <dhv:permission name="product-catalog-product-delete"><input type="button" value="<dhv:label name="global.button.delete">Delete</dhv:label>" onClick="javascript:popURLReturn('ProductsCatalog.do?command=ConfirmDeleteProduct&productId=<%=productDetails.getId()%>&moduleId=<%= PermissionCategory.getId() %>&popup=true','ProductsCatalog.do?command=ViewProduct&productId=<%=productDetails.getId()%>&moduleId=<%= PermissionCategory.getId() %>', 'Delete_product','330','250','yes','no');"></dhv:permission>
      <dhv:permission name="product-catalog-product-edit,product-catalog-product-delete" all="false"><br /><br /></dhv:permission>
        <table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
          <tr>
            <th colspan="2">
              <strong><dhv:label name="contacts.details">Details</dhv:label></strong>
            </th>
          </tr>
          <tr class="containerBody">
            <td class="formLabel">
              <dhv:label name="product.code">Code</dhv:label>
            </td>
            <td>
              <%=toHtml(productDetails.getSku())%>
            </td>
          </tr>
          <tr class="containerBody">
            <td class="formLabel">
              <dhv:label name="accounts.accountasset_include.Description">Description</dhv:label>
            </td>
            <td>
              <%=toHtml(productDetails.getName())%>
            </td>
          </tr>
          <tr class="containerBody">
            <td class="formLabel">
              <dhv:label name="accounts.accountasset_include.Category">Category</dhv:label>
            </td>
            <td>
              <%= toHtml(productDetails.getCategoryName()) %>
            </td>
          </tr>
        </table>
      <br />
      <dhv:permission name="product-catalog-product-edit"><input	type="submit" value="<dhv:label name="button.modify">Modify</dhv:label>" /></dhv:permission>
      <dhv:permission name="product-catalog-product-delete"><input type="button" value="<dhv:label name="global.button.delete">Delete</dhv:label>" onClick="javascript:popURLReturn('ProductsCatalog.do?command=ConfirmDeleteProduct&productId=<%=productDetails.getId()%>&moduleId=<%= PermissionCategory.getId() %>&popup=true','ProductsCatalog.do?command=ViewProduct&productId=<%=productDetails.getId()%>&moduleId=<%= PermissionCategory.getId() %>', 'Delete_product','330','250','yes','no');"></dhv:permission>
  </td>
  </tr>
</table>
</form>
