<%@ taglib uri="WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,com.darkhorseventures.cfsbase.*" %>
<jsp:useBean id="OrgDetails" class="com.darkhorseventures.cfsbase.Organization" scope="request"/>
<jsp:useBean id="OpportunityList" class="com.darkhorseventures.cfsbase.OpportunityList" scope="request"/>
<jsp:useBean id="OpportunityPagedInfo" class="com.darkhorseventures.webutils.PagedListInfo" scope="session"/>
<%@ include file="initPage.jsp" %>
<script language="JavaScript" TYPE="text/javascript" SRC="/javascript/confirmDelete.js"></script>
<a href="Accounts.do?command=View">Back to Account List</a><br>&nbsp;
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
<%= showAttribute(request, "actionError") %>
<table cellpadding="4" cellspacing="0" border="1" width="100%" class="pagedlist" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr class="title">
    <dhv:permission name="accounts-accounts-opportunities-edit,accounts-accounts-opportunities-delete">
    <td valign=center align=left>
      <strong>Action</strong>
    </td>
    </dhv:permission>
    <td valign=center align=left>
      <strong>Opportunity Name</strong>
    </td>
    <td valign=center align=left>
      <strong>Best Guess Amount</strong>
    </td>
    <td valign=center align=left>
      <strong>Close Date</strong>
    </td>
    <td valign=center align=left>
      <strong>Current Stage</strong>
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
          <dhv:permission name="accounts-accounts-opportunities-edit"><a href="/Opportunities.do?command=Modify&id=<%= thisOpp.getId() %>&orgId=<%= thisOpp.getAccountLink() %>&contactId=<%=thisOpp.getContactLink() %>">Edit</a></dhv:permission><dhv:permission name="accounts-accounts-opportunities-edit,accounts-accounts-opportunities-delete" all="true">|</dhv:permission><dhv:permission name="accounts-accounts-opportunities-delete"><a href="javascript:confirmDelete('/Opportunities.do?command=Delete&id=<%= thisOpp.getId()%>&orgId=<%= thisOpp.getAccountLink() %>&contactId=<%= thisOpp.getContactLink() %>');">Del</a></dhv:permission>
    </td>
    </dhv:permission>
    <td width=40% valign=center class="row<%= rowid %>">
      <a href="/Opportunities.do?command=Details&id=<%=thisOpp.getId()%>&orgId=<%=OrgDetails.getOrgId()%>">
      <%= toHtml(thisOpp.getDescription()) %></a>
    </td>
    <td width=20% valign=center nowrap class="row<%= rowid %>">
      $<%= thisOpp.getGuessCurrency() %>
    </td>
    <td width=20% valign=center nowrap class="row<%= rowid %>">
      <%= toHtml(thisOpp.getCloseDateString()) %>
    </td>
    <td width=20% valign=center class="row<%= rowid %>">
      <%= thisOpp.getStageName() %>
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

