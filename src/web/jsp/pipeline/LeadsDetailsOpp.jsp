<%@ taglib uri="WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,com.darkhorseventures.cfsbase.*,com.zeroio.iteam.base.*" %>
<jsp:useBean id="HeaderDetails" class="com.darkhorseventures.cfsbase.OpportunityHeader" scope="request"/>
<jsp:useBean id="ComponentList" class="com.darkhorseventures.cfsbase.OpportunityComponentList" scope="request"/>
<jsp:useBean id="LeadsComponentListInfo" class="com.darkhorseventures.webutils.PagedListInfo" scope="session"/>
<%@ include file="initPage.jsp" %>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popURL.js"></SCRIPT>
<form name="oppdet" action="Leads.do?id=<%=HeaderDetails.getId()%>&orgId=<%= HeaderDetails.getAccountLink() %>&contactId=<%= HeaderDetails.getContactLink() %>" method="post">

<a href="Leads.do">Pipeline Management</a> > 
<% if (request.getParameter("return") == null) { %>
	<a href="Leads.do?command=ViewOpp">View Opportunities</a> >
<%} else {%>
	<% if (request.getParameter("return").equals("dashboard")) { %>
		<a href="Leads.do?command=Dashboard">Dashboard</a> >
	<%}%>
<%}%>
Opportunity Details<br>
<hr color="#BFBFBB" noshade>
<% if (request.getAttribute("actionError") != null) { %>
<%= showError(request, "actionError") %>
<%}%>
<table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr class="containerHeader">
    <td width="80%">
      <% FileItem thisFile = new FileItem(); %>
      <strong><%= toHtml(HeaderDetails.getDescription()) %></strong>&nbsp;
      	<dhv:evaluate exp="<%=(HeaderDetails.getAccountEnabled() && HeaderDetails.getAccountLink() > -1)%>">
        <dhv:permission name="accounts-view,accounts-accounts-view">[ <a href="Accounts.do?command=Details&orgId=<%=HeaderDetails.getAccountLink()%>">Go to this Account</a> ]</dhv:permission>
	</dhv:evaluate>
        <% if (HeaderDetails.hasFiles()) {%>
        <%= thisFile.getImageTag() %>
        <%}%>       
  
	  
	<dhv:evaluate exp="<%=(HeaderDetails.getContactLink() > -1)%>">
	<dhv:permission name="contacts-view,contacts-external_contacts-view">[ <a href="ExternalContacts.do?command=ContactDetails&id=<%=HeaderDetails.getContactLink()%>">Go to this Contact</a> ]</dhv:permission>
	</dhv:evaluate>
    </td>
  </tr>
  <tr class="containerMenu">
    <td>
      <% String param1 = "id=" + HeaderDetails.getId(); %>      
      <dhv:container name="opportunities" selected="details" param="<%= param1 %>" />
    </td>
  </tr>
  <tr>
    <td class="containerBack">

<dhv:permission name="pipeline-opportunities-edit"><input type="button" value="Modify" onClick="javascript:this.form.action='Leads.do?command=ModifyOpp&oppId=<%=HeaderDetails.getId()%>';submit();"></dhv:permission>
<dhv:permission name="pipeline-opportunities-delete"><input type="button" value="Delete" onClick="javascript:popURLReturn('Leads.do?command=ConfirmDelete&id=<%=HeaderDetails.getOppId()%>','Leads.do?command=ViewOpp', 'Delete_opp','320','200','yes','no')"></dhv:permission>
<dhv:permission name="pipeline-opportunities-add"><input type="button" value="Add Component" onClick="javascript:this.form.action='LeadsComponents.do?command=AddOppComponent&id=<%=HeaderDetails.getId()%>';submit();"></dhv:permission>
<dhv:permission name="pipeline-opportunities-delete,pipeline-opportunities-edit"><br>&nbsp;</dhv:permission>

<table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr bgcolor="#DEE0FA">
    <td colspan=2 valign=center align=left>
      <strong>Primary Information</strong>
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
<br>
<dhv:pagedListStatus showExpandLink="false" title="<%= showError(request, "actionError") %>" object="LeadsComponentListInfo"/>
 <table cellpadding="4" cellspacing="0" border="1" width="100%" class="pagedlist" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr class="title">
    <dhv:permission name="pipeline-opportunities-edit,pipeline-opportunities-delete">
    <td valign=center align=left>
      <strong>Action</strong>
    </td>
    </dhv:permission>
    <td valign=center align=left>
	<strong><a href="Leads.do?command=Details&oppId=<%=HeaderDetails.getId()%>&column=description">Component</a></strong>
	<%= LeadsComponentListInfo.getSortIcon("description") %>
    </td>
    <td valign=center align=left>
	<strong><a href="Leads.do?command=Details&oppId=<%=HeaderDetails.getId()%>&column=guessvalue">Guess Amount</a></strong>
	<%= LeadsComponentListInfo.getSortIcon("guessvalue") %>
    </td>
    <td valign=center align=left>
      	<strong><a href="Leads.do?command=Details&oppId=<%=HeaderDetails.getId()%>&column=closedate">Close Date</a></strong>
	<%= LeadsComponentListInfo.getSortIcon("closedate") %>
    </td>
    <td valign=center align=left>
      	<strong><a href="Leads.do?command=Details&oppId=<%=HeaderDetails.getId()%>&column=stage">Current Stage</a></strong>
	<%= LeadsComponentListInfo.getSortIcon("stage") %>
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
    <dhv:permission name="pipeline-opportunities-edit,pipeline-opportunities-delete">
    <td width=8 valign=center nowrap class="row<%= rowid %>">
    <dhv:permission name="pipeline-opportunities-edit"><a href="LeadsComponents.do?command=ModifyComponent&id=<%= thisOpp.getId() %>&return=details">Edit</a></dhv:permission><dhv:permission name="pipeline-opportunities-edit,pipeline-opportunities-delete" all="true">|</dhv:permission><dhv:permission name="pipeline-opportunities-delete"><a href="javascript:popURLReturn('LeadsComponents.do?command=ConfirmComponentDelete&oppId=<%=thisOpp.getId()%>','Leads.do?command=ViewOpps', 'Delete_opp','320','200','yes','no');">Del</a></dhv:permission>
    </td>
    </dhv:permission>
    <td width=100% valign=center class="row<%= rowid %>">
      <a href="LeadsComponents.do?command=DetailsComponent&id=<%=thisOpp.getId()%>">
      <%= toHtml(thisOpp.getDescription()) %></a>
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
    <td colspan=5 valign=center>
      No opportunity components found.
    </td>
  </tr>
<%}%>
  
</table>
<br>
<dhv:pagedListControl object="LeadsComponentListInfo"/></td></tr>
</table>
<% if (request.getParameter("return") != null) { %>
<input type="hidden" name="return" value="<%=request.getParameter("return")%>">
<%}%>
</form>
