<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,org.aspcfs.modules.accounts.base.*,org.aspcfs.modules.contacts.base.*" %>
<jsp:useBean id="OrgDetails" class="org.aspcfs.modules.accounts.base.Organization" scope="request"/>
<%@ include file="../initPage.jsp" %>
<script language="JavaScript" TYPE="text/javascript" SRC="/javascript/popURL.js"></script>
<a href="Accounts.do">Account Management</a> > 
<% if (request.getParameter("return") == null) { %>
<a href="Accounts.do?command=View">View Accounts</a> >
<%} else if (request.getParameter("return").equals("dashboard")) {%>
<a href="Accounts.do?command=Dashboard">Dashboard</a> >
<%}%>
Account Details<br>
<hr color="#BFBFBB" noshade>
<%@ include file="accounts_details_header_include.jsp" %>
<% String param1 = "orgId=" + OrgDetails.getOrgId(); %>      
<dhv:container name="accounts" selected="details" param="<%= param1 %>" style="tabs"/>
<table cellpadding="4" cellspacing="0" border="0" width="100%">
  <tr>
    <td class="containerBack">
      <input type="hidden" name="orgId" value="<%= OrgDetails.getOrgId() %>">
<dhv:evaluate exp="<%= OrgDetails.getEnabled() %>">
  <dhv:permission name="accounts-accounts-edit">
      <input type="button" value="Modify" onClick="javascript:window.location.href='Accounts.do?command=Modify&orgId=<%= OrgDetails.getOrgId() %>';">
  </dhv:permission>
</dhv:evaluate>
<dhv:evaluate exp="<%= !(OrgDetails.getEnabled()) %>">
  <dhv:permission name="accounts-accounts-edit">
    <input type="button" value="Enable"	onClick="javascript:window.location.href='Accounts.do?command=Enable&orgId=<%= OrgDetails.getOrgId() %>';">
  </dhv:permission>
</dhv:evaluate>
<dhv:permission name="accounts-accounts-delete"><input type="button" value="Delete Account" onClick="javascript:popURLReturn('Accounts.do?command=ConfirmDelete&id=<%=OrgDetails.getId()%>&popup=true','Accounts.do?command=View', 'Delete_account','320','200','yes','no');"></dhv:permission>
<dhv:permission name="accounts-accounts-edit,accounts-accounts-delete"><br>&nbsp;</dhv:permission>
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="2">
      <strong>Primary Information</strong>
    </th>     
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      Owner
    </td>
    <td>
      <%= OrgDetails.getOwnerName() %>
      <dhv:evaluate exp="<%= !(OrgDetails.getHasEnabledOwnerAccount()) %>"><font color="red">*</font></dhv:evaluate>
    </td>
  </tr>
<dhv:evaluate exp="<%= hasText(OrgDetails.getTypes().valuesAsString()) %>">
  <tr class="containerBody">
    <td nowrap class="formLabel">
      Account Type(s)
    </td>
    <td>  
      <%= toHtml(OrgDetails.getTypes().valuesAsString()) %>
     </td>
  </tr>
</dhv:evaluate>
<dhv:evaluate exp="<%= hasText(OrgDetails.getAccountNumber()) %>">
  <tr class="containerBody">
    <td nowrap class="formLabel">
      Account Number
    </td>
    <td>
       <%= toHtml(OrgDetails.getAccountNumber()) %>&nbsp;
    </td>
  </tr>
</dhv:evaluate>
<dhv:evaluate exp="<%= hasText(OrgDetails.getUrl()) %>">
  <tr class="containerBody">
    <td nowrap class="formLabel">
      Web Site URL
    </td>
    <td>
      <a href="<%= toHtml(OrgDetails.getUrlString()) %>" target="_new"><%= toHtml(OrgDetails.getUrl()) %></a>&nbsp;
    </td>
  </tr>
</dhv:evaluate>
<dhv:evaluate exp="<%= hasText(OrgDetails.getIndustryName()) %>">
  <tr class="containerBody">
    <td nowrap class="formLabel">
      Industry
    </td>
    <td>
       <%= toHtml(OrgDetails.getIndustryName()) %>&nbsp;
    </td>
  </tr>
</dhv:evaluate>
<dhv:include name="accounts-employees" none="true">
<dhv:evaluate exp="<%= (OrgDetails.getEmployees() > 0) %>">
  <tr class="containerBody">
    <td nowrap class="formLabel">
      No. of Employees
    </td>
    <td>
       <%= OrgDetails.getEmployees() %>&nbsp;
    </td>
  </tr>
</dhv:evaluate>
</dhv:include>
<dhv:include name="accounts-revenue" none="true">
<dhv:evaluate exp="<%= (OrgDetails.getRevenue() > 0) %>">
  <tr class="containerBody">
    <td nowrap class="formLabel">
      Revenue
    </td>
    <td>
       $<%= OrgDetails.getRevenueCurrency() %>&nbsp;
    </td>
  </tr>
</dhv:evaluate>
</dhv:include>
<dhv:evaluate exp="<%= hasText(OrgDetails.getTicker()) %>">
  <tr class="containerBody">
    <td nowrap class="formLabel">
      Ticker Symbol
    </td>
    <td>
       <%= toHtml(OrgDetails.getTicker()) %>&nbsp;
    </td>
  </tr>
</dhv:evaluate>
<dhv:evaluate exp="<%= hasText(OrgDetails.getContractEndDateString()) %>">
  <tr class="containerBody">
    <td nowrap class="formLabel">
      Contract End Date
    </td>
    <td>
       <%= toHtml(OrgDetails.getContractEndDateString()) %>&nbsp;
    </td>
  </tr>
</dhv:evaluate>
<dhv:evaluate exp="<%= hasText(OrgDetails.getAlertText()) %>">
   <tr class="containerBody">
    <td nowrap class="formLabel">
      Alert Description
    </td>
    <td>
       <%= toHtml(OrgDetails.getAlertText()) %>
    </td>
  </tr>
</dhv:evaluate>
<dhv:evaluate exp="<%= (OrgDetails.getAlertDate() != null) %>">
   <tr class="containerBody">
    <td nowrap class="formLabel">
      Alert Date
    </td>
    <td>
       <%= OrgDetails.getAlertDateStringLongYear() %>
    </td>
  </tr>
</dhv:evaluate>
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
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="2">
	    <strong>Phone Numbers</strong>
	  </th>
  </tr>
<dhv:evaluate exp="<%=(OrgDetails.getPrimaryContact() == null)%>">
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
      <td>
        <%= toHtml(thisPhoneNumber.getPhoneNumber()) %>
      </td>
    </tr>
<%    
    }
  } else {
%>
    <tr class="containerBody">
      <td colspan="2">
        <font color="#9E9E9E">No phone numbers entered.</font>
      </td>
    </tr>
<%}%>
</dhv:evaluate>
<dhv:evaluate exp="<%=(OrgDetails.getPrimaryContact() != null)%>">
<%  
  Iterator inumber = OrgDetails.getPrimaryContact().getPhoneNumberList().iterator();
  if (inumber.hasNext()) {
    while (inumber.hasNext()) {
      ContactPhoneNumber thisPhoneNumber = (ContactPhoneNumber)inumber.next();
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
      <td colspan="2">
        <font color="#9E9E9E">No phone numbers entered.</font>
      </td>
    </tr>
<%}%>
</dhv:evaluate>
</table>
&nbsp;
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="2">
	    <strong>Addresses</strong>
	  </th>
  </tr>
<dhv:evaluate exp="<%=(OrgDetails.getPrimaryContact() == null)%>">
<%  
  Iterator iaddress = OrgDetails.getAddressList().iterator();
  if (iaddress.hasNext()) {
    while (iaddress.hasNext()) {
      OrganizationAddress thisAddress = (OrganizationAddress)iaddress.next();
%>    
    <tr class="containerBody">
      <td nowrap class="formLabel" valign="top">
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
      <td colspan="2">
        <font color="#9E9E9E">No addresses entered.</font>
      </td>
    </tr>
<%}%>
</dhv:evaluate>
<dhv:evaluate exp="<%=(OrgDetails.getPrimaryContact() != null)%>">
<%  
  Iterator iaddress = OrgDetails.getPrimaryContact().getAddressList().iterator();
  if (iaddress.hasNext()) {
    while (iaddress.hasNext()) {
      ContactAddress thisAddress = (ContactAddress)iaddress.next();
%>
    <tr class="containerBody">
      <td nowrap class="formLabel">
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
      <td colspan="2">
        <font color="#9E9E9E">No addresses entered.</font>
      </td>
    </tr>
<%}%>
</dhv:evaluate>
</table>
&nbsp;
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr class="title">
    <th colspan="2">
	    <strong>Email Addresses</strong>
	  </th>
  </tr>
<dhv:evaluate exp="<%=(OrgDetails.getPrimaryContact() == null)%>">
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
      <td>
        <a href="mailto:<%= toHtml(thisEmailAddress.getEmail()) %>"><%= toHtml(thisEmailAddress.getEmail()) %></a>
      </td>
    </tr>
<%    
    }
  } else {
%>
    <tr class="containerBody">
      <td colspan="2">
        <font color="#9E9E9E">No email addresses entered.</font>
      </td>
    </tr>
<%}%>
</dhv:evaluate>
<dhv:evaluate exp="<%= (OrgDetails.getPrimaryContact() != null) %>">
<%
  Iterator iemail = OrgDetails.getPrimaryContact().getEmailAddressList().iterator();
  if (iemail.hasNext()) {
    while (iemail.hasNext()) {
      ContactEmailAddress thisEmailAddress = (ContactEmailAddress)iemail.next();
%>    
    <tr class="containerBody">
      <td nowrap class="formLabel">
        <%= toHtml(thisEmailAddress.getTypeName()) %>
      </td>
      <td>
        <a href="mailto:<%= toHtml(thisEmailAddress.getEmail()) %>"><%= toHtml(thisEmailAddress.getEmail()) %></a>
      </td>
    </tr>
<%    
    }
  } else {
%>
    <tr class="containerBody">
      <td colspan="2">
        <font color="#9E9E9E">No email addresses entered.</font>
      </td>
    </tr>
<%}%>
</dhv:evaluate>
</table>
&nbsp;
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="2">
	    <strong>Additional Details</strong>
	  </th>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      Notes
    </td>
    <td>
      <%=toHtml(OrgDetails.getNotes()) %>&nbsp;
    </td>
  </tr>
</table>
<dhv:permission name="accounts-accounts-edit,accounts-accounts-delete"><br></dhv:permission>
<dhv:evaluate exp="<%=(OrgDetails.getEnabled())%>">
  <dhv:permission name="accounts-accounts-edit"><input type="button" value="Modify"	onClick="javascript:window.location.href='Accounts.do?command=Modify&orgId=<%= OrgDetails.getOrgId() %>';"></dhv:permission>
</dhv:evaluate>
<dhv:evaluate exp="<%=!(OrgDetails.getEnabled())%>">
  <dhv:permission name="accounts-accounts-edit">
    <input type="button" value="Enable" 	onClick="javascript:window.location.href='Accounts.do?command=Enable&orgId=<%= OrgDetails.getOrgId() %>';">
  </dhv:permission>
</dhv:evaluate>
<dhv:permission name="accounts-accounts-delete"><input type="button" value="Delete Account" onClick="javascript:popURLReturn('Accounts.do?command=ConfirmDelete&id=<%=OrgDetails.getId()%>&popup=true','Accounts.do?command=View', 'Delete_account','320','200','yes','no');"></dhv:permission>
</td></tr>
</table>
<% if (request.getParameter("return") != null) { %>
<input type="hidden" name="return" value="<%=request.getParameter("return")%>">
<%}%>
</form>
