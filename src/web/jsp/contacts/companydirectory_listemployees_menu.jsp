<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<script language="javascript">
  var thisEmpId = -1;
  var menu_init = false;
  //Set the action parameters for clicked item
  function displayMenu(id, empId) {
    thisEmpId = empId;
    if (!menu_init) {
      menu_init = true;
      new ypSlideOutMenu("menuEmployee", "down", 0, 0, 170, getHeight("menuEmployeeTable"));
    }
    return ypSlideOutMenu.displayMenu(id);
  }
  
  //Menu link functions
  function details() {
    window.location.href = 'CompanyDirectory.do?command=EmployeeDetails&empid=' + thisEmpId;
  }
  
  function modify() {
    window.location.href = 'CompanyDirectory.do?command=ModifyEmployee&empid=' + thisEmpId + '&return=list';
  }
  
  function deleteEmployee() {
  popURLReturn('CompanyDirectory.do?command=ConfirmDelete&id=' + thisEmpId + '&popup=true','CompanyDirectory.do?command=ListEmployees', 'Delete_Employee','330','200','yes','no');
  }
  
</script>
<div id="menuEmployeeContainer" class="menu">
  <div id="menuEmployeeContent">
    <table id="menuEmployeeTable" class="pulldown" width="170">
      <dhv:permission name="contacts-internal_contacts-view">
      <tr>
        <td>
          <img src="images/icons/stock_zoom-page-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </td>
        <td width="100%">
          <a href="javascript:details()">View Details</a>
        </td>
      </tr>
      </dhv:permission>
      <dhv:permission name="contacts-internal_contacts-edit">
      <tr>
        <td>
          <img src="images/icons/stock_edit-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </td>
        <td width="100%">
          <a href="javascript:modify()">Modify</a>
        </td>
      </tr>
      </dhv:permission>
      <dhv:permission name="contacts-internal_contacts-delete">
      <tr>
        <td>
          <img src="images/icons/stock_delete-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </td>
        <td width="100%">
          <a href="javascript:deleteEmployee()">Delete</a>
        </td>
      </tr>
      </dhv:permission>
    </table>
  </div>
</div>
