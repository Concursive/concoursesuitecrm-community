<%-- 
  - Copyright(c) 2007 Dark Horse Ventures LLC (http://www.centriccrm.com/) All
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
  - Version: $Id: companydirectory_listopps_menu.jsp 15115 2006-05-31 16:47:51Z matt $
  - Description:
  --%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<script language="javascript">
  var thisContactId = -1;
  var thisHeaderId = -1;
  var menu_init = false;
  //Set the action parameters for clicked item
  function displayMenu(loc, id, contactId, headerId, editPermission, deletePermission, trashed, hasPermission) {
    thisContactId = contactId;
    thisHeaderId = headerId;
     if (hasPermission == 'true') {
       updateMenu(editPermission, deletePermission, trashed);
     } else {
      updateMenu(0,0,'true');
     }
    if (!menu_init) {
      menu_init = true;
      new ypSlideOutMenu("menuOpp", "down", 0, 0, 170, getHeight("menuOppTable"));
    }
    return ypSlideOutMenu.displayDropMenu(id, loc);
  }

  //Update menu for this Contact based on permissions
  function updateMenu(hasEditPermission, hasDeletePermission, trashed){
    if (trashed == 'true'){
      hideSpan('menuEdit');
      hideSpan('menuDelete');
    } else {
      if(hasEditPermission == 0){
        hideSpan('menuEdit');
      } else {
        showSpan('menuEdit');
      }
      if(hasDeletePermission == 0){
        hideSpan('menuDelete');
      } else {
        showSpan('menuDelete');
      }
    }
  }

  //Menu link functions
  function details() {
    window.location.href = 'SalesOpportunities.do?command=DetailsOpp&headerId=' + thisHeaderId + '&contactId=' + thisContactId + '<%= isPopup(request)?"&sourcePopup=true":"" %><%= addLinkParams(request, "popup|popupType|actionId|from|listForm") %>';
  }
  
  function modify() {
    window.location.href = 'SalesOpportunities.do?command=ModifyOpp&headerId=' + thisHeaderId + '&contactId=' + thisContactId + '&return=list<%= addLinkParams(request, "popup|popupType|actionId|from|listForm") %>';
  }
  
  function deleteOpp() {
    popURLReturn('SalesOpportunities.do?command=ConfirmDelete&contactId=' + thisContactId + '&headerId=' + thisHeaderId + '&popup=true<%= isPopup(request)?"&sourcePopup=true":"" %><%= addLinkParams(request, "popupType|actionId|from|listForm") %>','ExternalContactsOpps.do?command=ViewOpps&contactId=' + thisContactId, 'Delete_opp','320','200','yes','no');
  }
  
</script>
<div id="menuOppContainer" class="menu">
  <div id="menuOppContent">
    <table id="menuOppTable" class="pulldown" width="170" cellspacing="0">
      <dhv:permission name="sales-leads-opportunities-view">
      <tr onmouseover="cmOver(this)" onmouseout="cmOut(this)" onclick="details()">
        <th>
          <img src="images/icons/stock_zoom-page-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td width="100%">
          <dhv:label name="accounts.accounts_calls_list_menu.ViewDetails">View Details</dhv:label>
        </td>
      </tr>
      </dhv:permission>
      <tr id="menuEdit" onmouseover="cmOver(this)" onmouseout="cmOut(this)" onclick="modify()">
        <th>
          <img src="images/icons/stock_edit-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td width="100%">
          <dhv:label name="accounts.Rename">Rename</dhv:label>
        </td>
      </tr>
      <tr id="menuDelete" onmouseover="cmOver(this)" onmouseout="cmOut(this)" onclick="deleteOpp()">
        <th>
          <img src="images/icons/stock_delete-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td width="100%">
          <dhv:label name="global.button.delete">Delete</dhv:label>
        </td>
      </tr>
    </table>
  </div>
</div>
