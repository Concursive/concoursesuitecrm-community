<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<script language="javascript">
  var thisTaskId = -1;
  var thisTypeId = -1;
  var thisContactId = -1;
  var menu_init_task = false;
  //Set the action parameters for clicked item
  function displayTaskMenu(loc, id, typeId, taskId, contactId) {
    thisTaskId = taskId;
    thisTypeId = typeId;
    thisContactId = contactId;
    updateTaskMenu();
    if (!menu_init_task) {
      menu_init_task = true;
      new ypSlideOutMenu("menuTask", "down", 0, 0, 170, getHeight("menuTaskTable"));
    }
    return ypSlideOutMenu.displayDropMenu(id, loc);
  }
  
  //Update menu for this Contact based on permissions
  function updateTaskMenu(){
    if(thisContactId > -1){
      showSpan('menuContactTask');
    }else{
      hideSpan('menuContactTask');
    }
  }
  //Menu link functions
  function detailsTask() {
    popURL('CalendarTasks.do?command=Modify&id=' + thisTaskId + '&popup=true&return=calendar', 'DETAILS','600','400','yes','no');
  }
  
  function deleteTask() {
    popURL('CalendarTasks.do?command=ConfirmDelete&id=' + thisTaskId + '&popup=true&return=calendar','Delete_task','320','200','yes','no');
  }
  
  //Menu link functions
  function contactDetailsTask() {
  popURL('ExternalContacts.do?command=ContactDetails&id=' + thisContactId + '&popup=true&popupType=inline','Details','650','500','yes','yes');
  }
  
</script>
<div id="menuTaskContainer" class="menu">
  <div id="menuTaskContent">
    <table id="menuTaskTable" class="pulldown" width="170" cellspacing="0">
      <dhv:permission name="myhomepage-tasks-edit">
      <tr onmouseover="cmOver(this)" onmouseout="cmOut(this)"
          onclick="detailsTask()">
        <th>
          <img src="images/icons/stock_edit-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td width="100%">
          Modify Task
        </td>
      </tr>
      </dhv:permission>
      <dhv:permission name="contacts-external_contacts-view">
      <tr id="menuContactTask" onmouseover="cmOver(this)" onmouseout="cmOut(this)"
          onclick="contactDetailsTask()">
        <th>
          <img src="images/icons/stock_zoom-page-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td width="100%">
          Go To Contact
        </td>
      </tr>
      </dhv:permission>
    </table>
  </div>
</div>
