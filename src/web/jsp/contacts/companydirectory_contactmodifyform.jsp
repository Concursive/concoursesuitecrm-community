<%@ taglib uri="WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,com.darkhorseventures.cfsbase.*,com.darkhorseventures.webutils.StateSelect,com.darkhorseventures.webutils.CountrySelect" %>
<jsp:useBean id="ContactDetails" class="com.darkhorseventures.cfsbase.Contact" scope="request"/>
<jsp:useBean id="ContactTypeList" class="com.darkhorseventures.cfsbase.ContactTypeList" scope="request"/>
<jsp:useBean id="ContactPhoneTypeList" class="com.darkhorseventures.webutils.LookupList" scope="request"/>
<jsp:useBean id="ContactEmailTypeList" class="com.darkhorseventures.webutils.LookupList" scope="request"/>
<jsp:useBean id="ContactAddressTypeList" class="com.darkhorseventures.webutils.LookupList" scope="request"/>
<jsp:useBean id="UserList" class="com.darkhorseventures.cfsbase.UserList" scope="request"/>
<jsp:useBean id="StateSelect" class="com.darkhorseventures.webutils.StateSelect" scope="request"/>
<jsp:useBean id="CountrySelect" class="com.darkhorseventures.webutils.CountrySelect" scope="request"/>
<jsp:useBean id="OrgList" class="com.darkhorseventures.cfsbase.OrganizationList" scope="request"/>
<%@ include file="initPage.jsp" %>

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
      		for (int i=1; i<=(ContactDetails.getPhoneNumberList().size()+1); i++) {
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

<form action="ExternalContacts.do?command=UpdateContact&auto-populate=true" onSubmit="return doCheck(this);" method="post">

<a href="ExternalContacts.do">Contacts &amp; Resources</a> > 

<% if (request.getParameter("return") != null) {%>
	<% if (request.getParameter("return").equals("list")) {%>
	<a href="ExternalContacts.do?command=ListContacts">View Contacts</a> >
  <%}%>
<%} else {%>
<a href="ExternalContacts.do?command=ListContacts">View Contacts</a> >
<a href="ExternalContacts.do?command=ContactDetails&id=<%=ContactDetails.getId()%>">Contact Details</a> >
<%}%>
Modify Contact<br>
<hr color="#BFBFBB" noshade>
<table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr class="containerHeader">
    <td>
      <strong><%= toHtml(ContactDetails.getNameFull()) %></strong>
    </td>
  </tr>
  <tr class="containerMenu">
    <td>
      <% String param1 = "id=" + ContactDetails.getId(); %>      
      <dhv:container name="contacts" selected="details" param="<%= param1 %>" />
    </td>
  </tr>
  <tr>
    <td class="containerBack">
    
       
    <input type="submit" value="Update" name="Save" onClick="this.form.dosubmit.value='true';">
    <% if (request.getParameter("return") != null) {%>
	<% if (request.getParameter("return").equals("list")) {%>
	<input type="submit" value="Cancel" onClick="javascript:this.form.action='ExternalContacts.do?command=ListContacts';this.form.dosubmit.value='false';">
	<%}%>
<%} else {%>
<input type="submit" value="Cancel" onClick="javascript:this.form.action='ExternalContacts.do?command=ContactDetails&id=<%= ContactDetails.getId() %>';this.form.dosubmit.value='false';">
<%}%>
<input type="reset" value="Reset"><br>
<%= showError(request, "actionError") %>
<input type="hidden" name="id" value="<%= ContactDetails.getId() %>">
<input type="hidden" name="modified" value="<%= ContactDetails.getModified() %>">

<% if (request.getParameter("return") != null) {%>
	<input type="hidden" name="return" value="<%=request.getParameter("return")%>">
<%}%>

<table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr class="title">
    <td colspan=2 valign=center align=left>
      <strong>Modify Primary Information</strong>
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      Reassign To
    </td>
    <td valign=center>
    <%= UserList.getHtmlSelect("owner", ContactDetails.getOwner() ) %>
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      Contact Type
    </td>
    <td valign=center>
      <%= ContactTypeList.getHtmlSelect("typeId", ContactDetails.getTypeId()) %>
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      First Name
    </td>
    <td valign=center>
      <input type=text size=35 name="nameFirst" value="<%= toHtmlValue(ContactDetails.getNameFirst()) %>">
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      Middle Name
    </td>
    <td valign=center>
      <input type=text size=35 name="nameMiddle" value="<%= toHtmlValue(ContactDetails.getNameMiddle()) %>">
    </td>
  </tr>
  
  <tr class="containerBody">
    <td nowrap class="formLabel">
      Last Name
    </td>
    <td valign=center>
      <input type=text size=35 name="nameLast" value="<%= toHtmlValue(ContactDetails.getNameLast()) %>">
      <font color="red">-</font> <%= showAttribute(request, "lastcompanyError") %>
    </td>
  </tr>
  
  <tr class="containerBody">
    <td nowrap class="formLabel">
      Company
    </td>
    <td valign=center>
      <input type=text size=35 name="company" value="<%= toHtmlValue(ContactDetails.getCompanyOnly()) %>">
      <font color="red">-</font> <%= showAttribute(request, "lastcompanyError") %>
    </td>
  </tr>
  
  <dhv:evaluate exp="<%= (ContactDetails.getOrgId() == -1) %>">
  <tr class="containerBody">
    <td nowrap class="formLabel">
      Associated with Account
    </td>
    <td valign=center>
    <%= OrgList.getHtmlSelectDefaultNone("orgId")%>
    </td>
  </tr>  
  </dhv:evaluate>
  
  <dhv:evaluate exp="<%= (ContactDetails.getOrgId() > -1) %>">
  <tr class="containerBody">
    <td nowrap class="formLabel">
      Associated with Account
    </td>
    <td valign=center>
    <%= OrgList.getHtmlSelectDefaultNone("orgId", ContactDetails.getOrgId())%>
    </td>
  </tr>  
  </dhv:evaluate>
  
  <tr class="containerBody">
    <td nowrap class="formLabel">
      Title
    </td>
    <td valign=center>
      <input type=text size=35 name="title" value="<%= toHtmlValue(ContactDetails.getTitle()) %>">
    </td>
  </tr>
</table>
&nbsp;<br>  
<table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr class="title">
    <td valign=center align=left>
	    <strong>Email Addresses</strong>
	  </td>
  </tr>
<%
  int ecount = 0;
  Iterator enumber = ContactDetails.getEmailAddressList().iterator();
  while (enumber.hasNext()) {
    ++ecount;
    ContactEmailAddress thisEmailAddress = (ContactEmailAddress)enumber.next();
%>
  <tr class="containerBody">
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
  <tr class="containerBody">
    <td>
      <%= ContactEmailTypeList.getHtmlSelect("email" + (++ecount) + "type", "Business") %>
      <input type=text size=40 name="email<%= ecount %>address" maxlength=255>
    </td>
  </tr>
</table>
<div align="center" style="padding:3px;">Note: All international phone numbers must be preceded by a "+" symbol.</div>
<table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr class="title">
    <td valign=center align=left>
	    <strong>Phone Numbers</strong>
	  </td>
  </tr>
<%  
  int icount = 0;
  Iterator inumber = ContactDetails.getPhoneNumberList().iterator();
  
  while (inumber.hasNext()) {
    ++icount;
    ContactPhoneNumber thisPhoneNumber = (ContactPhoneNumber)inumber.next();
%>    
  <tr class="containerBody">
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
  <tr class="containerBody">
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
  Iterator anumber = ContactDetails.getAddressList().iterator();
  while (anumber.hasNext()) {
    ++acount;
    ContactAddress thisAddress = (ContactAddress)anumber.next();
%>    
  <tr class="containerBody">
    <input type="hidden" name="address<%= acount %>id" value="<%= thisAddress.getId() %>">
    <td>
      &nbsp;
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
      <input type=text size=40 name="address<%= acount %>line1" maxlength=80 value="<%= toHtmlValue(thisAddress.getStreetAddressLine1()) %>">
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      Address Line 2
    </td>
    <td>
      <input type=text size=40 name="address<%= acount %>line2" maxlength=80 value="<%= toHtmlValue(thisAddress.getStreetAddressLine2()) %>">
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      City
    </td>
    <td>
      <input type=text size=28 name="address<%= acount %>city" maxlength=80 value="<%= toHtmlValue(thisAddress.getCity()) %>">
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      State/Province
    </td>
    <td>
      <%=StateSelect.getHtml("address" + acount + "state", thisAddress.getState())%>
      <% StateSelect = new StateSelect(); %>
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      Zip/Postal Code
    </td>
    <td>
      <input type=text size=10 name="address<%= acount %>zip" maxlength=12 value="<%= toHtmlValue(thisAddress.getZip()) %>">
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      Country
    </td>
    <td>
      <%=CountrySelect.getHtml("address" + acount + "country", thisAddress.getCountry())%>
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
%>
  <tr class="containerBody">
    <td>
      &nbsp;
    </td>
    <td>
      <%= ContactAddressTypeList.getHtmlSelect("address" + (++acount) + "type", "Business") %>
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      Address Line 1
    </td>
    <td>
      <input type=text size=40 name="address<%= acount %>line1" maxlength=80>
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      Address Line 2
    </td>
    <td>
      <input type=text size=40 name="address<%= acount %>line2" maxlength=80>
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      City
    </td>
    <td>
      <input type=text size=28 name="address<%= acount %>city" maxlength=80>
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      State/Province
    </td>
    <td>
      <%=StateSelect.getHtml("address" + acount + "state")%>
      <% StateSelect = new StateSelect(); %>
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      Zip/Postal Code
    </td>
    <td>
      <input type=text size=10 name="address<%= acount %>zip" maxlength=12>
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      Country
    </td>
    <td>
      <%=CountrySelect.getHtml("address" + acount + "country")%>
      <% CountrySelect = new CountrySelect(); %>
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
  <tr class="containerBody">
    <td valign="top" class="formLabel">
      Notes
    </td>
    <td>
      <TEXTAREA NAME='notes' ROWS=3 COLS=50><%= toString(ContactDetails.getNotes()) %></TEXTAREA>
    </td>
  </tr>
</table>
<br>
    <input type="submit" value="Update" name="Save" onClick="this.form.dosubmit.value='true';">
    <% if (request.getParameter("return") != null) {%>
	<% if (request.getParameter("return").equals("list")) {%>
	<input type="submit" value="Cancel" onClick="javascript:this.form.action='ExternalContacts.do?command=ListContacts';this.form.dosubmit.value='false';">
	<%}%>
<%} else {%>
<input type="submit" value="Cancel" onClick="javascript:this.form.action='ExternalContacts.do?command=ContactDetails&id=<%= ContactDetails.getId() %>';this.form.dosubmit.value='false';">
<%}%>
<input type="reset" value="Reset">
<input type="hidden" name="dosubmit" value="true">
<input type="hidden" name="primaryContact" value="<%=ContactDetails.getPrimaryContact()%>">
  </td>
  </tr>
  </table>
</form>
