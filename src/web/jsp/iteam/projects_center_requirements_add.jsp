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
      messageText += "- Title is a required field\r\n";
      formTest = false;
    }
    if (form.description.value == "") {    
      messageText += "- Description is a required field\r\n";
      formTest = false;
    }
    
    //Check LOE number field
    var valid = "0123456789.";
    var ok = true;
    if (form.estimatedLoe.value != "") {
      for (var i=0; i<form.estimatedLoe.value.length; i++) {
        temp = "" + form.estimatedLoe.value.substring(i, i+1);
        if (valid.indexOf(temp) == "-1") {
          ok = false;
        }
      }
      if (ok == false) {
        messageText += "- Only numbers are allowed in the LOE field\r\n";
        formTest = false;
      }
    }
    
    if (formTest == false) {
      messageText = "The outline form could not be submitted.          \r\nPlease verify the following items:\r\n\r\n" + messageText;
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
      <a href="ProjectManagement.do?command=ProjectCenter&section=Requirements&pid=<%= Project.getId() %>">Outlines</a> >
      <%= Requirement.getId() == -1?"Add":"Modify" %>
    </td>
  </tr>
</table>
<br>
  <input type="submit" value=" Save ">
  <input type="submit" value="Cancel" onClick="javascript:this.form.dosubmit.value='false';this.form.action='ProjectManagement.do?command=ProjectCenter&section=Requirements&pid=<%= Project.getId() %>';"><br />
  <dhv:formMessage />
  <table cellpadding="4" cellspacing="0" width="100%" class="pagedList">
    <tr>
      <th colspan="2">
        <strong><%= Requirement.getId()==-1?"Add":"Update" %> Outline</strong>
      </th>
    </tr>
    <tr class="containerBody">
      <td nowrap class="formLabel">Title</td>
      <td>
        <input type="text" name="shortDescription" size="57" maxlength="255" value="<%= toHtmlValue(Requirement.getShortDescription()) %>"><font color=red>*</font> <%= showAttribute(request, "shortDescriptionError") %>
      </td>
    </tr>
    <tr class="containerBody">
      <td nowrap class="formLabel" valign="top">Details</td>
      <td>
        <table border="0" cellpadding="0" cellspacing="0" class="empty">
          <tr>
            <td>
              <textarea rows="8" name="description" cols="80"><%= toString(Requirement.getDescription()) %></textarea><br>
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
      <td nowrap class="formLabel">Requested By</td>
      <td>
        <input type="text" name="submittedBy" size="24" maxlength="50" value="<%= toHtmlValue(Requirement.getSubmittedBy()) %>">
      </td>
    </tr>
    <tr class="containerBody">
      <td nowrap class="formLabel" valign="top">Department or<br>Company</td>
      <td valign="top">
        <input type="text" name="departmentBy" size="24" maxlength="50" value="<%= toHtmlValue(Requirement.getDepartmentBy()) %>">
      </td>
    </tr>
    <tr class="containerBody">
      <td nowrap class="formLabel" valign="top">Expected Dates</td>
      <td>
        <table border="0" cellspacing="0" cellpadding="0" class="empty">
          <tr>
            <td align="right">
              Start:
            </td>
            <td>
              <zeroio:dateSelect form="inputForm" field="startDate" timestamp="<%= Requirement.getStartDate() %>" timeZone="<%= Requirement.getStartDateTimeZone() %>" showTimeZone="yes" />
              <%=showAttribute(request,"startDateError")%>
            </td>
          </tr>
          <tr>
            <td align="right">
              Finish:
            </td>
            <td>
              <zeroio:dateSelect form="inputForm" field="deadline" timestamp="<%= Requirement.getDeadline() %>" timeZone="<%= Requirement.getDeadlineTimeZone() %>" showTimeZone="yes" />
              <%=showAttribute(request,"deadlineError")%>
            </td>
          </tr>
        </table>
      </td>
    </tr>
    <tr class="containerBody">
      <td nowrap class="formLabel" valign="top">Level of Effort</td>
      <td>
        <table border="0" cellspacing="0" cellpadding="0" class="empty">
          <tr>
            <td align="right">
              Estimated:
            </td>
            <td>
              <input type="text" name="estimatedLoe" size="4" value="<%= Requirement.getEstimatedLoeValue() %>">
              <%= LoeList.getHtmlSelect("estimatedLoeTypeId", Requirement.getEstimatedLoeTypeId()) %>
            </td>
          </tr>
          <tr>
            <td align="right">
              Actual:
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
      <td nowrap class="formLabel" valign="top">Status</td>
      <td>
        <input type="checkbox" name="approved" value="ON" <%= (Requirement.getApproved()?"checked":"") %>>
        Outline Approved
        <br>
        <input type="checkbox" name="closed" value="ON" <%= (Requirement.getClosed()?"checked":"") %>>
        Outline Closed
      </td>
    </tr>    
  </table>
  <br>
  <input type="submit" value=" Save ">
  <input type="submit" value="Cancel" onClick="javascript:this.form.dosubmit.value='false';this.form.action='ProjectManagement.do?command=ProjectCenter&section=Requirements&pid=<%= Project.getId() %>';">
  <input type="hidden" name="id" value="<%= Requirement.getId() %>">
  <input type="hidden" name="pid" value="<%= Project.getId() %>">
  <input type="hidden" name="projectId" value="<%= Project.getId() %>">
  <input type="hidden" name="modified" value="<%= Requirement.getModifiedString() %>">
  <input type="hidden" name="dosubmit" value="true">
</form>
</body>
