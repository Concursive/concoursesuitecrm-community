<%@ taglib uri="WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<jsp:useBean id="UserList" class="com.darkhorseventures.cfsbase.UserList" scope="request"/>
<jsp:useBean id="RoleList" class="com.darkhorseventures.cfsbase.RoleList" scope="request"/>
<jsp:useBean id="UserRecord" class="com.darkhorseventures.cfsbase.User" scope="request"/>
<%@ include file="initPage.jsp" %>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="/javascript/checkDate.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="/javascript/popCalendar.js"></SCRIPT>

<body onLoad="javascript:document.forms[0].username.focus();">
<form name="details" action="/Users.do?command=UpdateUser&auto-populate=true" method="post">

<a href="Admin.do">Setup</a> > 

<% if (request.getParameter("return") != null) {%>
	<% if (request.getParameter("return").equals("list")) {%>
	<a href="Users.do?command=ListUsers">View Users</a> >
  <%}%>
<%} else {%>
<a href="Users.do?command=ListUsers">View Users</a> >
<a href="Users.do?command=UserDetails&id=<%=UserRecord.getId()%>">User Details</a> >
<%}%>


Modify User<br>
<hr color="#BFBFBB" noshade>

<% if (request.getParameter("return") != null) {%>
<input type="hidden" name="return" value="<%=request.getParameter("return")%>">
<%}%>

<input type=submit value="Update">

<% if (request.getParameter("return") != null) {%>
	<% if (request.getParameter("return").equals("list")) {%>
	<input type=submit value="Cancel" onClick="javascript:this.form.action='/Users.do?command=ListUsers'">
	<%}%>
<%} else {%>
<input type=submit value="Cancel" onClick="javascript:this.form.action='/Users.do?command=UserDetails&id=<%=UserRecord.getId()%>'">
<%}%>
<input type=submit value="Delete" onClick="javascript:this.form.action='/Users.do?command=DeleteUser&id=<%=UserRecord.getId()%>'">
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
<input type=submit value="Update">
<% if (request.getParameter("return") != null) {%>
	<% if (request.getParameter("return").equals("list")) {%>
	<input type=submit value="Cancel" onClick="javascript:this.form.action='/Users.do?command=ListUsers'">
	<%}%>
<%} else {%>
<input type=submit value="Cancel" onClick="javascript:this.form.action='/Users.do?command=UserDetails&id=<%=UserRecord.getId()%>'">
<%}%>
<input type=submit value="Delete" onClick="javascript:this.form.action='/Users.do?command=DeleteUser&id=<%=UserRecord.getId()%>'">
</form>
</body>
