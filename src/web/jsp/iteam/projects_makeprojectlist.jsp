<%-- 
  - Copyright Notice: (C) 2000-2004 Team Elements, All Rights Reserved.
  - License: This source code cannot be modified, distributed or used without
  -          written permission from Team Elements. This notice must remain in
  -          place.
  - Author(s): Matt Rajkowski
  - Version: $Id$
  - Description: 
  --%>
<%@ page import="java.util.*,com.zeroio.iteam.base.*,org.aspcfs.utils.StringUtils" %>
<jsp:useBean id="projectList" class="com.zeroio.iteam.base.ProjectList" scope="request"/>
<html>
<head>
</head>
<body onload='page_init();'>
<script language='Javascript'>
function page_init() {
  var list = parent.document.forms['projectMemberForm'].elements['selDepartment'];
  list.options.length = 0;
<%
  Iterator i = projectList.iterator();
  while (i.hasNext()) {
    Project thisProject = (Project) i.next();
%>
    var newOpt = parent.document.createElement("OPTION");
    newOpt.text='<%= StringUtils.jsStringEscape(thisProject.getTitle()) %>';
    newOpt.value='<%= thisProject.getId() %>';
    list.options[list.length] = newOpt;
<%
  }
%>
}
</script>
</body>
</html>

