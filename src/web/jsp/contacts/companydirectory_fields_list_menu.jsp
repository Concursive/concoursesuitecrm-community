<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<script language="javascript">
  var thisContactId = -1;
  var thisRecId = -1;
  var thisCatId = -1;
  var menu_init = false;
  //Set the action parameters for clicked item
  function displayMenu(id, contactId, catId, recId) {
    thisContactId = contactId;
    thisRecId = recId;
    thisCatId = catId;
    if (!menu_init) {
      menu_init = true;
      new ypSlideOutMenu("menuField", "down", 0, 0, 170, getHeight("menuFieldTable"));
    }
    return ypSlideOutMenu.displayMenu(id);
  }
  
  //Menu link functions
  function details() {
    window.location.href = 'ExternalContacts.do?command=Fields&contactId=' + thisContactId + '&catId=' + thisCatId + '&recId=' + thisRecId + '<%= addLinkParams(request, "popup|popupType|actionId") %>';
  }
  
  function modify() {
    window.location.href = 'ExternalContacts.do?command=ModifyFields&contactId=' + thisContactId + '&catId=' + thisCatId + '&recId=' + thisRecId + '&return=list<%= addLinkParams(request, "popup|popupType|actionId") %>';
  }
  
  function deleteField() {
    
  }
  
</script>
<div id="menuFieldContainer" class="menu">
  <div id="menuFieldContent">
    <table id="menuFieldTable" class="pulldown" width="170">
      <dhv:permission name="contacts-external_contacts-folders-view">
      <tr>
        <td>
          <img src="images/icons/stock_zoom-page-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </td>
        <td width="100%">
          <a href="javascript:details()">View Details</a>
        </td>
      </tr>
      </dhv:permission>
      <dhv:permission name="contacts-external_contacts-folders-edit">
      <tr>
        <td>
          <img src="images/icons/stock_edit-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </td>
        <td width="100%">
          <a href="javascript:modify()">Modify</a>
        </td>
      </tr>
      </dhv:permission>
      <dhv:permission name="contacts-external_contacts-folders-delete">
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
