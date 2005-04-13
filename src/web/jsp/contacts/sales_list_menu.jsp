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
  - Description: Menu for leads
  --%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<script language="javascript">
  var thisContactId = -1;
  var menu_init = false;
  var thisReturnValue = '';
  var thisIsLead = '';
  //Set the action parameters for clicked item
  function displayMenu(loc, id, contactId, value, isLead) {
    thisContactId = contactId;
    thisReturnValue = value;
    thisIsLead = isLead;
    updateMenu();
    if (!menu_init) {
      menu_init = true;
      new ypSlideOutMenu("menuContact", "down", 0, 0, 170, getHeight("menuContactTable"));
    }
    return ypSlideOutMenu.displayDropMenu(id, loc);
  }
  //Menu link functions
  
  function updateMenu() {
    if (thisIsLead == 'true') {
      hideSpan('menuContact');
      showSpan('menuLeadDetails');
      showSpan('menuLeadTrash');
      showSpan('menuLeadDelete');
    } else {
      hideSpan('menuLeadDetails');
      hideSpan('menuLeadTrash');
      hideSpan('menuLeadDelete');
      showSpan('menuContact');
    }
  }

  function details() {
    window.location.href = 'Sales.do?command=Details&contactId=' + thisContactId +'&from='+ thisReturnValue + '&searchForm=<%= (searchForm!=null?searchForm:"")  %><%= addLinkParams(request, "popup|popupType|actionId") %>';
  }
  
  function trash() {
    var url = 'Sales.do?command=CheckAssignStatus&contactId='+thisContactId+'&next=trash&from='+ thisReturnValue + '&searchForm=<%= (searchForm!=null?searchForm:"") %>';
    window.frames['server_commands'].location.href=url;
  }

  function continueTrashLead() {
    var leadStatus = '<%= Contact.LEAD_TRASHED %>';
    window.location.href="Sales.do?command=Update&contactId="+thisContactId+"&leadStatus="+leadStatus+"&from="+thisReturnValue + '&backFromDetails=true&searchForm=<%= (searchForm!=null?searchForm:"") %>';
  }
  
  function deleteLead() {
    var url = 'Sales.do?command=CheckAssignStatus&contactId='+thisContactId+'&next=delete&from='+ thisReturnValue;
    window.frames['server_commands'].location.href=url;    
  }

  function continueDeleteLead() {
    if (thisReturnValue != 'list') {
      popURL('Sales.do?command=ConfirmDelete&contactId='+thisContactId+ '&popup=true&return='+thisReturnValue+'<%= addLinkParams(request, "popupType|actionId") %>','Delete_Lead','330','200','yes','no');
    } else {
      popURL('Sales.do?command=ConfirmDelete&contactId='+thisContactId+ '&popup=true&return='+thisReturnValue+'&searchForm=<%= (searchForm!=null?searchForm:"") %><%= addLinkParams(request, "popupType|actionId") %>', 'Delete_Lead','330','200','yes','no');
    }
  }
  
  function contactDetails() {
    popURL('ExternalContacts.do?command=ContactDetails&id=' + thisContactId + '&popup=true&viewOnly=true','Details','650','500','yes','yes');
  }

</script>
<div id="menuContactContainer" class="menu">
  <div id="menuContactContent">
    <table id="menuContactTable" class="pulldown" width="170" cellspacing="0">
      <dhv:permission name="sales-leads-view">
      <tr id="menuLeadDetails" onmouseover="cmOver(this)" onmouseout="cmOut(this)" onclick="details()">
        <th>
          <img src="images/icons/stock_zoom-page-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td width="100%">
          <dhv:label name="accounts.accounts_calls_list_menu.ViewDetails">View Details</dhv:label>
        </td>
      </tr>
      </dhv:permission>
      <dhv:permission name="sales-leads-edit">
      <tr id="menuLeadTrash" onmouseover="cmOver(this)" onmouseout="cmOut(this)" onclick="trash()">
        <th>
          <img src="images/icons/stock_edit-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td width="100%">
          <dhv:label name="button.trashLead">Trash Lead</dhv:label>
        </td>
      </tr>
      </dhv:permission>
      <dhv:permission name="sales-leads-delete">
      <tr id="menuLeadDelete" onmouseover="cmOver(this)" onmouseout="cmOut(this)" onclick="deleteLead()">
        <th>
          <img src="images/icons/stock_delete-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td width="100%">
          <dhv:label name="button.delete">Delete</dhv:label>
        </td>
      </tr>
      </dhv:permission>
      <dhv:permission name="contacts-external_contacts-view">
      <tr id="menuContact" onmouseover="cmOver(this)" onmouseout="cmOut(this)" onclick="contactDetails()">
        <th>
          <img src="images/icons/stock_zoom-page-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td width="100%">
          <dhv:label name="calendar.viewContactDetails">View Contact Details</dhv:label>
        </td>
      </tr>
      </dhv:permission>
    </table>
  </div>
</div>
