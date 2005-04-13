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
    document.location.href='ProjectManagementNews.do?command=Details&pid=<%= Project.getId() %>&id=' + thisItemId;
  }
  function editItem() {
    document.location.href='ProjectManagementNews.do?command=Edit&pid=<%= Project.getId() %>&id=' + thisItemId + '&return=list';
  }
  function deleteItem() {
    confirmDelete('ProjectManagementNews.do?command=Delete&pid=<%= Project.getId() %>&id=' + thisItemId);
  }
  function cloneItem() {
    document.location.href='ProjectManagementNews.do?command=Clone&pid=<%= Project.getId() %>&id=' + thisItemId + '&return=list';
  }
  function emailMe() {
    confirmForward('ProjectManagementNews.do?command=EmailMe&pid=<%= Project.getId() %>&id=' + thisItemId);
  }
  function emailTeam() {
    confirmForward('ProjectManagementNews.do?command=EmailTeam&pid=<%= Project.getId() %>&id=' + thisItemId);
  }
</script>
<%-- List Item Pop-up Menu --%>
<div id="menuItemContainer" class="menu">
  <div id="menuItemContent">
    <table id="menuItemTable" class="pulldown" width="170" cellspacing="0">
    <zeroio:permission name="project-news-view">
      <tr onmouseover="cmOver(this)" onmouseout="cmOut(this)"
          onclick="viewItem()">
        <th valign="top">
          <img src="images/icons/stock_zoom-page-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td width="100%">
          View Article
        </td>
      </tr>
    </zeroio:permission>
    <zeroio:permission name="project-news-edit">
      <tr onmouseover="cmOver(this)" onmouseout="cmOut(this)"
          onclick="editItem()">
        <th valign="top">
          <img src="images/icons/stock_edit-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td width="100%">
          Edit Article
        </td>
      </tr>
    </zeroio:permission>
    <zeroio:permission name="project-news-edit">
      <tr onmouseover="cmOver(this)" onmouseout="cmOut(this)"
          onclick="cloneItem()">
        <th valign="top">
          <img src="images/icons/stock_copy-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td width="100%">
          Clone Article
        </td>
      </tr>
    </zeroio:permission>
    <zeroio:permission name="project-news-add">
      <tr onmouseover="cmOver(this)" onmouseout="cmOut(this)"
          onclick="emailTeam()">
        <th valign="top">
          <img src="images/icons/stock_mail-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td width="100%">
          Send to All Team Members
        </td>
      </tr>
    </zeroio:permission>
    <zeroio:permission name="project-news-view">
      <tr onmouseover="cmOver(this)" onmouseout="cmOut(this)"
          onclick="emailMe()">
        <th valign="top">
          <img src="images/icons/stock_mail-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td width="100%">
          Send to My Email Address
        </td>
      </tr>
    </zeroio:permission>
    <zeroio:permission name="project-news-delete">
      <tr onmouseover="cmOver(this)" onmouseout="cmOut(this)"
          onclick="deleteItem()">
        <th valign="top">
          <img src="images/icons/stock_delete-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td width="100%">
          Delete Article
        </td>
      </tr>
    </zeroio:permission>
    </table>
  </div>
</div>

