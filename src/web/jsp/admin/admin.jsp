<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ include file="../initPage.jsp" %>
<%-- Trails --%>
<table class="trails">
<tr>
<td>
Admin
</td>
</tr>
</table>
<%-- End Trails --%>
Manage Dark Horse CRM by reviewing system usage, configuring specific modules, and configuring system parameters.<br>
&nbsp;<br>
<dhv:permission name="admin-sysconfig-view,admin-users-view,admin-roles-view">
  <table cellpadding="4" cellspacing="0" width="100%" class="pagedList">
    <tr>
      <th>
        <strong>Configuration</strong>
      </th>
    </tr>
    <tr class="containerBody">
      <td>
        <ul>
          <dhv:permission name="admin-users-view"><li><a href="Users.do?command=ListUsers">Manage Users</a></li></dhv:permission>
          <dhv:permission name="admin-roles-view"><li><a href="Roles.do?command=ListRoles">Manage Roles</a></li></dhv:permission>
          <dhv:permission name="admin-sysconfig-view"><li><a href="Admin.do?command=Config">Configure Modules</a></li></dhv:permission>
        </ul>
      </td>
    </tr>
  </table>
  &nbsp;
</dhv:permission>
  
<dhv:permission name="admin-sysconfig-view">
  <table cellpadding="4" cellspacing="0" width="100%" class="pagedList">
    <tr>
      <th>
        <strong>Global Parameters and Server Configuration</strong>
      </th>
    </tr>
    <tr class="containerBody">
      <td>
        <ul>
          <li><a href="AdminConfig.do?command=ListGlobalParams">Configure System</a></li>
        </ul>
      </td>
    </tr>
  </table>
  &nbsp;
</dhv:permission>

<dhv:permission name="admin-usage-view">
  <table cellpadding="4" cellspacing="0" width="100%" class="pagedList">
    <tr>
      <th>
        <strong>Usage</strong>
      </th>
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
