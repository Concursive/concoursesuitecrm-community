<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,org.aspcfs.modules.pipeline.base.*,com.zeroio.iteam.base.*" %>
<jsp:useBean id="opportunityHeader" class="org.aspcfs.modules.pipeline.base.OpportunityHeader" scope="request"/>
<jsp:useBean id="ComponentList" class="org.aspcfs.modules.pipeline.base.OpportunityComponentList" scope="request"/>
<jsp:useBean id="LeadsComponentListInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<jsp:useBean id="PipelineViewpointInfo" class="org.aspcfs.utils.web.ViewpointInfo" scope="session"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<%@ include file="../initPage.jsp" %>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popURL.js"></SCRIPT>
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
<%-- Begin container --%>
<table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr class="containerHeader">
    <td>
      <%@ include file="leads_details_header_include.jsp" %>
    </td>
  </tr>
  <tr class="containerMenu">
    <td>
      <% String param1 = "id=" + opportunityHeader.getId(); %>      
      <dhv:container name="opportunities" selected="details" param="<%= param1 %>" />
    </td>
  </tr>
  <tr>
    <td class="containerBack">
      <%-- Begin container content --%>
      <dhv:permission name="pipeline-opportunities-add">
        <a href="LeadsComponents.do?command=Prepare&headerId=<%= opportunityHeader.getId() %>">Add a Component</a><br>
      </dhv:permission>
      <dhv:evaluate if="<%= request.getParameter("return") != null %>">
        <input type="hidden" name="return" value="<%= request.getParameter("return") %>">
      </dhv:evaluate>
<dhv:pagedListStatus title="<%= showError(request, "actionError") %>" object="LeadsComponentListInfo"/>
 <table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr class="title">
    <dhv:permission name="pipeline-opportunities-edit,pipeline-opportunities-delete">
    <td align="center" nowrap>
      <strong>Action</strong>
    </td>
    </dhv:permission>
    <td nowrap>
      <strong><a href="Leads.do?command=DetailsOpp&headerId=<%= opportunityHeader.getId() %>&column=oc.description">Component</a></strong>
      <%= LeadsComponentListInfo.getSortIcon("oc.description") %>
    </td>
    <td nowrap>
      <strong><a href="Leads.do?command=DetailsOpp&headerId=<%= opportunityHeader.getId() %>&column=oc.closed">Status</a></strong>
      <%= LeadsComponentListInfo.getSortIcon("oc.closed") %>
    </td>
    <td align="center" nowrap>
      <strong><a href="Leads.do?command=DetailsOpp&headerId=<%= opportunityHeader.getId() %>&column=oc.guessvalue">Guess<br>Amount</a></strong>
      <%= LeadsComponentListInfo.getSortIcon("oc.guessvalue") %>
    </td>
    <td nowrap>
      <strong><a href="Leads.do?command=DetailsOpp&headerId=<%= opportunityHeader.getId() %>&column=oc.closedate">Close Date</a></strong>
      <%= LeadsComponentListInfo.getSortIcon("oc.closedate") %>
    </td>
    <td nowrap>
      <strong><a href="Leads.do?command=DetailsOpp&headerId=<%= opportunityHeader.getId() %>&column=stage">Current Stage</a></strong>
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
<dhv:pagedListControl object="LeadsComponentListInfo"/>
&nbsp;<br>
<dhv:permission name="pipeline-opportunities-edit"><input type="button" value="Rename Opportunity" onClick="javascript:window.location.href='Leads.do?command=ModifyOpp&headerId=<%= opportunityHeader.getId() %>';"></dhv:permission>
<dhv:permission name="pipeline-opportunities-delete"><input type="button" value="Delete Opportunity" onClick="javascript:popURLReturn('Leads.do?command=ConfirmDelete&id=<%= opportunityHeader.getId() %>&popup=true','Leads.do?command=ViewOpp', 'Delete_opp','320','200','yes','no')"></dhv:permission>
</td>
</tr>
</table>
