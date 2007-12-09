<%-- 
  - Copyright(c) 2004 Concursive Corporation (http://www.concursive.com/) All
  - rights reserved. This material cannot be distributed without written
  - permission from Concursive Corporation. Permission to use, copy, and modify
  - this material for internal use is hereby granted, provided that the above
  - copyright notice and this permission notice appear in all copies. CONCURSIVE
  - CORPORATION MAKES NO REPRESENTATIONS AND EXTENDS NO WARRANTIES, EXPRESS OR
  - IMPLIED, WITH RESPECT TO THE SOFTWARE, INCLUDING, BUT NOT LIMITED TO, THE
  - IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR ANY PARTICULAR
  - PURPOSE, AND THE WARRANTY AGAINST INFRINGEMENT OF PATENTS OR OTHER
  - INTELLECTUAL PROPERTY RIGHTS. THE SOFTWARE IS PROVIDED "AS IS", AND IN NO
  - EVENT SHALL CONCURSIVE CORPORATION OR ANY OF ITS AFFILIATES BE LIABLE FOR
  - ANY DAMAGES, INCLUDING ANY LOST PROFITS OR OTHER INCIDENTAL OR CONSEQUENTIAL
  - DAMAGES RELATING TO THE SOFTWARE.
  - 
  - Version: $Id$
  - Description:
  --%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popURL.js"></SCRIPT>
<script language="javascript">
  var thisHistoryId = -1;
  var thisContactId = -1;
  var thisOrgId = -1;
  var thisObjectId = -1;
  var thisItemId = -1;
  var urlDetails = "";
  var urlModify = "";
  var isPopup = false;
  var thisCanView = false;
  var thisCanModify = false;
  var thisCanDelete = false;
  var menu_init = false;
  //Set the action parameters for clicked item
  function displayMenu(loc, id, historyId, orgId, contactId, objectId, itemId, canView, canModify, canDelete) {
    thisHistoryId = historyId;
    thisOrgId = orgId;
    thisContactId = contactId;
    thisObjectId = objectId;
    thisItemId = itemId;
    thisCanView = canView;
    thisCanModify = canModify;
    thisCanDelete = canDelete;
    if (!menu_init) {
      menu_init = true;
      new ypSlideOutMenu("menuAccountHistory", "down", 0, 0, 170, getHeight("menuAccountHistoryTable"));
    }
    if (thisObjectId == '<%= OrganizationHistory.NOTE %>') {
      hideSpan('menuViewAccountHistoryObject');
      if (thisCanModify) {
        showSpan('menuEditAccountHistoryObject');
      } else {
        hideSpan('menuEditAccountHistoryObject');
      }
      if (thisCanDelete) {
        showSpan('menuDeleteAccountHistoryObject');
      } else {
        hideSpan('menuDeleteAccountHistoryObject');
      }
      if (thisOrgId != -1) {
        urlModify = 'AccountsHistory.do?command=ModifyNote&id='+ thisHistoryId +'&orgId='+ thisOrgId +'&popup=true';
      } else {
        urlModify = 'ExternalContactsHistory.do?command=ModifyNote&id='+ thisHistoryId +'&contactId='+ thisContactId +'&popup=true';
      }
      if (thisOrgId != -1) {
        urlDelete = 'AccountsHistory.do?command=DeleteNote&id='+ thisHistoryId +'<%= isPopup(request)?"&popup=true":"" %>&orgId='+ thisOrgId;
      } else {
        urlDelete = 'AccountContactsHistory.do?command=DeleteNote&id='+ thisHistoryId +'<%= isPopup(request)?"&popup=true":"" %>&contactId='+ thisContactId;
      }
      isPopup = true;
      } else if(thisObjectId == '<%= OrganizationHistory.COMPLETE_ACTIVITY %>') {
      hideSpan('menuDeleteAccountHistoryObject');
      if (thisCanView) {
        showSpan('menuViewAccountHistoryObject');
      } else {
        hideSpan('menuViewAccountHistoryObject');
      }
      if (thisCanModify) {
        showSpan('menuEditAccountHistoryObject');
      } else {
        hideSpan('menuEditAccountHistoryObject');
      }
      if(thisContactId!=-1){
      urlDetails = 'AccountContactsCalls.do?command=Details&contactId='+thisContactId+'&id='+ thisItemId+'&trailSource=accounts';
      urlModify = 'AccountContactsCalls.do?command=Modify&contactId='+thisContactId+'&id='+ thisItemId;
      }
      else{
      urlDetails = 'AccountsCalls.do?command=Details&orgId='+thisOrgId+'&id='+ thisItemId+'&trailSource=accounts';
      urlModify = 'AccountsCalls.do?command=Modify&orgId='+thisOrgId+'&id='+ thisItemId;
      
      }
      isPopup = false;
    } else if(thisObjectId == '<%= OrganizationHistory.CANCELED_ACTIVITY %>') {
      hideSpan('menuDeleteAccountHistoryObject');
      if (thisCanView) {
        showSpan('menuViewAccountHistoryObject');
      } else {
        hideSpan('menuViewAccountHistoryObject');
      }
      hideSpan('menuEditAccountHistoryObject');
      if(thisContactId!=-1){
      urlDetails = 'AccountContactsCalls.do?command=Details&contactId='+thisContactId+'&id='+ thisItemId+'&trailSource=accounts';
      }
      else
      {
      urlDetails = 'AccountsCalls.do?command=Details&orgId='+thisOrgId+'&id='+ thisItemId+'&trailSource=accounts';
      }
      isPopup = false;
    } else if(thisObjectId == '<%= OrganizationHistory.CFSNOTE %>') {
      hideSpan('menuDeleteAccountHistoryObject');
      if (thisCanView) {
        showSpan('menuViewAccountHistoryObject');
      } else {
        hideSpan('menuViewAccountHistoryObject');
      }
      hideSpan('menuEditAccountHistoryObject');
      urlDetails = 'MyCFSInbox.do?command=CFSNoteDetails&listView=sent&id='+thisItemId;
      isPopup = false;
    } else if(thisObjectId == '<%= OrganizationHistory.CAMPAIGN %>') {
      hideSpan('menuDeleteAccountHistoryObject');
      if (thisCanView) {
        showSpan('menuViewAccountHistoryObject');
      } else {
        hideSpan('menuViewAccountHistoryObject');
      }
      hideSpan('menuEditAccountHistoryObject');
      urlDetails = 'Contacts.do?command=MessageDetails&id='+ thisItemId +'&contactId='+ thisContactId;
      isPopup = false;
    } else if(thisObjectId == '<%= OrganizationHistory.TICKET %>') {
      hideSpan('menuDeleteAccountHistoryObject');
      if (thisCanView) {
        showSpan('menuViewAccountHistoryObject');
      } else {
        hideSpan('menuViewAccountHistoryObject');
      }
      if (thisCanModify) {
        showSpan('menuEditAccountHistoryObject');
      } else {
        hideSpan('menuEditAccountHistoryObject');
      }
      urlDetails = 'AccountTickets.do?command=TicketDetails&id='+ thisItemId;
      urlModify = 'AccountTickets.do?command=ModifyTicket&id='+ thisItemId;
      isPopup = false;
    } else if(thisObjectId == '<%= OrganizationHistory.TASK %>') {
      hideSpan('menuDeleteAccountHistoryObject');
      if (thisCanView) {
        showSpan('menuViewAccountHistoryObject');
      } else {
        hideSpan('menuViewAccountHistoryObject');
      }
      if (thisCanModify) {
        showSpan('menuEditAccountHistoryObject');
      } else {
        hideSpan('menuEditAccountHistoryObject');
      }
      urlDetails = 'MyTasks.do?command=Details&id='+ thisItemId;
      urlModify = 'MyTasks.do?command=Modify&id='+ thisItemId;
      isPopup = false;
    } else if(thisObjectId == '<%= OrganizationHistory.QUOTE %>') {
      hideSpan('menuDeleteAccountHistoryObject');
      if (thisCanView) {
        showSpan('menuViewAccountHistoryObject');
      } else {
        hideSpan('menuViewAccountHistoryObject');
      }
      if (thisCanModify) {
        showSpan('menuEditAccountHistoryObject');
      } else {
        hideSpan('menuEditAccountHistoryObject');
      }
      urlDetails = 'AccountQuotes.do?command=Details&quoteId='+ thisItemId;
      urlModify = 'AccountQuotes.do?command=ModifyForm&quoteId='+ thisItemId;
      isPopup = false;
    } else if(thisObjectId == '<%= OrganizationHistory.OPPORTUNITY %>') {
      hideSpan('menuDeleteAccountHistoryObject');
      if (thisCanView) {
        showSpan('menuViewAccountHistoryObject');
      } else {
        hideSpan('menuViewAccountHistoryObject');
      }
      if (thisCanModify) {
        showSpan('menuEditAccountHistoryObject');
      } else {
        hideSpan('menuEditAccountHistoryObject');
      }
      if (thisOrgId != -1) {
        urlDetails = 'OpportunitiesComponents.do?command=DetailsComponent&id='+thisItemId+'&orgId='+thisOrgId;
        urlModify = 'OpportunitiesComponents.do?command=ModifyComponent&id='+thisItemId+'&orgId='+thisOrgId;
      } else {
        urlDetails = 'OpportunitiesComponents.do?command=DetailsComponent&id='+thisItemId+'&contactId='+thisContactId;
        urlModify = 'OpportunitiesComponents.do?command=ModifyComponent&id='+thisItemId+'&contactId='+thisContactId;
      }
      isPopup = false;
    } else if(thisObjectId == '<%= OrganizationHistory.ACCOUNT_DOCUMENT %>') {
      hideSpan('menuDeleteAccountHistoryObject');
      if (thisCanView) {
        showSpan('menuViewAccountHistoryObject');
      } else {
        hideSpan('menuViewAccountHistoryObject');
      }
      hideSpan('menuEditAccountHistoryObject');
      urlDetails = 'AccountsDocuments.do?command=Details&orgId='+thisOrgId+'&fid='+thisItemId;
      urlModify = '';
      isPopup = false;
    } else if(thisObjectId == '<%= OrganizationHistory.SERVICE_CONTRACT %>') {
      hideSpan('menuDeleteAccountHistoryObject');
      if (thisCanView) {
        showSpan('menuViewAccountHistoryObject');
      } else {
        hideSpan('menuViewAccountHistoryObject');
      }
      if (thisCanModify) {
        showSpan('menuEditAccountHistoryObject');
      } else {
        hideSpan('menuEditAccountHistoryObject');
      }
      //Problem with contacts as orgId is required.
      if (thisOrgId != -1) {
        urlDetails = 'AccountsServiceContracts.do?command=View&id='+thisItemId+'&orgId='+thisOrgId;
        urlModify = 'AccountsServiceContracts.do?command=Modify&id='+thisItemId+'&orgId='+thisOrgId;
      } else {
        urlDetails = 'AccountsServiceContracts.do?command=View&id='+thisItemId+'&contactId='+thisContactId;
        urlModify = 'AccountsServiceContracts.do?command=Modify&id='+thisItemId+'&contactId='+thisContactId;
      }
      isPopup = false;
    } else if(thisObjectId == '<%= OrganizationHistory.RELATIONSHIP %>') {
      hideSpan('menuDeleteAccountHistoryObject');
      if (thisCanView) {
        showSpan('menuViewAccountHistoryObject');
      } else {
        hideSpan('menuViewAccountHistoryObject');
      }
      hideSpan('menuEditAccountHistoryObject');
      urlDetails = 'AccountRelationships.do?command=View&orgId='+thisOrgId;
      urlModify = '';
      isPopup = false;
    } else if(thisObjectId == '<%= OrganizationHistory.PROJECT %>') {
      hideSpan('menuDeleteAccountHistoryObject');
      if (thisCanView) {
        showSpan('menuViewAccountHistoryObject');
      } else {
        hideSpan('menuViewAccountHistoryObject');
      }
      hideSpan('menuEditAccountHistoryObject');
      urlDetails = 'ProjectManagement.do?command=ProjectCenter&section=Details&pid='+thisItemId;
      urlModify = 'ProjectManagement.do?command=ModifyProject&pid='+thisItemId+'&return=ProjectCenter';
      isPopup = false;
    } else if(thisObjectId == '<%= OrganizationHistory.ASSET %>') {
      hideSpan('menuDeleteAccountHistoryObject');
      if (thisCanView) {
        showSpan('menuViewContactHistoryObject');
      } else {
        hideSpan('menuViewContactHistoryObject');
      }
      if (thisCanModify) {
        showSpan('menuEditContactHistoryObject');
      } else {
        hideSpan('menuEditContactHistoryObject');
      }
      //Problem with contacts as orgId is required.
      if (thisContactId != -1) {
        urlDetails = 'AccountsAssets.do?command=View&orgId=&id='+thisItemId+'&contactId='+thisContactId;
        urlModify = 'AccountsAssets.do?command=Modify&id='+thisItemId+'&contactId='+thisContactId;
      } else {
        urlDetails = 'AccountsAssets.do?command=View&orgId=&id='+thisItemId+'&orgId='+thisOrgId;
        urlModify = 'AccountsAssets.do?command=Modify&id='+thisItemId+'&orgId='+thisOrgId;
      }
      isPopup = false;
    } else {
      hideSpan('menuViewAccountHistoryObject');
      hideSpan('menuEditAccountHistoryObject');
      hideSpan('menuDeleteAccountHistoryObject');
      isPopup = false;
    }
    return ypSlideOutMenu.displayDropMenu(id, loc);
  }
  //Menu link functions
  function details() {
    if (isPopup) {
      popURL(urlDetails, 'ContactHistory', 600, 300, true, true);
    } else {
      window.location.href = urlDetails+'<%= isPopup(request)?"&popup=true":"" %>';
    }
  }

  function modify() {
    if (isPopup) {
      popURL(urlModify, 'ContactHistory', 600, 300, true, true);
    } else {
      window.location.href = urlModify+'<%= isPopup(request)?"&popup=true":"" %>';
    }
  }

  function deleteObject() {
    if (confirm(label('confirm.delete.item','Are you sure you want to remove this item?'))) {
      window.location.href = urlDelete;
    }
  }
</script>
<div id="menuAccountHistoryContainer" class="menu">
  <div id="menuAccountHistoryContent">
    <table id="menuAccountHistoryTable" class="pulldown" width="170" cellspacing="0">
      <tr id="menuViewAccountHistoryObject" onmouseover="cmOver(this)" onmouseout="cmOut(this)" onclick="details()">
        <th>
          <img src="images/icons/stock_zoom-page-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td width="100%">
          <dhv:label name="accounts.accounts_calls_list_menu.ViewDetails">View Details</dhv:label>
        </td>
      </tr>
      <tr id="menuEditAccountHistoryObject" onmouseover="cmOver(this)" onmouseout="cmOut(this)" onclick="modify()">
        <th>
          <img src="images/icons/stock_edit-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td width="100%">
          <dhv:label name="global.button.modify">Modify</dhv:label>
        </td>
      </tr>
      <tr id="menuDeleteAccountHistoryObject" onmouseover="cmOver(this)" onmouseout="cmOut(this)" onclick="deleteObject();">
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
