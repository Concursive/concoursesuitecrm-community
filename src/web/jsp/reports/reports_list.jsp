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
<a href="Reports.do">Reports</a> >
<a href="Reports.do">Queue</a> >
<a href="Reports.do?command=RunReport">Add</a> >
<%= toHtml(category.getCategory()) %>
</td>
</tr>
</table>
<%-- End Trails --%>
Choose a report from the following list, the next step will be to set criteria
for the report:<br>
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
<%
  Iterator i = reports.iterator();
  int row = 0;
  while (i.hasNext()) {
    Report report = (Report) i.next();
%>
<dhv:permission name="<%= report.getPermissionId() > -1 ? report.getPermissionName() + "-view" : "" %>">
  <tr class="row<%= ++row%2 == 0 ? "2" : "1" %>">
    <td nowrap><a href="Reports.do?command=CriteriaList&categoryId=<%= category.getId() %>&reportId=<%= report.getId() %>"><%= toHtml(report.getTitle()) %></a></td>
    <td width="100%"><%= toHtml(report.getDescription()) %></td>
  </tr>
</dhv:permission>
<%
  }
%>
<dhv:evaluate if="<%= reports.size() == 0 || row == 0 %>">
  <tr>
    <td colspan="2">No reports found</td>
  </tr>
</dhv:evaluate>
</table>
