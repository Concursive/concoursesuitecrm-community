<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<script language="javascript">
  var thisContactId = -1;
  var thisRecId = -1;
  var thisCatId = -1;
  var menu_init = false;
  //Set the action parameters for clicked item
  function displayMenu(loc, id, contactId, catId, recId) {
    thisContactId = contactId;
    thisRecId = recId;
    thisCatId = catId;
    if (!menu_init) {
      menu_init = true;
      new ypSlideOutMenu("menuField", "down", 0, 0, 170, getHeight("menuFieldTable"));
    }
    return ypSlideOutMenu.displayDropMenu(id, loc);
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
    <table id="menuFieldTable" class="pulldown" width="170" cellspacing="0">
      <dhv:permission name="contacts-external_contacts-folders-view">
      <tr onmouseover="cmOver(this)" onmouseout="cmOut(this)" onclick="details()">
        <th>
          <img src="images/icons/stock_zoom-page-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td width="100%">
          View Details
        </td>
      </tr>
      </dhv:permission>
      <dhv:permission name="contacts-external_contacts-folders-edit">
      <tr onmouseover="cmOver(this)" onmouseout="cmOut(this)" onclick="modify()">
        <th>
          <img src="images/icons/stock_edit-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td width="100%">
          Modify
        </td>
      </tr>
      </dhv:permission>
      <dhv:permission name="contacts-external_contacts-folders-delete">
      <tr onmouseover="cmOver(this)" onmouseout="cmOut(this)" onclick="deleteContact()">
        <th>
          <img src="images/icons/stock_delete-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td width="100%">
          Delete
        </td>
      </tr>
      </dhv:permission>
    </table>
  </div>
</div>
