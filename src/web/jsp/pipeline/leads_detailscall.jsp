<%@ taglib uri="WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,com.darkhorseventures.cfsbase.*,com.zeroio.iteam.base.*" %>
<jsp:useBean id="OpportunityDetails" class="com.darkhorseventures.cfsbase.Opportunity" scope="request"/>
<jsp:useBean id="CallDetails" class="com.darkhorseventures.cfsbase.Call" scope="request"/>
<%@ include file="initPage.jsp" %>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="/javascript/confirmDelete.js"></SCRIPT>
<script language="JavaScript" type="text/javascript" src="/javascript/popURL.js"></script>
<form name="addCall" action="/LeadsCalls.do?id=<%= CallDetails.getId() %>&oppId=<%= OpportunityDetails.getId() %>" method="post">

<a href="/Leads.do">Pipeline Management</a> > 
<a href="/Leads.do?command=ViewOpp">View Opportunities</a> >
<a href="/Leads.do?command=DetailsOpp&id=<%=OpportunityDetails.getId()%>">Opportunity Details</a> >
<a href="/LeadsCalls.do?command=View&oppId=<%=OpportunityDetails.getId()%>">Calls</a> >
Call Details<br>
<hr color="#BFBFBB" noshade>
<table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr class="containerHeader">
    <td>
      <strong><%= toHtml(OpportunityDetails.getDescription()) %></strong>&nbsp;
      	<dhv:evaluate exp="<%=(OpportunityDetails.getAccountEnabled() && OpportunityDetails.getAccountLink() > -1)%>">
        <dhv:permission name="accounts-view,accounts-accounts-view">[ <a href="/Accounts.do?command=Details&orgId=<%=OpportunityDetails.getAccountLink()%>">Go to this Account</a> ]</dhv:permission>
	</dhv:evaluate>
	  
	<dhv:evaluate exp="<%=(OpportunityDetails.getContactLink() > -1)%>">
	<dhv:permission name="contacts-view,contacts-external_contacts-view">[ <a href="/ExternalContacts.do?command=ContactDetails&id=<%=OpportunityDetails.getContactLink()%>">Go to this Contact</a> ]</dhv:permission>
	</dhv:evaluate>
  
        <% if (OpportunityDetails.hasFiles()) { %>
      <% FileItem thisFile = new FileItem(); %>
      <%= thisFile.getImageTag()%>
      <%}%>    
  
    </td>
  </tr>
  <tr class="containerMenu">
    <td>
      <% String param1 = "id=" + OpportunityDetails.getId(); %>      
      <dhv:container name="opportunities" selected="calls" param="<%= param1 %>" />
    </td>
  </tr>
  <tr>
    <td class="containerBack">
    
<dhv:permission name="pipeline-opportunities-calls-edit"><input type="submit" name="command" value="Modify"></dhv:permission>
<dhv:permission name="pipeline-opportunities-calls-delete"><input type="submit" name="command" value="Delete" onClick="javascript:return confirmAction()"></dhv:permission>
<dhv:permission name="pipeline-opportunities-calls-edit,pipeline-opportunities-calls-delete"><br>&nbsp;</dhv:permission>

<table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr class="title">
    <td colspan=2 valign=center align=left>
      <strong>Call Details</strong> [ <a href="javascript:popURL('/ForwardNote.do?command=ShowForm&linkRecordId=<%=CallDetails.getId()%>&linkModuleId=8&popup=true','Forward_Call','600','290','yes','yes');">Forward</a> ]
    </td>     
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      Type
    </td>
    <td width=100%>
      <%= toHtml(CallDetails.getCallType()) %>
    </td>
  </tr>
  
  <tr class="containerBody">
    <td nowrap class="formLabel">
      Contact Name
    </td>
    <td width=100%>
      <%= toHtml(CallDetails.getContactName()) %>
    </td>
  </tr>
  
  <tr class="containerBody">
    <td nowrap class="formLabel">
      Length
    </td>
    <td>
      <%= toHtml(CallDetails.getLengthText()) %>
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      Subject
    </td>
    <td>
      <%= toHtml(CallDetails.getSubject()) %>
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel" width=100%>
      Notes
    </td>
    <td>
      <%= toHtml(CallDetails.getNotes()) %>
    </td>
  </tr>
  
  <tr class="containerBody">
    <td nowrap class="formLabel" width=100%>
      Alert Description
    </td>
    <td>
      <%= toHtml(CallDetails.getAlertText()) %>
    </td>
  </tr>
  
  <tr class="containerBody">
    <td nowrap class="formLabel" width=100%>
      Alert Date
    </td>
    <td>
      <%= toHtml(CallDetails.getAlertDateString()) %>
    </td>
  </tr>
  
  <tr class="containerBody">
    <td nowrap class="formLabel">
      Entered
    </td>
    <td>
      <%= toHtml(CallDetails.getEnteredName()) %>&nbsp;-&nbsp;<%= toHtml(CallDetails.getEnteredString()) %>
    </td>
  </tr>
  
  <tr class="containerBody">
    <td nowrap class="formLabel">
      Modified
    </td>
    <td>
      <%= toHtml(CallDetails.getModifiedName()) %>&nbsp;-&nbsp;<%= toHtml(CallDetails.getModifiedString()) %>
    </td>
  </tr>
</table>
<dhv:permission name="pipeline-opportunities-calls-edit,pipeline-opportunities-calls-delete"><br></dhv:permission>
<dhv:permission name="pipeline-opportunities-calls-edit"><input type="submit" name="command" value="Modify"></dhv:permission>
<dhv:permission name="pipeline-opportunities-calls-delete"><input type="submit" name="command" value="Delete" onClick="javascript:return confirmAction()"></dhv:permission>
</td>
</tr>
</table>
