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
<%@ page import="java.util.*,java.text.DateFormat,org.aspcfs.modules.accounts.base.*,org.aspcfs.utils.web.*,org.aspcfs.modules.contacts.base.*" %>
<jsp:useBean id="IndustryList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="OrgPhoneTypeList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="OrgAddressTypeList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="OrgEmailTypeList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="AccountTypeList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="OrgDetails" class="org.aspcfs.modules.accounts.base.Organization" scope="request"/>
<jsp:useBean id="StateSelect" class="org.aspcfs.utils.web.StateSelect" scope="request"/>
<jsp:useBean id="CountrySelect" class="org.aspcfs.utils.web.CountrySelect" scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<jsp:useBean id="applicationPrefs" class="org.aspcfs.controller.ApplicationPrefs" scope="application"/>
<jsp:useBean id="TimeZoneSelect" class="org.aspcfs.utils.web.HtmlSelectTimeZone" scope="request"/>
<%@ include file="../initPage.jsp" %>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/checkDate.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/checkPhone.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/checkNumber.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/checkEmail.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popCalendar.js"></script>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popLookupSelect.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/spanDisplay.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/confirmDelete.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/checkURL.js"></script>
<script language="JavaScript">
  indSelected = 0;
  orgSelected = 1;
  onLoad = 1;
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
    alertMessage = "";
  <dhv:include name="organization.alert" none="true">
    if ((!form.alertText.value == "") && (form.alertDate.value == "")) { 
      message += "- Please specify an alert date\r\n";
      formTest = false;
    }
    if ((!form.alertDate.value == "") && (form.alertText.value == "")) { 
      message += "- Please specify an alert description\r\n";
      formTest = false;
    }
  </dhv:include>
  <dhv:include name="organization.phoneNumbers" none="true">
    if ((!checkPhone(form.phone1number.value)) || (!checkPhone(form.phone2number.value))) { 
      message += "- At least one entered phone number is invalid.  Make sure there are no invalid characters and that you have entered the area code\r\n";
      formTest = false;
    }
  </dhv:include>
  <dhv:include name="organization.emailAddresses" none="true">
    if ((!checkEmail(form.email1address.value)) || (!checkEmail(form.email2address.value))) { 
      message += "- At least one entered email address is invalid.  Make sure there are no invalid characters\r\n";
      formTest = false;
    }
  </dhv:include>
  <dhv:include name="organization.url" none="true">
    if (!checkURL(form.url.value)) { 
      message += "- URL entered is invalid.  Make sure there are no invalid characters\r\n";
      formTest = false;
    }
  </dhv:include>
    if (formTest == false) {
      alert("Form could not be saved, please check the following:\r\n\r\n" + message);
      return false;
    } else {
      var test = document.addAccount.selectedList;
      if (test != null) {
        selectAllOptions(document.addAccount.selectedList);
      }
      if(alertMessage != ""){
        confirmAction(alertMessage);
      }
      return true;
    }
  }
  function resetFormElements() {
    if (document.getElementById) {
      elm1 = document.getElementById("nameFirst1");
      elm2 = document.getElementById("nameMiddle1");
      elm3 = document.getElementById("nameLast1");
      elm4 = document.getElementById("orgname1");
      elm5 = document.getElementById("ticker1");
      elm1.style.color = "#000000";
      document.addAccount.nameFirst.style.background = "#ffffff";
      elm2.style.color = "#000000";
      document.addAccount.nameMiddle.style.background = "#ffffff";
      elm3.style.color = "#000000";
      document.addAccount.nameLast.style.background = "#ffffff";
      elm4.style.color = "#000000";
      document.addAccount.name.style.background = "#ffffff";
      if (elm5) {
        elm5.style.color = "#000000";
        document.addAccount.ticker.style.background = "#ffffff";
      }
    }
  }
  function updateFormElements(index) {
    if (document.getElementById) {
      elm1 = document.getElementById("nameFirst1");
      elm2 = document.getElementById("nameMiddle1");
      elm3 = document.getElementById("nameLast1");
      elm4 = document.getElementById("orgname1");
      elm5 = document.getElementById("ticker1");
      if (index == 1) {
        indSelected = 1;
        orgSelected = 0;        
        resetFormElements();
        elm4.style.color="#cccccc";
        document.addAccount.name.style.background = "#cccccc";
        document.addAccount.name.value = "";
        if (elm5) {
          elm5.style.color="#cccccc";
          document.addAccount.ticker.style.background = "#cccccc";
          document.addAccount.ticker.value = "";
        }
      } else {
        indSelected = 0;
        orgSelected = 1;
        resetFormElements();        
        elm1.style.color = "#cccccc";
        document.addAccount.nameFirst.style.background = "#cccccc";
        document.addAccount.nameFirst.value = "";
        elm2.style.color = "#cccccc";  
        document.addAccount.nameMiddle.style.background = "#cccccc";
        document.addAccount.nameMiddle.value = ""; 
        elm3.style.color = "#cccccc";      
        document.addAccount.nameLast.style.background = "#cccccc";
        document.addAccount.nameLast.value = "";     
      }
    }
    if (onLoad != 1){
      var url = "Accounts.do?command=RebuildFormElements&index=" + index;
      window.frames['server_commands'].location.href=url;
    }
    onLoad = 0;
  }
  //-------------------------------------------------------------------
  // getElementIndex(input_object)
  //   Pass an input object, returns index in form.elements[] for the object
  //   Returns -1 if error
  //-------------------------------------------------------------------
  function getElementIndex(obj) {
    var theform = obj.form;
    for (var i=0; i<theform.elements.length; i++) {
      if (obj.name == theform.elements[i].name) {
        return i;
        }
      }
      return -1;
    }
  // -------------------------------------------------------------------
  // tabNext(input_object)
  //   Pass an form input object. Will focus() the next field in the form
  //   after the passed element.
  //   a) Will not focus to hidden or disabled fields
  //   b) If end of form is reached, it will loop to beginning
  //   c) If it loops through and reaches the original field again without
  //      finding a valid field to focus, it stops
  // -------------------------------------------------------------------
  function tabNext(obj) {
    if (navigator.platform.toUpperCase().indexOf("SUNOS") != -1) {
      obj.blur(); return; // Sun's onFocus() is messed up
      }
    var theform = obj.form;
    var i = getElementIndex(obj);
    var j=i+1;
    if (j >= theform.elements.length) { j=0; }
    if (i == -1) { return; }
    while (j != i) {
      if ((theform.elements[j].type!="hidden") && 
          (theform.elements[j].name != theform.elements[i].name) && 
        (!theform.elements[j].disabled)) {
        theform.elements[j].focus();
        break;
        }
      j++;
      if (j >= theform.elements.length) { j=0; }
    }
  }
  
  function update(countryObj, stateObj) {
  var country = document.forms['addAccount'].elements[countryObj].value;
   if(country == "UNITED STATES" || country == "CANADA"){
      hideSpan('state2' + stateObj);
      showSpan('state1' + stateObj);
   }else{
      hideSpan('state1' + stateObj);
      showSpan('state2' + stateObj);
    }
  }
</script>
<dhv:evaluate if="<%= (request.getParameter("form_type") == null || "organization".equals((String) request.getParameter("form_type"))) %>">
  <body onLoad="javascript:document.forms[0].name.focus();updateFormElements(0);">
</dhv:evaluate>
<dhv:evaluate if="<%= ("individual".equals((String) request.getParameter("form_type"))) %>">
  <body onLoad="javascript:document.forms[0].name.focus();updateFormElements(1);">
</dhv:evaluate>
<form name="addAccount" action="Accounts.do?command=Insert&auto-populate=true" onSubmit="return doCheck(this);" method="post">
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td width="100%">
<a href="Accounts.do"><dhv:label name="accounts.accounts">Accounts</dhv:label></a> > 
<dhv:label name="accounts.add">Add Account</dhv:label>
</td>
</tr>
</table>
<%-- End Trails --%>
<input type="submit" value="Insert" name="Save" onClick="this.form.dosubmit.value='true';">
<input type="submit" value="Cancel" onClick="javascript:this.form.action='Accounts.do?command=Search';this.form.dosubmit.value='false';">
<br />
<dhv:formMessage />
<iframe src="empty.html" name="server_commands" id="server_commands" style="visibility:hidden" height="0"></iframe>
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="2">
      <strong>Add a New Account</strong>
    </th>
  </tr>
  <dhv:include name="organization.types" none="true">
  <tr>
    <td nowrap class="formLabel" valign="top">
      <dhv:label name="accounts.account.types">Account Type(s)</dhv:label> 
    </td>
    <td>
      <table border="0" cellspacing="0" cellpadding="0" class="empty">
        <tr>
          <td>
            <select multiple name="selectedList" id="selectedList" size="5">
            <dhv:evaluate exp="<%=OrgDetails.getTypes().isEmpty()%>">
            <option value="-1">None Selected</option>
            </dhv:evaluate>
            <dhv:evaluate exp="<%=!(OrgDetails.getTypes().isEmpty())%>">
      <%
              Iterator i = OrgDetails.getTypes().iterator();
              while (i.hasNext()) {
                LookupElement thisElt = (LookupElement)i.next();
      %>
              <option value="<%=thisElt.getCode()%>"><%=thisElt.getDescription()%></option>
            <%}%>
            </dhv:evaluate>      
            </select>
          </td>
          <td valign="top">
            <input type="hidden" name="previousSelection" value="" />
            &nbsp;[<a href="javascript:popLookupSelectMultiple('selectedList','1','lookup_account_types');">Select</a>]
          </td>
        </tr>
      </table>
    </td>
  </tr>
  </dhv:include>
  <tr>
    <td nowrap class="formLabel">
      Classification
    </td>
    <td>
      <input type="radio" name="form_type" value="organization" onClick="javascript:updateFormElements(0);" <%= (request.getParameter("form_type") == null || "organization".equals((String) request.getParameter("form_type"))) ? " checked" : "" %>>Organization
      <input type="radio" name="form_type" value="individual" onClick="javascript:updateFormElements(1);" <%= "individual".equals((String) request.getParameter("form_type")) ? " checked" : "" %>>Individual
    </td>
  </tr>  
  <tr>
    <td nowrap class="formLabel" name="orgname1" id="orgname1">
      Organization Name
    </td>
    <td>
      <input onFocus="if (indSelected == 1) { tabNext(this) }" type="text" size="35" maxlength="80" name="name" value="<%= toHtmlValue(OrgDetails.getName()) %>"><font color="red">*</font> <%= showAttribute(request, "nameError") %>
    </td>
  </tr>
  <tr>
    <td name="nameFirst1" id="nameFirst1" nowrap class="formLabel">
      First Name
    </td>
    <td>
      <input onFocus="if (orgSelected == 1) { tabNext(this) }" type=text size="35" name="nameFirst" value="<%= toHtmlValue(OrgDetails.getNameFirst()) %>">
    </td>
  </tr>
  <tr>
    <td name="nameMiddle1" id="nameMiddle1" nowrap class="formLabel">
      Middle Name
    </td>
    <td>
      <input onFocus="if (orgSelected == 1) { tabNext(this) }" type=text size="35" name="nameMiddle" value="<%= toHtmlValue(OrgDetails.getNameMiddle()) %>">
    </td>
  </tr>
  <tr>
    <td name="nameLast1" id="nameLast1" nowrap class="formLabel">
      Last Name
    </td>
    <td>
      <input onFocus="if (orgSelected == 1) { tabNext(this) }" type="text" size="35" name="nameLast" value="<%= toHtmlValue(OrgDetails.getNameLast()) %>"><font color="red">*</font> <%= showAttribute(request, "nameLastError") %>
    </td>
  </tr>  
  <tr>
    <td nowrap class="formLabel">
      <dhv:label name="organization.accountNumber">Account Number</dhv:label>
    </td>
    <td>
      <input type="text" size="50" name="accountNumber" maxlength="50" value="<%= toHtmlValue(OrgDetails.getAccountNumber()) %>">
    </td>
  </tr>
  <dhv:include name="organization.url" none="true">
  <tr>
    <td nowrap class="formLabel">
      Web Site URL
    </td>
    <td>
      <input type="text" size="50" name="url" value="<%= toHtmlValue(OrgDetails.getUrl()) %>">
    </td>
  </tr>
  </dhv:include>
  <dhv:include name="organization.industry" none="true">
  <tr>
    <td nowrap class="formLabel">
      Industry
    </td>
    <td>
      <%= IndustryList.getHtmlSelect("industry",OrgDetails.getIndustry()) %>
    </td>
  </tr>
  </dhv:include>
  <dhv:include name="organization.employees" none="true">
  <tr>
    <td nowrap class="formLabel">
      <dhv:label name="organization.employees">No. of Employees</dhv:label>
    </td>
    <td>
      <input type="text" size="10" name="employees" value="<%= OrgDetails.getEmployees() == 0 ? "" : "" + OrgDetails.getEmployees() %>">
    </td>
  </tr>
  </dhv:include>
  <dhv:include name="organization.revenue" none="true">
  <tr>
    <td nowrap class="formLabel">
      Revenue
    </td>
    <td>
      <%= applicationPrefs.get("SYSTEM.CURRENCY") %>
      <input type="text" name="revenue" size="15" value="<zeroio:number value="<%= OrgDetails.getRevenue() %>" locale="<%= User.getLocale() %>" />">
    </td>
  </tr>
  </dhv:include>
  <dhv:include name="organization.ticker" none="true">
  <tr>
    <td name="ticker1" id="ticker1" nowrap class="formLabel">
      Ticker Symbol
    </td>
    <td>
      <input onFocus="if (indSelected == 1) { tabNext(this) }" type="text" size="10" maxlength="10" name="ticker" value="<%= toHtmlValue(OrgDetails.getTicker()) %>">
    </td>
  </tr>
  </dhv:include>
  <dhv:include name="organization.contractEndDate" none="true">
  <tr>
    <td nowrap class="formLabel">
      Contract End Date
    </td>
    <td>
      <zeroio:dateSelect form="addAccount" field="contractEndDate" timestamp="<%= OrgDetails.getContractEndDate() %>" timeZone="<%= OrgDetails.getContractEndDateTimeZone() %>" showTimeZone="yes" />
      <%= showAttribute(request, "contractEndDateError") %>
    </td>
  </tr>
  </dhv:include>
  <dhv:include name="organization.alert" none="true">
  <tr>
    <td nowrap class="formLabel">
      Alert Description
    </td>
    <td>
      <input type="text" size="50" name="alertText" value="<%= toHtmlValue(OrgDetails.getAlertText()) %>">
    </td>
  </tr>
  <tr>
    <td nowrap class="formLabel">
      Alert Date
    </td>
    <td>
      <zeroio:dateSelect form="addAccount" field="alertDate" timestamp="<%= OrgDetails.getAlertDate() %>" timeZone="<%= OrgDetails.getAlertDateTimeZone() %>" showTimeZone="yes" />
      <%= showAttribute(request, "alertDateError") %><%= showWarningAttribute(request, "alertDateWarning") %>
    </td>
  </tr>
  </dhv:include>
</table>
<br>
<%
  boolean noneSelected = false;
%>
<dhv:include name="organization.phoneNumbers" none="true">
<%-- Phone Numbers --%>
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="2">
	    <strong>Phone Numbers</strong>
	  </th>
  </tr>
  <dhv:evaluate exp="<%= (OrgDetails.getPrimaryContact() == null) %>">    
<%  
  int icount = 0;
  Iterator inumber = OrgDetails.getPhoneNumberList().iterator();
  if(inumber.hasNext()){
  while (inumber.hasNext()) {
    ++icount;
    OrganizationPhoneNumber thisPhoneNumber = (OrganizationPhoneNumber)inumber.next();
%>    
  <tr class="containerBody">
    <td class="formLabel">
      Phone <%= icount %>
    </td>
    <td>
      <input type="hidden" name="phone<%= icount %>id" value="<%= thisPhoneNumber.getId() %>">
      <%= OrgPhoneTypeList.getHtmlSelect("phone" + icount + "type", thisPhoneNumber.getType()) %>
      <input type="text" size="20" name="phone<%= icount %>number" value="<%= toHtmlValue(thisPhoneNumber.getNumber()) %>">&nbsp;ext.
      <input type="text" size="5" name="phone<%= icount %>ext" maxlength="10" value="<%= toHtmlValue(thisPhoneNumber.getExtension()) %>">
    </td>
  </tr>    
  <%}
  ++icount;
%>
  <tr class="containerBody">
    <td class="formLabel">
      Phone <%= icount %>
    </td>
    <td>
      <%= OrgPhoneTypeList.getHtmlSelect("phone" + icount + "type", "Business") %>
      <input type="text" size="20" name="phone<%= icount %>number">&nbsp;ext.
      <input type="text" size="5" name="phone<%= icount %>ext" maxlength="10">
    </td>
  </tr>
<%  }else{
    noneSelected = true;
  }
%>
</dhv:evaluate>
<dhv:evaluate exp="<%= (OrgDetails.getPrimaryContact() != null) %>">    
<%  
  int icount = 0;
  Iterator inumber = OrgDetails.getPrimaryContact().getPhoneNumberList().iterator();
  if(inumber.hasNext()){
  while (inumber.hasNext()) {
    ++icount;
    ContactPhoneNumber thisPhoneNumber = (ContactPhoneNumber) inumber.next();
%>
  <tr class="containerBody">
    <td class="formLabel">
      Phone <%= icount %>
    </td>
    <td>
      <input type="hidden" name="phone<%= icount %>id" value="<%= thisPhoneNumber.getId() %>">
      <%= OrgPhoneTypeList.getHtmlSelect("phone" + icount + "type", thisPhoneNumber.getType()) %>
      <input type="text" size="20" name="phone<%= icount %>number" value="<%= toHtmlValue(thisPhoneNumber.getNumber()) %>">&nbsp;ext.
      <input type="text" size="5" name="phone<%= icount %>ext" maxlength="10" value="<%= toHtmlValue(thisPhoneNumber.getExtension()) %>">
    </td>
  </tr>
<%
   }
   ++icount;
%>
  <tr class="containerBody">
    <td class="formLabel">
      Phone <%= icount %>
    </td>
    <td>
      <%= OrgPhoneTypeList.getHtmlSelect("phone" + icount + "type", "Business") %>
      <input type="text" size="20" name="phone<%= icount %>number">&nbsp;ext.
      <input type="text" size="5" name="phone<%= icount %>ext" maxlength="10">
    </td>
  </tr>
<% }else{
    noneSelected = true;
   }
%>
</dhv:evaluate>
<dhv:evaluate if="<%= noneSelected %>">
  <tr>
    <td class="formLabel">
      Phone 1
    </td>
    <td>
      <%= OrgPhoneTypeList.getHtmlSelect("phone1type", "Main") %>
      <input type="text" size="20" name="phone1number">&nbsp;ext.
      <input type="text" size="5" name="phone1ext" maxlength="10">
    </td>
  </tr>
  <tr>
    <td class="formLabel">
      Phone 2
    </td>
    <td>
      <%= OrgPhoneTypeList.getHtmlSelect("phone2type", "Fax") %>
      <input type="text" size="20" name="phone2number" />&nbsp;ext.
      <input type="text" size="5" name="phone2ext" maxlength="10" />
    </td>
  </tr>
</dhv:evaluate>
</table>
<br />
</dhv:include>
<dhv:include name="organization.addresses" none="true">
<%-- Addresses --%>
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="2">
      <strong>Addresses</strong>
    </th>
  </tr>
  <%
    noneSelected = false;
  %>
  <dhv:evaluate exp="<%= (OrgDetails.getPrimaryContact() == null) %>">  
<%  
  int acount = 0;
  Iterator anumber = OrgDetails.getAddressList().iterator();
  if(anumber.hasNext()){
  while (anumber.hasNext()) {
    ++acount;
    OrganizationAddress thisAddress = (OrganizationAddress)anumber.next();
%>    
  <tr class="containerBody">
    <input type="hidden" name="address<%= acount %>id" value="<%= thisAddress.getId() %>">
    <td class="formLabel">
      Type
    </td>
    <td>
      <%= OrgAddressTypeList.getHtmlSelect("address" + acount + "type", thisAddress.getType()) %>
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      Address Line 1
    </td>
    <td>
      <input type="text" size="40" name="address<%= acount %>line1" maxlength="80" value="<%= toHtmlValue(thisAddress.getStreetAddressLine1()) %>">
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      Address Line 2
    </td>
    <td>
      <input type="text" size="40" name="address<%= acount %>line2" maxlength="80" value="<%= toHtmlValue(thisAddress.getStreetAddressLine2()) %>">
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      Address Line 3
    </td>
    <td>
      <input type="text" size="40" name="address<%= acount %>line3" maxlength="80" value="<%= toHtmlValue(thisAddress.getStreetAddressLine3()) %>">
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      City
    </td>
    <td>
      <input type="text" size="28" name="address<%= acount %>city" maxlength="80" value="<%= toHtmlValue(thisAddress.getCity()) %>">
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      State/Province
    </td>
    <td>
      <span name="state1<%= acount %>" ID="state1<%= acount %>" style="<%= ("UNITED STATES".equals(thisAddress.getCountry()) || "CANADA".equals(thisAddress.getCountry()))? "" : " display:none" %>">
        <%= StateSelect.getHtml("address" + acount + "state", thisAddress.getState()) %>
      </span>
      <%-- If selected country is not US/Canada use textfield --%>
      <span name="state2<%= acount %>" ID="state2<%= acount %>" style="<%= (!"UNITED STATES".equals(thisAddress.getCountry()) && !"CANADA".equals(thisAddress.getCountry())) ? "" : " display:none" %>">
        <input type="text" size="25" name="<%= "address" + acount + "otherState" %>"  value="<%= toHtmlValue(thisAddress.getState()) %>">
      </span>
      <% StateSelect = new StateSelect(); %>
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      Zip/Postal Code
    </td>
    <td>
      <input type="text" size="10" name="address<%= acount %>zip" maxlength="12" value="<%= toHtmlValue(thisAddress.getZip()) %>">
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      Country
    </td>
    <td>
      <% CountrySelect.setJsEvent("onChange=\"javascript:update('address" + acount + "country', '" + acount + "');\"");%>
      <%= CountrySelect.getHtml("address" + acount + "country", thisAddress.getCountry()) %>
      <% CountrySelect = new CountrySelect(); %>
    </td>
  </tr>
  <tr class="containerBody">
    <td colspan="2">
      &nbsp;
    </td>
  </tr> 
<%    
  }
  ++acount;
%>
  <tr class="containerBody">
    <td class="formLabel">
      Type
    </td>
    <td>
      <%= OrgAddressTypeList.getHtmlSelect("address" + acount + "type", "Primary") %>
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      Address Line 1
    </td>
    <td>
      <input type="text" size="40" name="address<%= acount %>line1" maxlength="80">
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      Address Line 2
    </td>
    <td>
      <input type="text" size="40" name="address<%= acount %>line2" maxlength="80">
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      Address Line 3
    </td>
    <td>
      <input type="text" size="40" name="address<%= acount %>line3" maxlength="80">
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      City
    </td>
    <td>
      <input type="text" size="28" name="address<%= acount %>city" maxlength="80">
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      State/Province
    </td>
    <td>
      <span name="state1<%= acount %>" ID="state1<%= acount %>">
        <%= StateSelect.getHtml("address" + acount + "state") %>
      </span>
      <%-- If selected country is not US/Canada use textfield --%>
      <span name="state2<%= acount %>" ID="state2<%= acount %>" style="display:none">
        <input type="text" size="25" name="<%= "address" + acount + "otherState" %>">
      </span>
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      Zip/Postal Code
    </td>
    <td>
      <input type="text" size="10" name="address<%= acount %>zip" maxlength="12">
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      Country
    </td>
    <td>
      <% CountrySelect.setJsEvent("onChange=\"javascript:update('address" + acount + "country', '" + acount + "');\"");%>
      <%= CountrySelect.getHtml("address" + acount + "country",applicationPrefs.get("SYSTEM.COUNTRY")) %>
      <% CountrySelect = new CountrySelect(); %>
    </td>
  </tr>
<%
  }else{
    noneSelected = true;
  }
%>
  </dhv:evaluate>
<dhv:evaluate exp="<%= (OrgDetails.getPrimaryContact() != null) %>">  
<%  
  int acount = 0;
  Iterator anumber = OrgDetails.getPrimaryContact().getAddressList().iterator();
  if(anumber.hasNext()){
  while (anumber.hasNext()) {
    ++acount;
    ContactAddress thisAddress = (ContactAddress)anumber.next();
%>    
  <tr class="containerBody">
    <input type="hidden" name="address<%= acount %>id" value="<%= thisAddress.getId() %>">
    <td class="formLabel">
      Type
    </td>
    <td>
      <%= OrgAddressTypeList.getHtmlSelect("address" + acount + "type", thisAddress.getType()) %>
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      Address Line 1
    </td>
    <td>
      <input type="text" size="40" name="address<%= acount %>line1" maxlength="80" value="<%= toHtmlValue(thisAddress.getStreetAddressLine1()) %>">
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      Address Line 2
    </td>
    <td>
      <input type="text" size="40" name="address<%= acount %>line2" maxlength="80" value="<%= toHtmlValue(thisAddress.getStreetAddressLine2()) %>">
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      Address Line 3
    </td>
    <td>
      <input type="text" size="40" name="address<%= acount %>line3" maxlength="80" value="<%= toHtmlValue(thisAddress.getStreetAddressLine3()) %>">
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      City
    </td>
    <td>
      <input type="text" size="28" name="address<%= acount %>city" maxlength="80" value="<%= toHtmlValue(thisAddress.getCity()) %>">
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      State/Province
    </td>
    <td>
      <span name="state1<%= acount %>" ID="state1<%= acount %>" style="<%= ("UNITED STATES".equals(thisAddress.getCountry()) || "CANADA".equals(thisAddress.getCountry()))? "" : " display:none" %>">
        <%= StateSelect.getHtml("address" + acount + "state", thisAddress.getState()) %>
      </span>
      <%-- If selected country is not US/Canada use textfield --%>
      <span name="state2<%= acount %>" ID="state2<%= acount %>" style="<%= (!"UNITED STATES".equals(thisAddress.getCountry()) && !"CANADA".equals(thisAddress.getCountry())) ? "" : " display:none" %>">
        <input type="text" size="25" name="<%= "address" + acount + "otherState" %>"  value="<%= toHtmlValue(thisAddress.getState()) %>">
      </span>
      <% StateSelect = new StateSelect(); %>
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      Zip/Postal Code
    </td>
    <td>
      <input type="text" size="10" name="address<%= acount %>zip" maxlength="12" value="<%= toHtmlValue(thisAddress.getZip()) %>">
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      Country
    </td>
    <td>
      <% CountrySelect.setJsEvent("onChange=\"javascript:update('address" + acount + "country', '" + acount + "');\"");%>
      <%= CountrySelect.getHtml("address" + acount + "country", thisAddress.getCountry()) %>
      <% CountrySelect = new CountrySelect(); %>
    </td>
  </tr>
  <tr class="containerBody">
    <td colspan="2">
      &nbsp;
    </td>
  </tr> 
<%    
  }
   ++acount;
%>
  <tr class="containerBody">
    <td class="formLabel">
      Type
    </td>
    <td>
      <%= OrgAddressTypeList.getHtmlSelect("address" + acount + "type", "Business") %>
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      Address Line 1
    </td>
    <td>
      <input type="text" size="40" name="address<%= acount %>line1" maxlength="80">
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      Address Line 2
    </td>
    <td>
      <input type="text" size="40" name="address<%= acount %>line2" maxlength="80">
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      Address Line 3
    </td>
    <td>
      <input type="text" size="40" name="address<%= acount %>line3" maxlength="80">
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      City
    </td>
    <td>
      <input type="text" size="28" name="address<%= acount %>city" maxlength="80">
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      State/Province
    </td>
    <td>
      <span name="state1<%= acount %>" ID="state1<%= acount %>">
        <%= StateSelect.getHtml("address" + acount + "state") %>
      </span>
      <%-- If selected country is not US/Canada use textfield --%>
      <span name="state2<%= acount %>" ID="state2<%= acount %>" style="display:none">
        <input type="text" size="25" name="<%= "address" + acount + "otherState" %>">
      </span>
      <% StateSelect = new StateSelect(); %>
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      Zip/Postal Code
    </td>
    <td>
      <input type="text" size="10" name="address<%= acount %>zip" maxlength="12">
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      Country
    </td>
    <td>
      <% CountrySelect.setJsEvent("onChange=\"javascript:update('address" + acount + "country', '" + acount + "');\"");%>
      <%= CountrySelect.getHtml("address" + acount + "country",applicationPrefs.get("SYSTEM.COUNTRY")) %>
      <% CountrySelect = new CountrySelect(); %>
    </td>
  </tr>
<%
  }else{
    noneSelected = true;
  }
%>
  </dhv:evaluate>
<dhv:evaluate if="<%= noneSelected %>">
  <tr>
    <td class="formLabel">
      Type
    </td>
    <td>
      <%= OrgAddressTypeList.getHtmlSelect("address1type", "Primary") %>
    </td>
  </tr>
  <tr>
    <td nowrap class="formLabel">
      Address Line 1
    </td>
    <td>
      <input type="text" size="40" name="address1line1" maxlength="80">
    </td>
  </tr>
  <tr>
    <td nowrap class="formLabel">
      Address Line 2
    </td>
    <td>
      <input type="text" size="40" name="address1line2" maxlength="80">
    </td>
  </tr>
  <tr>
    <td nowrap class="formLabel">
      Address Line 3
    </td>
    <td>
      <input type="text" size="40" name="address1line3" maxlength="80">
    </td>
  </tr>
  <tr>
    <td nowrap class="formLabel">
      City
    </td>
    <td>
      <input type="text" size="28" name="address1city" maxlength="80">
    </td>
  </tr>
  <tr>
    <td nowrap class="formLabel">
      State/Province
    </td>
    <td>
      <span name="state11" ID="state11">
        <%= StateSelect.getHtml("address1state") %>
      </span>
      <%-- If selected country is not US/Canada use textfield --%>
      <span name="state21" ID="state21" style="display:none">
        <input type="text" size="25" name="<%= "address1otherState" %>">
      </span>
    </td>
  </tr>
  <tr>
    <td nowrap class="formLabel">
      Zip/Postal Code
    </td>
    <td>
      <input type="text" size="28" name="address1zip" maxlength="12">
    </td>
  </tr>
  <tr>
    <td nowrap class="formLabel">
      Country
    </td>
    <td>
    <% CountrySelect.setJsEvent("onChange=\"javascript:update('address1country', '1');\"");%>
    <%= CountrySelect.getHtml("address1country",applicationPrefs.get("SYSTEM.COUNTRY")) %>
    </td>
  </tr>
  <tr>
    <td colspan="2">&nbsp;</td>
  </tr>
  <tr>
    <td class="formLabel">
      Type
    </td>
    <td>
      <%= OrgAddressTypeList.getHtmlSelect("address2type", "Auxiliary") %>
    </td>
  </tr>
  <tr>
    <td nowrap class="formLabel">
      Address Line 1
    </td>
    <td>
      <input type="text" size="40" name="address2line1" maxlength="80">
    </td>
  </tr>
  <tr>
    <td nowrap class="formLabel">
      Address Line 2
    </td>
    <td>
      <input type="text" size="40" name="address2line2" maxlength="80">
    </td>
  </tr>
  <tr>
    <td nowrap class="formLabel">
      Address Line 3
    </td>
    <td>
      <input type="text" size="40" name="address2line3" maxlength="80">
    </td>
  </tr>
    <td nowrap class="formLabel">
      City
    </td>
    <td>
      <input type="text" size="28" name="address2city" maxlength="80">
    </td>
  </tr>
  <tr>
    <td nowrap class="formLabel">
      State/Province
    </td>
    <td>
      <span name="state12" ID="state12">
        <%= StateSelect.getHtml("address2state") %>
      </span>
      <%-- If selected country is not US/Canada use textfield --%>
      <span name="state22" ID="state22" style="display:none">
        <input type="text" size="25" name="<%= "address2otherState" %>">
      </span>
    </td>
  </tr>
  <tr>
    <td nowrap class="formLabel">
      Zip/Postal Code
    </td>
    <td>
      <input type="text" size="28" name="address2zip" maxlength="12">
    </td>
  </tr>
  <tr>
    <td nowrap class="formLabel">
      Country
    </td>
    <td>
      <%= CountrySelect.getHtml("address2country", applicationPrefs.get("SYSTEM.COUNTRY")) %>
    </td>
  </tr>
 </dhv:evaluate>
</table>
<br />
</dhv:include>
<dhv:include name="organization.emailAddresses" none="true">
<%-- Email Addresses --%>
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="2">
	    <strong>Email Addresses</strong>
	  </th>
  </tr>
  <%
    noneSelected = false;
  %>
 <dhv:evaluate exp="<%=(OrgDetails.getPrimaryContact() == null)%>">
<%
  int ecount = 0;
  Iterator enumber = OrgDetails.getEmailAddressList().iterator();
  if(enumber.hasNext()){
  while (enumber.hasNext()) {
    ++ecount;
    OrganizationEmailAddress thisEmailAddress = (OrganizationEmailAddress)enumber.next();
%>    
  <tr class="containerBody">
    <td class="formLabel">
      Email <%= ecount %>
    </td>
    <td>
      <input type="hidden" name="email<%= ecount %>id" value="<%= thisEmailAddress.getId() %>">
      <%= OrgEmailTypeList.getHtmlSelect("email" + ecount + "type", thisEmailAddress.getType()) %>
      <input type="text" size="40" name="email<%= ecount %>address" maxlength="255" value="<%= toHtmlValue(thisEmailAddress.getEmail()) %>">
    </td>
  </tr>
<%    
  }
   ++ecount;
%>
   <tr class="containerBody">
    <td class="formLabel">
      Email <%= ecount %>
    </td>
    <td>
      <%= OrgEmailTypeList.getHtmlSelect("email" + ecount + "type", "Primary") %>
      <input type="text" size="40" name="email<%= ecount %>address" maxlength="255">
    </td>
  </tr>
<%
  }else{
    noneSelected = true;
  }
%>
</dhv:evaluate>
<dhv:evaluate exp="<%=(OrgDetails.getPrimaryContact() != null)%>">
<%  
  int ecount = 0;
  Iterator enumber = OrgDetails.getPrimaryContact().getEmailAddressList().iterator();
  if(enumber.hasNext()){
  while (enumber.hasNext()) {
    ++ecount;
    ContactEmailAddress thisEmailAddress = (ContactEmailAddress) enumber.next();
%>    
  <tr class="containerBody">
    <td class="formLabel">
      Email <%= ecount %>
    </td>
    <td>
      <input type="hidden" name="email<%= ecount %>id" value="<%= thisEmailAddress.getId() %>">
      <%= OrgEmailTypeList.getHtmlSelect("email" + ecount + "type", thisEmailAddress.getType()) %>
      <input type="text" size="40" name="email<%= ecount %>address" maxlength="255" value="<%= toHtmlValue(thisEmailAddress.getEmail()) %>">
    </td>
  </tr>
<%    
  }
  ++ecount;
%>
  <tr class="containerBody">
    <td class="formLabel">
      Email <%= ecount %>
    </td>
    <td>
      <%= OrgEmailTypeList.getHtmlSelect("email" + ecount + "type", "Business") %>
      <input type="text" size="40" name="email<%= ecount %>address" maxlength="255">
    </td>
  </tr>
<%
  }else{
    noneSelected = true;
  }
%>
</dhv:evaluate>
<dhv:evaluate exp="<%= noneSelected %>">
  <tr>
    <td class="formLabel">
      Email 1
    </td>
    <td>
      <%= OrgEmailTypeList.getHtmlSelect("email1type", "Primary") %>
      <input type="text" size="40" name="email1address" maxlength="255">
    </td>
  </tr>
  <tr>
    <td class="formLabel">
      Email 2
    </td>
    <td>
      <%= OrgEmailTypeList.getHtmlSelect("email2type", "Auxiliary") %>
      <input type="text" size="40" name="email2address" maxlength="255">
    </td>
  </tr>
  </dhv:evaluate>
</table>
<br>
</dhv:include>
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="2">
	    <strong>Additional Details</strong>
	  </th>
  </tr>
  <tr>
    <td valign="top" nowrap class="formLabel">Notes</td>
    <td><TEXTAREA NAME="notes" ROWS="3" COLS="50"><%= toString(OrgDetails.getNotes()) %></TEXTAREA></td>
  </tr>
</table>
<br />
<dhv:label name="accounts.radio.header">Where do you want to go after this action is complete?</dhv:label><br />
<input type="radio" name="target" value="return" <%= request.getParameter("target") == null || "return".equals(request.getParameter("target")) ? " checked" : "" %> /> <dhv:label name="accounts.radio.details">View this account's details</dhv:label><br />
<input type="radio" name="target" value="add_contact" <%= "add_contact".equals(request.getParameter("target")) ? " checked" : "" %> /> <dhv:label name="accounts.radio.addContact">Add a contact to this account</dhv:label><br />
<br />
<input type="hidden" name="onlyWarnings" value="<%=(OrgDetails.getOnlyWarnings()?"on":"off")%>" />
<input type="submit" value="Insert" name="Save" onClick="this.form.dosubmit.value='true';" />
<input type="submit" value="Cancel" onClick="javascript:this.form.action='Accounts.do?command=Search';this.form.dosubmit.value='false';" />
<input type="hidden" name="dosubmit" value="true" />
</form>
</body>


