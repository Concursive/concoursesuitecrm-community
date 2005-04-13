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
  var thisOptionId = -1;
  var thisConfigId = -1;
  var menu_init = false;
  //Set the action parameters for clicked item
  function displayMenu(loc, id, optionId, configId, status) {
    thisOptionId = optionId;
    thisConfigId = configId;
    if (!menu_init) {
      menu_init = true;
      new ypSlideOutMenu("menuOption", "down", 0, 0, 170, getHeight("menuOptionTable"));
    }
    return ypSlideOutMenu.displayDropMenu(id, loc);
  }
  //Menu link functions
  function details() {
    window.location.href = 'ProductCatalogOptions.do?command=Details&optionId=' + thisOptionId + '&moduleId=<%= permissionCategory.getId() %>&productId=<%= productCatalog.getId() %>&categoryId=<%= productCategory.getId() %>';
  }
  
  function enable() {
    window.location.href = 'ProductCatalogOptions.do?command=Enable&optionId=' + thisOptionId + '&moduleId=<%= permissionCategory.getId() %>' + '&productId=<%= productCatalog.getId() %>' + '&return=list';
  }
  
  function modify() {
    window.location.href = 'ProductCatalogOptions.do?command=LoadConfigurator&optionId=' + thisOptionId + '&moduleId=<%= permissionCategory.getId() %>&productId=<%= productCatalog.getId() %>&categoryId=<%= productCategory.getId() %>&action=modify&configId=' + thisConfigId + '&return=list';
  }
  
  function deleteOption() {
    popURLReturn('ProductCatalogOptions.do?command=ConfirmDelete&optionId=' + thisOptionId + '&moduleId=<%= permissionCategory.getId() %>&productId=<%= productCatalog.getId() %>&categoryId=<%= productCategory.getId() %>&popup=true', 'ProductCatalogOptions.do?command=List', 'Delete_productoption', '330', '200', 'yes', 'no');
  }
</script>
<div id="menuOptionContainer" class="menu">
  <div id="menuOptionContent">
    <table id="menuOptionTable" class="pulldown" width="170" cellspacing="0">
      <dhv:permission name="admin-sysconfig-products-view">
      <tr onmouseover="cmOver(this)" onmouseout="cmOut(this)" onclick="details()">
        <th>
          <img src="images/icons/stock_zoom-page-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td width="100%">
          <dhv:label name="accounts.accounts_calls_list_menu.ViewDetails">View Details</dhv:label>
        </td>
      </tr>
      </dhv:permission>
      <%--
      <tr id="menuArchiveOption" onmouseover="cmOver(this)" onmouseout="cmOut(this)" onclick="enable()">
        <th>
          <img src="images/icons/stock_archive-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td>
          Enable
        </td>
      </tr>
      --%>
      <dhv:permission name="admin-sysconfig-products-edit">
      <tr onmouseover="cmOver(this)" onmouseout="cmOut(this)" onclick="modify()">
        <th>
          <img src="images/icons/stock_edit-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td>
          <dhv:label name="button.modify">Modify</dhv:label>
        </td>
      </tr>
      </dhv:permission>
      <dhv:permission name="admin-sysconfig-products-delete">
      <tr onmouseover="cmOver(this)" onmouseout="cmOut(this)" onclick="deleteOption()">
        <th>
          <img src="images/icons/stock_delete-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td>
          <dhv:label name="button.delete">Delete</dhv:label>
        </td>
      </tr>
      </dhv:permission>
    </table>
  </div>
</div>
