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
  var thisMsgId = -1;
  var menu_init = false;
  //Set the action parameters for clicked item
  function displayMenu(loc, id, msgId) {
    thisMsgId = msgId;
    if (!menu_init) {
      menu_init = true;
      new ypSlideOutMenu("menuMsg", "down", 0, 0, 170, getHeight("menuMsgTable"));
    }
    return ypSlideOutMenu.displayDropMenu(id, loc);
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
    <table id="menuMsgTable" class="pulldown" width="170" cellspacing="0">
      <dhv:permission name="campaign-campaigns-messages-view">
      <tr onmouseover="cmOver(this)" onmouseout="cmOut(this)" onclick="details()">
        <th>
          <img src="images/icons/stock_zoom-page-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td width="100%">
          View Details
        </td>
      </tr>
      </dhv:permission>
      <dhv:permission name="campaign-campaigns-messages-edit">
      <tr onmouseover="cmOver(this)" onmouseout="cmOut(this)" onclick="modify()">
        <th>
          <img src="images/icons/stock_edit-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td width="100%">
          Modify
        </td>
      </tr>
      </dhv:permission>
      <dhv:permission name="campaign-campaigns-messages-add">
      <tr onmouseover="cmOver(this)" onmouseout="cmOut(this)" onclick="clone()">
        <th>
          <img src="images/icons/stock_copy-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td width="100%">
          Clone
        </td>
      </tr>
      </dhv:permission>
      <dhv:permission name="campaign-campaigns-messages-delete">
      <tr onmouseover="cmOver(this)" onmouseout="cmOut(this)" onclick="deleteMsg()">
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
