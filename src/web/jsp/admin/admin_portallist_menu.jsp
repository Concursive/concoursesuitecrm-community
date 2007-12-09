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
  - Version: $Id: admin_listroles_menu.jsp 11310 2005-04-13 20:05:00 +0000 (Wed, 13 Apr 2005) mrajkowski $
  - Description: 
  --%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<script language="javascript">
  var thisRoleId = -1;
  var menu_init = false;
  //Set the action parameters for clicked item
  function displayMenu(loc, id, roleId, status) {
    thisRoleId = roleId;
    if (!menu_init) {
      menu_init = true;
    if (document.getElementById('menuDeactivate') != null && document.getElementById('menuActivate') != null) {
      showSpan('menuModify');
      showSpan('menuDelete');
      if (status == 0) {
        hideSpan('menuDeactivate');
        showSpan('menuActivate');
      } else if (status == 1) {
        hideSpan('menuActivate');
        showSpan('menuDeactivate');
      } else {
        hideSpan('menuActivate');
        hideSpan('menuDeactivate');
      }
    }
      
      new ypSlideOutMenu("menuRole", "down", 0, 0, 170, getHeight("menuRoleTable"));
    }
    return ypSlideOutMenu.displayDropMenu(id, loc);
  }
  //Menu link functions
  function modify() {
    window.location.href = 'PortalRoleEditor.do?command=Modify&id=' + thisRoleId;
  }

  function activate() {
    window.location.href = 'PortalRoleEditor.do?command=Activate&id=' + thisRoleId;
  }
  function deactivate() {
    window.location.href = 'PortalRoleEditor.do?command=Deactivate&id=' + thisRoleId;
  }

  function deleteRole() {
    popURLReturn('PortalRoleEditor.do?command=ConfirmDelete&id=' + thisRoleId + '&popup=true','PortalRoleEditor.do?command=List', 'Delete','320','200','yes','no');
  }
</script>
<div id="menuRoleContainer" class="menu">
  <div id="menuRoleContent">
    <table id="menuRoleTable" class="pulldown" width="170" cellspacing="0">
      <dhv:permission name="admin-portalroleeditor-edit">
      <tr onmouseover="cmOver(this)" onmouseout="cmOut(this)" onclick="modify()">
        <th>
          <img src="images/icons/stock_edit-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td width="100%">
          <dhv:label name="global.button.modify">Modify</dhv:label>
        </td>
      </tr>
      </dhv:permission>
      <dhv:permission name="admin-portalroleeditor-edit">
        <tr id="menuDeactivate" onmouseover="cmOver(this)" onmouseout="cmOut(this)"
            onclick="deactivate()">
          <th>
            <img src="images/icons/stock_edit-16.gif" border="0"
                 align="absmiddle" height="16" width="16"/>
          </th>
          <td>
            <dhv:label name="admin.deactivate">Deactivate</dhv:label>
          </td>
        </tr>
      </dhv:permission>
      <dhv:permission name="admin-portalroleeditor-edit">
        <tr id="menuActivate" onmouseover="cmOver(this)" onmouseout="cmOut(this)"
            onclick="activate()">
          <th>
            <img src="images/icons/stock_edit-16.gif" border="0"
                 align="absmiddle" height="16" width="16"/>
          </th>
          <td>
            <dhv:label name="admin.activate">Activate</dhv:label>
          </td>
        </tr>
      </dhv:permission>
      <dhv:permission name="admin-roles-delete">
      <tr onmouseover="cmOver(this)" onmouseout="cmOut(this)" onclick="deleteRole()">
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
