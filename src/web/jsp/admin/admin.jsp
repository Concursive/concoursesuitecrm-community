<%@ taglib uri="WEB-INF/dhv-taglib.tld" prefix="dhv" %>
Setup
<hr color="#BFBFBB" noshade>
Manage CFS by reviewing system usage, configuring specific modules, and configuring system parameters.<br>
&nbsp;<br>
<dhv:permission name="admin-reassign-view">
  <table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
    <tr class="title">
      <td>
        <strong>Usage</strong>
      </td>
    </tr>
    <tr class="containerBody">
      <td>
        <ul>
          <li><a href="Admin.do?command=Usage">Check system resources</a></li>
        </ul>
      </td>
    </tr>
  </table>
  &nbsp;
</dhv:permission>
  
<dhv:permission name="admin-sysconfig-view">
  <table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
    <tr class="title">
      <td>
        <strong>Configuration</strong>
      </td>
    </tr>
    <tr class="containerBody">
      <td>
        <ul>
          <dhv:permission name="admin-users-view"><li><a href="Users.do?command=ListUsers">Modify Users</a></li></dhv:permission>
          <dhv:permission name="admin-roles-view"><li><a href="Roles.do?command=ListRoles">Modify Roles</a></li></dhv:permission>
          <dhv:permission name="admin-sysconfig-view"><li><a href="Admin.do?command=Config">Configure Modules</a></li></dhv:permission>
        </ul>
      </td>
    </tr>
  </table>
  &nbsp;
</dhv:permission>
  
<dhv:permission name="admin-sysconfig-view">
  <table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
    <tr class="title">
      <td>
        <strong>Global Parameters</strong>
      </td>
    </tr>
    <tr class="containerBody">
      <td>
        <ul>
          <li><a href="Admin.do?command=ListGlobalParams">Configure global parameters</a></li>
        </ul>
      </td>
    </tr>
  </table>
  &nbsp;
</dhv:permission>

