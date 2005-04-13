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
<%@ page import="java.util.*,org.aspcfs.modules.base.*,org.aspcfs.utils.web.*,org.aspcfs.modules.contacts.base.*" %>
<%@ page import="java.lang.*,org.aspcfs.modules.contacts.base.*,com.zeroio.iteam.base.*" %>
<jsp:useBean id="typeSelect" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="errorMap" class="java.util.HashMap" scope="request" />
<jsp:useBean id="contact" class="org.aspcfs.modules.contacts.base.Contact" scope="request"/>
<jsp:useBean id="hiddenFieldId" class="java.lang.String" scope="request"/>
<%@ include file="../initPage.jsp" %>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/checkEmail.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/checkString.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/checkPhone.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/checkCreditCardNumber.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/checkRadioButton.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/spanDisplay.js"></script>
<script language="JavaScript">
  function checkForm(form) {
    formTest = true ;
    message = "";   
    if (checkNullString(form.email1address.value)) {
      message += label("check.email.address","- Email Address is required\r\n");
      formTest = false;
    } else {
      if (!checkEmail(form.email1address.value)) {
        message += label("check.valid.email.address","- Please enter a valid email address\r\n");
        formTest = false;
      }
    }
    if (formTest == false) {
      alert(label("check.form","Form could not be saved, please check the following:\r\n\r\n") + message);
    }
    return formTest;
  }
  
</script>
<body onLoad="javascript:document.contact_address_form.email1address.focus();">
<form name="contact_address_form" action="ContactEmailAddressSelector.do?command=Add&contactId=<%= (contact != null)?""+contact.getId():"" %>&hiddenFieldId=<%= hiddenFieldId!=null?hiddenFieldId:"" %>" method="post" onSubmit="return checkForm(this);">
<%ContactEmailAddress thisAddress = new ContactEmailAddress();%>
<table width="100%" border="0" cellspacing="0" cellpadding="4" class="details">
  <tr><th colspan="2"><strong><dhv:label name="addEmailAddress">Add Email Address</dhv:label></strong></th></tr>
  <tr>
    <td class="formLabel" nowrap><dhv:label name="contacts.emailAddressType">Email Address Type</dhv:label></td>
    <td align="left"><%= typeSelect.getHtmlSelect("email1type","Business") %></td>
    </td>
  </tr>
  <tr>
    <td class="formLabel" nowrap><dhv:label name="quotes.emailAddress">Email Adderss</dhv:label></td>
    <td align="left"><input type="text" name="email1address" size="25"/><font color="red">*<%= toHtml((String) errorMap.get("email1addressError")) %></font>
  </tr>
</table>
<br />
<input type="submit" value="<dhv:label name="button.submit">Submit</dhv:label>"/> 
<input type="button" value="<dhv:label name="button.cancel">Cancel</dhv:label>" onClick="javascript:window.location.href='ContactEmailAddressSelector.do?command=List&contactId=<%= (contact != null)?""+contact.getId():"" %>&hiddenFieldId=<%= hiddenFieldId!=null?hiddenFieldId:"" %>';"/>
</form>
