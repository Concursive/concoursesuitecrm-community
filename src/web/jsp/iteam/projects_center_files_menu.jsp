<%-- 
  - Copyright(c) 2004 Team Elements LLC (http://www.teamelements.com/) All
  - rights reserved. This material cannot be distributed without written
  - permission from Team Elements LLC. Permission to use, copy, and modify
  - this material for internal use is hereby granted, provided that the above
  - copyright notice and this permission notice appear in all copies. TEAM ELEMENTS
  - LLC MAKES NO REPRESENTATIONS AND EXTENDS NO WARRANTIES, EXPRESS OR
  - IMPLIED, WITH RESPECT TO THE SOFTWARE, INCLUDING, BUT NOT LIMITED TO, THE
  - IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR ANY PARTICULAR
  - PURPOSE, AND THE WARRANTY AGAINST INFRINGEMENT OF PATENTS OR OTHER
  - INTELLECTUAL PROPERTY RIGHTS. THE SOFTWARE IS PROVIDED "AS IS", AND IN NO
  - EVENT SHALL TEAM ELEMENTS LLC OR ANY OF ITS AFFILIATES BE LIABLE FOR
  - ANY DAMAGES, INCLUDING ANY LOST PROFITS OR OTHER INCIDENTAL OR CONSEQUENTIAL
  - DAMAGES RELATING TO THE SOFTWARE.
  - 
  - Author(s): Matt Rajkowski
  - Version: $Id$
  - Description: 
  --%>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<script language="javascript">
  var thisFolderId = -1;
  var thisFileId = -1;
  var thisDisplay = -1;
  var menu_init = false;
  //Set the action parameters for clicked item
  function displayMenu(loc, id, folderId, fileId, displayId, trashed) {
    thisFolderId = folderId;
    thisFileId = fileId;
    thisDisplay = displayId;
    updateMenu(trashed);
    if (!menu_init) {
      menu_init = true;
      new ypSlideOutMenu("menuFolder", "down", 0, 0, 170, getHeight("menuFolderTable"));
      new ypSlideOutMenu("menuFile", "down", 0, 0, 170, getHeight("menuFileTable"));
    }
    return ypSlideOutMenu.displayDropMenu(id, loc);
  }
  function updateMenu(trashed){
    if (trashed == 'true'){
      hideSpan('menuRenameFolder');
      hideSpan('menuMoveFolder');
      hideSpan('menuDeleteFolder');
      hideSpan('menuRenameFile');
      hideSpan('menuAddFileVersion');
      hideSpan('menuMoveFile');
      hideSpan('menuDeleteFile');
    } else {
      showSpan('menuRenameFolder');
      showSpan('menuMoveFolder');
      showSpan('menuDeleteFolder');
      showSpan('menuRenameFile');
      showSpan('menuAddFileVersion');
      showSpan('menuMoveFile');
      showSpan('menuDeleteFile');
    }
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
          <dhv:label name="accounts.accounts_documents_list_menu.ViewFolder">View Folder</dhv:label>
        </td>
      </tr>
    </zeroio:permission>
    <zeroio:permission name="project-documents-folders-edit">
      <tr id="menuRenameFolder" onmouseover="cmOver(this)" onmouseout="cmOut(this)"
          onclick="editFolder()">
        <th>
          <img src="images/icons/stock_rename-page-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td width="100%">
          <dhv:label name="accounts.accounts_documents_list_menu.RenameFolder">Rename Folder</dhv:label>
        </td>
      </tr>
    </zeroio:permission>
    <zeroio:permission name="project-documents-folders-edit">
      <tr id="menuMoveFolder" onmouseover="cmOver(this)" onmouseout="cmOut(this)"
          onclick="moveFolder()">
        <th>
          <img src="images/icons/stock_drag-mode-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td>
          <dhv:label name="accounts.accounts_documents_list_menu.MoveFolder">Move Folder</dhv:label>
        </td>
      </tr>
    </zeroio:permission>
    <zeroio:permission name="project-documents-folders-delete">
      <tr id="menuDeleteFolder" onmouseover="cmOver(this)" onmouseout="cmOut(this)"
          onclick="deleteFolder()">
        <th valign="top">
          <img src="images/icons/stock_left-with-subpoints-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td>
          <dhv:label name="accounts.accounts_documents_list_menu.DeleteFolderMoveContents">Delete Folder and Move contents to current folder</dhv:label>
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
          <dhv:label name="accounts.accounts_documents_list_menu.ViewFileHistory">View File History</dhv:label>
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
          <dhv:label name="accounts.accounts_contacts_detailsimport.DownloadFile">Download File</dhv:label>
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
          <dhv:label name="accounts.accounts_documents_list_menu.ViewFileContents">View File Contents</dhv:label>
        </td>
      </tr>
      </zeroio:permission>
      <zeroio:permission name="project-documents-files-rename">
      <tr id="menuRenameFile" onmouseover="cmOver(this)" onmouseout="cmOut(this)"
          onclick="renameFile()">
        <th>
          <img src="images/icons/stock_rename-page-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td>
          <dhv:label name="accounts.accounts_documents_list_menu.RenameFile">Rename File</dhv:label>
        </td>
      </tr>
      </zeroio:permission>
      <zeroio:permission name="project-documents-files-upload">
      <tr id="menuAddFileVersion" onmouseover="cmOver(this)" onmouseout="cmOut(this)"
          onclick="addVersion()">
        <th>
          <img src="images/icons/stock_insert-file-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td>
          <dhv:label name="accounts.accounts_documents_list_menu.AddVersion">Add Version</dhv:label>
        </td>
      </tr>
      </zeroio:permission>
      <zeroio:permission name="project-documents-files-rename">
      <tr id="menuMoveFile" onmouseover="cmOver(this)" onmouseout="cmOut(this)"
          onclick="moveFile()">
        <th>
          <img src="images/icons/stock_drag-mode-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td>
          <dhv:label name="accounts.accounts_documents_list_menu.MoveFile">Move File</dhv:label>
        </td>
      </tr>
      </zeroio:permission>
      <zeroio:permission name="project-documents-files-delete">
      <tr id="menuDeleteFile" onmouseover="cmOver(this)" onmouseout="cmOut(this)"
          onclick="deleteFile()">
        <th>
          <img src="images/icons/stock_delete-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td>
          <dhv:label name="documents.documents.deleteFile">Delete File</dhv:label>
        </td>
      </tr>
      </zeroio:permission>
    </table>
  </div>
</div>
