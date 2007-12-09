<%--
  - Copyright(c) 2006 Concursive Corporation (http://www.concursive.com/) All
  - rights reserved. This material cannot be distributed without written
  - permission from Concursive Corporation. Permission to use, copy, and modify
  - this material for internal use is hereby granted, provided that the above
  - copyright notice and this permission notice appear in all copies. CONCURSIVE
  - CORPORATION MAKES NO REPRESENTATIONS AND EXTENDS NO WARRANTIES, EXPRESS OR
  - IMPLIED, WITH RESPECT TO THE SOFTWARE, INCLUDING, BUT NOT LIMITED TO, THE
  - IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR ANY PARTICULAR
  - PURPOSE, AND THE WARRANTY AGAINST INFRINGEMENT OF PATENTS OR OTHER
  - INTELLECTUAL PROPERTY RIGHTS. THE SOFTWARE IS PROVIDED "AS IS", AND IN NO
  - EVENT SHALL CONCURSIVE CORPORATION OR ANY OF ITS AFFILIATES BE LIABLE FOR
  - ANY DAMAGES, INCLUDING ANY LOST PROFITS OR OTHER INCIDENTAL OR CONSEQUENTIAL
  - DAMAGES RELATING TO THE SOFTWARE.
  - 
  - Version: $Id: $
  - Description: 
  --%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ include file="../initPage.jsp" %>
<%-- Trails --%>
<table class="trails" cellspacing="0"><tr>
	<td>
		<dhv:label name="trails.sync">Sync</dhv:label>
	</td>
</tr></table>
<%-- End Trails --%>
<!-- dhv:permission name="admin-users-view,admin-roles-view" -->
<table cellpadding="4" cellspacing="0" width="100%" class="pagedList"><tr>
  <th>
    <strong><dhv:label name="sync.sync">Synchronization</dhv:label></strong>
  </th>
</tr><tr class="containerBody">
  <td>
    <ul>
      <li><a href="Sync.do?command=History"><dhv:label name="sync.history">Synchronization History</dhv:label></a></li>
      <li><a href="Sync.do?command=Download"><dhv:label name="sync.download">Download Data</dhv:label></a></li>
      <li><a href="Sync.do?command=Upload"><dhv:label name="sync.upload">Upload Data</dhv:label></a></li>
    </ul>
  </td>
</tr></table>
  &nbsp;
<!-- /dhv:permission -->