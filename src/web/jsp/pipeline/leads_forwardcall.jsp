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
<a href="LeadsCalls.do?command=Details&headerId=<%= request.getParameter("headerId") %>&id=<%= request.getParameter("id") %><%= addLinkParams(request, "viewSource") %>">Call Details</a> >
</dhv:evaluate>
Call Forward
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
