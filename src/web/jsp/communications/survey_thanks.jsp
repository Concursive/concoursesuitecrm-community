<%-- 
  - Copyright(c) 2004 Concursive Corporation (http://www.concursive.com/) All
  - rights reserved. This material cannot be distributed without written
  - permission from Concursive Corporation. Permission to use, copy, and modify
  - this material for internal use is hereby granted, provided that the above
  - copyright notice and this permission notice appear in all copies. CONCURSIVE
  - CORPORATION MAKES NO REPRESENTATIONS AND EXTENDS NO WARRANTIES, EXPRESS OR
  - IMPLIED, WITH RESPECT TO THE SOFTWARE, INCLUDING, BUT NOT LIMITED TO, THE
  - IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR ANY PARTICULAR
  - PURPOSE, AND THE WARRANTY AGAINST INFRINGEMENT OF PATENTS OR OTHER
  - INTELLECTUAL PROPERTY RIGHTS. THE SOFTWARE IS PROVIDED "AS IS", AND IN NO
  - EVENT SHALL CONCURSIVE CORPORATION OR ANY OF ITS AFFILIATES BE LIABLE FOR
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
  <title><dhv:label name="campaign.surveyPage.thankYou">Thank you for visiting our survey page</dhv:label></title>
</head>
<body>
&nbsp;<br>
&nbsp;<br>
<center>
<table cellpadding="4" cellspacing="0" border="0" width="85%">
  <tr class="row1">
    <td>
      <font color="#8c8c8c"><strong><dhv:label name="campaign.webSurvey">Web Survey</dhv:label></strong></font>
    </td>
  </tr>
</table>
&nbsp;<br>
<table cellpadding="4" cellspacing="0" border="0" width="85%">
  <tr>
    <td>
      <strong><dhv:label name="campaign.surveySubmitted">Survey Submitted</dhv:label></strong>
    </td>
  </tr>
  <tr>
    <td>
      <%= toHtml(ThankYouText) %> <br>
    </td>
  </tr>
</table>
&nbsp;<br>
<table cellpadding="4" cellspacing="0" border="0" width="85%">
  <tr class="row1">
    <td align="center">
      <font color="#8c8c8c"><strong>(C) 2000-2005 Concursive Corporation</strong></font>
    </td>
  </tr>
</table>
</center>
</body>
</html>
