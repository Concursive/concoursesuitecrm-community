<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<script language="javascript">
  var thisRoleId = -1;
  var menu_init = false;
  //Set the action parameters for clicked item
  function displayMenu(id, roleId) {
    thisRoleId = roleId;
    if (!menu_init) {
      menu_init = true;
      new ypSlideOutMenu("menuRole", "down", 0, 0, 170, getHeight("menuRoleTable"));
    }
    return ypSlideOutMenu.displayMenu(id);
  }
  //Menu link functions
  function details() {
    window.location.href = 'Roles.do?command=RoleDetails&id=' + thisRoleId;
  }
  
  function modify() {
    window.location.href = 'Roles.do?command=RoleDetails&id=' + thisRoleId;
  }
  
  function deleteRole() {
    popURLReturn('Roles.do?command=ConfirmDelete&id=' + thisRoleId + '&popup=true','Roles.do?command=ListRoles', 'Delete_role','320','200','yes','no');
  }
</script>
<div id="menuRoleContainer" class="menu">
  <div id="menuRoleContent">
    <table id="menuRoleTable" class="pulldown" width="170">
      <dhv:permission name="admin-roles-view">
      <tr>
        <td>
          <img src="images/icons/stock_zoom-page-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </td>
        <td width="100%">
          <a href="javascript:details()">View Details</a>
        </td>
      </tr>
      </dhv:permission>
      <dhv:permission name="admin-roles-edit">
      <tr>
        <td>
          <img src="images/icons/stock_edit-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </td>
        <td width="100%">
          <a href="javascript:modify()">Modify</a>
        </td>
      </tr>
      </dhv:permission>
      <dhv:permission name="admin-roles-delete">
      <tr>
        <td>
          <img src="images/icons/stock_delete-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </td>
        <td>
          <a href="javascript:deleteRole()">Delete</a>
        </td>
      </tr>
      </dhv:permission>
    </table>
  </div>
</div>
