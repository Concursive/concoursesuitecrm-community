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
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,org.aspcfs.modules.base.*,org.aspcfs.utils.web.*,org.aspcfs.modules.contacts.base.*" %>
<%@ page import="org.aspcfs.modules.contacts.base.*,com.zeroio.iteam.base.*" %>
<jsp:useBean id="typeSelect" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="errorMap" class="java.util.HashMap" scope="request" />
<jsp:useBean id="contact" class="org.aspcfs.modules.contacts.base.Contact" scope="request"/>
<jsp:useBean id="hiddenField" class="java.lang.String" scope="request"/>
<%@ include file="../initPage.jsp" %>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/checkEmail.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/checkString.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/checkPhone.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/checkCreditCardNumber.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/checkRadioButton.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/spanDisplay.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/checkPhone.js"></script>
<script language="JavaScript">
  function checkForm(form) {
    formTest = true ;
    message = "";
    if (checkNullString(form.phone1number.value) && !checkNullString(form.phone1ext.value)) {
      message += label("check.phonenumber","- Phone number is required\r\n");
      formTest = false;
    } else if (checkNullString(form.phone1number.value)) {
      message += label("check.phonenumber","- Phone number is required\r\n");
      formTest = false;
    } else {
      if (!checkPhone(form.phone1number.value)) {
        message += label("check.valid.number","- Please enter a valid number\r\n");
        formTest = false;
      }
    }
    if (checkNullString(form.phone1ext.value) && form.phone1ext.value != "") {
      message += label("check.phone.ext","- Please enter a valid phone number extension\r\n");
      formTest = false;
    }
    if (formTest == false) {
      alert(label("check.form","Form could not be saved, please check the following:\r\n\r\n") + message);
    }
    return formTest;
  }
  
</script>
<body onLoad="javascript:document.contact_address_form.phone1number.focus();">
<form name="contact_address_form" action="ContactPhoneNumberSelector.do?command=Add&contactId=<%= (contact != null)?""+contact.getId():"" %>" method="post" onSubmit="return checkForm(this);">
<input type="hidden" name="hiddenField" value="<%= (hiddenField != null?hiddenField:"") %>"/>
<%
  ContactPhoneNumber thisAddress = new ContactPhoneNumber();
%>
<table width="100%" border="0" cellspacing="0" cellpadding="4" class="details">
  <tr><th colspan="2"><strong><dhv:label name="button.addPhoneNumber">Add Phone Number</dhv:label></strong></th></tr>
  <tr>
    <td class="formLabel" nowrap><dhv:label name="quotes.phoneNumberType">Phone Number Type</dhv:label></td>
    <td align="left"><%= typeSelect.getHtmlSelect("phone1type","Business") %></td>
    </td>
  </tr>
  <tr>
    <td class="formLabel" nowrap><dhv:label name="quotes.phoneNumber">Phone Number</dhv:label></td>
    <td align="left">
      <table border="0" cellpadding="0" cellspacing="0" class="empty"><tr><td valign="top">
        <input type="text" size="20" name="phone1number" />&nbsp;<dhv:label name="accounts.accounts_add.ext">ext.</dhv:label>
        <input type="text" size="5" name="phone1ext" maxlength="10" />
      </td><td valign="top" nowrap>
        <font color="red">*<%= toHtml((String) errorMap.get("phone1numberError")) %></font>
      </td></tr></table>
    </td>
  </tr>
</table>
<br />
<input type="submit" value="<dhv:label name="button.submit">Submit</dhv:label>"/> 
<input type="button" value="<dhv:label name="button.cancel">Cancel</dhv:label>" onClick="javascript:window.location.href='ContactPhoneNumberSelector.do?command=List&contactId=<%= (contact != null)?""+contact.getId():"" %>&hiddenField=<%= (hiddenField!=null?hiddenField:"") %>';"/>
</form>
