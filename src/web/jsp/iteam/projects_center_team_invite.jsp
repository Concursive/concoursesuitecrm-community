<%-- 
  - Copyright Notice: (C) 2000-2004 Team Elements, All Rights Reserved.
  - License: This source code cannot be modified, distributed or used without
  -          written permission from Team Elements. This notice must remain in
  -          place.
  - Author(s): Matt Rajkowski
  - Version: $Id$
  - Description: 
  --%>
<%@ page import="java.util.*,com.zeroio.iteam.base.*,
                 org.aspcfs.modules.admin.base.User" %>
<jsp:useBean id="Project" class="com.zeroio.iteam.base.Project" scope="request"/>
<jsp:useBean id="TeamMemberList" class="com.zeroio.iteam.base.TeamMemberList" scope="request"/>
<%@ include file="../initPage.jsp" %>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/checkEmail.js"></script>
<script language="JavaScript">
  function checkForm(form) {
    var emailTest = true;
    var nameFirstTest = true;
    var nameLastTest = true;
    //Check required fields
    for (var i = 1; i < <%= TeamMemberList.size() + 1 %>; i++) {
      if (form.elements['check' + i].checked) {
        if (form.elements['email' + i].value == "" || !checkEmail(form.elements['email' + i].value)) {
          emailTest = false;
        }
        if (form.elements['firstName' + i].value == "") {
          nameFirstTest = false;
        }
        if (form.elements['lastName' + i].value == "") {
          nameLastTest = false;
        }
      }
    }
    var messageText = "";
    var formTest = true;
    if (emailTest == false) {
      messageText += "- Valid Email Address is a required field\r\n";
      formTest = false;
    }
    if (nameFirstTest == false) {
      messageText += "- First Name is a required field\r\n";
      formTest = false;
    }
    if (nameLastTest == false) {
      messageText += "- Last Name is a required field\r\n";
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
<table border="0" cellpadding="1" cellspacing="0" width="100%">
  <tr class="subtab">
    <td>
      <img src="images/icons/stock_new-bcard-16.gif" border="0" align="absmiddle">
      <a href="ProjectManagement.do?command=ProjectCenter&section=Team&pid=<%= Project.getId() %>">Team</a> >
      <a href="ProjectManagementTeam.do?command=Modify&pid=<%= Project.getId() %>">Modify</a> >
      Invite
    </td>
  </tr>
</table>
<br>
<form name="projectMemberForm" method="post" action="ProjectManagementTeam.do?command=SendInvitations&pid=<%= Project.getId() %>" onSubmit="return checkForm(this);">
The following user<%= TeamMemberList.size() == 1 ? "" : "s" %> could not be added to the project because an account with this system is required.
You can send an invitation by filling out the following fields and ensuring the invite field is checked.
If so, an email will be sent from you with information about becoming a member.  Once the request is
accepted, the user will appear normally in the project team.<br>
<br>
<table cellpadding="4" cellspacing="0" width="100%" class="pagedList">
  <tr>
    <th width="10">
      Invite
    </th>
    <th>
      Email Address
    </th>
    <th>
      First Name
    </th>
    <th>
      Last Name
    </th>
  </tr>
<%
  int rowId = 0;
  int count = 0;
  Iterator i = TeamMemberList.iterator();
  while (i.hasNext()) {
    rowId = (rowId != 1?1:2);
    ++count;
    TeamMember thisMember = (TeamMember) i.next();
    User thisContact = (User) thisMember.getUser();
    if (thisContact == null) thisContact = new User();
%>
  <%-- This file is not in use, so the following errors have been commented out --%>
  <tr class="row<%= rowId %>">
    <td align="center">
      <input type="hidden" name="count<%= count %>" value="true" />
      <input type="checkbox" name="check<%= count %>" value="ON" checked />
    </td>
    <%--
    <td nowrap>
      <input type="text" name="email<%= count %>" maxlength="255" value="<%= toHtmlValue(thisContact.getEmail()) %>"/>
      <font color="red">*</font>
    </td>
    <td nowrap>
      <input type="text" name="firstName<%= count %>" maxlength="50" value="<%= toHtmlValue(thisContact.getFirstName()) %>"/>
      <font color="red">*</font>
    </td>
    <td nowrap width="100%">
      <input type="text" name="lastName<%= count %>" maxlength="50" value="<%= toHtmlValue(thisContact.getLastName()) %>"/>
      <font color="red">*</font>
    </td>
    --%>
  </tr>
<% } %>
</table>
<br>
<input type="submit" value="Invite!"/>
<input type="button" value="Cancel" onclick="javascript:window.location.href='ProjectManagement.do?command=ProjectCenter&section=Team&pid=<%= Project.getId() %>'"/>
</form>
