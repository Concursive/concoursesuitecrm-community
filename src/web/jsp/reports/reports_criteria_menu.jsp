<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<script language="javascript">
  var thisCriteriaId = -1;
  var menu_init = false;
  //Set the action parameters for clicked item
  function displayMenu(id, criteriaId) {
    thisCriteriaId = criteriaId;
    if (!menu_init) {
      menu_init = true;
      new ypSlideOutMenu("menu1", "down", 0, 0, 170, getHeight("menu1Table"));
    }
    return ypSlideOutMenu.displayMenu(id);
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
    <table id="menu1Table" class="pulldown" width="170">
      <tr>
        <td>
          <img src="images/icons/stock_compile-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </td>
        <td width="100%">
          <a href="javascript:select()">Use this criteria</a>
        </td>
      </tr>
      <tr>
        <td>
          <img src="images/icons/stock_delete-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </td>
        <td width="100%">
          <a href="javascript:deleteCriteria()">Delete this criteria</a>
        </td>
      </tr>
    </table>
  </div>
</div>
