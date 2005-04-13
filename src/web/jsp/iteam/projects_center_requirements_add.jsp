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
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ page import="java.util.*,com.zeroio.iteam.base.*" %>
<jsp:useBean id="Project" class="com.zeroio.iteam.base.Project" scope="request"/>
<jsp:useBean id="Requirement" class="com.zeroio.iteam.base.Requirement" scope="request"/>
<jsp:useBean id="LoeList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<%@ include file="../initPage.jsp" %>
<body onLoad="document.inputForm.shortDescription.focus();">
<script language="JavaScript" type="text/javascript" src="javascript/checkDate.js"></script>
<script language="JavaScript" type="text/javascript" src="javascript/popCalendar.js"></script>
<script language="JavaScript">
  function checkForm(form) {
    if (form.dosubmit.value == "false") {
      return true;
    }
    var formTest = true;
    var messageText = "";
    //Required fields
    if (form.shortDescription.value == "") {    
      messageText += label("title.required","- Title is a required field\r\n");
      formTest = false;
    }
    if (form.description.value == "") {    
      messageText += label("description.required","- Description is a required field\r\n");
      formTest = false;
    }
    
    //Check LOE number field
    var valid = "0123456789.,";
    var ok = true;
    if (form.estimatedLoe.value != "") {
      for (var i=0; i<form.estimatedLoe.value.length; i++) {
        temp = "" + form.estimatedLoe.value.substring(i, i+1);
        if (valid.indexOf(temp) == "-1") {
          ok = false;
        }
      }
      if (ok == false) {
        messageText += label("check.number.loefield","- Only numbers are allowed in the LOE field\r\n");
        formTest = false;
      }
    }
    
    if (formTest == false) {
      messageText = label("check.form","Form could not be saved, please check the following:\r\n\r\n") + messageText;
      form.dosubmit.value = "true";
      alert(messageText);
      return false;
    } else {
      return true;
    }
  }
</script>
<form method="POST" name="inputForm" action="ProjectManagementRequirements.do?command=<%= Requirement.getId() == -1?"Insert":"Update" %>&auto-populate=true" onSubmit="return checkForm(this);">
<table border="0" cellpadding="1" cellspacing="0" width="100%">
  <tr class="subtab">
    <td>
      <img border="0" src="images/icons/stock_list_bullet2-16.gif" align="absmiddle">
      <a href="ProjectManagement.do?command=ProjectCenter&section=Requirements&pid=<%= Project.getId() %>"><dhv:label name="project.outlines">Outlines</dhv:label></a> >
      <% if(Requirement.getId() == -1) {%>
        <dhv:label name="button.add">Add</dhv:label>
      <%} else {%>
        <dhv:label name="button.modify">Modify</dhv:label>
      <%}%>
    </td>
  </tr>
</table>
<br>
  <input type="submit" value="<dhv:label name="global.button.save">Save</dhv:label>">
  <input type="submit" value="<dhv:label name="global.button.cancel">Cancel</dhv:label>" onClick="javascript:this.form.dosubmit.value='false';this.form.action='ProjectManagement.do?command=ProjectCenter&section=Requirements&pid=<%= Project.getId() %>';"><br />
  <dhv:formMessage />
  <table cellpadding="4" cellspacing="0" width="100%" class="pagedList">
    <tr>
      <th colspan="2">
        <strong>
        <% if(Requirement.getId()==-1) {%>
          <dhv:label name="project.addOutline">Add Outline</dhv:label>
        <%} else {%>
        <dhv:label name="project.updateOutline">Update Outline</dhv:label>
        <%}%></strong>
      </th>
    </tr>
    <tr class="containerBody">
      <td nowrap class="formLabel"><dhv:label name="accounts.accounts_contacts_add.Title">Title</dhv:label></td>
      <td>
        <input type="text" name="shortDescription" size="57" maxlength="255" value="<%= toHtmlValue(Requirement.getShortDescription()) %>"><font color=red>*</font> <%= showAttribute(request, "shortDescriptionError") %>
      </td>
    </tr>
    <tr class="containerBody">
      <td nowrap class="formLabel" valign="top"><dhv:label name="contacts.details">Details</dhv:label></td>
      <td>
        <table border="0" cellpadding="0" cellspacing="0" class="empty">
          <tr>
            <td>
              <textarea name="description" cols="55" rows="8"><%= toString(Requirement.getDescription()) %></textarea><br>
              <%= showAttribute(request, "descriptionError") %>
            </td>
            <td valign="top">
              <font color="red">*</font>
            </td>
          </tr>
        </table>
      </td>
    </tr>
    <tr class="containerBody">
      <td nowrap class="formLabel"><dhv:label name="documents.details.requestedBy">Requested By</dhv:label></td>
      <td>
        <input type="text" name="submittedBy" size="24" maxlength="50" value="<%= toHtmlValue(Requirement.getSubmittedBy()) %>">
      </td>
    </tr>
    <tr class="containerBody">
      <td nowrap class="formLabel" valign="top"><dhv:label name="project.deptOrCompany" param="break=<br />">Department or<br />Company</dhv:label></td>
      <td valign="top">
        <input type="text" name="departmentBy" size="24" maxlength="50" value="<%= toHtmlValue(Requirement.getDepartmentBy()) %>">
      </td>
    </tr>
    <tr class="containerBody">
      <td nowrap class="formLabel" valign="top"><dhv:label name="project.expectedDates">Expected Dates</dhv:label></td>
      <td>
        <table border="0" cellspacing="0" cellpadding="0" class="empty">
          <tr>
            <td align="right">
              <dhv:label name="project.start.colon">Start:</dhv:label>
            </td>
            <td>
              <zeroio:dateSelect form="inputForm" field="startDate" timestamp="<%= Requirement.getStartDate() %>" timeZone="<%= Requirement.getStartDateTimeZone() %>" showTimeZone="true" />
              <%=showAttribute(request,"startDateError")%>
            </td>
          </tr>
          <tr>
            <td align="right">
              <dhv:label name="project.finish.colon">Finish:</dhv:label>
            </td>
            <td>
              <zeroio:dateSelect form="inputForm" field="deadline" timestamp="<%= Requirement.getDeadline() %>" timeZone="<%= Requirement.getDeadlineTimeZone() %>" showTimeZone="true" />
              <%= showAttribute(request,"deadlineError") %>
              <%= showWarningAttribute(request, "deadlineWarning") %>
            </td>
          </tr>
        </table>
      </td>
    </tr>
    <tr class="containerBody">
      <td nowrap class="formLabel" valign="top"><dhv:label name="project.levelOfEffort">Level of Effort</dhv:label></td>
      <td>
        <table border="0" cellspacing="0" cellpadding="0" class="empty">
          <tr>
            <td align="right">
              <dhv:label name="project.estimated.colon">Estimated:</dhv:label>
            </td>
            <td>
              <input type="text" name="estimatedLoe" size="4" value="<%= Requirement.getEstimatedLoeValue() %>">
              <%= LoeList.getHtmlSelect("estimatedLoeTypeId", Requirement.getEstimatedLoeTypeId()) %>
            </td>
          </tr>
          <tr>
            <td align="right">
              <dhv:label name="project.actual">Actual:</dhv:label>
            </td>
            <td>
              <input type="text" name="actualLoe" size="4" value="<%= Requirement.getActualLoeValue() %>">
              <%= LoeList.getHtmlSelect("actualLoeTypeId", Requirement.getActualLoeTypeId()) %>
            </td>
          </tr>
        </table>
      </td>
    </tr>
    <tr class="containerBody">
      <td nowrap class="formLabel" valign="top"><dhv:label name="accounts.accountasset_include.Status">Status</dhv:label></td>
      <td>
        <input type="checkbox" name="approved" value="ON" <%= (Requirement.getApproved()?"checked":"") %>>
        <dhv:label name="project.outlineApproved">Outline Approved</dhv:label>
        <br>
        <input type="checkbox" name="closed" value="ON" <%= (Requirement.getClosed()?"checked":"") %>>
        <dhv:label name="project.outlineClosed">Outline Closed</dhv:label>
      </td>
    </tr>    
  </table>
  <br>
  <input type="submit" value="<dhv:label name="global.button.save">Save</dhv:label>">
  <input type="submit" value="<dhv:label name="global.button.cancel">Cancel</dhv:label>" onClick="javascript:this.form.dosubmit.value='false';this.form.action='ProjectManagement.do?command=ProjectCenter&section=Requirements&pid=<%= Project.getId() %>';">
  <input type="hidden" name="id" value="<%= Requirement.getId() %>">
  <input type="hidden" name="pid" value="<%= Project.getId() %>">
  <input type="hidden" name="projectId" value="<%= Project.getId() %>">
  <input type="hidden" name="modified" value="<%= Requirement.getModifiedString() %>">
  <input type="hidden" name="dosubmit" value="true">
</form>
</body>
