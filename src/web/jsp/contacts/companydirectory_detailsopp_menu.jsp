<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<script language="javascript">
  var thisContactId = -1;
  var thisHeaderId = -1;
  var thisCompId = -1;
  var menu_init = false;
  //Set the action parameters for clicked item
  function displayMenu(id, contactId, headerId, compId, editPermission, deletePermission) {
    thisContactId = contactId;
    thisHeaderId = headerId;
    thisCompId = compId;
     updateMenu(editPermission, deletePermission);
    if (!menu_init) {
      menu_init = true;
      new ypSlideOutMenu("menuOpp", "down", 0, 0, 170, getHeight("menuOppTable"));
    }
    return ypSlideOutMenu.displayMenu(id);
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
    <table id="menuOppTable" class="pulldown" width="170">
      <dhv:permission name="contacts-external_contacts-opportunities-view">
      <tr>
        <td>
          <img src="images/icons/stock_zoom-page-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </td>
        <td width="100%">
          <a href="javascript:details()">View Details</a>
        </td>
      </tr>
      </dhv:permission>
      <tr id="menuEdit">
        <td>
          <img src="images/icons/stock_edit-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </td>
        <td width="100%">
          <a href="javascript:modify()">Modify</a>
        </td>
      </tr>
      <tr id="menuDelete">
        <td>
          <img src="images/icons/stock_delete-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </td>
        <td width="100%">
          <a href="javascript:deleteOpp()">Delete</a>
        </td>
      </tr>
    </table>
  </div>
</div>
