<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<script language="javascript">
  var thisContactId = -1;
  var menu_init = false;
  //Set the action parameters for clicked item
  function displayMenu(id, contactId, deletePermission) {
    thisContactId = contactId;
    updateMenu(deletePermission);
    if (!menu_init) {
      menu_init = true;
      new ypSlideOutMenu("menuContact", "down", 0, 0, 170, getHeight("menuContactTable"));
    }
    return ypSlideOutMenu.displayMenu(id);
  }
  
  //Update menu for this Contact based on permissions
  function updateMenu(hasDeletePermission){
    if(document.getElementById('menuDelete') != null){
      if(hasDeletePermission == 0){
        hideSpan('menuDelete');
      }else{
        showSpan('menuDelete');
      }
    }
  }
  //Menu link functions
  function details() {
    window.location.href = 'ExternalContactsImports.do?command=ContactDetails&contactId=' + thisContactId;
  }
  
  function deleteContact() {
    url = 'ExternalContactsImports.do?command=DeleteContact&contactId=' + thisContactId + '&importId=<%= ImportDetails.getId() %>&return=list';
    confirmDelete(url);
  }
  
</script>
<div id="menuContactContainer" class="menu">
  <div id="menuContactContent">
    <table id="menuContactTable" class="pulldown" width="170">
      <dhv:permission name="contacts-external_contacts-imports-view">
      <tr>
        <td>
          <img src="images/icons/stock_zoom-page-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </td>
        <td width="100%">
          <a href="javascript:details()">View Details</a>
        </td>
      </tr>
      </dhv:permission>
      <dhv:permission name="contacts-external_contacts-imports-edit">
      <tr id="menuDelete">
        <td>
          <img src="images/icons/stock_delete-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </td>
        <td width="100%">
          <a href="javascript:deleteContact()">Delete</a>
        </td>
      </tr>
      </dhv:permission>
    </table>
  </div>
</div>
