<%-- 
  - Copyright Notice: (C) 2000-2004 Dark Horse Ventures, All Rights Reserved.
  - License: This source code cannot be modified, distributed or used without
  -          written permission from Dark Horse Ventures. This notice must
  -          remain in place.
  - Version: $Id$
  - Description: 
  --%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ page import="java.util.*,java.text.DateFormat,org.aspcfs.modules.contacts.base.Call,org.aspcfs.modules.pipeline.base.*,com.zeroio.iteam.base.*" %>
<%@ page import="org.aspcfs.modules.base.Constants" %>
<jsp:useBean id="opportunityHeader" class="org.aspcfs.modules.pipeline.base.OpportunityHeader" scope="request"/>
<jsp:useBean id="CallDetails" class="org.aspcfs.modules.contacts.base.Call" scope="request"/>
<jsp:useBean id="PipelineViewpointInfo" class="org.aspcfs.utils.web.ViewpointInfo" scope="session"/>
<jsp:useBean id="ReminderTypeList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="CallResult" class="org.aspcfs.modules.contacts.base.CallResult" scope="request"/>
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
<a href="LeadsCalls.do?command=View&headerId=<%= opportunityHeader.getId() %><%= addLinkParams(request, "viewSource") %>">Activities</a> >
Activity Details
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
      <dhv:evaluate if="<%= "pending".equals(request.getParameter("view")) %>">
      <dhv:permission name="pipeline-opportunities-calls-view"><input type="button" value="Complete" onClick="javascript:window.location.href='LeadsCalls.do?command=Complete&headerId=<%= opportunityHeader.getId() %>&id=<%= CallDetails.getId() %><%= addLinkParams(request, "viewSource") %>'"></dhv:permission>
     </dhv:evaluate>
     <% if("pending".equals(request.getParameter("view")) || CallDetails.getStatusId() != Call.CANCELED){ %>
      <dhv:permission name="pipeline-opportunities-calls-edit"><input type="button" onclick="javascript:window.location.href='LeadsCalls.do?command=Modify&headerId=<%= opportunityHeader.getId() %>&id=<%= CallDetails.getId() %><%= addLinkParams(request, "viewSource") %>';" value="Modify"></dhv:permission>
      <%}%>
      <dhv:evaluate if="<%= "pending".equals(request.getParameter("view")) %>">
      <dhv:permission name="pipeline-opportunities-calls-edit"><input type="button" value="Cancel Pending Activity" onClick="javascript:window.location.href='LeadsCalls.do?command=Cancel&headerId=<%= opportunityHeader.getId() %>&id=<%= CallDetails.getId() %>&action=cancel<%= addLinkParams(request, "viewSource") %>';"></dhv:permission></dhv:evaluate>
      <dhv:evaluate exp="<%= !isPopup(request) %>">
      <dhv:permission name="pipeline-opportunities-calls-view"><input type="button" name="action" value="Forward" onClick="javascript:window.location.href='LeadsCallsForward.do?command=ForwardCall&headerId=<%= opportunityHeader.getId() %>&id=<%= CallDetails.getId() %><%= addLinkParams(request, "viewSource") %>'"></dhv:permission>
      </dhv:evaluate>
      <dhv:permission name="pipeline-opportunities-calls-edit,pipeline-opportunities-calls-delete"><br>&nbsp;</dhv:permission>
      
      <% if("pending".equals(request.getParameter("view"))){ %>
        <dhv:evaluate if="<%= CallDetails.getAlertDate() != null %>">
        <%-- include follow up activity details --%>
        <%@ include file="../accounts/accounts_contacts_calls_details_followup_include.jsp" %>
        </dhv:evaluate>
        &nbsp;
        <%-- include completed activity details --%>
        <%@ include file="../accounts/accounts_contacts_calls_details_include.jsp" %>
      <% }else{ %>
        <%-- include completed activity details --%>
        <%@ include file="../accounts/accounts_contacts_calls_details_include.jsp" %>
        &nbsp;
        <dhv:evaluate if="<%= CallDetails.getAlertDate() != null %>">
        <%-- include follow up activity details --%>
        <%@ include file="../accounts/accounts_contacts_calls_details_followup_include.jsp" %>
        </dhv:evaluate>
      <% } %>
      
      &nbsp;
      <table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
        <tr>
          <th colspan="2">
            <strong>Record Information</strong>  
          </th>
        </tr>
        <tr class="containerBody">
          <td class="formLabel" nowrap>
            Entered
          </td>
          <td>
            <dhv:username id="<%= CallDetails.getEnteredBy() %>"/>
            <zeroio:tz timestamp="<%= CallDetails.getEntered() %>" />
          </td>
        </tr>
        <tr class="containerBody">
          <td class="formLabel" nowrap>
            Modified
          </td>
          <td>
            <dhv:username id="<%= CallDetails.getModifiedBy() %>"/>
            <zeroio:tz timestamp="<%= CallDetails.getModified() %>" />
          </td>
        </tr>
      </table>
      <br>
      <dhv:evaluate if="<%= "pending".equals(request.getParameter("view")) %>">
      <dhv:permission name="pipeline-opportunities-calls-view"><input type="button" value="Complete" onClick="javascript:window.location.href='LeadsCalls.do?command=Complete&headerId=<%= opportunityHeader.getId() %>&id=<%= CallDetails.getId() %><%= addLinkParams(request, "viewSource") %>'"></dhv:permission>
     </dhv:evaluate>
     <% if("pending".equals(request.getParameter("view")) || CallDetails.getStatusId() != Call.CANCELED){ %>
      <dhv:permission name="pipeline-opportunities-calls-edit"><input type="button" onclick="javascript:window.location.href='LeadsCalls.do?command=Modify&headerId=<%= opportunityHeader.getId() %>&id=<%= CallDetails.getId() %><%= addLinkParams(request, "viewSource") %>';" value="Modify"></dhv:permission>
      <%}%>
      <dhv:evaluate if="<%= "pending".equals(request.getParameter("view")) %>">
      <dhv:permission name="pipeline-opportunities-calls-edit"><input type="button" value="Cancel Pending Activity" onClick="javascript:window.location.href='LeadsCalls.do?command=Cancel&headerId=<%= opportunityHeader.getId() %>&id=<%= CallDetails.getId() %>&action=cancel<%= addLinkParams(request, "viewSource") %>';"></dhv:permission></dhv:evaluate>
      <dhv:evaluate exp="<%= !isPopup(request) %>">
      <dhv:permission name="pipeline-opportunities-calls-view"><input type="button" name="action" value="Forward" onClick="javascript:window.location.href='LeadsCallsForward.do?command=ForwardCall&headerId=<%= opportunityHeader.getId() %>&id=<%= CallDetails.getId() %><%= addLinkParams(request, "viewSource") %>'"></dhv:permission>
      </dhv:evaluate>
      <dhv:permission name="pipeline-opportunities-calls-edit,pipeline-opportunities-calls-delete"><br>&nbsp;</dhv:permission>
    </td>
  </tr>
</table>

