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
  var thisKbId = -1;
  var thisLinkId = -1;
  var thisCategoryId = -1;
  var menu_init = false;
  //Set the action parameters for clicked item
  function displayMenu(loc, id, kbId, linkId, categoryId) {
    thisKbId = kbId;
    thisLinkId = linkId;
    updateMenu(linkId);
    thisCategoryId = categoryId;
    if (!menu_init) {
      menu_init = true;
      new ypSlideOutMenu("menuKnowledgeBase", "down", 0, 0, 170, getHeight("menuKnowledgeBaseTable"));
    }
    return ypSlideOutMenu.displayDropMenu(id, loc);
  }
  function updateMenu(linkId) {
    if (linkId == '-1') {
      hideSpan("download");
      hideSpan("view");
    } else {
      showSpan("download");
      showSpan("view");
    }
  }
  //Menu link functions
  function details() {
    window.location.href = 'KnowledgeBaseManager.do?command=Details<%= isPopup(request) ? "&popup=true":"" %>&kbId=' + thisKbId;
  }
  
  function modify() {
    window.location.href = 'KnowledgeBaseManager.do?command=Modify<%= isPopup(request) ? "&popup=true":"" %>&kbId=' + thisKbId + '&categoryid='+thisCategoryId;
  }

  function download() {
    window.location.href='KnowledgeBaseManager.do?command=Download&kbId=' + thisKbId + '&fid=' + thisLinkId+'&folderId=-1';
  }

  function view() {
    popURL('KnowledgeBaseManager.do?command=Download&kbId='+ thisKbId +'&fid=' + thisLinkId + '&view=true', 'Content', 640,480, 1, 1);
  }

  function deleteTicketDefect() {
    popURL('KnowledgeBaseManager.do?command=ConfirmDelete&kbId=' + thisKbId + '<%= isPopup(request) ? "&isSourcePopup=true":"" %>&popup=true', 'Delete_KB','320','200','yes','no');
  }
</script>
<div id="menuKnowledgeBaseContainer" class="menu">
  <div id="menuKnowledgeBaseContent">
    <table id="menuKnowledgeBaseTable" class="pulldown" width="170" cellspacing="0">
      <dhv:permission name="tickets-knowledge-base-view">
      <tr onmouseover="cmOver(this)" onmouseout="cmOut(this)" onclick="details();">
        <th>
          <img src="images/icons/stock_zoom-page-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td width="100%">
          <dhv:label name="accounts.accounts_calls_list_menu.ViewDetails">View Details</dhv:label>
        </td>
      </tr>
      </dhv:permission>
      <dhv:permission name="tickets-knowledge-base-edit">
      <tr onmouseover="cmOver(this)" onmouseout="cmOut(this)" onclick="modify();">
        <th>
          <img src="images/icons/stock_edit-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td width="100%">
          <dhv:label name="global.button.modify">Modify</dhv:label>
        </td>
      </tr>
      </dhv:permission>
      <dhv:permission name="tickets-knowledge-base-view">
      <tr id="download" onmouseover="cmOver(this)" onmouseout="cmOut(this)" onclick="download();">
        <th>
          <img src="images/icons/stock_zoom-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td width="100%">
          <dhv:label name="accounts.accounts_contacts_detailsimport.DownloadFile">Download File</dhv:label>
        </td>
      </tr>
      </dhv:permission>
      <dhv:permission name="tickets-knowledge-base-view">
      <tr id="view" onmouseover="cmOver(this)" onmouseout="cmOut(this)" onclick="view();">
        <th>
          <img src="images/icons/stock_zoom-page-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td width="100%">
          <dhv:label name="accounts.accounts_documents_list_menu.ViewFileContents">View File Contents</dhv:label>
        </td>
      </tr>
      </dhv:permission>
      <dhv:permission name="tickets-knowledge-base-delete">
      <tr onmouseover="cmOver(this)" onmouseout="cmOut(this)" onclick="deleteTicketDefect();">
        <th>
          <img src="images/icons/stock_delete-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td>
          <dhv:label name="global.button.delete">Delete</dhv:label>
        </td>
      </tr>
      </dhv:permission>
    </table>
  </div>
</div>
