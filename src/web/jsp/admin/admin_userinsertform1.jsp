<%@ taglib uri="WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<jsp:useBean id="ContactTypeList" class="com.darkhorseventures.cfsbase.ContactTypeList" scope="request"/>
<jsp:useBean id="ContactList" class="com.darkhorseventures.cfsbase.ContactList" scope="request"/>
<jsp:useBean id="UserRecord" class="com.darkhorseventures.cfsbase.User" scope="request"/>
<jsp:useBean id="RoleList" class="com.darkhorseventures.cfsbase.RoleList" scope="request"/>
<jsp:useBean id="UserList" class="com.darkhorseventures.cfsbase.UserList" scope="request"/>
<%@ include file="initPage.jsp" %>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="/javascript/checkDate.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="/javascript/popCalendar.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript">
<!-- 
  function HideSpans() {
    //isNS = (document.layers) ? true : false;
    //if( (isNS) ) {
    //  document.newContact.visibility="hidden";
    //}
    
    <% System.out.println(UserRecord.getContact().getNameFirst() + ", " + UserRecord.getContact().getNameLast());%>
  
    <%if (UserRecord.getContact().getNameFirst().length() > 0 || UserRecord.getContact().getNameLast().length() > 0) {%>
      ShowSpan('newContact');
    <%} else {%>
      HideSpan('newContact');
    <%}%>
  
    return true;
  }
  
  //hide a particular <span>
  
  function HideSpan(thisID)
  {
    isNS4 = (document.layers) ? true : false;
    isIE4 = (document.all && !document.getElementById) ? true : false;
    isIE5 = (document.all && document.getElementById) ? true : false;
    isNS6 = (!document.all && document.getElementById) ? true : false;
  
    if (isNS4){
      elm = document.layers[thisID];
    }	else if (isIE4) {
      elm = document.all[thisID];
    }	else if (isIE5 || isNS6) {
      elm = document.getElementById(thisID);
    }
    
    document.addUser.contact_nameFirst.value == '';
    document.addUser.contact_nameLast.value == '';    
    elm.style.visibility="hidden";
  }  
  
  function clearContact() {
    if (document.addUser.contact_nameFirst.value == 'First Name' || document.addUser.contact_nameLast.value == 'Last Name') {
      document.addUser.contact_nameFirst.value='';
      document.addUser.contact_nameLast.value='';
    }
    
    return true;
  }  
  
  function ShowSpan(thisID)
  {
    updateContactList();
    
    isNS4 = (document.layers) ? true : false;
    isIE4 = (document.all && !document.getElementById) ? true : false;
    isIE5 = (document.all && document.getElementById) ? true : false;
    isNS6 = (!document.all && document.getElementById) ? true : false;
  
    if (isNS4){
      elm = document.layers[thisID];
    }
    else if (isIE4) {
      elm = document.all[thisID];
    }
    else if (isIE5 || isNS6) {
      elm = document.getElementById(thisID);
    }
    
    <% if (UserRecord.getContact().getNameFirst().length() == 0 && UserRecord.getContact().getNameLast().length() == 0) {%>
      document.addUser.contact_nameFirst.value='First Name';
      document.addUser.contact_nameLast.value='Last Name';
    <%}%>
    
    elm.style.visibility="visible";
  }  
  
  function updateContactList() {
    var sel = document.forms['addUser'].elements['typeId'];
    var value = sel.options[sel.selectedIndex].value;
    var url = "Users.do?command=ContactJSList&typeId=" + escape(value);
    window.frames['server_commands'].location.href=url;
  }
//  End -->
</SCRIPT>
<body onLoad="HideSpans();">
<form name="addUser" action="Users.do?command=AddUser&auto-populate=true" method="post">
<a href="/Admin.do">Setup</a> >
Add User<br>
<hr color="#BFBFBB" noshade>
<table cellpadding=6 cellspacing=0 border=1 width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
<iframe src="empty.html" name="server_commands" id="server_commands" style="visibility:hidden" height="0"></iframe>
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
    <td width="100" class="formLabel">
      Contact<br>
      [<a href="#" onClick="javascript:ShowSpan('newContact');">add new</a>]
    </td>
    <td valign="center">
      <% ContactList.setJsEvent("onChange=\"javascript:HideSpan('newContact');\""); %>
      <%= ContactList.getHtmlSelect("contactId", UserRecord.getContactId()) %><font color="red">*</font><span name="newContact" ID="newContact" style="position:relative; visibility:hidden">
      <input onFocus="javascript:clearContact();" type=text value="<%=toHtmlValue(UserRecord.getContact().getNameFirst())%>" name="contact_nameFirst">
      &nbsp;<input onFocus="javascript:clearContact();"type=text value="<%=toHtmlValue(UserRecord.getContact().getNameLast())%>" name="contact_nameLast"><font color="red">*</font>
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
<input type="button" value="Cancel" onClick="javascript:this.form.action='/Users.do?command=ListUsers';this.form.submit()">
</form>
