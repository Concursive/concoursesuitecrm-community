<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ page import="java.util.*,org.aspcfs.modules.contacts.base.*" %>
<%@ page import="java.text.DateFormat" %>
<jsp:useBean id="ContactDetails" class="org.aspcfs.modules.contacts.base.Contact" scope="request"/>
<%@ include file="../initPage.jsp" %>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/confirmDelete.js"></script>
<form name="details" action="CompanyDirectory.do?command=ModifyEmployee&empid=<%= ContactDetails.getId() %>" method="post">
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="CompanyDirectory.do?command=ListEmployees">Employees</a> >
<a href="CompanyDirectory.do?command=ListEmployees">View Employees</a> >
Employee Details
</td>
</tr>
</table>
<%-- End Trails --%>
<%@ include file="contact_details_header_include.jsp" %>
<% String param1 = "id=" + ContactDetails.getId(); %>
<dhv:container name="employees" selected="details" param="<%= param1 %>" style="tabs"/>
<table cellpadding="4" cellspacing="0" border="0" width="100%">
  <tr>
    <td class="containerBack">
<dhv:permission name="contacts-internal_contacts-edit"><input type="button" value="Modify" onClick="javascript:this.form.action='CompanyDirectory.do?command=ModifyEmployee&empid=<%= ContactDetails.getId() %>';submit();"></dhv:permission>
<dhv:permission name="contacts-internal_contacts-delete"><input type="button" value="Delete" onClick="javascript:this.form.action='CompanyDirectory.do?command=DeleteEmployee&empid=<%=ContactDetails.getId() %>';confirmSubmit(document.details);"></dhv:permission>
<dhv:permission name="contacts-internal_contacts-edit,contacts-internal_contacts-delete"><br>&nbsp;</dhv:permission>
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="2">
	    <strong>Email Addresses</strong>
	  </th>
  </tr>
<%  
  Iterator iemail = ContactDetails.getEmailAddressList().iterator();
  if (iemail.hasNext()) {
    while (iemail.hasNext()) {
      ContactEmailAddress thisEmailAddress = (ContactEmailAddress)iemail.next();
%>    
  <tr class="containerBody">
    <td nowrap class="formLabel"><%= toHtml(thisEmailAddress.getTypeName()) %></td>
    <td><a href="mailto:<%= toHtml(thisEmailAddress.getEmail()) %>"><%= toHtml(thisEmailAddress.getEmail()) %></a></td>
  </tr>
<%    
    }
  } else {
%>
  <tr class="containerBody">
    <td colspan="2"><font color="#9E9E9E">No email addresses entered.</font></td>
  </tr>
<%}%>
</table>
&nbsp;
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="2">
	    <strong>Phone Numbers</strong>
	  </th>
  </tr>
<%  
  Iterator inumber = ContactDetails.getPhoneNumberList().iterator();
  if (inumber.hasNext()) {
    while (inumber.hasNext()) {
      ContactPhoneNumber thisPhoneNumber = (ContactPhoneNumber)inumber.next();
%>    
  <tr class="containerBody">
    <td class="formLabel" nowrap><%= toHtml(thisPhoneNumber.getTypeName()) %></td>
    <td><%= toHtml(thisPhoneNumber.getPhoneNumber()) %>&nbsp;</td>
  </tr>
<%
    }
  } else {
%>
  <tr class="containerBody">
    <td colspan="2"><font color="#9E9E9E">No phone numbers entered.</font></td>
  </tr>
<%}%>
</table>
&nbsp;
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="2">
	    <strong>Addresses</strong>
	  </th>
  </tr>
<%  
  Iterator iaddress = ContactDetails.getAddressList().iterator();
  if (iaddress.hasNext()) {
    while (iaddress.hasNext()) {
      ContactAddress thisAddress = (ContactAddress)iaddress.next();
%>    
    <tr class="containerBody">
      <td class="formLabel" valign="top" nowrap><%= toHtml(thisAddress.getTypeName()) %></td>
      <td><%= toHtml(thisAddress.toString()) %>&nbsp;</td>
    </tr>
<%    
    }
  } else {
%>
  <tr class="containerBody">
    <td colspan="2"><font color="#9E9E9E">No addresses entered.</font></td>
  </tr>
<%}%>
</table>
&nbsp;
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="2">
	    <strong>Additional Details</strong>
	  </th>
  </tr>
  <tr class="containerBody">
    <td class="formLabel" nowrap>Notes</td>
    <td><%= toHtml(ContactDetails.getNotes()) %>&nbsp;</td>
  </tr>
</table>
&nbsp;
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="2">
      <strong>Record Information</strong>
    </th>
  </tr>
  <tr class="containerBody">
    <td class="formLabel">
      Owner
    </td>
    <td>
      <dhv:username id="<%= ContactDetails.getOwner() %>"/>
      <dhv:evaluate exp="<%=!(ContactDetails.getHasEnabledOwnerAccount())%>"><font color="red">(No longer has access)</font></dhv:evaluate>
    </td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel">
      Entered
    </td>
    <td>
      <dhv:username id="<%= ContactDetails.getEnteredBy() %>"/>
      <zeroio:tz timestamp="<%= ContactDetails.getEntered()  %>" />
    </td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel">
      Modified
    </td>
    <td>
      <dhv:username id="<%= ContactDetails.getModifiedBy() %>"/>
      <zeroio:tz timestamp="<%= ContactDetails.getModified()  %>" />
    </td>
  </tr>
</table>
<dhv:permission name="contacts-internal_contacts-edit,contacts-internal_contacts-delete"><br></dhv:permission>
<dhv:permission name="contacts-internal_contacts-edit"><input type="button" value="Modify" onClick="javascript:this.form.action='CompanyDirectory.do?command=ModifyEmployee&empid=<%= ContactDetails.getId() %>';submit();"></dhv:permission>
<dhv:permission name="contacts-internal_contacts-delete"><input type="button" value="Delete" onClick="javascript:this.form.action='CompanyDirectory.do?command=DeleteEmployee&empid=<%=ContactDetails.getId() %>';confirmSubmit(document.details);"></dhv:permission>
</td></tr>
</table>
</form>


