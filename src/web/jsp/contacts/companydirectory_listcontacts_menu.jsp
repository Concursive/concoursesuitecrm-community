<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<script language="javascript">
  var thisContactId = -1;
  var menu_init = false;
  //Set the action parameters for clicked item
  function displayMenu(id, contactId, editPermission, deletePermission, clonePermission) {
    thisContactId = contactId;
    updateMenu(editPermission, deletePermission, clonePermission);
    if (!menu_init) {
      menu_init = true;
      new ypSlideOutMenu("menuContact", "down", 0, 0, 170, getHeight("menuContactTable"));
    }
    return ypSlideOutMenu.displayMenu(id);
  }
  
  //Update menu for this Contact based on permissions
  function updateMenu(hasEditPermission, hasDeletePermission, hasClonePermission){
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
    
    if(hasClonePermission == 0){
      hideSpan('menuClone');
    }else{
      showSpan('menuClone');
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
  
</script>
<div id="menuContactContainer" class="menu">
  <div id="menuContactContent">
    <table id="menuContactTable" class="pulldown" width="170">
      <dhv:permission name="contacts-external_contacts-view">
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
      <tr id="menuClone">
        <td>
          &nbsp;
        </td>
        <td width="100%">
          <a href="javascript:clone()">Clone</a>
        </td>
      </tr>
      <tr id="menuDelete">
        <td>
          <img src="images/icons/stock_delete-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </td>
        <td width="100%">
          <a href="javascript:deleteContact()">Delete</a>
        </td>
      </tr>
    </table>
  </div>
</div>
