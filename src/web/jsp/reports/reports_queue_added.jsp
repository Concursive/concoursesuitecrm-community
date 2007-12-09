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
<jsp:useBean id="report" class="org.aspcfs.modules.reports.base.Report" scope="request"/>
<jsp:useBean id="queuePosition" class="java.lang.String" scope="request" />
<%@ include file="../initPage.jsp" %>
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="Reports.do"><dhv:label name="qa.reports">Reports</dhv:label></a> >
<a href="Reports.do"><dhv:label name="reports.queue">Queue</dhv:label></a> >
<a href="Reports.do?command=RunReport"><dhv:label name="admin.modules">Modules</dhv:label></a> >
<a href="Reports.do?command=ListReports&categoryId=<%= category.getId() %>"><%= toHtml(category.getCategory()) %></a> >
<a href="Reports.do?command=CriteriaList&categoryId=<%= category.getId() %>&reportId=<%= report.getId() %>&criteriaId=<%= request.getParameter("criteriaId") %>"><dhv:label name="reports.criteriaList">Criteria List</dhv:label></a> >
<a href="Reports.do?command=ParameterList&categoryId=<%= category.getId() %>&reportId=<%= report.getId() %>&criteriaId=<%= request.getParameter("criteriaId") %>"><dhv:label name="admin.parameters">Parameters</dhv:label></a> >
<dhv:label name="reports.reportAdded">Report Added</dhv:label>
</td>
</tr>
</table>
<%-- End Trails --%>
<dhv:label name="reports.reportAdded.text" param="report=<a href=\"Reports.do?command=ViewQueue\">|end=</a>">The following report has been added to the report queue.  The report can be retrieved from the <a href="Reports.do?command=ViewQueue">queue</a> once the server has finished processing the report.</dhv:label><br />
<br />
<table cellpadding="4" cellspacing="0" width="100%" class="details">
  <tr>
    <th colspan="2">
      <strong><dhv:label name="reports.reportAddedToQueue">Report Added to Queue</dhv:label></strong>
    </th>
  </tr>
  <tr>
    <td class="formLabel"><dhv:label name="qa.module">Module</dhv:label></td>
    <td><%= toHtml(category.getCategory()) %></td>
  </tr>
  <tr>
    <td class="formLabel"><dhv:label name="accounts.accounts_contacts_add.Title">Title</dhv:label></td>
    <td><%= toHtml(report.getTitle()) %></td>
  </tr>
  <tr>
    <td class="formLabel"><dhv:label name="accounts.accountasset_include.Description">Description</dhv:label></td>
    <td><%= toHtml(report.getDescription()) %></td>
  </tr>
  <tr>
    <td class="formLabel"><dhv:label name="reports.queuePosition">Queue Position</dhv:label></td>
    <td><%= queuePosition %> of <%= queuePosition %></td>
  </tr>
</table>
<br>
<input type="button" value="<dhv:label name="button.viewQueue">View Queue</dhv:label>" onClick="javascript:window.location.href='Reports.do?command=ViewQueue'"/>
</form>
