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
<%@ page import="org.aspcfs.modules.pipeline.base.*,com.zeroio.iteam.base.*" %>
<jsp:useBean id="PipelineViewpointInfo" class="org.aspcfs.utils.web.ViewpointInfo" scope="session"/>
<jsp:useBean id="opportunityHeader" class="org.aspcfs.modules.pipeline.base.OpportunityHeader" scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
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
<a href="Leads.do?command=DetailsOpp&headerId=<%= request.getParameter("headerId") %><%= addLinkParams(request, "viewSource") %>">Opportunity Details</a> >
<a href="LeadsCalls.do?command=View&headerId=<%= request.getParameter("headerId") %><%= addLinkParams(request, "viewSource") %>">Activities</a> >
<dhv:evaluate if="<%= !"list".equals(request.getParameter("return")) %>">
<a href="LeadsCalls.do?command=Details&headerId=<%= request.getParameter("headerId") %>&id=<%= request.getParameter("id") %><%= addLinkParams(request, "viewSource") %>">Activity Details</a> >
</dhv:evaluate>
Forward Activity
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
    <form name="newMessageForm" action="LeadsCallsForward.do?command=SendCall&headerId=<%= request.getParameter("headerId") %>&id=<%= request.getParameter("id") %>" method="post" onSubmit="return sendMessage();">
    <input type="submit" value="Send">
    <% if("list".equals(request.getParameter("return"))){ %>
      <input type="button" value="Cancel" onClick="javascript:window.location.href='LeadsCalls.do?command=View&headerId=<%= request.getParameter("headerId") %><%= addLinkParams(request, "viewSource") %>'">
    <% }else{ %>
      <input type="button" value="Cancel" onClick="javascript:window.location.href='LeadsCalls.do?command=Details&id=<%= request.getParameter("id")%>&headerId=<%= opportunityHeader.getId() %><%= addLinkParams(request, "viewSource") %>'">
    <%}%>
    <br><br>
    
    <%@ include file="../newmessage.jsp" %>
    <br>
    <input type="submit" value="Send">
    <% if("list".equals(request.getParameter("return"))){ %>
      <input type="button" value="Cancel" onClick="javascript:window.location.href='LeadsCalls.do?command=View&headerId=<%= request.getParameter("headerId") %><%= addLinkParams(request, "viewSource") %>'">
    <% }else{ %>
      <input type="button" value="Cancel" onClick="javascript:window.location.href='LeadsCalls.do?command=Details&id=<%= request.getParameter("id")%>&headerId=<%= opportunityHeader.getId() %><%= addLinkParams(request, "viewSource") %>'">
    <%}%>
    <input type="hidden" name="headerId" value="<%= opportunityHeader.getId() %>">
    <%= addHiddenParams(request, "viewSource") %>
    </form>
  </td>
  </tr>
</table>
