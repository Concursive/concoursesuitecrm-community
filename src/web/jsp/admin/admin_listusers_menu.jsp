<%-- 
  - Copyright Notice: (C) 2000-2004 Dark Horse Ventures, All Rights Reserved.
  - License: This source code cannot be modified, distributed or used without
  -          written permission from Dark Horse Ventures. This notice must
  -          remain in place.
  - Version: $Id$
  - Description: 
  --%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<script language="javascript">
  var thisUserId = -1;
  var menu_init = false;
  //Set the action parameters for clicked item
  function displayMenu(loc, id, userId, status) {
    thisUserId = userId;
    if (!menu_init) {
      menu_init = true;
      if(document.getElementById('menuDisable') != null && document.getElementById('menuEnable') != null){
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
          View Details
        </td>
      </tr>
      </dhv:permission>
      <dhv:permission name="admin-users-edit">
      <tr onmouseover="cmOver(this)" onmouseout="cmOut(this)" onclick="modify()">
        <th>
          <img src="images/icons/stock_edit-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td width="100%">
          Modify
        </td>
      </tr>
      </dhv:permission>
      <dhv:permission name="admin-users-delete">
      <tr id="menuDisable" onmouseover="cmOver(this)" onmouseout="cmOut(this)" onclick="disable()">
        <th>
          <img src="images/icons/stock_toggle-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td>
          Disable Login
        </td>
      </tr>
      </dhv:permission>
      <dhv:permission name="admin-users-delete">
      <tr id="menuEnable" onmouseover="cmOver(this)" onmouseout="cmOut(this)" onclick="enable()">
        <th>
          <img src="images/icons/stock_toggle-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>        
        <td>
          Enable Login
        </td>
      </tr>
      </dhv:permission>
    </table>
  </div>
</div>
