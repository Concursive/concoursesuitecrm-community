<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,org.aspcfs.modules.base.*,org.aspcfs.utils.web.*,org.aspcfs.modules.contacts.base.*,com.zeroio.iteam.base.*" %>
<jsp:useBean id="typeSelect" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="errorMap" class="java.util.HashMap" scope="request" />
<jsp:useBean id="StateSelect" class="org.aspcfs.utils.web.StateSelect" scope="request"/>
<jsp:useBean id="CountrySelect" class="org.aspcfs.utils.web.CountrySelect" scope="request"/>
<%@ include file="../initPage.jsp" %>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/checkEmail.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/checkPhone.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/checkCreditCardNumber.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/checkRadioButton.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/spanDisplay.js"></script>
<script language="JavaScript">
  function checkForm(form) {
    formTest = true ;
    message = "";   
    if (form.address1line1.value == "") {
      message += "- Street Address is required\r\n";
      formTest = false;
    }
    if (form.address1city.value == "") {
      message += "- City Name is required\r\n";
      formTest = false;
    }
    if (form.address1state.value == "") {
      message += "- State Name is required\r\n";
      formTest = false;
    }
    if (form.address1country.value == "") {
      message += "- Country Name is required\r\n";
      formTest = false;
    }
    if (formTest == false) {
      alert("Form could not be saved, please check the following:\r\n\r\n" + message);
    }
    return formTest;
  }
  
  function update(countryObj, stateObj) {
    var country = document.forms['contact_address_form'].elements[countryObj].value;
    if(country == "UNITED STATES" || country == "CANADA"){
      hideSpan('state2' + stateObj);
      showSpan('state1' + stateObj);
    }else{
      hideSpan('state1' + stateObj);
      showSpan('state2' + stateObj);
    }
  }
</script>

<form name="contact_address_form" action="ContactAddressSelector.do?command=Add" method="post" onSubmit="return checkForm(this);">
<%
  ContactAddress thisAddress = new ContactAddress();
  thisAddress.setCountry("UNITED STATES");
%>
<table width="100%" border="0" cellspacing="0" cellpadding="0" class="details">
  <tr><th colspan="2"><strong>Add Address</strong></th></tr>
  <tr>
    <td>Address Type</td>
    <td align="left" class="formLabel"><%= typeSelect.getHtmlSelect("address1type","Business") %></td>
    </td>
  </tr>
  <tr>
    <td>Street Address: Line 1</td>
    <td align="left" class="formLabel"><input type="text" name="address1line1" size="25"/><font color="red">*<%= toHtml((String) errorMap.get("addressError")) %></font>
  </tr>
  <tr>
    <td>Street Address: Line 2</td>
    <td align="left" class="formLabel"><input type="text" name="address1line2" size="25"/><font color="red"><%= toHtml((String) errorMap.get("addressError")) %></font></td>
  </tr>
  <tr>
    <td>Street Address: Line 3</td>
    <td align="left" class="formLabel"><input type="text" name="address1line3" size="25"/><font color="red"><%= toHtml((String) errorMap.get("addressError")) %></font></td>
  </tr>
  <tr>
    <td>City</td>
    <td align="left" class="formLabel"><input type="text" name="address1city" size="25"/><font color="red">*<%= toHtml((String) errorMap.get("addressError")) %></font></td>
  </tr>
  <tr>
    <td>State</td>
    <td>
      <span name="state11" ID="state11" style="<%= ("UNITED STATES".equals(thisAddress.getCountry()) || "CANADA".equals(thisAddress.getCountry()))? "" : " display:none" %>">
        <%= StateSelect.getHtml("address1state", thisAddress.getState()) %>
      </span>
      <%-- If selected country is not US/Canada use textfield --%>
      <span name="state21" ID="state21" style="<%= (!"UNITED STATES".equals(thisAddress.getCountry()) && !"CANADA".equals(thisAddress.getCountry())) ? "" : " display:none" %>">
        <input type="text" size="25" name="address1otherState"  value="<%= toHtmlValue(thisAddress.getOtherState()) %>">
      </span>
      <% StateSelect = new StateSelect(); %>
    </td>
  </tr>
  <tr>
    <td>Zip/Postal Code</td> 
    <td align="left" class="formLabel"><input type="text" name="address1zip" size="25"/><font color="red"><%= toHtml((String) errorMap.get("addressError")) %></font></td>
  </tr>
  <tr>
    <td>Country</td>
    <td>
      <% CountrySelect.setJsEvent("onChange=\"javascript:update('address1country', '1' );\"");%>
      <%= CountrySelect.getHtml("address1country", thisAddress.getCountry()) %><font color="red">*<%= toHtml((String) errorMap.get("addressError")) %></font>
      <% CountrySelect = new CountrySelect(); %>
    </td>
  </tr>
</table>
<br /><br />
<input type="submit" value="Submit"/> &nbsp; <input type="button" value="Cancel" onClick="javascript:window.location.href='ContactAddressSelector.do?command=List';"/>
</form>
