<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ page import="java.util.*,java.text.DateFormat,org.aspcfs.modules.accounts.base.*,org.aspcfs.controller.*,org.aspcfs.utils.*,org.aspcfs.utils.web.*,org.aspcfs.modules.contacts.base.*" %>
<jsp:useBean id="IndustryList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="OrgAddressTypeList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="OrgEmailTypeList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="ContactAddressTypeList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="ContactEmailTypeList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="OrgDetails" class="org.aspcfs.modules.accounts.base.Organization" scope="request"/>
<jsp:useBean id="OrgPhoneTypeList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="ContactPhoneTypeList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="UserList" class="org.aspcfs.modules.admin.base.UserList" scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<jsp:useBean id="StateSelect" class="org.aspcfs.utils.web.StateSelect" scope="request"/>
<jsp:useBean id="CountrySelect" class="org.aspcfs.utils.web.CountrySelect" scope="request"/>
<%@ include file="../initPage.jsp" %>
<%@ include file="../initPageIsManagerOf.jsp" %>
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
  orgSelected = 0; 
  function doCheck(form) {
    if (form.dosubmit.value == "false") {
      return true;
    } else {
      return(checkForm(form));
    }
  }
  function initializeClassification() {
  <% if (OrgDetails.getPrimaryContact() != null) { %>
      indSelected = 1;
      updateFormElements(1);
  <%} else if (OrgDetails.getPrimaryContact() == null) {%>
      orgSelected = 1;
      updateFormElements(0);
  <%}%>
  }
  function resetFormElements() {
    if (document.getElementById) {
      elm1 = document.getElementById("nameFirst1");
      elm2 = document.getElementById("nameMiddle1");
      elm3 = document.getElementById("nameLast1");
      elm4 = document.getElementById("orgname1");
      elm5 = document.getElementById("ticker1");
      if (elm1) {
        elm1.style.color = "#000000";
        document.addAccount.nameFirst.style.background = "#ffffff";
      }
      if (elm2) {
        elm2.style.color = "#000000";
        document.addAccount.nameMiddle.style.background = "#ffffff";
      }
      if (elm3) {
        elm3.style.color = "#000000";
        document.addAccount.nameLast.style.background = "#ffffff";
      }
      if (elm4) {
        elm4.style.color = "#000000";
        document.addAccount.name.style.background = "#ffffff";
      }
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
        if (elm4) {
          elm4.style.color="#cccccc";
          document.addAccount.name.style.background = "#cccccc";
          document.addAccount.name.value = "";
        }
        if (elm5) {
          elm5.style.color="#cccccc";
          document.addAccount.ticker.style.background = "#cccccc";
          document.addAccount.ticker.value = "";
        }
      } else {
        indSelected = 0;
        orgSelected = 1;
        resetFormElements();
        if (elm1) {
          elm1.style.color = "#cccccc";
          document.addAccount.nameFirst.style.background = "#cccccc";
          document.addAccount.nameFirst.value = "";
        }
        if (elm2) {
          elm2.style.color = "#cccccc";  
          document.addAccount.nameMiddle.style.background = "#cccccc";
          document.addAccount.nameMiddle.value = "";
        }
        if (elm3) {
          elm3.style.color = "#cccccc";      
          document.addAccount.nameLast.style.background = "#cccccc";
          document.addAccount.nameLast.value = "";
        }
      }
    }
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
  function checkForm(form) {
    formTest = true;
    message = "";
    alertMessage = "";
  <dhv:include name="organization.alert" none="true">
    if ((!form.alertDate.value == "") && (!checkDate(form.alertDate.value))) { 
      message += "- Check that Alert Date is entered correctly\r\n";
      formTest = false;
    }
  </dhv:include>
  <dhv:include name="organization.contractEndDate" none="true">
    if ((!form.contractEndDate.value == "") && (!checkDate(form.contractEndDate.value))) { 
      message += "- Check that Contract End Date is entered correctly\r\n";
      formTest = false;
    }
  </dhv:include>
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
<%
    for (int i=1; i<=(OrgDetails.getPhoneNumberList().size()+1); i++) {
%>
  <dhv:evaluate if="<%=(i>1)%>">else </dhv:evaluate>if (!checkPhone(form.phone<%=i%>number.value)) { 
      message += "- At least one entered phone number is invalid.  Make sure there are no invalid characters\r\n";
      formTest = false;
    }
<%
    }
%>
  </dhv:include>
  <dhv:include name="organization.emailAddresses" none="true">
<%
    for (int i=1; i<=(OrgDetails.getEmailAddressList().size()+1); i++) {
%>
  <dhv:evaluate exp="<%=(i>1)%>">else </dhv:evaluate>if (!checkEmail(form.email<%=i%>address.value)) { 
      message += "- At least one entered email address is invalid.  Make sure there are no invalid characters\r\n";
      formTest = false;
    }
<%
    }
%>
  </dhv:include>
  <dhv:include name="organization.url" none="true">
    if (!checkURL(form.url.value)) { 
      message += "- URL entered is invalid.  Make sure there are no invalid characters\r\n";
      formTest = false;
    }
  </dhv:include>
  <dhv:include name="organization.revenue" none="true">
    if (!checkNumber(form.revenue.value)) { 
      message += "- Revenue entered is invalid\r\n";
      formTest = false;
    }
  </dhv:include>
  <dhv:include name="organization.alert" none="true">
    if ((!form.alertDate.value == "") && (!checkAlertDate(form.alertDate.value))) { 
      alertMessage += "Alert Date is before today's date\r\n";
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
<body onLoad="javascript:initializeClassification();">
<form name="addAccount" action="Accounts.do?command=Update&orgId=<%= OrgDetails.getOrgId() %>&auto-populate=true<%= (request.getParameter("popup") != null?"&popup=true":"") %>" onSubmit="return doCheck(this);" method="post">
<%boolean popUp = false;
  if(request.getParameter("popup")!=null){
    popUp = true;
  }%>
<dhv:evaluate exp="<%= !popUp %>">
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="Accounts.do">Accounts</a> > 
<% if (request.getParameter("return") != null) {%>
	<% if (request.getParameter("return").equals("list")) {%>
	<a href="Accounts.do?command=Search">Search Results</a> >
	<%} else if (request.getParameter("return").equals("dashboard")) {%>
	<a href="Accounts.do?command=Dashboard">Dashboard</a> >
	<%}%>
<%} else {%>
<a href="Accounts.do?command=Search">Search Results</a> >
<a href="Accounts.do?command=Details&orgId=<%=OrgDetails.getOrgId()%>">Account Details</a> >
<%}%>
Modify Account
</td>
</tr>
</table>
<%-- End Trails --%>
</dhv:evaluate>
<%@ include file="accounts_details_header_include.jsp" %>
  <dhv:evaluate exp="<%= !popUp %>">
    <% String param1 = "orgId=" + OrgDetails.getOrgId(); %>      
    <dhv:container name="accounts" selected="details" param="<%= param1 %>" style="tabs"/>
  </dhv:evaluate>
<table cellpadding="4" cellspacing="0" border="0" width="100%">
  <tr>
    <td class="containerBack">
      <input type="hidden" name="modified" value="<%= OrgDetails.getModified() %>">
<% if (request.getParameter("return") != null) {%>
      <input type="hidden" name="return" value="<%=request.getParameter("return")%>">
<%}%>
<input type="submit" value="Update" name="Save" onClick="this.form.dosubmit.value='true';">
<% if (request.getParameter("return") != null) {%>
	<% if (request.getParameter("return").equals("list")) {%>
      <input type="submit" value="Cancel" onClick="javascript:this.form.action='Accounts.do?command=Search';this.form.dosubmit.value='false';">
	<%}%>
<%} else {%>
      <input type="submit" value="Cancel" onClick="javascript:this.form.action='Accounts.do?command=Details&orgId=<%= OrgDetails.getOrgId() %>';this.form.dosubmit.value='false';">
<%}%>
<dhv:evaluate exp="<%= popUp %>">
      <input type="button" value="Cancel" onclick="javascript:window.close();">
</dhv:evaluate>
<br>
<%= showError(request, "actionError") %>
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="2">
      <strong>Modify Primary Information</strong>
    </th>     
  </tr>
<dhv:evaluate if="<%= OrgDetails.getOwner() == User.getUserId() || isManagerOf(pageContext, User.getUserId(), OrgDetails.getOwner()) %>">
  <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="organization.owner">Account Owner</dhv:label>
    </td>
    <td>
      <%= UserList.getHtmlSelect("owner", OrgDetails.getOwner() ) %>
    </td>
  </tr>
</dhv:evaluate>
<dhv:include name="organization.types" none="true">
  <tr class="containerBody">
    <td nowrap class="formLabel" valign="top">
      Account Type(s)
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
  <tr class="containerBody">
    <td class="formLabel">
      Classification
    </td>
    <td>
      <dhv:evaluate if="<%= (OrgDetails.getPrimaryContact() != null) %>">
        Individual
        <input type="hidden" name="form_type" value="individual">
      </dhv:evaluate>
      <dhv:evaluate if="<%= (OrgDetails.getPrimaryContact() == null) %>">
        Organization
        <input type="hidden" name="form_type" value="organization">
      </dhv:evaluate>        
    </td>
  </tr>
  <dhv:evaluate if="<%= OrgDetails.getPrimaryContact() == null %>">
  <tr class="containerBody">
    <td nowrap class="formLabel" name="orgname1" id="orgname1">
      Organization Name
    </td>
    <td>
      <input onFocus="if (indSelected == 1) { tabNext(this) }" type="text" size="35" maxlength="80" name="name" value="<%= toHtmlValue(OrgDetails.getName()) %>"><font color="red">*</font> <%= showAttribute(request, "nameError") %>
    </td>
  </tr>
  </dhv:evaluate>
  <dhv:evaluate if="<%= OrgDetails.getPrimaryContact() != null %>">
  <tr class="containerBody">
    <td name="nameFirst1" id="nameFirst1" nowrap class="formLabel">
      First Name
    </td>
    <td>
      <input onFocus="if (orgSelected == 1) { tabNext(this) }" type="text" size="35" name="nameFirst" value="<%= toHtmlValue(OrgDetails.getNameFirst()) %>">
    </td>
  </tr>
  <tr class="containerBody">
    <td name="nameMiddle1" id="nameMiddle1" nowrap class="formLabel">
      Middle Name
    </td>
    <td>
      <input onFocus="if (orgSelected == 1) { tabNext(this) }" type="text" size="35" name="nameMiddle" value="<%= toHtmlValue(OrgDetails.getNameMiddle()) %>">
    </td>
  </tr>
  <tr class="containerBody">
    <td name="nameLast1" id="nameLast1" nowrap class="formLabel">
      Last Name
    </td>
    <td>
      <input onFocus="if (orgSelected == 1) { tabNext(this) }" type="text" size="35" name="nameLast" value="<%= toHtmlValue(OrgDetails.getNameLast()) %>"><font color="red">*</font> <%= showAttribute(request, "nameLastError") %>
    </td>
  </tr>
  </dhv:evaluate>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="organization.accountNumber">Account Number</dhv:label>
    </td>
    <td>
      <input type="text" size="50" name="accountNumber" maxlength="50" value="<%= toHtmlValue(OrgDetails.getAccountNumber()) %>">
    </td>
  </tr>
  <dhv:include name="organization.url" none="true">
  <tr class="containerBody">
    <td class="formLabel">
      Web Site URL
    </td>
    <td>
      <input type="text" size="50" name="url" value="<%= toHtmlValue(OrgDetails.getUrl()) %>">
    </td>
  </tr>
  </dhv:include>
  <dhv:include name="organization.industry" none="true">
  <tr class="containerBody">
    <td class="formLabel">
      Industry
    </td>
    <td>
      <%= IndustryList.getHtmlSelect("industry",OrgDetails.getIndustry()) %>
    </td>
  </tr>
  </dhv:include>
  <dhv:include name="organization.employees" none="true">
  <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="organization.employees">No. of Employees</dhv:label>
    </td>
    <td>
      <input type="text" size="10" name="employees" value="<%= OrgDetails.getEmployees() %>">
    </td>
  </tr>
  </dhv:include>
  <dhv:include name="organization.revenue" none="true">
  <tr class="containerBody">
    <td nowrap class="formLabel">
      Revenue
    </td>
    <td>
      <input type="text" size="10" name="revenue" value="<%= OrgDetails.getRevenue() %>">
    </td>
  </tr>
  </dhv:include>
  <dhv:include name="organization.ticker" none="true">
  <tr class="containerBody">
    <td name="ticker1" id="ticker1" nowrap class="formLabel">
      Ticker Symbol
    </td>
    <td>
      <input onFocus="if (indSelected == 1) { tabNext(this) }" type="text" size="10" maxlength="10" name="ticker" value="<%= toHtmlValue(OrgDetails.getTicker()) %>">
    </td>
  </tr>
  </dhv:include>
  <dhv:include name="organization.contractEndDate" none="true">
  <tr class="containerBody">
    <td nowrap class="formLabel">
      Contract End Date
    </td>
    <td>
      <input type="text" size="10" name="contractEndDate" value="<zeroio:tz timestamp="<%= OrgDetails.getContractEndDate() %>" dateOnly="true" />">
      <a href="javascript:popCalendar('addAccount', 'contractEndDate', '<%= User.getLocale().getLanguage() %>', '<%= User.getLocale().getCountry() %>');"><img src="images/icons/stock_form-date-field-16.gif" height="16" width="16" border="0" align="absmiddle"></a>
    </td>
  </tr>
  </dhv:include>
  <dhv:include name="organization.alert" none="true">
  <tr class="containerBody">
    <td nowrap class="formLabel">
      Alert Description
    </td>
    <td>
      <input type="text" size="50" name="alertText" value="<%= toHtmlValue(OrgDetails.getAlertText()) %>">
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      Alert Date
    </td>
    <td>
      <input type="text" size="10" name="alertDate" value="<zeroio:tz timestamp="<%= OrgDetails.getAlertDate() %>" dateOnly="true" />">
      <a href="javascript:popCalendar('addAccount', 'alertDate', '<%= User.getLocale().getLanguage() %>', '<%= User.getLocale().getCountry() %>');"><img src="images/icons/stock_form-date-field-16.gif" height="16" width="16" border="0" align="absmiddle"></a>
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
      <input type="checkbox" name="phone<%= icount %>delete" value="on">mark to remove
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
      <%= OrgPhoneTypeList.getHtmlSelect("phone" + icount + "type", "Main") %>
      <input type="text" size="20" name="phone<%= icount %>number">&nbsp;ext.
      <input type="text" size="5" name="phone<%= icount %>ext" maxlength="10">
    </td>
  </tr>
</dhv:evaluate>
<dhv:evaluate exp="<%= (OrgDetails.getPrimaryContact() != null) %>">    
<%  
  int icount = 0;
  Iterator inumber = OrgDetails.getPrimaryContact().getPhoneNumberList().iterator();
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
      <%= ContactPhoneTypeList.getHtmlSelect("phone" + icount + "type", thisPhoneNumber.getType()) %>
      <input type="text" size="20" name="phone<%= icount %>number" value="<%= toHtmlValue(thisPhoneNumber.getNumber()) %>">&nbsp;ext.
      <input type="text" size="5" name="phone<%= icount %>ext" maxlength="10" value="<%= toHtmlValue(thisPhoneNumber.getExtension()) %>">
      <input type="checkbox" name="phone<%= icount %>delete" value="on">mark to remove
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
      <%= ContactPhoneTypeList.getHtmlSelect("phone" + icount + "type", "Business") %>
      <input type="text" size="20" name="phone<%= icount %>number">&nbsp;ext.
      <input type="text" size="5" name="phone<%= icount %>ext" maxlength="10">
    </td>
  </tr>
</dhv:evaluate>
</table>
<br />
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="2">
      <strong>Addresses</strong>
    </th>
  </tr>
<dhv:evaluate exp="<%= (OrgDetails.getPrimaryContact() == null) %>">  
<%  
  int acount = 0;
  Iterator anumber = OrgDetails.getAddressList().iterator();
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
      <input type="checkbox" name="address<%= acount %>delete" value="on">mark to remove
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
      <span name="state1<%= acount %>" ID="state1<%= acount %>" style="<%= ("UNITED STATES".equals(thisAddress.getCountry()) || "CANADA".equals(thisAddress.getCountry())) ? "" : " display:none" %>">
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
      <%= CountrySelect.getHtml("address" + acount + "country") %>
      <% CountrySelect = new CountrySelect(); %>
    </td>
  </tr>
  </dhv:evaluate>
<dhv:evaluate exp="<%= (OrgDetails.getPrimaryContact() != null) %>">  
<%  
  int acount = 0;
  Iterator anumber = OrgDetails.getPrimaryContact().getAddressList().iterator();
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
      <%= ContactAddressTypeList.getHtmlSelect("address" + acount + "type", thisAddress.getType()) %>
      <input type="checkbox" name="address<%= acount %>delete" value="on">mark to remove
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
      <span name="state1<%= acount %>" ID="state1<%= acount %>" style="<%= ("UNITED STATES".equals(thisAddress.getCountry()) || "CANADA".equals(thisAddress.getCountry())) ? "" : " display:none" %>">
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
      <%= ContactAddressTypeList.getHtmlSelect("address" + acount + "type", "Business") %>
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
      <%= CountrySelect.getHtml("address" + acount + "country") %>
      <% CountrySelect = new CountrySelect(); %>
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
<dhv:evaluate exp="<%=(OrgDetails.getPrimaryContact() == null)%>">
<%  
  int ecount = 0;
  Iterator enumber = OrgDetails.getEmailAddressList().iterator();
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
      <input type="checkbox" name="email<%= ecount %>delete" value="on">mark to remove
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
</dhv:evaluate>
<dhv:evaluate exp="<%=(OrgDetails.getPrimaryContact() != null)%>">
<%  
  int ecount = 0;
  Iterator enumber = OrgDetails.getPrimaryContact().getEmailAddressList().iterator();
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
      <%= ContactEmailTypeList.getHtmlSelect("email" + ecount + "type", thisEmailAddress.getType()) %>
      <input type="text" size="40" name="email<%= ecount %>address" maxlength="255" value="<%= toHtmlValue(thisEmailAddress.getEmail()) %>">
      <input type="checkbox" name="email<%= ecount %>delete" value="on">mark to remove
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
      <%= ContactEmailTypeList.getHtmlSelect("email" + ecount + "type", "Business") %>
      <input type="text" size="40" name="email<%= ecount %>address" maxlength="255">
    </td>
  </tr>
</dhv:evaluate>
</table>
<br />
</dhv:include>
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="2">
      <strong>Additional Details</strong>
    </th>
  </tr>  
  <tr class="containerBody">
    <td valign="top" class="formLabel">
      Notes
    </td>
    <td>
      <TEXTAREA NAME="notes" ROWS="3" COLS="50"><%= toString(OrgDetails.getNotes()) %></TEXTAREA>
    </td>
  </tr>
</table>
<br />
<input type="submit" value="Update" name="Save" onClick="this.form.dosubmit.value='true';">
<% if (request.getParameter("return") != null) {%>
	<% if (request.getParameter("return").equals("list")) {%>
	<input type="submit" value="Cancel" onClick="javascript:this.form.action='Accounts.do?command=Search';this.form.dosubmit.value='false';">
	<%}%>
<%} else {%>
<input type="submit" value="Cancel" onClick="javascript:this.form.action='Accounts.do?command=Details&orgId=<%= OrgDetails.getOrgId() %>';this.form.dosubmit.value='false';">
<%}%>
<dhv:evaluate exp="<%= popUp %>">
  <input type="button" value="Cancel" onclick="javascript:window.close();">
</dhv:evaluate>
<input type="hidden" name="dosubmit" value="true">
  </td>
  </tr>
</table>
</form>

