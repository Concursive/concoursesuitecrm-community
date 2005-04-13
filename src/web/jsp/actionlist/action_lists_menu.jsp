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
  var thisActionId = -1;
  var thisLinkId = -1;
  var menu_init = false;
  var thisHierarchy = -1;
  //Set the action parameters for clicked item
  function displayMenu(loc, id, actionId, linkId, hierarchy) {
    thisLinkId = linkId;
    thisActionId = actionId;
    thisHierarchy = hierarchy;
    if (!menu_init) {
      menu_init = true;
      new ypSlideOutMenu("menuAction", "down", 0, 0, 170, getHeight("menuActionTable"));
    }
    return ypSlideOutMenu.displayDropMenu(id, loc);
  }
  
  //Menu link functions
  function details() {
    if (thisHierarchy == -1) {
      window.location.href = 'MyActionContacts.do?command=List&actionId=' + thisActionId + '&reset=true';
    } else {
      window.location.href = 'MyActionContacts.do?command=List&actionId=' + thisActionId + '&reset=true&viewUserId='+ thisHierarchy ;
    }
  }
  
  function modify() {
    if (thisHierarchy == -1) {
      window.location.href = 'MyActionLists.do?command=Modify&id=' + thisActionId;
    } else {
      window.location.href = 'MyActionLists.do?command=Modify&id=' + thisActionId+ '&viewUserId='+ thisHierarchy;
    }
  }
  
  function deleteAction() {
    if (thisHierarchy == -1) {
      popURLReturn('MyActionLists.do?command=ConfirmDelete&id=' + thisActionId + '&popup=true&linkModuleId=' + thisLinkId,'MyActionLists.do?command=List', 'Delete_message','320','200','yes','no');
    } else {
      popURLReturn('MyActionLists.do?command=ConfirmDelete&id=' + thisActionId + '&popup=true&linkModuleId=' + thisLinkId + '&viewUserId='+ thisHierarchy,'MyActionLists.do?command=List', 'Delete_message','320','200','yes','no');
    }
  }
  
  function addContacts(){
    if (thisHierarchy == -1) {
      window.location.href='MyActionContacts.do?command=Prepare&actionId=' + thisActionId + '&return=list&params=' + escape('filters=all|mycontacts|accountcontacts');
    } else {
      window.location.href='MyActionContacts.do?command=Prepare&actionId=' + thisActionId + '&viewUserId='+ thisHierarchy + '&return=list&params=' + escape('filters=all|mycontacts|accountcontacts');
    }
  }
  
</script>
<div id="menuActionContainer" class="menu">
  <div id="menuActionContent">
    <table id="menuActionTable" class="pulldown" width="170" cellspacing="0">
      <dhv:permission name="myhomepage-action-lists-view">
      <tr onmouseover="cmOver(this)" onmouseout="cmOut(this)" onclick="details()">
        <th>
          &nbsp;
        </th>
        <td width="100%">
          <dhv:label name="accounts.accounts_calls_list_menu.ViewDetails">View Details</dhv:label>
        </td>
      </tr>
      </dhv:permission>
      <dhv:permission name="myhomepage-action-lists-edit">
      <tr onmouseover="cmOver(this)" onmouseout="cmOut(this)" onclick="modify()">
        <th>
          &nbsp;
        </th>
        <td width="100%">
          <dhv:label name="global.button.modify">Modify</dhv:label>
        </td>
      </tr>
      </dhv:permission>
      <dhv:permission name="myhomepage-action-lists-edit">
      <tr onmouseover="cmOver(this)" onmouseout="cmOut(this)" onclick="addContacts()">
        <th>
          &nbsp;
        </th>
        <td width="100%">
          <dhv:label name="actionList.addContacts">Add Contacts</dhv:label>
        </td>
      </tr>
      </dhv:permission>
      <dhv:permission name="myhomepage-action-lists-delete">
      <tr onmouseover="cmOver(this)" onmouseout="cmOut(this)" onclick="deleteAction()">
        <th>
          &nbsp;
        </th>
        <td width="100%">
          <dhv:label name="global.button.delete">Delete</dhv:label>
        </td>
      </tr>
      </dhv:permission>
    </table>
  </div>
</div>
