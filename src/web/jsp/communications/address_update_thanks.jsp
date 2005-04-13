<%-- 
  - Copyright(c) 2004 Dark Horse Ventures LLC (http://www.centriccrm.com/) All
  - rights reserved. This material cannot be distributed without written
  - permission from Dark Horse Ventures LLC. Permission to use, copy, and modify
  - this material for internal use is hereby granted, provided that the above
  - copyright notice and this permission notice appear in all copies. DARK HORSE
  - VENTURES LLC MAKES NO REPRESENTATIONS AND EXTENDS NO WARRANTIES, EXPRESS OR
  - IMPLIED, WITH RESPECT TO THE SOFTWARE, INCLUDING, BUT NOT LIMITED TO, THE
  - IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR ANY PARTICULAR
  - PURPOSE, AND THE WARRANTY AGAINST INFRINGEMENT OF PATENTS OR OTHER
  - INTELLECTUAL PROPERTY RIGHTS. THE SOFTWARE IS PROVIDED "AS IS", AND IN NO
  - EVENT SHALL DARK HORSE VENTURES LLC OR ANY OF ITS AFFILIATES BE LIABLE FOR
  - ANY DAMAGES, INCLUDING ANY LOST PROFITS OR OTHER INCIDENTAL OR CONSEQUENTIAL
  - DAMAGES RELATING TO THE SOFTWARE.
  - 
  - Version: $Id$
  - Description: 
  --%>
<jsp:useBean id="ThankYouText" class="java.lang.String" scope="request"/>
<%@ include file="../initPage.jsp" %>
<html>
<head>
  <title>Contact Information Update</title>
</head>
<body>
&nbsp;<br>
&nbsp;<br>
<center>
<table cellpadding="4" cellspacing="0" border="0" width="85%">
  <tr class="row1">
    <td>
      <font color="#8c8c8c"><strong>Contact Information Update</strong></font>
    </td>
  </tr>
</table>
&nbsp;<br>
<table cellpadding="4" cellspacing="0" border="0" width="85%">
  <tr>
    <td>
      <strong>Contact Information Submitted</strong>
    </td>
  </tr>
  <tr>
    <td>
      <%--<%= toHtml(ThankYouText) %>--%>
      Thank you for informing us about changes in your contact information.<br>
    </td>
  </tr>
</table>
&nbsp;<br>
<table cellpadding="4" cellspacing="0" border="0" width="85%">
  <tr class="row1">
    <td align="center">
      <font color="#8c8c8c"><strong>(C) 2000-2005 Dark Horse Ventures, LLC</strong></font>
    </td>
  </tr>
</table>
</center>
</body>
</html>
