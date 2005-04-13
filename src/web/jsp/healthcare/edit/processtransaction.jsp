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
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<jsp:useBean id="Transaction" class="org.aspcfs.modules.healthcare.edit.base.TransactionRecord" scope="request"/>
<%@ include file="../../initPage.jsp" %>
<html>
<head>
<title>EDIT: Process Billing Transaction</title>
<body>
<% if (request.getAttribute("errors") == null) { %>
    Transaction Record processed successfully.
    <p>
    <table width="98%" cellspacing="0" cellpadding="2">
    
    <tr class="row0"><td width="125" nowrap class="formlabel">
    Tax ID
    </td>
    <td>
    <%=toHtml(Transaction.getTaxId())%>
    </td>
    
    <tr><td width="125" nowrap class="formlabel">
    License Number
    </td>
    <td>
    <%=toHtml(Transaction.getLicenseNumber())%>
    </td>

    <tr><td width="125" nowrap class="formlabel">
    NPI
    </td>
    <td>
    <%=toHtml(Transaction.getNpi())%>
    </td>

    <tr><td width="125" nowrap class="formlabel">
    Provider ID
    </td>
    <td>
    <%=toHtml(Transaction.getProviderId())%>
    </td>    
    
    <tr><td width="125" nowrap class="formlabel">
    <dhv:label name="accounts.accounts_add.FirstName">First Name</dhv:label>
    </td>
    <td>
    <%=toHtml(Transaction.getNameFirst())%>
    </td>
    </tr>
    
    <tr><td width="125" nowrap class="formlabel">
    <dhv:label name="accounts.accounts_add.LastName">Last Name</dhv:label>
    </td>
    <td>
    <%=toHtml(Transaction.getNameLast())%>
    </td></tr>
    
    <tr><td width="125" nowrap class="formlabel">
    Payer ID
    </td>
    <td>
    <%=toHtml(Transaction.getPayerId())%>
    </td></tr>

    <tr><td width="125" nowrap class="formlabel">
    Transaction Type
    </td>
    <td>
    <%=toHtml(Transaction.getType())%>
    </td></tr>

    <tr><td width="125" nowrap class="formlabel">
    Transaction ID
    </td>
    <td>
    <%=toHtml(Transaction.getTransactionId())%>
    </td></tr>    
    
    <tr><td width="125" nowrap class="formlabel">
    Performed Date/Time
    </td>
    <td>
    <%=toHtml(Transaction.getPerformedString())%>
    </td></tr>
   
    </table>
<%} else {%>
  Transaction could not be processed.  Please check the following:
  <p>
  
  <% if (request.getAttribute("parametersError") != null) { %>
  <%= showAttribute(request, "parametersError") %><br>
  <%}%>
  
  <% if (request.getAttribute("taxIdError") != null) { %>
  <%= showAttribute(request, "taxIdError") %><br>
  <%}%>
  
  <% if (request.getAttribute("licenseNumberError") != null) { %>
  <%= showAttribute(request, "licenseNumberError") %><br>
  <%}%>
  
  <% if (request.getAttribute("nameLastError") != null) { %>
  <%= showAttribute(request, "nameLastError") %><br>
  <%}%>
  
  <% if (request.getAttribute("payerIdError") != null) { %>
  <%= showAttribute(request, "payerIdError") %><br>
  <%}%>
  
  <% if (request.getAttribute("typeError") != null) { %>
  <%= showAttribute(request, "typeError") %><br>
  <%}%>
  
  <% if (request.getAttribute("transactionIdError") != null) { %>
  <%= showAttribute(request, "transactionIdError") %><br>
  <%}%>
  
  <% if (request.getAttribute("transactionDateError") != null) { %>
  <%= showAttribute(request, "transactionDateError") %><br>
  <%}%>
  
  <% if (request.getAttribute("transactionTimeError") != null) { %>
  <%= showAttribute(request, "transactionTimeError") %><br>
  <%}%>
  
<%}%>
</body>
</html>
