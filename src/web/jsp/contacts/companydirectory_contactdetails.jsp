<%@ taglib uri="WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,com.darkhorseventures.cfsbase.*" %>
<jsp:useBean id="ContactDetails" class="com.darkhorseventures.cfsbase.Contact" scope="request"/>
<%@ include file="initPage.jsp" %>
<script language="JavaScript" TYPE="text/javascript" SRC="/javascript/confirmDelete.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="/javascript/popURL.js"></script>
<form name="details" action="/ExternalContacts.do?command=ContactDetails&id=<%= ContactDetails.getId() %>&action=modify" method="post">
<a href="/ExternalContacts.do">Contacts &amp; Resources</a> > 
<a href="/ExternalContacts.do?command=ListContacts">View Contacts</a> >
Contact Details<br>
<hr color="#BFBFBB" noshade>
<a href="/ExternalContacts.do?command=ListContacts">Back to Contact List</a><br>&nbsp;
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
    

<dhv:permission name="contacts-external_contacts-edit"><input type=button name="action" value="Modify"	onClick="document.details.command.value='Modify';document.details.submit()"></dhv:permission>
<dhv:permission name="contacts-external_contacts-delete"><input type="button" name="action" value="Delete" onClick="javascript:popURLReturn('/ExternalContacts.do?command=ConfirmDelete&id=<%=ContactDetails.getId()%>','ExternalContacts.do?command=ListContacts', 'Delete_contact','320','200','yes','no');"></dhv:permission>
<dhv:permission name="contacts-external_contacts-edit,contacts-external_contacts-delete"><br>&nbsp;</dhv:permission>

<table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr class="title">
    <td colspan="2">
      <strong>Primary Information</strong>
      [Category: <%= toHtml(ContactDetails.getTypeName()) %>]
    </td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel">
      Owner
    </td>
    <td>
      <%= toHtml(ContactDetails.getOwnerName()) %>
      <dhv:evaluate exp="<%=!(ContactDetails.getHasEnabledOwnerAccount())%>"><font color="red">*</font></dhv:evaluate>
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      Company
    </td>
    <td width="100%">
      <%= toHtml(ContactDetails.getCompany()) %><dhv:evaluate exp="<%= (!(ContactDetails.getOrgEnabled()) && ContactDetails.getOrgId() > 0)%>">&nbsp;<font color="red">(account disabled)</font></dhv:evaluate>
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      Title
    </td>
    <td>
      <%= toHtml(ContactDetails.getTitle()) %>&nbsp; &nbsp;
    </td>
  </tr>
  
  <tr class="containerBody">
    <td nowrap class="formLabel">
      Entered
    </td>
    <td>
      <%= ContactDetails.getEnteredByName() %>&nbsp;-&nbsp;<%= ContactDetails.getEnteredString() %>
    </td>
  </tr>
  
  <tr class="containerBody">
    <td nowrap class="formLabel">
      Modified
    </td>
    <td>
      <%= ContactDetails.getModifiedByName() %>&nbsp;-&nbsp;<%= ContactDetails.getModifiedString() %>
    </td>
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
  Iterator iemail = ContactDetails.getEmailAddressList().iterator();
  if (iemail.hasNext()) {
    while (iemail.hasNext()) {
      ContactEmailAddress thisEmailAddress = (ContactEmailAddress)iemail.next();
%>    
    <tr class="containerBody">
      <td nowrap class="formLabel">
        <%= toHtml(thisEmailAddress.getTypeName()) %>
      </td>
      <td width="100%">
        <a href="mailto:<%= toHtml(thisEmailAddress.getEmail()) %>"><%= toHtml(thisEmailAddress.getEmail()) %></a>
      </td>
    </tr>
<%    
    }
  } else {
%>
    <tr class="containerBody">
      <td>
        <font color="#9E9E9E">No email addresses entered.</font>
      </td>
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
  Iterator inumber = ContactDetails.getPhoneNumberList().iterator();
  if (inumber.hasNext()) {
    while (inumber.hasNext()) {
      ContactPhoneNumber thisPhoneNumber = (ContactPhoneNumber)inumber.next();
%>    
    <tr class="containerBody">
      <td nowrap class="formLabel">
        <%= toHtml(thisPhoneNumber.getTypeName()) %>
      </td>
      <td width="100%">
        <%= toHtml(thisPhoneNumber.getPhoneNumber()) %>
      </td>
    </tr>
<%    
    }
  } else {
%>
  <tr class="containerBody">
    <td>
      <font color="#9E9E9E">No phone numbers entered.</font>
    </td>
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
  Iterator iaddress = ContactDetails.getAddressList().iterator();
  if (iaddress.hasNext()) {
    while (iaddress.hasNext()) {
      ContactAddress thisAddress = (ContactAddress)iaddress.next();
%>    
    <tr class="containerBody">
      <td nowrap class="formLabel">
        <%= toHtml(thisAddress.getTypeName()) %>
      </td>
      <td width="100%">
        <%= toHtml(thisAddress.toString()) %>
      </td>
    </tr>
<%    
    }
  } else {
%>
  <tr class="containerBody">
    <td>
      <font color="#9E9E9E">No addresses entered.</font>
    </td>
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
  <tr class="containerBody">
    <td nowrap class="formLabel">Notes</td>
    <td width="100%"><%= toHtml(ContactDetails.getNotes()) %>&nbsp;</td>
  </tr>
</table>
<dhv:permission name="contacts-external_contacts-delete,contacts-external_contacts-edit"><br></dhv:permission>
<dhv:permission name="contacts-external_contacts-edit"><input type=button name="action" value="Modify"	onClick="document.details.command.value='Modify';document.details.submit()"></dhv:permission>
<dhv:permission name="contacts-external_contacts-delete"><input type="button" name="action" value="Delete" onClick="javascript:popURLReturn('/ExternalContacts.do?command=ConfirmDelete&id=<%=ContactDetails.getId()%>','ExternalContacts.do?command=ListContacts', 'Delete_contact','320','200','yes','no');"></dhv:permission>
</td></tr>
</table>
<input type=hidden name="command" value="">
</form>
