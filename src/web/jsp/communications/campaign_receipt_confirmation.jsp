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
  - Version: $Id: campaign_receipt_confirmation.jsp
  - Description: 
  --%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,org.aspcfs.modules.communications.base.*" %>
<jsp:useBean id="scheduledRecipient" class="org.aspcfs.modules.communications.base.ScheduledRecipient" scope="request"/>
<jsp:useBean id="status" class="java.lang.String" scope="request"/>
<%@ include file="../initPage.jsp" %>
<html>
<head>
  <title><dhv:label name="campaign.receipt.confirmation">Campaign Receipt Confirmation</dhv:label></title>
</head>
<body>
&nbsp;<br>
&nbsp;<br>
<center>
<table cellpadding="4" cellspacing="0" border="0" width="85%">
  <tr>
    <td>
      <dhv:evaluate if='<%= status.equals("OK") %>'>
        <dhv:label name="campaign.receipt.confirmation.thankyoutext">Thank you for the campaign confirmation. Your confirmation was received on:</dhv:label> <%= scheduledRecipient.getReplyDate() %>
      </dhv:evaluate>
      <dhv:evaluate if='<%= status.equals("EXISTS") %>'>
        <dhv:label name="campaign.receipt.confirmation.text">Your confirmation has already been received on:</dhv:label> <%= scheduledRecipient.getReplyDate() %>
      </dhv:evaluate>
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
