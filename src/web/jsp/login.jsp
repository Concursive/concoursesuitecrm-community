<jsp:useBean id="LoginBean" class="com.darkhorseventures.cfsmodule.LoginBean" scope="request"/>
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
<html>
<head>
<title>Dark Horse Ventures L.L.C. ASPCFS</title>
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
    <td height="10%" valign="top"> 
      <table width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr> 
          <td rowspan="3" valign="top"><img src="images/dhv.gif" width="339" height="106"></td>
          <td width="61%"><img src="images/spaceb1.gif" width="100%" height="65"></td>
          <td width="39%" background="images/spaceb1.gif"><img src="images/keys.gif" width="445" height="65"></td>
        </tr>
        <tr> 
          <td width="61%"><img src="images/spaceb2.gif" width="100%" height="30"></td>
          <td width="39%" background="images/spaceb2.gif"><img src="images/textspace.gif" width="18" height="30"><img src="images/textspace.gif" width="18" height="30"><img src="images/textspace.gif" width="18" height="30"><img src="images/textspace.gif" width="18" height="30"><img src="images/textspace.gif" width="18" height="30"><img src="images/textspace.gif" width="18" height="30"></td>
        </tr>
        <tr> 
          <td width="61%"><img src="images/spaceg1.gif" width="100%" height="11"></td>
          <td width="39%"><img src="images/green.gif" width="446" height="11"></td>
        </tr>
      </table>
    </td>
  </tr>
  <tr valign="top"> 
    <td height="85%"> 
      <table width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr> 
          <td width="9%" height="26"><img src="images/sidespace.gif" width="97" height="1"></td>
          <td width="1%" height="26">&nbsp;</td>
        </tr>
        <tr>
          <td width="9%">&nbsp;</td>
          <td width="1%">&nbsp;</td>
          <td width="15%" valign="top">&nbsp;</td>
          <td width="30%">&nbsp;</td>
        </tr>
        <tr> 
          <form name="login" method="POST" action="Login.do?command=Login&auto-populate=true">
            <table width="100%" align="left" cellspacing="1" cellpadding="3" border="0">
              <tr>
                <td valign="center" colspan="4">&nbsp;</td>
              </tr>
              <tr>
                <td align="center" valign="center" colspan="4">
                  <font face="Arial, Helvetica, sans-serif" size="2"><strong>ASPCFS <%= ("https".equals(request.getScheme())?"Secure ":"") %>Login</strong></font>
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
                  <font face="Arial, Helvetica, sans-serif" size="2"><a href="https://<%= request.getServerName() %>">Go to Secure Login</a></font>
                </td>
              </tr>
<%} else {%>
              <tr>
                <td align="center" colspan="4">
                  <center><font face="Arial, Helvetica, sans-serif" size="2" color='red'><%= LoginBean.getMessage() %></font></center>
                </td>
              </tr>
              <tr>
                <td width="33%">&nbsp;</td>
                <td valign="top" align="right" width="100">
                  <font face="Arial, Helvetica, sans-serif" size="2">Username:</font>
                </td>
                <td valign="top" align="left" width="200">
                  <font face="Arial, Helvetica, sans-serif" size="2"><input type="text" name="username" value="<%= LoginBean.getUsername() %>" size="20">&nbsp;</font>
                </td>
                <td width="33%">&nbsp;</td>
              </tr>
              <tr>
                <td width="33%">&nbsp;</td>
                <td valign="top" align="right" width="100">
                  <font face="Arial, Helvetica, sans-serif" size="2">Password:</font>
                </td>
                <td valign="top" align="left" width="200">
                  <font face="Arial, Helvetica, sans-serif" size="2"><input type="password" name="password" size="20"></font>
                </td>
                <td width="33%">&nbsp;</td>
              </tr>
              <tr>
                <td width="23%">&nbsp;</td>
                <td valign="center" align="right" width="100">&nbsp;</td>
                <td valign="center" align="left">
                  <input type="submit" value="Login" name="action">
                  <input type="reset" value="Reset">
                </td>
                <td width="43%">&nbsp;</td>
              </tr>
<%}%>
            </table>
          </form>
        </tr>
      </table>
    </td>
  </tr>
  <tr> 
    <td height="5%" valign="bottom"> 
      <table width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td width="33%"><img src="images/bottom.gif" width="339" height="35"></td>
          <td width="34%"><img src="images/bottomspace.gif" width="100%" height="35"></td>
          <td width="33%"><div align="right"><img src="images/bottomcopyright.gif" width="339" height="35"></div></td>
        </tr>
      </table>
    </td>
  </tr>
</table>
</body>
</html>


