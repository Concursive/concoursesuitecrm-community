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
  - Version: $Id$
  - Description: 
  --%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ page import="java.text.DateFormat"%>
<jsp:useBean id="UserRecord" class="org.aspcfs.modules.admin.base.User" scope="request"/>
<jsp:useBean id="RoleList" class="org.aspcfs.modules.admin.base.RoleList" scope="request"/>
<jsp:useBean id="UserList" class="org.aspcfs.modules.admin.base.UserList" scope="request"/>
<jsp:useBean id="APP_SIZE" class="java.lang.String" scope="application"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<%@ include file="../initPage.jsp" %>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/checkDate.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popCalendar.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popContacts.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/submit.js"></SCRIPT>
<script type="text/javascript">
function checkForm(form) {
    formTest = true;
    message = "";
    if ((form.contactId.value == "-1")) { 
      message += "- Check that a Contact is selected\r\n";
      formTest = false;
    }
    if ((form.username.value == "")) { 
      message += "- Check that a Username is entered\r\n";
      formTest = false;
    }
    if ((form.password1.value == "") || (form.password2.value == "") || (form.password1.value != form.password2.value)) { 
      message += "- Check that both Passwords are entered correctly\r\n";
      formTest = false;
    }
    
    if (form.roleId.value == "-1") { 
      message += "- Check that a Role is selected\r\n";
      formTest = false;
    }
    if (formTest == false) {
      alert("Form could not be saved, please check the following:\r\n\r\n" + message);
      return false;
    } else {
      return true;
    }
  }
</script>
<form name="addUser" action="Users.do?command=AddUser&auto-populate=true" onSubmit="return checkForm(this);" method="post">
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="Admin.do">Admin</a> >
Add User
</td>
</tr>
</table>
<%-- End Trails --%>
<%-- License info --%>
<dhv:evaluate if="<%= hasText(APP_SIZE) %>">
<table class="note" cellspacing="0">
  <tr>
    <th><img src="images/icons/stock_about-16.gif" border="0" align="absmiddle"/></th>
    <td>The installed license limits this system to <%= APP_SIZE %> active users.</td></tr>
</table>
</dhv:evaluate>
<%-- End license info --%>
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
<dhv:formMessage showSpace="false" />
  <tr>
    <th colspan="2">
      <strong>Add a user account</strong>
    </th>
  </tr>
  <tr>
    <td class="formLabel">
      Contact
    </td>
    <td>
      <table border="0" cellspacing="0" cellpadding="4" class="empty">
        <tr>
          <td valign="top" nowrap>
            <div id="changecontact"><%= UserRecord.getContactId() == -1 ? "None Selected" : UserRecord.getContact().getNameLastFirst() %></div>
          </td>
          <td valign="top" width="100%" nowrap>
            <font color="red">*</font><%= showAttribute(request, "contactIdError") %>
            [<a href="javascript:popContactsListSingle('contactLink','changecontact','nonUsersOnly=true&reset=true&filters=accountcontacts|employees');">Select Contact</a>]
            <input type="hidden" name="contactId" id="contactLink" value="<%= UserRecord.getContactId() %>">
            [<a href="javascript:popURL('ExternalContacts.do?command=Prepare&popup=true&source=adduser', 'New_Contact','600','550','yes','yes');">Create new contact</a>]
          </td>
        </tr>
      </table>
    </td>
  </tr>
	<tr>
    <td class="formLabel">
      Unique Username
    </td>
    <td>
      <input type="text" name="username" value="<%= toHtmlValue(UserRecord.getUsername()) %>"><font color=red>*</font>
      <%= showAttribute(request, "usernameError") %>
    </td>
  </tr>
	<tr>
    <td class="formLabel">
      Password
    </td>
    <td>
     <input type="password" name="password1"><font color=red>*</font>
     <%= showAttribute(request, "password1Error") %>
    </td>
  </tr>
	<tr>
    <td class="formLabel">
      Password (again)
    </td>
    <td>
     <input type="password" name="password2"><font color=red>*</font>
     <%= showAttribute(request, "password2Error") %>
    </td>
  </tr>
	<tr>
    <td class="formLabel">
      Role
    </td>
    <td>
      <%= RoleList.getHtmlSelect("roleId", UserRecord.getRoleId()) %><font color="red">*</font>
      <%= showAttribute(request, "roleError") %>
    </td>
  </tr>
	<tr>
    <td class="formLabel">
      Reports To
    </td>
    <td>
      <%= UserList.getHtmlSelect("managerId", UserRecord.getManagerId()) %>
      <%= showAttribute(request, "managerIdError") %>
    </td>
  </tr>
<dhv:permission name="demo-view">
	<tr>
    <td class="formLabel">
      Alias User
    </td>
    <td>
      <%= UserList.getHtmlSelect("alias", UserRecord.getAlias()) %>
    </td>
  </tr>
</dhv:permission>
	<tr>
    <td class="formLabel">
      Expire Date
    </td>
    <td>
      <zeroio:dateSelect form="addUser" field="expires" timestamp="<%= UserRecord.getExpires() %>" />
      <%= showAttribute(request, "expiresError") %>
    </td>
  </tr>
</table>
<br>
<input type="submit" value="Add User">
<input type="button" value="Cancel" onClick="javascript:this.form.action='Users.do?command=ListUsers';this.form.submit()">
<iframe src="empty.html" name="server_commands" id="server_commands" style="visibility:hidden" height="0"></iframe>
</form>

