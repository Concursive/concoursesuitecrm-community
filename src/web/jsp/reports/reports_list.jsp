<%-- 
  - Copyright(c) 2004 Concursive Corporation (http://www.concursive.com/) All
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
  - Version: $Id$
  - Description: 
  --%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*" %>
<%@ page import="org.aspcfs.modules.admin.base.*" %>
<%@ page import="org.aspcfs.modules.reports.base.*" %>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<jsp:useBean id="category" class="org.aspcfs.modules.admin.base.PermissionCategory" scope="request"/>
<jsp:useBean id="reports" class="org.aspcfs.modules.reports.base.ReportList" scope="request"/>
<%@ include file="../initPage.jsp" %>
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="Reports.do"><dhv:label name="qa.reports">Reports</dhv:label></a> >
<a href="Reports.do"><dhv:label name="reports.queue">Queue</dhv:label></a> >
<a href="Reports.do?command=RunReport"><dhv:label name="admin.modules">Modules</dhv:label></a> >
<%= toHtml(category.getCategory()) %>
</td>
</tr>
</table>
<%-- End Trails --%>
<dhv:label name="reports.chooseReportFromList.text">Choose a report from the following list, the next step will be to set criteria for the report:</dhv:label><br>
<br>
<table cellpadding="4" cellspacing="0" width="100%" class="pagedList">
  <tr>
    <th nowrap>
      <strong><dhv:label name="accounts.accounts_contacts_add.Title">Title</dhv:label></strong>
    </th>
    <th width="100%">
      <strong><dhv:label name="accounts.accountasset_include.Description">Description</dhv:label></strong>
    </th>
  </tr>
<%
  Iterator i = reports.iterator();
  int row = 0;
  while (i.hasNext()) {
    Report report = (Report) i.next();
%>
<dhv:permission name='<%= report.getPermissionId() > -1 ? report.getPermissionName() + "-view" : "" %>'>
  <tr class="row<%= ++row%2 == 0 ? "2" : "1" %>">
    <td nowrap><a href="Reports.do?command=CriteriaList&categoryId=<%= category.getId() %>&reportId=<%= report.getId() %>&criteriaId=-1"><%= toHtml(report.getTitle()) %></a></td>
    <td width="100%"><%= toHtml(report.getDescription()) %></td>
  </tr>
</dhv:permission>
<%
  }
%>
<dhv:evaluate if="<%= reports.size() == 0 || row == 0 %>">
  <tr>
    <td colspan="2"><dhv:label name="reports.noReportsFound">No reports found</dhv:label></td>
  </tr>
</dhv:evaluate>
</table>
