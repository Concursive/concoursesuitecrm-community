<%@ page import="java.util.*,com.darkhorseventures.cfsbase.*" %>
<jsp:useBean id="OpportunityDetails" class="com.darkhorseventures.cfsbase.Opportunity" scope="request"/>
<jsp:useBean id="CallDetails" class="com.darkhorseventures.cfsbase.Call" scope="request"/>
<%@ include file="initPage.jsp" %>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="/javascript/confirmDelete.js"></SCRIPT>
<form name="addCall" action="/LeadsCalls.do?id=<%= CallDetails.getId() %>&oppId=<%= OpportunityDetails.getId() %>" method="post">
<a href="/Leads.do?command=ViewOpp">Back to Opportunities List</a><br>&nbsp;
<table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr class="containerHeader">
    <td>
      <strong><%= toHtml(OpportunityDetails.getDescription()) %></strong>&nbsp;&nbsp;
      
      	<% 
	  if (OpportunityDetails.getAccountLink() != -1) { %>
	  	[ <a href="/Accounts.do?command=Details&orgId=<%=OpportunityDetails.getAccountLink()%>">Go to this Account</a> ]
	  <%} else { %>
	  	[ <a href="/ExternalContacts.do?command=ContactDetails&id=<%=OpportunityDetails.getContactLink()%>">Go to this Contact</a> ]
	  <%}%>
    </td>
  </tr>
  <tr class="containerMenu">
    <td>
      <a href="/Leads.do?command=DetailsOpp&id=<%= OpportunityDetails.getId() %>"><font color="#000000">Details</font></a> | 
      <a href="/LeadsCalls.do?command=View&oppId=<%= OpportunityDetails.getId() %>"><font color="#0000FF">Calls</font></a> |
      <a href=""><font color="#000000">Documents</font></a>
    </td>
  </tr>
  <tr>
    <td class="containerBack">
<input type="submit" name="command" value="Modify">
<input type="submit" name="command" value="Delete" onClick="javascript:return confirmAction()">
<br>&nbsp;
<table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr class="title">
    <td colspan=2 valign=center align=left>
      <strong>Call Details</strong>
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
      Alert Date
    </td>
    <td>
      <%= toHtml(CallDetails.getAlertDateString()) %>
    </td>
  </tr>
  
  <tr class="containerBody">
    <td align=center colspan=2>
      <i>Created <%= toHtml(CallDetails.getEnteredString()) %> by <%= toHtml(CallDetails.getEnteredName()) %><br>
      Modifed <%= toHtml(CallDetails.getModifiedString()) %> by <%= toHtml(CallDetails.getModifiedName()) %></i>
    </td>
  </tr>
</table>
<br>
<input type="submit" name="command" value="Modify">
<input type="submit" name="command" value="Delete" onClick="javascript:return confirmAction()">
</td>
</tr>
</table>
