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
  - Version: $Id: companydirectory_history_menu.jsp 15115 2006-05-31 16:47:51Z matt $
  - Description: 
  --%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popURL.js"></SCRIPT>
<script language="javascript">
  var thisHistoryId = -1;
  var thisContactId = -1;
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
  function displayMenu(loc, id, historyId, contactId, objectId, itemId, canView, canModify, canDelete ) {
    thisHistoryId = historyId;
    thisContactId = contactId;
    thisObjectId = objectId;
    thisItemId = itemId;
    thisCanView = canView;
    thisCanModify = canModify;
    thisCanDelete = canDelete;
    if (!menu_init) {
      menu_init = true;
      new ypSlideOutMenu("menuContactHistory", "down", 0, 0, 170, getHeight("menuContactHistoryTable"));
    }
    if (thisObjectId == '<%= OrganizationHistory.NOTE %>') {
      hideSpan('menuViewContactHistoryObject');
      if (thisCanDelete) {
        showSpan('menuDeleteContactHistoryObject');
      } else {
        hideSpan('menuDeleteContactHistoryObject');
      }
      if (thisCanModify) {
        showSpan('menuEditContactHistoryObject');
      } else {
        hideSpan('menuEditContactHistoryObject');
      }
      urlDelete = 'SalesHistory.do?command=DeleteNote&id='+ thisHistoryId +'&contactId='+ thisContactId+'<%= isPopup(request)?"&popup=true":"" %><%= addLinkParams(request, "popup|popupType|actionId|from|listForm") %>';
      urlModify = 'SalesHistory.do?command=ModifyNote&id='+ thisHistoryId +'&contactId='+ thisContactId +'&popup=true<%= addLinkParams(request, "popup|popupType|actionId|from|listForm") %>';
      isPopup = true;
      } else if(thisObjectId == '<%= OrganizationHistory.COMPLETE_ACTIVITY %>') {
      hideSpan('menuDeleteContactHistoryObject');
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
      urlDetails = 'SalesCalls.do?command=Details&contactId='+thisContactId+'&id='+ thisItemId+'<%= isPopup(request)?"&popup=true":"" %><%= addLinkParams(request, "popup|popupType|actionId|from|listForm") %>';
      urlModify = 'SalesCalls.do?command=Modify&contactId='+thisContactId+'&id='+ thisItemId+'<%= isPopup(request)?"&popup=true":"" %><%= addLinkParams(request, "popup|popupType|actionId|from|listForm") %>';
      isPopup = false;
    } else if(thisObjectId == '<%= OrganizationHistory.CANCELED_ACTIVITY %>') {
      hideSpan('menuDeleteContactHistoryObject');
      if (thisCanView) {
        showSpan('menuViewContactHistoryObject');
      } else {
        hideSpan('menuViewContactHistoryObject');
      }
      hideSpan('menuEditContactHistoryObject');
      urlDetails = 'SalesCalls.do?command=Details&contactId='+thisContactId+'&id='+ thisItemId+'<%= isPopup(request)?"&popup=true":"" %><%= addLinkParams(request, "popup|popupType|actionId|from|listForm") %>';
      isPopup = false;
    } else if(thisObjectId == '<%= OrganizationHistory.CFSNOTE %>') {
      hideSpan('menuDeleteContactHistoryObject');
      if (thisCanView) {
        showSpan('menuViewContactHistoryObject');
      } else {
        hideSpan('menuViewContactHistoryObject');
      }
      hideSpan('menuEditContactHistoryObject');
      urlDetails = 'MyCFSInbox.do?command=CFSNoteDetails&listView=sent&id='+thisItemId;
      isPopup = false;
    } else if(thisObjectId == '<%= OrganizationHistory.CAMPAIGN %>') {
      hideSpan('menuDeleteContactHistoryObject');
      if (thisCanView) {
        showSpan('menuViewContactHistoryObject');
      } else {
        hideSpan('menuViewContactHistoryObject');
      }
      hideSpan('menuEditContactHistoryObject');
      urlDetails = 'SalesMessages.do?command=MessageDetails&id='+ thisItemId +'&contactId='+ thisContactId+'<%= isPopup(request)?"&popup=true":"" %><%= addLinkParams(request, "popup|popupType|actionId|from|listForm") %>';
      isPopup = false;
    } else if(thisObjectId == '<%= OrganizationHistory.TICKET %>') {
      hideSpan('menuDeleteContactHistoryObject');
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
      urlDetails = 'TroubleTickets.do?command=Details&id='+ thisItemId +'<%= isPopup(request)?"&popup=true":"" %><%= addLinkParams(request, "popup|popupType|actionId|from|listForm") %>';
      urlModify = 'TroubleTickets.do?command=Modify&id='+ thisItemId +'<%= isPopup(request)?"&popup=true":"" %>';
      isPopup = false;
    } else if(thisObjectId == '<%= OrganizationHistory.TASK %>') {
      hideSpan('menuDeleteContactHistoryObject');
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
       urlDetails = 'MyTasks.do?command=Details&id='+ thisItemId +'<%= isPopup(request)?"&popup=true":"" %><%= addLinkParams(request, "popup|popupType|actionId|from|listForm") %>';
       urlModify = 'MyTasks.do?command=Modify&id='+ thisItemId +'<%= isPopup(request)?"&popup=true":"" %><%= addLinkParams(request, "popup|popupType|actionId|from|listForm") %>';
      isPopup = false;
    } else if(thisObjectId == '<%= OrganizationHistory.QUOTE %>') {
      hideSpan('menuDeleteContactHistoryObject');
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
      urlDetails = 'Quotes.do?command=Details&quoteId='+ thisItemId;
      urlModify = 'Quotes.do?command=ModifyForm&version=&quoteId='+ thisItemId;
      isPopup = false;
    } else if(thisObjectId == '<%= OrganizationHistory.OPPORTUNITY %>') {
      hideSpan('menuDeleteContactHistoryObject');
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
      urlDetails = 'SalesOpportunitiesComponents.do?command=DetailsComponent&id='+thisItemId+'&contactId='+thisContactId+'<%= isPopup(request)?"&popup=true":"" %><%= addLinkParams(request, "popup|popupType|actionId|from|listForm") %>';
      urlModify = 'SalesOpportunitiesComponents.do?command=ModifyComponent&id='+thisItemId+'&contactId='+thisContactId+'<%= isPopup(request)?"&popup=true":"" %><%= addLinkParams(request, "popup|popupType|actionId|from|listForm") %>';
      isPopup = false;
    } else if(thisObjectId == '<%= OrganizationHistory.SERVICE_CONTRACT %>') {
      hideSpan('menuDeleteContactHistoryObject');
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
      urlDetails = 'AccountsServiceContracts.do?command=View&id='+thisItemId+'&contactId='+thisContactId;
      urlModify = 'AccountsServiceContracts.do?command=Modify&id='+thisItemId+'&contactId='+thisContactId;
      isPopup = false;
    } else if(thisObjectId == '<%= OrganizationHistory.ASSET %>') {
      hideSpan('menuDeleteContactHistoryObject');
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
      urlDetails = 'AccountsAssets.do?command=View&orgId=&id='+thisItemId;
      urlModify = 'AccountsAssets.do?command=Modify&auto-populate=true&id='+thisItemId;
      isPopup = false;
    } else {
      hideSpan('menuDeleteContactHistoryObject');
      hideSpan('menuViewContactHistoryObject');
      hideSpan('menuEditContactHistoryObject');
      isPopup = false;
    }
    return ypSlideOutMenu.displayDropMenu(id, loc);
  }
  //Menu link functions
  function details() {
    if (isPopup) {
      popURL(urlDetails, 'ContactHistory', 600, 300, true, true);
    } else {
      window.location.href = urlDetails;
    }
  }
  
  function modify() {
    if (isPopup) {
      popURL(urlModify, 'ContactHistory', 600, 300, true, true);
    } else {
      window.location.href = urlModify;
    }
  }

  function deleteObject() {
    if (confirm(label('confirm.delete.item','Are you sure you want to remove this item?'))) {
      window.location.href = urlDelete;
    }
  }
</script>
<div id="menuContactHistoryContainer" class="menu">
  <div id="menuContactHistoryContent">
    <table id="menuContactHistoryTable" class="pulldown" width="170" cellspacing="0">
      <tr id="menuViewContactHistoryObject" onmouseover="cmOver(this)" onmouseout="cmOut(this)" onclick="details();">
        <th>
          <img src="images/icons/stock_zoom-page-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td width="100%">
          <dhv:label name="accounts.accounts_calls_list_menu.ViewDetails">View Details</dhv:label>
        </td>
      </tr>
      <tr id="menuEditContactHistoryObject" onmouseover="cmOver(this)" onmouseout="cmOut(this)" onclick="modify();">
        <th>
          <img src="images/icons/stock_edit-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td width="100%">
          <dhv:label name="global.button.modify">Modify</dhv:label>
        </td>
      </tr>
      <tr id="menuDeleteContactHistoryObject" onmouseover="cmOver(this)" onmouseout="cmOut(this)" onclick="deleteObject();">
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
