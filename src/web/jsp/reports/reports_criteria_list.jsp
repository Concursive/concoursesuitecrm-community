<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*" %>
<%@ page import="org.aspcfs.modules.admin.base.*" %>
<%@ page import="org.aspcfs.modules.reports.base.*" %>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<jsp:useBean id="category" class="org.aspcfs.modules.admin.base.PermissionCategory" scope="request"/>
<jsp:useBean id="report" class="org.aspcfs.modules.reports.base.Report" scope="request"/>
<jsp:useBean id="criteriaList" class="org.aspcfs.modules.reports.base.CriteriaList" scope="request"/>
<%@ include file="../initPage.jsp" %>
<%-- Initialize the drop-down menus --%>
<%@ include file="../initPopupMenu.jsp" %>
<%@ include file="reports_criteria_menu.jsp" %>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/confirmDelete.js"></SCRIPT>
<script language="JavaScript" type="text/javascript">
  <%-- Preload image rollovers for drop-down menu --%>
  loadImages('select');
</script>
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="Reports.do">Reports</a> >
<a href="Reports.do">Queue</a> >
<a href="Reports.do?command=RunReport">Add</a> >
<a href="Reports.do?command=ListReports&categoryId=<%= category.getId() %>"><%= toHtml(category.getCategory()) %></a> >
Criteria List
</td>
</tr>
</table>
<%-- End Trails --%>
<strong><%= toHtml(report.getTitle()) %>:</strong>
<%= toHtml(report.getDescription()) %>
<p>Choose to base this report on previously saved criteria, or continue and create
new criteria:</p>
<table cellpadding="4" cellspacing="0" width="100%" class="pagedList">
  <tr>
    <th>
      <strong>Action</strong>
    </th>
    <th width="100%">
      <strong>Subject</strong>
    </th>
    <th>
      <strong>Date</strong>
    </th>
  </tr>
<dhv:evaluate if="<%= criteriaList.size() == 0 %>">
  <tr>
    <td colspan="3">No criteria found, choose continue to create new criteria.</td>
  </tr>
</dhv:evaluate>
<%
  Iterator i = criteriaList.iterator();
  int row = 0;
  while (i.hasNext()) {
    Criteria criteria = (Criteria) i.next();
%>
  <tr class="row<%= ++row%2 == 0 ? "2" : "1" %>">
    <td><a href="javascript:displayMenu('menu1','<%= criteria.getId() %>');" onMouseOver="over(0, <%= criteria.getId() %>)" onmouseout="out(0, <%= criteria.getId() %>)"><img src="images/select.gif" name="select<%= criteria.getId() %>" align="absmiddle" border="0"></a></td>
    <td><a href="Reports.do?command=ParameterList&categoryId=<%= category.getId() %>&reportId=<%= report.getId() %>&criteriaId=<%= criteria.getId() %>"><%= toHtml(criteria.getSubject()) %></a></td>
    <td nowrap><%= criteria.getModified() %></td>
  </tr>
<%
  }
%>
</table>
<br>
<input type="button" value="Continue" onClick="javascript:window.location.href='Reports.do?command=ParameterList&categoryId=<%= category.getId() %>&reportId=<%= report.getId() %>&criteriaId=-1'"/>
