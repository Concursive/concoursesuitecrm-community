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
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ page import="java.util.*,com.zeroio.iteam.base.*, org.aspcfs.modules.documents.base.* " %>
<jsp:useBean id="documentStore" class="org.aspcfs.modules.documents.base.DocumentStore" scope="request"/>
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
      messageText += label("description.required","- Description is a required field\r\n");;
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
<form method="post" name="inputForm" action="DocumentManagement.do?command=UpdateDocumentStore&auto-populate=true" onSubmit="return checkForm(this);">
<table border="0" cellpadding="1" cellspacing="0" width="100%">
  <tr class="subtab">
    <td>
      <img src="images/icons/stock_macro-objects-16.gif" border="0" align="absmiddle">
      <a href="DocumentManagement.do?command=DocumentStoreCenter&section=Details&documentStoreId=<%= documentStore.getId() %>"><dhv:label name="documents.details.overview">Overview</dhv:label></a> >
      <dhv:label name="documents.details.modifyDocumentStore">Modify Document Store</dhv:label>
    </td>
  </tr>
</table>
<br />
<input type="submit" value="<dhv:label name="documents.modify.update">Update</dhv:label>" />
<input type="button" value="<dhv:label name="documents.modify.cancel">Cancel</dhv:label>" onClick="javascript:window.location.href='DocumentManagement.do?command=DocumentStoreCenter&section=Details&documentStoreId=<%= documentStore.getId() %>'" />
<br />
&nbsp;
<dhv:formMessage />
<table cellpadding="4" cellspacing="0" width="100%" class="pagedList">
  <input type="hidden" name="id" value="<%= documentStore.getId() %>">
  <input type="hidden" name="modified" value="<%= documentStore.getModified() %>">
  <tr>
    <th colspan="2" valign="center">
      <strong><dhv:label name="documents.details.updateDocumentStore">Update Document Store Information</dhv:label></strong>
    </th>
  </tr>
  <tr class="containerBody">
<%
  String documentStoreApprovedCheck = "";
  if (documentStore.getApprovalDate() != null) {
    documentStoreApprovedCheck = " checked";
  }
  String documentStoreClosedCheck = "";
  if (documentStore.getCloseDate() != null) {
    documentStoreClosedCheck = " checked";
  }
%>
    <td valign="top" nowrap class="formLabel">
      <dhv:label name="documents.details.status">Status</dhv:label>
    </td>
    <td>
      <input type="checkbox" name="approved" value="ON"<%= documentStoreApprovedCheck %>>
      <dhv:label name="documents.details.approved">Approved</dhv:label> <zeroio:tz timestamp="<%= documentStore.getApprovalDate() %>"/><br />
      <input type="checkbox" name="closed" value="ON"<%= documentStoreClosedCheck %>>
      <dhv:label name="documents.details.archived">Archived</dhv:label> <zeroio:tz timestamp="<%= documentStore.getCloseDate() %>"/><br />
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="documents.details.title">Title</dhv:label>
    </td>
    <td>
      <input type="text" name="title" size="57" maxlength="100" value="<%= toHtmlValue(documentStore.getTitle()) %>"><font color=red>*</font> <%= showAttribute(request, "titleError") %>
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="documents.details.shortDescription">Short Description</dhv:label>
    </td>
    <td>
      <input type="text" name="shortDescription" size="57" maxlength="200" value="<%= toHtmlValue(documentStore.getShortDescription()) %>"><font color=red>*</font>
      <%= showAttribute(request, "shortDescriptionError") %>
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="documents.details.startDate">Start Date</dhv:label>
    </td>
    <td>
      <zeroio:dateSelect form="inputForm" field="requestDate" timestamp="<%= documentStore.getRequestDate() %>"/>
     <dhv:label name="project.at">at</dhv:label>
      <zeroio:timeSelect baseName="requestDate" value="<%= documentStore.getRequestDate() %>" timeZone="<%= documentStore.getRequestDateTimeZone() %>" showTimeZone="true" />
      <font color="red">*</font>
      <%= showAttribute(request, "requestDateError") %>
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="documents.details.requestedBy">Requested By</dhv:label>
    </td>
    <td>
      <input type="text" name="requestedBy" size="24" maxlength="50" value="<%= toHtmlValue(documentStore.getRequestedBy()) %>">
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="documents.details.organization">Organization</dhv:label>
    </td>
    <td>
      <input type="text" name="requestedDept" size="24" maxlength="50" value="<%= toHtmlValue(documentStore.getRequestedDept()) %>">
    </td>
  </tr>
</table>
<br />
<input type="hidden" name="onlyWarnings" value="<%=(documentStore.getOnlyWarnings()?"on":"off")%>" />
<input type="submit" value="<dhv:label name="documents.modify.update">Update</dhv:label>" />
<input type="button" value="<dhv:label name="documents.modify.cancel">Cancel</dhv:label>" onClick="javascript:window.location.href='DocumentManagement.do?command=DocumentStoreCenter&section=Details&documentStoreId=<%= documentStore.getId() %>'" />
</form>
</body>
