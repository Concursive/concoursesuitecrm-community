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
%>
<dhv:browser id="moz" maxVersion="1.2" os="linux">
  <link rel="stylesheet" href="css/template0ns-linux.css" type="text/css">
</dhv:browser>
<dhv:browser id="moz" maxVersion="1.2" os="linux" include="false">
  <link rel="stylesheet" href="css/template0ie.css" type="text/css">
</dhv:browser>
<link rel="stylesheet" href="css/template0.css" type="text/css">

