<%@ taglib uri="WEB-INF/dhv-taglib.tld" prefix="dhv" %>
Setup
<hr color="#BFBFBB" noshade>
Customize and configure CFS with the following options:<br>
&nbsp;<br>
<dhv:permission name="admin-reassign-view">
  <table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
    <tr class="title">
      <td>
        <strong><a href="/Users.do?command=Reassign">Re-Assignments</a></strong>
      </td>
    </tr>
    <tr class="containerBody">
      <td>
        Quickly move data associated with one user to another user.
      </td>
    </tr>
  </table>
  &nbsp;
</dhv:permission>
  
<dhv:permission name="admin-users-view">
  <table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
    <tr class="title">
      <td>
        <strong><a href="/Users.do">Users</a></strong>
      </td>
    </tr>
    <tr class="containerBody">
      <td>
        Add and modify users, and define user hierarchy.
      </td>
    </tr>
  </table>
  &nbsp;
</dhv:permission>
  
<dhv:permission name="admin-roles-view">
  <table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
    <tr class="title">
      <td>
        <strong><a href="/Roles.do">Roles</a></strong>
      </td>
    </tr>
    <tr class="containerBody">
      <td>
        Add and modify roles, and define role permissions.
      </td>
    </tr>
  </table>
  &nbsp;
</dhv:permission>

<dhv:permission name="admin-sysconfig-view">
  <table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
    <tr class="title">
      <td>
        <strong><a href="/Admin.do?command=Manage">System Management</a></strong>
      </td>
    </tr>
    <tr class="containerBody">
      <td>
        Review and configure CFS to meet the specific needs of your organization.
      </td>
    </tr>
  </table>
  &nbsp;
</dhv:permission>
