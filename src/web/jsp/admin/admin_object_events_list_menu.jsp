<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<script language="javascript">
  var thisProcessId = -1;
  var thisModuleId = -1;
  var menu_init = false;
  //Set the action parameters for clicked item
  function displayMenu(id, moduleId, processId) {
    thisProcessId = processId;
    thisModuleId = moduleId;
    if (!menu_init) {
      menu_init = true;
      new ypSlideOutMenu("menuProcess", "down", 0, 0, 170, getHeight("menuProcessTable"));
    }
    return ypSlideOutMenu.displayMenu(id);
  }
  //Menu link functions
  function details() {
    window.location.href = 'AdminObjectEvents.do?command=Workflow&moduleId=' + thisModuleId + '&process=' + thisProcessId + '&return=AdminObjectEvents';
  }
  
</script>
<div id="menuProcessContainer" class="menu">
  <div id="menuProcessContent">
    <table id="menuProcessTable" class="pulldown" width="170">
      <tr>
        <td>
          <img src="images/icons/stock_zoom-page-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </td>
        <td width="100%">
          <a href="javascript:details()">View Details</a>
        </td>
      </tr>
    </table>
  </div>
</div>
