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
<%@ page import="java.util.*,com.zeroio.iteam.base.*" %>
<jsp:useBean id="Project" class="com.zeroio.iteam.base.Project" scope="request"/>
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
<form method="post" name="inputForm" action="ProjectManagement.do?command=UpdateProject&auto-populate=true" onSubmit="return checkForm(this);">
<table border="0" cellpadding="1" cellspacing="0" width="100%">
  <tr class="subtab">
    <td>
      <img src="images/icons/stock_macro-objects-16.gif" border="0" align="absmiddle">
      <a href="ProjectManagement.do?command=ProjectCenter&section=Details&pid=<%= Project.getId() %>">Overview</a> >
      Modify Project
    </td>
  </tr>
</table>
<br />
<input type="submit" value=" Update ">
<input type="button" value="Cancel" onClick="javascript:window.location.href='ProjectManagement.do?command=ProjectCenter&section=Details&pid=<%= Project.getId() %>'"><br />
&nbsp;
<dhv:formMessage />
<table cellpadding="4" cellspacing="0" width="100%" class="pagedList">
  <input type="hidden" name="id" value="<%= Project.getId() %>">
  <input type="hidden" name="modified" value="<%= Project.getModified() %>">
  <tr>
    <th colspan="2" valign="center">
      <strong>Update Project Information</strong>
    </th>
  </tr>
  <tr class="containerBody">
<%
  String projectApprovedCheck = "";
  if (Project.getApprovalDate() != null) {
    projectApprovedCheck = " checked";
  }
  String projectClosedCheck = "";
  if (Project.getCloseDate() != null) {
    projectClosedCheck = " checked";
  }
%>
    <td class="formLabel" valign="middle" nowrap>Status</td>
    <td>
      <input type="checkbox" name="approved" value="ON"<%= projectApprovedCheck %>>
      Approved <zeroio:tz timestamp="<%= Project.getApprovalDate() %>"/><br />
      <input type="checkbox" name="closed" value="ON"<%= projectClosedCheck %>>
      Closed <zeroio:tz timestamp="<%= Project.getCloseDate() %>"/>
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">Title</td>
    <td>
      <input type="text" name="title" size="57" maxlength="100" value="<%= toHtmlValue(Project.getTitle()) %>"><font color=red>*</font> <%= showAttribute(request, "titleError") %>
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">Short Description</td>
    <td>
      <input type="text" name="shortDescription" size="57" maxlength="200" value="<%= toHtmlValue(Project.getShortDescription()) %>"><font color=red>*</font>
      <%= showAttribute(request, "shortDescriptionError") %>
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">Start Date</td>
    <td>
      <zeroio:dateSelect form="inputForm" field="requestDate" timestamp="<%= Project.getRequestDate() %>"/>
      at
      <zeroio:timeSelect baseName="requestDate" value="<%= Project.getRequestDate() %>" timeZone="<%= Project.getRequestDateTimeZone() %>" showTimeZone="yes" />
      <font color="red">*</font>
      <%= showAttribute(request, "requestDateError") %>
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">Estimated Close Date</td>
    <td>
      <zeroio:dateSelect form="inputForm" field="estimatedCloseDate" timestamp="<%= Project.getEstimatedCloseDate() %>" />
      at
      <zeroio:timeSelect baseName="estimatedCloseDate" value="<%= Project.getEstimatedCloseDate() %>" timeZone="<%= Project.getEstimatedCloseDateTimeZone() %>" showTimeZone="yes" />
      <%= showAttribute(request, "estimatedCloseDateError") %><%= showWarningAttribute(request, "estimatedCloseDateWarning") %>
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">Requested By</td>
    <td>
      <input type="text" name="requestedBy" size="24" maxlength="50" value="<%= toHtmlValue(Project.getRequestedBy()) %>">
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">Organization</td>
    <td>
      <input type="text" name="requestedByDept" size="24" maxlength="50" value="<%= toHtmlValue(Project.getRequestedByDept()) %>">
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">Budget</td>
    <td>
      <%= applicationPrefs.get("SYSTEM.CURRENCY") %>
      <input type="hidden" name="budgetCurrency" value="<%= applicationPrefs.get("SYSTEM.CURRENCY") %>" />
      <input type="text" name="budget" size="15" value="<zeroio:number value="<%= Project.getBudget() %>" locale="<%= User.getLocale() %>" />">
      <%=showAttribute(request,"budgetError")%>
    </td>
  </tr>
  <%--
  <tr class="containerBody">
    <td nowrap class="formLabel">Category</td>
    <td>
      <%= categoryList.getHtmlSelect("categoryId", -1) %>
      <a href="javascript:popURL('ProjectManagementCategories.do?command=Edit&popup=true','Category_Editor','400','375','yes','yes');">Edit List</a>
    </td>
  </tr>
  --%>
</table>
<br />
<input type="hidden" name="onlyWarnings" value="<%=(Project.getOnlyWarnings()?"on":"off")%>" />
<input type="hidden" name="portal" value="<%= Project.getPortal() %>">
<input type="hidden" name="allowGuests" value="<%= Project.getAllowGuests() %>">
<input type="hidden" name="showNews" value="<%= Project.getShowNews() %>">
<input type="hidden" name="showDetails" value="<%= Project.getShowDetails() %>">
<input type="hidden" name="showTeam" value="<%= Project.getShowTeam() %>">
<input type="hidden" name="showPlan" value="<%= Project.getShowPlan() %>">
<input type="hidden" name="showLists" value="<%= Project.getShowLists() %>">
<input type="hidden" name="showDiscussion" value="<%= Project.getShowDiscussion() %>">
<input type="hidden" name="showTickets" value="<%= Project.getShowTickets() %>">
<input type="hidden" name="showDocuments" value="<%= Project.getShowDocuments() %>">
<input type="submit" value=" Update ">
<input type="button" value="Cancel" onClick="javascript:window.location.href='ProjectManagement.do?command=ProjectCenter&section=Details&pid=<%= Project.getId() %>'">
</form>
</body>
