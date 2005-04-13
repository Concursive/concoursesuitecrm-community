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
<%@ page import="java.util.*,java.text.*,java.text.DateFormat,org.aspcfs.modules.products.base.*,org.aspcfs.utils.web.*,org.aspcfs.modules.quotes.base.*,com.zeroio.iteam.base.*" %>
<jsp:useBean id="quote" class="org.aspcfs.modules.quotes.base.Quote" scope="request"/>
<jsp:useBean id="description" class="java.lang.String" scope="request"/>
<%@ include file="../initPage.jsp" %>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/checkString.js"></script>
<script type="text/javascript">
  function checkForm(form) {
    formTest = true;
    message = "";
    
    if (checkNullString(form.description.value)) { 
      message += label("check.description","- Check that the description is entered\r\n");
      formTest = false;
    }
    if (formTest == false) {
      alert(label("check.form","Form could not be saved, please check the following:\r\n\r\n") + message);
      return false;
    }
    return true;
  }
</script>
<body onLoad="javascript:document.addRemark.description.focus();">
<form method="post" name="addRemark" action="QuotesConditions.do?command=SaveRemark&quoteId=<%= quote.getId() %>" onSubmit="return checkForm(this);">
<input type="hidden" name="quoteId" value="<%= quote.getId() %>" />
<dhv:formMessage showSpace="false" />
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="2"><dhv:label name="quotes.newRemark">New Remark</dhv:label></th>
  </tr>
  <tr class="containerBody">
    <td class="formLabel" nowrap valign="top"><dhv:label name="accounts.accountasset_include.Description">Description</dhv:label></td>
    <td width="100%" valign="top">
      <table border="0" cellpadding="0" cellspacing="0" class="empty"><tr><td valign="top">
      <input type="text" name="description" value="<%= toHtmlValue(description) %>" size="58"/>
      </td><td valign="top" nowrap>&nbsp; 
      <font color="red">*</font><%= showAttribute(request, "descriptionError") %>
      </td></tr></table>
    </td>
  </tr>
</table>
&nbsp;<br />
<input type="submit" value="<dhv:label name="global.button.save">Save</dhv:label>"/>
<input type="button" value="<dhv:label name="button.cancel">Cancel</dhv:label>" onClick="javascript:window.close();"/>
</form>

