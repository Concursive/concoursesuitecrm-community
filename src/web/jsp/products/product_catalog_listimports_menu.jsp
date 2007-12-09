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
  - Version: $Id: product_catalog_listimports_menu.jsp 11310 2005-04-13 20:05:00 +0000 (Ср, 13 апр 2005) mrajkowski $
  - Description:
  --%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<script language="javascript">
  var thisImportId = -1;
  var menu_init = false;
  //Set the action parameters for clicked item
  function displayMenu(loc, id, importId, hasCancelOption, process, canDelete) {
    thisImportId = importId;
    updateMenu(hasCancelOption, process, canDelete);
    if (!menu_init) {
      menu_init = true;
      new ypSlideOutMenu("menuImport", "down", 0, 0, 170, getHeight("menuImportTable"));
    }
    return ypSlideOutMenu.displayDropMenu(id, loc);
  }

  function updateMenu(hasCancelOption, process, canDelete){
    if(document.getElementById('menuCancel') != null){
      if(hasCancelOption == 0){
          hideSpan('menuCancel');
      }else{
        showSpan('menuCancel');
      }
    }

    if(document.getElementById('menuProcess') != null){
      if(process == 0){
          hideSpan('menuProcess');
      }else{
        showSpan('menuProcess');
      }
    }

    if(document.getElementById('menuDelete') != null){
      if(canDelete == 0){
          hideSpan('menuDelete');
      }else{
        showSpan('menuDelete');
      }
    }
  }

  //Menu link functions
  function details() {
    window.location.href = 'ProductCatalogImports.do?command=Details&importId=' + thisImportId+'&moduleId=<%=permissionCategory.getId()%>';
  }

  function deleteAction() {
   popURLReturn('ProductCatalogImports.do?command=ConfirmDelete&importId=' + thisImportId+'&moduleId=<%=permissionCategory.getId()%>','ProductCatalogImports.do?command=View&moduleId=<%=permissionCategory.getId()%>', 'Delete_message','320','200','yes','no');
  }

  function cancel() {
   confirmDelete('ProductCatalogImports.do?command=Cancel&importId=' + thisImportId+'&moduleId=<%=permissionCategory.getId()%>');
  }

  function process() {
   window.location.href = 'ProductCatalogImports.do?command=InitValidate&return=list&importId=' + thisImportId+'&moduleId=<%=permissionCategory.getId()%>';
  }
</script>
<div id="menuImportContainer" class="menu">
  <div id="menuImportContent">
    <table id="menuImportTable" class="pulldown" width="170" cellspacing="0">
      <dhv:permission name="product-catalog-product-imports-view">
      <tr onmouseover="cmOver(this)" onmouseout="cmOut(this)"
          onclick="details()">
        <th>
          <img src="images/icons/stock_zoom-page-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td width="100%">
          <dhv:label name="products.viewDetails">View Details</dhv:label>
        </td>
      </tr>
      </dhv:permission>
      <dhv:permission name="product-catalog-product-imports-add">
      <tr id="menuProcess" onmouseover="cmOver(this)" onmouseout="cmOut(this)"
          onclick="process()">
        <th>
          <img src="images/icons/stock_compile-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td width="100%">
          <dhv:label name="products.process">Process</dhv:label>
        </td>
      </tr>
      </dhv:permission>
      <dhv:permission name="product-catalog-product-imports-add">
      <tr id="menuCancel" onmouseover="cmOver(this)" onmouseout="cmOut(this)"
          onclick="cancel()">
        <th>
          <img src="images/icons/stock_calc-cancel-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td width="100%">
          <dhv:label name="global.button.cancel">Cancel</dhv:label>
        </td>
      </tr>
      </dhv:permission>
      <dhv:permission name="product-catalog-product-imports-delete">
      <tr id="menuDelete" onmouseover="cmOver(this)" onmouseout="cmOut(this)"
          onclick="deleteAction()">
        <th>
          <img src="images/icons/stock_delete-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td width="100%">
          <dhv:label name="global.button.delete">Delete</dhv:label>
        </td>
      </tr>
      </dhv:permission>
    </table>
  </div>
</div>
