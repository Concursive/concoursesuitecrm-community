<%--
  - Copyright Notice: (C) 2000-2004 Dark Horse Ventures, All Rights Reserved.
  - License: This source code cannot be modified, distributed or used without
  -          written permission from Dark Horse Ventures. This notice must
  -          remain in place.
  - Version: $Id$
  - Description:
  --%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<jsp:useBean id="FileString" class="java.lang.String" scope="request"/>
<%@ include file="../../initPage.jsp" %>
<html>
<head>
<title>test</title>
<body>
<%=toHtml(FileString)%>
</body>
</html>
