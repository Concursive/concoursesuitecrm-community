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
      location = "AccountsDocuments.do?command=View&orgId="+ param;
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
