<%@ page import="java.util.*,com.darkhorseventures.cfsbase.*" %>
<jsp:useBean id="ContactDetails" class="com.darkhorseventures.cfsbase.Contact" scope="request"/>
<jsp:useBean id="OpportunityList" class="com.darkhorseventures.cfsbase.OpportunityList" scope="request"/>
<jsp:useBean id="OpportunityPagedListInfo" class="com.darkhorseventures.webutils.PagedListInfo" scope="session"/>
<%@ include file="initPage.jsp" %>
<script language="JavaScript" TYPE="text/javascript" SRC="/javascript/confirmDelete.js"></script>
<a href="ExternalContacts.do?command=ListContacts">Back to Contact List</a><br>&nbsp;
<table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr class="containerHeader">
    <td>
      <strong><%= toHtml(ContactDetails.getNameFull()) %></strong>
    </td>
  </tr>
  <tr class="containerMenu">
    <td>
      <a href="/ExternalContacts.do?command=ContactDetails&id=<%= ContactDetails.getId() %>"><font color="#000000">Details</font></a> | 
            <a href="/ExternalContacts.do?command=Fields&contactId=<%= ContactDetails.getId() %>"><font color="#000000">Folders</font></a> | 
      <a href="/ExternalContactsCalls.do?command=View&contactId=<%= ContactDetails.getId() %>"><font color="#000000">Calls</font></a> |
      <a href="/ExternalContacts.do?command=ViewMessages&contactId=<%= ContactDetails.getId() %>"><font color="#000000">Messages</font></a> |
      <a href="/ExternalContactsOpps.do?command=ViewOpps&contactId=<%= ContactDetails.getId() %>"><font color="#0000FF">Opportunities</font></a> 
    </td>
  </tr>
  <tr>
    <td class="containerBack">
<a href="/ExternalContactsOpps.do?command=AddOpp&contactId=<%= ContactDetails.getId() %>">Add an Opportunity</a>
<center><%= OpportunityPagedListInfo.getAlphabeticalPageLinks() %></center><br>
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
        <a href="/ExternalContactsOpps.do?command=ModifyOpp&id=<%= thisOpp.getId() %>&orgId=<%= thisOpp.getAccountLink() %>&contactId=<%= thisOpp.getContactLink() %>">Edit</a>|<a href="javascript:confirmDelete('/ExternalContactsOpps.do?command=DeleteOpp&id=<%= thisOpp.getId() %>&orgId=<%= thisOpp.getAccountLink() %>&contactId=<%= thisOpp.getContactLink() %>');">Del</a>
      </td>
      <td width=40% valign=center class="row<%= rowid %>">
        <a href="/ExternalContactsOpps.do?command=DetailsOpp&id=<%=thisOpp.getId()%>&contactId=<%= ContactDetails.getId() %>">
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
<%
    }
	} else {
%>
    <tr class="containerBody">
      <td colspan=5 valign=center>
        No opportunities found.
      </td>
    </tr>
<%}%>
</table>
<br>
[<%= OpportunityPagedListInfo.getPreviousPageLink("<font class='underline'>Previous</font>", "Previous") %> <%= OpportunityPagedListInfo.getNextPageLink("<font class='underline'>Next</font>", "Next") %>]
</td>
</tr>
</table>

