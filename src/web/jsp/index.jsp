<%
  //During Servlet initialization, the setup parameter is set if the application
  //is completely configured
  if ((Object) getServletConfig().getServletContext().getAttribute("cfs.setup") == null) {
    RequestDispatcher setup = getServletContext( ).getRequestDispatcher("/Setup.do?command=Default");
    setup.forward(request, response);
  }
  String scheme = request.getScheme();
  //If SSL is configured, but this user isn't using SSL, then go to the welcome page
  if ("true".equals((String) getServletConfig().getServletContext().getAttribute("ForceSSL")) && 
     scheme.equals("http")) {
%>
<jsp:include page="welcome.jsp" flush="true"/>
<%
  } else {
    //If SSL is configured, and this user is using SSL, then go to the login page
%>
<jsp:include page="login.jsp" flush="true"/>
<%  
  }
%>
