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
<%@ page import="java.util.*" %>
<%@ page import="org.aspcfs.modules.admin.base.*" %>
<%@ page import="org.aspcfs.modules.reports.base.*" %>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<jsp:useBean id="category" class="org.aspcfs.modules.admin.base.PermissionCategory" scope="request"/>
<jsp:useBean id="report" class="org.aspcfs.modules.reports.base.Report" scope="request"/>
<jsp:useBean id="criteria" class="org.aspcfs.modules.reports.base.Criteria" scope="request"/>
<jsp:useBean id="parameterList" class="org.aspcfs.modules.reports.base.ParameterList" scope="request"/>
<jsp:useBean id="systemStatus" class="org.aspcfs.controller.SystemStatus" scope="request"/>
<%@ include file="../initPage.jsp" %>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/checkDate.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popCalendar.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popAccounts.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popLookupSelect.js"></script>
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
<a href="Reports.do"><dhv:label name="qa.reports">Reports</dhv:label></a> >
<a href="Reports.do"><dhv:label name="reports.queue">Queue</dhv:label></a> >
<a href="Reports.do?command=RunReport"><dhv:label name="admin.modules">Modules</dhv:label></a> >
<a href="Reports.do?command=ListReports&categoryId=<%= category.getId() %>"><%= toHtml(category.getCategory()) %></a> >
<a href="Reports.do?command=CriteriaList&categoryId=<%= category.getId() %>&reportId=<%= report.getId() %>&criteriaId=<%= request.getParameter("criteriaId") %>"><dhv:label name="reports.criteriaList">Criteria List</dhv:label></a> >
<dhv:label name="admin.parameters">Parameters</dhv:label>
</td>
</tr>
</table>
<%-- End Trails --%>
<strong><%= toHtml(report.getTitle()) %>:</strong>
<%= toHtml(report.getDescription()) %>
<p><dhv:label name="reports.parametersMustBeSpecified.text">The following parameters must be specified for this report:</dhv:label></p>
<%= showError(request, "actionError") %>
<table cellpadding="4" cellspacing="0" width="100%" class="details">
  <tr>
    <th colspan="2">
      <strong><dhv:label name="admin.parameters">Parameters</dhv:label></strong>
    </th>
  </tr>
<%
  int count = 0;
  Iterator i = parameterList.iterator();
  while (i.hasNext()) {
    Parameter parameter = (Parameter) i.next();
    //Show only the parameters that require input from the user and those that are required
    //siteid is to be prompted but not when user belongs to a specific site (required == false)
%>
<dhv:evaluate if="<%= parameter.getIsForPrompting() %>"><% ++count; %>
  <dhv:evaluate if="<%= !parameter.getName().startsWith("hidden_") %>">
    <tr>
      <td class="formLabel"><%= toHtml(parameter.getDisplayName(systemStatus)) %></td>
      <td>
  </dhv:evaluate>
        <%= parameter.getHtml(systemStatus, request, parameterList) %>
  <dhv:evaluate if="<%= !parameter.getName().startsWith("hidden_") %>">
        <font color="red">*</font>
        <%= showAttribute(request,parameter.getName() + "Error") %>
      </td>
    </tr>
  </dhv:evaluate>
</dhv:evaluate>
<%-- 
  In the report design if the parameter was specified to be used for prompting and while
  preparing the parameter's context it was decided that system's value is to be used
  instead of user provided value, then 'isForPrompting' will be set to false, but the parmeter
  will still be required to use the system provided value while preparing its context
--%>
<dhv:evaluate if="<%= parameter.getRequired() && !parameter.getIsForPrompting() %>">
  <input type="hidden" name="<%= parameter.getName() %>" id="<%= parameter.getName() %>" value="<%= toHtmlValue(parameter.getValue()) %>">
</dhv:evaluate>
<%
  }
%>
<dhv:evaluate if="<%= count == 0 %>">
  <tr>
    <td colspan="2"><dhv:label name="reports.noParametersRequired">No parameters required.</dhv:label></td>
  </tr>
</dhv:evaluate>
</table>
<dhv:evaluate if="<%= count > 0 %>">
<br>
<table cellpadding="4" cellspacing="0" width="100%" class="details">
  <tr>
    <th colspan="2">
      <strong><dhv:label name="reports.nameCriteria.text">Name the criteria for future reference</dhv:label></strong>
      <dhv:label name="reports.optional.brackets">(optional)</dhv:label>
    </th>
  </tr>
  <tr>
    <td class="formLabel"><dhv:label name="accounts.accounts_contacts_calls_details_include.Subject">Subject</dhv:label></td>
    <td><input type="text" name="criteria_subject" size="35" value="<%= toHtmlValue(criteria.getSubject()) %>"/></td>
  </tr>
</table>
<%-- No previously saved criteria --%>
<dhv:evaluate if="<%= "-1".equals(request.getAttribute("criteriaId")) %>">
<input type="checkbox" name="save" value="true"> <dhv:label name="reports.saveCriteria.text">Save this criteria for generating future reports</dhv:label><br />
</dhv:evaluate>
<%-- Using previously saved criteria --%>
<dhv:evaluate if="<%= !"-1".equals(request.getAttribute("criteriaId")) %>">
<input type="radio" name="saveType" value="none" checked> <dhv:label name="reports.doNotSaveCriteria">Do not save criteria for generating future reports</dhv:label><br />
<input type="radio" name="saveType" value="overwrite"> <dhv:label name="reports.overwritePreviousCriteria.text">Overwrite previously saved criteria</dhv:label><br />
<input type="radio" name="saveType" value="save"> <dhv:label name="reports.saveNewCopyCriteria.text">Save a new copy of this criteria</dhv:label><br />
</dhv:evaluate>
</dhv:evaluate>
<br />
<input type="submit" value="<dhv:label name="reports.generateReport">Generate Report</dhv:label>"/>
</form>
