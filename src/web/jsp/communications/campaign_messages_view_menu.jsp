<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<script language="javascript">
  var thisMsgId = -1;
  var menu_init = false;
  //Set the action parameters for clicked item
  function displayMenu(id, msgId) {
    thisMsgId = msgId;
    if (!menu_init) {
      menu_init = true;
      new ypSlideOutMenu("menuMsg", "down", 0, 0, 170, getHeight("menuMsgTable"));
    }
    return ypSlideOutMenu.displayMenu(id);
  }
  
  //Menu link functions
  function details() {
    window.location.href='CampaignManagerMessage.do?command=Details&id=' + thisMsgId;
  }
  
  function modify() {
    window.location.href='CampaignManagerMessage.do?command=Modify&id=' + thisMsgId + '&return=list';
  }
  
  function clone() {
    window.location.href='CampaignManagerMessage.do?command=Clone&id=' + thisMsgId + '&return=list';
  }
  
  function deleteMsg() {
   popURLReturn('CampaignManagerMessage.do?command=ConfirmDelete&id=' + thisMsgId + '&popup=true','CampaignManagerMessage.do?command=View', 'Delete_message','330','200','yes','no');
  }
</script>
<div id="menuMsgContainer" class="menu">
  <div id="menuMsgContent">
    <table id="menuMsgTable" class="pulldown" width="170">
      <dhv:permission name="campaign-campaigns-messages-view">
      <tr>
        <td>
          <img src="images/icons/stock_zoom-page-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </td>
        <td width="100%">
          <a href="javascript:details()">View Details</a>
        </td>
      </tr>
      </dhv:permission>
      <dhv:permission name="campaign-campaigns-messages-edit">
      <tr>
        <td>
          <img src="images/icons/stock_edit-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </td>
        <td width="100%">
          <a href="javascript:modify()">Modify</a>
        </td>
      </tr>
      </dhv:permission>
      <dhv:permission name="campaign-campaigns-messages-add">
      <tr>
        <td>
          &nbsp;
        </td>
        <td width="100%">
          <a href="javascript:clone()">Clone</a>
        </td>
      </tr>
      </dhv:permission>
      <dhv:permission name="campaign-campaigns-messages-delete">
      <tr>
        <td>
          <img src="images/icons/stock_delete-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </td>
        <td width="100%">
          <a href="javascript:deleteMsg()">Delete</a>
        </td>
      </tr>
      </dhv:permission>
    </table>
  </div>
</div>
