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
  var thisCriteriaId = -1;
  var menu_init = false;
  //Set the action parameters for clicked item
  function displayMenu(loc, id, criteriaId) {
    thisCriteriaId = criteriaId;
    if (!menu_init) {
      menu_init = true;
      new ypSlideOutMenu("menu1", "down", 0, 0, 170, getHeight("menu1Table"));
    }
    return ypSlideOutMenu.displayDropMenu(id, loc);
  }
  //Menu link functions
  function select() {
    window.location.href='Reports.do?command=ParameterList&categoryId=<%= category.getId() %>&reportId=<%= report.getId() %>&criteriaId=' + thisCriteriaId;
  }
  function deleteCriteria() {
    confirmDelete('Reports.do?command=DeleteCriteria&categoryId=<%= category.getId() %>&reportId=<%= report.getId() %>&criteriaId=' + thisCriteriaId);
  }
</script>
<div id="menu1Container" class="menu">
  <div id="menu1Content">
    <table id="menu1Table" class="pulldown" width="170" cellspacing="0">
      <tr onmouseover="cmOver(this)" onmouseout="cmOut(this)" onclick="select()">
        <th>
          <img src="images/icons/stock_compile-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td width="100%">
          Use this criteria
        </td>
      </tr>
      <tr onmouseover="cmOver(this)" onmouseout="cmOut(this)" onclick="deleteCriteria()">
        <th>
          <img src="images/icons/stock_delete-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td width="100%">
          Delete this criteria
        </td>
      </tr>
    </table>
  </div>
</div>
