<%-- 
  - Copyright(c) 2004 Dark Horse Ventures LLC (http://www.centriccrm.com/) All
  - rights reserved. This material cannot be distributed without written
  - permission from Dark Horse Ventures LLC. Permission to use, copy, and modify
  - this material for internal use is hereby granted, provided that the above
  - copyright notice and this permission notice appear in all copies. DARK HORSE
  - VENTURES LLC MAKES NO REPRESENTATIONS AND EXTENDS NO WARRANTIES, EXPRESS OR
  - IMPLIED, WITH RESPECT TO THE SOFTWARE, INCLUDING, BUT NOT LIMITED TO, THE
  - IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR ANY PARTICULAR
  - PURPOSE, AND THE WARRANTY AGAINST INFRINGEMENT OF PATENTS OR OTHER
  - INTELLECTUAL PROPERTY RIGHTS. THE SOFTWARE IS PROVIDED "AS IS", AND IN NO
  - EVENT SHALL DARK HORSE VENTURES LLC OR ANY OF ITS AFFILIATES BE LIABLE FOR
  - ANY DAMAGES, INCLUDING ANY LOST PROFITS OR OTHER INCIDENTAL OR CONSEQUENTIAL
  - DAMAGES RELATING TO THE SOFTWARE.
  - 
  - Version: $Id$
  - Description: 
  --%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ include file="../initPage.jsp" %>
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<dhv:label name="trails.admin">Admin</dhv:label>
</td>
</tr>
</table>
<%-- End Trails --%>
<table class="note" cellspacing="0">
  <tr>
    <th><img src="images/icons/stock_about-16.gif" border="0" align="absmiddle"/></th>
    <td><dhv:label name="admin.manageApplication.text">Manage this application by reviewing system usage, configuring specific modules, and configuring system parameters.</dhv:label></td>
  </tr>
</table>
<dhv:permission name="admin-users-view,admin-roles-view">
  <table cellpadding="4" cellspacing="0" width="100%" class="pagedList">
    <tr>
      <th>
        <strong><dhv:label name="admin.usersAndRoles" param="amp=&amp;">Users &amp; Roles</dhv:label></strong>
      </th>
    </tr>
    <tr class="containerBody">
      <td>
        <ul>
          <dhv:permission name="admin-users-view"><li><a href="Users.do?command=ListUsers"><dhv:label name="admin.manageUsers">Manage Users</dhv:label></a></li></dhv:permission>
          <dhv:permission name="admin-roles-view"><li><a href="Roles.do?command=ListRoles"><dhv:label name="admin.manageRoles">Manage Roles</dhv:label></a></li></dhv:permission>
          <dhv:permission name="admin-users-view"><li><a href="UserGroups.do?command=List"><dhv:label name="usergroups.manageGroups">Manage Groups</dhv:label></a></li></dhv:permission>
          <dhv:permission name="admin-portalroleeditor-view"><li><a href="PortalRoleEditor.do?command=List"><dhv:label name="admin.managePortalRoles">Manage Portal Roles</dhv:label></a></li></dhv:permission>
        </ul>
        </td>
    </tr>
  </table>
  &nbsp;
</dhv:permission>

<dhv:permission name="admin-sysconfig-view,admin-actionplans-view">
  <table cellpadding="4" cellspacing="0" width="100%" class="pagedList">
    <tr>
      <th>
        <strong><dhv:label name="admin.configuration">Configuration</dhv:label></strong>
      </th>
    </tr>
    <tr class="containerBody">
      <td>
        <ul>
          <dhv:permission name="admin-sysconfig-view"><li><a href="Admin.do?command=Config"><dhv:label name="trails.configureModules">Configure Modules</dhv:label></a></li></dhv:permission>
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
        <strong><dhv:label name="admin.globalParameters">Global Parameters and Server Configuration</dhv:label></strong>
      </th>
    </tr>
    <tr class="containerBody">
      <td>
        <ul>
          <li><a href="AdminConfig.do?command=ListGlobalParams"><dhv:label name="admin.configureSystem">Configure System</dhv:label></a></li>
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
        <strong><dhv:label name="admin.Usage">Usage</dhv:label></strong>
      </th>
    </tr>
    <tr class="containerBody">
      <td>
        <ul>
          <li><a href="Admin.do?command=Usage"><dhv:label name="admin.checkSystemResources">Check system resources</dhv:label></a></li>
        </ul>
      </td>
    </tr>
  </table>
  &nbsp;
</dhv:permission>
