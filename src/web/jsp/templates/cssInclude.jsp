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
  //Mozilla: Mac (any version), Linux (1.2)
  if ("moz".equals(User.getBrowserId()) &&
      ("mac".equals(User.getClientType().getOsString()) ||
      ("linux".equals(User.getClientType().getOsString()) && 
       User.getBrowserVersion() == 1.2 ))) {
%>
<link rel="stylesheet" href="css/template0-10pt.css" type="text/css">
<%
  } else {
%>
<link rel="stylesheet" href="css/template0-8pt.css" type="text/css">
<%  
  }
%>
<link rel="stylesheet" href="css/template0.css" type="text/css">

