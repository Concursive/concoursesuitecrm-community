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
<%@ page import="java.util.*" %>
<%@ page import="com.zeroio.iteam.base.*" %>
<%@ page import="org.aspcfs.utils.web.*" %>
<jsp:useBean id="Project" class="com.zeroio.iteam.base.Project" scope="request"/>
<jsp:useBean id="assignmentFolder" class="com.zeroio.iteam.base.AssignmentFolder" scope="request"/>
<%@ include file="../initPage.jsp" %>
<%
  String onLoad = "";
  if ("true".equals(request.getParameter("donew"))) {
    onLoad = "window.opener.scrollReload('ProjectManagement.do?command=ProjectCenter&section=Assignments&pid=" + Project.getId() + "&rid=" + (assignmentFolder.getId() == -1 ? request.getParameter("rid"):String.valueOf(assignmentFolder.getRequirementId())) + "')";
  }
  //Only evaluate on an insert
  int maxIndent = 0;
  if (assignmentFolder.getId() == -1) {
    maxIndent = (assignmentFolder.getPrevIndent() > -1 ? assignmentFolder.getPrevIndent() + 1 : Integer.parseInt(request.getParameter("prevIndent")) + 1);
  }
%>
<body onLoad="document.inputForm.name.focus();<%= onLoad %>">
<script language="JavaScript" type="text/javascript" src="javascript/checkDate.js"></script>
<script language="JavaScript" type="text/javascript" src="javascript/popCalendar.js"></script>
<script language="JavaScript">
  function checkForm(form) {
    if (form.dosubmit.value == "false") {
      return true;
    }
    var formTest = true;
    var messageText = "";
    
    //Check required field
    if (form.name.value == "") {
      messageText += label("name.required","- Name is a required field.\r\n");
      formTest = false;
    }
<dhv:evaluate if="<%= assignmentFolder.getId() == -1 %>">
    //Check max indent
    if (form.indent.value > <%= maxIndent %>) {
      messageText += label("check.indentlevel.between","- Indent level must be between 0 and ")+"<%= maxIndent %>\r\n";
      formTest = false;
    }
</dhv:evaluate>
<%--
    if (form.description.value == "") {
      messageText += label("description.required","- Description field is required\r\n");
      formTest = false;
    }
--%>
    if (formTest == false) {
      messageText = label("check.activity.folder.items","The activity folder form could not be submitted.          \r\nPlease verify the following items:\r\n\r\n") + messageText;
      form.dosubmit.value = "true";
      alert(messageText);
      return false;
    } else {
      return true;
    }
  }
</script>
<form method="POST" name="inputForm" action="ProjectManagementAssignmentsFolder.do?command=SaveFolder&pid=<%= Project.getId() %>&rid=<%= (assignmentFolder.getId() == -1 ? request.getParameter("rid"):String.valueOf(assignmentFolder.getRequirementId())) %>&auto-populate=true<%= (request.getParameter("popup") != null?"&popup=true":"") %>" onSubmit="return checkForm(this);">
  <dhv:formMessage />
  <table cellpadding="4" cellspacing="0" width="100%" class="pagedList">
    <tr>
      <th colspan="2">
        <strong><% if(assignmentFolder.getId()==-1) {%>
        <dhv:label name="project.addActivityFolder">Add Activity Folder</dhv:label>
        <%} else {%>
        <dhv:label name="project.updateActivityFolder">Update Activity Folder</dhv:label>
        <%}%></strong>
      </th>
    </tr>
    <tr class="containerBody">
      <td valign="top" nowrap class="formLabel"><dhv:label name="contacts.name">Name</dhv:label></td>
      <td valign="top" nowrap>
        <input type="text" name="name" size="57" maxlength="150" value="<%= toHtmlValue(assignmentFolder.getName()) %>"><font color=red>*</font> <%= showAttribute(request, "nameError") %>
      </td>
    </tr>
<dhv:evaluate if="<%= assignmentFolder.getId() == -1 %>">
<%-- Temp. fix for Weblogic --%>
<%
int assignmentFolderIndent = assignmentFolder.getIndent() > -1 ? assignmentFolder.getIndent() : Integer.parseInt(request.getParameter("prevIndent"));
%>
    <tr>
      <td class="formLabel" nowrap>
        <dhv:label name="project.indentLevel">Indent Level</dhv:label>
      </td>
      <td>
        <zeroio:spinner name="indent" value="<%= assignmentFolderIndent %>" min="0" max="<%= maxIndent %>"/>
      </td>
    </tr>
</dhv:evaluate>
    <tr class="containerBody">
      <td nowrap class="formLabel" valign="top"><dhv:label name="contacts.details">Details</dhv:label></td>
      <td>
        <table border="0" cellpadding="0" cellspacing="0" class="empty">
          <tr>
            <td>
              <textarea rows="8" name="description" cols="55"><%= toString(assignmentFolder.getDescription()) %></textarea><br>
              <%= showAttribute(request, "descriptionError") %>
            </td>
            <td valign="top">
              &nbsp;<%--<font color="red">*</font>--%>
            </td>
          </tr>
        </table>
      </td>
    </tr>
  </table>
  <br>
  <input type="submit" value="<dhv:label name="global.button.save">Save</dhv:label>" onClick="javascript:this.form.donew.value='false'">
<dhv:evaluate if="<%= assignmentFolder.getId() == -1 %>">
  <input type="submit" value="<dhv:label name="button.saveAndNew">Save & New</dhv:label>" onClick="javascript:this.form.donew.value='true'">
</dhv:evaluate>
  <input type="submit" value="<dhv:label name="global.button.cancel">Cancel</dhv:label>" onClick="javascript:this.form.dosubmit.value='false';<%= (request.getParameter("popup") != null?"window.close();":"this.form.action='ProjectManagement.do?command=ProjectCenter&pid=" + Project.getId()  + ("Requirements".equals(request.getParameter("return"))?"&section=Requirements":"&section=Assignments") + "';") %>;">
  <input type="hidden" name="id" value="<%= assignmentFolder.getId() %>">
  <input type="hidden" name="parentId" value="<%= (assignmentFolder.getId() == -1 ? request.getParameter("parentId"):String.valueOf(assignmentFolder.getParentId())) %>">
  <input type="hidden" name="projectId" value="<%= Project.getId() %>">
  <input type="hidden" name="requirementId" value="<%= (assignmentFolder.getId() == -1 ? request.getParameter("rid"):String.valueOf(assignmentFolder.getRequirementId())) %>">
  <input type="hidden" name="modified" value="<%= assignmentFolder.getModifiedString() %>">
  <input type="hidden" name="dosubmit" value="true">
  <input type="hidden" name="donew" value="false">
  <input type="hidden" name="return" value="<%= request.getAttribute("return") %>">
  <input type="hidden" name="param" value="<%= Project.getId() %>">
  <input type="hidden" name="param2" value="<%= (assignmentFolder.getId() == -1 ? request.getParameter("rid"):String.valueOf(assignmentFolder.getRequirementId())) %>">
  <input type="hidden" name="prevIndent" value="<%= assignmentFolder.getPrevIndent() > -1 ? String.valueOf(assignmentFolder.getPrevIndent()) : request.getParameter("prevIndent") %>">
  <input type="hidden" name="prevMapId" value="<%= assignmentFolder.getPrevMapId() > -1 ? String.valueOf(assignmentFolder.getPrevMapId()) : request.getParameter("prevMapId") %>">
</form>
</body>
