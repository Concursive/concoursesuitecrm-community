<%--
  @author     Matt Rajkowski
  @created    March 10, 2003
  @version    $Id$
  
  Description: An include that configures CSS for all pages, whether
  the user is logged in or not, depending on browser, version, and os.
--%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="org.aspcfs.modules.login.beans.UserBean" %>
<%@ page import="org.aspcfs.utils.web.ClientType" %>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<%
  if (User.getClientType() == null) {
    ClientType clientType = new ClientType(request);
    User.setClientType(clientType);
  }
  //Based on Red Hat releases, RH9 Mozilla 1.2.1 was the first to use XFT
  if ("moz".equals(User.getBrowserId()) &&
      ("linux".equals(User.getClientType().getOsString()) && 
       User.getBrowserVersion() < 1.2 )) {
%>
<link rel="stylesheet" href="css/template0-10pt.css" type="text/css" media="screen">
<%
  } else {
%>
<link rel="stylesheet" href="css/template0-8pt.css" type="text/css" media="screen">
<%  
  }
%>
<link rel="stylesheet" href="css/template0.css" type="text/css" media="screen">
<link rel="stylesheet" href="css/print.css" type="text/css" media="print">
