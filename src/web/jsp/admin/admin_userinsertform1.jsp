<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<jsp:useBean id="ContactTypeList" class="org.aspcfs.modules.contacts.base.ContactTypeList" scope="request"/>
<jsp:useBean id="ContactList" class="org.aspcfs.modules.contacts.base.ContactList" scope="request"/>
<jsp:useBean id="UserRecord" class="org.aspcfs.modules.admin.base.User" scope="request"/>
<jsp:useBean id="RoleList" class="org.aspcfs.modules.admin.base.RoleList" scope="request"/>
<jsp:useBean id="UserList" class="org.aspcfs.modules.admin.base.UserList" scope="request"/>
<%@ include file="../initPage.jsp" %>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/checkDate.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popCalendar.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript">
<!-- 
  function toggle(id) {
    var obj = document.getElementById(id);
    obj.style.display = (obj.style.display == 'none')?'':'none';
    if (obj.style.display == '') {
      document.addUser.contact_nameFirst.focus();
    }
    return false;
  }
  
  function hideSpan(id) {
    var obj = document.getElementById(id);
    obj.style.display = 'none';
    return false;
  }
  
  function updateContactList() {
    var sel = document.forms['addUser'].elements['typeId'];
    var value = sel.options[sel.selectedIndex].value;
    var url = "Users.do?command=ContactJSList&typeId=" + escape(value);
    window.frames['server_commands'].location.href=url;
  }
//  End -->
</SCRIPT>
<body>
<form name="addUser" action="Users.do?command=AddUser&auto-populate=true" method="post">
<a href="Admin.do">Setup</a> >
Add User<br>
<hr color="#BFBFBB" noshade>
<table cellpadding=6 cellspacing=0 border=1 width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
<% if (request.getAttribute("actionError") != null) { %>
  <%= showError(request, "actionError") %>
<%}%>
  <tr>
    <td colspan=2 align="left" bgcolor="#DEE0FA">
    <strong>Add a CFS User account</strong>
    </td>
  </tr>
  
	<tr>
    <td width="100" class="formLabel">
      Contact Type
    </td>
    <td valign="center">
      <%= ContactTypeList.getHtmlSelect("typeId") %><font color="red">*</font>
    </td>
  </tr>  
  
	<tr>
    <td width="100" class="formLabel" valign="top">
      Contact
    </td>
    <td valign="center">
      <% ContactList.setJsEvent("onChange=\"javascript:hideSpan('newContact');\""); %>
      <%= ContactList.getHtmlSelect("contactId", UserRecord.getContactId()) %><font color="red">*</font>
      &nbsp;[<a href="#" onClick="javascript:toggle('newContact'); return false;">add new</a>]
      <span name="newContact" ID="newContact" style="display:none">
        <br>
        &nbsp;<br>
        New Contact Details<br>
        &nbsp;<br>
        First Name: <input type="text" value="<%=toHtmlValue(UserRecord.getContact().getNameFirst())%>" name="contact_nameFirst"><br>
        Last Name: <input type="text" value="<%=toHtmlValue(UserRecord.getContact().getNameLast())%>" name="contact_nameLast"><font color="red">*</font>
      </span>
      <%if (request.getAttribute("contactIdError") != null) {%>
        <br><%= showAttribute(request, "contactIdError") %>
      <%}%>
    </td>
  </tr>  
  
	<tr>
    <td width="100" class="formLabel">
      Unique Username
    </td>
    <td valign="center">
      <input type="text" name="username" value="<%= toHtmlValue(UserRecord.getUsername()) %>"><font color=red>*</font> <%= showAttribute(request, "usernameError") %>
    </td>
  </tr>   
  
	<tr>
    <td width="100" class="formLabel">
      Password
    </td>
    <td valign="center">
     <input type="password" name="password1"><font color=red>*</font> <%= showAttribute(request, "password1Error") %>
    </td>
  </tr>    
  
	<tr>
    <td width="100" class="formLabel">
      Password (again)
    </td>
    <td valign="center">
     <input type="password" name="password2"><font color=red>*</font> <%= showAttribute(request, "password2Error") %>
    </td>
  </tr> 
  
	<tr>
    <td width="100" class="formLabel">
      Role
    </td>
    <td valign="center">
      <%= RoleList.getHtmlSelect("roleId", UserRecord.getRoleId()) %><font color="red">*</font> <%= showAttribute(request, "roleError") %>
    </td>
  </tr>  
  
	<tr>
    <td width="100" class="formLabel">
      Reports To
    </td>
    <td valign="center">
      <%= UserList.getHtmlSelect("managerId", UserRecord.getManagerId()) %>
      <%= showAttribute(request, "managerIdError") %>
    </td>
  </tr>   
  
	<tr>
    <td width="100" class="formLabel">
      Alias User
    </td>
    <td valign="center">
      <%= UserList.getHtmlSelect("alias", UserRecord.getAlias()) %>
    </td>
  </tr>   
  
	<tr>
    <td width="100" class="formLabel">
      Expire Date
    </td>
    <td valign="center">
      <input type=text size=10 name="expires" value="">
      <a href="javascript:popCalendar('addUser', 'expires');">Date</a> (mm/dd/yyyy)
    </td>
  </tr>   
</table>
<br>
<input type="submit" value="Add User">
<input type="button" value="Cancel" onClick="javascript:this.form.action='Users.do?command=ListUsers';this.form.submit()">
<iframe src="empty.html" name="server_commands" id="server_commands" style="visibility:hidden" height="0"></iframe>
</form>
