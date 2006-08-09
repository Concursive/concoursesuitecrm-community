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
  - Version: $Id: projects_makeuserlist.jsp 15115 2006-05-31 16:47:51 +0000 (Wed, 31 May 2006) matt $
  - Description: 
  --%>
<%@ page import="java.util.*,org.aspcfs.modules.admin.base.*,org.aspcfs.utils.StringUtils,org.aspcfs.modules.contacts.base.*" %>
<%@ page import="java.sql.*" %>
<jsp:useBean id="contactList" class="org.aspcfs.modules.contacts.base.ContactList" scope="request"/>
<jsp:useBean id="Contact" class="org.aspcfs.modules.contacts.base.Contact" scope="request"/>
<html>
<head>
</head>
<body onload='page_init();'>
<script language='Javascript'>
function page_init() {
  var list = parent.document.forms['projectMemberForm'].elements['selTotalList'];
  list.options.length = 0;
<%
  Iterator i = contactList.iterator();
  while (i.hasNext()) {
  	Contact thisContact = (Contact)i.next();
    Timestamp currentTime = new Timestamp(Calendar.getInstance().getTimeInMillis());
    String tempString = "";
%>
  if ( !(inArray(parent.document.forms['projectMemberForm'].elements['selProjectList'], <%= thisContact.getUserId() %>)) ) {
    var newOpt = parent.document.createElement("OPTION");
    newOpt.text="<%= StringUtils.jsStringEscape(thisContact.getNameFirstLast()+tempString) %>";
    newOpt.value='<%= thisContact.getUserId() %>';
    if (newOpt.value != '0') {
      list.options[list.length] = newOpt;
    }
  }
  parent.initList(<%= thisContact.getUserId() %>);
<%
  }
%>
}

function inArray(a, s) {
	var i = 0;
	for(i=0; i < a.length; i++) {
		if (a.options[i].value == s) {
			return true;
		}
	}
	return false;
}
</script>
</body>
</html>

