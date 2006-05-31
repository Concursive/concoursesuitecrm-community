<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,org.aspcfs.modules.base.*,org.aspcfs.utils.web.*,org.aspcfs.modules.contacts.base.*" %>
<%@ page import="org.aspcfs.modules.contacts.base.*,com.zeroio.iteam.base.*" %>
<jsp:useBean id="typeSelect" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="errorMap" class="java.util.HashMap" scope="request" />
<jsp:useBean id="StateSelect" class="org.aspcfs.utils.web.StateSelect" scope="request"/>
<jsp:useBean id="CountrySelect" class="org.aspcfs.utils.web.CountrySelect" scope="request"/>
<jsp:useBean id="contact" class="org.aspcfs.modules.contacts.base.Contact" scope="request"/>
<jsp:useBean id="applicationPrefs" class="org.aspcfs.controller.ApplicationPrefs" scope="application"/>
<jsp:useBean id="systemStatus" class="org.aspcfs.controller.SystemStatus" scope="request"/>
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
    if (checkNullString(form.address1line1.value)) {
      message += label("check.street.address","- Street Address is required\r\n");
      formTest = false;
    }
    if (checkNullString(form.address1city.value)) {
      message += label("check.city.name","- City Name is required\r\n");
      formTest = false;
    }
    if ((checkNullString(form.address1state.value) || form.address1state.value == '-1') && checkNullString(form.address1otherState.value)) {
      message += label("check.state.name","- State Name is required\r\n");
      formTest = false;
    }
    if (checkNullString(form.address1country.value) || form.address1country.value == '-1') {
      message += label("check.country.name","- Country Name is required\r\n");
      formTest = false;
    }
    if (formTest == false) {
      alert(label("check.form","Form could not be saved, please check the following:\r\n\r\n") + message);
    }
    return formTest;
  }
  
  function update(countryObj, stateObj, selectedValue) {
    var country = document.forms['contact_address_form'].elements[countryObj].value;
    var url = "ExternalContacts.do?command=States&country="+country+"&obj="+stateObj+"&selected="+selectedValue+"&form=contact_address_form&stateObj=address"+stateObj+"state";
    window.frames['server_commands'].location.href=url;
  }

  function continueUpdateState(stateObj, showText) {
    if(showText == 'true'){
      hideSpan('state1' + stateObj);
      showSpan('state2' + stateObj);
    } else {
      hideSpan('state2' + stateObj);
      showSpan('state1' + stateObj);
    }
  }
</script>
<body onLoad="javascript:document.contact_address_form.address1line1.focus();">
<form name="contact_address_form" action="ContactAddressSelector.do?command=Add&contactId=<%= (contact != null)?""+contact.getId():"" %>" method="post" onSubmit="return checkForm(this);">
<%
  ContactAddress thisAddress = new ContactAddress();
  thisAddress.setCountry("UNITED STATES");
%>
<table width="100%" border="0" cellspacing="0" cellpadding="4" class="details">
  <tr><th colspan="2"><strong><dhv:label name="button.addAddress">Add Address</dhv:label></strong></th></tr>
  <tr>
    <td class="formLabel" nowrap><dhv:label name="contacts.addressType">Address Type</dhv:label></td>
    <td align="left"><%= typeSelect.getHtmlSelect("address1type","Business") %></td>
    </td>
  </tr>
  <tr>
    <td class="formLabel" nowrap><dhv:label name="contacts.address.streetLine1">Street Address: Line 1</dhv:label></td>
    <td align="left"><input type="text" name="address1line1" size="25"/><font color="red">*<%= toHtml((String) errorMap.get("addressError")) %></font>
  </tr>
  <tr>
    <td class="formLabel" nowrap><dhv:label name="contacts.address.streetLine2">Street Address: Line 2</dhv:label></td>
    <td align="left"><input type="text" name="address1line2" size="25"/><font color="red"><%= toHtml((String) errorMap.get("addressError")) %></font></td>
  </tr>
  <tr>
    <td class="formLabel" nowrap><dhv:label name="contacts.address.streetLine3">Street Address: Line 3</dhv:label></td>
    <td align="left"><input type="text" name="address1line3" size="25"/><font color="red"><%= toHtml((String) errorMap.get("addressError")) %></font></td>
  </tr>
  <tr>
    <td class="formLabel" nowrap><dhv:label name="accounts.accounts_add.City">City</dhv:label></td>
    <td align="left"><input type="text" name="address1city" size="25"/><font color="red">*<%= toHtml((String) errorMap.get("addressError")) %></font></td>
  </tr>
  <tr>
    <td class="formLabel" nowrap><dhv:label name="accounts.accounts_add.StateProvince">State</dhv:label></td>
    <td>
      <span name="state11" ID="state11" style="<%= StateSelect.hasCountry(thisAddress.getCountry())? "" : " display:none" %>">
        <%= StateSelect.getHtmlSelect("address1state", thisAddress.getCountry(), thisAddress.getState()) %>
      </span>
      <%-- If selected country is not US/Canada use textfield --%>
      <span name="state21" ID="state21" style="<%= !StateSelect.hasCountry(thisAddress.getCountry()) ? "" : " display:none" %>">
        <input type="text" size="25" name="address1otherState"  value="<%= toHtmlValue(thisAddress.getOtherState()) %>">
      </span><font color="red">*</font>
    </td>
  </tr>
  <tr>
    <td class="formLabel" nowrap><dhv:label name="accounts.accounts_add.ZipPostalCode">Zip/Postal Code</dhv:label></td> 
    <td align="left"><input type="text" name="address1zip" size="25"/><font color="red"><%= toHtml((String) errorMap.get("addressError")) %></font></td>
  </tr>
  <tr>
    <td class="formLabel" nowrap><dhv:label name="accounts.accounts_add.Country">Country</dhv:label></td>
    <td>
      <% CountrySelect.setJsEvent("onChange=\"javascript:update('address1country', '1','' );\"");%>
      <%= CountrySelect.getHtml("address1country", thisAddress.getCountry()) %><font color="red"><%= toHtml((String) errorMap.get("addressError")) %></font>
      <% CountrySelect = new CountrySelect(systemStatus); %>
    </td>
  </tr>
</table>
<br />
<input type="submit" value="<dhv:label name="button.submit">Submit</dhv:label>"/> 
<input type="button" value="<dhv:label name="button.cancel">Cancel</dhv:label>" onClick="javascript:window.location.href='ContactAddressSelector.do?command=List&contactId=<%= (contact != null)?""+contact.getId():"" %>';"/>
<iframe src="empty.html" name="server_commands" id="server_commands" style="visibility:hidden" height="0"></iframe>
</form>
