<%@ page import="java.util.*,org.aspcfs.modules.*,org.aspcfs.controller.*,org.aspcfs.utils.*" %>
<%
  response.setHeader("Pragma", "no-cache"); // HTTP 1.0
  response.setHeader("Cache-Control", "no-cache"); // HTTP 1.1
%>
<% 
  Hashtable globalStatus = (Hashtable)getServletConfig().getServletContext().getAttribute("SystemStatus");
  ConnectionElement ce = (ConnectionElement)request.getSession().getAttribute("ConnectionElement");
  if (ce == null) {
%>
  <font color="red">You must be logged in.</font><br>
<%
  } else {
    SystemStatus systemStatus = (SystemStatus)globalStatus.get(ce.getUrl());
%>
  
  There are currently <%= globalStatus.size() %> sites cached<br>
  Reset the following: <%= ce.getDbName() %><br>
<%
    if (systemStatus == null) {
%>
      Error: Object is null<br>
<%
    } else {
      systemStatus = null;
      globalStatus.remove(ce.getUrl());
%>
    Status: OK<br>
<%
    }

    HttpSession oldSession = request.getSession(false);
    if (oldSession != null) {
      oldSession.invalidate();
    }


  }
%>
<a href="/Login.do?command=Logout">Login</a>
