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
  var thisHeaderId = -1;
  var thisContactId = -1;
  var thisId = -1;
  var menu_init = false;
  //Set the action parameters for clicked item
  function displayMenu(loc, id, headerId, compId, contactId, trashed, hasPermission) {
    thisHeaderId = headerId;
    thisId = compId;
    thisContactId = contactId;
    if (hasPermission == 'true') {
      updateMenu(trashed);
    } else {
      updateMenu('true');
    }
    if (!menu_init) {
      menu_init = true;
      new ypSlideOutMenu("menuOpp", "down", 0, 0, 170, getHeight("menuOppTable"));
    }
    return ypSlideOutMenu.displayDropMenu(id, loc);
  }

  function updateMenu(trashed){
    if (trashed == 'true'){
      hideSpan('menuOppCompModify');
      hideSpan('menuOppCompDelete');
    } else {
      showSpan('menuOppCompModify');
      showSpan('menuOppCompDelete');
    }
  }
  //Menu link functions
  function details() {
    window.location.href='AccountContactsOppComponents.do?command=DetailsComponent&id=' + thisId + '&contactId=' + thisContactId+'<%= addLinkParams(request, "popup|popupType") %>';
  }

  function log() {
    window.location.href='AccountContactsOppComponents.do?command=ComponentHistory&contactId=' + thisContactId + '&headerId=' + thisHeaderId + '&id=' + thisId+'<%= addLinkParams(request, "popup|popupType") %>';
  }
  
  function modify() {
    window.location.href='AccountContactsOppComponents.do?command=ModifyComponent&headerId=' + thisHeaderId + '&contactId=' + thisContactId + '&id=' + thisId + '&return=list&actionSource=AccountContactsOppComponents'+'<%= addLinkParams(request, "popup|popupType") %>';
  }

  function deleteOpp() {
    popURLReturn('AccountContactsOppComponents.do?command=ConfirmComponentDelete&contactId=' + thisContactId + '&id=' + thisId + '&popup=true<%= isPopup(request)?"&popupType=inline":"" %>','AccountContactsOpps.do?command=ViewOpps' + '&contactId=' + thisContactId, 'Delete_contact','330','200','yes','no');
  }
</script>
<div id="menuOppContainer" class="menu">
  <div id="menuOppContent">
    <table id="menuOppTable" class="pulldown" width="170" cellspacing="0">
      <dhv:permission name="accounts-accounts-contacts-opportunities-view">
      <tr onmouseover="cmOver(this)" onmouseout="cmOut(this)" onclick="details()">
        <th>
          <img src="images/icons/stock_zoom-page-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td width="100%">
          <dhv:label name="accounts.accounts_calls_list_menu.ViewDetails">View Details</dhv:label>
        </td>
      </tr>
      </dhv:permission>
      <dhv:permission name="accounts-accounts-contacts-opportunities-view">
      <tr onmouseover="cmOver(this)" onmouseout="cmOut(this)" onclick="log()">
        <th>
          <img src="images/icons/stock_zoom-page-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td width="100%">
          <dhv:label name="accounts.accounts_contacts_oppcomponent.viewComponentLog">View Component Log</dhv:label>
        </td>
      </tr>
      </dhv:permission>
      <dhv:permission name="accounts-accounts-contacts-opportunities-edit">
      <tr id="menuOppCompModify" onmouseover="cmOver(this)" onmouseout="cmOut(this)" onclick="modify()">
        <th>
          <img src="images/icons/stock_edit-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td width="100%">
          <dhv:label name="global.button.modify">Modify</dhv:label>
        </td>
      </tr>
      </dhv:permission>
      <dhv:permission name="accounts-accounts-contacts-opportunities-delete">
      <tr id="menuOppCompDelete" onmouseover="cmOver(this)" onmouseout="cmOut(this)" onclick="deleteOpp()">
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
