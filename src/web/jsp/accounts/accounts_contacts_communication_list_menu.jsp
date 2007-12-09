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
  var thisContactId = -1;
  var thisPreferenceId = -1;
  var menu_init = false;
  //Set the action parameters for clicked item
  function displayMenu(loc, id, contactId, preferenceId) {
    thisContactId = contactId;
    thisPreferenceId = preferenceId;
    if (!menu_init) {
      menu_init = true;
      new ypSlideOutMenu("menuPreference", "down", 0, 0, 170, getHeight("menuPreferenceTable"));
    }
    return ypSlideOutMenu.displayDropMenu(id, loc);
  }
  //Menu link functions
  function deletePreference() {
    if(confirm(label("delete.preference", "Are you sure you would like to delete the preference"))) {
      window.location.href='Contacts.do?command=DeleteCommunicationsPreference&contactId='+thisContactId+'&preferenceId='+thisPreferenceId;
    }
  }
</script>
<div id="menuPreferenceContainer" class="menu">
  <div id="menuPreferenceContent">
    <table id="menuPreferenceTable" class="pulldown" width="170" cellspacing="0">
      <dhv:permission name="accounts-view">
      <tr onmouseover="cmOver(this)" onmouseout="cmOut(this)" onclick="deletePreference();">
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
