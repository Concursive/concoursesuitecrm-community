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
          Modify
        </td>
      </tr>
      </dhv:permission>
      <dhv:permission name="admin-roles-delete">
      <tr onmouseover="cmOver(this)" onmouseout="cmOut(this)" onclick="deleteRole()">
        <th>
          <img src="images/icons/stock_delete-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td>
          Delete
        </td>
      </tr>
      </dhv:permission>
    </table>
  </div>
</div>
