<%-- 
  - Copyright Notice: (C) 2000-2004 Dark Horse Ventures, All Rights Reserved.
  - License: This source code cannot be modified, distributed or used without
  -          written permission from Dark Horse Ventures. This notice must
  -          remain in place.
  - Version: $Id$
  - Description: 
  --%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<script language="javascript">
  var thisTypeId = -1;
  var thisNoteId = -1;
  var menu_init = false;
  //Set the action parameters for clicked item
  function displayMenu(loc, id, typeId, noteId) {
    thisTypeId = typeId;
    thisNoteId = noteId;
    if (!menu_init) {
      menu_init = true;
      new ypSlideOutMenu("menuNote", "down", 0, 0, 170, getHeight("menuNoteTable"));
    }
    return ypSlideOutMenu.displayDropMenu(id, loc);
  }
  
  //Menu link functions
  function details() {
    window.location.href='MyCFSInbox.do?command=CFSNoteDetails&id=' + thisNoteId;
  }
  
  function forward() {
    window.location.href='MyCFSInbox.do?command=ForwardMessage&forwardType=' + thisTypeId + '&id=' + thisNoteId + '&return=list';
  }
  
  function reply() {
    window.location.href='MyCFSInbox.do?command=ReplyToMessage&id=' + thisNoteId + '&return=list';
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
          View Details
        </td>
      </tr>
      </dhv:permission>
      <dhv:permission name="myhomepage-inbox-view">
      <tr onmouseover="cmOver(this)" onmouseout="cmOut(this)" onclick="reply()">
        <th>
          <img src="images/icons/stock_reply_mail-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td width="100%">
          Reply
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
          Delete
        </td>
      </tr>
      </dhv:permission>
    </table>
  </div>
</div>
