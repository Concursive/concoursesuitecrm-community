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
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ page import="java.util.*,java.text.*,java.text.DateFormat,org.aspcfs.modules.products.base.*,org.aspcfs.utils.web.*,org.aspcfs.modules.quotes.base.*,com.zeroio.iteam.base.*" %>
<jsp:useBean id="quoteStatusList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="quote" class="org.aspcfs.modules.quotes.base.Quote" scope="request"/>
<jsp:useBean id="contactList" class="org.aspcfs.modules.contacts.base.ContactList" scope="request"/>
<jsp:useBean id="version" class="java.lang.String" scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<jsp:useBean id="applicationPrefs" class="org.aspcfs.controller.ApplicationPrefs" scope="application"/>
<jsp:useBean id="systemStatus" class="org.aspcfs.controller.SystemStatus" scope="request"/>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/confirmDelete.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popCalendar.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popOpportunities.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popURL.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popAccounts.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/executeFunction.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/moveContact.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/checkEmail.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/checkPhone.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popContactAddressListSingle.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popContactEmailAddressListSingle.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popContactPhoneNumberListSingle.js"></script>
<%@ include file="../initPage.jsp" %>
<script type="text/javascript">
  function checkForm(form) {
    formTest = true;
    message = "";
    if (form.contactId.options[form.contactId.selectedIndex].value == "-1") { 
      message += label("check.ticket.contact.entered","- Check that a Contact is selected\r\n");
      formTest = false;
    }
    if (formTest == false) {
      alert(label("check.form","Form could not be saved, please check the following:\r\n\r\n") + message);
      return false;
    }
    return true;
  }

  function changeDivContent(divName, divContents) {
    if(document.layers){
      // Netscape 4 or equiv.
      divToChange = document.layers[divName];
      divToChange.document.open();
      divToChange.document.write(divContents);
      divToChange.document.close();
    } else if(document.all){
      // MS IE or equiv.
      divToChange = document.all[divName];
      divToChange.innerHTML = divContents;
    } else if(document.getElementById){
      // Netscape 6 or equiv.
      divToChange = document.getElementById(divName);
      divToChange.innerHTML = divContents;
    }
    //when the content of any of the select items changes, do something here
    if(document.forms['addQuote'].orgId.value != '-1' && divName=='changeaccount'){
      updateContactList();
    }
  }
  
  function updateContactList() {
    var sel = document.forms['addQuote'].elements['orgId'];
    var value = document.forms['addQuote'].orgId.value;
    var url = "Quotes.do?command=OrganizationJSList&orgId=" + escape(value);
    window.frames['server_commands'].location.href=url;
  }

</script>
<body onLoad="javascript:document.addQuote.expirationDate.focus();">
<form method="post" name="addQuote" action="Quotes.do?command=Clone&auto-populate=true&quoteId=<%= quote.getId() %>&versionId=<%= version %>" onSubmit="return checkForm(this);">
<%= showError(request, "actionError", false) %>
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="4">
      <strong><dhv:label name="quotes.cloneQuote.symbol.number">Clone quote #</dhv:label><%= quote.getGroupId() %> <dhv:label name="accounts.accounts_documents_details.Version">version</dhv:label> <%= quote.getVersion() %></strong>
    </th>
  </tr>
  <tr class="containerBody">
    <td class="formLabel">
      <dhv:label name="ticket.organizationLink">Organization</dhv:label>
    </td>
    <td>
      <table cellspacing="0" cellpadding="0" border="0" class="empty">
        <tr>
          <td>
            <div id="changeaccount">
              <% if(quote.getOrgId() != -1) {%>
                <%= toHtml(quote.getName()) %>
              <%} else {%>
                <dhv:label name="accounts.accounts_add.NoneSelected">None Selected</dhv:label>
              <%}%>
            </div>
          </td>
          <td>
            <input type="hidden" name="orgId" id="orgId" value="<%= quote.getOrgId() %>"/>
            &nbsp;<font color="red">*</font>
            <%= showAttribute(request, "orgIdError") %>
            [<a href="javascript:popAccountsListSingle('orgId','changeaccount', 'showMyCompany=false&filters=all|my|disabled');"><dhv:label name="accounts.accounts_add.select">Select</dhv:label></a>]
          </td>
        </tr>
      </table>
    </td>
	</tr>	
	<tr class="containerBody">
    <td class="formLabel">
      <dhv:label name="accounts.accountasset_include.Contact">Contact</dhv:label>
    </td>
    <td>
<% if (quote == null || quote.getOrgId() == -1 || contactList.size() == 0) { %>
      <%= contactList.getEmptyHtmlSelect(systemStatus, "contactId") %>
<%} else {%>
      <%= contactList.getHtmlSelect("contactId", quote.getContactId() ) %>
<%}%>
      <font color="red">*</font><%= showAttribute(request, "contactIdError") %>
     </td>
	</tr>
  <tr class="containerBody">
    <td class="formLabel"><dhv:label name="accounts.accountasset_include.Status">Status</dhv:label></td>
    <td>
      <%= quoteStatusList.getHtmlSelect("statusId", quote.getStatusId()) %>
    </td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel">
      <dhv:label name="accounts.accountasset_include.ExpirationDate">Expiration Date</dhv:label>
    </td>
    <td colspan="5">
      <zeroio:dateSelect form="addQuote" field="expirationDate" timestamp="<%= quote.getExpirationDate() %>"  timeZone="<%= User.getTimeZone() %>" showTimeZone="false" />
      <%= showAttribute(request, "expirationDateError") %>
    </td>
  </tr>
</table>
&nbsp;
<br />
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th>
      <strong><dhv:label name="quotes.nextStep">Next Step</dhv:label></strong>
    </th>
  </tr>
  <tr class="containerBody">
    <td align="left">
      <table cellspacing="0" border="0" class="empty"><tr><td valign="top" align="right">
        <input type="radio" name="return" value="old"/></td><td align="left" valign="top"> <dhv:label name="quotes.returnToPreviousQuote">Return to the previous quote details</dhv:label>
      </td></tr><tr><td valign="top" align="right">
        <input type="radio" name="return" value="clone" checked /></td><td align="left" valign="top"> <dhv:label name="quotes.viewClonedQuoteDetails">View the cloned quote details</dhv:label>
      </td></tr></table>
    </td>
  </tr>
</table>
<br />
<input type="submit" value="<dhv:label name="button.save">Save</dhv:label>" />
<input type="button" value="<dhv:label name="button.cancel">Cancel</dhv:label>" onClick="javascript:window.close();" />
</form>
<iframe src="../empty.html" name="server_commands" id="server_commands" style="visibility:hidden" height="0"></iframe>

