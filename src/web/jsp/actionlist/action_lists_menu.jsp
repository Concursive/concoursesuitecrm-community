<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<script language="javascript">
  var thisActionId = -1;
  var thisLinkId = -1;
  var menu_init = false;
  //Set the action parameters for clicked item
  function displayMenu(id, actionId, linkId) {
    thisLinkId = linkId;
    thisActionId = actionId;
    if (!menu_init) {
      menu_init = true;
      new ypSlideOutMenu("menuAction", "down", 0, 0, 170, getHeight("menuActionTable"));
    }
    return ypSlideOutMenu.displayMenu(id);
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
    <table id="menuActionTable" class="pulldown" width="170">
      <dhv:permission name="myhomepage-action-lists-view">
      <tr>
        <td>
          &nbsp;
        </td>
        <td width="100%">
          <a href="javascript:details()">View Details</a>
        </td>
      </tr>
      </dhv:permission>
      <dhv:permission name="myhomepage-action-lists-edit">
      <tr>
        <td>
          &nbsp;
        </td>
        <td width="100%">
          <a href="javascript:modify()">Modify</a>
        </td>
      </tr>
      </dhv:permission>
      <dhv:permission name="myhomepage-action-lists-edit">
      <tr>
        <td>
          &nbsp;
        </td>
        <td width="100%">
          <a href="javascript:addContacts()">Add Contacts</a>
        </td>
      </tr>
      </dhv:permission>
      <dhv:permission name="myhomepage-action-lists-delete">
      <tr>
        <td>
          &nbsp;
        </td>
        <td width="100%">
          <a href="javascript:deleteAction()">Delete</a>
        </td>
      </tr>
      </dhv:permission>
    </table>
  </div>
</div>
