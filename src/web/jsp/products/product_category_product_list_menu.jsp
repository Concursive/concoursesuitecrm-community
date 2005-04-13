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
  var thisCatalogId = -1;
  var menu_init = false;
  //Set the action parameters for clicked item
  function displayMenu(loc, id, catalogId, status) {
    thisCatalogId = catalogId;
    if (!menu_init) {
      menu_init = true;
      new ypSlideOutMenu("menuCatalog", "down", 0, 0, 170, getHeight("menuCatalogTable"));
    }

    if(status == 0){
      hideSpan('menuArchiveCatalog');
      showSpan('menuReEnableCatalog');
    }else if(status == 1){
      hideSpan('menuReEnableCatalog');
      showSpan('menuArchiveCatalog');
    }else{
      hideSpan('menuReEnableCatalog');
      hideSpan('menuArchiveCatalog');
    }
    return ypSlideOutMenu.displayDropMenu(id, loc);
  }
  //Menu link functions
  function details() {
    window.location.href = 'ProductCategoryProducts.do?command=Details&catalogId=' + thisCatalogId + '&moduleId=<%= PermissionCategory.getId() %>' + '&categoryId=<%= ProductCategory.getId() %>';
  }
  
  /*
  function modify() {
    window.location.href = 'ProductCategoryProducts.do?command=Modify&catalogId=' + thisCatalogId + '&moduleId=<%= PermissionCategory.getId() %>&categoryId=<%= ProductCategory.getId() %>' + '&return=list';
  }
  */
  
  /*
  function enable() {
    window.location.href = 'ProductCategoryProducts.do?command=Enable&catalogId=' + thisCatalogId + '&moduleId=<%= PermissionCategory.getId() %>&categoryId=<%= ProductCategory.getId() %>' + '&return=list';
  }
  */
  
  /*
  function deleteCatalog() {
    popURLReturn('ProductCategoryProducts.do?command=ConfirmDelete&catalogId=' + thisCatalogId + '&moduleId=<%= PermissionCategory.getId() %>&categoryId=<%= ProductCategory.getId() %>' + '&popup=true','ProductCategories.do?command=List&moduleId=<%= PermissionCategory.getId() %>', 'Delete_productcatalog','330','200','yes','no');
  }
  */
</script>
<div id="menuCatalogContainer" class="menu">
  <div id="menuCatalogContent">
    <table id="menuCatalogTable" class="pulldown" width="170" cellspacing="0">
      <tr onmouseover="cmOver(this)" onmouseout="cmOut(this)" onclick="details()">
        <th>
          <img src="images/icons/stock_zoom-page-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td width="100%">
          <dhv:label name="accounts.accounts_calls_list_menu.ViewDetails">View Details</dhv:label>
        </td>
      </tr>
      <%--
      <tr onmouseover="cmOver(this)" onmouseout="cmOut(this)" onclick="modify()">
        <th>
          <img src="images/icons/stock_edit-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td width="100%">
          Modify
        </td>
      </tr>
      <tr id="menuArchiveCatalog" onmouseover="cmOver(this)" onmouseout="cmOut(this)" onclick="archive()">
        <th>
          <img src="images/icons/stock_archive-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td>
          <dhv:label name="accounts.accounts_list_menu.Archive">Archive</dhv:label>
        </td>
      </tr>
      <tr id="menuReEnableCatalog" onmouseover="cmOver(this)" onmouseout="cmOut(this)" onclick="enable()">
        <th>
          <img src="images/icons/stock_archive-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td>
          Un-Archive
        </td>
      </tr>
      <tr onmouseover="cmOver(this)" onmouseout="cmOut(this)" onclick="deleteCatalog()">
        <th>
          <img src="images/icons/stock_delete-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td>
          Delete
        </td>
      </tr>
      --%>
    </table>
  </div>
</div>
