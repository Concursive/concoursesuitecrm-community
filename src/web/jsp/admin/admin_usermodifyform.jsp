<jsp:useBean id="UserList" class="com.darkhorseventures.cfsbase.UserList" scope="request"/>
<jsp:useBean id="RoleList" class="com.darkhorseventures.cfsbase.RoleList" scope="request"/>
<jsp:useBean id="UserRecord" class="com.darkhorseventures.cfsbase.User" scope="request"/>
<%@ include file="initPage.jsp" %>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="/javascript/checkDate.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="/javascript/popCalendar.js"></SCRIPT>
<body onLoad="javascript:document.forms[0].username.focus();">
<form name="details" action="/Users.do?auto-populate=true" method="post">
<input type=hidden name="command" value="UpdateUser">
<input type=button name="action" value="Update"	onClick="document.details.command.value='UpdateUser';document.details.submit()">
<input type=button name="action" value="Cancel"	onClick="document.details.command.value='ListUsers';document.details.submit()">
<input type=button name="action" value="Delete" onClick="document.details.command.value='DeleteUser';document.details.submit()">
<br>
&nbsp;
<input type="hidden" name="id" value="<%= UserRecord.getId() %>">
<input type="hidden" name="previousUsername" value="<%= ((UserRecord.getPreviousUsername() == null)?UserRecord.getUsername():UserRecord.getPreviousUsername()) %>">
<table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr bgcolor="#DEE0FA">
    <td colspan=2 valign=center align=left>
	    <strong>Modify a user record</strong>
    </td>
  </tr>
  <tr>
    <td width="150">Unique Username</td>
    <td>
      <input type="text" name="username" value="<%= toHtmlValue(UserRecord.getUsername()) %>">
      <font color=red>*</font> <%= showAttribute(request, "usernameError") %>
    </td>
  </tr>
  <!--tr><td width="150">Password</td><td><input type="password" name="password1" value="<%= toHtmlValue(UserRecord.getPassword()) %>"><font color=red>*</font> <%= showAttribute(request, "password1Error") %></td></tr>
  <tr><td width="150">Type password again</td><td><input type="password" name="password2" value="<%= toHtmlValue(UserRecord.getPassword()) %>"><font color=red>*</font> <%= showAttribute(request, "password2Error") %></td></tr-->
  <tr>
    <td width="150">Role (User Group)</td>
    <td>
      <%= RoleList.getHtmlSelect("roleId", UserRecord.getRoleId()) %>
      <font color="red">*</font> <%= showAttribute(request, "roleError") %>
    </td>
  </tr>
  <tr>
    <td width="150">Reports To</td>
    <td>
      <%= UserList.getHtmlSelect("managerId", UserRecord.getManagerId()) %>
      <%= showAttribute(request, "managerIdError") %>
    </td>
  </tr>
  
    <tr>
    <td width="150">Alias User</td>
    <td>
      <%= UserList.getHtmlSelect("alias", UserRecord.getAlias()) %>
    </td>
  </tr>
  
  <tr>
    <td width="150">Expire Date</td>
    <td>
    <input type=text size=10 name="expires" value="<%=toHtmlValue(UserRecord.getExpiresString())%>">
    <a href="javascript:popCalendar('details', 'expires');">Date</a> (mm/dd/yyyy)
    </td>
  </tr>
  

  
</table>
<br>
<input type=button name="action" value="Update"	onClick="document.details.command.value='UpdateUser';document.details.submit()">
<input type=button name="action" value="Cancel"	onClick="document.details.command.value='ListUsers';document.details.submit()">
<input type=button name="action" value="Delete" onClick="document.details.command.value='DeleteUser';document.details.submit()">
</form>
</body>
