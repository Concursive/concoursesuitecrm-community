<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<jsp:useBean id="LoginBean" class="org.aspcfs.modules.login.beans.LoginBean" scope="request"/>
<jsp:useBean id="APP_TEXT" class="java.lang.String" scope="application"/>
<jsp:useBean id="APP_ORGANIZATION" class="java.lang.String" scope="application"/>
<%!
  public static String getLongDate(java.util.Date tmp) {
    java.text.SimpleDateFormat formatter1 = new java.text.SimpleDateFormat ("MMMMM d, yyyy");
    return(formatter1.format(tmp));
  }
%>
<%
  response.setHeader("Pragma", "no-cache"); // HTTP 1.0
  response.setHeader("Cache-Control", "no-cache"); // HTTP 1.1
  response.setHeader("Expires", "-1");
%>
<%@ include file="initPage.jsp" %>
<html>
<head>
<title>Centric CRM</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
</head>
<link rel="stylesheet" href="css/template-login.css" type="text/css">
<script language="JavaScript">
  function focusForm(form) {
      form.username.focus();
      return false;
  }
</script>
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" onLoad="<%
  if (request.getParameter("popup") != null) {
    out.println("window.opener.location='MyCFS.do?command=Home'; window.close();");
  } else if (request.getParameter("inline") != null) {
    out.println("window.parent.location='MyCFS.do?command=Home'");
  } else if (LoginBean.getUsername().equals("")) {
    out.println("document.login.username.focus()");
  } else {
    out.println("document.login.password.focus()");
  }
%>">
<table width="100%" border="0" cellspacing="0" cellpadding="0" height="100%">
  <tr>
    <td height="10%" valign="top" width="100%">
      &nbsp;
    </td>
  </tr>
  <tr>
    <td height="85%" width="100%" valign="top">
      <%-- Content --%>
      <table width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr> 
          <td width="9%" height="26"><img src="images/sidespace.gif" width="97" height="1"></td>
          <td width="1%" height="26" colspan="3">&nbsp;</td>
        </tr>
        <tr>
          <td colspan="4">
          <form name="login" method="POST" action="Login.do?command=Login&auto-populate=true">
            <table width="100%" cellspacing="1" cellpadding="3" border="0">
              <tr>
                <td colspan="4" align="center"><img src="images/centric/logo-centric.gif" width="295" height="66" alt="" border="0" /></td>
              </tr>
              <tr>
                <td align="center" valign="center" colspan="4">
                <table style="border:1px #EFEFEF solid;background: #EFEFEF" align="center" width="50%">
                <tr><td align="center">
                  <font size="2"><strong>Centric CRM <%= ("https".equals(request.getScheme())?"Secure ":"") %>Login</strong><br />
                  <%= toHtml(APP_TEXT) %><dhv:evaluate if="<%= hasText(APP_ORGANIZATION) %>"><br />Licensed To: <%= toHtml(APP_ORGANIZATION) %></dhv:evaluate></font>
                </td></tr>
                </table>
                </td>
              </tr>
<%
  String scheme = request.getScheme();
  if ("true".equals((String)getServletConfig().getServletContext().getAttribute("ForceSSL")) && 
     scheme.equals("http")) {
%>              
              <tr> 
                <td align="center" colspan="4">
                  <font size="2">This site is configured for secure connections only</font><br />
                  <font size="2"><a href="https://<%= getServerUrl(request) %>">Go to Secure Login</a></font>
                </td>
              </tr>
<%} else {%>
              <tr>
                <td align="center" colspan="4">
                
              <table style="border:1px #EFEFEF solid" align="center" width="50%">
              <tr><td colspan="2">&nbsp;</td></tr>
<dhv:evaluate if="<%= hasText(LoginBean.getMessage()) %>">
              <tr>
                <td align="center" colspan="2">
                  <center><font size="2" color='red'><%= LoginBean.getMessage() %></font></center>
                </td>
              </tr>
</dhv:evaluate>
              <tr><td align="center">
              <table border="0">
              <tr>
                <td align="right">
                  <font size="2">Username:</font>
                </td>
                <td>
                  <font size="2"><input type="text" name="username" value="<%= LoginBean.getUsername() %>" size="20">&nbsp;</font>
                </td>
              </tr>
              <tr>
                <td align="right">
                  <font size="2">Password:</font>
                </td>
                <td>
                  <font size="2"><input type="password" name="password" size="20"></font>
                </td>
              </tr>
              <tr>
                <td align="right">&nbsp;</td>
                <td>
                  <input type="submit" value="Login" name="action">
                </td>
              </tr>
              </table>
              </td></tr>
              <tr><td colspan="2">&nbsp;</td></tr>
              </table>
              </td>
            </tr>
<%}%>
            </table>
          </form>
          </td>
        </tr>
      </table>
    </td>
  </tr>
  <tr> 
    <td height="5%" valign="top" width="100%">
      <%-- Copyright --%>
      <table width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td align="center">&#169; Copyright 2000-2004 Dark Horse Ventures, LLC &#149; All rights reserved.</td>
        </tr>
      </table>
    </td>
  </tr>
</table>
</body>
</html>


