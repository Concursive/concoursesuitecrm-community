<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<script language="javascript">
  var thisProcessId = -1;
  var thisModuleId = -1;
  var menu_init = false;
  //Set the action parameters for clicked item
  function displayMenu(loc, id, moduleId, processId) {
    thisProcessId = processId;
    thisModuleId = moduleId;
    if (!menu_init) {
      menu_init = true;
      new ypSlideOutMenu("menuProcess", "down", 0, 0, 170, getHeight("menuProcessTable"));
    }
    return ypSlideOutMenu.displayDropMenu(id, loc);
  }
  //Menu link functions
  function details() {
    window.location.href = 'AdminObjectEvents.do?command=Workflow&moduleId=' + thisModuleId + '&process=' + thisProcessId + '&return=AdminObjectEvents';
  }
  
</script>
<div id="menuProcessContainer" class="menu">
  <div id="menuProcessContent">
    <table id="menuProcessTable" class="pulldown" width="170" cellspacing="0">
      <tr onmouseover="cmOver(this)" onmouseout="cmOut(this)" onclick="details()">
        <th>
          <img src="images/icons/stock_zoom-page-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td width="100%">
          View Details
        </td>
      </tr>
    </table>
  </div>
</div>
