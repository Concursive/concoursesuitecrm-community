<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,org.aspcfs.modules.accounts.base.*,org.aspcfs.modules.pipeline.base.OpportunityHeader,com.zeroio.iteam.base.*" %>
<jsp:useBean id="OrgDetails" class="org.aspcfs.modules.accounts.base.Organization" scope="request"/>
<jsp:useBean id="OpportunityList" class="org.aspcfs.modules.pipeline.base.OpportunityHeaderList" scope="request"/>
<jsp:useBean id="OpportunityPagedInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<%@ include file="../initPage.jsp" %>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="/javascript/popURL.js"></SCRIPT>
<a href="Accounts.do">Account Management</a> > 
<a href="Accounts.do?command=View">View Accounts</a> >
<a href="Accounts.do?command=Details&orgId=<%=OrgDetails.getOrgId()%>">Account Details</a> >
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
<dhv:permission name="accounts-accounts-opportunities-add"><a href="Opportunities.do?command=Add&orgId=<%=request.getParameter("orgId")%>">Add an Opportunity</a></dhv:permission>
<center><%= OpportunityPagedInfo.getAlphabeticalPageLinks() %></center>
<table width="100%" border="0">
  <tr>
    <form name="listView" method="post" action="Opportunities.do?command=View&orgId=<%=OrgDetails.getOrgId()%>">
    <td align="left">
      <select size="1" name="listView" onChange="javascript:document.forms[0].submit();">
        <option <%= OpportunityPagedInfo.getOptionValue("my") %>>My Open Opportunities </option>
        <option <%= OpportunityPagedInfo.getOptionValue("all") %>>All Open Opportunities</option>
        <option <%= OpportunityPagedInfo.getOptionValue("closed") %>>All Closed Opportunities</option>
      </select>
    </td>
    <td>
      <dhv:pagedListStatus title="<%= showError(request, "actionError") %>" object="OpportunityPagedInfo"/>
    </td>
    </form>
  </tr>
</table>

<table cellpadding="4" cellspacing="0" border="1" width="100%" class="pagedlist" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr class="title">
    <dhv:permission name="accounts-accounts-opportunities-edit,accounts-accounts-opportunities-delete">
    <td valign=center align=left>
      <strong>Action</strong>
    </td>
    </dhv:permission>
    <td valign=center align=left>
      <strong><a href="Opportunities.do?command=View&orgId=<%=OrgDetails.getId()%>&column=description">Opportunity Name</a></strong>
      <%= OpportunityPagedInfo.getSortIcon("description") %>
    </td>
    
    <td valign=center align=left>
      <strong>Best Guess Total</strong>
    </td>
    
    <td valign=center align=left>
      <strong><a href="Opportunities.do?command=View&orgId=<%=OrgDetails.getId()%>&column=modified">Last Modified</a></strong>
      <%= OpportunityPagedInfo.getSortIcon("modified") %>
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
		
		OpportunityHeader thisOpp = (OpportunityHeader)j.next();
%>      
  <tr class="containerBody">
    <dhv:permission name="accounts-accounts-opportunities-edit,accounts-accounts-opportunities-delete">
    <td width=8 valign=center nowrap class="row<%= rowid %>">
          <dhv:permission name="accounts-accounts-opportunities-edit"><a href="Opportunities.do?command=Modify&oppId=<%= thisOpp.getId() %>&orgId=<%= thisOpp.getAccountLink() %>&return=list">Edit</a></dhv:permission><dhv:permission name="accounts-accounts-opportunities-edit,accounts-accounts-opportunities-delete" all="true">|</dhv:permission><dhv:permission name="accounts-accounts-opportunities-delete"><a href="javascript:popURLReturn('Opportunities.do?command=ConfirmDelete&orgId=<%=OrgDetails.getId()%>&id=<%=thisOpp.getId()%>','Opportunities.do?command=View&orgId=<%=OrgDetails.getId()%>&popup=true', 'Delete_opp','320','200','yes','no');">Del</a></dhv:permission>
    </td>
    </dhv:permission>
      <td width=40% valign=center class="row<%= rowid %>">
        <a href="Opportunities.do?command=Details&oppId=<%=thisOpp.getOppId()%>&orgId=<%= OrgDetails.getId() %>&reset=true">
        <%= toHtml(thisOpp.getDescription()) %></a>
        (<%=thisOpp.getComponentCount()%>)
        <% if (thisOpp.hasFiles()) {%>
        <%= thisFile.getImageTag() %>
        <%}%>        
      </td>  
      <td width="115" valign=center class="row<%= rowid %>">
        $<%= toHtml(thisOpp.getTotalValueCurrency()) %>
      </td>      
      <td valign=center class="row<%= rowid %>">
        <%= toHtml(thisOpp.getModifiedString()) %>
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

