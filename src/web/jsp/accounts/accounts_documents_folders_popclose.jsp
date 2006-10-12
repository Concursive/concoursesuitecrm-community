<%-- 
  - Copyright(c) 2004 Dark Horse Ventures LLC (http://www.centriccrm.com/) All
  - rights reserved. This material cannot be distributed without written
  - permission from Dark Horse Ventures LLC. Permission to use, copy, and modify
  - this material for internal use is hereby granted, provided that the above
  - copyright notice and this permission notice appear in all copies. DARK HORSE
  - VENTURES LLC MAKES NO REPRESENTATIONS AND EXTENDS NO WARRANTIES, EXPRESS OR
  - IMPLIED, WITH RESPECT TO THE SOFTWARE, INCLUDING, BUT NOT LIMITED TO, THE
  - IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR ANY PARTICULAR
  - PURPOSE, AND THE WARRANTY AGAINST INFRINGEMENT OF PATENTS OR OTHER
  - INTELLECTUAL PROPERTY RIGHTS. THE SOFTWARE IS PROVIDED "AS IS", AND IN NO
  - EVENT SHALL DARK HORSE VENTURES LLC OR ANY OF ITS AFFILIATES BE LIABLE FOR
  - ANY DAMAGES, INCLUDING ANY LOST PROFITS OR OTHER INCIDENTAL OR CONSEQUENTIAL
  - DAMAGES RELATING TO THE SOFTWARE.
  - 
  - Version: $Id$
  - Description: 
  --%>
<html>
<%
  boolean scrollReload = false;
  String location = null;
  String returnPage = (String) request.getAttribute("return");
  if (returnPage == null) {
    returnPage = (String) request.getParameter("return");
  }
  String param = (String) request.getAttribute("param");
  if (param == null) {
    param = request.getParameter("param");
  }
  String param2 = (String) request.getAttribute("param2");
  if (param2 == null) {
    param2 = request.getParameter("param2");
  }
  if (returnPage != null) {
    if ("AccountsDocuments".equals(returnPage)) {
      location = "AccountsDocuments.do?command=View&orgId="+ param+"&accountfolders=true";
    }
  }
  if (location == null) {
%>
<body onload="window.opener.location.href='Accounts.do?command=Search'; window.close();">
<% } else { %>
<body onload="window.opener.scrollReload('<%= location %>'); window.close();">
<% } %>
</body>
</html>
