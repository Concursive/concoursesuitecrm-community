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
    if(confirm('Are you sure you would like to delete the preference')) {
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
          Delete
        </td>
      </tr>
      </dhv:permission>
    </table>
  </div>
</div>
