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
      <a href="Accounts.do?command=Details&orgId=<%=OrgDetails.getOrgId()%>"><font color="#000000">Details</font></a> | 
      <a href="/Accounts.do?command=Fields&orgId=<%= OrgDetails.getOrgId() %>"><font color="#000000">Folders</font></a> |
      <font color="#787878">Activities</font> | 
      <a href="Contacts.do?command=View&orgId=<%=OrgDetails.getOrgId()%>"><font color="#000000">Contacts</font></a> | 
      <a href="Opportunities.do?command=View&orgId=<%=OrgDetails.getOrgId()%>"><font color="#0000FF">Opportunities</font></a> | 
      <a href="Accounts.do?command=ViewTickets&orgId=<%= OrgDetails.getOrgId() %>"><font color="#000000">Tickets</font></a> |
      <a href="AccountsDocuments.do?command=View&orgId=<%=OrgDetails.getOrgId()%>"><font color="#000000">Documents</font></a>
    </td>
  </tr>
  <tr>
    <td class="containerBack">
<a href="/Opportunities.do?command=Add&orgId=<%=request.getParameter("orgId")%>">Add an Opportunity</a>
<center><%= OpportunityPagedInfo.getAlphabeticalPageLinks() %></center>
<%= showAttribute(request, "actionError") %>
<table cellpadding="4" cellspacing="0" border="1" width="100%" class="pagedlist" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr class="title">
    <td valign=center align=left>
      <strong>Action</strong>
    </td>
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
    <td width=8 valign=center nowrap class="row<%= rowid %>">
      <a href="/Opportunities.do?command=Modify&id=<%= thisOpp.getId() %>&orgId=<%= thisOpp.getAccountLink() %>&contactId=<%= thisOpp.getContactLink() %>">Edit</a>|<a href="javascript:confirmDelete('/Opportunities.do?command=Delete&id=<%= thisOpp.getId() %>&orgId=<%= thisOpp.getAccountLink() %>&contactId=<%= thisOpp.getContactLink() %>');">Del</a>
    </td>
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
[<%= OpportunityPagedInfo.getPreviousPageLink("<font class='underline'>Previous</font>", "Previous") %> <%= OpportunityPagedInfo.getNextPageLink("<font class='underline'>Next</font>", "Next") %>] <%= OpportunityPagedInfo.getNumericalPageLinks() %>
</td>
</tr>
</table>

