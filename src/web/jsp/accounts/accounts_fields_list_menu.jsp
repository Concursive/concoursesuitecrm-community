<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<script language="javascript">
  var thisOrgId = -1;
  var thisCatId = -1;
  var thisRecId = -1;
  var menu_init = false;
  //Set the action parameters for clicked item
  function displayMenu(id, orgId, catId, recId) {
    thisOrgId = orgId;
    thisCatId = catId;
    thisRecId = recId;
    if (!menu_init) {
      menu_init = true;
      new ypSlideOutMenu("menuFolders", "down", 0, 0, 170, getHeight("menuFoldersTable"));
    }
    return ypSlideOutMenu.displayMenu(id);
  }
  //Menu link functions
  function details() {
    window.location.href='Accounts.do?command=Fields&orgId=' + thisOrgId + '&catId=' + thisCatId + '&recId=' + thisRecId;
  }
  
  function modify() {
    window.location.href = 'Accounts.do?command=ModifyFields&orgId=' + thisOrgId + '&catId=' + thisCatId + '&recId=' + thisRecId + '&return=list';
  }
  
  function deleteFolder() {
    window.location.href = 'Accounts.do?command=DeleteFields&orgId=' + thisOrgId + '&catId=' + thisCatId + '&recId=' + thisRecId + '&return=list';
  }
</script>
<div id="menuFoldersContainer" class="menu">
  <div id="menuFoldersContent">
    <table id="menuFoldersTable" class="pulldown" width="170">
      <dhv:permission name="accounts-accounts-folders-view">
      <tr>
        <td>
          <img src="images/icons/stock_zoom-page-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </td>
        <td width="100%">
          <a href="javascript:details()">View Details</a>
        </td>
      </tr>
      </dhv:permission>
      <dhv:permission name="accounts-accounts-folders-edit">
      <tr>
        <td>
          <img src="images/icons/stock_edit-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </td>
        <td width="100%">
          <a href="javascript:modify()">Modify</a>
        </td>
      </tr>
      </dhv:permission>
      <dhv:permission name="accounts-accounts-folders-delete">
      <tr>
        <td>
          <img src="images/icons/stock_delete-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </td>
        <td width="100%">
          <a href="javascript:deleteFolder()">Delete</a>
        </td>
      </tr>
      </dhv:permission>
    </table>
  </div>
</div>
