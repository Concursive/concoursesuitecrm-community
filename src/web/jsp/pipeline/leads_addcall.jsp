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
<%@ page import="java.util.*,org.aspcfs.modules.pipeline.base.*,org.aspcfs.modules.contacts.base.Call,com.zeroio.iteam.base.*" %>
<jsp:useBean id="opportunityHeader" class="org.aspcfs.modules.pipeline.base.OpportunityHeader" scope="request"/>
<jsp:useBean id="CallDetails" class="org.aspcfs.modules.contacts.base.Call" scope="request"/>
<jsp:useBean id="PreviousCallDetails" class="org.aspcfs.modules.contacts.base.Call" scope="request"/>
<jsp:useBean id="ContactList" class="org.aspcfs.modules.contacts.base.ContactList" scope="request"/>
<jsp:useBean id="PipelineViewpointInfo" class="org.aspcfs.utils.web.ViewpointInfo" scope="session"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<jsp:useBean id="TimeZoneSelect" class="org.aspcfs.utils.web.HtmlSelectTimeZone" scope="request"/>
<%@ include file="../initPage.jsp" %>
<body onLoad="javascript:document.forms[0].callTypeId.focus();">
<form name="addCall" action="LeadsCalls.do?command=Save&auto-populate=true" onSubmit="return doCheck(this);" method="post">
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
<% if(PreviousCallDetails.getId() > 0 && !"cancel".equals(request.getParameter("action"))){ %>
  <% if (!"list".equals(request.getParameter("return"))){ %>
    <a href="LeadsCalls.do?command=Details&headerId=<%= opportunityHeader.getId() %>&id=<%= (PreviousCallDetails.getId() > -1 ? PreviousCallDetails.getId() : CallDetails.getId()) %><%= addLinkParams(request, "viewSource") %>">Activity Details</a> >
  <% } %>
  Complete Activity
<% }else if(PreviousCallDetails.getId() > 0 && "cancel".equals(request.getParameter("action"))){ %>
  <% if (!"list".equals(request.getParameter("return"))){ %>
    <a href="LeadsCalls.do?command=Details&headerId=<%= opportunityHeader.getId() %>&id=<%= (PreviousCallDetails.getId() > -1 ? PreviousCallDetails.getId() : CallDetails.getId()) %><%= addLinkParams(request, "viewSource") %>">Activity Details</a> >
  <% } %>
  Cancel Activity
<% }else{ %>
Add Activity
<% } %>
</td>
</tr>
</table>
<%-- End Trails --%>
<dhv:evaluate exp="<%= PipelineViewpointInfo.isVpSelected(User.getUserId()) %>">
  <b>Viewpoint: </b><b class="highlight"><%= PipelineViewpointInfo.getVpUserName() %></b><br>
  &nbsp;<br>
</dhv:evaluate>
<%-- Begin container --%>
<%@ include file="leads_details_header_include.jsp" %>
<% String param1 = "id=" + opportunityHeader.getId(); 
   String param2 = addLinkParams(request, "viewSource");
%>
<dhv:container name="opportunities" selected="calls" param="<%= param1 %>" appendToUrl="<%= param2 %>" style="tabs"/>
<table cellpadding="4" cellspacing="0" border="0" width="100%">
  <tr>
    <td class="containerBack">
<%-- Begin the container contents --%>
      <input type="submit" value="Save" onClick="this.form.dosubmit.value='true';">
      <input type="submit" value="Cancel" onClick="javascript:this.form.action='LeadsCalls.do?command=View&headerId=<%= opportunityHeader.getId() %>';this.form.dosubmit.value='false';">
      <br />
      <dhv:formMessage />
      <iframe src="empty.html" name="server_commands" id="server_commands" style="visibility:hidden" height="0"></iframe>
      <%@ include file="leads_call_include.jsp" %>
      &nbsp;
      <br />
      <input type="submit" value="Save" onClick="this.form.dosubmit.value='true';">
      <input type="submit" value="Cancel" onClick="javascript:this.form.action='LeadsCalls.do?command=View&headerId=<%= opportunityHeader.getId() %>';this.form.dosubmit.value='false';">
      <input type="hidden" name="dosubmit" value="true">
      <input type="hidden" name="oppHeaderId" value=<%= opportunityHeader.getId() %>>
      <input type="hidden" name="headerId" value=<%= opportunityHeader.getId() %>>
      <dhv:evaluate if="<%= PreviousCallDetails.getId() > -1 %>">
      <input type="hidden" name="parentId" value="<%= PreviousCallDetails.getId() %>">
      </dhv:evaluate>
      <%= addHiddenParams(request, "action|viewSource") %>
<%-- End container contents --%>
    </td>
  </tr>
</table>
<%-- End container --%>
</form>
</body>
