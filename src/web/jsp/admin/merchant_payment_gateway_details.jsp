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
  - Version: $Id: product_catalog_details.jsp 15638 2006-08-10 13:29:42Z Olga.Kaptyug@corratech.com $
  - Description: 
  --%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ page import="java.util.*" %>
<jsp:useBean id="merchantPaymentGateway" class="org.aspcfs.modules.admin.base.MerchantPaymentGateway" scope="request"/>
<%@ include file="../initPage.jsp" %>
 <%-- Trails --%>
  <table class="trails" cellspacing="0">
    <tr>
      <td>
        <a href="Admin.do"><dhv:label name="trails.admin">Admin</dhv:label></a> > <a href="AdminConfig.do?command=ListGlobalParams"><dhv:label name="admin.configureSystem">Configure System</dhv:label></a> >
        <dhv:label name="admin.modifySetting">Modify Setting</dhv:label>
      </td>
    </tr>
  </table>
  <%-- End Trails --%>
<table border="0" cellpadding="1" cellspacing="0" width="100%">
  <tr class="abovetab">
    <td>
      <dhv:label name="admin.merchantPaymentGatewayDetails">Merchant Payment Gateway Details</dhv:label>
    </td>
  </tr>
</table>

  <table cellpadding="4" cellspacing="0" border="0" width="100%">
    <tr>
      <td>
        <table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
          <tr>
            <th colspan="2">
              <strong><dhv:label name="accounts.accounts_details.PrimaryInformation">Primary Information</dhv:label></strong>
            </th>
          </tr>
        <dhv:evaluate if="<%= hasText(merchantPaymentGateway.getGatewayName()) %>">
          <tr class="containerBody">
            <td class="formLabel">
              <dhv:label name="admin.paymentGateway">Payment Gateway</dhv:label>
            </td>
            <td><%= toHtml(merchantPaymentGateway.getGatewayName()) %></td>
          </tr>
        </dhv:evaluate>

        <dhv:evaluate if="<%= hasText(merchantPaymentGateway.getMerchantId()) %>">
          <tr class="containerBody">
            <td class="formLabel">
              <dhv:label name="admin.merchantId">Merchant Id</dhv:label>
            </td>
            <td><%= toHtml(merchantPaymentGateway.getMerchantId()) %></td>
          </tr>
        </dhv:evaluate>
        <dhv:evaluate if="<%= hasText(merchantPaymentGateway.getMerchantCode()) %>">
          <tr class="containerBody">
            <td class="formLabel">
              <dhv:label name="admin.merchantCode">Merchant Code</dhv:label>
            </td>
            <td><%= toHtml(merchantPaymentGateway.getMerchantCode()) %></td>
          </tr>
        </dhv:evaluate>
       </table>
        &nbsp;<br />
 
        <table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
          <tr>
            <th colspan="2">
              <strong><dhv:label name="accounts.accounts_contacts_calls_details.RecordInformation">Record Information</dhv:label></strong>
            </th>
          </tr>
          <tr class="containerBody">
            <td nowrap class="formLabel">
              <dhv:label name="accounts.accounts_calls_list.Entered">Entered</dhv:label>
            </td>
            <td>
              <dhv:username id="<%= merchantPaymentGateway.getEnteredBy() %>" />
              <zeroio:tz timestamp="<%= merchantPaymentGateway.getEntered() %>" />
            </td>
          </tr>
          <tr class="containerBody">
            <td nowrap class="formLabel">
              <dhv:label name="accounts.accounts_contacts_calls_details.Modified">Modified</dhv:label>
            </td>
            <td>
              <dhv:username id="<%= merchantPaymentGateway.getModifiedBy() %>" />
              <zeroio:tz timestamp="<%= merchantPaymentGateway.getModified() %>" />
            </td>
          </tr>
        </table>
        <br />
            <input type="button" value="<dhv:label name="button.modify">Modify</dhv:label>" onClick="javascript:window.location.href='MerchantPaymentGateways.do?command=Modify&id=<%= merchantPaymentGateway.getId() %>'">
            <input type="button" value="<dhv:label name="button.delete">Delete</dhv:label>" onClick="javascript:if(confirm('Delete?')){window.location.href='MerchantPaymentGateways.do?command=Delete&id=<%=merchantPaymentGateway.getId()%>'};">
 
      </td>
      </tr>
    </table>
