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
<%@ page import="java.util.*, org.aspcfs.modules.quotes.base.*" %>
<%@ page import="java.sql.*" %>
<jsp:useBean id="PermissionCategory" class="org.aspcfs.modules.admin.base.PermissionCategory" scope="request"/>
<jsp:useBean id="quote" class="org.aspcfs.modules.quotes.base.Quote" scope="request"/>
<jsp:useBean id="quoteStatusList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<jsp:useBean id="applicationPrefs" class="org.aspcfs.controller.ApplicationPrefs" scope="application"/>
<%@ include file="../initPage.jsp" %>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/confirmDelete.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/checkNumber.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/checkDate.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/checkCurrency.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popCalendar.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/submit.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popProductCatalogs.js"></script> 
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popLookupSelect.js?1"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popURL.js"></script>
<script language="JavaScript">
	function checkForm(form) {
    var actionValue = "";
    for (var i=0; i<form.submitAction.length; i++) {
       if (form.submitAction[i].checked) {
        actionValue = form.submitAction[i].value;
        break;
       }
    }
		if (actionValue == '<%= Quote.NONE %>') {
      alert(label("quote.submitted.withoutaction","Quote submitted without performing any action."));
		}
    if (actionValue == '<%= Quote.PRINT %>') {
      form.action += '&canPrint=true';
//      opener.printQuote('<%= quote.getId() %>');
    }
    if (actionValue == '<%= Quote.EMAIL %>') {
      opener.emailQuote('<%= quote.getId() %>');
    }
    return true;
	}
</script>
<form name="submitQuote" action="Quotes.do?command=Modify&quoteId=<%= quote.getId() %>&auto-populate=true&popup=true&return=submit" onSubmit="return checkForm(this);" method="post">
<dhv:formMessage />
<br />
<%-- Insert the quote product form here --%>
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th>
      <strong><dhv:label name="quotes.submitMethod">Submit Method</dhv:label></strong>
    </th>
  </tr>      
	<tr class="containerBody">
		<td>
      <table border="0" cellpadding="0" cellspacing="0" class="empty"><tr><td valign="top">
        <input type="radio" name="submitAction" value="<%= Quote.NONE %>" checked>
      </td><td valign="top" nowrap>&nbsp;<dhv:label name="accounts.accounts_contacts_calls_details_followup_include.None">None</dhv:label>
        <%= showAttribute(request, "nameError") %>
      </td></tr></table>
		</td>
	</tr>
	<tr class="containerBody">
		<td>
      <table border="0" cellpadding="0" cellspacing="0" class="empty"><tr><td valign="top">
        <input type="radio" name="submitAction" value="<%= Quote.PRINT %>">
      </td><td valign="top" nowrap>&nbsp;<dhv:label name="global.button.Print">Print</dhv:label>
        <%= showAttribute(request, "nameError") %>
      </td></tr></table>
		</td>
	</tr>
	<tr class="containerBody">
		<td>
      <table border="0" cellpadding="0" cellspacing="0" class="empty"><tr><td valign="top">
        <input type="radio" name="submitAction" value="<%= Quote.EMAIL %>">
      </td><td valign="top" nowrap>&nbsp;<dhv:label name="quotes.emailToCustomer">Email to Customer</dhv:label>
        <%= showAttribute(request, "nameError") %>
      </td></tr></table>
		</td>
	</tr>
	<tr class="containerBody">
		<td>
      <table border="0" cellpadding="0" cellspacing="0" class="empty"><tr><td valign="top">
        <input type="radio" name="submitAction" value="<%= Quote.FAX %>" disabled>
      </td><td valign="top" nowrap>&nbsp;<dhv:label name="quotes.faxToCustomer">Fax to Customer</dhv:label>
        <%= showAttribute(request, "nameError") %>
      </td></tr></table>
		</td>
	</tr>
</table>
<dhv:evaluate if="<%=(!quote.getLock())%>" >
  &nbsp;<br />
  <table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
    <tr>
      <th>
        <strong><dhv:label name="accounts.accounts_quotes_list.QuoteStatus">Quote Status</dhv:label></strong>
      </th>
    </tr>
    <tr class="containerBody">
      <td>
        <table border="0" cellpadding="0" cellspacing="0" class="empty"><tr><td valign="top">
        </td><td valign="top" nowrap><%= quoteStatusList.getHtmlSelect("statusId", quote.getStatusId()) %> &nbsp;
          <%= showAttribute(request, "nameError") %>
        </td></tr></table>
      </td>
    </tr>
  </table>
</dhv:evaluate>
<br />
<input type="hidden" name="contactId" value="<%= quote.getContactId() %>"/>
<input type="hidden" name="orgId" value="<%= quote.getOrgId() %>"/>
<zeroio:dateSelect form="submitQuote" field="issuedDate" timestamp="<%= new Timestamp(Calendar.getInstance().getTimeInMillis()) %>"  timeZone="<%= User.getTimeZone() %>" showTimeZone="false" hidden="true" />
<zeroio:dateSelect form="submitQuote" field="statusDate" timestamp="<%= new Timestamp(Calendar.getInstance().getTimeInMillis()) %>"  timeZone="<%= User.getTimeZone() %>" showTimeZone="false" hidden="true" />
<zeroio:dateSelect form="submitQuote" field="expirationDate" timestamp="<%= quote.getExpirationDate() %>"  timeZone="<%= User.getTimeZone() %>" showTimeZone="false" hidden="true" />
<zeroio:dateSelect form="submitQuote" field="closed" timestamp="<%= quote.getClosed() %>"  timeZone="<%= User.getTimeZone() %>" showTimeZone="false" hidden="true" />
<input type="hidden" name="shortDescription" value="<%= quote.getShortDescription() %>"/>
<input type="hidden" name="notes" value="<%= quote.getNotes() %>"/>
<input type="hidden" name="headerId" value="<%= quote.getHeaderId() %>"/>
<input type="hidden" name="deliveryId" value="<%= quote.getDeliveryId() %>"/>
<input type="hidden" name="emailAddress" value="<%= quote.getEmailAddress() %>"/>
<input type="hidden" name="phoneNumber" value="<%= quote.getPhoneNumber() %>"/>
<input type="hidden" name="address" value="<%= quote.getAddress() %>"/>
<input type="hidden" name="closeIt" value="<%= quote.getCloseIt() %>"/>
<input type="hidden" name="showTotal" value="<%= quote.getShowTotal() %>"/>
<input type="hidden" name="showSubtotal" value="<%= quote.getShowSubtotal() %>"/>
<input type="hidden" name="logoFileId" value="<%= quote.getLogoFileId() %>"/>
<input type="submit" value="<dhv:label name="button.save">Save</dhv:label>" />
<input type="button" value="<dhv:label name="button.cancel">Cancel</dhv:label>" onClick="javascript:window.close();" />
</form>

