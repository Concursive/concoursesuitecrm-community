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
  var thisFolderId = -1;
  var thisTicId = -1;
  var thisFileId = -1;
  var menu_init = false;
  //Set the action parameters for clicked item
  function displayMenu(loc, id, folderId, fileId, ticId) {
    thisFolderId = folderId;
    thisTicId = ticId;
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
    window.location.href='AccountTicketsDocuments.do?command=View&tId=' + thisTicId + '&folderId=' + thisFolderId;
  }
  function editFolder() {
    window.location.href='AccountTicketsDocumentsFolders.do?command=Modify&tId=' + thisTicId + '&folderId=' + thisFileId + '&id=' + thisFolderId + '&parentId='+thisFileId;
  }
  function moveFolder() {
    popURL('AccountTicketsDocumentsFolders.do?command=Move&tId=' + thisTicId + '&id=' + thisFolderId + '&popup=true&return=AccountTicketsDocuments&param='+ thisTicId +'&param2='+ thisFolderId ,'Files','400','375','yes','yes');
  }
  function deleteFolder() {
    confirmDelete('AccountTicketsDocumentsFolders.do?command=Delete&tId=' + thisTicId + '&id=' + thisFolderId + '&folderId=' + thisFileId );
  }

  //File operations
  function viewFileHistory() {
    document.location.href='AccountTicketsDocuments.do?command=Details&&tId=' + thisTicId +'&fid=' + thisFileId + '&folderId='+thisFolderId;
  }
  function details() {
    window.location.href='AccountTicketsDocuments.do?command=Details&tId=' + thisTicId + '&fid=' + thisFileId + '&folderId='+thisFolderId;
  }
  function modify() {
    window.location.href='AccountTicketsDocuments.do?command=Modify&fid=' + thisFileId + '&tId=' + thisTicId + '&folderId='+thisFolderId;
  }
  function view() {
    popURL('AccountTicketsDocuments.do?command=Download&tId='+ thisTicId +'&fid=' + thisFileId + '&stream=true', 'Content', 640,480, 1, 1);
  }
  function download() {
    window.location.href='AccountTicketsDocuments.do?command=Download&tId=' + thisTicId + '&fid=' + thisFileId + '&folderId='+thisFolderId;
  }
  function addVersion() {
    document.location.href='AccountTicketsDocuments.do?command=AddVersion&tId=' + thisTicId + '&fid=' + thisFileId + '&folderId='+ thisFolderId;
  }
  function moveFile() {
    popURL('AccountTicketsDocuments.do?command=Move&tId=' + thisTicId + '&fid=' + thisFileId + '&popup=true&return=AccountTicketsDocuments&param=' + thisTicId +'&param2='+thisFolderId,'Files','400','375','yes','yes');
  }
  function deleteFile() {
    confirmDelete('AccountTicketsDocuments.do?command=Delete&fid='+ thisFileId + '&tId=' + thisTicId + '&folderId='+thisFolderId);
  }
  
</script>

<div id="menuFileContainer" class="menu">
  <div id="menuFileContent">
    <table id="menuFileTable" class="pulldown" width="170" cellspacing="0">
      <dhv:permission name="tickets-tickets-view">
      <tr onmouseover="cmOver(this)" onmouseout="cmOut(this)"
          onclick="viewFileHistory()">
        <th>
          <img src="images/icons/stock_zoom-page-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td width="100%">
          <dhv:label name="accounts.accounts_documents_list_menu.ViewFileHistory">View File History</dhv:label>
        </td>
      </tr>
      </dhv:permission>
      <dhv:permission name="tickets-tickets-edit">
      <tr onmouseover="cmOver(this)" onmouseout="cmOut(this)" onclick="download();">
        <th>
          <img src="images/icons/stock_data-save-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td width="100%">
          <dhv:label name="accounts.accounts_contacts_detailsimport.DownloadFile">Download File</dhv:label>
        </td>
      </tr>
      </dhv:permission>
      <dhv:permission name="tickets-tickets-view">
      <tr onmouseover="cmOver(this)" onmouseout="cmOut(this)" onclick="view();">
        <th>
          <img src="images/icons/stock_zoom-page-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td width="100%">
          <dhv:label name="accounts.accounts_documents_list_menu.ViewFileContents">View File Contents</dhv:label>
        </td>
      </tr>
      </dhv:permission>
      <dhv:permission name="tickets-tickets-edit">
      <tr onmouseover="cmOver(this)" onmouseout="cmOut(this)" onclick="modify();">
        <th>
          <img src="images/icons/stock_edit-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td width="100%">
          <dhv:label name="accounts.accounts_documents_list_menu.RenameFile">Rename File</dhv:label>
        </td>
      </tr>
      </dhv:permission>
      <dhv:permission name="tickets-tickets-edit">
      <tr onmouseover="cmOver(this)" onmouseout="cmOut(this)"
          onclick="addVersion()">
        <th>
          <img src="images/icons/stock_insert-file-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td>
          <dhv:label name="accounts.accounts_documents_list_menu.AddVersion">Add Version</dhv:label>
        </td>
      </tr>
      </dhv:permission>
      <dhv:permission name="tickets-tickets-edit">
      <tr onmouseover="cmOver(this)" onmouseout="cmOut(this)"
          onclick="moveFile()">
        <th>
          <img src="images/icons/stock_drag-mode-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td>
          <dhv:label name="accounts.accounts_documents_list_menu.MoveFile">Move File</dhv:label>
        </td>
      </tr>
      </dhv:permission>
      <dhv:permission name="tickets-tickets-delete">
      <tr onmouseover="cmOver(this)" onmouseout="cmOut(this)" onclick="deleteFile();">
        <th>
          <img src="images/icons/stock_delete-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td width="100%">
          <dhv:label name="global.button.delete">Delete</dhv:label>
        </td>
      </tr>
      </dhv:permission>
    </table>
  </div>
</div>
<div id="menuFolderContainer" class="menu">
  <div id="menuFolderContent">
    <table id="menuFolderTable" class="pulldown" width="170" cellspacing="0">
      <dhv:permission name="tickets-tickets-view">
      <tr onmouseover="cmOver(this)" onmouseout="cmOut(this)"
          onclick="viewFolder();">
        <th valign="top">
          <img src="images/icons/stock_zoom-folder-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td width="100%">
          <dhv:label name="accounts.accounts_documents_list_menu.ViewFolder">View Folder</dhv:label>
        </td>
      </tr>
    </dhv:permission>
      <dhv:permission name="tickets-tickets-edit">
      <tr onmouseover="cmOver(this)" onmouseout="cmOut(this)"
          onclick="editFolder();">
        <th>
          <img src="images/icons/stock_rename-page-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td width="100%">
          <dhv:label name="accounts.accounts_documents_list_menu.RenameFolder">Rename Folder</dhv:label>
        </td>
      </tr>
    </dhv:permission>
      <dhv:permission name="tickets-tickets-edit">
      <tr onmouseover="cmOver(this)" onmouseout="cmOut(this)"
          onclick="moveFolder();">
        <th>
          <img src="images/icons/stock_drag-mode-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td>
          <dhv:label name="accounts.accounts_documents_list_menu.MoveFolder">Move Folder</dhv:label>
        </td>
      </tr>
    </dhv:permission>
      <dhv:permission name="tickets-tickets-delete">
      <tr onmouseover="cmOver(this)" onmouseout="cmOut(this)"
          onclick="deleteFolder();">
        <th valign="top">
          <img src="images/icons/stock_left-with-subpoints-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td>
          <dhv:label name="accounts.accounts_documents_list_menu.DeleteFolderMoveContents">Delete Folder and Move contents to current folder</dhv:label>
        </td>
      </tr>
    </dhv:permission> 
    </table>
  </div>
</div>

