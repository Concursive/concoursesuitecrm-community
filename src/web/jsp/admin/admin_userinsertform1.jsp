<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<jsp:useBean id="UserRecord" class="org.aspcfs.modules.admin.base.User" scope="request"/>
<jsp:useBean id="RoleList" class="org.aspcfs.modules.admin.base.RoleList" scope="request"/>
<jsp:useBean id="UserList" class="org.aspcfs.modules.admin.base.UserList" scope="request"/>
<%@ include file="../initPage.jsp" %>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/checkDate.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popCalendar.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popContacts.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/submit.js"></SCRIPT>
<body>
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
<a href="Admin.do">Setup</a> >
Add User<br>
<hr color="#BFBFBB" noshade>
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
<% if (request.getAttribute("actionError") != null) { %>
  <%= showError(request, "actionError") %>
<%}%>
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
            [<a href="javascript:popURL('ExternalContacts.do?command=Prepare&popup=true&source=adduser', 'New_Contact','500','600','yes','yes');">Create new contact</a>]
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
      <input type="text" size="10" name="expires" value="">
      <a href="javascript:popCalendar('addUser', 'expires');">Date</a> (mm/dd/yyyy)
    </td>
  </tr>
</table>
<br>
<input type="submit" value="Add User">
<input type="button" value="Cancel" onClick="javascript:this.form.action='Users.do?command=ListUsers';this.form.submit()">
<iframe src="empty.html" name="server_commands" id="server_commands" style="visibility:hidden" height="0"></iframe>
</form>

