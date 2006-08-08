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
  var thisQuoteId = -1;
  var thisVersionId = -1;
  var thisContactId = -1;
  var thisHeaderId = -1;
  var menu_init = false;
  var modifiable = 'true';
  var thisAllowMultipleQuote = 'true';
  var thisAllowMultipleVersion = 'true';
  
  //Set the action parameters for clicked item
  function displayMenu(loc, id, orgId, quoteId, contactId, headerId, versionId, modi, trashed, allowMultipleQuote, allowMultipleVersion) {
    thisOrgId = orgId;
    thisQuoteId = quoteId;
    thisContactId = contactId;
    thisHeaderId = headerId;
    thisVersionId = versionId;
    this.modifiable = modi;
    thisAllowMultipleQuote = allowMultipleQuote;
    thisAllowMultipleVersion = allowMultipleVersion;
    updateMenu(trashed);
    if (!menu_init) {
      menu_init = true;
      new ypSlideOutMenu("menuQuote", "down", 0, 0, 170, getHeight("menuQuoteTable"));
    }
    return ypSlideOutMenu.displayDropMenu(id, loc);
  }

  function updateMenu(trashed) {
    if (trashed == 'true'){
      hideSpan('menuModify');
      hideSpan('menuClone');
      hideSpan('menuAddVersion');
      hideSpan('menuDelete');
    } else {
      if(modifiable == 'true'){
        showSpan('menuModify');
      }else{
        hideSpan('menuModify');
      }
      if(thisAllowMultipleQuote == 'true'){
        showSpan('menuClone');
      } else {
        hideSpan('menuClone');
      }
      if(thisAllowMultipleVersion == 'true'){
        showSpan('menuAddVersion');
        showSpan('menuShowVersion');
        if (thisVersionId == ''){
          showSpan('menuShowVersion');
        } else {
          hideSpan('menuShowVersion');
        }
      } else {
        hideSpan('menuAddVersion');
        hideSpan('menuShowVersion');
      }
    }
  }
  //Menu link functions
  function details() {
    window.location.href='AccountContactsOppQuotes.do?command=Details&quoteId=' + thisQuoteId +'&orgId='+ thisOrgId + '&contactId=' + thisContactId + '&headerId=' + thisHeaderId + '<%= addLinkParams(request, "popup|popupType|actionId") %>&version=' + thisVersionId;
  }

  function showVersions() {
    window.location.href='AccountContactsOppQuotes.do?command=View&version=' + thisQuoteId +'&orgId='+ thisOrgId + '&contactId=' + thisContactId + '<%= addLinkParams(request, "popup|popupType|actionId") %>&headerId=' + thisHeaderId;
  }

  function modify() {
    if(modifiable == 'true') {
      window.location.href='AccountContactsOppQuotes.do?command=ModifyForm&quoteId='+ thisQuoteId +'&orgId=' + thisOrgId + '&contactId=' + thisContactId + '<%= addLinkParams(request, "popup|popupType|actionId") %>&headerId=' + thisHeaderId;
    } else {
      alert(label("quote.notmodifiable","The Quote can not be modified.\nPlease either Clone or Create a new Version."));
    }
  }

  function clone() {
    popURL('Quotes.do?command=CloneForm&quoteId='+ thisQuoteId,'Close','500','400','yes','yes');
  }

  function addVersion() {
    if (confirm(label("verify.quote.newversion","Are you sure you want to create a new Version of this Quote?"))) {
      window.location.href='AccountContactsOppQuotes.do?command=AddVersion&quoteId='+ thisQuoteId +'&orgId=' + thisOrgId + '&contactId=' + thisContactId + '<%= addLinkParams(request, "popup|popupType|actionId") %>&headerId=' + thisHeaderId;
    }
  }

  function deleteQuote() {
    popURLReturn('AccountContactsOppQuotes.do?command=ConfirmDelete&quoteId=' + thisQuoteId+ '&popup=true<%= isPopup(request)?"&popupType=inline":"" %>','AccountContactsOppQuotes.do?command=View<%= isPopup(request)?"&popupType=inline":"" %>', 'Delete_Quote','330','200','yes','no');
  }

  function reopenId(tempId) {
    window.location.href='AccountContactsOppQuotes.do?command=Details&quoteId='+tempId+'<%= addLinkParams(request, "popup|popupType|actionId") %>';
  }

  function reopen() {
    window.location.href='AccountContactsOppQuotes.do?command=View&orgId='+ thisOrgId + '&contactId=' + thisContactId + '&headerId=' + thisHeaderId+'<%= addLinkParams(request, "popup|popupType|actionId") %>';
  }

</script>
<div id="menuQuoteContainer" class="menu">
  <div id="menuQuoteContent">
    <table id="menuQuoteTable" class="pulldown" width="170" cellspacing="0">
      <tr onmouseover="cmOver(this)" onmouseout="cmOut(this)" onclick="details();">
        <th>
          <img src="images/icons/stock_zoom-page-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td width="100%">
          <dhv:label name="accounts.accounts_calls_list_menu.ViewDetails">View Details</dhv:label>
        </td>
      </tr>
      <tr id="menuShowVersion" onmouseover="cmOver(this)" onmouseout="cmOut(this)" onclick="showVersions();">
        <th>
          <img src="images/icons/stock_zoom-page-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td width="100%">
          Show Versions
        </td>
      </tr>
      <dhv:permission name="accounts-accounts-contacts-opportunities-quotes-edit">
      <tr id="menuModify" onmouseover="cmOver(this)" onmouseout="cmOut(this)" onclick="modify();">
        <th>
          <img src="images/icons/stock_edit-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td width="100%">
          <dhv:label name="button.modify">Modify</dhv:label>
        </td>
      </tr>
      </dhv:permission>
      <dhv:permission name="accounts-accounts-contacts-opportunities-quotes-add">
      <tr id="menuClone" onmouseover="cmOver(this)" onmouseout="cmOut(this)" onclick="clone();">
        <th>
          <img src="images/icons/stock_copy-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td width="100%">
          <dhv:label name="button.clone">Clone</dhv:label>
        </td>
      </tr>
      </dhv:permission>
      <dhv:permission name="accounts-accounts-contacts-opportunities-quotes-add">
      <tr id="menuAddVersion" onmouseover="cmOver(this)" onmouseout="cmOut(this)" onclick="addVersion();">
        <th>
          <img src="images/icons/stock_copy-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td width="100%">
          <dhv:label name="documents.documents.addVersion">Add Version</dhv:label>
        </td>
      </tr>
      </dhv:permission>
      <dhv:permission name="accounts-accounts-contacts-opportunities-quotes-delete">
      <tr onmouseover="cmOver(this)" onmouseout="cmOut(this)" onclick="deleteQuote();">
        <th>
          <img src="images/icons/stock_delete-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td width="100%">
          <dhv:label name="button.delete">Delete</dhv:label>
        </td>
      </tr>
      </dhv:permission>
    </table>
  </div>
</div>
