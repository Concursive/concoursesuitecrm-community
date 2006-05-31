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
  - Version: $Id:  $
  - Description: 
  --%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<script language="javascript">
  //Set the action parameters for clicked item
  function displayMenuItem(loc, id, itemId, categoryId) {
    thisItemId = itemId;
    thisCategoryId = categoryId;
    if (!menu_init) {
      menu_init = true;
      new ypSlideOutMenu("menuItem", "down", 0, 0, 170, getHeight("menuItemTable"));
      new ypSlideOutMenu("menuCategory", "down", 0, 0, 170, getHeight("menuCategoryTable"));
    }
    return ypSlideOutMenu.displayDropMenu(id, loc);
  }

  //Menu link functions
  function itemDetails() {
    window.location.href='PortfolioItemEditor.do?command=ItemDetails&categoryId='+thisCategoryId+'&itemId=' + thisItemId;
  }

  function modifyItem() {
    window.location.href='PortfolioItemEditor.do?command=ModifyItem&categoryId='+thisCategoryId+'&itemId='+ thisItemId+'&return=list';
  }
  function moveItem() {
    popURL('PortfolioItemEditor.do?command=Move&itemId='+ thisItemId +'&categoryId=' + thisCategoryId + '&popup=true&return=PortfolioEditor.do?command=List','MoveItem','400','375','yes','yes');
  }

  function deleteItem() {
    popURLReturn('PortfolioItemEditor.do?command=ConfirmDeleteItem&categoryId='+thisCategoryId+'&itemId=' + thisItemId+ '&popup=true','PortfolioEditor.do?command=List&categoryId='+thisCategoryId, 'Delete_Item','330','200','yes','no');
  }
</script>

<div id="menuItemContainer" class="menu">
  <div id="menuItemContent">
    <table id="menuItemTable" class="pulldown" width="170" cellspacing="0">
      <tr id="menuDetails" onmouseover="cmOver(this)" onmouseout="cmOut(this)" onclick="itemDetails();">
        <th>
          <img src="images/icons/stock_zoom-page-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td width="100%">
          <dhv:label name="accounts.accounts_calls_list_menu.ViewDetails">View Details</dhv:label>
        </td>
      </tr>
      <dhv:permission name="website-portfolio-edit">
      <tr id="menuModify" onmouseover="cmOver(this)" onmouseout="cmOut(this)" onclick="modifyItem();">
        <th>
          <img src="images/icons/stock_edit-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td width="100%">
          <dhv:label name="button.modify">Modify</dhv:label>
        </td>
      </tr>
      </dhv:permission>
      <dhv:permission name="website-portfolio-edit">
      <tr onmouseover="cmOver(this)" onmouseout="cmOut(this)" onclick="moveItem();">
        <th>
          <img src="images/icons/stock_drag-mode-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td>
          <dhv:label name="">Move Item</dhv:label>
        </td>
      </tr>
      </dhv:permission>
      <dhv:permission name="website-portfolio-delete">
      <tr id="menuDelete" onmouseover="cmOver(this)" onmouseout="cmOut(this)" onclick="deleteItem();">
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
