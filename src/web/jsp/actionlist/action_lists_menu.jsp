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
