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
  var thisTypeId = -1;
  var thisNoteId = -1;
  var menu_init = false;
  //Set the action parameters for clicked item
  function displayMenu(loc, id, typeId, noteId, statusId) {
    thisTypeId = typeId;
    thisNoteId = noteId;
    updateMenu(statusId);
    if (!menu_init) {
      menu_init = true;
      new ypSlideOutMenu("menuNote", "down", 0, 0, 170, getHeight("menuNoteTable"));
    }
    return ypSlideOutMenu.displayDropMenu(id, loc);
  }
  
  function updateMenu(statusId) {
    if (statusId != 0) {
      if (statusId == 2) {
        hideSpan("archive");
        showSpan("sendToInbox");
      } else {
        showSpan("archive");
        hideSpan("sendToInbox");
      }
    } else {
      hideSpan("archive");
      hideSpan("sendToInbox");
    }
  }
  
  //Menu link functions
  function details() {
    window.location.href='MyCFSInbox.do?command=CFSNoteDetails&id=' + thisNoteId;
  }
  
  function sendToInbox() {
    window.location.href='MyCFSInbox.do?command=CFSNoteTrash&id='+ thisNoteId +'&type='+ thisTypeId +'&return=list';
  }
  
  function archive() {
    window.location.href='MyCFSInbox.do?command=CFSNoteTrash&id='+ thisNoteId +'&type='+ thisTypeId+'&return=list';
  }

  function forward() {
    window.location.href='MyCFSInbox.do?command=ForwardMessage&forwardType=' + thisTypeId + '&id=' + thisNoteId + '&return=list';
  }
  
  function reply() {
    window.location.href='MyCFSInbox.do?command=ReplyToMessage&id=' + thisNoteId + '&return=list&forwardType=' + thisTypeId;
  }
  
  function deleteNote() {
    confirmDelete('MyCFSInbox.do?command=CFSNoteDelete&id=' + thisNoteId);
  }
  
</script>
<div id="menuNoteContainer" class="menu">
  <div id="menuNoteContent">
    <table id="menuNoteTable" class="pulldown" width="170" cellspacing="0">
      <dhv:permission name="myhomepage-inbox-view">
      <tr onmouseover="cmOver(this)" onmouseout="cmOut(this)" onclick="details()">
        <th>
          <img src="images/icons/stock_zoom-page-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td width="100%">
          <dhv:label name="accounts.accounts_calls_list_menu.ViewDetails">View Details</dhv:label>
        </td>
      </tr>
      </dhv:permission>
      <dhv:permission name="myhomepage-inbox-view">
      <tr onmouseover="cmOver(this)" onmouseout="cmOut(this)" onclick="reply()">
        <th>
          <img src="images/icons/stock_reply_mail-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td width="100%">
          <dhv:label name="project.reply">Reply</dhv:label>
        </td>
      </tr>
      </dhv:permission>
      <dhv:permission name="myhomepage-inbox-view">
      <tr id="archive" onmouseover="cmOver(this)" onmouseout="cmOut(this)" onclick="archive()">
        <th>
          <img src="images/icons/stock_archive-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td width="100%">
          <dhv:label name="accounts.accounts_list_menu.Archive">Archive</dhv:label>
        </td>
      </tr>
      </dhv:permission>
      <dhv:permission name="myhomepage-inbox-view">
      <tr id="sendToInbox" onmouseover="cmOver(this)" onmouseout="cmOut(this)" onclick="sendToInbox()">
        <th>
          <img src="images/icons/stock_archive-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td width="100%">
          <dhv:label name="">Send to Inbox</dhv:label>
        </td>
      </tr>
      </dhv:permission>
      <dhv:permission name="myhomepage-inbox-view">
      <tr onmouseover="cmOver(this)" onmouseout="cmOut(this)" onclick="forward()">
        <th>
          <img src="images/icons/stock_forward_mail-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td width="100%">
          Forward
        </td>
      </tr>
      </dhv:permission>
      <dhv:permission name="myhomepage-inbox-view">
      <tr onmouseover="cmOver(this)" onmouseout="cmOut(this)" onclick="deleteNote()">
        <th>
          <img src="images/icons/stock_delete-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td width="100%">
          <dhv:label name="global.button.delete">Delete</dhv:label>
        </td>
      </tr>
      </dhv:permission>
    </table>
  </div>
</div>
