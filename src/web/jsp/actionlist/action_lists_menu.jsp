<%-- 
  - Copyright Notice: (C) 2000-2004 Dark Horse Ventures, All Rights Reserved.
  - License: This source code cannot be modified, distributed or used without
  -          written permission from Dark Horse Ventures. This notice must
  -          remain in place.
  - Version: $Id$
  - Description: 
  --%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<script language="javascript">
  var thisActionId = -1;
  var thisLinkId = -1;
  var menu_init = false;
  //Set the action parameters for clicked item
  function displayMenu(loc, id, actionId, linkId) {
    thisLinkId = linkId;
    thisActionId = actionId;
    if (!menu_init) {
      menu_init = true;
      new ypSlideOutMenu("menuAction", "down", 0, 0, 170, getHeight("menuActionTable"));
    }
    return ypSlideOutMenu.displayDropMenu(id, loc);
  }
  
  //Menu link functions
  function details() {
    window.location.href = 'MyActionContacts.do?command=List&actionId=' + thisActionId + '&reset=true';
  }
  
  function modify() {
    window.location.href = 'MyActionLists.do?command=Modify&id=' + thisActionId;
  }
  
  function deleteAction() {
   popURLReturn('MyActionLists.do?command=ConfirmDelete&id=' + thisActionId + '&popup=true&linkModuleId=' + thisLinkId,'MyActionLists.do?command=List', 'Delete_message','320','200','yes','no');
  }
  
  function addContacts(){
    window.location.href='MyActionContacts.do?command=Prepare&actionId=' + thisActionId + '&return=list&params=' + escape('filters=all|mycontacts|accountcontacts');
  }
  
</script>
<div id="menuActionContainer" class="menu">
  <div id="menuActionContent">
    <table id="menuActionTable" class="pulldown" width="170" cellspacing="0">
      <dhv:permission name="myhomepage-action-lists-view">
      <tr onmouseover="cmOver(this)" onmouseout="cmOut(this)" onclick="details()">
        <td>
          &nbsp;
        </td>
        <td width="100%">
          View Details
        </td>
      </tr>
      </dhv:permission>
      <dhv:permission name="myhomepage-action-lists-edit">
      <tr onmouseover="cmOver(this)" onmouseout="cmOut(this)" onclick="modify()">
        <td>
          &nbsp;
        </td>
        <td width="100%">
          Modify
        </td>
      </tr>
      </dhv:permission>
      <dhv:permission name="myhomepage-action-lists-edit">
      <tr onmouseover="cmOver(this)" onmouseout="cmOut(this)" onclick="addContacts()">
        <td>
          &nbsp;
        </td>
        <td width="100%">
          Add Contacts
        </td>
      </tr>
      </dhv:permission>
      <dhv:permission name="myhomepage-action-lists-delete">
      <tr onmouseover="cmOver(this)" onmouseout="cmOut(this)" onclick="deleteAction()">
        <td>
          &nbsp;
        </td>
        <td width="100%">
          Delete
        </td>
      </tr>
      </dhv:permission>
    </table>
  </div>
</div>
