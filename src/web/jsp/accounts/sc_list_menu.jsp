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
  var thisOrgId = -1;
  var thisContractId = -1;
  var menu_init = false;
  //Set the action parameters for clicked item
  function displayMenu(loc, id, orgId, contractId, trashed) {
    thisOrgId = orgId;
    thisContractId = contractId;
    updateMenu(trashed);
    if (!menu_init) {
      menu_init = true;
      new ypSlideOutMenu("menuServiceContract", "down", 0, 0, 170, getHeight("menuServiceContractTable"));
    }

    return ypSlideOutMenu.displayDropMenu(id, loc);
  }

  function updateMenu(trashed){
    if (trashed == 'true'){
      hideSpan("menuModify");
      hideSpan("menuDelete");
    } else {
      showSpan("menuModify");
      showSpan("menuDelete");
    }
  }
  //Menu link functions
  function details() {
    window.location.href = 'AccountsServiceContracts.do?command=View&orgId=' + thisOrgId + '&id=' + thisContractId+'<%= addLinkParams(request, "popup|popupType|actionId") %>';
  }

  function modify() {
    window.location.href = 'AccountsServiceContracts.do?command=Modify&orgId=' + thisOrgId + '&id=' + thisContractId + '&return=list<%= addLinkParams(request, "popup|popupType|actionId") %>';
  }

  function deleteContract() {
    popURLReturn('AccountsServiceContracts.do?command=ConfirmDelete&orgId=' + thisOrgId + '&id=' + thisContractId + '&popup=true<%= isPopup(request)?"&popupType=true":"" %>','AccountsServiceContracts.do?command=List&orgId=' + thisOrgId,'Delete_servicecontract','330','200','yes','no');
  }

</script>
<div id="menuServiceContractContainer" class="menu">
  <div id="menuServiceContractContent">
    <table id="menuServiceContractTable" class="pulldown" width="170" cellspacing="0">
      <dhv:permission name="accounts-service-contracts-view">
      <tr id="menuView" onmouseover="cmOver(this)" onmouseout="cmOut(this)" onclick="details()">
        <th>
          <img src="images/icons/stock_zoom-page-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td width="100%">
          <dhv:label name="accounts.accounts_calls_list_menu.ViewDetails">View Details</dhv:label>
        </td>
      </tr>
      </dhv:permission>
      <dhv:permission name="accounts-service-contracts-edit">
      <tr id="menuModify" onmouseover="cmOver(this)" onmouseout="cmOut(this)" onclick="modify()">
        <th>
          <img src="images/icons/stock_edit-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td width="100%">
          <dhv:label name="global.button.modify">Modify</dhv:label>
        </td>
      </tr>
      </dhv:permission>
      <dhv:permission name="accounts-service-contracts-delete">
      <tr id="menuDelete" onmouseover="cmOver(this)" onmouseout="cmOut(this)" onclick="deleteContract()">
        <th>
          <img src="images/icons/stock_delete-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td>
          <dhv:label name="global.button.delete">Delete</dhv:label>
        </td>
      </tr>
      </dhv:permission>
    </table>
  </div>
</div>
