<%--
 Copyright 2000-2004 Matt Rajkowski
 matt.rajkowski@teamelements.com
 http://www.teamelements.com
 This source code cannot be modified, distributed or used without
 permission from Matt Rajkowski
--%>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*" %>
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
<script language="JavaScript">
  function checkForm(form) {
    if (form.dosubmit.value == "false") {
      return true;
    }
    var formTest = true;
    var messageText = "";
    //Check required field
    if (form.role.value == "") {
      messageText += "- Description field is required\r\n";
      formTest = false;
    }
<dhv:evaluate if="<%= Assignment.getId() == -1 %>">
    //Check max indent
    if (form.indent.value > <%= maxIndent %>) {
      messageText += "- Indent level must be between 0 and <%= maxIndent %>\r\n";
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
        messageText += "- Only numbers are allowed in the LOE field\r\n";
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
  <input type="submit" value=" Save " onClick="javascript:this.form.donew.value='false'">
<dhv:evaluate if="<%= Assignment.getId() == -1 %>">
  <input type="submit" value="Save & New" onClick="javascript:this.form.donew.value='true'">
</dhv:evaluate>
  <input type="button" value="Cancel" onClick="javascript:this.form.dosubmit.value='false';<%= (isPopup(request)?"window.close();":"window.location.href='ProjectManagement.do?command=ProjectCenter&section=Assignments&pid=" + Project.getId()  + "&rid=" + String.valueOf(Assignment.getRequirementId()) + "';") %>">
  <br>
  <%= showError(request, "actionError") %>
  <table cellpadding="4" cellspacing="0" width="100%" class="pagedList">
    <tr>
      <th colspan="2">
        <strong><%= Assignment.getId()==-1?"Add":"Update" %> Activity</strong>
      </th>
    </tr>
    <tr class="containerBody">
      <td valign="top" nowrap class="formLabel">Description</td>
      <td valign="top" nowrap>
        <input type="text" name="role" size="57" maxlength="150" value="<%= toHtmlValue(Assignment.getRole()) %>"><font color=red>*</font>
        <%= showAttribute(request, "roleError") %>
      </td>
    </tr>
<dhv:evaluate if="<%= Assignment.getId() == -1 %>">
    <tr>
      <td class="formLabel" nowrap>
        Indent Level
      </td>
      <td>
        <zeroio:spinner name="indent" value="<%= Assignment.getIndent() > -1 ? Assignment.getIndent() : Integer.parseInt(request.getParameter("prevIndent")) %>" min="0" max="<%= maxIndent %>"/>
      </td>
    </tr>
</dhv:evaluate>
    <tr class="containerBody">
      <td class="formLabel">Priority</td>
      <td valign="top">
        <%= PriorityList.getHtmlSelect("priorityId", Assignment.getPriorityId()) %>
      </td>
    </tr>
    <tr class="containerBody">
      <td nowrap class="formLabel">Assigned To</td>
      <td valign="top">
<% 
    TeamMemberList thisTeam = Project.getTeam();
    HtmlSelect team = new HtmlSelect();
    team.addItem(-1, "-- None Selected --");
    Iterator iTeam = thisTeam.iterator();
    while (iTeam.hasNext()) {
      TeamMember thisMember = (TeamMember)iTeam.next();
      team.addItem(thisMember.getUserId(), 
        ((User) thisMember.getUser()).getContact().getNameLastFirst());
    }
%>
        <%= team.getHtml("userAssignedId", Assignment.getUserAssignedId()) %>
        <%= showAttribute(request, "userAssignedIdError") %>
      </td>
    </tr>
    <tr class="containerBody">
      <td nowrap class="formLabel" rowspan="2" valign="top">Status</td>
      <td>
        <%= StatusList.getHtmlSelect("statusId", Assignment.getStatusId()) %><font color="red">*</font>
        <%= showAttribute(request, "statusIdError") %>
      </td>
    </tr>
    <tr class="containerBody">
      <td>
        <%= StatusPercentList.getHtml("percentComplete", Assignment.getPercentComplete()) %>
      </td>
    </tr>
    <tr class="containerBody">
      <td nowrap class="formLabel">Keywords</td>
      <td valign="top">
        <input type="text" name="technology" size="24" maxlength="50" value="<%= toHtmlValue(Assignment.getTechnology()) %>">
      </td>
    </tr>
    <tr class="containerBody">
      <td class="formLabel" valign="top" nowrap>Level of Effort</td>
      <td>
        <table border="0" cellspacing="0" cellpadding="0" class="empty">
          <tr>
            <td align="right">
              Estimated:
            </td>
            <td>
              <input type="text" name="estimatedLoe" size="4" value="<%= Assignment.getEstimatedLoeValue() %>">
              <%= LoeList.getHtmlSelect("estimatedLoeTypeId", Assignment.getEstimatedLoeTypeId()) %>
            </td>
          </tr>
          <tr>
            <td align="right">
              Actual:
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
      <td nowrap class="formLabel">Due Date</td>
      <td valign="top">
        <zeroio:dateSelect form="inputForm" field="dueDate" timestamp="<%= Assignment.getDueDate() %>" />
      </td>
    </tr>
  </table>
  <br>
  <input type="submit" value=" Save " onClick="javascript:this.form.donew.value='false'">
<dhv:evaluate if="<%= Assignment.getId() == -1 %>">
  <input type="submit" value="Save & New" onClick="javascript:this.form.donew.value='true'">
</dhv:evaluate>
  <input type="button" value="Cancel" onClick="javascript:this.form.dosubmit.value='false';<%= (isPopup(request)?"window.close();":"window.location.href='ProjectManagement.do?command=ProjectCenter&section=Assignments&pid=" + Project.getId()  + "&rid=" + String.valueOf(Assignment.getRequirementId()) + "';") %>">
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
