<%@ taglib uri="WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,com.darkhorseventures.cfsbase.*" %>
<jsp:useBean id="OpportunityDetails" class="com.darkhorseventures.cfsbase.Opportunity" scope="request"/>
<%@ include file="initPage.jsp" %>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popURL.js"></SCRIPT>
<form name="oppdet" action="Leads.do?id=<%=OpportunityDetails.getId()%>&orgId=<%= OpportunityDetails.getAccountLink() %>&contactId=<%= OpportunityDetails.getContactLink() %>" method="post">

<a href="Leads.do">Pipeline Management</a> > 
<% if (request.getParameter("return") == null) { %>
	<a href="Leads.do?command=ViewOpp">View Opportunities</a> >
<%} else {%>
	<% if (request.getParameter("return").equals("dashboard")) { %>
		<a href="Leads.do?command=Dashboard">Dashboard</a> >
	<%}%>
<%}%>
Opportunity Details<br>
<hr color="#BFBFBB" noshade>

<a href="Leads.do?command=ViewOpp">Back to Opportunities List</a><br>
<%= showError(request, "actionError") %>
<table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr class="containerHeader">
    <td width="80%">
      <strong><%= toHtml(OpportunityDetails.getDescription()) %></strong>&nbsp;
      	<dhv:evaluate exp="<%=(OpportunityDetails.getAccountEnabled() && OpportunityDetails.getAccountLink() > -1)%>">
        <dhv:permission name="accounts-view,accounts-accounts-view">[ <a href="Accounts.do?command=Details&orgId=<%=OpportunityDetails.getAccountLink()%>">Go to this Account</a> ]</dhv:permission>
	</dhv:evaluate>
	  
	<dhv:evaluate exp="<%=(OpportunityDetails.getContactLink() > -1)%>">
	<dhv:permission name="contacts-view,contacts-external_contacts-view">[ <a href="ExternalContacts.do?command=ContactDetails&id=<%=OpportunityDetails.getContactLink()%>">Go to this Contact</a> ]</dhv:permission>
	</dhv:evaluate>
    </td>
  </tr>
  <tr class="containerMenu">
    <td>
      <% String param1 = "id=" + OpportunityDetails.getId(); %>      
      <dhv:container name="opportunities" selected="details" param="<%= param1 %>" />
    </td>
  </tr>
  <tr>
    <td class="containerBack">

<input type=hidden name="command" value="<%= OpportunityDetails.getId() %>">

<dhv:permission name="pipeline-opportunities-edit"><input type="button" name="action" value="Modify" onClick="document.oppdet.command.value='ModifyOpp';document.oppdet.submit();"></dhv:permission>
<dhv:permission name="pipeline-opportunities-delete"><input type="button" name="action" value="Delete" onClick="document.oppdet.command.value='DeleteOpp';javascript:popURLReturn('Leads.do?command=ConfirmDelete&id=<%=OpportunityDetails.getId()%>','Leads.do?command=ViewOpp', 'Delete_opp','320','200','yes','no');"></dhv:permission>
<dhv:permission name="pipeline-opportunities-delete,pipeline-opportunities-edit"><br>&nbsp;</dhv:permission>

<table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr bgcolor="#DEE0FA">
    <td colspan=2 valign=center align=left>
      <strong>Primary Information</strong>
    </td>
  </tr>
  
  <tr class="containerBody">
    <td nowrap class="formLabel">
      Organization
    </td>
    <td valign=center width=100%>
      <%= OpportunityDetails.getAccountName() %><dhv:evaluate exp="<%= (!(OpportunityDetails.getAccountEnabled()) && OpportunityDetails.getAccountLink() > 0)%>">&nbsp;<font color="red">(account disabled)</font></dhv:evaluate>
    </td>
  </tr>
    
  <tr class="containerBody">
    <td nowrap class="formLabel">
      Owner
    </td>
    <td valign=center width=100%>
      <%= OpportunityDetails.getOwnerName() %>
      <dhv:evaluate exp="<%=!(OpportunityDetails.getHasEnabledOwnerAccount())%>"><font color="red">*</font></dhv:evaluate>
    </td>
  </tr>
  
  <dhv:evaluate exp="<%= hasText(OpportunityDetails.getNotes()) %>">
  <tr class="containerBody">
    <td valign="top" nowrap class="formLabel">Additional Notes</td>
    <td valign="top"><%= toHtml(OpportunityDetails.getNotes()) %></td>
  </tr>
  </dhv:evaluate>

  <tr class="containerBody">
    <td nowrap class="formLabel">
      Prob. of Close
    </td>
    <td width="100%">
      <%= OpportunityDetails.getCloseProbValue() %>%
    </td>
  </tr>

  <tr class="containerBody">
    <td nowrap class="formLabel">
      Est. Close Date
    </td>
    <td>
      <%= OpportunityDetails.getCloseDateString() %>&nbsp;
    </td>
  </tr>
  
  <tr class="containerBody">
    <td nowrap class="formLabel">
      Low Estimate
    </td>
    <td>
      $<%= OpportunityDetails.getLowCurrency() %>&nbsp;
    </td>
  </tr>
  
  <tr class="containerBody">
    <td nowrap class="formLabel">
      Best Guess
    </td>
    <td>
      $<%= OpportunityDetails.getGuessCurrency() %>&nbsp;
    </td>
  </tr>
  
  <tr class="containerBody">
    <td nowrap class="formLabel">
      High Estimate
    </td>
    <td>
      $<%= OpportunityDetails.getHighCurrency() %>&nbsp;
    </td>
  </tr>
  
  <tr class="containerBody">
    <td nowrap class="formLabel">
        Est. Term (months)
    </td>
    <td>
      <%= OpportunityDetails.getTerms() %>
    </td>
  </tr>  
  
  <tr class="containerBody">
    <td nowrap class="formLabel">
      Current Stage
    </td>
    <td>
      <%= toHtml(OpportunityDetails.getStageName()) %>&nbsp;
    </td>
  </tr>
  
  <tr class="containerBody">
    <td nowrap class="formLabel">
      Current Stage Date
    </td>
    <td>
      <%= toHtml(OpportunityDetails.getStageDateString()) %>&nbsp;
    </td>
  </tr>
  
  <tr class="containerBody">
    <td nowrap class="formLabel">
      Est. Commission
    </td>
    <td>
      <%= OpportunityDetails.getCommission() %>%
    </td>
  </tr>
  
  <dhv:evaluate exp="<%= hasText(OpportunityDetails.getAlertText()) %>">
   <tr class="containerBody">
    <td nowrap class="formLabel">
      Alert Description
    </td>
    <td valign=center colspan=1>
       <%= toHtml(OpportunityDetails.getAlertText()) %>
    </td>
  </tr>
</dhv:evaluate>

<dhv:evaluate exp="<%= (OpportunityDetails.getAlertDate() != null) %>">
   <tr class="containerBody">
    <td nowrap class="formLabel">
      Alert Date
    </td>
    <td valign=center colspan=1>
       <%= OpportunityDetails.getAlertDateStringLongYear() %>
    </td>
  </tr>
</dhv:evaluate>
  
    <tr class="containerBody">
    <td nowrap class="formLabel">
      Entered
    </td>
    <td>
      <%= OpportunityDetails.getEnteredByName() %>&nbsp;-&nbsp;<%= OpportunityDetails.getEnteredString() %>
    </td>
  </tr>
  
      <tr class="containerBody">
    <td nowrap class="formLabel">
      Modified
    </td>
    <td>
      <%= OpportunityDetails.getModifiedByName() %>&nbsp;-&nbsp;<%= OpportunityDetails.getModifiedString() %>
    </td>
  </tr>
  
</table>
<dhv:permission name="pipeline-opportunities-delete,pipeline-opportunities-edit"><br></dhv:permission>
<dhv:permission name="pipeline-opportunities-edit"><input type="button" name="action" value="Modify" onClick="document.oppdet.command.value='ModifyOpp';document.oppdet.submit()"></dhv:permission>
<dhv:permission name="pipeline-opportunities-delete"><input type="button" name="action" value="Delete" onClick="document.oppdet.command.value='DeleteOpp';javascript:popURLReturn('Leads.do?command=ConfirmDelete&id=<%=OpportunityDetails.getId()%>','Leads.do?command=ViewOpp', 'Delete_opp','320','200','yes','no');"></dhv:permission>
</td></tr>
</table>
<% if (request.getParameter("return") != null) { %>
<input type="hidden" name="return" value="<%=request.getParameter("return")%>">
<%}%>
</form>
