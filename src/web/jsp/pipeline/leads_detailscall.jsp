<%-- 
  - Copyright(c) 2004 Dark Horse Ventures LLC (http://www.centriccrm.com/) All
  - rights reserved. This material cannot be distributed without written
  - permission from Dark Horse Ventures LLC. Permission to use, copy, and modify
  - this material for internal use is hereby granted, provided that the above
  - copyright notice and this permission notice appear in all copies. DARK HORSE
  - VENTURES LLC MAKES NO REPRESENTATIONS AND EXTENDS NO WARRANTIES, EXPRESS OR
  - IMPLIED, WITH RESPECT TO THE SOFTWARE, INCLUDING, BUT NOT LIMITED TO, THE
  - IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR ANY PARTICULAR
  - PURPOSE, AND THE WARRANTY AGAINST INFRINGEMENT OF PATENTS OR OTHER
  - INTELLECTUAL PROPERTY RIGHTS. THE SOFTWARE IS PROVIDED "AS IS", AND IN NO
  - EVENT SHALL DARK HORSE VENTURES LLC OR ANY OF ITS AFFILIATES BE LIABLE FOR
  - ANY DAMAGES, INCLUDING ANY LOST PROFITS OR OTHER INCIDENTAL OR CONSEQUENTIAL
  - DAMAGES RELATING TO THE SOFTWARE.
  - 
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
<script type="text/javascript">
function reopenOpportunity(id) {
  if (id == '<%= opportunityHeader.getId() %>') {
    if ('<%= "dashboard".equals(request.getParameter("viewSource")) %>' == 'true') {
      scrollReload('Leads.do?command=Dashboard');
    } else {
      scrollReload('Leads.do?command=Search');
    }
    return id;
  } else {
    return '<%= opportunityHeader.getId() %>';
  }
}
</script>
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="Leads.do"><dhv:label name="pipeline.pipeline">Pipeline</dhv:label></a> >
<% if ("dashboard".equals(request.getParameter("viewSource"))){ %>
	<a href="Leads.do?command=Dashboard"><dhv:label name="communications.campaign.Dashboard">Dashboard</dhv:label></a> >
<% }else{ %>
	<a href="Leads.do?command=Search"><dhv:label name="accounts.SearchResults">Search Results</dhv:label></a> >
<% } %>
<a href="Leads.do?command=DetailsOpp&headerId=<%= opportunityHeader.getId() %><%= addLinkParams(request, "viewSource|view") %>"><dhv:label name="accounts.accounts_contacts_oppcomponent_add.OpportunityDetails">Opportunity Details</dhv:label></a> >
<a href="LeadsCalls.do?command=View&headerId=<%= opportunityHeader.getId() %><%= addLinkParams(request, "viewSource|view") %>"><dhv:label name="accounts.accounts_calls_list.Activities">Activities</dhv:label></a> >
<dhv:label name="accounts.accounts_contacts_calls_add.ActivityDetails">Activity Details</dhv:label>
</td>
</tr>
</table>
<%-- End Trails --%>
<dhv:evaluate if="<%= PipelineViewpointInfo.isVpSelected(User.getUserId()) %>">
  <dhv:label name="pipeline.viewpoint.colon" param="<%= "username="+PipelineViewpointInfo.getVpUserName() %>"><b>Viewpoint: </b><b class="highlight"><%= PipelineViewpointInfo.getVpUserName() %></b></dhv:label><br />
  &nbsp;<br>
</dhv:evaluate>
<% String param1 = "id=" + opportunityHeader.getId(); 
   String param2 = addLinkParams(request, "viewSource");
%>
<dhv:container name="opportunities" selected="calls" object="opportunityHeader" param="<%= param1 %>" appendToUrl="<%= param2 %>">
  <dhv:evaluate if="<%= "pending".equals(request.getParameter("view")) %>">
    <dhv:permission name="pipeline-opportunities-calls-view"><input type="button" value="<dhv:label name="global.button.complete">Complete</dhv:label>" onClick="javascript:window.location.href='LeadsCalls.do?command=Complete&headerId=<%= opportunityHeader.getId() %>&id=<%= CallDetails.getId() %><%= addLinkParams(request, "viewSource|view") %>'"></dhv:permission>
  </dhv:evaluate>
  <% if("pending".equals(request.getParameter("view")) || CallDetails.getStatusId() != Call.CANCELED){ %>
  <dhv:permission name="pipeline-opportunities-calls-edit"><input type="button" onclick="javascript:window.location.href='LeadsCalls.do?command=Modify&headerId=<%= opportunityHeader.getId() %>&id=<%= CallDetails.getId() %><%= addLinkParams(request, "viewSource|view") %>';" value="<dhv:label name="global.button.modify">Modify</dhv:label>"></dhv:permission>
  <%}%>
  <dhv:evaluate if="<%= "pending".equals(request.getParameter("view")) %>">
    <dhv:permission name="pipeline-opportunities-calls-edit"><input type="button" value="<dhv:label name="global.button.CancelPendingActivity">Cancel Pending Activity</dhv:label>" onClick="javascript:window.location.href='LeadsCalls.do?command=Cancel&headerId=<%= opportunityHeader.getId() %>&id=<%= CallDetails.getId() %>&action=cancel<%= addLinkParams(request, "viewSource|view") %>';"></dhv:permission>
  </dhv:evaluate>
  <dhv:evaluate if="<%= !isPopup(request) %>">
    <dhv:permission name="pipeline-opportunities-calls-view"><input type="button" name="action" value="<dhv:label name="accounts.accounts_calls_list_menu.Forward">Forward</dhv:label>" onClick="javascript:window.location.href='LeadsCallsForward.do?command=ForwardCall&headerId=<%= opportunityHeader.getId() %>&id=<%= CallDetails.getId() %><%= addLinkParams(request, "viewSource|view") %>'"></dhv:permission>
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
        <strong><dhv:label name="accounts.accounts_contacts_calls_details.RecordInformation">Record Information</dhv:label></strong>
      </th>
    </tr>
    <tr class="containerBody">
      <td class="formLabel" nowrap>
        <dhv:label name="accounts.accounts_calls_list.Entered">Entered</dhv:label>
      </td>
      <td>
        <dhv:username id="<%= CallDetails.getEnteredBy() %>"/>
        <zeroio:tz timestamp="<%= CallDetails.getEntered() %>" />
      </td>
    </tr>
    <tr class="containerBody">
      <td class="formLabel" nowrap>
        <dhv:label name="accounts.accounts_contacts_calls_details.Modified">Modified</dhv:label>
      </td>
      <td>
        <dhv:username id="<%= CallDetails.getModifiedBy() %>"/>
        <zeroio:tz timestamp="<%= CallDetails.getModified() %>" />
      </td>
    </tr>
  </table>
  <br>
  <dhv:evaluate if="<%= "pending".equals(request.getParameter("view")) %>">
  <dhv:permission name="pipeline-opportunities-calls-view"><input type="button" value="<dhv:label name="global.button.complete">Complete</dhv:label>" onClick="javascript:window.location.href='LeadsCalls.do?command=Complete&headerId=<%= opportunityHeader.getId() %>&id=<%= CallDetails.getId() %><%= addLinkParams(request, "viewSource|view") %>'"></dhv:permission>
  </dhv:evaluate>
  <% if("pending".equals(request.getParameter("view")) || CallDetails.getStatusId() != Call.CANCELED){ %>
  <dhv:permission name="pipeline-opportunities-calls-edit"><input type="button" onclick="javascript:window.location.href='LeadsCalls.do?command=Modify&headerId=<%= opportunityHeader.getId() %>&id=<%= CallDetails.getId() %><%= addLinkParams(request, "viewSource|view") %>';" value="<dhv:label name="global.button.modify">Modify</dhv:label>"></dhv:permission>
  <%}%>
  <dhv:evaluate if="<%= "pending".equals(request.getParameter("view")) %>">
  <dhv:permission name="pipeline-opportunities-calls-edit"><input type="button" value="<dhv:label name="global.button.CancelPendingActivity">Cancel Pending Activity</dhv:label>" onClick="javascript:window.location.href='LeadsCalls.do?command=Cancel&headerId=<%= opportunityHeader.getId() %>&id=<%= CallDetails.getId() %>&action=cancel<%= addLinkParams(request, "viewSource|view") %>';"></dhv:permission></dhv:evaluate>
  <dhv:evaluate if="<%= !isPopup(request) %>">
  <dhv:permission name="pipeline-opportunities-calls-view"><input type="button" name="action" value="<dhv:label name="accounts.accounts_calls_list_menu.Forward">Forward</dhv:label>" onClick="javascript:window.location.href='LeadsCallsForward.do?command=ForwardCall&headerId=<%= opportunityHeader.getId() %>&id=<%= CallDetails.getId() %><%= addLinkParams(request, "viewSource|view") %>'"></dhv:permission>
  </dhv:evaluate>
  <dhv:permission name="pipeline-opportunities-calls-edit,pipeline-opportunities-calls-delete"><br>&nbsp;</dhv:permission>
</dhv:container>

