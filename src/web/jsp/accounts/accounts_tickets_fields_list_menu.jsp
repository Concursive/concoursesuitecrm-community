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
  var thisTicketId = -1;
  var thisCatId = -1;
  var thisRecId = -1;
  var menu_init = false;
  //Set the action parameters for clicked item
  function displayMenu(loc, id, ticketId, catId, recId) {
    thisTicketId = ticketId;
    thisCatId = catId;
    thisRecId = recId;
    if (!menu_init) {
      menu_init = true;
      new ypSlideOutMenu("menuFolders", "down", 0, 0, 170, getHeight("menuFoldersTable"));
    }
    return ypSlideOutMenu.displayDropMenu(id, loc);
  }

  //Menu link functions
  function folderDetails() {
    window.location.href='AccountTicketFolders.do?command=Fields&ticketId=' + thisTicketId + '&catId=' + thisCatId + '&recId=' + thisRecId+'<%= addLinkParams(request, "popup|popupType|actionId") %>';
  }
  
  function modify() {
    window.location.href = 'AccountTicketFolders.do?command=ModifyFields&ticketId=' + thisTicketId + '&catId=' + thisCatId + '&recId=' + thisRecId + '&return=list<%= addLinkParams(request, "popup|popupType|actionId") %>';
  }
  
  function deleteFolder() {
    confirmDelete('AccountTicketFolders.do?command=DeleteFields&ticketId='+ thisTicketId + '&catId=' + thisCatId + '&recId=' + thisRecId + '&return=list<%= addLinkParams(request, "popup|popupType|actionId") %>');
  }
</script>
<div id="menuFoldersContainer" class="menu">
  <div id="menuFoldersContent">
    <table id="menuFoldersTable" class="pulldown" width="170" cellspacing="0">
      <dhv:permission name="accounts-accounts-folders-view">
      <tr onmouseover="cmOver(this)" onmouseout="cmOut(this)" onclick="folderDetails()">
        <th>
          <img src="images/icons/stock_zoom-page-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td width="100%">
          <dhv:label name="accounts.accounts_calls_list_menu.ViewDetails">View Details</dhv:label>
        </td>
      </tr>
      </dhv:permission>
      <dhv:permission name="accounts-accounts-folders-edit">
      <tr onmouseover="cmOver(this)" onmouseout="cmOut(this)" onclick="modify()">
        <th>
          <img src="images/icons/stock_edit-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td width="100%">
          <dhv:label name="global.button.modify">Modify</dhv:label>
        </td>
      </tr>
      </dhv:permission>
      <dhv:permission name="accounts-accounts-folders-delete">
      <tr onmouseover="cmOver(this)" onmouseout="cmOut(this)" onclick="deleteFolder()">
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
