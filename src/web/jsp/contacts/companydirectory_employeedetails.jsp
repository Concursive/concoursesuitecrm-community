<%@ taglib uri="WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,com.darkhorseventures.cfsbase.*" %>
<jsp:useBean id="EmployeeBean" class="com.darkhorseventures.cfsbase.Contact" scope="request"/>
<%@ include file="initPage.jsp" %>
<script language="JavaScript" TYPE="text/javascript" SRC="/javascript/confirmDelete.js"></script>
<a href="/CompanyDirectory.do?command=ListEmployees">Back to Employee List</a><p>
<form name="details" action="/CompanyDirectory.do?command=EmployeeDetails&empid=<%= EmployeeBean.getId() %>&action=modify" method="post">
<dhv:permission name="contacts-internal_contacts-edit"><input type="button" value="Modify" onClick="javascript:this.form.action='/CompanyDirectory.do?command=EmployeeDetails&empid=<%= EmployeeBean.getId() %>&action=modify';submit();"></dhv:permission>
<dhv:permission name="contacts-internal_contacts-delete"><input type="button" value="Delete" onClick="javascript:this.form.action='/CompanyDirectory.do?command=DeleteEmployee&empid=<%=EmployeeBean.getId() %>';confirmSubmit(document.details);"></dhv:permission>
<dhv:permission name="contacts-internal_contacts-edit,contacts-internal_contacts-delete"><br>&nbsp;</dhv:permission>

<table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr class="title">
    <td colspan=2 valign=center align=left>
	    <strong><%= toHtml(EmployeeBean.getNameFull()) %></strong>
	  </td>
  </tr>
  <tr>
    <td nowrap class="formLabel">Department</td>
    <td width="100%"><%= toHtml(EmployeeBean.getDepartmentName()) %> &nbsp;</td>
  </tr>
  <tr>
    <td nowrap class="formLabel">Title</td>
    <td><%= toHtml(EmployeeBean.getTitle()) %> &nbsp;</td>
  </tr>
</table>
&nbsp;
<table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr class="title">
    <td colspan=2 valign=center align=left>
	    <strong>Email Addresses</strong>
	  </td>
  </tr>
<%  
  Iterator iemail = EmployeeBean.getEmailAddressList().iterator();
  if (iemail.hasNext()) {
    while (iemail.hasNext()) {
      ContactEmailAddress thisEmailAddress = (ContactEmailAddress)iemail.next();
%>    
  <tr>
    <td nowrap class="formLabel"><%= toHtml(thisEmailAddress.getTypeName()) %></td>
    <td width="100%"><a href="mailto:<%= toHtml(thisEmailAddress.getEmail()) %>"><%= toHtml(thisEmailAddress.getEmail()) %></a></td>
  </tr>
<%    
    }
  } else {
%>
  <tr>
    <td><font color="#9E9E9E">No email addresses entered.</font></td>
  </tr>
<%}%>
</table>
&nbsp;
<table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr class="title">
    <td colspan=2 valign=center align=left>
	    <strong>Phone Numbers</strong>
	  </td>
  </tr>
<%  
  Iterator inumber = EmployeeBean.getPhoneNumberList().iterator();
  if (inumber.hasNext()) {
    while (inumber.hasNext()) {
      ContactPhoneNumber thisPhoneNumber = (ContactPhoneNumber)inumber.next();
%>    
  <tr>
    <td nowrap class="formLabel"><%= toHtml(thisPhoneNumber.getTypeName()) %></td>
    <td width="100%"><%= toHtml(thisPhoneNumber.getPhoneNumber()) %></td>
  </tr>
<%
    }
  } else {
%>
  <tr>
    <td><font color="#9E9E9E">No phone numbers entered.</font></td>
  </tr>
<%}%>
</table>
&nbsp;
<table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr class="title">
    <td colspan=2 valign=center align=left>
	    <strong>Addresses</strong>
	  </td>
  </tr>
<%  
  Iterator iaddress = EmployeeBean.getAddressList().iterator();
  if (iaddress.hasNext()) {
    while (iaddress.hasNext()) {
      ContactAddress thisAddress = (ContactAddress)iaddress.next();
%>    
    <tr>
      <td nowrap class="formLabel"><%= toHtml(thisAddress.getTypeName()) %></td>
      <td width="100%"><%= toHtml(thisAddress.toString()) %></td>
    </tr>
<%    
    }
  } else {
%>
  <tr>
    <td><font color="#9E9E9E">No addresses entered.</font></td>
  </tr>
<%}%>
</table>
&nbsp;
<table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr class="title">
    <td colspan=2 valign=center align=left>
	    <strong>Additional Details</strong>
	  </td>
  </tr>
  <tr>
    <td nowrap class="formLabel">Notes</td>
    <td width="100%"><%= toHtml(EmployeeBean.getNotes()) %>&nbsp;</td>
  </tr>
</table>
<dhv:permission name="contacts-internal_contacts-edit,contacts-internal_contacts-delete"><br></dhv:permission>
<dhv:permission name="contacts-internal_contacts-edit"><input type="button" value="Modify" onClick="javascript:this.form.action='/CompanyDirectory.do?command=EmployeeDetails&empid=<%= EmployeeBean.getId() %>&action=modify';submit();"></dhv:permission>
<dhv:permission name="contacts-internal_contacts-delete"><input type="button" value="Delete" onClick="javascript:this.form.action='/CompanyDirectory.do?command=DeleteEmployee&empid=<%=EmployeeBean.getId() %>';confirmSubmit(document.details);"></dhv:permission>
</form>

