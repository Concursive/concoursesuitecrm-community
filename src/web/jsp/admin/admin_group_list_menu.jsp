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
  var thisGroupId = -1;
  var menu_init = false;
  var permisison = -1;
  //Set the action parameters for clicked item
  function displayMenu(loc, id, groupId, perm) {
    thisGroupId = groupId;
    permission = perm;
    updateMenu();
    if (!menu_init) {
      menu_init = true;
      new ypSlideOutMenu("menuGroup", "down", 0, 0, 170, getHeight("menuGroupTable"));
    }
    return ypSlideOutMenu.displayDropMenu(id, loc);
  }
  
  function updateMenu() {
    if (permission > <%= PermissionCategory.VIEW %>) {
      showSpan('view');
      showSpan('modify');
      showSpan('delete');
    } else if (permission == <%= PermissionCategory.VIEW %>) {
      showSpan('view');
      hideSpan('modify');
      hideSpan('delete');
    } else {
      hideSpan('view');
      hideSpan('modify');
      hideSpan('delete');
    }
  }
  //Menu link functions
  function details() {
    window.location.href = 'UserGroups.do?command=Details&groupId=' + thisGroupId;
  }
  
  function modify() {
    window.location.href = 'UserGroups.do?command=Modify&groupId=' + thisGroupId;
  }
  
  function deleteGroup() {
    popURLReturn('UserGroups.do?command=ConfirmDelete&groupId=' + thisGroupId + '&popup=true','UserGroups.do?command=List', 'Delete_group','320','200','yes','no');
  }
</script>
<div id="menuGroupContainer" class="menu">
  <div id="menuGroupContent">
    <table id="menuGroupTable" class="pulldown" width="170" cellspacing="0">
      <dhv:permission name="admin-view">
      <tr id="view" onmouseover="cmOver(this)" onmouseout="cmOut(this)" onclick="details();">
        <th>
          <img src="images/icons/stock_zoom-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td width="100%">
          <dhv:label name="accounts.accounts_calls_list_menu.ViewDetails">View Details</dhv:label>
        </td>
      </tr>
      </dhv:permission>
      <dhv:permission name="admin-view">
      <tr id="modify" onmouseover="cmOver(this)" onmouseout="cmOut(this)" onclick="modify();">
        <th>
          <img src="images/icons/stock_edit-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td width="100%">
          <dhv:label name="global.button.modify">View Details</dhv:label>
        </td>
      </tr>
      </dhv:permission>
      <dhv:permission name="admin-view">
      <tr id="delete" onmouseover="cmOver(this)" onmouseout="cmOut(this)" onclick="deleteGroup();">
        <th>
          <img src="images/icons/stock_delete-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td>
          <dhv:label name="global.button.delete">Delete</dhv:label>
        </td>
      </tr>
      </dhv:permission>
    </table>
  </div>
</div>
