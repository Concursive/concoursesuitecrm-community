<jsp:useBean id="LoginBean" class="org.aspcfs.modules.login.beans.LoginBean" scope="request"/>
<jsp:useBean id="APP_TEXT" class="java.lang.String" scope="application"/>
<%!
  public static String getLongDate(java.util.Date tmp) {
    java.text.SimpleDateFormat formatter1 = new java.text.SimpleDateFormat ("MMMMM d, yyyy");
    return(formatter1.format(tmp));
  }
%>
<%
  response.setHeader("Pragma", "no-cache"); // HTTP 1.0
  response.setHeader("Cache-Control", "no-cache"); // HTTP 1.1
%>
<%@ include file="initPage.jsp" %>
<html>
<head>
<title>Dark Horse CRM</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
</head>
<script language="JavaScript">
  function focusForm(form) {
      form.username.focus();
      return false;
  }
</script>
<body bgcolor="#FFFFFF" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" onLoad="<%
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
      <%-- Logo --%>
      <table width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td><img src="images/dhv1.gif" width="339" height="65"></td>
          <td width="100%"><img src="images/spaceb1.gif" width="100%" height="65"></td>
          <td><img src="images/keys.gif" width="326" height="65"></td>
        </tr>
        <tr>
          <td><img src="images/dhv2.gif" width="339" height="30"></td>
          <td width="100%"><img src="images/spaceb2.gif" width="100%" height="30"></td>
          <td><img src="images/spaceb2.gif" width="326" height="30"></td>
        </tr>
        <tr>
          <td><img src="images/dhv3.gif" width="339" height="11"></td>
          <td width="100%"><img src="images/spaceg1.gif" width="100%" height="11"></td>
          <td><img src="images/green.gif" width="326" height="11"></td>
        </tr>
      </table>
    </td>
  </tr>
  <tr>
    <td height="85%" width="100%" valign="top">
      <%-- Content --%>
      <table width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr> 
          <td width="9%" height="26"><img src="images/sidespace.gif" width="97" height="1"></td>
          <td width="1%" height="26">&nbsp;</td>
        </tr>
        <tr>
          <td colspan="4">
          <form name="login" method="POST" action="Login.do?command=Login&auto-populate=true">
            <table width="100%" cellspacing="1" cellpadding="3" border="0">
              <tr>
                <td colspan="4">&nbsp;</td>
              </tr>
              <tr>
                <td align="center" valign="center" colspan="4">
                  <font face="Arial, Helvetica, sans-serif" size="2"><strong>Dark Horse CRM <%= ("https".equals(request.getScheme())?"Secure ":"") %>Login</strong><br>
                  <%= toHtml(APP_TEXT) %></font>
                </td>
              </tr>
<%
  String scheme = request.getScheme();
  if ("true".equals((String)getServletConfig().getServletContext().getAttribute("ForceSSL")) && 
     scheme.equals("http")) {
%>              
              <tr> 
                <td align="center" colspan="4">
                  <font face="Arial, Helvetica, sans-serif" size="2">This site is configured for secure connections only</font><br>
                  <font face="Arial, Helvetica, sans-serif" size="2"><a href="https://<%= getServerUrl(request) %>">Go to Secure Login</a></font>
                </td>
              </tr>
<%} else {%>
              <tr>
                <td align="center" colspan="4">
                  <center><font face="Arial, Helvetica, sans-serif" size="2" color='red'><%= LoginBean.getMessage() %></font></center>
                </td>
              </tr>
              <tr>
                <td align="center" colspan="4">
                
              <table style="border:1px #EFEFEF solid" align="center" width="50%">
              <tr><td colspan="2">&nbsp;</td></tr>
              <tr><td align="center">
                
              <table border="0">
              <tr>
                <td align="right">
                  <font face="Arial, Helvetica, sans-serif" size="2">Username:</font>
                </td>
                <td>
                  <font face="Arial, Helvetica, sans-serif" size="2"><input type="text" name="username" value="<%= LoginBean.getUsername() %>" size="20">&nbsp;</font>
                </td>
              </tr>
              <tr>
                <td align="right">
                  <font face="Arial, Helvetica, sans-serif" size="2">Password:</font>
                </td>
                <td>
                  <font face="Arial, Helvetica, sans-serif" size="2"><input type="password" name="password" size="20"></font>
                </td>
              </tr>
              <tr>
                <td align="right">&nbsp;</td>
                <td>
                  <input type="submit" value="Login" name="action">
                  <input type="reset" value="Reset">
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
          <td><img src="images/bottom.gif" width="120" height="35"></td>
          <td width="100%"><img src="images/bottomspace.gif" width="100%" height="35"></td>
          <td align="right"><img src="images/bottomcopyright.gif" width="339" height="35"></td>
        </tr>
      </table>
    </td>
  </tr>
</table>
</body>
</html>


