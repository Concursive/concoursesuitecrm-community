<%--
 Copyright 2000-2004 Matt Rajkowski
 matt.rajkowski@teamelements.com
 http://www.teamelements.com
 This source code cannot be modified, distributed or used without
 permission from Matt Rajkowski
--%>
<%@ page import="java.util.*,org.aspcfs.utils.web.*" %>
<jsp:useBean id="departments" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<html>
<head>
</head>
<body onload='page_init();'>
<script language='Javascript'>
function page_init() {
  var list = parent.document.forms['projectMemberForm'].elements['selDepartment'];
  list.options.length = 0;
<%
  Iterator i = departments.iterator();
  while (i.hasNext()) {
    LookupElement element = (LookupElement) i.next();
%>
  	list.options[list.length] = new Option("<%= element.getDescription() %>", "<%= element.getId() %>");
<%
  }
%>
}
</script>
</body>
</html>

