<%@ page import="org.aspcfs.modules.system.base.ApplicationVersion" %>
<jsp:useBean id="applicationPrefs" class="org.aspcfs.controller.ApplicationPrefs" scope="application"/>
<%
  // During Servlet initialization, the setup parameter is set if the application
  // is completely configured
  if ((Object) getServletConfig().getServletContext().getAttribute("cfs.setup") == null) {
    RequestDispatcher setup = getServletContext().getRequestDispatcher("/Setup.do?command=Default");
    setup.forward(request, response);
  }
  // BEGIN DHV CODE ONLY
  // If the site is setup, then check to see if this is an upgraded version of the app
  if (applicationPrefs.isUpgradeable()) {
    RequestDispatcher upgrade = getServletContext().getRequestDispatcher("/Upgrade.do?command=Default");
    upgrade.forward(request, response);
  }
  // END DHV CODE ONLY
  String scheme = request.getScheme();
  // If SSL is configured, but this user isn't using SSL, then go to the welcome page
  if ("true".equals((String) getServletConfig().getServletContext().getAttribute("ForceSSL")) && 
      scheme.equals("http")) {
%>
  <jsp:include page="<%= applicationPrefs.get("LAYOUT.JSP.WELCOME") %>" flush="true"/>
<%
  } else {
    // If SSL is configured, and this user is using SSL, then go to the login page
%>
  <jsp:include page="<%= applicationPrefs.get("LAYOUT.JSP.LOGIN") %>" flush="true"/>
<%
  }
%>
