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
  var thisFolderId = -1;
  var thisFileId = -1;
  var thisDisplay = -1;
  var menu_init = false;
  //Set the action parameters for clicked item
  function displayMenu(loc, id, folderId, fileId, displayId) {
    thisFolderId = folderId;
    thisFileId = fileId;
    thisDisplay = displayId;
    if (!menu_init) {
      menu_init = true;
      new ypSlideOutMenu("menuFolder", "down", 0, 0, 170, getHeight("menuFolderTable"));
      new ypSlideOutMenu("menuFile", "down", 0, 0, 170, getHeight("menuFileTable"));
    }
    return ypSlideOutMenu.displayDropMenu(id, loc);
  }
  //Menu link functions
  function viewFolder() {
    if (thisDisplay == -1) {
      document.location.href='ProjectManagement.do?command=ProjectCenter&section=File_Library&pid=<%= Project.getId() %>&folderId=' + thisFolderId;
    } else {
      var details = '';
      if (thisDisplay == 2) {
        details = '&details=true';
      }
      document.location.href='ProjectManagement.do?command=ProjectCenter&section=File_Gallery&pid=<%= Project.getId() %>&folderId=' + thisFolderId + details;
    }
  }
  function editFolder() {
    document.location.href='ProjectManagementFileFolders.do?command=Modify&pid=<%= Project.getId() %>&folderId=<%= Project.getFiles().getFolderId() %>&id=' + thisFolderId + '&parentId=<%= Project.getFiles().getFolderId() %>';
  }
  function moveFolder() {
    popURL('ProjectManagementFileFolders.do?command=Move&pid=<%= Project.getId() %>&id=' + thisFolderId + '&popup=true&return=ProjectFiles&param=<%= Project.getId() %>&param2=<%= Project.getFiles().getFolderId() %>','Files','400','375','yes','yes');
  }
  function viewFileHistory() {
    document.location.href='ProjectManagementFiles.do?command=Details&pid=<%= Project.getId() %>&fid=' + thisFileId + '&folderId=<%= Project.getFiles().getFolderId() %>';
  }
  function deleteFolder() {
    confirmDelete('ProjectManagementFileFolders.do?command=Delete&pid=<%= Project.getId() %>&folderId=<%= Project.getFiles().getFolderId() %>&id=' + thisFolderId);
  }
  function downloadFile() {
    document.location.href='ProjectManagementFiles.do?command=Download&pid=<%= Project.getId() %>&fid=' + thisFileId;
  }
  function viewFile() {
    popURL('ProjectManagementFiles.do?command=Download&pid=<%= Project.getId() %>&fid=' + thisFileId + '&view=true', 'Content', 640,480, 1, 1);
  }
  function renameFile() {
    document.location.href='ProjectManagementFiles.do?command=Modify&pid=<%= Project.getId() %>&fid=' + thisFileId + '&folderId=<%= Project.getFiles().getFolderId() %>';
  }
  function addVersion() {
    document.location.href='ProjectManagementFiles.do?command=AddVersion&pid=<%= Project.getId() %>&fid=' + thisFileId + '&folderId=<%= Project.getFiles().getFolderId() %>';
  }
  function moveFile() {
    popURL('ProjectManagementFiles.do?command=Move&pid=<%= Project.getId() %>&fid=' + thisFileId + '&popup=true&return=ProjectFiles&param=<%= Project.getId() %>&param2=<%= Project.getFiles().getFolderId() %>','Files','400','375','yes','yes');
  }
  function deleteFile() {
    confirmDelete('ProjectManagementFiles.do?command=Delete&pid=<%= Project.getId() %>&fid=' + thisFileId + '&folderId=<%= Project.getFiles().getFolderId() %>');
  }
</script>
<div id="menuFolderContainer" class="menu">
  <div id="menuFolderContent">
    <table id="menuFolderTable" class="pulldown" width="170" cellspacing="0">
    <zeroio:permission name="project-documents-view">
      <tr onmouseover="cmOver(this)" onmouseout="cmOut(this)"
          onclick="viewFolder()">
        <th valign="top">
          <img src="images/icons/stock_zoom-folder-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td width="100%">
          View Folder
        </td>
      </tr>
    </zeroio:permission>
    <zeroio:permission name="project-documents-folders-edit">
      <tr onmouseover="cmOver(this)" onmouseout="cmOut(this)"
          onclick="editFolder()">
        <th>
          <img src="images/icons/stock_rename-page-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td width="100%">
          Rename Folder
        </td>
      </tr>
    </zeroio:permission>
    <zeroio:permission name="project-documents-folders-edit">
      <tr onmouseover="cmOver(this)" onmouseout="cmOut(this)"
          onclick="moveFolder()">
        <th>
          <img src="images/icons/stock_drag-mode-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td>
          Move Folder
        </td>
      </tr>
    </zeroio:permission>
    <zeroio:permission name="project-documents-folders-delete">
      <tr onmouseover="cmOver(this)" onmouseout="cmOut(this)"
          onclick="deleteFolder()">
        <th valign="top">
          <img src="images/icons/stock_left-with-subpoints-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td>
          Delete Folder and Move contents to current folder
        </td>
      </tr>
    </zeroio:permission>
    </table>
  </div>
</div>
<div id="menuFileContainer" class="menu">
  <div id="menuFileContent">
    <table id="menuFileTable" class="pulldown" width="170" cellspacing="0">
      <zeroio:permission name="project-documents-view">
      <tr onmouseover="cmOver(this)" onmouseout="cmOut(this)"
          onclick="viewFileHistory()">
        <th>
          <img src="images/icons/stock_zoom-page-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td width="100%">
          View File History
        </td>
      </tr>
      </zeroio:permission>
      <zeroio:permission name="project-documents-files-download">
      <tr onmouseover="cmOver(this)" onmouseout="cmOut(this)"
          onclick="downloadFile()">
        <th>
          <img src="images/icons/stock_data-save-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td width="100%">
          Download File
        </td>
      </tr>
      </zeroio:permission>
      <zeroio:permission name="project-documents-files-download">
      <tr onmouseover="cmOver(this)" onmouseout="cmOut(this)"
          onclick="viewFile()">
        <th>
          <img src="images/icons/stock_data-save-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td width="100%">
          View File Contents
        </td>
      </tr>
      </zeroio:permission>
      <zeroio:permission name="project-documents-files-rename">
      <tr onmouseover="cmOver(this)" onmouseout="cmOut(this)"
          onclick="renameFile()">
        <th>
          <img src="images/icons/stock_rename-page-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td>
          Rename File
        </td>
      </tr>
      </zeroio:permission>
      <zeroio:permission name="project-documents-files-upload">
      <tr onmouseover="cmOver(this)" onmouseout="cmOut(this)"
          onclick="addVersion()">
        <th>
          <img src="images/icons/stock_insert-file-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td>
          Add Version
        </td>
      </tr>
      </zeroio:permission>
      <zeroio:permission name="project-documents-files-rename">
      <tr onmouseover="cmOver(this)" onmouseout="cmOut(this)"
          onclick="moveFile()">
        <th>
          <img src="images/icons/stock_drag-mode-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td>
          Move File
        </td>
      </tr>
      </zeroio:permission>
      <zeroio:permission name="project-documents-files-delete">
      <tr onmouseover="cmOver(this)" onmouseout="cmOut(this)"
          onclick="deleteFile()">
        <th>
          <img src="images/icons/stock_delete-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td>
          Delete File
        </td>
      </tr>
      </zeroio:permission>
    </table>
  </div>
</div>
