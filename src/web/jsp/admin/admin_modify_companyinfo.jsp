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
<%@ page import="java.util.*,java.text.DateFormat,org.aspcfs.modules.accounts.base.*,org.aspcfs.controller.*,org.aspcfs.utils.*,org.aspcfs.utils.web.*" %>
<jsp:useBean id="OrgDetails" class="org.aspcfs.modules.accounts.base.Organization" scope="request"/>
<jsp:useBean id="OrgPhoneTypeList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="OrgAddressTypeList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="OrgEmailTypeList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="StateSelect" class="org.aspcfs.utils.web.StateSelect" scope="request"/>
<jsp:useBean id="CountrySelect" class="org.aspcfs.utils.web.CountrySelect" scope="request"/>
<jsp:useBean id="applicationPrefs" class="org.aspcfs.controller.ApplicationPrefs" scope="application"/>
<jsp:useBean id="TimeZoneSelect" class="org.aspcfs.utils.web.HtmlSelectTimeZone" scope="request"/>
<jsp:useBean id="systemStatus" class="org.aspcfs.controller.SystemStatus" scope="request"/>
<%@ include file="../initPage.jsp" %>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/checkPhone.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/checkNumber.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/checkEmail.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/spanDisplay.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popLookupSelect.js?1"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/checkURL.js"></SCRIPT>
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
<%
    for (int i=1; i<=(OrgDetails.getPhoneNumberList().size()+1); i++) {
%>
  <dhv:evaluate if="<%=(i>1)%>">else </dhv:evaluate>if (!checkPhone(form.phone<%=i%>number.value)) { 
      message += label("check.phone", "- At least one entered phone number is invalid.  Make sure there are no invalid characters\r\n");
      formTest = false;
    }
<%
    }
%>
    <%
    for (int i=1; i<=(OrgDetails.getEmailAddressList().size()+1); i++) {
%>
  <dhv:evaluate if="<%=(i>1)%>">else </dhv:evaluate>if (!checkEmail(form.email<%=i%>address.value)) {
      message += label("check.email", "- At least one entered email address is invalid.  Make sure there are no invalid characters\r\n");
      formTest = false;
    }
<%
    }
%>
    if (!checkURL(form.url.value)) {
      message += label("check.url", "- URL entered is invalid.  Make sure there are no invalid characters\r\n");
      formTest = false;
    }
    if (formTest == false) {
      alert(label("check.form", "Form could not be saved, please check the following:\r\n\r\n") + message);
      return false;
    } else {
      return true;
    }
  }

  function update(countryObj, stateObj, selectedValue) {
    var country = document.forms['modifyCompanyInfo'].elements[countryObj].value;
    var url = "ExternalContacts.do?command=States&country="+country+"&obj="+stateObj+"&selected="+selectedValue+"&form=modifyCompanyInfo&stateObj=address"+stateObj+"state";
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
<form name="modifyCompanyInfo" action="AdminConfig.do?command=UpdateCompanyInfo&auto-populate=true" onSubmit="return doCheck(this);" method="post">
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="Admin.do">Admin</a> >
<a href="AdminConfig.do?command=ListGlobalParams">Configure System</a> >
Company Information
</td>
</tr>
</table>
<%-- End Trails --%>
<input type="hidden" name="modified" value="<%= OrgDetails.getModified() %>">
<dhv:permission name="admin-sysconfig-view">
  <table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
    <tr>
      <th colspan="2">
        <strong>Modify Company Information</strong>
      </th>
    </tr>
    <tr class="containerBody">
      <td nowrap class="formLabel" name="orgname1" id="orgname1">
      	<dhv:label name="accounts.accounts_add.OrganizationName">Organization Name</dhv:label>
			</td>
      <td>
      	<input type="text" size="35" maxlength="80" name="name" value="<%= toHtmlValue(OrgDetails.getName()) %>"><font color="red">*</font> <%= showAttribute(request, "nameError") %>
			</td>
    </tr>
		<tr class="containerBody">
      <td class="formLabel">
        <dhv:label name="accounts.accounts_add.WebSiteURL">Web Site URL</dhv:label>
      </td>
      <td>
      	<input type="text" size="50" name="url" value="<%= toHtmlValue(OrgDetails.getUrl()) %>">
			</td>
    </tr>
  </table>
	&nbsp;<br>
	<%-- Phone Numbers --%>
	<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
		<tr>
			<th colspan="2">
				<strong><dhv:label name="accounts.accounts_add.PhoneNumbers">Phone Numbers</dhv:label></strong>
			</th>
		</tr>
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
			</td>
		</tr>
	</table>
  &nbsp;<br>
	<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
		<tr>
			<th colspan="2">
				<strong><dhv:label name="accounts.accounts_add.Addresses">Addresses</dhv:label></strong>
			</th>
		</tr>
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
				<span name="state1<%= acount %>" ID="state1<%= acount %>" style="<%= StateSelect.hasCountry(thisAddress.getCountry()) ? "" : " display:none" %>">
					<%= StateSelect.getHtmlSelect("address" + acount + "state", thisAddress.getCountry(), thisAddress.getState()) %>
				</span>
				<%-- If selected country is not US/Canada use textfield --%>
				<span name="state2<%= acount %>" ID="state2<%= acount %>" style="<%= !StateSelect.hasCountry(thisAddress.getCountry()) ? "" : " display:none" %>">
					<input type="text" size="25" name="<%= "address" + acount + "otherState" %>"  value="<%= toHtmlValue(thisAddress.getState()) %>">
				</span>
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
				<% CountrySelect.setJsEvent("onChange=\"javascript:update('address" + acount + "country', '" + acount + "','"+thisAddress.getState()+"');\"");%>
				<%= CountrySelect.getHtml("address" + acount + "country", thisAddress.getCountry()) %>
				<% CountrySelect = new CountrySelect(systemStatus); %>
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
				<%= OrgAddressTypeList.getHtmlSelect("address" + acount + "type", "Primary") %>
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
					<%= StateSelect.getHtmlSelect("address" + acount + "state", applicationPrefs.get("SYSTEM.COUNTRY")) %>
				</span>
				<%-- If selected country is not US/Canada use textfield --%>
				<span name="state2<%= acount %>" ID="state2<%= acount %>" style="display:none">
					<input type="text" size="25" name="<%= "address" + acount + "otherState" %>">
				</span>
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
				<% CountrySelect.setJsEvent("onChange=\"javascript:update('address" + acount + "country', '" + acount + "','');\"");%>
				<%= CountrySelect.getHtml("address" + acount + "country",applicationPrefs.get("SYSTEM.COUNTRY")) %>
				<% CountrySelect = new CountrySelect(systemStatus); %>
			</td>
		</tr>
	</table>
  &nbsp;<br>
	<%-- Email Addresses --%>
	<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
		<tr>
			<th colspan="2">
				<strong><dhv:label name="accounts.accounts_add.EmailAddresses">Email Addresses</dhv:label></strong>
			</th>
		</tr>
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
				<%= OrgEmailTypeList.getHtmlSelect("email" + ecount + "type", "Primary") %>
				<input type="text" size="40" name="email<%= ecount %>address" maxlength="255">
			</td>
		</tr>
	</table>
	&nbsp;<br />
	<input type="submit" value="<dhv:label name="global.button.update">Update</dhv:label>" onClick="this.form.dosubmit.value='true';"/>
  <input type="button" value="<dhv:label name="global.button.cancel">Cancel</dhv:label>" onClick="javascript:window.location.href='AdminConfig.do?command=ListGlobalParams';this.form.dosubmit.value='false';">
	<input type="hidden" name="dosubmit" value="true" />
</dhv:permission>
<iframe src="empty.html" name="server_commands" id="server_commands" style="visibility:hidden" height="0"></iframe>
</form>
