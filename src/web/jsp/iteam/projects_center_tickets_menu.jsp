<%--
 Copyright 2000-2004 Matt Rajkowski
 matt.rajkowski@teamelements.com
 http://www.teamelements.com
 This source code cannot be modified, distributed or used without
 permission from Matt Rajkowski
--%>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<script language="javascript">
  var thisItemId = -1;
  var menu_init = false;
  //Set the action parameters for clicked item
  function displayMenu(loc, id, itemId) {
    thisItemId = itemId;
    if (!menu_init) {
      menu_init = true;
      new ypSlideOutMenu("menuItem", "down", 0, 0, 170, getHeight("menuItemTable"));
    }
    return ypSlideOutMenu.displayDropMenu(id, loc);
  }
  //Menu link functions
  function viewItem() {
    document.location.href='ProjectManagementTickets.do?command=Details&pid=<%= Project.getId() %>&id=' + thisItemId;
  }
  function editItem() {
    document.location.href='ProjectManagementTickets.do?command=Modify&pid=<%= Project.getId() %>&id=' + thisItemId + '&return=list';
  }
  function deleteItem() {
    confirmDelete('ProjectManagementTickets.do?command=Delete&pid=<%= Project.getId() %>&id=' + thisItemId);
  }
</script>
<%-- List Item Pop-up Menu --%>
<div id="menuItemContainer" class="menu">
  <div id="menuItemContent">
    <table id="menuItemTable" class="pulldown" width="170" cellspacing="0">
    <zeroio:permission name="project-tickets-view">
      <tr onmouseover="cmOver(this)" onmouseout="cmOut(this)"
          onclick="viewItem()">
        <th valign="top">
          <img src="images/icons/stock_zoom-page-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td width="100%">
          View Ticket
        </td>
      </tr>
    </zeroio:permission>
    <zeroio:permission name="project-tickets-edit">
      <tr onmouseover="cmOver(this)" onmouseout="cmOut(this)"
          onclick="editItem()">
        <th valign="top">
          <img src="images/icons/stock_edit-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td width="100%">
          Edit Ticket
        </td>
      </tr>
    </zeroio:permission>
    <zeroio:permission name="project-tickets-delete">
      <tr onmouseover="cmOver(this)" onmouseout="cmOut(this)"
          onclick="deleteItem()">
        <th valign="top">
          <img src="images/icons/stock_delete-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td width="100%">
          Delete Ticket
        </td>
      </tr>
    </zeroio:permission>
    </table>
  </div>
</div>

