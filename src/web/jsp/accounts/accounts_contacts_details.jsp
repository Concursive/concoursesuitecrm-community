<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,java.text.DateFormat,org.aspcfs.modules.accounts.base.*,org.aspcfs.modules.contacts.base.*" %>
<jsp:useBean id="OrgDetails" class="org.aspcfs.modules.accounts.base.Organization" scope="request"/>
<jsp:useBean id="ContactDetails" class="org.aspcfs.modules.contacts.base.Contact" scope="request"/>
<%@ include file="../initPage.jsp" %>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popURL.js"></script>
<form name="modContact" action="Contacts.do?command=Modify&id=<%=ContactDetails.getId()%>&orgId=<%=ContactDetails.getOrgId()%>" method="post">
<%-- Trails --%>
<table class="trails">
<tr>
<td>
<a href="Accounts.do">Account Management</a> > 
<a href="Accounts.do?command=Search">Search Results</a> >
<a href="Accounts.do?command=Details&orgId=<%=OrgDetails.getOrgId()%>">Account Details</a> >
<a href="Contacts.do?command=View&orgId=<%=OrgDetails.getOrgId()%>">Contacts</a> >
Contact Details
</td>
</tr>
</table>
<%-- End Trails --%>
<%@ include file="accounts_details_header_include.jsp" %>
<dhv:container name="accounts" selected="contacts" param="<%= "orgId=" + OrgDetails.getOrgId() %>" style="tabs"/>
<table cellpadding="4" cellspacing="0" border="0" width="100%">
  <tr>
    <td class="containerBack">
    <% String param1 = "id=" + ContactDetails.getId(); 
    %>
        <strong><%= ContactDetails.getNameLastFirst() %>:</strong>
        [ <dhv:container name="accountscontacts" selected="details" param="<%= param1 %>"/> ]
      <br>
      <br>
      <input type="hidden" name="id" value="<%=ContactDetails.getId()%>">
      <input type="hidden" name="orgId" value="<%=ContactDetails.getOrgId()%>">
<dhv:permission name="accounts-accounts-contacts-edit"><input type='button' value="Modify"	onClick="javascript:this.form.action='Contacts.do?command=Modify';submit();"></dhv:permission>
<dhv:permission name="accounts-accounts-contacts-add"><input type='button' value="Clone"	onClick="javascript:this.form.action='Contacts.do?command=Clone';submit();"></dhv:permission>
<dhv:permission name="accounts-accounts-contacts-delete"><input type='button' value="Delete" onClick="javascript:popURLReturn('Contacts.do?command=ConfirmDelete&orgId=<%=OrgDetails.getId()%>&id=<%=ContactDetails.getId()%>&popup=true','Contacts.do?command=View', 'Delete_contact','320','200','yes','no');"></dhv:permission>
<dhv:permission name="accounts-accounts-contacts-edit,accounts-accounts-contacts-delete"><br>&nbsp;</dhv:permission>
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="2">
      <strong><%= toHtml(ContactDetails.getNameFull()) %></strong>
    </th>     
  </tr>
<dhv:evaluate exp="<%= hasText(ContactDetails.getTitle()) %>">
  <tr class="containerBody">
    <td class="formLabel">
      Title
    </td>
    <td>
      <%= toHtml(ContactDetails.getTitle()) %>
    </td>
  </tr>
</dhv:evaluate>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      Entered
    </td>
    <td>
      <dhv:username id="<%= ContactDetails.getEnteredBy() %>"/>
      -
      <dhv:tz timestamp="<%= ContactDetails.getEntered() %>" dateFormat="<%= DateFormat.SHORT %>" timeFormat="<%= DateFormat.LONG %>"/>
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      Modified
    </td>
    <td>
      <dhv:username id="<%= ContactDetails.getModifiedBy() %>"/>
      -
      <dhv:tz timestamp="<%= ContactDetails.getModified() %>" dateFormat="<%= DateFormat.SHORT %>" timeFormat="<%= DateFormat.LONG %>"/>
    </td>
  </tr>
</table>
&nbsp;
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
    <td class="formLabel">
      <%= toHtml(thisEmailAddress.getTypeName()) %>
    </td>
    <td>
      <dhv:evaluate exp="<%= hasText(thisEmailAddress.getEmail()) %>">
      <a href="mailto:<%= toHtml(thisEmailAddress.getEmail()) %>"><%= toHtml(thisEmailAddress.getEmail()) %></a>
      </dhv:evaluate>
      &nbsp;
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
    <td class="formLabel">
      <%= toHtml(thisPhoneNumber.getTypeName()) %>
    </td>
    <td>
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
    <td class="formLabel" valign="top">
      <%= toHtml(thisAddress.getTypeName()) %>
    </td>
    <td>
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
<dhv:permission name="accounts-accounts-contacts-edit,accounts-accounts-contacts-delete"><br></dhv:permission>
<dhv:permission name="accounts-accounts-contacts-edit"><input type='button' value="Modify"	onClick="javascript:this.form.action='Contacts.do?command=Modify';submit();"></dhv:permission>
<dhv:permission name="accounts-accounts-contacts-add"><input type='button' value="Clone"	onClick="javascript:this.form.action='Contacts.do?command=Clone';submit();"></dhv:permission>
<dhv:permission name="accounts-accounts-contacts-delete"><input type='button' value="Delete" onClick="javascript:popURLReturn('Contacts.do?command=ConfirmDelete&orgId=<%=OrgDetails.getId()%>&id=<%=ContactDetails.getId()%>&popup=true','Contacts.do?command=View', 'Delete_contact','320','200','yes','no');"></dhv:permission>
  </td>
  </tr>
</table>
</form>
