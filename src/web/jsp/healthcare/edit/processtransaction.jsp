<%--
  - Copyright Notice: (C) 2000-2004 Dark Horse Ventures, All Rights Reserved.
  - License: This source code cannot be modified, distributed or used without
  -          written permission from Dark Horse Ventures. This notice must
  -          remain in place.
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
    First Name
    </td>
    <td>
    <%=toHtml(Transaction.getNameFirst())%>
    </td>
    </tr>
    
    <tr><td width="125" nowrap class="formlabel">
    Last Name
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
