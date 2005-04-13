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
  - Author(s): 
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
    if ("DocumentsEnterpriseView".equals(returnPage)) {
      location = "DocumentManagement.do?command=EnterpriseView";
      scrollReload = true;
    } else if ("DocumentsPersonalView".equals(returnPage)) {
      location = "DocumentManagement.do?command=PersonalView";
      scrollReload = true;
    } else if ("DocumentsFiles".equals(returnPage)) {
      location = "DocumentManagement.do?command=DocumentStoreCenter&section=File_Library&documentStoreId=" + param + "&folderId=" + param2;
      scrollReload = true;
    }
  }
  
  if (location == null) {
%>
<body onload="window.opener.document.location.href='DocumentManagement.do'; window.close();">
<% 
  } else if (scrollReload) {
%>
<body onload="window.opener.scrollReload('<%= location %>'); window.close();">
<%  
  } else {
%>
<body onload="window.opener.location='<%= location %>'; window.close();">
<%
  }
%>
</body>
</html>
