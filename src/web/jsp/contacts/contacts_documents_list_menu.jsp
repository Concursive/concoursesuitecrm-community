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
  - Version: $Id: contacts_documents_list_menu.jsp 17754 2006-12-11 vadim.vishnevsky@corratech.com $
  - Description: 
  --%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<script language="javascript">
  var thisFolderId = -1;
  var thisContactId = -1;
  var thisFileId = -1;
  var menu_init = false;
  //Set the action parameters for clicked item
  function displayMenu(loc, id, folderId, fileId, contactId) {
    thisFolderId = folderId
    thisContactId = contactId;
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
    window.location.href='ExternalContactsDocuments.do?command=View&contactId=' + thisContactId + '&folderId=' + thisFolderId+'<%= addLinkParams(request, "popup|popupType|actionId|actionplan") %>';
  }

  function editFolder() {
    window.location.href='ExternalContactsDocumentsFolders.do?command=Modify&contactId=' + thisContactId + '&folderId=' + thisFileId + '&id=' + thisFolderId + '&parentId='+thisFileId+'<%= addLinkParams(request, "popup|popupType|actionId|actionplan") %>';
  }
  function moveFolder() {
    popURL('ExternalContactsDocumentsFolders.do?command=Move&contactId=' + thisContactId + '&id=' + thisFolderId + '&popup=true&return=ExternalContactsDocuments&param='+ thisContactId+'&param2='+ thisFolderId ,'Files','400','375','yes','yes');
  }
  function deleteFolder() {
    confirmDelete('ExternalContactsDocumentsFolders.do?command=Delete&contactId=' + thisContactId + '&id=' + thisFolderId + '&folderId=' + thisFileId+'<%= addLinkParams(request, "popup|popupType|actionId|actionplan") %>' );
  }

  //File operations
  function viewFileHistory() {
    document.location.href='ExternalContactsDocuments.do?command=Details&contactId='+ thisContactId +'&fid=' + thisFileId + '&folderId='+thisFolderId+'<%= addLinkParams(request, "popup|popupType|actionId|actionplan") %>';
  }
  function details() {
    window.location.href='ExternalContactsDocuments.do?command=Details&contactId=' + thisContactId + '&fid=' + thisFileId+'&folderId='+ thisFolderId+'<%= addLinkParams(request, "popup|popupType|actionId|actionplan") %>';
  }
  function modify() {
    window.location.href='ExternalContactsDocuments.do?command=Modify&contactId=' + thisContactId + '&fid=' + thisFileId +'&folderId='+ thisFolderId+'<%= addLinkParams(request, "popup|popupType|actionId|actionplan") %>';
  }
  function download() {
    window.location.href='ExternalContactsDocuments.do?command=Download&contactId=' + thisContactId + '&fid=' + thisFileId+'&folderId='+ thisFolderId+'<%= addLinkParams(request, "popup|popupType|actionId|actionplan") %>';
  }
  function view() {
    popURL('ExternalContactsDocuments.do?command=Download&contactId='+ thisContactId +'&fid=' + thisFileId + '&view=true', 'Content', 640,480, 1, 1);
  }
  function addVersion() {
    document.location.href='ExternalContactsDocuments.do?command=AddVersion&contactId='+ thisContactId +'&fid=' + thisFileId + '&folderId='+ thisFolderId+'<%= addLinkParams(request, "popup|popupType|actionId|actionplan") %>';
  }
  function moveFile() {
    popURL('ExternalContactsDocuments.do?command=Move&contactId='+ thisContactId+'&fid=' + thisFileId + '&popup=true&return=ExternalContactsDocuments&param='+thisContactId+'&param2='+thisFolderId,'Files','400','375','yes','yes');
  }
  function deleteFile() {
    confirmDelete('ExternalContactsDocuments.do?command=Delete&fid=' + thisFileId + '&contactId=' + thisContactId+'&folderId='+ thisFolderId+'<%= addLinkParams(request, "popup|popupType|actionId|actionplan") %>');
  }
  
</script>
<div id="menuFileContainer" class="menu">
  <div id="menuFileContent">
    <table id="menuFileTable" class="pulldown" width="170" cellspacing="0">
      <dhv:permission name="contacts-external_contacts-documents-view">
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
      <dhv:permission name="contacts-external_contacts-documents-view">
      <tr onmouseover="cmOver(this)" onmouseout="cmOut(this)" onclick="download();">
        <th>
          <img src="images/icons/stock_data-save-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td width="100%">
          <dhv:label name="accounts.accounts_contacts_detailsimport.DownloadFile">Download File</dhv:label>
        </td>
      </tr>
      </dhv:permission>
      <dhv:permission name="contacts-external_contacts-documents-view">
      <tr onmouseover="cmOver(this)" onmouseout="cmOut(this)" onclick="view();">
        <th>
          <img src="images/icons/stock_zoom-page-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td width="100%">
          <dhv:label name="accounts.accounts_documents_list_menu.ViewFileContents">View File Contents</dhv:label>
        </td>
      </tr>
      </dhv:permission>
      <dhv:permission name="contacts-external_contacts-documents-edit">
      <tr onmouseover="cmOver(this)" onmouseout="cmOut(this)" onclick="modify();">
        <th>
          <img src="images/icons/stock_edit-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td width="100%">
          <dhv:label name="accounts.accounts_documents_list_menu.RenameFile">Rename File</dhv:label>
        </td>
      </tr>
      </dhv:permission>
      <dhv:permission name="contacts-external_contacts-documents-edit">
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
      <dhv:permission name="contacts-external_contacts-documents-edit">
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
      <dhv:permission name="contacts-external_contacts-documents-delete">
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
      <dhv:permission name="contacts-external_contacts-documents-view">
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
      <dhv:permission name="contacts-external_contacts-documents-edit">
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
    <dhv:permission name="contacts-external_contacts-documents-edit">
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
    <dhv:permission name="contacts-external_contacts-documents-delete">
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

