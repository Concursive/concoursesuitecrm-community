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
  var thisContactId = -1;
  var thisOrgId = -1;
  var menu_init = false;
  //Set the action parameters for clicked item
  function displayMenu(loc, id, contactId, editPermission, deletePermission, clonePermission, addressRequestPermission, orgId, trashed) {
    thisContactId = contactId;
    thisOrgId = orgId;
    updateMenu(editPermission, deletePermission, clonePermission,addressRequestPermission, trashed);
    if (!menu_init) {
      menu_init = true;
      new ypSlideOutMenu("menuContact", "down", 0, 0, 170, getHeight("menuContactTable"));
    }
    return ypSlideOutMenu.displayDropMenu(id, loc);
  }

  //Update menu for this Contact based on permissions
  function updateMenu(hasEditPermission, hasDeletePermission, hasClonePermission, hasAddressRequestPermission, trashed){
    if (trashed == 'true') {
      hideSpan('menuEdit');
      hideSpan('menuDelete');
      hideSpan('menuClone');
      hideSpan('menuAddressRequest');
      hideSpan('menuMove');
    } else {
      if (hasEditPermission == 0){
        hideSpan('menuEdit');
      }else{
        showSpan('menuEdit');
      }

      if (hasDeletePermission == 0){
        hideSpan('menuDelete');
      }else{
        showSpan('menuDelete');
      }

      if(hasClonePermission == 0){
        hideSpan('menuClone');
      }else{
        showSpan('menuClone');
      }

      if(hasAddressRequestPermission == 0){
        hideSpan('menuAddressRequest');
      }else{
        showSpan('menuAddressRequest');
      }

      if (thisOrgId == -1) {
        hideSpan('menuMove');
      } else {
        showSpan('menuMove');
      }
    }
  }

  //Menu link functions
  function details() {
    window.location.href = 'ExternalContacts.do?command=ContactDetails&id=' + thisContactId + '<%= addLinkParams(request, "popup|popupType|actionId") %>';
  }
  
  function modify() {
    window.location.href = 'ExternalContacts.do?command=ModifyContact&id=' + thisContactId + '&return=list<%= addLinkParams(request, "popup|popupType|actionId") %>';
  }
  
  function clone() {
    window.location.href = 'ExternalContacts.do?command=Clone&id=' + thisContactId + '&return=list<%= addLinkParams(request, "popup|popupType|actionId") %>';
  }
  
  function deleteContact() {
    popURLReturn('ExternalContacts.do?command=ConfirmDelete&id=' + thisContactId + '&popup=true<%= addLinkParams(request, "popupType|actionId") %>','ExternalContacts.do?command=SearchContacts', 'Delete_contact','330','200','yes','no');
  }

  function moveTheContact() {
    popURLReturn('ExternalContacts.do?command=MoveToAccount&orgId='+ thisOrgId + '&id='+ thisContactId + '&popup=true','Contacts.do?command=View', 'Move_contact','400','320','yes','no');
  }

  function sendMessage() {
    popURL('MyActionContacts.do?command=PrepareMessage&actionSource=MyActionContacts&orgId=' + thisOrgId + '&contactId=' + thisContactId + '&messageType=addressRequest' + '&popup=true','Message','700','550','yes','yes');
  }

  function exportVCard() {
    window.location.href = 'ExternalContacts.do?command=DownloadVCard&id=' + thisContactId;
  }

</script>
<div id="menuContactContainer" class="menu">
  <div id="menuContactContent">
    <table id="menuContactTable" class="pulldown" width="170" cellspacing="0">
      <dhv:permission name="contacts-external_contacts-view">
      <tr onmouseover="cmOver(this)" onmouseout="cmOut(this)" onclick="details()">
        <th>
          <img src="images/icons/stock_zoom-page-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td width="100%">
          <dhv:label name="accounts.accounts_calls_list_menu.ViewDetails">View Details</dhv:label>
        </td>
      </tr>
      </dhv:permission>
      <tr id="menuEdit" onmouseover="cmOver(this)" onmouseout="cmOut(this)" onclick="modify()">
        <th>
          <img src="images/icons/stock_edit-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td width="100%">
          <dhv:label name="global.button.modify">Modify</dhv:label>
        </td>
      </tr>
      <tr id="menuMove" onmouseover="cmOver(this)" onmouseout="cmOut(this)" onclick="moveTheContact()">
        <th>
          <img src="images/icons/stock_move-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td width="100%">
          <dhv:label name="global.button.move">Move</dhv:label>
        </td>
      </tr>
      <tr id="menuClone" onmouseover="cmOver(this)" onmouseout="cmOut(this)" onclick="clone()">
        <th>
          <img src="images/icons/stock_copy-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td width="100%">
          <dhv:label name="global.button.Clone">Clone</dhv:label>
        </td>
      </tr>
      <tr id="menuAddressRequest" onmouseover="cmOver(this)" onmouseout="cmOut(this)" onclick="sendMessage()">
        <th>
          <img src="images/icons/stock_mail-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td width="100%">
          <dhv:label name="global.button.sendAddressRequest">Send Address Request</dhv:label>
        </td>
      </tr>
      <dhv:permission name="contacts-external_contacts-view">
      <tr onmouseover="cmOver(this)" onmouseout="cmOut(this)" onclick="exportVCard()">
        <th>
          <img src="images/icons/stock_bcard-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td width="100%">
          <dhv:label name="button.downloadVcard">Download VCard</dhv:label>
        </td>
      </tr>
      </dhv:permission>
      <tr id="menuDelete" onmouseover="cmOver(this)" onmouseout="cmOut(this)" onclick="deleteContact()">
        <th>
          <img src="images/icons/stock_delete-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td width="100%">
          <dhv:label name="global.button.delete">Delete</dhv:label>
        </td>
      </tr>
    </table>
  </div>
</div>
