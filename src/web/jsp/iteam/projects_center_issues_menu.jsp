<%-- 
  - Copyright Notice: (C) 2000-2004 Team Elements, All Rights Reserved.
  - License: This source code cannot be modified, distributed or used without
  -          written permission from Team Elements. This notice must remain in
  -          place.
  - Author(s): Matt Rajkowski
  - Version: $Id$
  - Description: 
  --%>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<script language="javascript">
  var thisItemId = -1;
  var thisCategoryId = -1;
  var menu_init = false;
  //Set the action parameters for clicked item
  function displayMenu(loc, id, itemId, categoryId) {
    thisItemId = itemId;
    thisCategoryId = categoryId;
    if (!menu_init) {
      menu_init = true;
      new ypSlideOutMenu("menuItem", "down", 0, 0, 170, getHeight("menuItemTable"));
    }
    return ypSlideOutMenu.displayDropMenu(id, loc);
  }
  //Menu link functions
  function viewItem() {
    document.location.href='ProjectManagementIssues.do?command=Details&pid=<%= Project.getId() %>&iid=' + thisItemId + '&cid=' + thisCategoryId + '&resetList=true';
  }
  function editItem() {
    document.location.href='ProjectManagementIssues.do?command=Edit&pid=<%= Project.getId() %>&iid=' + thisItemId + '&cid=' + thisCategoryId + '&return=list';
  }
  function deleteItem() {
    confirmDelete('ProjectManagementIssues.do?command=Delete&pid=<%= Project.getId() %>&iid=' + thisItemId + '&cid=' + thisCategoryId);
  }
</script>
<%-- List Item Pop-up Menu --%>
<div id="menuItemContainer" class="menu">
  <div id="menuItemContent">
    <table id="menuItemTable" class="pulldown" width="170" cellspacing="0">
    <zeroio:permission name="project-discussion-topics-view">
      <tr onmouseover="cmOver(this)" onmouseout="cmOut(this)"
          onclick="viewItem()">
        <th valign="top">
          <img src="images/icons/stock_zoom-page-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td width="100%">
          View Topic
        </td>
      </tr>
    </zeroio:permission>
    <zeroio:permission name="project-discussion-topics-edit">
      <tr onmouseover="cmOver(this)" onmouseout="cmOut(this)"
          onclick="editItem()">
        <th valign="top">
          <img src="images/icons/stock_edit-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td width="100%">
          Modify Topic
        </td>
      </tr>
    </zeroio:permission>
    <zeroio:permission name="project-discussion-topics-delete">
      <tr onmouseover="cmOver(this)" onmouseout="cmOut(this)"
          onclick="deleteItem()">
        <th valign="top">
          <img src="images/icons/stock_delete-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td width="100%">
          Delete Topic
        </td>
      </tr>
    </zeroio:permission>
    </table>
  </div>
</div>

