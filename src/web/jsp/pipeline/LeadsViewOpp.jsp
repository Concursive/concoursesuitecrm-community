<%@ page import="java.util.*,com.darkhorseventures.cfsbase.*" %>
<jsp:useBean id="OpportunityList" class="com.darkhorseventures.cfsbase.OpportunityList" scope="request"/>
<jsp:useBean id="OpportunityListInfo" class="com.darkhorseventures.webutils.PagedListInfo" scope="session"/>
<%@ include file="initPage.jsp" %>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="/javascript/confirmDelete.js"></SCRIPT>
<form name="listView" method="post" action="/Leads.do?command=ViewOpp"><br>
<center><%= OpportunityListInfo.getAlphabeticalPageLinks() %></center>

<table width="100%" border="0">
  <tr>
    <td align="left">
      <select size="1" name="listView" onChange="javascript:document.forms[0].submit();">
        <option <%= OpportunityListInfo.getOptionValue("my") %>>My Opportunities</option>
        <option <%= OpportunityListInfo.getOptionValue("all") %>>All Opportunities</option>
      </select>
      <%= showAttribute(request, "actionError") %>
    </td>
  </tr>
</table>

<table cellpadding="4" cellspacing="0" border="1" width="100%" class="pagedlist" bordercolorlight="#000000" bordercolor="#FFFFFF">

  <tr bgcolor="#DEE0FA">
    <td valign=center>
      <strong>Action</strong>
    </td>
    
    <td valign=center>
      <strong><a href="/Leads.do?command=ViewOpp&column=description">Opportunity</a></strong>
    </td>
    
    <td valign=center>
      <strong><a href="/Leads.do?command=ViewOpp&column=acct_name">Organization</a></strong>
    </td>
    
    <td valign=center>
      <strong><a href="/Leads.do?command=ViewOpp&column=guessvalue">Amount</a></strong>
    </td>
    
    <td valign=center nowrap>
      <strong><a href="/Leads.do?command=ViewOpp&column=closeprob">Close Prob.</a></strong>
    </td>
    
    <td valign=center nowrap>
      <strong><a href="/Leads.do?command=ViewOpp&column=closedate">Revenue Start</a></strong>
    </td>
    
    <td valign=center>
      <strong>Term</strong>
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
	
		<tr bgcolor="white">
      <td width=8 valign=center nowrap class="row<%= rowid %>">
        <a href="/Leads.do?command=ModifyOpp&id=<%= thisOpp.getId() %>&orgId=<%= thisOpp.getAccountLink() %>&contactId=<%= thisOpp.getContactLink() %>&return=list">Edit</a>|<a href="javascript:confirmDelete('/Leads.do?command=DeleteOpp&id=<%= thisOpp.getId() %>&orgId=<%= thisOpp.getAccountLink() %>&contactId=<%= thisOpp.getContactLink() %>');">Del</a>
      </td>
      
      <td width=35% valign=center class="row<%= rowid %>">
        <a href="/Leads.do?command=DetailsOpp&id=<%=thisOpp.getId()%>">
        <%= toHtml(thisOpp.getDescription()) %></a>
      </td>
      
      <td width=30% valign=center class="row<%= rowid %>">
<%
      if (thisOpp.getAccountLink() > -1) {
%>        
        <a href="/Opportunities.do?command=View&orgId=<%= thisOpp.getAccountLink() %>">
<%
      }
%>        
        <%= toHtml(thisOpp.getAccountName()) %>
<%
      if (thisOpp.getAccountLink() > -1) {
%>     
        </a>
<%
      }
%>        
      </td>
      
      <td width=15% valign=center nowrap class="row<%= rowid %>">
        $<%= thisOpp.getGuessCurrency() %>
      </td>
      
      <td width=10% class="row<%= rowid %>">
        <%= thisOpp.getCloseProbPercent() %>
      </td>
      
      <td width=10% valign=center nowrap class="row<%= rowid %>">
        <%= toHtml(thisOpp.getCloseDate()) %>
      </td>
      
      <td width=8 class="row<%= rowid %>">
        <%= thisOpp.getTermsString() %>
      </td>
    </tr>
<%}%>
	
</table>
<br>
[<%= OpportunityListInfo.getPreviousPageLink("<font class='underline'>Previous</font>", "Previous") %> <%= OpportunityListInfo.getNextPageLink("<font class='underline'>Next</font>", "Next") %>] <%= OpportunityListInfo.getNumericalPageLinks() %>
<%} else {%>
  <tr bgcolor="white"><td colspan=7 valign=center>No opportunities found.</td></tr>
</table>
	<%}%>
</form>

