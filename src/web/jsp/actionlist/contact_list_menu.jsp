<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<script language="javascript">
  var thisActionId = -1;
  var thisContactId = -1;
  var thisOrgId = -1;
  var thisItemId = -1;
  var menu_init = false;
  //Set the action parameters for clicked item
  function displayMenu(id, aId, cId, oId, itemId) {
    thisActionId = aId;
    thisContactId = cId;
    thisOrgId = oId;
    thisItemId = itemId;
    if (!menu_init) {
      menu_init = true;
      new ypSlideOutMenu("menuContact", "down", 0, 0, 170, getHeight("menuContactTable"));
    }
    return ypSlideOutMenu.displayMenu(id);
  }
  //Menu link functions
  function showContact() {
    popURL('ExternalContacts.do?command=ContactDetails&actionId=' + thisItemId + '&id=' + thisContactId + '&popup=true&popupType=inline','Details','650','500','yes','yes');
  }
  function addCall() {
    if(thisOrgId == -1){
      popURL('ExternalContactsCalls.do?command=Add&popup=true&actionSource=MyActionContacts&contactId=' + thisContactId + '&actionId=' + thisItemId,'Call','600','300','yes','yes');
    }else{
      popURL('AccountContactsCalls.do?command=Add&popup=true&actionSource=MyActionContacts&contactId=' + thisContactId + '&actionId=' + thisItemId,'Call','600','300','yes','yes');
    }
  }
  function addTicket() {
  if(thisOrgId == -1){
    popURL('TroubleTickets.do?command=Add&actionSource=MyActionContacts&contactId=' + thisContactId + '&contactSet=true&actionId=' + thisItemId,'Ticket','600','500','yes','yes');
   }else{
    popURL('TroubleTickets.do?command=Add&actionSource=MyActionContacts&orgId=' + thisOrgId + '&contactId=' + thisContactId + '&contactSet=true&actionId=' + thisItemId,'Ticket','600','500','yes','yes');
   }
  }
  function addTask() {
    popURL('MyTasks.do?command=New&actionSource=MyActionContacts&popup=true&contactId=' + thisContactId + '&ownerSet=true&actionId=' + thisItemId,'Task','600','425','yes','yes');
  }
  function addOpportunity() {
    if(thisOrgId == -1){
      popURL('ExternalContactsOpps.do?command=Prepare&actionSource=MyActionContacts&contactId=' + thisContactId + '&actionId=' + thisItemId,'Opportunity','600','500','yes','yes');
    }
    else{
      popURL('AccountContactsOpps.do?command=Prepare&actionSource=MyActionContacts&contactId=' + thisContactId + '&actionId=' + thisItemId,'Opportunity','600','500','yes','yes');
    }
  }
  function sendMessage() {
    popURL('MyActionContacts.do?command=PrepareMessage&actionSource=MyActionContacts&contactId=' + thisContactId + '&actionId=' + thisItemId + '&actionListId=' + thisActionId + '&popup=true','Message','700','550','yes','yes');
  }
</script>
<div id="menuContactContainer" class="menu">
  <div id="menuContactContent">
    <table id="menuContactTable" class="pulldown" width="170">
      <dhv:permission name="myhomepage-action-lists-edit">
      <tr>
        <td>
          <img src="images/icons/stock_bcard-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </td>
        <td width="100%">
          <a href="javascript:showContact()">Show Contact Record</a>
        </td>
      </tr>
      </dhv:permission>
      
      <dhv:permission name="contacts-external_contacts-calls-add">
      <tr>
        <td>
          <!-- <img src="images/doAction.gif" border="0" align="absmiddle" height="16" width="16"/> -->
          &nbsp;
        </td>
        <td width="100%">
          <a href="javascript:addCall()">Add Call</a>
        </td>
      </tr>
      </dhv:permission>
      <dhv:permission name="contacts-external_contacts-opportunities-add">
      <tr>
        <td>
          &nbsp;
        </td>
        <td>
          <a href="javascript:addOpportunity()">Add Opportunity</a>
        </td>
      </tr>
      </dhv:permission>
      <dhv:permission name="tickets-tickets-add">
      <tr>
        <td>
          &nbsp;
        </td>
        <td>
          <a href="javascript:addTicket()">Add Ticket</a>
        </td>
      </tr>
      </dhv:permission>
      <dhv:permission name="myhomepage-tasks-add">
      <tr>
        <td>
          &nbsp;
        </td>
        <td>
          <a href="javascript:addTask()">Add Task</a>
        </td>
      </tr>
      </dhv:permission>
      <tr>
        <td>
          <img src="images/icons/stock_mail-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </td>
        <td>
          <a href="javascript:sendMessage()">Send Message</a>
        </td>
      </tr>
    </table>
  </div>
</div>
