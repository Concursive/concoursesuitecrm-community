<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,java.text.DateFormat,org.aspcfs.modules.pipeline.base.*,com.zeroio.iteam.base.*" %>
<%@ page import="org.aspcfs.modules.base.Constants" %>
<jsp:useBean id="opportunityHeader" class="org.aspcfs.modules.pipeline.base.OpportunityHeader" scope="request"/>
<jsp:useBean id="CallDetails" class="org.aspcfs.modules.contacts.base.Call" scope="request"/>
<jsp:useBean id="PipelineViewpointInfo" class="org.aspcfs.utils.web.ViewpointInfo" scope="session"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<%@ include file="../initPage.jsp" %>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/confirmDelete.js"></SCRIPT>
<script language="JavaScript" type="text/javascript" src="javascript/popURL.js"></script>
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="Leads.do">Pipeline</a> >
<% if ("dashboard".equals(request.getParameter("viewSource"))){ %>
	<a href="Leads.do?command=Dashboard">Dashboard</a> >
<% }else{ %>
	<a href="Leads.do?command=Search">Search Results</a> >
<% } %>
<a href="Leads.do?command=DetailsOpp&headerId=<%= opportunityHeader.getId() %><%= addLinkParams(request, "viewSource") %>">Opportunity Details</a> >
<a href="LeadsCalls.do?command=View&headerId=<%= opportunityHeader.getId() %><%= addLinkParams(request, "viewSource") %>">Calls</a> >
Call Details
</td>
</tr>
</table>
<%-- End Trails --%>
<dhv:evaluate exp="<%= PipelineViewpointInfo.isVpSelected(User.getUserId()) %>">
  <b>Viewpoint: </b><b class="highlight"><%= PipelineViewpointInfo.getVpUserName() %></b><br>
  &nbsp;<br>
</dhv:evaluate>
<%@ include file="leads_details_header_include.jsp" %>
<% String param1 = "id=" + opportunityHeader.getId(); 
   String param2 = addLinkParams(request, "viewSource");
%>
<dhv:container name="opportunities" selected="calls" param="<%= param1 %>" appendToUrl="<%= param2 %>" style="tabs"/>
<table cellpadding="4" cellspacing="0" border="0" width="100%">
  <tr>
    <td class="containerBack">
      <dhv:permission name="pipeline-opportunities-calls-edit"><input type="button" value="Modify" onClick="javascript:window.location.href='LeadsCalls.do?command=Modify&id=<%= CallDetails.getId() %>&headerId=<%= opportunityHeader.getId() %><%= addLinkParams(request, "viewSource") %>';"></dhv:permission>
      <dhv:permission name="pipeline-opportunities-calls-delete"><input type="button" value="Delete" onClick="javascript:return confirmDelete('LeadsCalls.do?command=Delete&id=<%= CallDetails.getId() %>&headerId=<%= opportunityHeader.getId() %><%= addLinkParams(request, "viewSource") %>')"></dhv:permission>
      <dhv:permission name="pipeline-opportunities-calls-view"><input type="button" name="action" value="Forward" onClick="javascript:window.location.href='LeadsCallsForward.do?command=ForwardCall&forwardType=<%= Constants.PIPELINE_CALLS %>&headerId=<%= opportunityHeader.getId() %>&id=<%=CallDetails.getId()%><%= addLinkParams(request, "viewSource") %>';"></dhv:permission>
      <dhv:permission name="pipeline-opportunities-calls-edit,pipeline-opportunities-calls-delete"><br>&nbsp;</dhv:permission>
      <table cellpadding="4" cellspacing="0" border="0" width="100%" class="pagedList">
        <tr>
          <th colspan="2">
            <strong>Call Details</strong>
          </th>     
        </tr>
        <tr class="containerBody">
          <td class="formLabel">
            Type
          </td>
          <td>
            <%= toHtml(CallDetails.getCallType()) %>
          </td>
        </tr>
        <tr class="containerBody">
          <td class="formLabel">
            Contact Name
          </td>
          <td>
            <%= toHtml(CallDetails.getContactName()) %>
          </td>
        </tr>
        <tr class="containerBody">
          <td class="formLabel">
            Length
          </td>
          <td>
            <%= toHtml(CallDetails.getLengthText()) %>
          </td>
        </tr>
        <tr class="containerBody">
          <td class="formLabel">
            Subject
          </td>
          <td>
            <%= toHtml(CallDetails.getSubject()) %>
          </td>
        </tr>
        <tr class="containerBody">
          <td class="formLabel">
            Notes
          </td>
          <td>
            <%= toHtml(CallDetails.getNotes()) %>
          </td>
        </tr>
        <tr class="containerBody">
          <td nowrap class="formLabel">
            Alert Description
          </td>
          <td>
            <%= toHtml(CallDetails.getAlertText()) %>
          </td>
        </tr>
        <tr class="containerBody">
          <td nowrap class="formLabel">
            Alert Date
          </td>
          <td>
          <dhv:tz timestamp="<%= CallDetails.getAlertDate() %>" dateOnly="true" dateFormat="<%= DateFormat.SHORT %>"/>
          </td>
        </tr>
        <tr class="containerBody">
          <td class="formLabel">
            Entered
          </td>
          <td>
            <dhv:username id="<%= CallDetails.getEnteredBy() %>"/>
            -
            <dhv:tz timestamp="<%= CallDetails.getEntered() %>" dateFormat="<%= DateFormat.SHORT %>" timeFormat="<%= DateFormat.LONG %>"/>
          </td>
        </tr>
        <tr class="containerBody">
          <td nowrap class="formLabel">
            Modified
          </td>
          <td>
            <dhv:username id="<%= CallDetails.getModifiedBy() %>"/>
            -
            <dhv:tz timestamp="<%= CallDetails.getModified() %>" dateFormat="<%= DateFormat.SHORT %>" timeFormat="<%= DateFormat.LONG %>"/>
          </td>
        </tr>
      </table><br>
      <dhv:permission name="pipeline-opportunities-calls-edit"><input type="button" value="Modify" onClick="javascript:window.location.href='LeadsCalls.do?command=Modify&id=<%= CallDetails.getId() %>&headerId=<%= opportunityHeader.getId() %><%= addLinkParams(request, "viewSource") %>';"></dhv:permission>
      <dhv:permission name="pipeline-opportunities-calls-delete"><input type="button" value="Delete" onClick="javascript:return confirmDelete('LeadsCalls.do?command=Delete&id=<%= CallDetails.getId() %>&headerId=<%= opportunityHeader.getId() %><%= addLinkParams(request, "viewSource") %>')"></dhv:permission>
      <dhv:permission name="pipeline-opportunities-calls-view"><input type="button" name="action" value="Forward" onClick="javascript:window.location.href='LeadsCallsForward.do?command=ForwardCall&forwardType=<%= Constants.PIPELINE_CALLS %>&headerId=<%= opportunityHeader.getId() %>&id=<%=CallDetails.getId()%><%= addLinkParams(request, "viewSource") %>';"></dhv:permission>
    </td>
  </tr>
</table>

