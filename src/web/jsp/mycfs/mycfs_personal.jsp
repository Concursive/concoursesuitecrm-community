<%-- 
  - Copyright Notice: (C) 2000-2004 Dark Horse Ventures, All Rights Reserved.
  - License: This source code cannot be modified, distributed or used without
  -          written permission from Dark Horse Ventures. This notice must
  -          remain in place.
  - Version: $Id$
  - Description: 
  --%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,org.aspcfs.modules.mycfs.base.*,org.aspcfs.modules.contacts.base.*" %>
<jsp:useBean id="User" class="org.aspcfs.modules.admin.base.User" scope="request"/>
<jsp:useBean id="EmployeeBean" class="org.aspcfs.modules.contacts.base.Contact" scope="request"/>
<jsp:useBean id="ContactPhoneTypeList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="ContactEmailTypeList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="ContactAddressTypeList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="DepartmentList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="StateSelect" class="org.aspcfs.utils.web.StateSelect" scope="request"/>
<jsp:useBean id="CountrySelect" class="org.aspcfs.utils.web.CountrySelect" scope="request"/>
<%@ include file="../initPage.jsp" %>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/checkPhone.js"></script>
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
<%
    for (int i=1; i<=(EmployeeBean.getPhoneNumberList().size()+1); i++) {
%>		
<dhv:evaluate exp="<%=(i>1)%>">else </dhv:evaluate>if (!checkPhone(form.phone<%=i%>number.value)) { 
    message += "- At least one entered phone number is invalid.  Make sure there are no invalid characters and that you have entered the area code\r\n";
    formTest = false;
    }
<%
    }
%>
    if (formTest == false) {
      alert("Form could not be saved, please check the following:\r\n\r\n" + message);
      return false;
    } else {
      return true;
    }
  }
</script>
<form action="MyCFSProfile.do?command=UpdateProfile&auto-populate=true" onSubmit="return doCheck(this);" method="post">
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="MyCFS.do?command=Home">My Home Page</a> > 
<a href="MyCFS.do?command=MyProfile">Settings</a> >
Personal Information
</td>
</tr>
</table>
<%-- End Trails --%>
<dhv:permission name="myhomepage-profile-personal-edit">
<input type="submit" value="Update" name="Save" onClick="this.form.dosubmit.value='true';">
<input type="submit" value="Cancel" onClick="javascript:this.form.action='MyCFS.do?command=MyProfile';this.form.dosubmit.value='false';">
</dhv:permission>
<br>
<%= showError(request, "actionError") %>
<input type="hidden" name="empid" value="<%= EmployeeBean.getId() %>">
<input type="hidden" name="id" value="<%= EmployeeBean.getId() %>">
<input type="hidden" name="modified" value="<%= EmployeeBean.getModified().toString() %>">
<input type="hidden" name="orgId" value="<%= EmployeeBean.getOrgId() %>">
<input type="hidden" name="typeId" value="<%= EmployeeBean.getTypesNameString() %>">
<input type="hidden" name="department" value="<%=EmployeeBean.getDepartment()%>">
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="2">
      <strong>Details for: <%= toHtml(EmployeeBean.getNameFirstLast()) %></strong>
    </th>
  </tr>
  <tr>
    <td nowrap class="formLabel">First Name</td>
    <td><input type="text" name="nameFirst" value="<%= toHtmlValue(EmployeeBean.getNameFirst()) %>"></td>
  </tr>
  <tr>
    <td nowrap class="formLabel">Middle Name</td>
    <td><input type="text" name="nameMiddle" value="<%= toHtmlValue(EmployeeBean.getNameMiddle()) %>"></td>
  </tr>
  <tr>
    <td nowrap class="formLabel">
      Last Name
    </td>
    <td>
      <input type="text" name="nameLast" value="<%= toHtmlValue(EmployeeBean.getNameLast()) %>">
      <font color="red">*</font> <%= showAttribute(request, "nameLastError") %>
    </td>
  </tr>
  <tr>
    <td nowrap class="formLabel">Department</td>
    <td><%= toHtml(EmployeeBean.getDepartmentName()) %></td>
  </tr>
  <tr>
    <td nowrap class="formLabel">Title</td>
    <td><input type="text" name="title" value="<%= toHtmlValue(EmployeeBean.getTitle()) %>"></td>
  </tr>
</table>
&nbsp;<br>  
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="2">
	    <strong>Email Addresses</strong>
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
      Email <%= ecount %>
    </td>
    <td>
      <input type="hidden" name="email<%= ecount %>id" value="<%= thisEmailAddress.getId() %>">
      <%= ContactEmailTypeList.getHtmlSelect("email" + ecount + "type", thisEmailAddress.getType()) %>
      <input type="text" size="40" name="email<%= ecount %>address" maxlength="255" value="<%= toHtmlValue(thisEmailAddress.getEmail()) %>">
      <dhv:permission name="myhomepage-profile-personal-edit"><input type="checkbox" name="email<%= ecount %>delete" value="on">mark to remove</dhv:permission>
    </td>
  </tr>
<%    
  }
  ++ecount;
%>
  <tr>
    <td class="formLabel">
      Email <%= ecount %>
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
	    <strong>Phone Numbers</strong>
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
      Phone <%= icount %>
    </td>
    <td>
      <input type="hidden" name="phone<%= icount %>id" value="<%= thisPhoneNumber.getId() %>">
      <%= ContactPhoneTypeList.getHtmlSelect("phone" + icount + "type", thisPhoneNumber.getType()) %>
      <input type="text" size="20" name="phone<%= icount %>number" value="<%= toHtmlValue(thisPhoneNumber.getNumber()) %>">&nbsp;ext.
      <input type="text" size="5" name="phone<%= icount %>ext" maxlength="10" value="<%= toHtmlValue(thisPhoneNumber.getExtension()) %>">
      <dhv:permission name="myhomepage-profile-personal-edit"><input type="checkbox" name="phone<%= icount %>delete" value="on">mark to remove</dhv:permission>
    </td>
  </tr>    
<%    
  }
  ++icount;
%>
  <tr>
    <td class="formLabel">
      Phone <%= icount %>
    </td>
    <td>
      <%= ContactPhoneTypeList.getHtmlSelect("phone" + icount + "type", "Business") %>
      <input type="text" size="20" name="phone<%= icount %>number">&nbsp;ext.
      <input type="text" size="5" name="phone<%= icount %>ext" maxlength="10">
    </td>
  </tr>
</table>
&nbsp;<br>  
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="2">
      <strong>Addresses</strong>
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
      Type
    </td>
    <td>
      <%= ContactAddressTypeList.getHtmlSelect("address" + acount + "type", thisAddress.getType()) %>
      <dhv:permission name="myhomepage-profile-personal-edit"><input type="checkbox" name="address<%= acount %>delete" value="on">mark to remove</dhv:permission>
      <input type="hidden" name="address<%= acount %>id" value="<%= thisAddress.getId() %>">
    </td>
  </tr>
  <tr>
    <td class="formLabel" nowrap>
      Address Line 1
    </td>
    <td>
      <input type="text" size="40" name="address<%= acount %>line1" maxlength="80" value="<%= toHtmlValue(thisAddress.getStreetAddressLine1()) %>">
    </td>
  </tr>
  <tr>
    <td nowrap class="formLabel">
      Address Line 2
    </td>
    <td>
      <input type="text" size="40" name="address<%= acount %>line2" maxlength="80" value="<%= toHtmlValue(thisAddress.getStreetAddressLine2()) %>">
    </td>
  </tr>
  <tr>
    <td nowrap class="formLabel">
      City
    </td>
    <td>
      <input type="text" size="28" name="address<%= acount %>city" maxlength="80" value="<%= toHtmlValue(thisAddress.getCity()) %>">
    </td>
  </tr>
  <tr>
    <td class="formLabel" nowrap>
      State/Province
    </td>
    <td>
    <%= StateSelect.getHtml("address"+acount+"state") %>
    </td>
  </tr>
  <tr>
    <td class="formLabel" nowrap>
      Zip/Postal Code
    </td>
    <td>
      <input type="text" size="10" name="address<%= acount %>zip" maxlength="12" value="<%= toHtmlValue(thisAddress.getZip()) %>">
    </td>
  </tr>
  <tr>
    <td class="formLabel" nowrap>
      Country
    </td>
    <td>
      <%= CountrySelect.getHtml("address" + acount + "country") %>
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
      Type
    </td>
    <td>
      <%= ContactAddressTypeList.getHtmlSelect("address" + acount + "type", "Business") %>
    </td>
  </tr>
  <tr>
    <td class="formLabel" nowrap>
      Address Line 1
    </td>
    <td>
      <input type="text" size="40" name="address<%= acount %>line1" maxlength="80">
    </td>
  </tr>
  <tr>
    <td class="formLabel" nowrap>
      Address Line 2
    </td>
    <td>
      <input type="text" size="40" name="address<%= acount %>line2" maxlength="80">
    </td>
  </tr>
  <tr>
    <td class="formLabel" nowrap>
      City
    </td>
    <td>
      <input type="text" size="28" name="address<%= acount %>city" maxlength="80">
    </td>
  </tr>
  <tr>
    <td class="formLabel" nowrap>
      State/Province
    </td>
    <td>
    	<%= StateSelect.getHtml("address"+acount+"state") %>
    </td>
  </tr>
  <tr>
    <td class="formLabel" nowrap>
      Zip/Postal Code
    </td>
    <td>
      <input type="text" size="10" name="address<%= acount %>zip" maxlength="12">
    </td>
  </tr>
  <tr>
    <td nowrap class="formLabel">
      Country
    </td>
    <td>
    	<%= CountrySelect.getHtml("address" + acount + "country") %>
    </td>
  </tr>
</table>
<dhv:permission name="myhomepage-profile-personal-edit">
<br>
<input type="submit" value="Update" name="Save" onClick="this.form.dosubmit.value='true';">
<input type="submit" value="Cancel" onClick="javascript:this.form.action='MyCFS.do?command=MyProfile';this.form.dosubmit.value='false';">
<input type="hidden" name="dosubmit" value="true">
</dhv:permission>
<input type="hidden" name="accessType" value="<%= EmployeeBean.getAccessType() %>">
</form>
