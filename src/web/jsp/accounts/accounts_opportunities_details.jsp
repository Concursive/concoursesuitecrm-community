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
<a href="Accounts.do?command=Details&orgId=<%= OrgDetails.getOrgId() %>">Account Details</a> >
<a href="Opportunities.do?command=View&orgId=<%= OrgDetails.getOrgId() %>">Opportunities</a> >
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
<form name="oppdet" action="Opportunities.do?command=ModifyOpp&headerId=<%= HeaderDetails.getId() %>&orgId=<%= HeaderDetails.getAccountLink() %>&contactId=<%= HeaderDetails.getContactLink() %>" method="post">
<dhv:permission name="accounts-accounts-opportunities-edit"><input type="button" value="Rename" onClick="javascript:this.form.action='Opportunities.do?command=Modify&headerId=<%= HeaderDetails.getId() %>&orgId=<%= HeaderDetails.getAccountLink() %>';submit();"></dhv:permission>
<dhv:permission name="accounts-accounts-opportunities-delete"><input type="button" value="Delete" onClick="javascript:popURLReturn('Opportunities.do?command=ConfirmDelete&orgId=<%= OrgDetails.getId() %>&headerId=<%= HeaderDetails.getId() %>&popup=true','Opportunities.do?command=View&orgId=<%= OrgDetails.getId() %>', 'Delete_opp','320','200','yes','no')"></dhv:permission>
<dhv:permission name="accounts-accounts-opportunities-add"><input type="button" value="Add Component" onClick="javascript:this.form.action='OpportunitiesComponents.do?command=AddOppComponent&headerId=<%= HeaderDetails.getId() %>&orgId=<%= HeaderDetails.getAccountLink() %>';submit();"></dhv:permission>
<dhv:permission name="accounts-accounts-opportunities-edit,accounts-accounts-opportunities-delete"><br>&nbsp;</dhv:permission>
<table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr class="title">
    <td colspan="2">
      <% FileItem thisFile = new FileItem(); %>
      <strong><%= toHtml(HeaderDetails.getDescription()) %></strong>
      <dhv:evaluate if="<%= HeaderDetails.hasFiles() %>">
        <%= thisFile.getImageTag() %>
      </dhv:evaluate>
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
<dhv:pagedListStatus title="<%= showError(request, "actionError") %>" object="AccountsComponentListInfo"/>
 <table cellpadding="4" cellspacing="0" border="1" width="100%" class="pagedlist" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr class="title">
    <dhv:permission name="accounts-accounts-opportunities-edit,accounts-accounts-opportunities-delete">
    <td>
      <strong>Action</strong>
    </td>
    </dhv:permission>
    <td nowrap>
      <strong><a href="Opportunities.do?command=Details&headerId=<%= HeaderDetails.getId() %>&orgId=<%= OrgDetails.getId() %>&column=oc.description">Component</a></strong>
      <%= AccountsComponentListInfo.getSortIcon("oc.description") %>
    </td>
    <td nowrap>
      <strong><a href="Opportunities.do?command=Details&headerId=<%= HeaderDetails.getId() %>&orgId=<%= OrgDetails.getId() %>&column=oc.closed">Status</a></strong>
      <%= AccountsComponentListInfo.getSortIcon("oc.closed") %>
    </td>
    <td nowrap>
      <strong><a href="Opportunities.do?command=Details&headerId=<%= HeaderDetails.getId() %>&orgId=<%= OrgDetails.getId() %>&column=oc.guessvalue">Guess Amount</a></strong>
      <%= AccountsComponentListInfo.getSortIcon("oc.guessvalue") %>
    </td>
    <td nowrap>
      <strong><a href="Opportunities.do?command=Details&headerId=<%= HeaderDetails.getId() %>&orgId=<%=OrgDetails.getId()%>&column=oc.closedate">Close Date</a></strong>
      <%= AccountsComponentListInfo.getSortIcon("oc.closedate") %>
    </td>
    <td nowrap>
      <strong><a href="Opportunities.do?command=Details&headerId=<%= HeaderDetails.getId() %>&orgId=<%=OrgDetails.getId()%>&column=stagename">Current Stage</a></strong>
      <%= AccountsComponentListInfo.getSortIcon("stagename") %>
    </td>  
  </tr>
<%
	Iterator j = ComponentList.iterator();
	if (j.hasNext()) {
		int rowid = 0;
	    while (j.hasNext()) {
        rowid = (rowid != 1?1:2);
        OpportunityComponent oppComponent = (OpportunityComponent)j.next();
%>
  <tr class="containerBody">
    <dhv:permission name="accounts-accounts-opportunities-edit,accounts-accounts-opportunities-delete">
    <td width="8" valign="top" align="center" nowrap class="row<%= rowid %>">
      <dhv:permission name="accounts-accounts-opportunities-edit"><a href="OpportunitiesComponents.do?command=ModifyComponent&id=<%= oppComponent.getId() %>&orgId=<%=OrgDetails.getId()%>&return=list">Edit</a></dhv:permission><dhv:permission name="accounts-accounts-opportunities-edit,accounts-accounts-opportunities-delete" all="true">|</dhv:permission><dhv:permission name="accounts-accounts-opportunities-delete"><a href="javascript:popURLReturn('OpportunitiesComponents.do?command=ConfirmComponentDelete&orgId=<%= OrgDetails.getId() %>&id=<%= oppComponent.getId() %>&popup=true','Opportunities.do?command=ViewOpps&orgId=<%= OrgDetails.getId() %>', 'Delete_opp','320','200','yes','no');">Del</a></dhv:permission>
    </td>
    </dhv:permission>
    <td width="100%" valign="top" class="row<%= rowid %>">
      <a href="OpportunitiesComponents.do?command=DetailsComponent&orgId=<%= OrgDetails.getId() %>&id=<%=oppComponent.getId()%>">
      <%= toHtml(oppComponent.getDescription()) %></a>
    </td>
    <td valign="top" nowrap class="row<%= rowid %>">
      <%= oppComponent.getClosed() != null ? "<font color=\"red\">closed</font>" : "<font color=\"green\">open</font>" %>
    </td>
    <td valign="top" nowrap class="row<%= rowid %>">
      $<%= oppComponent.getGuessCurrency() %>
    </td>
    <td valign="top" nowrap class="row<%= rowid %>">
      <%= toHtml(oppComponent.getCloseDateString()) %>
    </td>
    <td valign="top" nowrap class="row<%= rowid %>">
      <%= toHtml(oppComponent.getStageName()) %>
    </td>		
  </tr>
<%}%>
<%} else {%>
  <tr class="containerBody">
    <td colspan="6">
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

