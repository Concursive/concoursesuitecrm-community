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
  var thisFileId = -1;
  var menu_init = false;
  //Set the action parameters for clicked item
  function displayMenu(loc, id, fileId, status) {
    thisFileId = fileId;
    if (!menu_init) {
      menu_init = true;
      new ypSlideOutMenu("menuFile", "down", 0, 0, 170, getHeight("menuFileTable"));
    }
		if (status == 0) {
			hideSpan('menuArchiveFile');
      showSpan('menuReEnableFile');
		} else if (status == 1) {
			hideSpan('menuReEnableFile');
      showSpan('menuArchiveFile');
		} else {
		  hideSpan('menuReEnableFile');
      hideSpan('menuArchiveFile');
    }
    return ypSlideOutMenu.displayDropMenu(id, loc);
  }
	
	//File operations
  function enable() {
    window.location.href = 'AdminLogos.do?command=Enable&fid=' + thisFileId + '&moduleId=<%= PermissionCategory.getId() %>&return=list';
  }
  
  function archive() {
    window.location.href = 'AdminLogos.do?command=Delete&fid=' + thisFileId + '&moduleId=<%= PermissionCategory.getId() %>&action=disable&return=list';
  }
  
  function deleteFile() {
    confirmDelete('AdminLogos.do?command=Delete&fid=' + thisFileId + '&moduleId=<%= PermissionCategory.getId() %>&action=delete');
  }
	
	function markDefault() {
		window.location.href = 'AdminLogos.do?command=MarkDefault&fid=' + thisFileId + '&moduleId=<%= PermissionCategory.getId() %>&action=disable&return=list';
	}
</script>
<div id="menuFileContainer" class="menu">
  <div id="menuFileContent">
    <table id="menuFileTable" class="pulldown" width="170" cellspacing="0">
      <dhv:permission name="admin-sysconfig-logos-delete">
		  <tr onmouseover="cmOver(this)" onmouseout="cmOut(this)" onclick="deleteFile()">
        <th>
          <img src="images/icons/stock_delete-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td>
          <dhv:label name="global.button.delete">Delete</dhv:label>
        </td>
      </tr>
			</dhv:permission>
			<dhv:permission name="admin-sysconfig-logos-edit">
			<tr onmouseover="cmOver(this)" onmouseout="cmOut(this)" onclick="markDefault()">
        <th>
          <img src="images/icons/stock_edit-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td>
          <dhv:label name="admin.markAsDefault">Mark as Default</dhv:label>
        </td>
      </tr>
			</dhv:permission>
			<dhv:permission name="admin-sysconfig-logos-edit">
			<tr id="menuArchiveFile" onmouseover="cmOver(this)" onmouseout="cmOut(this)" onclick="archive()">
        <th>
          <img src="images/icons/stock_archive-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td>
          <dhv:label name="accounts.accounts_list_menu.Archive">Archive</dhv:label>
        </td>
      </tr>
			</dhv:permission>
			<dhv:permission name="admin-sysconfig-logos-edit">
      <tr id="menuReEnableFile" onmouseover="cmOver(this)" onmouseout="cmOut(this)" onclick="enable()">
        <th>
          <img src="images/icons/stock_archive-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td>
          <dhv:label name="accounts.accounts_list_menu.UnArchive">Un-Archive</dhv:label>
        </td>
      </tr>
			</dhv:permission>
    </table>
  </div>
</div>
