<%-- 
  - Copyright Notice: (C) 2000-2004 Team Elements, All Rights Reserved.
  - License: This source code cannot be modified, distributed or used without
  -          written permission from Team Elements. This notice must remain in
  -          place.
  - Author(s): Matt Rajkowski
  - Version: $Id$
  - Description: 
  --%>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,com.zeroio.iteam.base.*,org.aspcfs.utils.StringUtils" %>
<jsp:useBean id="team" class="com.zeroio.iteam.base.TeamMemberList" scope="request"/>
<html>
<head>
</head>
<body onload='page_init();'>
<script language='Javascript'>
function page_init() {
  var list = parent.document.forms['projectMemberForm'].elements['selTotalList'];
  list.options.length = 0;
<%
  Iterator i = team.iterator();
  while (i.hasNext()) {
    TeamMember member = (TeamMember) i.next();
%>
  if ( !(inArray(parent.document.forms['projectMemberForm'].elements['selProjectList'], <%= member.getUserId() %>)) ) {
    var newOpt = parent.document.createElement("OPTION");
    newOpt.text='<dhv:username id="<%= member.getUserId() %>"/>';
    newOpt.value='<%= member.getUserId() %>';
    list.options[list.length] = newOpt;
  }
  parent.initList(<%= member.getUserId() %>);
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

