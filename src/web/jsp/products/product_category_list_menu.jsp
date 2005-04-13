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
  var thisCategoryId = -1;
  var menu_init = false;
  //Set the action parameters for clicked item
  function displayMenu(loc, id, categoryId, status) {
    thisCategoryId = categoryId;
    if (!menu_init) {
      menu_init = true;
      new ypSlideOutMenu("menuCategory", "down", 0, 0, 170, getHeight("menuCategoryTable"));
    }

    /*
    if(status == 0){
      hideSpan('menuArchiveCategory');
      showSpan('menuReEnableCategory');
    }else if(status == 1){
      hideSpan('menuReEnableCategory');
      showSpan('menuArchiveCategory');
    }else{
      hideSpan('menuReEnableCategory');
      hideSpan('menuArchiveCategory');
    }
    */
    return ypSlideOutMenu.displayDropMenu(id, loc);
  }
  //Menu link functions
  function details() {
    window.location.href = 'ProductCategories.do?command=Details&categoryId=' + thisCategoryId + '&moduleId=<%= PermissionCategory.getId() %>';
  }
  
  function modify() {
    window.location.href = 'ProductCategories.do?command=Modify&categoryId=' + thisCategoryId + '&moduleId=<%= PermissionCategory.getId() %>' + '&return=list';
  }
  
  /*
  function enable() {
    window.location.href = 'ProductCategories.do?command=Enable&categoryId=' + thisCategoryId + '&moduleId=<%= PermissionCategory.getId() %>' + '&return=list';
  }
  
  function archive() {
    window.location.href = 'ProductCategories.do?command=Delete&categoryId=' + thisCategoryId + '&moduleId=<%= PermissionCategory.getId() %>' + '&action=disable&return=list';
  }
  */
  /*
  function deleteCategory() {
    popURLReturn('ProductCategories.do?command=ConfirmDelete&categoryId=' + thisCategoryId+ '&popup=true','ProductCategories.do?command=List&moduleId=<%= PermissionCategory.getId() %>', 'Delete_productcategory','330','200','yes','no');
  }
  */
</script>
<div id="menuCategoryContainer" class="menu">
  <div id="menuCategoryContent">
    <table id="menuCategoryTable" class="pulldown" width="170" cellspacing="0">
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
      <%--
      <tr id="menuArchiveCategory" onmouseover="cmOver(this)" onmouseout="cmOut(this)" onclick="archive()">
        <th>
          <img src="images/icons/stock_archive-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td>
          <dhv:label name="accounts.accounts_list_menu.Archive">Archive</dhv:label>
        </td>
      </tr>
      <tr id="menuReEnableCategory" onmouseover="cmOver(this)" onmouseout="cmOut(this)" onclick="enable()">
        <th>
          <img src="images/icons/stock_archive-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td>
          Un-Archive
        </td>
      </tr>
      <tr onmouseover="cmOver(this)" onmouseout="cmOut(this)" onclick="deleteCategory()">
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
