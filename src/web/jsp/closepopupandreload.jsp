<html>
<%
  String location = null;
  String returnPage = (String)request.getAttribute("return");
  String param = (String)request.getAttribute("param");
  if (returnPage != null) {
    if ("ProjectEnterpriseView".equals(returnPage)) {
      location = "ProjectManagement.do?command=EnterpriseView";
    } else if ("ProjectPersonalView".equals(returnPage)) {
      location = "ProjectManagement.do?command=PersonalView";
    } else if ("ProjectRequirements".equals(returnPage)) {
      location = "ProjectManagement.do?command=ProjectCenter&section=Requirements&pid=" + param;
    }
  }
  
  if (location == null) {
%>
<body onload="window.opener.location.reload(); window.close();">
<% 
  } else {
%>
<body onload="window.opener.location='<%= location %>'; window.close();">
<%
  }
%>
</body>
</html>
