<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*" %>
<%@ page import="org.aspcfs.modules.admin.base.*" %>
<%@ page import="org.aspcfs.modules.reports.base.*" %>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<jsp:useBean id="category" class="org.aspcfs.modules.admin.base.PermissionCategory" scope="request"/>
<jsp:useBean id="report" class="org.aspcfs.modules.reports.base.Report" scope="request"/>
<jsp:useBean id="queuePosition" class="java.lang.String" scope="request" />
<%@ include file="../initPage.jsp" %>
<%-- trails --%>
<a href="Reports.do">Reports</a> >
<a href="Reports.do?command=RunReport">Run Report</a> >
<a href="Reports.do?command=ListReports&categoryId=<%= category.getId() %>">Reports</a> >
<a href="Reports.do?command=CriteriaList&categoryId=<%= category.getId() %>&reportId=<%= report.getId() %>">Criteria List</a> >
<a href="Reports.do?command=ParameterList&categoryId=<%= category.getId() %>&reportId=<%= report.getId() %>&criteriaId=<%= request.getParameter("criteriaId") %>">Parameters</a> >
Report Added<br>
<hr color="#BFBFBB" noshade>
<%-- end trails --%>
<p>The following report has been added to the report queue.  The report can be
retrieved from the <a href="Reports.do?command=ViewQueue">queue</a> once the server 
has finished processing the report.</p>
<table cellpadding="4" cellspacing="0" width="100%" class="details">
  <tr>
    <th colspan="2">
      <strong>Report Added to Queue</strong>
    </th>
  </tr>
  <tr>
    <td class="formLabel">Module</td>
    <td><%= toHtml(category.getCategory()) %></td>
  </tr>
  <tr>
    <td class="formLabel">Title</td>
    <td><%= toHtml(report.getTitle()) %></td>
  </tr>
  <tr>
    <td class="formLabel">Description</td>
    <td><%= toHtml(report.getDescription()) %></td>
  </tr>
  <tr>
    <td class="formLabel">Queue Position</td>
    <td><%= queuePosition %> of <%= queuePosition %></td>
  </tr>
</table>
<br>
<input type="button" value="View Queue" onClick="javascript:window.location.href='Reports.do?command=ViewQueue'"/>
</form>
