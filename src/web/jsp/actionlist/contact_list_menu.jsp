<%-- 
  - Copyright(c) 2004 Dark Horse Ventures LLC (http://www.centriccrm.com/) All
  - rights reserved. This material cannot be distributed without written
  - permission from Dark Horse Ventures LLC. Permission to use, copy, and modify
  - this material for internal use is hereby granted, provided that the above
  - copyright notice and this permission notice appear in all copies. DARK HORSE
  - VENTURES LLC MAKES NO REPRESENTATIONS AND EXTENDS NO WARRANTIES, EXPRESS OR
  - IMPLIED, WITH RESPECT TO THE SOFTWARE, INCLUDING, BUT NOT LIMITED TO, THE
  - IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR ANY PARTICULAR
  - PURPOSE, AND THE WARRANTY AGAINST INFRINGEMENT OF PATENTS OR OTHER
  - INTELLECTUAL PROPERTY RIGHTS. THE SOFTWARE IS PROVIDED "AS IS", AND IN NO
  - EVENT SHALL DARK HORSE VENTURES LLC OR ANY OF ITS AFFILIATES BE LIABLE FOR
  - ANY DAMAGES, INCLUDING ANY LOST PROFITS OR OTHER INCIDENTAL OR CONSEQUENTIAL
  - DAMAGES RELATING TO THE SOFTWARE.
  - 
  - Version: $Id$
  - Description: 
  --%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<script language="javascript">
  var thisActionId = -1;
  var thisContactId = -1;
  var thisOrgId = -1;
  var thisItemId = -1;
  var menu_init = false;
  //Set the action parameters for clicked item
  function displayMenu(loc, id, aId, cId, oId, itemId) {
    thisActionId = aId;
    thisContactId = cId;
    thisOrgId = oId;
    thisItemId = itemId;
    updateMenu();
    if (!menu_init) {
      menu_init = true;
      new ypSlideOutMenu("menuContact", "down", 0, 0, 170, getHeight("menuContactTable"));
    }
    return ypSlideOutMenu.displayDropMenu(id, loc);
  }
  
  //Display menu items based on the action contact's properties
  function updateMenu(){
    if(thisOrgId == -1){
      hideSpan('menuTicket');
    }else{
      showSpan('menuTicket');
    }
  }
  
  //Menu link functions
  function showContact() {
    popURL('ExternalContacts.do?command=ContactDetails&actionId=' + thisItemId + '&id=' + thisContactId + '&popup=true&popupType=inline','Details','650','500','yes','yes');
  }
  function addCall() {
    if(thisOrgId == -1){
      popURL('ExternalContactsCalls.do?command=Add&popup=true&actionSource=MyActionContacts&contactId=' + thisContactId + '&actionId=' + thisItemId,'Activity','600','300','yes','yes');
    }else{
      popURL('AccountContactsCalls.do?command=Add&popup=true&actionSource=MyActionContacts&contactId=' + thisContactId + '&actionId=' + thisItemId,'Activity','600','300','yes','yes');
    }
  }
  function addTicket() {
    popURL('TroubleTickets.do?command=Add&actionSource=MyActionContacts&orgId=' + thisOrgId + '&contactId=' + thisContactId + '&contactSet=true&actionId=' + thisItemId,'Ticket','600','500','yes','yes');
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
    <table id="menuContactTable" class="pulldown" width="170" cellspacing="0">
      <dhv:permission name="myhomepage-action-lists-edit">
      <tr onmouseover="cmOver(this)" onmouseout="cmOut(this)" onclick="showContact()">
        <th>
          <img src="images/icons/stock_bcard-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td width="100%">
          <dhv:label name="actionList.showContactRecord">Show Contact Record</dhv:label>
        </td>
      </tr>
      </dhv:permission>
      <dhv:permission name="contacts-external_contacts-calls-add">
      <tr onmouseover="cmOver(this)" onmouseout="cmOut(this)" onclick="addCall()">
        <th>
          <!-- <img src="images/doAction.gif" border="0" align="absmiddle" height="16" width="16"/> -->
          &nbsp;
        </th>
        <td width="100%">
          <dhv:label name="accounts.accounts_contacts_calls_add.AddActivity">Add Activity</dhv:label>
        </td>
      </tr>
      </dhv:permission>
      <dhv:permission name="contacts-external_contacts-opportunities-add">
      <tr onmouseover="cmOver(this)" onmouseout="cmOut(this)" onclick="addOpportunity()">
        <th>
          <img src="images/icons/stock_form-currency-field-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td>
          <dhv:label name="accounts.accounts_contacts_oppcomponent_add.AddOpportunity">Add Opportunity</dhv:label>
        </td>
      </tr>
      </dhv:permission>
      <dhv:permission name="tickets-tickets-add">
      <tr id="menuTicket" onmouseover="cmOver(this)" onmouseout="cmOut(this)" onclick="addTicket()">
        <th>
          <img src="images/icons/stock_macro-organizer-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td>
          <dhv:label name="tickets.add">Add Ticket</dhv:label>
        </td>
      </tr>
      </dhv:permission>
      <dhv:permission name="myhomepage-tasks-add">
      <tr onmouseover="cmOver(this)" onmouseout="cmOut(this)" onclick="addTask()">
        <th>
          &nbsp;
        </th>
        <td>
          <dhv:label name="actionList.addTask">Add Task</dhv:label>
        </td>
      </tr>
      </dhv:permission>
      <tr onmouseover="cmOver(this)" onmouseout="cmOut(this)" onclick="sendMessage()">
        <th>
          <img src="images/icons/stock_mail-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td>
          <dhv:label name="button.sendMessage">Send Message</dhv:label>
        </td>
      </tr>
    </table>
  </div>
</div>
