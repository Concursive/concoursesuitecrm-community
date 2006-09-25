<%@ page import="org.aspcfs.utils.StringUtils"%>
<%--
- Copyright(c) 2006 Dark Horse Ventures LLC (http://www.centriccrm.com/) All
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
- Version: $Id: accounts_add.jsp 13563 2005-12-12 16:13:25Z mrajkowski $
- Description:
--%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv"%>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio"%>
<%@ taglib uri="/WEB-INF/portlet.tld" prefix="portlet"%>
<%@ include file="../../initPage.jsp" %>
<jsp:useBean id="cardTypeList" class="org.aspcfs.utils.web.LookupList" scope="request" />
<input type="hidden" name="updateCreditCard" value="1">
  <table cellpadding="4" cellspacing="0" border="0" id="tav">
    <tr>
      <td>
        Credit Card Type
      </td>
      <td>
        <%= cardTypeList.getHtmlSelect("cardType", creditCard.getCardType())%>
      </td>
    </tr>
    <tr>
      <td>
      Card Number
      </td>
      <td>
        <input type="text" name="cardNumber" size="25" value="<%= toHtmlValue(creditCard.getCardNumber())%>" />
      </td>
    </tr>
    <tr>
      <td>
       Name on Card
      </td>
      <td>
        <input type="text" name="nameOnCard" size="25" value="<%= toHtmlValue(creditCard.getNameOnCard())%>"/>
      </td>
    </tr>
    <tr>
      <td >
        Expiration Date
      </td>
      <td>
         <input type="text" name="expirationMonth" size="2" maxlength="2" value="<%= creditCard.getExpirationMonth()!=-1?toHtmlValue(creditCard.getExpirationMonth()):"MM"%>"/>&nbsp;<input type="text" name="expirationYear" size="4" maxlength="4" value="<%=creditCard.getExpirationYear()!=-1?toHtmlValue(creditCard.getExpirationYear()):"YYYY"%>"/>
      </td>
    </tr>
  </table>
