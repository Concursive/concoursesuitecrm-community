<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<script language="javascript">
  var thisOrgId = -1;
  var thisAssetId = -1;
  var menu_init = false;
  //Set the action parameters for clicked item
  function displayMenu(loc, id, orgId, assetId) {
    thisOrgId = orgId;
    thisAssetId = assetId;
    if (!menu_init) {
      menu_init = true;
      new ypSlideOutMenu(id, "down", 0, 0, 170, getHeight("menuAssetTable"));
    }

    return ypSlideOutMenu.displayDropMenu(id, loc);
  }
  //Menu link functions
  function details() {
    window.location.href = 'AccountsAssets.do?command=View&orgId=' + thisOrgId + '&id=' + thisAssetId;
  }
  
  
  function modify() {
    window.location.href = 'AccountsAssets.do?command=Modify&orgId=' + thisOrgId + '&id=' + thisAssetId + '&return=list';
  }
  
  function deleteAsset() {
    popURLReturn('AccountsAssets.do?command=ConfirmDelete&orgId=' + thisOrgId + '&id=' + thisAssetId + '&popup=true','AccountsAssets.do?command=List&orgId=' + thisOrgId,'Delete_asset','330','200','yes','no');
  }

</script>
<div id="menuAssetContainer" class="menu">
  <div id="menuAssetContent">
    <table id="menuAssetTable" class="pulldown" width="170" cellspacing="0">
      <dhv:permission name="accounts-assets-view">
      <tr onmouseover="cmOver(this)" onmouseout="cmOut(this)" onclick="details()">
        <th>
          <img src="images/icons/stock_zoom-page-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td width="100%">
          View Details
        </td>
      </tr>
      </dhv:permission>
      <dhv:permission name="accounts-assets-edit">
      <tr onmouseover="cmOver(this)" onmouseout="cmOut(this)" onclick="modify()">
        <th>
          <img src="images/icons/stock_edit-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td width="100%">
          Modify
        </td>
      </tr>
      </dhv:permission>
      <dhv:permission name="accounts-assets-delete">
      <tr onmouseover="cmOver(this)" onmouseout="cmOut(this)" onclick="deleteAsset()">
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
