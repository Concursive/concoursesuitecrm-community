<%-- 
  - Copyright Notice: (C) 2000-2004 Dark Horse Ventures, All Rights Reserved.
  - License: This source code cannot be modified, distributed or used without
  -          written permission from Dark Horse Ventures. This notice must
  -          remain in place.
  - Version: $Id$
  - Description: 
  --%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*" %>
<jsp:useBean id="recipientText" class="java.lang.String" scope="request"/><html>
<head>
</head>
<body onload="page_init();">
<script language="JavaScript">
function page_init() {
  parent.changeDivContent("change<%= request.getParameter("contactId") %>", 
    "<a href='javascript:toggleRecipient(<%= request.getParameter("contactId") %>)'><%= recipientText %></a>");
}
</script>
</body>
</html>

