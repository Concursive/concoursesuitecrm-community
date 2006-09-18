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
<script language="javascript">
  var thisProductId = -1;
  var thisCategoryId = -1;
  var thisParentCatId = -1
  var thisView = "";
  var menu_init = false;
  //Set the action parameters for clicked item
  function displayMenu(loc, id, productId, categoryId, parentCatId, view, trashed) {
    thisProductId = productId;
    thisCategoryId = categoryId;
    thisParentCatId = parentCatId;
    thisView = view;
    updateMenu(trashed);
    if (!menu_init) {
      menu_init = true;
      new ypSlideOutMenu("menuCatalog", "down", 0, 0, 170, getHeight("menuCatalogTable"));
    }
    return ypSlideOutMenu.displayDropMenu(id, loc);
  }

  function updateMenu(trashed) {
    if (trashed == 'true'){
      hideSpan('menuModifyProduct');
      hideSpan('menuCloneProduct');
      hideSpan('menuMoveProduct');
      hideSpan('menuDeleteProduct');
      hideSpan('menuRenameCategory');
      hideSpan('menuMoveCategory');
      hideSpan('menuDeleteCategory');
    } else {
      if (thisView == 'PRODUCT') {
        showSpan('menuViewProduct');
        showSpan('menuModifyProduct');
        showSpan('menuCloneProduct');
        showSpan('menuMoveProduct');
        showSpan('menuDeleteProduct');
        hideSpan('menuRenameCategory');
        hideSpan('menuMoveCategory');
        hideSpan('menuDeleteCategory');
      } else if (thisView == 'CATEGORY') {
        showSpan('menuRenameCategory');
        showSpan('menuMoveCategory');
        showSpan('menuDeleteCategory');
        hideSpan('menuViewProduct');
        hideSpan('menuModifyProduct');
        hideSpan('menuCloneProduct');
        hideSpan('menuMoveProduct');
        hideSpan('menuDeleteProduct');
      }
    }
  }

  //Product Category Functions
  function renameCategory() {
    window.location.href = 'ProductCategories.do?command=Modify&catId=' + thisCategoryId + '&categoryId=' + thisParentCatId + '&moduleId=<%= permissionCategory.getId() %>';
  }
  function moveCategory() {
    popURL('ProductCategories.do?command=Move&categoryId=' + thisCategoryId + '&moduleId=<%= permissionCategory.getId() %>&popup=true&return=ProductCatalogEditor.do?command=List', 'Categories', '400','375','yes','yes'); 
  }
  function deleteCategory() {
    popURLReturn('ProductCategories.do?command=ConfirmDelete&categoryId=' + thisCategoryId + '&popup=true','ProductCatalogEditor.do?command=List&moduleId=<%= permissionCategory.getId() %>&parentId=' + thisParentCatId, 'Delete_productcategory','330','200','yes','no');
  }
  
  //Product Funtions
  function viewProductDetails() {
    window.location.href = 'ProductCatalogs.do?command=Details&productId=' + thisProductId + '&categoryId=' + thisCategoryId + '&moduleId=<%= permissionCategory.getId() %>';
  }
  function modifyProduct() {
    window.location.href = 'ProductCatalogs.do?command=Modify&productId=' + thisProductId + '&categoryId=' + thisCategoryId + '&moduleId=<%= permissionCategory.getId() %>&return=list';
  }
  function cloneProduct() {
    window.location.href = 'ProductCatalogs.do?command=Clone&productId=' + thisProductId + '&categoryId=' + thisCategoryId + '&moduleId=<%= permissionCategory.getId() %>&actionReq=clone';
  }
  function moveProduct() {
    popURL('ProductCatalogs.do?command=Move&productId=' + thisProductId + '&categoryId=' + thisCategoryId + '&moduleId=<%= permissionCategory.getId() %>&return=ProductCatalogEditor.do?command=List', 'Products', '400', '375', 'yes', 'yes');
  }
  function deleteProduct() {
    popURLReturn('ProductCatalogs.do?command=ConfirmDelete&productId=' + thisProductId + '&popup=true','ProductCatalogEditor.do?command=List&moduleId=<%= permissionCategory.getId() %>&categoryId=' + thisCategoryId , 'Delete_productcatalog','330','200','yes','no');
  }
  
  
</script>
<div id="menuCatalogContainer" class="menu">
  <div id="menuCatalogContent">
    <table id="menuCatalogTable" class="pulldown" width="170" cellspacing="0">
      <dhv:permission name="product-catalog-view">
      <tr id="menuViewProduct" onmouseover="cmOver(this)" onmouseout="cmOut(this)" onclick="viewProductDetails()">
        <th><img src="images/icons/stock_zoom-page-16.gif" border="0" align="absmiddle" height="16" width="16"/></th>
        <td width="100%"><dhv:label name="product.viewProduct">View Product</dhv:label></td>
      </tr>
      </dhv:permission>
      <dhv:permission name="product-catalog-edit">
      <tr id="menuModifyProduct" onmouseover="cmOver(this)" onmouseout="cmOut(this)" onclick="modifyProduct()">
        <th><img src="images/icons/stock_edit-16.gif" border="0" align="absmiddle" height="16" width="16"/></th>
        <td width="100%"><dhv:label name="product.modifyProduct">Modify Product</dhv:label></td>
      </tr>
      </dhv:permission>
      <dhv:permission name="product-catalog-add">
      <tr id="menuCloneProduct" onmouseover="cmOver(this)" onmouseout="cmOut(this)" onclick="cloneProduct()">
        <th><img src="images/icons/stock_copy-16.gif" border="0" align="absmiddle" height="16" width="16"/></th>
        <td width="100%"><dhv:label name="product.cloneProduct">Clone Product</dhv:label></td>
      </tr>
      </dhv:permission>
      <dhv:permission name="product-catalog-edit">
      <tr id="menuMoveProduct" onmouseover="cmOver(this)" onmouseout="cmOut(this)" onclick="moveProduct()">
        <th><img src="images/icons/stock_drag-mode-16.gif" border="0" align="absmiddle" height="16" width="16"/></th>
        <td width="100%"><dhv:label name="product.manageCategoryAssociations">Manage Category Associations</dhv:label></td>
      </tr>
      </dhv:permission>
      <dhv:permission name="product-catalog-delete">
      <tr id="menuDeleteProduct" onmouseover="cmOver(this)" onmouseout="cmOut(this)" onclick="deleteProduct()">
        <th><img src="images/icons/stock_delete-16.gif" border="0" align="absmiddle" height="16" width="16"/></th>
        <td width="100%"><dhv:label name="product.deleteProduct">Delete Product</dhv:label></td>
      </tr>
      </dhv:permission>
      <dhv:permission name="product-catalog-edit">
      <tr id="menuRenameCategory" onmouseover="cmOver(this)" onmouseout="cmOut(this)" onclick="renameCategory()">
        <th><img src="images/icons/stock_edit-16.gif" border="0" align="absmiddle" height="16" width="16"/></th>
        <td width="100%"><dhv:label name="product.renameCategory">Rename Category</dhv:label></td>
      </tr>
      </dhv:permission>
      <dhv:permission name="product-catalog-edit">
      <tr id="menuMoveCategory" onmouseover="cmOver(this)" onmouseout="cmOut(this)" onclick="moveCategory()">
        <th><img src="images/icons/stock_drag-mode-16.gif" border="0" align="absmiddle" height="16" width="16"/></th>
        <td width="100%"><dhv:label name="product.moveCategory">Move Category</dhv:label></td>
      </tr>
      </dhv:permission>
      <dhv:permission name="product-catalog-delete">
      <tr id="menuDeleteCategory" onmouseover="cmOver(this)" onmouseout="cmOut(this)" onclick="deleteCategory()">
        <th><img src="images/icons/stock_delete-16.gif" border="0" align="absmiddle" height="16" width="16"/></th>
        <td width="100%"><dhv:label name="product.deleteCategory">Delete Category</dhv:label></td>
      </tr>
      </dhv:permission>
    </table>
  </div>
</div>
