<%--
 Copyright 2000-2004 Matt Rajkowski
 matt.rajkowski@teamelements.com
 http://www.teamelements.com
 This source code cannot be modified, distributed or used without
 permission from Matt Rajkowski
--%>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
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
      messageText += "- Title is a required field\r\n";
      formTest = false;
    }
    if (form.shortDescription.value == "") {    
      messageText += "- Description is a required field\r\n";
      formTest = false;
    }
    if (form.requestDate.value == "") {    
      messageText += "- Start Date is a required field\r\n";
      formTest = false;
    }
    if (formTest == false) {
      messageText = "The form could not be submitted.          \r\nPlease verify the following items:\r\n\r\n" + messageText;
      alert(messageText);
      return false;
    } else {
      return true;
    }
  }
</script>
<body onLoad="document.inputForm.title.focus()">
<form method="post" name="inputForm" action="ProjectManagement.do?command=InsertProject&auto-populate=true" onSubmit="return checkForm(this);">
<%= !"&nbsp;".equals(showError(request, "actionError").trim())? showError(request, "actionError"):showWarning(request, "actionWarning")%>
<table cellpadding="4" cellspacing="0" width="100%" class="pagedList">
  <tr>
    <th colspan="2">
      <strong>New Project Information</strong>
    </th>
  </tr>
  <tr>
    <td class="formLabel">
      Title
    </td>
    <td>
      <input type="text" name="title" size="57" maxlength="100" value="<%= toHtmlValue(Project.getTitle()) %>"><font color=red>*</font> <%= showAttribute(request, "titleError") %>
    </td>
  </tr>
  <tr>
    <td nowrap class="formLabel">Short Description</td>
    <td>
      <input type="text" name="shortDescription" size="57" maxlength="200" value="<%= toHtmlValue(Project.getShortDescription()) %>"><font color=red>*</font> <%= showAttribute(request, "shortDescriptionError") %>
    </td>
  </tr>
<zeroio:debug value="Start Date"/>
  <tr>
    <td nowrap class="formLabel">Start Date</td>
    <td>
      <zeroio:dateSelect form="inputForm" field="requestDate" timestamp="<%= Project.getRequestDate() %>" />
      at
<zeroio:debug value="Start Date: Time"/>
      <zeroio:timeSelect baseName="requestDate" value="<%= Project.getRequestDate() %>" timeZone="<%= Project.getRequestDateTimeZone() %>" showTimeZone="yes" />
<zeroio:debug value="Start Date: AMPM"/>
      <font color="red">*</font>
      <%= showAttribute(request, "requestDateError") %>
    </td>
  </tr>
<zeroio:debug value="Est. Close Date"/>
  <tr>
    <td nowrap class="formLabel">Estimated Close Date</td>
    <td>
      <zeroio:dateSelect form="inputForm" field="estimatedCloseDate" timestamp="<%= Project.getEstimatedCloseDate() %>" />
      at
      <zeroio:timeSelect baseName="estimatedCloseDate" value="<%= Project.getEstimatedCloseDate() %>" timeZone="<%= Project.getEstimatedCloseDateTimeZone() %>" showTimeZone="yes" />
      <%= showAttribute(request, "estimatedCloseDateError") %><%= showWarningAttribute(request, "estimatedCloseDateWarning") %>
    </td>
  </tr>
<zeroio:debug value="Requested By"/>
  <tr>
    <td nowrap class="formLabel">Requested By</td>
    <td>
      <input type="text" name="requestedBy" size="24" maxlength="50" value="<%= toHtmlValue(Project.getRequestedBy()) %>">
    </td>
  </tr>
  <tr>
    <td nowrap class="formLabel">Organization</td>
    <td>
      <input type="text" name="requestedByDept" size="24" maxlength="50" value="<%= toHtmlValue(Project.getRequestedByDept()) %>">
    </td>
  </tr>
<zeroio:debug value="Budget"/>
  <tr>
    <td nowrap class="formLabel">Budget</td>
    <td>
      <%= applicationPrefs.get("SYSTEM.CURRENCY") %>
      <input type="hidden" name="budgetCurrency" value="<%= applicationPrefs.get("SYSTEM.CURRENCY") %>" />
      <input type="text" name="budget" size="15" value="<zeroio:number value="<%= Project.getBudget() %>" locale="<%= User.getLocale() %>" />">
      <%=showAttribute(request,"budgetError")%>
    </td>
  </tr>
  <%--
  <tr>
    <td nowrap class="formLabel">Category</td>
    <td>
      <%= categoryList.getHtmlSelect("categoryId", -1) %>
      <a href="javascript:popURL('ProjectManagementCategories.do?command=Edit&popup=true','Category_Editor','400','375','yes','yes');">Edit List</a>
    </td>
  </tr>
  --%>
  <tr>
<%
  String projectApprovedCheck = "";
  String projectClosedCheck = "";
%>
    <zeroio:debug value="Approved"/>
    <td class="formLabel" valign="top" nowrap>Status</td>
    <td>
      <input type="checkbox" name="approved" value="ON"<%= projectApprovedCheck %>>
      Approved <zeroio:tz timestamp="<%= Project.getApprovalDate() %>"/><br />
      <input type="checkbox" name="closed" value="ON"<%= projectClosedCheck %>>
      Closed <zeroio:tz timestamp="<%= Project.getCloseDate() %>"/>
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
<input type="hidden" name="showNews" value="true">
<input type="hidden" name="showDetails" value="true">
<input type="hidden" name="showTeam" value="true">
<input type="hidden" name="showPlan" value="true">
<input type="hidden" name="showLists" value="true">
<input type="hidden" name="showDiscussion" value="true">
<input type="hidden" name="showTickets" value="true">
<input type="hidden" name="showDocuments" value="true">
<input type="submit" value=" Save ">
<input type="button" value="Cancel" onClick="javascript:window.location.href='ProjectManagement.do?command=EnterpriseView'">
</form>
</body>
