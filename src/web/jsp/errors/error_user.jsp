<%--
  - Copyright Notice: (C) 2000-2004 Dark Horse Ventures, All Rights Reserved.
  - License: This source code cannot be modified, distributed or used without
  -          written permission from Dark Horse Ventures. This notice must
  -          remain in place.
  - Version: $Id$
  - Description:
  --%>
<%@ page  import="java.util.*" %>
<jsp:useBean id="errors" class="java.util.HashMap" scope="request"/>
<img src="images/error.gif" border="0" align="absmiddle"/>
<font color='red'>An Error Has Occurred</font>
<hr color="#BFBFBB" noshade>
<%
  String errorMessage = (String)request.getAttribute("Error");
  if (errorMessage != null) {
%>
<%= errorMessage %>
<%
  } else {
    Iterator errorList = errors.values().iterator();
    while (errorList.hasNext()) {
%>
  <%= (String)errorList.next() %><br>
<%  
    }
%>
No further information is available.
<%
  }
%>
