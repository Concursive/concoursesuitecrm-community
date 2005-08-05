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
  var menu_init = false;
  //Set the action parameters for clicked item
  function displayMenu(loc, id, historyId, orgId, contactId, objectId, itemId) {
    thisHistoryId = historyId;
    thisOrgId = orgId;
    thisContactId = contactId;
    thisObjectId = objectId;
    thisItemId = itemId;
    if (!menu_init) {
      menu_init = true;
      new ypSlideOutMenu("menuAccountHistory", "down", 0, 0, 170, getHeight("menuAccountHistoryTable"));
    }
    if (thisObjectId == '<%= OrganizationHistory.NOTE %>') {
      hideSpan('menuViewAccountHistoryObject');
      showSpan('menuEditAccountHistoryObject');
      if (thisOrgId != -1) {
        urlModify = 'AccountsHistory.do?command=ModifyNote&id='+ thisHistoryId +'&orgId='+ thisOrgId +'&popup=true';
      } else {
        urlModify = 'ExternalContactsHistory.do?command=ModifyNote&id='+ thisHistoryId +'&contactId='+ thisContactId +'&popup=true';
      }
      isPopup = true;
      }else if(thisObjectId == '<%= OrganizationHistory.COMPLETE_ACTIVITY %>') {
      showSpan('menuViewAccountHistoryObject');
      showSpan('menuEditAccountHistoryObject');
      urlDetails = 'AccountContactsCalls.do?command=Details&contactId='+thisContactId+'&id='+ thisItemId+'&trailSource=accounts';
      urlModify = 'AccountContactsCalls.do?command=Modify&contactId='+thisContactId+'&id='+ thisItemId;
      isPopup = false;
    } else if(thisObjectId == '<%= OrganizationHistory.CANCELED_ACTIVITY %>') {
      showSpan('menuViewAccountHistoryObject');
      hideSpan('menuEditAccountHistoryObject');
      urlDetails = 'AccountContactsCalls.do?command=Details&contactId='+thisContactId+'&id='+ thisItemId+'&trailSource=accounts';
      isPopup = false;
    } else if(thisObjectId == '<%= OrganizationHistory.CFSNOTE %>') {
      showSpan('menuViewAccountHistoryObject');
      hideSpan('menuEditAccountHistoryObject');
      urlDetails = 'MyCFSInbox.do?command=CFSNoteDetails&listView=sent&id='+thisItemId;
      isPopup = false;
    } else if(thisObjectId == '<%= OrganizationHistory.CAMPAIGN %>') {
      showSpan('menuViewAccountHistoryObject');
      hideSpan('menuEditAccountHistoryObject');
      urlDetails = 'Contacts.do?command=MessageDetails&id='+ thisItemId +'&contactId='+ thisContactId;
      isPopup = false;
    } else if(thisObjectId == '<%= OrganizationHistory.TICKET %>') {
      showSpan('menuViewAccountHistoryObject');
      showSpan('menuEditAccountHistoryObject');
      urlDetails = 'AccountTickets.do?command=TicketDetails&id='+ thisItemId;
      urlModify = 'AccountTickets.do?command=ModifyTicket&id='+ thisItemId;
      isPopup = false;
    } else if(thisObjectId == '<%= OrganizationHistory.TASK %>') {
      showSpan('menuViewAccountHistoryObject');
      showSpan('menuEditAccountHistoryObject');
      urlDetails = 'MyTasks.do?command=Modify&id='+ thisItemId +'<%= isPopup(request)?"&popup=true":"" %>';
      urlModify = 'MyTasks.do?command=Details&id='+ thisItemId +'<%= isPopup(request)?"&popup=true":"" %>';
      isPopup = false;
    } else if(thisObjectId == '<%= OrganizationHistory.QUOTE %>') {
      showSpan('menuViewAccountHistoryObject');
      showSpan('menuEditAccountHistoryObject');
      urlDetails = 'AccountQuotes.do?command=Details&quoteId='+ thisItemId;
      urlModify = 'AccountQuotes.do?command=ModifyForm&quoteId='+ thisItemId;
      isPopup = false;
    } else if(thisObjectId == '<%= OrganizationHistory.OPPORTUNITY %>') {
      showSpan('menuViewAccountHistoryObject');
      showSpan('menuEditAccountHistoryObject');
      if (thisOrgId != -1) {
        urlDetails = 'OpportunitiesComponents.do?command=DetailsComponent&id='+thisItemId+'&orgId='+thisOrgId;
        urlModify = 'OpportunitiesComponents.do?command=ModifyComponent&id='+thisItemId+'&orgId='+thisOrgId;
      } else {
        urlDetails = 'AccountContactsOppComponents.do?command=DetailsComponent&id='+thisItemId+'&contactId='+thisContactId;
        urlModify = 'AccountContactsOppComponents.do?command=ModifyComponent&id='+thisItemId+'&contactId='+thisContactId;
      }
      isPopup = false;
    } else if(thisObjectId == '<%= OrganizationHistory.ACCOUNT_DOCUMENT %>') {
      showSpan('menuViewAccountHistoryObject');
      hideSpan('menuEditAccountHistoryObject');
      urlDetails = 'AccountsDocuments.do?command=Details&orgId='+thisOrgId+'&fid='+thisItemId;
      urlModify = 'ExternalContactsOppComponents.do?command=ModifyComponent&id='+thisItemId+'&contactId='+thisContactId+'<%= isPopup(request)?"&popup=true":"" %>';
      isPopup = false;
    } else if(thisObjectId == '<%= OrganizationHistory.SERVICE_CONTRACT %>') {
      showSpan('menuViewAccountHistoryObject');
      showSpan('menuEditAccountHistoryObject');
      urlDetails = 'AccountsServiceContracts.do?command=View&id='+thisItemId+'&orgId='+thisOrgId;
      urlModify = 'AccountsServiceContracts.do?command=Modify&id='+thisItemId+'&orgId='+thisOrgId;
      isPopup = false;
    } else if(thisObjectId == '<%= OrganizationHistory.RELATIONSHIP %>') {
      showSpan('menuViewAccountHistoryObject');
      hideSpan('menuEditAccountHistoryObject');
      urlDetails = 'AccountRelationships.do?command=View&orgId='+thisOrgId;
      urlModify = 'ExternalContactsOppComponents.do?command=ModifyComponent&id='+thisItemId+'&contactId='+thisContactId+'<%= isPopup(request)?"&popup=true":"" %>';
      isPopup = false;
    } else if(thisObjectId == '<%= OrganizationHistory.PROJECT %>') {
      showSpan('menuViewAccountHistoryObject');//ProjectManagement.do?command=ProjectCenter&pid=11
      hideSpan('menuEditAccountHistoryObject');//ProjectManagement.do?command=ModifyProject&pid=11&return=ProjectCenter
      urlDetails = 'ProjectManagement.do?command=ProjectCenter&section=Details&pid='+thisItemId;
      urlModify = 'ProjectManagement.do?command=ModifyProject&pid='+thisItemId+'&return=ProjectCenter';
      isPopup = false;
    } else {
      hideSpan('menuViewAccountHistoryObject');
      hideSpan('menuEditAccountHistoryObject');
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
    </table>
  </div>
</div>
