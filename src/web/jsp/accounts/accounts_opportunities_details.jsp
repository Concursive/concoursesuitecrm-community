<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,org.aspcfs.modules.accounts.base.*,org.aspcfs.modules.pipeline.base.OpportunityComponent,com.zeroio.iteam.base.*" %>
<jsp:useBean id="OrgDetails" class="org.aspcfs.modules.accounts.base.Organization" scope="request"/>
<jsp:useBean id="HeaderDetails" class="org.aspcfs.modules.pipeline.base.OpportunityHeader" scope="request"/>
<jsp:useBean id="ComponentList" class="org.aspcfs.modules.pipeline.base.OpportunityComponentList" scope="request"/>
<jsp:useBean id="AccountsComponentListInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<%@ include file="../initPage.jsp" %>
<script language="JavaScript" TYPE="text/javascript" SRC="/javascript/popURL.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="/javascript/confirmDelete.js"></script>
<a href="Accounts.do">Account Management</a> > 
<a href="Accounts.do?command=View">View Accounts</a> >
<a href="Accounts.do?command=Details&orgId=<%=OrgDetails.getOrgId()%>">Account Details</a> >
<a href="Opportunities.do?command=View&orgId=<%=OrgDetails.getOrgId()%>">Opportunities</a> >
Opportunity Details<br>
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
<form name="oppdet" action="Opportunities.do?command=ModifyOpp&id=<%=HeaderDetails.getId()%>&orgId=<%= HeaderDetails.getAccountLink() %>&contactId=<%= HeaderDetails.getContactLink() %>" method="post">
<dhv:permission name="accounts-accounts-opportunities-edit"><input type="button" value="Modify" onClick="javascript:this.form.action='Opportunities.do?command=Modify&oppId=<%=HeaderDetails.getId()%>&orgId=<%= HeaderDetails.getAccountLink() %>';submit();"></dhv:permission>
<dhv:permission name="accounts-accounts-opportunities-delete"><input type="button" value="Delete" onClick="javascript:popURLReturn('Opportunities.do?command=ConfirmDelete&orgId=<%=OrgDetails.getId()%>&id=<%=HeaderDetails.getOppId()%>&popup=true','Opportunities.do?command=View&orgId=<%=OrgDetails.getId()%>', 'Delete_opp','320','200','yes','no')"></dhv:permission>
<dhv:permission name="accounts-accounts-opportunities-add"><input type="button" value="Add Component" onClick="javascript:this.form.action='OpportunitiesComponents.do?command=AddOppComponent&id=<%=HeaderDetails.getId()%>&orgId=<%= HeaderDetails.getAccountLink() %>';submit();"></dhv:permission>
<dhv:permission name="accounts-accounts-opportunities-edit,accounts-accounts-opportunities-delete"><br>&nbsp;</dhv:permission>
<table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr class="title">
    <td colspan=2 valign=center align=left>
      <% FileItem thisFile = new FileItem(); %>
      <strong><%= toHtml(HeaderDetails.getDescription()) %></strong>
        <% if (HeaderDetails.hasFiles()) {%>
        <%= thisFile.getImageTag() %>
        <%}%>         
    </td>
  </tr> 
  
  <tr class="containerBody">
    <td nowrap class="formLabel">
      Entered
    </td>
    <td>
      <%= HeaderDetails.getEnteredByName() %>&nbsp;-&nbsp;<%= HeaderDetails.getEnteredString() %>
    </td>
  </tr>
  
      <tr class="containerBody">
    <td nowrap class="formLabel">
      Modified
    </td>
    <td>
      <%= HeaderDetails.getModifiedByName() %>&nbsp;-&nbsp;<%= HeaderDetails.getModifiedString() %>
    </td>
  </tr>    
  </table>
</form>
<br>
<dhv:pagedListStatus showExpandLink="false" title="<%= showError(request, "actionError") %>" object="AccountsComponentListInfo"/>
 <table cellpadding="4" cellspacing="0" border="1" width="100%" class="pagedlist" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr class="title">
    <dhv:permission name="accounts-accounts-opportunities-edit,accounts-accounts-opportunities-delete">
    <td valign=center align=left>
      <strong>Action</strong>
    </td>
    </dhv:permission>
    <td valign=center align=left>
	<strong><a href="Opportunities.do?command=Details&oppId=<%=HeaderDetails.getId()%>&orgId=<%=OrgDetails.getId()%>&column=description">Component</a></strong>
	<%= AccountsComponentListInfo.getSortIcon("description") %>
    </td>
    <td valign=center align=left>
	<strong><a href="Opportunities.do?command=Details&oppId=<%=HeaderDetails.getId()%>&orgId=<%=OrgDetails.getId()%>&column=closed">Status</a></strong>
	<%= AccountsComponentListInfo.getSortIcon("closed") %>
    </td>
    <td valign=center align=left>
	<strong><a href="Opportunities.do?command=Details&oppId=<%=HeaderDetails.getId()%>&orgId=<%=OrgDetails.getId()%>&column=guessvalue">Guess Amount</a></strong>
	<%= AccountsComponentListInfo.getSortIcon("guessvalue") %>
    </td>
    <td valign=center align=left>
      	<strong><a href="Opportunities.do?command=Details&oppId=<%=HeaderDetails.getId()%>&orgId=<%=OrgDetails.getId()%>&column=closedate">Close Date</a></strong>
	<%= AccountsComponentListInfo.getSortIcon("closedate") %>
    </td>
    <td valign=center align=left>
      	<strong><a href="Opportunities.do?command=Details&oppId=<%=HeaderDetails.getId()%>&orgId=<%=OrgDetails.getId()%>&column=stage">Current Stage</a></strong>
	<%= AccountsComponentListInfo.getSortIcon("stage") %>
    </td>  
  </tr>
  
<%
	Iterator j = ComponentList.iterator();
	
	if ( j.hasNext() ) {
		int rowid = 0;
	    while (j.hasNext()) {
		
        if (rowid != 1) {
          rowid = 1;
        } else {
          rowid = 2;
        }
		
		OpportunityComponent thisOpp = (OpportunityComponent)j.next();
%>      
  <tr class="containerBody">
    <dhv:permission name="accounts-accounts-opportunities-edit,accounts-accounts-opportunities-delete">
    <td width=8 valign=center nowrap class="row<%= rowid %>">
    <dhv:permission name="accounts-accounts-opportunities-edit"><a href="OpportunitiesComponents.do?command=ModifyComponent&id=<%= thisOpp.getId() %>&orgId=<%=OrgDetails.getId()%>">Edit</a></dhv:permission><dhv:permission name="accounts-accounts-opportunities-edit,accounts-accounts-opportunities-delete" all="true">|</dhv:permission><dhv:permission name="accounts-accounts-opportunities-delete"><a href="javascript:popURLReturn('OpportunitiesComponents.do?command=ConfirmComponentDelete&orgId=<%=OrgDetails.getId()%>&oppId=<%=thisOpp.getId()%>&popup=true','Opportunities.do?command=ViewOpps&orgId=<%=OrgDetails.getId()%>', 'Delete_opp','320','200','yes','no');">Del</a></dhv:permission>
    </td>
    </dhv:permission>
    <td width=100% valign=center class="row<%= rowid %>">
      <a href="OpportunitiesComponents.do?command=DetailsComponent&orgId=<%=OrgDetails.getId()%>&id=<%=thisOpp.getId()%>">
      <%= toHtml(thisOpp.getDescription()) %></a>
    </td>
    <td width=125 valign=center nowrap class="row<%= rowid %>">
      <%= thisOpp.getClosed() != null ? "<font color=\"red\">closed</font>" : "<font color=\"green\">open</font>" %>
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
    <td colspan="6" valign=center>
      No opportunity components found.
    </td>
  </tr>
<%}%>
  
</table>
<br>
<dhv:pagedListControl object="AccountsComponentListInfo"/>
</td>
</tr>
</table>


