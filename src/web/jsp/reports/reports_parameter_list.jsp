<%-- 
  - Copyright Notice: (C) 2000-2004 Dark Horse Ventures, All Rights Reserved.
  - License: This source code cannot be modified, distributed or used without
  -          written permission from Dark Horse Ventures. This notice must
  -          remain in place.
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
<jsp:useBean id="criteria" class="org.aspcfs.modules.reports.base.Criteria" scope="request"/>
<jsp:useBean id="parameterList" class="org.aspcfs.modules.reports.base.ParameterList" scope="request"/>
<%@ include file="../initPage.jsp" %>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/checkDate.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popCalendar.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popAccounts.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/div.js"></script>
<script language="JavaScript">
 function resetNumericFieldValue(fieldId){
  document.getElementById(fieldId).value = -1;
 }
</script>
<form name="paramForm" method="post" action="Reports.do?command=GenerateReport&categoryId=<%= category.getId() %>&reportId=<%= report.getId() %>&criteriaId=<%= request.getParameter("criteriaId") %>">
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="Reports.do">Reports</a> >
<a href="Reports.do">Queue</a> >
<a href="Reports.do?command=RunReport">Modules</a> >
<a href="Reports.do?command=ListReports&categoryId=<%= category.getId() %>"><%= toHtml(category.getCategory()) %></a> >
<a href="Reports.do?command=CriteriaList&categoryId=<%= category.getId() %>&reportId=<%= report.getId() %>&criteriaId=<%= request.getParameter("criteriaId") %>">Criteria List</a> >
Parameters
</td>
</tr>
</table>
<%-- End Trails --%>
<strong><%= toHtml(report.getTitle()) %>:</strong>
<%= toHtml(report.getDescription()) %>
<p>The following parameters must be specified for this report:</p>
<table cellpadding="4" cellspacing="0" width="100%" class="details">
  <tr>
    <th colspan="2">
      <strong>Parameters</strong>
    </th>
  </tr>
<%
  int count = 0;
  Iterator i = parameterList.iterator();
  while (i.hasNext()) {
    Parameter parameter = (Parameter) i.next();
    //Show only the parameters that require input from the user
%>
<dhv:evaluate if="<%= parameter.getIsForPrompting() %>"><% ++count; %>
  <tr>
    <td class="formLabel"><%= toHtml(parameter.getDisplayName()) %></td>
    <td>
      <%= parameter.getHtml(request) %> <font color="red">*</font>
      <%=showAttribute(request,parameter.getName() + "Error") %>
    </td>
  </tr>
</dhv:evaluate>
<%
  }
%>
<dhv:evaluate if="<%= count == 0 %>">
  <tr>
    <td colspan="2">No parameters required.</td>
  </tr>
</dhv:evaluate>
</table>
<dhv:evaluate if="<%= count > 0 %>">
<br>
<table cellpadding="4" cellspacing="0" width="100%" class="details">
  <tr>
    <th colspan="2">
      <strong>Name the criteria for future reference</strong>
    </th>
  </tr>
  <tr>
    <td class="formLabel">Subject</td>
    <td><input type="text" name="criteria_subject" size="35" value="<%= toHtmlValue(criteria.getSubject()) %>"/>
    (optional)</td>
  </tr>
</table>
<%-- No previously saved criteria --%>
<dhv:evaluate if="<%= request.getParameter("criteriaId").equals("-1") %>">
<input type="checkbox" name="save" value="true"> Save this criteria for generating future reports<br />
</dhv:evaluate>
<%-- Using previously saved criteria --%>
<dhv:evaluate if="<%= !request.getParameter("criteriaId").equals("-1") %>">
<input type="radio" name="saveType" value="none" checked> Do not save criteria for generating future reports<br />
<input type="radio" name="saveType" value="overwrite"> Overwrite previously saved criteria<br />
<input type="radio" name="saveType" value="save"> Save a new copy of this criteria<br />
</dhv:evaluate>
</dhv:evaluate>
<br />
<input type="submit" value="Generate Report"/>
</form>
