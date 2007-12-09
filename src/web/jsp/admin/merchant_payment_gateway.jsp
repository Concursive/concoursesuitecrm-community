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
  - Version: $Id:{ }
  - Description: 
  --%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv"%>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio"%>
<%@ page import="java.util.*,org.aspcfs.modules.products.base.*"%>
<jsp:useBean id="merchantPaymentGateway" class="org.aspcfs.modules.admin.base.MerchantPaymentGateway" scope="request" />
<jsp:useBean id="paymentGatewayList" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session" />

<%@ include file="../initPage.jsp"%>
<body>
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
  <form name="modify" action="MerchantPaymentGateways.do?command=Save&auto-populate=true" method="post">
    <input type="hidden" name="id" value="<%= merchantPaymentGateway.getId()%>" />
    <table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
      <tr class="abovetab">
        <th colspan="2">
          <dhv:label name="admin.addMerchantPaymentGateway">Add Merchant Payment Gateway</dhv:label>
        </th>
      </tr>
      <tr class="containerBody">
        <td nowrap class="formLabel">
          <dhv:label name="admin.paymentGateway">Payment Gateway</dhv:label>
        </td>
        <td>
          <%=paymentGatewayList.getHtmlSelect("gatewayId",
              merchantPaymentGateway.getGatewayId())%>
        </td>
      </tr>
      <tr class="containerBody">
        <td nowrap class="formLabel">
          <dhv:label name="admin.merchantId">Merchant Id</dhv:label>
        </td>
        <td>
          <input type="text" size="35" maxlength="80" name="merchantId" value="<%= toHtmlValue(merchantPaymentGateway.getMerchantId()) %>">
        </td>
      </tr>
      <tr class="containerBody">
        <td nowrap class="formLabel">
          <dhv:label name="admin.merchantCode">Merchant Code</dhv:label>
        </td>
        <td>
          <input type="text" size="35" maxlength="80" name="merchantCode" value="<%= toHtmlValue(merchantPaymentGateway.getMerchantCode()) %>">
        </td>
      </tr>
    </table>
    <br />
    <input type="submit" value="<dhv:label name="button.save">Save</dhv:label>" name="Save" onClick="this.form.dosubmit.value='true';">
    <input type="button" value="<dhv:label name="button.cancel">Cancel</dhv:label>" onClick="javascript:window.location.href='AdminConfig.do?command=ListGlobalParams';">
    <input type="hidden" name="dosubmit" value="true" />
  </form>
</body>
