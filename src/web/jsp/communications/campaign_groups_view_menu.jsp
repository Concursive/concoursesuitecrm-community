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
  var thisGroupId = -1;
  var menu_init = false;
  //Set the action parameters for clicked item
  function displayMenu(loc, id, groupId) {
    thisGroupId = groupId;
    if (!menu_init) {
      menu_init = true;
      new ypSlideOutMenu("menuGroup", "down", 0, 0, 170, getHeight("menuGroupTable"));
    }
    return ypSlideOutMenu.displayDropMenu(id, loc);
  }
  
  //Menu link functions
  function details() {
    window.location.href='CampaignManagerGroup.do?command=Details&id=' + thisGroupId;
  }
  
  function modify() {
    window.location.href='CampaignManagerGroup.do?command=Modify&id=' + thisGroupId + '&return=list';
  }
  
  function deleteGroup() {
  popURLReturn('CampaignManagerGroup.do?command=ConfirmDelete&id=' + thisGroupId + '&popup=true','CampaignManagerGroup.do?command=View', 'Delete_group','330','200','yes','no');
  }
</script>
<div id="menuGroupContainer" class="menu">
  <div id="menuGroupContent">
    <table id="menuGroupTable" class="pulldown" width="170" cellspacing="0">
      <dhv:permission name="campaign-campaigns-groups-view">
      <tr onmouseover="cmOver(this)" onmouseout="cmOut(this)" onclick="details()">
        <th>
          <img src="images/icons/stock_zoom-page-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td width="100%">
          View Details
        </td>
      </tr>
      </dhv:permission>
      <dhv:permission name="campaign-campaigns-groups-edit">
      <tr onmouseover="cmOver(this)" onmouseout="cmOut(this)" onclick="modify()">
        <th>
          <img src="images/icons/stock_edit-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td width="100%">
          Modify
        </td>
      </tr>
      </dhv:permission>
      <dhv:permission name="campaign-campaigns-groups-delete">
      <tr onmouseover="cmOver(this)" onmouseout="cmOut(this)" onclick="deleteGroup()">
        <th>
          <img src="images/icons/stock_delete-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td width="100%">
          Delete
        </td>
      </tr>
      </dhv:permission>
    </table>
  </div>
</div>
