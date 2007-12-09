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
<script language="javascript">
  var thisTicketId = -1;
  var thisProjectId = -1;
  var menu_init = false;
  //Set the action parameters for clicked item
  function displayProjectTicketMenu(loc, id, ticketId,projectId) {
    thisTicketId = ticketId;
    thisProjectId = projectId;
    if (!menu_init) {
      menu_init = true;
      initialize_menus();
    }
    return ypSlideOutMenu.displayDropMenu(id, loc);
  }
  
  function modifyprojectticket() {
    popURL('ProjectManagementTickets.do?command=Modify&id=' + thisTicketId + '&pid='+ thisProjectId + '&popup=true&return=Calendar','CRM_Project_Ticket','600','450','yes','yes');
  }
  
</script>
<div id="menuProjectTicketContainer" class="menu">
  <div id="menuProjectTicketContent">
    <table id="menuProjectTicketTable" class="pulldown" width="170" cellspacing="0">
      <tr onmouseover="cmOver(this)" onmouseout="cmOut(this)"
          onclick="modifyprojectticket()">
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
