<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,org.aspcfs.modules.accounts.base.*,org.aspcfs.modules.pipeline.base.OpportunityHeader,com.zeroio.iteam.base.*" %>
<jsp:useBean id="OrgDetails" class="org.aspcfs.modules.accounts.base.Organization" scope="request"/>
<jsp:useBean id="OpportunityList" class="org.aspcfs.modules.pipeline.base.OpportunityHeaderList" scope="request"/>
<jsp:useBean id="OpportunityPagedInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<%@ include file="../initPage.jsp" %>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popURL.js"></SCRIPT>
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
<dhv:permission name="accounts-accounts-opportunities-add"><a href="Opportunities.do?command=Add&orgId=<%= request.getParameter("orgId") %>">Add an Opportunity</a></dhv:permission>
<center><%= OpportunityPagedInfo.getAlphabeticalPageLinks() %></center>
<table width="100%" border="0">
  <tr>
    <form name="listView" method="post" action="Opportunities.do?command=View&orgId=<%= OrgDetails.getOrgId() %>">
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
<table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr class="title">
    <dhv:permission name="accounts-accounts-opportunities-edit,accounts-accounts-opportunities-delete">
    <td width="8" nowrap>
      <strong>Action</strong>
    </td>
    </dhv:permission>
    <td width="100%" nowrap>
      <strong><a href="Opportunities.do?command=View&orgId=<%= OrgDetails.getId() %>&column=x.description">Opportunity Name</a></strong>
      <%= OpportunityPagedInfo.getSortIcon("x.description") %>
    </td>
    <td nowrap>
      <strong>Best Guess Total</strong>
    </td>
    <td nowrap>
      <strong><a href="Opportunities.do?command=View&orgId=<%= OrgDetails.getId() %>&column=x.modified">Last Modified</a></strong>
      <%= OpportunityPagedInfo.getSortIcon("x.modified") %>
    </td>
  </tr>
<%
	Iterator j = OpportunityList.iterator();
  FileItem thisFile = new FileItem();
	if ( j.hasNext() ) {
		int rowid = 0;
	    while (j.hasNext()) {
		    rowid = (rowid != 1?1:2);
        OpportunityHeader oppHeader = (OpportunityHeader)j.next();
%>      
  <tr class="containerBody">
    <dhv:permission name="accounts-accounts-opportunities-edit,accounts-accounts-opportunities-delete">
    <td width="8" valign="center" nowrap class="row<%= rowid %>">
      <dhv:permission name="accounts-accounts-opportunities-edit"><a href="Opportunities.do?command=Modify&headerId=<%= oppHeader.getId() %>&orgId=<%= oppHeader.getAccountLink() %>&return=list">Edit</a></dhv:permission><dhv:permission name="accounts-accounts-opportunities-edit,accounts-accounts-opportunities-delete" all="true">|</dhv:permission><dhv:permission name="accounts-accounts-opportunities-delete"><a href="javascript:popURLReturn('Opportunities.do?command=ConfirmDelete&orgId=<%= OrgDetails.getId() %>&headerId=<%= oppHeader.getId() %>','Opportunities.do?command=View&orgId=<%= OrgDetails.getId() %>&popup=true', 'Delete_opp','320','200','yes','no');">Del</a></dhv:permission>
    </td>
    </dhv:permission>
      <td valign="center" class="row<%= rowid %>">
        <a href="Opportunities.do?command=Details&headerId=<%= oppHeader.getId() %>&orgId=<%= OrgDetails.getId() %>&reset=true">
        <%= toHtml(oppHeader.getDescription()) %></a>
        (<%= oppHeader.getComponentCount() %>)
<dhv:evaluate if="<%= oppHeader.hasFiles() %>">
        <%= thisFile.getImageTag() %>
</dhv:evaluate>
      </td>  
      <td valign="center" align="right" class="row<%= rowid %>" nowrap>
        $<%= toHtml(oppHeader.getTotalValueCurrency()) %>
      </td>      
      <td valign="center" class="row<%= rowid %>" nowrap>
        <%= toHtml(oppHeader.getModifiedString()) %>
      </td>   
  </tr>
<%}%>
<%} else {%>
  <tr class="containerBody">
    <td colspan="5">
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

