<%-- 
  - Copyright(c) 2004 Dark Horse Ventures LLC (http://www.centriccrm.com/) All
  - rights reserved. This material cannot be distributed without written
  - permission from Dark Horse Ventures LLC. Permission to use, copy, and modify
  - this material for internal use is hereby granted, provided that the above
  - copyright notice and this permission notice appear in all copies. DARK HORSE
  - VENTURES LLC MAKES NO REPRESENTATIONS AND EXTENDS NO WARRANTIES, EXPRESS OR
  - IMPLIED, WITH RESPECT TO THE SOFTWARE, INCLUDING, BUT NOT LIMITED TO, THE
  - IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR ANY PARTICULAR
  - PURPOSE, AND THE WARRANTY AGAINST INFRINGEMENT OF PATENTS OR OTHER
  - INTELLECTUAL PROPERTY RIGHTS. THE SOFTWARE IS PROVIDED "AS IS", AND IN NO
  - EVENT SHALL DARK HORSE VENTURES LLC OR ANY OF ITS AFFILIATES BE LIABLE FOR
  - ANY DAMAGES, INCLUDING ANY LOST PROFITS OR OTHER INCIDENTAL OR CONSEQUENTIAL
  - DAMAGES RELATING TO THE SOFTWARE.
  - 
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
