<%-- 
  - Copyright(c) 2004 Team Elements LLC (http://www.teamelements.com/) All
  - rights reserved. This material cannot be distributed without written
  - permission from Team Elements LLC. Permission to use, copy, and modify
  - this material for internal use is hereby granted, provided that the above
  - copyright notice and this permission notice appear in all copies. TEAM ELEMENTS
  - LLC MAKES NO REPRESENTATIONS AND EXTENDS NO WARRANTIES, EXPRESS OR
  - IMPLIED, WITH RESPECT TO THE SOFTWARE, INCLUDING, BUT NOT LIMITED TO, THE
  - IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR ANY PARTICULAR
  - PURPOSE, AND THE WARRANTY AGAINST INFRINGEMENT OF PATENTS OR OTHER
  - INTELLECTUAL PROPERTY RIGHTS. THE SOFTWARE IS PROVIDED "AS IS", AND IN NO
  - EVENT SHALL TEAM ELEMENTS LLC OR ANY OF ITS AFFILIATES BE LIABLE FOR
  - ANY DAMAGES, INCLUDING ANY LOST PROFITS OR OTHER INCIDENTAL OR CONSEQUENTIAL
  - DAMAGES RELATING TO THE SOFTWARE.
  - 
  - Author(s): Matt Rajkowski
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
    if ("ProjectEnterpriseView".equals(returnPage)) {
      location = "ProjectManagement.do?command=EnterpriseView";
      scrollReload = true;
    } else if ("ProjectPersonalView".equals(returnPage)) {
      location = "ProjectManagement.do?command=PersonalView";
      scrollReload = true;
    } else if ("ProjectRequirements".equals(returnPage)) {
      location = "ProjectManagement.do?command=ProjectCenter&section=Requirements&pid=" + param;
      scrollReload = true;
    } else if ("ProjectAssignments".equals(returnPage)) {
      location = "ProjectManagement.do?command=ProjectCenter&section=Assignments&pid=" + param + "&rid=" + param2;
      scrollReload = true;
    } else if ("ProjectLists".equals(returnPage)) {
      location = "ProjectManagement.do?command=ProjectCenter&section=Lists&pid=" + param + "&cid=" + param2;
      scrollReload = true;
    } else if ("ProjectFiles".equals(returnPage)) {
      location = "ProjectManagement.do?command=ProjectCenter&section=File_Library&pid=" + param + "&folderId=" + param2;
      scrollReload = true;
    }
  }
  
  if (location == null) {
%>
<body onload="window.opener.document.location.href='ProjectManagement.do'; window.close();">
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
