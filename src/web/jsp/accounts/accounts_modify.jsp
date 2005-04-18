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
<jsp:useBean id="applicationPrefs" class="org.aspcfs.controller.ApplicationPrefs" scope="application"/>
<jsp:useBean id="TimeZoneSelect" class="org.aspcfs.utils.web.HtmlSelectTimeZone" scope="request"/>
<%@ include file="../initPage.jsp" %>
<%@ include file="../initPageIsManagerOf.jsp" %>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/checkDate.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/checkString.js"></script>
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
  if (form.name){
    if ((orgSelected == 1) && (checkNullString(form.name.value))){
      message += label("organization.required", "- Organization name is required\r\n");
      formTest = false;
    }
  }
  if (form.nameLast){
    if ((indSelected == 1) && (checkNullString(form.nameLast.value))){
      message += label("check.lastname", "- Last name is a required field\r\n");
      formTest = false;
    }
  }
  <dhv:include name="organization.alert" none="true">
    if ((!checkNullString(form.alertText.value)) && (checkNullString(form.alertDate.value))) { 
      message += label("specify.alert.date", "- Please specify an alert date\r\n");
      formTest = false;
    }
    if ((!checkNullString(form.alertDate.value)) && (checkNullString(form.alertText.value))) { 
      message += label("specify.alert.description", "- Please specify an alert description\r\n");
      formTest = false;
    }
  </dhv:include>
  <dhv:include name="organization.phoneNumbers" none="true">
<%
    for (int i=1; i<=OrgDetails.getPhoneNumberList().size(); i++) {
%>
  <dhv:evaluate if="<%=(i>1)%>">else </dhv:evaluate>if (!checkPhone(form.phone<%=i%>number.value)) { 
      message += label("check.phone", "- At least one entered phone number is invalid.  Make sure there are no invalid characters\r\n");
      formTest = false;
    }
<%
    }
    for (int i=1; i<=OrgDetails.getPhoneNumberList().size(); i++) {
%>
      <dhv:evaluate if="<%=(i>1)%>">else </dhv:evaluate>if ((checkNullString(form.phone<%= i %>ext.value) && form.phone<%= i %>ext.value != "")) {
        message += label("check.phone.ext","- Please enter a valid phone number extension\r\n");
        formTest = false;
      }
  <%}%>
  </dhv:include>
  <dhv:include name="organization.emailAddresses" none="true">
<%
    for (int i=1; i<=OrgDetails.getEmailAddressList().size(); i++) {
%>
  <dhv:evaluate if="<%=(i>1)%>">else </dhv:evaluate>if (!checkEmail(form.email<%=i%>address.value)) {
      message += label("check.email", "- At least one entered email address is invalid.  Make sure there are no invalid characters\r\n");
      formTest = false;
    }
<%
    }
%>
  </dhv:include>
  <dhv:include name="organization.url" none="true">
    if (!checkURL(form.url.value)) { 
      message += label("URL.invalid", "- URL entered is invalid.  Make sure there are no invalid characters\r\n");
      formTest = false;
    }
  </dhv:include>
    if (formTest == false) {
      alert(label("check.form", "Form could not be saved, please check the following:\r\n\r\n") + message);
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
<dhv:evaluate if="<%= !popUp %>">
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="Accounts.do"><dhv:label name="accounts.accounts">Accounts</dhv:label></a> > 
<% if (request.getParameter("return") != null) {%>
	<% if (request.getParameter("return").equals("list")) {%>
	<a href="Accounts.do?command=Search"><dhv:label name="accounts.SearchResults">Search Results</dhv:label></a> >
	<%} else if (request.getParameter("return").equals("dashboard")) {%>
	<a href="Accounts.do?command=Dashboard"><dhv:label name="communications.campaign.Dashboard">Dashboard</dhv:label></a> >
	<%}%>
<%} else {%>
<a href="Accounts.do?command=Search"><dhv:label name="accounts.SearchResults">Search Results</dhv:label></a> >
<a href="Accounts.do?command=Details&orgId=<%=OrgDetails.getOrgId()%>"><dhv:label name="accounts.details">Account Details</dhv:label></a> >
<%}%>
<dhv:label name="accounts.modify">Modify Account</dhv:label>
</td>
</tr>
</table>
<%-- End Trails --%>
</dhv:evaluate>
<dhv:container name="accounts" selected="details" object="OrgDetails" param="<%= "orgId=" + OrgDetails.getOrgId() %>">
      <input type="hidden" name="modified" value="<%= OrgDetails.getModified() %>">
<% if (request.getParameter("return") != null) {%>
      <input type="hidden" name="return" value="<%=request.getParameter("return")%>">
<%}%>
<input type="submit" value="<dhv:label name="global.button.update">Update</dhv:label>" name="Save" onClick="this.form.dosubmit.value='true';" />
<% if (request.getParameter("return") != null) {%>
	<% if (request.getParameter("return").equals("list")) {%>
      <input type="submit" value="<dhv:label name="global.button.cancel">Cancel</dhv:label>" onClick="javascript:this.form.action='Accounts.do?command=Search';this.form.dosubmit.value='false';" />
	<%}%>
<%} else {%>
      <input type="submit" value="<dhv:label name="global.button.cancel">Cancel</dhv:label>" onClick="javascript:this.form.action='Accounts.do?command=Details&orgId=<%= OrgDetails.getOrgId() %>';this.form.dosubmit.value='false';" />
<%}%>
<dhv:evaluate if="<%= popUp %>">
      <input type="button" value="<dhv:label name="global.button.cancel">Cancel</dhv:label>" onclick="javascript:window.close();" />
</dhv:evaluate>
<br />
<dhv:formMessage />
<iframe src="empty.html" name="server_commands" id="server_commands" style="visibility:hidden" height="0"></iframe>
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="2">
      <strong><dhv:label name="accounts.accounts_modify.ModifyPrimaryInformation">Modify Primary Information</dhv:label></strong>
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
      <dhv:label name="accounts.account.types">Account Type(s)</dhv:label> 
    </td>
  	<td>
      <table border="0" cellspacing="0" cellpadding="0" class="empty">
        <tr>
          <td>
            <select multiple name="selectedList" id="selectedList" size="5">
            <dhv:evaluate if="<%=OrgDetails.getTypes().isEmpty()%>">
            <option value="-1"><dhv:label name="accounts.accounts_add.NoneSelected">None Selected</dhv:label></option>
            </dhv:evaluate>
            <dhv:evaluate if="<%=!(OrgDetails.getTypes().isEmpty())%>">
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
            &nbsp;[<a href="javascript:popLookupSelectMultiple('selectedList','1','lookup_account_types');"><dhv:label name="accounts.accounts_add.select">Select</dhv:label></a>]
          </td>
        </tr>
      </table>
    </td>
  </tr>
</dhv:include>
<dhv:include name="organization.classification" none="true">
  <tr class="containerBody">
    <td class="formLabel">
      <dhv:label name="accounts.accounts_add.Classification">Classification</dhv:label>
    </td>
    <td>
      <dhv:evaluate if="<%= (OrgDetails.getPrimaryContact() != null) %>">
        <dhv:label name="accounts.accounts_add.Individual">Individual</dhv:label>
      </dhv:evaluate>
      <dhv:evaluate if="<%= (OrgDetails.getPrimaryContact() == null) %>">
        <dhv:label name="accounts.accounts_add.Organization">Organization</dhv:label>
      </dhv:evaluate>
    </td>
  </tr>
</dhv:include>
  <dhv:evaluate if="<%= (OrgDetails.getPrimaryContact() != null) %>">
    <input type="hidden" name="form_type" value="individual">
  </dhv:evaluate>
  <dhv:evaluate if="<%= (OrgDetails.getPrimaryContact() == null) %>">
    <input type="hidden" name="form_type" value="organization">
  </dhv:evaluate>        
  <dhv:evaluate if="<%= OrgDetails.getPrimaryContact() == null %>">
  <tr class="containerBody">
    <td nowrap class="formLabel" name="orgname1" id="orgname1">
      <dhv:label name="accounts.accounts_add.OrganizationName">Organization Name</dhv:label>
    </td>
    <td>
      <input onFocus="if (indSelected == 1) { tabNext(this) }" type="text" size="35" maxlength="80" name="name" value="<%= toHtmlValue(OrgDetails.getName()) %>"><font color="red">*</font> <%= showAttribute(request, "nameError") %>
    </td>
  </tr>
  </dhv:evaluate>
  <dhv:evaluate if="<%= OrgDetails.getPrimaryContact() != null %>">
  <tr class="containerBody">
    <td name="nameFirst1" id="nameFirst1" nowrap class="formLabel">
      <dhv:label name="accounts.accounts_add.FirstName">First Name</dhv:label>
    </td>
    <td>
      <input onFocus="if (orgSelected == 1) { tabNext(this) }" type="text" size="35" name="nameFirst" value="<%= toHtmlValue(OrgDetails.getNameFirst()) %>">
    </td>
  </tr>
  <tr class="containerBody">
    <td name="nameMiddle1" id="nameMiddle1" nowrap class="formLabel">
      <dhv:label name="accounts.accounts_add.MiddleName">Middle Name</dhv:label>
    </td>
    <td>
      <input onFocus="if (orgSelected == 1) { tabNext(this) }" type="text" size="35" name="nameMiddle" value="<%= toHtmlValue(OrgDetails.getNameMiddle()) %>">
    </td>
  </tr>
  <tr class="containerBody">
    <td name="nameLast1" id="nameLast1" nowrap class="formLabel">
      <dhv:label name="accounts.accounts_add.LastName">Last Name</dhv:label>
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
      <dhv:label name="accounts.accounts_add.WebSiteURL">Web Site URL</dhv:label>
    </td>
    <td>
      <input type="text" size="50" name="url" value="<%= toHtmlValue(OrgDetails.getUrl()) %>">
    </td>
  </tr>
  </dhv:include>
  <dhv:include name="organization.industry" none="true">
  <tr class="containerBody">
    <td class="formLabel">
      <dhv:label name="accounts.accounts_add.Industry">Industry</dhv:label>
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
      <dhv:label name="accounts.accounts_add.Revenue">Revenue</dhv:label>
    </td>
    <td>
      <%= applicationPrefs.get("SYSTEM.CURRENCY") %>
      <input type="text" name="revenue" size="15" value="<zeroio:number value="<%= OrgDetails.getRevenue() %>" locale="<%= User.getLocale() %>" />">
    </td>
  </tr>
  </dhv:include>
  <dhv:include name="organization.ticker" none="true">
  <tr class="containerBody">
    <td name="ticker1" id="ticker1" nowrap class="formLabel">
      <dhv:label name="accounts.accounts_add.TickerSymbol">Ticker Symbol</dhv:label>
    </td>
    <td>
      <input onFocus="if (indSelected == 1) { tabNext(this) }" type="text" size="10" maxlength="10" name="ticker" value="<%= toHtmlValue(OrgDetails.getTicker()) %>">
    </td>
  </tr>
  </dhv:include>
  <dhv:include name="organization.contractEndDate" none="true">
  <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="accounts.accounts_add.ContractEndDate">Contract End Date</dhv:label>
    </td>
    <td>
      <zeroio:dateSelect form="addAccount" field="contractEndDate" timestamp="<%= OrgDetails.getContractEndDate() %>" timeZone="<%= OrgDetails.getContractEndDateTimeZone() %>" showTimeZone="true" />
      <%= showAttribute(request, "contractEndDateError") %>
    </td>
  </tr>
  </dhv:include>
  <dhv:include name="organization.alert" none="true">
  <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="accounts.accounts_add.AlertDescription">Alert Description</dhv:label>
    </td>
    <td>
      <input type="text" size="50" name="alertText" value="<%= toHtmlValue(OrgDetails.getAlertText()) %>">
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="accounts.accounts_add.AlertDate">Alert Date</dhv:label>
    </td>
    <td>
      <zeroio:dateSelect form="addAccount" field="alertDate" timestamp="<%= OrgDetails.getAlertDate() %>" timeZone="<%= OrgDetails.getAlertDateTimeZone() %>" showTimeZone="true" />
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
	    <strong><dhv:label name="accounts.accounts_add.PhoneNumbers">Phone Numbers</dhv:label></strong>
	  </th>
  </tr>
<dhv:evaluate if="<%= (OrgDetails.getPrimaryContact() == null) %>">
<%  
  int icount = 0;
  Iterator inumber = OrgDetails.getPhoneNumberList().iterator();
  while (inumber.hasNext()) {
    ++icount;
    OrganizationPhoneNumber thisPhoneNumber = (OrganizationPhoneNumber)inumber.next();
%>    
  <tr class="containerBody">
    <td class="formLabel">
      <dhv:label name="accounts.accounts_add.Phone">Phone</dhv:label> <%= icount %>
    </td>
    <td>
      <input type="hidden" name="phone<%= icount %>id" value="<%= thisPhoneNumber.getId() %>">
      <%= OrgPhoneTypeList.getHtmlSelect("phone" + icount + "type", thisPhoneNumber.getType()) %>
      <input type="text" size="20" name="phone<%= icount %>number" value="<%= toHtmlValue(thisPhoneNumber.getNumber()) %>">&nbsp;<dhv:label name="accounts.accounts_add.ext">ext.</dhv:label>
      <input type="text" size="5" name="phone<%= icount %>ext" maxlength="10" value="<%= toHtmlValue(thisPhoneNumber.getExtension()) %>">
      <input type="radio" name="primaryNumber" value="<%= icount %>" <%= (thisPhoneNumber.getPrimaryNumber()) ? " checked" : "" %>>Primary
      <input type="checkbox" name="phone<%= icount %>delete" value="on"><dhv:label name="accounts.accounts_modify.MarkToRemove">mark to remove</dhv:label>
    </td>
  </tr>    
<%    
  }
  ++icount;
%>
  <tr class="containerBody">
    <td class="formLabel">
      <dhv:label name="accounts.accounts_add.Phone">Phone</dhv:label> <%= icount %>
    </td>
    <td>
      <%= OrgPhoneTypeList.getHtmlSelect("phone" + icount + "type", "Main") %>
      <input type="text" size="20" name="phone<%= icount %>number">&nbsp;<dhv:label name="accounts.accounts_add.ext">ext.</dhv:label>
      <input type="text" size="5" name="phone<%= icount %>ext" maxlength="10" />
      <input type="radio" name="primaryNumber" value="<%= icount %>">Primary
    </td>
  </tr>
</dhv:evaluate>
<dhv:evaluate if="<%= (OrgDetails.getPrimaryContact() != null) %>">
<%  
  int icount = 0;
  Iterator inumber = OrgDetails.getPrimaryContact().getPhoneNumberList().iterator();
  while (inumber.hasNext()) {
    ++icount;
    ContactPhoneNumber thisPhoneNumber = (ContactPhoneNumber) inumber.next();
%>
  <tr class="containerBody">
    <td class="formLabel">
      <dhv:label name="accounts.accounts_add.Phone">Phone</dhv:label> <%= icount %>
    </td>
    <td>
      <input type="hidden" name="phone<%= icount %>id" value="<%= thisPhoneNumber.getId() %>">
      <%= ContactPhoneTypeList.getHtmlSelect("phone" + icount + "type", thisPhoneNumber.getType()) %>
      <input type="text" size="20" name="phone<%= icount %>number" value="<%= toHtmlValue(thisPhoneNumber.getNumber()) %>">&nbsp;<dhv:label name="accounts.accounts_add.ext">ext.</dhv:label>
      <input type="text" size="5" name="phone<%= icount %>ext" maxlength="10" value="<%= toHtmlValue(thisPhoneNumber.getExtension()) %>">
      <input type="checkbox" name="phone<%= icount %>delete" value="on"><dhv:label name="accounts.accounts_modify.MarkToRemove">mark to remove</dhv:label>
      <input type="radio" name="primaryNumber" value="<%= icount %>" <%= (thisPhoneNumber.getPrimaryNumber()) ? " checked" : "" %>>Primary
    </td>
  </tr>
<%
  }
  ++icount;
%>
  <tr class="containerBody">
    <td class="formLabel">
      <dhv:label name="accounts.accounts_add.Phone">Phone</dhv:label> <%= icount %>
    </td>
    <td>
      <%= ContactPhoneTypeList.getHtmlSelect("phone" + icount + "type", "") %>
      <input type="text" size="20" name="phone<%= icount %>number">&nbsp;<dhv:label name="accounts.accounts_add.ext">ext.</dhv:label>
      <input type="text" size="5" name="phone<%= icount %>ext" maxlength="10">
      <input type="radio" name="primaryNumber" value="<%= icount %>">Primary
    </td>
  </tr>
</dhv:evaluate>
</table>
<br />
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="2">
      <strong><dhv:label name="accounts.accounts_add.Addresses">Addresses</dhv:label></strong>
    </th>
  </tr>
<dhv:evaluate if="<%= (OrgDetails.getPrimaryContact() == null) %>">
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
      <dhv:label name="accounts.accounts_add.Type">Type</dhv:label>
    </td>
    <td>
      <%= OrgAddressTypeList.getHtmlSelect("address" + acount + "type", thisAddress.getType()) %>
      <input type="radio" name="primaryAddress" value="<%=acount%>" <%= thisAddress.getPrimaryAddress() ? " checked" : ""%>>Primary
      <input type="checkbox" name="address<%= acount %>delete" value="on"><dhv:label name="accounts.accounts_modify.MarkToRemove">mark to remove</dhv:label>
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="accounts.accounts_add.AddressLine1">Address Line 1</dhv:label>
    </td>
    <td>
      <input type="text" size="40" name="address<%= acount %>line1" maxlength="80" value="<%= toHtmlValue(thisAddress.getStreetAddressLine1()) %>">
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="accounts.accounts_add.AddressLine2">Address Line 2</dhv:label>
    </td>
    <td>
      <input type="text" size="40" name="address<%= acount %>line2" maxlength="80" value="<%= toHtmlValue(thisAddress.getStreetAddressLine2()) %>">
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="accounts.accounts_add.AddressLine3">Address Line 3</dhv:label>
    </td>
    <td>
      <input type="text" size="40" name="address<%= acount %>line3" maxlength="80" value="<%= toHtmlValue(thisAddress.getStreetAddressLine3()) %>">
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="accounts.accounts_add.City">City</dhv:label>
    </td>
    <td>
      <input type="text" size="28" name="address<%= acount %>city" maxlength="80" value="<%= toHtmlValue(thisAddress.getCity()) %>">
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="accounts.accounts_add.StateProvince">State/Province</dhv:label>
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
      <dhv:label name="accounts.accounts_add.ZipPostalCode">Zip/Postal Code</dhv:label>
    </td>
    <td>
      <input type="text" size="10" name="address<%= acount %>zip" maxlength="12" value="<%= toHtmlValue(thisAddress.getZip()) %>">
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="accounts.accounts_add.Country">Country</dhv:label>
    </td>
    <td>
      <% CountrySelect.setJsEvent("onChange=\"javascript:update('address" + acount + "country', '" + acount + "');\"");%>
      <%= CountrySelect.getHtml("address" + acount + "country", thisAddress.getCountry()) %>
      <script type="text/javascript">
        update('address<%= acount %>country','<%= acount %>');
      </script>
      <%
        CountrySelect = new CountrySelect();
       %>
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
     <dhv:label name="accounts.accounts_add.Type">Type</dhv:label>
    </td>
    <td>
      <%= OrgAddressTypeList.getHtmlSelect("address" + acount + "type", "") %>
      <input type="radio" name="primaryAddress" value="<%=acount%>">Primary
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="accounts.accounts_add.AddressLine1">Address Line 1</dhv:label>
    </td>
    <td>
      <input type="text" size="40" name="address<%= acount %>line1" maxlength="80">
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="accounts.accounts_add.AddressLine2">Address Line 2</dhv:label>
    </td>
    <td>
      <input type="text" size="40" name="address<%= acount %>line2" maxlength="80">
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="accounts.accounts_add.AddressLine3">Address Line 3</dhv:label>
    </td>
    <td>
      <input type="text" size="40" name="address<%= acount %>line3" maxlength="80">
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="accounts.accounts_add.City">City</dhv:label>
    </td>
    <td>
      <input type="text" size="28" name="address<%= acount %>city" maxlength="80">
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="accounts.accounts_add.StateProvince">State/Province</dhv:label>
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
      <dhv:label name="accounts.accounts_add.ZipPostalCode">Zip/Postal Code</dhv:label>
    </td>
    <td>
      <input type="text" size="10" name="address<%= acount %>zip" maxlength="12">
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="accounts.accounts_add.Country">Country</dhv:label>
    </td>
    <td>
      <% CountrySelect.setJsEvent("onChange=\"javascript:update('address" + acount + "country', '" + acount + "');\"");%>
      <%= CountrySelect.getHtml("address" + acount + "country",applicationPrefs.get("SYSTEM.COUNTRY")) %>
      <script type="text/javascript">
        update('address<%= acount %>country','<%= acount %>');
      </script>
      <%
        CountrySelect = new CountrySelect();
       %>
    </td>
  </tr>
  </dhv:evaluate>
<dhv:evaluate if="<%= (OrgDetails.getPrimaryContact() != null) %>">
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
      <dhv:label name="accounts.accounts_add.Type">Type</dhv:label>
    </td>
    <td>
      <%= ContactAddressTypeList.getHtmlSelect("address" + acount + "type", thisAddress.getType()) %>
      <input type="radio" name="primaryAddress" value="<%=acount%>" <%= thisAddress.getPrimaryAddress() ? " checked" : ""%>>Primary
      <input type="checkbox" name="address<%= acount %>delete" value="on"><dhv:label name="accounts.accounts_modify.MarkToRemove">mark to remove</dhv:label>
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="accounts.accounts_add.AddressLine1">Address Line 1</dhv:label>
    </td>
    <td>
      <input type="text" size="40" name="address<%= acount %>line1" maxlength="80" value="<%= toHtmlValue(thisAddress.getStreetAddressLine1()) %>">
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="accounts.accounts_add.AddressLine2">Address Line 2</dhv:label>
    </td>
    <td>
      <input type="text" size="40" name="address<%= acount %>line2" maxlength="80" value="<%= toHtmlValue(thisAddress.getStreetAddressLine2()) %>">
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="accounts.accounts_add.AddressLine3">Address Line 3</dhv:label>
    </td>
    <td>
      <input type="text" size="40" name="address<%= acount %>line3" maxlength="80" value="<%= toHtmlValue(thisAddress.getStreetAddressLine3()) %>">
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="accounts.accounts_add.City">City</dhv:label>
    </td>
    <td>
      <input type="text" size="28" name="address<%= acount %>city" maxlength="80" value="<%= toHtmlValue(thisAddress.getCity()) %>">
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="accounts.accounts_add.StateProvince">State/Province</dhv:label>
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
      <dhv:label name="accounts.accounts_add.ZipPostalCode">Zip/Postal Code</dhv:label>
    </td>
    <td>
      <input type="text" size="10" name="address<%= acount %>zip" maxlength="12" value="<%= toHtmlValue(thisAddress.getZip()) %>">
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="accounts.accounts_add.Country">Country</dhv:label>
    </td>
    <td>
      <% CountrySelect.setJsEvent("onChange=\"javascript:update('address" + acount + "country', '" + acount + "');\"");%>
      <%= CountrySelect.getHtml("address" + acount + "country", thisAddress.getCountry()) %>
      <script type="text/javascript">
        update('address<%= acount %>country','<%= acount %>');
      </script>
      <%
        CountrySelect = new CountrySelect();
       %>
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
      <dhv:label name="accounts.accounts_add.Type">Type</dhv:label>
    </td>
    <td>
      <%= ContactAddressTypeList.getHtmlSelect("address" + acount + "type", "") %>
      <input type="radio" name="primaryAddress" value="<%=acount%>">Primary
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="accounts.accounts_add.AddressLine1">Address Line 1</dhv:label>
    </td>
    <td>
      <input type="text" size="40" name="address<%= acount %>line1" maxlength="80">
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="accounts.accounts_add.AddressLine2">Address Line 2</dhv:label>
    </td>
    <td>
      <input type="text" size="40" name="address<%= acount %>line2" maxlength="80">
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="accounts.accounts_add.AddressLine3">Address Line 3</dhv:label>
    </td>
    <td>
      <input type="text" size="40" name="address<%= acount %>line3" maxlength="80">
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="accounts.accounts_add.City">City</dhv:label>
    </td>
    <td>
      <input type="text" size="28" name="address<%= acount %>city" maxlength="80">
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="accounts.accounts_add.StateProvince">State/Province</dhv:label>
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
      <dhv:label name="accounts.accounts_add.ZipPostalCode">Zip/Postal Code</dhv:label>
    </td>
    <td>
      <input type="text" size="10" name="address<%= acount %>zip" maxlength="12">
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="accounts.accounts_add.Country">Country</dhv:label>
    </td>
    <td>
      <% CountrySelect.setJsEvent("onChange=\"javascript:update('address" + acount + "country', '" + acount + "');\"");%>
      <%= CountrySelect.getHtml("address" + acount + "country",applicationPrefs.get("SYSTEM.COUNTRY")) %>
      <script type="text/javascript">
        update('address<%= acount %>country','<%= acount %>');
      </script>
      <%
        CountrySelect = new CountrySelect();
       %>
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
	    <strong><dhv:label name="accounts.accounts_add.EmailAddresses">Email Addresses</dhv:label></strong>
	  </th>
  </tr>
<dhv:evaluate if="<%=(OrgDetails.getPrimaryContact() == null)%>">
<%  
  int ecount = 0;
  Iterator enumber = OrgDetails.getEmailAddressList().iterator();
  while (enumber.hasNext()) {
    ++ecount;
    OrganizationEmailAddress thisEmailAddress = (OrganizationEmailAddress)enumber.next();
%>    
  <tr class="containerBody">
    <td class="formLabel">
      <dhv:label name="accounts.accounts_add.Email">Email</dhv:label> <%= ecount %>
    </td>
    <td>
      <input type="hidden" name="email<%= ecount %>id" value="<%= thisEmailAddress.getId() %>">
      <%= OrgEmailTypeList.getHtmlSelect("email" + ecount + "type", thisEmailAddress.getType()) %>
      <input type="text" size="40" name="email<%= ecount %>address" maxlength="255" value="<%= toHtmlValue(thisEmailAddress.getEmail()) %>">
      <input type="radio" name="primaryEmail" value="<%= ecount %>" <%= (thisEmailAddress.getPrimaryEmail()) ? " checked" : "" %>>Primary
      <input type="checkbox" name="email<%= ecount %>delete" value="on"><dhv:label name="accounts.accounts_modify.MarkToRemove">mark to remove</dhv:label>
    </td>
  </tr>
<%    
  }
  ++ecount;
%>
  <tr class="containerBody">
    <td class="formLabel">
      <dhv:label name="accounts.accounts_add.Email">Email</dhv:label> <%= ecount %>
    </td>
    <td>
      <%= OrgEmailTypeList.getHtmlSelect("email" + ecount + "type", "") %>
      <input type="text" size="40" name="email<%= ecount %>address" maxlength="255">
      <input type="radio" name="primaryEmail" value="<%= ecount %>">Primary
    </td>
  </tr>
</dhv:evaluate>
<dhv:evaluate if="<%=(OrgDetails.getPrimaryContact() != null)%>">
<%  
  int ecount = 0;
  Iterator enumber = OrgDetails.getPrimaryContact().getEmailAddressList().iterator();
  while (enumber.hasNext()) {
    ++ecount;
    ContactEmailAddress thisEmailAddress = (ContactEmailAddress) enumber.next();
%>    
  <tr class="containerBody">
    <td class="formLabel">
      <dhv:label name="accounts.accounts_add.Email">Email</dhv:label> <%= ecount %>
    </td>
    <td>
      <input type="hidden" name="email<%= ecount %>id" value="<%= thisEmailAddress.getId() %>">
      <%= ContactEmailTypeList.getHtmlSelect("email" + ecount + "type", thisEmailAddress.getType()) %>
      <input type="text" size="40" name="email<%= ecount %>address" maxlength="255" value="<%= toHtmlValue(thisEmailAddress.getEmail()) %>">
      <input type="checkbox" name="email<%= ecount %>delete" value="on"><dhv:label name="accounts.accounts_modify.MarkToRemove">mark to remove</dhv:label>
    </td>
  </tr>
<%    
  }
  ++ecount;
%>
    <tr class="containerBody">
      <td class="formLabel">
        <dhv:label name="accounts.accounts_add.Email">Email</dhv:label> <%= ecount %>
      </td>
      <td>
        <%= ContactEmailTypeList.getHtmlSelect("email" + ecount + "type", "") %>
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
        <strong><dhv:label name="accounts.accounts_add.AdditionalDetails">Additional Details</dhv:label></strong>
      </th>
    </tr>
    <tr class="containerBody">
      <td valign="top" class="formLabel">
        <dhv:label name="accounts.accountasset_include.Notes">Notes</dhv:label>
      </td>
      <td>
        <TEXTAREA NAME="notes" ROWS="3" COLS="50"><%= toString(OrgDetails.getNotes()) %></TEXTAREA>
      </td>
    </tr>
  </table>
  <br />
  <input type="hidden" name="onlyWarnings" value=<%=(OrgDetails.getOnlyWarnings()?"on":"off")%> />
  <input type="submit" value="<dhv:label name="global.button.update">Update</dhv:label>" name="Save" onClick="this.form.dosubmit.value='true';" />
<% if (request.getParameter("return") != null) {%>
	<% if (request.getParameter("return").equals("list")) {%>
	<input type="submit" value="<dhv:label name="global.button.cancel">Cancel</dhv:label>" onClick="javascript:this.form.action='Accounts.do?command=Search';this.form.dosubmit.value='false';" />
	<%}%>
<%} else {%>
  <input type="submit" value="<dhv:label name="global.button.cancel">Cancel</dhv:label>" onClick="javascript:this.form.action='Accounts.do?command=Details&orgId=<%= OrgDetails.getOrgId() %>';this.form.dosubmit.value='false';" />
  <%}%>
  <dhv:evaluate if="<%= popUp %>">
    <input type="button" value="<dhv:label name="global.button.cancel">Cancel</dhv:label>" onclick="javascript:window.close();" />
  </dhv:evaluate>
  <input type="hidden" name="dosubmit" value="true">
</dhv:container>
</form>
