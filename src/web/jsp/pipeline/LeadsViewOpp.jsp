<%@ taglib uri="WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,com.darkhorseventures.cfsbase.*,com.zeroio.iteam.base.*" %>
<jsp:useBean id="OpportunityList" class="com.darkhorseventures.cfsbase.OpportunityList" scope="request"/>
<jsp:useBean id="OpportunityListInfo" class="com.darkhorseventures.webutils.PagedListInfo" scope="session"/>
<jsp:useBean id="TypeSelect" class="com.darkhorseventures.webutils.LookupList" scope="request"/>
<%@ include file="initPage.jsp" %>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popURL.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/confirmDelete.js"></SCRIPT>
<a href="Leads.do">Pipeline Management</a> > 
View Opportunities<br>
<hr color="#BFBFBB" noshade>
<center><%= OpportunityListInfo.getAlphabeticalPageLinks() %></center>
<table width="100%" border="0">
  <tr>
    <form name="listView" method="post" action="Leads.do?command=ViewOpp">
    <td align="left">
      <select size="1" name="listView" onChange="javascript:document.forms[0].submit();">
        <option <%= OpportunityListInfo.getOptionValue("my") %>>My Opportunities</option>
        <option <%= OpportunityListInfo.getOptionValue("all") %>>All Opportunities</option>
	      <dhv:evaluate if="<%= (!OpportunityListInfo.getSavedCriteria().isEmpty()) %>">
          <option <%= OpportunityListInfo.getOptionValue("search") %>>Search Results</option>
        </dhv:evaluate>
      </select>
			<% TypeSelect.setJsEvent("onChange=\"javascript:document.forms[0].submit();\""); %>
      <%=TypeSelect.getHtmlSelect("listFilter1", OpportunityListInfo.getFilterKey("listFilter1"))%>
      
      
    </td>
    <td>
      <dhv:pagedListStatus title="<%= showError(request, "actionError") %>" object="OpportunityListInfo"/>
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
      <strong><a href="Leads.do?command=ViewOpp&column=acct_name">Organization</a></strong>
      <%= OpportunityListInfo.getSortIcon("acct_name") %>
    </td>    
    
    <td valign=center nowrap>
      <strong><a href="Leads.do?command=ViewOpp&column=x.description">Component</a></strong>
      <%= OpportunityListInfo.getSortIcon("x.description") %>
    </td>
    
    <td valign=center nowrap>
      <strong><a href="Leads.do?command=ViewOpp&column=guessvalue">Amount</a></strong>
      <%= OpportunityListInfo.getSortIcon("guessvalue") %>
    </td>
    
    <td valign=center nowrap>
      <strong><a href="Leads.do?command=ViewOpp&column=closeprob">Prob.</a></strong>
      <%= OpportunityListInfo.getSortIcon("closeprob") %>
    </td>
    
    <td valign=center nowrap>
      <strong><a href="Leads.do?command=ViewOpp&column=closedate">Start</a></strong>
      <%= OpportunityListInfo.getSortIcon("closedate") %>
    </td>
    
    <td valign=center nowrap>
      <strong><a href="Leads.do?command=ViewOpp&column=terms">Term</a></strong>
      <%= OpportunityListInfo.getSortIcon("terms") %>
    </td>
    
  </tr>

<%
	Iterator j = OpportunityList.iterator();
  FileItem thisFile = new FileItem();
	
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
	<dhv:permission name="pipeline-opportunities-edit"><a href="LeadsComponents.do?command=ModifyComponent&id=<%= thisOpp.getComponentId() %>&return=list">Edit</a></dhv:permission><dhv:permission name="pipeline-opportunities-edit,pipeline-opportunities-delete" all="true">|</dhv:permission><dhv:permission name="pipeline-opportunities-delete"><a href="javascript:popURLReturn('LeadsComponents.do?command=ConfirmComponentDelete&oppId=<%=thisOpp.getComponentId()%>&return=list','Leads.do?command=ViewOpp', 'Delete_opp','320','200','yes','no');">Del</a></dhv:permission>
	</td>
	</dhv:permission>
  
      <td width=30% valign=center class="row<%= rowid %>">

      <dhv:evaluate exp="<%=(thisOpp.getAccountEnabled() && thisOpp.getAccountLink() > -1)%>">
      <a href="Opportunities.do?command=View&orgId=<%= thisOpp.getAccountLink() %>">
      </dhv:evaluate>
     
      <%= toHtml(thisOpp.getAccountName()) %><dhv:evaluate exp="<%=!(thisOpp.getAccountEnabled())%>">&nbsp;<font color="red">*</font></dhv:evaluate>

      <dhv:evaluate exp="<%=(thisOpp.getAccountEnabled() && thisOpp.getAccountLink() > -1)%>">
      </a>
      </dhv:evaluate>
      </td>  
      
      <td width=35% valign=center class="row<%= rowid %>">
        <a href="Leads.do?command=DetailsOpp&oppId=<%=thisOpp.getOppId()%>">
        <%= toHtml(thisOpp.getComponentDescription()) %></a>
      <% if (thisOpp.hasFiles()) { %>
      <%= thisFile.getImageTag()%>
      <%}%>        
      </td>
      
      <td width=80 valign=center nowrap class="row<%= rowid %>">
        $<%= thisOpp.getGuessCurrency() %>
      </td>
      
      <td width=25 class="row<%= rowid %>">
        <%= thisOpp.getCloseProbValue() %>%
      </td>
      
      <td width=80 valign=center nowrap class="row<%= rowid %>">
        <%= toHtml(thisOpp.getCloseDateString()) %>
      </td>
      
      <td width=15 valign=center nowrap class="row<%= rowid %>">
        <%= toHtml(thisOpp.getTermsString()) %>
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

