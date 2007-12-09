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
  var thisRoleId = -1;
  var menu_init = false;
  //Set the action parameters for clicked item
  function displayMenu(loc, id, roleId) {
    thisRoleId = roleId;
    if (!menu_init) {
      menu_init = true;
      new ypSlideOutMenu("menuRole", "down", 0, 0, 170, getHeight("menuRoleTable"));
    }
    return ypSlideOutMenu.displayDropMenu(id, loc);
  }
  //Menu link functions
  function modify() {
    window.location.href = 'Roles.do?command=RoleDetails&id=' + thisRoleId;
  }
  
  function deleteRole() {
    popURLReturn('Roles.do?command=ConfirmDelete&id=' + thisRoleId + '&popup=true','Roles.do?command=ListRoles', 'Delete_role','320','200','yes','no');
  }
</script>
<div id="menuRoleContainer" class="menu">
  <div id="menuRoleContent">
    <table id="menuRoleTable" class="pulldown" width="170" cellspacing="0">
      <dhv:permission name="admin-roles-edit">
      <tr onmouseover="cmOver(this)" onmouseout="cmOut(this)" onclick="modify()">
        <th>
          <img src="images/icons/stock_edit-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td width="100%">
          <dhv:label name="global.button.modify">Modify</dhv:label>
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
