<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ page import="java.util.*,org.aspcfs.modules.mycfs.base.*" %>
<%@ page import="org.aspcfs.modules.contacts.base.*,org.aspcfs.utils.web.*,org.aspcfs.modules.quotes.base.*,com.zeroio.iteam.base.*" %>
<jsp:useBean id="quote" class="org.aspcfs.modules.quotes.base.Quote" scope="request"/>
<jsp:useBean id="userContact" class="org.aspcfs.modules.contacts.base.Contact" scope="request"/>
<jsp:useBean id="quoteStatusList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<jsp:useBean id="applicationPrefs" class="org.aspcfs.controller.ApplicationPrefs" scope="application"/>
<%@ include file="../initPage.jsp" %>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/spanDisplay.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/submit.js"></script>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popContactEmailAddressListSingle.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/confirmDelete.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popCalendar.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popURL.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popLookupSelect.js?1"></script>
<script type="text/javascript">
function checkForm(form) {
  formTest = true;
  message = "";
  if(form.emailToAddress.value == ""){
    message += label("select.one.emailaddress","- Select at least one email address\r\n");
    formTest = false;
  }
  if(form.fromEmailAddress.value == ""){
    message += label("specify.youremailaddress","- Please specify your email address\r\n");
    formTest = false;
  }
  if(form.subject.value == ""){
    message += label("check.subject","- Enter a subject\r\n");
    formTest = false;
  }
  if(form.body.value == ""){
    message += label("check.message","- Enter a message in the body\r\n");
    formTest = false;
  }
  if (formTest) {
    hideSpan('send');
    showSpan('sendingEmail');
    return true;
  } else {
    alert(label("check.form","Form could not be saved, please check the following:\r\n\r\n") + message);
    return false;
  }
}

function populateEmailAddress(address, type, field) {
  document.getElementById(field).value = address;
}
</script>
<body onLoad="javascript:showSpan('send');hideSpan('sendingEmail');document.forms['emailQuote'].emailToAddress.focus();">
<form name="emailQuote" action="Quotes.do?command=SendEmail&quoteId=<%= quote.getId() %>&displayGrandTotal=<%= quote.getShowTotal() %>&displaySubTotal=<%= quote.getShowSubtotal() %>'" method="post" onSubmit="return checkForm(this);">
<%= showError(request, "actionError") %>
<br />
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="2">
      <strong><dhv:label name="quotes.emailQuoteNumber.symbol">Email Quote #</dhv:label><%= quote.getGroupId() %></strong>
    </th>
  </tr>
  <tr class="containerBody">
    <td valign="top" class="formLabel">
      <dhv:label name="quotes.to">To</dhv:label>
    </td>
    <td width="100%" valign="top" align="left">
      <table border="0" cellpadding="0" cellspacing="0" class="empty">
        <tr>
          <td>
            <input type="text" name="emailToAddress" id="emailToAddress" value="<%= toHtmlValue(quote.getEmailAddress()) %>" size="40" />
          </td>
          <td valign="top">&nbsp;
            [<a href="javascript:popContactEmailAddressListSingle('<%= quote.getContactId() %>','emailToAddress');"><dhv:label name="quotes.toEmailAddress">To Email Address</dhv:label></a>]
            <font color="red">*</font><%= showAttribute(request, "emailToAddressError") %>
          </td>
        </tr>
      </table>
    </td>
  </tr>
  <tr class="containerBody">
    <td valign="top" class="formLabel">
      <dhv:label name="campaign.from">From</dhv:label>
    </td>
    <td width="100%" valign="top" align="left">
      <table border="0" cellpadding="0" cellspacing="0" class="empty">
        <tr>
          <td>
            <input type="text" name="fromEmailAddress" id="fromEmailAddress" size="40" value="<%= (userContact.getEmailAddressList().size()>0)?((ContactEmailAddress) userContact.getEmailAddressList().get(0)).getEmail():"" %>"/>
          </td>
          <td valign="top">&nbsp;
            [<a href="javascript:popContactEmailAddressListSingle('<%= User.getUserRecord().getContact().getId() %>','fromEmailAddress');"><dhv:label name="quotes.fromEmailAddress">From Email Address</dhv:label></a>]
          <font color="red">*</font><%= showAttribute(request, "fromEmailAddressError") %>
          </td>
        </tr>
      </table>
    </td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel">
      <dhv:label name="accounts.accounts_calls_list.Subject">Subject</dhv:label>
    </td>
    <td width="100%" valign="top" align="left">
      <table border="0" cellpadding="0" cellspacing="0" class="empty"><tr><td>
      <% String temp_quoteNumber = "quoteNumber="+quote.getGroupId(); %>
        <input type="text" name="subject" value="<dhv:label name="quotes.quoteNumber.value" param="<%= temp_quoteNumber %>">Quote #<%= quote.getGroupId() %></dhv:label>" size="50">
      </td><td valign="top">&nbsp;
        <font color="red">*</font> <%= showAttribute(request, "subjectError") %>
      </td></tr></table>
    </td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel">
      <dhv:label name="product.options">Options</dhv:label>
    </td>
    <td width="100%" valign="top" align="left">
      <input type="checkbox" name="emailMe" value="true">
      <dhv:label name="quotes.emailMeACopy.bcc.brackets">Email me a copy (BCC)</dhv:label>
    </td>
  </tr>
  <tr class="containerBody">
    <td valign="top" class="formLabel">
      <dhv:label name="calendar.body">Body</dhv:label>
    </td>
    <td width="100%" valign="top" align="left">
      <table border="0" cellpadding="0" cellspacing="0" class="empty"><tr><td>
        <textarea name="body" rows="10" COLS="60" value="body"></textarea>
      </td><td valign="top">&nbsp;
        <font color="red">*</font> <%= showAttribute(request, "bodyError") %>
      </td></tr></table>
    </td>
  </tr>
  <tr class="containerBody">
    <td valign="top" class="formLabel">
      <dhv:label name="quotes.attachment">Attachment</dhv:label>
    </td>
    <td width="100%" valign="top" align="left">
      <img src="images/mime/gnome-application-pdf-23.gif" border="0" align="absmiddle"/>&nbsp; <dhv:label name="quotes.quoteNumberPDF.filename" param="<%= temp_quoteNumber %>">Quote_<%= quote.getId() %>.pdf</dhv:label>
    </td>
  </tr>
</table>
&nbsp;<br />
<table border="0" style="empty">
<tr id="send"><td>
<input type="submit" value="<dhv:label name="button.send">Send</dhv:label>" />&nbsp;
<input type="button" value="<dhv:label name="button.cancel">Cancel</dhv:label>" onClick="javascript:window.close();"/>
</td>
</tr>
<tr id="sendingEmail"><td><dhv:label name="quotes.sendingEmail.label">Sending the email...</dhv:label></td></tr>
</table>
</form>
