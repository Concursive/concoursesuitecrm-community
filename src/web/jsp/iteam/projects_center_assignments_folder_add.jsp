<%-- 
  - Copyright Notice: (C) 2000-2004 Team Elements, All Rights Reserved.
  - License: This source code cannot be modified, distributed or used without
  -          written permission from Team Elements. This notice must remain in
  -          place.
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
      messageText += "- Name field is required\r\n";
      formTest = false;
    }
<dhv:evaluate if="<%= assignmentFolder.getId() == -1 %>">
    //Check max indent
    if (form.indent.value > <%= maxIndent %>) {
      messageText += "- Indent level must be between 0 and <%= maxIndent %>\r\n";
      formTest = false;
    }
</dhv:evaluate>
<%--
    if (form.description.value == "") {
      messageText += "- Description field is required\r\n";
      formTest = false;
    }
--%>
    if (formTest == false) {
      messageText = "The activity folder form could not be submitted.          \r\nPlease verify the following items:\r\n\r\n" + messageText;
      form.dosubmit.value = "true";
      alert(messageText);
      return false;
    } else {
      return true;
    }
  }
</script>
<form method="POST" name="inputForm" action="ProjectManagementAssignmentsFolder.do?command=SaveFolder&pid=<%= Project.getId() %>&rid=<%= (assignmentFolder.getId() == -1 ? request.getParameter("rid"):String.valueOf(assignmentFolder.getRequirementId())) %>&auto-populate=true<%= (request.getParameter("popup") != null?"&popup=true":"") %>" onSubmit="return checkForm(this);">
  <input type="submit" value=" Save " onClick="javascript:this.form.donew.value='false'">
<dhv:evaluate if="<%= assignmentFolder.getId() == -1 %>">
  <input type="submit" value="Save & New" onClick="javascript:this.form.donew.value='true'">
</dhv:evaluate>
  <input type="submit" value="Cancel" onClick="javascript:this.form.dosubmit.value='false';<%= (request.getParameter("popup") != null?"window.close();":"this.form.action='ProjectManagement.do?command=ProjectCenter&pid=" + Project.getId()  + ("Requirements".equals(request.getParameter("return"))?"&section=Requirements":"&section=Assignments") + "';") %>;"><br />
  <dhv:formMessage />
  <table cellpadding="4" cellspacing="0" width="100%" class="pagedList">
    <tr>
      <th colspan="2">
        <strong><%= assignmentFolder.getId()==-1?"Add":"Update" %> Activity Folder</strong>
      </th>
    </tr>
    <tr class="containerBody">
      <td valign="top" nowrap class="formLabel">Name</td>
      <td valign="top" nowrap>
        <input type="text" name="name" size="57" maxlength="150" value="<%= toHtmlValue(assignmentFolder.getName()) %>"><font color=red>*</font> <%= showAttribute(request, "nameError") %>
      </td>
    </tr>
<dhv:evaluate if="<%= assignmentFolder.getId() == -1 %>">
    <tr>
      <td class="formLabel" nowrap>
        Indent Level
      </td>
      <td>
        <zeroio:spinner name="indent" value="<%= assignmentFolder.getIndent() > -1 ? assignmentFolder.getIndent() : Integer.parseInt(request.getParameter("prevIndent")) %>" min="0" max="<%= maxIndent %>"/>
      </td>
    </tr>
</dhv:evaluate>
    <tr class="containerBody">
      <td nowrap class="formLabel" valign="top">Details</td>
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
  <input type="submit" value=" Save " onClick="javascript:this.form.donew.value='false'">
<dhv:evaluate if="<%= assignmentFolder.getId() == -1 %>">
  <input type="submit" value="Save & New" onClick="javascript:this.form.donew.value='true'">
</dhv:evaluate>
  <input type="submit" value="Cancel" onClick="javascript:this.form.dosubmit.value='false';<%= (request.getParameter("popup") != null?"window.close();":"this.form.action='ProjectManagement.do?command=ProjectCenter&pid=" + Project.getId()  + ("Requirements".equals(request.getParameter("return"))?"&section=Requirements":"&section=Assignments") + "';") %>;">
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
