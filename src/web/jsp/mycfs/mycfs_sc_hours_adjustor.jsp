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
<%@ page import="java.util.*,org.aspcfs.modules.accounts.base.*, org.aspcfs.utils.web.*,java.util.*,org.aspcfs.modules.assets.base.*,org.aspcfs.modules.servicecontracts.base.*,java.text.DateFormat" %>
<%@ page import="org.aspcfs.utils.StringUtils" %>
<jsp:useBean id="serviceContractHours" class="org.aspcfs.modules.servicecontracts.base.ServiceContractHours" scope="request"/>
<jsp:useBean id="adjustmentReasonList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<%@ include file="../initPage.jsp" %>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popContractHours.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/checkNumber.js"></script>
<script language="JavaScript">
  function doCheck(form) {
    if (form.dosubmit.value == "false") {
      return true;
    } else {
      return(checkForm(form));
    }
  }
  function checkForm(form) {
    formTest = true;
    message = "";
    if (form.adjustmentHours.value == "") { 
      message += label("select.hours","- Hours is required\r\n");
      formTest = false;
    }

    if (!checkRealNumber(form.adjustmentHours.value)) { 
      message += label("check.hours.invalid","- Hours is invalid\r\n");
      formTest = false;
    }

    if (form.adjustmentReason.value == -1) { 
      message += label("select.reason","- Reason is required\r\n");
      formTest = false;
    }

    if (formTest == false) {
      alert(label("check.form", "Form could not be saved, please check the following:\r\n\r\n") + message);
      return false;
    }
  }
</script>
<form name="serviceContractHoursAdjust" method="post" action="ServiceContractHoursAdjustor.do?command=AdjustHours&finalsubmit=true" onSubmit="return doCheck(this);">
<input type="hidden" name="hiddenFieldId1" value="<%= toHtmlValue(request.getParameter("hiddenFieldId1")) %>" />
<input type="hidden" name="displayFieldId1" value="<%= toHtmlValue(request.getParameter("displayFieldId1")) %>" />
<input type="hidden" name="hiddenFieldId2" value="<%= toHtmlValue(request.getParameter("hiddenFieldId2")) %>" />
<input type="hidden" name="displayFieldId2" value="<%= toHtmlValue(request.getParameter("displayFieldId2")) %>" />
<input type="hidden" name="hiddenFieldId3" value="<%= toHtmlValue(request.getParameter("hiddenFieldId3")) %>" />
<input type="hidden" name="displayFieldId3" value="<%= toHtmlValue(request.getParameter("displayFieldId3")) %>" />
<%
  if (!"true".equals(request.getAttribute("finalsubmit"))) {
%>
  <body onLoad="javascript:document.serviceContractHoursAdjust.adjustmentHours.focus();" >
  <table cellpadding="4" cellspacing="0" border="0" width="100%">
    <tr>
      <td class="containerBack">
      <input type="submit" value="<dhv:label name="account.sc.adjust">Adjust</dhv:label>"  onClick="this.form.dosubmit.value='true';" />
      <input type="button" value="<dhv:label name="global.button.cancel">Cancel</dhv:label>" onClick="window.close()" />
      <br /><br />
<%= showError(request, "actionError") %>
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="2">
	    <strong><dhv:label name="calendar.adjustmentInformation">Adjustment Information</dhv:label></strong>
	  </th>
  </tr>
  <tr class="containerBody">
    <td class="formLabel">
      <dhv:label name="calendar.hours">Hours</dhv:label>
    </td>
    <td>
      <input type="text" size="7" name="adjustmentHours" maxlength="8" ><font color="red">*</font>
      <dhv:label name="calendar.useMinusToSubtractHours">(Use "-" to substract hours)</dhv:label>
    </td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel">
      <dhv:label name="account.sc.reason">Reason</dhv:label>
    </td>
    <td>
      <%= adjustmentReasonList.getHtmlSelect("adjustmentReason", -1) %><font color="red">*</font>
    </td>
  </tr>
  <tr class="containerBody">
    <td valign="top" class="formLabel">
      <dhv:label name="accounts.accountasset_include.Notes">Notes</dhv:label>
    </td>
    <td>
      <textarea name="adjustmentNotes" rows="3" cols="50"></textarea>
    </td>
  </tr>
  </table>
  <br>
  <input type="submit" value="<dhv:label name="account.sc.adjust">Adjust</dhv:label>" onClick="this.form.dosubmit.value='true';" />
  <input type="button" value="<dhv:label name="global.button.cancel">Cancel</dhv:label>" onClick="window.close()" />
  <input type="hidden" name="dosubmit" value="true" />
  </td>
  </tr>
</table>
  
  <input type="hidden" name="finalsubmit" value="false" />
  </form>

<%} else { %>
<%-- The final submit --%>
  <body OnLoad="javascript:setHours(hiddenValues, hiddenFields, displayValues, displayFields);window.close()" >
    <script>
      hiddenValues = new Array();
      displayValues = new Array();
      hiddenFields = new Array();
      displayFields = new Array();
    </script>
    <% int count = -1;
      String hf1 = request.getParameter("hiddenFieldId1");
      String hf2 = request.getParameter("hiddenFieldId2");
      String hf3 = request.getParameter("hiddenFieldId3");
      String df1 = request.getParameter("displayFieldId1");
      String df2 = request.getParameter("displayFieldId2");
      String df3 = request.getParameter("displayFieldId3");
    %>
    <script language="JavaScript">
      <% count++; %>
      hiddenValues[<%= count %>] = "<%= serviceContractHours.getAdjustmentHours() %>";
      hiddenFields[<%= count %>] = "<%=hf1%>";
      displayValues[<%= count %>] = "<%= serviceContractHours.getAdjustmentHours() %>";
      displayFields[<%= count %>] = "<%=df1%>";

      <% 
      if (hf2 != null){
        count++; 
      %>
      hiddenValues[<%= count %>] = "<%= serviceContractHours.getAdjustmentReason() %>";
      hiddenFields[<%= count %>] = "<%=hf2%>";
      displayValues[<%= count %>] = "<%= toHtml(adjustmentReasonList.getSelectedValue(serviceContractHours.getAdjustmentReason())) %>";
      displayFields[<%= count %>] = "<%=df2%>";
      <%}%>

      <% 
      if (hf2 != null){
        count++; 
      %>
      hiddenValues[<%= count %>] = "<%= StringUtils.jsEscape(serviceContractHours.getAdjustmentNotes()) %>";
      hiddenFields[<%= count %>] = "<%=hf3%>";
      displayValues[<%= count %>] = "<%= toHtml(serviceContractHours.getAdjustmentNotes()) %>";
      displayFields[<%= count %>] = "<%=df3%>";
      <%}%>
  </script>
  </body>
<% }%>

