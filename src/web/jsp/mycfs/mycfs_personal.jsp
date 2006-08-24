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
<%@ page import="java.util.*,org.aspcfs.modules.mycfs.base.*,org.aspcfs.modules.contacts.base.*" %>
<%@ page import="org.aspcfs.utils.web.*" %>
<jsp:useBean id="User" class="org.aspcfs.modules.admin.base.User" scope="request"/>
<jsp:useBean id="EmployeeBean" class="org.aspcfs.modules.contacts.base.Contact" scope="request"/>
<jsp:useBean id="ContactPhoneTypeList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="ContactEmailTypeList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="ContactAddressTypeList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="DepartmentList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="StateSelect" class="org.aspcfs.utils.web.StateSelect" scope="request"/>
<jsp:useBean id="CountrySelect" class="org.aspcfs.utils.web.CountrySelect" scope="request"/>
<jsp:useBean id="applicationPrefs" class="org.aspcfs.controller.ApplicationPrefs" scope="application"/>
<jsp:useBean id="systemStatus" class="org.aspcfs.controller.SystemStatus" scope="request"/>
<%@ include file="../initPage.jsp" %>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/checkPhone.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/checkEmail.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/checkString.js"></script>
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
<%  for (int i=1; i<=EmployeeBean.getPhoneNumberList().size()+1; i++) { %>
    <dhv:evaluate if="<%=(i>1)%>">else </dhv:evaluate>if (!checkPhone(form.phone<%=i%>number.value) || (checkNullString(form.phone<%=i%>number.value) && !checkNullString(form.phone<%= i %>ext.value))) {
      message += label("check.phone", "- At least one entered phone number is invalid.  Make sure there are no invalid characters and that you have entered the area code\r\n");
      formTest = false;
    }
<%  }
    for (int i= 1; i <= EmployeeBean.getPhoneNumberList().size()+1; i++) { %>
    <dhv:evaluate if="<%=(i>1)%>">else </dhv:evaluate>if (checkNullString(form.phone<%= i %>ext.value) && form.phone<%= i %>ext.value != "") {
      message += label("check.phone.ext","- Please enter a valid phone number extension\r\n");
      formTest = false;
    }
<%  }
    for (int i=1; i<=EmployeeBean.getEmailAddressList().size()+1; i++) { %>
    <dhv:evaluate if="<%=(i>1)%>">else </dhv:evaluate>if (!checkEmail(form.email<%=i%>address.value)) {
      message += label("check.email", "- At least one entered email address is invalid.  Make sure there are no invalid characters\r\n");
      formTest = false;
    }
<%  } %>
    if (formTest == false) {
      alert(label("check.form", "Form could not be saved, please check the following:\r\n\r\n") + message);
      return false;
    } else {
      return true;
    }
  }

  function update(countryObj, stateObj, selectedValue) {
    var country = document.forms['addContact'].elements[countryObj].value;
    var url = "ExternalContacts.do?command=States&country="+country+"&obj="+stateObj+"&selected="+selectedValue+"&form=addContact&stateObj=address"+stateObj+"state";
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
<form name="addContact" action="MyCFSProfile.do?command=UpdateProfile&auto-populate=true" onSubmit="return doCheck(this);" method="post">
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="MyCFS.do?command=Home"><dhv:label name="actionList.myHomePage" mainMenuItem="true">My Home Page</dhv:label></a> >
<a href="MyCFS.do?command=MyProfile"><dhv:label name="myitems.settings">Settings</dhv:label></a> >
<dhv:label name="calendar.personalInformation">Personal Information</dhv:label>
</td>
</tr>
</table>
<%-- End Trails --%>
<dhv:permission name="myhomepage-profile-personal-edit">
<input type="submit" value="<dhv:label name="global.button.update">Update</dhv:label>" name="Save" onClick="this.form.dosubmit.value='true';">
<input type="submit" value="<dhv:label name="global.button.cancel">Cancel</dhv:label>" onClick="javascript:this.form.action='MyCFS.do?command=MyProfile';this.form.dosubmit.value='false';">
</dhv:permission>
<br />
<dhv:formMessage />
<input type="hidden" name="empid" value="<%= EmployeeBean.getId() %>">
<input type="hidden" name="id" value="<%= EmployeeBean.getId() %>">
<input type="hidden" name="modified" value="<%= EmployeeBean.getModified() %>">
<input type="hidden" name="orgId" value="<%= EmployeeBean.getOrgId() %>">
<input type="hidden" name="typeId" value="<%= EmployeeBean.getTypesNameString() %>">
<input type="hidden" name="department" value="<%=EmployeeBean.getDepartment()%>">
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="2">
      <strong><dhv:label name="calendar.detailsFor.colon" param="<%= "employee.name="+toHtml(EmployeeBean.getNameFirstLast()) %>">Details for: <%= toHtml(EmployeeBean.getNameFirstLast()) %></dhv:label></strong>
    </th>
  </tr>
  <tr>
    <td nowrap class="formLabel"><dhv:label name="accounts.accounts_add.FirstName">First Name</dhv:label></td>
    <td><input type="text" name="nameFirst" value="<%= toHtmlValue(EmployeeBean.getNameFirst()) %>"></td>
  </tr>
  <tr>
    <td nowrap class="formLabel"><dhv:label name="accounts.accounts_add.MiddleName">Middle Name</dhv:label></td>
    <td><input type="text" name="nameMiddle" value="<%= toHtmlValue(EmployeeBean.getNameMiddle()) %>"></td>
  </tr>
  <tr>
    <td nowrap class="formLabel">
      <dhv:label name="accounts.accounts_add.LastName">Last Name</dhv:label>
    </td>
    <td>
      <input type="text" name="nameLast" value="<%= toHtmlValue(EmployeeBean.getNameLast()) %>">
      <font color="red">*</font> <%= showAttribute(request, "nameLastError") %>
    </td>
  </tr>
  <tr>
    <td nowrap class="formLabel"><dhv:label name="project.department">Department</dhv:label></td>
    <td><%= toHtml(EmployeeBean.getDepartmentName()) %></td>
  </tr>
  <tr>
    <td nowrap class="formLabel"><dhv:label name="accounts.accounts_contacts_add.Title">Title</dhv:label></td>
    <td><input type="text" name="title" value="<%= toHtmlValue(EmployeeBean.getTitle()) %>"></td>
  </tr>
</table>
&nbsp;<br>
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="2">
	    <strong><dhv:label name="accounts.accounts_add.EmailAddresses">Email Addresses</dhv:label></strong>
	  </th>
  </tr>
  <tr>
<%
  int ecount = 0;
  Iterator enumber = EmployeeBean.getEmailAddressList().iterator();
  while (enumber.hasNext()) {
    ++ecount;
    ContactEmailAddress thisEmailAddress = (ContactEmailAddress) enumber.next();
%>
  <tr>
    <td class="formLabel">
      <dhv:label name="accounts.accounts_add.Email">Email</dhv:label> <%= ecount %>
    </td>
    <td>
      <input type="hidden" name="email<%= ecount %>id" value="<%= thisEmailAddress.getId() %>">
      <%= ContactEmailTypeList.getHtmlSelect("email" + ecount + "type", thisEmailAddress.getType()) %>
      <input type="text" size="40" name="email<%= ecount %>address" maxlength="255" value="<%= toHtmlValue(thisEmailAddress.getEmail()) %>">
      <dhv:permission name="myhomepage-profile-personal-edit"><input type="checkbox" name="email<%= ecount %>delete" value="on"><dhv:label name="accounts.accounts_modify.MarkToRemove">mark to remove</dhv:label></dhv:permission>
    </td>
  </tr>
<%
  }
  ++ecount;
%>
  <tr>
    <td class="formLabel">
      <dhv:label name="accounts.accounts_add.Email">Email</dhv:label> <%= ecount %>
    </td>
    <td>
      <%= ContactEmailTypeList.getHtmlSelect("email" + ecount + "type", "Business") %>
      <input type="text" size="40" name="email<%= ecount %>address" maxlength="255">
    </td>
  </tr>
</table>
&nbsp;<br>
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="2">
	    <strong><dhv:label name="accounts.accounts_add.PhoneNumbers">Phone Numbers</dhv:label></strong>
	  </th>
  </tr>
<%
  int icount = 0;
  Iterator inumber = EmployeeBean.getPhoneNumberList().iterator();
  while (inumber.hasNext()) {
    ++icount;
    ContactPhoneNumber thisPhoneNumber = (ContactPhoneNumber)inumber.next();
%>
  <tr>
    <td class="formLabel">
      <dhv:label name="reports.accounts.contacts.phone">Phone</dhv:label> <%= icount %>
    </td>
    <td>
      <input type="hidden" name="phone<%= icount %>id" value="<%= thisPhoneNumber.getId() %>">
      <%= ContactPhoneTypeList.getHtmlSelect("phone" + icount + "type", thisPhoneNumber.getType()) %>
      <input type="text" size="20" name="phone<%= icount %>number" value="<%= toHtmlValue(thisPhoneNumber.getNumber()) %>">&nbsp;<dhv:label name="account.ext">Ext</dhv:label>.
      <input type="text" size="5" name="phone<%= icount %>ext" maxlength="10" value="<%= toHtmlValue(thisPhoneNumber.getExtension()) %>">
      <dhv:permission name="myhomepage-profile-personal-edit"><input type="checkbox" name="phone<%= icount %>delete" value="on"><dhv:label name="accounts.accounts_modify.MarkToRemove">mark to remove</dhv:label></dhv:permission>
    </td>
  </tr>
<%
  }
  ++icount;
%>
  <tr>
    <td class="formLabel">
      <dhv:label name="reports.accounts.contacts.phone">Phone</dhv:label> <%= icount %>
    </td>
    <td>
      <%= ContactPhoneTypeList.getHtmlSelect("phone" + icount + "type", "Business") %>
      <input type="text" size="20" name="phone<%= icount %>number">&nbsp;<dhv:label name="account.ext">Ext</dhv:label>.
      <input type="text" size="5" name="phone<%= icount %>ext" maxlength="10">
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
  Iterator anumber = EmployeeBean.getAddressList().iterator();
  while (anumber.hasNext()) {
    ++acount;
    ContactAddress thisAddress = (ContactAddress)anumber.next();
%>
  <tr>
    <td class="formLabel">
      <dhv:label name="accounts.accounts_add.Type">Type</dhv:label>
    </td>
    <td>
      <%= ContactAddressTypeList.getHtmlSelect("address" + acount + "type", thisAddress.getType()) %>
      <dhv:permission name="myhomepage-profile-personal-edit"><input type="checkbox" name="address<%= acount %>delete" value="on"><dhv:label name="accounts.accounts_modify.MarkToRemove">mark to remove</dhv:label></dhv:permission>
      <input type="hidden" name="address<%= acount %>id" value="<%= thisAddress.getId() %>">
    </td>
  </tr>
  <tr>
    <td class="formLabel" nowrap>
      <dhv:label name="accounts.accounts_add.AddressLine1">Address Line 1</dhv:label>
    </td>
    <td>
      <input type="text" size="40" name="address<%= acount %>line1" maxlength="80" value="<%= toHtmlValue(thisAddress.getStreetAddressLine1()) %>">
    </td>
  </tr>
  <tr>
    <td nowrap class="formLabel">
      <dhv:label name="accounts.accounts_add.AddressLine2">Address Line 2</dhv:label>
    </td>
    <td>
      <input type="text" size="40" name="address<%= acount %>line2" maxlength="80" value="<%= toHtmlValue(thisAddress.getStreetAddressLine2()) %>">
    </td>
  </tr>
  <tr>
    <td nowrap class="formLabel">
      <dhv:label name="accounts.accounts_add.City">City</dhv:label>
    </td>
    <td>
      <input type="text" size="28" name="address<%= acount %>city" maxlength="80" value="<%= toHtmlValue(thisAddress.getCity()) %>">
    </td>
  </tr>
  <tr>
    <td class="formLabel" nowrap>
      <dhv:label name="accounts.accounts_add.StateProvince">State/Province</dhv:label>
    </td>
    <td>
      <span name="state1<%= acount %>" ID="state1<%= acount %>" style="<%= StateSelect.hasCountry(thisAddress.getCountry())? "" : " display:none" %>">
        <%= StateSelect.getHtmlSelect("address" + acount + "state", thisAddress.getCountry(), thisAddress.getState()) %>
      </span>
      <%-- If selected country is not US/Canada use textfield --%>
      <span name="state2<%= acount %>" ID="state2<%= acount %>" style="<%= !StateSelect.hasCountry(thisAddress.getCountry()) ? "" : " display:none" %>">
        <input type="text" size="25" name="<%= "address" + acount + "otherState" %>"  value="<%= toHtmlValue(thisAddress.getState()) %>">
      </span>
    </td>
  </tr>
  <tr>
    <td class="formLabel" nowrap>
      <dhv:label name="accounts.accounts_add.ZipPostalCode">Zip/Postal Code</dhv:label>
    </td>
    <td>
      <input type="text" size="10" name="address<%= acount %>zip" maxlength="12" value="<%= toHtmlValue(thisAddress.getZip()) %>">
    </td>
  </tr>
  <tr>
    <td class="formLabel" nowrap>
      <dhv:label name="accounts.accounts_add.Country">Country</dhv:label>
    </td>
    <td>
    <% CountrySelect.setJsEvent("onChange=\"javascript:update('address" + acount + "country', '" + acount + "','"+thisAddress.getState()+"');\""); %>
      <%= CountrySelect.getHtml("address" + acount + "country", thisAddress.getCountry()) %>
      <% CountrySelect = new CountrySelect(systemStatus); %>
    </td>
  </tr>
  <tr>
    <td colspan="2">&nbsp;</td>
  </tr>
<%
  }
  ++acount;
%>
  <tr>
    <td class="formLabel">
      <dhv:label name="accounts.accounts_add.Type">Type</dhv:label>
    </td>
    <td>
      <%= ContactAddressTypeList.getHtmlSelect("address" + acount + "type", "Business") %>
    </td>
  </tr>
  <tr>
    <td class="formLabel" nowrap>
      <dhv:label name="accounts.accounts_add.AddressLine1">Address Line 1</dhv:label>
    </td>
    <td>
      <input type="text" size="40" name="address<%= acount %>line1" maxlength="80">
    </td>
  </tr>
  <tr>
    <td class="formLabel" nowrap>
      <dhv:label name="accounts.accounts_add.AddressLine2">Address Line 2</dhv:label>
    </td>
    <td>
      <input type="text" size="40" name="address<%= acount %>line2" maxlength="80">
    </td>
  </tr>
  <tr>
    <td class="formLabel" nowrap>
      <dhv:label name="accounts.accounts_add.City">City</dhv:label>
    </td>
    <td>
      <input type="text" size="28" name="address<%= acount %>city" maxlength="80">
    </td>
  </tr>
  <tr>
    <td class="formLabel" nowrap>
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
  <tr>
    <td class="formLabel" nowrap>
      <dhv:label name="accounts.accounts_add.ZipPostalCode">Zip/Postal Code</dhv:label>
    </td>
    <td>
      <input type="text" size="10" name="address<%= acount %>zip" maxlength="12">
    </td>
  </tr>
  <tr>
    <td nowrap class="formLabel">
      <dhv:label name="accounts.accounts_add.Country">Country</dhv:label>
    </td>
    <td>
      <% CountrySelect.setJsEvent("onChange=\"javascript:update('address" + acount + "country', '" + acount + "','');\""); %>
      <%= CountrySelect.getHtml("address" + acount + "country",applicationPrefs.get("SYSTEM.COUNTRY")) %>
    </td>
  </tr>
</table>
<dhv:permission name="myhomepage-profile-personal-edit">
<br />
<input type="submit" value="<dhv:label name="global.button.update">Update</dhv:label>" name="Save" onClick="this.form.dosubmit.value='true';">
<input type="submit" value="<dhv:label name="global.button.cancel">Cancel</dhv:label>" onClick="javascript:this.form.action='MyCFS.do?command=MyProfile';this.form.dosubmit.value='false';">
<input type="hidden" name="dosubmit" value="true">
</dhv:permission>
<input type="hidden" name="accessType" value="<%= EmployeeBean.getAccessType() %>">
<iframe src="empty.html" name="server_commands" id="server_commands" style="visibility:hidden" height="0"></iframe>
</form>