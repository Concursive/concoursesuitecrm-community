<%@ page import="java.util.*,com.darkhorseventures.cfsbase.*,com.darkhorseventures.controller.*,com.darkhorseventures.utils.*" %>
<%!
  public boolean isManagerOf(javax.servlet.jsp.PageContext context, int managerId, int userId) {
    Hashtable globalStatus = (Hashtable)context.getServletConfig().getServletContext().getAttribute("SystemStatus");
    ConnectionElement ce = (ConnectionElement)context.getSession().getAttribute("ConnectionElement");
    SystemStatus systemStatus = (SystemStatus) ((Hashtable)context.getServletConfig().getServletContext().getAttribute("SystemStatus")).get(ce.getUrl());
    return (systemStatus.getHierarchyList().getUser(managerId).isManagerOf(userId));
  }
%>
