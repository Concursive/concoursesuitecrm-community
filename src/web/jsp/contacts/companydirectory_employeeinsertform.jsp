<%-- Import required libraries --%>
<jsp:useBean id="EmployeeBean" class="org.aspcfs.modules.contacts.base.Contact" scope="request"/>
<jsp:useBean id="DepartmentList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="ContactPhoneTypeList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="ContactEmailTypeList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="ContactAddressTypeList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
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
      if ((!checkPhone(form.phone1number.value)) || (!checkPhone(form.phone2number.value)) || (!checkPhone(form.phone3number.value)) ) { 
        message += "- At least one entered phone number is invalid.  Make sure there are no invalid characters and that you have entered the area code\r\n";
        formTest = false;
      }
      if (formTest == false) {
        alert("Form could not be saved, please check the following:\r\n\r\n" + message);
        return false;
      } else {
        return true;
      }
    }
</script>
<body onLoad="javascript:document.forms[0].nameFirst.focus();">
<form action="CompanyDirectory.do?command=InsertEmployee&auto-populate=true" onSubmit="return doCheck(this);" method="post">
<a href="MyCFS.do?command=Home">My Home Page</a> > 
Add Employee<br>
<hr color="#BFBFBB" noshade>
<input type="submit" value="Save" name="Save" onClick="this.form.dosubmit.value='true';">
<input type="submit" value="Save & New" onClick="this.form.saveAndNew.value='true';this.form.dosubmit.value='true';">
<input type="submit" value="Cancel" onClick="javascript:this.form.action='CompanyDirectory.do?command=ListEmployees';this.form.dosubmit.value='false';">
<input type="reset" value="Reset">
<br>
<%= showError(request, "actionError") %>
<input type="hidden" name="empid" value="<%= EmployeeBean.getId() %>">
<input type="hidden" name="id" value="<%= EmployeeBean.getId() %>">
<table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr class="title">
    <td colspan="2">
	    <strong>Add a New Employee Record</strong>
	  </td>
  </tr>
  <tr>
    <td class="formLabel" nowrap>First Name</td>
    <td>
      <input type="text" name="nameFirst" value="<%= toHtmlValue(EmployeeBean.getNameFirst()) %>">
    </td>
  </tr>
  <tr>
    <td class="formLabel" nowrap>Middle Name</td>
    <td>
      <input type="text" name="nameMiddle" value="<%= toHtmlValue(EmployeeBean.getNameMiddle()) %>">
    </td>
  </tr>
  <tr>
    <td class="formLabel" nowrap>Last Name</td>
    <td>
      <input type="text" name="nameLast" value="<%= toHtmlValue(EmployeeBean.getNameLast()) %>">
      <font color="red">*</font> <%= showAttribute(request, "nameLastError") %>
    </td>
  </tr>
  <tr>
    <td class="formLabel" nowrap>Department</td>
    <td>
      <%= DepartmentList.getHtmlSelect("department", EmployeeBean.getDepartment()) %>
    </td>
  </tr>
  <tr>
    <td class="formLabel" nowrap>Title</td>
    <td>
      <input type="text" name="title" value="<%= toHtmlValue(EmployeeBean.getTitle()) %>">
    </td>
  </tr>
</table>
&nbsp;<br>  
<table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr class="title">
    <td colspan="2">
	    <strong>Email Addresses</strong>
	  </td>
  </tr>
  <tr>
    <td class="formLabel">
      Email 1
    </td>
    <td>
      <%= ContactEmailTypeList.getHtmlSelect("email1type", ((EmployeeBean.getEmailAddressTypeId(1)==-1)?1:EmployeeBean.getEmailAddressTypeId(1))) %>
      <input type="text" size="40" name="email1address" maxlength="255" value="<%= toHtmlValue(EmployeeBean.getEmailAddress(1)) %>">
    </td>
  </tr>
  <tr>
    <td class="formLabel">
      Email 2
    </td>
    <td>
      <%= ContactEmailTypeList.getHtmlSelect("email2type", ((EmployeeBean.getEmailAddressTypeId(2)==-1)?2:EmployeeBean.getEmailAddressTypeId(2))) %>
      <input type="text" size="40" name="email2address" maxlength="255" value="<%= toHtmlValue(EmployeeBean.getEmailAddress(2)) %>">
    </td>
  </tr>
</table>
&nbsp;<br>
<table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr class="title">
    <td colspan="2">
	    <strong>Phone Numbers</strong>
	  </td>
  </tr>
  <tr>
    <td class="formLabel">
      Phone 1
    </td>
    <td>
      <%= ContactPhoneTypeList.getHtmlSelect("phone1type", "Business") %>
      <input type="text" size="20" name="phone1number">&nbsp;ext.
      <input type="text" size="5" name="phone1ext" maxlength="10">
    </td>
  </tr>
  <tr>
    <td class="formLabel">
      Phone 2
    </td>
    <td>
      <%= ContactPhoneTypeList.getHtmlSelect("phone2type", "Home") %>
      <input type=text size=20 name="phone2number">&nbsp;ext.
      <input type=text size=5 name="phone2ext" maxlength=10>
    </td>
  </tr>
  <tr>
    <td class="formLabel">
      Phone 3
    </td>
    <td>
      <%= ContactPhoneTypeList.getHtmlSelect("phone3type", "Mobile") %>
      <input type=text size=20 name="phone3number">&nbsp;ext.
      <input type=text size=5 name="phone3ext" maxlength=10>
    </td>
  </tr>
</table>
&nbsp;<br>  
<table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr class="title">
    <td colspan="2">
      <strong>Addresses</strong>
    </td>
  </tr>
  <tr>
    <td class="formLabel">
      Type
    </td>
    <td>
      <%= ContactAddressTypeList.getHtmlSelect("address1type", "Business") %>
    </td>
  </tr>
  <tr>
    <td class="formLabel" nowrap>
      Address Line 1
    </td>
    <td>
      <input type=text size=40 name="address1line1" maxlength=80>
    </td>
  </tr>
  <tr>
    <td class="formLabel" nowrap>
      Address Line 2
    </td>
    <td>
      <input type=text size=40 name="address1line2" maxlength=80>
    </td>
  </tr>
  <tr>
    <td class="formLabel" nowrap>
      City
    </td>
    <td>
      <input type=text size=28 name="address1city" maxlength=80>
    </td>
  </tr>
  <tr>
    <td class="formLabel" nowrap>
      State/Province
    </td>
    <td>
      <%=StateSelect.getHtml("address1state")%>
      <!--input type=text size=12 name="address1state" maxlength=80-->
    </td>
  </tr>
  <tr>
    <td class="formLabel" nowrap>
      Zip/Postal Code
    </td>
    <td>
      <input type=text size=28 name="address1zip" maxlength=12>
    </td>
  </tr>
  <tr>
    <td class="formLabel" nowrap>
      Country
    </td>
    <td>
      <%= CountrySelect.getHtml("address1country") %>
      <!--input type=text size=28 name="address1country" maxlength=80-->
    </td>
  </tr>
  <tr><td colspan="2">&nbsp;</td></tr>
  <tr>
    <td class="formLabel">
      Type
    </td>
    <td>
      <%= ContactAddressTypeList.getHtmlSelect("address2type", "Home") %>
    </td>
  </tr>
  <tr>
    <td class="formLabel" nowrap>
      Address Line 1
    </td>
    <td>
      <input type=text size=40 name="address2line1" maxlength=80>
    </td>
  </tr>
  <tr>
    <td class="formLabel" nowrap>
      Address Line 2
    </td>
    <td>
      <input type=text size=40 name="address2line2" maxlength=80>
    </td>
  </tr>
  <tr>
    <td class="formLabel" nowrap>
      City
    </td>
    <td>
      <input type="text" size="28" name="address2city" maxlength="80">
    </td>
  </tr>
  <tr>
    <td class="formLabel" nowrap>
      State/Province
    </td>
    <td>
      <%= StateSelect.getHtml("address2state") %>
      <!--input type=text size=12 name="address2state" maxlength=80-->
    </td>
  </tr>
  <tr>
    <td class="formLabel" nowrap>
      Zip/Postal Code
    </td>
    <td>
      <input type="text" size="28" name="address2zip" maxlength="12">
    </td>
  </tr>
  <tr>
    <td class="formLabel" nowrap>
      Country
    </td>
    <td>
      <%= CountrySelect.getHtml("address2country") %>
      <!--input type=text size=28 name="address2country" maxlength=80-->
    </td>
  </tr>
</table>
&nbsp;<br>
<table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr class="title">
    <td colspan="2" valign="center" align="left">
	    <strong>Additional Details</strong>
	  </td>
  </tr>
  <tr>
    <td nowrap class="formLabel" valign="top">Notes</td>
    <td><TEXTAREA NAME="notes" ROWS="3" COLS="50"><%= toString(EmployeeBean.getNotes()) %></TEXTAREA></td>
  </tr>
</table>
<br>
<input type="submit" value="Save" name="Save" onClick="this.form.dosubmit.value='true';">
<input type="submit" value="Save & New" onClick="this.form.saveAndNew.value='true';this.form.dosubmit.value='true';">
<input type="submit" value="Cancel" onClick="javascript:this.form.action='CompanyDirectory.do?command=ListEmployees';this.form.dosubmit.value='false';">
<input type="reset" value="Reset">
<input type="hidden" name="dosubmit" value="true">
<input type="hidden" name="saveAndNew" value="">
</form>
</body>
