<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<script language="javascript">
  var thisTypeId = -1;
  var thisNoteId = -1;
  var menu_init = false;
  //Set the action parameters for clicked item
  function displayMenu(id, typeId, noteId) {
    thisTypeId = typeId;
    thisNoteId = noteId;
    if (!menu_init) {
      menu_init = true;
      new ypSlideOutMenu("menuNote", "down", 0, 0, 170, getHeight("menuNoteTable"));
    }
    return ypSlideOutMenu.displayMenu(id);
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
    <table id="menuNoteTable" class="pulldown" width="170">
      <dhv:permission name="myhomepage-inbox-view">
      <tr>
        <td>
          <img src="images/icons/stock_zoom-page-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </td>
        <td width="100%">
          <a href="javascript:details()">View Details</a>
        </td>
      </tr>
      </dhv:permission>
      <dhv:permission name="myhomepage-inbox-view">
      <tr>
        <td>
          &nbsp;
        </td>
        <td width="100%">
          <a href="javascript:reply()">Reply</a>
        </td>
      </tr>
      </dhv:permission>
      <dhv:permission name="myhomepage-inbox-view">
      <tr>
        <td>
          &nbsp;
        </td>
        <td width="100%">
          <a href="javascript:forward()">Forward</a>
        </td>
      </tr>
      </dhv:permission>
      <dhv:permission name="myhomepage-inbox-view">
      <tr>
        <td>
          <img src="images/icons/stock_delete-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </td>
        <td width="100%">
          <a href="javascript:deleteNote()">Delete</a>
        </td>
      </tr>
      </dhv:permission>
    </table>
  </div>
</div>
