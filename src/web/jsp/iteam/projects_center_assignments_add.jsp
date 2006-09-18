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
<%@ page import="java.util.*,
                 java.sql.Timestamp" %>
<%@ page import="com.zeroio.iteam.base.*" %>
<%@ page import="org.aspcfs.utils.web.*" %>
<%@ page import="org.aspcfs.modules.contacts.base.*" %>
<%@ page import="org.aspcfs.modules.admin.base.User" %>
<jsp:useBean id="Project" class="com.zeroio.iteam.base.Project" scope="request"/>
<jsp:useBean id="Assignment" class="com.zeroio.iteam.base.Assignment" scope="request"/>
<jsp:useBean id="PriorityList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="StatusList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="StatusPercentList" class="com.zeroio.iteam.base.HtmlPercentList" scope="request"/>
<jsp:useBean id="LoeList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<%@ include file="../initPage.jsp" %>
<%
  String onLoad = "";
  if ("true".equals(request.getParameter("donew"))) {
    onLoad = "window.opener.scrollReload('ProjectManagement.do?command=ProjectCenter&section=Assignments&pid=" + Project.getId() + "&rid=" +  (Assignment.getId() == -1?request.getParameter("rid"):String.valueOf(Assignment.getRequirementId())) + "')";
  }
  //Only evaluate on an insert
  int maxIndent = 0;
  if (Assignment.getId() == -1) {
    maxIndent = (Assignment.getPrevIndent() > -1 ? Assignment.getPrevIndent() + 1 : Integer.parseInt(request.getParameter("prevIndent")) + 1);
  }
%>
<body onLoad="document.inputForm.role.focus();<%= onLoad %>">
<script language="JavaScript" type="text/javascript" src="javascript/checkDate.js"></script>
<script language="JavaScript" type="text/javascript" src="javascript/popCalendar.js"></script>
<script language="JavaScript" type="text/javascript" src="javascript/popURL.js"></script>
<script language="JavaScript">
  function checkForm(form) {
    if (form.dosubmit.value == "false") {
      return true;
    }
    var formTest = true;
    var messageText = "";
    //Check required field
    if (form.role.value == "") {
      messageText += label("description.required","- Description field is required\r\n");
      formTest = false;
    }
<dhv:evaluate if="<%= Assignment.getId() == -1 %>">
    //Check max indent
    if (form.indent.value > <%= maxIndent %>) {
      messageText += label("check.indentlevel.between","- Indent level must be between 0 and ")+"<%= maxIndent %>\r\n";
      formTest = false;
    }
</dhv:evaluate>
    //Check number field
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
        messageText += label("check.number.loefield","- Only numbers are allowed in the LOE field\r\n");
        formTest = false;
      }
    }
    //Check date field
    if ((form.dueDate.value != "") && (!checkDate(form.dueDate.value))) {
      messageText += "- Due date was not properly entered\r\n";
      formTest = false;
    }
    if (formTest == false) {
      messageText = "The activity form could not be submitted.          \r\nPlease verify the following items:\r\n\r\n" + messageText;
      form.dosubmit.value = "true";
      alert(messageText);
      return false;
    } else {
      return true;
    }
  }
</script>
<form method="POST" name="inputForm" action="ProjectManagementAssignments.do?command=Save&pid=<%= Project.getId() %>&rid=<%= (Assignment.getId() == -1?request.getParameter("rid"):String.valueOf(Assignment.getRequirementId())) %>&auto-populate=true<%= (request.getParameter("popup") != null?"&popup=true":"") %>" onSubmit="return checkForm(this);">
  <dhv:formMessage showSpace="false"  />
  <table cellpadding="4" cellspacing="0" width="100%" class="pagedList">
    <tr>
      <th colspan="2">
        <strong>
        <% if(Assignment.getId()==-1) { %>
          <dhv:label name="projects.center_assignments.AddAnActivity">Add an Activity</dhv:label>
        <%} else {%>
          <dhv:label name="accounts.accounts_contacts_calls_modify.UpdateActivity">Update Activity</dhv:label>
        <%}%></strong>
      </th>
    </tr>
    <tr class="containerBody">
      <td valign="top" nowrap class="formLabel"><dhv:label name="accounts.accountasset_include.Description">Description</dhv:label></td>
      <td valign="top" nowrap>
        <input type="text" name="role" size="57" maxlength="150" value="<%= toHtmlValue(Assignment.getRole()) %>"><font color=red>*</font>
        <%= showAttribute(request, "roleError") %>
      </td>
    </tr>
<dhv:evaluate if="<%= Assignment.getId() == -1 %>">
<%-- Temp. fix for Weblogic --%>
<%
int assignmentIndent = Assignment.getIndent() > -1 ? Assignment.getIndent() : Integer.parseInt(request.getParameter("prevIndent"));
%>
    <tr>
      <td class="formLabel" nowrap>
        <dhv:label name="project.indentLevel">Indent Level</dhv:label>
      </td>
      <td>
        <zeroio:spinner name="indent" value="<%= assignmentIndent %>" min="0" max="<%= maxIndent %>"/>
      </td>
    </tr>
</dhv:evaluate>
    <tr class="containerBody">
      <td class="formLabel"><dhv:label name="accounts.accounts_contacts_calls_details_followup_include.Priority">Priority</dhv:label></td>
      <td valign="top">
        <%= PriorityList.getHtmlSelect("priorityId", Assignment.getPriorityId()) %>
      </td>
    </tr>
    <tr class="containerBody">
      <td nowrap class="formLabel"><dhv:label name="project.keywords">Keywords</dhv:label></td>
      <td valign="top">
        <input type="text" name="technology" size="24" maxlength="50" value="<%= toHtmlValue(Assignment.getTechnology()) %>">
      </td>
    </tr>
  </table>
  <br />
  <table cellpadding="4" cellspacing="0" width="100%" class="pagedList">
    <tr>
      <th colspan="2">
        Assignment
      </th>
    </tr>
    <tr class="containerBody">
      <td nowrap class="formLabel"><dhv:label name="accounts.accounts_contacts_calls_list.AssignedTo">Assigned To</dhv:label></td>
      <td valign="top">
<% 
    TeamMemberList thisTeam = Project.getTeam();
    HtmlSelect team = new HtmlSelect();
    team.addItem(-1, "-- None Selected --");
    Iterator iTeam = thisTeam.iterator();
    while (iTeam.hasNext()) {
      TeamMember thisMember = (TeamMember)iTeam.next();
      User thisUser = (User)thisMember.getUser();
      if ((thisUser.getEnabled() || (!thisUser.getEnabled() && Assignment.getUserAssignedId() == thisUser.getId())) && 
          !(thisUser.getExpires() != null && thisUser.getExpires().before(new Timestamp(Calendar.getInstance().getTimeInMillis())))) {
        team.addItem(thisMember.getUserId(), 
          ((User) thisMember.getUser()).getContact().getNameLastFirst() + (thisUser.getEnabled()?"":" *"));
      }
    }
%>
        <%= team.getHtml("userAssignedId", Assignment.getUserAssignedId()) %>
        <%= showAttribute(request, "userAssignedIdError") %>
      </td>
    </tr>
    <tr class="containerBody">
      <td class="formLabel" valign="top" nowrap>
        <dhv:label name="project.levelOfEffort">Level of Effort</dhv:label>
      </td>
      <td>
        <table border="0" cellspacing="0" cellpadding="0" class="empty">
          <tr>
            <td align="right">
              <dhv:label name="project.estimated.colon">Estimated:</dhv:label>
            </td>
            <td>
              <input type="text" name="estimatedLoe" size="4" value="<%= Assignment.getEstimatedLoeValue() %>">
              <%= LoeList.getHtmlSelect("estimatedLoeTypeId", Assignment.getEstimatedLoeTypeId()) %>
            </td>
          </tr>
          <tr>
            <td align="right">
              <dhv:label name="project.actual">Actual:</dhv:label>
            </td>
            <td>
              <input type="text" name="actualLoe" size="4" value="<%= Assignment.getActualLoeValue() %>">
              <%= LoeList.getHtmlSelect("actualLoeTypeId", Assignment.getActualLoeTypeId()) %>
            </td>
          </tr>
        </table>
      </td>
    </tr>
    <tr class="containerBody">
      <td nowrap class="formLabel">Start Date</td>
      <td valign="top">
        <zeroio:dateSelect form="inputForm" field="estStartDate" timestamp="<%= Assignment.getEstStartDate() %>" timeZone="<%= Assignment.getDueDateTimeZone() %>" />
        <%= showAttribute(request, "estStartDateError") %>
      </td>
    </tr>
    <tr class="containerBody">
      <td nowrap class="formLabel"><dhv:label name="accounts.accounts_calls_list.DueDate">Due Date</dhv:label></td>
      <td valign="top">
        <zeroio:dateSelect form="inputForm" field="dueDate" timestamp="<%= Assignment.getDueDate() %>" timeZone="<%= Assignment.getDueDateTimeZone() %>" showTimeZone="true" />
        <%= showAttribute(request, "dueDateError") %>
      </td>
    </tr>
  </table>
  <br />
  <table cellpadding="4" cellspacing="0" width="100%" class="pagedList">
    <tr>
      <th colspan="2">
        <dhv:label name="project.progress">Progress</dhv:label>
      </th>
    </tr>
    <tr class="containerBody">
      <td nowrap class="formLabel"><dhv:label name="accounts.accountasset_include.Status">Status</dhv:label></td>
      <td>
        <%= StatusList.getHtmlSelect("statusId", Assignment.getStatusId()) %>
        <%= StatusPercentList.getHtml("percentComplete", Assignment.getPercentComplete()) %>
        <%= showAttribute(request, "statusIdError") %>
      </td>
    </tr>
    <tr class="containerBody">
      <td class="formLabel" valign="top">Additional Note</td>
      <td>
        <table border="0" class="empty">
          <tr>
            <td>
              <textarea name="additionalNote" cols="55" rows="2"><%= toString(Assignment.getAdditionalNote()) %></textarea>
            </td>
            <td valign="top">
              <a href="javascript:popURL('ProjectManagementAssignments.do?command=ShowNotes&pid=<%= Assignment.getProjectId() %>&aid=<%= Assignment.getId() %>&popup=true','ITEAM_Assignment_Notes','400','500','yes','yes');"><dhv:evaluate if="<%= Assignment.hasNotes() %>"><img src="images/icons/stock_insert-note-16.gif" border="0" align="absmiddle" alt="Review all notes"/></dhv:evaluate><dhv:evaluate if="<%= !Assignment.hasNotes() %>"><img src="images/icons/stock_insert-note-gray-16.gif" border="0" align="absmiddle" alt="Review all notes"/></dhv:evaluate></a>
              <%= Assignment.getNoteCount() %>
            </td>
          </tr>
        </table>
      </td>
    </tr>
  </table>
  <br />
  <input type="submit" value="<dhv:label name="global.button.save">Save</dhv:label>" onClick="javascript:this.form.donew.value='false'">
<dhv:evaluate if="<%= Assignment.getId() == -1 %>">
  <input type="submit" value="<dhv:label name="button.saveAndNew">Save & New</dhv:label>" onClick="javascript:this.form.donew.value='true'">
</dhv:evaluate>
  <input type="button" value="<dhv:label name="global.button.cancel">Cancel</dhv:label>" onClick="javascript:this.form.dosubmit.value='false';<%= (isPopup(request)?"window.close();":"window.location.href='ProjectManagement.do?command=ProjectCenter&section=Assignments&pid=" + Project.getId()  + "&rid=" + String.valueOf(Assignment.getRequirementId()) + "';") %>">
  <input type="hidden" name="id" value="<%= Assignment.getId() %>">
  <input type="hidden" name="folderId" value="<%= (Assignment.getId() == -1?request.getParameter("folderId"):String.valueOf(Assignment.getFolderId())) %>">
  <input type="hidden" name="projectId" value="<%= Project.getId() %>">
  <input type="hidden" name="requirementId" value="<%= (Assignment.getId() == -1?request.getParameter("rid"):String.valueOf(Assignment.getRequirementId())) %>">
  <input type="hidden" name="modified" value="<%= Assignment.getModifiedString() %>">
  <input type="hidden" name="dosubmit" value="true">
  <input type="hidden" name="donew" value="false">
  <input type="hidden" name="return" value="<%= request.getAttribute("return") %>">
  <input type="hidden" name="param" value="<%= Project.getId() %>">
  <input type="hidden" name="param2" value="<%= (Assignment.getId() == -1?request.getParameter("rid"):String.valueOf(Assignment.getRequirementId())) %>">
  <input type="hidden" name="prevIndent" value="<%= Assignment.getPrevIndent() > -1 ? String.valueOf(Assignment.getPrevIndent()) : request.getParameter("prevIndent") %>">
  <input type="hidden" name="prevMapId" value="<%= Assignment.getPrevMapId() > -1 ? String.valueOf(Assignment.getPrevMapId()) : request.getParameter("prevMapId") %>">
</form>
</body>
