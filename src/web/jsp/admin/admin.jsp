<%@ taglib uri="WEB-INF/dhv-taglib.tld" prefix="dhv" %>
Setup
<hr color="#BFBFBB" noshade>
Customize and configure CFS with the following options:<br>
&nbsp;<br>
<table cellpadding="0" cellspacing="0" border="0" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr>
    <td width="49%" valign="top">
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
      </dhv:permission>
    </td>
    <td width="2%">
      &nbsp;
    </td>
    <td width="49%" valign="top">
      <dhv:permission name="admin-sysconfig-view">
      <table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
        <tr class="title">
          <td>
            <strong><a href="/Admin.do?command=Config">System Configuration</a></strong>
          </td>
        </tr>
        <tr class="containerBody">
          <td>
            Setup CFS to meet the specific needs of your organization, including 
            setting up modules and custom fields.
          </td>
        </tr>
      </table>
      &nbsp;
      </dhv:permission>
      <dhv:permission name="admin-sysmaintenance-view">
      <table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
        <tr class="title">
          <td>
            <strong><a href="#">System Maintenance</a></strong>
          </td>
        </tr>
        <tr class="containerBody">
          <td>
            Maintain your data, including importing, exporting, and backing-up data.
          </td>
        </tr>
      </table>
      </dhv:permission>
    </td>
  </tr>
</table>
