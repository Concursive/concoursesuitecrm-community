<%@ taglib uri="WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,com.darkhorseventures.cfsbase.*" %>
<jsp:useBean id="OrgDetails" class="com.darkhorseventures.cfsbase.Organization" scope="request"/>
<jsp:useBean id="OpportunityList" class="com.darkhorseventures.cfsbase.OpportunityList" scope="request"/>
<jsp:useBean id="OpportunityPagedInfo" class="com.darkhorseventures.webutils.PagedListInfo" scope="session"/>
<%@ include file="initPage.jsp" %>
<script language="JavaScript" TYPE="text/javascript" SRC="/javascript/confirmDelete.js"></script>
<a href="/Accounts.do">Account Management</a> > 
<a href="/Accounts.do?command=View">View Accounts</a> >
<a href="/Accounts.do?command=Details&orgId=<%=OrgDetails.getOrgId()%>">Account Details</a> >
Opportunities<br>
<hr color="#BFBFBB" noshade>
<table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr class="containerHeader">
    <td>
      <strong><%= toHtml(OrgDetails.getName()) %></strong>
    </td>
  </tr>
  <tr class="containerMenu">
    <td>
      <% String param1 = "orgId=" + OrgDetails.getOrgId(); %>      
      <dhv:container name="accounts" selected="opportunities" param="<%= param1 %>" />
    </td>
  </tr>
  <tr>
    <td class="containerBack">
<dhv:permission name="accounts-accounts-opportunities-add"><a href="/Opportunities.do?command=Add&orgId=<%=request.getParameter("orgId")%>">Add an Opportunity</a></dhv:permission>
<center><%= OpportunityPagedInfo.getAlphabeticalPageLinks() %></center>
<dhv:pagedListStatus title="<%= showAttribute(request, "actionError") %>" object="OpportunityPagedInfo"/>
<table cellpadding="4" cellspacing="0" border="1" width="100%" class="pagedlist" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr class="title">
    <dhv:permission name="accounts-accounts-opportunities-edit,accounts-accounts-opportunities-delete">
    <td valign=center align=left>
      <strong>Action</strong>
    </td>
    </dhv:permission>
    <td valign=center align=left>
	<strong><a href="/Opportunities.do?command=View&orgId=<%= OrgDetails.getOrgId() %>&column=description">Description</a></strong>
	<%= OpportunityPagedInfo.getSortIcon("description") %>
    </td>
    <td valign=center align=left>
	<strong><a href="/Opportunities.do?command=View&orgId=<%= OrgDetails.getOrgId() %>&column=guessvalue">Guess Amount</a></strong>
	<%= OpportunityPagedInfo.getSortIcon("guessvalue") %>
    </td>
    <td valign=center align=left>
      	<strong><a href="/Opportunities.do?command=View&orgId=<%= OrgDetails.getOrgId() %>&column=closedate">Close Date</a></strong>
	<%= OpportunityPagedInfo.getSortIcon("closedate") %>
    </td>
    <td valign=center align=left>
      	<strong><a href="/Opportunities.do?command=View&orgId=<%= OrgDetails.getOrgId() %>&column=stage">Current Stage</a></strong>
	<%= OpportunityPagedInfo.getSortIcon("stage") %>
    </td>  
  </tr>

<%
	Iterator j = OpportunityList.iterator();
	
	if ( j.hasNext() ) {
		int rowid = 0;
	    while (j.hasNext()) {
		
        if (rowid != 1) {
          rowid = 1;
        } else {
          rowid = 2;
        }
		
		Opportunity thisOpp = (Opportunity)j.next();
%>      
  <tr class="containerBody">
    <dhv:permission name="accounts-accounts-opportunities-edit,accounts-accounts-opportunities-delete">
    <td width=8 valign=center nowrap class="row<%= rowid %>">
          <dhv:permission name="accounts-accounts-opportunities-edit"><a href="/Opportunities.do?command=Modify&id=<%= thisOpp.getId() %>&orgId=<%= thisOpp.getAccountLink() %>&contactId=<%=thisOpp.getContactLink() %>&return=list">Edit</a></dhv:permission><dhv:permission name="accounts-accounts-opportunities-edit,accounts-accounts-opportunities-delete" all="true">|</dhv:permission><dhv:permission name="accounts-accounts-opportunities-delete"><a href="javascript:confirmDelete('/Opportunities.do?command=Delete&id=<%= thisOpp.getId()%>&orgId=<%= thisOpp.getAccountLink() %>&contactId=<%= thisOpp.getContactLink() %>');">Del</a></dhv:permission>
    </td>
    </dhv:permission>
    <td width=100% valign=center class="row<%= rowid %>">
      <a href="/Opportunities.do?command=Details&id=<%=thisOpp.getId()%>&orgId=<%=OrgDetails.getOrgId()%>">
      <%= toHtml(thisOpp.getDescription()) %></a>
    </td>
    <td width=125 valign=center nowrap class="row<%= rowid %>">
      $<%= thisOpp.getGuessCurrency() %>
    </td>
    <td width=125 valign=center nowrap class="row<%= rowid %>">
      <%= toHtml(thisOpp.getCloseDateString()) %>
    </td>
    <td width=125 valign=center nowrap class="row<%= rowid %>">
      <%= toHtml(thisOpp.getStageName()) %>
    </td>		
  </tr>
<%}%>
<%} else {%>
  <tr class="containerBody">
    <td colspan=5 valign=center>
      No opportunities found.
    </td>
  </tr>
<%}%>
</table>
<br>
<dhv:pagedListControl object="OpportunityPagedInfo"/>
</td>
</tr>
</table>

