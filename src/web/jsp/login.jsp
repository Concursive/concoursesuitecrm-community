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
<HEAD>
<title>Customer Facing Systems Login</title>
<STYLE TYPE="text/css">
<!--
H1 {
      font-weight: bold;
      font-size: 18pt;
      line-height: 18pt;
      font-family: arial,helvetica,sans-serif;
      font-variant: normal;
      font-style: normal;
  }
table, body, p,td,li{font-family:arial,helvetica,sans-serif;font-size=10pt;}
th{font-family:arial,helvetica,sans-serif;font-size=10pt; background-color:#3366cc; color:#ffffff;}

.th2{font-family:arial,helvetica,sans-serif;font-size=12pt; background-color:#8888ff; color:#ffffff;}

.flat{border:0;background-color:#cccccc;color:#000066;}
.flat2{border:0;background-color:#ffffee;color:#000066;}
-->
</STYLE>
</HEAD>
<SCRIPT language="JavaScript">
  function focusForm(form) {
      form.username.focus();
      return false;
  }
</SCRIPT>
<body bgcolor="white" onLoad="<%
  if (LoginBean.getUsername().equals("")) {
    out.println("document.login.username.focus()");
  } else {
    out.println("document.login.password.focus()");
  }
%>">
<!-- XX MAIN HEADER -->
<table width=98% align=center cellspacing=0 cellpadding=0 border=0>
	<tr rowspan=3>
    <!-- LOGO COLUMN -->
    <td valign=center width=150 align=center bgcolor="#eeeeee">
      <a href="./main.html"><img src="images/horsetry.gif" width="70" height="88" border=0></a>
    </td>
    <td valign=bottom align=left width=60%>
      &nbsp;&nbsp;<H1>&nbsp;Login</H1>
    </td>
    <td valign=bottom align=right>
      <a href="/login.html?action=Logout" style="background-color:#ffffff;"></a>&nbsp;&nbsp;<p>
    </td>
	</tr>
</table>
<!-- XX BAR -->
<table width=98% align=center cellspacing=0 cellpadding=0 border=0>
  <tr bgcolor="#000066">
    <td align=right valign=center><font color=white><%= getLongDate(new java.util.Date()) %></font>&nbsp;</td>
  </tr>
</table>
<!-- END BAR -->
<table width=98% align=center cellspacing=1 cellpadding=0 border=0>
<form name="login" method="POST" action="Login.do?command=Login&auto-populate=true">
	<tr rowspan=2>
    <td width=150 valign=top bgcolor="#eeeeee">&nbsp;
  </td>
	<td valign=center>
    <table width=100% align=left cellspacing=1 cellpadding=3 border=0>
      <tr>
        <td valign=center>&nbsp;</td><td valign=center>&nbsp;</td></tr>
      <tr>
        <td valign=center align=right width="100">
          Username:
        </td>
        <td valign=center align=left>
          <input type="text" name="username" value = "<%= LoginBean.getUsername() %>" size="20">&nbsp;<font color='red'><%= LoginBean.getMessage() %></font>
        </td>
      </tr>
      <tr>
        <td valign=center align=right width="100">
          Password:
        </td>
        <td valign=center align=left>
          <input type="password" name="password" size="20">
        </td>
      </tr>
      <tr>
        <td valign=center align=right width="100">
        </td>
        <td valign=center align=left>
          <input type="submit" value="Login" name="action">
          <input type="submit" value="Clear" name="action">
        </td>
      </tr>
    </table>
	</td>
  </tr>
</form>
</table>

</body>

</html>

