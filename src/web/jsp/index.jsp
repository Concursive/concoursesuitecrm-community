<%-- Depending on browser type --%>
<%
  String scheme = request.getScheme();
  if ("true".equals((String)getServletConfig().getServletContext().getAttribute("ForceSSL")) && 
     scheme.equals("http")) {
%>
<jsp:include page="welcome.jsp" flush="true"/>
<%
  } else {
%>
<jsp:include page="login.jsp" flush="true"/>
<%  
  }
%>
