<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<script language="javascript">
  var thisOrgId = -1;
  var thisContactId = -1;
  var menu_init = false;
  var isPrimaryContact = false;
  //Set the action parameters for clicked item
  function displayMenu(id, orgId, contactId, isPrimary) {
    thisOrgId = orgId;
    thisContactId = contactId;
    isPrimaryContact = isPrimary;
    if (!menu_init) {
      menu_init = true;
      new ypSlideOutMenu("menuContact", "down", 0, 0, 170, getHeight("menuContactTable"));
    }
    return ypSlideOutMenu.displayMenu(id);
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
</script>
<div id="menuContactContainer" class="menu">
  <div id="menuContactContent">
    <table id="menuContactTable" class="pulldown" width="170">
      <dhv:permission name="accounts-accounts-contacts-view">
      <tr>
        <td>
          <img src="images/icons/stock_zoom-page-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </td>
        <td width="100%">
          <a href="javascript:details()">View Details</a>
        </td>
      </tr>
      </dhv:permission>
      <dhv:permission name="accounts-accounts-contacts-edit">
      <tr>
        <td>
          <img src="images/icons/stock_edit-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </td>
        <td width="100%">
          <a href="javascript:modify()">Modify</a>
        </td>
      </tr>
      </dhv:permission>
      <dhv:permission name="accounts-accounts-contacts-add">
      <tr>
        <td>
          <img src="images/icons/stock_copy-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </td>
        <td width="100%">
          <a href="javascript:clone()">Clone</a>
        </td>
      </tr>
      </dhv:permission>
      <dhv:permission name="accounts-accounts-contacts-edit">
      <tr>
        <td>
          <img src="images/icons/stock_drag-mode-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </td>
        <td width="100%">
          <a href="javascript:move()">Move</a>
        </td>
      </tr>
      </dhv:permission>
      <dhv:permission name="accounts-accounts-contacts-delete">
      <tr>
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
