<%-- 
  - Copyright Notice: (C) 2000-2004 Team Elements, All Rights Reserved.
  - License: This source code cannot be modified, distributed or used without
  -          written permission from Team Elements. This notice must remain in
  -          place.
  - Author(s): Matt Rajkowski
  - Version: $Id$
  - Description: 
  --%>
<%@ page import="java.util.*,org.aspcfs.modules.admin.base.*,org.aspcfs.utils.StringUtils" %>
<jsp:useBean id="UserList" class="org.aspcfs.modules.admin.base.UserList" scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.admin.base.User" scope="request"/>
<html>
<head>
</head>
<body onload='page_init();'>
<script language='Javascript'>
function page_init() {
  var list = parent.document.forms['projectMemberForm'].elements['selTotalList'];
  list.options.length = 0;
<%
  Iterator i = UserList.iterator();
  while (i.hasNext()) {
    User thisUser = (User)i.next();
%>
  if ( !(inArray(parent.document.forms['projectMemberForm'].elements['selProjectList'], <%= thisUser.getId() %>)) ) {
    var newOpt = parent.document.createElement("OPTION");
    newOpt.text='<%= StringUtils.jsStringEscape(thisUser.getContact().getNameFirstLast()) %>';
    newOpt.value='<%= thisUser.getId() %>';
    list.options[list.length] = newOpt;
  }
  parent.initList(<%= thisUser.getId() %>);
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

