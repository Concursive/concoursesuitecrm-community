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
  var thisContactId = -1;
  var thisHeaderId = -1;
  var thisCompId = -1;
  var menu_init = false;
  //Set the action parameters for clicked item
  function displayMenu(loc, id, contactId, headerId, compId, editPermission, deletePermission) {
    thisContactId = contactId;
    thisHeaderId = headerId;
    thisCompId = compId;
     updateMenu(editPermission, deletePermission);
    if (!menu_init) {
      menu_init = true;
      new ypSlideOutMenu("menuOpp", "down", 0, 0, 170, getHeight("menuOppTable"));
    }
    return ypSlideOutMenu.displayDropMenu(id, loc);
  }
  
  //Update menu for this Contact based on permissions
  function updateMenu(hasEditPermission, hasDeletePermission){
    if(hasEditPermission == 0){
      hideSpan('menuEdit');
    }else{
      showSpan('menuEdit');
    }
    
    if(hasDeletePermission == 0){
      hideSpan('menuDelete');
    }else{
      showSpan('menuDelete');
    }
  }
  
  //Menu link functions
  function details() {
    window.location.href='ExternalContactsOppComponents.do?command=DetailsComponent&contactId=' + thisContactId +  '&id=' + thisCompId + '<%= addLinkParams(request, "popup|popupType|actionId") %>';
  }
  
  function modify() {
      window.location.href = 'ExternalContactsOppComponents.do?command=ModifyComponent&headerId=' + thisHeaderId + '&id=' + thisCompId + '&contactId=' + thisContactId + '&return=list&actionSource=ExternalContactsOppComponents<%= addLinkParams(request, "popup|popupType|actionId") %>';
  }
  
  function deleteOpp() {
    popURLReturn('ExternalContactsOppComponents.do?command=ConfirmComponentDelete&contactId=' + thisContactId + '&id='  + thisCompId + '&popup=true<%= addLinkParams(request, "popupType|actionId") %>','ExternalContactsOpps.do?command=ViewOpps&contactId=' + thisContactId, 'Delete_opp','320','200','yes','no');
  }
  
</script>
<div id="menuOppContainer" class="menu">
  <div id="menuOppContent">
    <table id="menuOppTable" class="pulldown" width="170" cellspacing="0">
      <dhv:permission name="contacts-external_contacts-opportunities-view">
      <tr onmouseover="cmOver(this)" onmouseout="cmOut(this)" onclick="details()">
        <th>
          <img src="images/icons/stock_zoom-page-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td width="100%">
          View Details
        </td>
      </tr>
      </dhv:permission>
      <tr id="menuEdit" onmouseover="cmOver(this)" onmouseout="cmOut(this)" onclick="modify()">
        <th>
          <img src="images/icons/stock_edit-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td width="100%">
          Modify
        </td>
      </tr>
      <tr id="menuDelete" onmouseover="cmOver(this)" onmouseout="cmOut(this)" onclick="deleteOpp()">
        <th>
          <img src="images/icons/stock_delete-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td width="100%">
          Delete
        </td>
      </tr>
    </table>
  </div>
</div>
