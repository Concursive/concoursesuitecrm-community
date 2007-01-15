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
  - Author(s): 
  - Version: $Id$
  - Description: 
  --%>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,com.zeroio.iteam.base.*" %>
<jsp:useBean id="documentStore" class="org.aspcfs.modules.documents.base.DocumentStore" scope="request"/>
<jsp:useBean id="categoryList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
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
      messageText = label("check.form","The form could not be submitted.          \r\nPlease verify the following items:\r\n\r\n") + messageText;
      alert(messageText);
      return false;
    } else {
      return true;
    }
  }
</script>
<body onLoad="document.inputForm.title.focus()">
<form method="post" name="inputForm" action="DocumentManagement.do?command=InsertDocumentStore&auto-populate=true" onSubmit="return checkForm(this);">
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="DocumentManagement.do"><dhv:label name="Documents" mainMenuItem="true">Documents</dhv:label></a> >
<dhv:label name="Add" subMenuItem="true">Add</dhv:label>
</td>
</tr>
</table>
<%-- End trails --%>
<dhv:formMessage showSpace="false" />
<table cellpadding="4" cellspacing="0" width="100%" class="pagedList">
  <tr>
    <th colspan="2">
      <strong>
        <dhv:label name="documents.add.newDocumentStoreInformation">New Document Store Information</dhv:label>
      </strong>
    </th>
  </tr>
  <tr>
    <td nowrap class="formLabel">
      <dhv:label name="documents.details.title">Title</dhv:label>
    </td>
    <td>
      <input type="text" name="title" size="57" maxlength="100" value="<%= toHtmlValue(documentStore.getTitle()) %>"><font color=red>*</font> <%= showAttribute(request, "titleError") %>
    </td>
  </tr>
  <tr>
    <td nowrap class="formLabel">
      <dhv:label name="documents.details.shortDescription">Short Description</dhv:label>
    </td>
    <td>
      <input type="text" name="shortDescription" size="57" maxlength="200" value="<%= toHtmlValue(documentStore.getShortDescription()) %>"><font color=red>*</font> <%= showAttribute(request, "shortDescriptionError") %>
    </td>
  </tr>
  <tr>
    <td nowrap class="formLabel">
      <dhv:label name="documents.details.startDate">Start Date</dhv:label>
    </td>
    <td>
      <zeroio:dateSelect form="inputForm" field="requestDate" timestamp="<%= documentStore.getRequestDate() %>" />
     <dhv:label name="project.at">at</dhv:label>
      <zeroio:timeSelect baseName="requestDate" value="<%= documentStore.getRequestDate() %>" timeZone="<%= documentStore.getRequestDateTimeZone() %>" showTimeZone="true" />
      <font color="red">*</font>
      <%= showAttribute(request, "requestDateError") %>
    </td>
  </tr>
  <tr>
    <td nowrap class="formLabel">
      <dhv:label name="documents.details.requestedBy">Requested By</dhv:label>
    </td>
    <td>
      <input type="text" name="requestedBy" size="24" maxlength="50" value="<%= toHtmlValue(documentStore.getRequestedBy()) %>">
    </td>
  </tr>
  <tr>
    <td nowrap class="formLabel">
      <dhv:label name="documents.details.organization">Organization</dhv:label>
    </td>
    <td>
      <input type="text" name="requestedDept" size="24" maxlength="50" value="<%= toHtmlValue(documentStore.getRequestedDept()) %>">
    </td>
  </tr>
<%
  String documentStoreApprovedCheck = "";
  String documentStoreClosedCheck = "";
%>
  <tr>
    <td nowrap class="formLabel">
      <dhv:label name="documents.details.status">Status</dhv:label>
    </td>
    <td>
      <input type="checkbox" name="approved" value="ON"<%= documentStoreApprovedCheck %>>
      <dhv:label name="documents.details.approved">Approved</dhv:label> <zeroio:tz timestamp="<%= documentStore.getApprovalDate() %>"/><br />
    </td>
  </tr>
</table>
<br />
<input type="hidden" name="onlyWarnings" value='<%=(documentStore.getOnlyWarnings()?"on":"off")%>' />
<input type="hidden" name="showDetails" value="true" />
<input type="hidden" name="showTeam" value="true" />
<input type="hidden" name="showDocuments" value="true" />
<input type="submit" value="<dhv:label name="documents.add.save">Save</dhv:label>" />
<input type="button" value="<dhv:label name="documents.add.cancel">Cancel</dhv:label>" onClick="javascript:window.location.href='DocumentManagement.do?'" />
</form>
</body>
