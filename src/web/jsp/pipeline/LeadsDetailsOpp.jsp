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
	<a href="Leads.do?command=ViewOpp">View Components</a> >
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
<%@ include file="leads_details_header_include.jsp" %>
<% String param1 = "id=" + opportunityHeader.getId(); %>      
<dhv:container name="opportunities" selected="details" param="<%= param1 %>" style="tabs"/>
<table cellpadding="4" cellspacing="0" border="0" width="100%">
  <tr>
    <td class="containerBack">
      <%-- Begin container content --%>
      <dhv:permission name="pipeline-opportunities-add">
        <a href="LeadsComponents.do?command=Prepare&headerId=<%= opportunityHeader.getId() %>">Add a Component</a><br>
      </dhv:permission>
<dhv:pagedListStatus title="<%= showError(request, "actionError") %>" object="LeadsComponentListInfo"/>
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="pagedList">
  <tr>
    <dhv:permission name="pipeline-opportunities-edit,pipeline-opportunities-delete">
    <th align="center" nowrap>
      <strong>Action</strong>
    </th>
    </dhv:permission>
    <th nowrap>
      <strong><a href="Leads.do?command=DetailsOpp&headerId=<%= opportunityHeader.getId() %>&column=oc.description">Component</a></strong>
      <%= LeadsComponentListInfo.getSortIcon("oc.description") %>
    </th>
    <th nowrap>
      <strong><a href="Leads.do?command=DetailsOpp&headerId=<%= opportunityHeader.getId() %>&column=oc.closed">Status</a></strong>
      <%= LeadsComponentListInfo.getSortIcon("oc.closed") %>
    </th>
    <th align="center" nowrap>
      <strong><a href="Leads.do?command=DetailsOpp&headerId=<%= opportunityHeader.getId() %>&column=oc.guessvalue">Guess<br>Amount</a></strong>
      <%= LeadsComponentListInfo.getSortIcon("oc.guessvalue") %>
    </th>
    <th nowrap>
      <strong><a href="Leads.do?command=DetailsOpp&headerId=<%= opportunityHeader.getId() %>&column=oc.closedate">Close Date</a></strong>
      <%= LeadsComponentListInfo.getSortIcon("oc.closedate") %>
    </th>
    <th nowrap>
      <strong><a href="Leads.do?command=DetailsOpp&headerId=<%= opportunityHeader.getId() %>&column=stage">Current Stage</a></strong>
      <%= LeadsComponentListInfo.getSortIcon("stage") %>
    </th>
    <th>
      <strong>Owner</strong>
    </th>
  </tr>
<%
	Iterator j = ComponentList.iterator();
	if ( j.hasNext() ) {
		int rowid = 0;
	    while (j.hasNext()) {
        rowid = (rowid != 1?1:2);
        OpportunityComponent thisComponent = (OpportunityComponent)j.next();
%>      
  <tr class="row<%= rowid %>">
    <dhv:permission name="pipeline-opportunities-edit,pipeline-opportunities-delete">
    <td width="8" valign="top" align="center" nowrap>
      <dhv:permission name="pipeline-opportunities-edit"><a href="LeadsComponents.do?command=ModifyComponent&id=<%= thisComponent.getId() %>&return=details">Edit</a></dhv:permission><dhv:permission name="pipeline-opportunities-edit,pipeline-opportunities-delete" all="true">|</dhv:permission><dhv:permission name="pipeline-opportunities-delete"><a href="javascript:popURLReturn('LeadsComponents.do?command=ConfirmComponentDelete&id=<%= thisComponent.getId() %>&popup=true','Leads.do?command=ViewOpps', 'Delete_opp','320','200','yes','no');">Del</a></dhv:permission>
    </td>
    </dhv:permission>
    <td width="100%" valign="top">
      <a href="LeadsComponents.do?command=DetailsComponent&id=<%= thisComponent.getId() %>">
      <%= toHtml(thisComponent.getDescription()) %></a>
    </td>
    <td valign="top" align="center" nowrap>
      <%= thisComponent.getClosed() != null ? "<font color=\"red\">closed</font>" : "<font color=\"green\">open</font>" %>
    </td>
    <td valign="top" align="right" nowrap>
      $<%= thisComponent.getGuessCurrency() %>
    </td>
    <td valign="top" align="center" nowrap>
      <%= toHtml(thisComponent.getCloseDateString()) %>
    </td>
    <td valign="top" align="center" nowrap>
      <%= toHtml(thisComponent.getStageName()) %>
    </td>
    <td valign="top" align="center" nowrap>
      <dhv:username id="<%= thisComponent.getOwner() %>"/>
    </td>
  </tr>
<%}%>
<%} else {%>
  <tr class="containerBody">
    <td colspan="7">
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
