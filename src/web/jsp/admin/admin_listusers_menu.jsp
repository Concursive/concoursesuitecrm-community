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
  var thisUserId = -1;
  var menu_init = false;
  //Set the action parameters for clicked item
  function displayMenu(loc, id, userId, status, contactDeleted) {
    thisUserId = userId;
    if (!menu_init) {
      menu_init = true;
      if(document.getElementById('menuDisable') != null && document.getElementById('menuEnable') != null){
        if (contactDeleted == 'true'){
          hideSpan('menuEnable');
          hideSpan('menuDisable');
          hideSpan('menuModify');
        } else {
          showSpan('menuModify');
          if(status == 0){
            hideSpan('menuDisable');
            showSpan('menuEnable');
          }else if(status == 1){
            hideSpan('menuEnable');
            showSpan('menuDisable');
          }else{
            hideSpan('menuEnable');
            hideSpan('menuDisable');
          }
        }
      }
      new ypSlideOutMenu("menuUser", "down", 0, 0, 170, getHeight("menuUserTable"));
    }
    return ypSlideOutMenu.displayDropMenu(id, loc);
  }
  //Menu link functions
  function details() {
    window.location.href = 'Users.do?command=UserDetails&id=' + thisUserId;
  }

  function modify() {
    window.location.href = 'Users.do?command=ModifyUser&id=' + thisUserId + '&return=list';
  }

  function disable() {
    window.location.href = 'Users.do?command=DisableUserConfirm&id=' + thisUserId + '&return=list';
  }

  function enable() {
    window.location.href = 'Users.do?command=EnableUser&id=' + thisUserId + '&return=list';
  }
</script>
<div id="menuUserContainer" class="menu">
  <div id="menuUserContent">
    <table id="menuUserTable" class="pulldown" width="170" cellspacing="0">
      <dhv:permission name="admin-users-view">
      <tr onmouseover="cmOver(this)" onmouseout="cmOut(this)" onclick="details()">
        <th>
          <img src="images/icons/stock_zoom-page-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td width="100%">
          <dhv:label name="accounts.accounts_calls_list_menu.ViewDetails">View Details</dhv:label>
        </td>
      </tr>
      </dhv:permission>
      <dhv:permission name="admin-users-edit">
      <tr id="menuModify" onmouseover="cmOver(this)" onmouseout="cmOut(this)" onclick="modify()">
        <th>
          <img src="images/icons/stock_edit-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td width="100%">
          <dhv:label name="global.button.modify">Modify</dhv:label>
        </td>
      </tr>
      </dhv:permission>
      <dhv:permission name="admin-users-delete">
      <tr id="menuDisable" onmouseover="cmOver(this)" onmouseout="cmOut(this)" onclick="disable()">
        <th>
          <img src="images/icons/stock_toggle-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td>
          <dhv:label name="admin.disableLogin">Disable Login</dhv:label>
        </td>
      </tr>
      </dhv:permission>
      <dhv:permission name="admin-users-delete">
      <tr id="menuEnable" onmouseover="cmOver(this)" onmouseout="cmOut(this)" onclick="enable()">
        <th>
          <img src="images/icons/stock_toggle-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>        
        <td>
          <dhv:label name="admin.enableLogin">Enable Login</dhv:label>
        </td>
      </tr>
      </dhv:permission>
    </table>
  </div>
</div>
