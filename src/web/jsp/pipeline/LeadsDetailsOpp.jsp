<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,org.aspcfs.modules.pipeline.base.*,com.zeroio.iteam.base.*" %>
<jsp:useBean id="HeaderDetails" class="org.aspcfs.modules.pipeline.base.OpportunityHeader" scope="request"/>
<jsp:useBean id="ComponentList" class="org.aspcfs.modules.pipeline.base.OpportunityComponentList" scope="request"/>
<jsp:useBean id="LeadsComponentListInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<jsp:useBean id="PipelineViewpointInfo" class="org.aspcfs.utils.web.ViewpointInfo" scope="session"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<%@ include file="../initPage.jsp" %>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popURL.js"></SCRIPT>
<form name="oppdet" action="Leads.do?id=<%= HeaderDetails.getId() %>&orgId=<%= HeaderDetails.getAccountLink() %>&contactId=<%= HeaderDetails.getContactLink() %>" method="post">
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
<dhv:evaluate exp="<%= PipelineViewpointInfo.isVpSelected(User.getUserId()) %>">
  <b>Viewpoint: </b><b class="highlight"><%= PipelineViewpointInfo.getVpUserName() %></b><br>
  &nbsp;<br>
</dhv:evaluate>
<table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr class="containerHeader">
    <td width="80%">
      <% FileItem thisFile = new FileItem(); %>
      <strong><%= toHtml(HeaderDetails.getDescription()) %></strong>&nbsp;
      <dhv:evaluate exp="<%= HeaderDetails.getAccountEnabled() && HeaderDetails.getAccountLink() > -1 %>">
        <dhv:permission name="accounts-view,accounts-accounts-view">[ <a href="Accounts.do?command=Details&orgId=<%= HeaderDetails.getAccountLink() %>">Go to this Account</a> ]</dhv:permission>
      </dhv:evaluate>
      <dhv:evaluate if="<%= HeaderDetails.hasFiles() %>">
        <%= thisFile.getImageTag() %>
      </dhv:evaluate>
      <dhv:evaluate exp="<%= HeaderDetails.getContactLink() > -1 %>">
        <dhv:permission name="contacts-view,contacts-external_contacts-view">[ <a href="ExternalContacts.do?command=ContactDetails&id=<%= HeaderDetails.getContactLink() %>">Go to this Contact</a> ]</dhv:permission>
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
      <dhv:permission name="pipeline-opportunities-edit"><input type="button" value="Rename" onClick="javascript:this.form.action='Leads.do?command=ModifyOpp&headerId=<%= HeaderDetails.getId() %>';submit();"></dhv:permission>
      <dhv:permission name="pipeline-opportunities-delete"><input type="button" value="Delete" onClick="javascript:popURLReturn('Leads.do?command=ConfirmDelete&id=<%= HeaderDetails.getId() %>&popup=true','Leads.do?command=ViewOpp', 'Delete_opp','320','200','yes','no')"></dhv:permission>
      <dhv:permission name="pipeline-opportunities-add"><input type="button" value="Add Component" onClick="javascript:this.form.action='LeadsComponents.do?command=AddOppComponent&id=<%= HeaderDetails.getId() %>';submit();"></dhv:permission>
      <dhv:permission name="pipeline-opportunities-delete,pipeline-opportunities-edit"><br>&nbsp;</dhv:permission>
      <table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
        <tr class="title">
          <td colspan="2">
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
<dhv:pagedListStatus title="<%= showError(request, "actionError") %>" object="LeadsComponentListInfo"/>
 <table cellpadding="4" cellspacing="0" border="1" width="100%" class="pagedlist" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr class="title">
    <dhv:permission name="pipeline-opportunities-edit,pipeline-opportunities-delete">
    <td align="center" nowrap>
      <strong>Action</strong>
    </td>
    </dhv:permission>
    <td nowrap>
      <strong><a href="Leads.do?command=DetailsOpp&headerId=<%= HeaderDetails.getId() %>&column=oc.description">Component</a></strong>
      <%= LeadsComponentListInfo.getSortIcon("oc.description") %>
    </td>
    <td nowrap>
      <strong><a href="Leads.do?command=DetailsOpp&headerId=<%= HeaderDetails.getId() %>&column=oc.closed">Status</a></strong>
      <%= LeadsComponentListInfo.getSortIcon("oc.closed") %>
    </td>
    <td align="center" nowrap>
      <strong><a href="Leads.do?command=DetailsOpp&headerId=<%= HeaderDetails.getId() %>&column=oc.guessvalue">Guess<br>Amount</a></strong>
      <%= LeadsComponentListInfo.getSortIcon("oc.guessvalue") %>
    </td>
    <td nowrap>
      <strong><a href="Leads.do?command=DetailsOpp&headerId=<%= HeaderDetails.getId() %>&column=oc.closedate">Close Date</a></strong>
      <%= LeadsComponentListInfo.getSortIcon("oc.closedate") %>
    </td>
    <td nowrap>
      <strong><a href="Leads.do?command=DetailsOpp&headerId=<%= HeaderDetails.getId() %>&column=stage">Current Stage</a></strong>
      <%= LeadsComponentListInfo.getSortIcon("stage") %>
    </td>  
  </tr>
<%
	Iterator j = ComponentList.iterator();
	if ( j.hasNext() ) {
		int rowid = 0;
	    while (j.hasNext()) {
        rowid = (rowid != 1?1:2);
        OpportunityComponent thisComponent = (OpportunityComponent)j.next();
%>      
  <tr class="containerBody">
    <dhv:permission name="pipeline-opportunities-edit,pipeline-opportunities-delete">
    <td width="8" valign="top" align="center" nowrap class="row<%= rowid %>">
      <dhv:permission name="pipeline-opportunities-edit"><a href="LeadsComponents.do?command=ModifyComponent&id=<%= thisComponent.getId() %>&return=details">Edit</a></dhv:permission><dhv:permission name="pipeline-opportunities-edit,pipeline-opportunities-delete" all="true">|</dhv:permission><dhv:permission name="pipeline-opportunities-delete"><a href="javascript:popURLReturn('LeadsComponents.do?command=ConfirmComponentDelete&id=<%= thisComponent.getId() %>&popup=true','Leads.do?command=ViewOpps', 'Delete_opp','320','200','yes','no');">Del</a></dhv:permission>
    </td>
    </dhv:permission>
    <td width="100%" valign="top" class="row<%= rowid %>">
      <a href="LeadsComponents.do?command=DetailsComponent&id=<%= thisComponent.getId() %>">
      <%= toHtml(thisComponent.getDescription()) %></a>
    </td>
    <td valign="top" align="center" nowrap class="row<%= rowid %>">
      <%= thisComponent.getClosed() != null ? "<font color=\"red\">closed</font>" : "<font color=\"green\">open</font>" %>
    </td>
    <td valign="top" align="right" nowrap class="row<%= rowid %>">
      $<%= thisComponent.getGuessCurrency() %>
    </td>
    <td valign="top" align="center" nowrap class="row<%= rowid %>">
      <%= toHtml(thisComponent.getCloseDateString()) %>
    </td>
    <td valign="top" align="center" nowrap class="row<%= rowid %>">
      <%= toHtml(thisComponent.getStageName()) %>
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
<% if (request.getParameter("return") != null) { %>
<input type="hidden" name="return" value="<%= request.getParameter("return") %>">
<%}%>
<dhv:pagedListControl object="LeadsComponentListInfo"/>
</td>
</tr>
</table>
</form>
