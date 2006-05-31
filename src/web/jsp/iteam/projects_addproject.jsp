<%-- 
  - Copyright(c) 2004 Team Elements LLC (http://www.teamelements.com/) All
  - rights reserved. This material cannot be distributed without written
  - permission from Team Elements LLC. Permission to use, copy, and modify
  - this material for internal use is hereby granted, provided that the above
  - copyright notice and this permission notice appear in all copies. TEAM ELEMENTS
  - LLC MAKES NO REPRESENTATIONS AND EXTENDS NO WARRANTIES, EXPRESS OR
  - IMPLIED, WITH RESPECT TO THE SOFTWARE, INCLUDING, BUT NOT LIMITED TO, THE
  - IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR ANY PARTICULAR
  - PURPOSE, AND THE WARRANTY AGAINST INFRINGEMENT OF PATENTS OR OTHER
  - INTELLECTUAL PROPERTY RIGHTS. THE SOFTWARE IS PROVIDED "AS IS", AND IN NO
  - EVENT SHALL TEAM ELEMENTS LLC OR ANY OF ITS AFFILIATES BE LIABLE FOR
  - ANY DAMAGES, INCLUDING ANY LOST PROFITS OR OTHER INCIDENTAL OR CONSEQUENTIAL
  - DAMAGES RELATING TO THE SOFTWARE.
  - 
  - Author(s): Matt Rajkowski
  - Version: $Id$
  - Description: 
  --%>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,com.zeroio.iteam.base.*" %>
<jsp:useBean id="Project" class="com.zeroio.iteam.base.Project" scope="request"/>
<jsp:useBean id="ProjectList" class="com.zeroio.iteam.base.ProjectList" scope="request"/>
<jsp:useBean id="categoryList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="currencyCodeList" class="org.aspcfs.utils.web.HtmlSelectCurrencyCode" scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<jsp:useBean id="applicationPrefs" class="org.aspcfs.controller.ApplicationPrefs" scope="application"/>
<%@ include file="../initPage.jsp" %>
<script language="JavaScript" type="text/javascript" src="javascript/checkDate.js"></script>
<script language="JavaScript" type="text/javascript" src="javascript/popCalendar.js"></script>
<script language="JavaScript" type="text/javascript" src="javascript/popURL.js"></script>
<script language="JavaScript">
  function checkForm(form) {
    var formTest = true;
    var messageText = "";
    //Check required fields
    if (form.title.value == "") {
      messageText += label("title.required","- Title is a required field\r\n");
      formTest = false;
    }
    if (form.shortDescription.value == "") {    
      messageText += label("description.required","- Description is a required field\r\n");
      formTest = false;
    }
    if (form.requestDate.value == "") {    
      messageText += label("startdate.required","- Start Date is a required field\r\n");
      formTest = false;
    }
    if (formTest == false) {
      messageText = label("Form.not.submitted","The form could not be submitted.          \r\nPlease verify the following items:\r\n\r\n") + messageText;
      alert(messageText);
      return false;
    } else {
      return true;
    }
  }
</script>
<body onLoad="document.inputForm.title.focus()">
<form method="post" name="inputForm" action="ProjectManagement.do?command=InsertProject&auto-populate=true" onSubmit="return checkForm(this);">
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="ProjectManagement.do"><dhv:label name="Projects" mainMenuItem="true">Projects</dhv:label></a> >
<dhv:label name="Add" subMenuItem="true">Add</dhv:label>
</td>
</tr>
</table>
<%-- End Trails --%>
<dhv:formMessage showSpace="false" />
<table cellpadding="4" cellspacing="0" width="100%" class="pagedList">
  <tr>
    <th colspan="2">
      <strong><dhv:label name="project.newProjectInformation">New Project Information</dhv:label></strong>
    </th>
  </tr>
  <tr class="containerBody">
    <td class="formLabel">
      <dhv:label name="accounts.accounts_contacts_add.Title">Title</dhv:label>
    </td>
    <td>
      <input type="text" name="title" size="57" maxlength="100" value="<%= toHtmlValue(Project.getTitle()) %>"><font color=red>*</font> <%= showAttribute(request, "titleError") %>
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel"><dhv:label name="documents.details.shortDescription">Short Description</dhv:label></td>
    <td>
      <input type="text" name="shortDescription" size="57" maxlength="200" value="<%= toHtmlValue(Project.getShortDescription()) %>"><font color=red>*</font> <%= showAttribute(request, "shortDescriptionError") %>
    </td>
  </tr>
  <dhv:evaluate if="<%= categoryList.size() > 1 %>">
  <tr class="containerBody">
    <td nowrap class="formLabel">Category</td>
    <td>
      <%= categoryList.getHtmlSelect("categoryId", Project.getCategoryId()) %>
    </td>
  </tr>
  </dhv:evaluate>
  <tr class="containerBody">
    <td nowrap class="formLabel"><dhv:label name="documents.details.startDate">Start Date</dhv:label></td>
    <td>
      <zeroio:dateSelect form="inputForm" field="requestDate" timestamp="<%= Project.getRequestDate() %>" />
      <dhv:label name="project.at">at</dhv:label>
      <zeroio:timeSelect baseName="requestDate" value="<%= Project.getRequestDate() %>" timeZone="<%= Project.getRequestDateTimeZone() %>" showTimeZone="true" />
      <font color="red">*</font>
      <%= showAttribute(request, "requestDateError") %>
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel"><dhv:label name="project.estimatedCloseDate">Estimated Close Date</dhv:label></td>
    <td>
      <zeroio:dateSelect form="inputForm" field="estimatedCloseDate" timestamp="<%= Project.getEstimatedCloseDate() %>" />
      <dhv:label name="project.at">at</dhv:label>
      <zeroio:timeSelect baseName="estimatedCloseDate" value="<%= Project.getEstimatedCloseDate() %>" timeZone="<%= Project.getEstimatedCloseDateTimeZone() %>" showTimeZone="true" />
      <%= showAttribute(request, "estimatedCloseDateError") %><%= showWarningAttribute(request, "estimatedCloseDateWarning") %>
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel"><dhv:label name="documents.details.requestedBy">Requested By</dhv:label></td>
    <td>
      <input type="text" name="requestedBy" size="24" maxlength="50" value="<%= toHtmlValue(Project.getRequestedBy()) %>">
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel"><dhv:label name="documents.details.organization">Organization</dhv:label></td>
    <td>
      <input type="text" name="requestedByDept" size="24" maxlength="50" value="<%= toHtmlValue(Project.getRequestedByDept()) %>">
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel"><dhv:label name="project.budget">Budget</dhv:label></td>
    <td>
      <%= applicationPrefs.get("SYSTEM.CURRENCY") %>
      <input type="hidden" name="budgetCurrency" value="<%= applicationPrefs.get("SYSTEM.CURRENCY") %>" />
      <input type="text" name="budget" size="15" value="<zeroio:number value="<%= Project.getBudget() %>" locale="<%= User.getLocale() %>" />">
      <%=showAttribute(request,"budgetError")%>
    </td>
  </tr>
  <tr class="containerBody">
<%
  String projectApprovedCheck = (Project.getApproved()?" CHECKED":"");
  String projectClosedCheck = (Project.getClosed()?" CHECKED":"");
%>
    <zeroio:debug value="Approved"/>
    <td class="formLabel" valign="top" nowrap><dhv:label name="accounts.accountasset_include.Status">Status</dhv:label></td>
    <td>
      <input type="checkbox" name="approved" value="ON"<%= projectApprovedCheck %>>
      <dhv:label name="documents.details.approved">Approved</dhv:label> <zeroio:tz timestamp="<%= Project.getApprovalDate() %>"/><br />
      <input type="checkbox" name="closed" value="ON"<%= projectClosedCheck %>>
      <dhv:label name="quotes.closed">Closed</dhv:label> <zeroio:tz timestamp="<%= Project.getCloseDate() %>"/>
    </td>
  </tr>
  <%--
  <tr>
   <td class="formLabel" valign="top" nowrap >Import Data</td>
   <td>
      Copy Requirements, Assignments, and Team from an existing project:<br />
      &nbsp;<%= ProjectList.getHtmlSelect("templateId", 0) %>
    </td>
  </tr>
  --%>
</table>
<br />
<input type="hidden" name="onlyWarnings" value="<%=(Project.getOnlyWarnings()?"on":"off")%>" />
<input type="hidden" name="showCalendar" value="true">
<input type="hidden" name="showNews" value="true">
<input type="hidden" name="showDetails" value="true">
<input type="hidden" name="showTeam" value="true">
<input type="hidden" name="showPlan" value="true">
<input type="hidden" name="showLists" value="true">
<input type="hidden" name="showDiscussion" value="true">
<input type="hidden" name="showTickets" value="true">
<input type="hidden" name="showDocuments" value="true">
<input type="hidden" name="showAccounts" value="true">
<input type="submit" value="<dhv:label name="global.button.save">Save</dhv:label>">
<input type="button" value="<dhv:label name="global.button.cancel">Cancel</dhv:label>" onClick="javascript:window.location.href='ProjectManagement.do?command=ProjectList'">
</form>
</body>
