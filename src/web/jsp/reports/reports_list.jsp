<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*" %>
<%@ page import="org.aspcfs.modules.admin.base.*" %>
<%@ page import="org.aspcfs.modules.reports.base.*" %>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<jsp:useBean id="category" class="org.aspcfs.modules.admin.base.PermissionCategory" scope="request"/>
<jsp:useBean id="reports" class="org.aspcfs.modules.reports.base.ReportList" scope="request"/>
<%@ include file="../initPage.jsp" %>
<a href="Reports.do">Reports</a> >
<a href="Reports.do?command=RunReport">Run Report</a> >
Reports<br>
<hr color="#BFBFBB" noshade>
<%-- Trails --%>
<%--
<table cellpadding="4" cellspacing="0" width="100%" class="trails">
<tr>
<td width="100%">
<a href="Reports.do">Reports</a> >
<a href="Reports.do?command=RunReport">Run Report</a> >
Reports
</td>
</tr>
</table>
<br>
--%>
<%-- End Trails --%>
<strong><%= toHtml(category.getCategory()) %></strong><br>
Choose a report to run:<br>
<br>
<table cellpadding="4" cellspacing="0" width="100%" class="pagedList">
  <tr>
    <th nowrap>
      <strong>Title</strong>
    </th>
    <th width="100%">
      <strong>Description</strong>
    </th>
  </tr>
<dhv:evaluate if="<%= reports.size() == 0 %>">
  <tr>
    <td colspan="2">No reports found</td>
  </tr>
</dhv:evaluate>
<%
  Iterator i = reports.iterator();
  int row = 0;
  while (i.hasNext()) {
    Report report = (Report) i.next();
%>
  <tr class="row<%= ++row%2 == 0 ? "2" : "1" %>">
    <td nowrap><a href="Reports.do?command=CriteriaList&categoryId=<%= category.getId() %>&reportId=<%= report.getId() %>"><%= toHtml(report.getTitle()) %></a></td>
    <td width="100%"><%= toHtml(report.getDescription()) %></td>
  </tr>
<%
  }
%>
</table>
