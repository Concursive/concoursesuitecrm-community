<%@ taglib uri="WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,com.darkhorseventures.cfsbase.*,com.darkhorseventures.webutils.StateSelect,com.darkhorseventures.webutils.CountrySelect" %>
<jsp:useBean id="EmployeeBean" class="com.darkhorseventures.cfsbase.Contact" scope="request"/>
<jsp:useBean id="DepartmentList" class="com.darkhorseventures.webutils.LookupList" scope="request"/>
<jsp:useBean id="ContactPhoneTypeList" class="com.darkhorseventures.webutils.LookupList" scope="request"/>
<jsp:useBean id="ContactEmailTypeList" class="com.darkhorseventures.webutils.LookupList" scope="request"/>
<jsp:useBean id="ContactAddressTypeList" class="com.darkhorseventures.webutils.LookupList" scope="request"/>
<jsp:useBean id="StateSelect" class="com.darkhorseventures.webutils.StateSelect" scope="request"/>
<jsp:useBean id="CountrySelect" class="com.darkhorseventures.webutils.CountrySelect" scope="request"/>
<%@ include file="initPage.jsp" %>

<script language="JavaScript" TYPE="text/javascript" SRC="/javascript/checkPhone.js"></script>

<script language="JavaScript">
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
      
      <%}%>
      
      if (formTest == false) {
        alert("Form could not be saved, please check the following:\r\n\r\n" + message);
        return false;
      } else {
        return true;
      }
    }
</script>

<form action='/CompanyDirectory.do?command=UpdateEmployee&auto-populate=true' method='post'>
<input type="submit" value="Update" name="Save" onClick="return checkForm(this.form)">

    <% if (request.getParameter("return") != null) {%>
	<% if (request.getParameter("return").equals("list")) {%>
	<input type="submit" value="Cancel" onClick="javascript:this.form.action='/CompanyDirectory.do?command=ListEmployees'">
	<%}%>
    <%} else {%>
    	<input type="submit" value="Cancel" onClick="javascript:this.form.action='/CompanyDirectory.do?command=EmployeeDetails&empid=<%= EmployeeBean.getId() %>'">
    <%}%>
    
<input type="reset" value="Reset">
<br>
&nbsp;

<% if (request.getParameter("return") != null) {%>
<input type="hidden" name="return" value="<%=request.getParameter("return")%>">
<%}%>

<input type="hidden" name="empid" value="<%= EmployeeBean.getId() %>">
<input type="hidden" name="id" value="<%= EmployeeBean.getId() %>">
<input type="hidden" name="modified" value="<%= EmployeeBean.getModified()%>">
<table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr class="title">
    <td colspan=2 valign=center align=left>
      <strong>Modify "<%= toHtml(EmployeeBean.getNameFirst()) %> <%= toHtml(EmployeeBean.getNameLast()) %>"</strong>
    </td>
  </tr>
  <tr><td nowrap class="formLabel">First Name</td><td><input type="text" name="nameFirst" value="<%= toHtmlValue(EmployeeBean.getNameFirst()) %>"></td></tr>
  <tr><td nowrap class="formLabel">Middle Name</td><td><input type="text" name="nameMiddle" value="<%= toHtmlValue(EmployeeBean.getNameMiddle()) %>"></td></tr>
  <tr>
    <td nowrap class="formLabel">
      Last Name
    </td>
    <td>
      <input type="text" name="nameLast" value="<%= toHtmlValue(EmployeeBean.getNameLast()) %>">
      <font color="red">*</font> <%= showAttribute(request, "nameLastError") %>
    </td>
  </tr>
  <tr><td nowrap class="formLabel">Department</td><td><%= DepartmentList.getHtmlSelect("department", EmployeeBean.getDepartment()) %></td></tr>
  <tr><td nowrap class="formLabel">Title</td><td><input type="text" name="title" value="<%= toHtmlValue(EmployeeBean.getTitle()) %>"></td></tr>
</table>
&nbsp;<br>  
<table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr class="title">
    <td valign=center align=left>
	    <strong>Email Addresses</strong>
	  </td>
  </tr>
  <tr>
<%  
  int ecount = 0;
  Iterator enumber = EmployeeBean.getEmailAddressList().iterator();
  while (enumber.hasNext()) {
    ++ecount;
    ContactEmailAddress thisEmailAddress = (ContactEmailAddress)enumber.next();
%>    
  <tr>
    <td>
      <input type="hidden" name="email<%= ecount %>id" value="<%= thisEmailAddress.getId() %>">
      <%= ContactEmailTypeList.getHtmlSelect("email" + ecount + "type", thisEmailAddress.getType()) %>
      <input type=text size=40 name="email<%= ecount %>address" maxlength=255 value="<%= toHtmlValue(thisEmailAddress.getEmail()) %>">
      <input type="checkbox" name="email<%= ecount %>delete" value="on">mark to remove
    </td>
  </tr>
<%    
  }
%>
  <tr>
    <td>
      <%= ContactEmailTypeList.getHtmlSelect("email" + (++ecount) + "type", "Business") %>
      <input type=text size=40 name="email<%= ecount %>address" maxlength=255>
    </td>
  </tr>
</table>
&nbsp;<br>
<table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr class="title">
    <td valign=center align=left>
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
    <td>
      <input type="hidden" name="phone<%= icount %>id" value="<%= thisPhoneNumber.getId() %>">
      <%= ContactPhoneTypeList.getHtmlSelect("phone" + icount + "type", thisPhoneNumber.getType()) %>
      <!--input type=text size=3 name="phone<%= icount %>ac" maxlength=3 value="<%= toHtmlValue(thisPhoneNumber.getAreaCode()) %>">-
      <input type=text size=3 name="phone<%= icount %>pre" maxlength=3 value="<%= toHtmlValue(thisPhoneNumber.getPrefix()) %>">-
      <input type=text size=4 name="phone<%= icount %>number" maxlength=4 value="<%= toHtmlValue(thisPhoneNumber.getPostfix()) %>">ext. -->
      <input type=text size=20 name="phone<%= icount %>number" value="<%= toHtmlValue(thisPhoneNumber.getNumber()) %>">&nbsp;ext.
      <input type="text" size="5" name="phone<%= icount %>ext" maxlength="10" value="<%= toHtmlValue(thisPhoneNumber.getExtension()) %>">
      <input type="checkbox" name="phone<%= icount %>delete" value="on">mark to remove
    </td>
  </tr>    
<%    
  }
%>
  <tr>
    <td>
      <%= ContactPhoneTypeList.getHtmlSelect("phone" + (++icount) + "type", "Business") %>
      <!--input type=text size=3 name="phone<%= icount %>ac" maxlength=3>-
      <input type=text size=3 name="phone<%= icount %>pre" maxlength=3>-
      <input type=text size=4 name="phone<%= icount %>number" maxlength=4>ext. -->
      <input type=text size=20 name="phone<%= icount %>number">&nbsp;ext.
      <input type=text size=5 name="phone<%= icount %>ext" maxlength=10>
    </td>
  </tr>
</table>
&nbsp;<br>  
<table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr class="title">
    <td valign=center align=left colspan="2">
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
    <td>
      &nbsp;
    </td>
    <td>
      <%= ContactAddressTypeList.getHtmlSelect("address" + acount + "type", thisAddress.getType()) %>
      <input type="checkbox" name="address<%= acount %>delete" value="on">mark to remove
    </td>
  </tr>
  <tr>
    <td nowrap class="formLabel">
      Address Line 1
    </td>
    <td>
      <input type=text size=40 name="address<%= acount %>line1" maxlength=80 value="<%= toHtmlValue(thisAddress.getStreetAddressLine1()) %>">
    </td>
  </tr>
  <tr>
    <td nowrap class="formLabel">
      Address Line 2
    </td>
    <td>
      <input type=text size=40 name="address<%= acount %>line2" maxlength=80 value="<%= toHtmlValue(thisAddress.getStreetAddressLine2()) %>">
    </td>
  </tr>
  <tr>
    <td nowrap class="formLabel">
      City
    </td>
    <td>
      <input type=text size=28 name="address<%= acount %>city" maxlength=80 value="<%= toHtmlValue(thisAddress.getCity()) %>">
    </td>
  </tr>
  <tr>
    <td nowrap class="formLabel">
      State/Province
    </td>
    <td>
      <%=StateSelect.getHtml("address" + acount + "state", thisAddress.getState())%>
      <% StateSelect = new StateSelect(); %>
      <!--input type=text size=28 name="address<%= acount %>state" maxlength=80 value="<%= toHtmlValue(thisAddress.getState()) %>"-->
    </td>
  </tr>
  <tr>
    <td nowrap class="formLabel">
      Zip/Postal Code
    </td>
    <td>
      <input type=text size=10 name="address<%= acount %>zip" maxlength=12 value="<%= toHtmlValue(thisAddress.getZip()) %>">
    </td>
  </tr>
  <tr>
    <td nowrap class="formLabel">
      Country
    </td>
    <td>
      <%=CountrySelect.getHtml("address" + acount + "country", thisAddress.getCountry())%>
      <% CountrySelect = new CountrySelect(); %>
      <!--input type=text size=28 name="address<%= acount %>country" maxlength=80 value="<%= toHtmlValue(thisAddress.getCountry()) %>"-->
    </td>
  </tr>
  <tr><td colspan="2">&nbsp;</td></tr>
<%    
  }
%>
  <tr>
    <td>
      &nbsp;
    </td>
    <td>
      <%= ContactAddressTypeList.getHtmlSelect("address" + (++acount) + "type", "Business") %>
    </td>
  </tr>
  <tr>
    <td nowrap class="formLabel">
      Address Line 1
    </td>
    <td>
      <input type=text size=40 name="address<%= acount %>line1" maxlength=80>
    </td>
  </tr>
  <tr>
    <td nowrap class="formLabel">
      Address Line 2
    </td>
    <td>
      <input type=text size=40 name="address<%= acount %>line2" maxlength=80>
    </td>
  </tr>
  <tr>
    <td nowrap class="formLabel">
      City
    </td>
    <td>
      <input type=text size=28 name="address<%= acount %>city" maxlength=80>
    </td>
  </tr>
  <tr>
    <td nowrap class="formLabel">
      State/Province
    </td>
    <td>
      <%=StateSelect.getHtml("address" + acount + "state")%>
      <% StateSelect = new StateSelect(); %>
      <!--input type=text size=28 name="address<%= acount %>state" maxlength=80-->
    </td>
  </tr>
  <tr>
    <td nowrap class="formLabel">
      Zip/Postal Code
    </td>
    <td>
      <input type=text size=10 name="address<%= acount %>zip" maxlength=12>
    </td>
  </tr>
  <tr>
    <td nowrap class="formLabel">
      Country
    </td>
    <td>
      <%=CountrySelect.getHtml("address" + acount + "country")%>
      <% CountrySelect = new CountrySelect(); %>
      <!--input type=text size=28 name="address<%= acount %>country" maxlength=80-->
    </td>
  </tr>
</table>
&nbsp;<br>
<table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr class="title">
    <td valign=center align=left colspan="2">
      <strong>Additional Details</strong>
    </td>
  </tr>  
  <tr>
    <td class="formLabel">
      Notes
    </td>
    <td>
      <TEXTAREA NAME='notes' ROWS=3 COLS=50><%= (EmployeeBean.getNotes()) %></TEXTAREA>
    </td>
  </tr>
</table>
<br>
<input type="submit" value="Update" name="Save" onClick="return checkForm(this.form)">
    <% if (request.getParameter("return") != null) {%>
	<% if (request.getParameter("return").equals("list")) {%>
	<input type="submit" value="Cancel" onClick="javascript:this.form.action='/CompanyDirectory.do?command=ListEmployees'">
	<%}%>
    <%} else {%>
    	<input type="submit" value="Cancel" onClick="javascript:this.form.action='/CompanyDirectory.do?command=EmployeeDetails&empid=<%= EmployeeBean.getId() %>'">
    <%}%>
    <input type="reset" value="Reset">
</form>
