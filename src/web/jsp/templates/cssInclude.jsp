<%-- 
  - Copyright Notice: (C) 2000-2004 Dark Horse Ventures, All Rights Reserved.
  - License: This source code cannot be modified, distributed or used without
  -          written permission from Dark Horse Ventures. This notice must
  -          remain in place.
  - Version: $Id$

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
<jsp:useBean id="applicationPrefs" class="org.aspcfs.controller.ApplicationPrefs" scope="application"/>
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
<link rel="stylesheet" href="css/<%= applicationPrefs.get("LAYOUT.TEMPLATE") %>-10pt.css" type="text/css" media="screen">
<%
  } else {
%>
<link rel="stylesheet" href="css/<%= applicationPrefs.get("LAYOUT.TEMPLATE") %>-8pt.css" type="text/css" media="screen">
<%  
  }
%>
<link rel="stylesheet" href="css/<%= applicationPrefs.get("LAYOUT.TEMPLATE") %>.css" type="text/css">
<link rel="stylesheet" href="css/print.css" type="text/css" media="print">
