<%@ taglib uri="WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,com.darkhorseventures.cfsbase.*" %>
<jsp:useBean id="OrgDetails" class="com.darkhorseventures.cfsbase.Organization" scope="request"/>
<%@ include file="initPage.jsp" %>
<form name="details" action="/Accounts.do" method="post">
<a href="/Accounts.do?command=View">Back to Account List</a><br>&nbsp;
<table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr class="containerHeader">
    <td>
      <strong><%= toHtml(OrgDetails.getName()) %></strong>
    </td>
  </tr>
  <tr class="containerMenu">
    <td>
      <a href="/Accounts.do?command=Details&orgId=<%= OrgDetails.getOrgId() %>"><font color="#0000FF">Details</font></a><dhv:permission name="accounts-accounts-folders-view"> | 
      <a href="/Accounts.do?command=Fields&orgId=<%= OrgDetails.getOrgId() %>"><font color="#000000">Folders</font></a></dhv:permission><dhv:permission name="accounts-accounts-contacts-view"> |
      <a href="Contacts.do?command=View&orgId=<%= OrgDetails.getOrgId() %>"><font color="#000000">Contacts</font></a></dhv:permission><dhv:permission name="accounts-accounts-opportunities-view"> | 
      <a href="Opportunities.do?command=View&orgId=<%= OrgDetails.getOrgId() %>"><font color="#000000">Opportunities</font></a></dhv:permission><dhv:permission name="accounts-accounts-tickets-view"> | 
      <a href="Accounts.do?command=ViewTickets&orgId=<%= OrgDetails.getOrgId() %>"><font color="#000000">Tickets</font></a></dhv:permission><dhv:permission name="accounts-accounts-documents-view"> |
      <a href="AccountsDocuments.do?command=View&orgId=<%=OrgDetails.getOrgId()%>"><font color="#000000">Documents</font></a></dhv:permission>
    </td>
  </tr>
  <tr>
    <td class="containerBack">
<input type=hidden name="orgId" value="<%= OrgDetails.getOrgId() %>">

<dhv:permission name="accounts-accounts-edit"><input type=button name="action" value="Modify"	onClick="document.details.command.value='Modify';document.details.submit()"></dhv:permission>
<dhv:permission name="accounts-accounts-delete"><input type=button name="action" value="Delete" onClick="document.details.command.value='Delete';document.details.submit()"></dhv:permission>
<dhv:permission name="accounts-accounts-edit,accounts-accounts-delete"><br>&nbsp;</dhv:permission>

<table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr class="title">
    <td colspan=2 valign=center align=left>
      <strong>Primary Information</strong>
    </td>     
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      Owner
    </td>
    <td>
      <%= OrgDetails.getOwnerName() %>
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      Acct. Number
    </td>
    <td width=100%>
       <%= toHtml(OrgDetails.getAccountNumber()) %>&nbsp;
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      Web Site URL
    </td>
    <td>
      <a href="<%= toHtml(OrgDetails.getUrl()) %>" target="_new"><%=toHtml(OrgDetails.getUrl()) %></a>&nbsp;
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      Industry
    </td>
    <td>
       <%= toHtml(OrgDetails.getIndustryName()) %>&nbsp;
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      No. of Employees
    </td>
    <td>
       <%= OrgDetails.getEmployees() %>&nbsp;
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      Revenue
    </td>
    <td>
       <%= OrgDetails.getRevenue() %>&nbsp;
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      Ticker Symbol
    </td>
    <td>
       <%= toHtml(OrgDetails.getTicker()) %>&nbsp;
    </td>
  </tr>
  
    <tr class="containerBody">
    <td nowrap class="formLabel">
      Contract End Date
    </td>
    <td>
       <%= toHtml(OrgDetails.getContractEndDateString()) %>&nbsp;
    </td>
  </tr>
  
   <tr class="containerBody">
    <td nowrap class="formLabel">
      Alert
    </td>
    <td valign=center colspan=1>
       <%= toHtml(OrgDetails.getAlertDetails()) %>
    </td>
  </tr>
  
  <tr class="containerBody">
    <td nowrap class="formLabel">
      Entered
    </td>
    <td>
      <%= toHtml(OrgDetails.getEnteredByName()) %>&nbsp;-&nbsp;<%= OrgDetails.getEnteredString() %>
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      Modified
    </td>
    <td>
      <%= toHtml(OrgDetails.getModifiedByName()) %>&nbsp;-&nbsp;<%= OrgDetails.getModifiedString() %>
    </td>
  </tr>
</table>
&nbsp;
<table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr class="title">
    <td colspan=2 valign=center align=left>
	    <strong>Phone Numbers</strong>
	  </td>
  </tr>
<%  
  Iterator inumber = OrgDetails.getPhoneNumberList().iterator();
  if (inumber.hasNext()) {
    while (inumber.hasNext()) {
      OrganizationPhoneNumber thisPhoneNumber = (OrganizationPhoneNumber)inumber.next();
%>    
    <tr class="containerBody">
      <td nowrap class="formLabel">
        <%= toHtml(thisPhoneNumber.getTypeName()) %>
      </td>
      <td width=100%>
        <%= toHtml(thisPhoneNumber.getPhoneNumber()) %>
      </td>
    </tr>
<%    
    }
  } else {
%>
    <tr class="containerBody">
      <td colspan=2>
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
  Iterator iaddress = OrgDetails.getAddressList().iterator();
  if (iaddress.hasNext()) {
    while (iaddress.hasNext()) {
      OrganizationAddress thisAddress = (OrganizationAddress)iaddress.next();
%>    
    <tr class="containerBody">
      <td nowrap class="formLabel">
        <%= toHtml(thisAddress.getTypeName()) %>
      </td>
      <td width=100%>
        <%= toHtml(thisAddress.toString()) %>
      </td>
    </tr>
<%    
    }
  } else {
%>
    <tr class="containerBody">
      <td colspan=2>
        <font color="#9E9E9E">No addresses entered.</font>
      </td>
    </tr>
<%}%>
</table>
&nbsp;
<table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr class="title">
    <td colspan=2 valign=center align=left>
	    <strong>Email Addresses</strong>
	  </td>
  </tr>
<%
  Iterator iemail = OrgDetails.getEmailAddressList().iterator();
  if (iemail.hasNext()) {
    while (iemail.hasNext()) {
      OrganizationEmailAddress thisEmailAddress = (OrganizationEmailAddress)iemail.next();
%>    
    <tr class="containerBody">
      <td nowrap class="formLabel">
        <%= toHtml(thisEmailAddress.getTypeName()) %>
      </td>
      <td width=100%>
        <a href="mailto:<%= toHtml(thisEmailAddress.getEmail()) %>"><%= toHtml(thisEmailAddress.getEmail()) %></a>
      </td>
    </tr>
<%    
    }
  } else {
%>
    <tr class="containerBody">
      <td colspan=2>
        <font color="#9E9E9E">No email addresses entered.</font>
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
    <td nowrap class="formLabel">
      Notes
    </td>
    <td width=100%>
      <%=toHtml(OrgDetails.getNotes()) %>&nbsp;
    </td>
  </tr>
</table>
<input type=hidden name="command" value="">
<dhv:permission name="accounts-accounts-edit,accounts-accounts-delete"><br></dhv:permission>
<dhv:permission name="accounts-accounts-edit"><input type=button name="action" value="Modify"	onClick="document.details.command.value='Modify';document.details.submit()"></dhv:permission>
<dhv:permission name="accounts-accounts-delete"><input type=button name="action" value="Delete" onClick="document.details.command.value='Delete';document.details.submit()"></dhv:permission>
</td></tr>
</table>
</form>
