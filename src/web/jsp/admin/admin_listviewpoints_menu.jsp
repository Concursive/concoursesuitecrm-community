<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<script language="javascript">
  var thisVpId = -1;
  var thisUserId  =-1;
  var menu_init = false;
  //Set the action parameters for clicked item
  function displayMenu(id, userId, vpId) {
    thisUserId = userId;
    thisVpId = vpId;
    if (!menu_init) {
      menu_init = true;
      new ypSlideOutMenu("menuViewpoint", "down", 0, 0, 170, getHeight("menuViewpointTable"));
    }
    return ypSlideOutMenu.displayMenu(id);
  }
  //Menu link functions
  function details() {
    window.location.href = 'Viewpoints.do?command=ViewpointDetails&id=' + thisVpId + '&userId=' + thisUserId;
  }
  
  function modify() {
    window.location.href = 'Viewpoints.do?command=ViewpointDetails&id=' + thisVpId + '&userId=' + thisUserId;
  }
  
  function deleteViewpoint() {
    popURLReturn('Viewpoints.do?command=ConfirmDelete&id=' + thisVpId + '&userId=' + thisUserId + '&popup=true','Viewpoints.do?command=ListViewpoints&userId=' + thisUserId, 'Delete_Viewpoint','320','200','yes','no');
  }
</script>
<div id="menuViewpointContainer" class="menu">
  <div id="menuViewpointContent">
    <table id="menuViewpointTable" class="pulldown" width="170">
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
        <td id="menuDisable">
          <a href="javascript:deleteViewpoint()">Delete</a>
        </td>
      </tr>
      </dhv:permission>
    </table>
  </div>
</div>
