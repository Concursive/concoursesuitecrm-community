<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,org.aspcfs.modules.contacts.base.*,org.aspcfs.utils.web.StateSelect,org.aspcfs.utils.web.CountrySelect" %>
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
<form action="CompanyDirectory.do?command=UpdateEmployee&auto-populate=true" onSubmit="return doCheck(this);" method="post">
<a href="MyCFS.do?command=Home">My Home Page</a> > 
<% if (request.getParameter("return") != null) {%>
	<% if (request.getParameter("return").equals("list")) {%>
	<a href="CompanyDirectory.do?command=ListEmployees">View Employees</a> >
  <%}%>
<%} else {%>
<a href="CompanyDirectory.do?command=ListEmployees">View Employees</a> >
<a href="CompanyDirectory.do?command=EmployeeDetails&empid=<%=EmployeeBean.getId()%>">Employee Details</a> >
<%}%>
Modify Employee<br>
<hr color="#BFBFBB" noshade>
<input type="submit" value="Update" name="Save" onClick="this.form.dosubmit.value='true';">
<% if (request.getParameter("return") != null) {%>
	<% if (request.getParameter("return").equals("list")) {%>
	<input type="submit" value="Cancel" onClick="javascript:this.form.action='CompanyDirectory.do?command=ListEmployees';this.form.dosubmit.value='false';">
	<%}%>
<%} else {%>
  <input type="submit" value="Cancel" onClick="javascript:this.form.action='CompanyDirectory.do?command=EmployeeDetails&empid=<%= EmployeeBean.getId() %>';this.form.dosubmit.value='false';">
<%}%>
<input type="reset" value="Reset">
<br>
<%= showError(request, "actionError") %>
<% if (request.getParameter("return") != null) {%>
<input type="hidden" name="return" value="<%=request.getParameter("return")%>">
<%}%>
<input type="hidden" name="empid" value="<%= EmployeeBean.getId() %>">
<input type="hidden" name="id" value="<%= EmployeeBean.getId() %>">
<input type="hidden" name="modified" value="<%= EmployeeBean.getModified()%>">
<input type="hidden" name="orgId" value="<%= EmployeeBean.getOrgId() %>">
<table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr class="title">
    <td colspan="2">
      <strong>Modify <%= toHtml(EmployeeBean.getNameFirstLast()) %></strong>
      <dhv:evaluate exp="<%=!(EmployeeBean.hasEnabledAccount())%>"><font color="red">*</font></dhv:evaluate>          
    </td>
  </tr>
  <tr>
    <td class="formLabel" nowrap>First Name</td>
    <td><input type="text" name="nameFirst" value="<%= toHtmlValue(EmployeeBean.getNameFirst()) %>"></td>
  </tr>
  <tr>
    <td class="formLabel" nowrap>Middle Name</td>
    <td><input type="text" name="nameMiddle" value="<%= toHtmlValue(EmployeeBean.getNameMiddle()) %>"></td>
  </tr>
  <tr>
    <td class="formLabel" nowrap>
      Last Name
    </td>
    <td>
      <input type="text" name="nameLast" value="<%= toHtmlValue(EmployeeBean.getNameLast()) %>">
      <font color="red">*</font> <%= showAttribute(request, "nameLastError") %>
    </td>
  </tr>
  <tr>
    <td class="formLabel" nowrap>Department</td>
    <td><%= DepartmentList.getHtmlSelect("department", EmployeeBean.getDepartment()) %></td>
  </tr>
  <tr>
    <td class="formLabel" nowrap>Title</td>
    <td><input type="text" name="title" value="<%= toHtmlValue(EmployeeBean.getTitle()) %>"></td>
  </tr>
</table>
&nbsp;<br>  
<table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr class="title">
    <td colspan="2">
	    <strong>Email Addresses</strong>
	  </td>
  </tr>
<%
  int ecount = 0;
  Iterator enumber = EmployeeBean.getEmailAddressList().iterator();
  while (enumber.hasNext()) {
    ++ecount;
    ContactEmailAddress thisEmailAddress = (ContactEmailAddress) enumber.next();
%>    
  <tr>
    <td class="formLabel" nowrap>
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
  <tr>
    <td class="formLabel" nowrap>
      Email <%= ecount %>
    </td>
    <td>
      <%= ContactEmailTypeList.getHtmlSelect("email" + ecount + "type", "Business") %>
      <input type="text" size="40" name="email<%= ecount %>address" maxlength="255">
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
<%
  int icount = 0;
  Iterator inumber = EmployeeBean.getPhoneNumberList().iterator();
  while (inumber.hasNext()) {
    ++icount;
    ContactPhoneNumber thisPhoneNumber = (ContactPhoneNumber)inumber.next();
%>    
  <tr>
    <td class="formLabel" nowrap>
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
  <tr>
    <td class="formLabel" nowrap>
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
<table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr class="title">
    <td colspan="2">
      <strong>Addresses</strong>
    </td>
  </tr>
<%  
  int acount = 0;
  Iterator anumber = EmployeeBean.getAddressList().iterator();
  while (anumber.hasNext()) {
    ++acount;
    ContactAddress thisAddress = (ContactAddress)anumber.next();
%>    
  <tr>
    <input type="hidden" name="address<%= acount %>id" value="<%= thisAddress.getId() %>">
    <td class="formLabel">
      Type
    </td>
    <td>
      <%= ContactAddressTypeList.getHtmlSelect("address" + acount + "type", thisAddress.getType()) %>
      <input type="checkbox" name="address<%= acount %>delete" value="on">mark to remove
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
    <td class="formLabel" nowrap>
      Address Line 2
    </td>
    <td>
      <input type="text" size="40" name="address<%= acount %>line2" maxlength="80" value="<%= toHtmlValue(thisAddress.getStreetAddressLine2()) %>">
    </td>
  </tr>
  <tr>
    <td class="formLabel" nowrap>
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
      <%= StateSelect.getHtml("address" + acount + "state", thisAddress.getState()) %>
      <% StateSelect = new StateSelect(); %>
    </td>
  </tr>
  <tr>
    <td nowrap class="formLabel">
      Zip/Postal Code
    </td>
    <td>
      <input type="text" size="10" name="address<%= acount %>zip" maxlength="12" value="<%= toHtmlValue(thisAddress.getZip()) %>">
    </td>
  </tr>
  <tr>
    <td nowrap class="formLabel">
      Country
    </td>
    <td>
      <%= CountrySelect.getHtml("address" + acount + "country", thisAddress.getCountry()) %>
      <% CountrySelect = new CountrySelect(); %>
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
      <%= StateSelect.getHtml("address" + acount + "state") %>
      <% StateSelect = new StateSelect(); %>
    </td>
  </tr>
  <tr>
    <td nowrap class="formLabel">
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
      <% CountrySelect = new CountrySelect(); %>
    </td>
  </tr>
</table>
&nbsp;<br>
<table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr class="title">
    <td colspan="2">
      <strong>Additional Details</strong>
    </td>
  </tr>  
  <tr>
    <td valign="top" class="formLabel">
      Notes
    </td>
    <td>
      <TEXTAREA NAME="notes" ROWS="3" COLS="50"><%= toString(EmployeeBean.getNotes()) %></TEXTAREA>
    </td>
  </tr>
</table>
<br>
<input type="submit" value="Update" name="Save" onClick="this.form.dosubmit.value='true';">
<% if (request.getParameter("return") != null) {%>
	<% if (request.getParameter("return").equals("list")) {%>
	<input type="submit" value="Cancel" onClick="javascript:this.form.action='CompanyDirectory.do?command=ListEmployees';this.form.dosubmit.value='false';">
	<%}%>
<%} else {%>
  <input type="submit" value="Cancel" onClick="javascript:this.form.action='CompanyDirectory.do?command=EmployeeDetails&empid=<%= EmployeeBean.getId() %>';this.form.dosubmit.value='false';">
<%}%>
<input type="reset" value="Reset">
<input type="hidden" name="dosubmit" value="true">
</form>
