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
  var thisOrgId = '';
  var thisIsAssigned = 'false';
  var thisSiteId = -1;
  var thisStatus = '';
  //Set the action parameters for clicked item
  function displayMenu(loc, id, contactId, value, isLead, orgId, isAssigned, siteId, status) {
    thisContactId = contactId;
    thisReturnValue = value;
    thisIsLead = isLead;
    thisIsAssigned = isAssigned;
    thisOrgId = orgId;
    thisSiteId = siteId;
    thisStatus = status;
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
      hideSpan('menuAccount');
      hideSpan('menuContact');
      if (thisIsAssigned == 'true') {
        showSpan('menuLeadReassign');
        hideSpan('menuLeadAssign');
      } else {
        showSpan('menuLeadAssign');
        hideSpan('menuLeadReassign');
      }
      showSpan('menuLeadWorkAccount');
      showSpan('menuLeadDetails');
      showSpan('menuLeadDelete');
      if (thisStatus == '<%= Contact.LEAD_TRASHED %>') {
        hideSpan('menuLeadTrash');
      } else {
        showSpan('menuLeadTrash');
      }
    } else {
      hideSpan('menuLeadAssign');
      hideSpan('menuLeadReassign');
      hideSpan('menuLeadWorkAccount');
      hideSpan('menuLeadDetails');
      hideSpan('menuLeadTrash');
      hideSpan('menuLeadDelete');
      showSpan('menuContact');
      if (thisOrgId == '' || thisOrgId == '-1') {
        hideSpan('menuAccount');
      } else {
        showSpan('menuAccount');
      }
    }
  }

  function details() {
    window.location.href = 'Sales.do?command=Details&contactId=' + thisContactId +'&from='+ thisReturnValue + '&listForm=<%= (listForm!=null?listForm:"")  %><%= addLinkParams(request, "popup|popupType|actionId") %>';
  }

  function trash() {
    var url = 'Sales.do?command=CheckAssignStatus&contactId='+thisContactId+'&next=trash&from='+ thisReturnValue + '&listForm=<%= (listForm!=null?listForm:"") %>';
    window.frames['server_commands'].location.href=url;
  }

  function continueTrashLead() {
    var leadStatus = '<%= Contact.LEAD_TRASHED %>';
    window.location.href="Sales.do?command=Update&contactId="+thisContactId+"&leadStatus="+leadStatus+"&from="+thisReturnValue + '&listForm=<%= (listForm!=null?listForm:"") %>';
  }

  function deleteLead() {
    var url = 'Sales.do?command=CheckAssignStatus&contactId='+thisContactId+'&next=delete&from='+ thisReturnValue;
    popURL(url+'&popup=true','DeleteLead','330','200','yes','yes');
  }

  function continueDeleteLead() {
    if (thisReturnValue != 'list') {
      popURL('Sales.do?command=ConfirmDelete&contactId='+thisContactId+ '&popup=true&return='+thisReturnValue+'<%= addLinkParams(request, "popupType|actionId") %>','Delete_Lead','330','200','yes','no');
    } else {
      popURL('Sales.do?command=ConfirmDelete&contactId='+thisContactId+ '&popup=true&return='+thisReturnValue+'&listForm=<%= (listForm!=null?listForm:"") %><%= addLinkParams(request, "popupType|actionId") %>', 'Delete_Lead','330','200','yes','no');
    }
  }

  function contactDetails() {
    popURL('ExternalContacts.do?command=ContactDetails&id=' + thisContactId + '&popup=true&viewOnly=true','Details','650','500','yes','yes');
  }

  function orgDetails() {
    popURL('Accounts.do?command=Details&orgId=' + thisOrgId + '&popup=true&viewOnly=true','Details','650','500','yes','yes');
  }

  function workAsAccount() {
    popURL('Sales.do?command=AssignLead&contactId=' + thisContactId + '&from='+ thisReturnValue + '&listForm=<%= (listForm!=null?listForm:"")  %><%= addLinkParams(request, "popup|popupType|actionId") %>&popup=true','Details','650','200','yes','yes');
  }

  function continueReassign(assignTo) {
    window.location.href = 'Sales.do?command=Update&contactId='+thisContactId+'&next=&from='+ thisReturnValue + '&listForm=<%= (listForm!=null?listForm:"") %>&owner='+assignTo;
  }

  function reassign() {
    var URL = 'ContactsList.do?command=ContactList&listView=employees&listType=single<%= User.getUserRecord().getSiteId() > -1?"&mySiteOnly=true":"" %>&siteId='+thisSiteId+'&searchcodePermission=sales-leads-edit,myhomepage-action-plans-view&reset=true&source=leads&flushtemplist=true&usersOnly=true&leads=true&from='+ thisReturnValue + '&listForm=<%= (listForm!=null?listForm:"")  %><%= addLinkParams(request, "popup|popupType|actionId") %>';
    popURL(URL, 'Action_Plan', 700, 425, 'yes', 'yes');
  }
</script>
<div id="menuContactContainer" class="menu">
  <div id="menuContactContent">
    <table id="menuContactTable" class="pulldown" width="170" cellspacing="0">
      <dhv:permission name="sales-leads-view">
      <tr id="menuLeadDetails" onmouseover="cmOver(this)" onmouseout="cmOut(this)" onclick="details();">
        <th>
          <img src="images/icons/stock_zoom-page-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td width="100%">
          <dhv:label name="accounts.accounts_calls_list_menu.ViewDetails">View Details</dhv:label>
        </td>
      </tr>
      </dhv:permission>
       <dhv:permission name="sales-leads-edit">
      <tr id="menuLeadAssign" onmouseover="cmOver(this)" onmouseout="cmOut(this)" onclick="reassign();">
        <th>
          <img src="images/icons/stock_edit-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td width="100%">
          <dhv:label name="button.assignLead">Assign Lead ></dhv:label>
        </td>
      </tr>
      </dhv:permission>
      <dhv:permission name="sales-leads-edit">
      <tr id="menuLeadReassign" onmouseover="cmOver(this)" onmouseout="cmOut(this)" onclick="reassign();">
        <th>
          <img src="images/icons/stock_edit-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td width="100%">
          <dhv:label name="button.reassignLead">Reassign Lead</dhv:label>
        </td>
      </tr>
      </dhv:permission>
      <dhv:permission name="sales-leads-edit">
      <tr id="menuLeadWorkAccount" onmouseover="cmOver(this)" onmouseout="cmOut(this)" onclick="workAsAccount();">
        <th>
          <img src="images/icons/stock_link_account-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td width="100%">
          <dhv:label name="button.convertToAccount">Convert to Account</dhv:label>
        </td>
      </tr>
      </dhv:permission>
      <dhv:permission name="sales-leads-edit">
      <tr id="menuLeadTrash" onmouseover="cmOver(this)" onmouseout="cmOut(this)" onclick="trash();">
        <th>
          <img src="images/icons/stock_edit-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td width="100%">
          <dhv:label name="button.archiveLead">Archive Lead</dhv:label>
        </td>
      </tr>
      </dhv:permission>
      <dhv:permission name="sales-leads-delete">
      <tr id="menuLeadDelete" onmouseover="cmOver(this)" onmouseout="cmOut(this)" onclick="deleteLead();">
        <th>
          <img src="images/icons/stock_delete-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td width="100%">
          <dhv:label name="button.delete">Delete</dhv:label>
        </td>
      </tr>
      </dhv:permission>
      <dhv:permission name="contacts-external_contacts-view">
      <tr id="menuContact" onmouseover="cmOver(this)" onmouseout="cmOut(this)" onclick="contactDetails();">
        <th>
          <img src="images/icons/stock_zoom-page-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td width="100%">
          <dhv:label name="calendar.viewContactDetails">View Contact Details</dhv:label>
        </td>
      </tr>
      </dhv:permission>
      <dhv:permission name="accounts-accounts-view">
      <tr id="menuAccount" onmouseover="cmOver(this)" onmouseout="cmOut(this)" onclick="orgDetails();">
        <th>
          <img src="images/icons/stock_zoom-page-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td width="100%">
          <dhv:label name="actionPlan.viewAccount.text">View Account</dhv:label>
        </td>
      </tr>
      </dhv:permission>
    </table>
  </div>
</div>
