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
  var thisFolderId = -1;
  var thisOrgId = -1;
  var thisFileId = -1;
  var menu_init = false;
  //Set the action parameters for clicked item
  function displayMenu(loc, id, folderId, fileId, orgId) {
    thisFolderId = folderId
    thisOrgId = orgId;
    thisFileId = fileId;
    if (!menu_init) {
      menu_init = true;
      new ypSlideOutMenu("menuFolder", "down", 0, 0, 170, getHeight("menuFolderTable"));
      new ypSlideOutMenu("menuFile", "down", 0, 0, 170, getHeight("menuFileTable"));
    }
    return ypSlideOutMenu.displayDropMenu(id, loc);
  }

  //Menu link functions

  //Folder operations
  function viewFolder() {
    window.location.href='AccountsDocuments.do?command=View&orgId=' + thisOrgId + '&folderId=' + thisFolderId;
  }

  function editFolder() {
    window.location.href='AccountsDocumentsFolders.do?command=Modify&orgId=' + thisOrgId + '&folderId=' + thisFileId + '&id=' + thisFolderId + '&parentId='+thisFileId;
  }
  function moveFolder() {
    popURL('AccountsDocumentsFolders.do?command=Move&orgId=' + thisOrgId + '&id=' + thisFolderId + '&popup=true&return=AccountsDocuments&param='+ thisOrgId+'&param2='+ thisFolderId ,'Files','400','375','yes','yes');
  }
  function deleteFolder() {
    confirmDelete('AccountsDocumentsFolders.do?command=Delete&orgId=' + thisOrgId + '&id=' + thisFolderId + '&folderId=' + thisFileId );
  }

  //File operations
  function viewFileHistory() {
    document.location.href='AccountsDocuments.do?command=Details&orgId='+ thisOrgId +'&fid=' + thisFileId + '&folderId='+thisFolderId;
  }
  function details() {
    window.location.href='AccountsDocuments.do?command=Details&orgId=' + thisOrgId + '&fid=' + thisFileId+'&folderId='+ thisFolderId;
  }
  function modify() {
    window.location.href='AccountsDocuments.do?command=Modify&orgId=' + thisOrgId + '&fid=' + thisFileId +'&folderId='+ thisFolderId;
  }
  function download() {
    window.location.href='AccountsDocuments.do?command=Download&orgId=' + thisOrgId + '&fid=' + thisFileId+'&folderId='+ thisFolderId;
  }
  function view() {
    popURL('AccountsDocuments.do?command=Download&orgId='+ thisOrgId +'&fid=' + thisFileId + '&view=true', 'Content', 640,480, 1, 1);
  }
  function addVersion() {
    document.location.href='AccountsDocuments.do?command=AddVersion&orgId='+ thisOrgId +'&fid=' + thisFileId + '&folderId='+ thisFolderId;
  }
  function moveFile() {
    popURL('AccountsDocuments.do?command=Move&orgId='+ thisOrgId+'&fid=' + thisFileId + '&popup=true&return=AccountsDocuments&param='+thisOrgId+'&param2='+thisFolderId,'Files','400','375','yes','yes');
  }
  function deleteFile() {
    confirmDelete('AccountsDocuments.do?command=Delete&fid=' + thisFileId + '&orgId=' + thisOrgId+'&folderId='+ thisFolderId);
  }
  
</script>
<div id="menuFileContainer" class="menu">
  <div id="menuFileContent">
    <table id="menuFileTable" class="pulldown" width="170" cellspacing="0">
      <dhv:permission name="accounts-accounts-documents-view">
      <tr onmouseover="cmOver(this)" onmouseout="cmOut(this)"
          onclick="viewFileHistory()">
        <th>
          <img src="images/icons/stock_zoom-page-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td width="100%">
          View File History
        </td>
      </tr>
      </dhv:permission>
      <dhv:permission name="accounts-accounts-documents-view">
      <tr onmouseover="cmOver(this)" onmouseout="cmOut(this)" onclick="download();">
        <th>
          <img src="images/icons/stock_data-save-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td width="100%">
          Download File
        </td>
      </tr>
      </dhv:permission>
      <dhv:permission name="accounts-accounts-documents-view">
      <tr onmouseover="cmOver(this)" onmouseout="cmOut(this)" onclick="view();">
        <th>
          <img src="images/icons/stock_zoom-page-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td width="100%">
          View File Contents
        </td>
      </tr>
      </dhv:permission>
      <dhv:permission name="accounts-accounts-documents-edit">
      <tr onmouseover="cmOver(this)" onmouseout="cmOut(this)" onclick="modify();">
        <th>
          <img src="images/icons/stock_edit-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td width="100%">
          Rename File
        </td>
      </tr>
      </dhv:permission>
      <dhv:permission name="accounts-accounts-documents-edit">
      <tr onmouseover="cmOver(this)" onmouseout="cmOut(this)"
          onclick="addVersion()">
        <th>
          <img src="images/icons/stock_insert-file-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td>
          Add Version
        </td>
      </tr>
      </dhv:permission>
      <dhv:permission name="accounts-accounts-documents-edit">
      <tr onmouseover="cmOver(this)" onmouseout="cmOut(this)"
          onclick="moveFile()">
        <th>
          <img src="images/icons/stock_drag-mode-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td>
          Move File
        </td>
      </tr>
      </dhv:permission>
      <dhv:permission name="accounts-accounts-documents-delete">
      <tr onmouseover="cmOver(this)" onmouseout="cmOut(this)" onclick="deleteFile();">
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
<div id="menuFolderContainer" class="menu">
  <div id="menuFolderContent">
    <table id="menuFolderTable" class="pulldown" width="170" cellspacing="0">
      <dhv:permission name="accounts-accounts-documents-view">
      <tr onmouseover="cmOver(this)" onmouseout="cmOut(this)"
          onclick="viewFolder();">
        <th valign="top">
          <img src="images/icons/stock_zoom-folder-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td width="100%">
          View Folder
        </td>
      </tr>
      </dhv:permission>
      <dhv:permission name="accounts-accounts-documents-edit">
      <tr onmouseover="cmOver(this)" onmouseout="cmOut(this)"
          onclick="editFolder();">
        <th>
          <img src="images/icons/stock_rename-page-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td width="100%">
          Rename Folder
        </td>
      </tr>
    </dhv:permission>
    <dhv:permission name="accounts-accounts-documents-edit">
      <tr onmouseover="cmOver(this)" onmouseout="cmOut(this)"
          onclick="moveFolder();">
        <th>
          <img src="images/icons/stock_drag-mode-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td>
          Move Folder
        </td>
      </tr>
    </dhv:permission>
    <dhv:permission name="accounts-accounts-documents-delete">
      <tr onmouseover="cmOver(this)" onmouseout="cmOut(this)"
          onclick="deleteFolder();">
        <th valign="top">
          <img src="images/icons/stock_left-with-subpoints-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td>
          Delete Folder and Move contents to current folder
        </td>
      </tr>
    </dhv:permission> 
    </table>
  </div>
</div>

