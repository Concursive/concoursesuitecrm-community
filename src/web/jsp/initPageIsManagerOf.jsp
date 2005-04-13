<%@ page import="java.util.*,org.aspcfs.controller.SystemStatus,com.darkhorseventures.database.ConnectionElement,org.aspcfs.utils.*" %>
<%!
  public boolean isManagerOf(javax.servlet.jsp.PageContext context, int managerId, int userId) {
    ConnectionElement ce = (ConnectionElement)context.getSession().getAttribute("ConnectionElement");
    SystemStatus systemStatus = (SystemStatus) ((Hashtable)context.getServletConfig().getServletContext().getAttribute("SystemStatus")).get(ce.getUrl());
    return (systemStatus.getHierarchyList().getUser(managerId).isManagerOf(userId));
  }
%>
