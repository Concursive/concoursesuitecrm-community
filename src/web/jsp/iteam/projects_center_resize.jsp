<%-- 
  - Copyright Notice: (C) 2000-2004 Team Elements, All Rights Reserved.
  - License: This source code cannot be modified, distributed or used without
  -          written permission from Team Elements. This notice must remain in
  -          place.
  - Author(s): Matt Rajkowski
  - Version: $Id$
  - Description: 
  --%>
<jsp:useBean id="projectView" class="java.lang.String" scope="session"/>
<html>
<head>
</head>
<body onload='page_init();'>
<script language='Javascript'>
function page_init() {
<% if ("MAXIMIZED".equals(projectView)) { %>
  parent.doMax();
<% } else { %>
  parent.doMin();
<% } %>
}
</script>
</body>
</html>

