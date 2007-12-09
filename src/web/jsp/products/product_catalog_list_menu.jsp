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
      hideSpan('menuDisableCatalog');
      showSpan('menuEnableCatalog');
    } else if(status == 1) {
      hideSpan('menuEnableCatalog');
      showSpan('menuDisableCatalog');
    } else {
      hideSpan('menuEnableCatalog');
      hideSpan('menuDisableCatalog');
    }
    return ypSlideOutMenu.displayDropMenu(id, loc);
  }
  //Menu link functions
  function details() {
    window.location.href = 'ProductCatalogs.do?command=Details&catalogId=' + thisCatalogId + '&moduleId=<%= PermissionCategory.getId() %>';
  }
  
  function modify() {
    window.location.href = 'ProductCatalogs.do?command=Modify&catalogId=' + thisCatalogId + '&moduleId=<%= PermissionCategory.getId() %>' + '&return=list';
  }
  
  function enable() {
    window.location.href = 'ProductCatalogs.do?command=EnableProduct&catalogId=' + thisCatalogId + '&moduleId=<%= PermissionCategory.getId() %>' + '&return=list' + '&status=ON';
  }
  
  function disable() {
    window.location.href = 'ProductCatalogs.do?command=EnableProduct&catalogId=' + thisCatalogId + '&moduleId=<%= PermissionCategory.getId() %>' + '&return=list' + '&status=OFF';
  }
  
  function deleteCatalog() {
    popURLReturn('ProductCatalogs.do?command=ConfirmDelete&catalogId=' + thisCatalogId + '&moduleId=<%= PermissionCategory.getId() %>' + '&popup=true','ProductCategories.do?command=List&moduleId=<%= PermissionCategory.getId() %>', 'Delete_productcatalog','330','200','yes','no');
  }
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
      <tr onmouseover="cmOver(this)" onmouseout="cmOut(this)" onclick="modify()">
        <th>
          <img src="images/icons/stock_edit-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td width="100%">
          <dhv:label name="button.modify">Modify</dhv:label>
        </td>
      </tr>
      <tr id="menuEnableCatalog" onmouseover="cmOver(this)" onmouseout="cmOut(this)" onclick="enable()">
        <th>
          <img src="images/icons/stock_archive-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td>
          <dhv:label name="button.enable">Enable</dhv:label>
        </td>
      </tr>
      <tr id="menuDisableCatalog" onmouseover="cmOver(this)" onmouseout="cmOut(this)" onclick="disable()">
        <th>
          <img src="images/icons/stock_archive-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td>
          <dhv:label name="button.disable">Diasble</dhv:label>
        </td>
      </tr>
<%--
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
