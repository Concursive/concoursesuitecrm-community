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
  --%>
<html>
<%
  String location = null;
  String returnPage = request.getParameter("return");
  if (returnPage == null) {
    returnPage = (String)request.getAttribute("return");
  }
  String param = (String)request.getAttribute("param");
  if (param == null) {
    param = (String) request.getParameter("param");
  }
  String param1 = (String) request.getAttribute("param1");
  if (param1 == null) {
    param1 = (String) request.getParameter("param1");
  }
  if (returnPage != null) {
    if ("ProjectEnterpriseView".equals(returnPage)) {
      location = "ProjectManagement.do?command=EnterpriseView";
    } else if ("ProjectPersonalView".equals(returnPage)) {
      location = "ProjectManagement.do?command=PersonalView";
    } else if ("ProjectRequirements".equals(returnPage)) {
      location = "ProjectManagement.do?command=ProjectCenter&section=Requirements&pid=" + param;
    } else if ("ProductCatalogEditor".equals(returnPage)) {
      location = "ProductCatalogEditor.do?command=List&moduleId=" + param + "&categoryId=" + param1;
    } else {
      location = returnPage;
    }
  }
  
  if (location == null) {
%>
<body onload="window.opener.location=window.opener.location; window.close();">
<% 
  } else if("Calendar".equalsIgnoreCase(returnPage)) {
%>
<body onload="window.opener.parent.reloadFrames(); window.close();">
<%
  }
else {
%>
<body onload="window.opener.location='<%= location %>'; window.close();">
<%
  }
%>
</body>
</html>
