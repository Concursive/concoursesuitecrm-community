<%-- 
  - Copyright Notice: (C) 2000-2004 Team Elements, All Rights Reserved.
  - License: This source code cannot be modified, distributed or used without
  -          written permission from Team Elements. This notice must remain in
  -          place.
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
