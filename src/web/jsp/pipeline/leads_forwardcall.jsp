<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%-- Trails --%>
<table class="trails">
<tr>
<td>
<a href="Leads.do">Pipeline Management</a> > 
<a href="Leads.do?command=ViewOpp">View Components</a> >
<a href="Leads.do?command=DetailsOpp&headerId=<%= request.getParameter("headerId") %>">Opportunity Details</a> >
<a href="LeadsCalls.do?command=View&headerId=<%= request.getParameter("headerId") %>">Calls</a> >
<dhv:evaluate if="<%= !"list".equals(request.getParameter("return")) %>">
<a href="LeadsCalls.do?command=Details&headerId=<%= request.getParameter("headerId") %>&id=<%= request.getParameter("id") %>">Call Details</a> >
</dhv:evaluate>
Call Forward
</td>
</tr>
</table>
<%-- End Trails --%>
<input type="submit" value="Send">
<% if("list".equals(request.getParameter("return"))){ %>
  <input type="button" value="Cancel" onClick="javascript:window.location.href='LeadsCalls.do?command=View&headerId=<%= request.getParameter("headerId") %>'">
<% }else{ %>
  <input type="button" value="Cancel" onClick="javascript:window.location.href='LeadsCalls.do?command=Details&id=<%= request.getParameter("id")%>&headerId=<%= request.getParameter("headerId") %>'">
<%}%>
<br><br>
<form name="newMessageForm" action="LeadsCallsForward.do?command=SendCall&headerId=<%= request.getParameter("headerId") %>&id=<%= request.getParameter("id") %>" method="post" onSubmit="return sendMessage();">
<%@ include file="../newmessage.jsp" %>
<br>
<input type="submit" value="Send">
<% if("list".equals(request.getParameter("return"))){ %>
  <input type="button" value="Cancel" onClick="javascript:window.location.href='LeadsCalls.do?command=View&headerId=<%= request.getParameter("headerId") %>'">
<% }else{ %>
  <input type="button" value="Cancel" onClick="javascript:window.location.href='LeadsCalls.do?command=Details&id=<%= request.getParameter("id")%>&headerId=<%= request.getParameter("headerId") %>'">
<%}%>
</form>
