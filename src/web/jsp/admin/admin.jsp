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
Admin
</td>
</tr>
</table>
<%-- End Trails --%>
<table class="note" cellspacing="0">
  <tr>
    <th><img src="images/icons/stock_about-16.gif" border="0" align="absmiddle"/></th>
    <td>Manage this application by reviewing system usage, configuring specific modules, and configuring system parameters.</td>
  </tr>
</table>
<dhv:permission name="admin-users-view,admin-roles-view">
  <table cellpadding="4" cellspacing="0" width="100%" class="pagedList">
    <tr>
      <th>
        <strong>Users &amp; Roles</strong>
      </th>
    </tr>
    <tr class="containerBody">
      <td>
        <ul>
          <dhv:permission name="admin-users-view"><li><a href="Users.do?command=ListUsers">Manage Users</a></li></dhv:permission>
          <dhv:permission name="admin-roles-view"><li><a href="Roles.do?command=ListRoles">Manage Roles</a></li></dhv:permission>
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
        <strong>Configuration</strong>
      </th>
    </tr>
    <tr class="containerBody">
      <td>
        <ul>
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
