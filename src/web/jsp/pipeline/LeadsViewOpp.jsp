<%@ taglib uri="WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,com.darkhorseventures.cfsbase.*" %>
<jsp:useBean id="OpportunityList" class="com.darkhorseventures.cfsbase.OpportunityList" scope="request"/>
<jsp:useBean id="OpportunityListInfo" class="com.darkhorseventures.webutils.PagedListInfo" scope="session"/>
<%@ include file="initPage.jsp" %>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="/javascript/confirmDelete.js"></SCRIPT>
<br>
<center><%= OpportunityListInfo.getAlphabeticalPageLinks() %></center>

<table width="100%" border="0">
  <tr>
    <form name="listView" method="post" action="/Leads.do?command=ViewOpp">
    <td align="left">
      <select size="1" name="listView" onChange="javascript:document.forms[0].submit();">
        <option <%= OpportunityListInfo.getOptionValue("my") %>>My Opportunities</option>
        <option <%= OpportunityListInfo.getOptionValue("all") %>>All Opportunities</option>
      </select>
      <%= showAttribute(request, "actionError") %>
    </td>
    </form>
  </tr>
</table>

<table cellpadding="4" cellspacing="0" border="1" width="100%" class="pagedlist" bordercolorlight="#000000" bordercolor="#FFFFFF">

  <tr bgcolor="#DEE0FA">
    <dhv:permission name="pipeline-opportunities-edit,pipeline-opportunities-delete">
    <td valign=center>
      <strong>Action</strong>
    </td>
    </dhv:permission>
    
    <td valign=center nowrap>
      <strong><a href="/Leads.do?command=ViewOpp&column=x.description">Opportunity</a></strong>
      <%= OpportunityListInfo.getSortIcon("x.description") %>
    </td>
    
    <td valign=center nowrap>
      <strong><a href="/Leads.do?command=ViewOpp&column=acct_name">Organization</a></strong>
      <%= OpportunityListInfo.getSortIcon("acct_name") %>
    </td>
    
    <td valign=center nowrap>
      <strong><a href="/Leads.do?command=ViewOpp&column=guessvalue">Amount</a></strong>
      <%= OpportunityListInfo.getSortIcon("guessvalue") %>
    </td>
    
    <td valign=center nowrap>
      <strong><a href="/Leads.do?command=ViewOpp&column=closeprob">Close Prob.</a></strong>
      <%= OpportunityListInfo.getSortIcon("closeprob") %>
    </td>
    
    <td valign=center nowrap>
      <strong><a href="/Leads.do?command=ViewOpp&column=closedate">Revenue Start</a></strong>
      <%= OpportunityListInfo.getSortIcon("closedate") %>
    </td>
    
    <td valign=center nowrap>
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
	<dhv:permission name="pipeline-opportunities-edit,pipeline-opportunities-delete">
	<td width=8 valign=center nowrap class="row<%= rowid %>">
	<dhv:permission name="pipeline-opportunities-edit"><a href="/Leads.do?command=ModifyOpp&id=<%= thisOpp.getId() %>&orgId=<%= thisOpp.getAccountLink() %>&contactId=<%= thisOpp.getContactLink()%>&return=list">Edit</a></dhv:permission><dhv:permission name="pipeline-opportunities-edit,pipeline-opportunities-delete" all="true">|</dhv:permission><dhv:permission name="pipeline-opportunities-delete"><a href="javascript:confirmDelete('/Leads.do?command=DeleteOpp&id=<%= thisOpp.getId() %>&orgId=<%= thisOpp.getAccountLink() %>&contactId=<%=thisOpp.getContactLink() %>');">Del</a></dhv:permission>
	</td>
	</dhv:permission>
      
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
        <%= thisOpp.getCloseProbValue() %>%
      </td>
      
      <td width=10% valign=center nowrap class="row<%= rowid %>">
        <%= toHtml(thisOpp.getCloseDateString()) %>
      </td>
      
      <td width=8 class="row<%= rowid %>">
        <%= thisOpp.getTermsString() %>
      </td>
    </tr>
<%
    }
  } else {%>
  <tr bgcolor="white"><td colspan=7 valign=center>No opportunities found.</td></tr>
<%}%>
</table>
<br>
<dhv:pagedListControl object="OpportunityListInfo" tdClass="row1"/>

