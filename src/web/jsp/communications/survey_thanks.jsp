<jsp:useBean id="ThankYouText" class="java.lang.String" scope="request"/>
<%@ include file="../initPage.jsp" %>
<html>
<head>
  <title>Thank you for visiting our survey page</title>
</head>
<body>
&nbsp;<br>
&nbsp;<br>
<center>
<table cellpadding="4" cellspacing="0" border="0" width="85%" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr class="row1">
    <td>
      <font color="#8c8c8c"><strong>Web Survey</strong></font>
    </td>
  </tr>
</table>
&nbsp;<br>
<table cellpadding="4" cellspacing="0" border="0" width="85%" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr>
    <td>
      <strong>Survey Submitted</strong>
    </td>
  </tr>
  <tr>
    <td>
      <%= toHtml(ThankYouText) %> <br>
    </td>
  </tr>
</table>
&nbsp;<br>
<table cellpadding="4" cellspacing="0" border="0" width="85%" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr class="row1">
    <td align="center">
      <font color="#8c8c8c"><strong>(C) 2002 Dark Horse Ventures, LLC</strong></font>
    </td>
  </tr>
</table>
</center>
</body>
</html>
