<%--
 Copyright 2000-2004 Matt Rajkowski
 matt.rajkowski@teamelements.com
 http://www.teamelements.com
 This source code cannot be modified, distributed or used without
 permission from Matt Rajkowski
--%>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<script language="javascript">
  var thisListId = -1;
  var menu_init = false;
  //Set the action parameters for clicked item
  function displayMenu(loc, id, listId) {
    thisListId = listId;
    if (!menu_init) {
      menu_init = true;
      new ypSlideOutMenu("menuListItem", "down", 0, 0, 170, getHeight("menuListItemTable"));
    }
    return ypSlideOutMenu.displayDropMenu(id, loc);
  }
  //Menu link functions
  function viewItem() {
    popURL('ProjectManagementLists.do?command=Details&pid=<%= Project.getId() %>&cid=<%= category.getId() %>&id=' + thisListId + '&popup=true','List_Details','650','375','yes','yes');
  }
  function editItem() {
    document.location.href='ProjectManagementLists.do?command=Modify&pid=<%= Project.getId() %>&cid=<%= category.getId() %>&id=' + thisListId;
  }
  function moveItem() {
    popURL('ProjectManagementLists.do?command=Move&pid=<%= Project.getId() %>&id=' + thisListId + '&popup=true&return=ProjectLists&param=<%= Project.getId() %>','Lists','325','375','yes','yes');
  }
  function deleteItem() {
    confirmDelete('ProjectManagementLists.do?command=Delete&pid=<%= Project.getId() %>&cid=<%= category.getId() %>&id=' + thisListId);
  }
</script>
<%-- List Item Pop-up Menu --%>
<div id="menuListItemContainer" class="menu">
  <div id="menuListItemContent">
    <table id="menuListItemTable" class="pulldown" width="170" cellspacing="0">
      <tr onmouseover="cmOver(this)" onmouseout="cmOut(this)"
          onclick="viewItem()">
        <th valign="top">
          <img src="images/icons/stock_zoom-page-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td width="100%">
          View Item
        </td>
      </tr>
    <zeroio:permission name="project-lists-modify">
      <tr onmouseover="cmOver(this)" onmouseout="cmOut(this)"
          onclick="editItem()">
        <th valign="top">
          <img src="images/icons/stock_edit-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td width="100%">
          Edit Item
        </td>
      </tr>
    </zeroio:permission>
    <zeroio:permission name="project-lists-modify">
      <tr onmouseover="cmOver(this)" onmouseout="cmOut(this)"
          onclick="moveItem()">
        <th valign="top">
          <img src="images/icons/stock_drag-mode-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td width="100%">
          Move Item to Another List
        </td>
      </tr>
    </zeroio:permission>
    <zeroio:permission name="project-lists-modify">
      <tr onmouseover="cmOver(this)" onmouseout="cmOut(this)"
          onclick="deleteItem()">
        <th valign="top">
          <img src="images/icons/stock_delete-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td width="100%">
          Delete Item
        </td>
      </tr>
    </zeroio:permission>
    </table>
  </div>
</div>

