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
  var thisVpId = -1;
  var thisUserId  =-1;
  var menu_init = false;
  //Set the action parameters for clicked item
  function displayMenu(loc, id, userId, vpId) {
    thisUserId = userId;
    thisVpId = vpId;
    if (!menu_init) {
      menu_init = true;
      new ypSlideOutMenu("menuViewpoint", "down", 0, 0, 170, getHeight("menuViewpointTable"));
    }
    return ypSlideOutMenu.displayDropMenu(id, loc);
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
    <table id="menuViewpointTable" class="pulldown" width="170" cellspacing="0">
      <dhv:permission name="admin-roles-view">
      <tr onmouseover="cmOver(this)" onmouseout="cmOut(this)" onclick="details()">
        <th>
          <img src="images/icons/stock_zoom-page-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td width="100%">
          View Details
        </td>
      </tr>
      </dhv:permission>
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
      <tr onmouseover="cmOver(this)" onmouseout="cmOut(this)" onclick="deleteViewpoint()">
        <th>
          <img src="images/icons/stock_delete-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td id="menuDisable">
          Delete
        </td>
      </tr>
      </dhv:permission>
    </table>
  </div>
</div>
