<%-- 
  - Copyright(c) 2004 Concursive Corporation (http://www.concursive.com/) All
  - rights reserved. This material cannot be distributed without written
  - permission from Concursive Corporation. Permission to use, copy, and modify
  - this material for internal use is hereby granted, provided that the above
  - copyright notice and this permission notice appear in all copies. CONCURSIVE
  - CORPORATION MAKES NO REPRESENTATIONS AND EXTENDS NO WARRANTIES, EXPRESS OR
  - IMPLIED, WITH RESPECT TO THE SOFTWARE, INCLUDING, BUT NOT LIMITED TO, THE
  - IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR ANY PARTICULAR
  - PURPOSE, AND THE WARRANTY AGAINST INFRINGEMENT OF PATENTS OR OTHER
  - INTELLECTUAL PROPERTY RIGHTS. THE SOFTWARE IS PROVIDED "AS IS", AND IN NO
  - EVENT SHALL CONCURSIVE CORPORATION OR ANY OF ITS AFFILIATES BE LIABLE FOR
  - ANY DAMAGES, INCLUDING ANY LOST PROFITS OR OTHER INCIDENTAL OR CONSEQUENTIAL
  - DAMAGES RELATING TO THE SOFTWARE.
  - 
  - Version: $Id$
  - Description: 
  --%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<script language="javascript">
  var thisFolderId = -1;
  var thisHeaderId = -1;
  var thisFileId = -1;
  var menu_init = false;
  //Set the action parameters for clicked item
  function displayMenu(loc, id, folderId, fileId, headerId, hasPermission) {
    thisFolderId = folderId
    thisHeaderId = headerId;
    thisFileId = fileId;
    updateMenu(hasPermission);
    if (!menu_init) {
      menu_init = true;
      new ypSlideOutMenu("menuFolder", "down", 0, 0, 170, getHeight("menuFolderTable"));
      new ypSlideOutMenu("menuFile", "down", 0, 0, 170, getHeight("menuFileTable"));
    }
    return ypSlideOutMenu.displayDropMenu(id, loc);
  }
  
  function updateMenu(show) {
    if (show == 'false') {
      hideSpan("modifyFile");
      hideSpan("addVersion");
      hideSpan("moveFile");
      hideSpan("deleteFile");
      hideSpan("renameFolder");
      hideSpan("moveFolder");
      hideSpan("deleteFolder");
    } else {
      showSpan("modifyFile");
      showSpan("addVersion");
      showSpan("moveFile");
      showSpan("deleteFile");
      showSpan("renameFolder");
      showSpan("moveFolder");
      showSpan("deleteFolder");
    }
  }
  
  //Menu link functions

  //Folder operations
  function viewFolder() {
    window.location.href='LeadsDocuments.do?command=View&headerId=' + thisHeaderId + '&folderId=' + thisFolderId;
  }

  function editFolder() {
    window.location.href='LeadsDocumentsFolders.do?command=Modify&headerId=' + thisHeaderId + '&folderId=' + thisFileId + '&id=' + thisFolderId + '&parentId='+thisFileId;
  }
  function moveFolder() {
    popURL('LeadsDocumentsFolders.do?command=Move&headerId=' + thisHeaderId + '&id=' + thisFolderId + '&popup=true&return=LeadsDocuments&param='+ thisHeaderId +'&param2='+ thisFolderId ,'Files','400','375','yes','yes');
  }
  function deleteFolder() {
    confirmDelete('LeadsDocumentsFolders.do?command=Delete&headerId=' + thisHeaderId + '&id=' + thisFolderId + '&folderId=' + thisFileId );
  }


  //File operations
  function viewFileHistory() {
    document.location.href='LeadsDocuments.do?command=Details&headerId=' + thisHeaderId +'&fid=' + thisFileId + '&folderId='+thisFolderId + '<%= addLinkParams(request, "viewSource") %>';
  }
  function details() {
    window.location.href = 'LeadsDocuments.do?command=Details&headerId=' + thisHeaderId + '&fid=' + thisFileId + '&folderId='+thisFolderId + '<%= addLinkParams(request, "viewSource") %>';
  }
  function view() {
    popURL('LeadsDocuments.do?command=Download&headerId='+ thisHeaderId +'&fid=' + thisFileId + '&view=true', 'Content', 640,480, 1, 1);
  }
  function modify() {
    window.location.href = 'LeadsDocuments.do?command=Modify&fid=' + thisFileId + '&headerId=' + thisHeaderId  + '&folderId='+thisFolderId + '<%= addLinkParams(request, "viewSource") %>';
  }
  function download() {
    window.location.href = 'LeadsDocuments.do?command=Download&headerId=' + thisHeaderId + '&fid=' + thisFileId + '&folderId='+thisFolderId + '<%= addLinkParams(request, "viewSource") %>';
  }
  function addVersion() {
    document.location.href='LeadsDocuments.do?command=AddVersion&headerId=' + thisHeaderId + '&fid=' + thisFileId + '&folderId='+ thisFolderId + '<%= addLinkParams(request, "viewSource") %>';
  }
  function moveFile() {
    popURL('LeadsDocuments.do?command=Move&headerId=' + thisHeaderId + '&fid=' + thisFileId + '<%= addLinkParams(request, "viewSource") %>' + '&popup=true&return=LeadsDocuments&param=' + thisHeaderId +'&param2='+thisFolderId,'Files','400','375','yes','yes');
  }
  function deleteFile() {
    confirmDelete('LeadsDocuments.do?command=Delete&fid=' + thisFileId + '&headerId=' + thisHeaderId  + '&folderId='+thisFolderId + '<%= addLinkParams(request, "viewSource") %>');
  }
  
</script>
<div id="menuFileContainer" class="menu">
  <div id="menuFileContent">
    <table id="menuFileTable" class="pulldown" width="170" cellspacing="0">
      <dhv:permission name="pipeline-opportunities-documents-view">
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
      <dhv:permission name="pipeline-opportunities-documents-view">
      <tr onmouseover="cmOver(this)" onmouseout="cmOut(this)" onclick="download();">
        <th>
          <img src="images/icons/stock_data-save-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td width="100%">
          <dhv:label name="accounts.accounts_contacts_detailsimport.DownloadFile">Download File</dhv:label>
        </td>
      </tr>
      </dhv:permission>
      <dhv:permission name="pipeline-opportunities-documents-view">
      <tr onmouseover="cmOver(this)" onmouseout="cmOut(this)" onclick="view();">
        <th>
          <img src="images/icons/stock_zoom-page-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td width="100%">
          <dhv:label name="accounts.accounts_documents_list_menu.ViewFileContents">View File Contents</dhv:label>
        </td>
      </tr>
      </dhv:permission>
      <dhv:permission name="pipeline-opportunities-documents-edit">
      <tr id="modifyFile"onmouseover="cmOver(this)" onmouseout="cmOut(this)" onclick="modify();">
        <th>
          <img src="images/icons/stock_edit-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td width="100%">
          <dhv:label name="accounts.accounts_documents_list_menu.RenameFile">Rename File</dhv:label>
        </td>
      </tr>
      </dhv:permission>
      <dhv:permission name="pipeline-opportunities-documents-edit">
      <tr id="addVersion" onmouseover="cmOver(this)" onmouseout="cmOut(this)"
          onclick="addVersion()">
        <th>
          <img src="images/icons/stock_insert-file-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td>
          <dhv:label name="accounts.accounts_documents_list_menu.AddVersion">Add Version</dhv:label>
        </td>
      </tr>
      </dhv:permission>
      <dhv:permission name="pipeline-opportunities-documents-edit">
      <tr id="moveFile" onmouseover="cmOver(this)" onmouseout="cmOut(this)"
          onclick="moveFile()">
        <th>
          <img src="images/icons/stock_drag-mode-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td>
          <dhv:label name="accounts.accounts_documents_list_menu.MoveFile">Move File</dhv:label>
        </td>
      </tr>
      </dhv:permission>
      <dhv:permission name="pipeline-opportunities-documents-delete">
      <tr id="deleteFile" onmouseover="cmOver(this)" onmouseout="cmOut(this)" onclick="deleteFile();">
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
      <dhv:permission name="pipeline-opportunities-documents-view">
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
      <dhv:permission name="pipeline-opportunities-documents-edit">
      <tr id="renameFolder" onmouseover="cmOver(this)" onmouseout="cmOut(this)"
          onclick="editFolder();">
        <th>
          <img src="images/icons/stock_rename-page-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td width="100%">
          <dhv:label name="accounts.accounts_documents_list_menu.RenameFolder">Rename Folder</dhv:label>
        </td>
      </tr>
    </dhv:permission>
      <dhv:permission name="pipeline-opportunities-documents-edit">
      <tr id="moveFolder" onmouseover="cmOver(this)" onmouseout="cmOut(this)"
          onclick="moveFolder();">
        <th>
          <img src="images/icons/stock_drag-mode-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td>
          <dhv:label name="accounts.accounts_documents_list_menu.MoveFolder">Move Folder</dhv:label>
        </td>
      </tr>
    </dhv:permission>
      <dhv:permission name="pipeline-opportunities-documents-delete">
      <tr id="deleteFolder" onmouseover="cmOver(this)" onmouseout="cmOut(this)"
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

