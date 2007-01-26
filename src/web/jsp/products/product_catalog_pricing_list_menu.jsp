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
  var thisPriceId = -1;
  var thisType = "";
  var menu_init = false;
  //Set the action parameters for clicked item
  function displayMenu(loc, id, priceId, type, trashed) {
    thisPriceId = priceId;
    thisType = type;
    updateMenu(trashed);
    if (!menu_init) {
      menu_init = true;
      new ypSlideOutMenu("menuPricing", "down", 0, 0, 170, getHeight("menuPricingTable"));
    }
	  return ypSlideOutMenu.displayDropMenu(id, loc);
  }

  function updateMenu(trashed) {
    if (trashed == 'true'){
      hideSpan('menuEnable');
      hideSpan('menuDisable');
    } else {
      if (thisType == 'active') {
        showSpan('menuDisable');
        hideSpan('menuEnable');
      } else if (thisType == 'inactive') {
        showSpan('menuEnable');
        hideSpan('menuDisable');
      }
    }
  }

  function details() {
		window.location.href='ProductCatalogPricings.do?command=PricingDetails&productId=<%= productCatalog.getId() %>&pricingId=' + thisPriceId + '&moduleId=<%= permissionCategory.getId() %>&categoryId=<%= productCategory.getId() %>';
	}
  function modify() {
		window.location.href='ProductCatalogPricings.do?command=ModifyPricing&productId=<%= productCatalog.getId() %>&pricingId=' + thisPriceId + '&moduleId=<%= permissionCategory.getId() %>&categoryId=<%= productCategory.getId() %>';
	}
  function disable() {
    confirmDelete('ProductCatalogPricings.do?command=DisablePricing&productId=<%= productCatalog.getId() %>&pricingId=' + thisPriceId + '&moduleId=<%= permissionCategory.getId() %>&categoryId=<%= productCategory.getId() %>');
  }
  function enable() {
    confirmDelete('ProductCatalogPricings.do?command=EnablePricing&productId=<%= productCatalog.getId() %>&pricingId=' + thisPriceId + '&moduleId=<%= permissionCategory.getId() %>&categoryId=<%= productCategory.getId() %>');
  }
</script>
<div id="menuPricingContainer" class="menu">
  <div id="menuPricingContent">
    <table id="menuPricingTable" class="pulldown" width="170" cellspacing="0">
      <dhv:permission name="product-catalog-product-view">
      <tr onmouseover="cmOver(this)" onmouseout="cmOut(this)" onclick="details()">
        <th>
          <img src="images/icons/stock_zoom-page-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td>
          <dhv:label name="accounts.accounts_calls_list_menu.ViewDetails">View Details</dhv:label>
        </td>
      </tr>
      </dhv:permission>
      <dhv:permission name="product-catalog-product-edit">
      <tr onmouseover="cmOver(this)" onmouseout="cmOut(this)" onclick="modify()">
        <th>
          <img src="images/icons/stock_edit-16.gif" border="0" align="absmiddle" height="16" width="16" />
        </th>
        <td>
          <dhv:label name="product.modifyPrice">Modify Price</dhv:label>
        </td>
      </tr>
      </dhv:permission>
      <dhv:permission name="product-catalog-product-edit">
			<tr id="menuEnable" onmouseover="cmOver(this)" onmouseout="cmOut(this)" onclick="enable()">
        <th>
          <img src="images/icons/stock_delete-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td>
          <dhv:label name="button.enable">Enable</dhv:label>
        </td>
      </tr>
      </dhv:permission>
      <dhv:permission name="product-catalog-product-edit">
      <tr id="menuDisable" onmouseover="cmOver(this)" onmouseout="cmOut(this)" onclick="disable()">
        <th>
          <img src="images/icons/stock_delete-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td>
          <dhv:label name="button.disable">Disable</dhv:label>
        </td>
      </tr>
      </dhv:permission>
    </table>
  </div>
</div>
