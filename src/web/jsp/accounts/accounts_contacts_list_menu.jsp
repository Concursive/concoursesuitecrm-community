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
  var thisOrgId = -1;
  var thisContactId = -1;
  var menu_init = false;
  var isPrimaryContact = false;
  //Set the action parameters for clicked item
  function displayMenu(loc, id, orgId, contactId, isPrimary) {
    thisOrgId = orgId;
    thisContactId = contactId;
    isPrimaryContact = isPrimary;
    if (!menu_init) {
      menu_init = true;
      new ypSlideOutMenu("menuContact", "down", 0, 0, 170, getHeight("menuContactTable"));
    }
    return ypSlideOutMenu.displayDropMenu(id, loc);
  }
  //Menu link functions
  function details() {
    window.location.href='Contacts.do?command=Details&id=' + thisContactId;
  }
  
  function modify() {
    window.location.href='Contacts.do?command=Modify&orgId=' + thisOrgId + '&id=' + thisContactId + '&return=list';
  }

  function move() {
    check('moveContact', thisOrgId, thisContactId, '&filters=all|my|disabled', isPrimaryContact);
  }
  
  function clone() {
    window.location.href='Contacts.do?command=Clone&orgId=' + thisOrgId + '&id=' + thisContactId + '&return=list';
  }
  
  function deleteContact() {
    popURLReturn('Contacts.do?command=ConfirmDelete&orgId=' + thisOrgId + '&id=' + thisContactId + '&popup=true','Contacts.do?command=View', 'Delete_contact','330','200','yes','no');
  }
  function moveTheContact() {
    popURLReturn('Contacts.do?command=MoveToAccount&orgId='+ thisOrgId + '&id='+ thisContactId + '&popup=true','Contacts.do?command=View', 'Move_contact','400','320','yes','no');
  }
  function sendMessage() {
    popURL('MyActionContacts.do?command=PrepareMessage&actionSource=MyActionContacts&orgId=' + thisOrgId + '&contactId=' + thisContactId + '&messageType=addressRequest' + '&popup=true','Message','700','550','yes','yes');
  }  
</script>
<div id="menuContactContainer" class="menu">
  <div id="menuContactContent">
    <table id="menuContactTable" class="pulldown" width="170" cellspacing="0">
      <dhv:permission name="accounts-accounts-contacts-view">
      <tr onmouseover="cmOver(this)" onmouseout="cmOut(this)"
           onclick="details()">
        <th valign="top">
          <img src="images/icons/stock_zoom-page-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td width="100%">
          <dhv:label name="accounts.accounts_calls_list_menu.ViewDetails">View Details</dhv:label>
        </td>
      </tr>
      </dhv:permission>
      <dhv:permission name="accounts-accounts-contacts-edit">
      <tr onmouseover="cmOver(this)" onmouseout="cmOut(this)"
           onclick="modify()">
        <th>
          <img src="images/icons/stock_edit-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td width="100%">
          <dhv:label name="global.button.modify">Modify</dhv:label>
        </td>
      </tr>
      </dhv:permission>
      <dhv:permission name="accounts-accounts-contacts-move-view">
      <tr onmouseover="cmOver(this)" onmouseout="cmOut(this)"
           onclick="moveTheContact()">
        <th>
          <img src="images/icons/stock_edit-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td width="100%">
          <dhv:label name="global.button.move">Move</dhv:label>
        </td>
      </tr>
      </dhv:permission>
      <dhv:permission name="accounts-accounts-contacts-add">
      <tr onmouseover="cmOver(this)" onmouseout="cmOut(this)"
           onclick="clone()">
        <th>
          <img src="images/icons/stock_copy-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td width="100%">
          <dhv:label name="global.button.Clone">Clone</dhv:label>
        </td>
      </tr>
      </dhv:permission>
      <%--
      <dhv:permission name="accounts-accounts-contacts-edit">
      <tr onmouseover="cmOver(this)" onmouseout="cmOut(this)"
           onclick="move()">
        <th>
          <img src="images/icons/stock_drag-mode-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td width="100%">
          <dhv:label name="global.button.Move">Move</dhv:label>
        </td>
      </tr>
      </dhv:permission>
      --%>
      <dhv:permission name="accounts-accounts-contacts-delete">
      <tr onmouseover="cmOver(this)" onmouseout="cmOut(this)"
           onclick="deleteContact()">
        <th>
          <img src="images/icons/stock_delete-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td width="100%">
          <dhv:label name="global.button.delete">Delete</dhv:label>
        </td>
      </tr>
      </dhv:permission>
      <dhv:permission name="accounts-accounts-contact-updater-view">
      <tr id="menuAddressRequest" onmouseover="cmOver(this)" onmouseout="cmOut(this)" onclick="sendMessage()">
        <th>
          <img src="images/icons/stock_mail-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td width="100%">
          <dhv:label name="global.button.sendAddressRequest">Send Address Request</dhv:label>
        </td>
      </tr>
      </dhv:permission>
     </table>
  </div>
</div>
