<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<script language="javascript">
  var thisCompId = -1;
  var menu_init_opp = false;
  //Set the action parameters for clicked item
  function displayOppMenu(loc, id, compId) {
    thisCompId = compId;
    if (!menu_init_opp) {
      menu_init_opp = true;
      new ypSlideOutMenu("menuOpp", "down", 0, 0, 170, getHeight("menuOppTable"));
    }
    return ypSlideOutMenu.displayDropMenu(id, loc);
  }
  
  function modifyOpp() {
    var url = 'CalendarOpportunities.do?command=ModifyComponent&id=' + thisCompId + '&popup=true&return=Calendar';
    popURL(url, 'CONFIRM_DELETE','500','475','yes','yes');
  }
</script>
<div id="menuOppContainer" class="menu">
  <div id="menuOppContent">
    <table id="menuOppTable" class="pulldown" width="170" cellspacing="0">
      <dhv:permission name="pipeline-opportunities-edit">
      <tr onmouseover="cmOver(this)" onmouseout="cmOut(this)"
          onclick="modifyOpp()">
        <th>
          <img src="images/icons/stock_edit-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td width="100%">
          Modify
        </td>
      </tr>
      </dhv:permission>
    </table>
  </div>
</div>
