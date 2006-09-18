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
  function displayMenu(loc, id, orgId, contactId, isPrimary, trashed) {
    thisOrgId = orgId;
    thisContactId = contactId;
    isPrimaryContact = isPrimary;
    updateMenu(trashed);
    if (!menu_init) {
      menu_init = true;
      new ypSlideOutMenu("menuContact", "down", 0, 0, 170, getHeight("menuContactTable"));
    }
    return ypSlideOutMenu.displayDropMenu(id, loc);
  }

  function updateMenu(trashed){
    if (trashed == 'true'){
      hideSpan('menuModify');
      hideSpan('menuClone');
      hideSpan('menuDelete');
      hideSpan('menuAddressRequest');
      hideSpan('menuMove');
      hideSpan('menuViewActivityList');
      hideSpan('menuAddActivity');
    } else {
      showSpan('menuModify');
      showSpan('menuClone');
      showSpan('menuDelete');
      showSpan('menuAddressRequest');
      showSpan('menuMove');
      showSpan('menuViewActivityList');
      showSpan('menuAddActivity');
    }
  }
  //Menu link functions
  function details() {
    window.location.href='Contacts.do?command=Details&id=' + thisContactId+'<%= isPopup(request)?"&popup=true":"" %>';
  }

  function modify() {
    window.location.href='Contacts.do?command=Modify&orgId=' + thisOrgId + '&id=' + thisContactId + '&return=list<%= isPopup(request)?"&popup=true":"" %>';
  }

  function move() {
    check('moveContact', thisOrgId, thisContactId, '&filters=all|my|disabled', isPrimaryContact);
  }

  function clone() {
    window.location.href='Contacts.do?command=Clone&orgId=' + thisOrgId + '&id=' + thisContactId + '&return=list<%= isPopup(request)?"&popup=true":"" %>';
  }

  function deleteContact() {
    popURLReturn('Contacts.do?command=ConfirmDelete&orgId=' + thisOrgId + '&id=' + thisContactId + '&popup=true<%= isPopup(request)?"&accountpopup=true":"" %>','Contacts.do?command=View', 'Delete_contact','330','200','yes','no');
  }

  function addActivity(){
    window.location.href='AccountContactsCalls.do?command=Log&contactId=' + thisContactId + '&return=list<%= isPopup(request)?"&popup=true":"" %>';
  }
  
 function scheduleActivity(){
    window.location.href='AccountContactsCalls.do?command=Schedule&contactId=' + thisContactId + '&return=list';
  }
  
  function viewActivityList(){
    window.location.href='AccountContactsCalls.do?command=View&contactId=' + thisContactId+'<%= isPopup(request)?"&popup=true":"" %>';
  }

  function moveTheContact() {
    popURLReturn('Contacts.do?command=MoveToAccount&orgId='+ thisOrgId + '&id='+ thisContactId + '&popup=true','Contacts.do?command=View', 'Move_contact','400','320','yes','no');
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
      <dhv:permission name="accounts-accounts-contacts-view">
      <tr id="menuView" onmouseover="cmOver(this)" onmouseout="cmOut(this)"
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
        <tr id="menuModify" onmouseover="cmOver(this)" onmouseout="cmOut(this)"
             onclick="modify()">
          <th>
            <img src="images/icons/stock_edit-16.gif" border="0" align="absmiddle" height="16" width="16"/>
          </th>
          <td width="100%">
            <dhv:label name="global.button.modify">Modify</dhv:label>
          </td>
        </tr>
      </dhv:permission>
      <dhv:permission name="accounts-accounts-contacts-calls-add">
        <tr id="menuAddActivity" onmouseover="cmOver(this)" onmouseout="cmOut(this)"
             onclick="addActivity()">
          <th>
            <img src="images/icons/stock_zoom-page-16.gif" border="0" align="absmiddle" height="16" width="16"/>
          </th>
          <td width="100%">
            <dhv:label name="accounts.accounts_contacts_calls_list.LogAnActivity">Log an Activity</dhv:label>
          </td>
        </tr>
      </dhv:permission>
      <dhv:permission name="accounts-accounts-contacts-calls-add">
        <tr id="menuScheduleActivity" onmouseover="cmOver(this)" onmouseout="cmOut(this)"
             onclick="scheduleActivity()">
          <th>
            <img src="images/icons/stock_zoom-page-16.gif" border="0" align="absmiddle" height="16" width="16"/>
          </th>
          <td width="100%">
            <dhv:label name="accounts.accounts_contacts_calls_list.SchduleActivity">Schedule an Activity</dhv:label>
          </td>
        </tr>
      </dhv:permission>
      <dhv:permission name="accounts-accounts-contacts-calls-view">
        <tr id="menuViewActivityList" onmouseover="cmOver(this)" onmouseout="cmOut(this)"
            onclick="viewActivityList()">
          <th>
            <img src="images/icons/stock_zoom-page-16.gif" border="0" align="absmiddle" height="16" width="16"/>
          </th>
          <td width="100%">
            <dhv:label name="accounts.viewActivities">View Activities</dhv:label>
          </td>
        </tr>
      </dhv:permission>
      <dhv:permission name="accounts-accounts-contacts-move-view">
      <tr id="menuMove" onmouseover="cmOver(this)" onmouseout="cmOut(this)"
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
      <tr id="menuClone" onmouseover="cmOver(this)" onmouseout="cmOut(this)"
           onclick="clone()">
        <th>
          <img src="images/icons/stock_copy-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td width="100%">
          <dhv:label name="global.button.Clone">Clone</dhv:label>
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
      <dhv:permission name="accounts-accounts-contacts-view">
      <tr onmouseover="cmOver(this)" onmouseout="cmOut(this)" onclick="exportVCard()">
        <th>
          <img src="images/icons/stock_bcard-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td width="100%">
          <dhv:label name="button.downloadVcard">Download VCard</dhv:label>
        </td>
      </tr>
      </dhv:permission>

      <dhv:permission name="accounts-accounts-contacts-delete">
      <tr id="menuDelete" onmouseover="cmOver(this)" onmouseout="cmOut(this)"
           onclick="deleteContact()">
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
